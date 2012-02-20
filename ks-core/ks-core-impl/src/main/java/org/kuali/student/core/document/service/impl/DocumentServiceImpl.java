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

import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.document.dao.DocumentDao;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.dto.RefDocRelationInfo;
import org.kuali.student.core.document.dto.RefDocRelationTypeInfo;
import org.kuali.student.core.document.entity.Document;
import org.kuali.student.core.document.entity.DocumentCategory;
import org.kuali.student.core.document.entity.DocumentType;
import org.kuali.student.core.document.entity.RefDocRelation;
import org.kuali.student.core.document.entity.RefDocRelationType;
import org.kuali.student.core.document.entity.RefObjectSubType;
import org.kuali.student.core.document.entity.RefObjectType;
import org.kuali.student.core.document.service.DocumentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.document.service.DocumentService", serviceName = "DocumentService", portName = "DocumentService", targetNamespace = "http://student.kuali.org/wsdl/documentService")
public class DocumentServiceImpl implements DocumentService {
    private DocumentDao dao;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    private SearchManager searchManager;
    
    public DocumentDao getDocumentDao(){
        return dao;
    }
    
    public void setDocumentDao(DocumentDao dao){
        this.dao=dao;
    }
    
    
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(dao.addDocumentCategoryToDocument(documentId, documentCategoryKey));
        
