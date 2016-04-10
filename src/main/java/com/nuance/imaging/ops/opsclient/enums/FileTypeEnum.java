package com.nuance.imaging.ops.opsclient.enums;

public enum FileTypeEnum {
	
	INPUT_DOCUMENT_TO_RECOGNIZE(0),
	ZONE_FILE_FOR_CUSTOM_ZONING(6),
	FORM_SPLITTING_TEMPLATE_FILE(7),
	GENERAL_OUTPUT_DOCUMENT(2),
	PREPROCESSED_COLOR_IMAGE(3),
	PREPROCESSED_BW_IMAGE(4),
	PREPROCESSED_THUMBNAIL_IMAGE(5),
	FILLED_FORM_TEMPLATE(8),
	FORM_TEMPLATE_LIB(9);
	
	private final int id;
	
	private FileTypeEnum(final int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	
}
