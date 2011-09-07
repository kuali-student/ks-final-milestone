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
package org.kuali.student.enrollment.courseoffering.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE. Course Offering is a Class II service
 * supporting the process of offering courses for student registration. Courses
 * are offered for a specific term which is associated with a specific Academic
 * Calendar. At the canonical level a course is defined by formats for which the
 * course will be offered. Each format describes the activity types that
 * comprise that format, e.g., lecture and lab. The purpose of multiple formats
 * is to support different formats based on a term type, e.g., Fall versus
 * Spring offering, or to offer multiple formats in the same term, e.g., in
 * person (traditional) versus online. Offering a course is the process of
 * creating specific instances of the course, and for each format to be offered
 * in the selected term, creating a specified number of each activity type that
 * comprises the format, e.g. five (5) lectures and ten (10) labs of Biology
 * 101. Individual activity offerings correspond to events in a scheduling
 * system, each with a meeting pattern. The term 'section' varies by
 * institution, but refers to either the individual activity offering, or it
 * refers to the combination of activity offerings, when the course has more
 * than one activity type, that the student registers in as part of that course.
 * To avoid confusion, this service introduces a new entity to capture the
 * second definition of section. A registration group represents a valid
 * combination of activity offerings, even if the number is one, in which a
 * student registers. The design supports unrestricted matching, e.g., any
 * lecture with any lab, as well as specific matching, e.g., lecture 1 with lab
 * A or B, and lecture 2 with lab C or D. Version: 1.0 (Dev)
 * 
 * @author Kuali Student Team (Kamal)
 */