        return statusInfo;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public DocumentInfo createDocument(String documentTypeKey, String documentCategoryKey, DocumentInfo documentInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        DocumentType type;
        DocumentCategory category;
        
        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        checkForMissingParameter(documentInfo, "documentInfo");
    	
        // Validate
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateDocument("OBJECT", documentInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Validation call failed." + e.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
    	
    	try {
    	    type = dao.fetch(DocumentType.class, documentTypeKey);
    	    category = dao.fetch(DocumentCategory.class, documentCategoryKey);
    	} catch (DoesNotExistException dnee) {
    	    throw new OperationFailedException("error fetching document keys", dnee);
        }
    	
    	Document doc = DocumentServiceAssembler.toDocument(new Document(), documentInfo, dao);
    	doc.setType(type);
    	doc.setCategoryList(Arrays.asList(category));
    	dao.create(doc);
    	return DocumentServiceAssembler.toDocumentInfo(doc);
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        dao.delete(Document.class, documentId);
        return new StatusInfo();
    }
    @Override
    @Transactional(readOnly=true)
    public List<DocumentCategoryInfo> getCategoriesByDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        List<DocumentCategory> categories = dao.getCategoriesByDocument(documentId);
        return DocumentServiceAssembler.toDocumentCategoryInfos(categories);
    }
    @Override
    @Transactional(readOnly=true)
    public DocumentInfo getDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        return DocumentServiceAssembler.toDocumentInfo(dao.fetch(Document.class, documentId));
    }
    @Override
    @Transactional(readOnly=true)
    public List<DocumentCategoryInfo> getDocumentCategories() throws OperationFailedException {
        List<DocumentCategory> categories = dao.find(DocumentCategory.class);
        return DocumentServiceAssembler.toDocumentCategoryInfos(categories);
    }
    
    @Override
    @Transactional(readOnly=true)
    public DocumentCategoryInfo getDocumentCategory(String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        return DocumentServiceAssembler.toDocumentCategoryInfo(dao.fetch(DocumentCategory.class, documentCategoryKey));
    }
    
    @Override
    @Transactional(readOnly=true)
    public DocumentTypeInfo getDocumentType(String documentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        return DocumentServiceAssembler.toGenericTypeInfo(DocumentTypeInfo.class,(dao.fetch(DocumentType.class, documentTypeKey)));
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<DocumentTypeInfo> getDocumentTypes() throws OperationFailedException {
        return DocumentServiceAssembler.toGenericTypeInfoList(DocumentTypeInfo.class,dao.find(DocumentType.class));
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<String> getRefObjectTypes() throws OperationFailedException {
        return DocumentServiceAssembler.toGenericTypeKeyList(dao.find(RefObjectType.class));
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<String> getRefObjectSubTypes(String refObjectTypeKey) throws MissingParameterException, OperationFailedException {
        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
        RefObjectType refOjectType;
		try {
			refOjectType = dao.fetch(RefObjectType.class, refObjectTypeKey);
		} catch (DoesNotExistException e) {
			return new ArrayList<String>(0);
		}
        return DocumentServiceAssembler.toGenericTypeKeyList(refOjectType.getRefObjectSubTypes());
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<RefDocRelationTypeInfo> getRefDocRelationTypes() throws OperationFailedException {
        return DocumentServiceAssembler.toGenericTypeInfoList(RefDocRelationTypeInfo.class, dao.find(RefDocRelationType.class));
    }
    
    @Override
    @Transactional(readOnly=true)
	public List<RefDocRelationTypeInfo> getRefDocRelationTypesForRefObjectSubType(String refSubTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(refSubTypeKey, "refSubTypeKey");
        
        RefObjectSubType refObjectSubType = dao.fetch(RefObjectSubType.class, refSubTypeKey);
        return DocumentServiceAssembler.toGenericTypeInfoList(RefDocRelationTypeInfo.class, refObjectSubType.getRefDocRelationTypes());
    }
    @Override
    @Transactional(readOnly=true)
    public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentIdList, "documentIdList");
        checkForEmptyList(documentIdList, "documentIdList");
        List<Document> documents = dao.getDocumentsByIdList(documentIdList);
        return DocumentServiceAssembler.toDocumentInfos(documents);
    }
    @Override
    @Transactional(readOnly=true)
    public RefDocRelationInfo getRefDocRelation(String refDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refDocRelationId, "refDocRelationId");
        return DocumentServiceAssembler.toRefDocRelationInfo(dao.fetch(RefDocRelation.class, refDocRelationId));
    }
    @Override
    @Transactional(readOnly=true)
	public List<RefDocRelationInfo> getRefDocRelationsByRef(String refObjectTypeKey, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForMissingParameter(refObjectId, "refObjectId");
        return DocumentServiceAssembler.toRefDocRelationInfos(dao.getRefDocRelationsByRef(refObjectTypeKey, refObjectId));       
    }
    @Override
    @Transactional(readOnly=true)
    public List<RefDocRelationInfo> getRefDocRelationsByDoc(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        return DocumentServiceAssembler.toRefDocRelationInfos(dao.getRefDocRelationsByDoc(documentId));       
    }
    
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(dao.removeDocumentCategoryFromDocument(documentId, documentCategoryKey));
        return statusInfo;
    }

    /**
     * Does not update Type or categories
     */
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentInfo, "documentInfo");
        
        List<ValidationResultInfo> validationResults = validateDocument("OBJECT", documentInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        Document document = dao.fetch(Document.class, documentId);
        
        if (!String.valueOf(document.getVersionNumber()).equals(documentInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("Document to be updated is not the current version");
        }
        
        document = DocumentServiceAssembler.toDocument(document, documentInfo, dao);
                
        return DocumentServiceAssembler.toDocumentInfo(dao.update(document));
    }
    

    @Override
    public List<ValidationResultInfo> validateDocument(String validationType, DocumentInfo documentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(documentInfo, "documentInfo");
        
        ObjectStructureDefinition objStructure = this.getObjectStructure(DocumentInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(documentInfo, objStructure);
        return validationResults;        
    }


    @Override
    public List<ValidationResultInfo> validateRefDocRelation(String validationType, RefDocRelationInfo refDocRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(RefDocRelationInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(refDocRelationInfo, objStructure);
        return validationResults;        
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public RefDocRelationInfo createRefDocRelation(String refObjectTypeKey, String refObjectId, String documentId, String refDocRelationTypeKey, RefDocRelationInfo refDocRelationInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForMissingParameter(refObjectId, "refObjectId");
        checkForMissingParameter(refDocRelationTypeKey, "refDocRelationTypeKey");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");
        
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateRefDocRelation("OBJECT", refDocRelationInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Validation call failed." + e.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
                         
        refDocRelationInfo.setRefObjectTypeKey(refObjectTypeKey);
        refDocRelationInfo.setRefObjectId(refObjectId);
        refDocRelationInfo.setType(refDocRelationTypeKey);;
        
        RefDocRelation refDocRelation = DocumentServiceAssembler.toRefDocRelation(new RefDocRelation(), refDocRelationInfo, dao);

        return DocumentServiceAssembler.toRefDocRelationInfo(dao.create(refDocRelation));
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public RefDocRelationInfo updateRefDocRelation(String refDocRelationId, RefDocRelationInfo refDocRelationInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException {
        checkForMissingParameter(refDocRelationId, "refDocRelationId");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");
        
        List<ValidationResultInfo> validationResults = validateRefDocRelation("OBJECT", refDocRelationInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
                
        refDocRelationInfo.setId(refDocRelationId);
        
        RefDocRelation refDocRelation = dao.fetch(RefDocRelation.class, refDocRelationId);
        
        if (!String.valueOf(refDocRelation.getVersionNumber()).equals(refDocRelationInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("RefDocRelation to be updated is not the current version");
        }
        
        refDocRelation = DocumentServiceAssembler.toRefDocRelation(refDocRelation, refDocRelationInfo, dao);
                
        return DocumentServiceAssembler.toRefDocRelationInfo(dao.update(refDocRelation));
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteRefDocRelation(String refDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refDocRelationId, "refDocRelationId");
        dao.delete(RefDocRelation.class, refDocRelationId);
        return new StatusInfo();
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
        if (param != null && param instanceof List<?> && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }
    
    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }
    
    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
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

    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }	
}
