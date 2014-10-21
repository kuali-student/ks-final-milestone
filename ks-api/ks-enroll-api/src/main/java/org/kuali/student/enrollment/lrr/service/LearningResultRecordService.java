/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
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
package org.kuali.student.enrollment.lrr.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LrrServiceConstants;


/**
 * 
 * 
 * The Learning Result Record Service supports the management of results earned through a person's participation in a learning unit instance. 
 * 
 * @version: 0.0.7
 * @author Kuali Student Team (Kamal)
 */
@WebService(name = "LearningResultRecordService", targetNamespace = LrrServiceConstants.NAMESPACE) // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LearningResultRecordService { 


    /** 
     * Retrieves a learning result record by its identifier
     * 
     * @param learningResultRecordId learning result record identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return learning result record information
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId
     * @throws MissingParameterException learningResultRecordId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
	 */
    public LearningResultRecordInfo getLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of learningResultRecords from a list of learningResultRecord Ids.
     * The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param learningResultRecordIds a list of learningResultRecordId identifiers.
     * @param contextInfo information containing the principalId and locale information
     *                    about the caller of the service operation.
     * @return LearningResultRecordInfo  a list of learningResultRecords
     * @throws DoesNotExistException     an learningResultRecordId in the list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException learningResultRecordIds, an Id in the learningResultRecordIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LearningResultRecordInfo> getLearningResultRecordsByIds(@WebParam(name = "learningResultRecordIds") List<String> learningResultRecordIds,@WebParam(name = "contextInfo")ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a list of learning result records for a Lui Person Relation
     * @param lprId Lui person relation identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of learning result record information
     * @throws DoesNotExistException person not found
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException personId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(@WebParam(name="lprId")String lprId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    
    /**
     * 
     * Retrieves a list of learning result records based on LPR and the type of LRR
     * 
     * @param lprId
     * @param lrrType
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprAndType(@WebParam(name="lprId")String lprId, @WebParam(name="lrrType")String lrrType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /** 
     * Retrieves a list of learning result records for Lui Person Relation Ids
     * @param lprIds List of Lui person relation identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of learning result record information
     * @throws DoesNotExistException person not found
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException personId not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprIds(@WebParam(name="lprIds")List<String> lprIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning result records by source Id
     * @param sourceIds List of Lui person relation identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of learning result record information
     * @throws DoesNotExistException person not found
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException personId not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(@WebParam(name="sourceIds")List<String> sourceIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /** 
     * Creates an learning result record.
     * @param learningResultRecord information about the learning result record
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return information about the newly created learning result record
     * @throws AlreadyExistsException Learning result record already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LearningResultRecordInfo createLearningResultRecord(@WebParam(name="learningResultRecord")LearningResultRecordInfo learningResultRecord, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an learning result record
     * @param learningResultRecordId identifier of the learning result record to be updated
     * @param learningResultRecordInfo information about the learningResultRecord to be updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the updated learning result record information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LearningResultRecordInfo updateLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name="learningResultRecordInfo")LearningResultRecordInfo learningResultRecordInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Updates the state of an existing LearningResultRecord to another state
     * provided that it is valid to do so.
     *
     * @param learningResultRecordId    identifier of the LearningResultRecord to be
     *                                  updated
     * @param nextStateKey       The State Key into which the identified
     *                           LearningResultRecord will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified LearningResultRecord does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeLearningResultRecordState(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes an learning result record
     * @param learningResultRecordId identifier of the learning result record to delete
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId
     * @throws MissingParameterException missing learningResultRecordId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /** 
     * Validates an learning result record. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning result record (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning result record can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param learningResultRecordInfo learning result record information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, learningResultRecordInfo
     * @throws MissingParameterException missing validationTypeKey, learningResultRecordInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateLearningResultRecord(@WebParam(name="validationType")String validationType, @WebParam(name="learningResultRecordInfo")LearningResultRecordInfo learningResultRecordInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Retrieves a result source for a given identifier
     * 
     * @param resultSourceId result source id
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return learning result record information
     * @throws DoesNotExistException result source not found
     * @throws InvalidParameterException invalid resultSourceId
     * @throws MissingParameterException resultSourceId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public ResultSourceInfo getResultSource(@WebParam(name="resultSourceId")String resultSourceId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /** 
     * Retrieves a list of result sources for a list of Ids
     * 
     * @param resultSourceIds result source Id list
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of result sources
     * @throws DoesNotExistException result source not found
     * @throws InvalidParameterException invalid resultSourceIds
     * @throws MissingParameterException resultSourceIds not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public List<ResultSourceInfo> getResultSourcesByIds(@WebParam(name="resultSourceIds")List <String> resultSourceIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /** 
     * Retrieves a list of result sources by type
     * 
     * @param resultSourceTypeKey result source type key
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of result sources
     * @throws DoesNotExistException result source not found
     * @throws InvalidParameterException invalid resultSourceTypeKey
     * @throws MissingParameterException resultSourceTypeKey not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure 
     */
    public List<ResultSourceInfo> getResultSourcesByType(@WebParam(name="resultSourceTypeKey")String resultSourceTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /** 
     * Create a new result source 
     * @param sourceInfo learning result record identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @throws AlreadyExistsException the result source being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure    
	 */
    public ResultSourceInfo createResultSource(@WebParam(name="sourceInfo")ResultSourceInfo sourceInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a result source 
     * @param resultSourceId source identifier
     * @param resultSourceInfo source information
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return updated result source information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException result source not found 
     * @throws InvalidParameterException invalid resultSourceId or resultSourceInfo
     * @throws MissingParameterException resultSourceId or resultSourceInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException  The action was attempted on an out of date version
     */
    public ResultSourceInfo updateResultSource(@WebParam(name="resultSourceId")String resultSourceId, @WebParam(name="resultSourceInfo")ResultSourceInfo resultSourceInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Updates the state of an existing ResultSource to another state
     * provided that it is valid to do so.
     *
     * @param resultSourceId     identifier of the ResultSource to be
     *                           updated
     * @param nextStateKey       The State Key into which the identified
     *                           ResultSource will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified ResultSource does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeResultSourceState(@WebParam(name = "resultSourceId") String resultSourceId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Delete a result source. The result source should not be deleteable if it is still being referenced by any LRR
     * @param resultSourceId source information identifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status
     * @throws DoesNotExistException the result source does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteResultSource(@WebParam(name="resultSourceId")String resultSourceId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a result source 
     * @param validationType identifier of the extent of validation
     * @param resultSourceInfo result source information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, resultSourceInfo
     * @throws MissingParameterException missing validationTypeKey, resultSourceInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateResultSource(@WebParam(name="validationType")String validationType, @WebParam(name="resultSourceInfo")ResultSourceInfo resultSourceInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}