package org.kuali.student.enrollment.class2.grading.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"}) // TODO create own config file
public class TestGradingServiceImpl {

    private GradingService gradingService;

    public static String principalId = "123";
    public ContextInfo contextInfo = ContextInfo.newInstance();

    //@Autowired // TODO autowire
	public void setAcalServiceValidation(GradingService gradingService) {
		this.gradingService = gradingService;
	}

    @Before
    public void setUp() {
        principalId = "123";    
        contextInfo = ContextInfo.getInstance(contextInfo);
        contextInfo.setPrincipalId(principalId);
    }
    
    @Test
    @Ignore("Need to get service wired")
    public void testSetup() {
        assertNotNull(gradingService);
    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRosterType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRostersByGraderAndTerm() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetFinalGradeRostersForCourseOffering() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetFinalGradeRostersForActivityOffering() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRostersForActivityOffering() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testBuildInterimGradeRosterByType() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateInterimGradeRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testDeleteInterimGradeRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateFinalGradeRosterState() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testValidateGradeRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRosterEntry() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRosterEntriesByIdList() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetGradeRosterEntriesByRosterId() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetValidGradesForStudentByRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetFinalGradeForStudentInCourseOffering() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testAddEntrytoInterimRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testRemoveEntryFromInterimRoster() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateAssignedGrade() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testUpdateCredit() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetDataDictionaryEntryKeys() throws Exception {

    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetDataDictionaryEntry() throws Exception {

    }
}
