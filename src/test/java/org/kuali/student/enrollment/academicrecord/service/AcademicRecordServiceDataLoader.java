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
        StudentProgramRecordInfo freshman = createStudentProgramRecord("program1", "programType", "programCode", "8");
        StudentProgramRecordInfo sophomore = createStudentProgramRecord("program2", "programType", "programCode", "35");
        StudentProgramRecordInfo sophomoreOnThreshold = createStudentProgramRecord("program2", "programType", "programCode", "25");
        StudentProgramRecordInfo juniorOnThreshold = createStudentProgramRecord("program2", "programType", "programCode", "55");
        StudentProgramRecordInfo seniorOnThreshold = createStudentProgramRecord("program2", "programType", "programCode", "85");

        String studentProgramRecordTypeKey = "kuali.student.acad.student.program.record";

        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, PopulationTestStudentEnum.STUDENT21.getPersonId(), freshman, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, PopulationTestStudentEnum.STUDENT22.getPersonId(), sophomore, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, PopulationTestStudentEnum.STUDENT23.getPersonId(), sophomoreOnThreshold, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, PopulationTestStudentEnum.STUDENT24.getPersonId(), juniorOnThreshold, context);
        academicRecordService.createStudentProgramRecord(studentProgramRecordTypeKey, PopulationTestStudentEnum.STUDENT25.getPersonId(), seniorOnThreshold, context);
    }

    // convenience method for generating dto
    private StudentProgramRecordInfo createStudentProgramRecord(String programTitle,
                                                                String programTypeKey, String programCode,
                                                                String creditsEarned) {
        StudentProgramRecordInfo studentProgramRecord = new StudentProgramRecordInfo();
        studentProgramRecord.setProgramTitle(programTitle);
        studentProgramRecord.setProgramTypeKey(programTypeKey);
        studentProgramRecord.setProgramCode(programCode);
        studentProgramRecord.setCreditsEarned(creditsEarned);
        studentProgramRecord.setTypeKey("kuali.student.acad.student.program.record");
        studentProgramRecord.setChildPrograms(new ArrayList<StudentProgramRecordInfo>());
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
        credentialRecord.setTypeKey("kuali.student.acad.student.credential.record");
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
        testScoreRecord.setTypeKey("kuali.student.acad.student.test.score.record");
        return testScoreRecord;
    }

    // convenience method for generating dto
    private GPAInfo createGpa(String calculationTypeKey, String scaleKey, String value) {
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey(calculationTypeKey);
        gpa.setScaleKey(scaleKey);
        gpa.setValue(value);
        gpa.setTypeKey("kuali.student.acad.student.gpa");
        return gpa;
    }

    // convenience method for generating dto
    private LoadInfo createLoad(String loadLevelTypeKey, int totalCredits) {
        LoadInfo load = new LoadInfo();
        load.setLoadLevelTypeKey(loadLevelTypeKey);
        load.setTotalCredits(new KualiDecimal(totalCredits));
        load.setTypeKey("kuali.student.acad.student.load");
        return load;
    }
}
