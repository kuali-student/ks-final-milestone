/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.atp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r1.core.atp.dto.AtpTypeInfo;
import org.kuali.student.r1.core.atp.dto.DateRangeInfo;
import org.kuali.student.r1.core.atp.dto.DateRangeTypeInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r1.core.atp.dto.MilestoneTypeInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

@Daos( { @Dao(value = "org.kuali.student.r1.core.atp.dao.impl.AtpDaoImpl", testDataFile = "classpath:atp-test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpService extends AbstractServiceTest {
	final Logger LOG = Logger.getLogger(TestAtpService.class);
	
	@Client(value = "org.kuali.student.r2.core.class1.atp.service.impl.AtpServiceImpl", additionalContextFile="classpath:atp-additional-context.xml")
	public AtpService client;

	public static final String atpType_fallSemester = "atp.atpType.fallSemester";
	public static final String milestoneType_lastDateToDrop = "atp.milestoneType.lastDateToDrop";
	public static final String dateRangeType_finals = "atp.dateRangeType.finals";
	public static final String atpAttribute_notes = "atp.attribute.notes";
	public static final String dateRangeAttribute_notes = "atp.dateRangeAttribute.notes";
	public static final String milestoneAttribute_notes = "atp.milestoneAttribute.notes";
	public static final String atp_fall2008Semester = "atp.fall2008Semester";
	public static final String milestone_lastDateToDropFall2008 = "atp.milestone.lastDateToDropFall2008";
	public static final String dateRange_finalsFall2008 = "atp.dateRange.finalsFall2008";

	public static final String atp_2009FallSemester = "atp.2009FallSemester";
	
	@Test
	public void TestFinds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException{

		Calendar cal = Calendar.getInstance();
		cal.set(2009, 9, 1);

		Calendar startCal = Calendar.getInstance();
		startCal.set(2008, 1, 1);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(2010, 1, 1);

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		// TODO KSCM-504
//		List<String> atpTypes = client.getAtp();
//		assertEquals(2,atpTypes.size());
		
//		List<AtpInfo> atpsByAtpType = client.getAtpsByAtpType(atpType_fallSemester);
//		assertEquals(1,atpsByAtpType.size());
//		assertEquals(atp_2009FallSemester,atpsByAtpType.get(0).getId());
		
//		List<AtpInfo> atpsByDate = client.getAtpsByDate(cal.getTime());
//		assertEquals(1,atpsByDate.size());
//		assertEquals(atp_2009FallSemester,atpsByDate.get(0).getId());
		
//		List<AtpInfo> atpsByDates = client.getAtpsByDates(startCal.getTime(), endCal.getTime());
//		assertEquals(2,atpsByDates.size());
		
//		List<MilestoneInfo> milestonesByAtp = client.getMilestonesByAtp(atp_2009FallSemester);
//		assertEquals(1,milestonesByAtp.size());
		
//		List<MilestoneInfo> milestonesByDates = client.getMilestonesByDates(startCal.getTime(), endCal.getTime());
//		assertEquals(1,milestonesByDates.size());
		
//		List<MilestoneInfo> milestonesByDatesAndType = client.getMilestonesByDatesAndType(milestoneType_lastDateToDrop, startCal.getTime(), endCal.getTime());
//		assertEquals(1,milestonesByDatesAndType.size());
		
//		List<MilestoneTypeInfo> milestoneTypesForAtpType = client.getMilestoneTypesForAtpType(atpType_fallSemester);
//		assertEquals(1,milestoneTypesForAtpType.size());
	}
	
	@Test
	public void TestCreateUpdateDelete(){
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
		//Make an ATP
		AtpInfo atpInfo = new AtpInfo();
		atpInfo.setDescr(new RichTextInfo());
		atpInfo.getDescr().setFormatted("Atp for fall 2008 semester");
		atpInfo.getDescr().setPlain("Atp for fall 2008 semester");
		atpInfo.setName("Fall 2008 Semester");
		atpInfo.setStartDate(new Date());
		atpInfo.setEndDate(new Date());
		
		Date stDate = atpInfo.getStartDate();
		Date enDate = atpInfo.getEndDate();
		
		atpInfo.setStateKey("new");
		
		atpInfo.getAttributes().add(new AttributeInfo( atpAttribute_notes, "Notes for the Fall 2008 Semester"));
		
		AtpInfo createdAtp=null;
		try {
			createdAtp = client.createAtp(atpInfo, contextInfo);
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
		
		assertTrue(stDate.equals(createdAtp.getStartDate()));
		assertTrue(enDate.equals(createdAtp.getEndDate()));
		
		//Make a Milestone
		MilestoneInfo milestoneInfo=new MilestoneInfo();
		milestoneInfo.setDescr(new RichTextInfo());
		milestoneInfo.getDescr().setFormatted("Milestone for fall 2008 semester last day to drop");
		milestoneInfo.setName("Last Day to Drop Fall 2008 Semester");
		milestoneInfo.setStartDate(new Date());
		milestoneInfo.setStateKey("new");
		milestoneInfo.setId(atp_fall2008Semester);
		milestoneInfo.setTypeKey(milestoneType_lastDateToDrop);

		milestoneInfo.getAttributes().add(new AttributeInfo(milestoneAttribute_notes, "Notes for the Last Day to Drop Fall 2008 Semester"));
		
		MilestoneInfo createdMilestone=null;
		try {
			createdMilestone = client.createMilestone(milestoneInfo, contextInfo);
		} catch (Exception e) {
			LOG.error(e);
			fail();
		} 
		
		//Do some updates
		
		//Update Atp
		createdAtp.getDescr().setFormatted("Updated Atp for the Fall 2008 Semester");
		try {
			assertEquals("0",createdAtp.getMeta().getVersionInd());
			AtpInfo updatedAtp = client.updateAtp("atp_fall2008Semester", createdAtp, contextInfo);
			assertEquals("1",updatedAtp.getMeta().getVersionInd());
			assertEquals("Updated Atp for the Fall 2008 Semester", updatedAtp.getDescr().getFormatted());
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
		
		//now try to update again with the same version
		try {
			client.updateAtp("atp_fall2008Semester", createdAtp, contextInfo);
			fail("AtpService.updateAtp() should have thrown VersionMismatchException");
		} catch (VersionMismatchException vme) {
			// what we expect
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
		
		//Update Milestone
		createdMilestone.getDescr().setFormatted("Updated Milestone for fall 2008 semester last day to drop");
		try {
			assertEquals("0",createdMilestone.getMeta().getVersionInd());
			MilestoneInfo updatedMilestone = client.updateMilestone("milestone_lastDateToDropFall2008", createdMilestone, contextInfo);
			assertEquals("1",updatedMilestone.getMeta().getVersionInd());
			assertEquals("Updated Milestone for fall 2008 semester last day to drop", updatedMilestone.getDescr().getFormatted());
		} catch (Exception e) {
			LOG.error(e);
		}
		
		//Try to update again should fail
		try {
			client.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone, contextInfo);
			fail("AtpService.updateDateRange() should have thrown VersionMismatchException");
		} catch (VersionMismatchException vme) {
			// what we expect
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
		
		//Do some deletes that should cascade
		try {
			client.deleteAtp(atp_fall2008Semester, contextInfo);
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
	}
}
