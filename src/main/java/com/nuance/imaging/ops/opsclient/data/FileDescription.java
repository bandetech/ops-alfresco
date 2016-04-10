package com.nuance.imaging.ops.opsclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDescription {
	@JsonProperty("DocumentId")
	private int documentId;
	
	@JsonProperty("FileIndex")
	private int fileIndex;
	
	@JsonProperty("FileType")
	private int fileType;
	
	@JsonProperty("PageId")
	private Integer pageId;

	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getFileIndex() {
		return fileIndex;
	}
	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	
	
}
