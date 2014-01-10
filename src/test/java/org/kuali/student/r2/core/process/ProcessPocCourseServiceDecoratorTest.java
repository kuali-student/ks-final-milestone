/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import java.util.Calendar;
import java.util.Date;
import org.kuali.student.enrollment.class2.course.service.impl.CourseServiceMapImpl;

import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

/**
 *
 * @author nwright
 */
public class ProcessPocCourseServiceDecoratorTest {

    public ProcessPocCourseServiceDecoratorTest() {
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

    private ContextInfo getContextInfoAsOf12302011() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 30);
        return getContextInfo(cal.getTime());
    }

    private ContextInfo getContextInfo(Date asOfDate) {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("testPrincipal1");
        contextInfo.setCurrentDate(asOfDate);
        return contextInfo;

    }
    @Test
    public void testSomeMethod() throws Exception {
        ContextInfo context = this.getContextInfoAsOf12302011();
        CourseService courseService = new CourseServiceMapImpl();
        courseService = new ProcessPocCourseServiceDecorator(courseService);
        CourseInfo eng101 = courseService.getCourse("ENGL101", context);        
    }
}
