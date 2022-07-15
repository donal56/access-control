package com.insigniait.accessControl.controllers;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insigniait.accessControl.dto.DeviceEvent;

@Controller
@RequestMapping(path = "/ws")
public class WebSocketController {

	@SubscribeMapping("/topic/face-recognition")
	@SendTo("/topic/face-recognition")
	public DeviceEvent alertStream() {
		return new DeviceEvent();
	}
}
