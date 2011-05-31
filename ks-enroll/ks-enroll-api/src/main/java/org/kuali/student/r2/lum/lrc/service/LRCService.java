/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

/**
 * The Learning Result Catalog Service is a Class I service which gives a set of operations to manage a learning result. A
 * learning result can be of various types e.g grades, credits etc. This service has basic CRUD operations to touch various
 * concepts that exist to model learning results e.g Result Value, Result Value Group, and Result Value Range.
 * 
 * @Author sambit
 * @Since Tue May 10 14:09:46 PDT 2011
 * @See <a href= "https://test.kuali.org/confluence/display/KULSTU/Learning+Result+Catalog+Service" >LrcService</>
 */
@WebService(name = "LrcService", targetNamespace = "http://student.kuali.org/wsdl/lrc")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LRCService extends DataDictionaryService, TypeService, StateService {

    /**
     * Retrieves existing result component by an identifier.
     * 
     * @param resultComponentId identifiers for resultComponent to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the results for these ids
     * @throws DoesNotExistException  resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException invalid resultComponentId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultComponentInfo getResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves result components by a list of identifiers.
     * 
     * @param resultComponentIdList  identifiers for result component
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return result component list
     * @throws DoesNotExistException resultComponent not found
     * @throws InvalidParameterException invalid resultComponentIdList
     * @throws MissingParameterException invalid resultComponentIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultComponentInfo> getResultComponentsByIdList(@WebParam(name = "resultComponentIdList") List<String> resultComponentIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of existing result components that a result value is tied to.
     * 
     * @param resultValueId identifier for result value
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the results for these ids
     * @throws DoesNotExistException  resultValue not found
     * @throws InvalidParameterException  invalid resultValueId
     * @throws MissingParameterException  invalid resultValueId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultComponentInfo> getResultComponentsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result group identifiers for a specified result component type.
     * 
     * @param resultValueGroupTypeKey   identifier for the result group type
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException  resultComponentTypeKey not found
     * @throws InvalidParameterException invalid resultComponentTypeKey
     * @throws MissingParameterException  missing resultComponentTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<ResultComponentInfo> getResultComponentsByType(@WebParam(name = "resultComponentTypeKey") String resultComponentTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new result Component.
     * 
     * @param resultComponentInfo   information about the result component being created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result component information
     * @throws AlreadyExistsException   result component already exists
     * @throws DataValidationErrorException   one or more values invalid for this operation
     * @throws InvalidParameterException   invalid resultComponentInfo
     * @throws MissingParameterException  missing resultComponentInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public ResultComponentInfo createResultComponent(@WebParam(name = "resultGroupInfo") ResultComponentInfo resultComponentInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing result component.
     * 
     * @param resultComponentId  identifier of the result component to update
     * @param resultGroupInfo   updated information about the result component
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated result component information
     * @throws DataValidationErrorException    one or more values invalid for this operation
     * @throws DoesNotExistException   resultComponentKey not found
     * @throws InvalidParameterException  invalid resultComponentId, resultComponentInfo
     * @throws MissingParameterException  missing resultComponentId, resultComponentInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException   action was attempted on an out of date version.
     */
    public ResultComponentInfo updateResultComponent(@WebParam(name = "resultComponentId") String resultComponentKey, @WebParam(name = "resultComponentInfo") ResultComponentInfo resultComponentInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing result component.
     * 
     * @param resultComponentId   identifier of the result component to update
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return status of the operation
     * @throws DoesNotExistException   resultComponentId not found
     * @throws InvalidParameterException  invalid resultComponentId
     * @throws MissingParameterException  missing resultComponentId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an academic calendar. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the academic calendar and a record
     * is found for that identifier, the validation checks if the
     * academic calendar can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     * @param validationType Identifier of the extent of validation
     * @param resultComponentInfo Result component to be validated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return
     * @throws DoesNotExistException resultComponentInfo does not exist
     * @throws InvalidParameterException validationType, resultComponentInfo does not exist
     * @throws MissingParameterException missing validationType, resultComponentInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultComponent(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultComponentInfo resultComponentInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /**
     * Retrieves a list of result value objects for a list of identifiers. 
     * 
     * @param resultValueIdList  identifier for the result
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation      
     * @return list of result group identifiers
     * @throws DoesNotExistException   If any resultValueId from the list is not found
     * @throws InvalidParameterException   invalid resultValueIdList
     * @throws MissingParameterException  missing resultValueIdList
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public List<ResultValueInfo> getResultValuesByIdList(@WebParam(name = "resultValueIdList") List<String> resultValueIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result value objects for a specified result component. It is sorted by the scale inside the component
     * 
     * @param resultComponentId  identifier for the result component
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException  resultValueId not found
     * @throws InvalidParameterException   invalid resultComponentId
     * @throws MissingParameterException  missing resultComponentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<ResultValueInfo> getResultValuesForResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new result value 
     * @param resultValueInfo
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return newly created resultValue
     * @throws AlreadyExistsException resultValue already exists
     * @throws DataValidationErrorException   one or more values invalid for this operation 
     * @throws InvalidParameterException  invalid resultValueInfo
     * @throws MissingParameterException missing resultValueInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update a result value
     * @param resultValueId resultValueId to be updated
     * @param resultValueInfo update information for the result value
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated information about the result value
     * @throws DataValidationErrorException   one or more values invalid for this operation
     * @throws DoesNotExistException resultValueId does not exist
     * @throws InvalidParameterException  invalid resultValueId, resultValueInfo
     * @throws MissingParameterException missing resultValueId, resultValueInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
     */
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Delete a result value. This should not be allowed if any result component is still referencing the result value.
     * @param resultValueId result value to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the delete operation
     * @throws DoesNotExistException resultValueId does not exist
     * @throws InvalidParameterException  invalid resultValueId
     * @throws MissingParameterException missing resultValueId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an academic calendar. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the academic calendar and a record
     * is found for that identifier, the validation checks if the
     * academic calendar can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     * @param validationType  Identifier of the extent of validation
     * @param resultValueInfo Result value to be validated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return
     * @throws DoesNotExistException resultValueInfo does not exist
     * @throws InvalidParameterException validationType, resultValueInfo does not exist
     * @throws MissingParameterException missing validationType, resultValueInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves result scale by an identifier.
     * 
     * @param resultScaleId identifiers for result scale to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the result scale for the id
     * @throws DoesNotExistException  resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException invalid resultComponentId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleId") String resultScaleId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
        
}
