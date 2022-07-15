package com.insigniait.accessControl.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * @author Doni
 *
 * TODO
 * -	Revisar e investigar HikSenseEventsThread.run.fileSize y porque no se dividen los archivos correctamente.
 * -	Al detectar una cara, correr un proceso de enrolamiento
 * -	MinorEvents: 75(Exito), 21, 22, 76(No exito), MajorEvent: 5, Verificado: face, invalid
 */
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.insigniait.accessControl")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean("threadManagerExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(30);
        return taskExecutor;
    }
}
