package com.nuance.imaging.ops.opsclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobNotificationConfiguration {
	
	@JsonProperty("CompletionNotifications")
	private JobNotification[] completionNotifications;
	@JsonProperty("FailureNotofications")
	private JobNotification[] failureNotifications;
	
	
	public JobNotification[] getCompletionNotifications() {
		return completionNotifications;
	}
	public void setCompletionNotifications(JobNotification[] completionNotifications) {
		this.completionNotifications = completionNotifications;
	}
	public JobNotification[] getFailureNotifications() {
		return failureNotifications;
	}
	public void setFailureNotifications(JobNotification[] failureNotifications) {
		this.failureNotifications = failureNotifications;
	}

}
