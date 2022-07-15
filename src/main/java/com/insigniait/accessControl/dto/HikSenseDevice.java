package com.insigniait.accessControl.dto;

public class HikSenseDevice {

	protected String hostName;
	protected String user;
	protected String pass;
	protected String httpProtocol;
	protected int httpPort;
	
	public HikSenseDevice(String hostName, String user, String pass, String httpProtocol, int httpPort) {
		this.hostName = hostName;
		this.user = user;
		this.pass = pass;
		this.httpProtocol = httpProtocol;
		this.httpPort = httpPort;
	}
}
