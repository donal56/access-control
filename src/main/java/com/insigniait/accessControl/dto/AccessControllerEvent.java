package com.insigniait.accessControl.dto;

public class AccessControllerEvent {
	
	private String deviceName;
	private Integer majorEventType;
	private Integer subEventType;
	private String name;
	private Integer cardReaderKind;
	private Integer cardReaderNo;
	private Integer doorNo;
	private Integer serialNo;
	private String currentVerifyMode;
	private Boolean currentEvent;
	private Integer frontSerialNo;
	private String attendanceStatus;
	private String label;
	private Integer statusValue;
	private String mask;
	private Boolean purePwdVerifyEnable;
	private Integer verifyNo;
	private String employeeNoString;
	private String userType;
	private Integer picturesNumber;
	
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getMajorEventType() {
		return majorEventType;
	}

	public void setMajorEventType(Integer majorEventType) {
		this.majorEventType = majorEventType;
	}

	public Integer getSubEventType() {
		return subEventType;
	}

	public void setSubEventType(Integer subEventType) {
		this.subEventType = subEventType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCardReaderKind() {
		return cardReaderKind;
	}

	public void setCardReaderKind(Integer cardReaderKind) {
		this.cardReaderKind = cardReaderKind;
	}

	public Integer getCardReaderNo() {
		return cardReaderNo;
	}

	public void setCardReaderNo(Integer cardReaderNo) {
		this.cardReaderNo = cardReaderNo;
	}

	public Integer getDoorNo() {
		return doorNo;
	}

	public void setDoorNo(Integer doorNo) {
		this.doorNo = doorNo;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getCurrentVerifyMode() {
		return currentVerifyMode;
	}

	public void setCurrentVerifyMode(String currentVerifyMode) {
		this.currentVerifyMode = currentVerifyMode;
	}

	public Boolean getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Boolean currentEvent) {
		this.currentEvent = currentEvent;
	}

	public Integer getFrontSerialNo() {
		return frontSerialNo;
	}

	public void setFrontSerialNo(Integer frontSerialNo) {
		this.frontSerialNo = frontSerialNo;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public Boolean getPurePwdVerifyEnable() {
		return purePwdVerifyEnable;
	}

	public void setPurePwdVerifyEnable(Boolean purePwdVerifyEnable) {
		this.purePwdVerifyEnable = purePwdVerifyEnable;
	}

	public Integer getVerifyNo() {
		return verifyNo;
	}

	public void setVerifyNo(Integer verifyNo) {
		this.verifyNo = verifyNo;
	}

	public String getEmployeeNoString() {
		return employeeNoString;
	}

	public void setEmployeeNoString(String employeeNoString) {
		this.employeeNoString = employeeNoString;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getPicturesNumber() {
		return picturesNumber;
	}

	public void setPicturesNumber(Integer picturesNumber) {
		this.picturesNumber = picturesNumber;
	}

	@Override
	public String toString() {
		return "AccessControllerEvent [deviceName=" + deviceName + ", majorEventType=" + majorEventType
				+ ", subEventType=" + subEventType + ", name=" + name + ", cardReaderKind=" + cardReaderKind
				+ ", cardReaderNo=" + cardReaderNo + ", doorNo=" + doorNo + ", serialNo=" + serialNo
				+ ", currentVerifyMode=" + currentVerifyMode + ", currentEvent=" + currentEvent + ", frontSerialNo="
				+ frontSerialNo + ", attendanceStatus=" + attendanceStatus + ", label=" + label + ", statusValue="
				+ statusValue + ", mask=" + mask + ", purePwdVerifyEnable=" + purePwdVerifyEnable + "]";
	}
}