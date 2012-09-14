package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.academicrecord.service.assembler.StudentCourseRecordAssembler;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class AcademicRecordServiceImpl implements AcademicRecordService{
	private CourseRegistrationService courseRegService;

    public GradingService getGradingService() {
        return gradingService;
    }

    public void setGradingService(GradingService gradingService) {
        this.gradingService = gradingService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    private GradingService gradingService;
    private LRCService lrcService;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    private AtpService atpService;
	private StudentCourseRecordAssembler courseRecordAssembler;
	
	public CourseRegistrationService getCourseRegService() {
		return courseRegService;
	}

	public void setCourseRegService(CourseRegistrationService courseRegService) {
		this.courseRegService = courseRegService;
	}

	
	public StudentCourseRecordAssembler getCourseRecordAssembler() {
		return courseRecordAssembler;
	}

	public void setCourseRecordAssembler(
			StudentCourseRecordAssembler courseRecordAssembler) {
		courseRecordAssembler.setAtpService(atpService);
        courseRecordAssembler.setGradingService(gradingService);
        courseRecordAssembler.setLrcService(lrcService);
        this.courseRecordAssembler = courseRecordAssembler;
	}

	@Override
	public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
			String personId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
		try {
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudentAndTerm(personId, termId, context);
			if(regs != null && !regs.isEmpty()){
				for (CourseRegistrationInfo reg : regs ){
					StudentCourseRecordInfo courseRecord = courseRecordAssembler.assemble(reg, context);
					if (courseRecord != null) courseRecords.add(courseRecord);
				}
			}
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		} catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return courseRecords;
	}

	@Override
	public List<StudentCourseRecordInfo> getCompletedCourseRecords(
			String personId, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
		try {
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudent(personId, context);
			getCompletedCourseRecords(courseRecords, regs, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		}

		return courseRecords;
	}

	@Override
	public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
			String personId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
		try {
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudentAndTerm(personId, termId, context);
			getCompletedCourseRecords(courseRecords, regs, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		}

		return courseRecords;
	}

	private void getCompletedCourseRecords(List<StudentCourseRecordInfo> courseRecords, List<CourseRegistrationInfo> regs, ContextInfo context)
            throws OperationFailedException {
		if(regs != null && !regs.isEmpty()){
			for (CourseRegistrationInfo reg : regs ){
                StudentCourseRecordInfo courseRecord = null;
                try {
                    courseRecord = courseRecordAssembler.assemble(reg, context);
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException : " + e.getMessage());
                }

                if (courseRecord != null) {
					if(courseRecord.getAssignedGradeValue()!= null || courseRecord.getAdministrativeGradeValue() != null)
						courseRecords.add(courseRecord);
				}
			}
		}		
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
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
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
}
