package org.kuali.student.enrollment.classII.grading.conformance.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.classII.grading.service.GradingServiceMockImpl;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.test.utilities.TestHelper;


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
        greInfo.setAssignedGradeKey(String.valueOf(Math.random()));
        greInfo.setCalculatedGradeKey(String.valueOf(Math.random()));
        greInfo.setCreditsEarnedKey(String.valueOf(Math.random()));
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

        for (String key : keys) {
            GradeRosterInfo grInfo = serviceMock.getGradeRoster(key, TestHelper.getContext1());
            assertTrue(serviceMock.gradeRostersCache.containsValue(grInfo));
        }

    }

}
