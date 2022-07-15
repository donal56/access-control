package com.insigniait.accessControl.dto;

public class GenericResponse {
	
	private Integer statusCode;
	private String statusString;
	private String subStatusCode;
	private String errorCode;
	private String errorMsg;
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusString() {
		return statusString;
	}
	
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	
	public String getSubStatusCode() {
		return subStatusCode;
	}
	
	public void setSubStatusCode(String subStatusCode) {
		this.subStatusCode = subStatusCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean isSuccessful() {
		return this.statusString.equals("OK");
	}
}
