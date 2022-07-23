package com.insigniait.accessControl.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
    	
    	String snapshotsPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\visitors";
    	
    	FaceRecognitionTerminal terminal = new FaceRecognitionTerminal("192.168.100.48", "admin", "Insigniait01!");
    	terminal.setSnapshotsPath(snapshotsPath);
    	
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
								
								File snapshot = terminal.retrieveSnapshot(null, newUser);
								try {
									byte[] snapshotBytes = Base64.encodeBase64(FileUtils.readFileToByteArray(snapshot));
									customData.put("snapshot", new String(snapshotBytes));
								} 
								catch (IOException e) {
									e.printStackTrace();
								}
								
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