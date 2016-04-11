package com.nuance.imaging.ops.opsclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobNotification {
	
	@JsonProperty("ServiceUrl")
	private String serviceUrl;
	@JsonProperty("AuthenticationHeader")
	private String authenticationHeader;
	@JsonProperty("CertificateSubject")
	private String certificateSubject;
	@JsonProperty("Parameters")
	private String parameters;
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getAuthenticationHeader() {
		return authenticationHeader;
	}
	public void setAuthenticationHeader(String authenticationHeader) {
		this.authenticationHeader = authenticationHeader;
	}
	public String getCertificateSubject() {
		return certificateSubject;
	}
	public void setCertificateSubject(String certificateSubject) {
		this.certificateSubject = certificateSubject;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	
}
