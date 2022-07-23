package com.insigniait.accessControl.dto;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.insigniait.accessControl.dto.UserInfo.RightPlan;
import com.insigniait.accessControl.dto.UserInfo.Valid;

public class FaceRecognitionTerminal extends ISAPIDevice {

	public FaceRecognitionTerminal(String hostName, String user, String pass) {
		super(hostName, user, pass);
	}

	/**
	 * Registra un usuario nuevo del tipo visitante
	 * @return
	 */
	public String registerUnknowVistor() {
		
		String identifier = UUID
			.randomUUID()
			.toString()
			.replace("-", "");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmployeeNo(identifier);
		userInfo.setName("Visitante Sin Nombre");
		userInfo.setUserType("visitor");
		userInfo.setGender("unknown");
		userInfo.setLocalUIRight(false);
		userInfo.setMaxOpenDoorTime(255);
		userInfo.setDoorRight("1");
		userInfo.setRoomNumber(0);
		userInfo.setFloorNumber(0);
		userInfo.setUserVerifyMode("");
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.YEAR, 10);
		Date endDate = calendar.getTime();
		
		Valid valid = userInfo.new Valid();
		valid.setEnable(true);
		valid.setBeginTime(ISAPIDevice.formatDate(currentDate));
		valid.setEndTime(ISAPIDevice.formatDate(endDate));
		valid.setTimeType("local");
		userInfo.setValid(valid);
		
		RightPlan rightPlan = userInfo.new RightPlan();
		rightPlan.setDoorNo(1);
		rightPlan.setPlanTemplateNo("1");
		userInfo.setRightPlan(Arrays.asList(rightPlan));
		
		return addUser(userInfo) ? userInfo.getEmployeeNo() : null;
	}
}
