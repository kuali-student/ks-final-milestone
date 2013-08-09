package org.kuali.student.enrollment.grading.service;

import java.util.List;

import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

public abstract class GradingServiceDecorator implements GradingService {
    private GradingService nextDecorator;

    public GradingService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(GradingService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public TypeInfo getGradeRosterType(String gradeRosterTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getGradeRosterType(gradeRosterTypeKey, context);
    }

    @Override
    public GradeRosterInfo getGradeRoster(String gradeRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRoster(gradeRosterId, context);
    }

    @Override
    public List<GradeRosterInfo> getGradeRostersByGraderAndTerm(String graderId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRostersByGraderAndTerm(graderId, termId, context);
    }

    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRostersForActivityOffering(courseOfferingId, context);
    }

    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getFinalGradeRostersForActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<GradeRosterInfo> getGradeRostersForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRostersForActivityOffering(activityOfferingId, context);
    }

    @Override
    public GradeRosterInfo buildInterimGradeRosterByType(String courseOfferingId, List<String> activityOfferingIds,
            String rosterTypeKey, ContextInfo context) throws AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().buildInterimGradeRosterByType(courseOfferingId, activityOfferingIds,
                rosterTypeKey, context);
    }

    @Override
    public GradeRosterInfo updateInterimGradeRoster(GradeRosterInfo gradeRoster, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateInterimGradeRoster(gradeRoster, context);
    }

    @Override
    public StatusInfo deleteInterimGradeRoster(String gradeRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteInterimGradeRoster(gradeRosterId, context);
    }

    @Override
    public GradeRosterInfo updateFinalGradeRosterState(String gradeRosterId, String stateKey, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateFinalGradeRosterState(gradeRosterId, stateKey, context);

    }

    @Override
    public List<ValidationResultInfo> validateGradeRoster(GradeRosterInfo gradeRoster, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateGradeRoster(gradeRoster, context);
    }

    @Override
    public GradeRosterEntryInfo getGradeRosterEntry(String gradeRosterEntryId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRosterEntry(gradeRosterEntryId, context);
    }

    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByIds(List<String> gradeRosterEntryIds,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRosterEntriesByIds(gradeRosterEntryIds, context);
    }

    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByRosterId(String gradeRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRosterEntriesByRosterId(gradeRosterId, context);
    }

    @Override
    public GradeRosterEntryInfo getFinalGradeForStudentInCourseOffering(String studentId, String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        return getNextDecorator().getFinalGradeForStudentInCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public GradeRosterEntryInfo addEntrytoInterimRoster(GradeRosterEntryInfo gradeRosterEntry, String gradeRosterId,
            ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return getNextDecorator().addEntrytoInterimRoster(gradeRosterEntry, gradeRosterId, context);
    }

    @Override
    public StatusInfo removeEntryFromInterimRoster(String gradeRosterEntryId, String gradeRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeEntryFromInterimRoster(gradeRosterEntryId, gradeRosterId, context);
    }

    @Override
    public boolean updateGrade(String gradeRosterEntryId, String assignedGradeId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        return getNextDecorator().updateGrade(gradeRosterEntryId, assignedGradeId, context);
    }

    @Override
    public boolean updateCredit(String gradeRosterEntryId, String creditId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCredit(gradeRosterEntryId, creditId, context);
    }

	@Override
	public List<GradeValuesGroupInfo> getGradeGroupsByKeyList(
			List<String> gradeGroupKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getGradeGroupsByKeyList(gradeGroupKeyList, context);
	}

	@Override
	public boolean updateNumberGrade(String gradeRosterEntryId,
			String numberGradeValue, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return getNextDecorator().updateNumberGrade(gradeRosterEntryId, numberGradeValue, context);
	}

}
