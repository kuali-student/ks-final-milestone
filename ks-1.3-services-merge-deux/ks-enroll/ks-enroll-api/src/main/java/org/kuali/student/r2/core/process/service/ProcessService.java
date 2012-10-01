/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.process.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Process Service Description and Assumptions.
 *
 * This service supports the management of Checks.
 *
 */
@WebService(name = "ProcessService", serviceName = "ProcessService", portName = "ProcessService", targetNamespace = ProcessServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProcessService {

    /**
     * Retrieves a ProcessCategory.
     *
     * @param processCategoryId a unique Id of a ProcessCategory
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a ProcessCategory
     * @throws DoesNotExistException processCategoryId not found
     * @throws InvalidParameterException invalid processCategoryId or
     * contextInfo
     * @throws MissingParameterException missing processCategoryId or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProcessCategoryInfo getProcessCategory(@WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ProcessCategorys corresponding to the given list of
     * ProcessCategory Ids.
     *
     * @param processCategoryIds list of ProcessCategories to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of ProcessCategory Ids of the given type
     * @throws DoesNotExistException an processCategoryId in list not found
     * @throws InvalidParameterException invalid processCategoryId or
     * contextInfo
     * @throws MissingParameterException missing processCategoryId or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessCategoryInfo> getProcessCategoriesByIds(@WebParam(name = "processCategoryIds") List<String> processCategoryIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ProcessCategory Ids of the specified type.
     *
     * @param processTypeKey a ProcessCategory type to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of ProcessCategory Ids
     * @throws InvalidParameterException invalid processTypeKey or contextInfo
     * @throws MissingParameterException missing processTypeKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getProcessCategoryIdsByType(@WebParam(name = "processTypeKey") String processTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ProcessCategories in which the given Process is
     * related.
     *
     * @param processKey a Process key
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of ProcessCategories
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessCategoryInfo> getProcessCategoriesForProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ProcessCategories based on the criteria and returns a list
     * of ProcessCategory identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of ProcessCategory Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForProcessCategoryIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ProcessCategories based on the criteria and returns a list
     * of ProcessCategories which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of ProcessCategories
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessCategoryInfo> searchForProcessCategories(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a ProcessCategory. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the
     * ProcessCategory and a record is found for that identifier, the validation
     * checks if the ProcessCategory can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that the record
     * does not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param processCategoryTypeKey the ProcessCategory type key
     * @param processCategoryInfo the ProcessCategory information to be tested
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, processInfo,
     * or contextInfo
     * @throws MissingParameterException missing validationTypeKey, processInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateProcessCategory(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "processCategoryTypeKey") String processCategoryTypeKey,
            @WebParam(name = "processCategoryInfo") ProcessCategoryInfo processCategoryInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new ProcessCategory.
     *
     * @param processCategoryTypeKey the ProcessCategory type key
     * @param processCategoryInfo the details of ProcessCategory to be created
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the ProcessCategory just created
     * @throws DataValidationErrorException one or more values invalid for this
     * operation
     * @throws InvalidParameterException invalid processInfo or contextInfo
     * @throws MissingParameterException missing processInfo or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     */
    public ProcessCategoryInfo createProcessCategory(@WebParam(name = "processCategoryTypeKey") String processCategoryTypeKey,
            @WebParam(name = "processCategoryInfo") ProcessCategoryInfo processCategoryInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing ProcessCategory.
     *
     * @param processCategoryId the Id of ProcessCategory to be updated
     * @param processInfo the details of updates to ProcessCategory being
     * updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of ProcessCategory just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException processCategoryId not found
     * @throws InvalidParameterException invalid processCategoryId, processInfo,
     * or contextInfo
     * @throws MissingParameterException missing processCategoryId, processInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public ProcessCategoryInfo updateProcessCategory(@WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "processInfo") ProcessCategoryInfo processInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing ProcessCategory.
     *
     * @param processCategoryId the Id of the ProcessCategory to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException processCategoryId not found
     * @throws InvalidParameterException invalid processCategoryId or
     * contextInfo
     * @throws MissingParameterException missing processCategoryId or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteProcessCategory(@WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DependentObjectsExistException;

    /**
     * Adds Process to a ProcessCategory.
     *
     * @param processKey a unique identifier for a Process
     * @param processCategoryId a unique identifier for a ProcessCategory
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status
     * @throws AlreadyExistsException processKey already related to
     * processCategoryId
     * @throws DoesNotExistException processKey or processCategoryId not found
     * @throws InvalidParameterException invalid processKey, processCategoryId,
     * or contextInfo
     * @throws MissingParameterException missing processKey, processCategoryId,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addProcessToProcessCategory(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes Process from a ProcessCategory.
     *
     * @param processKey a unique identifier for a Process
     * @param processCategoryId a unique identifier for a ProcessCategory
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status
     * @throws DoesNotExistException processKey or processCategoryId not found
     * or unrelated
     * @throws InvalidParameterException invalid processKey, processCategoryId,
     * or contextInfo
     * @throws MissingParameterException missing processKey, processCategoryId,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeProcessFromProcessCategory(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a Process.
     *
     * @param processKey a unique key of a Process
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a Process
     * @throws DoesNotExistException processKey not found
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProcessInfo getProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Processes corresponding to the given list of Process
     * keys.
     *
     * @param processKeys list of Processess to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Process keys of the given type
     * @throws DoesNotExistException an processKey in list not found
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessInfo> getProcessesByKeys(@WebParam(name = "processKeys") List<String> processKeys,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Process keys of the specified type.
     *
     * @param processTypeKey a Process type to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Process keys
     * @throws InvalidParameterException invalid processTypeKey or contextInfo
     * @throws MissingParameterException missing processTypeKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getProcessKeysByType(@WebParam(name = "processTypeKey") String processTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Processes in which the given ProcessCategory is
     * related.
     *
     * @param processCategoryId a ProcessCategory Id
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Processes
     * @throws InvalidParameterException invalid processCategoryId or
     * contextInfo
     * @throws MissingParameterException missing processCategoryId or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessInfo> getProcessesForProcessCategory(@WebParam(name = "processCategoryId") String processCategoryId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Processs based on the criteria and returns a list of Process
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Process Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForProcessKeys(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Processs based on the criteria and returns a list of
     * Processs which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Processs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ProcessInfo> searchForProcess(@WebParam(name = "criteria") QueryByCriteria criteria,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Process. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the Process and a record
     * is found for that identifier, the validation checks if the Process can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param processTypeKey the identifier for the Process Type
     * @param processInfo the Process information to be tested
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, processInfo,
     * or contextInfo
     * @throws MissingParameterException missing validationTypeKey, processInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateProcess(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "processTypeKey") String processTypeKey,
            @WebParam(name = "processInfo") ProcessInfo processInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Process.
     *
     * @param processKey the key of Process to be created
     * @param processTypeKey the identifier for the Process Type
     * @param processInfo the details of Process to be created
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the Process just created
     * @throws AlreadyExistsException the Process being created already exists
     * @throws DataValidationErrorException one or more values invalid for this
     * operation
     * @throws InvalidParameterException invalid processKey, processInfo, or
     * contextInfo
     * @throws MissingParameterException missing processKey, processInfo, or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     */
    public ProcessInfo createProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "processTypeKey") String processTypeKey,
            @WebParam(name = "processInfo") ProcessInfo processInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Process.
     *
     * @param processKey the key of Process to be updated
     * @param processInfo the details of updates to Process being updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of Process just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException processKey not found
     * @throws InvalidParameterException invalid processKey, processInfo, or
     * contextInfo
     * @throws MissingParameterException missing processKey, processInfo, or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public ProcessInfo updateProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "processInfo") ProcessInfo processInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Process.
     *
     * @param processKey the key of the Process to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DependentObjectsExistException Instructions related to this
     * Process
     * @throws DoesNotExistException processKey not found
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DependentObjectsExistException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a Check.
     *
     * @param checkId a unique Id of a Check
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a Check
     * @throws DoesNotExistException checkId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException checkId or contextInfo is missing or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CheckInfo getCheck(@WebParam(name = "checkId") String checkId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Checks corresponding to the given list of Check Ids.
     *
     * @param checkIds list of Checks to be retrieved
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return a list of Check Ids of the given type
     * @throws DoesNotExistException a checkId in list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException checkId or contextInfo is missing or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CheckInfo> getChecksByIds(@WebParam(name = "checkIds") List<String> checkIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Check Ids of the specified type.
     *
     * @param checkTypeKey a Check type to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Check Ids
     * @throws InvalidParameterException invalid checkTypeKey or contextInfo
     * @throws MissingParameterException missing checkTypeKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCheckIdsByType(@WebParam(name = "checkTypeKey") String checkTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Checks based on the criteria and returns a list of Check
     * identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Check Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForCheckIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Checks based on the criteria and returns a list of Checks
     * which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Checks
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CheckInfo> searchForChecks(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Check. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the Check and a record is
     * found for that identifier, the validation checks if the Check can be
     * shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param checkInfo the Check information to be tested
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, checkInfo,
     * or contextInfo
     * @throws MissingParameterException missing validationTypeKey, checkInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCheck(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "checkTypeKey") String checkTypeKey,
            @WebParam(name = "checkInfo") CheckInfo checkInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Check.
     *
     * @param checkTypeKey an identifier for the Check Type
     * @param checkInfo the details of Check to be created
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the Check just created
     * @throws DataValidationErrorException one or more values invalid for this
     * operation
     * @throws InvalidParameterException checkInfo or contextInfo is not valid
     * @throws MissingParameterException checkTypeKey, checkInfo or contextInfo
     * is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     */
    public CheckInfo createCheck(@WebParam(name = "checkTypeKey") String checkTypeKey,
            @WebParam(name = "checkInfo") CheckInfo checkInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Check.
     *
     * @param checkId the Id of Check to be updated
     * @param checkInfo the details of updates to Check being updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of Check just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException checkId not found
     * @throws InvalidParameterException checkInfo or contextInfo is not valid
     * @throws MissingParameterException checkId, checkInfo, or contextInfo is
     * missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public CheckInfo updateCheck(@WebParam(name = "checkId") String checkId,
            @WebParam(name = "checkInfo") CheckInfo checkInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Check.
     *
     * @param checkId the Id of the Check to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DependentObjectsExistException Instructions related to this Check
     * @throws DoesNotExistException checkIs not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException checkId or contextInfo is missing or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCheck(@WebParam(name = "checkId") String checkId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DependentObjectsExistException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves an Instruction.
     *
     * @param instructionId a unique Id of an Instruction
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return an Instruction
     * @throws DoesNotExistException instructionId not found
     * @throws InvalidParameterException invalid instructionId or contextInfo
     * @throws MissingParameterException missing instructionId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public InstructionInfo getInstruction(@WebParam(name = "instructionId") String instructionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Instructions corresponding to the given list of
     * Instruction Ids.
     *
     * @param instructionIds list of Instructioness to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instruction Ids of the given type
     * @throws DoesNotExistException an instructionId in list not found
     * @throws InvalidParameterException invalid instructionId or contextInfo
     * @throws MissingParameterException missing instructionId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<InstructionInfo> getInstructionsByIds(@WebParam(name = "instructionIds") List<String> instructionIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Instruction Ids of the specified type.
     *
     * @param instructionTypeKey an Instruction type to be retrieved
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instruction Ids
     * @throws InvalidParameterException invalid instructionTypeKey or
     * contextInfo
     * @throws MissingParameterException missing instructionTypeKey or
     * contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getInstructionIdsByType(@WebParam(name = "instructionTypeKey") String instructionTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of all Instructions relating to the given Process. This
     * returns a list of Instructions ordered by position.
     *
     * @param processKey a unique identfiier for a Process
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instructions
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<InstructionInfo> getInstructionsByProcess(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Instructions relating to the given Check.
     *
     * @param checkId a unique identfiier for a Check
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instructions
     * @throws InvalidParameterException contextInfo is not valie
     * @throws MissingParameterException checkId or contextInfo is misisng or
     * null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<InstructionInfo> getInstructionsByCheck(@WebParam(name = "checkId") String checkId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Instructions relating to the given Process and Check.
     *
     * @param processKey a unique identfiier for a Process
     * @param checkId a unique identfiier for a Check
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instructions
     * @throws DoesNotExistException process and check and instruction relation
     * does not exist
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException processKey, checkId, or contextInfo is
     * missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occuured
     */
    public List<InstructionInfo> getInstructionsByProcessAndCheck(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "checkId") String checkId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Instructions based on the criteria and returns a list of
     * Instruction identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Instruction Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForInstructionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Instructions based on the criteria and returns a list of
     * Instructions which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return list of Instructions
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<InstructionInfo> searchForInstructions(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an Instruction. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the Instruction and a
     * record is found for that identifier, the validation instructions if the
     * Instruction can be shifted to the new values. If a record cannot be found
     * for the identifier, it is assumed that the record does not exist and as
     * such, the instructions performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard validation
     * as the caller provides the identifier in the create statement instead of
     * the server assigning an identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param processKey the identifier of the related Process
     * @param checkId the identifier of the related Check
     * @param instructionTypeKey the identifier of an Instruction Type
     * @param instructionInfo the Instruction information to be tested
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey, processKey, or checkId
     * not found
     * @throws InvalidParameterException instructionInfo or contextInfo is
     * invalid
     * @throws MissingParameterException missing validationTypeKey, processKey,
     * checkId, instructionTypeKey instructionInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occured
     */
    public List<ValidationResultInfo> validateInstruction(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "processKey") String processKey,
            @WebParam(name = "checkId") String checkId,
            @WebParam(name = "instructionTypeKey") String instructionTypeKey,
            @WebParam(name = "instructionInfo") InstructionInfo instructionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Instruction.
     *
     * @param processKey the identifier of the related Process
     * @param checkId the identifier of the related Check
     * @param instructionTypeKey the identifier of an Instruction Type
     * @param instructionInfo the details of Instruction to be created
     * @param contextInfo information containing the principalId and locale
     * information about the caller of service operation
     * @return the Instruction just created
     * @throws DataValidationErrorException one or more values invalid for this
     * operation
     * @throws InvalidParameterException instructionInfo or contextInfo is
     * invalid
     * @throws MissingParameterException checkId, instructionInfo, or
     * contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     */
    public InstructionInfo createInstruction(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "checkId") String checkId,
            @WebParam(name = "instructionTypeKey") String instructionTypeKey,
            @WebParam(name = "instructionInfo") InstructionInfo instructionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Instruction.
     *
     * @param instructionId the Id of Instruction to be updated
     * @param instructionInfo the details of updates to Instruction being
     * updated
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of Instruction just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException instructionId not found
     * @throws InvalidParameterException invalid instructionId, instructionInfo,
     * or contextInfo
     * @throws MissingParameterException missing instructionId, instructionInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information designated
     * as read-only
     * @throws VersionMismatchException The action was attempted on an out of
     * date version.
     */
    public InstructionInfo updateInstruction(@WebParam(name = "instructionId") String instructionId,
            @WebParam(name = "instructionInfo") InstructionInfo instructionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Reorder Instructions
     *
     * Assigns sequential numbers to the instructions in the order they are
     * supplied in the list of instruction ids.
     *
     * Any instructions not included in the list are added in an undetermined
     * order after the ones that are listed.
     *
     * @param processKey the process who's instructions are to be reordered
     * @param instructionIds the Ids of the Instruction to be reordered
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return the details of Instruction just updated
     * @throws DataValidationErrorException One or more values invalid for this
     * operation
     * @throws DoesNotExistException instructionId not found
     * @throws InvalidParameterException invalid instructionId, instructionInfo,
     * or contextInfo
     * @throws MissingParameterException missing instructionId, instructionInfo,
     * or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo reorderInstructions(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "instructionIds") List<String> instructionIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes an existing Instruction.
     *
     * @param instructionId the Id of the Instruction to be deleted
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException instructionId not found
     * @throws InvalidParameterException invalid instructionId or contextInfo
     * @throws MissingParameterException missing instructionId or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteInstruction(@WebParam(name = "instructionId") String instructionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of all Instructions ready to be evaluated in order. This
     * method: 1. orders the Instructions for a Process 2. filters out
     * Instructions whose state is not "active" 3. filters out Instructions
     * whose effective dates are not current or the current context date does
     * not apply (?)
     *
     * @param processKey a unique identfiier for a Process
     * @param contextInfo Context information containing the principalId and
     * locale information about the caller of service operation
     * @return a list of Instructions
     * @throws InvalidParameterException invalid processKey or contextInfo
     * @throws MissingParameterException missing processKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<InstructionInfo> getInstructionsForEvaluation(@WebParam(name = "processKey") String processKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
