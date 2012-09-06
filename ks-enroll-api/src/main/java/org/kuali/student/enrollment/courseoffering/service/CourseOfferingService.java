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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Course Offering is a class II service supporting the process of offering
 * courses for student registration.
 * <p/>
 * Courses are offered for a specific term which is associated with a specific
 * Academic Calendar. At the canonical level a course is defined by formats for
 * which the course will be offered. Each format describes the activity types
 * that comprise that format, e.g., lecture and lab.
 * <p/>
 * The purpose of multiple formats is to support different formats based on a
 * term type, e.g., Fall versus Spring offering, or to offer multiple formats in
 * the same term, e.g., in person (traditional) versus online. Offering a course
 * is the process of creating specific instances of the course, and for each
 * format to be offered in the selected term, creating a specified number of
 * each activity type that comprises the format, e.g. five (5) lectures and ten
 * (10) labs of Biology 101.
 * <p/>
 * Individual activity offerings correspond to events in a scheduling system,
 * each with a meeting pattern. The term 'section' varies by institution, but
 * refers to either the individual activity offering, or it refers to the
 * combination of activity offerings, when the course has more than one activity
 * type, that the student registers in as part of that course.
 * <p/>
 * To avoid confusion, this service introduces a new entity to capture the
 * second definition of section. A registration group represents a valid
 * combination of activity offerings, even if the number is one, in which a
 * student registers. The design supports unrestricted matching, e.g., any
 * lecture with any lab, as well as specific matching, e.g., lecture 1 with lab
 * A or B, and lecture 2 with lab C or D.
 * <p/>
 * Version: 1.0 (Dev)
 *
 * @author Kuali Student Team (Kamal)
 */

