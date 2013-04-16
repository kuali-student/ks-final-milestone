/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.document.service;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;

import java.util.List;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

/**
 * Refer to service contract javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class DocumentServiceDecorator implements DocumentService {

    private DocumentService nextDecorator;

    public DocumentService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(DocumentService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public DocumentInfo getDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getDocument(documentId, contextInfo);
    }

    @Override
    public List<DocumentInfo> getDocumentsByIds(List<String> documentIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getDocumentsByIds(documentIds, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateDocument(String validationTypeKey, DocumentInfo documentInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().validateDocument(validationTypeKey, documentInfo, contextInfo);
    }

    @Override
    public DocumentInfo createDocument(String documentTypeKey, String documentCategoryKey, DocumentInfo documentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().createDocument(documentTypeKey, documentCategoryKey, documentInfo, contextInfo);
    }

    @Override
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator ().updateDocument(documentId, documentInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().deleteDocument(documentId, contextInfo);
    }

    @Override
    public List<TypeInfo> getDocumentCategoryTypesByDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getDocumentCategoryTypesByDocument(documentId, contextInfo);
    }

    @Override
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().removeDocumentCategoryFromDocument(documentId, documentCategoryKey, contextInfo);
    }

    @Override
    public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator ().addDocumentCategoryToDocument(documentId, documentCategoryKey, contextInfo);
    }

    @Override
    public RefDocRelationInfo getRefDocRelation(String refDocRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getRefDocRelation(refDocRelationId, contextInfo);
    }

    @Override
    public List<RefDocRelationInfo> getRefDocRelationsByDocument(String documentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getRefDocRelationsByDocument(documentId, contextInfo);
    }

    @Override
    public List<RefDocRelationInfo> getRefDocRelationsByRef(String refObjectTypeKey, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getRefDocRelationsByRef(refObjectTypeKey, refObjectId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRefDocRelation(String validationTypeKey, RefDocRelationInfo refDocRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().validateRefDocRelation(validationTypeKey, refDocRelationInfo, contextInfo);
    }

    @Override
    public RefDocRelationInfo createRefDocRelation(String refObjectTypeKey, String refObjectId, String documentId, String refDocRelationTypeKey, RefDocRelationInfo refDocRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator ().createRefDocRelation(refObjectTypeKey, refObjectId, documentId, refDocRelationTypeKey, refDocRelationInfo, contextInfo);
    }

    @Override
    public RefDocRelationInfo updateRefDocRelation(String refDocRelationId, RefDocRelationInfo refDocRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator ().updateRefDocRelation(refDocRelationId, refDocRelationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRefDocRelation(String refDocRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().deleteRefDocRelation(refDocRelationId, contextInfo);
    }

    public TypeInfo getDocumentType(String documentTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getDocumentType(documentTypeKey, contextInfo);
    }

    public List<TypeInfo> getDocumentTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getDocumentTypes(contextInfo);
    }

    public TypeInfo getRefDocRelationType(String refDocRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRefDocRelationType(refDocRelationTypeKey, contextInfo);
    }

    public List<TypeInfo> getRefDocRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRefDocRelationTypes(contextInfo);
    }

    public TypeInfo getDocumentCategoryType(String documentCategoryTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getDocumentCategoryType(documentCategoryTypeKey, contextInfo);
    }

    public List<TypeInfo> getDocumentCategoryTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getDocumentCategoryTypes(contextInfo);
    }

    
    
    
    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        try {
            return getNextDecorator ().getObjectStructure(objectTypeKey);
        } catch (OperationFailedException ex) {
            throw new IllegalArgumentException ("Unexpected", ex);
        }
    }

    @Override
    public List<String> getObjectTypes() {
        try {
            return getNextDecorator ().getObjectTypes();
        } catch (OperationFailedException ex) {
            throw new IllegalArgumentException ("Unexpected", ex);
        }
    }

}
