/**
 */
package org.kuali.student.enrollment.registration.course.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.exemption.dto.ExemptionRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.registration.course.dto.RegResponseInfo;
import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlist;
import org.kuali.student.enrollment.waitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.CourseWaitlistInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

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
     */
    public RegResponseInfo canRegister(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Holds, student Status, not term specific Check to see if student can
     * register
     **/
    /**
     * 
     */
    public RegResponseInfo canBuildRegRequestForTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * 
     */
    public KeyDateInfo getAppointmentWindow(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * TODO - follow up if we need this at all
     */
    // public String canAccessRegRequest(String studentId, String termId);

    /**
     * 
     */
    public RegResponseInfo checkEligibilityOfStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * Return the possible reg groups for the course offering, return a
     * RegGroupInfo
     */

    public String getRegGroupsForOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * Check if student satisfies all eligibilities for this particular reg
     * group.
     */
    public RegRequestItemInfo checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context);

    /**
     * Returns the seat count for the course
     */
    public String getAllOpenSeatCount(@WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * Similar to the one above but evaluates the students eligibility for the
     * open seats. Sometimes, the number of open seats mightnot be available to
     * all the students in the course.
     */
    public String getSeatCountForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context);

    /**
     * Request exception for a course
     */
    public String requestExceptionForCourseRegistration(
            @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * Returns the eligible seat pools that the student can be in for the reg
     * group, will be in one in most cases.
     */
    public List<String> getEligibleSeatPools(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context);

    /**
     * 
     */
    public String getAvailablebleSeatsInSeatpool(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "seatpoolId") String seatpoolId, @WebParam(name = "context") ContextInfo context);

    /**
     * - maybe another service (call NotificationService at class 1, where would
     * messages be stored?)
     */
    public List<String> getRegAlertsForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * (get details of the hold and the issue - can we call hold service ?);
     */
    public List<String> getRegHoldsForStudent(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * Get/Create Reg Request
     */
    public RegRequestInfo createRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * cannot change state, cannot update submitted requests. Check the request
     * state and update it with the input regReuestInfo values. The id of the
     * {@link RegRequestInfo} will be used from the determined from the object
     * passedin as parameter.
     */
    public RegRequestInfo updateRegRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * Cancel the reg request - set the state as CANCELLED instead of actually
     * deleting it. The reg requests can be kept in the system for reporting or
     * auditing purposes.
     * 
     * @param reqRequestId
     * @param context
     * @return
     */
    public StatusInfo deleteRegRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * 
     */
    public RegRequestInfo getRegRequest(@WebParam(name = "reqRequestId") String reqRequestId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentId
     * @param termId
     * @param requestState
     * @return
     */
    public List<RegRequestInfo> getRegRequestForStuByTerm(@WebParam(name = "requestStates") List<String> requestStates,
            @WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * 
     */
    public RegRequestInfo validateReqRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    // /**
    // *
    // */
    // public String getRegRequests(String studentId, String termId);
    //
    // /**
    // *
    // */
    // public String getOpenRegRequests(String studentId, String termId);

    /**
     * 
     */
    public RegRequestInfo createNewReqRequestFromExisting(
            @WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * OpTypes :: ADD - regGroupId, canWaitlist, canDropList, gradingOptiosn,
     * credit DROP - regGroupId UPDATE - regGroupId, gradingOptions,
     * creditOptions
     */
    public RegRequestInfo submitReqRequest(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param reqRequests
     * @param context
     * @return
     */
    public String bulkRegisterStudentsToCourse(@WebParam(name = "reqRequests") List<RegRequestInfo> reqRequests,
            @WebParam(name = "context") ContextInfo context);

    // Managing Waitist

    /**
     * This method ...
     * 
     * @param courseOfferingId
     * @param regGroupId
     * @return
     */
    public CourseWaitlistInfo initializeWaitlistForCourse(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "regGroupIds") List<String> regGroupIds, @WebParam(name = "context") ContextInfo context);

  /**
   * 
   * This method ...
   * 
   * @param reqRequestInfo
   * @param context
   * @return
   */
    public CourseWaitlistEntryInfo addStudentToWaitlist(
            @WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

   /**
    * 
    * This method ...
    * 
    * @param regRequests
    * @param context
    * @return
    */
    public String addStudentsToWaitlistForCourse(@WebParam(name = "reqRequests") List<RegRequestInfo> regRequests,
            @WebParam(name = "context") ContextInfo context);

    /**
     * 
     * This method ...
     * 
     * @param reqRequestInfo
     * @param context
     * @return
     */
    public String removeStudentFromWaitlist(@WebParam(name = "reqRequestInfo") RegRequestInfo reqRequestInfo,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentIds
     * @param courseWaitlistId
     * @param context
     * @return
     */
    public String removeStudentsFromWaitlistForCourse(@WebParam(name = "studentIds") List<String> studentIds,
            @WebParam(name = "courseWaitlistId") String courseWaitlistId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentIds
     * @param oldWaitListId
     * @param newWaitlistId
     * @param context
     * @return
     */
    public String moveStudentsToNewWaitlist(@WebParam(name = "studentIds") List<String> studentIds,
            @WebParam(name = "sourceWaitListId") String oldWaitListId,
            @WebParam(name = "targetWaitlistId") String newWaitlistId, @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentIds
     * @param context
     * @return
     */
    public String bulkRegisterWaitlistedStuToCourse(@WebParam(name = "studentIds") List<String> studentIds,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param courseOfferingId
     * @param context
     * @return
     */
    public CourseWaitlistInfo getWaitlistForCourse(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param departmentId
     * @param termId
     * @param context
     * @return
     */
    public List<CourseWaitlistInfo> getWaitlistsByDeptForTerm(@WebParam(name = "departmentId") String departmentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     */
    public List<CourseWaitlistEntryInfo> getWaitlistsForStudentByTerm(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context);

    /**
     * This method ...
     * 
     * @param courseWaitlistEntryId
     * @param context
     * @return
     */
    public CourseWaitlistEntryInfo getWaitlistEntryById(
            @WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "context") ContextInfo context);

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

    public List<CourseRegistrationInfo> searchForCourseOfferings(CriteriaInfo criteria);

    public List<String> searchForCourseOfferingIds(CriteriaInfo criteria);

}
