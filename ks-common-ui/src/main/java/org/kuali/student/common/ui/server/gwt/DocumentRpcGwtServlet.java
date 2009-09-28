package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;

public class DocumentRpcGwtServlet extends BaseRpcGwtServletAbstract<DocumentService> implements DocumentRpcService{

	private static final long serialVersionUID = 1L;
	
	public DocumentInfo getDocument(String documentId) throws Exception{
		return service.getDocument(documentId);
	}
	
	public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList)throws Exception{
		return service.getDocumentsByIdList(documentIdList);
	}
	
	public StatusInfo deleteDocument(String documentId) throws Exception{
		return service.deleteDocument(documentId);
	}
	
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws Exception{
    	return updateDocument(documentId, documentInfo);
    }
	
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws Exception{
		return addDocumentCategoryToDocument(documentId, documentCategoryKey);
	}
	
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws Exception{
    	return removeDocumentCategoryFromDocument(documentId, documentCategoryKey);
    }
}
