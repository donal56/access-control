package com.insigniait.accessControl.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DeviceEvent {

	private String ipAddress;
	private Integer portNo;
	private String protocol;
	private Integer channelID;
	private String dateTime;
	private Integer activePostCount;
	private String eventType;
	private String eventState;
	private String eventDescription;
	@JsonAlias("AccessControllerEvent")
	private AccessControllerEvent accessControllerEvent;
	private Map<String, String> customData;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPortNo() {
		return portNo;
	}

	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getChannelID() {
		return channelID;
	}

	public void setChannelID(Integer channelID) {
		this.channelID = channelID;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getActivePostCount() {
		return activePostCount;
	}

	public void setActivePostCount(Integer activePostCount) {
		this.activePostCount = activePostCount;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventState() {
		return eventState;
	}

	public void setEventState(String eventState) {
		this.eventState = eventState;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public AccessControllerEvent getAccessControllerEvent() {
		return accessControllerEvent;
	}

	public void setAccessControllerEvent(AccessControllerEvent accessControllerEvent) {
		this.accessControllerEvent = accessControllerEvent;
	}

	public Map<String, String> getCustomData() {
		return customData;
	}

	public void setCustomData(Map<String, String> customData) {
		this.customData = customData;
	}
	
	@Override
	public String toString() {
		return "EventStreamResponse [ipAddress=" + ipAddress + ", portNo=" + portNo + ", protocol=" + protocol
				+ ", channelID=" + channelID + ", dateTime=" + dateTime + ", activePostCount=" + activePostCount
				+ ", eventType=" + eventType + ", eventState=" + eventState + ", eventDescription=" + eventDescription 
				+ (accessControllerEvent != null ? ", accessControllerEvent=" + accessControllerEvent : "")
				+ "]";
	}
}
