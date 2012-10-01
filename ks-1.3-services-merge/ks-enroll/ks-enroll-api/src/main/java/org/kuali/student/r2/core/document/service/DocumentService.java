/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0(the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.document.service;

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
import org.kuali.student.r2.common.util.constants.DocumentServiceConstants;

import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 *
 * The Document Service supports the management of document objects. Relations
 * between stored documents and external entities are managed through
 *
 * the respective entity service.
 *
 * @Version 2.0
 * @Author tom
 * @Author Sri komandur@uw.edu
 *
 */
@WebService(name = "DocumentService", targetNamespace = DocumentServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DocumentService {

    /**
     * Retrieves information about a document
     *
     * @param documentId identifier of the document
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a document
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId, contextInfo
     * @throws MissingParameterException documentId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public DocumentInfo getDocument(@WebParam(name="documentId")String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about documents from a list of identifiers
     *
     * @param documentIds list of document identifiers
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of document information
     * @throws DoesNotExistException one of more documentIds not found
     * @throws InvalidParameterException invalid documentIds, contextInfo
     * @throws MissingParameterException documentIds, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<DocumentInfo> getDocumentsByIds(@WebParam(name="documentIds")List<String> documentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a document. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object
     * and its directly contained sub-objects or expanded to perform all
     * tests related to this object. If an identifier is present for the
     * document (and/or one of its contained sub-objects) and a record is found
     * for that identifier, the validation checks if the document can be shifted
     * to the new values. If an identifier is not present or a record cannot
     * be found for the identifier, it is assumed that the record does not exist
     * and as such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the current
     * object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param documentInfo document information to be tested.
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, documentInfo, contextInfo
     * @throws MissingParameterException validationTypeKey, documentInfo, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateDocument(@WebParam(name="validationTypeKey")String validationTypeKey, @WebParam(name="documentInfo")DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new document
     *
     * @param documentTypeKey identifier of the document type
     * @param documentCategoryKey identifier of the document category
     * @param documentInfo detailed information on the document
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return detailed information on the document created
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Id or Key does not exist
     * @throws InvalidParameterException invalid documentTypeKey, documentCategoryKey, documentInfo, contextInfo
     * @throws MissingParameterException documentTypeKey, documentCategoryKey, documentInfo, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException attempted update of readonly data
     */
    public DocumentInfo createDocument(@WebParam(name="documentTypeKey")String documentTypeKey, @WebParam(name="documentCategoryKey")String documentCategoryKey, @WebParam(name="documentInfo")DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing document
     *
     * @param documentId identifier of the document to update
     * @param documentInfo updated information for the document
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return updated information on the document
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Document being updated does not exist
     * @throws InvalidParameterException invalid documentId, documentInfo, contextInfo
     * @throws MissingParameterException documentId, documentInfo, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException attempted update of readonly data
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public DocumentInfo updateDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentInfo")DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing document
     *
     * @param documentId identifier of the document to delete
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException Document to delete does not exist
     * @throws InvalidParameterException invalid documentId, contextInfo
     * @throws MissingParameterException documentId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteDocument(@WebParam(name="documentId")String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a particular document category
     *
     * @param documentCategoryKey document category identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return document category information
     * @throws DoesNotExistException specified documentCategoryKey not found
     * @throws InvalidParameterException invalid documentCategoryKey, contextInfo
     * @throws MissingParameterException documentCategoryKey, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     *
     */
    public DocumentCategoryInfo getDocumentCategory(@WebParam(name="documentCategoryKey")String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of document categories known by this service
     *
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of document category information
     * @throws MissingParameterException contextInfo not specified
     * @throws OperationFailedException unable to complete request
     *
     */
    public List<DocumentCategoryInfo> getDocumentCategories(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException;

    /**
     * Retrieves category information for a document
     *
     * @param documentId identifier of the document
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of document category information
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId, contextInfo
     * @throws MissingParameterException documentId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<DocumentCategoryInfo> getDocumentCategoriesByDocumentId(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes an existing category from a document
     *
     * @param documentId identifier of the document
     * @param documentCategoryKey identifier of the document category
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException documentId, documentCategoryKey does not exist
     * @throws InvalidParameterException invalid documentId, documentCategoryKey, contextInfo
     * @throws MissingParameterException documentId, documentCategoryKey, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeDocumentCategoryFromDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentCategoryKey")String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds an existing document category to a document
     *
     * @param documentId identifier of the document
     * @param documentCategoryKey identifier of the document category
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException documentId, documentCategoryKey does not exist
     * @throws InvalidParameterException invalid documentId, documentCategoryKey, contextInfo
     * @throws MissingParameterException documentId, documentCategoryKey, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version
     */
    public StatusInfo addDocumentCategoryToDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentCategoryKey")String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Retrieves information about a relationship between a reference
     * and document
     *
     * @param refDocRelationId reference document relationship identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a relationship between a reference and a document
     * @throws DoesNotExistException specified relationship not found
     * @throws InvalidParameterException invalid refDocRelationId, contextInfo
     * @throws MissingParameterException refDocRelationId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RefDocRelationInfo getRefDocRelation(@WebParam(name="refDocRelationId")String refDocRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about reference document relationships
     * for a particular document
     *
     * @param documentId document identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of reference document relationships
     * @throws DoesNotExistException specified document not found
     * @throws InvalidParameterException invalid documentId, contextInfo
     * @throws MissingParameterException documentId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RefDocRelationInfo> getRefDocRelationsByDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about reference document relationships for a
     * particular reference
     *
     * @param refObjectTypeKey reference type
     * @param refObjectId reference identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of reference document relationships
     * @throws DoesNotExistException specified refId, refObjectTypeKey not found
     * @throws InvalidParameterException invalid refId, refObjectTypeKey, contextInfo
     * @throws MissingParameterException refId, refObjectTypeKey, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RefDocRelationInfo> getRefDocRelationsByRef(@WebParam(name="refObjectTypeKey")String refObjectTypeKey, @WebParam(name="refObjectId")String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a reference document relationship. Depending on the
     * value of validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects or
     * expanded to perform all tests related to this object. If an identifier
     * is present for the comment (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation checks if the
     * comment can be shifted to the new values. If an identifier is not present
     * or a record cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be much
     * shallower, typically mimicking those performed by setting the
     * validationType to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param refDocRelationInfo reference document relationship information to be tested
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, refDocRelationInfo, contextInfo
     * @throws MissingParameterException missing validationTypeKey, refDocRelationInfo, contextInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateRefDocRelation(@WebParam(name="validationTypeKey")String validationTypeKey, @WebParam(name="refDocRelationInfo")RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a relationship between a reference and a document.
     *
     * @param refObjectTypeKey reference type
     * @param refObjectId identifier of the reference
     * @param documentId identifier of the document
     * @param refDocRelationTypeKey type of relationship between reference and document
     * @param refDocRelationInfo detailed information about the reference/document relationship
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return detailed information about the relationship
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Id or Key does not exist
     * @throws InvalidParameterException invalid refObjectTypeKey, refObjectId, documentId, refDocRelationTypeKey, refDocRelationInfo, contextInfo
     * @throws MissingParameterException refObjectTypeKey, refObjectId, documentId, refDocRelationTypeKey, refDocRelationInfo, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException attempted update of readonly data
     */
    public RefDocRelationInfo createRefDocRelation(@WebParam(name="refObjectTypeKey")String refObjectTypeKey, @WebParam(name="refObjectId")String refObjectId, @WebParam(name="documentId")String documentId, @WebParam(name="refDocRelationTypeKey")String refDocRelationTypeKey, @WebParam(name="refDocRelationInfo")RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between a reference and a document
     *
     * @param refDocRelationId identifier of a relationship between a reference and document
     * @param refDocRelationInfo detailed information about the relationship between a reference and document
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return detailed information about the relationship between a reference and document
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid refDocRelationId, refDocRelationInfo, contextInfo
     * @throws MissingParameterException refDocRelationId, refDocRelationInfo, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version
     */
    public RefDocRelationInfo updateRefDocRelation(@WebParam(name="refDocRelationId")String refDocRelationId, @WebParam(name="refDocRelationInfo")RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Removes a relationship between a reference and a document
     *
     * @param refDocRelationId identifier of the relationship between the reference and document
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException relationship does not exist
     * @throws InvalidParameterException invalid refDocRelationId, contextInfo
     * @throws MissingParameterException refDocRelationId, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRefDocRelation(@WebParam(name="refDocRelationId")String refDocRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
