package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
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
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AcademicRecordServiceMockImpl implements AcademicRecordService{

    private CourseRegistrationService courseRegService;
    private GradingService gradingService;
    private LRCService lrcService;
    private AtpService atpService;
    private StudentCourseRecordAssembler courseRecordAssembler;

    //Mock datastructures
    private Map<String, GPAInfo> gpasMap = new LinkedHashMap<String, GPAInfo>();
    private Map<String, String> creditsMap = new LinkedHashMap<String, String>();
    private List<StudentCourseRecordInfo> courseRecordInfoList = new ArrayList<StudentCourseRecordInfo>();

    public AcademicRecordServiceMockImpl() {
        //GPAInfo
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey1");
        gpa.setScaleKey("1");
        gpa.setValue("1.9");
        gpasMap.put("gpa1", gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey2");
        gpa.setScaleKey("1");
        gpa.setValue("2.9");
        gpasMap.put("gpa2", gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey3");
        gpa.setScaleKey("1");
        gpa.setValue("3.9");
        gpasMap.put("gpa3", gpa);

        //Credits
        creditsMap.put("credits1", "1");
        creditsMap.put("credits2", "2");
        creditsMap.put("credits3", "3");

        //StudentCourseRecordInfo
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setCourseRegistrationId(Integer.toString(courseRecordInfoList.size()+1));
        courseRecord.setPersonId("12020303");
        courseRecord.setCourseTitle("Dummy Test Course 101");
        courseRecord.setCourseCode("DTC101");
        courseRecord.setActivityCode("dummyActivityCode");
        courseRecord.setTermName("term1");
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 1);
        courseRecord.setCourseBeginDate(cal.getTime());
        cal.set(2012, Calendar.NOVEMBER, 30);
        courseRecord.setCourseEndDate(cal.getTime());
        courseRecord.setAssignedGradeValue("3.0");
        courseRecord.setAssignedGradeScaleKey("1");
        courseRecord.setAdministrativeGradeValue("3.0");
        courseRecord.setAdministrativeGradeScaleKey("1");
        courseRecord.setCalculatedGradeValue("3.0");
        courseRecord.setCalculatedGradeScaleKey("1");
        courseRecordInfoList.add(courseRecord);
        courseRecord = new StudentCourseRecordInfo();
        courseRecord.setCourseRegistrationId(Integer.toString(courseRecordInfoList.size()+1));
        courseRecord.setPersonId("12020303");
        courseRecord.setCourseTitle("Dummy Test Course 102");
        courseRecord.setCourseCode("DTC102");
        courseRecord.setActivityCode("dummyActivityCode");
        courseRecord.setTermName("term2");
        cal.set(2012, Calendar.JANUARY, 1);
        courseRecord.setCourseBeginDate(cal.getTime());
        cal.set(2012, Calendar.NOVEMBER, 30);
        courseRecord.setCourseEndDate(cal.getTime());
        courseRecord.setAssignedGradeValue("3.0");
        courseRecord.setAssignedGradeScaleKey("1");
        courseRecord.setAdministrativeGradeValue("3.0");
        courseRecord.setAdministrativeGradeScaleKey("1");
        courseRecord.setCalculatedGradeValue("3.0");
        courseRecord.setCalculatedGradeScaleKey("1");
        courseRecordInfoList.add(courseRecord);

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
    /* Mocked */
	public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
			String personId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList){
            if(courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId)){
                courseRecords.add(courseRecord);
            }
        }
        return courseRecords;
	}

	@Override
	public List<StudentCourseRecordInfo> getCompletedCourseRecords(
			String personId, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList){
            if(courseRecord.getPersonId().equals(personId) && (courseRecord.getAssignedGradeValue()!= null || courseRecord.getAdministrativeGradeValue() != null)){
                courseRecords.add(courseRecord);
            }
        }
		return courseRecords;
	}

	@Override
	public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
			String personId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList){
            if(courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId) && (courseRecord.getAssignedGradeValue()!= null || courseRecord.getAdministrativeGradeValue() != null)){
                courseRecords.add(courseRecord);
            }
        }
		return courseRecords;
	}


	@Override
    /* Mocked */
	public GPAInfo getGPAForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return gpasMap.get("gpa1");
	}

	@Override
    /* Mocked */
	public GPAInfo getGPAForAcademicCalendar(String personId,
			String academicCalendarKey, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return gpasMap.get("gpa2");
	}

	@Override
    /* Mocked */
	public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return gpasMap.get("gpa3");
	}

	@Override
    /* Mocked */
	public String getEarnedCreditsForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        return creditsMap.get("credits1");
	}

	@Override
    /* Mocked */
	public String getEarnedCreditsForAcademicCalendar(String personId,
			String academicCalendarKey, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return creditsMap.get("credits2");
	}

	@Override
    /* Mocked */
	public String getEarnedCredits(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return creditsMap.get("credits3");
	}

}
