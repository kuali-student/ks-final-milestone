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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Course Offering is a Class II service supporting the process of offering courses for student registration. 
 * 
 * Courses
 * are offered for a specific term which is associated with a specific Academic
 * Calendar. At the canonical level a course is defined by formats for which the
 * course will be offered. Each format describes the activity types that
 * comprise that format, e.g., lecture and lab. 
 * 
 * The purpose of multiple formats
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
public interface CourseOfferingService {

    /**
     * Retrieve information about a CourseOffering
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of course offerings by id list.
     *
     * @param courseOfferingIds List of unique Ids of CourseOffering
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws DoesNotExistException     courseOfferingId in the list not found
     * @throws InvalidParameterException invalid courseOfferingIds
     * @throws MissingParameterException missing courseOfferingIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOfferings by canonical course id. 
     * This could
     * return multiple offerings in cases of multiple offerings for formats and
     * cross listed
     *
     * @param courseId Unique Id of the Course (canonical)
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOfferings by canonical course id and term. This could
     * return multiple offerings in cases of multiple offerings for formats and
     * cross listed
     *
     * @param courseId Unique Id of the Course (canonical)
     * @param termId   Unique key of the term in which the course is being offered
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering Ids for a given term and if useIncludedTerms is
     * set to 'true' then use included terms also
     *
     * @param termId          Unique key of the term in which the course is being offered
     * @param useIncludedTerm Indicates if the offerings from included term are also to be
     *                        returned
     * @param context         Context information containing the principalId and locale
     *                        information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTerm(@WebParam(name = "termId") String termId, @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering Ids for a given term and subject area.
     * A CourseOffering will have an official and "other" subject areas, this
     * operation will the course offeiring ids with either official or other subject area
     * that match.
     *
     * @param termId      Unique key of the term in which the course is being offered
     * @param subjectArea subject area
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(@WebParam(name = "termId") String termId, @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve Course Offerings for a given term and instructor id
     *
     *
     * @param termId       Unique key of the term in which the course is being offered
     * @param instructorId person id of an instructor
     * @param context      Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException     courseId or termId or instructorId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(@WebParam(name = "termId") String termId, @WebParam(name = "instructorId") String instructorId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering Ids for a given term and unit content owner
     *
     *
     * @param termId      Unique key of the term in which the course is being offered
     * @param unitsContentOwnerId Org Id of the Units content owner
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId, @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve CourseOffering Ids for a given term and unit content owner
     *
     * @param typeKey      Unique key of the term in which the course is being offered
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException     courseId or termId not found
     * @throws InvalidParameterException invalid courseId or termId
     * @throws MissingParameterException missing courseId or termId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    ;

    /**
     * Gets a list  course offering ids given the SOC that the course offering is a part of.
     *
     * @param socId   Unique id for the SOC
     * @param context
     * @return
     * @throws DoesNotExistException   SOC Id doesn't exist
     * @throws InvalidParameterException  Invalid soc id
     * @throws MissingParameterException  Missing SOC id
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException  authorization failure
     */
    public List<String> getCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list fo course offerings that are already published by SOC id
     *
     * @param socId   Unique id for the SOC
     * @param context
     * @return
     * @throws DoesNotExistException  SOC Id doesn't exist
     * @throws InvalidParameterException    Invalid soc id
     * @throws MissingParameterException  Missing SOC id
     * @throws OperationFailedException   unable to complete request
     * @throws PermissionDeniedException  authorization failure
     */
    public List<String> getPublishedCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CourseOffering from a canonical course.
     * 
     * Fields in course offering will be initialized with data from the canonical.
     *
     * @param courseId     Canonical course Id of courseOffering Id that the
     *                     ActivityOffering will belong to
     * @param termId       Unique key of the term in which the course is being offered
     *                     course offering
     * @param context      Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return newly created CourseOfferingInfo
     * @throws AlreadyExistsException       the CourseOffering being created already exists
     * @throws DoesNotExistException        courseId not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing CourseOffering.
     *
     * @param courseOfferingId   Id of CourseOffering to be updated
     * @param courseOfferingInfo Details of updates to the CourseOffering
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException      the CourseOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Updates an existing CourseOffering from its canonical. This should
     * reinitialize and overwrite any changes to the course offering that were
     * made since its creation with the defaults from the canonical course
     *
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        the CourseOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing CourseOffering. Deleting a course offering should
     * also delete all the activity offerings and registrations groups within
     * it. Cross listed course offerings should also be deleted along with
     * passed in courseOfferingId.
     *
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a course offering. Depending on the value of validationType,
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
     * @param courseOfferingInfo the course offering information to be tested.
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, courseOfferingInfo
     * @throws MissingParameterException missing validationTypeKey, courseOfferingInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates / Compares a
     * @param courseOfferingInfo
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     *  Gets an format offering  based on Id.
     *
     * @param formatOfferingId  The activity offering template identifier
     * @param  context
     * @return
     * @throws DoesNotExistException  The activity offering template doesn't exist
     * @throws InvalidParameterException  Invalid formatOfferingId
     * @throws MissingParameterException  Missing formatOfferingId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException
     */
    public FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of format offering by a course offering id they belong to.
     * @param courseOfferingId  Course offering identifier
     * @param context
     * @return
     * @throws DoesNotExistException   The course offering  doesn't exist
     * @throws InvalidParameterException Invalid course offering id
     * @throws MissingParameterException     Missing course offering id
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<FormatOfferingInfo> getFormatOfferingByCourseOfferingId(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates an activity offering template for a course offering
     *
     * @param courseOfferingId  Course offering that the activity offering template belongs to
     * @param formatId
     * @param formatOfferingType  the type key of the activity offering template
     * @param formatOfferingInfo  The activity offering template info object
     * @return
     * @throws DataValidationErrorException
     * @throws DoesNotExistException if courseOfferingId or formatId does not exist for the course in the course offering
     * @throws InvalidParameterException Invalid course offering id
     * @throws MissingParameterException     Missing course offering id, formatOfferingTemplate  or formatOfferingType
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException
     */
    public FormatOfferingInfo createFormatOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatId") String formatId, @WebParam(name = "formatOfferingType") String formatOfferingType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) 
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update an activity offering template.
     *
     * @param formatOfferingId  The  Id formatOffering to be updated
     * @param formatOfferingInfo  The new formatOffering Info
     * @param context
     * @return
     * @throws DataValidationErrorException   One or more values invalid for this operation
     * @throws DoesNotExistException  The formatOfferingId doesn't exist
     * @throws InvalidParameterException Invalid  formatOfferingId or formatOffering
     * @throws MissingParameterException Missing formatOffering or  formatOfferingId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     * @throws VersionMismatchException stale version being updated
     */
    public FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, 
            @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo,
            @WebParam(name = "context") ContextInfo context) 
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;


