package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.academicrecord.service.assembler.StudentCourseRecordAssembler;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AcademicRecordServiceMockImpl implements AcademicRecordService, MockService {

    private CourseRegistrationService courseRegService;
    private GradingService gradingService;
    private LRCService lrcService;
    private AtpService atpService;
    private StudentCourseRecordAssembler courseRecordAssembler;

    //Mock datastructures
    private Map<String, GPAInfo> gpasMap = new LinkedHashMap<String, GPAInfo>();
    private Map<String, String> creditsMap = new LinkedHashMap<String, String>();
    private List<StudentCourseRecordInfo> courseRecordInfoList = new ArrayList<StudentCourseRecordInfo>();
    private Map<String, LoadInfo> loadsMap = new LinkedHashMap<String, LoadInfo>();
    private Map<String, StudentProgramRecordInfo> studentProgramRecordsMap = new LinkedHashMap<String, StudentProgramRecordInfo>();
    private Map<String, StudentCredentialRecordInfo> studentCredentialRecordsMap = new LinkedHashMap<String, StudentCredentialRecordInfo>();
    private Map<String, StudentTestScoreRecordInfo> studentTestScoreRecordsMap = new LinkedHashMap<String, StudentTestScoreRecordInfo>();

    @Override
    public void clear() {
        gpasMap.clear();
        creditsMap.clear();
        courseRecordInfoList.clear();
        loadsMap.clear();
        studentProgramRecordsMap.clear();
        studentCredentialRecordsMap.clear();
        studentTestScoreRecordsMap.clear();
    }

    public AcademicRecordServiceMockImpl() {
        //StudentProgramRecordInfo
        StudentProgramRecordInfo programRecord = new StudentProgramRecordInfo();
        programRecord.setProgramId("mock.id.program1");
        programRecord.setProgramTitle("Program One");
        programRecord.setProgramCode("MP101");
        programRecord.setProgramTypeKey("mock.program.type.graduate");
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 1);
        programRecord.setAdmittedDate(cal.getTime().toString());
        programRecord.setCreditsEarned("2");
        programRecord.setClassStanding("14");
        studentProgramRecordsMap.put("1",programRecord);

        //StudentCredentialRecordInfo
        StudentCredentialRecordInfo credentialRecord = new  StudentCredentialRecordInfo();
        credentialRecord.setProgramId("mock.id.program1");
        credentialRecord.setProgramCode("MP101");
        credentialRecord.setProgramTitle("Program One");
        credentialRecord.setAwardingInstitution("Mock University of Kuali");
        cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 1);
        credentialRecord.setDateAdmitted(cal.getTime());
        cal.set(2012, Calendar.NOVEMBER, 20);
        credentialRecord.setDateAwarded(cal.getTime());
        studentCredentialRecordsMap.put("1",credentialRecord);

        //StudentTestScoreRecordInfo
        StudentTestScoreRecordInfo testScoreRecord = new StudentTestScoreRecordInfo();
        testScoreRecord.setTestCode("mock.code.test1");
        testScoreRecord.setTestTitle("The First Mock Test");
        testScoreRecord.setTestTypeKey("mock.test.type.first");
        testScoreRecord.setScorePercent("70");
        testScoreRecord.setScoreValue("70");
        cal.set(2012, Calendar.JUNE, 03);
        testScoreRecord.setDateTaken(cal.getTime());
        studentTestScoreRecordsMap.put("1",testScoreRecord);

        testScoreRecord = new StudentTestScoreRecordInfo();
        testScoreRecord.setTestCode("mock.code.test2");
        testScoreRecord.setTestTitle("The Second Mock Test");
        testScoreRecord.setTestTypeKey("mock.test.type.second");
        testScoreRecord.setScorePercent("74");
        testScoreRecord.setScoreValue("74");
        cal.set(2012, Calendar.NOVEMBER, 9);
        testScoreRecord.setDateTaken(cal.getTime());
        studentTestScoreRecordsMap.put("2",testScoreRecord);

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

        //LoadInfo
        LoadInfo load = new LoadInfo();
        load.setLoadLevelTypeKey("mock.TypeKey.MediumLoad");
        load.setTotalCredits("4");
        loadsMap.put("mediumLoad", load);

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
        cal = Calendar.getInstance();
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
        courseRecord.setCreditsEarned("5");
        courseRecord.setCreditsForGPA("5");
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
        courseRecord.setCreditsEarned("2");
        courseRecord.setCreditsForGPA("2");
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
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse( String personId, String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList){
            if(courseRecord.getPersonId().equals(personId) && courseRecord.getCourseCode().equals(courseId) && (courseRecord.getAssignedGradeValue()!= null || courseRecord.getAdministrativeGradeValue() != null)){
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
	public GPAInfo getGPAForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return gpasMap.get("gpa1");
	}


	@Override
	public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return gpasMap.get("gpa3");
	}

    @Override
    public GPAInfo getCumulativeGPAForProgram( String personId,  String programId, String calculationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram( String personId,  String programId,  String termKey,  String calculationTypeKey,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    @Override
    public LoadInfo getLoadForTerm(String personId,  String termId,  String calculationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return loadsMap.get("mediumLoad");
    }

    @Override
    public List<StudentProgramRecordInfo> getProgramRecords( String personId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return Collections.singletonList(studentProgramRecordsMap.get("1"));
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials( String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return Collections.singletonList(studentCredentialRecordsMap.get("1"));
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("1"));
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType( String personId,  String testTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("2"));
    }

    @Override
	public String getEarnedCreditsForTerm(String personId, String termId,
			String calculationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        return creditsMap.get("credits1");
	}

	@Override
	public String getEarnedCredits(String personId, String calculationTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        return creditsMap.get("credits3");
	}

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm( String personId,  String programId, String termId, String calculationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return creditsMap.get("credits2");
    }

}
