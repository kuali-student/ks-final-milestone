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

package org.kuali.student.core.document.ui.server;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.document.ui.client.service.DocumentRpcService;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;

public class DocumentRpcGwtServlet extends BaseRpcGwtServletAbstract<DocumentService> implements DocumentRpcService{
	private static final long serialVersionUID = 1L;
	
	@Override
    public List<TypeInfo> getDocumentTypes() throws Exception {
        return service.getDocumentTypes(ContextUtils.getContextInfo());
    }
	
    @Override
	public DocumentInfo getDocument(String documentId) throws Exception{
		return service.getDocument(documentId, ContextUtils.getContextInfo());
		
	}
	
    @Override
	public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList)throws Exception{
		return service.getDocumentsByIds(documentIdList, ContextUtils.getContextInfo());
	}
	
    @Override
	public StatusInfo deleteDocument(String documentId) throws Exception{
		return service.deleteDocument(documentId, ContextUtils.getContextInfo());
	}
	
    @Override
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws Exception{
		return service.updateDocument(documentId, documentInfo, ContextUtils.getContextInfo());
    }
	
    @Override
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws Exception{
		return service.addDocumentCategoryToDocument(documentId, documentCategoryKey, ContextUtils.getContextInfo());
	}
	
    @Override
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws Exception{
		return service.removeDocumentCategoryFromDocument(documentId, documentCategoryKey, ContextUtils.getContextInfo());
    }

	@Override
    public Boolean isAuthorizedUploadDocuments(String id, String referenceTypeKey) {
		if (id != null && (!"".equals(id.trim()))) {
			String user = getCurrentUser();
            Map<String,String> permissionDetails = new LinkedHashMap<String,String>();
            permissionDetails.put(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, referenceTypeKey);
	        if (getPermissionService().isPermissionDefinedByTemplate(PermissionType.UPLOAD_DOCUMENTS.getPermissionNamespace(), PermissionType.UPLOAD_DOCUMENTS.getPermissionTemplateName(), permissionDetails)) {
	            Map<String,String> roleQuals = new LinkedHashMap<String,String>();
	            roleQuals.put(referenceTypeKey, id);
	            return Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(user, PermissionType.UPLOAD_DOCUMENTS.getPermissionNamespace(), PermissionType.UPLOAD_DOCUMENTS.getPermissionTemplateName(), permissionDetails, roleQuals));
	        }
		}
		return Boolean.TRUE;
    }

	@Override
	public StatusInfo deleteRefDocRelation(String documentId) throws Exception {
		return service.deleteRefDocRelation(documentId, ContextUtils.getContextInfo());
	}

	@Override
	public List<RefDocRelationInfo> getRefDocIdsForRef(String refObjectTypeKey, String refObjectId) throws Exception{
		return service.getRefDocRelationsByRef(refObjectTypeKey, refObjectId, ContextUtils.getContextInfo());
	}

	@Override
	public StatusInfo deleteRefDocRelationAndOrphanedDoc(String docRelationId, String documentId) throws Exception {
		
		//Delete the relation
		service.deleteRefDocRelation(docRelationId, ContextUtils.getContextInfo());
		
		//Also delete the document if there are no more relations to it
		try{
			List<RefDocRelationInfo> allRelations = null;
			service.getRefDocRelationsByDocument(documentId, ContextUtils.getContextInfo());
			if(allRelations == null || allRelations.isEmpty()){
				service.deleteDocument(documentId, ContextUtils.getContextInfo());
			}
		}catch(DoesNotExistException e){
			service.deleteDocument(documentId, ContextUtils.getContextInfo());
		}
		return new StatusInfo();
	}   
}