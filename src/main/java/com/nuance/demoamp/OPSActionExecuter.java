
package com.nuance.demoamp;

import java.io.File;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.TempFileProvider;


import com.nuance.imaging.ops.opsclient.OPSService;
import com.nuance.imaging.ops.opsclient.data.ConversionParameters;

import com.nuance.imaging.ops.opsclient.enums.JobPriority;
import com.nuance.imaging.ops.opsclient.enums.JobStateEnum;

public class OPSActionExecuter extends ActionExecuterAbstractBase {
	ContentService 	contentService;
	NodeService		nodeService;
	OPSService		opsService;
	ConversionParameters	params;
	FileFolderService	fileFolderService;
	
	
	
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setOpsService(OPSService opsService) {
		this.opsService = opsService;
	}

	public void setParams(ConversionParameters params) {
		this.params = params;
	}

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public ContentService getContentService() {
		return contentService;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public OPSService getOpsService() {
		return opsService;
	}

	public ConversionParameters getParams() {
		return params;
	}

	public FileFolderService getFileFolderService() {
		return fileFolderService;
	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		// Get Original Filename
		String inputFileName = (String)nodeService.getProperty(actionedUponNodeRef, ContentModel.PROP_NAME);
		Locale orgLocale = (Locale)nodeService.getProperty(actionedUponNodeRef, ContentModel.PROP_LOCALE);

		// Get Original content and save as a temp file.
		ContentReader reader = contentService.getReader(actionedUponNodeRef, ContentModel.PROP_CONTENT);
		InputStream originalInputStream = reader.getContentInputStream();
		
		File inputFile = null;
		try {
			inputFile = TempFileProvider.createTempFile(originalInputStream, "OCS-", null);
			String jobId = opsService.createJob(18, "From Alfresco", "Searchable PDF Conversion", "alfresco");
			List<String> uploadUrl = opsService.getUploadUrls(jobId, 1);
			Map<String, File> upload = new HashMap<String, File>();
			upload.put(uploadUrl.get(0), inputFile);
			opsService.postInputFiles(upload);
			
	        opsService.startJob(jobId, 1800, JobPriority.MEDIUM, params);
			
			while(opsService.getJobInfo(jobId).getState() != JobStateEnum.COMPLETED){
				Thread.sleep(opsService.getJobInfo(jobId).getEstimatedWorkTimeSec() * 1000);
			}
			List<String> downloadUrls = opsService.getDownloadUrls(jobId);
			File outputFile = TempFileProvider.createTempFile("OCSO-", null);
	        opsService.downloadFile(downloadUrls.get(0), outputFile);
			
	        // Get Parent Node
	        ChildAssociationRef childAssociationRef = nodeService.getPrimaryParent(actionedUponNodeRef);
	        NodeRef parent = childAssociationRef.getParentRef();
	        
	        // Create a file and get writer
	        FileInfo outFileInfo = fileFolderService.create(parent, inputFileName+".pdf", ContentModel.TYPE_CONTENT);
	        ContentWriter writer = fileFolderService.getWriter(outFileInfo.getNodeRef());
	        	        
	        // Write output
	        writer.setLocale(orgLocale);
	        writer.setMimetype("application/pdf");
	        writer.putContent(outputFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		

	}

}
