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
package org.kuali.student.enrollment.classI.lrr.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.classI.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.classI.lrr.dto.SourceInfo;
import org.kuali.student.enrollment.classI.lrr.dto.SourceTypeInfo;

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


/**
 *
 * @Author KSContractMojo
 * @Author sambit
 * @Since Wed May 04 15:34:10 PDT 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/Learning+Result+Record+Service">LearningResultRecordService</>
 *
 */
@WebService(name = "LearningResultRecordService", targetNamespace = "http://student.kuali.org/wsdl/lrr") // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LearningResultRecordService { 
    /** 
     * Retrieves the list of learning result record style source types known by this service
     * @return list of learning result record style source types
     * @throws OperationFailedException unable to complete request
	 */
    public List<SourceTypeInfo> getSourceTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning result record style source type
     * @param sourceTypeKey learning result record style source type identifier
     * @return learning result record style source type information
     * @throws DoesNotExistException specified source type not found
     * @throws InvalidParameterException invalid sourceTypeKey
     * @throws MissingParameterException sourceTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public SourceTypeInfo getSourceType(@WebParam(name="sourceTypeKey")String sourceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result record style source types allowed for an learning result record
     * @return list of learning result record style source types
     * @throws OperationFailedException unable to complete request
	 */
    public List<SourceTypeInfo> getAllowedSourceTypesForLearningResultRecord() throws OperationFailedException;

    /** 
     * Retrieves an learning result record by its identifier
     * @param learningResultRecordId learning result record identifier
     * @return learning result record information
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId
     * @throws MissingParameterException learningResultRecordId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LearningResultRecordInfo getLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning result records for a Person
     * @param personId person identifier
     * @return list of learning result record information
     * @throws DoesNotExistException person not found
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException personId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LearningResultRecordInfo> getLearningResultRecordsForPerson(@WebParam(name="personId")String personId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning result records for a Lui
     * @param luiId lui identifier
     * @return list of learning result record information
     * @throws DoesNotExistException lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException luiId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LearningResultRecordInfo> getLearningResultRecordsForLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of sources of an learning result record.
     * @param learningResultRecordId learning result record identifier
     * @return list of source records
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId
     * @throws MissingParameterException learningResultRecordId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<SourceInfo> getSourcesForLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates an learning result record. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning result record (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning result record can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param learningResultRecordInfo learning result record information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, learningResultRecordInfo
     * @throws MissingParameterException missing validationTypeKey, learningResultRecordInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLearningResultRecord(@WebParam(name="validationType")String validationType, @WebParam(name="learningResultRecordInfo")LearningResultRecordInfo learningResultRecordInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates an learning result record.
     * @param personId identifier of the person the learning result record is for
     * @param lprType identifier of the type of relationship between the person and learning unit instance
     * @param luiId identifier of the learning unit instance
     * @param resultUsageType identifier of the type of usage for the result
     * @param resultComponentId identifier of the domain of result values the person could have achieved in the learning unit instance for this usage type
     * @param resultValueId identifier of the result earned
     * @param sourceInfo the source of this learning result record
     * @param learningResultRecordInfo information about the learning result record
     * @return information about the newly created learning result record
     * @throws AlreadyExistsException Learning result record already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LearningResultRecordInfo createLearningResultRecord(@WebParam(name="personId")String personId, @WebParam(name="lprType")String lprType, @WebParam(name="luiId")String luiId, @WebParam(name="resultUsageType")String resultUsageType, @WebParam(name="resultComponentId")String resultComponentId, @WebParam(name="resultValueId")String resultValueId, @WebParam(name="sourceInfo")SourceInfo sourceInfo, @WebParam(name="learningResultRecordInfo")LearningResultRecordInfo learningResultRecordInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an learning result record
     * @param learningResultRecordId identifier of the learning result record to be updated
     * @param learningResultRecordInfo information about the learningResultRecord to be updated
     * @return the updated learning result record information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LearningResultRecordInfo updateLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name="learningResultRecordInfo")LearningResultRecordInfo learningResultRecordInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an learning result record
     * @param learningResultRecordId identifier of the learning result record to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId
     * @throws MissingParameterException missing learningResultRecordId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds a source to an learning result record. The service is expected to enforce that the Id in the source record agrees with the LearningResultRecordId parameter.
     * @param learningResultRecordId learning result record identifier
     * @param sourceInfo source information
     * @return status
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId or sourceInfo
     * @throws MissingParameterException learningResultRecordId or sourceInfo not specified
     * @throws OperationFailedException unable to complete request
	 */
    public StatusInfo addSourceToLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name="sourceInfo")SourceInfo sourceInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * removes a source to an learning result record. An Learning Result Record always must have at least one Source therefore the last source cannot be removed.
     * @param learningResultRecordId learning result record identifier
     * @param sourceId source information identifier
     * @return status
     * @throws DoesNotExistException learning result record not found
     * @throws InvalidParameterException invalid learningResultRecordId or sourceId
     * @throws MissingParameterException learningResultRecordId or sourceId not specified
     * @throws OperationFailedException unable to complete request
     * @throws UnsupportedOperationException Last source may not be removed
	 */
    public StatusInfo removeSourceFromLearningResultRecord(@WebParam(name="learningResultRecordId")String learningResultRecordId, @WebParam(name="sourceId")String sourceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException;

    /** 
     * Updates a source to an learning result record.
     * @param sourceId source identifier
     * @param sourceInfo source information
     * @return status
     * @throws DoesNotExistException source not found
     * @throws InvalidParameterException invalid sourceId or sourceInfo
     * @throws MissingParameterException sourceId or sourceInfo not specified
     * @throws OperationFailedException unable to complete request
	 */
    public StatusInfo updateSourceOfLearningResultRecord(@WebParam(name="sourceId")String sourceId, @WebParam(name="sourceInfo")SourceInfo sourceInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}