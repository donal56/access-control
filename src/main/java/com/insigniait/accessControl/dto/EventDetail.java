package com.insigniait.accessControl.dto;

/**
 * @author Doni, 23/07/2022 17:52:07
 *
 */
public class EventDetail {

	/**
	 * Major alarm/event types (the type value should be transformed to the decimal number)
	 */
	private Integer major;
	
	/**
	 * Minor alarm/event types (the type value should be transformed to the decimal number)
	 */
	private Integer minor;
	
	/**
	 * Time (UTC time), e.g., "2016-12-12T17:30:08+08:00"
	 */
	private String time;
	
	/**
	 * Username
	 */
	private String netUser;
	
	/**
	 * Remote host address
	 */
	private String remoteHostAddr;
	
	/**
	 * Card No,
	 */
	private String cardNo;
	
	/**
	 * Card types: 1-normal card, 2-disabled card, 3-blacklist card, 4-patrol card, 5-duress card, 6-super card, 
	 * 7-visitor card, 8-dismiss card
	 */
	private Integer cardType;
	
	/**
	 * Whitelist No., which is between 1 and 8
	 */
	private Integer whiteListNo;
	
	/**
	 * Channel type for uploading alarm/event: 1-for uploading arming information, 2-for uploading by central group 1, 3-for uploading by central group 2
	 */
	private Integer reportChannel;
	
	/**
	 * Authentication unit type: 1-IC card reader, 2-ID card reader, 3-QR code scanner, 4-fingerprint module
	 */
	private Integer cardReaderKind;
	
	/**
	 * Authentication unit No.
	 */
	private Integer cardReaderNo;

	/**
	 * Coor or floor No.
	 */
	private Integer doorNo;

	/**
	 * Multiple authentication No.
	 */
	private Integer verifyNo;

	/**
	 * Alarm input No.
	 */
	private Integer alarmInNo;
	
	/**
	 * Alarm output No.
	 */
	private Integer alarmOutNo;

	/**
	 * Event trigger No.
	 */
	private Integer caseSensorNo;
	
	/**
	 * RS-485 channel No.
	 */
	private Integer RS485No;
	
	/**
	 * Group No.
	 */
	private Integer multiCardGroupNo;
	
	/**
	 * Swing barrier No.
	 */
	private Integer accessChannel;
	
	/**
	 * Device No.
	 */
	private Integer deviceNo;
	
	/**
	 * Distributed controller No.
	 */
	private Integer distractControlNo;
	
	/**
	 * Employee No. (person ID)
	 */
	private Integer employeeNoString;
	
	/**
	 * Distributed access controller No.: 0-access controller, 1 to 64-distributed access controller No.1 to distributed access controller No.64
	 */
	private Integer localControllerID;

	/**
	 * Network interface No.: 1-upstream network interface No.1, 2-upstream network interface No.2, 3-downstream network interface No.1
	 */
	private Integer InternetAccess;
	
	/**
	 * Zone type: 0-instant alarm zone, 1-24-hour alarm zone, 2-delayed zone, 3-internal zone, 4-key zone, 5-fire alarm zone, 
	 * 6-perimeter protection, 7-24-hour silent alarm zone, 8-24-hour auxiliary zone, 9-24-hour shock alarm zone, 
	 * 10-emergency door open alarm zone, 11-emergency door closed alarm zone, 255-none
	 */
	private Integer type;

	/**
	 * Physical address
	 */
	private String MACAddr;
	
	/**
	 * Card swiping types: 0-invalid, 1-QR code
	 */
	private Integer swipeCardType;

	/**
	 * Event serial No., which is used to judge whether the event loss occurred
	 */
	private Integer serialNo;
	
	/**
	 * Lane controller No.: 1-master lane controller, 2-slave lane controller
	 */
	private Integer channelControllerID;
	
	/**
	 * Light board No. of lane controller, which is between 1 and 255
	 */
	private Integer channelControllerLampID;
	
	/**
	 * IR adapter No. of lane controller, which is between 1 and 255
	 */
	private Integer channelControllerIRAdaptorID;
	
	/**
	 * Active infrared intrusion detector No. of lane controller, which is between 1 and 255
	 */
	private Integer channelControllerIREmitterID;
	
	/**
	 * person type: "normal"-normal person (household), "visitor"-visitor, "blacklist"-person in blacklist, "administrators"-administrator
	 */
	private String userType;
	
	/**
	 * Authentication mode: face card or password or fingerprint 
	 */
	private String currentVerifyMode;
	
	/**
	 * Whether contains picture
	 */
	private Boolean picEnable;
	
