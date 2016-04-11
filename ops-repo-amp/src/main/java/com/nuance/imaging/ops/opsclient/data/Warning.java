package com.nuance.imaging.ops.opsclient.data;

public class Warning {
    private int code;
    private int inputDocumentId;
    private Integer inputPageId;
    private String message;
    private int outputDocumentId;
    private Integer outputPageId;
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getInputDocumentId() {
		return inputDocumentId;
	}
	public void setInputDocumentId(int inputDocumentId) {
		this.inputDocumentId = inputDocumentId;
	}
	public Integer getInputPageId() {
		return inputPageId;
	}
	public void setInputPageId(Integer inputPageId) {
		this.inputPageId = inputPageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getOutputDocumentId() {
		return outputDocumentId;
	}
	public void setOutputDocumentId(int outputDocumentId) {
		this.outputDocumentId = outputDocumentId;
	}
	public Integer getOutputPageId() {
		return outputPageId;
	}
	public void setOutputPageId(Integer outputPageId) {
		this.outputPageId = outputPageId;
	}
    
    
}
