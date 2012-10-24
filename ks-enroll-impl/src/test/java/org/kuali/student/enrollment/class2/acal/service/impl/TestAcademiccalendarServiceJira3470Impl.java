/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.Date;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-with-mocks-context.xml"})
public class TestAcademiccalendarServiceJira3470Impl {
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Resource(name = "acalService")
    private AcademicCalendarService acalService;

    @Before
    public void setUp() {
           principalId = "123";
           callContext = new ContextInfo();
           callContext.setPrincipalId(principalId);
    }

    private void populateRequiredFields(AcademicCalendarInfo acal) {
        acal.setEndDate(new Date());
        acal.setStartDate(new Date());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        acal.setDescr(richTextInfo);
    }

    @Test
    public void testHcalAndHoliday() throws Exception{
        //Hcal
        HolidayCalendarInfo hcal = new HolidayCalendarInfo();
        hcal.setName("Jira3470TestHcal name");
        hcal.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestHcal", "description formatted Jira3470TestHcal"));
        hcal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcal.setStartDate(new Date());
        hcal.setEndDate(new Date(new Date().getTime() + 100000));
        hcal.setAdminOrgId("testOrgId1");

        try{
             hcal.setTypeKey("not configured hcal type");
             acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        hcal.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        HolidayCalendarInfo info = acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(hcal.getName(), info.getName());


        //Holiday
        HolidayInfo holiday = new HolidayInfo();
        holiday.setName("Jira3470TestHoliday name");
        holiday.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestHoliday", "description formatted Jira3470TestHoliday"));
        holiday.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        holiday.setStartDate(new Date());
        holiday.setEndDate(new Date(new Date().getTime() + 100000));
        holiday.setIsAllDay(Boolean.TRUE);
        holiday.setIsDateRange(Boolean.TRUE);
        holiday.setIsInstructionalDay(Boolean.TRUE);;

        try{
            holiday.setTypeKey("not configured holiday type");
            acalService.createHoliday(info.getId(), holiday.getTypeKey(), holiday, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        holiday.setTypeKey(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY);
        HolidayInfo holidayInfo = acalService.createHoliday(info.getId(), holiday.getTypeKey(), holiday, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(holiday.getName(), holidayInfo.getName());
    }

    @Test
    public void testAcalAndEvent() throws Exception{
        //Acal
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("Jira3470TestAcal name");
        acal.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestAcal", "description formatted Jira3470TestAcal"));
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setStartDate(new Date());
        acal.setEndDate(new Date(new Date().getTime() + 100000));
        acal.setAdminOrgId("testOrgId1");

        try{
            acal.setTypeKey("not configured acal type");
            acalService.createAcademicCalendar(acal.getTypeKey(), acal, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        AcademicCalendarInfo acalInfo = acalService.createAcademicCalendar(acal.getTypeKey(), acal, callContext);
        assertNotNull(acalInfo);
        assertNotNull(acalInfo.getId());
        assertEquals(acal.getName(), acalInfo.getName());

        //AcalEvent
        AcalEventInfo event = new AcalEventInfo();
        event.setName("Jira3470TestAcalEvent name");
        event.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestAcalEven", "description formatted Jira3470TestAcalEvent"));
        event.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        event.setStartDate(new Date());
        event.setEndDate(new Date(new Date().getTime() + 100000));
        event.setIsAllDay(Boolean.TRUE);
        event.setIsDateRange(Boolean.TRUE);

        try{
            event.setTypeKey("not configured acalevent type");
            acalService.createAcalEvent(acalInfo.getId(), event.getTypeKey(), event, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        event.setTypeKey(AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY);
        AcalEventInfo eventInfo = acalService.createAcalEvent(acalInfo.getId(), event.getTypeKey(), event, callContext);
        assertNotNull(eventInfo);
        assertNotNull(eventInfo.getId());
        assertEquals(event.getName(), eventInfo.getName());
    }

    @Test
    public void testTermAndKeydate() throws Exception{
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
         acal.setName("Jira3470TestAcalWTerm name");
         acal.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestAcalWTerm", "description formatted Jira3470TestAcalWTerm"));
         acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
         acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
         acal.setStartDate(new Date());
         acal.setEndDate(new Date(new Date().getTime() + 100000));
         acal.setAdminOrgId("testOrgId1");

        AcademicCalendarInfo acalInfo = acalService.createAcademicCalendar(acal.getTypeKey(), acal, callContext);
        assertNotNull(acalInfo);
        assertNotNull(acalInfo.getId());
        assertEquals(acal.getName(), acalInfo.getName());

        //Term
       TermInfo term = new TermInfo();
        term.setName("Jira3470TestTerm name");
        term.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestTerm", "description formatted Jira3470TestTerm"));
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setStartDate(new Date());
        term.setEndDate(new Date(new Date().getTime() + 100000));

        try{
            term.setTypeKey("not configured term type");
            acalService.createTerm(term.getTypeKey(), term, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        TermInfo termInfo = acalService.createTerm(term.getTypeKey(), term, callContext);
        assertNotNull(termInfo);
        assertNotNull(termInfo.getId());
        assertEquals(term.getName(), termInfo.getName());

         //Keydate
        KeyDateInfo kd = new KeyDateInfo();
        kd.setName("Jira3470TestKeydate name");
        kd.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestKeydate", "description formatted Jira3470TestKeydate"));
        kd.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        kd.setStartDate(new Date());
        kd.setEndDate(new Date(new Date().getTime() + 100000));
        kd.setIsAllDay(Boolean.TRUE);
        kd.setIsDateRange(Boolean.TRUE);

        try{
            kd.setTypeKey("not configured keydate type");
            acalService.createKeyDate(termInfo.getId(), kd.getTypeKey(), kd, callContext);
        }catch(InvalidParameterException ex){
            System.out.println("expected exception: "  + ex.getMessage());
        }

        kd.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        KeyDateInfo kdInfo = acalService.createKeyDate(termInfo.getId(), kd.getTypeKey(), kd, callContext);
        assertNotNull(kdInfo);
        assertNotNull(kdInfo.getId());
        assertEquals(kd.getName(), kdInfo.getName());
    }
}
