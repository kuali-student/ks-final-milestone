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
package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.rice.StudentIdentityConstants;

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

	@Override
    public Boolean isAuthorizedUploadDocuments(String referenceId, String referenceTypeKey) {
		if (referenceId != null && (!"".equals(referenceId.trim()))) {
			String namespaceCode = "KS-SYS";
			String permissionTemplateName = "Upload to Document";
			String user = getCurrentUser();
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE,referenceTypeKey);
			roleQuals.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID, referenceId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(user, namespaceCode, permissionTemplateName, permissionDetails, roleQuals));
		}
		return Boolean.TRUE;
    }
}
