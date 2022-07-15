package com.insigniait.accessControl.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class MainTest {

	public static void main(String[] args) {
	    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "Insigniait01!"));

	    HttpClient httpClient = HttpClients
	            .custom()
	            .setDefaultCredentialsProvider(credentialsProvider)
				.useSystemProperties()		//?
	            .build();

	    RestTemplate restTemplate = new RestTemplateBuilder()
	    		.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
	    		.build();
		
	    HttpHost host = new HttpHost("192.168.1.68", 80, "http");
		String service = host.toString() + "/ISAPI/Event/notification/alertStream";
		String downloadPath = System.getProperty("user.home") + "/Documents/";
		
		RequestCallback requestCallback = request -> {				//?
			request.getHeaders().remove("Keep-Alive");
			request.getHeaders().remove("Connection");
			request.getHeaders().remove("Accept-Encoding");
		};
		
		ResponseExtractor<File> responseExtractor = response -> {
			
			InputStream is = response.getBody();
			
			Integer fileSize = 1024 * 1024;
			byte[] buffer = new byte[fileSize];
			int bytesRead;
			
		    while ((bytesRead = is.read(buffer)) != -1) {
		    	
		    	String fileName = System.currentTimeMillis() + ".json";
		    	String filePath = downloadPath + "/" + fileName;
		    	OutputStream outStream = new FileOutputStream(filePath);
		        outStream.write(buffer, 0, bytesRead);
		        
		        /*String json = Files
		        	.lines(Paths.get(filePath))
		        	.skip(4)
		        	.reduce(String::concat)
		        	.orElse("");
		        
		        outStream.flush();
		        outStream.write(json.getBytes());*/

		        IOUtils.closeQuietly(outStream);
		    }
		    
		    IOUtils.closeQuietly(is);
			return null;
		};
		
		while(true) {
			try {
				restTemplate.execute(service, HttpMethod.GET, requestCallback, responseExtractor);
			}
			catch(Exception e) {}
		}
	}
}
