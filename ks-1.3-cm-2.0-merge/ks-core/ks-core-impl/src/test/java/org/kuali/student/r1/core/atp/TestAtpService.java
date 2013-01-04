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

package org.kuali.student.r1.core.atp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.type.service.TypeService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

@Ignore
@Daos( { @Dao(value = "org.kuali.student.r1.core.atp.dao.impl.AtpDaoImpl", testDataFile = "classpath:atp-test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpService extends AbstractServiceTest {
//	final Logger LOG = Logger.getLogger(TestAtpService.class);
//
//	@Client(value = "org.kuali.student.r1.core.atp.service.impl.AtpServiceImpl", additionalContextFile="classpath:atp-additional-context.xml")
//	public AtpService atpService;
//    @Client(value = "org.kuali.student.r2.common.type.service.TypeServiceImpl", additionalContextFile="classpath:atp-additional-context.xml")
//    public TypeService typeService;
//
//	public static final String atpType_fallSemester = "atp.atpType.fallSemester";
//	public static final String milestoneType_lastDateToDrop = "atp.milestoneType.lastDateToDrop";
//	public static final String dateRangeType_finals = "atp.dateRangeType.finals";
//	public static final String atpAttribute_notes = "atp.attribute.notes";
//	public static final String dateRangeAttribute_notes = "atp.dateRangeAttribute.notes";
//	public static final String milestoneAttribute_notes = "atp.milestoneAttribute.notes";
//	public static final String atp_fall2008Semester = "atp.fall2008Semester";
//	public static final String milestone_lastDateToDropFall2008 = "atp.milestone.lastDateToDropFall2008";
//	public static final String dateRange_finalsFall2008 = "atp.dateRange.finalsFall2008";
//
//	public static final String atp_2009FallSemester = "atp.2009FallSemester";
//
//	@Test
//	public void TestFinds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException{
//
//		Calendar cal = Calendar.getInstance();
//		cal.set(2009, 9, 1);
//
//		Calendar startCal = Calendar.getInstance();
//		startCal.set(2008, 1, 1);
//
//		Calendar endCal = Calendar.getInstance();
//		endCal.set(2010, 1, 1);
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//
//		List<TypeInfo> atpTypes = atpService.getAtpTypes();
//		assertEquals(2,atpTypes.size());
//
//		List<AtpInfo> atpsByAtpType = atpService.getAtpsByAtpType(atpType_fallSemester);
//		assertEquals(1,atpsByAtpType.size());
//		assertEquals(atp_2009FallSemester,atpsByAtpType.get(0).getId());
//
//		List<AtpInfo> atpsByDate = atpService.getAtpsByDate(cal.getTime());
//		assertEquals(1,atpsByDate.size());
//		assertEquals(atp_2009FallSemester,atpsByDate.get(0).getId());
//
//		List<AtpInfo> atpsByDates = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime());
//		assertEquals(2,atpsByDates.size());
//
//		List<DateRangeInfo> dateRangesByAtp = atpService.getDateRangesByAtp(atp_2009FallSemester);
//		assertEquals(1,dateRangesByAtp.size());
//
//		List<DateRangeInfo> dateRangesByDate = atpService.getDateRangesByDate(df.parse("20091205"));
//		assertEquals(1,dateRangesByDate.size());
//
//		List<DateRangeTypeInfo> dateRangeTypesForAtpType = atpService.getDateRangeTypesForAtpType(atpType_fallSemester);
//		assertEquals(1,dateRangeTypesForAtpType.size());
//
//		List<MilestoneInfo> milestonesByAtp = atpService.getMilestonesByAtp(atp_2009FallSemester);
//		assertEquals(1,milestonesByAtp.size());
//
//		List<MilestoneInfo> milestonesByDates = atpService.getMilestonesByDates(startCal.getTime(), endCal.getTime());
//		assertEquals(1,milestonesByDates.size());
//
//		List<MilestoneInfo> milestonesByDatesAndType = atpService.getMilestonesByDatesAndType(milestoneType_lastDateToDrop, startCal.getTime(), endCal.getTime());
//		assertEquals(1,milestonesByDatesAndType.size());
//
//		List<MilestoneTypeInfo> milestoneTypesForAtpType = atpService.getMilestoneTypesForAtpType(atpType_fallSemester);
//		assertEquals(1,milestoneTypesForAtpType.size());
//	}
//
//	@Test
//	public void TestCreateUpdateDelete(){
//		//Make an ATP
//		AtpInfo atpInfo = new AtpInfo();
//		atpInfo.setDesc(new RichTextInfo());
//		atpInfo.getDesc().setFormatted("Atp for fall 2008 semester");
//		atpInfo.getDesc().setPlain("Atp for fall 2008 semester");
//		atpInfo.setName("Fall 2008 Semester");
//		atpInfo.setStartDate(new Date());
//		atpInfo.setEndDate(new Date());
//
//		Date stDate = atpInfo.getStartDate();
//		Date enDate = atpInfo.getEndDate();
//
//		atpInfo.setState("new");
//
//		atpInfo.getAttributes().put(atpAttribute_notes, "Notes for the Fall 2008 Semester");
//
//		AtpInfo createdAtp=null;
//		try {
//			createdAtp = atpService.createAtp(atpType_fallSemester, atp_fall2008Semester, atpInfo);
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		assertTrue(stDate.equals(createdAtp.getStartDate()));
//		assertTrue(enDate.equals(createdAtp.getEndDate()));
//
//		//Make a DateRange
//		DateRangeInfo dateRangeInfo=new DateRangeInfo();
//		dateRangeInfo.setDesc(new RichTextInfo());
//		dateRangeInfo.getDesc().setFormatted("Date Range for fall 2008 semester finals");
//		dateRangeInfo.setName("Finals Fall 2008 Semester");
//		dateRangeInfo.setStartDate(new Date());
//		dateRangeInfo.setEndDate(new Date());
//		dateRangeInfo.setState("new");
//		dateRangeInfo.setAtpId(atp_fall2008Semester);
//		dateRangeInfo.setType(dateRangeType_finals);
//
//		dateRangeInfo.getAttributes().put(dateRangeAttribute_notes, "Notes for the Finals date range Fall 2008 Semester");
//
//		DateRangeInfo createdDateRange=null;
//		try {
//			createdDateRange = atpService.addDateRange(atp_fall2008Semester, dateRange_finalsFall2008, dateRangeInfo);
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Make a Milestone
//		MilestoneInfo milestoneInfo=new MilestoneInfo();
//		milestoneInfo.setDesc(new RichTextInfo());
//		milestoneInfo.getDesc().setFormatted("Milestone for fall 2008 semester last day to drop");
//		milestoneInfo.setName("Last Day to Drop Fall 2008 Semester");
//		milestoneInfo.setMilestoneDate(new Date());
//		milestoneInfo.setState("new");
//		milestoneInfo.setAtpId(atp_fall2008Semester);
//		milestoneInfo.setType(milestoneType_lastDateToDrop);
//
//		milestoneInfo.getAttributes().put(milestoneAttribute_notes, "Notes for the Last Day to Drop Fall 2008 Semester");
//
//		MilestoneInfo createdMilestone=null;
//		try {
//			createdMilestone = atpService.addMilestone(atp_fall2008Semester, milestone_lastDateToDropFall2008, milestoneInfo);
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Do some updates
//
//		//Update Atp
//		createdAtp.getDesc().setFormatted("Updated Atp for the Fall 2008 Semester");
//		try {
//			assertEquals("0",createdAtp.getMetaInfo().getVersionInd());
//			AtpInfo updatedAtp = atpService.updateAtp(atp_fall2008Semester, createdAtp);
//			assertEquals("1",updatedAtp.getMetaInfo().getVersionInd());
//			assertEquals("Updated Atp for the Fall 2008 Semester", updatedAtp.getDesc().getFormatted());
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//now try to update again with the same version
//		try {
//			atpService.updateAtp(atp_fall2008Semester, createdAtp);
//			fail("AtpService.updateAtp() should have thrown VersionMismatchException");
//		} catch (VersionMismatchException vme) {
//			// what we expect
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Update Date Range
//		createdDateRange.getDesc().setFormatted("Updated DateRange for the Finals date range Fall 2008 Semester");
//		try {
//			assertEquals("0",createdDateRange.getMetaInfo().getVersionInd());
//			DateRangeInfo updatedDateRange = atpService.updateDateRange(dateRange_finalsFall2008, createdDateRange);
//			assertEquals("1",updatedDateRange.getMetaInfo().getVersionInd());
//			assertEquals("Updated DateRange for the Finals date range Fall 2008 Semester", updatedDateRange.getDesc().getFormatted());
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Updating with the old version again should fail
//		try {
//			atpService.updateDateRange(dateRange_finalsFall2008, createdDateRange);
//			fail("AtpService.updateDateRange() should have thrown VersionMismatchException");
//		} catch (VersionMismatchException vme) {
//			// what we expect
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Update Milestone
//		createdMilestone.getDesc().setFormatted("Updated Milestone for fall 2008 semester last day to drop");
//		try {
//			assertEquals("0",createdMilestone.getMetaInfo().getVersionInd());
//			MilestoneInfo updatedMilestone = atpService.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone);
//			assertEquals("1",updatedMilestone.getMetaInfo().getVersionInd());
//			assertEquals("Updated Milestone for fall 2008 semester last day to drop", updatedMilestone.getDesc().getFormatted());
//		} catch (Exception e) {
//			LOG.error(e);
//		}
//
//		//Try to update again should fail
//		try {
//			atpService.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone);
//			fail("AtpService.updateDateRange() should have thrown VersionMismatchException");
//		} catch (VersionMismatchException vme) {
//			// what we expect
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//
//		//Do some deletes that should cascade
//		try {
//			atpService.deleteAtp(atp_fall2008Semester);
//		} catch (Exception e) {
//			LOG.error(e);
//			fail();
//		}
//	}
}
