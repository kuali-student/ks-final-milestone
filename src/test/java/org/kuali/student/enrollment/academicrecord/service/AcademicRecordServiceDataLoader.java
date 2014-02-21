/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.test.AbstractMockServicesAwareDataLoader;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.population.service.impl.PopulationTestStudentEnum;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    @Resource
    private GesService gesService;

    public GesService getGesService() {
        return gesService;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }

    @Resource
    private AcademicRecordService academicRecordService;

    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    @Override
    protected void initializeData() throws Exception {
        StudentProgramRecordInfo freshman = createStudentProgramRecord("program1", "programType", "programCode",
                PopulationTestStudentEnum.STUDENT21.getCreditsCompleted());
        StudentProgramRecordInfo sophomore = createStudentProgramRecord("program2", "programType", "programCode",
                PopulationTestStudentEnum.STUDENT22.getCreditsCompleted());
        StudentProgramRecordInfo sophomoreOnThreshold = createStudentProgramRecord("program2", "programType", "programCode",
                PopulationTestStudentEnum.STUDENT23.getCreditsCompleted());
        StudentProgramRecordInfo juniorOnThreshold = createStudentProgramRecord("program2", "programType", "programCode",
                PopulationTestStudentEnum.STUDENT24.getCreditsCompleted());
        StudentProgramRecordInfo seniorOnThreshold = createStudentProgramRecord("program2", "programType", "programCode",
                PopulationTestStudentEnum.STUDENT25.getCreditsCompleted());

        String studentProgramRecordTypeKey = "kuali.student.acad.student.program.record";

        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey,
                PopulationTestStudentEnum.STUDENT21.getPersonId(), freshman, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey,
                PopulationTestStudentEnum.STUDENT22.getPersonId(), sophomore, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey,
                PopulationTestStudentEnum.STUDENT23.getPersonId(), sophomoreOnThreshold, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey,
                PopulationTestStudentEnum.STUDENT24.getPersonId(), juniorOnThreshold, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey,
                PopulationTestStudentEnum.STUDENT25.getPersonId(), seniorOnThreshold, context);

        StudentCourseRecordInfo student21CourseRecord = createStudentCourseRecord(PopulationTestStudentEnum.STUDENT21.getPersonId(),
                "coId", "crId", "courseTitle", "activityCode", "termId", "12", "12", true, 2014, 1, 6, 2014, 5, 16);
        StudentCourseRecordInfo student22CourseRecord = createStudentCourseRecord(PopulationTestStudentEnum.STUDENT22.getPersonId(),
                "coId", "crId", "courseTitle", "activityCode", "termId", "12", "12", true, 2014, 1, 6, 2014, 5, 16);
        StudentCourseRecordInfo student23CourseRecord = createStudentCourseRecord(PopulationTestStudentEnum.STUDENT23.getPersonId(),
                "coId", "crId", "courseTitle", "activityCode", "termId", "12", "12", true, 2014, 1, 6, 2014, 5, 16);
        StudentCourseRecordInfo student24CourseRecord = createStudentCourseRecord(PopulationTestStudentEnum.STUDENT24.getPersonId(),
                "coId", "crId", "courseTitle", "activityCode", "termId", "12", "12", true, 2014, 1, 6, 2014, 5, 16);

        StudentCredentialRecordInfo student21CredentialRecord = createStudentCredentialRecord(PopulationTestStudentEnum.STUDENT21.getPersonId(),
                "programId", "programTitle", "programCode", "awardingInstitution", 2014, 1, 6, 2014, 5, 16);
        StudentCredentialRecordInfo student22CredentialRecord = createStudentCredentialRecord(PopulationTestStudentEnum.STUDENT22.getPersonId(),
                "programId", "programTitle", "programCode", "awardingInstitution", 2014, 1, 6, 2014, 5, 16);
        StudentCredentialRecordInfo student23CredentialRecord = createStudentCredentialRecord(PopulationTestStudentEnum.STUDENT23.getPersonId(),
                "programId", "programTitle", "programCode", "awardingInstitution", 2014, 1, 6, 2014, 5, 16);
        StudentCredentialRecordInfo student24CredentialRecord = createStudentCredentialRecord(PopulationTestStudentEnum.STUDENT24.getPersonId(),
                "programId", "programTitle", "programCode", "awardingInstitution", 2014, 1, 6, 2014, 5, 16);

        StudentTestScoreRecordInfo student21TestScoreRecord = createStudentTestScoreRecord(PopulationTestStudentEnum.STUDENT21.getPersonId(),
                "testCode", "testTitle", "testTypeKey", "90", "55", 2014, 1, 6);
        StudentTestScoreRecordInfo student22TestScoreRecord = createStudentTestScoreRecord(PopulationTestStudentEnum.STUDENT22.getPersonId(),
                "testCode", "testTitle", "testTypeKey", "90", "55", 2014, 1, 6);
        StudentTestScoreRecordInfo student23TestScoreRecord = createStudentTestScoreRecord(PopulationTestStudentEnum.STUDENT23.getPersonId(),
                "testCode", "testTitle", "testTypeKey", "90", "55", 2014, 1, 6);
        StudentTestScoreRecordInfo student24TestScoreRecord = createStudentTestScoreRecord(PopulationTestStudentEnum.STUDENT24.getPersonId(),
                "testCode", "testTitle", "testTypeKey", "90", "55", 2014, 1, 6);

        GPAInfo student21Gpa = createGpa(PopulationTestStudentEnum.STUDENT21.getPersonId(), "calculationTypeKey", "scaleKey", "3.5");
        GPAInfo student22Gpa = createGpa(PopulationTestStudentEnum.STUDENT22.getPersonId(), "calculationTypeKey", "scaleKey", "2.5");
        GPAInfo student23Gpa = createGpa(PopulationTestStudentEnum.STUDENT23.getPersonId(), "calculationTypeKey", "scaleKey", "2");
        GPAInfo student24Gpa = createGpa(PopulationTestStudentEnum.STUDENT24.getPersonId(), "calculationTypeKey", "scaleKey", "4.0");

        LoadInfo student21Load = createLoad(PopulationTestStudentEnum.STUDENT21.getPersonId(), "loadLevelTypeKey", 17, "atpId");
        LoadInfo student22Load = createLoad(PopulationTestStudentEnum.STUDENT22.getPersonId(), "loadLevelTypeKey", 17, "atpId");
        LoadInfo student23Load = createLoad(PopulationTestStudentEnum.STUDENT23.getPersonId(), "loadLevelTypeKey", 17, "atpId");
        LoadInfo student24Load = createLoad(PopulationTestStudentEnum.STUDENT24.getPersonId(), "loadLevelTypeKey", 17, "atpId");

        academicRecordService.createStudentCourseRecord(student21CourseRecord.getTypeKey(), student21CourseRecord.getPersonId(), student21CourseRecord, context);
        academicRecordService.createStudentCourseRecord(student22CourseRecord.getTypeKey(), student22CourseRecord.getPersonId(), student22CourseRecord, context);
        academicRecordService.createStudentCourseRecord(student23CourseRecord.getTypeKey(), student23CourseRecord.getPersonId(), student23CourseRecord, context);
        academicRecordService.createStudentCourseRecord(student24CourseRecord.getTypeKey(), student24CourseRecord.getPersonId(), student24CourseRecord, context);

        academicRecordService.createStudentCredentialRecord(student21CredentialRecord.getTypeKey(), student21CredentialRecord.getPersonId(),
                student21CredentialRecord, context);
        academicRecordService.createStudentCredentialRecord(student22CredentialRecord.getTypeKey(), student22CredentialRecord.getPersonId(),
                student22CredentialRecord, context);
        academicRecordService.createStudentCredentialRecord(student23CredentialRecord.getTypeKey(), student23CredentialRecord.getPersonId(),
                student23CredentialRecord, context);
        academicRecordService.createStudentCredentialRecord(student24CredentialRecord.getTypeKey(), student24CredentialRecord.getPersonId(),
                student24CredentialRecord, context);

        academicRecordService.createStudentTestScoreRecord(student21TestScoreRecord.getTypeKey(), student21TestScoreRecord.getPersonId(),
                student21TestScoreRecord, context);
        academicRecordService.createStudentTestScoreRecord(student22TestScoreRecord.getTypeKey(), student22TestScoreRecord.getPersonId(),
                student22TestScoreRecord, context);
        academicRecordService.createStudentTestScoreRecord(student23TestScoreRecord.getTypeKey(), student23TestScoreRecord.getPersonId(),
                student23TestScoreRecord, context);
        academicRecordService.createStudentTestScoreRecord(student24TestScoreRecord.getTypeKey(), student24TestScoreRecord.getPersonId(),
                student24TestScoreRecord, context);

        academicRecordService.createGPA(student21Gpa.getTypeKey(), student21Gpa, context);
        academicRecordService.createGPA(student22Gpa.getTypeKey(), student22Gpa, context);
        academicRecordService.createGPA(student23Gpa.getTypeKey(), student23Gpa, context);
        academicRecordService.createGPA(student24Gpa.getTypeKey(), student24Gpa, context);

        academicRecordService.createLoad(student21Load.getTypeKey(), student21Load, context);
        academicRecordService.createLoad(student22Load.getTypeKey(), student22Load, context);
        academicRecordService.createLoad(student23Load.getTypeKey(), student23Load, context);
        academicRecordService.createLoad(student24Load.getTypeKey(), student24Load, context);
    }

    // convenience method for generating dto
    private StudentProgramRecordInfo createStudentProgramRecord(String programTitle,
                                                                String programTypeKey, String programCode,
                                                                String creditsEarned) {
        StudentProgramRecordInfo studentProgramRecord = new StudentProgramRecordInfo();
        studentProgramRecord.setProgramTitle(programTitle);
        studentProgramRecord.setProgramTypeKey(programTypeKey);
        studentProgramRecord.setProgramCode(programCode);
        studentProgramRecord.setCreditsEarned(new KualiDecimal(creditsEarned));
        studentProgramRecord.setTypeKey("kuali.student.acad.student.program.record");
        studentProgramRecord.setChildPrograms(new ArrayList<StudentProgramRecordInfo>());
        return studentProgramRecord;
    }

    // convenience method for generating dto
    private StudentCredentialRecordInfo createStudentCredentialRecord(String personId, String programId, String programTitle,
                                                                      String programCode, String awardingInstitution,
                                                                      int dateAdmittedYear, int dateAdmittedMonth, int dateAdmittedDay,
                                                                      int dateAwardedYear, int dateAwardedMonth, int dateAwardedDay) {
        StudentCredentialRecordInfo credentialRecord = new StudentCredentialRecordInfo();
        credentialRecord.setProgramId(programId);
        credentialRecord.setProgramCode(programCode);
        credentialRecord.setProgramTitle(programTitle);
        credentialRecord.setAwardingInstitution(awardingInstitution);
        Calendar cal = Calendar.getInstance();
        cal.set(dateAdmittedYear, dateAdmittedMonth, dateAdmittedDay);
        credentialRecord.setDateAdmitted(cal.getTime());
        cal.set(dateAwardedYear, dateAwardedMonth, dateAwardedDay);
        credentialRecord.setDateAwarded(cal.getTime());
        credentialRecord.setTypeKey("kuali.student.acad.student.credential.record");
        return credentialRecord;
    }

    // convenience method for generating dto
    private StudentCourseRecordInfo createStudentCourseRecord(String personId, String courseOfferingId, String courseRegistrationId,
                                                              String courseTitle, String activityCode, String termId,
                                                              String creditsEarned, String creditsForGPA, Boolean countsTowardCredits,
                                                              int dateBeginYear, int dateBeginMonth, int dateBeginDay,
                                                              int dateEndYear, int dateEndMonth, int dateEndDay) {
        StudentCourseRecordInfo studentCourseRecord = new StudentCourseRecordInfo();
        studentCourseRecord.setCourseOfferingId(courseOfferingId);
        studentCourseRecord.setCourseRegistrationId(courseRegistrationId);
        studentCourseRecord.setPersonId(personId);
        studentCourseRecord.setCourseTitle(courseTitle);
        studentCourseRecord.setActivityCode(activityCode);
        studentCourseRecord.setTermId(termId);
        studentCourseRecord.setCreditsEarned(creditsEarned);
        studentCourseRecord.setCreditsForGPA(creditsForGPA);
        studentCourseRecord.setCountsTowardCredits(countsTowardCredits);
        Calendar calBegin = Calendar.getInstance();
        calBegin.set(dateBeginYear, dateBeginMonth, dateBeginDay);
        studentCourseRecord.setCourseBeginDate(calBegin.getTime());
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(dateEndYear, dateEndMonth, dateEndDay);
        studentCourseRecord.setCourseEndDate(calEnd.getTime());
        studentCourseRecord.setTypeKey("kuali.student.acad.student.course.record");
        return studentCourseRecord;
    }

    // convenience method for generating dto
    private StudentTestScoreRecordInfo createStudentTestScoreRecord(String personId, String testCode, String testTitle,
                                                                String testTypeKey, String scorePercent,
                                                                String scoreValue,
                                                                int year, int month, int day) {
        StudentTestScoreRecordInfo testScoreRecord = new StudentTestScoreRecordInfo();
        testScoreRecord.setTestCode(testCode);
        testScoreRecord.setTestTitle(testTitle);
        testScoreRecord.setTestTypeKey(testTypeKey);
        testScoreRecord.setScorePercent(scorePercent);
        testScoreRecord.setScoreValue(scoreValue);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        testScoreRecord.setDateTaken(cal.getTime());
        testScoreRecord.setTypeKey("kuali.student.acad.student.test.score.record");
        return testScoreRecord;
    }

    // convenience method for generating dto
    private GPAInfo createGpa(String personId, String calculationTypeKey, String scaleKey, String value) {
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey(calculationTypeKey);
        gpa.setScaleKey(scaleKey);
        gpa.setValue(value);
        gpa.setTypeKey("kuali.student.acad.student.gpa");
        gpa.setPersonId(personId);
        return gpa;
    }

    // convenience method for generating dto
    private LoadInfo createLoad(String personId, String loadLevelTypeKey, int totalCredits, String atpId) {
        LoadInfo load = new LoadInfo();
        load.setLoadLevelTypeKey(loadLevelTypeKey);
        load.setTotalCredits(new KualiDecimal(totalCredits));
        load.setTypeKey("kuali.student.acad.student.load");
        load.setPersonId(personId);
        load.setAtpId(atpId);
        return load;
    }
}
