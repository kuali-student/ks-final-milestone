package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
//import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

public  class CourseOfferingServiceAuthorizationDecorator extends CourseOfferingServiceDecorator implements HoldsPermissionService{
    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "CourseOfferingService.";
    
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
	public CourseOfferingInfo getCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOffering", null)) {
	        return getNextDecorator().getCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByIds(
            List<String> courseOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingsByIds", null)) {
	        return getNextDecorator().getCourseOfferingsByIds(courseOfferingIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(
            String courseId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingsByCourseAndTerm", null)) {
	        return getNextDecorator().getCourseOfferingsByCourseAndTerm(courseId, termId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getCourseOfferingIdsByTerm(String termId,
            Boolean useIncludedTerm, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingIdsByTerm", null)) {
	        return getNextDecorator().getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getCourseOfferingIdsByTermAndSubjectArea(
			String termId, String subjectArea, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingIdsByTermAndSubjectArea", null)) {
	        return getNextDecorator().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectArea, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(
            String termId, String instructorId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingsByTermAndInstructor", null)) {
	        return getNextDecorator().getCourseOfferingsByTermAndInstructor(termId, instructorId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(
            String termId, String unitsContentOwnerId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCourseOfferingIdsByTermAndUnitContentOwner", null)) {
	        return getNextDecorator().getCourseOfferingIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseOfferingInfo createCourseOffering(String courseId, 
                                                       String termId, 
                                                       String courseOfferingTypeKey,
                                                       CourseOfferingInfo courseOfferingInfo,
                                                       List<String> optionKeys,
                                                       ContextInfo context) 
            throws DoesNotExistException, DataValidationErrorException,
                   InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException,
                   ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createCourseOffering", null)) {
	        return getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
			CourseOfferingInfo courseOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
                               ReadOnlyException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCourseOffering", null)) {
	        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CourseOfferingInfo updateCourseOfferingFromCanonical(
			String courseOfferingId,
                        List<String> optionKeys, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
                        VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCourseOfferingFromCanonical", null)) {
	        return getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCourseOffering", null)) {
	        return getNextDecorator().deleteCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateCourseOffering(
			String validationType, CourseOfferingInfo courseOfferingInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateCourseOffering", null)) {
	        return getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
        }
        else {
        	throw new OperationFailedException ("Permission Denied");
        }
	}

	
    @Override
	public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActivityOfferingType", null)) {
	        return getNextDecorator().getActivityOfferingType(activityOfferingTypeKey, context);
        }
        else {
        	throw new PermissionDeniedException ();
        }
	}

		@Override
	public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActivityOffering", null)) {
	        return getNextDecorator().getActivityOffering(activityOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByIds(
            List<String> activityOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActivityOfferingsByIds", null)) {
	        return getNextDecorator().getActivityOfferingsByIds(activityOfferingIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(
            String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getActivityOfferingsByCourseOffering", null)) {
	        return getNextDecorator().getActivityOfferingsByCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public ActivityOfferingInfo createActivityOffering(String formatOfferingId, 
                                                           String activityId, 
                                                           String activityOfferingTypeKey,
                                                           ActivityOfferingInfo activityOfferingInfo, 
                                                           ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
                   InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException,
                   ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createActivityOffering", null)) {
	        return getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

    @Override
    public ActivityOfferingInfo updateActivityOffering(
			String activityOfferingId,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
                               ReadOnlyException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateActivityOffering", null)) {
	        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteActivityOffering", null)) {
	        return getNextDecorator().deleteActivityOffering(activityOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateActivityOffering(
			String validationType, ActivityOfferingInfo activityOfferingInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateActivityOffering", null)) {
	        return getNextDecorator().validateActivityOffering(validationType, activityOfferingInfo, context);
        }
        else {
        	throw new OperationFailedException ("Permission Denied");
        }
	}

    @Override
	public Float calculateInClassContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateInClassContactHoursForTerm", null)) {
	        return getNextDecorator().calculateInClassContactHoursForTerm(activityOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Float calculateOutofClassContactHoursForTerm(
			String activityOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateOutofClassContactHoursForTerm", null)) {
	        return getNextDecorator().calculateOutofClassContactHoursForTerm(activityOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Float calculateTotalContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateTotalContactHoursForTerm", null)) {
	        return getNextDecorator().calculateTotalContactHoursForTerm(activityOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}


	@Override
	public RegistrationGroupInfo getRegistrationGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroup", null)) {
	        return getNextDecorator().getRegistrationGroup(registrationGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsByIds(
			List<String> registrationGroupIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroupsByIds", null)) {
	        return getNextDecorator().getRegistrationGroupsByIds(registrationGroupIds, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(
            String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroupsForCourseOffering", null)) {
	        return getNextDecorator().getRegistrationGroupsForCourseOffering(courseOfferingId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}




	@Override
	public RegistrationGroupInfo updateRegistrationGroup(
			String registrationGroupId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
                        ReadOnlyException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegistrationGroup", null)) {
	        return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteRegistrationGroup(String registrationGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteRegistrationGroup", null)) {
	        return getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateRegistrationGroup(
			String validationType, RegistrationGroupInfo registrationGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateRegistrationGroup", null)) {
	        return getNextDecorator().validateRegistrationGroup(validationType, registrationGroupInfo, context);
        }
        else {
        	throw new OperationFailedException ("Permission Denied");
        }
	}

	@Override
	public SeatPoolDefinitionInfo getSeatPoolDefinition(
			String seatPoolDefinitionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getSeatPoolDefinition", null)) {
	        return getNextDecorator().getSeatPoolDefinition(seatPoolDefinitionId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	
	@Override
	public SeatPoolDefinitionInfo createSeatPoolDefinition(
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
                               OperationFailedException, PermissionDeniedException,
                               ReadOnlyException  {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createSeatPoolDefinition", null)) {
	        return getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public SeatPoolDefinitionInfo updateSeatPoolDefinition(
			String seatPoolDefinitionId,
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
                               ReadOnlyException, 
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateSeatPoolDefinition", null)) {
	        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteSeatPoolDefinition", null)) {
	        return getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CourseOfferingInfo> searchForCourseOfferings(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferings", null)) {
	        return getNextDecorator().searchForCourseOfferings(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForCourseOfferingIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferingIds", null)) {
	        return getNextDecorator().searchForCourseOfferingIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ActivityOfferingInfo> searchForActivityOfferings(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityOfferings", null)) {
	        return getNextDecorator().searchForActivityOfferings(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForActivityOfferingIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityOfferingIds", null)) {
	        return getNextDecorator().searchForActivityOfferingIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<RegistrationGroupInfo> searchForRegistrationGroups(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegistrationGroups", null)) {
	        return getNextDecorator().searchForRegistrationGroups(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegistrationGroupIds", null)) {
	        return getNextDecorator().searchForRegistrationGroupIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintions", null)) {
	        return getNextDecorator().searchForSeatpoolDefinitions(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForSeatpoolDefinitionIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintionIds", null)) {
	        return getNextDecorator().searchForSeatpoolDefinitionIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType,
            FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
                                                                               MissingParameterException, OperationFailedException, PermissionDeniedException,
                                                                               ReadOnlyException  {
              if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintionIds", null)) {
	        return getNextDecorator().createFormatOffering(courseOfferingId,formatId,formatOfferingType, formatOfferingInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
     }
}
