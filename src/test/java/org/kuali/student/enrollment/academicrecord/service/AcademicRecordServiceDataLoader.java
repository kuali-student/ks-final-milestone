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
import org.kuali.student.enrollment.class2.academicrecord.service.impl.AcademicRecordServiceMapImpl;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    private int countStudentId;
    private int countProgramId;

    @Resource
    private AcademicRecordService academicRecordService;

    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    // convenience method for generating dto
    private StudentProgramRecordInfo createStudentProgramRecord(String programId, String programTitle,
                                                                String programTypeKey, String programCode,
                                                                String creditsEarned) {
        StudentProgramRecordInfo studentProgramRecord = new StudentProgramRecordInfo();
        studentProgramRecord.setProgramId(programId);
        studentProgramRecord.setProgramTitle(programTitle);
        studentProgramRecord.setProgramTypeKey(programTypeKey);
        studentProgramRecord.setProgramCode(programCode);
        studentProgramRecord.setCreditsEarned(creditsEarned);
        return studentProgramRecord;
    }

    // convenience method for generating dto
    private StudentCredentialRecordInfo createStudentCredentialRecord(String programId, String programTitle,
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
        return credentialRecord;
    }

    // convenience method for generating dto
    private StudentTestScoreRecordInfo createStudentTestScoreRecord(String testCode, String testTitle,
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
        return testScoreRecord;
    }

    // convenience method for generating dto
    private GPAInfo createGpa(String calculationTypeKey, String scaleKey, String value) {
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey(calculationTypeKey);
        gpa.setScaleKey(scaleKey);
        gpa.setValue(value);
        return gpa;
    }

    // convenience method for generating dto
    private LoadInfo createLoad(String loadLevelTypeKey, int totalCredits) {
        LoadInfo load = new LoadInfo();
        load.setLoadLevelTypeKey(loadLevelTypeKey);
        load.setTotalCredits(new KualiDecimal(totalCredits));
        return load;
    }

    // convenience method for generating test studentId
    private String generateStudentId() {
        return Integer.toString(countStudentId++);
    }

    // convenience method for generating test programId
    private String generateProgramId() {
        return Integer.toString(countProgramId++);
    }

    @Override
    protected void initializeData() throws Exception {

        StudentProgramRecordInfo studentProgramRecord = createStudentProgramRecord(generateProgramId(),
                "program1", "programType", "programCode", "8");

        String student1Id = generateStudentId();

        String studentProgramRecordTypeKey = "kuali.student.acad.student.program.record";

        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, student1Id, studentProgramRecord, context);

        StudentProgramRecordInfo studentProgramRecord2 = createStudentProgramRecord(generateProgramId(),
                "program2", "programType", "programCode", "35");

        String student2Id = generateStudentId();

        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, student2Id, studentProgramRecord2, context);

        StudentCredentialRecordInfo credentialRecord = createStudentCredentialRecord(generateProgramId(),
           "Program One", "MP101", "Mock University of Kuali", 2014, Calendar.JANUARY, 1, 2014, Calendar.NOVEMBER, 20);

        String studentCredentialRecordTypeKey = "kuali.student.acad.student.credential.record";
        academicRecordService.createStudentCredentialRecord(studentCredentialRecordTypeKey, student1Id, credentialRecord, context);

        StudentTestScoreRecordInfo testScoreRecord = createStudentTestScoreRecord("mock.code.test1",
                "The First Mock Test", "mock.test.type.first", "70", "70", 2014, Calendar.JUNE, 03);
        String studentTestScoreRecordTypeKey = "kuali.student.acad.student.test.score.record";
        academicRecordService.createStudentTestScoreRecord(studentTestScoreRecordTypeKey, student1Id, testScoreRecord, context);

        testScoreRecord = createStudentTestScoreRecord("mock.code.test2",
                "The Second Mock Test", "mock.test.type.second", "74", "74", 2014, Calendar.NOVEMBER, 9);
        academicRecordService.createStudentTestScoreRecord(studentTestScoreRecordTypeKey, student1Id, testScoreRecord, context);

        String resultScaleId = "1";
        String atpId = "1";
        String studentCredentialRecordProgramId = credentialRecord.getProgramId();

        String gpaTypeKey = "kuali.student.acad.gpa.record";
        GPAInfo gpa = createGpa("mockTypeKey1", "1", "1.9");
        academicRecordService.createGPA(gpaTypeKey, student1Id, studentCredentialRecordProgramId, resultScaleId, atpId, gpa, context);
        gpa = new GPAInfo();
        gpa = createGpa("mockTypeKey2", "1", "2.9");
        academicRecordService.createGPA(gpaTypeKey, student1Id, studentCredentialRecordProgramId, resultScaleId, atpId, gpa, context);
        gpa = createGpa("mockTypeKey3", "1", "3.9");
        academicRecordService.createGPA(gpaTypeKey, student1Id, studentCredentialRecordProgramId, resultScaleId, atpId, gpa, context);

        LoadInfo load = createLoad("mock.TypeKey.MediumLoad", 4);
        String loadTypeKey = "kuali.student.acad.load.record";
        academicRecordService.createLoad(loadTypeKey, student1Id, atpId, load, context);

    }
}
