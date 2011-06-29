/**
 */
package org.kuali.student.enrollment.registration.course.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.registration.course.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.RegResponseInfo;
import org.kuali.student.enrollment.registration.course.infc.RegRequest;
import org.kuali.student.enrollment.waitlist.course.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.waitlist.course.dto.CourseWaitlistInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;

/**
 * The Course Registration Service is a Class II service supporting the process
 * of registering a student in course(s) for a term. The service will provide
 * operations related to building registration requests , registering for a
 * course, waitlist processing, and dropping a course.This service supports the
 * concept of Reg Cart in the application and all of the transactional requests
 * for registration are made through this service. As part of negotiating the
 * student's registration, operations are provided to manage related exceptions
 * and holds related to registration.
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
    public Boolean canRegister(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks students eligibility for term and if the courses are available to
     * be put in the registration cart. If some of the courses of a future or
     * current term are published the student can put them in the registration
     * cart.
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
    public RegResponseInfo canBuildRegRequestForTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the appointment window for a term that a student can register in.
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
    public KeyDateInfo getAppointmentWindow(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * TODO - follow up if we need this at all
     */
    // public String canAccessRegRequest(String studentId, String termId);

    /**
     * Checks the eligibility of a student to register given the term.
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
    public OperationStatusInfo checkEligibilityOfStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular
     * registration group. Used when student or admin tries to do one-click
     * registration or register for a single course.
     * 
     * @param studentId
     * @param regGroupId
     * @param context
     * @return
     */
    public OperationStatusInfo checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId,
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
    public List<RegistrationGroupInfo> getEligibleRegGroupsForCourseOffering(
            @WebParam(name = "studentId") String studentId,
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular term. This piece
     * of information can be used to display in the cart.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     */
    public Integer calculateCreditLoadForTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * Calculate the credit load for a student in a particular request. It also
     * adds up the courses the student has registered for already in the term.
     * 
     * @param studentId
     * @param regRequestInfo
     * @param context
     * @return
     */
    public Integer getCreditLoadForRequest(@WebParam(name = "studentId") String studentId,
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
    public Integer getAvailableSeatsForRegGroup(@WebParam(name = "regGroupId") String regGroupId,
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
    public Integer getAvailableSeatCountForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * The available seats in a particular seat pool for a student. Checks the
     * students eligibility for the seat pool and then returns the remaining
     * available seats.
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
    public Integer getAvailablebleSeatsInSeatpool(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "seatpoolId") String seatpoolId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Get all the registration alerts for a student - part of notification.
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
    public List<String> getRegAlertsForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the registration holds for a student.
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
    public List<HoldInfo> getRegHoldsForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Create a registration request for a student. This operation does the
     * following steps: 1. Validate the data in the reqRequestInfo parameter. If
     * invalid throw an exception. 2. Create an id and persist the registration
     * request. 3. Return the updated registration request.
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
     * Check the request state and if its not SUBMITTED or beyond, update it
     * with the input regReuestInfo values. The id is fetched from the reg
     * request info in the parameter. This method shouldn't update the state of
     * a reg request since that can be done as part of any transaction only.
     * This operation will be called to save a reg cart after changes e.g
     * addition or deletion of courses.
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
    public RegResponseInfo updateRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Delete the reg request.
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
     * Set the state of the reg request to canceled. This operation would be
     * used instead of delete when the reg request might need to be stored and
     * not deleted for reporting purposes. Scenarios are request for a course
     * that was canceled later etc. Throws exception if a reg request is already
     * in a success or failure state post completion of the transaction.
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
     * Retrieve a reg request by id.
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
    public RegRequestInfo getRegRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the reg requests for a student by term and student id. Additionally
     * the state of the reg request can also be passed so that the student only
     * requests in certain states are returned.
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
            @WebParam(name = "termId") String termId, @WebParam(name = "requestStates") List<String> requestStates,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate a reg request to see that there are no conflicting or invalid
     * registration groups in the request after each modification or when
     * finally submitting or saving it.
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
    public RegRequestInfo validateReqRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new reg request from an existing reg request. Convenient when a
     * transaction fails and a students wants to rebuild the reg cart with
     * earlier items. It can also be used in any such other scenario where a
     * registration request has passed one of the final states (or is cancelled)
     * and a new reg request needs to be created for re-initiating the
     * transaction.
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
    public RegRequestInfo createNewReqRequestFromExisting(
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
     * Creates the {@link RegRequest} in the backed, validates it
     * and registers a student to the course. This is a one-step shortcut to
     * registration for admin or students.
     * 
     * @param reqRequest
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo oneStepRegister(@WebParam(name = "reqRequest") RegRequestInfo reqRequest,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * TODO review the need for this  
     * 
     * NOTE - do not implement this now 
     * 
     * An admin operation to register or drop
     * students as a group to/from a course. Validate the reg requests. THe
     * RegRequestInfo will contain only one item in this case. This method is
     * not transactional, so if registration fails for some students, then rest
     * of the registration will continue processing.
     * 
     * @param reqRequests
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */

    public List<RegResponseInfo> bulkRegisterStudentsToCourse(
            @WebParam(name = "reqRequests") List<RegRequestInfo> reqRequests,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

  
    /**
     * Removes a student from a waitlist for a course.
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
    public RegResponseInfo removeStudentFromWaitlist(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;
    
    /**
     * Gets a waitlist entry by id.
     * 
     * @param courseWaitlistEntryId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistEntryInfo getWaitlistEntryById(
            @WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Removes a student from a waitlist for a course.
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
    public RegResponseInfo updateWaitlistEntry(
            @WebParam(name = "reqRequestInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a waitlist entry
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
    public RegResponseInfo deleteWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the waitlist for a course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistInfo getWaitlistForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the waitlist for a course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistInfo getWaitlistForCourseOfferings(
            @WebParam(name = "courseOfferingIds") List<String> courseOfferingIds,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * TODO review Get all the waitlist the student is in for a term. Returns
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
    public List<CourseWaitlistEntryInfo> getWaitlistsForStudentByTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    

    /**
     * Gets a waitlist by id.
     * 
     * @param courseWaitlistId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistInfo getWaitlistById(@WebParam(name = "courseWaitlistId") String courseWaitlistId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration by ID.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public CourseRegistrationInfo getCourseRegistration(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration for a student.
     * 
     * @param studentId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration for a student for a term.
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
     * Gets course registrations by course offering id. TODO REVIEW do we need
     * different kinds of students as part of the input?
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
    public RegRequestInfo getCourseRegRequestForCourseRegn(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
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
    public ActivityRegistrationInfo getActivityRegistration(
            @WebParam(name = "activityRegistrationId") String activityRegistrationId,
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
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentForTerm(
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

    public List<CourseRegistrationInfo> searchForCourseRegistrations(CriteriaInfo criteria);

    public List<String> searchForCourseOfferingRegistrationIds(CriteriaInfo criteria);

    public List<ActivityRegistrationInfo> searchForActivityRegistrations(CriteriaInfo criteria);

    public List<String> searchForActivityRegistrationIds(CriteriaInfo criteria);

    public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(CriteriaInfo criteria);

    public List<String> searchForRegGroupRegistrationIds(CriteriaInfo criteria);

    public List<CourseWaitlistInfo> searchForCourseWaitlists(CriteriaInfo criteria);

    public List<String> searchForCourseWaitlistIds(CriteriaInfo criteria);

    public List<CourseWaitlistInfo> searchForCourseWaitlistEntries(CriteriaInfo criteria);

    public List<CourseWaitlistInfo> searchForCourseWaitlistEntryIds(CriteriaInfo criteria);

}
