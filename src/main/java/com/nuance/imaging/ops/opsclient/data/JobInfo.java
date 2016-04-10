package com.nuance.imaging.ops.opsclient.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nuance.imaging.ops.opsclient.enums.JobStateEnum;

public class JobInfo {
	
	@JsonProperty("JobTypeId")
	private String 		jobTypeId;
	
	@JsonProperty("JobId")
	private String 		jobId;
	
	@JsonProperty("Completeness")
	private int 		completeness;
	
	@JsonProperty("ProgressMessage")
	private String		progressMessage;
	
	@JsonProperty("State")
	private JobStateEnum 		state;
	
	@JsonProperty("EstimatedWorkTimeSec")
	private long 		estimatedWorkTimeSec;
	
	@JsonProperty("PollIntervalSec")
	private long 		pollIntervalSec;
	
	@JsonProperty("Started")
	private Date 	started;
	
	@JsonProperty("Ended")
	private Date 	ended;
	
	@JsonProperty("ResultCode")
	private int 		resultCode;
	
	@JsonProperty("ResultMessage")
	private String 		resultMessage;
	
	@JsonProperty("Metadata")
	private String 		metadata;
	
	@JsonProperty("JobPriority")
	private long 		jobPriority;
	
	@JsonProperty("ProcessedPageCount")
	private long 		processedPageCount;
	
	@JsonProperty("OutputDataDescription")
	private String 		outputDataDescription;
	
	public String getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(String jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public int getCompleteness() {
		return completeness;
	}
	public void setCompleteness(int completeness) {
		this.completeness = completeness;
	}
	public String getProgressMessage() {
		return progressMessage;
	}
	public void setProgressMessage(String progressMessage) {
		this.progressMessage = progressMessage;
	}
	public JobStateEnum getState() {
		return state;
	}
	public void setState(JobStateEnum state) {
		this.state = state;
	}
	public long getEstimatedWorkTimeSec() {
		return estimatedWorkTimeSec;
	}
	public void setEstimatedWorkTimeSec(long estimatedWorkTimeSec) {
		this.estimatedWorkTimeSec = estimatedWorkTimeSec;
	}
	public long getPollIntervalSec() {
		return pollIntervalSec;
	}
	public void setPollIntervalSec(long pollIntervalSec) {
		this.pollIntervalSec = pollIntervalSec;
	}
	public Date getStarted() {
		return started;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public Date getEnded() {
		return ended;
	}
	public void setEnded(Date ended) {
		this.ended = ended;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public long getJobPriority() {
		return jobPriority;
	}
	public void setJobPriority(long jobPriority) {
		this.jobPriority = jobPriority;
	}
	public long getProcessedPageCount() {
		return processedPageCount;
	}
	public void setProcessedPageCount(long processedPageCount) {
		this.processedPageCount = processedPageCount;
	}
	public String getOutputDataDescription() {
		return outputDataDescription;
	}
	public void setOutputDataDescription(String outputDataDescription) {
		this.outputDataDescription = outputDataDescription;
	}
	
	
}