@WebService(name = "CourseOfferingService", serviceName = "CourseOfferingService", portName = "CourseOfferingService", targetNamespace = CourseOfferingServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface CourseOfferingService
        extends CourseOfferingServiceBusinessLogic {


    /**
     * This method returns the TypeInfo for a given course offering type key.
     *
     * @param courseOfferingTypeKey the unique identifier for the type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return the type requested
     * @throws DoesNotExistException     courseOfferingTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the valid course offering types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid CourseOffering Types
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getCourseOfferingTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the valid instructor (LPR) types for a CourseOffering
     * type.
     *
     * @param courseOfferingTypeKey a unqiue identifier for a CourseOffering
     *                              type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of valid instructor types
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single CourseOffering by a CourseOffering Id.
     *
     * @param courseOfferingId the identifier for the CourseOffering to be
     *                         retrieved
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @throws DoesNotExistException     courseOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single CourseOfferingDisplayInfo by a CourseOffering Id.
     *
     * @param courseOfferingId an identifier for a CourseOffering
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return the CourseOfferingDisplay requested
     * @throws DoesNotExistException     courseOfferingId does not exist
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public CourseOfferingDisplayInfo getCourseOfferingDisplay(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseOfferings from a list of CourseOffering Ids.
     * The returned list may be in any order and if duplicate Ids are supplied,
     * a unique set may or may not be returned.
     *
     * @param courseOfferingIds a list of CourseOffering identifiers
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @throws DoesNotExistException     a courseOfferingId in the list is not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingIds, an Id in the
     *                                   courseOfferingIds, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseOfferingInfo> getCourseOfferingsByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieve a list of CourseOfferingDisplays corresponding to a list of
     * CourseOfferingIds. The returned list may be in any order and if duplicate
     * Ids are supplied, a unique set may or may not be returned.
     *
     * @param courseOfferingIds a list of CourseOffering identifiers
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of CourseOfferingDisplays
     * @throws DoesNotExistException     a courseOfferingId in the list not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingIds, an Id in
     *                                   courseOfferingIds, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieve a list of CourseOffering Ids by CourseOffering Type.
     *
     * @param courseOfferingTypeKey the identfiier for a CourseOffering Type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of CourseOffering identifiers matching
     *         courseOfferingTypeKey or an empty list if none found
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingypeKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieve CourseOfferings by canonical Course Id across all Terms.
     *
     * @param courseId    the identifier for a Course
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings of the given Course or an empty list if
     *         none found
     * @throws DoesNotExistException     courseId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseId orcontextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieve a list of CourseOfferings by canonical Course Id and Term. There
     * may be more than one CourseOffering for a Course in a single Term.
     *
     * @param courseId    the identifier for a Course
     * @param termId      the identifier for a Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings of the given Course offered in the
     *         given Term or an empty list if none found
     * @throws DoesNotExistException     courseId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseId, termId, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occured
     */
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(@WebParam(name = "courseId") String courseId,
                                                                      @WebParam(name = "termId") String termId,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseOffering Ids for CourseOfferings offered in a
     * given term. If useIncludedTerms is true, then include any CourseOfferings
     * offered within child Terms of the given Term.
     *
     * @param termId          the identifier for a Term
     * @param useIncludedTerm true to include CourseOfferings of child Terms of
     *                        the given Term, false to include only
     *                        CourseOfferings offered in the given Term
     * @param contextInfo     information containing the principalId and locale
     *                        information about the caller of service operation
     * @return the list of CourseOffering Ids offered in the given Term or an
     *         empty list if none found
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseOfferingIdsByTerm(@WebParam(name = "termId") String termId,
                                                   @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieve a list of CourseOffering Ids for CourseOfferings of a given
     * subject area offered in the given Term.
     * <p/>
     * A CourseOffering will have an official and "other" subject areas>
     * CourseOfferrings with either official or other subject area that match
     * are returned.
     *
     * @param termId      the identifier for a Term
     * @param subjectArea a subject area
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOffering Ids for CourseOfferings matching the
     *         give subject area and offered in the given Term or an empty list
     *         if none found
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, subjectArea, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(@WebParam(name = "termId") String termId,
                                                                 @WebParam(name = "subjectArea") String subjectArea,
                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseOfferings for a given Term and Instructor.
     *
     * @param termId       the identifier for a Term
     * @param instructorId the Person Id for an instructor
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return a list of CourseOfferings for the given Term and instructor or an
     *         empty list if none found
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, instructorId, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(@WebParam(name = "termId") String termId,
                                                                          @WebParam(name = "instructorId") String instructorId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of CourseOffering Ids for CourseOfferings offered in a
     * given Term by a units content owner.
     *
     * @param termId              the identifier for a Term
     * @param unitsContentOwnerId the Org Id of the units content owner
     * @param contextInfo         information containing the principalId and
     *                            locale information about the caller of service
     *                            operation
     * @return a list of CourseOffering Ids for CourseOfferings offered in the
     *         given Term by the given Org
     * @throws DoesNotExistException     deprecated
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, unisContentOwnerId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId,
                                                                       @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CourseOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOffering Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCourseOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CourseOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CourseOfferingInfo> searchForCourseOfferings(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get the valid options that can be specified to control canonical Course
     * to CourseOffering operations.
     * <p/>
     * This can happen in several situations: (1) when creating a CourseOffering
     * from scratch that copies data from the canonical Course
     * <p/>
     * (2) when a Course is rolled over and the "use canonical" option is
     * specified in a rollover
     * <p/>
     * (3) when a CourseOffering is explicitly asked to be updated based on the
     * canonical Course
     * <p/>
     * (4) when a course offering is explicitly asked to be validated against
     * the canonical Course
     * <p/>
     * These may identify fields to be copied or not copied or special checks or
     * comparisons to be made, such as comparing that the credits of the course
     * are consistent with the specified classroom hours.
     * <p/>
     * TODO: The exact types that can be specified here have not yet been
     * defined
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of option keys used to to indicate the options to be used
     *         when copying data
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Get the valid rollover option keys.
     * <p/>
     * This is the list of option keys supported by the rollover operation. Keys
     * released with kuali student can be found here: https://wiki.kuali.org/display/STUDENT/Course+Offering+Set+Types+and+States#CourseOfferingSetTypesandStates-RolloverOptionKeys
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of option keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getValidRolloverOptionKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Validates a CourseOffering. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * CourseOffering and its directly contained sub-objects or expanded to
     * perform all tests related to this CourseOffering. If an identifier is
     * present for the CourseOffering (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation checks if the
     * CourseOffering can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if the object
     * with the given data can be created.
     *
     * @param validationTypeKey     the identifier for the validation Type
     * @param courseOfferingTypeKey missing
     * @param courseOfferingInfo    the CourseOffering ti be validated
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or courseOfferingTypeKey
     *                                   is not found
     * @throws InvalidParameterException courseOfferingInfo or contextInfo is
     *                                   not valid
     * @throws MissingParameterException validationTypeKey, courseOfferingTypeKey,
     *                                   courseOfferingInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *                                   (missing)
     */
    public List<ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType,
                                                             @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Creates a new course offering from a canonical course.
     * <p/>
     * Fields in course offering will be initialized with data from the
     * canonical.
     *
     * @param courseId   Canonical course Id of courseOffering Id that the
     *                   ActivityOffering will belong to
     * @param termId     Unique key of the term in which the course is being
     *                   offered course offering
     * @param optionKeys options to use when copying data from the canonical
     * @param context    Context information containing the principalId and
     *                   locale information about the caller of service
     *                   operation
     * @return newly created CourseOfferingInfo
     * @throws DoesNotExistException        courseId not found
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId,
                                                   @WebParam(name = "termId") String termId,
                                                   @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey,
                                                   @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo,
                                                   @WebParam(name = "optionKeys") List<String> optionKeys,
                                                   @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException;


    /**
     * Creates a new course offering based on the source course offering.
     * <p/>
     * Fields in course offering will be initialized with data from the source
     * course offering. .
     *
     * @param sourceCourseOfferingId The id of the course offering to be rolled
     *                               over.
     * @param targetTermId           Unique key of the term in which the course
     *                               is rolled over into
     * @param optionKeys             keys that control optional processing
     * @param context                Context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return newly created CourseOfferingInfo
     * @throws DoesNotExistException        sourceCoId not found
     * @throws AlreadyExistsException       if the course offering already
     *                                      exists in the target term and skip
     *                                      if already exists option is
     *                                      specified
     * @throws DataValidationErrorException data in system is not valid or not
     *                                      valid for an option key specified
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    @Override
    public SocRolloverResultItemInfo rolloverCourseOffering(@WebParam(name = "sourceCourseOfferingId") String sourceCourseOfferingId, @WebParam(name = "targetTermId") String targetTermId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing CourseOffering.
     *
     * @param courseOfferingId   Id of CourseOffering to be updated
     * @param courseOfferingInfo Details of updates to the CourseOffering
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        the CourseOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Updates the state of an existing CourseOffering into another state
     * provided that it is valid to do so.
     *
     * @param courseOfferingId Id of the CourseOffering to be updated.
     * @param nextStateKey     The State Key into which the identified
     *                         courseOffering will be placed if the operation
     *                         succeeds.
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified CourseOffering does not
     *                                   exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateCourseOfferingState(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing CourseOffering from its canonical. This should
     * reinitialize and overwrite any changes to the course offering that were
     * made since its creation with the defaults from the canonical course
     *
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param optionKeys       options to use when copying data from the
     *                         canonical
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        the CourseOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                @WebParam(name = "optionKeys") List<String> optionKeys,
                                                                @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing CourseOffering.
     *
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException          the SeatPoolDefinition does not
     *                                        exist
     * @throws InvalidParameterException      One or more parameters invalid
     * @throws MissingParameterException      One or more parameters missing
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     * @throws DependentObjectsExistException When one or more Format Offering,
     *                                        Activity Offering, Registration
     *                                        Group or Seat Pool Definition
     *                                        exist for course offering.
     */
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

    /**
     * Deletes an existing CourseOffering cascaded style. Deleting a course
     * offering cascaded style would also delete all the format offering,
     * activity offerings, registration groups and seat pool definitions within
     * it. Cross listed course offerings should also be deleted along with
     * passed in courseOfferingId.
     *
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOfferingCascaded(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates / Compares a course offering against it's canonical course.
     *
     * @param courseOfferingInfo the course offering information to be tested.
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     if the course associated with the
     *                                   course offering does not exist
     * @throws InvalidParameterException if a parameter is invalid
     * @throws MissingParameterException if a parameter is missing
     * @throws OperationFailedException  unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo,
                                                                          @WebParam(name = "optionKeys") List<String> optionKeys,
                                                                          @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Gets an format offering  based on Id.
     *
     * @param formatOfferingId The  Format Offering  identifier
     * @param context
     * @return
     * @throws DoesNotExistException     The Format Offering doesn't exist
     * @throws InvalidParameterException Invalid formatOfferingId
     * @throws MissingParameterException Missing formatOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException
     */
    public FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of format offering by a course offering id they belong to.
     *
     * @param courseOfferingId Course offering identifier
     * @param context
     * @return
     * @throws DoesNotExistException     The course offering  doesn't exist
     * @throws InvalidParameterException Invalid course offering id
     * @throws MissingParameterException Missing course offering id
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates an  Format Offering  for a course offering
     *
     * @param courseOfferingId   Course offering that the  Format Offering
     *                           belongs to
     * @param formatId
     * @param formatOfferingType the type key of the  Format Offering  template
     * @param formatOfferingInfo The Format Offering  info object
     * @return
     * @throws DataValidationErrorException
     * @throws DoesNotExistException        if courseOfferingId or formatId does
     *                                      not exist for the course in the
     *                                      course offering
     * @throws InvalidParameterException    Invalid course offering id
     * @throws MissingParameterException    Missing course offering id,
     *                                      formatOfferingTemplate  or
     *                                      formatOfferingType
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException
     */
    public FormatOfferingInfo createFormatOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatId") String formatId, @WebParam(name = "formatOfferingType") String formatOfferingType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Update a  Format Offering
     *
     * @param formatOfferingId   The  Id formatOffering to be updated
     * @param formatOfferingInfo The new formatOffering Info
     * @param context
     * @return
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        The formatOfferingId doesn't exist
     * @throws InvalidParameterException    Invalid  formatOfferingId or
     *                                      formatOffering
     * @throws MissingParameterException    Missing formatOffering or
     *                                      formatOfferingId
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     stale version being updated
     */
    public FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;


    /**
     * Updates the state of an existing FormatOffering to another state provided
     * that it is valid to do so.
     *
     * @param formatOfferingId Id of the FormatOffering to be updated.
     * @param nextStateKey     The State Key into which the identified
     *                         FormatOffering will be placed if the operation
     *                         succeeds.
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified FormatOffering does not
     *                                   exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateFormatOfferingState(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a format offering. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object.
     *
     * @param validationType     Identifier of the extent of validation
     * @param formatOfferingInfo the format offering information to be tested.
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, formatOfferingInfo
     * @throws MissingParameterException missing validationTypeKey, formatOfferingInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateFormatOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Deletes an  Format Offering
     *
     * @param formatOfferingId The  Id formatOffering to be deleted
     * @param context
     * @return
     * @throws DoesNotExistException          The formatOfferingId doesn't
     *                                        exist
     * @throws InvalidParameterException      Invalid  formatOfferingId
     * @throws MissingParameterException      Missing  formatOfferingId
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     * @throws DependentObjectsExistException When one or more Activity
     *                                        Offering, Registration Group or
     *                                        Seat Pool Definition exist for the
     *                                        format offering.
     */
    public StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;


    /**
     * Deletes an  Format Offering with dependent Activity Offering and
     * Registration group
     *
     * @param formatOfferingId The  Id formatOffering to be deleted
     * @param context
     * @return
     * @throws DoesNotExistException     The formatOfferingId doesn't exist
     * @throws InvalidParameterException Invalid  formatOfferingId
     * @throws MissingParameterException Missing  formatOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    public StatusInfo deleteFormatOfferingCascaded(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the TypeInfo for a given activity offering type key.
     *
     * @param activityOfferingTypeKey Key of the type
     * @param context                 Context information containing the
     *                                principalId and locale information about
     *                                the caller of service operation
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
     * @param context         Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return a list of valid activity offering Types
     * @throws DoesNotExistException     activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid instructor (lpr) types for an activity
     * offering type.
     *
     * @param activityOfferingTypeKey a key for an activity offering type
     * @param context                 information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of valid instructor types
     * @throws DoesNotExistException     activityOfferingTypeKey not found
     * @throws InvalidParameterException context is not valud
     * @throws MissingParameterException activityOfferingTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information about an ActivityOffering
     *
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return ActivityOffering associated with the passed in Id
     * @throws DoesNotExistException     seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException missing activityOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single ActivityOfferingDisplay by a ActivityOffering Id.
     *
     * @param activityOfferingId an identifier for an ActivityOffering
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the ActivityOfferingDisplay requested
     * @throws DoesNotExistException     activityOfferingId does not exist
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(@WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of activity offerings by id list.
     *
     * @param activityOfferingIds List of unique Ids of ActivityCourseOffering
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Activity offering list
     * @throws DoesNotExistException     activityOfferingId in the list not
     *                                   found
     * @throws InvalidParameterException invalid activityOfferingIds
     * @throws MissingParameterException missing activityOfferingIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieve a list of ActivitAOfferingAdminDisplays corresponding to a list
     * of ActivityOffering Ids. The returned list may be in any order and if
     * duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param activityOfferingIds a list of ActivityOffering identifiers
     * @param contextInfo         information containing the principalId and
     *                            locale information about the caller of service
     *                            operation
     * @return a list of ActivityOfferingDisplays
     * @throws DoesNotExistException     an activityOfferingId in the list not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException activityOfferingIds, an Id in
     *                                   activityOfferingId, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds,
                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve a list of ActivityOfferingDisplays corresponding to a
     * CourseOffering Id. Activity Offerings for all FormatOfferings within the
     * given CourseOffering are used to assemble this administrative view.
     *
     * @param courseOfferingId the identifier for a CourseOffering
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return a list of ActivityOfferingDisplayInfos
     * @throws DoesNotExistException     courseOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
                                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to an
     * ActivityOfferingCluster.
     *
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return List of ActivityOfferings
     * @throws DoesNotExistException     activityOfferingClusterId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException activityOfferingClusterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException     formatOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * FormatOffering that are not associated with a cluster
     *
     * @param formatOfferingId Id of the CourseOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOfferings without cluster association
     * @throws DoesNotExistException     formatOfferingId not found
     * @throws InvalidParameterException invalid formatOfferingId
     * @throws MissingParameterException formatOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Activity Offerings by actvity offering template id which
     * don't have registration groups created for them yet.
     *
     * @param formatOfferingId The Id of the format offering
     * @param context
     * @return
     * @throws DoesNotExistException     The formatOfferingId does not exist
     * @throws InvalidParameterException Invalid formatOfferingId
     * @throws MissingParameterException Missing formatOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for ActivityOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of ActivityOffering Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForActivityOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ActivityOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of ActivityOfferings matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityOfferingInfo> searchForActivityOfferings(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Activity Offering for a format offering.
     *
     * @param formatOfferingId     courseOffering that the ActivityOffering
     *                             belongs to
     * @param activityId           the canonical activity this is associated
     *                             with
     * @param activityOfferingInfo Details of the ActivityOffering to be
     *                             created
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return newly created ActivityOffering
     * @throws DoesNotExistException        if the format offering does not
     *                                      exist
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityId") String activityId, @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Creates a new Activity Offering from another activity offering, the
     * generated activity offering is the same format offering, type and
     * canonical activity as the source activity fofering
     *
     * @param activityOfferingId the  activity offering used as source
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return newly created ActivityOffering
     * @throws DoesNotExistException        if the format offering does not
     *                                      exist
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public ActivityOfferingInfo copyActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Generates activity offerings based on a format offering.
     *
     * @param formatOfferingId
     * @param activityOfferingType
     * @param quantity
     * @param context
     * @return
     * @throws InvalidParameterException formatOfferingId invalid
     * @throws MissingParameterException Missing formatOfferingId in the input
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    List<ActivityOfferingInfo> generateActivityOfferings(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingType") String activityOfferingType, @WebParam(name = "quantity") Integer quantity, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing ActivityOffering.
     *
     * @param activityOfferingId   Id of ActivitOffering to be updated
     * @param activityOfferingInfo Details of updates to the ActivityOffering
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return updated ActivityOffering
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        the ActivityOffering does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException;


    /**
     * Updates the state of an existing ActivityOffering to another state
     * provided that it is valid to do so.
     *
     * @param activityOfferingId Id of the ActivityOffering to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           ActivityOffering will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified ActivityOffering does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateActivityOfferingState(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing ActivityOffering. Deleting an activity will also
     * delete any relation it has with course offerings. An activity offering
     * cannot be deleted if it is being referenced in a registration group and
     * will be DependentObjectsExistException. The registration group needs to
     * be updated to drop the activity offering references before the activity
     * offering can be deleted. The difference in behavior is because of the
     * relationship nature is different between course offering to activity
     * offering and registration group to activity offering. Course offering
     * contains activity offering, so deleting an activity offering can be
     * logically interpreted as removing the containing relationship.
     * Registration group only references existing activity offerings and hence
     * deleting an activity offering will leave the registration group in
     * inconsistent state and updating registration group automatically will
     * lead to unintended side effects.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be deleted
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException          the identified ActivityOffering
     *                                        does not exist
     * @throws InvalidParameterException      One or more parameters invalid
     * @throws MissingParameterException      One or more parameters missing
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     * @throws DependentObjectsExistException when one or more Registration
     *                                        Group and/or Seat Pool Definitions
     *                                        dependencies exist.
     */
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

    /**
     * Deletes an existing ActivityOffering cascaded style. Deleting an activity
     * offering cascaded style would also delete all the registration groups and
     * seat pools associated with it.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be deleted
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified Activity o does not
     *                                   exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOfferingCascaded(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Attempt to schedule a single Activity Offering using the Scheduling
     * Service.
     * <p/>
     * This is designed to be used for one-off scheduling of activity offerings
     * created after the mass scheduling event.
     * <p/>
     * The expectation is that this method is synchronous (i.e. it will block
     * until the request is completed or fails).
     * <p/>
     * We also assume that the underlying scheduling service call will not take
     * an unbounded amount of time to solve but rather a quick one-off that will
     * return in a short amount of time.
     *
     * @param activityOfferingId Id of the Activity Offering to be scheduled.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the starting the scheduling process for the
     *         activityOffering (success, failed)
     * @throws DoesNotExistException     the identified activity offering does
     *                                   not exist.
     * @throws InvalidParameterException the contextInfo parameter object is
     *                                   invalid.
     * @throws MissingParameterException one or more of the method parameter's
     *                                   is missing.
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo scheduleActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an activity offering. Depending on the value of validationType,
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
     * @param activityOfferingInfo the activity offering information to be
     *                             tested.
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
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
     * @param activityOfferingId the Id of the ActivityOffering to be used for
     *                           contact hour calculation
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
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
     * @param activityOfferingId the Id of the ActivityOffering to be used for
     *                           contact hour calculation
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
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
     * @param activityOfferingId the Id of the ActivityOffering to be used for
     *                           contact hour calculation
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
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
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
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
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return Registration Group list
     * @throws DoesNotExistException     registrationGroupId in the list not
     *                                   found
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
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that contain all the
     * activity offerings in the input list.
     *
     * @param activityOfferingIds List of activityOffering Identifiers
     * @param context
     * @return
     * @throws DoesNotExistException     One or more of the activityOfferingIds
     *                                   doesn't exist
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
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException     courseOfferingId or formatTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid courseOfferingId or
     *                                   formatTypeKey
     * @throws MissingParameterException missing courseOfferingId or
     *                                   formatTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a specified
     * ActivityOfferingCluster.
     *
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param contextInfo               information containing the principalId
     *                                  and locale information about the caller
     *                                  of service operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException     No ActivityOfferingCluster exists for
     *                                   the specified activityOfferingClusterId.
     * @throws InvalidParameterException invalid contextInfo object
     * @throws MissingParameterException one or more method parameters are
     *                                   missing.
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for RegistrationGroups that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of RegistrationGroup Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForRegistrationGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for RegistrationGroups that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of RegistrationGroups matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RegistrationGroupInfo> searchForRegistrationGroups(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

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
     * @param validationType            Identifier of the extent of validation
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param registrationGroupType     the identifier for the RegistrationGroup
     *                                  type
     * @param registrationGroupInfo     the registrationGroup information to be
     *                                  tested.
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType,
                                                                @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId,
                                                                @WebParam(name = "registrationGroupType") String registrationGroupType,
                                                                @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new Registration Group.
     *
     * @param formatOfferingId          formatOfferingId that the  RegistrationGroup
     *                                  is based on
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param registrationGroupType     the identifier for the RegistrationGroup
     *                                  type
     * @param registrationGroupInfo     Details of the RegistrationGroup to be
     *                                  created
     * @param context                   Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return newly created registrationGroup
     * @throws DoesNotExistException        courseOfferingId not found
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                         @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId,
                                                         @WebParam(name = "registrationGroupType") String registrationGroupType,
                                                         @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo,
                                                         @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing RegistrationGroup.
     *
     * @param registrationGroupId   Id of RegistrationGroup to be updated
     * @param registrationGroupInfo Details of updates to the RegistrationGroup
     * @param context               Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return updated RegistrationGroup
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        the registrationGroupId does not
     *                                      exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Updates the state of an existing RegistrationGroup to another state
     * provided that it is valid to do so.
     *
     * @param registrationGroupId Id of the RegistrationGroup to be updated.
     * @param nextStateKey        The State Key into which the identified
     *                            RegistrationGroup will be placed if the
     *                            operation succeeds.
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified RegsitrationGroup does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateRegistrationGroupState(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing Registration Group. Removes the relationship to the
     * course offering and activity offering. The activity offerings are not
     * automatically deleted
     *
     * @param registrationGroupId the Id of the RegistrationGroup to be deleted
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @throws DoesNotExistException     the RegistrationGroup does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes all Registration Groups for a Format Offering.
     *
     * @param formatOfferingId the Id of the FormatOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes all generated Registration Groups for a Format Offering. A
     * generated registration group is one whose isGenerated() flag is true.
     *
     * @param formatOfferingId the Id of the FormatOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteGeneratedRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes all Registration Groups associated with an Activity Offering
     * Cluster
     *
     * @param activityOfferingClusterId the Id of the FormatOffering
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException activityOfferingClusterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure has occurred
     */
    public StatusInfo deleteRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies a Registration Group applying rules such as: (1) Reg Group has
     * one of each AO type (2) AO's don't meet at the same time (if scheduling
     * has already happened) (3) AO's are all offered at the same campus (4)
     * AO's don't have conflicting seatpool/enrollment restrictions
     *
     * @param registrationGroupId the registrationGroup information to be
     *                            tested.
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     contextInfo not found
     * @throws InvalidParameterException invalid registrationGroupId or
     *                                   contextInfo
     * @throws MissingParameterException registrationGroupId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> verifyRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Gets an Activity Offering Cluster based on the Identifier
     *
     * @param activityOfferingClusterId Identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return ActivityOfferingCluster
     * @throws DoesNotExistException     activityOfferingClusterId does not
     *                                   exist
     * @throws InvalidParameterException Invalid contextInfo
     * @throws MissingParameterException activityOfferingClusterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ActivityOfferingClusterInfo getActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOfferingClusters associated with a
     * FormatOffering
     *
     * @param formatOfferingId Id of the FormatOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException formatOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of ActivityOfferingCluster Id's associated with a
     * FormatOffering
     *
     * @param formatOfferingId Id of the FormatOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering Id's
     * @throws DoesNotExistException     courseOfferingId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException formatOfferingId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getActivityOfferingClustersIdsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an Activity Offering Cluster. Depending on the value of
     * validationTypeKey, this validation could be limited to tests on just the
     * current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the academic calendar and a record is found for that identifier, the
     * validation checks if the academic calendar can be shifted to the new
     * values. If a record cannot be found for the identifier, it is assumed
     * that the record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting the
     * validationTypeKey to the current object. This is a slightly different
     * pattern from the standard validation as the caller provides the
     * identifier in the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeKey           Identifier of the extent of
     *                                    validation
     * @param formatOfferingId            Format Offering identifier
     * @param activityOfferingClusterInfo the Activity Offering Cluster
     *                                    information to be validated.
     * @param contextInfo                 Context information containing the
     *                                    principalId and locale information
     *                                    about the caller of service operation
     * @return the results from performing the validation
     * @throws DoesNotExistException     validationTypeKey or activityOfferingClusterTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid activityOfferingClusterInfo or
     *                                   contextInfo
     * @throws MissingParameterException validationTypeKey, activityOfferingClusterTypeKey,
     *                                   activityOfferingClusterInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateActivityOfferingCluster(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new Activity Offering Cluster from the given Format Offering
     *
     * @param formatOfferingId               Format Offering identifier
     * @param activityOfferingClusterTypeKey Activity Offering Cluster type
     * @param activityOfferingClusterInfo    Details of the ActivityOfferingCluster
     *                                       to be created
     * @param contextInfo                    Context information containing the
     *                                       principalId and locale information
     *                                       about the caller of service
     *                                       operation
     * @return newly created ActivityOfferingCluster
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        validationTypeKey or activityOfferingClusterTypeKey
     *                                      not found
     * @throws InvalidParameterException    invalid activityOfferingClusterInfo
     *                                      or contextInfo
     * @throws MissingParameterException    validationTypeKey, activityOfferingClusterTypeKey
     *                                      or activityOfferingClusterInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure has
     *                                      occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public ActivityOfferingClusterInfo createActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterTypeKey") String activityOfferingClusterTypeKey, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an ActivityOfferingCluster based on the info object
     *
     * @param formatOfferingId            Format Offering identifier
     * @param activityOfferingClusterId   Identifier of the Activity Offering
     *                                    Cluster
     * @param activityOfferingClusterInfo ActivityOfferingCluster with new
     *                                    information
     * @param contextInfo                 Context information containing the
     *                                    principalId and locale information
     *                                    about the caller of service operation
     * @return updated ActivityOfferingCluster
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        activityOfferingClusterId does not
     *                                      exist
     * @throws InvalidParameterException    Invalid activityOfferingClusterInfo
     * @throws MissingParameterException    activityOfferingClusterId or
     *                                      activityOfferingClusterInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure has
     *                                      occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Updates the state of an existing ActivityOfferingCluster to another state
     * provided that it is valid to do so.
     *
     * @param activityOfferingClusterId Id of the ActivityOfferingCluster to be
     *                                  updated.
     * @param nextStateKey              The State Key into which the identified
     *                                  ActivityOfferingCluster will be placed
     *                                  if the operation succeeds.
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified ActivityOfferingCluster
     *                                   does not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateActivityOfferingClusterState(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an activity offering cluster  based on the identifier
     *
     * @param activityOfferingClusterId Identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException          activityOfferingClusterId does not
     *                                        exist
     * @throws InvalidParameterException      Invalid contextInfo
     * @throws MissingParameterException      activityOfferingClusterId or
     *                                        contextInfo is missing or null
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      an authorization failure has
     *                                        occurred
     * @throws DependentObjectsExistException Registration Groups exist for this
     *                                        cluster which prevents the delete
     *                                        from occuring.
     */
    public StatusInfo deleteActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException;

    /**
     * Deletes an existing ActivityOfferingCluster cascaded style. Deleting an
     * activity offering cluster cascaded style would also delete all the
     * registration groups associated with it.
     *
     * @param activityOfferingClusterId the Id of the ActivityOfferingCluster to
     *                                  be deleted
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified Activity o does not
     *                                   exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOfferingClusterCascaded(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies an Activity Offering Cluster completeness for generation,
     * verifying that each of the created RegGroups will be legitimate
     *
     * @param activityOfferingClusterId Activity Offering Cluster to be
     *                                  verified
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return Information gleaned from verifying the ActivityOfferingCluster
     * @throws DoesNotExistException     activityOfferingClusterId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException activityOfferingClusterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @impl See https://wiki.kuali.org/display/STUDENT/Reg+Group+Verification
     */
    public AOClusterVerifyResultsInfo verifyActivityOfferingClusterForGeneration(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information about a SeatPoolDefinition
     *
     * @param seatPoolDefinitionId Unique Id of the SeatPoolDefinition
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return SeatPoolDefinition associated with the passed in Id
     * @throws DoesNotExistException     seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid seatPoolDefinitionId
     * @throws MissingParameterException missing seatPoolDefinitionId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to an
     * ActivityOffering. This should return SeatPoolDefinitions that apply
     * globally across all RegistrationGroups in the ActivityOffering.
     *
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException     activityOfferingId not found
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException missing activityOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for SeatPoolDefinitions that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of SeatPoolDefinition Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForSeatpoolDefinitionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for SeatPoolDefinitions that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of SeatPoolDefinitions matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Seat Pool
     *
     * @param seatPoolDefinitionInfo Details of the SeatPoolDefinition to be
     *                               created
     * @param context                Context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return newly created SeatPoolDefinition
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId   Id of SeatPoolDefinition to be updated
     * @param seatPoolDefinitionInfo Details of updates to the SeatPoolDefinition
     * @param context                Context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return updated SeatPoolDefinition
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        the SeatPoolDefinition does not
     *                                      exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Updates the state of an existing SeatPoolDefinition to another state
     * provided that it is valid to do so.
     *
     * @param seatPoolDefinitionId Id of the SeatPoolDefinition to be updated.
     * @param nextStateKey         The State Key into which the identified
     *                             SeatPoolDefinition will be placed if the
     *                             operation succeeds.
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified SeatPoolDefinition does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo updateSeatPoolDefinitionState(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate a seat pool definition
     *
     * @param validationTypeKey * @param seatPoolDefinitionInfo
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
     * @param seatPoolDefinitionId the Id of the SeatPoolDefinition to be
     *                             deleted
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @throws DoesNotExistException     the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Add a SeatPoolDefinition to an ActivityOffering
     *
     * @param seatPoolDefinitionId a unique identifier for a SeatPoolDefinition
     * @param activityOfferingId   a unique identifier for an ActivityOffering
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status
     * @throws AlreadyExistsException    seatPoolDefinitionId already related to
     *                                   activityOfferingId
     * @throws DoesNotExistException     seatPoolDefinitionId or activityOfferingId
     *                                   not found
     * @throws InvalidParameterException invalid seatPoolDefinitionId,
     *                                   activityOfferingId, or contextInfo
     * @throws MissingParameterException missing seatPoolDefinitionId,
     *                                   activityOfferingId, or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addSeatPoolDefinitionToActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId,
                                                              @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes a SeatPoolDefinition from an ActivityOffering.
     *
     * @param seatPoolDefinitionId a unique identifier for a SeatPoolDefinition
     * @param activityOfferingId   a unique identifier for an ActivityOffering
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status
     * @throws DoesNotExistException     seatPoolDefinitionId or activityOfferingId
     *                                   not found
     * @throws InvalidParameterException invalid seatPoolDefinitionId,
     *                                   activityOfferingId, or contextInfo
     * @throws MissingParameterException missing seatPoolDefinitionId,
     *                                   activityOfferingId, or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId,
                                                                   @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
