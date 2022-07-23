package com.insigniait.accessControl.dto;

public class HikSenseDevice {

	protected String hostName;
	protected String user;
	private String pass;
	protected String httpProtocol;
	protected int httpPort;
	protected int rtspPort;
	
	public HikSenseDevice(String hostName, String user, String pass) {
		this.hostName = hostName;
		this.user = user;
		this.pass = pass;
		this.httpProtocol = "http";
		this.httpPort = 80;
		this.rtspPort = 554;
	}

	public void setHttpProtocol(String httpProtocol) {
		this.httpProtocol = httpProtocol;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public void setRtspPort(int rtspPort) {
		this.rtspPort = rtspPort;
	}
	
	protected String getPassword() {
		return this.pass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HikSenseDevice [hostName=");
		builder.append(hostName);
		builder.append(", user=");
		builder.append(user);
		builder.append(", pass= ******");
		builder.append(", httpProtocol=");
		builder.append(httpProtocol);
		builder.append(", httpPort=");
		builder.append(httpPort);
		builder.append(", rtspPort=");
		builder.append(rtspPort);
		builder.append("]");
		return builder.toString();
	}
}
