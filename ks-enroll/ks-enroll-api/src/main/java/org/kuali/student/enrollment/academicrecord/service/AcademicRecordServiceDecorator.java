package org.kuali.student.enrollment.academicrecord.service;

import java.util.List;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;

import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;


public abstract class AcademicRecordServiceDecorator 
    implements AcademicRecordService {

    private AcademicRecordService nextDecorator;	

    public AcademicRecordService getNextDecorator()
        throws OperationFailedException {

        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(AcademicRecordService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(String personId, String termKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getAttemptedCourseRecordsForTerm(personId, termKey, context);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getCompletedCourseRecords(personId, context);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getCompletedCourseRecordsForTerm(personId, termKey, context);
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termKey, String calculationTypeKey, ContextInfo context) 
         throws DoesNotExistException, InvalidParameterException, 
                MissingParameterException, OperationFailedException {

        return getNextDecorator().getGPAForTerm(personId, termKey, calculationTypeKey, context);
    }

    @Override
    public GPAInfo getGPAForAcademicCalendar(String personId, String academicCalendarKey, String calculationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getGPAForAcademicCalendar(personId, academicCalendarKey, calculationTypeKey, context);
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getCumulativeGPA(personId, calculationTypeKey, context);
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termKey, String calculationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getEarnedCreditsForTerm(personId, termKey, calculationTypeKey, context);
    }

    @Override
    public String getEarnedCreditsForAcademicCalendar(String personId, String academicCalendarKey, String calculationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getEarnedCreditsForAcademicCalendar(personId, academicCalendarKey, calculationTypeKey, context);
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getEarnedCredits(personId, calculationTypeKey, context);
    }
}
