package com.nuance.imaging.ops.opsclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobType {
	@JsonProperty("JobTypeId")
	private int jobTypeId;
	@JsonProperty("SourceFormat")
	private String sourceFormat;
	@JsonProperty("TargetFormat")
	private String targetFormat;
	@JsonProperty("Description")
	private String description;
	
	public int getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(int jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getSourceFormat() {
		return sourceFormat;
	}
	public void setSourceFormat(String sourceFormat) {
		this.sourceFormat = sourceFormat;
	}
	public String getTargetFormat() {
		return targetFormat;
	}
	public void setTargetFormat(String targetFormat) {
		this.targetFormat = targetFormat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
