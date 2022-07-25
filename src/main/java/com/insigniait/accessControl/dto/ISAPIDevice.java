package com.insigniait.accessControl.dto;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insigniait.accessControl.util.StringUtils;

public class ISAPIDevice extends HikSenseDevice {
	
	protected RestTemplate isapi;
	protected ObjectMapper mapper;
	protected String snapshotsPath;
	private Boolean eventFetching;
	
	public ISAPIDevice(String hostName, String user, String pass) {
		super(hostName, user, pass);
		this.isapi = createConnection();
		this.mapper = createObjectMapper();
	}
	
	/**
	 * Escucha de manera indefinida los eventos de una terminal
	 * Llamar a endEventFetching() para finalizar
	 * @param callback - Metodo a llamar al encontrar un evento
	 */
	public void fetchEvents(Consumer<GenericEvent> callback) {
		
		String service = getIsapiUrl() + "/Event/notification/alertStream";
		
		// Controlar la continuidad en la lectura del stream
		this.eventFetching = true;
		
		// Leer JSON del evento y llamar al callback
		Consumer<String> readEventJson = new Consumer<String>() {
			@Override
			public void accept(String json) {
				GenericEvent event = null;
				try {
					event = mapper
							.createParser(json)
							.readValueAs(GenericEvent.class);
					
			        if(callback != null) {
			        	callback.accept(event);
			        }
				} 
				catch (IOException e) {
					System.err.println("No se pudo parsear: [" + json.substring(0, 120) + "...]");
				}
			}
		};
		
		ResponseExtractor<File> responseExtractor = rawResponse -> {
			

			// Detectar delimitador
			String contentType = rawResponse.getHeaders().getFirst("Content-Type");
			String boundary = contentType.replace("multipart/mixed; boundary=", "").trim();
			
			// Convertir InputStream en un MultipartStream
			// El tamaño de buffer debe estar entre el rango del tamaño promedio de las fotos y el tamaño minimo de una partición de datos del stream
			Integer bufferSize = 2 * 1024;
		    MultipartStream multipartStream = new MultipartStream(rawResponse.getBody(), boundary.getBytes(), bufferSize, null); 
		    ByteArrayOutputStream chunkStream = new ByteArrayOutputStream();
		    Boolean hasNext = multipartStream.skipPreamble(); 
		    
		    while(hasNext && eventFetching) { 
		    	
	            // Leer partición del stream
		    	multipartStream.readBodyData(chunkStream); 
		    	String chunk = chunkStream.toString();

		    	// El stream esta mal formado
		    	// No sigue el patrón correcto para el delimitador en ocaciones
		    	// Lo siguiente intenta solucionarlo
		    	String regex = "Content-Type:.*?\\nContent-Length: \\d+";
		    	Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
		    	String[] parts = pattern.split(chunk);
		    	
		    	for(String part : parts) {
		    		try {
		    			String json = part.substring(part.indexOf("{"), part.lastIndexOf("}") + 1).trim();
		    			
		    			if(!json.equals("")) {
		    				readEventJson.accept(json);
		    			}
		    		}
		    		catch(IndexOutOfBoundsException ioobe) {}
		    	}
	            
		        hasNext = multipartStream.readBoundary(); 
		        
//		        Map<String, String> headers = new HashMap<String, String>();
//		    	String headersText = multipartStream.readHeaders(); 
//		    	Matcher headerPropsMatcher = Pattern
//		    			.compile("(.+): (.+)", Pattern.MULTILINE)
//		    			.matcher(headersText);
//		    	
//		    	while(headerPropsMatcher.find()) {
//		    		headers.put(headerPropsMatcher.group(1), headerPropsMatcher.group(2));
//		    	}
//		    	
//	            if(headers.get("Content-Type").startsWith("image/")) {
//	            	
//	            	String imagePath = getSnapshotsPath() + "\\" + Long.toString(System.currentTimeMillis()) + 
//						(headers.get("Content-Type").equals("image/png") ? ".png" : ".jpg");
//				
//					try(OutputStream outputStream = new FileOutputStream(imagePath)) {
//						chunkStream.writeTo(outputStream);
//					}
//	            }
		    } 
			
			IOUtils.closeQuietly(chunkStream);
			return null;
		};
		
		while(eventFetching) {
			
			try {
				isapi.execute(service, HttpMethod.GET, null, responseExtractor);
			}
			catch(Exception e) {
				String fullMessage = StringUtils.deleteAny(e.getMessage(), "\n\r");
				
				if(e instanceof ResourceAccessException) {
					System.err.println("REINICIANDO CONEXIÓN... [Stream ended unexpectedly]");
				}
				else {
					String regex = "<statusString>(.*)</statusString>.*<subStatusCode>(.*)</subStatusCode>.*<errorMsg>(.*)</errorMsg>.*";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(fullMessage);
					
					if(matcher.find() && matcher.groupCount() == 3) {
						
						List<String> codes = Arrays.asList("deployExceedMax", "deviceBusy");
						
						if(!codes.contains(matcher.group(2))) {
							System.err.println("REINICIANDO CONEXIÓN... [" + matcher.group(1) + "/" + matcher.group(2) + "/" + matcher.group(3) + "/" + "]");
						}
						
						if(matcher.group(2).equals("deployExceedMax")) {
							try {
								Thread.sleep(1000);
							} 
							catch (InterruptedException e1) { }
						}
					}
					else {
						System.err.println("REINICIANDO CONEXIÓN... [" + e.getMessage() + "]");
					}
				}
			}
		}
	}
	
