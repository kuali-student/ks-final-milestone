package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
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
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(String courseId, String termId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsForCourseAndTerm(courseId, termId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsForTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsForTerm(termId, useIncludedTerm, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectArea, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(@WebParam(name = "termId") String termId, @WebParam(name = "instructorId") String instructorId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(String termId, String unitOwnerId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTermAndUnitContentOwner(termId, unitOwnerId, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<CourseOfferingInfo> getPublishedCourseOfferingsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseOffering(courseOfferingId, context);
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public List<TypeInfo> getAllActivityOfferingTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllActivityOfferingTypes(context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingTypesForActivityType(activityTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByActivityTypeAndActivityOfferingTemplate(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByActivityOfferingTemplateWithoutRegGroup(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferingsForActivityOfferingTemplate(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateInClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateTotalContactHoursForTerm(activityOfferingId, context);
    }


    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(String courseOfferingId, String formatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegGroupsByFormatForCourse(courseOfferingId, formatTypeKey, context);
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String courseOfferingId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createRegistrationGroup(courseOfferingId, registrationGroupInfo, context);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForActivityOfferingTemplate(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSeatPoolsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSeatPoolsForRegGroup(registrationGroupId, context);
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType,
            CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public ActivityOfferingTemplateInfo getActivityOfferingTemplate( String activityOfferingTemplateId,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingTemplateInfo> getActivityOfferingTemplatesByCourseOfferingId( String courseOfferingId,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public ActivityOfferingTemplateInfo createActivityOfferingTemplate( String courseOfferingId,  String activityOfferingTemplateType,  ActivityOfferingTemplateInfo activityOfferingTemplateInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public ActivityOfferingTemplateInfo updateActivityOfferingTemplate( String activityOfferingTemplateId,  ActivityOfferingTemplateInfo activityOfferingTemplateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public StatusInfo deleteActivityOfferingTemplate(String activityOfferingTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateRegistrationGroup(validationType, registrationGroupInfo, context);
    }

    @Override
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<RegistrationGroupTemplateInfo> getRegistrationGroupTemplatesByActivityOfferingTemplateId(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public RegistrationGroupTemplateInfo createRegistrationGroupTemplate(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, @WebParam(name = "registrationGroupTemplateType") String registrationGroupTemplateType, @WebParam(name = "registrationGroupTemplateInfo") RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "registrationGroupTemplateInfo") RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public StatusInfo deleteRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByIds(courseOfferingIds, context);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourse(@WebParam(name = "courseId") String courseId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActivityOfferingsByIds(activityOfferingIds, context);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationGroupsByIds(registrationGroupIds, context);
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferings(criteria, context);
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferingIds(criteria, context);
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferings(criteria, context);
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityOfferingIds(criteria, context);
    }

    @Override
    public List<CourseRegistrationInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForRegistrationGroups(criteria, context);
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForRegistrationGroupIds(criteria, context);
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForSeatpoolDefintions(criteria, context);
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForSeatpoolDefintionIds(criteria, context);
    }


}
