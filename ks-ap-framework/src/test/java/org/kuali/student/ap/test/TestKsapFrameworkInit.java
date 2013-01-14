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
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
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
		assertNotNull("Kuali Rice default resouce loader not found",
				GlobalResourceLoader.getResourceLoader());
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
				"19841985ACADEMICCALENDAR",
				KsapFrameworkServiceLocator.getContext().getContextInfo());
		assertNotNull(atp);
		assertEquals("1984-1985 Academic Calendar", atp.getName());
	}

	@Test
	public void testAcalService() throws Throwable {
		AcademicCalendarInfo ac = KsapFrameworkServiceLocator
				.getAcademicCalendarService().getAcademicCalendar(
						"19841985ACADEMICCALENDAR",
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
		assertNotNull(ac);
		assertEquals("1984-1985 Academic Calendar", ac.getName());
		List<String> holiday = ac.getHolidayCalendarIds();
		assertEquals(1, holiday.size());
		assertEquals("19841985HOLIDAYCALENDAR", holiday.get(0));
	}

}
