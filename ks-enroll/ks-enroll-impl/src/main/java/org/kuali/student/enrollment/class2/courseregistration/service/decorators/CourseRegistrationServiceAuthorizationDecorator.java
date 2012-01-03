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
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
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
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getType", null, null)) {
	        return getNextDecorator().getType(typeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTypesByRefObjectURI", null, null)) {
	        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAllowedTypesForType", null, null)) {
	        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTypeRelationsByOwnerType", null, null)) {
	        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getProcessByKey", null, null)) {
	        return getNextDecorator().getProcessByKey(processKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getProcessByObjectType(String refObjectUri,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getProcessByObjectType", null, null)) {
	        return getNextDecorator().getProcessByObjectType(refObjectUri, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getState", null, null)) {
	        return getNextDecorator().getState(processKey, stateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	       if (null == context) {
	            throw new MissingParameterException();
	        }
	           
	        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getStatesByProcess", null, null)) {
		        return getNextDecorator().getStatesByProcess(processKey, context);
	        }
	        else {
	        	throw new OperationFailedException("Permission Denied.");
	        }
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInitialValidStates", null, null)) {
	        return getNextDecorator().getInitialValidStates(processKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getNextHappyState", null, null)) {
	        return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public Boolean checkStudentEligibility(String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibility", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibilityForTerm", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAppointmentWindows", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibiltyForCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "checkStudentEligibiltyForRegGroup", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getEligibleRegGroupsForStudentInCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateCreditLoadForTerm", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateCreditLoadForRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForRegGroup", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsForStudentInRegGroup", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAvailableSeatsInSeatpool", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "verifyRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "verifySavedReqRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createRegRequestFromExisting", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "submitRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "dropStudentsFromRegGroups", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "moveStudentsBetweenRegGroups", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "cancelRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequest", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsByIdList", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForStudentByTerm", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntry", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCourseWaitlistEntry", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "reorderCourseWaitlistEntries", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "insertCourseWaitlistEntryAtPosition", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeCourseWaitlistEntry", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCourseWaitlistEntry", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateCourseWaitlistEntry", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "registerStudentFromWaitlist", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForRegGroup", null, null)) {
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
	           
	        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForStudentInCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntryForStudentInRegGroup", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseWaitlistEntriesForStudentByTerm", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistration", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsByIdList", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActiveCourseRegistrationForStudentByCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudent", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudentByCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseRegistrationsForStudentByTerm", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActiveCourseRegistrationsByCourseOfferingId", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDroppedCourseRegistrationsByCourseOfferingId", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseRegistration", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseOffering", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegRequestsForCourseOfferingByStudent", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseRegistrations", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferingRegistrationIds", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityRegistrations", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityRegistrationIds", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegGroupRegistrations", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegGroupRegistrationIds", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseWaitlistEntries", null, null)) {
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
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseWaitlistEntryIds", null, null)) {
	        return getNextDecorator().searchForCourseWaitlistEntryIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

}
