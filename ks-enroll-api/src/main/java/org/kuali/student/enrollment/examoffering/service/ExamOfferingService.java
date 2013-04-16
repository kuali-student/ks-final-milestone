/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 2/15/13
 */
package org.kuali.student.enrollment.examoffering.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * This class represents a service to manage an offering of an exam.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
@WebService(name = "ExamOfferingService", serviceName = "ExamOfferingService", portName = "ExamOfferingService", targetNamespace = ExamOfferingServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ExamOfferingService {

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
    public List<ExamOfferingInfo> getExamOfferingsByIds (@WebParam(name = "examOfferingIds") List<String> examOfferingIds,
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
     * @param termId    Unique key of the term for which the exam offering is being
     *                  validated
     * @param examId    Unique key of the canonical exam for which the exam offering is being
     *                  validated
     * @param examTypeKey           the identifier for the exam type to be validated
     * @param validationTypeKey     the identifier for the validation Type
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
    public List<ValidationResultInfo> validateExamOffering(@WebParam(name = "termId") String termId,
                                                           @WebParam(name = "examId") String examId,
                                                           @WebParam(name = "examTypeKey") String examTypeKey,
                                                           @WebParam(name = "validationTypeKey") String validationTypeKey,
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
     * @param termId    Unique key of the term for which the exam offering is being
     *                  created
     * @param examId    Unique key of the canonical exam for which the exam offering is being
     *                  created
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
    public ExamOfferingInfo createExamOffering(@WebParam(name = "termId") String termId,
                                               @WebParam(name = "examId") String examId,
                                               @WebParam(name = "examTypeKey") String examTypeKey,
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
     * Validates an ExamOfferingRelation.
     *
     * @param formatOfferingId  Unique key of the FormatOffering for which the relation is being validated
     * @param examOfferingId    Unique key of the ExamOffering for which the relation is being validated
     * @param examOfferingTypeKey         the identifier of the ExamOfferingRelation type to be validated
     * @param validationTypeKey                         the identifier for the validation Type
     * @param examOfferingRelationInfo    the ExamOfferingRelation record to be validated
     * @param contextInfo                               information containing the principalId and
     *                                                  locale information about the caller of
     *                                                  service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException        validationTypeKey or examOfferingTypeKey is not found
     * @throws InvalidParameterException    examOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException    validationTypeKey, examOfferingTypeKey, examOfferingRelationInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<ValidationResultInfo> validateExamOfferingRelation(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                                   @WebParam(name = "examOfferingId") String examOfferingId,
                                                                   @WebParam(name = "examOfferingTypeKey") String examOfferingTypeKey,
                                                                   @WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                   @WebParam(name = "examOfferingRelationInfo") ExamOfferingRelationInfo examOfferingRelationInfo,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new ExamOfferingRelation.
     *
     * @param formatOfferingId  Unique key of the FormatOffering for which the relation is being created
     * @param examOfferingId    Unique key of the ExamOffering for which the relation is being created
     * @param examOfferingTypeKey         a unique identifier for the Type of the new ExamOfferingRelation
     * @param examOfferingRelationInfo    the data with which to create the ExamOfferingRelation
     * @param contextInfo                               information containing the principalId and locale
     *                                                  information about the caller of service operation
     * @return the new ExamOfferingRelation created
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException examOfferingTypeKey does not exist or is not supported
     * @throws InvalidParameterException examOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException examOfferingTypeKey, examOfferingRelationInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public ExamInfo createExamOfferingRelation(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                               @WebParam(name = "examOfferingId") String examOfferingId,
                                               @WebParam(name = "examOfferingTypeKey") String examOfferingTypeKey,
                                               @WebParam(name = "examOfferingRelationInfo") ExamOfferingRelationInfo examOfferingRelationInfo,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing ExamOfferingRelation.
     *
     * @param examOfferingRelationId      the identifier for the ExamOfferingRelation to be updated
     * @param examOfferingRelationInfo    the new data for the ExamOffering
     * @param contextInfo   information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated object
     * @throws DataValidationErrorException supplied data is invalid (xml)
     * @throws DoesNotExistException examOfferingRelationId is not found
     * @throws InvalidParameterException examOfferingRelationInfo or contextInfo is not valid
     * @throws MissingParameterException examOfferingRelationId, examOfferingRelationInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt was made to change information designated as read only
     * @throws VersionMismatchException an optimistic locking failure or the action was attempted on an out
     * of data version
     */
    public ExamInfo updateExamOfferingRelation (@WebParam(name = "examOfferingRelationId") String examOfferingRelationId,
                                                @WebParam(name = "examOfferingRelationInfo") ExamOfferingRelationInfo examOfferingRelationInfo,
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
     * Deletes an existing ExamOfferingRelation.
     *
     * @param examOfferingRelationId  the identifier for the ExamOfferingRelation to be deleted
     * @param contextInfo                           information containing the principalId and locale
     *                                              information about the caller of service operation
     * @return the status of the operation
     *
     * @throws DoesNotExistException examId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException examOfferingRelationId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteExamOfferingRelation(@WebParam(name = "examOfferingRelationId") String examOfferingRelationId,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves information about a ExamOfferingRelation relationship object.
     *
     * @param examOfferingRelationId  the id of the relationship.
     * @param contextInfo                           Context information containing the principalId and locale
     *                                              information about the caller of service operation
     * @return a ExamOfferingRelation relationship whose id matches the given id
     * @throws DoesNotExistException if the relationship does not exist
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException examOfferingRelationId or contextInfo are absent
     * (missing or null)
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ExamOfferingRelationInfo getExamOfferingRelation(@WebParam(name = "examOfferingRelationId") String examOfferingRelationId,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ExamOfferingRelations from a list of ids. The returned list may be in any order and
     * if duplicates ids are supplied, a unique set may or may not be returned.
     *
     * @param examOfferingRelationIds the relationship ids.
     * @param contextInfo                           Context information containing the principalId and locale
     *                                              information about the caller of service operation
     * @return a list of ExamOfferingRelations
     * @throws DoesNotExistException a relationshipId in the list was not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException  	relationshipIds, a relationshipId in the relationshipIds, or
     * contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByIds(@WebParam(name = "examOfferingRelationIds") List<String> examOfferingRelationIds,
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ExamOfferingRelation Ids by relation type.
     *
     * @param relationshipTypeKey   an identifier for a ExamOfferingRelation Type
     * @param contextInfo           Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return a list of ExamOfferingRelation identifiers matching relationshipTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException relationshipTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getExamOfferingRelationIdsByType(@WebParam(name = "relationshipTypeKey") List<String> relationshipTypeKey,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ExamOfferingRelations to the given FormatOffering.
     *
     * @param formatOfferingId the identifier for the FormatOffering
     * @param contextInfo  Context information containing the principalId and locale information about the caller of service operation
     * @return the ExamOfferingRelations to the given FormatOffering or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException formatOfferingId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ExamOfferingRelations to the given ExamOffering.
     *
     * @param examOfferingId the identifier for the ExamOffering
     * @param contextInfo  Context information containing the principalId and locale information about the caller of service operation
     * @return the ExamOfferingRelations to the given ExamOffering or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException examOfferingId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByExamOffering(@WebParam(name = "examOfferingId") String examOfferingId,
                                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ExamOfferingRelations Ids that are associated with an ActivityOffering id.
     *
     * @param activityOfferingId the identifier for the ActivityOffering
     * @param contextInfo  Context information containing the principalId and locale information about the caller of service operation
     * @return the Ids of the ExamOfferingRelations for the given ActivityOffering Id or an empty list if none found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException activityOfferingId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getExamOfferingRelationIdsByActivityOfferingId(@WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ExamOfferingRelations that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of ExamOfferingRelation Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForExamOfferingRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ExamOfferingRelations that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of ExamOfferingRelations matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ExamOfferingRelationInfo> searchForExamOfferingRelations (@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
