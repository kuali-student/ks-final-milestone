package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

public class CourseRegistrationServiceDecorator implements CourseRegistrationService {

    private CourseRegistrationService nextDecorator;

    public CourseRegistrationServiceDecorator() {
    }

    public CourseRegistrationServiceDecorator(CourseRegistrationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    public CourseRegistrationService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(CourseRegistrationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public  List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibility(studentId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibilityForTerm(String studentId,
            String termId, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibilityForTerm(studentId, termId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForCourseOffering(
            String studentId, String courseOfferingId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForRegGroup(
            String studentId, String regGroupId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibiltyForRegGroup(studentId, regGroupId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(String studentId,
            String courseOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEligibleRegGroupsForStudentInCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public LoadInfo calculateCreditLoadForTerm(String studentId, String termId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().calculateCreditLoadForTerm(studentId, termId, context);
    }

    @Override
    public LoadInfo calculateCreditLoadForRegRequest(String studentId, RegRequestInfo regRequestInfo,
            ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().calculateCreditLoadForRegRequest(studentId, regRequestInfo, context);
    }

    @Override
    public Integer getAvailableSeatsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public Integer getAvailableSeatsForRegGroup(String regGroupId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForRegGroup(regGroupId, context);
    }

    @Override
    public Integer getAvailableSeatsForStudentInRegGroup(String studentId, String regGroupId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForStudentInRegGroup(studentId, regGroupId, context);
    }

    @Override
    public Integer getAvailableSeatsInSeatPool(String seatpoolId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsInSeatPool(seatpoolId, context);
    }

    @Override
    public RegRequestInfo createRegRequest(String regRequestTypeKey, RegRequestInfo regRequestInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRegRequest(regRequestTypeKey, regRequestInfo, context);
    }

    @Override
    public RegRequestInfo updateRegRequest(String regRequestId, RegRequestInfo regRequestInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        return getNextDecorator().updateRegRequest(regRequestId, regRequestInfo, context);
    }

    @Override
    public StatusInfo deleteRegRequest(String regRequestId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().deleteRegRequest(regRequestId, context);
    }

    
    @Override
	public List<ValidationResultInfo> validateRegRequest(
			String validationTypeKey, String regRequestTypeKey,
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().validateRegRequest(validationTypeKey, regRequestTypeKey, regRequestInfo, context);
	}

	@Override
	public List<ValidationResultInfo> verifyRegRequestForSubmission(
			String regRequestId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().verifyRegRequestForSubmission(regRequestId, context);
	}

	
    @Override
    public RegRequestInfo createRegRequestFromExisting(String existingRegRequestId, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().createRegRequestFromExisting(existingRegRequestId, context);
    }

    @Override
    public RegResponseInfo submitRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
             AlreadyExistsException {
        return getNextDecorator().submitRegRequest(regRequestId, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsByIds(List<String> regRequestIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsByIds(regRequestIds, context);
    }

    
    @Override
	public List<RegRequestItemInfo> getRegRequestItemsByCourseRegistration(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getRegRequestItemsByCourseRegistration(courseRegistrationId, context);
	}

	@Override
	public List<RegRequestItemInfo> getRegRequestItemsByCourseOfferingAndStudent(
			String courseOfferingId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getRegRequestItemsByCourseOfferingAndStudent(courseOfferingId, studentId, context);
	}

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseRegistration(courseRegistrationId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(List<String> courseRegistrationIds,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseRegistrationsByIds(courseRegistrationIds, context);
    }

    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseRegistrations(criteria, context);
    }

    @Override
    public List<String> searchForCourseOfferingRegistrationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferingRegistrationIds(criteria, context);
    }

    @Override
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityRegistrations(criteria, context);
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityRegistrationIds(criteria, context);
    }

    @Override
    public RegRequestInfo getRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequest(regRequestId, context);
    }

	@Override
	public List<RegRequestInfo> getUnsubmittedRegRequestsByRequestorAndTerm(
			String requestorId, String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().getUnsubmittedRegRequestsByRequestorAndTerm(requestorId, termId, context);
	}

	@Override
	public List<String> getCourseRegistrationIdsByType(
			String courseRegistrationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().getCourseRegistrationIdsByType(courseRegistrationTypeKey, context);
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(
			String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
		return getNextDecorator().getCourseRegistrationsByStudent(studentId, context);
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DisabledIdentifierException {
		return getNextDecorator().getCourseRegistrationsByStudentAndCourseOffering(studentId, courseOfferingId, context);
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DisabledIdentifierException {
		return getNextDecorator().getCourseRegistrationsByStudentAndTerm(studentId, termId, context);
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

   

}
