package org.kuali.student.enrollment.grading.service;

import java.util.List;

import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CreditsEarnedInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupInfo;

public abstract class GradingServiceDecorator implements GradingService {


    protected GradingServiceDecorator nextDecorator;
            
    public GradingServiceDecorator getNextDecorator() {
        return nextDecorator;
    }
   
    public void setNextDecorator(GradingServiceDecorator nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
      
        return nextDecorator.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
      
        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
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
    public List<GradeRosterInfo> getGradeRostersByGraderAndTerm(String graderId, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRostersByGraderAndTerm(graderId, termKey, context);
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
    public GradeRosterInfo buildInterimGradeRosterByType(List<String> activityOfferingIdList, String rosterTypeKey,
            ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().buildInterimGradeRosterByType(activityOfferingIdList, rosterTypeKey, context);
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
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByIdList(List<String> gradeRosterEntryIdList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRosterEntriesByIdList(gradeRosterEntryIdList, context);
    }

    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByRosterId(String gradeRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getGradeRosterEntriesByRosterId(gradeRosterId, context);
    }

    @Override
    public List<ResultValueGroupInfo> getValidGradesForStudentByRoster(String studentId, String rosterId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getValidGradesForStudentByRoster(studentId, rosterId, context);
    }

    @Override
    public GradeRosterEntryInfo getFinalGradeForStudentInCourseOffering(String studentId, String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    public AssignedGradeInfo updateAssignedGrade(String gradeRosterEntryId, AssignedGradeInfo assignedGrade,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        
        return getNextDecorator().updateAssignedGrade(gradeRosterEntryId, assignedGrade, context);
    }

    @Override
    public CreditsEarnedInfo updateCredit(String gradeRosterEntryId, CreditsEarnedInfo assignedGrade,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCredit(gradeRosterEntryId, assignedGrade, context);
    }

}
