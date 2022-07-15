package com.insigniait.accessControl.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.insigniait.accessControl.dto.AccessControllerEvent;
import com.insigniait.accessControl.dto.DeviceEvent;
import com.insigniait.accessControl.dto.FaceRecognitionTerminal;

@Component
public class ThreadManager {

    @Autowired
    @Qualifier("threadManagerExecutor")
    private TaskExecutor taskExecutor;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void inicializarTerminalReconocimientoFacial() {
    	
    	FaceRecognitionTerminal terminal = new FaceRecognitionTerminal("192.168.1.68", "admin", "Insigniait01!", "http", 80);

    	Runnable faceRecognitionEventsThread = new Thread(new Runnable() {

			@Override
			public void run() {
				terminal.fetchEvents(new Consumer<DeviceEvent>() {
					
					@Override
					public void accept(DeviceEvent event) {
						AccessControllerEvent accessControllerEvent = event.getAccessControllerEvent();
						List<Integer> availableEvents = Arrays.asList(75, 76);
						
						Boolean isAccesEvent = accessControllerEvent != null && 
								accessControllerEvent.getMajorEventType() == 5 && 
								availableEvents.contains(accessControllerEvent.getSubEventType());
						
						if(isAccesEvent) {
							Boolean registrar = accessControllerEvent.getCurrentVerifyMode().equals("face") &&
									accessControllerEvent.getSubEventType() == 76;
							
							if(registrar) {
								String newUser = terminal.registerUnknowVistor();
								Map<String, String> customData = new HashMap<String, String>();
								customData.put("newUser", newUser);
								event.setCustomData(customData);
							}

							simpMessagingTemplate.convertAndSend("/topic/face-recognition", event);
				        }
					}
				});
			}
    	});
    	
        taskExecutor.execute(faceRecognitionEventsThread);
    }
}