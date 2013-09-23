/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cwl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback =true)
@Transactional
public class TestCourseWaitListServiceImpl {

    @Resource
    private CourseWaitListService cwlService;

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp()
    {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @Test
    public void testCreateAndReadCwl() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        try {
            CourseWaitListInfo returned = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListInfo fetched = cwlService.getCourseWaitList(id, contextInfo);
            assertEquals(cwlInfo.getActivityOfferingIds().get(0), fetched.getActivityOfferingIds().get(0));
            assertEquals(cwlInfo.getFormatOfferingIds().get(0), fetched.getFormatOfferingIds().get(0));
            assertEquals(cwlInfo.getConfirmationRequired(), fetched.getConfirmationRequired());
            assertEquals(cwlInfo.getMaxSize(), fetched.getMaxSize());
            assertEquals(cwlInfo.getCheckInFrequency().getTimeQuantity(), fetched.getCheckInFrequency().getTimeQuantity());
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    // CourseWaitListEntry is not yet implemented; when ready to test, add Test annotation
    public void testCreateAndReadCwlEntry() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        CourseWaitListInfo createdCwl = null;
        try {
            createdCwl = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
        CourseWaitListEntryInfo cwlEntryInfo = buildDefaultCourseWaitListEntry(createdCwl);
        try {
            CourseWaitListEntryInfo returned = cwlService.createCourseWaitListEntry(cwlEntryInfo.getCourseWaitListId(), cwlEntryInfo.getStudentId(), CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlEntryInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListEntryInfo fetched = cwlService.getCourseWaitListEntry(id, contextInfo);
            assertEquals(cwlEntryInfo.getLastCheckIn(), fetched.getLastCheckIn());
            assertEquals(cwlEntryInfo.getCourseWaitListId(), fetched.getCourseWaitListId());
            assertEquals(cwlEntryInfo.getPosition(), fetched.getPosition());
            assertEquals(cwlEntryInfo.getStudentId(), fetched.getStudentId());
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testUpdateCwl() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        try {
            CourseWaitListInfo returned = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListInfo fetched = cwlService.getCourseWaitList(id, contextInfo);
            fetched.setMaxSize(50);
            cwlService.updateCourseWaitList(fetched.getId(), fetched, contextInfo);

            CourseWaitListInfo fetchedAgain = cwlService.getCourseWaitList(id, contextInfo);

            assertEquals(fetchedAgain.getMaxSize(), fetched.getMaxSize());
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    // CourseWaitListEntry is not yet implemented; when ready to test, add Test annotation
    public void testUpdateCwlEntry() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        CourseWaitListInfo createdCwl = null;
        try {
            createdCwl = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
        CourseWaitListEntryInfo cwlEntryInfo = buildDefaultCourseWaitListEntry(createdCwl);
        try {
            CourseWaitListEntryInfo returned = cwlService.createCourseWaitListEntry(cwlEntryInfo.getCourseWaitListId(), cwlEntryInfo.getStudentId(), CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlEntryInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListEntryInfo fetched = cwlService.getCourseWaitListEntry(id, contextInfo);
            fetched.setPosition(2);
            cwlService.updateCourseWaitListEntry(fetched.getId(), fetched, contextInfo);

            CourseWaitListEntryInfo fetchedAgain = cwlService.getCourseWaitListEntry(id, contextInfo);

            assertEquals(fetchedAgain.getPosition(), fetched.getPosition());
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGetCwlsByAo(){
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        try {
            List<CourseWaitListInfo> courseWaitListInfos = cwlService.getCourseWaitListsByActivityOffering("123", contextInfo);
            assertTrue(courseWaitListInfos.isEmpty());
            CourseWaitListInfo returned = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);

            courseWaitListInfos = cwlService.getCourseWaitListsByActivityOffering("123", contextInfo);
            assertTrue(!courseWaitListInfos.isEmpty());

            for(CourseWaitListInfo courseWaitListInfo : courseWaitListInfos) {
                assertTrue(courseWaitListInfo.getActivityOfferingIds().contains("123"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            assert(false);
        }
    }


    @Test
    public void testChangeCwlState(){
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        try {
            CourseWaitListInfo returned = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListInfo fetched = cwlService.getCourseWaitList(id, contextInfo);

            assertTrue(null == fetched.getStateKey());

            StatusInfo statusInfo = cwlService.changeCourseWaitListState(fetched.getId(),CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY,contextInfo);
            assertTrue(statusInfo.getIsSuccess());

            CourseWaitListInfo fetchAgain = cwlService.getCourseWaitList(id, contextInfo);
            assertNotNull(fetchAgain.getStateKey());
            assertTrue(fetchAgain.getStateKey().equals(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY));
        }
        catch (Exception e){
            e.printStackTrace();
            assert(false);
        }
    }



    @Test
    public void testDeleteCwl() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        try {
            CourseWaitListInfo returned = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListInfo fetched = cwlService.getCourseWaitList(id, contextInfo);
            cwlService.deleteCourseWaitList(fetched.getId(), contextInfo);

            try {
                CourseWaitListInfo fetchedAgain = cwlService.getCourseWaitList(id, contextInfo);
            } catch(DoesNotExistException e) {
                assert(true);
                return;
            }
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
        assert(false); // Shouldn't get here
    }

    // CourseWaitListEntry is not yet implemented; when ready to test, add Test annotation
    public void testDeleteCwlEntry() {
        CourseWaitListInfo cwlInfo = buildDefaultCourseWaitList();
        CourseWaitListInfo createdCwl = null;
        try {
            createdCwl = cwlService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlInfo, contextInfo);
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
        CourseWaitListEntryInfo cwlEntryInfo = buildDefaultCourseWaitListEntry(createdCwl);
        try {
            CourseWaitListEntryInfo returned = cwlService.createCourseWaitListEntry(cwlEntryInfo.getCourseWaitListId(), cwlEntryInfo.getStudentId(), CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, cwlEntryInfo, contextInfo);
            String id = returned.getId();
            CourseWaitListEntryInfo fetched = cwlService.getCourseWaitListEntry(id, contextInfo);
            cwlService.deleteCourseWaitListEntry(fetched.getId(), contextInfo);

            try {
                CourseWaitListEntryInfo fetchedAgain = cwlService.getCourseWaitListEntry(id, contextInfo);
            } catch(DoesNotExistException e) {
                assert(true);
                return;
            }
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
        assert(false); // Shouldn't get here
    }

    private CourseWaitListInfo buildDefaultCourseWaitList() {
        CourseWaitListInfo cwlInfo = new CourseWaitListInfo();
        cwlInfo.setConfirmationRequired(true);
        cwlInfo.setCheckInRequired(true);
        cwlInfo.setAutomaticallyProcessed(true);
        cwlInfo.setAllowHoldUntilEntries(true);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(1000);
        cwlInfo.setCheckInFrequency(timeAmountInfo);
        cwlInfo.setMaxSize(30);
        cwlInfo.setRegisterInFirstAvailableActivityOffering(true);
        List<String> activityOfferings = new ArrayList<String>();
        activityOfferings.add("123");
        List<String> formatOfferings = new ArrayList<String>();
        formatOfferings.add("321");
        cwlInfo.setActivityOfferingIds(activityOfferings);
        cwlInfo.setFormatOfferingIds(formatOfferings);
        return cwlInfo;
    }

    private CourseWaitListEntryInfo buildDefaultCourseWaitListEntry(CourseWaitListInfo cwlInfo) {
        CourseWaitListEntryInfo cwlEntryInfo = new CourseWaitListEntryInfo();
        cwlEntryInfo.setRegistrationGroupId("foo");
        cwlEntryInfo.setCourseWaitListId(cwlInfo.getId());
        cwlEntryInfo.setLastCheckIn(new Date());
        cwlEntryInfo.setPosition(1);
        cwlEntryInfo.setStudentId("543");
        return cwlEntryInfo;
    }
}
