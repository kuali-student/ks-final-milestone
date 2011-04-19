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

package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.rice.core.xml.dto.AttributeSet;
import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.RefDocRelationInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.authorization.PermissionType;

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
    	return service.updateDocument(documentId, documentInfo);
    }
	
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws Exception{
		return service.addDocumentCategoryToDocument(documentId, documentCategoryKey);
	}
	
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws Exception{
    	return service.removeDocumentCategoryFromDocument(documentId, documentCategoryKey);
    }

	@Override
    public Boolean isAuthorizedUploadDocuments(String id, String referenceTypeKey) {
		if (id != null && (!"".equals(id.trim()))) {
			String user = getCurrentUser();
            AttributeSet permissionDetails = new AttributeSet(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, referenceTypeKey);
	        if (getPermissionService().isPermissionDefinedForTemplateName(PermissionType.UPLOAD_DOCUMENTS.getPermissionNamespace(), PermissionType.UPLOAD_DOCUMENTS.getPermissionTemplateName(), permissionDetails)) {
	            AttributeSet roleQuals = new AttributeSet();
	            roleQuals.put(referenceTypeKey, id);
	            return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(user, PermissionType.UPLOAD_DOCUMENTS.getPermissionNamespace(), PermissionType.UPLOAD_DOCUMENTS.getPermissionTemplateName(), permissionDetails, roleQuals));
	        }
		}
		return Boolean.TRUE;
    }

	@Override
	public StatusInfo deleteRefDocRelation(String documentId) throws Exception {
		return service.deleteRefDocRelation(documentId);
	}

	@Override
	public List<RefDocRelationInfo> getRefDocIdsForRef(String refObjectTypeKey, String refObjectId) throws Exception{
		return service.getRefDocRelationsByRef(refObjectTypeKey, refObjectId);
	}

	@Override
	public StatusInfo deleteRefDocRelationAndOrphanedDoc(String docRelationId, String documentId) throws Exception {
		
		//Delete the relation
		service.deleteRefDocRelation(docRelationId);
		
		//Also delete the document if there are no more relations to it
		try{
			List<RefDocRelationInfo> allRelations = service.getRefDocRelationsByDoc(documentId);
			if(allRelations == null || allRelations.isEmpty()){
				service.deleteDocument(documentId);
			}
		}catch(DoesNotExistException e){
			service.deleteDocument(documentId);
		}
		return new StatusInfo();
	}
}
