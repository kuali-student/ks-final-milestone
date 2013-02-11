/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.exam.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.exam.dto.FormatOfferingExamOfferingRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab (mezba.mahtab@utoronto.ca)
 * Date: 1/17/13
 * Time: 3:11 PM
 */
@WebService(name = "ExamService", serviceName = "ExamService", portName = "ExamService", targetNamespace = ExamServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ExamService {

    /**
     * Retrieves information about an exam offering.
     *
     * @param examOfferingId    the id of the exam
     * @param contextInfo       Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @return an exam offering whose id matches the given id
     * @throws DoesNotExistException if the exam offering does not exist
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException examOfferingId or contextInfo are absent (missing or null)
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occured
     */
    public ExamOfferingInfo getExamOffering(@WebParam(name = "examOfferingId") String examOfferingId,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
                InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Retrieves the information for the specified list of ExamOfferings (that match the given Ids).
     *
     * @param examOfferingIds   List of identifiers for exam offerings
     * @param contextInfo       Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @return List of exam offerings matching the given ids
     * @throws DoesNotExistException     One or more examOfferingIds not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException examOfferingIds or contextInfo are absent (missing or null)
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExamOfferingInfo> getExamsOfferingsByIds (@WebParam(name = "examOfferingIds") List<String> examOfferingIds,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
                InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Retrieve a list of ExamOffering Ids by Exam Type.
     *
     * @param examTypeKey   the identifier for an exam Type
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of
     *                      service operation
     * @return a list of exam offering identifiers matching
     *         examTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getExamOfferingIdsByType(@WebParam(name = "examTypeKey") String examTypeKey,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Retrieve a list of Exam Offerings by CourseOffering.
     *
     * @param courseOfferingId  the identifier for the course offering.
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of
     *                          service operation
     * @return a list of exam offerings matching the course offering or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingInfo> getExamOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Retrieve a list of ExamOfferings by ActivityOffering.
     *
     * @param activityOfferingId    the identifier for the activity offering.
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of exam offerings matching the activity offering or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingInfo> getExamOfferingsByActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ExamOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of ExamOffering Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForExamOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Searches for ExamOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of ExamOfferings matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingInfo> searchForExamOfferings (@WebParam(name = "criteria") QueryByCriteria criteria,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Validates an ExamOffering. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * exam offering and its directly contained sub-objects or expanded to
     * perform all tests related to this exam offering. If an identifier is
     * present for the exam offering and a record is found for that identifier, the validation
     * checks if the exam can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if the object
     * with the given data can be created.
     *
     * @param validationTypeKey     the identifier for the validation Type
     * @param examTypeKey           the identifier for the exam type to be validated
     * @param examOfferingInfo      the exam offering to be validated
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException        validationTypeKey or examTypeKey is not found
     * @throws InvalidParameterException    examOfferingInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, examTypeKey, examOfferingInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<ValidationResultInfo> validateExamOffering(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                           @WebParam(name = "examTypeKey") String examTypeKey,
                                                           @WebParam(name = "examOfferingInfo") ExamOfferingInfo examOfferingInfo,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Exam Offering.
     *
     * @param examTypeKey       a unique identifier for the Type of the new exam
     * @param examOfferingInfo  the data with which to create the exam
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the new exam offering created
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException examTypeKey does not exist or is not supported
     * @throws InvalidParameterException examOfferingInfo or contextInfo is not valid
     * @throws MissingParameterException examTypeKey, examOfferingInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public ExamOfferingInfo createExamOffering(@WebParam(name = "examTypeKey") String examTypeKey,
                                               @WebParam(name = "examOfferingInfo") ExamOfferingInfo examOfferingInfo,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
                DoesNotExistException,
                InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException,
                ReadOnlyException;

    /**
     * Updates an existing exam offering.
     *
     * @param examOfferingId        the identifier for the exam offering to be updated
     * @param examOfferingInfo      the new data for the exam offering
     * @param contextInfo   information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated object
     * @throws DataValidationErrorException supplied data is invalid (xml)
     * @throws DoesNotExistException examOfferingId is not found
     * @throws InvalidParameterException examOfferingInfo or contextInfo is not valid
     * @throws MissingParameterException examOfferingId, examOfferingInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt was made to change information designated as read only
     * @throws VersionMismatchException an optimistic locking failure or the action was attempted on an out
     * of data version
     */
    public ExamOfferingInfo updateExamOffering (@WebParam(name = "examOfferingId") String examOfferingId,
                                                @WebParam(name = "examOfferingInfo") ExamOfferingInfo examOfferingInfo,
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
     * Deletes an existing exam offering.
     *
     * @param examOfferingId    the identifier for the exam offering to be deleted
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the status of the operation

     * @throws DoesNotExistException examOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examOfferingId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteExamOffering(@WebParam(name = "examOfferingId") String examOfferingId,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
                InvalidParameterException,
                MissingParameterException,
                OperationFailedException,
                PermissionDeniedException;

    /**
     * Change the state of an exam offering.
     *
     * @param examOfferingId    the identifier for the exam offering whose state is to be changed
     * @param stateKey          the state to change to
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the status of the operation

     * @throws DoesNotExistException examOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examOfferingId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeExamOfferingState(@WebParam(name = "examOfferingId") String examOfferingId,
                                              @WebParam(name = "stateKey") String stateKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an Exam. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * exam and its directly contained sub-objects or expanded to
     * perform all tests related to this exam. If an identifier is
     * present for the exam and a record is found for that identifier, the validation
     * checks if the exam can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if the object
     * with the given data can be created.
     *
     * @param validationTypeKey     the identifier for the validation Type
     * @param examTypeKey           the identifier for the exam type to be validated
     * @param examInfo              the exam to be validated
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException        validationTypeKey or examTypeKey is not found
     * @throws InvalidParameterException    examInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, examTypeKey, examInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<ValidationResultInfo> validateExam(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                   @WebParam(name = "examTypeKey") String examTypeKey,
                                                   @WebParam(name = "examInfo") ExamInfo examInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Exam.
     *
     * @param examTypeKey       a unique identifier for the Type of the new exam
     * @param examInfo          the data with which to create the exam
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the new exam created
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException examTypeKey does not exist or is not supported
     * @throws InvalidParameterException examInfo or contextInfo is not valid
     * @throws MissingParameterException examTypeKey, examInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public ExamInfo createExam(@WebParam(name = "examTypeKey") String examTypeKey,
                               @WebParam(name = "examInfo") ExamInfo examInfo,
                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing exam.
     *
     * @param examId        the identifier for the exam to be updated
     * @param examInfo      the new data for the exam
     * @param contextInfo   information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated object
     * @throws DataValidationErrorException supplied data is invalid (xml)
     * @throws DoesNotExistException examId is not found
     * @throws InvalidParameterException examInfo or contextInfo is not valid
     * @throws MissingParameterException examId, examInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt was made to change information designated as read only
     * @throws VersionMismatchException an optimistic locking failure or the action was attempted on an out
     * of data version
     */
    public ExamInfo updateExam (@WebParam(name = "examId") String examId,
                                @WebParam(name = "examInfo") ExamInfo examInfo,
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
     * Deletes an existing exam.
     *
     * @param examId            the identifier for the exam to be deleted
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the status of the operation

     * @throws DoesNotExistException examId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteExam(@WebParam(name = "examId") String examId,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates an FormatOfferingExamOfferingRelation.
     *
     * @param validationTypeKey                         the identifier for the validation Type
     * @param formatOfferingExamOfferingTypeKey         the identifier of the FormatOfferingExamOfferingRelation type to be validated
     * @param formatOfferingExamOfferingRelationInfo    the FormatOfferingExamOfferingRelation record to be validated
     * @param contextInfo                               information containing the principalId and
     *                                                  locale information about the caller of
     *                                                  service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException        validationTypeKey or formatOfferingExamOfferingTypeKey is not found
     * @throws InvalidParameterException    formatOfferingExamOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, formatOfferingExamOfferingTypeKey, formatOfferingExamOfferingRelationInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<ValidationResultInfo> validateFormatOfferingExamOffering(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                         @WebParam(name = "formatOfferingExamOfferingTypeKey") String formatOfferingExamOfferingTypeKey,
                                                                         @WebParam(name = "formatOfferingExamOfferingRelationInfo") FormatOfferingExamOfferingRelationInfo formatOfferingExamOfferingRelationInfo,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new FormatOfferingExamOfferingRelation.
     *
     * @param formatOfferingExamOfferingTypeKey         a unique identifier for the Type of the new FormatOfferingExamOfferingRelation
     * @param formatOfferingExamOfferingRelationInfo    the data with which to create the FormatOfferingExamOfferingRelation
     * @param contextInfo                               information containing the principalId and locale
     *                                                  information about the caller of service operation
     * @return the new FormatOfferingExamOfferingRelation created
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException formatOfferingExamOfferingTypeKey does not exist or is not supported
     * @throws InvalidParameterException formatOfferingExamOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException formatOfferingExamOfferingTypeKey, formatOfferingExamOfferingRelationInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public ExamInfo createFormatOfferingExamOffering(@WebParam(name = "formatOfferingExamOfferingTypeKey") String formatOfferingExamOfferingTypeKey,
                                                     @WebParam(name = "formatOfferingExamOfferingRelationInfo") FormatOfferingExamOfferingRelationInfo formatOfferingExamOfferingRelationInfo,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing FormatOfferingExamOfferingRelation.
     *
     * @param formatOfferingExamOfferingRelationId      the identifier for the FormatOfferingExamOfferingRelation to be updated
     * @param formatOfferingExamOfferingRelationInfo    the new data for the FormatOfferingExamOffering
     * @param contextInfo   information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated object
     * @throws DataValidationErrorException supplied data is invalid (xml)
     * @throws DoesNotExistException formatOfferingExamOfferingRelationId is not found
     * @throws InvalidParameterException formatOfferingExamOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException formatOfferingExamOfferingRelationId, formatOfferingExamOfferingRelationInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt was made to change information designated as read only
     * @throws VersionMismatchException an optimistic locking failure or the action was attempted on an out
     * of data version
     */
    public ExamInfo updateFormatOfferingExamOfferingRelation (@WebParam(name = "formatOfferingExamOfferingRelationId") String formatOfferingExamOfferingRelationId,
                                                              @WebParam(name = "formatOfferingExamOfferingRelationInfo") FormatOfferingExamOfferingRelationInfo formatOfferingExamOfferingRelationInfo,
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
     * Deletes an existing FormatOfferingExamOfferingRelation.
     *
     * @param formatOfferingExamOfferingRelationId  the identifier for the FormatOfferingExamOfferingRelation to be deleted
     * @param contextInfo                           information containing the principalId and locale
     *                                              information about the caller of service operation
     * @return the status of the operation
     *
     * @throws DoesNotExistException examId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException formatOfferingExamOfferingRelationId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteFormatOfferingExamOfferingRelation(@WebParam(name = "formatOfferingExamOfferingRelationId") String formatOfferingExamOfferingRelationId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
