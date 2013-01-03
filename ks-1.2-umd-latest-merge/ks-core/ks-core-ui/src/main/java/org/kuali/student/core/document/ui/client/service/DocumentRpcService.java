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

package org.kuali.student.core.document.ui.client.service;

import java.util.List;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.dto.RefDocRelationInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/DocumentRpcService")
public interface DocumentRpcService extends BaseRpcService {    

    public List<DocumentTypeInfo> getDocumentTypes() throws Exception;
    
	public DocumentInfo getDocument(String documentId) throws Exception;
	
	public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList) throws Exception;
	
	public StatusInfo deleteDocument(String documentId) throws Exception;
	
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws Exception;
	
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws Exception;
	
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws Exception;

    /**
     * Check for authorization to upload documents
     * @param id identifier of the object
     * @param referenceTypeKey reference type key of the object
     */
    public Boolean isAuthorizedUploadDocuments(String id, String referenceTypeKey);
    
	public StatusInfo deleteRefDocRelation(String docRelationId) throws Exception;
	
	public StatusInfo deleteRefDocRelationAndOrphanedDoc(String docRelationId, String documentId) throws Exception;
	
	public List<RefDocRelationInfo> getRefDocIdsForRef(String refObjectTypeKey, String refObjectId) throws Exception;

}
