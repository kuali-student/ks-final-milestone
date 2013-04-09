package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.List;


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
    public StatusInfo deleteCourseOfferingCascaded(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseOfferingCascaded(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteFormatOfferingCascaded(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteFormatOfferingCascaded(formatOfferingId, context);
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String formatOfferingId, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRegistrationGroup(formatOfferingId, activityOfferingClusterId, registrationGroupType, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateSeatPoolDefinition(validationTypeKey, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateRegistrationGroup(validationType, activityOfferingClusterId, registrationGroupType, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateFormatOffering(validationType, formatOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo,
                                                                          List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseOfferingFromCanonical(courseOfferingInfo, optionKeys, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
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
    public ActivityOfferingClusterInfo getActivityOfferingCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingClustersByFormatOffering(formatOfferingId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(String validationTypeKey, String formatOfferingId,
                                                                      ActivityOfferingClusterInfo activityOfferingClusterInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateActivityOfferingCluster(validationTypeKey, formatOfferingId, activityOfferingClusterInfo,
                contextInfo);
    }

    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(String formatOfferingId,
                                                                     String activityOfferingClusterTypeKey, ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                     ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createActivityOfferingCluster(formatOfferingId, activityOfferingClusterTypeKey,
                activityOfferingClusterInfo, contextInfo);
    }


    @Override
    public AOClusterVerifyResultsInfo verifyActivityOfferingClusterForGeneration(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifyActivityOfferingClusterForGeneration(activityOfferingClusterId, contextInfo);
    }

    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(String formatOfferingId, String activityOfferingClusterId,
                                                                     ActivityOfferingClusterInfo activityOfferingClusterInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateActivityOfferingCluster(formatOfferingId, activityOfferingClusterId,
                activityOfferingClusterInfo, contextInfo);
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
    public CourseOfferingDisplayInfo getCourseOfferingDisplay(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingDisplay(courseOfferingId, context);
    }

    @Override
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingDisplaysByIds(courseOfferingIds, context);
    }

    @Override
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingDisplay(activityOfferingId, contextInfo);
    }

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(List<String> activityOfferingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingDisplaysByIds(activityOfferingIds, contextInfo);
    }

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysForCourseOffering(String courseOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingDisplaysForCourseOffering(courseOfferingId, contextInfo);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByIds(activityOfferingIds, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByFormatOffering(formatOfferingId, contextInfo);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsWithoutClusterByFormatOffering(formatOfferingId, contextInfo);
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
    public List<ActivityOfferingInfo> getActivityOfferingsByCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByCluster(activityOfferingClusterId, contextInfo);
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
    public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return getNextDecorator().generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return getNextDecorator().generateRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public StatusInfo deleteActivityOfferingCluster(String activityOfferingClusterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteActivityOfferingCluster(activityOfferingClusterId, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<BulkStatusInfo> deleteGeneratedRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteGeneratedRegistrationGroupsByFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(String registrationGroupId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().verifyRegistrationGroup(registrationGroupId, contextInfo);
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
    public ActivityOfferingInfo copyActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.copyActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferings(String formatOfferingId, String activityOfferingType, Integer quantity, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.generateActivityOfferings(formatOfferingId, activityOfferingType, quantity, context);
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
    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCoId, String targetTermId, List<String> optionKeys, ContextInfo context) throws AlreadyExistsException,
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
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteActivityOfferingCascaded(activityOfferingId, context);
    }

    @Override
    public StatusInfo changeCourseOfferingState(
            String courseOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeCourseOfferingState(courseOfferingId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeFormatOfferingState(
            String formatOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeFormatOfferingState(formatOfferingId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeActivityOfferingState(
            String activityOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeActivityOfferingState(activityOfferingId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeRegistrationGroupState(
            String registrationGroupId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeRegistrationGroupState(registrationGroupId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeActivityOfferingClusterState(
            String activityOfferingClusterId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeActivityOfferingClusterState(activityOfferingClusterId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeSeatPoolDefinitionState(
            String seatPoolDefinitionId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().changeSeatPoolDefinitionState(seatPoolDefinitionId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo scheduleActivityOffering(String activityOfferingId,
                                               ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().scheduleActivityOffering(activityOfferingId, contextInfo);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOfferingCluster(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    public StatusInfo deleteActivityOfferingClusterCascaded(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteActivityOfferingClusterCascaded(activityOfferingClusterId, contextInfo);
    }

    @Override
    public List<String> getActivityOfferingClustersIdsByFormatOffering(
            String formatOfferingId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getActivityOfferingClustersIdsByFormatOffering(formatOfferingId, contextInfo);
    }

    @Override
    public List<FormatOfferingInfo> searchForFormatOfferings(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForFormatOfferings(criteria, contextInfo);
    }

    @Override
    public List<String> searchForFormatOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForFormatOfferingIds(criteria, contextInfo);
    }

    @Override
    public List<ActivityOfferingClusterInfo> searchForActivityOfferingClusters(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferingClusters(criteria, contextInfo);
    }

    @Override
    public List<String> searchForActivityOfferingClusterIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferingClusterIds(criteria, contextInfo);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsForSeatPoolDefinition(
            String seatPoolDefinitionId,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsForSeatPoolDefinition(seatPoolDefinitionId, context);
    }
    
    


}
