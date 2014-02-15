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

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.population.service.impl.PopulationTestStudentEnum;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.ClassStanding;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acad-test-with-map-context.xml"})
public class AcademicRecordServiceTest {

    @Resource
    private AcademicRecordServiceDataLoader dataLoader;

    @Resource(name = "academicRecordService")
    private AcademicRecordService academicRecordService;

    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    private ContextInfo contextInfo;
    private String principalId = "123";

    @org.junit.Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @After
    public void cleanup() throws Exception {
        dataLoader.afterTest();
    }

    private void checkStudentProgramRecords(String personId) throws Exception {
        List<StudentProgramRecordInfo> studentProgramRecords = academicRecordService.getProgramRecords(personId, contextInfo);
        for(StudentProgramRecordInfo studentProgramRecord : studentProgramRecords) {
            String classStanding = studentProgramRecord.getClassStanding();
            String creditsEarned = studentProgramRecord.getCreditsEarned();

            int FRESHMAN_THRESHOLD = AcademicRecordIntegrationTestGesServiceDataLoadingDecorator.FRESHMAN_CLASS_STANDING_THRESHOLD.intValue();
            int SOPHOMORE_THRESHOLD = AcademicRecordIntegrationTestGesServiceDataLoadingDecorator.SOPHOMORE_CLASS_STANDING_THRESHOLD.intValue();
            int JUNIOR_THRESHOLD = AcademicRecordIntegrationTestGesServiceDataLoadingDecorator.JUNIOR_CLASS_STANDING_THRESHOLD.intValue();
            int SENIOR_THRESHOLD = AcademicRecordIntegrationTestGesServiceDataLoadingDecorator.SENIOR_CLASS_STANDING_THRESHOLD.intValue();

            if(classStanding.equals(ClassStanding.FRESHMAN.getDescription())) {
                assertTrue("Freshman threshold test failed", Integer.valueOf(creditsEarned) > FRESHMAN_THRESHOLD && Integer.valueOf(creditsEarned) < SOPHOMORE_THRESHOLD);
            } else if(classStanding.equals(ClassStanding.SOPHOMORE.getDescription())) {
                assertTrue("Sophomore threshold test failed", Integer.valueOf(creditsEarned) >= SOPHOMORE_THRESHOLD && Integer.valueOf(creditsEarned) < JUNIOR_THRESHOLD);
            } else if(classStanding.equals(ClassStanding.JUNIOR.getDescription())) {
                assertTrue("Junior threshold test failed", Integer.valueOf(creditsEarned) >= JUNIOR_THRESHOLD && Integer.valueOf(creditsEarned) < SENIOR_THRESHOLD);
            } else if(classStanding.equals(ClassStanding.SENIOR.getDescription())) {
                assertTrue("Senior threshold test failed", Integer.valueOf(creditsEarned) >= SENIOR_THRESHOLD);
            }
        }
    }

    @Test
    public void testDataLoader() throws Exception {
        dataLoader.beforeTest();

        checkStudentProgramRecords(PopulationTestStudentEnum.STUDENT21.getPersonId());
        checkStudentProgramRecords(PopulationTestStudentEnum.STUDENT22.getPersonId());
        checkStudentProgramRecords(PopulationTestStudentEnum.STUDENT23.getPersonId());
        checkStudentProgramRecords(PopulationTestStudentEnum.STUDENT24.getPersonId());
        checkStudentProgramRecords(PopulationTestStudentEnum.STUDENT25.getPersonId());
    }
}
