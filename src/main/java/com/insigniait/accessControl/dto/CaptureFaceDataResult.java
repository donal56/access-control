package com.insigniait.accessControl.dto;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Doni, 24/07/2022 19:15:21
 *
 */
public class CaptureFaceDataResult {
	
	public final CaptureFaceData captureFaceData;
	public final File faceData;
	
	@JsonCreator
    public CaptureFaceDataResult(@JsonProperty("CaptureFaceData") CaptureFaceData captureFaceData, @JsonProperty("FaceData") File faceData) {
        this.captureFaceData = captureFaceData;
        this.faceData = faceData;
    }
	
	public class CaptureFaceData {
		
		/**
		 *  Face data URL, if this node does not exist, it indicates that there is no face data
		 */
		private String faceDataUrl;
		
		/**
		 * Collection progress, which is between 0 and 100, 0-no face data collected, 100-collected, 
		 * the face data URL can be parsed only when the progress is 100
		 */
		private Integer captureProgress;
		
		/**
		 * Whether the current collection request is completed: "true"-yes, "false"-no
		 */
		private Boolean isCurRequestOver;
		
		/**
		 * Infrared face data URL, if this node does not exist, it indicates that there is no infrared face data
		 */
		private String infraredFaceDataUrl;

		public String getFaceDataUrl() {
			return faceDataUrl;
		}

		public void setFaceDataUrl(String faceDataUrl) {
			this.faceDataUrl = faceDataUrl;
		}

		public Integer getCaptureProgress() {
			return captureProgress;
		}

		public void setCaptureProgress(Integer captureProgress) {
			this.captureProgress = captureProgress;
		}

		public Boolean getIsCurRequestOver() {
			return isCurRequestOver;
		}

		public void setIsCurRequestOver(Boolean isCurRequestOver) {
			this.isCurRequestOver = isCurRequestOver;
		}

		public String getInfraredFaceDataUrl() {
			return infraredFaceDataUrl;
		}

		public void setInfraredFaceDataUrl(String infraredFaceDataUrl) {
			this.infraredFaceDataUrl = infraredFaceDataUrl;
		}
	}
}
