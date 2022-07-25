package com.insigniait.accessControl.config;

import java.io.File;
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

import com.insigniait.accessControl.dto.AccessControlDevice;
import com.insigniait.accessControl.dto.AccessControllerEvent;
import com.insigniait.accessControl.dto.GenericEvent;

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
    	
    	AccessControlDevice terminal = new AccessControlDevice("192.168.1.194", "admin", "Insigniait01!");
    	terminal.setSnapshotsPath(snapshotsPath);
    	
    	Runnable faceRecognitionEventsThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				terminal.captureFaceData();
				
				/*terminal.fetchEvents(new Consumer<GenericEvent>() {
					
					@Override
					public void accept(GenericEvent event) {
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
								Map<String, Object> customData = new HashMap<String, Object>();
								customData.put("newUser", newUser);

								//terminal.captureFaceData();
								File snapshot = terminal.retrieveSnapshot(101, newUser);
								
								terminal.agregarRostro("1", newUser, "Invitado", snapshot);
								
								event.setCustomData(customData);
							}

							simpMessagingTemplate.convertAndSend("/topic/face-recognition", event);
				        }
					}
				});*/
			}
    	});
    	
        taskExecutor.execute(faceRecognitionEventsThread);
    }
}