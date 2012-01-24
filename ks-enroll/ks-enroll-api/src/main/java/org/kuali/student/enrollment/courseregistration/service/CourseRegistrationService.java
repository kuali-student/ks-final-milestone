/**
 */
package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
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
public interface CourseRegistrationService  {

    /**
     * Checks if a student can register at all i.e., checks if the students
     * current academic status allows them to register. This is more generic
     * operation and doesn't take in the term information.
     * <p>
     * Implementation notes: Checks high-level conditions required for
     * registration e.g. student admitted,in good standing , alive etc.
     * 
     * @param studentId Identifier of the student
     * @param context
     * @return list of errors, warnings or informational messages
     * @throws DoesNotExistException If student id does not exist student id not
     *             found
     * @throws InvalidParameterException Invalid student id in the input
     * @throws MissingParameterException Student id missing in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException
     */
    public  List<ValidationResultInfo> checkStudentEligibility(@WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks the eligibility of a student to register given the term. If the
     * student is eligible for a term, then they can build reg cart for the
     * term.
     * <p>
     * Implementation notes: Check term eligibility for the student e.g.
     * exemptions and no holds for that term on the student
     * 
     * @param studentId Identifier of the student
     * @param termId The unique key for the term
     * @param context
     * @return list of errors, warnings or informational messages
     * @throws InvalidParameterException Invalid student id or term id
     * @throws MissingParameterException Student id or term id missing in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the appointment windows for a term that a student can register in.
     * <p>
     * Implementation notes: Return multiple {@link DateRangeInfo} for possible
     * appointment windows.
     * 
     * @param studentId Identifier of the student
     * @param termId The unique key for the term
     * @param context
     * @return
     * @throws InvalidParameterException Invalid student id or term id
     * @throws MissingParameterException Student id or term id missing in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<DateRangeInfo> getAppointmentWindows(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular course
     * offering.
     * <p>
     * Implementation notes: This operation does course requirements,
     * eligibility rules, prerequisite and corequisite checks for course
     * offering eligibility. Doesn't do any seat restriction checks.
     * 
     * @param studentId Identifier of the student
     * @param courseOfferingId Identifier of the course offering
     * @param context
     * @return
     * @throws InvalidParameterException Invalid student or course offering id
     * @throws MissingParameterException Missing student or course offering id
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular
     * registration group. Returns a {@link List} of
     * {@link ValidationResultInfo}. When a student is eligible the {@link List}
     * contains a single {@link ValidationResultInfo} with error level OK. Also
     * returns info on expiring restrictions as part of the message.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context
     * @return
     * @throws InvalidParameterException Invalid student id or regGroupId
     * @throws MissingParameterException Missing student id or regGroupId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the registration groups for a course offering from the
     * CourseOfferingService and then filters out the registration groups the
     * student is not eligible for using checkEligibilityForRegGroup. It does
     * the eligibility checks without the seat pool availability.
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException Invalid studentId or courseOfferingId
     * @throws MissingParameterException Missing studentId or courseOfferingId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular term. This
     * information can be used to display in the cart. this
     * 
     * @param studentId Identifier of the student
     * @param termId Unique key of the term
     * @param context
     * @return
     * @throws InvalidParameterException Invalid termId or studentId in the
     *             input
     * @throws MissingParameterException Missing termId or studentId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public LoadInfo calculateCreditLoadForTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular registration
     * request. It also adds up the credits for courses the student has
     * registered for already in the given term to the registration request and
     * returns the total.
     * 
     * @param studentId Id of the student
     * @param regRequestInfo Registration request info
     * @param context
     * @return
     * @throws InvalidParameterException Invalid student id or
     *             {@link RegRequestInfo}
     * @throws MissingParameterException Missing student id or
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to calculate the credit
     *             load for the student
     */
    public LoadInfo calculateCreditLoadForRegRequest(@WebParam(name = "studentId") String studentId, @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the open seat count for a particular course offering. It sums
     * up the open seats for individual registration groups under the same
     * course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException Invalid courseOfferingId in the input
     * @throws MissingParameterException Missing courseOfferingId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get available seats for the registration group.
     * 
     * @param regGroupId Identifier of the registration group
     * @param context
     * @return
     * @throws InvalidParameterException Invalid regGroupId in the input
     * @throws MissingParameterException Missing regGroupId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the number of seats available for a particular student in a
     * registration group.
     * <p>
     * Implementation notes : Seats available for a student taking seat pool (if
     * any) into consideration.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context
     * @return
     * @throws InvalidParameterException Invalid studentId or regGroupId in the
     *             input
     * @throws MissingParameterException Missing studentId or regGroupId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForStudentInRegGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Returns the available seats in a particular seat pool. This is an admin
     * support function to check the seat pool usage.
     * 
     * @param studentId Identifier of the student
     * @param seatpoolId Identifier of the seatpool
     * @param context
     * @return
     * @throws InvalidParameterException Invalid seatpool in the input
     * @throws MissingParameterException Missing parameter seatpoolId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsInSeatpool(@WebParam(name = "seatpoolId") String seatpoolId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a registration request for a student.
     * <p>
     * Implementation Notes : This operation does the following steps:
     * <ul>
     * <li>Validate the data in the regRequestInfo parameter. If invalid throw
     * an exception.
     * <li>Create an id and persist the registration request.
     * <li>Return the updated registration request.
     * <li>Throw an AlreadyExistsException when there is an existing request by
     * the same requesting person for a term in DRAFT state.
     * 
     * @param regRequestInfo The registration request object to be created
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException Invalid data in the create request
     * @throws InvalidParameterException Invalid parameter
     *             {@link RegRequestInfo} in the input
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo} in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public RegRequestInfo createRegRequest(@WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Check the request state and if its in DRAFT, updates it with the input
     * {@link RegRequestInfo} values. The id is fetched from the
     * {@link RegRequestInfo} in the parameter. If the state is not valid, throw
     * a {@link DataValidationErrorException}.
     * <p>
     * Implementation notes:This method shouldn't update the state of a
     * registration request since that can be done as part of any transaction
     * only. This operation will be called to save a registration cart after
     * changes e.g addition or deletion of courses.
     * 
     * @param regRequestInfo The registration request object to be saved or
     *            updated
     * @param context
     * @return
     * @throws DataValidationErrorException The {@link RegRequestInfo} is not a
     *             valid request
     * @throws InvalidParameterException Invalid regRequestId in the input
     * @throws MissingParameterException or {@link RegRequestInfo} in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public RegRequestInfo updateRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Delete the registration request from the database. There is permission
     * restriction and only administrative users should be allowed to delete
     * registration requests that are not in draft state.
     * 
     * @param regRequestId Identifier of registration request
     * @param context
     * @return
     * @throws InvalidParameterException Invalid regRequestId in the input
     * @throws MissingParameterException Missing parameter regRequestId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     * @throws DoesNotExistException
     */
    public StatusInfo deleteRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Validate a registration request to see that there are no conflicting or
     * invalid/ in-eligible registration groups in the request after each
     * modification or when finally submitting or saving it.
     * 
     * @param regRequestInfo The registration request to be validated
     * @param context
     * @return
     * @throws DataValidationErrorException Invalid {@link RegRequestInfo} in
     *             the input
     * @throws InvalidParameterException Invalid {@link RegRequestInfo} in the
     *             input
     * @throws InvalidParameterException Invalid fields e.g, regRequestId or
     *             regGroupId in the {@link RegRequestInfo}
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public List<ValidationResultInfo> validateRegRequest(@WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies a registration request to make sure that the data in the
     * {@link RegRequestInfo} satisfies the rules to be a valid registration
     * request. This includes course pre-requisites or co-requisites and credit
     * load rules.
     * <p>
     * Implementation notes: Call the following methods as part of the
     * eligibility checks - checkStudentEligibilityForTerm,
     * checkStudentEligibiltyForRegGroup,
     * checkStudentEligibiltyForCourseOffering,
     * getAvailableSeatsForStudentInRegGroup.
     * 
     * @param regRequestInfo The registration request to be verified
     * @param context
     * @return
     * @throws DataValidationErrorException Invalid data in
     *             {@link RegRequestInfo}
     * @throws InvalidParameterException Invalid fields e.g, regRequestId or
     *             regGroupId in the {@link RegRequestInfo}
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public List<ValidationResultInfo> verifyRegRequest(@WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Same as above but takes in a reg request id assuming that the
     * {@link RegRequestInfo} is already saved in the database.
     * <p>
     * 
     * @param regRequestInfo The saved registration request to be verified
     * @param context
     * @return
     * @throws DataValidationErrorException Invalid data in
     *             {@link RegRequestInfo}
     * @throws InvalidParameterException Invalid fields e.g, regRequestId or
     *             regGroupId in the {@link RegRequestInfo}
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public RegResponseInfo verifySavedReqRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new registration request from an existing registration request.
     * Convenient when a transaction fails and a students wants to rebuild the
     * registration cart with earlier items. It can also be used in any such
     * other scenario where a registration request has passed one of the final
     * states (or is canceled) and a new registration request needs to be
     * created for re-initiating the transaction.
     * 
     * @param existingRegRequestId The exiting req request id from which the new
     *            one is created
     * @param context
     * @return
     * @throws DoesNotExistException The existingRegRequestId does not exist
     * @throws InvalidParameterException Invalid field existingRegRequestId
     * @throws MissingParameterException Missing parameter existingRegRequestId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public RegRequestInfo createRegRequestFromExisting(@WebParam(name = "existingRegRequestId") String existingRegRequestId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Fetches the {@link RegRequestInfo}, validates and checks eligibility
     * against multiple rules and then submits the {@link RegRequestInfo}. The
     * latest {@link RegRequestInfo} should already be saved before this
     * operation is called. This operation also handles dropping courses or
     * waitlisting or putting a student in hold-until list if the requested
     * course is full and if the student is is okay to be put in these
     * lists.This method is transactional and for multiple registration request
     * items, each need to succeed or else the overall registration transaction
     * fails; the successful transactions are rolled back. A failure occurs when
     * for any reg group in the request registration, drop, swap, waitlisting,
     * hold until-listing or exception-listing cannot be completed successfully.
     * This operation calls verifyRegRequest to make sure that the request is
     * valid before starting the transaction.
     * 
     * @param regRequestInfo The {@link RegRequestInfo} to be submitted for
     *            registration process.
     * @param context
     * @return
     * @throws DoesNotExistException The regRequestId does not exist
     * @throws DataValidationErrorException Invalid data in the
     *             {@link RegRequestInfo}
     * @throws InvalidParameterException Invalid id regRequestId
     * @throws MissingParameterException Missing regRequestId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     * @throws AlreadyExistsException When the reg request is already submitted
     */
    public RegResponseInfo submitRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, AlreadyExistsException;

    /**
     * Bulk operation to drop all students from a reg group if it gets canceled.
     * TODO This is WIP now, DO NOT IMPLEMENT
     * 
     * @param regGroupIdList
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo dropStudentsFromRegGroups(@WebParam(name = "regGroupIdList") List<String> regGroupIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Bulk operation to move all students between source and destination reg
     * groups in case a reg group gets canceled. TODO This is WIP now, DO NOT
     * IMPLEMENT
     * 
     * @param sourceRegGroupId
     * @param destinationRegGroupId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo moveStudentsBetweenRegGroups(@WebParam(name = "sourceRegGroupId") String sourceRegGroupId, @WebParam(name = "destinationRegGroupId") String destinationRegGroupId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Set the state of the registration request to canceled. This operation
     * would be used instead of delete when the registration request might need
     * to be stored and not deleted for reporting purposes. Scenarios are
     * request for a course that was canceled later etc. Throws an exception if
     * a registration request is already in a success or failure state post
     * completion of the transaction.
     * 
     * @param regRequestId The regRequestId to be canceled
     * @param context
     * @return
     * @throws InvalidParameterException Invalid id regRequestId
     * @throws MissingParameterException Missing regRequestId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public StatusInfo cancelRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a registration request by id.
     * 
     * @param regRequestId The regRequestId to be retrieved
     * @param context
     * @return
     * @throws DoesNotExistException No {@link RegRequestInfo} found for the id.
     * @throws InvalidParameterException Invalid id regRequestId
     * @throws MissingParameterException Missing regRequestId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public RegRequestInfo getRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a registration requests by id list.
     * 
     * @param regRequestId
     * @param context
     * @return
     * @throws DoesNotExistException No regRequestId found for one of the
     *             regRequestIds
     * @throws InvalidParameterException Invalid regRequestId in regRequestIds
     *             list
     * @throws MissingParameterException Missing regRequestIds in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<RegRequestInfo> getRegRequestsByIdList(@WebParam(name = "regRequestIds") List<String> regRequestIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the registration requests for a student by term and student id.
     * Additionally the state of the registration request can also be passed so
     * that only requests in certain states are returned.
     * 
     * @param requestStates A list of state for the {@link RegRequestInfo} to be
     *            retrieved. This is optional
     * @param studentId Id of the student
     * @param termId Key of the term
     * @param context
     * @return
     * @throws DoesNotExistException No {@link RegRequestInfo} found for the
     *             input parameters
     * @throws InvalidParameterException Invalid studentId, termId or request
     *             state
     * @throws MissingParameterException Missing studentId or termId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<RegRequestInfo> getRegRequestsForStudentByTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "requestStates") List<String> requestStates, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a course waitlist entry by id.
     * 
     * @param courseWaitlistEntryId Id of the course waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException No courseWaitlistEntryId exists
     * @throws InvalidParameterException Invalid courseWaitlistEntryId
     * @throws MissingParameterException Missing courseWaitlistEntryId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public CourseWaitlistEntryInfo getCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a course waitlist entry
     * 
     * @param courseWaitlistEntryId Id of the course waitlist entry to be
     *            updated
     * @param courseWaitlistEntryInfo The modified
     *            {@link CourseWaitlistEntryInfo}
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException The courseWaitlistEntryInfo is not
     *             valid
     * @throws InvalidParameterException Invalid courseWaitlistEntryId or
     *             courseWaitlistEntryInfo in the input
     * @throws MissingParameterException Missing courseWaitlistEntryId or
     *             courseWaitlistEntryInfo in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo updateCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "courseWaitlistEntryInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Reorder all the entries that are passed in in the input list, i.e.,
     * update each of the entries rank to begin from the top and push the
     * entries not in the list to the ranks after the entries.
     * 
     * @param courseWaitlistEntryId
     * @param position
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException Invalid courseWaitlistEntryIds in the
     *             input
     * @throws MissingParameterException Missing courseWaitlistEntryIdsin the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo reorderCourseWaitlistEntries(@WebParam(name = "courseWaitlistEntryIds") List<String> courseWaitlistEntryIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Insert a waitlist entry at a particular position in the waitlist. The
     * courseWaitlistEntryId would be moved to the position and all other
     * waitlist entries for that reg group would have adjusted rank
     * 
     * @param courseWaitlistEntryId The id of the course waitlist entry
     * @param position The new rank for the waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException The courseWaitlistEntryId is not found
     * @throws InvalidParameterException The courseWaitlistEntryId is invalid
     * @throws MissingParameterException Input courseWaitlistEntryId or position
     *             is missing
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo insertCourseWaitlistEntryAtPosition(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "position") Integer position,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Remove the {@link CourseWaitlistEntryInfo}, change its state to CANCELLED
     * 
     * @param courseWaitlistEntryId The id of the course waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException The courseWaitlistEntryId is not found
     * @throws InvalidParameterException The courseWaitlistEntryId is invalid
     * @throws MissingParameterException Input courseWaitlistEntryId or position
     *             is missing
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo removeCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a course waitlist entry
     * 
     * @param courseWaitlistEntryId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a course waitlist entry.
     * 
     * @param validateTypeKey
     * @param courseWaitlistEntryInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo validateCourseWaitlistEntry(@WebParam(name = "validateTypeKey") String validateTypeKey,
            @WebParam(name = "courseWaitlistEntryInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Register a student to a reg group from a waitlist.
     * 
     * @param regRequestInfo
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegResponseInfo registerStudentFromWaitlist(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

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
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registration by ID.
     * 
     * @param courseRegistrationId
     * @param context
     * @return
     */
    public CourseRegistrationInfo getCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * @param courseRegistrationIds
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByIdList(@WebParam(name = "courseRegistrationIds") List<String> courseRegistrationIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DisabledIdentifierException
     */
    public CourseRegistrationInfo getActiveCourseRegistrationForStudentByCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DisabledIdentifierException;

    /**
     * This method ...
     * 
     * @param studentId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DisabledIdentifierException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(@WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException;

    /**
     * This method ...
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DisabledIdentifierException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DisabledIdentifierException;

    /**
     * Gets the course registrations for a student by term. Note: not clear if
     * gets the registrations in just the specified term or that term and all
     * included terms. For example: if you ask for the "fall term" do you get
     * registrations for the mini-mesters within that term.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DisabledIdentifierException
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DisabledIdentifierException;

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

    public List<CourseRegistrationInfo> getActiveCourseRegistrationsByCourseOfferingId(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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

    public List<CourseRegistrationInfo> getDroppedCourseRegistrationsByCourseOfferingId(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<RegRequestInfo> getRegRequestsForCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<RegRequestInfo> getRegRequestsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} object.
     * 
     * @param criteria
     * @return
     */
    public List<CourseRegistrationInfo> searchForCourseRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} ids.
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForCourseOfferingRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForActivityRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForRegGroupRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForCourseWaitlistEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;;

}
