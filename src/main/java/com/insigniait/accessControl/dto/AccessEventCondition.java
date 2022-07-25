package com.insigniait.accessControl.dto;

/**
 * @author Doni, 23/07/2022 17:17:17
 *
 */
public class AccessEventCondition {

	/**
	 * Which is used to confirm the upper-level platform or system. If the platform or the system is the same one during two searching, 
	 * the search history will be saved in the memory to speed up next searching
	 */
	private String searchID;
	
	/**
	 * The start position of the search result in the result list. When there are multiple records and you 
	 * cannot get all search results at a time, you can search for the records after the specified position next time
	 */
	private Integer searchResultPosition;
	
	/**
	 * Maximum number of search results. If maxResults exceeds the range returned by the device capability, 
	 * the device will return the maximum number of search results according to the device capability and will not return error message
	 */
	private Integer maxResults;
	
	/**
	 * Major alarm/event types (the type value should be transformed to the decimal number), see Access Control Alarm Types for details
	 */
	private Integer major;
	
	/**
	 *  Minor alarm/event types (the type value should be transformed to the decimal number), see Access Control Alarm Types for details
	 */
	private Integer minor;
	
	/**
	 * Start time (UTC time), e.g., 2016-12-12T17:30:08+08:00
	 */
	private String startTime;
	
	/**
	 * End time (UTC time), e.g.,2017-12-12T17:30:08+08:00
	 */
	private String endTime;
	
	/**
	 * Card No.
	 */
	private String cardNo;
	
	/**
	 * Cardholder name
	 */
	private String name;
	
	/**
	 * Whether to contain pictures
	 */
	private Boolean picEnable;
	
	/**
	 * Start serial No.
	 */
	private Integer beginSerialNo;
	
	/**
	 * End serial No.
	 */
	private Integer endSerialNo;
	
	/**
	 * Employee No. (person ID)
	 */
	private String employeeNoString;
	
	/**
	 * Event attribute: "attendance"-valid authentication, "other"
	 */
	private String eventAttribute;
	
	/**
	 * Employee No. (person ID)
	 */
	private String employeeNo;
	
	/**
	 * whether to return events in descending order of time (later events will be returned first): true-yes, 
	 * false or this node is not returned-no
	 */
	private Boolean timeReverseOrder;

	public String getSearchID() {
		return searchID;
	}

	public void setSearchID(String searchID) {
		this.searchID = searchID;
	}

	public Integer getSearchResultPosition() {
		return searchResultPosition;
	}

	public void setSearchResultPosition(Integer searchResultPosition) {
		this.searchResultPosition = searchResultPosition;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getPicEnable() {
		return picEnable;
	}

	public void setPicEnable(Boolean picEnable) {
		this.picEnable = picEnable;
	}

	public Integer getBeginSerialNo() {
		return beginSerialNo;
	}

	public void setBeginSerialNo(Integer beginSerialNo) {
		this.beginSerialNo = beginSerialNo;
	}

	public Integer getEndSerialNo() {
		return endSerialNo;
	}

	public void setEndSerialNo(Integer endSerialNo) {
		this.endSerialNo = endSerialNo;
	}

	public String getEmployeeNoString() {
		return employeeNoString;
	}

	public void setEmployeeNoString(String employeeNoString) {
		this.employeeNoString = employeeNoString;
	}

	public String getEventAttribute() {
		return eventAttribute;
	}

	public void setEventAttribute(String eventAttribute) {
		this.eventAttribute = eventAttribute;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public Boolean getTimeReverseOrder() {
		return timeReverseOrder;
	}

	public void setTimeReverseOrder(Boolean timeReverseOrder) {
		this.timeReverseOrder = timeReverseOrder;
	}
}