@WebService(name = "CourseOfferingService", serviceName = "CourseOfferingService", portName = "CourseOfferingService", targetNamespace = CourseOfferingServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseOfferingService extends DataDictionaryService {

    /**
     * Retrieve information about a CourseOffering
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return CourseOffering associated with the passed in Id
     * @throws DoesNotExistException courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of course offerings by id list.
     * 
     * @param courseOfferingIds List of unique Ids of CourseOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Course offering list
     * @throws DoesNotExistException courseOfferingId in the list not found
     * @throws InvalidParameterException invalid courseOfferingIds
     * @throws MissingParameterException missing courseOfferingIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsByIdList(
            @WebParam(name = "courseOfferingIds") List<String> courseOfferingIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOfferings by canonical course id and term. This could
     * return multiple offerings in cases of multiple offerings for formats and
     * cross listed
     * 
     * @param courseId Unique Id of the Course (canonical)
     * @param termKey Unique key of the term in which the course is being offered
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of CourseOfferings
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey
     * @throws MissingParameterException missing courseId or termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(@WebParam(name = "courseId") String courseId,
            @WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering ids for a given term and if useIncludedTerms is
     * set to 'true' then use included terms also
     * 
     * @param termKey Unique key of the term in which the course is being offered
     * @param useIncludedTerm Indicates if the offerings from included term are also to be
     *            returned
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey
     * @throws MissingParameterException missing courseId or termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsForTerm(@WebParam(name = "termKey") String termKey,
            @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering ids for a given term and subject area
     * 
     * @param termKey Unique key of the term in which the course is being offered
     * @param subjectArea subject area
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey
     * @throws MissingParameterException missing courseId or termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(@WebParam(name = "termKey") String termKey,
            @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering ids for a given term and instructor id
     * 
     * @param termKey Unique key of the term in which the course is being offered
     * @param instructorId person id of an instructor
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey or instructorId not found
     * @throws InvalidParameterException invalid courseId or termKey
     * @throws MissingParameterException missing courseId or termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTermAndInstructorId (@WebParam(name = "termKey") String termKey,
            @WebParam(name = "instructorId") String instructorId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering ids for a given term and unit content owner
     * 
     * @param termKey Unique key of the term in which the course is being offered
     * @param unitOwnerId Unit content owner Id
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey
     * @throws MissingParameterException missing courseId or termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(@WebParam(name = "termKey") String termKey,
            @WebParam(name = "unitOwnerId") String unitOwnerId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CourseOffering from a canonical course.
     * 
     * @param courseId Canonical course IdList of courseOffering Ids that the
     *            ActivityOffering will belong to
     * @param termKey Unique key of the term in which the course is being offered
     * @param formatIdList Canonical formats from the canonical course to be used for the
     *            course offering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return newly created CourseOfferingInfo
     * @throws AlreadyExistsException the CourseOffering being created already exists
     * @throws DoesNotExistException courseId not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseOfferingInfo createCourseOfferingFromCanonical(@WebParam(name = "courseId") String courseId,
            @WebParam(name = "termKey") String termKey, @WebParam(name = "formatIdList") List<String> formatIdList,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing CourseOffering.
     * 
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param courseOfferingInfo Details of updates to the CourseOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the CourseOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Updates an existing CourseOffering from its canonical. This should
     * reinitialize and overrwrite any changes to the course offering that were
     * made since its creation with the defaults from the canonical course
     * 
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the CourseOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOfferingFromCanonical(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing CourseOffering. Deleting a course offering should
     * also delete all the activity offerings and registrations groups within
     * it. Cross listed course offerings should also be deleted along with
     * passed in courseOfferingId.
     * 
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a course offering. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained subobjects or expanded to perform all tests
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
     * @param validationType Identifier of the extent of validation
     * @param courseOfferingInfo the course offering information to be tested.
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Retrieves the course offering restrictions.
     * 
     * @param courseOfferingId Unique Id of the Course Offering
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language Translation language e.g en, es, gr
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return a list of StatemenTree structures defining the restrictions
     * @throws DoesNotExistException CourseOffering does not exist
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException invalid courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementTreeViewInfo> getCourseOfferingRestrictions(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, @WebParam(name = "language") String language,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates the Course offering restriction (Statement)
     * 
     * @param courseOfferingId Unique Id of the Course Offering
     * @param restrictionInfo Offering restriction as a statementree structure
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return created restriction as a statementree Structures
     * @throws DoesNotExistException Course Offering does not exist
     * @throws InvalidParameterException invalid courseOfferingId, statementTreeViewInfoList
     * @throws MissingParameterException invalid courseOfferingId, statementTreeViewInfoList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo createCourseOfferingRestriction(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException;

    /**
     * Updates the course offering restriction
     * 
     * @param courseOfferingId Unique Id of the Course Offering
     * @param restrictionInfo Offering restriction as a statementree structure
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated restriction
     * @throws DoesNotExistException Course Offering does not exist
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException invalid courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws CircularReferenceException circular reference in statements
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo updateCourseOfferingRestriction(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, CircularReferenceException, VersionMismatchException;

    /**
     * Delete the course offering restriction
     * 
     * @param courseOfferingId Unique Id of the Course Offering
     * @param restrictionId restriction Id to be deleted
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException invalid courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOfferingRestriction(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "restrictionId") String restrictionId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Validates a course offering restrictions. Depending on the value of
     * validationType, this validation could be limited to tests on just the
     * current object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the academic calendar and a record is found for that identifier, the
     * validation checks if the academic calendar can be shifted to the new
     * values. If a record cannot be found for the identifier, it is assumed
     * that the record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting the
     * validationType to the current object. This is a slightly different
     * pattern from the standard validation as the caller provides the
     * identifier in the create statement instead of the server assigning an
     * identifier.
     * 
     * @param validationType Identifier of the extent of validation
     * @param restrictionInfo the course offering restriction information to be tested.
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCourseOfferingRestriction(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * This method returns the TypeInfo for a given activity offering type key.
     * 
     * @param activityOfferingTypeKey Key of the type
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Information about the Type
     * @throws DoesNotExistException activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid activityOfferingTypeKey
     * @throws MissingParameterException missing activityOfferingTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid activity offering types.
     * 
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getAllActivityOfferingTypes(@WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid activity offering types for a given
     * canonical activity type 
     * 
     * @param activityTypeKey Key of the canonical activity type
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws DoesNotExistException activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getActivityOfferingTypesForActivityType(
            @WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException;

    /**
     * Retrieve information about an ActivityOffering
     * 
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return ActivityOffering associated with the passed in Id
     * @throws DoesNotExistException seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException missing activityOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of activity offerings by id list.
     * 
     * @param activityOfferingIds List of unique Ids of ActivityCourseOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Activity offering list
     * @throws DoesNotExistException activityOfferingId in the list not found
     * @throws InvalidParameterException invalid activityOfferingIds
     * @throws MissingParameterException missing activityOfferingIds
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByIdList(
            @WebParam(name = "activityOfferingIds") List<String> activityOfferingIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivitiesForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Activity Offering
     * 
     * @param courseOfferingIdList List of courseOffering Ids that the ActivityOffering will
     *            belong to
     * @param activityOfferingInfo Details of the ActivityOffering to be created
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return newly created ActivityOffering
     * @throws AlreadyExistsException the ActivityOffering being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo createActivityOffering(
            @WebParam(name = "courseOfferingIdList") List<String> courseOfferingIdList,
            @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Assigns an existing activity offering to a list of course offering. The
     * activity offering should have been created with at least one course
     * offering association
     * 
     * @param activityOfferingId Id of the ActivityOffering to be assigned to course offerings
     * @param courseOfferingIdList List of courseOffering Ids that the ActivityOffering will
     *            belong to
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws AlreadyExistsException the ActivityOffering being created already exists
     * @throws DoesNotExistException activityOfferingId or courseOfferingId not found
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo assignActivityToCourseOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "courseOfferingIdList") List<String> courseOfferingIdList,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing ActivityOffering.
     * 
     * @param activityOfferingId Id of ActivitOffering to be updated
     * @param activityOfferingInfo Details of updates to the ActivityOffering
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated ActivityOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the ActivityOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public ActivityOfferingInfo updateActivityOffering(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing ActivityOffering. Deleting an activity will also
     * delete any relation it has with course offerings. An activity offering
     * cannot be deleted if it is being referenced in a registration group. The
     * registration group needs to be updated to drop the activity offering
     * references before the activity offering can be deleted. The difference in
     * behavior is because of the relationship nature is different between
     * course offering to activity offering and registration group to activity
     * offering. Course offering contains activity offering, so deleting an
     * activity offering can be logically interpreted as removing the containing
     * relationship. Registration group only references existing activity
     * offerings and hence deleting an activity offering will leave the
     * registration group in inconsistent state and updating registration group
     * automatically will lead to unintended side effects.
     * 
     * @param activityOfferingId the Id of the ActivityOffering to be deleted
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a activity offering. Depending on the value of validationType,
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
     * @param validationType Identifier of the extent of validation
     * @param activityOfferingInfo the activity offering information to be tested.
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException    validationTypeKey not found
     * @throws InvalidParameterException
     *             invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException
     *             missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<ValidationResultInfo> validateActivityOffering(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Retrieves the activity offering restrictions.
     * 
     * @param activityOfferingId
     *            Unique Id of the Activity Offering
     * @param nlUsageTypeKey
     *            Natural language usage type key (context)
     * @param language
     *            Translation language e.g en, es, gr
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return a list of StatemenTree structures defining the restrictions
     * @throws DoesNotExistException
     *             ActivityOffering does not exist
     * @throws InvalidParameterException
     *             invalid activityOfferingId
     * @throws MissingParameterException
     *             invalid activityOfferingId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<StatementTreeViewInfo> getActivityOfferingRestrictions(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, @WebParam(name = "language") String language,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates the Activity offering restriction (Statement)
     * 
     * @param activityOfferingId
     *            Unique Id of the Activity Offering
     * @param restrictionInfo
     *            Offering restriction as a statementree structure
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return created restriction as a statementree Structures
     * @throws DoesNotExistException
     *             Activity Offering does not exist
     * @throws InvalidParameterException
     *             invalid activityOfferingId, statementTreeViewInfoList
     * @throws MissingParameterException
     *             invalid activityOfferingId, statementTreeViewInfoList
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     */
    public StatementTreeViewInfo createActivityOfferingRestriction(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException;

    /**
     * Updates the activity offering restriction
     * 
     * @param activityOfferingId
     *            Unique Id of the Activity Offering
     * @param restrictionInfo
     *            Offering restriction as a statementree structure
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated restriction
     * @throws DoesNotExistException
     *             Activity Offering does not exist
     * @throws InvalidParameterException
     *             invalid activityOfferingId
     * @throws MissingParameterException
     *             invalid activityOfferingId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     *             The action was attempted on an out of date version.
     * @throws CircularReferenceException
     *             circular reference in statements
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     */
    public StatementTreeViewInfo updateActivityOfferingRestriction(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, CircularReferenceException, VersionMismatchException;

    /**
     * Delete the activity offering restriction
     * 
     * @param activityOfferingId
     *            Unique Id of the Activity Offering
     * @param restrictionId
     *            restriction Id to be deleted
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid activityOfferingId
     * @throws MissingParameterException
     *             invalid activityOfferingId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteActivityOfferingRestriction(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "restrictionId") String restrictionId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Validates a activity offering restrictions. Depending on the value of
     * validationType, this validation could be limited to tests on just the
     * current object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the academic calendar and a record is found for that identifier, the
     * validation checks if the academic calendar can be shifted to the new
     * values. If a record cannot be found for the identifier, it is assumed
     * that the record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting the
     * validationType to the current object. This is a slightly different
     * pattern from the standard validation as the caller provides the
     * identifier in the create statement instead of the server assigning an
     * identifier.
     * 
     * @param validationType
     *            Identifier of the extent of validation
     * @param restrictionInfo
     *            the activity offering restriction information to be tested.
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException
     *             validationTypeKey not found
     * @throws InvalidParameterException
     *             invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException
     *             missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<ValidationResultInfo> validateActivityOfferingRestriction(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "restrictionInfo") StatementTreeViewInfo restrictionInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * When/for how long does the offering meet in class during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     * 
     * @param activityOfferingId
     *            the Id of the ActivityOffering to be used for contact hour
     *            calculation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return in class contact hours for the term
     * @throws DoesNotExistException
     *             the SeatPoolDefinition does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * When/for how long does the offering meet out of class during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     * 
     * @param activityOfferingId
     *            the Id of the ActivityOffering to be used for contact hour
     *            calculation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return out of class contact hours for the term
     * @throws DoesNotExistException
     *             the SeatPoolDefinition does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public Float calculateOutofClassContactHoursForTerm(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * When/for how long does the offering meet in total during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     * 
     * @param activityOfferingId
     *            the Id of the ActivityOffering to be used for contact hour
     *            calculation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return total class contact hours for the term
     * @throws DoesNotExistException
     *             the SeatPoolDefinition does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates specified number of copies from a given activity offering. The
     * amount of data copied over is specified using the copyContextType.
     * 
     * @param activityOfferingId
     *            ActivityOffering to be copied from
     * @param numberOfCopies
     *            Number of copies to be made
     * @param copyContextTypeKey
     *            copy context specifies the amount of information to be copied
     *            over
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return newly created ActivityOfferings
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<ActivityOfferingInfo> copyActivityOffering(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "numberOfCopies") Integer numberOfCopies,
            @WebParam(name = "copyContextTypeKey") String copyContextTypeKey,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information about a RegistrationGroup
     * 
     * @param registrationGroupId
     *            Unique Id of the RegistrationGroup
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return RegistrationGroup associated with the passed in Id
     * @throws DoesNotExistException
     *             registrationGroupId not found
     * @throws InvalidParameterException
     *             invalid registrationGroupId
     * @throws MissingParameterException
     *             missing registrationGroupId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public RegistrationGroupInfo getRegistrationGroup(
            @WebParam(name = "registrationGroupId") String registrationGroupId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of registration group by id list.
     * 
     * @param registrationGroupIds
     *            List of unique Ids of RegistrationGroup
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Registration Group list
     * @throws DoesNotExistException
     *             registrationGroupId in the list not found
     * @throws InvalidParameterException
     *             invalid registrationGroupIds
     * @throws MissingParameterException
     *             missing registrationGroupIds
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsByIdList(
            @WebParam(name = "registrationGroupIds") List<String> registrationGroupIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering
     * 
     * @param courseOfferingId
     *            Unique Id of the CourseOffering
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException
     *             courseOfferingId not found
     * @throws InvalidParameterException
     *             invalid courseOfferingId
     * @throws MissingParameterException
     *             missing courseOfferingId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering for a given canonical format type
     * 
     * @param courseOfferingId
     *            Unique Id of the CourseOffering
     * @param formatTypeKey
     *            Type of the canonical format
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException
     *             courseOfferingId or formatTypeKey not found
     * @throws InvalidParameterException
     *             invalid courseOfferingId or formatTypeKey
     * @throws MissingParameterException
     *             missing courseOfferingId or formatTypeKey
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "formatTypeKey") String formatTypeKey, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Registration Group
     * 
     * @param courseOfferingId
     *            courseOffering Id that the RegistrationGroup will belong to
     * @param registrationGroupInfo
     *            Details of the RegistrationGroup to be created
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return newly created registrationGroup
     * @throws AlreadyExistsException
     *             the RegistrationGroup being created already exists
     * @throws DoesNotExistException
     *             courseOfferingId not found
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing RegistrationGroup.
     * 
     * @param registrationGroupId
     *            Id of RegistrationGroup to be updated
     * @param registrationGroupInfo
     *            Details of updates to the RegistrationGroup
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated RegistrationGroup
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws DoesNotExistException
     *             the RegistrationGroup does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     *             The action was attempted on an out of date version.
     */
    public RegistrationGroupInfo updateRegistrationGroup(
            @WebParam(name = "registrationGroupId") String registrationGroupId,
            @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing Registration Group. Removes the relationship to the
     * course offering and activity offering. The activity offerings are not
     * automatically deleted
     * 
     * @param registrationGroupId
     *            the Id of the RegistrationGroup to be deleted
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException
     *             the RegistrationGroup does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a registration group. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained subobjects or expanded to perform all tests
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
     * @param validationType
     *            Identifier of the extent of validation
     * @param registrationGroupInfo
     *            the registrationGroup information to be tested.
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException
     *             validationTypeKey not found
     * @throws InvalidParameterException
     *             invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException
     *             missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<ValidationResultInfo> validateRegistrationGroup(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Retrieve information about a SeatPoolDefinition
     * 
     * @param seatPoolDefinitionId
     *            Unique Id of the SeatPoolDefinition
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return SeatPoolDefinition associated with the passed in Id
     * @throws DoesNotExistException
     *             seatPoolDefinitionId not found
     * @throws InvalidParameterException
     *             invalid seatPoolDefinitionId
     * @throws MissingParameterException
     *             missing seatPoolDefinitionId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public SeatPoolDefinitionInfo getSeatPoolDefinition(
            @WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a
     * CourseOffering. This should return SeatPoolDefinitions that apply
     * globally across all RegistrationGroup in the CourseOffering
     * 
     * @param courseOfferingId
     *            Unique Id of the CourseOffering
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException
     *             courseOfferingId not found
     * @throws InvalidParameterException
     *             invalid courseOfferingId
     * @throws MissingParameterException
     *             missing courseOfferingId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a
     * RegistrationGroup.
     * 
     * @param registrationGroupId
     *            Unique Id of the RegistrationGroup
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException
     *             registrationGroupId not found
     * @throws InvalidParameterException
     *             invalid registrationGroupId
     * @throws MissingParameterException
     *             missing registrationGroupId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(
            @WebParam(name = "registrationGroupId") String registrationGroupId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Seat Pool
     * 
     * @param seatPoolDefinitionInfo
     *            Details of the SeatPoolDefinition to be created
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return newly created SeatPoolDefinition
     * @throws AlreadyExistsException
     *             the SeatPoolDefinition being created already exists
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public SeatPoolDefinitionInfo createSeatPoolDefinition(
            @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing SeatPoolDefinition.
     * 
     * @param seatPoolDefinitionId
     *            Id of SeatPoolDefinition to be updated
     * @param seatPoolDefinitionInfo
     *            Details of updates to the SeatPoolDefinition
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return updated SeatPoolDefinition
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws DoesNotExistException
     *             the SeatPoolDefinition does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     *             The action was attempted on an out of date version.
     */
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(
            @WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId,
            @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing SeatPoolDefinition.
     * 
     * @param seatPoolDefinitionId
     *            the Id of the SeatPoolDefinition to be deleted
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException
     *             the SeatPoolDefinition does not exist
     * @throws InvalidParameterException
     *             One or more parameters invalid
     * @throws MissingParameterException
     *             One or more parameters missing
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseOfferingInfo> searchForCourseOfferings(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForCourseOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ActivityOfferingInfo> searchForActivityOfferings(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForActivityOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseRegistrationInfo> searchForRegistrationGroups(
            @WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForRegistrationGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(
            @WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForSeatpoolDefintionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;
}
