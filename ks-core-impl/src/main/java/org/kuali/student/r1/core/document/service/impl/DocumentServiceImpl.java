/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r1.core.document.service.impl;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.core.document.dao.DocumentDao;
import org.kuali.student.r1.core.document.entity.Document;
import org.kuali.student.r1.core.document.entity.DocumentCategory;
import org.kuali.student.r1.core.document.entity.DocumentType;
import org.kuali.student.r1.core.document.entity.RefDocRelation;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;
import org.kuali.student.r1.core.document.entity.RefDocRelationType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;

/**
 * This is a description of what this class does - lindholm don't forget to fill
 * this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.r2.core.document.service.DocumentService", serviceName = "DocumentService", portName = "DocumentService", targetNamespace = "http://student.kuali.org/wsdl/documentService")
public class DocumentServiceImpl implements DocumentService {

    private DocumentDao dao;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    private SearchManager searchManager;

    public DocumentDao getDocumentDao() {
        return dao;
    }

    public void setDocumentDao(DocumentDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(dao.addDocumentCategoryToDocument(documentId, documentCategoryKey));

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public DocumentInfo createDocument(String documentTypeKey, String documentCategoryKey, DocumentInfo documentInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        DocumentType type;
        DocumentCategory category;

        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        checkForMissingParameter(documentCategoryKey, "documentCategoryKey");
        checkForMissingParameter(documentInfo, "documentInfo");

        // Validate
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = this.validateDocument("OBJECT", documentInfo, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Validation call failed." + e.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            // Convert R1 to R2
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        dao.delete(Document.class, documentId);
        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getDocumentCategoryTypesByDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        List<DocumentCategory> categories = dao.getCategoriesByDocument(documentId);
        return DocumentServiceAssembler.toDocumentCategoryTypeInfos(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentInfo getDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        return DocumentServiceAssembler.toDocumentInfo(dao.fetch(Document.class, documentId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getDocumentCategoryTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
       List<DocumentCategory> categories = dao.find(DocumentCategory.class);
       return DocumentServiceAssembler.toDocumentCategoryTypeInfos(categories);
    }

    
    @Override
    @Transactional(readOnly = true)
    public TypeInfo getDocumentCategoryType(String documentCategoryTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
             checkForMissingParameter(documentCategoryTypeKey, "documentCategoryTypeKey");
        return DocumentServiceAssembler.toDocumentCategoryTypeInfo(dao.fetch(DocumentCategory.class, documentCategoryTypeKey));

    }
    

    @Override
    @Transactional(readOnly=true)
    public TypeInfo getDocumentType(String documentTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(documentTypeKey, "documentTypeKey");
        return DocumentServiceAssembler.toGenericTypeInfo(dao.fetch(DocumentType.class, documentTypeKey));
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<TypeInfo> getDocumentTypes(ContextInfo contextInfo) throws OperationFailedException {
        return DocumentServiceAssembler.toGenericTypeInfoList(dao.find(DocumentType.class));
    }
       
    
//    @Override
//    @Transactional(readOnly=true)
//    public List<String> getRefObjectTypes(ContextInfo contextInfo) throws OperationFailedException {
//        return DocumentServiceAssembler.toGenericTypeKeyList(dao.find(RefObjectType.class));
//    }
    
//    @Override
//    @Transactional(readOnly=true)
//    public List<String> getRefObjectSubTypes(String refObjectTypeKey, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException {
//        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
//        RefObjectType refOjectType;
//		try {
//			refOjectType = dao.fetch(RefObjectType.class, refObjectTypeKey);
//		} catch (DoesNotExistException e) {
//			return new ArrayList<String>(0);
//		}
//        return DocumentServiceAssembler.toGenericTypeKeyList(refOjectType.getRefObjectSubTypes());
//    }
    
    @Override
    @Transactional(readOnly=true)
    public List<TypeInfo> getRefDocRelationTypes(ContextInfo contextInfo) throws OperationFailedException {
        return DocumentServiceAssembler.toGenericTypeInfoList(dao.find(RefDocRelationType.class));
    }

    @Override
    @Transactional(readOnly=true)
    public TypeInfo getRefDocRelationType(String refDocRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
       checkForMissingParameter(refDocRelationTypeKey, "refDocRelationTypeKey");
       return DocumentServiceAssembler.toGenericTypeInfo(dao.fetch(RefDocRelationType.class, refDocRelationTypeKey));
    }
    
    
//    @Override
//    @Transactional(readOnly=true)
//	public List<TypeInfo> getRefDocRelationTypesForRefObjectSubType(String refSubTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        checkForMissingParameter(refSubTypeKey, "refSubTypeKey");
//        
//        RefObjectSubType refObjectSubType = dao.fetch(RefObjectSubType.class, refSubTypeKey);
//        return DocumentServiceAssembler.toGenericTypeInfoList(refObjectSubType.getRefDocRelationTypes());
//    }
//    
    
    @Override
    @Transactional(readOnly = true)
    public List<DocumentInfo> getDocumentsByIds(List<String> documentIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentIds, "documentIdList");
        checkForEmptyList(documentIds, "documentIdList");
        List<Document> documents = dao.getDocumentsByIdList(documentIds);
        return DocumentServiceAssembler.toDocumentInfos(documents);
    }

    @Override
    @Transactional(readOnly = true)
    public RefDocRelationInfo getRefDocRelation(String refDocRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refDocRelationId, "refDocRelationId");
        return DocumentServiceAssembler.toRefDocRelationInfo(dao.fetch(RefDocRelation.class, refDocRelationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefDocRelationInfo> getRefDocRelationsByRef(String refObjectTypeKey, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForMissingParameter(refObjectId, "refObjectId");
        return DocumentServiceAssembler.toRefDocRelationInfos(dao.getRefDocRelationsByRef(refObjectTypeKey, refObjectId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RefDocRelationInfo> getRefDocRelationsByDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(documentId, "documentId");
        return DocumentServiceAssembler.toRefDocRelationInfos(dao.getRefDocRelationsByDoc(documentId));
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(documentInfo, "documentInfo");

        List<ValidationResultInfo> validationResults = validateDocument("OBJECT", documentInfo, contextInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        Document document = dao.fetch(Document.class, documentId);

        if (!String.valueOf(document.getVersionNumber()).equals(documentInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Document to be updated is not the current version");
        }

        document = DocumentServiceAssembler.toDocument(document, documentInfo, dao);

        return DocumentServiceAssembler.toDocumentInfo(dao.update(document));
    }

    @Override
    public List<ValidationResultInfo> validateDocument(String validationType, DocumentInfo documentInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(documentInfo, "documentInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(DocumentInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(documentInfo, objStructure, null);
        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateRefDocRelation(String validationType, RefDocRelationInfo refDocRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(RefDocRelationInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(refDocRelationInfo, objStructure, null);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public RefDocRelationInfo createRefDocRelation(String refObjectTypeKey,
            String refObjectId,
            String documentId,
            String refDocRelationTypeKey,
            RefDocRelationInfo refDocRelationInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForMissingParameter(refObjectId, "refObjectId");
        checkForMissingParameter(refDocRelationTypeKey, "refDocRelationTypeKey");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");

        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateRefDocRelation("OBJECT", refDocRelationInfo, contextInfo);
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public RefDocRelationInfo updateRefDocRelation(String refDocRelationId,
            RefDocRelationInfo refDocRelationInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException, 
            DataValidationErrorException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException, 
            VersionMismatchException {
        checkForMissingParameter(refDocRelationId, "refDocRelationId");
        checkForMissingParameter(refDocRelationInfo, "refDocRelationInfo");

        List<ValidationResultInfo> validationResults = validateRefDocRelation("OBJECT", refDocRelationInfo, contextInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        refDocRelationInfo.setId(refDocRelationId);

        RefDocRelation refDocRelation = dao.fetch(RefDocRelation.class, refDocRelationId);

        if (!String.valueOf(refDocRelation.getVersionNumber()).equals(refDocRelationInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException("RefDocRelation to be updated is not the current version");
        }

        refDocRelation = DocumentServiceAssembler.toRefDocRelation(refDocRelation, refDocRelationInfo, dao);

        return DocumentServiceAssembler.toRefDocRelationInfo(dao.update(refDocRelation));
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteRefDocRelation(String refDocRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
        if (param != null && param instanceof List<?> && ((List<?>) param).size() == 0) {
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

    /**
     *
     * This method ...
     *
     * @return
     * @
     */
    @Deprecated
    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    /**
     *
     * This method ...
     *
     * @param dictionaryServiceDelegate
     * @
     */
    @Deprecated
    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    /**
     *
     * This method ...
     *
     * @return
     * @
     */
    @Deprecated
    public DocumentDao getDao() {
        return dao;
    }

    /**
     *
     * This method ...
     *
     * @param dao
     * @
     */
    @Deprecated
    public void setDao(DocumentDao dao) {
        this.dao = dao;
    }

    /**
     *
     * This method ...
     *
     * @return
     * @
     */
    @Deprecated
    public SearchManager getSearchManager() {
        return searchManager;
    }

    /**
     *
     * This method ...
     *
     * @param searchManager
     * @
     */
    @Deprecated
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    /**
     *
     * This method ...
     *
     * @return
     * @
     */
    @Deprecated
    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    /**
     *
     * This method ...
     *
     * @param validatorFactory
     * @
     */
    @Deprecated
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }
}
