package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentRpcServiceAsync extends BaseRpcServiceAsync{
	public void getDocument(String documentId, AsyncCallback<DocumentInfo> callback) throws Exception;
	
	public void getDocumentsByIdList(List<String> documentIdList, AsyncCallback<List<DocumentInfo>> callback) throws Exception;
	
	public void deleteDocument(String documentId, AsyncCallback<StatusInfo> callback) throws Exception;
	
    public void updateDocument(String documentId, DocumentInfo documentInfo, AsyncCallback<DocumentInfo> callback) throws Exception;
	
	public void addDocumentCategoryToDocument(String documentId, String documentCategoryKey, AsyncCallback<StatusInfo> callback) throws Exception;
	
    public void removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey, AsyncCallback<StatusInfo> callback) throws Exception;
}