	/**
	 * attendance status: "undefined", "checkIn"-check in, "checkOut"-check out, "breakOut"-break out, "breakIn"-break in, 
	 * "overtimeIn"-overtime in, "overTimeOut"-overtime out
	 */
	private String attendanceStatus;
	
	/**
	 * Status value
	 */
	private Integer statusValue;

	/**
	 * File name. If multiple pictures are returned at a time, filename of each picture should be unique
	 */
	private String filename;

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNetUser() {
		return netUser;
	}

	public void setNetUser(String netUser) {
		this.netUser = netUser;
	}

	public String getRemoteHostAddr() {
		return remoteHostAddr;
	}

	public void setRemoteHostAddr(String remoteHostAddr) {
		this.remoteHostAddr = remoteHostAddr;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getWhiteListNo() {
		return whiteListNo;
	}

	public void setWhiteListNo(Integer whiteListNo) {
		this.whiteListNo = whiteListNo;
	}

	public Integer getReportChannel() {
		return reportChannel;
	}

	public void setReportChannel(Integer reportChannel) {
		this.reportChannel = reportChannel;
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

	public Integer getVerifyNo() {
		return verifyNo;
	}

	public void setVerifyNo(Integer verifyNo) {
		this.verifyNo = verifyNo;
	}

	public Integer getAlarmInNo() {
		return alarmInNo;
	}

	public void setAlarmInNo(Integer alarmInNo) {
		this.alarmInNo = alarmInNo;
	}

	public Integer getAlarmOutNo() {
		return alarmOutNo;
	}

	public void setAlarmOutNo(Integer alarmOutNo) {
		this.alarmOutNo = alarmOutNo;
	}

	public Integer getCaseSensorNo() {
		return caseSensorNo;
	}

	public void setCaseSensorNo(Integer caseSensorNo) {
		this.caseSensorNo = caseSensorNo;
	}

	public Integer getRS485No() {
		return RS485No;
	}

	public void setRS485No(Integer rS485No) {
		RS485No = rS485No;
	}

	public Integer getMultiCardGroupNo() {
		return multiCardGroupNo;
	}

	public void setMultiCardGroupNo(Integer multiCardGroupNo) {
		this.multiCardGroupNo = multiCardGroupNo;
	}

	public Integer getAccessChannel() {
		return accessChannel;
	}

	public void setAccessChannel(Integer accessChannel) {
		this.accessChannel = accessChannel;
	}

	public Integer getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(Integer deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getDistractControlNo() {
		return distractControlNo;
	}

	public void setDistractControlNo(Integer distractControlNo) {
		this.distractControlNo = distractControlNo;
	}

	public Integer getEmployeeNoString() {
		return employeeNoString;
	}

	public void setEmployeeNoString(Integer employeeNoString) {
		this.employeeNoString = employeeNoString;
	}

	public Integer getLocalControllerID() {
		return localControllerID;
	}

	public void setLocalControllerID(Integer localControllerID) {
		this.localControllerID = localControllerID;
	}

	public Integer getInternetAccess() {
		return InternetAccess;
	}

	public void setInternetAccess(Integer internetAccess) {
		InternetAccess = internetAccess;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMACAddr() {
		return MACAddr;
	}

	public void setMACAddr(String mACAddr) {
		MACAddr = mACAddr;
	}

	public Integer getSwipeCardType() {
		return swipeCardType;
	}

	public void setSwipeCardType(Integer swipeCardType) {
		this.swipeCardType = swipeCardType;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getChannelControllerID() {
		return channelControllerID;
	}

	public void setChannelControllerID(Integer channelControllerID) {
		this.channelControllerID = channelControllerID;
	}

	public Integer getChannelControllerLampID() {
		return channelControllerLampID;
	}

	public void setChannelControllerLampID(Integer channelControllerLampID) {
		this.channelControllerLampID = channelControllerLampID;
	}

	public Integer getChannelControllerIRAdaptorID() {
		return channelControllerIRAdaptorID;
	}

	public void setChannelControllerIRAdaptorID(Integer channelControllerIRAdaptorID) {
		this.channelControllerIRAdaptorID = channelControllerIRAdaptorID;
	}

	public Integer getChannelControllerIREmitterID() {
		return channelControllerIREmitterID;
	}

	public void setChannelControllerIREmitterID(Integer channelControllerIREmitterID) {
		this.channelControllerIREmitterID = channelControllerIREmitterID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCurrentVerifyMode() {
		return currentVerifyMode;
	}

	public void setCurrentVerifyMode(String currentVerifyMode) {
		this.currentVerifyMode = currentVerifyMode;
	}

	public Boolean getPicEnable() {
		return picEnable;
	}

	public void setPicEnable(Boolean picEnable) {
		this.picEnable = picEnable;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public Integer getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
