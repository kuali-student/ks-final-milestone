/**
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.batchjobresults.service;

import java.util.Date;
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


import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import org.kuali.student.enrollment.batchjobresults.dto.BatchJobResultInfo;
import org.kuali.student.enrollment.batchjobresults.dto.BatchJobResultItemInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.constants.BatchJobResultServiceConstants;

/**
 * The batch job results service provides a simple mechanism to 
 * store and progress and final results of of a long running batch job.
 * 
 * @version 1.0
 *
 * @author nwright
 */
@WebService(name = "BatchJobResultService", serviceName = "BatchJobResultService", portName = "BatchJobResultService", targetNamespace = BatchJobResultServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface BatchJobResultService {

    /**
     * Retrieve information about a BatchJobResult
     *
     * @param batchJobResultId Unique Id of the BatchJobResult
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * 
     * @throws DoesNotExistException     batchJobResultId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BatchJobResultInfo getBatchJobResult(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of batch job results by id list.
     *
     * @param batchJobResultIds List of unique Ids of BatchJobResult
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultId in the list not found
     * @throws InvalidParameterException invalid batchJobResultIds
     * @throws MissingParameterException missing batchJobResultIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BatchJobResultInfo> getBatchJobResultsByIds(@WebParam(name = "batchJobResultIds") List<String> batchJobResultIds,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve BatchJobResult Ids by type
     *
     * @param typeKey      Unique key type of BatchJobResult
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of BatchJobResult Ids
     * @throws DoesNotExistException     typeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultIdsByType(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve BatchJobResult Ids by type created on or after a date
     *
     * @param typeKey     Unique key type of BatchJobResult
     * @param sinceDateCreated the date on which or after the result was created
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of BatchJobResult Ids
     * @throws DoesNotExistException     typeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultIdsByTypeSinceDateCreated(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "sinceDateCreated") Date sinceDateCreated,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve BatchJobResult Ids by type and the value of a single parameter
     *
     * @param typeKey      Unique key type of BatchJobResult
     * @param parameter    key value pair used to match an existing parameter
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of BatchJobResult Ids
     * @throws DoesNotExistException     typeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultIdsByTypeAndParameter(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "parameter") AttributeInfo parameter,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve BatchJobResult Ids by type and the value of a single global result
     *
     * @param typeKey      Unique key type of BatchJobResult
     * @param globalResult    key value pair used to match an single global result
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of BatchJobResult Ids
     * @throws DoesNotExistException     typeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultIdsByTypeAndGlobalResult(@WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "globalResult") AttributeInfo globalResult,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new BatchJobResult
     * 
     * @param batchJobResultTypeKey     batch job result type key
     * @param batchJobResultInfo    object to be created
     * @param context      Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return newly created BatchJobResultInfo
     * @throws DoesNotExistException        termId or batchJobResultTypeKey not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public BatchJobResultInfo createBatchJobResult(@WebParam(name = "batchJobResultTypeKey") String batchJobResultTypeKey,
            @WebParam(name = "batchJobResultInfo") BatchJobResultInfo batchJobResultInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing BatchJobResult.
     *
     * @param batchJobResultId   Id of BatchJobResult to be updated
     * @param batchJobResultInfo Details of updates to the BatchJobResult
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return updated BatchJobResult
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException      the BatchJobResult does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public BatchJobResultInfo updateBatchJobResult(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "batchJobResultInfo") BatchJobResultInfo batchJobResultInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException;

    /**
     * Update progress information
     *
     * @param batchJobResultId   Id of BatchJobResult to be updated
     * @param itemsProcessed new count of the number of items processed
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return StatusInfo indicates the update worked
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException      the BatchJobResult does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public BatchJobResultInfo updateBatchJobProgress(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "itemsProcessed") Integer itemsProcessed,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing BatchJobResult. 
     *
     * @param batchJobResultId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteBatchJobResult(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a BatchJobResult. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the academic
     * calendar and a record is found for that identifier, the validation checks
     * if the academic calendar can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationType     Identifier of the extent of validation
     * @param batchJobResultInfo the batchJobResult information to be tested.
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, batchJobResultInfo
     * @throws MissingParameterException missing validationTypeKey, batchJobResultInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateBatchJobResult(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "batchJobResultInfo") BatchJobResultInfo batchJobResultInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    ////
    //// items
    ////
    /**
     * Retrieves a list of result items associated with the batch job result id
     *
     * @param batchJobResultId Unique Ids of the rollover results for which the items are to be fetched
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultId in the list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BatchJobResultItemInfo> getBatchJobResultItemsByResultId(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result item ids associated with a particular batch job
     * result type and a source id.
     * 
     * This would allows you to get all the result items associated with a particular course offering 
     * that has been rolled over.
     *
     * @param batchJobResultTypeKey Unique Ids of the rollover results for which the items are to be fetched
     * @param sourceId source id to be matched
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultId in the list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultItemIdsByResultTypeAndSourceId(@WebParam(name = "batchJobResultTypeKey") String batchJobResultTypeKey,
            @WebParam(name = "sourceId") String sourceId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    
     /**
     * Retrieves a list of result item ids associated with a particular batch job
     * result type and a source id.
     * 
     * This would allows you to get all the result items associated with a particular course offering 
     * that has been created via a rolled over.
     *
     * @param batchJobResultTypeKey Unique Ids of the rollover results for which the items are to be fetched
     * @param targetId target id to be matched
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultId in the list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getBatchJobResultItemIdsByResultTypeAndTargetId(@WebParam(name = "batchJobResultTypeKey") String batchJobResultTypeKey,
            @WebParam(name = "targetId") String targetId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;
    /**
     * Retrieve information about a BatchJobResultItem
     *
     * @param batchJobResultItemId Unique Id of the BatchJobResultItem
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultItemId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BatchJobResultItemInfo getBatchJobResultItem(@WebParam(name = "batchJobResultItemId") String batchJobResultItemId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of batch job result items by id list.
     *
     * @param batchJobResultItemIds List of unique Ids of BatchJobResultItem
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     batchJobResultItemId in the list not found
     * @throws InvalidParameterException invalid batchJobResultItemIds
     * @throws MissingParameterException missing batchJobResultItemIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<BatchJobResultItemInfo> getBatchJobResultItemsByIds(@WebParam(name = "batchJobResultItemIds") List<String> batchJobResultItemIds,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new BatchJobResultItem
     * 
     * @param batchJobResultId     Id of the corresponding batch job result
     * @param batchJobResultItemTypeKey     batch job result type key
     * @param batchJobResultItemInfo    object to be created
     * @param context      Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return newly created BatchJobResultItemInfo
     * @throws DoesNotExistException        termId or batchJobResultItemTypeKey not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public BatchJobResultItemInfo createBatchJobResultItem(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "batchJobResultItemTypeKey") String batchJobResultItemTypeKey,
            @WebParam(name = "batchJobResultItemInfo") BatchJobResultItemInfo batchJobResultItemInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Bulk create of  BatchJobResultItems
     * All must be for the same result but the types may vary.
     * 
     * @param batchJobResultId     Id of the corresponding batch job result
     * @param batchJobResultItemInfos    objects to be created
     * @param context      Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return count of number of items created
     * @throws DoesNotExistException        termId or batchJobResultItemTypeKey not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public Integer createBatchJobResultItems(@WebParam(name = "batchJobResultId") String batchJobResultId,
            @WebParam(name = "batchJobResultItemInfos") List<BatchJobResultItemInfo> batchJobResultItemInfos,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing BatchJobResultItem.
     *
     * @param batchJobResultItemId   Id of BatchJobResultItem to be updated
     * @param batchJobResultItemInfo Details of updates to the BatchJobResultItem
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return updated BatchJobResultItem
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException      the BatchJobResultItem does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public BatchJobResultItemInfo updateBatchJobResultItem(@WebParam(name = "batchJobResultItemId") String batchJobResultItemId,
            @WebParam(name = "batchJobResultItemInfo") BatchJobResultItemInfo batchJobResultItemInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing BatchJobResultItem. 
     *
     * @param batchJobResultItemId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteBatchJobResultItem(@WebParam(name = "batchJobResultItemId") String batchJobResultItemId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a BatchJobResultItem. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the academic
     * calendar and a record is found for that identifier, the validation checks
     * if the academic calendar can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationType     Identifier of the extent of validation
     * @param batchJobResultItemInfo the batchJobResultItem information to be tested.
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, batchJobResultItemInfo
     * @throws MissingParameterException missing validationTypeKey, batchJobResultItemInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateBatchJobResultItem(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "batchJobResultItemInfo") BatchJobResultItemInfo batchJobResultItemInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;
}
