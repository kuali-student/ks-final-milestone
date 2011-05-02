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

package org.kuali.student.enrollment.classI.hold.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


import org.kuali.student.enrollment.classI.hold.dto.HoldInfo;
import org.kuali.student.enrollment.classI.hold.dto.HoldCategoryInfo;

import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
/*import org.kuali.student.r2.common.util.constants.HoldServiceConstants;*/

/**
 * Hold Service Description and Assumptions.
 *
 * Version: DRAFT - NOT READY FOR RELEASE.
 *
 * @Author tom
 * @Since Sun May 1 14:22:34 EDT 2011
 */

/*@WebService(name = "HoldService", targetNamespace = HoldServiceConstants.NAMESPACE)*/
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface HoldService extends DataDictionaryService, StateService, TypeService {

    /** 
     * Retrieves the details of a single Hold by a hold Id.
     *
     * @param holdId Unique Id of the Hold to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold requested
     * @throws DoesNotExistException holdId not found
     * @throws InvalidParameterException invalid holdId
     * @throws MissingParameterException missing holdId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo getHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Holds corresponding to a list of
     * hold Ids.
     *
     * @param holdIdList list of unique Ids of the
     *        Hold to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws DoesNotExistException an  holdId in list not found
     * @throws InvalidParameterException invalid holdId in list
     * @throws MissingParameterException missing holdIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByIdList(@WebParam(name = "holdIdList") List<String> holdIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Holds that pertain to the
     * given year.
     *
     * @param year 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid year
     * @throws MissingParameterException missing year
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByCategory(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Holds that pertain to the
     * given year.
     *
     * @param year 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds 
     * @throws InvalidParameterException invalid year
     * @throws MissingParameterException missing year
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Holds mapped to a credential
     * program type and pertains to the given year.
     *
     * @param holdCategoryId a hold Category
     * @param personId Id of a person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldInfo> getHoldsByCategoryForPerson(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Holds mapped to a credential
     * program type and pertains to the given year.
     *
     * @param holdCategoryId a hold Category
     * @param personId Id of a person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Holds
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     public List<HoldInfo> getActiveHoldsByCategoryForPerson(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Hold. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the hold and a record is found for that identifier,
     * the validation checks if the hold can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param holdInfo the hold information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, holdInfo
     * @throws MissingParameterException missing validationTypeKey, holdInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHold(@WebParam(name = "validationType") String validationType, @WebParam(name = "holdInfo") HoldInfo HoldInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Hold.
     *
     * @param holdId the Id of the Hold to be created
     * @param holdInfo Details of the Hold to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold just created
     * @throws AlreadyExistsException the Hold being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldInfo createHold(@WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Hold.
     *
     * @param holdId Id of Hold to be updated
     * @param holdInfo Details of updates to the Hold
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Hold just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public HoldInfo updateHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "holdInfo") HoldInfo holdInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Hold.
     *
     * @param holdId the Id of the Hold to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Hold does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHold(@WebParam(name = "holdId") String holdId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single Hold Category by a hold
     * category Id.
     *
     * @param holdCategoryId Unique Id of the Hold Category to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold Category requested
     * @throws DoesNotExistException holdCategoryId not found
     * @throws InvalidParameterException invalid holdCategoryId
     * @throws MissingParameterException missing holdCategoryId
     * @Throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldCategoryInfo getHoldCategory(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Hold Categories corresponding to a list of
     * hold category keys.
     *
     * @param holdCategoryIdList list of unique Ids of the
     *        Hold Category to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Hold Categories
     * @throws DoesNotExistException a holdCategoryId in list not found
     * @throws InvalidParameterException invalid holdCategoryId in list
     * @throws MissingParameterException missing holdCategoryIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldCategoryInfo> getHoldCategoriesByIdList(@WebParam(name = "holdCategoryIdList") List<String> holdCategoryIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Hold Categories by Type.
     *
     * @param holdCategoryTypeKey a Type of Hold Category to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Hold Categories of the given Type
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getHoldCategoryIdsByType(@WebParam(name = "holdCategoryTypeKey") String holdCategoryTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Hold Categories that pertain to the
     * given organization.
     *
     * @param organizationId a unique Id of an organoization
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Hold Categories 
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HoldCategoryInfo> getHoldCategoriesByOrg(@WebParam(name = "organizationId") String organizationId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a hold category. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the hold category and a record is
     * found for that identifier, the validation checks if the hold
     * category can be shifted to the new values. If a record cannot
     * be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much
     * shallower, typically mimicking those performed by setting the
     * validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param holdCategoryInfo the hold category information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, holdCategoryInfo
     * @throws MissingParameterException missing validationTypeKey, holdCategoryInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHoldCategory(@WebParam(name = "validationType") String validationType, @WebParam(name = "holdCategoryInfo") HoldCategoryInfo holdCategoryInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Hold Category.
     *
     * @param holdCategoryId the id of the Hold Category to be created
     * @param holdCategoryInfo Details of the Hold Category to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Hold Category just created
     * @throws AlreadyExistsException the Hold Category being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HoldCategoryInfo createHoldCategory(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "holdCategoryInfo") HoldCategoryInfo holdCategoryInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Hold Category.
     *
     * @param holdCategoryId Id of Hold Category to be updated
     * @param holdCategoryInfo Details of updates to the Hold
     *        Category being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Hold Category just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Hold Category does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public HoldCategoryInfo updateHoldCategory(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "holdCategoryInfo") HoldCategoryInfo holdCategoryInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Hold Category.
     *
     * @param holdCategoryId the Id of the Hold Category to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Hold Category does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHoldCategory(@WebParam(name = "holdCategoryId") String holdCategoryId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