	/**
	 * Finaliza la recuperación de eventos
	 */
	public void endEventFetching() {
		this.eventFetching = false;
	}
	
	public AccessEvent fetchOneEvent(String date, Integer majorEventType, Integer minorEventType, Boolean hasPicture) {
		String service = getIsapiUrl() + "/AccessControl/AcsEvent?format=json";
		
		Map<String, Object> accessEventCondition = new HashMap<String, Object>();
		accessEventCondition.put("searchID", "AccessControlSystem--fetchOneEvent()");
		accessEventCondition.put("maxResults", 1);
		accessEventCondition.put("searchResultPosition", 0);
		accessEventCondition.put("startTime", date);
		accessEventCondition.put("endTime", date);
		accessEventCondition.put("major", majorEventType);
		accessEventCondition.put("minor", minorEventType);
		accessEventCondition.put("picEnable", hasPicture);
		
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("AcsEventCond", accessEventCondition);
		
		ResponseEntity<String> response = isapi.postForEntity(service, payload, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			try {
				JsonParser json = mapper.createParser(response.getBody());
				String realJson = json.readValueAsTree().get("AcsEvent").asToken().asString();
				return mapper.createParser(realJson).readValueAs(AccessEvent.class);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * Otras opciones:
	 * 		URL: http://192.168.100.48/ISAPI/Streaming/Channels/101/picture /httpPreview /preview 
	 */
	@Deprecated
	public File retrieveSnapshot(Integer channel, String fileName) {
		
		// Si no se especifica
		if(channel == null) {
			channel = 101;
		}
		
	    // Recuperar ruta de FFMPEG
		String ffmpegPath = null;
		try {
			ffmpegPath = new ClassPathResource("/static/ffmpeg.exe")
					.getURL()
					.getPath()
					.replace("%20", " ")
					.replace("/", "\\");
			ffmpegPath = ffmpegPath.substring(1, ffmpegPath.length());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Preparar y ejecutar comando
		Process process = null;
		String filePath = getSnapshotsPath() + "\\" + fileName + ".jpg";
		try {
			String command = StringUtils.doubleQuote(ffmpegPath) + " -y -loglevel fatal -rtsp_transport tcp -i " + 
					StringUtils.doubleQuote(getRtspUrl() + "/Streaming/channels/" + channel) + " -frames:v 1 " + 
					StringUtils.doubleQuote(filePath);
			process = Runtime.getRuntime().exec(command);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Esperar a que termine el prceso  y recuperar error de consola si existe
		Integer exitFlag;
		try {
			exitFlag = process.waitFor();
		} 
		catch (InterruptedException e) {
			exitFlag = -1;
			e.printStackTrace();
		}
		
		if(exitFlag == 0) {
			process.destroy();
			return new File(filePath);
		}
		else {
			String message = new BufferedReader(new InputStreamReader(process.getErrorStream()))
					.lines()
					.collect(Collectors.joining("\n"));
			process.destroy();
			throw new IllegalStateException(message);
		}
	}
	
	/**
	 * Configurar el directorio donde se guardan las imagenes
	 * @param snapshotsPath
	 */
	public void setSnapshotsPath(String snapshotsPath) {
		this.snapshotsPath = snapshotsPath;
	}

	protected static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return sdf.format(date);
	}
	
	protected String getIsapiUrl() {
		return httpProtocol + "://" + hostName + ":" + httpPort + "/ISAPI";
	}
	
	protected String getRtspUrl() {
		return "rtsp://" + user + ":" + getPassword() + "@" + hostName + ":" + rtspPort + "/ISAPI";
	}
	
	protected String getSnapshotsPath() {
		return snapshotsPath == null ? System.getProperty("user.home") : snapshotsPath;
	}
	
	private RestTemplate createConnection() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, getPassword()));		
	    HttpClient httpClient = HttpClients
	            .custom()
	            .setDefaultCredentialsProvider(credentialsProvider)
	            .setConnectionTimeToLive(1000, TimeUnit.MILLISECONDS)
	            .setRetryHandler(new HttpRequestRetryHandler() {
					
					@Override
					public boolean retryRequest(IOException e, int executionCount, HttpContext context) {
						if(e instanceof NoHttpResponseException) {
		                    return true;
		                }
						return false;
					}
				})
	            .build();
	    RestTemplate restTemplate = new RestTemplateBuilder()
	    		.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
	    		.setConnectTimeout(Duration.ofSeconds(20))
	    		.setReadTimeout(Duration.ofMinutes(1))
	    		.build();
	    
	    return restTemplate;
	}
	
	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
}
