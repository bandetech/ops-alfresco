package com.nuance.imaging.ops.opsclient;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.nuance.imaging.ops.opsclient.data.ConversionParameters;
import com.nuance.imaging.ops.opsclient.data.JobDataDescription;
import com.nuance.imaging.ops.opsclient.data.JobInfo;
import com.nuance.imaging.ops.opsclient.data.JobNotificationConfiguration;
import com.nuance.imaging.ops.opsclient.data.JobType;
import com.nuance.imaging.ops.opsclient.data.PreallocatedJobResponse;
import com.nuance.imaging.ops.opsclient.util.TicksSinceFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Component
public class OPSServiceImpl implements OPSService {
	@Autowired
	private OmniPageServerInfo server;
	private HttpClient httpClient = null;

	Log logger = LogFactory.getLog(OPSServiceImpl.class);

	public OPSServiceImpl(){
		int socketTimeOut = 90000;
		int connectionTimeOut = 180000;
		final String USER_AGENT = "Nuance OPS Client 0.1";

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionTimeOut)
				.setSocketTimeout(socketTimeOut)
				.build();

		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept", "application/json"));
		//headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Accept-Charset", "utf-8"));
		//headers.add(new BasicHeader("Accept-Language", "ja, en;q=0.8"));
		headers.add(new BasicHeader("User-Agent", USER_AGENT));

		httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.setDefaultHeaders(headers).build();

