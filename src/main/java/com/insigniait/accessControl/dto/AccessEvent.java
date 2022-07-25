package com.insigniait.accessControl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author Doni, 23/07/2022 17:47:52
 *
 */
public class AccessEvent {

	/**
	 * Is used to confirm the upper-level platform or system. If the platform or the system is the same one during two searching, 
	 * the search history will be saved in the memory to speed up next searching
	 */
	private String searchID;
	
	/**
	 * "OK"-searching completed, "MORE"-searching for more results, "NO MATCH"-no
	 */
	private String responseStatusStrg;
	
	/**
	 * Number of returned results
	 */
	private Integer numOfMatches;
	
	/**
	 * Total number of matched results
	 */
	private Integer totalMatches;
	
	/**
	 * number of captured pictures if the capture linkage action is configured. This node will be 0 or not
	be returned if there is no picture
	 */
	private Integer picturesNumber;
	
	/**
	 * Event details
	 */
	@JsonAlias("InfoList")
	private List<EventDetail> eventDetails;

	public String getSearchID() {
		return searchID;
	}

	public void setSearchID(String searchID) {
		this.searchID = searchID;
	}

	public String getResponseStatusStrg() {
		return responseStatusStrg;
	}

	public void setResponseStatusStrg(String responseStatusStrg) {
		this.responseStatusStrg = responseStatusStrg;
	}

	public Integer getNumOfMatches() {
		return numOfMatches;
	}

	public void setNumOfMatches(Integer numOfMatches) {
		this.numOfMatches = numOfMatches;
	}

	public Integer getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(Integer totalMatches) {
		this.totalMatches = totalMatches;
	}

	public Integer getPicturesNumber() {
		return picturesNumber;
	}

	public void setPicturesNumber(Integer picturesNumber) {
		this.picturesNumber = picturesNumber;
	}

	public List<EventDetail> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(List<EventDetail> eventDetails) {
		this.eventDetails = eventDetails;
	}
}
