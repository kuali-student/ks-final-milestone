/**
 */
package org.kuali.student.enrollment.registration.course.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.registration.course.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.RegResponseInfo;
import org.kuali.student.enrollment.waitlist.course.dto.CourseWaitlistEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;

/**
 * The Course Registration Service is a Class II service supporting the process
 * of registering a student in course(s) for a term. The service provides
 * operations for creating and validating registration requests , registering
 * for a course, waitlist processing, and dropping a course. This service
 * supports the concept of registration cart in the application and all of the
 * transactional requests for registration are made through this service. As
 * part of negotiating the student's registration, operations are provided to
 * manage related exceptions and holds related to registration.
 * 
 * @author Kuali Student Team (sambit)
 */

@WebService(name = "CourseRegistrationService", targetNamespace = CourseRegistrationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseRegistrationService extends DataDictionaryService, TypeService, StateService {

    /**
     * Checks if a student can register at all i.e., checks if the students
     * current academic status allows them to register. This is more generic
     * operation and doesn't take in the term information.
     * <p>
     * Implementation notes: Checks high-level conditions required for
     * registration e.g. student admitted,in good standing , alove etc.
     * 
     * @param studentId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Boolean checkStudentEligibility(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks the eligibility of a student to register given the term. If the
     * student is eligible for a term, then they can build reg cart for the
     * term.
     * <p>
     * Implementation notes: expected that term, holds for that term
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the appointment window for a term that a student can register in.
     * <p>
     * Implementation notes:
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<DateRangeInfo> getAppointmentWindow(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular course
     * offering.
     * <p>
     * Implementation notes: This operation does prerequisite checks basic
     * prerequisites check
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context
     * @return
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(
            @WebParam(name = "studentId") String studentId,
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular
     * registration group. Returns a {@link List} of
     * {@link ValidationResultInfo}. When a student is eligible the {@link List}
     * contains a single {@link ValidationResultInfo} with error level OK.
     * 
     * @param studentId
     * @param regGroupId
     * @param context
     * @return
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the registration groups for a course offering from the
     * CourseOfferingService and then filters out the registration groups the
     * student is not eligible for using checkEligibilityForRegGroup.
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context
     * @return
     */
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(
            @WebParam(name = "studentId") String studentId,
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular term. This
     * information can be used to display in the cart.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     */
    public String calculateCreditLoadForTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * Calculate the credit load for a student in a particular request. It also
     * adds up the credits for courses the student has registered for already in
     * the term.
     * 
     * @param studentId
     * @param regRequestInfo
     * @param context
     * @return
     */
    public String calculateCreditLoadForRegRequest(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * Retrieves the open seat count for a particular registration group.
     * 
     * @param regGroupId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Integer getAvailableSeatsForRegGroupAndStudent(@WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the open seat count for a particular registration group.
     * 
     * @param regGroupId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Integer getAvailableSeatsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the number of seats available for a particular student in a
     * registration group.
     * 
     * @param studentId
     * @param regGroupId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Integer getAvailableSeatCountForStudentInRegGroup(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Returns the available seats in a particular seat pool.
     * 
     * @param studentId
     * @param seatpoolId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Integer getAvailableSeatsInSeatpool(@WebParam(name = "seatpoolId") String seatpoolId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a registration request for a student.
     * <p>
     * Implementation Notes : This operation does the following steps:
     * <ul>
     * <li>Validate the data in the reqRequestInfo parameter. If invalid throw
     * an exception.
     * <li>Create an id and persist the registration request.
     * <li>Return the updated registration request.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegRequestInfo createRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Check the request state and if its not SUBMITTED or beyond and updates it
     * with the input {@link RegRequestInfo} values. The id is fetched from the
     * {@link RegRequestInfo} in the parameter. If the state is not valid, throw
     * an exception.
     * <p>
     * Implementation notes:This method shouldn't update the state of a
     * registration request since that can be done as part of any transaction
     * only. This operation will be called to save a registration cart after
     * changes e.g addition or deletion of courses.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegRequestInfo updateRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    /**
     * Delete the registration request from the database.
     * 
     * @param reqRequestId
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteRegRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Set the state of the registration request to canceled. This operation
     * would be used instead of delete when the registration request might need
     * to be stored and not deleted for reporting purposes. Scenarios are
     * request for a course that was canceled later etc. Throws an exception if
     * a registration request is already in a success or failure state post
     * completion of the transaction.
     * 
     * @param reqRequestId
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo cancelRegRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a registration request by id.
     * 
     * @param reqRequestId
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegRequestInfo getRegRequestById(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a registration requests by id list.
     * 
     * @param reqRequestId
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> getRegRequestByIdList(@WebParam(name = "reqRequestId") List<String> reqRequestIds,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the registration requests for a student by term and student id.
     * Additionally the state of the registration request can also be passed so
     * that only requests in certain states are returned.
     * 
     * @param requestStates
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> getRegRequestsForStuByTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termKey") String termKey, @WebParam(name = "requestStates") List<String> requestStates,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate a registration request to see that there are no conflicting or
     * invalid/ in-eligible registration groups in the request after each
     * modification or when finally submitting or saving it.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ValidationResultInfo> validateReqRequest(
            @WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies a registration request to make sure that the data in the
     * {@link RegRequestInfo} satisfies the rules to be a valid registration
     * request. This includes course pre-requisites or co-requisites and credit
     * load rules.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo verifyReqRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies a reg request to see that there are no conflicting or invalid/
     * ineligible registration groups in the request after each modification or
     * when finally submitting it. This method takes in a id assuming that the
     * {@link RegRequestInfo} is already saved in the database.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo verifyReqRequestForSubmission(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new registration request from an existing registration request.
     * Convenient when a transaction fails and a students wants to rebuild the
     * registration cart with earlier items. It can also be used in any such other
     * scenario where a registration request has passed one of the final states
     * (or is canceled) and a new registration request needs to be created for
     * re-initiating the transaction.
     * 
     * @param existingRegRequestId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegRequestInfo createReqRequestFromExisting(
            @WebParam(name = "existingRegRequestId") String existingRegRequestId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Submits a reg request for a course or multiple courses. Assumes that the
     * cart is already saved before this operation is called. This operation
     * also handles dropping courses. If the requested course is full and if the
     * student is requesting to be waitlisted, hold-listed etc. this operation
     * process that after attempt to register fails. This method is
     * transactional and for multiple registration request items, each need to
     * succeed or else the overall registration fails and the successful
     * transactions are rolled back. A failure is when a course registration,
     * waitlisting, hold-listing or exception-listing cannot be completed
     * successfully. This operation calls validateReqRequest to make sure that
     * the request is valid before starting the transaction.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo submitReqRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a course waitlist entry by id.
     * 
     * @param courseWaitlistEntryId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistEntryInfo getCourseWaitlistEntryById(
            @WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a course waitlist entry - used to update rank or other info in a
     * course waitlist entry.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo updateCourseWaitlistEntry(
            @WebParam(name = "reqRequestInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a course waitlist entry
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo deleteCourseWaitlistEntry(
            @WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Register a student to a reg group from a waitlist.
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo registerStudentFromWaitlist(
            @WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course waitlist entries for a course offering. Returns all
     * students who are on waitlists for that course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course waitlist entries for a reg group.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the waitlist entries for a course offering by student. A student
     * might be listed in multiple reg group waitlists in the same course.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentAndCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the waitlist for a reg group and student.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentAndRegGroup(
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get all the waitlist the student is in for a term. Returns
     * CourseWaitlistEntryInfo which is the student-waitlist relation.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration by ID.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public CourseRegistrationInfo getCourseRegistrationById(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration by ID list.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public CourseRegistrationInfo getCourseRegistrationsByIdList(
            @WebParam(name = "courseRegistrationIds") List<String> courseRegistrationIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registrations for a student by term.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get course registrations by course offering id. Gets all student
     * registrations for the course.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */

    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOfferingId(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the request that resulted in this course registration.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> getRegRequestsForCourseRegn(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method gets the reg requests for a course offering for all students.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> getRegRequestsForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get reg requests objects which are attached to this course offering for a
     * student.
     * 
     * @param courseOfferingId
     * @param studentId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Get the activity registrations for this course registration. A course
     * registration results in one-many activity registrations depending on the
     * reg group the student registered for.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegn(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the activity registration by id.
     * 
     * @param activityRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public ActivityRegistrationInfo getActivityRegistrationById(
            @WebParam(name = "activityRegistrationId") String activityRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the activity resgistrations by the list of identifiers.
     * 
     * @param activityRegistrationIds
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public ActivityRegistrationInfo getActivityRegistrationsByIdList(
            @WebParam(name = "activityRegistrationIds") List<String> activityRegistrationIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the activity registrations for this course registration. A course
     * registration results in one-many activity registrations depending on the
     * reg group the student registered for.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsForActivityOffering(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get all activity registrations by student for a certain term.
     * 
     * @param courseRegistrationId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ActivityRegistrationInfo> getActivityRegistrationsForStudentByTerm(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get reg group registration by id.
     * 
     * @param regGroupRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegGroupRegistrationInfo getRegGroupRegistration(
            @WebParam(name = "regGroupRegistrationId") String regGroupRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get reg group registration for a course registration .
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */

    public List<RegGroupRegistrationInfo> getRegGroupRegistrationsForCourseRegistration(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get reg group registrations by id id of reg groups.
     * 
     * @param regGroupIds
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegGroupRegistrationInfo> getRegGroupRegistrationsByIdList(
            @WebParam(name = "courseRegistrationId") List<String> regGroupIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method get reg group registration by reg group id. Returns the reg
     * group registration for a reg group id
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegGroupRegistrationInfo> getRegGroupRegistrationsByRegGroupId(
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Get the registration group registration for a student in a particular
     * term.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegGroupRegistrationInfo getRegGroupRegistrationsForStudentByTerm(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} object.
     * 
     * @param criteria
     * @return
     */
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria);

    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} ids.
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForCourseOfferingRegistrationIds(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForRegGroupRegistrationIds(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(QueryByCriteria criteria);

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForCourseWaitlistEntryIds(QueryByCriteria criteria);

}
