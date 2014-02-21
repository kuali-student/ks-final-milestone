package org.kuali.student.ap.test.mock;

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
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingServiceMockTest implements CourseOfferingService {
    /**
     * This method returns the TypeInfo for a given course offering type key.
     *
     * @param courseOfferingTypeKey the unique identifier for the type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return the type requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public TypeInfo getCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the valid course offering types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid CourseOffering Types
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getCourseOfferingTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a single CourseOffering by a CourseOffering Id.
     *
     * @param courseOfferingId the identifier for the CourseOffering to be
     *                         retrieved
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a single CourseOfferingDisplayInfo by a CourseOffering Id.
     *
     * @param courseOfferingId an identifier for a CourseOffering
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return the CourseOfferingDisplay requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public CourseOfferingDisplayInfo getCourseOfferingDisplay(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of CourseOfferings from a list of CourseOffering Ids.
     * The returned list may be in any order and if duplicate Ids are supplied,
     * a unique set may or may not be returned.
     *
     * @param courseOfferingIds a list of CourseOffering identifiers
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a courseOfferingId in the list is not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingIds, an Id in the
     *          courseOfferingIds, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a courseOfferingId in the list not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingIds, an Id in
     *          courseOfferingIds, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve a list of CourseOffering Ids by CourseOffering Type.
     *
     * @param courseOfferingTypeKey the identifier for a CourseOffering Type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return a list of CourseOffering identifiers matching
     *         courseOfferingTypeKey or an empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve CourseOfferings by canonical Course Id across all Terms.
     *
     * @param courseId    the identifier for a Course
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings of the given Course or an empty list if
     *         none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseId orcontextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseId, termId, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occured
     */
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          termId or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getCourseOfferingIdsByTerm(@WebParam(name = "termId") String termId, @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          termId, subjectArea, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(@WebParam(name = "termId") String termId, @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of CourseOfferings for a given Term and Instructor.
     *
     * @param termId       the identifier for a Term
     * @param instructorId the Person Id for an instructor
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return a list of CourseOfferings for the given Term and instructor or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          termId, instructorId, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(@WebParam(name = "termId") String termId, @WebParam(name = "instructorId") String instructorId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deprecated
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          termId, unisContentOwnerId, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId, @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for CourseOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOffering Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForCourseOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for CourseOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the valid rollover option keys.
     * <p/>
     * This is the list of option keys supported by the rollover operation. Keys
     * released with kuali student can be found here: https://wiki.kuali.org/display/STUDENT/Course+Offering+Set+Types+and+States#CourseOfferingSetTypesandStates-RolloverOptionKeys
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of option keys
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getValidRolloverOptionKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @param validationTypeKey  the identifier for the validation Type
     * @param courseOfferingInfo the CourseOffering to be validated
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of
     *                           service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or courseOfferingTypeKey
     *          is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          courseOfferingInfo or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, courseOfferingTypeKey,
     *          courseOfferingInfo, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          sourceCoId not found
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          if the course offering already
     *          exists in the target term and skip
     *          if already exists option is
     *          specified
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data in system is not valid or not
     *          valid for an option key specified
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultItemInfo rolloverCourseOffering(@WebParam(name = "sourceCourseOfferingId") String sourceCourseOfferingId, @WebParam(name = "targetTermId") String targetTermId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing CourseOffering.
     *
     * @param courseOfferingId   Id of CourseOffering to be updated
     * @param courseOfferingInfo Details of updates to the CourseOffering
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return updated CourseOffering
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the CourseOffering does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing CourseOffering into another state
     * provided that it is valid to do so.
     *
     * @param courseOfferingId Id of the CourseOffering to be changed.
     * @param nextStateKey     The State Key into which the identified
     *                         courseOffering will be placed if the operation
     *                         succeeds.
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified CourseOffering does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeCourseOfferingState(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the CourseOffering does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing CourseOffering.
     *
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the CourseOffering does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          When one or more Format Offering,
     *          Activity Offering, Registration
     *          Group or Seat Pool Definition
     *          exist for course offering.
     */
    @Override
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the CourseOffering does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCourseOfferingCascaded(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates / Compares a course offering against it's canonical course.
     *
     * @param courseOfferingInfo the course offering information to be tested.
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          if the course associated with the
     *          course offering does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          if a parameter is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          if a parameter is missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets an format offering  based on Id.
     *
     * @param formatOfferingId The  Format Offering  identifier
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return the FormatOffering identified by the identifier
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The Format Offering doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets a list of format offering by a course offering id they belong to.
     *
     * @param courseOfferingId Course offering identifier
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return a list of FormatOffering by course offering
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The course offering  doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid course offering id
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing course offering id
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates an  Format Offering  for a course offering
     *
     * @param courseOfferingId   Course offering that the  Format Offering
     *                           belongs to
     * @param formatId           the identifier for the FormatOffering
     * @param formatOfferingType the type key of the  Format Offering  template
     * @param formatOfferingInfo The Format Offering  info object
     * @return the information about the FormatOffering created
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          if courseOfferingId or formatId does
     *          not exist for the course in the
     *          course offering
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid course offering id
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing course offering id,
     *          formatOfferingTemplate  or
     *          formatOfferingType
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public FormatOfferingInfo createFormatOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatId") String formatId, @WebParam(name = "formatOfferingType") String formatOfferingType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update a  Format Offering
     *
     * @param formatOfferingId   The  Id formatOffering to be updated
     * @param formatOfferingInfo The new formatOffering Info
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the updated information about the FormatOffering updated.
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The formatOfferingId doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid  formatOfferingId or
     *          formatOffering
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing formatOffering or
     *          formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          stale version being updated
     */
    @Override
    public FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing FormatOffering to another state provided
     * that it is valid to do so.
     *
     * @param formatOfferingId Id of the FormatOffering to be changed.
     * @param nextStateKey     The State Key into which the identified
     *                         FormatOffering will be placed if the operation
     *                         succeeds.
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified FormatOffering does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeFormatOfferingState(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, formatOfferingInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, formatOfferingInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateFormatOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an  Format Offering
     *
     * @param formatOfferingId The  Id formatOffering to be deleted
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The formatOfferingId doesn't
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid  formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing  formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          When one or more Activity
     *          Offering, Registration Group or
     *          Seat Pool Definition exist for the
     *          format offering.
     */
    @Override
    public StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an  Format Offering with dependent Activity Offering and
     * Registration group
     *
     * @param formatOfferingId The  Id formatOffering to be deleted
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return the status of the operation (success/fail)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The formatOfferingId doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid  formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing  formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteFormatOfferingCascaded(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for FormatOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOffering Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForFormatOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for FormatOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<FormatOfferingInfo> searchForFormatOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the TypeInfo for a given activity offering type key.
     *
     * @param activityOfferingTypeKey Key of the type
     * @param context                 Context information containing the
     *                                principalId and locale information about
     *                                the caller of service operation
     * @return Information about the Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing activityOfferingTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the valid activity offering types.
     *
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid context
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing context
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getActivityOfferingTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the valid activity offering types for a given
     * canonical activity type
     *
     * @param activityTypeKey Key of the canonical activity type
     * @param context         Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return a list of valid activity offering Types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid context
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing context
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the valid instructor (lpr) types for an activity
     * offering type.
     *
     * @param activityOfferingTypeKey a key for an activity offering type
     * @param context                 information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of valid instructor types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          context is not valud
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingTypeKey or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information about an ActivityOffering
     *
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return ActivityOffering associated with the passed in Id
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          ActivityOffering with activityOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing activityOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a single ActivityOfferingDisplay by a ActivityOffering Id.
     *
     * @param activityOfferingId an identifier for an ActivityOffering
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the ActivityOfferingDisplay requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of activity offerings by id list.
     *
     * @param activityOfferingIds List of unique Ids of ActivityCourseOffering
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Activity offering list
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingId in the list not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          an activityOfferingId in the list not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingIds, an Id in
     *          activityOfferingId, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOffering records that belongs to an
     * ActivityOfferingCluster.
     *
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return List of ActivityOfferings
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * CourseOffering.
     *
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          formatOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOffering records that belongs to a
     * FormatOffering that are not associated with a cluster
     *
     * @param formatOfferingId Id of the CourseOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOfferings without cluster association
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          formatOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          formatOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the Activity Offerings by actvity offering template id which
     * don't have registration groups created for them yet.
     *
     * @param formatOfferingId The Id of the format offering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOfferings by format offering that don't have reg groups yet.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The formatOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing formatOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of allowed time slots Ids for an activity offering
     *
     * @param activityOfferingId identifier for an activity offering
     * @param contextInfo        Context information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of TimeSlots allowed for activityOfferingId or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getAllowedTimeSlotIdsForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of allowed time slots for an activity offering
     *
     * @param activityOfferingId identifier for an activity offering
     * @param contextInfo        Context information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of TimeSlots allowed for activityOfferingId or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TimeSlotInfo> getAllowedTimeSlotsForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of allowed time slots for given days, startTime
     * and for an activity offering
     *
     * @param activityOfferingId identifier for an activity offering
     * @param daysOfWeek         days of the week of interest
     * @param startTime          start time of interest
     * @param contextInfo        Context information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of TimeSlots allowed for activityOfferingId, days,
     *         startTime or an empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid daysOfWeek, startTime or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingId, daysOfWeek, startTime or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TimeSlotInfo> getAllowedTimeSlotsByDaysAndStartTimeForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "daysOfWeek") List<Integer> daysOfWeek, @WebParam(name = "startTime") TimeOfDayInfo startTime, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for ActivityOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of ActivityOffering Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForActivityOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for ActivityOfferings that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of ActivityOfferings matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          if the format offering does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityId") String activityId, @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          if the format offering does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ActivityOfferingInfo copyActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Generates activity offerings based on a format offering.
     *
     * @param formatOfferingId     the identifier for the FormatOffering.
     * @param activityOfferingType a key for an activity offering type
     * @param quantity             quantity of the activity offerings of that type
     * @param context              Context information containing the principalId
     *                             and locale information about the caller of
     *                             service operation
     * @return activity offerings based on format offering
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          formatOfferingId invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing formatOfferingId in the input
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> generateActivityOfferings(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingType") String activityOfferingType, @WebParam(name = "quantity") Integer quantity, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing ActivityOffering.
     *
     * @param activityOfferingId   Id of ActivitOffering to be updated
     * @param activityOfferingInfo Details of updates to the ActivityOffering
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return updated ActivityOffering
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the ActivityOffering does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing ActivityOffering to another state
     * provided that it is valid to do so.
     *
     * @param activityOfferingId Id of the ActivityOffering to be changed.
     * @param nextStateKey       The State Key into which the identified
     *                           ActivityOffering will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified ActivityOffering does
     *          not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeActivityOfferingState(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified ActivityOffering
     *          does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          when one or more Registration
     *          Group and/or Seat Pool Definitions
     *          dependencies exist.
     */
    @Override
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing ActivityOffering cascaded style. Deleting an activity
     * offering cascaded style would also delete all the registration groups and
     * seat pools associated with it.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be deleted
     * @param formatOfferingId
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified Activity o does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteActivityOfferingCascaded(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified activity offering does
     *          not exist.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo parameter object is
     *          invalid.
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          one or more of the method parameter's
     *          is missing.
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo scheduleActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, academicCalendarInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, academicCalendarInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateActivityOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the ActivityOffering with activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the ActivityOffering with activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the ActivityOffering with activityOfferingId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve  a RegistrationGroup   based on id
     *
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return RegistrationGroup associated with the passed in Id
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          registrationGroupId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid registrationGroupId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing registrationGroupId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public RegistrationGroupInfo getRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of registration group by id list.
     *
     * @param registrationGroupIds List of unique Ids of RegistrationGroup
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return Registration Group list
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          registrationGroupId in the list not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid registrationGroupIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing registrationGroupIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(@WebParam(name = "registrationGroupIds") List<String> registrationGroupIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering
     *
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of RegistrationGroups
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseOfferingId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of RegistrationGroup records that contain all the
     * activity offerings in the input list.
     *
     * @param activityOfferingIds List of activityOffering Identifiers
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return a list of RegistrationGroup records that contain all the
     *         activity offerings in the input list.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          One or more of the activityOfferingIds
     *          doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more invalid activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns all registration groups that contain activityOfferingId in the list of AOs associated with
     * the registration group.  A variant of getRegistrationGroupsWithActivityOfferings with only a single
     * activity offering.
     *
     * @param activityOfferingId An activity offering id.
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of RegistrationGroup records that contain all the
     *         activity offerings in the input list.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          One or more of the activityOfferingIds
     *          doesn't exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more invalid activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          Missing activityOfferingIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a
     * CourseOffering for a given canonical format type
     *
     * @param formatOfferingId Unique Id of the CourseOffering
     * @param context          information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return List of RegistrationGroups
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId or formatTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseOfferingId or
     *          formatTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseOfferingId or
     *          formatTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a specified
     * ActivityOfferingCluster.
     *
     * @param activityOfferingClusterId Unique Id of the ActivityOfferingCluster
     * @param contextInfo               information containing the principalId
     *                                  and locale information about the caller
     *                                  of service operation
     * @return List of RegistrationGroups
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          No ActivityOfferingCluster exists for
     *          the specified activityOfferingClusterId.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo object
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          one or more method parameters are
     *          missing.
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for RegistrationGroups that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of RegistrationGroup Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForRegistrationGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for RegistrationGroups that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of RegistrationGroups matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, academicCalendarInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, academicCalendarInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "registrationGroupType") String registrationGroupType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "registrationGroupType") String registrationGroupType, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing RegistrationGroup.
     *
     * @param registrationGroupId   Id of RegistrationGroup to be updated
     * @param registrationGroupInfo Details of updates to the RegistrationGroup
     * @param context               Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return updated RegistrationGroup
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the registrationGroupId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing RegistrationGroup to another state
     * provided that it is valid to do so.
     *
     * @param registrationGroupId Id of the RegistrationGroup to be changed.
     * @param nextStateKey        The State Key into which the identified
     *                            RegistrationGroup will be placed if the
     *                            operation succeeds.
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified RegsitrationGroup does
     *          not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeRegistrationGroupState(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing Registration Group. Removes the relationship to the
     * course offering and activity offering. The activity offerings are not
     * automatically deleted
     *
     * @param registrationGroupId the Id of the RegistrationGroup to be deleted
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the RegistrationGroup does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes all Registration Groups for a Format Offering.
     *
     * @param formatOfferingId the Id of the FormatOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes all generated Registration Groups for a Format Offering. A
     * generated registration group is one whose isGenerated() flag is true.
     *
     * @param formatOfferingId the Id of the FormatOffering
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<BulkStatusInfo> deleteGeneratedRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes all Registration Groups associated with an Activity Offering
     * Cluster
     *
     * @param activityOfferingClusterId the Id of the FormatOffering
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure has occurred
     */
    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Verifies a Registration Group applying rules such as:
     * <ol>
     * <li>Registration Group has one ActivityOffering for each Format Offering
     * activityOfferingType</li>
     * <li>ActivityOffering's don't have any time conflicts (if scheduling
     * has already happened)</li>
     * <li>ActivityOffering's are all offered at the same campus</li>
     * <li>ActivityOffering's don't have conflicting seatpool/enrollment
     * restrictions</li>
     * </ol>
     * <p></p>
     * The distinction between <b>validateRegistrationGroup</b> and this
     * method is that <b>validate</b> checks if its OK to save the object where
     * as <b>verify</b> checks a saved object and its relation to other saved objects.
     * <p></p>
     * <b>validate</b> is called for every <b>create</b> and <b>update</b> and
     * needs to execute quickly where as this method can take longer to
     * accurately evaluate the specified RegistrationGroup.
     *
     * @param registrationGroupId the registrationGroup information to be
     *                            tested.
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          contextInfo not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid registrationGroupId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          registrationGroupId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets an Activity Offering Cluster based on the Identifier
     *
     * @param activityOfferingClusterId Identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return ActivityOfferingCluster
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public ActivityOfferingClusterInfo getActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of activity offering clusters using the given id list.
     * The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param activityOfferingClusterIds List of unique Ids of ActivityOfferingClusters
     * @param contextInfo                Context information containing the principalId
     *                                   and locale information about the caller of
     *                                   service operation
     * @return Activity offering cluster list
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId in the list not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingClusterIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing activityOfferingClusterIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByIds(@WebParam(name = "activityOfferingClusterIds") List<String> activityOfferingClusterIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOfferingClusters associated with a
     * FormatOffering
     *
     * @param formatOfferingId Id of the FormatOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          formatOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOfferingCluster Id's associated with a
     * FormatOffering
     *
     * @param formatOfferingId Id of the FormatOffering
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of ActivityOffering Id's
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          formatOfferingId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getActivityOfferingClustersIdsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or activityOfferingClusterTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingClusterInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, activityOfferingClusterTypeKey,
     *          activityOfferingClusterInfo or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or activityOfferingClusterTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid activityOfferingClusterInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, activityOfferingClusterTypeKey
     *          or activityOfferingClusterInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure has
     *          occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterTypeKey") String activityOfferingClusterTypeKey, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid activityOfferingClusterInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          activityOfferingClusterInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure has
     *          occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "activityOfferingClusterInfo") ActivityOfferingClusterInfo activityOfferingClusterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing ActivityOfferingCluster to another state
     * provided that it is valid to do so.
     *
     * @param activityOfferingClusterId Id of the ActivityOfferingCluster to be
     *                                  changed.
     * @param nextStateKey              The State Key into which the identified
     *                                  ActivityOfferingCluster will be placed
     *                                  if the operation succeeds.
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified ActivityOfferingCluster
     *          does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeActivityOfferingClusterState(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an activity offering cluster  based on the identifier
     *
     * @param activityOfferingClusterId Identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          Invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure has
     *          occurred
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          Registration Groups exist for this
     *          cluster which prevents the delete
     *          from occuring.
     */
    @Override
    public StatusInfo deleteActivityOfferingCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified Activity o does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteActivityOfferingClusterCascaded(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Verifies an Activity Offering Cluster's completeness for the generation
     * of Registration Group's.
     * <p/>
     * The distinction between <b>validateActivityOfferingCluster</b> and this
     * method is that <b>validate</b> checks if its OK to save the object where
     * as <b>verify</b> checks a saved object in relation to other saved objects.
     * <p/>
     * <p></p>
     * <b>validate</b> is called for every <b>create</b> and <b>update</b> and
     * needs to execute quickly where as this method can take longer to
     * accurately evaluate the specified Activity Offering Cluster.
     *
     * @param activityOfferingClusterId Activity Offering Cluster to be
     *                                  verified
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return Information gleaned from verifying the ActivityOfferingCluster
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @impl See https://wiki.kuali.org/display/STUDENT/Reg+Group+Verification
     */
    @Override
    public AOClusterVerifyResultsInfo verifyActivityOfferingClusterForGeneration(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Activity Offering Clusters that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOffering Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForActivityOfferingClusterIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Activity Offering Clusters that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of CourseOfferings matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ActivityOfferingClusterInfo> searchForActivityOfferingClusters(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information about a SeatPoolDefinition
     *
     * @param seatPoolDefinitionId Unique Id of the SeatPoolDefinition
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return SeatPoolDefinition associated with the passed in Id
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          seatPoolDefinitionId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid seatPoolDefinitionId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing seatPoolDefinitionId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          No ActivityOffering found for the specified activityOfferingId
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          context is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          one or more missing parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ActivityOffering records that are associated to a specific
     * SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId Unique Id of the SeatPoolDefinition
     * @param context              Context information containing the principalId
     *                             and locale information about the caller of
     *                             service operation
     * @return List of ActivityOfferings
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          no seat pool exists for seatPoolDefinitionId
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is null.
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing seatPoolDefinitionId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsForSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for SeatPoolDefinitions that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of SeatPoolDefinition Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForSeatpoolDefinitionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for SeatPoolDefinitions that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of SeatPoolDefinitions matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Seat Pool
     *
     * @param seatPoolDefinitionInfo Details of the SeatPoolDefinition to be
     *                               created
     * @param context                Context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return newly created SeatPoolDefinition
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId   Id of SeatPoolDefinition to be updated
     * @param seatPoolDefinitionInfo Details of updates to the SeatPoolDefinition
     * @param context                Context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return updated SeatPoolDefinition
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SeatPoolDefinition does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing SeatPoolDefinition to another state
     * provided that it is valid to do so.
     *
     * @param seatPoolDefinitionId Id of the SeatPoolDefinition to be changed.
     * @param nextStateKey         The State Key into which the identified
     *                             SeatPoolDefinition will be placed if the
     *                             operation succeeds.
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified SeatPoolDefinition does
     *          not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeSeatPoolDefinitionState(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validate a seat pool definition
     *
     * @param validationTypeKey * @param seatPoolDefinitionInfo
     * @param context           Context information containing the
     *                          principalId and locale information about the
     *                          caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          if validation type key is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the context or object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId the Id of the SeatPoolDefinition to be
     *                             deleted
     * @param context              Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SeatPoolDefinition does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a SeatPoolDefinition to an ActivityOffering
     *
     * @param seatPoolDefinitionId a unique identifier for a SeatPoolDefinition
     * @param activityOfferingId   a unique identifier for an ActivityOffering
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          seatPoolDefinitionId already related to
     *          activityOfferingId
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          seatPoolDefinitionId or activityOfferingId
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid seatPoolDefinitionId,
     *          activityOfferingId, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing seatPoolDefinitionId,
     *          activityOfferingId, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a SeatPoolDefinition from an ActivityOffering.
     *
     * @param seatPoolDefinitionId a unique identifier for a SeatPoolDefinition
     * @param activityOfferingId   a unique identifier for an ActivityOffering
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return status
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          seatPoolDefinitionId or activityOfferingId
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid seatPoolDefinitionId,
     *          activityOfferingId, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing seatPoolDefinitionId,
     *          activityOfferingId, or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This is a bulk create method for generateRegistrationGroupsForAOC().  Instead of working on a single Activity Offering Cluster it will
     * work on all of the AOC's of the format offering specified.
     *
     * @param formatOfferingId The identifier of the format offering to generate registration groups for.
     * @param contextInfo      Context information containing the principalId and locale information about the caller of service operation
     * @return status of the operation (success, failed) for each Registration Group created.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          The formatOfferingId does not refer to an existing FormatOffering.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          The formatOfferingId or context is invalid.
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          the formatOfferingId or context is missing.
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request, can also occur when verification of any AOC in the format offering fails.
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          verification of any of the underlying Activity Offering Cluster's failed.
     */
    @Override
    public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Generates all possible registration groups for the Activity Offering
     * Cluster
     *
     * @param activityOfferingClusterId identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed) for each Registration Group created.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          activityOfferingClusterId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          verification of the Activity Offering Cluster failed.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          activityOfferingClusterId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure has occurred
     * @impl Does 'delta' generation: Creates only new RGs
     */
    @Override
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
