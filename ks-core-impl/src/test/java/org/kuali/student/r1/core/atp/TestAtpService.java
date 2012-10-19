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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.class1.type.service.TypeService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-additional-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAtpService {
	final Logger LOG = Logger.getLogger(TestAtpService.class);

    @Resource(name = "atpEnrService" )
    public AtpService atpService;
    @Resource(name = "typeServiceImpl" )
    public TypeService typeService;

	public static final String milestoneAttribute_notes = "atp.milestoneAttribute.notes";
	public static final String atp_fall2008Semester = "atp.fall2008Semester";

    public static String principalId = "123";


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
	@Test
	public void TestCreateGetUpdateDelete(){
		//Make an ATP
		AtpInfo atpInfo = new AtpInfo();
        ContextInfo context = new ContextInfo();

        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());

        atpInfo.setName("Winter 2008 Semester");
		atpInfo.setStartDate(new Date());
		atpInfo.setEndDate(new Date());
        atpInfo.setId("2008WINTER");
        atpInfo.setDescr(new RichTextHelper().fromPlain("Winter 2008 Semester"));
        atpInfo.setCode("20122");
		Date stDate = atpInfo.getStartDate();
		Date enDate = atpInfo.getEndDate();

		atpInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);

		AtpInfo createdAtp=null;
		try {
			createdAtp = atpService.createAtp(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, atpInfo, context);
		} catch (Exception e) {
			LOG.error(e);
            fail();
		}

		assertTrue(stDate.equals(createdAtp.getStartDate()));
		assertTrue(enDate.equals(createdAtp.getEndDate()));

		//Get Atp
		try {
            createdAtp = atpService.getAtp(atpInfo.getId(), context);
		} catch (Exception e) {
			LOG.error(e);
            fail();
		}

        assertNotNull(createdAtp);
        assertTrue(stDate.equals(createdAtp.getStartDate()));
        assertTrue(enDate.equals(createdAtp.getEndDate()));

		//Update Atp
        assertTrue(StringUtils.equals("0", createdAtp.getMeta().getVersionInd()));
        createdAtp.setName("Spring 2009 Semester");
        try {
			AtpInfo updatedAtp = atpService.updateAtp(createdAtp.getId(), createdAtp, context);
            assertTrue(StringUtils.equals(createdAtp.getName(),updatedAtp.getName()));
            assertTrue(StringUtils.equals(createdAtp.getDescr().getPlain(), updatedAtp.getDescr().getPlain()));
		} catch (Exception e) {
			LOG.error(e);
            fail();
		}

		//Update Date Range
        createdAtp.getDescr().setFormatted("Updated DateRange for the Finals date range Fall 2008 Semester");
        Date sDate = new Date();
        Date eDate = new Date();

        sDate.setTime(1350000000);
        eDate.setTime(1360000000);
        createdAtp.setStartDate(sDate);
        createdAtp.setEndDate(eDate);

        try {
            AtpInfo updatedAtp = atpService.updateAtp(createdAtp.getId(), createdAtp, context);
            assertTrue(StringUtils.equals(createdAtp.getName(),updatedAtp.getName()));
            assertTrue(StringUtils.equals(createdAtp.getDescr().getPlain(), updatedAtp.getDescr().getPlain()));
            assertTrue(updatedAtp.getStartDate().getTime() == sDate.getTime());
            assertTrue(updatedAtp.getEndDate().getTime() == eDate.getTime());
        } catch (Exception e) {
			LOG.error(e);
            fail();
		}

		//Create a Milestone
		MilestoneInfo milestoneInfo=new MilestoneInfo();
		milestoneInfo.setName("Last Day to Drop Fall 2008 Semester");
        milestoneInfo.setStartDate(sDate);
        milestoneInfo.setEndDate(eDate);
		milestoneInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
		milestoneInfo.setId(atp_fall2008Semester);
		milestoneInfo.setTypeKey(AtpServiceConstants.SEASON_FALL_SPRING_TYPE_KEY);
        milestoneInfo.setDescr(new RichTextHelper().fromPlain("Milestone for fall 2008 semester last day to drop"));
		milestoneInfo.setAttributeValue(milestoneAttribute_notes, "Notes for the Last Day to Drop Fall 2008 Semester");
        milestoneInfo.setIsDateRange(true);

		MilestoneInfo createdMilestone=null;
		try {
			createdMilestone = atpService.createMilestone(milestoneInfo.getTypeKey(), milestoneInfo, context);
            assertNotNull(createdMilestone);
            assertTrue(StringUtils.equals(milestoneInfo.getDescr().getPlain(), createdMilestone.getDescr().getPlain()));
            assertTrue(createdMilestone.getIsDateRange ());
            assertTrue(createdMilestone.getStartDate().getTime() == sDate.getTime());
            assertTrue(createdMilestone.getEndDate().getTime() == eDate.getTime());
        } catch (Exception e) {
			LOG.error(e);
            fail();
		}

		//Update Milestone
        Date upStart = new Date();
        Date upEnd =  new Date();
        upStart.setTime(1330000000L);
        upEnd.setTime(1340000000L);
		createdMilestone.setDescr(new RichTextHelper().fromPlain("Updated Milestone for fall 2008 semester last day to drop"));
        createdMilestone.setStartDate(upStart);
        createdMilestone.setEndDate(upEnd);
		try {
			MilestoneInfo updatedMilestone = atpService.updateMilestone(createdMilestone.getId(), createdMilestone, context);
			assertNotNull(updatedMilestone);
            assertTrue(updatedMilestone.getStartDate().getTime() == upStart.getTime());
            assertTrue(updatedMilestone.getEndDate().getTime() == upEnd.getTime());
            assertTrue(StringUtils.equals(updatedMilestone.getDescr().getPlain(), createdMilestone.getDescr().getPlain()));
        } catch (Exception e) {
			LOG.error(e);
            fail();
		}

		//Try to update again
        createdMilestone.setIsDateRange(false);
		try {
			MilestoneInfo updatedMilestone = atpService.updateMilestone(createdMilestone.getId(), createdMilestone, context);
            assertNotNull(updatedMilestone);
            assertTrue(updatedMilestone.getStartDate().getTime() == upStart.getTime());
            assertNull(updatedMilestone.getEndDate());
        } catch (Exception e) {
			LOG.error(e);
			fail();
		}

		//Do some deletes that should cascade
		try {
            StatusInfo statusInfo = atpService.deleteAtp(createdAtp.getId(), context);
            assertNotNull(statusInfo);
            assertTrue(statusInfo.getIsSuccess());
		} catch (Exception e) {
			LOG.error(e);
			fail();
		}
	}
}
