package org.kuali.student.enrollment.classI.hold.conformance.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;




import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.service.HoldService;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
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

		if (service == null) {
			ApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "testContext.xml" });
			service = (HoldService) appContext.getBean("mockHoldService");

		}
	}

	@After
	public void tearDown() {
	}

	private HoldService service;

	public HoldService getService() {

		return service;
	}

	public void setService(HoldService service) {
		this.service = service;
	}


	@Test
	public void testCreateHold() throws Exception {

		HoldInfo holdInfo = HoldInfo.newInstance("1221",
				HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
				HoldServiceConstants.HOLD_RELEASED_STATE_KEY,
				"Library Hold for Student 1", new RichTextInfo(), "21212",
				"1111", new Boolean(false), new Boolean(false), new Date(),
				new Date(), null, null);
		HoldInfo info = service.createHold(holdInfo, null);
		assertNotNull(info);
		assertEquals(holdInfo, info);

	}


	@Test
	public void testDeleteHold() throws Exception {

		HoldInfo holdInfo = HoldInfo.newInstance("1221",
				HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
				HoldServiceConstants.HOLD_RELEASED_STATE_KEY,
				"Library Hold for Student 1", new RichTextInfo(), "21212",
				"1111", new Boolean(false), new Boolean(false), new Date(),
				new Date(), null, null);
		HoldInfo info = service.createHold(holdInfo, null);
		assertNotNull(info);
		assertEquals(holdInfo, info);
		service.deleteHold("1221", null);
		assertNull(service.getHold("1221", null));

	}
}
