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

package org.kuali.student.core.document.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

/**
 *
 * @Author KSContractMojo
 * @Since Tue Jun 16 13:57:56 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Document+Service+v1.0-rc1">DocumentService</>
 *
 */
@WebService(name = "DocumentService", targetNamespace = "http://student.kuali.org/wsdl/document") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DocumentService { 
    /** 
     * Retrieves the list of document types known by this service
     * @return list of document type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<DocumentTypeInfo> getDocumentTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular document type
     * @param documentTypeKey document type identifier
     * @return document type information
     * @throws DoesNotExistException specified documentTypeKey not found
     * @throws InvalidParameterException invalid documentTypeKey
     * @throws MissingParameterException documentTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public DocumentTypeInfo getDocumentType(@WebParam(name="documentTypeKey")String documentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of document categories known by this service
     * @return list of document category information
     * @throws OperationFailedException unable to complete request
	 */
    public List<DocumentCategoryInfo> getDocumentCategories() throws OperationFailedException;

    /** 
     * Retrieves information about a particular document category
     * @param documentCategoryKey document category identifier
     * @return document category information
     * @throws DoesNotExistException specified documentCategoryKey not found
     * @throws InvalidParameterException invalid documentCategoryKey
     * @throws MissingParameterException documentCategoryKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public DocumentCategoryInfo getDocumentCategory(@WebParam(name="documentCategoryKey")String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a document. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the document (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the document can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param documentInfo document information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, documentInfo
     * @throws MissingParameterException missing validationTypeKey, documentInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateDocument(@WebParam(name="validationType")String validationType, @WebParam(name="documentInfo")DocumentInfo documentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about a document.
     * @param documentId identifier of the document
     * @return information about a document
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId
     * @throws MissingParameterException missing documentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public DocumentInfo getDocument(@WebParam(name="documentId")String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about documents from a list of identifiers.
     * @param documentIdList list of document identifiers
     * @return list of document information
     * @throws DoesNotExistException one of more documentIds not found
     * @throws InvalidParameterException one of more invalid documentIds
     * @throws MissingParameterException missing documentIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<DocumentInfo> getDocumentsByIdList(@WebParam(name="documentIdList")List<String> documentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves category information for a document.
     * @param documentId identifier of the document
     * @return list of document category information
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId
     * @throws MissingParameterException missing documentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<DocumentCategoryInfo> getCategoriesByDocument(@WebParam(name="documentId")String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new document.
     * @param documentTypeKey identifier of the document type
     * @param documentCategoryKey identifier of the document category
     * @param documentInfo detailed information on the document
     * @return detailed information on the document created
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DoesNotExistException documentCategory or documentType don't exist
	 */
    public DocumentInfo createDocument(@WebParam(name="documentTypeKey")String documentTypeKey, @WebParam(name="documentCategoryKey")String documentCategoryKey, @WebParam(name="documentInfo")DocumentInfo documentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /** 
     * Updates an existing document.
     * @param documentId identifier of the document to update
     * @param documentInfo updated information for the document
     * @return updated information on the document
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Document being updated does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public DocumentInfo updateDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentInfo")DocumentInfo documentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing document.
     * @param documentId identifier of the document to delete
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException Document to delete does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteDocument(@WebParam(name="documentId")String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds an existing document category to a document.
     * @param documentId identifier of the document
     * @param documentCategoryKey identifier of the document category
     * @return status of the operation (success, failed)
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException documentId, documentCategoryKey does not exist
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public StatusInfo addDocumentCategoryToDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentCategoryKey")String documentCategoryKey) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Removes an existing category from a document.
     * @param documentId identifier of the document
     * @param documentCategoryKey identifier of the document category
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException documentId, documentCategoryKey does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeDocumentCategoryFromDocument(@WebParam(name="documentId")String documentId, @WebParam(name="documentCategoryKey")String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
