/**
 */
package org.kuali.student.enrollment.registration.course.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.exemption.dto.ExemptionInfo;
import org.kuali.student.enrollment.exemption.dto.ExemptionRequestInfo;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.registration.course.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.registration.course.dto.RegResponseInfo;
import org.kuali.student.enrollment.waitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.CourseWaitlistInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
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
    public RegResponseInfo checkEligibilityOfStudent(@WebParam(name = "studentId") String studentId,
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
    public RegRequestItemInfo checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Gets the registration groups for a course offering from the
     * CourseOfferingService and then filters out the registration groups the
     * student is not eligible for.
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
    public String getAllOpenSeatCount(@WebParam(name = "regGroupId") String regGroupId,
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
    public String getSeatCountForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Request an exception for the course registration.
     * 
     * @param exemptionRequestInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public ExemptionInfo requestExemptionForCourseRegistration(
            @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Returns the eligible seat pools that the student can be in for the
     * registration group, will be in one in most cases.
     * 
     * @param studentId
     * @param regGroupId
     * @param context
     * @return
     */
    public List<SeatPoolDefinitionInfo> getEligibleSeatPools(@WebParam(name = "studentId") String studentId,
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
    public String getAvailablebleSeatsInSeatpool(@WebParam(name = "studentId") String studentId,
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
    public RegRequestInfo updateRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
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
    public RegRequestInfo createNewReqRequestFromExisting(
            @WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Submits a reg request for a course or multiple courses. This operation
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
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegRequestInfo submitReqRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Similar to submit but a different method specially for swapping a reg
     * group for another. Steps in swapping are: 1. Check if the request is
     * valid. 2. Check if there are two items only - one ADD and one DROP. 3.
     * For the reg group which is of DROP type, check if the student has already
     * registered in that reg group. 4. Do other necessary functions (same as
     * submitReqRequest()) that are part of either registering or dropping a
     * course. 4. Try to fulfill the ADD request first, and then if that
     * transaction is successful, process the DROP request.
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
    public RegRequestInfo swapCourses(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * An admin operation to register or drop students as a group to/from a
     * course. THe RegRequestInfo will contain only one item in this case. This
     * method is not transactional, so if registration fails for some students,
     * then rest of the registration will continue processing.
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

    public List<RegRequestInfo> bulkProcessRegRequestsForCourse(
            @WebParam(name = "reqRequests") List<RegRequestInfo> reqRequests,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Initializes a waitlist taking in data defined for waitlist in
     * CourseOffering or RegistrationGroup. Creates as many waitlist as the
     * registration groups passed in the input. The waitlist types for each of
     * the registration group waitlist are taken from the registration group
     * definition. Validates that all the registration groups that are passed in
     * the parameter are for the same course offering and that a waitlist can be
     * created for the offering.
     * 
     * @param courseOfferingId
     * @param regGroupIds
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistInfo initializeWaitlistForCourse(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "regGroupIds") List<String> regGroupIds, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Separate operation to add a student to a waitlist directly. The
     * RegRequestInfo must contains RegRequestItems of type ADD and okToWaitlist
     * set to true for this operation to continue. This is a transactional
     * operation and fails if one of the items is not processed succesfully. The
     * number of CourseWaitlistEntryInfo in the return list will be equal to
     * thenumber of regRequestItems in the request.
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
    public List<RegRequestInfo> addStudentToWaitlist(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Bulk operation which is same as above for admin users to put a group of
     * students in a waitlist.
     * 
     * @param regRequests
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo addStudentsToWaitlistForCourse(@WebParam(name = "reqRequests") List<RegRequestInfo> regRequests,
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
    public RegRequestInfo removeStudentFromWaitlist(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * An admin operation to bulk remove students from a waitlist.
     * 
     * @param studentIds
     * @param courseWaitlistId
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo removeStudentsFromWaitlistForCourse(@WebParam(name = "studentIds") List<String> studentIds,
            @WebParam(name = "courseWaitlistId") String courseWaitlistId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Move students to a new waitlist - an admin operation.
     * 
     * @param studentIds
     * @param oldWaitListId
     * @param newWaitlistId
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo moveStudentsToNewWaitlist(@WebParam(name = "studentIds") List<String> studentIds,
            @WebParam(name = "sourceWaitListId") String oldWaitListId,
            @WebParam(name = "targetWaitlistId") String newWaitlistId, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * An admin function to register all waitlisted student to a course
     * 
     * @param studentIds
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestInfo> bulkRegisterWaitlistedStuToCourse(
            @WebParam(name = "studentIds") List<String> studentIds, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
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
    public CourseWaitlistInfo getWaitlistForCourse(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets all the waitlists for a department by term.
     * 
     * @param departmentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistInfo> getWaitlistsByDepartmentForTerm(
            @WebParam(name = "departmentId") String departmentId, @WebParam(name = "termId") String termId,
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
    public List<CourseWaitlistEntryInfo> getWaitlistsForStudentByTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

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
    public CourseWaitlistEntryInfo getWaitlistById(@WebParam(name = "courseWaitlistId") String courseWaitlistId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public CourseRegistrationInfo getCourseRegistration(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentId
     * @param context
     * @return
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param courseOfferingId
     * @param context
     * @return
     */
    public List<CourseRegistrationInfo> getCourseRegnByCourseOfferingId(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public List<RegRequestInfo> getCourseRegRequestsByCourseRegn(
            @WebParam(name = "courseRegistrationId") String courseRegistrationId,
            @WebParam(name = "context") ContextInfo context);

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
