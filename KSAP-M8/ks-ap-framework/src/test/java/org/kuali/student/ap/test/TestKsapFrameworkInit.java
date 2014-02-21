package org.kuali.student.ap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testKsapFrameworkInit-context.xml" })
public class TestKsapFrameworkInit {

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1");
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() {
        assertNotNull("KSAP Context not found",
                KsapFrameworkServiceLocator.getContext());
        assertNotNull("AtpService provider not found",
                KsapFrameworkServiceLocator.getAtpService());
        assertNotNull("MessageService provider not found",
                KsapFrameworkServiceLocator.getMessageService());
        assertNotNull("CourseOfferingService provider not found",
                KsapFrameworkServiceLocator.getCourseOfferingService());
        assertNotNull("AcademicCalendarService provider not found",
                KsapFrameworkServiceLocator.getAcademicCalendarService());
        assertNotNull("CluService provider not found",
                KsapFrameworkServiceLocator.getCluService());
        assertNotNull("CourseService provider not found",
                KsapFrameworkServiceLocator.getCourseService());
    }

    @Test
    public void testAtpService() throws Throwable {
        AtpInfo atp = KsapFrameworkServiceLocator.getAtpService().getAtp(
                "76e1d98a-0f82-485b-academic-calendar",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        assertNotNull(atp);
        assertEquals("2011-2012 Academic Calendar", atp.getName());
    }

    @Test
    public void testAcalService() throws Throwable {
        AcademicCalendarInfo ac = KsapFrameworkServiceLocator
                .getAcademicCalendarService().getAcademicCalendar(
                        "76e1d98a-0f82-485b-academic-calendar",
                        KsapFrameworkServiceLocator.getContext()
                                .getContextInfo());
        assertNotNull(ac);
        assertEquals("2011-2012 Academic Calendar", ac.getName());
    }

}
