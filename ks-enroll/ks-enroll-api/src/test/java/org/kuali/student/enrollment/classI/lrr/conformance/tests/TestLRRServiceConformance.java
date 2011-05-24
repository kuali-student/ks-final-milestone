package org.kuali.student.enrollment.classI.lrr.conformance.tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.classI.lrr.mock.LRRServiceMockImpl;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.test.utilities.TestHelper;

public class TestLRRServiceConformance {

	@BeforeClass
	public static void setUp() {

	}

	@AfterClass
	public static void tearDown() {

	}

	@Test
	public void testCreateLearningResultRecord() throws Exception {
		LearningResultRecordInfo lrrInfo = new LearningResultRecordInfo();
		lrrInfo.setId(String.valueOf(Math.random()));
		lrrInfo.setName("KS YY Course Result");
		lrrInfo.setResultValueId(String.valueOf(Math.random()));
		lrrInfo.setDescr(null);
		LRRServiceMockImpl lrrMockImpl = new LRRServiceMockImpl();

		LearningResultRecordInfo source = lrrMockImpl.createLearningResultRecord(lrrInfo, TestHelper.getContext1());
		assertNotNull(source);
		assertEquals(source, lrrInfo);

	}

	@Test
	public void testCreateResultSource() throws Exception {
		ResultSourceInfo sourceInfo = new ResultSourceInfo();
		sourceInfo.setArticulationId(String.valueOf(Math.random()));
		sourceInfo.setName("AB College YY Course Result");
		sourceInfo.setTypeKey("");

		LRRServiceMockImpl lrrMockImpl = new LRRServiceMockImpl();

		ResultSourceInfo source = lrrMockImpl.createResultSource(sourceInfo,
				TestHelper.getContext1());
		assertNotNull(source);
		assertEquals(source, sourceInfo);

	}
	
	public void testGetLearningResultRecord() throws Exception{
		LearningResultRecordInfo lrrInfo = new LearningResultRecordInfo();
		lrrInfo.setId(String.valueOf(Math.random()));
		lrrInfo.setName("KS YY Course Result");
		lrrInfo.setResultValueId(String.valueOf(Math.random()));
		lrrInfo.setDescr(null);
		LRRServiceMockImpl lrrMockImpl = new LRRServiceMockImpl();

		LearningResultRecordInfo source = lrrMockImpl.createLearningResultRecord(lrrInfo, TestHelper.getContext1());
		
		LearningResultRecordInfo returned = lrrMockImpl.getLearningResultRecord(source.getId(), TestHelper.getContext1());
		assertEquals(source, returned);
		
	}
}
