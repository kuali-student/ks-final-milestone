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
package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

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
@WebService(name = "LrcService", targetNamespace = LrcServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LRCService extends LrcServiceBusinessLogic {

    /**
     * Retrieves existing result values group by an identifier.
     *
     * @param resultValuesGroupKey identifiers for resultValuesGroup to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the results for these Ids
     * @throws DoesNotExistException  resultValuesGroupKey not found
     * @throws InvalidParameterException invalid resultValuesGroupKey
     * @throws MissingParameterException invalid resultValuesGroupKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves result values groups by a list of identifiers.
     *
     * @param resultValuesGroupKeys  identifiers for result values group
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return result values group list
     * @throws DoesNotExistException resultValuesGroup not found
     * @throws InvalidParameterException invalid resultValuesGroupKeys
     * @throws MissingParameterException invalid resultValuesGroupKeys
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(@WebParam(name = "resultValuesGroupKeys") List<String> resultValuesGroupKeys,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of existing result values groups that a result value is tied to.
     *
     * @param resultValueKey identifier for result value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return details of the results for these keys
     * @throws DoesNotExistException resultValue not found
     * @throws InvalidParameterException invalid resultValueKey
     * @throws MissingParameterException invalid resultValueKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueKey") String resultValueKey,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of existing result values groups that a result scale is tied to.
     *
     * @param resultScaleKey identifier for result scale
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return details of the results for these keys
     * @throws DoesNotExistException resultValue not found
     * @throws InvalidParameterException invalid resultValueKey
     * @throws MissingParameterException invalid resultValueKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of result group identifiers for a specified
     * result values group type.
     *
     * @param resultValuesGroupTypeKey identifier for the result group type
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
    public List<String> getResultValuesGroupKeysByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new result Values Group.
     *
     * @param resultValuesGroupInfo information about the result values group 
     *        being created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result values group information
     * @throws AlreadyExistsException result values group already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValuesGroupInfo createResultValuesGroup(
            @WebParam(name = "resultScaleKey") String resultScaleKey,
            @WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey,
            @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo resultValuesGroupInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Updates an existing result values group.
     *
     * @param resultValuesGroupKey identifier of the result values group to update
     * @param resultGroupInfo updated information about the result values group
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated result values group information
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation
     * @throws DoesNotExistException resultValuesGroupKey not found
     * @throws InvalidParameterExceptioninvalid resultValuesGroupKey or 
     *                                          resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupKey or
     *                                   resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of 
     *                                  date version
     */
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey,
                                                         @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Deletes an existing result values group.
     *
     * @param resultValuesGroupKey identifier of the result values group to update
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return status of the operation
     * @throws DoesNotExistException resultValuesGroupKey not found
     * @throws InvalidParameterException invalid resultValuesGroupKey
     * @throws MissingParameterException missing resultValuesGroupKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a result values group. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object. 
     *
     * @param validationType Identifier of the extent of validation
     * @param gradeValuesGroupInfo Result values group to be validated
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
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType,
                                                                @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Get or create a new result values group holding the specified number of credits
     *
     * The resulting RVG should have a type of FIXED
     *
     * May also create the corresponding credit value.
     *
     * @param creditValue the credit value to be found/created
     * @param scaleKey the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result values group information
     * @throws AlreadyExistsException result values group already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(@WebParam(name = "creditValue") String creditValue,
                                                                       @WebParam(name = "scaleKey") String scaleKey,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get or create a new result values group holding the specified number of credits
     *
     * The resulting RVG will have a type of RANGE
     *
     * @param creditValueMin the minimum credit value of the range to be found/created
     * @param creditValueMax the maximum credit value to be found/created
     * @param creditValueIncrement the credit value increment of the range to be found/created
     * @param scaleKey the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result values group information
     * @throws AlreadyExistsException result values group already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(@WebParam(name = "creditValueMin") String creditValueMin,
                                                                       @WebParam(name = "creditValueMax") String creditValueMax,
                                                                       @WebParam(name = "creditValueIncrement") String creditValueIncrement,
                                                                       @WebParam(name = "scaleKey") String scaleKey,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get or create a new result values group holding the specified number of credits
     *
     * The resulting RVG should have the type of MULTIPLE
     *
     * @param creditValues the minimum credit value of the range to be found/created
     * @param scaleKey the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result values group information
     * @throws AlreadyExistsException result values group already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultValuesGroupInfo
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(@WebParam(name = "creditValues") List<String> creditValues,
                                                                          @WebParam(name = "scaleKey") String scaleKey,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves result value by its id.
     *
     * @param resultValueKey identifier for the result
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation      
     * @return details about a result value
     * @throws DoesNotExistException the resultValueKey is not found
     * @throws InvalidParameterException invalid resultValueKey
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueKey") String resultValueKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of result value objects for a list of identifiers. 
     *
     * @param resultValueKeys identifier for the result
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation      
     * @return list of result group identifiers
     * @throws DoesNotExistException a resultValueKey from the list is not found
     * @throws InvalidParameterException invalid resultValueKeys
     * @throws MissingParameterException missing resultValueKeys
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public List<ResultValueInfo> getResultValuesByKeys(@WebParam(name = "resultValueKeys") List<String> resultValueKeys,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of result values by type.
     *
     * @param resultValueTypeKey identifier for the result group type
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException resultValueTypeKey not found
     * @throws InvalidParameterException invalid resultValueTypeKey
     * @throws MissingParameterException missing resultValueTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<String> getResultValueKeysByType(@WebParam(name = "resultValueTypeKey") String resultValueTypeKey,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of result value objects for a specified result
     * values group. It is sorted by the scale inside the values group.
     *
     * @param resultValuesGroupKey identifier for the result values group
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException resultValueKey not found
     * @throws InvalidParameterException invalid resultValuesGroupKey
     * @throws MissingParameterException missing resultValuesGroupKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Create a new result value 
     * @param resultScaleKey scale to which this value is attached
     * @param resultValueTypeKey type of the result value to be created
     * @param resultValueInfo  info about the result value
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
    public ResultValueInfo createResultValue(
            @WebParam(name = "resultScaleKey") String resultScaleKey,
            @WebParam(name = "resultValueTypeKey") String resultValueTypeKey,
            @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Update a result value
     * @param resultValueKey resultValueKey to be updated
     * @param resultValueInfo update information for the result value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated information about the result value
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation
     * @throws DoesNotExistException resultValueKey does not exist
     * @throws InvalidParameterException invalid resultValueKey, resultValueInfo
     * @throws MissingParameterException missing resultValueKey, resultValueInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of 
     *                                  date version
     */
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueKey") String resultValueKey,
                                             @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Delete a result value. This should not be allowed if any result values group is still referencing the result value.
     * @param resultValueKey result value to be deleted
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the delete operation
     * @throws DoesNotExistException resultValueKey does not exist
     * @throws DependentObjectsExistException if a group is tied to this value
     * @throws InvalidParameterException  invalid resultValueKey
     * @throws MissingParameterException missing resultValueKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueKey") String resultValueKey,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

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
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType,
                                                          @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Get or create a new result value holding the specified numeric value within the range
     *
     * The resulting result value should be attached to the specified group and must be within 
     * the range of the group
     *
     * @param resultValue the result value within the specified group range to be found/created
     * @param scaleKey the with associated with this value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return the result value group information
     * @throws InvalidParameterException invalid scaleKey or scaleKey is not a range
     * @throws MissingParameterException missing resultValuesGroupInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValueInfo getCreateResultValueForScale(@WebParam(name = "resultValue") String resultValue,
                                                        @WebParam(name = "scaleKey") String scaleKey,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves result scale by an identifier.
     *
     * @param resultScaleKey identifiers for result scale to be retrieved
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return details of the result scale for the id
     * @throws DoesNotExistException  resultValuesGroupKey not found
     * @throws InvalidParameterException invalid resultValuesGroupKey
     * @throws MissingParameterException invalid resultValuesGroupKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves result scales by a list of identifiers.
     *
     * @param resultScaleKeys  identifiers for result scale
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return result scale list
     * @throws DoesNotExistException resultScale not found
     * @throws InvalidParameterException invalid resultScaleKeys
     * @throws MissingParameterException invalid resultScaleKeys
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultScaleInfo> getResultScalesByKeys(@WebParam(name = "resultScaleKeys") List<String> resultScaleKeys,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of result group identifiers for a specified
     * result scale type.
     *
     * @param resultScaleTypeKey identifier for the result group type
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return list of result group identifiers
     * @throws DoesNotExistException resultScaleTypeKey not found
     * @throws InvalidParameterException invalid resultScaleTypeKey
     * @throws MissingParameterException missing resultScaleTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure     
     */
    public List<String> getResultScaleKeysByType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new result scale.
     *
     * @param resultScaleTypeKey type key of the result scale
     * @param resultScaleInfo information about the result scale 
     *        being created
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return create result scale information
     * @throws AlreadyExistsException result scale already exists
     * @throws DataValidationErrorException one or more values invalid for 
     *         this operation
     * @throws InvalidParameterException invalid resultScaleInfo
     * @throws MissingParameterException missing resultScaleInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultScaleInfo createResultScale(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey,
                                             @WebParam(name = "resultGroupInfo") ResultScaleInfo resultScaleInfo,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Updates an existing result scale.
     *
     * @param resultScaleKey identifier of the result scale to update
     * @param resultGroupInfo updated information about the result scale
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return updated result scale information
     * @throws DataValidationErrorException one or more values invalid for 
     *                                      this operation
     * @throws DoesNotExistException resultScaleKey not found
     * @throws InvalidParameterExceptioninvalid resultScaleKey or 
     *                                          resultScaleInfo
     * @throws MissingParameterException missing resultScaleKey or
     *                                   resultScaleInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of 
     *                                  date version
     */
    public ResultScaleInfo updateResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                             @WebParam(name = "resultScaleInfo") ResultScaleInfo resultScaleInfo,
                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Deletes an existing result scale.
     *
     * @param resultScaleKey identifier of the result scale to update
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation     
     * @return status of the operation
     * @throws DoesNotExistException resultScaleKey not found
     * @throws DependentObjectsExistException if a group or value exists for this scale
     * @throws InvalidParameterException invalid resultScaleKey
     * @throws MissingParameterException missing resultScaleKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a result scale. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object. 
     *
     * @param validationType Identifier of the extent of validation
     * @param gradeScaleInfo Result scale to be validated
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return
     * @throws DoesNotExistException resultScaleInfo does not exist
     * @throws InvalidParameterException validationType or 
     *                                   resultScaleInfo does not exist
     * @throws MissingParameterException missing validationType, resultScaleInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultScale(@WebParam(name = "validationType") String validationType,
                                                          @WebParam(name = "gradeScaleInfo") ResultScaleInfo gradeScaleInfo,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Retrieves result values by result scale key.
     *
     * @param resultScaleKey key to the scale 
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
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a result value by result scale key and value
     *
     * @param resultScaleKey key to the scale
     * @param value the specific value
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the matching Result Value
     * @throws DoesNotExistException resultScaleKey is not found
     * @throws InvalidParameterException invalid resultScaleKey
     * @throws MissingParameterException null resultScaleKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ResultValueInfo getResultValueForScaleAndValue(@WebParam(name = "resultScaleKey") String resultScaleKey,
                                                          @WebParam(name = "value") String value,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves result values for the list of Result values groups.
     *
     * No values are selected for groups that are RANGES.
     *
     * @param resultValuesGroupKeys list of result value groups for which to return values
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of unique result values for that set of groups
     * @throws DoesNotExistException resultScaleKey is not found
     * @throws InvalidParameterException invalid resultScaleKey
     * @throws MissingParameterException null resultScaleKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(@WebParam(name = "resultValuesGroupKeys") List<String> resultValuesGroupKeys,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of existing result values groups that have a scale
     * of the specified type.
     *
     * @param resultScaleTypeKey identifier for result scale type key
     * @param contextInfo Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return details of the results for these keys
     * @throws DoesNotExistException resultValue not found
     * @throws InvalidParameterException invalid resultValueKey
     * @throws MissingParameterException invalid resultValueKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey,
                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}