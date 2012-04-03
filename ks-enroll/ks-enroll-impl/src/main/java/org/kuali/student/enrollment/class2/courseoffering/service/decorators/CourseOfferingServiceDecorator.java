package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.type.dto.TypeInfo;


public class CourseOfferingServiceDecorator implements CourseOfferingService {

    private CourseOfferingService nextDecorator;

    public CourseOfferingService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(CourseOfferingService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.validateSeatPoolDefinition(validationTypeKey, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateRegistrationGroup(validationType, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateFormatOffering(validationType, formatOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateCourseOfferingFromCanonical(courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId, RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateRegistrationGroupTemplate(registrationGroupTemplateId, registrationGroupTemplateInfo, context);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context) 
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateCourseOfferingFromCanonical(courseOfferingId, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForSeatpoolDefintions(criteria, context);
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForSeatpoolDefintionIds(criteria, context);
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForRegistrationGroups(criteria, context);
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForRegistrationGroupIds(criteria, context);
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForCourseOfferings(criteria, context);
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForCourseOfferingIds(criteria, context);
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForActivityOfferings(criteria, context);
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForActivityOfferingIds(criteria, context);
    }

    @Override
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getUnscheduledActivityOfferingsBySoc(socId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getUnpublishedActivityOfferingsBySoc(socId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getSeatPoolDefinitionsForRegGroup(registrationGroupId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getSeatPoolDefinitionsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroupsWithActivityOfferings(activityOfferingIds, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroupsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroupsByIds(registrationGroupIds, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroupTemplate(registrationGroupTemplateId, context);
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getPublishedCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingByCourseOfferingId(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getFormatOfferingByCourseOfferingId(courseOfferingId, context);
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingsByTermAndInstructor(termId, instructorId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingsByIds(courseOfferingIds, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingsByCourseAndTerm(courseId, termId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingsByCourse(courseId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingIdsByType(typeKey, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingIdsByTermAndSubjectArea(termId, subjectArea, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingsByIds(activityOfferingIds, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingsByCourseOfferingWithoutRegGroup(courseOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingTypesForActivityType(activityTypeKey, context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingTypes(context);
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.generateActivityOfferingsForFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteRegistrationGroupTemplate(registrationGroupTemplateId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return nextDecorator.deleteFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteCourseOffering(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createSeatPoolDefinition(seatPoolDefinitionInfo, context);
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createRegistrationGroup(registrationGroupType, registrationGroupInfo, context);
    }

    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId, 
    String activityId,
    String activityOfferingTypeKey, 
    ActivityOfferingInfo activityOfferingInfo, ContextInfo context) 
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.calculateTotalContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.calculateInClassContactHoursForTerm(activityOfferingId, context);
    }

    
}
