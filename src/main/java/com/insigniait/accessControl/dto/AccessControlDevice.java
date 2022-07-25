package com.insigniait.accessControl.dto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.insigniait.accessControl.dto.UserInfo.RightPlan;
import com.insigniait.accessControl.dto.UserInfo.Valid;

public class AccessControlDevice extends ISAPIDevice {

	public AccessControlDevice(String hostName, String user, String pass) {
		super(hostName, user, pass);
	}
	
	/**
	 * Agrega un nuevo usuario a la terminal
	 * @param userInfo
	 * @return
	 */
	public Boolean addUser(UserInfo userInfo) {
		String service = getIsapiUrl() + "/AccessControl/UserInfo/Record?format=json";
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("UserInfo", userInfo);
		
		ResponseEntity<GenericResponse> response = isapi.postForEntity(service, body, GenericResponse.class);
		return response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccessful();
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
	
	/**
	 * Liga la foto de un rostro al registro de un usuario en la terminal
	 * @param employeeId - ID de empleado
	 * @param facePhoto - Foto del rostro
	 */
	public void registerUserFace(String employeeId, File facePhoto) {
		String service = getIsapiUrl() + "/Intelligent/FDLib/FDSetUp?format=json";
		
		Map<String, Object> faceDataRecord = new HashMap<String, Object>();
		faceDataRecord.put("faceLibType", "blackFD");
		faceDataRecord.put("FDID", employeeId);
		faceDataRecord.put("FPID", employeeId);
		
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("FaceDataRecord", faceDataRecord);
		request.put("img", facePhoto);
		
		isapi.put(service, request);
	}
	
	public File captureFaceData() {
		
		String service = getIsapiUrl() + "/AccessControl/CaptureFaceData";
		
		String payload = 
			"<CaptureFaceDataCond version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">" + 
			"<captureInfrared>false</captureInfrared>" + 
			"<dataType>binary</dataType>" + 
			"</CaptureFaceDataCond>";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_XML);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.setConnection("");
		HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
		
		ResponseExtractor<File> responseExtractor = response -> {
			 if(response.getStatusCode() == HttpStatus.OK) {
				 
				 String fileName = Long.toString(System.currentTimeMillis());
				 String imagePath = getSnapshotsPath() + "\\" + fileName + ".jpg";
				 String tempPath = System.getProperty("java.io.tmpdir") + "\\" + fileName + ".txt";
				 
				 Files.copy(response.getBody(), Paths.get(tempPath));
				 
				 // Despues de guardarlo, por que si tratas el inputStream como una cadena jodes el encodeo del binario de la foto
				 // https://superuser.com/q/782692
				 File file = new File(imagePath);
				 file.createNewFile();
				 FileWriter fileWriter = new FileWriter(file);
				 Files.newBufferedReader(Paths.get(tempPath), Charset.forName("Cp1252"))
				 	.lines()
				 	.skip(6)
				 	.forEach(line -> {
						try {
							fileWriter.write(line);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
				 });
				 fileWriter.close();
				 
				 return file;
			 }
			 return null;
		};
		RequestCallback requestCallback = isapi.httpEntityCallback(entity, String.class);
		return isapi.execute(service, HttpMethod.POST, requestCallback, responseExtractor);
	}
	
	public AddFaceRecordResult agregarRostro(String facePictureDataLibraryID, String employeeID, String fullName, File photo) {
		
		String service = getIsapiUrl() + "/Intelligent/FDLib/FaceDataRecord?format=json";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		Map<String, Object> faceRecord = new HashMap<String, Object>();
		faceRecord.put("faceLibType", "blackFD");
		faceRecord.put("FDID", facePictureDataLibraryID);
		faceRecord.put("FPID", "employeeID");
		faceRecord.put("name", fullName);
		faceRecord.put("gender", "unknown");
		faceRecord.put("bornTime", null);
		faceRecord.put("city", null);
		faceRecord.put("certificateType", null);
		faceRecord.put("certificateNumber", null);
		
		String contentType = "image/jpg";
		DiskFileItem diskFileItem = new DiskFileItem("FaceImage", contentType, false, photo.getName(), (int) photo.length(), photo.getParentFile());
		MultipartFile multipartFile = new CommonsMultipartFile(diskFileItem);

		LinkedMultiValueMap<String, String> faceImage = new LinkedMultiValueMap<>();
	    faceImage.add("Content-disposition", "form-data; name=FaceImage; filename=" + multipartFile.getOriginalFilename());
	    faceImage.add("Content-type", "image/jpg");
	    
	    HttpEntity<byte[]> faceImageEntity = null;
		try {
			faceImageEntity = new HttpEntity<byte[]>(multipartFile.getBytes(), faceImage);
		} 
		catch (IOException e) { }
		
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("FaceDataRecord", faceRecord);
		map.add("FaceImage", faceImageEntity);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
		ResponseEntity<AddFaceRecordResult> response = isapi.exchange(service, HttpMethod.POST, requestEntity, AddFaceRecordResult.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		
		return null;
	}
}
