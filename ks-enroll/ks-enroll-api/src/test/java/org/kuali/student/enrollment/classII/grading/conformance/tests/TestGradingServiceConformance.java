package org.kuali.student.enrollment.classII.grading.conformance.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.cxf.test.TestUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.classII.grading.service.GradingServiceMockImpl;
import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CalculatedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CreditsEarnedInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.test.utilities.TestHelper;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Conformance test for Grading Service
 * 
 * @author Kuali Student Team (sambit)
 */

@Ignore
public class TestGradingServiceConformance {

    @Before
    public void setUp() {

        serviceMock = new GradingServiceMockImpl();

        GradeRosterEntryInfo greInfo = new GradeRosterEntryInfo();
        greInfo.setActivityOfferingId(String.valueOf(1));
        greInfo.setAssignedGrade(new AssignedGradeInfo());
        greInfo.setCalculatedGrade(new CalculatedGradeInfo());
        greInfo.setCreditsEarned(new CreditsEarnedInfo());
        greInfo.setId(String.valueOf(Math.random()));
        serviceMock.gradeRosterEntriesCache.put(greInfo.getId(), greInfo);
        GradeRosterInfo gradeRosterInfo = new GradeRosterInfo();
        gradeRosterInfo.setId(String.valueOf(Math.random()));
        gradeRosterInfo.setCourseOfferingId(String.valueOf(1234));
        List<String> entryIds = new ArrayList<String>();
        entryIds.add(greInfo.getId());
        gradeRosterInfo.setGradeRosterEntryIds(entryIds);
        serviceMock.gradeRostersCache.put(gradeRosterInfo.getId(), gradeRosterInfo);
    }

    @After
    public void tearDown() {
        
        serviceMock.gradeRosterEntriesCache.clear();
        serviceMock.gradeRostersCache.clear();
    }

    private static GradingServiceMockImpl serviceMock;

    @Test
    public void testGetGradeRoster() throws Exception {

        Set<String> keys = serviceMock.gradeRosterEntriesCache.keySet();

        List<String> keysList = Arrays.asList(keys.toArray());

        for (String key : keysList) {
            GradeRosterInfo grInfo = serviceMock.getGradeRoster(key, TestHelper.getContext1());
            assertTrue(serviceMock.gradeRostersCache.containsValue(grInfo));
        }

    }

}