    /**
        * Validates a format offering. Depending on the value of validationType,
        * this validation could be limited to tests on just the current object and
        * its directly contained sub-objects or expanded to perform all tests
        * related to this object.
        *
        * @param validationType     Identifier of the extent of validation
        * @param formatOfferingInfo the format offering information to be tested.
        * @param context            Context information containing the principalId and locale
        *                           information about the caller of service operation
        * @return the results from performing the validation
        * @throws DoesNotExistException     validationTypeKey not found
        * @throws InvalidParameterException invalid validationTypeKey, formatOfferingInfo
        * @throws MissingParameterException missing validationTypeKey, formatOfferingInfo
        * @throws OperationFailedException  unable to complete request
        */
       public List<ValidationResultInfo> validateFormatOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Deletes an activity offering template
     *
     * @param formatOfferingId  The  Id formatOffering to be deleted
     * @param context
     * @return
     * @throws DoesNotExistException  The formatOfferingId doesn't exist
     * @throws InvalidParameterException  Invalid  formatOfferingId
     * @throws MissingParameterException  Missing  formatOfferingId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

    /**
     * This method returns the TypeInfo for a given activity offering type key.
     *
     * @param activityOfferingTypeKey Key of the type
     * @param context                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @return Information about the Type
     * @throws DoesNotExistException     activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid activityOfferingTypeKey
     * @throws MissingParameterException missing activityOfferingTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid activity offering types.
     *
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getActivityOfferingTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid activity offering types for a given
     * canonical activity type
     *
     * @param activityTypeKey Key of the canonical activity type
     * @param context         Context information containing the principalId and locale
     *                        information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws DoesNotExistException     activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information about an ActivityOffering
     *
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return ActivityOffering associated with the passed in Id
     * @throws DoesNotExistException     seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException missing activityOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of activity offerings by id list.
     *
     * @param activityOfferingIds List of unique Ids of ActivityCourseOffering
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return Activity offering list
     * @throws DoesNotExistException     activityOfferingId in the list not found
     * @throws InvalidParameterException invalid activityOfferingIds
     * @throws MissingParameterException missing activityOfferingIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Activity Offerings by actvity offering template id which don't have
     * registration groups created for them yet.
     *
     * @param courseOfferingId  The Id of the course offering
     * @param context
     * @return
     * @throws DoesNotExistException   The courseOfferingId does not exist
     * @throws InvalidParameterException  Invalid formatOfferingId
     * @throws MissingParameterException  Missing formatOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of ActivityOfferings for a SOC which are not scheduled yet
     *
     * @param socId  Identifier of the SOC
     * @param context
     * @return
     * @throws DoesNotExistException   The socId does not exist
     * @throws InvalidParameterException  Invalid socId
     * @throws MissingParameterException  Missing socId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *Gets a list of ActivityOfferings for a SOC which are not published in the Schedule of Classes yet
    
     * @param socId  Identifier of the SOC
     * @param context
     * @return
     * @throws DoesNotExistException   The socId does not exist
     * @throws InvalidParameterException  Invalid socId
     * @throws MissingParameterException  Missing socId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Activity Offering for a format offering.
     *
     *
     * @param formatOfferingId       courseOffering that the ActivityOffering belongs to
     * @param activityId  the canonical activity this is associated with
     * @param activityOfferingInfo Details of the ActivityOffering to be created
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @return newly created ActivityOffering
     * @throws DoesNotExistException        if the format offering does not exist
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, 
            @WebParam(name = "activityId") String activityId, 
            @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, 
            @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, 
            @WebParam(name = "context") ContextInfo context) 
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Generates activity offerings based on a format offering.
     *
     * @param formatOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException    formatOfferingId invalid
     * @throws MissingParameterException    Missing formatOfferingId in the input
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    List<ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing ActivityOffering.
     *
     * @param activityOfferingId   Id of ActivitOffering to be updated
     * @param activityOfferingInfo Details of updates to the ActivityOffering
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @return updated ActivityOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        the ActivityOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

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
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param validationType       Identifier of the extent of validation
     * @param activityOfferingInfo the activity offering information to be tested.
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateActivityOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * When/for how long does the offering meet in class during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour
     *                           calculation
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return in class contact hours for the term
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * When/for how long does the offering meet out of class during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour
     *                           calculation
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return out of class contact hours for the term
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * When/for how long does the offering meet in total during the term.
     * Calculated by system based on meeting times and term length; may be
     * validated against CLU.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour
     *                           calculation
     * @param context            Context information containing the principalId and locale
     *                           information about the caller of service operation
     * @return total class contact hours for the term
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve  a RegistrationGroup   based on id
     *
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return RegistrationGroup associated with the passed in Id
     * @throws DoesNotExistException     registrationGroupId not found
     * @throws InvalidParameterException invalid registrationGroupId
     * @throws MissingParameterException missing registrationGroupId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RegistrationGroupInfo getRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of registration group by id list.
     *
     * @param registrationGroupIds List of unique Ids of RegistrationGroup
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @return Registration Group list
     * @throws DoesNotExistException     registrationGroupId in the list not found
     * @throws InvalidParameterException invalid registrationGroupIds
     * @throws MissingParameterException missing registrationGroupIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(@WebParam(name = "registrationGroupIds") List<String> registrationGroupIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *  Retrieves a list of RegistrationGroup records that contain all the activity offerings in the input list
     *
     * @param activityOfferingIds  List of activityOffering Identifiers
     * @param context
     * @return
     * @throws DoesNotExistException  One or more of the activityOfferingIds doesn't exist
     * @throws InvalidParameterException One or more invalid activityOfferingIds
     * @throws MissingParameterException Missing activityOfferingIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering for a given canonical format type
     *
     *
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException     courseOfferingId or formatTypeKey not found
     * @throws InvalidParameterException invalid courseOfferingId or formatTypeKey
     * @throws MissingParameterException missing courseOfferingId or formatTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Registration Group
     *
     * @param registrationGroupType      courseOffering Id that the RegistrationGroup will belong to
     * @param registrationGroupInfo Details of the RegistrationGroup to be created
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return newly created registrationGroup
     * @throws DoesNotExistException        courseOfferingId not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "registrationGroupType") String registrationGroupType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Generates all possible registration groups needed (not already in a regGroup) for the given format
     * Offering  if there are no reg group templates for the Activity Offering Template;
     * else generate by constraints in the reg group template.
     *
     * @param formatOfferingId    identifier of the activity offering
     * @param context
     * @return
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing RegistrationGroup.
     *
     * @param registrationGroupId   Id of RegistrationGroup to be updated
     * @param registrationGroupInfo Details of updates to the RegistrationGroup
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return updated RegistrationGroup
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        the RegistrationGroup does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing Registration Group. Removes the relationship to the
     * course offering and activity offering. The activity offerings are not
     * automatically deleted
     *
     * @param registrationGroupId the Id of the RegistrationGroup to be deleted
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @throws DoesNotExistException     the RegistrationGroup does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param validationType        Identifier of the extent of validation
     * @param registrationGroupInfo the registrationGroup information to be tested.
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Gets a registration group template based on the Identifier
     * 
     * @param registrationGroupTemplateId Identifier of the Reg Group template
     * @param context
     * @throws DoesNotExistException  registrationGroupTemplateId doesn't exist
     * @throws InvalidParameterException  Invalid registrationGroupTemplateId
     * @throws MissingParameterException  Missing registrationGroupTemplateId in the input
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a registration Group template based on the info object
     *
     * @param registrationGroupTemplateId  Identifier of the reg group template
     * @param registrationGroupTemplateInfo
     * @param context
     * @return
     * @throws DataValidationErrorException  registrationGroupTemplateInfo not valid
     * @throws DoesNotExistException  registrationGroupTemplateId does not exist
     * @throws InvalidParameterException    Invalid registrationGroupTemplateInfo
     * @throws MissingParameterException    Missing registrationGroupTemplateInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException
     * @throws VersionMismatchException
     */
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "registrationGroupTemplateInfo") RegistrationGroupTemplateInfo registrationGroupTemplateInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes a registration group template based on the identifier
     *
     * @param registrationGroupTemplateId  Identifier of the reg group template
     * @param context
     * @return
     * @throws DoesNotExistException  registrationGroupTemplateId does not exist
     * @throws InvalidParameterException   Invalid registrationGroupTemplateId
     * @throws MissingParameterException  Missing registrationGroupTemplateId in the input
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information about a SeatPoolDefinition
     *
     * @param seatPoolDefinitionId Unique Id of the SeatPoolDefinition
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @return SeatPoolDefinition associated with the passed in Id
     * @throws DoesNotExistException     seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid seatPoolDefinitionId
     * @throws MissingParameterException missing seatPoolDefinitionId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a
     * CourseOffering. This should return SeatPoolDefinitions that apply
     * globally across all RegistrationGroup in the CourseOffering
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a
     * RegistrationGroup.
     *
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException     registrationGroupId not found
     * @throws InvalidParameterException invalid registrationGroupId
     * @throws MissingParameterException missing registrationGroupId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Seat Pool
     *
     * @param seatPoolDefinitionInfo Details of the SeatPoolDefinition to be created
     * @param context                Context information containing the principalId and locale
     *                               information about the caller of service operation
     * @return newly created SeatPoolDefinition
     * @throws AlreadyExistsException       the SeatPoolDefinition being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId   Id of SeatPoolDefinition to be updated
     * @param seatPoolDefinitionInfo Details of updates to the SeatPoolDefinition
     * @param context                Context information containing the principalId and locale
     *                               information about the caller of service operation
     * @return updated SeatPoolDefinition
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        the SeatPoolDefinition does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Validate a seat pool definition
     * 
     * @param validationTypeKey
     * @param seatPoolDefinitionInfo
     * @param context
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws VersionMismatchException
     */
    public List<ValidationResultInfo> validateSeatPoolDefinition(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId the Id of the SeatPoolDefinition to be deleted
     * @param context              Context information containing the principalId and locale
     *                             information about the caller of service operation
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for course offerings using a free form search criteria
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
     * Searches for course offering ids using a free form search criteria
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
     * Searches for activity offerings using a free form search criteria
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
     * Searches for activity offering ids using a free form search criteria
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
     * Searches for registration group ids using a free form search criteria
     * 
     * @param criteria
     * @param context
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegistrationGroupInfo> searchForRegistrationGroups(
            @WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for registration group ids using a free form search criteria.
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
     * Searches for seat pool definition ids using a free form search criteria
     *
     * @param criteria
     * @param context
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
     * Searches for seat pool definition ids using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForSeatpoolDefintionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;
}
