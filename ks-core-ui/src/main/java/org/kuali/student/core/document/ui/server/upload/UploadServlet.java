/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.document.ui.server.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.common.ui.client.dto.FileStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.dto.FileStatus.FileTransferStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus.UploadTransferStatus;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;

public class UploadServlet extends HttpServlet{
	final Logger LOG = Logger.getLogger(UploadServlet.class);
	private static final long serialVersionUID = 1L;
	DocumentService documentService;
	
	private class DocumentProgressListener implements ProgressListener{
		
		private long kiloBytes = -1;
		private String uploadId;
		private int currentItem = 1;
		private boolean uploadFinished = false;
		private HttpSession session;
		
		public DocumentProgressListener(String uploadId, HttpSession session){
			this.uploadId = uploadId;
			this.session = session;
		}
		
		public void update(long pBytesRead, long pContentLength, int pItems) {
	       UploadStatus status = (UploadStatus) (session.getAttribute(uploadId));
	       if(status.getTotalSize() == null && pContentLength != -1){
	    	   status.setTotalSize(pContentLength);
	       }
	       
	       if(!uploadFinished && pBytesRead == pContentLength){
	    	   status.setItemsRead(status.getItemsRead()+ 1);
	    	   status.setStatus(UploadTransferStatus.UPLOAD_FINISHED);
	    	   uploadFinished = true;
	       }
	       
	       //check to not update too often
	       long kBytes = pBytesRead / 1024;
	       if (kiloBytes == kBytes) {
	           return;
	       }
	       kiloBytes = kBytes;

	       status.setProgress(pBytesRead);
	       if(pItems > currentItem){
	    	   status.setItemsRead(status.getItemsRead()+ 1);
	       }
	       
		}
	}
	
	String data;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UploadStatus status = new UploadStatus();
		request.getSession().setAttribute(request.getParameter("uploadId"), status);
		try {
			
			ServletFileUpload upload = new ServletFileUpload();
			upload.setProgressListener(new DocumentProgressListener(request.getParameter("uploadId"), request.getSession()));
			FileItemIterator iter = upload.getItemIterator(request);
			DocumentInfo info = new DocumentInfo();
			
			boolean fileError = false;
			int currentItem = 0;
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();
			    String name = item.getFieldName();
			    InputStream stream = item.openStream();
			    if (item.isFormField()) {
			    	String value = Streams.asString(stream);
			        if(name.equals("documentDescription")){
			        	RichTextInfo text = new RichTextInfo();
			        	text.setPlain(value);
			        	info.setDescr(text);
			        }
			    } else {
			    	String fullFileName = item.getName();
			    	if (fullFileName != null) {
			            String filename = FilenameUtils.getName(fullFileName);
			            FileStatus fileStatus = new FileStatus();
			            fileStatus.setFileName(filename);
			            status.getFileStatusList().add(currentItem, fileStatus);
				    	DocumentBinaryInfo bInfo = new DocumentBinaryInfo();
				    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				    	byte[] buffer = new byte[4096];
				    	while (true) {
				    	    int read = stream.read(buffer);
				    	    if (read == -1) {
				    	        break;
				    	    } else {
				    	        bytes.write(buffer, 0, read);
				    	        fileStatus.setProgress(fileStatus.getProgress() + read);
				    	    }
				    	}
				    	bytes.flush();
				    	fileStatus.setStatus(FileTransferStatus.UPLOAD_FINISHED);
				    	bInfo.setBinary(new String(Base64.encodeBase64(bytes.toByteArray())));
				    	
				    	info.setDocumentBinary(bInfo);
				    	info.setStateKey(DtoState.ACTIVE.toString());
				    	info.setFileName(filename);
				    	
				    	int extSeperator = filename.lastIndexOf(".");
				    	info.setName(filename.substring(0, extSeperator));
				    	
				    	//FIXME Probably temporary solution for type on document info
				    	String type = "documentType." + filename.substring(extSeperator + 1);
				    	info.setTypeKey(type);
			    	}
			    	else{
			    		//No file specified error
			    		status.setStatus(UploadTransferStatus.ERROR);
			    	}
			    }
			     
		    	if(info.getDescr() != null 
		    	   && info.getDocumentBinary() != null && 
		    	   info.getType() != null){
		    		//FileStatus fileStatus = status.getFileStatusMap().get(info.getFileName());
		    		FileStatus fileStatus = status.getFileStatusList().get(currentItem);
		    		try{
		    		    DocumentInfo createdDoc = documentService.createDocument(info.getTypeKey(), "documentCategory.proposal", info, ContextUtils.getContextInfo());
			    		fileStatus.setStatus(FileTransferStatus.COMMIT_FINISHED);
			    		if(createdDoc != null){
			    			status.getCreatedDocIds().add(createdDoc.getId());
			    			fileStatus.setDocId(createdDoc.getId());
			    		}
			    		
			    		RefDocRelationInfo relationInfo = new RefDocRelationInfo();
			    		relationInfo.setStateKey(DtoState.ACTIVE.toString());
			    		relationInfo.setDescr(info.getDescr());
			    		relationInfo.setRefObjectId(request.getParameter("referenceId"));
			    		relationInfo.setRefObjectTypeKey(request.getParameter("refObjectTypeKey"));
			    		relationInfo.setTitle(info.getFileName());
			    		relationInfo.setDocumentId(createdDoc.getId());
			    		relationInfo.setType(request.getParameter("refDocRelationTypeKey"));
			    		documentService.createRefDocRelation(relationInfo.getRefObjectTypeKey(),
                                                relationInfo.getRefObjectId(),
                                                relationInfo.getDocumentId(),
                                                relationInfo.getTypeKey(), 
                                                relationInfo,
                                                ContextUtils.getContextInfo());
		    		}catch(Exception e){
		    			fileError = true;
		    			LOG.error(e);
		    			fileStatus.setStatus(FileTransferStatus.ERROR);
		    		}
		    		info = new DocumentInfo();
		    		currentItem++;
		    	}
			}
			
			if(fileError){
				status.setStatus(UploadTransferStatus.ERROR);
			}
			else{
				status.setStatus(UploadTransferStatus.COMMIT_FINISHED);
			}
			
		} catch (Exception e) {
			status.setStatus(UploadTransferStatus.ERROR);
			LOG.error(e);
		}
			
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			DocumentInfo info = null;
			try {
				info = documentService.getDocument(request.getParameter("docId"), ContextUtils.getContextInfo());
			} catch (Exception e) {
				LOG.error(e);
			}
			
			if(info != null 
			      && info.getDocumentBinary() != null 
			      && info.getDocumentBinary().getBinary() != null 
			      && !(info.getDocumentBinary().getBinary().isEmpty())
			        ){
				
				ServletOutputStream op = response.getOutputStream();
				try {
				    byte[] fileBytes = Base64.decodeBase64(info.getDocumentBinary().getBinary().getBytes());
					int length = fileBytes.length;
			        
			        ServletContext context = getServletConfig().getServletContext();
			        String mimetype = context.getMimeType(info.getFileName());
			        
			        response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
			        response.setContentLength(length);
			        response.setHeader( "Content-Disposition", "attachment; filename=\"" + info.getFileName() + "\"" );
		
			        //
			        //  Stream to the requester.
			        //
			        op.write(fileBytes,0,length);
				} catch (Exception e) {
					LOG.error(e);
				}
				finally{
			        op.flush();
			        op.close();
				}

			}
			else{
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("Sorry, the file could not be retrieved.  It may not exist, or the server could not be contacted.");
			}
        
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
