package com.nuance.imaging.ops.opsclient.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreallocatedJobResponse {
	
	@JsonProperty("JobId")
	public String jobId;
	@JsonProperty("UploadUrls")
	public List<String> uploadUrls;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public List<String> getUploadUrls() {
		return uploadUrls;
	}
	public void setUploadUrls(List<String> uploadUrls) {
		this.uploadUrls = uploadUrls;
	}
		
}
