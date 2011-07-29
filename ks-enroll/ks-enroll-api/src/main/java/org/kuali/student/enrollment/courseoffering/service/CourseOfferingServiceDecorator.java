package org.kuali.student.enrollment.courseoffering.service;

import java.util.List;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class CourseOfferingServiceDecorator implements CourseOfferingService {
    
	protected CourseOfferingService nextDecorator;
    	
    public CourseOfferingService getNextDecorator() {
		return nextDecorator;
	}

	public void setNextDecorator(CourseOfferingService nextDecorator) {
		this.nextDecorator = nextDecorator;
	}

	@Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        
        return this.nextDecorator.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return this.nextDecorator.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(String courseId, String termKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getCourseOfferingsForCourseAndTerm(courseId, termKey, context);
    }

    @Override
    public List<String> getCourseOfferingIdsForTerm(String termKey, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getCourseOfferingIdsForTerm(termKey, useIncludedTerm, context);
    }

    @Override
    public List<String> getCourseOfferingIdsBySubjectArea(String termKey, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getCourseOfferingIdsBySubjectArea(termKey, subjectArea, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByUnitContentOwner(String termKey, String unitOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getCourseOfferingIdsByUnitContentOwner(termKey, unitOwnerId, context);
    }

    @Override
    public CourseOfferingInfo createCourseOfferingFromCanonical(String courseid, String termKey,
            List<String> formatIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createCourseOfferingFromCanonical(courseid, termKey, formatIdList, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateCourseOfferingFromCanonical(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteCourseOffering(courseOfferingId, context);
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	return this.nextDecorator.getActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public List<TypeInfo> getAllActivityOfferingTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getAllActivityOfferingTypes(context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	return this.nextDecorator.getActivityOfferingTypesForActivityType(activityTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivitiesForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getActivitiesForCourseOffering(courseOfferingId, context);
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(List<String> courseOfferingIdList,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.calculateInClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.calculateTotalContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> copyActivityOffering(String activityOfferingId, Integer numberOfCopies,
            String copyContextTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.copyActivityOffering(activityOfferingId, numberOfCopies, copyContextTypeKey, context);
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getRegGroupsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(String courseOfferingId, String formatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getRegGroupsByFormatForCourse(courseOfferingId, formatTypeKey, context);
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String courseOfferingId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createRegistrationGroup(courseOfferingId, registrationGroupInfo, context);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getSeatPoolsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getSeatPoolsForRegGroup(registrationGroupId, context);
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createSeatPoolDefinition(seatPoolDefinitionInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<StatementTreeViewInfo> getCourseOfferingRestrictions(String courseOfferingId, String nlUsageTypeKey, String language, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getCourseOfferingRestrictions(courseOfferingId, nlUsageTypeKey, language, context);
    }

    @Override
    public StatementTreeViewInfo createCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
    	return this.nextDecorator.createCourseOfferingRestriction(courseOfferingId, restrictionInfo, context);
    }

    @Override
    public StatementTreeViewInfo updateCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
    	return this.nextDecorator.updateCourseOfferingRestriction(courseOfferingId, restrictionInfo, context);
    }

    @Override
    public StatusInfo deleteCourseOfferingRestriction(String courseOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteCourseOfferingRestriction(courseOfferingId, restrictionId, context);
    }

    @Override
    public List<StatementTreeViewInfo> getActivityOfferingRestrictions(String activityOfferingId, String nlUsageTypeKey, String language, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getActivityOfferingRestrictions(activityOfferingId, nlUsageTypeKey, language, context);
    }

    @Override
    public StatementTreeViewInfo createActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
    	return this.nextDecorator.createActivityOfferingRestriction(activityOfferingId, restrictionInfo, context);
    }

    @Override
    public StatementTreeViewInfo updateActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
    	return this.nextDecorator.updateActivityOfferingRestriction(activityOfferingId, restrictionInfo, context);
    }

    @Override
    public StatusInfo deleteActivityOfferingRestriction(String activityOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteActivityOfferingRestriction(activityOfferingId, restrictionId, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateCourseOfferingRestriction(validationType, restrictionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateActivityOfferingRestriction(validationType, restrictionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateRegistrationGroup(validationType, registrationGroupInfo, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIdList(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getCourseOfferingsByIdList(courseOfferingIds, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIdList(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getActivityOfferingsByIdList(activityOfferingIds, context);
    }

    @Override
    public StatusInfo assignActivityToCourseOffering(String activityOfferingId, List<String> courseOfferingIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.assignActivityToCourseOffering(activityOfferingId, courseOfferingIdList, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIdList(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.nextDecorator.getRegistrationGroupsByIdList(registrationGroupIds, context);
    }
}
