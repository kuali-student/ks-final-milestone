/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcademicCalendarServiceMockImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseR1TestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseServiceR1MockImpl;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;

/**
 *
 * @author nwright
 */
public class TestR1CourseVersionHelper {

    public TestR1CourseVersionHelper() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private void loadData(CourseService courseService, AcademicCalendarService acalService) {

        AcalTestDataLoader acalLoader = new AcalTestDataLoader(acalService);
        acalLoader.loadTerm("2011SP", "Spring 2011", "Spring Term 2011", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2011-03-01 00:00:00.0", "2011-05-31 00:00:00.0");
        acalLoader.loadTerm("2011FA", "Fall 2011", "Fall Term 2011", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2011-09-01 00:00:00.0", "2011-12-31 00:00:00.0");
        acalLoader.loadTerm("2012SP", "Spring 2012", "Spring Term 2012", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2012-03-01 00:00:00.0", "2012-05-31 00:00:00.0");
        acalLoader.loadTerm("2012FA", "Fall 2012", "Fall Term 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY,
                "2012-09-01 00:00:00.0", "2012-12-31 00:00:00.0");
        acalLoader.loadTerm("2013SP", "Spring 2013", "Spring Term 2013", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                "2013-03-01 00:00:00.0", "2013-05-31 00:00:00.0");

        CourseR1TestDataLoader courseLoader = new CourseR1TestDataLoader(courseService);
        courseLoader.loadCourse("COURSE1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1", "COURSE1-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);
        courseLoader.loadCourse("COURSE2", "2012SP", "ENG", "ENG101", "Intro English", "description 2", "COURSE2-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, null);
    }

    /**
     * Test of getCourse method, of class R1CourseVersionHelper.
     */
    @Test
    public void testGetCourse() throws Exception {
        System.out.println("getCourse");
        CourseService courseService = new CourseServiceR1MockImpl();
        AcademicCalendarService acalService = new AcademicCalendarServiceMockImpl();
        loadData (courseService, acalService);
        R1CourseServiceHelper instance = new R1CourseServiceHelper(courseService, acalService);
        CourseInfo course = instance.getCourse("COURSE1");
        assertEquals ("COURSE1", course.getId());
        course = instance.getCourse("COURSE2");
        assertEquals ("COURSE2", course.getId());
    }

    /**
     * Test of getCoursesForTerm method, of class R1CourseVersionHelper.
     */
    @Test
    public void testGetCoursesForTerm() throws Exception {
        System.out.println("getCoursesForTerm");
        String courseId = null;
        String targetTermId = null;
        ContextInfo context = new ContextInfo ();
        CourseService courseService = new CourseServiceR1MockImpl();
        AcademicCalendarService acalService = new AcademicCalendarServiceMockImpl();
        loadData (courseService, acalService);
        R1CourseServiceHelper instance = new R1CourseServiceHelper(courseService, acalService);
        List<CourseInfo> courses = null;
        
        courseId = "COURSE1";
        targetTermId = "2011SP";
        courses = instance.getCoursesForTerm(courseId, targetTermId, context);
        assertEquals (0, courses.size());
        
        
        courseId = "COURSE1";
        targetTermId = "2012SP";
        courses = instance.getCoursesForTerm(courseId, targetTermId, context);
        assertEquals (0, courses.size());
        
        courseId = "COURSE1";
        targetTermId = "2012FA";
        courses = instance.getCoursesForTerm(courseId, targetTermId, context);
        assertEquals (1, courses.size());
        
        courseId = "COURSE1";
        targetTermId = "2013SP";
        courses = instance.getCoursesForTerm(courseId, targetTermId, context);
        assertEquals (1, courses.size());
    }
}
