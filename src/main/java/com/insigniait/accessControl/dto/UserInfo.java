package com.insigniait.accessControl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserInfo {

	private String employeeNo;
	private String name;
	private String userType;
	private String gender;
	private Boolean localUIRight;
	private Integer maxOpenDoorTime;
	private String doorRight;
	private Integer roomNumber;
	private Integer floorNumber;
	private String userVerifyMode;
	@JsonAlias("RightPlan")
	private List<RightPlan> rightPlan;
	@JsonAlias("Valid")
	private Valid valid;
	
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getLocalUIRight() {
		return localUIRight;
	}

	public void setLocalUIRight(Boolean localUIRight) {
		this.localUIRight = localUIRight;
	}

	public Integer getMaxOpenDoorTime() {
		return maxOpenDoorTime;
	}

	public void setMaxOpenDoorTime(Integer maxOpenDoorTime) {
		this.maxOpenDoorTime = maxOpenDoorTime;
	}

	public String getDoorRight() {
		return doorRight;
	}

	public void setDoorRight(String doorRight) {
		this.doorRight = doorRight;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getUserVerifyMode() {
		return userVerifyMode;
	}

	public void setUserVerifyMode(String userVerifyMode) {
		this.userVerifyMode = userVerifyMode;
	}

	public List<RightPlan> getRightPlan() {
		return rightPlan;
	}

	public void setRightPlan(List<RightPlan> rightPlan) {
		this.rightPlan = rightPlan;
	}

	public Valid getValid() {
		return valid;
	}

	public void setValid(Valid valid) {
		this.valid = valid;
	}
	
	public class Valid {
    	private Boolean enable;
    	private String beginTime;
    	private String endTime;
    	private String timeType;
    	
		public Boolean getEnable() {
			return enable;
		}
		
		public void setEnable(Boolean enable) {
			this.enable = enable;
		}
		
		public String getBeginTime() {
			return beginTime;
		}
		
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		
		public String getEndTime() {
			return endTime;
		}
		
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		
		public String getTimeType() {
			return timeType;
		}
		
		public void setTimeType(String timeType) {
			this.timeType = timeType;
		}
    }
	
	public class RightPlan {
		private Integer doorNo;
		private String planTemplateNo;
		
		public Integer getDoorNo() {
			return doorNo;
		}
		
		public void setDoorNo(Integer doorNo) {
			this.doorNo = doorNo;
		}
		
		public String getPlanTemplateNo() {
			return planTemplateNo;
		}
		
		public void setPlanTemplateNo(String planTemplateNo) {
			this.planTemplateNo = planTemplateNo;
		}
	}
}
