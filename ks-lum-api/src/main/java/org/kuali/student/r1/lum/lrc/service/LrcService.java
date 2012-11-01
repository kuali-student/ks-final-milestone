/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations
 * under the License.
 */

package org.kuali.student.r1.lum.lrc.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.r1.lum.lrc.dto.ScaleInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * The Learning Result Catalog Service is a Class I service which
 * gives a set of operations to manage a learning result. A learning
 * result can be of various types e.g grades, credits etc. This
 * service has basic CRUD operations to touch various concepts that
 * exist to model learning results e.g Result Value, Result Value
 * Group, and Result Value Range.
 *
 * @Author sambit
 * @Since Tue May 10 14:09:46 PDT 2011
 */
@WebService(name = "LRCService", targetNamespace = LrcServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface LrcService extends SearchService, DictionaryService {

    /**
     * Retrieves existing result component by an identifier.
     *
     * @param resultValuesGroupId identifiers for resultValuesGroup to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the results for these Ids
     * @throws DoesNotExistException  resultValuesGroupId not found
     * @throws InvalidParameterException invalid resultValuesGroupId
     * @throws MissingParameterException invalid resultValuesGroupId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves result components by a list of identifiers.
     *
     * @param resultValuesGroupIds  identifiers for result component
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return result component list
     * @throws DoesNotExistException resultValuesGroup not found
     * @throws InvalidParameterException invalid resultValuesGroupIds
     * @throws MissingParameterException invalid resultValuesGroupIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIds(@WebParam(name = "resultValuesGroupIds") List<String> resultValuesGroupIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of existing result components that a result value is tied to.
     *
     * @param resultValueId identifier for result value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return details of the results for these Ids
     * @throws DoesNotExistException resultValue not found
     * @throws InvalidParameterException invalid resultValueId
     * @throws MissingParameterException invalid resultValueId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result group identifiers for a specified
     * result component type.
     *
     * @param resultValueGroupTypeKey identifier for the result group type
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException resultValuesGroupTypeKey not found
     * @throws InvalidParameterException invalid resultValuesGroupTypeKey
     * @throws MissingParameterException missing resultValuesGroupTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<String> getResultValuesGroupIdsByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new result Component.
     *
     * @param gradeValuesGroupInfo information about the result component 
     *        being created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result component information
     * @throws AlreadyExistsException result component already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing result component.
     *
     * @param resultValuesGroupId identifier of the result component to update
     * @param resultGroupInfo updated information about the result component
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated result component information
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation
     * @throws DoesNotExistException resultValuesGroupKey not found
     * @throws InvalidParameterExceptioninvalid resultValuesGroupId or 
     *                                          resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupId or
     *                                   resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of 
     *                                  date version
     */
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing result component.
     *
     * @param resultValuesGroupId identifier of the result component to update
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return status of the operation
     * @throws DoesNotExistException resultValuesGroupId not found
     * @throws InvalidParameterException invalid resultValuesGroupId
     * @throws MissingParameterException missing resultValuesGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a result component. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object. 
     *
     * @param validationType Identifier of the extent of validation
     * @param gradeValuesGroupInfo Result component to be validated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return
     * @throws DoesNotExistException resultValuesGroupInfo does not exist
     * @throws InvalidParameterException validationType or 
     *                                   resultValuesGroupInfo does not exist
     * @throws MissingParameterException missing validationType, resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves result value by its id.
     *
     * @param resultValueId identifier for the result
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation      
     * @return details about a result value
     * @throws DoesNotExistException the resultValueId is not found
     * @throws InvalidParameterException invalid resultValueId
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result value objects for a list of identifiers. 
     *
     * @param resultValueIds identifier for the result
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation      
     * @return list of result group identifiers
     * @throws DoesNotExistException a resultValueId from the list is not found
     * @throws InvalidParameterException invalid resultValueIds
     * @throws MissingParameterException missing resultValueIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public List<ResultValueInfo> getResultValuesByIds(@WebParam(name = "resultValueIds") List<String> resultValueIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result value objects for a specified result
     * component. It is sorted by the scale inside the component.
     *
     * @param resultValuesGroupId identifier for the result component
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException resultValueId not found
     * @throws InvalidParameterException invalid resultValuesGroupId
     * @throws MissingParameterException missing resultValuesGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new result value 
     * @param resultValueInfo
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return newly created resultValue
     * @throws AlreadyExistsException resultValue already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation 
     * @throws InvalidParameterException invalid resultValueInfo
     * @throws MissingParameterException missing resultValueInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update a result value
     * @param resultValueId resultValueId to be updated
     * @param resultValueInfo update information for the result value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated information about the result value
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation
     * @throws DoesNotExistException resultValueId does not exist
     * @throws InvalidParameterException invalid resultValueId, resultValueInfo
     * @throws MissingParameterException missing resultValueId, resultValueInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of 
     *                                  date version
     */
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Delete a result value. This should not be allowed if any result component is still referencing the result value.
     * @param resultValueId result value to be deleted
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the delete operation
     * @throws DoesNotExistException resultValueId does not exist
     * @throws InvalidParameterException  invalid resultValueId
     * @throws MissingParameterException missing resultValueId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Result Value. Depending on the value of
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
     *
     * @param validationType  Identifier of the extent of validation
     * @param resultValueInfo Result value to be validated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a ValidationResultInfo
     * @throws DoesNotExistException resultValueInfo does not exist
     * @throws InvalidParameterException validationType or resultValueInfo 
     *                                   does not exist
     * @throws MissingParameterException missing validationType or resultValueInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves result scale by an identifier.
     *
     * @param resultScaleId identifiers for result scale to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the result scale for the id
     * @throws DoesNotExistException  resultValuesGroupId not found
     * @throws InvalidParameterException invalid resultValuesGroupId
     * @throws MissingParameterException invalid resultValuesGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *
     * Retrieves result values by result scale key.
     *
     * @param resultScaleKey
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of result values for the scale
     * @throws DoesNotExistException resultScaleKey is not found
     * @throws InvalidParameterException invalid resultScaleKey
     * @throws MissingParameterException null resultScaleKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *
     * @param scaleKey
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @Deprecated
    public ScaleInfo getScale(@WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,	OperationFailedException;

    /**
     *
     * @param resultComponentTypeKey
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @Deprecated
    public ResultComponentTypeInfo getResultComponentType(@WebParam(name = "resultComponentTypeKey") String resultComponentTypeKey, @WebParam(name = "contextInfo")  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     *
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    @Deprecated
    public List<ResultComponentTypeInfo> getResultComponentTypes(@WebParam(name = "contextInfo")  ContextInfo contextInfo) throws OperationFailedException;

}
