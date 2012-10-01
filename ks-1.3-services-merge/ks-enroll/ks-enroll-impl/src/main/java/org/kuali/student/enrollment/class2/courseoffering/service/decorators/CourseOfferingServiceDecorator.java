package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.acal.dto.TermInfo;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.jws.WebParam;


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
    public TypeInfo getCourseOfferingType(String courseOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingType(courseOfferingTypeKey, context);
    }        

    @Override
    public List<TypeInfo> getCourseOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingTypes(context);
    }

    @Override
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(String courseOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructorTypesForCourseOfferingType(courseOfferingTypeKey, context);
    }

    @Override
    public StatusInfo deleteCourseOfferingCascaded(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseOfferingCascaded(courseOfferingId,  context);
    }

    @Override
    public StatusInfo deleteFormatOfferingCascaded(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteFormatOfferingCascaded(formatOfferingId,  context);
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup( String formatOfferingId, String registrationGroupType,  RegistrationGroupInfo registrationGroupInfo,  ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRegistrationGroup(formatOfferingId,registrationGroupType, registrationGroupInfo,  context);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().validateSeatPoolDefinition(validationTypeKey, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateRegistrationGroup(validationType, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateFormatOffering(validationType, formatOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo, 
    List<String> optionKeys, ContextInfo context) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateCourseOfferingFromCanonical(courseOfferingInfo, optionKeys, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroupTemplate(String validationTypeKey, RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateRegistrationGroupTemplate(validationTypeKey, registrationGroupTemplateInfo, context);
    }

    @Override
    public RegistrationGroupTemplateInfo createRegistrationGroupTemplate(RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRegistrationGroupTemplate(registrationGroupTemplateInfo, context);
    }

    @Override
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId, RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRegistrationGroupTemplate(registrationGroupTemplateId, registrationGroupTemplateInfo, context);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context) 
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
                   MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context) 
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSeatpoolDefinitions(criteria, context);
    }

    @Override
    public List<String> searchForSeatpoolDefinitionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSeatpoolDefinitionIds(criteria, context);
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRegistrationGroups(criteria, context);
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForRegistrationGroupIds(criteria, context);
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferings(criteria, context);
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferingIds(criteria, context);
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferings(criteria, context);
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferingIds(criteria, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsWithActivityOfferings(activityOfferingIds, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsByIds(registrationGroupIds, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupTemplate(registrationGroupTemplateId, context);
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getFormatOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByTermAndInstructor(termId, instructorId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByIds(courseOfferingIds, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByCourseAndTerm(courseId, termId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByCourse(courseId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByType(typeKey, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectArea, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOffering(courseOfferingId, context);
    }

    @Override
    public CourseOfferingAdminDisplayInfo getCourseOfferingAdminDisplay( String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingAdminDisplay(courseOfferingId, context) ;
    }

    @Override
    public List<CourseOfferingAdminDisplayInfo> getCourseOfferingAdminDisplaysByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingAdminDisplaysByIds(courseOfferingIds, context);
    }

    @Override
    public ActivityOfferingAdminDisplayInfo getActivityOfferingAdminDisplay(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingAdminDisplay(activityOfferingId, contextInfo);
    }

    @Override
    public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysByIds(List<String> activityOfferingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingAdminDisplaysByIds(activityOfferingIds, contextInfo);
    }

    @Override                                                                                        
    public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysForCourseOffering(String courseOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingAdminDisplaysForCourseOffering(courseOfferingId, contextInfo);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByIds(activityOfferingIds, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByFormatOfferingWithoutRegGroup(formatOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingTypesForActivityType(activityTypeKey, context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingTypes(context);
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructorTypesForActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        return getNextDecorator().generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForTemplate(String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().generateRegistrationGroupsForTemplate(registrationGroupTemplateId, context);
    }   

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroupTemplate(registrationGroupTemplateId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteGeneratedRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteGeneratedRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteGeneratedRegistrationGroupsForTemplate(String registrationGroupTemplateId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteGeneratedRegistrationGroupsForTemplate(registrationGroupTemplateId, context);
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteCourseOffering(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
    }


    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, 
    CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId, 
    String activityId,
    String activityOfferingTypeKey, 
    ActivityOfferingInfo activityOfferingInfo, ContextInfo context) 
        throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering( String activityOfferingId,ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.copyActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferings(String formatOfferingId, String activityOfferingType,  Integer quantity, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.generateActivityOfferings(formatOfferingId,activityOfferingType , quantity, context)     ;
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateTotalContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateInClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public CourseOfferingInfo rolloverCourseOffering(String sourceCoId, String targetTermId, List<String> optionKeys, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().rolloverCourseOffering(sourceCoId, targetTermId, optionKeys, context);
    }

    @Override
    public List<String> getValidRolloverOptionKeys(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().getValidRolloverOptionKeys(context);
    }

    @Override
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().getValidCanonicalCourseToCourseOfferingOptionKeys(context);
    }


    @Override
    public TermInfo getTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTerm(termId, context);
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTermTypes(context);
    }

	@Override
	public StatusInfo addSeatPoolDefinitionToActivityOffering(
			String seatPoolDefinitionId, String activityOfferingId,
			ContextInfo contextInfo) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
				
	}

	@Override
	public StatusInfo removeSeatPoolDefinitionFromActivityOffering(
			String seatPoolDefinitionId, String activityOfferingId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().removeSeatPoolDefinitionFromActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
	}

	@Override
	public StatusInfo deleteActivityOfferingCascaded(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException
			 {
		return getNextDecorator().deleteActivityOfferingCascaded(activityOfferingId, context);
	} 
    
    
}
