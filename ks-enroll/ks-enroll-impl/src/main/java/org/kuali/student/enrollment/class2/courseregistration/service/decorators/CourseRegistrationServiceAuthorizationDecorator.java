package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
//import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationServiceDecorator;
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
import org.kuali.student.r2.common.infc.HoldsPermissionService;

public class CourseRegistrationServiceAuthorizationDecorator extends CourseRegistrationServiceDecorator implements HoldsPermissionService{
    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "CourseRegistrationService.";
    
	private PermissionService permissionService;
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	public  List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibility", null)) {
	        return getNextDecorator().checkStudentEligibility(studentId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibilityForTerm(
			String studentId, String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibilityForTerm", null)) {
	        return getNextDecorator().checkStudentEligibilityForTerm(studentId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<DateRangeInfo> getAppointmentWindows(String studentId,
			String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAppointmentWindows", null)) {
	        return getNextDecorator().getAppointmentWindows(studentId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibiltyForCourseOffering", null)) {
	        return getNextDecorator().checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibiltyForRegGroup(
			String studentId, String regGroupId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibiltyForRegGroup", null)) {
	        return getNextDecorator().checkStudentEligibiltyForRegGroup(studentId, regGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getEligibleRegGroupsForStudentInCourseOffering", null)) {
	        return getNextDecorator().getEligibleRegGroupsForStudentInCourseOffering(studentId, courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LoadInfo calculateCreditLoadForTerm(String studentId,
			String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateCreditLoadForTerm", null)) {
	        return getNextDecorator().calculateCreditLoadForTerm(studentId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LoadInfo calculateCreditLoadForRegRequest(String studentId,
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateCreditLoadForRegRequest", null)) {
	        return getNextDecorator().calculateCreditLoadForRegRequest(studentId, regRequestInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Integer getAvailableSeatsForCourseOffering(String courseOfferingId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForCourseOffering", null)) {
	        return getNextDecorator().getAvailableSeatsForCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Integer getAvailableSeatsForRegGroup(String regGroupId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForRegGroup", null)) {
	        return getNextDecorator().getAvailableSeatsForRegGroup(regGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Integer getAvailableSeatsForStudentInRegGroup(String studentId,
			String regGroupId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForStudentInRegGroup", null)) {
	        return getNextDecorator().getAvailableSeatsForStudentInRegGroup(studentId, regGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Integer getAvailableSeatsInSeatpool(String seatpoolId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsInSeatpool", null)) {
	        return getNextDecorator().getAvailableSeatsInSeatpool(seatpoolId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegRequestInfo createRegRequest(RegRequestInfo regRequestInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createRegRequest", null)) {
	        return getNextDecorator().createRegRequest(regRequestInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegRequestInfo updateRegRequest(String regRequestId,
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegRequest", null)) {
	        return getNextDecorator().updateRegRequest(regRequestId, regRequestInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteRegRequest(String regRequestId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DoesNotExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteRegRequest", null)) {
	        return getNextDecorator().deleteRegRequest(regRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateRegRequest(
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateRegRequest", null)) {
	        return getNextDecorator().validateRegRequest(regRequestInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> verifyRegRequest(
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "verifyRegRequest", null)) {
	        return getNextDecorator().verifyRegRequest(regRequestInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegResponseInfo verifySavedReqRequest(String regRequestId,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "verifySavedReqRequest", null)) {
	        return getNextDecorator().verifySavedReqRequest(regRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegRequestInfo createRegRequestFromExisting(
			String existingRegRequestId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DoesNotExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createRegRequestFromExisting", null)) {
	        return getNextDecorator().createRegRequestFromExisting(existingRegRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegResponseInfo submitRegRequest(String regRequestId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, AlreadyExistsException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "submitRegRequest", null)) {
	        return getNextDecorator().submitRegRequest(regRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegResponseInfo dropStudentsFromRegGroups(
			List<String> regGroupIdList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "dropStudentsFromRegGroups", null)) {
	        return getNextDecorator().dropStudentsFromRegGroups(regGroupIdList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegResponseInfo moveStudentsBetweenRegGroups(
			String sourceRegGroupId, String destinationRegGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "moveStudentsBetweenRegGroups", null)) {
	        return getNextDecorator().moveStudentsBetweenRegGroups(sourceRegGroupId, destinationRegGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo cancelRegRequest(String regRequestId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "cancelRegRequest", null)) {
	        return getNextDecorator().cancelRegRequest(regRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegRequestInfo getRegRequest(String regRequestId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequest", null)) {
	        return getNextDecorator().getRegRequest(regRequestId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegRequestInfo> getRegRequestsByIdList(
			List<String> regRequestIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsByIdList", null)) {
	        return getNextDecorator().getRegRequestsByIdList(regRequestIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForStudentByTerm(
			String studentId, String termId, List<String> requestStates,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForStudentByTerm", null)) {
	        return getNextDecorator().getRegRequestsForStudentByTerm(studentId, termId, requestStates, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseWaitlistEntryInfo getCourseWaitlistEntry(
			String courseWaitlistEntryId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntry", null)) {
	        return getNextDecorator().getCourseWaitlistEntry(courseWaitlistEntryId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo updateCourseWaitlistEntry(String courseWaitlistEntryId,
			CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCourseWaitlistEntry", null)) {
	        return getNextDecorator().updateCourseWaitlistEntry(courseWaitlistEntryId, courseWaitlistEntryInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo reorderCourseWaitlistEntries(
			List<String> courseWaitlistEntryIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "reorderCourseWaitlistEntries", null)) {
	        return getNextDecorator().reorderCourseWaitlistEntries(courseWaitlistEntryIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo insertCourseWaitlistEntryAtPosition(
			String courseWaitlistEntryId, Integer position, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "insertCourseWaitlistEntryAtPosition", null)) {
	        return getNextDecorator().insertCourseWaitlistEntryAtPosition(courseWaitlistEntryId, position, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo removeCourseWaitlistEntry(String courseWaitlistEntryId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeCourseWaitlistEntry", null)) {
	        return getNextDecorator().removeCourseWaitlistEntry(courseWaitlistEntryId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteCourseWaitlistEntry(String courseWaitlistEntryId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCourseWaitlistEntry", null)) {
	        return getNextDecorator().deleteCourseWaitlistEntry(courseWaitlistEntryId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo validateCourseWaitlistEntry(String validateTypeKey,
			CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateCourseWaitlistEntry", null)) {
	        return getNextDecorator().validateCourseWaitlistEntry(validateTypeKey, courseWaitlistEntryInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegResponseInfo registerStudentFromWaitlist(
			String courseWaitlistEntryId, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "registerStudentFromWaitlist", null)) {
	        return getNextDecorator().registerStudentFromWaitlist(courseWaitlistEntryId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForCourseOffering", null)) {
	        return getNextDecorator().getCourseWaitlistEntriesForCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(
			String regGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForRegGroup", null)) {
	        return getNextDecorator().getCourseWaitlistEntriesForRegGroup(regGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(
			String courseOfferingId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	     if (null == context) {
	            throw new MissingParameterException();
	        }
	           
	        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForStudentInCourseOffering", null)) {
		        return getNextDecorator().getCourseWaitlistEntriesForStudentInCourseOffering(courseOfferingId, studentId, context);
	        }
	        else {
	           throw new PermissionDeniedException();
	        }
	}

	@Override
	public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(
			String regGroupId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntryForStudentInRegGroup", null)) {
	        return getNextDecorator().getCourseWaitlistEntryForStudentInRegGroup(regGroupId, studentId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForStudentByTerm", null)) {
	        return getNextDecorator().getCourseWaitlistEntriesForStudentByTerm(studentId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseRegistrationInfo getCourseRegistration(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistration", null)) {
	        return getNextDecorator().getCourseRegistration(courseRegistrationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByIdList(
			List<String> courseRegistrationIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsByIdList", null)) {
	        return getNextDecorator().getCourseRegistrationsByIdList(courseRegistrationIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseRegistrationInfo getActiveCourseRegistrationForStudentByCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActiveCourseRegistrationForStudentByCourseOffering", null)) {
	        return getNextDecorator().getActiveCourseRegistrationForStudentByCourseOffering(studentId, courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(
			String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudent", null)) {
	        return getNextDecorator().getCourseRegistrationsForStudent(studentId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudentByCourseOffering", null)) {
	        return getNextDecorator().getCourseRegistrationsForStudentByCourseOffering(studentId, courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudentByTerm", null)) {
	        return getNextDecorator().getCourseRegistrationsForStudentByTerm(studentId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getActiveCourseRegistrationsByCourseOfferingId(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActiveCourseRegistrationsByCourseOfferingId", null)) {
	        return getNextDecorator().getActiveCourseRegistrationsByCourseOfferingId(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> getDroppedCourseRegistrationsByCourseOfferingId(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDroppedCourseRegistrationsByCourseOfferingId", null)) {
	        return getNextDecorator().getDroppedCourseRegistrationsByCourseOfferingId(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseRegistration(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseRegistration", null)) {
	        return getNextDecorator().getRegRequestsForCourseRegistration(courseRegistrationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseOffering", null)) {
	        return getNextDecorator().getRegRequestsForCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(
			String courseOfferingId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseOfferingByStudent", null)) {
	        return getNextDecorator().getRegRequestsForCourseOfferingByStudent(courseOfferingId, studentId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseRegistrationInfo> searchForCourseRegistrations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseRegistrations", null)) {
	        return getNextDecorator().searchForCourseRegistrations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForCourseOfferingRegistrationIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferingRegistrationIds", null)) {
	        return getNextDecorator().searchForCourseOfferingRegistrationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ActivityRegistrationInfo> searchForActivityRegistrations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityRegistrations", null)) {
	        return getNextDecorator().searchForActivityRegistrations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForActivityRegistrationIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityRegistrationIds", null)) {
	        return getNextDecorator().searchForActivityRegistrationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegGroupRegistrations", null)) {
	        return getNextDecorator().searchForRegGroupRegistrations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForRegGroupRegistrationIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegGroupRegistrationIds", null)) {
	        return getNextDecorator().searchForRegGroupRegistrationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseWaitlistEntries", null)) {
	        return getNextDecorator().searchForCourseWaitlistEntries(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForCourseWaitlistEntryIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseWaitlistEntryIds", null)) {
	        return getNextDecorator().searchForCourseWaitlistEntryIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

}
