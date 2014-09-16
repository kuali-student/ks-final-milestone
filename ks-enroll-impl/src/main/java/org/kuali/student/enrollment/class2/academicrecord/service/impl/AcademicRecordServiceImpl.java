package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class AcademicRecordServiceImpl implements AcademicRecordService{

    private AcademicRecordService academicRecordServiceCurrent;
    private AcademicRecordService academicRecordServiceHistory;

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        boolean historyDoesNotExist = false;
        boolean currentDoesNotExist = false;

        List<StudentCourseRecordInfo> courseRecords;

        // Get historical data
        try {
            courseRecords = academicRecordServiceHistory.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);
        } catch (DoesNotExistException ex) {
            courseRecords = new ArrayList<>();
            historyDoesNotExist = true;
        }

        // Get current data
        try {
            courseRecords.addAll(academicRecordServiceCurrent.getStudentCourseRecordsForCourse(personId, courseId, contextInfo));
        } catch (DoesNotExistException ex) {
            currentDoesNotExist = true;
        }

        if (historyDoesNotExist && currentDoesNotExist) {
            throw new DoesNotExistException("No course records for student Id = " + personId);
        }

        return courseRecords;
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> studentCourseRecords = new ArrayList<>();
        for(String courseId : courseIds) {
            studentCourseRecords.addAll(getStudentCourseRecordsForCourse(personId, courseId, contextInfo));
        }
        return studentCourseRecords;
    }

    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
            String personId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return academicRecordServiceCurrent.getAttemptedCourseRecordsForTerm(personId, termId, context);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(
            String personId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        boolean historyDoesNotExist = false;

        List<StudentCourseRecordInfo> courseRecords;

        // Get historical data
        try {
            courseRecords = academicRecordServiceHistory.getCompletedCourseRecords(personId,context);
        } catch (DoesNotExistException ex) {
            courseRecords = new ArrayList<>();
            historyDoesNotExist = true;
        }

        if (historyDoesNotExist) {
            throw new DoesNotExistException("No course records for student Id = " + personId);
        }

        return courseRecords;
    }

    @Override
	public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
			String personId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
		return academicRecordServiceCurrent.getCompletedCourseRecordsForTerm(personId, termId, context);
	}
	
	@Override
	public GPAInfo getGPAForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
	public String getEarnedCreditsForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	public String getEarnedCredits(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        boolean historyDoesNotExist = false;
        boolean currentDoesNotExist = false;

        List<StudentCourseRecordInfo> courseRecords;

        // Get historical data
        try {
            courseRecords = academicRecordServiceHistory.getCompletedCourseRecordsForCourse(personId, courseId, contextInfo);
        } catch (DoesNotExistException ex) {
            courseRecords = new ArrayList<>();
            historyDoesNotExist = true;
        }

        // Get current data
        try {
            courseRecords.addAll(academicRecordServiceCurrent.getCompletedCourseRecordsForCourse(personId, courseId, contextInfo));
        } catch (DoesNotExistException ex) {
            currentDoesNotExist = true;
        }

        if (historyDoesNotExist && currentDoesNotExist) {
            throw new DoesNotExistException("No course records for student Id = " + personId);
        }

        return courseRecords;
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    public void setAcademicRecordServiceCurrent(AcademicRecordService academicRecordServiceCurrent) {
        this.academicRecordServiceCurrent = academicRecordServiceCurrent;
    }

    public void setAcademicRecordServiceHistory(AcademicRecordService academicRecordServiceHistory) {
        this.academicRecordServiceHistory = academicRecordServiceHistory;
    }

}
