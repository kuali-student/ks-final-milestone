package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.academicrecord.service.assembler.StudentCourseRecordAssembler;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class AcademicRecordServiceMockImpl implements AcademicRecordService{

    private CourseRegistrationService courseRegService;
    private GradingService gradingService;
    private LRCService lrcService;
    private AtpService atpService;
    private StudentCourseRecordAssembler courseRecordAssembler;

    //Mock datastructures
    private Map<String, GPAInfo> gpas = new LinkedHashMap<String, GPAInfo>();
    private Map<String, String> credits = new LinkedHashMap<String, String>();

    public AcademicRecordServiceMockImpl() {
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey1");
        gpa.setScaleKey("1");
        gpa.setValue("1.9");
        gpas.put("gpa1",gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey2");
        gpa.setScaleKey("1");
        gpa.setValue("2.9");
        gpas.put("gpa2",gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey3");
        gpa.setScaleKey("1");
        gpa.setValue("3.9");
        gpas.put("gpa3",gpa);
        credits.put("credits1", "1");
        credits.put("credits2", "2");
        credits.put("credits3", "3");
    }

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

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }


	
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
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsForStudentByTerm(personId, termId, context);
			if(regs != null && !regs.isEmpty()){
				for (CourseRegistrationInfo reg : regs ){
					StudentCourseRecordInfo courseRecord = courseRecordAssembler.assemble(reg, context);
					if (courseRecord != null) courseRecords.add(courseRecord);
				}
			}
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		} catch (DisabledIdentifierException e) {
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
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsForStudent(personId, context);
			getCompletedCourseRecords(courseRecords, regs, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		} catch (DisabledIdentifierException e) {
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
			List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsForStudentByTerm(personId, termId, context);
			getCompletedCourseRecords(courseRecords, regs, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException();
		} catch (DisabledIdentifierException e) {
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
    /* Mocked */
	public GPAInfo getGPAForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return gpas.get("gpa1");
	}

	@Override
    /* Mocked */
	public GPAInfo getGPAForAcademicCalendar(String personId,
			String academicCalendarKey, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return gpas.get("gpa2");
	}

	@Override
    /* Mocked */
	public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return gpas.get("gpa3");
	}

	@Override
    /* Mocked */
	public String getEarnedCreditsForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        return credits.get("credits1");
	}

	@Override
    /* Mocked */
	public String getEarnedCreditsForAcademicCalendar(String personId,
			String academicCalendarKey, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return credits.get("credits2");
	}

	@Override
    /* Mocked */
	public String getEarnedCredits(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return credits.get("credits3");
	}

}
