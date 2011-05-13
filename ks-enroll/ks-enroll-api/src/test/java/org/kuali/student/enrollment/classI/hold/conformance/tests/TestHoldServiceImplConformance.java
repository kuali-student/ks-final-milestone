package org.kuali.student.enrollment.classI.hold.conformance.tests;


import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.hold.service.HoldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHoldServiceImplConformance {

	public TestHoldServiceImplConformance() {
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

	private HoldService service;

	public HoldService getService() {
		if (service == null) {
			ApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "testContext.xml" });
			service = (HoldService) appContext.getBean("mockHoldService");

		}
		return service;
	}

	public void setService(HoldService service) {
		this.service = service;
	}

	/**
	 * Test of createBulkRelationshipsForPerson method,
	 */
	@Test
	public void testCreateHold() throws Exception {
	
		
	}

}
