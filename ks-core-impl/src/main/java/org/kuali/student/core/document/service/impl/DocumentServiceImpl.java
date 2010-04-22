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

package org.kuali.student.core.document.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.document.dao.DocumentDao;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.entity.Document;
import org.kuali.student.core.document.entity.DocumentCategory;
import org.kuali.student.core.document.entity.DocumentType;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.document.service.DocumentService", serviceName = "DocumentService", portName = "DocumentService", targetNamespace = "http://student.kuali.org/wsdl/documentService")
@Transactional(rollbackFor={Throwable.class})
public class DocumentServiceImpl implements DocumentService {
    private DocumentDao dao;
    private DictionaryService dictionaryServiceDelegate;// = new DictionaryServiceImpl(); //TODO this should probably be done differently, but I don't want to copy/paste the code in while it might still change
    private SearchManager searchManager;
    private Validator validator;
    
    public DocumentDao getDocumentDao(){
        return dao;
    }
    
    public void setDocumentDao(DocumentDao dao){
        this.dao=dao;
    }
    
    
    @Override
    public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(dao.addDocumentCategoryToDocument(documentId, documentCategoryKey));
        
        return statusInfo;
    }
    @Override
    public DocumentInfo createDocument(String documentTypeKey, String documentCategoryKey, DocumentInfo documentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        checkForMissingParameter(documentInfo, "documentInfo");
        
        DocumentType type = dao.fetch(DocumentType.class, documentTypeKey);
        DocumentCategory category = dao.fetch(DocumentCategory.class, documentCategoryKey);
        
        Document doc = DocumentServiceAssembler.toDocument(documentInfo, dao);
        doc.setType(type);
        doc.setCategoryList(Arrays.asList(category));
        dao.create(doc);
        return DocumentServiceAssembler.toDocumentInfo(doc);
    }
    @Override
    public StatusInfo deleteDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        dao.delete(Document.class, documentId);
        return new StatusInfo();
    }
    @Override
    public List<DocumentCategoryInfo> getCategoriesByDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        List<DocumentCategory> categories = dao.getCategoriesByDocument(documentId);
        return DocumentServiceAssembler.toDocumentCategoryInfos(categories);
    }
    @Override
    public DocumentInfo getDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        return DocumentServiceAssembler.toDocumentInfo(dao.fetch(Document.class, documentId));
    }
    @Override
    public List<DocumentCategoryInfo> getDocumentCategories() throws OperationFailedException {
        List<DocumentCategory> categories = dao.find(DocumentCategory.class);
        return DocumentServiceAssembler.toDocumentCategoryInfos(categories);
    }
    @Override
    public DocumentCategoryInfo getDocumentCategory(String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        return DocumentServiceAssembler.toDocumentCategoryInfo(dao.fetch(DocumentCategory.class, documentCategoryKey));
    }
    @Override
    public DocumentTypeInfo getDocumentType(String documentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        return DocumentServiceAssembler.toDocumentTypeInfo(dao.fetch(DocumentType.class, documentTypeKey));
    }
    @Override
    public List<DocumentTypeInfo> getDocumentTypes() throws OperationFailedException {
        return DocumentServiceAssembler.toDocumentTypeInfos(dao.find(DocumentType.class));
    }
    @Override
    public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentIdList, "documentIdList");
        checkForEmptyList(documentIdList, "documentIdList");
        List<Document> documents = dao.getDocumentsByIdList(documentIdList);
        return DocumentServiceAssembler.toDocumentInfos(documents);
    }
    @Override
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(dao.removeDocumentCategoryFromDocument(documentId, documentCategoryKey));
        return statusInfo;
    }

    @Override
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentInfo, "documentInfo");
        
        Document document = dao.fetch(Document.class, documentId);
        
        if (!String.valueOf(document.getVersionInd()).equals(documentInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("Document to be updated is not the current version");
        }
        
        document = DocumentServiceAssembler.toDocument(document, documentInfo, dao);
        dao.update(document);
        
        return DocumentServiceAssembler.toDocumentInfo(document);
    }
    

    @Override
    public List<ValidationResultContainer> validateDocument(String validationType, DocumentInfo documentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(documentInfo, "documentInfo");

		List<ValidationResultInfo> valResults = validator.validateTypeStateObject(documentInfo, getObjectStructure("documentInfo")); 
		ValidationResultContainer valContainer = new ValidationResultContainer();
		valContainer.setValidationResults(valResults);
		
		List<ValidationResultContainer> valContList = new ArrayList<ValidationResultContainer>();
		return valContList;        
    }

   
    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }
    
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }
    
    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(
            DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

	public DocumentDao getDao() {
		return dao;
	}

	public void setDao(DocumentDao dao) {
		this.dao = dao;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
