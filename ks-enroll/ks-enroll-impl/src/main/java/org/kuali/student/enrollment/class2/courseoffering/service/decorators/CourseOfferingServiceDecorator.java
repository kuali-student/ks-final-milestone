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
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> getCourseOfferingsByIds(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingsByCourseAndTerm(courseId, termId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
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
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForSeatpoolDefintionIds( QueryByCriteria criteria,  ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria,  org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria,org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
	   return getNextDecorator().searchForCourseOfferings(criteria, context);
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateSeatPoolDefinition( String validationTypeKey,org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo updateSeatPoolDefinition( String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo createSeatPoolDefinition( org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(String registrationGroupId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering( String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo getSeatPoolDefinition( String seatPoolDefinitionId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "registrationGroupTemplateInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo registrationGroupTemplateInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo getRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "registrationGroupInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "registrationGroupType") String registrationGroupType, @WebParam(name = "registrationGroupInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsByIds(@WebParam(name = "registrationGroupIds") List<String> registrationGroupIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo getRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateActivityOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo createActivityOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "activityOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.core.type.dto.TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.core.type.dto.TypeInfo> getActivityOfferingTypes(@WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.core.type.dto.TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "formatOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "formatOfferingInfo") FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return getNextDecorator().validateFormatOffering(validationType,formatOfferingInfo, context);
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo createFormatOffering( String courseOfferingId, String formatId, String formatOfferingType, @WebParam(name = "formatOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo> getFormatOfferingByCourseOfferingId(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo createCourseOffering(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "courseOfferingTypeKey") String courseOfferingTypeKey, @WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


}
