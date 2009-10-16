/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.server.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

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
import org.kuali.student.common.ui.client.dto.FileStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.dto.FileStatus.FileTransferStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus.UploadTransferStatus;
import org.kuali.student.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.mock.service.DocumentRelationService;

public class UploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	DocumentService documentService;
	DocumentRelationService relationService;
	
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
			        	info.setDesc(text);
			        }
			        else if(name.equals("documentType")){
			        	//TODO: Dont know what to do with this yet
			        }
			    } 
			    else {
			    	String fullFileName = item.getName();
			    	if (fullFileName != null) {
			            String filename = FilenameUtils.getName(fullFileName);
			            FileStatus fileStatus = new FileStatus();
			            fileStatus.setFileName(filename);
			            status.getFileStatusList().add(currentItem, fileStatus);
				    	DocumentBinaryInfo bInfo = new DocumentBinaryInfo();
				    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*				    	Iterator headers = item.getHeaders().getHeaderNames();
				    	while(headers.hasNext()){
				    		System.out.println(headers.next());
				    	}*/
				    	//TODO import this class maybe?
				    	//Base64OutputStream base64 = new Base64OutputStream(bytes);
				    	byte[] buffer = new byte[4096];
				    	while (true) {
				    	    int read = stream.read(buffer);
				    	    if (read == -1) {
				    	        break;
				    	    } else {
				    	        bytes.write(buffer, 0, read);
				    	        fileStatus.setProgress(fileStatus.getProgress() + read);
				    	        // TODO you could put a Thread.sleep(500) here to simulate slow network
				    	        //Thread.sleep(500);
				    	    }
				    	}
				    	bytes.flush();
				    	fileStatus.setStatus(FileTransferStatus.UPLOAD_FINISHED);
				    	bInfo.setBinary(new String(Base64.encodeBase64(bytes.toByteArray())));
				    	
				    	info.setDocumentBinaryInfo(bInfo);
				    	info.setState("active");
				    	info.setFileName(filename);
				    	
				    	int extSeperator = filename.lastIndexOf(".");
				    	info.setName(filename.substring(0, extSeperator));
				    	
				    	//FIXME Probably temporary solution for type on document info
				    	String type = "documentType." + filename.substring(extSeperator + 1);
				    	info.setType(type);
			    	}
			    	else{
			    		//No file specified error
			    		status.setStatus(UploadTransferStatus.ERROR);
			    	}
			    }
			     
		    	if(info.getDesc() != null && info.getDocumentBinaryInfo() != null && info.getType() != null){
		    		//FileStatus fileStatus = status.getFileStatusMap().get(info.getFileName());
		    		FileStatus fileStatus = status.getFileStatusList().get(currentItem);
		    		try{
			    		DocumentInfo createdDoc = documentService.createDocument(info.getType(), "documentCategory.proposal", info);
			    		fileStatus.setStatus(FileTransferStatus.COMMIT_FINISHED);
			    		if(createdDoc != null){
			    			status.getCreatedDocIds().add(createdDoc.getId());
			    			fileStatus.setDocId(createdDoc.getId());
			    		}
			    		
			    		//TODO replace this with real stuff later
			    		RefDocRelationInfoMock relationInfo = new RefDocRelationInfoMock();
			    		relationInfo.setDesc(info.getDesc());
			    		relationInfo.setRefId(request.getParameter("referenceId"));
			    		relationInfo.setTitle(info.getFileName());
			    		relationInfo.setDocumentId(info.getId());
			    		relationService.createRefDocRelation(request.getParameter("referenceId"), createdDoc.getId(), relationInfo);
		    		}
		    		catch(Exception e){
		    			fileError = true;
		    			e.printStackTrace();
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
			e.printStackTrace();
		}	
		
/*    	
    	//fileStatusMap.put(request.getParameter("uploadId"), status);

		int itemNum = 0;

		if(ServletFileUpload.isMultipartContent(request)){
			try {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setProgressListener(new DocumentProgressListener(request.getParameter("uploadId")));
			
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				DocumentInfo info = new DocumentInfo();
				
				//assumes that transfer retains order, I believe this is true
				while (iter.hasNext()) {
				    FileItem item = (FileItem) iter.next();
				    itemNum++;
				    System.out.println(itemNum);
				    if (item.isFormField()) {
				    	String name = item.getFieldName();
				    	String value = item.getString();
				        //System.out.println(item.getFieldName() + ": " + item.getString());
				        if(name.equals("documentDescription")){
				        	RichTextInfo text = new RichTextInfo();
				        	text.setPlain(value);
				        	info.setDesc(text);
				        }
				        else if(name.equals("documentType")){
				        	//TODO: Dont know what to do with this yet
				        }
				        //TODO: probably eventually get document category from hidden field here too
				    }
				    else{
				    	String fullFileName = item.getName();
				    	if(item.getName() != null && !(item.getName().equals("")) && item.getSize() != 0){
					    	DocumentBinaryInfo bInfo = new DocumentBinaryInfo();
					    	bInfo.setBinary(new String(Base64.encodeBase64(item.get())));
					    	info.setDocumentBinaryInfo(bInfo);
					    	info.setState("active");
					    	
					    	int lastSeperator = fullFileName.lastIndexOf("\\");
					    	if(lastSeperator == -1){
					    		lastSeperator = fullFileName.lastIndexOf("/");
					    	}
					    	String fileName = fullFileName.substring(lastSeperator + 1).replace(' ', '_');
					    	info.setFileName(fileName);
					    	
					    	int extSeperator = fileName.lastIndexOf(".");
					    	
					    	info.setName(fileName.substring(0, extSeperator));
					    	String type = "documentType." + fileName.substring(extSeperator + 1);
					    	info.setType(type);
					    	if(documentService != null){
					    		DocumentInfo createdDoc = documentService.createDocument(type, "documentCategory.proposal", info);
					    		if(createdDoc != null){
					    			status.getCreatedDocIds().add(createdDoc.getId());
					    		}
					    	}
					    	else{
					    		//Could not contact service error
					    		//TODO: what should we do with files that do finish, but one (or more) in the batch errors?
					    		status.setStatus(UploadTransferStatus.ERROR);
					    	}
				    	}
				    	else{
				    		//No file specified error
				    		//TODO: what should we do with files that do finish, but one (or more) in the batch errors?
				    		status.setStatus(UploadTransferStatus.ERROR);
				    	}
				    	info = new DocumentInfo();
				    	
				    }
				}
				
				if(status.getStatus() != UploadTransferStatus.ERROR){
					status.setStatus(UploadTransferStatus.COMMIT_FINISHED);
				}
				
			} catch (Exception e) {
				//Server upload error
				status.setStatus(UploadTransferStatus.ERROR);
				e.printStackTrace();
			}*/
			
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			DocumentInfo info = null;
			try {
				info = documentService.getDocument(request.getParameter("docId"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(info != null && info.getDocumentBinaryInfo() != null && info.getDocumentBinaryInfo().getBinary() != null && 
					!(info.getDocumentBinaryInfo().getBinary().isEmpty())){
				
				ServletOutputStream op = response.getOutputStream();
				try {
				
					byte[] fileBytes = Base64.decodeBase64(info.getDocumentBinaryInfo().getBinary().getBytes());
					int length = fileBytes.length;
			        
			        ServletContext context = getServletConfig().getServletContext();
			        //TODO no mimetypes in info exist - switch check or future addition to DocumentInfo needed later
			        String mimetype = context.getMimeType(info.getFileName());
			        
			        response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
			        response.setContentLength(length);
			        response.setHeader( "Content-Disposition", "attachment; filename=\"" + info.getFileName() + "\"" );
		
			        //
			        //  Stream to the requester.
			        //
			        op.write(fileBytes,0,length);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	public DocumentRelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(DocumentRelationService relationService) {
		this.relationService = relationService;
	}
	

}
