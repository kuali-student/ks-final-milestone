package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/DocumentRpcService")
public interface DocumentRpcService extends BaseRpcService {
	public DocumentInfo getDocument(String documentId) throws Exception;
	
	public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList) throws Exception;
	
	public StatusInfo deleteDocument(String documentId) throws Exception;
	
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws Exception;
	
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws Exception;
	
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws Exception;
}
