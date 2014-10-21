package org.kuali.student.r2.core.acal.service.impl;

import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.atp.service.impl.DateUtil;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-with-mocks-context.xml"})
public class TestAcademicCalendarServiceImplWithMocks {

    @Resource(name = "acalService")
    private AcademicCalendarService acalService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testCRUD() throws Exception {
        this.testCRUDHolidayCalendar();
        this.testCRUDAcademicCalendar();
        this.testCRUDTerm();
    }

    private void testCRUDHolidayCalendar() throws Exception {
        // create
        HolidayCalendarInfo orig = new HolidayCalendarInfo();
        orig.setName("testHcal1 name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setAdminOrgId("testOrgId1");
        orig.getCampusKeys().add("NorthCampus");
        orig.getCampusKeys().add("SouthCampus");
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        HolidayCalendarInfo info = acalService.createHolidayCalendar(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getCampusKeys(), info.getCampusKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        
        // get
        orig = info;
        info = this.acalService.getHolidayCalendar(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getCampusKeys(), info.getCampusKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("testHcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setAdminOrgId("testOrgId1Updated");
        orig.getCampusKeys().remove(0);
        orig.getCampusKeys().add("WestCampus");
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateHolidayCalendar(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getCampusKeys(), info.getCampusKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getHolidayCalendar(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        this.compareStringList(orig.getCampusKeys(), info.getCampusKeys());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        this.testCRUDHoliday(orig.getId());

        // delete
        orig = info;
        StatusInfo status = this.acalService.deleteHolidayCalendar(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getHolidayCalendar(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
    }

    private void testCRUDHoliday(String holidayCalendarId) throws Exception {
        // create
        HolidayInfo orig = new HolidayInfo();
        orig.setName("testHcal1 name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setIsAllDay(Boolean.TRUE);
        orig.setIsDateRange(Boolean.TRUE);
        orig.setIsInstructionalDay(Boolean.TRUE);
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        HolidayInfo info = acalService.createHoliday(holidayCalendarId, orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(DateUtil.startOfDay(orig.getStartDate()), info.getStartDate());
        assertEquals(DateUtil.endOfDay(orig.getEndDate()), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        assertEquals(orig.getIsInstructionalDay(), info.getIsInstructionalDay());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.acalService.getHoliday(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        assertEquals(orig.getIsInstructionalDay(), info.getIsInstructionalDay());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("testHcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setIsAllDay(Boolean.FALSE);
        orig.setIsDateRange(Boolean.FALSE);
        orig.setIsInstructionalDay(Boolean.FALSE);
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateHoliday(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertNull(info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        assertEquals(orig.getIsInstructionalDay(), info.getIsInstructionalDay());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getHoliday(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        assertEquals(orig.getIsInstructionalDay(), info.getIsInstructionalDay());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.acalService.deleteHoliday(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getHoliday(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
    }

    private void compareStringList(List<String> list1, List<String> list2) {
        assertEquals(list1.size(), list2.size());
        List<String> lst1 = new ArrayList<String>(list1);
        Collections.sort(lst1);
        List lst2 = new ArrayList<String>(list2);
        Collections.sort(lst2);
        for (int i = 0; i < lst1.size(); i++) {
            assertEquals(i + "", lst1.get(i), lst2.get(i));
        }
    }

    private void testCRUDAcademicCalendar() throws Exception {
        HolidayCalendarInfo hcal = new HolidayCalendarInfo();
        hcal.setName("hcal1");
        hcal.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        hcal.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        hcal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcal.setStartDate(new Date());
        hcal.setEndDate(new Date(new Date().getTime() + 100000));
        hcal.setAdminOrgId("testOrgId1");
        HolidayCalendarInfo hcal1 = acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);

        hcal = new HolidayCalendarInfo();
        hcal.setName("hcal2");
        hcal.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        hcal.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        hcal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcal.setStartDate(new Date());
        hcal.setEndDate(new Date(new Date().getTime() + 100000));
        hcal.setAdminOrgId("testOrgId1");
        HolidayCalendarInfo hcal2 = acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);

        hcal = new HolidayCalendarInfo();
        hcal.setName("hcal3");
        hcal.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        hcal.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        hcal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcal.setStartDate(new Date());
        hcal.setEndDate(new Date(new Date().getTime() + 100000));
        hcal.setAdminOrgId("testOrgId1");
        HolidayCalendarInfo hcal3 = acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);

        AcademicCalendarInfo orig = new AcademicCalendarInfo();
        orig.setName("testAcal1 name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setAdminOrgId("testOrgId1");
        orig.getHolidayCalendarIds().add(hcal1.getId());
        orig.getHolidayCalendarIds().add(hcal2.getId());
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        AcademicCalendarInfo info = acalService.createAcademicCalendar(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getHolidayCalendarIds(), info.getHolidayCalendarIds());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // test get after you do the create
        orig = info;
        info = this.acalService.getAcademicCalendar(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getHolidayCalendarIds(), info.getHolidayCalendarIds());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        
        // update
        orig = info;
        orig.setName("testAcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setAdminOrgId("testOrgId1Updated");
        orig.getHolidayCalendarIds().remove(0);
        orig.getHolidayCalendarIds().add(hcal3.getId());
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateAcademicCalendar(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getHolidayCalendarIds(), info.getHolidayCalendarIds());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getAcademicCalendar(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getAdminOrgId(), info.getAdminOrgId());
        compareStringList(orig.getHolidayCalendarIds(), info.getHolidayCalendarIds());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        this.testCRUDAcalEvent(info.getId());

        // delete
        orig = info;
        StatusInfo status = this.acalService.deleteAcademicCalendar(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getAcademicCalendar(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
        // clean up hcals
        this.acalService.deleteHolidayCalendar(hcal1.getId(), callContext);
        this.acalService.deleteHolidayCalendar(hcal2.getId(), callContext);
        this.acalService.deleteHolidayCalendar(hcal3.getId(), callContext);
    }

    private void testCRUDAcalEvent(String academicCalendarId) throws Exception {
        // create
        AcalEventInfo orig = new AcalEventInfo();
        orig.setName("test name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setIsAllDay(Boolean.TRUE);
        orig.setIsDateRange(Boolean.TRUE);
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        AcalEventInfo info = acalService.createAcalEvent(academicCalendarId, orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(DateUtil.startOfDay(orig.getStartDate()), info.getStartDate());
        assertEquals(DateUtil.endOfDay(orig.getEndDate()), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.acalService.getAcalEvent(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("testHcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setIsAllDay(Boolean.FALSE);
        orig.setIsDateRange(Boolean.FALSE);
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateAcalEvent(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertNull(info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getAcalEvent(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.acalService.deleteAcalEvent(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getAcalEvent(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
    }

    private void testCRUDTerm() throws Exception {

        AcademicCalendarInfo acalInfo = new AcademicCalendarInfo();
        acalInfo.setName("testAcal1 name");
        acalInfo.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        acalInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        acalInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acalInfo.setStartDate(new Date());
        acalInfo.setEndDate(new Date(new Date().getTime() + 100000));
        acalInfo.setAdminOrgId("testOrgId1");
        acalInfo = acalService.createAcademicCalendar(acalInfo.getTypeKey(), acalInfo, callContext);

        List<TypeInfo> termTypes = acalService.getTermTypes(callContext);
        System.out.println(termTypes.size() + " term types found");
        for (TypeInfo type : termTypes) {
            System.out.println(type.getKey() + "\t" + type.getName());
        }
        if (termTypes.size() < 3) {
            fail("too few term types");
        }

        TermInfo orig = new TermInfo();
        orig.setName("testTerm1 name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);

        // make this term one year in the future to avoid collisions with term codes
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        orig.setStartDate(cal.getTime());
        orig.setEndDate(new Date(cal.getTimeInMillis() + 100000));
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        TermInfo info = acalService.createTerm(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("testAcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateTerm(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getTerm(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        this.testCRUDKeyDate(orig.getId());

        // test adding term to acal
        orig = info;
        StatusInfo status = this.acalService.addTermToAcademicCalendar(acalInfo.getId(), info.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // test getting that term
        List<TermInfo> terms = this.acalService.getTermsForAcademicCalendar(acalInfo.getId(), callContext);
        assertEquals(1, terms.size());
        info = terms.get(0);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // remove term from acal
        status = this.acalService.removeTermFromAcademicCalendar(acalInfo.getId(), info.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        terms = this.acalService.getTermsForAcademicCalendar(acalInfo.getId(), callContext);
        assertTrue(terms.isEmpty());

        // test adding term to term
        TermInfo subTerm = new TermInfo();
        subTerm.setName("subterm name");
        subTerm.setCode(info.getCode() + "1");
        subTerm.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        subTerm.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY);
        subTerm.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        subTerm.setStartDate(new Date());
        subTerm.setEndDate(new Date(new Date().getTime() + 100000));
        subTerm.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        subTerm.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        subTerm = acalService.createTerm(subTerm.getTypeKey(), subTerm, callContext);

        orig = info;
        status = this.acalService.addTermToTerm(info.getId(), subTerm.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // test getting that term
        terms = this.acalService.getIncludedTermsInTerm(info.getId(), callContext);
        assertEquals(1, terms.size());
        info = terms.get(0);
        assertEquals(subTerm.getId(), info.getId());

         // test getting then contaiing term
        terms = this.acalService.getContainingTerms(subTerm.getId(), callContext);
        assertEquals(1, terms.size());
        info = terms.get(0);
        assertEquals(orig.getId(), info.getId());
        
        // remove term from term
        status = this.acalService.removeTermFromTerm(info.getId(), subTerm.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        terms = this.acalService.getIncludedTermsInTerm(info.getId(), callContext);
        assertTrue(terms.isEmpty());
        terms = this.acalService.getContainingTerms(subTerm.getId(), callContext);
        assertTrue(terms.isEmpty());

        // delete
        orig = info;
        status = this.acalService.deleteTerm(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getTerm(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
    }

    private void testCRUDKeyDate(String termId) throws Exception {
        // create
        KeyDateInfo orig = new KeyDateInfo();
        orig.setName("test name");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setStartDate(new Date());
        orig.setEndDate(new Date(new Date().getTime() + 100000));
        orig.setIsAllDay(Boolean.TRUE);
        orig.setIsDateRange(Boolean.TRUE);
        orig.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        orig.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));
        KeyDateInfo info = acalService.createKeyDate(termId, orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(DateUtil.startOfDay(orig.getStartDate()), info.getStartDate());
        assertEquals(DateUtil.endOfDay(orig.getEndDate()), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());

        // get
        orig = info;
        info = this.acalService.getKeyDate(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());

        // update
        orig = info;
        orig.setName("testHcal1 name updated");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1 updated", "description formatted 1 updated"));
        orig.setStartDate(new Date(orig.getStartDate().getTime() - 10000));
        orig.setEndDate(new Date(orig.getEndDate().getTime() + 10000));
        orig.setIsAllDay(Boolean.FALSE);
        orig.setIsDateRange(Boolean.FALSE);
        new AttributeTester().findAttributes(orig.getAttributes(), "key1").get(0).setValue("value1Updated");
        info = this.acalService.updateKeyDate(orig.getId(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertNull(info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // test get after you do the update
        orig = info;
        info = this.acalService.getKeyDate(orig.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getId(), info.getId());
        assertEquals(orig.getName(), info.getName());
        assertNotNull(info.getDescr());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getDescr().getFormatted(), info.getDescr().getFormatted());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getStartDate(), info.getStartDate());
        assertEquals(orig.getEndDate(), info.getEndDate());
        assertEquals(orig.getIsAllDay(), info.getIsAllDay());
        assertEquals(orig.getIsDateRange(), info.getIsDateRange());
        new AttributeTester().check(orig.getAttributes(), info.getAttributes());
        assertNotNull(info.getMeta());
        assertEquals(orig.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(orig.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(orig.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(orig.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());

        // delete
        orig = info;
        StatusInfo status = this.acalService.deleteKeyDate(orig.getId(), callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());

        // try getting again
        try {
            this.acalService.getKeyDate(orig.getId(), callContext);
            fail("should have thrown does not exist exception");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().contains(orig.getId()));
        }
    }

    @Test  // Jira - KSENROLL-3470
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
            fail("InvalidParameterException should have been thrown");
        }catch(InvalidParameterException ex){
            assertNotNull(ex.getMessage());
            assertEquals("HolidayCalendar type " + hcal.getTypeKey() + " not right", ex.getMessage());
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
        holiday.setIsInstructionalDay(Boolean.TRUE);

        try{
            holiday.setTypeKey("not configured holiday type");
            acalService.createHoliday(info.getId(), holiday.getTypeKey(), holiday, callContext);
            fail("InvalidParameterException should have been thrown");
        }catch(InvalidParameterException ex){
            assertNotNull(ex.getMessage());
            assertEquals("Holiday type not found: '" + holiday.getTypeKey() + "'", ex.getMessage());
        }

        holiday.setTypeKey(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY);
        HolidayInfo holidayInfo = acalService.createHoliday(info.getId(), holiday.getTypeKey(), holiday, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(holiday.getName(), holidayInfo.getName());
    }

    @Test  // Jira - KSENROLL-3470
    public void testAcalAndEvent() throws Exception{
        //Acal
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("Jira3470TestAcal name");
        acal.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestAcal", "description formatted Jira3470TestAcal"));
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setStartDate(new Date());
        acal.setEndDate(new Date(new Date().getTime() + 100000));
        acal.setAdminOrgId("testOrgId1");

        acal.setTypeKey("not configured acal type");
        acalService.createAcademicCalendar(acal.getTypeKey(), acal, callContext);

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
            fail("InvalidParameterException should have been thrown");
        }catch(InvalidParameterException ex){
            assertNotNull(ex.getMessage());
            assertEquals("AcalEvent type not found: '" + event.getTypeKey() + "'", ex.getMessage());
        }

        event.setTypeKey(AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY);
        AcalEventInfo eventInfo = acalService.createAcalEvent(acalInfo.getId(), event.getTypeKey(), event, callContext);
        assertNotNull(eventInfo);
        assertNotNull(eventInfo.getId());
        assertEquals(event.getName(), eventInfo.getName());
    }

    @Test  // Jira - KSENROLL-3470
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
            fail("InvalidParameterException should have been thrown");
        }catch(InvalidParameterException ex){
            assertNotNull(ex.getMessage());
            assertEquals("Term type not found: '" + term.getTypeKey() + "'", ex.getMessage());
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
            fail("InvalidParameterException should have been thrown");
        }catch(InvalidParameterException ex){
            assertNotNull(ex.getMessage());
            assertEquals("Keydate type not found: '" + kd.getTypeKey() + "'", ex.getMessage());
        }

        kd.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        KeyDateInfo kdInfo = acalService.createKeyDate(termInfo.getId(), kd.getTypeKey(), kd, callContext);
        assertNotNull(kdInfo);
        assertNotNull(kdInfo.getId());
        assertEquals(kd.getName(), kdInfo.getName());
    }
}
