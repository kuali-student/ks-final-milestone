/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceMapImpl;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;


/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestCourseOfferingServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestCourseOfferingServiceDataLoadingDecoratorTest() {
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
        CourseOfferingService coService = new CourseOfferingServiceMapImpl();
        coService = new ProcessIntegrationTestCourseOfferingServiceDataLoadingDecorator(coService);
        CourseOfferingInfo eng101 = coService.getCourseOffering("ENGL101-FA2011", context);
        List<FormatOfferingInfo> fos = coService.getFormatOfferingsByCourseOffering(eng101.getId(), context);
        assertEquals (1, fos.size());
        List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCourseOffering(eng101.getId(), context);
        assertEquals (1, aos.size());
        List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsForCourseOffering(eng101.getId(), context);
        assertEquals (1, rgs.size());
        RegistrationGroupInfo rg = rgs.get(0);
        assertEquals ("ENGL101-FA2011", rg.getId());
        rg = coService.getRegistrationGroup("ENGL101-FA2011", context);
        assertEquals ("ENGL101-FA2011", rg.getId());
    }
}
