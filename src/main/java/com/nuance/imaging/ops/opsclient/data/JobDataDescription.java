package com.nuance.imaging.ops.opsclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobDataDescription {
	@JsonProperty("FileDescriptions")
	private FileDescription[] fileDescription;
	@JsonProperty("Warnings")
	private Warning[] warnings;
	
	
	public FileDescription[] getFileDesciptions() {
		return fileDescription;
	}
	public void setFileDesciptions(FileDescription[] fileDescription) {
		this.fileDescription = fileDescription;
	}
	public Warning[] getWarnings() {
		return warnings;
	}
	public void setWarnings(Warning[] warnings) {
		this.warnings = warnings;
	}
	
	
 }
