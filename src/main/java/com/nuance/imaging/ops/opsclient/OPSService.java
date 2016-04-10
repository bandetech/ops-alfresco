package com.nuance.imaging.ops.opsclient;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.nuance.imaging.ops.opsclient.data.ConversionParameters;
import com.nuance.imaging.ops.opsclient.data.JobDataDescription;
import com.nuance.imaging.ops.opsclient.data.JobInfo;
import com.nuance.imaging.ops.opsclient.data.JobNotificationConfiguration;
import com.nuance.imaging.ops.opsclient.data.JobType;
import com.nuance.imaging.ops.opsclient.data.PreallocatedJobResponse;

public interface OPSService {
	public static final String CREATE_JOB_URI = "/CreateJob";
	public static final String GET_UPLOAD_URLS_URI = "/GetUploadUrls";
	public static final String START_JOB_URI = "/StartJob";
	public static final String GET_JOBINFO_URI = "/GetJobInfo";
	public static final String GET_DOWNLOAD_URI = "/GetDownloadUrls";
	public static final String PROCESS_ONCALL_URI = "/ProcessOnCall";
	public static final String CONF_JOB_NOTIFICATION_URI = "/ConfigureJobNotification";
	public static final String GET_JOBDATA_DESCRIPTION_URI = "/GetJobDataDescription";
	public static final String GET_JOB_NOTIFICATION_INFO_URI = "/GetNotificationInfo";
	public static final String CANCEL_JOB_URI = "/CancelJob";
	public static final String DELETE_JOB_DATA_URI = "/DeleteJobData";
	public static final String GET_JOB_TYPES_URI = "/GetJobTypes";
	public static final String CREATE_PREALLOCATED_JOB_URI = "/CreatePreallocatedJob";

	/**
	 * Get list of all Job Types in OCS.
	 * @return List of JobType
	 */
	public List<JobType> getJobTypes();

	/**
	 * Create a new job.
	 * @param jobTypeId Job Type Id. You can get jobTypeId from getJobTypes() list corresponding to what you want.
	 * @param title Title of Job
	 * @param description Description of Job
	 * @param metadata Metadata for this job
	 * @return jobId
	 */
	public String createJob(int jobTypeId, String title, String description, String metadata);

	/**
	 * Create a new job. This method returns upload urls with jobId
	 * @param jobTypeId Job Type Id. You can get jobTypeId from getJobTypes() list corresponding to what you want.
	 * @param title Title of Job
	 * @param description Description of Job
	 * @param metadata Metadata of Job
	 * @param count Number of files for input of this job
	 * @return PreallocatedJobResponse contains jobId and upload urls for input files.
	 */
	public PreallocatedJobResponse createPreallocatedJob(int jobTypeId, String title, String description, String metadata, int count) ;

	/**
	 * Get Upload urls for input files.
	 * @param jobId Job ID for this job.
	 * @param count Number of input files.
	 * @return List of String urls.
	 */
	public List<String> getUploadUrls(String jobId, int count);

	/**
	 * Start OCS Job
	 * @param jobId Job ID to start
	 * @param timeToLiveSec Time to live for this job in seccond.
	 * @param priority Priority for this Job. See {@link com.nuance.imaging.ops.opsclient.enums.JobPriority}
	 * @param conversionParams Conversion parameters for this job. See {@link com.nuance.imaging.ops.opsclient.data.ConversionParameters}
	 * @return
	 */
	public JobInfo startJob(String jobId, long timeToLiveSec, int priority, ConversionParameters conversionParams);

	/**
	 * Get Job status of Job
	 * @param jobId jobId to get status
	 * @return JobInfo for Job Status. See {@link com.nuance.imaging.ops.data.JobInfo}
	 */
	public JobInfo getJobInfo(String jobId);
	
	/**
	 * Get Download Urls for result
	 * @param jobId jobId
	 * @return List of Url string
	 */
	public List<String> getDownloadUrls(String jobId);
	
	/**
	 * Post Input files
	 * @param data Map data of upload Url and local file
	 * 
	 */
	public void postInputFiles(Map<String, File> data);
	
	/**
	 * Download File to write to local file
	 * @param url url to download
	 * @param file local file
	 */
	public void downloadFile(String url, File file);
	
	/**
	 * Get job data description
	 * @param jobId jobId
	 * @return JobDataDescription
	 */
	public JobDataDescription getJobDataDescription(String jobId);
	/**
	 * Process a job immediately
	 * @param jobId jobId
	 * @param conversionParams parameters for conversion
	 * @return
	 */
	//public List<String> processOnCall(String jobId, ConversionParameters conversionParams);
	
	/**
	 * Configure Job notification
	 * @param jobId jobId
	 * @param conf JobNotificationConfiguration
	 */
	public void configureJobNotification(final String jobId, final JobNotificationConfiguration conf);
	
	/**
	 * Cancel Job
	 * @param jobId jobId
	 * @return JobInfo for result
	 */
	public JobInfo cancelJob(String jobId);
	
	/**
	 * Delete Job Data
	 * @param jobId jobId
	 * @param dataTypeFlag (1: Input File, 2: Output File, 3: Both)
	 */
	public void deleteJobData(String jobId, int dataTypeFlag);

}
