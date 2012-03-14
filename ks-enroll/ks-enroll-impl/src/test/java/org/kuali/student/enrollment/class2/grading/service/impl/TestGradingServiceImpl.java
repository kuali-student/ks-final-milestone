package org.kuali.student.enrollment.class2.grading.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:grading-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestGradingServiceImpl {

    private GradingService gradingService;

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    @Autowired
	public void setAcalServiceValidation(GradingService gradingService) {
		this.gradingService = gradingService;
	}

    @Before
    public void setUp() {
        principalId = "123";    
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }
    
    @Test
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
    public void testGetFinalGradeRostersForCourseOffering() throws Exception {

        Set<String> expcActivityIds = new HashSet<String>();

        Set<String> expcGraderIds = new HashSet<String>();
        expcGraderIds.add("admin");

        Set<String> expcEntryIds = new HashSet<String>();
        expcEntryIds.add("lprRosterEntry2");

        String courseOfferingId = "Lui-1";
        List<GradeRosterInfo> gradeRosters = gradingService.getFinalGradeRostersForCourseOffering(courseOfferingId, contextInfo);
        assertNotNull("Roster list is null.", gradeRosters);
        assertEquals("Number of rosters not as expected.", 1, gradeRosters.size());

        GradeRosterInfo roster = gradeRosters.get(0);
        assertEquals("Course Offering ID not as expected.", courseOfferingId, roster.getCourseOfferingId());

        List<String> activityIds = roster.getActivityOfferingIds();
        assertNotNull("Null activity id list.", activityIds);
        assertEquals("Different number of activity ids returned than expected.", expcActivityIds.size(), activityIds.size());
        assertTrue("Unexpected activity ids returned.", activityIds.containsAll(expcActivityIds));

        List<String> graderIds = roster.getGraderIds();
        assertNotNull("Null grader id list.", graderIds);
        assertEquals("Different number of grader ids returned than expected.", expcGraderIds.size(), graderIds.size());
        assertTrue("Unexpected grader ids returned.", graderIds.containsAll(expcGraderIds));

        List<String> entryIds = roster.getGradeRosterEntryIds();
        assertNotNull("Null entry id list.", entryIds);
        assertEquals("Different number of entry ids returned than expected.", expcEntryIds.size(), entryIds.size());
        assertTrue("Unexpected entry ids returned.", entryIds.containsAll(expcEntryIds));
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
    public void testGetGradeRosterEntriesByRosterId() throws Exception {
        String rosterId = "testLprRoster1";
        List<String> expcStudentIds = new ArrayList<String>();
        expcStudentIds.add("admin");
        expcStudentIds.add("testPersonId1");

        List<GradeRosterEntryInfo> gradeRosterEntries = gradingService.getGradeRosterEntriesByRosterId(rosterId, contextInfo);
        assertNotNull("Result list is null.", gradeRosterEntries);

        List<String> studentIds = new ArrayList<String>();
        for (GradeRosterEntryInfo entry : gradeRosterEntries) {
            studentIds.add(entry.getStudentId());
        }

        assertEquals("Number of results not as expected.", expcStudentIds.size(), studentIds.size());
        assertTrue("Expected results not returned.", studentIds.containsAll(expcStudentIds));
        assertTrue("Unexpected results returned.", expcStudentIds.containsAll(studentIds));
    }

    @Test
    @Ignore("Not implemented.") // TODO implement method
    public void testGetValidGradesForStudentByRoster() throws Exception {

    }

    @Test
    public void testGetFinalGradeForStudentInCourseOffering() throws Exception {
        String studentId = "testPersonId1";
        String courseOfferingId = "Lui-1";
        GradeRosterEntryInfo entry = gradingService.getFinalGradeForStudentInCourseOffering(studentId, courseOfferingId, contextInfo);
        assertNotNull("Entry not returned.", entry);
        assertEquals("Student ID does not match.", studentId, entry.getStudentId());
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
