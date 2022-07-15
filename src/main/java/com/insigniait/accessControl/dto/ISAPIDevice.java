package com.insigniait.accessControl.dto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ISAPIDevice extends HikSenseDevice {
	
	protected RestTemplate isapi;
	protected ObjectMapper mapper;
	
	public ISAPIDevice(String hostName, String user, String pass, String httpProtocol, int httpPort) {
		super(hostName, user, pass, httpProtocol, httpPort);
		this.isapi = createConnection();
		this.mapper = createObjectMapper();
	}
	
	public Boolean adduser(UserInfo userInfo) {
		String service = getISAPIUrl() + "/AccessControl/UserInfo/Record?format=json";
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("UserInfo", userInfo);
		
		ResponseEntity<GenericResponse> response = isapi.postForEntity(service, body, GenericResponse.class);
		return response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccessful();
	}
	
	public void fetchEvents(Consumer<DeviceEvent> callback) {
		
		String service = getISAPIUrl() + "/Event/notification/alertStream";
		
		ResponseExtractor<File> responseExtractor = response -> {
			
			Integer bufferSize = 4 * 1024;
			byte[] buffer = new byte[bufferSize];
			int bytesRead;
			
			InputStream is = response.getBody();
		
		    while ((bytesRead = is.read(buffer)) != -1) {
		    	
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        baos.write(buffer, 0, bytesRead);
		        
		        String eventText = new String(baos.toByteArray());
		        String eventJson = eventText.substring(eventText.indexOf("{"), eventText.length() - 1);
		        
		        DeviceEvent event = mapper
		        		.createParser(eventJson)
		        		.readValueAs(DeviceEvent.class);
		        
		        if(callback != null) {
		        	callback.accept(event);
		        }
		        
		        IOUtils.closeQuietly(baos);
		    }
		    
		    IOUtils.closeQuietly(is);
			return null;
		};
		
		while(true) {
			
			try {
				isapi.execute(service, HttpMethod.GET, null, responseExtractor);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private RestTemplate createConnection() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pass));		
	    HttpClient httpClient = HttpClients
	            .custom()
	            .setDefaultCredentialsProvider(credentialsProvider)
	            .build();
	    RestTemplate restTemplate = new RestTemplateBuilder()
	    		.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
	    		.build();
	    
	    return restTemplate;
	}
	
	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
	
	protected String getISAPIUrl() {
		return httpProtocol + "://" + hostName + ":" + httpPort + "/ISAPI";
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return sdf.format(date);
	}
}
