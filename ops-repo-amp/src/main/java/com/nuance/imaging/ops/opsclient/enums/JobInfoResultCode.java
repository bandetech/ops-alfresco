package com.nuance.imaging.ops.opsclient.enums;

public interface JobInfoResultCode {
	
	public static final int SUCCESS = 0x0;
	public static final int JOBID_NOT_FOUND_IN_STORAGE = 0xF00001;
	public static final int TYPE_OF_JOB_NOLONGER_SUPPORTED = 0xF00004;
	public static final int INVALID_LANGAUGE_PARAMETER = 0xF00005;
	public static final int INVALID_LANGAUGE_PARAMETER_COMB = 0xF00006;
	public static final int NO_INPUT_FILE = 0xF00014;
	public static final int DID_NOT_PRODUCE_OUTPUT_FILE = 0xF00015;
	public static final int NO_TEMPLATE_FILE_UPLOADED = 0xF00016;
	public static final int ZONE_FILE_IS_INVALID = 0xF00017;
	public static final int INVALID_BARCODE_TYPE_COMBINATION = 0xF00018;
	public static final int NO_MATCHING_TEMPLATE_FOUND = 0xF00019;
	public static final int INPUT_PDF_PASSWORD_PROTECTED = 0xF00100;
	public static final int MAX_INPUT_PAGE_SIZE_EXCEEDED = 0xF00101;
	public static final int INPUT_FILE_MAYBE_CORRUPTED = 0xF00102;
	public static final int GENERIC_CONVERSION_FAILURE = 0xF00208;
	public static final int JOB_EXPIRED_BEFORE_PROCESSING = 0xF00210;

	
}
