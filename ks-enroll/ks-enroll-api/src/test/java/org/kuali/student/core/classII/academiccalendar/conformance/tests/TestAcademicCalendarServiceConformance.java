package org.kuali.student.core.classII.academiccalendar.conformance.tests;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.classII.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.classII.academiccalendar.service.AcademicCalendarService;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

 
@Ignore
public class TestAcademicCalendarServiceConformance {
	
	
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
	private AcademicCalendarService service = null;
	
	public AcademicCalendarService getService() {
		if (service == null) {
			ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{ "classpath:testContext.xml"});
			service = (AcademicCalendarService) appContext.getBean("academicCalendarServiceToTest");
            }
		return service;
	}
	
	@Test
	public void testCreateAcademicCalendar() throws Exception {
	
		
		AcademicCalendarInfo.Builder academicCalendarBuilder =  new AcademicCalendarInfo.Builder();
		

		academicCalendarBuilder.setEndDate(new Date(2010,12,01 )) ;
		academicCalendarBuilder.setStartDate(new Date(2010,01,01 ) );
		academicCalendarBuilder.setName("First AC");
		
		AcademicCalendarInfo createdAC  = getService().createAcademicCalendar("test1AC", academicCalendarBuilder.build(), TestHelper.getContext1());
		assertNotNull(createdAC);
		assertNotNull(createdAC.getKey());
	}

	@Test
	public void testGetAcademicCalendar() throws Exception {
		
		
		AcademicCalendarInfo retrievedAC  = getService().getAcademicCalendar("test1AC",TestHelper.getContext1());
		assertNotNull(retrievedAC);
		assertNotNull(retrievedAC.getKey());
	}
	
	
	
}