		logger.debug("Initialize OPSServiceImpl....");


	}

	public List<JobType> getJobTypes(){
		String data = sendRequest(GET_JOB_TYPES_URI, null);
		logger.debug("GetJobTypes :"+data);
		return jsonArrayToList(data, JobType.class);
	}

	public String createJob(int jobTypeId, String title, String description, String metadata) {

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobTypeId", String.valueOf(jobTypeId)));
		urlParams.add(new BasicNameValuePair("title", title));
		urlParams.add(new BasicNameValuePair("description", description));
		urlParams.add(new BasicNameValuePair("metadata", metadata));


		String data = sendRequest(CREATE_JOB_URI, urlParams);

		return StringUtils.strip(data, "\"");

	}

	public List<String> getUploadUrls(String jobId, int count) {

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", jobId));
		urlParams.add(new BasicNameValuePair("count", String.valueOf(count)));

		String data = sendRequest(GET_UPLOAD_URLS_URI, urlParams);
		logger.debug("GetUploadUrl Result:"+data);

		return convertToUrls(data);
	}

	public JobInfo startJob(String jobId, long timeToLiveSec, int priority, ConversionParameters conversionParams) {

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", jobId));
		urlParams.add(new BasicNameValuePair("timeToLiveSec", String.valueOf(timeToLiveSec)));
		urlParams.add(new BasicNameValuePair("priority", String.valueOf(priority)));

        Serializer serializer = new Persister();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String params = null;
        try {
			serializer.write(conversionParams, stream);
			params = stream.toString();
		} catch (Exception e) {
			logger.error("Exception occured in startJob()...");
			e.printStackTrace();
		}
        logger.debug("conversionParameters: "+params);
        urlParams.add(new BasicNameValuePair("conversionParameters", params));
		String data = sendRequest(START_JOB_URI, urlParams);
		logger.debug("StartJob Return: "+data);

		return (JobInfo)jsonToObject(data, JobInfo.class);
	}

	public JobInfo getJobInfo(String jobId) {
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", jobId));

		String data = sendRequest(GET_JOBINFO_URI, urlParams);
		logger.debug("GetJobInfo Result:"+data);

		JobInfo resp = jsonToObject(data, JobInfo.class);

		return resp;
	}

	public List<String> getDownloadUrls(String jobId) {
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", jobId));

		String data = sendRequest(GET_DOWNLOAD_URI, urlParams);
		logger.debug("GetDownloadUrls Result:"+data);
		return convertToUrls(data);
	}

	public OmniPageServerInfo getServer() {
		return server;
	}

	public void setServer(OmniPageServerInfo server) {
		this.server = server;
	}

	private <T>T jsonToObject(final String jsonStr, final Class<T> clazz){

		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new TicksSinceFormat());
		try {
			return mapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			logger.error("jsonToObject JSON Parser Error.");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("jsonToObject JSON Mapping Error.");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("jsonToObject I/O Exception.");
			e.printStackTrace();
		}
		return null;
	}

	private <T extends Object> List<T> jsonArrayToList(final String jsonStr, final Class<T> clazz){
		final ObjectMapper mapper = new ObjectMapper();
		final CollectionType ct = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

		try {
			return (List<T>) mapper.readValue(jsonStr, ct);
		} catch (JsonParseException e) {
			logger.error("JsonParserException occured in jsonArrayToList()..");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException occured in jsonArrayToList()..");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException occured in jsonArrayToList()..");
			e.printStackTrace();
		}

		return null;
	}

	private String sendRequest(final String jobUri, final List<NameValuePair> urlParams){

		return sendRequest(jobUri, urlParams, HttpGet.METHOD_NAME);

	}

	private String sendDeleteRequest(final String jobUri, final List<NameValuePair> urlParams){
		
		return sendRequest(jobUri, urlParams, HttpDelete.METHOD_NAME);

	}
	
	private String sendRequest(final String jobUri, final List<NameValuePair> urlParams, final String method){
		
		String url;
		if(urlParams != null){
			String queryString = URLEncodedUtils.format(urlParams, Charsets.UTF_8);

			url = new StringBuilder(server.getJobUrlBase())
				.append(jobUri)
				.append("?")
				.append(queryString)
				.toString();
		} else {
			url = new StringBuilder(server.getJobUrlBase())
					.append(jobUri)
					.toString();
		}

		logger.debug("Accessing url: "+url);
		HttpRequestBase httpRequest = null;

		if(method.equals(HttpGet.METHOD_NAME)){
			httpRequest = new HttpGet(url);
		} else if (method.equals(HttpDelete.METHOD_NAME)){
			httpRequest = new HttpDelete(url);
		}
				
		return executeRequest(httpRequest);
		
	}
	
	private String executeRequest(HttpRequestBase httpRequest){
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			switch(statusCode){
				case HttpStatus.SC_OK:
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(outputStream);
					String data = outputStream.toString();

					return data;
				case HttpStatus.SC_NO_CONTENT:
					return "No Content from Server (HTTP204)";

				default:
					logger.error("Got Error from HTTP Server. Status Code :" + statusCode);
			}
				
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException occured in sendRequest()..");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException occured in sendRequest()..");
			e.printStackTrace();
		}

		return null;
	}
	
	
	private String sendPostRequestWithJson(final String url, String jsonStringParams){


		HttpPost httpPost = new HttpPost(server.getJobUrlBase() + url);
		httpPost.addHeader("Content-Type", "application/json");
		StringBuilder builder = new StringBuilder();
		builder.append(jsonStringParams);
		builder.append("\r\n");
		httpPost.setEntity(new StringEntity(builder.toString(), StandardCharsets.UTF_8));

		return executeRequest(httpPost);
	}

	private List<String>convertToUrls(String data){
		String tmpStr = StringUtils.strip(data, "[]");
		String[] rawUrls  = tmpStr.split(",");

		if(rawUrls.length == 0){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for(String url: rawUrls){
			url = StringUtils.strip(url, "\"");
			list.add(url);

		}
		return list;
	}

	public void postInputFiles(Map<String, File> data) {

		for(Map.Entry<String, File> e: data.entrySet()){

			HttpPost post = new HttpPost(e.getKey());

			FileBody bin = new FileBody(e.getValue());
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("bin", bin)
					.build();
			post.setEntity(reqEntity);
			try {

				HttpResponse response = httpClient.execute(post);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(outputStream);
					String d = outputStream.toString();
					logger.debug("PUT: "+d);
				}else{
					logger.error("Something error : "+response.getStatusLine().getStatusCode());
				}
			} catch (ClientProtocolException e1) {
				logger.error("ClientProtocol Exception occured. ");
				e1.printStackTrace();
			} catch (IOException e1) {
				logger.error("I/O Exception occured. ");
				e1.printStackTrace();
			}
		}

	}

	public void downloadFile(final String url, final File file){
		HttpGet httpGet = new HttpGet(url);
		final int BUFFER = 1024;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode;
			if((statusCode = response.getStatusLine().getStatusCode()) == HttpStatus.SC_OK){
				logger.info("Got right url and now start downloading ....");
				DataInputStream in = new DataInputStream(response.getEntity().getContent());
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				byte[] b = new byte[BUFFER];
				int readByte = 0;

				while((readByte = in.read(b)) != -1){
					out.write(b, 0, readByte);
				}

				// Close in/out stream
				in.close();
				out.close();
				logger.info("Downloaded DONE.");
			} else {
				logger.error("Got error when downloading file. status code :" + statusCode);
			}
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocol Exception occured. ");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("I/O Exception occured. ");
			e.printStackTrace();
		}
	}

	public JobDataDescription getJobDataDescription(final String jobId){
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", jobId));

		String data = sendRequest(GET_JOBDATA_DESCRIPTION_URI, urlParams);
		logger.debug("GetJobDataDescription Result:"+data);


		return (JobDataDescription)jsonToObject(data, JobDataDescription.class);
	}

	public PreallocatedJobResponse createPreallocatedJob(int jobTypeId, String title, String description,
			String metadata, int count) {

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobTypeId", String.valueOf(jobTypeId)));
		urlParams.add(new BasicNameValuePair("title", title));
		urlParams.add(new BasicNameValuePair("description", description));
		urlParams.add(new BasicNameValuePair("metadata", metadata));
		urlParams.add(new BasicNameValuePair("count", String.valueOf(count)));

		String data = sendRequest(CREATE_PREALLOCATED_JOB_URI, urlParams);
		logger.debug("CreatePreallocatedJob Response :"+ data);
		return jsonToObject(data, PreallocatedJobResponse.class);
	}

	public void configureJobNotification(final String jobId, final JobNotificationConfiguration conf){

		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(conf);
			logger.debug("ConfigureJobNotification JSON Data :" +json);
			sendPostRequestWithJson(CONF_JOB_NOTIFICATION_URI+"?jobId="+jobId, json);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException occured in configureJobNotification(). ");
			e.printStackTrace();
		}
	}

	public JobInfo cancelJob(String jobId) {
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", String.valueOf(jobId)));
		String data = sendRequest(CANCEL_JOB_URI, urlParams);
		logger.debug("Cancel Job Return :" + data);
		JobInfo resp = jsonToObject(data, JobInfo.class);

		return resp;
	}

	// Need to send DELETE request
	public void deleteJobData(String jobId, int dataTypeFlag) {
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("jobId", String.valueOf(jobId)));
		urlParams.add(new BasicNameValuePair("dataTypeFlag", String.valueOf(dataTypeFlag)));
		String data = sendDeleteRequest(DELETE_JOB_DATA_URI, urlParams);
		logger.debug("DeleteJob Return :" + data);
	}
}
