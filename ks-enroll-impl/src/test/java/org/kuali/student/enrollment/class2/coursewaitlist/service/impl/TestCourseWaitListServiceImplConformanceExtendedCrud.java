/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;


import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:courseWaitList-test-with-map-context.xml"})
public class TestCourseWaitListServiceImplConformanceExtendedCrud extends TestCourseWaitListServiceImplConformanceBaseCrud
{

    @Resource
    protected  CourseWaitListDataLoader dataLoader;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           CourseWaitListInfo
	// ****************************************************
	
	/*
		A method to set the fields for a CourseWaitList in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudCourseWaitList_setDTOFieldsForTestCreate(CourseWaitListInfo expected) throws ParseException
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
        List<String> offerings = new ArrayList<String>();
        offerings.add("1");
        offerings.add("2");
        offerings.add("3");
		expected.setActivityOfferingIds(offerings);
        offerings = new ArrayList<String>();
        offerings.add("4");
        offerings.add("5");
        expected.setFormatOfferingIds(offerings);
        expected.setAutomaticallyProcessed(true);
        expected.setRegisterInFirstAvailableActivityOffering(false);
        expected.setConfirmationRequired(true);
		expected.setMaxSize(200);
		expected.setCheckInRequired(true);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(5);
        timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
		expected.setCheckInFrequency(timeAmountInfo);
		expected.setAllowHoldUntilEntries(true);
        expected.setEffectiveDate(dateFormat.parse("20130611"));
        expected.setExpirationDate(dateFormat.parse("21000101"));
	}
	
	/*
		A method to test the fields for a CourseWaitList. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudCourseWaitList_testDTOFieldsForTestCreateUpdate(CourseWaitListInfo expected, CourseWaitListInfo actual)
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        if(expected.getActivityOfferingIds() != null) {
            assertEquals(expected.getActivityOfferingIds().size(), actual.getActivityOfferingIds().size());
            for(String aoId : expected.getActivityOfferingIds()) {
                assertTrue(actual.getActivityOfferingIds().contains(aoId));
            }
        } else {
            assertNull(actual.getActivityOfferingIds());
        }
        if(expected.getFormatOfferingIds() != null) {
            assertEquals(expected.getFormatOfferingIds().size(), actual.getFormatOfferingIds().size());
            for(String foId : expected.getFormatOfferingIds()) {
                assertTrue(actual.getFormatOfferingIds().contains(foId));
            }
        } else {
            assertNull(actual.getFormatOfferingIds());
        }
		assertEquals (expected.getAutomaticallyProcessed(), actual.getAutomaticallyProcessed());
        assertEquals (expected.getRegisterInFirstAvailableActivityOffering(), actual.getRegisterInFirstAvailableActivityOffering());
        assertEquals (expected.getConfirmationRequired(), actual.getConfirmationRequired());
		assertEquals (expected.getMaxSize(), actual.getMaxSize());
		assertEquals (expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
		assertEquals (expected.getAllowHoldUntilEntries(), actual.getAllowHoldUntilEntries());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
	}
	
	/*
		A method to set the fields for a CourseWaitList in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudCourseWaitList_setDTOFieldsForTestUpdate(CourseWaitListInfo expected) throws ParseException
	{
        List<String> offerings = new ArrayList<String>();
        offerings.add("1");
        offerings.add("22");
        offerings.add("33");
        expected.setActivityOfferingIds(offerings);
        offerings = new ArrayList<String>();
        offerings.add("55");
        expected.setFormatOfferingIds(offerings);
		expected.setConfirmationRequired(false);
        expected.setAutomaticallyProcessed(false);
        expected.setRegisterInFirstAvailableActivityOffering(true);
		expected.setMaxSize(1234);
		expected.setCheckInRequired(false);
		expected.setCheckInFrequency(null);
		expected.setAllowHoldUntilEntries(false);
        expected.setEffectiveDate(dateFormat.parse("20130519"));
        expected.setExpirationDate(dateFormat.parse("21000102"));
	}
	
	/*
		A method to test the fields for a CourseWaitList after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudCourseWaitList_testDTOFieldsForTestReadAfterUpdate(CourseWaitListInfo expected, CourseWaitListInfo actual)
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        if(expected.getActivityOfferingIds() != null) {
            assertEquals(expected.getActivityOfferingIds().size(), actual.getActivityOfferingIds().size());
            for(String aoId : expected.getActivityOfferingIds()) {
                assertTrue(actual.getActivityOfferingIds().contains(aoId));
            }
        } else {
            assertNull(actual.getActivityOfferingIds());
        }
        if(expected.getFormatOfferingIds() != null) {
            assertEquals(expected.getFormatOfferingIds().size(), actual.getFormatOfferingIds().size());
            for(String foId : expected.getFormatOfferingIds()) {
                assertTrue(actual.getFormatOfferingIds().contains(foId));
            }
        } else {
            assertNull(actual.getFormatOfferingIds());
        }
        assertEquals (expected.getRegisterInFirstAvailableActivityOffering(), actual.getRegisterInFirstAvailableActivityOffering());
        assertEquals (expected.getAutomaticallyProcessed(), actual.getAutomaticallyProcessed());
        assertEquals (expected.getConfirmationRequired(), actual.getConfirmationRequired());
        assertEquals (expected.getMaxSize(), actual.getMaxSize());
        assertEquals (expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
        assertEquals (expected.getAllowHoldUntilEntries(), actual.getAllowHoldUntilEntries());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
	}
	
	/*
		A method to set the fields for a CourseWaitList in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudCourseWaitList_setDTOFieldsForTestReadAfterUpdate(CourseWaitListInfo expected) throws ParseException
	{
        List<String> offerings = new ArrayList<String>();
        offerings.add("1");
        offerings.add("aoeu");
        offerings.add("tnoeht");
        expected.setActivityOfferingIds(offerings);
        offerings = new ArrayList<String>();
        offerings.add("ooooeuoeuoe");
        expected.setFormatOfferingIds(offerings);
        expected.setConfirmationRequired(true);
        expected.setAutomaticallyProcessed(true);
        expected.setRegisterInFirstAvailableActivityOffering(false);
        expected.setMaxSize(4321);
        expected.setCheckInRequired(false);
        expected.setCheckInFrequency(null);
        expected.setAllowHoldUntilEntries(true);
        expected.setEffectiveDate(dateFormat.parse("20110519"));
        expected.setExpirationDate(dateFormat.parse("21000212"));
	}
	
	
	// ****************************************************
	//           CourseWaitListEntryInfo
	// ****************************************************
	
	/*
		A method to set the fields for a CourseWaitListEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudCourseWaitListEntry_setDTOFieldsForTestCreate(CourseWaitListEntryInfo expected) throws ParseException
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
        expected.setEffectiveDate(dateFormat.parse("20110519"));
        expected.setExpirationDate(dateFormat.parse("21000212"));
        expected.setLastCheckIn(dateFormat.parse("19000101"));
		expected.setCourseWaitListId("waitListId01");
		expected.setStudentId("studentId01");
		expected.setRegistrationGroupId("registrationGroupId01");
		expected.setPosition(1);
	}
	
	/*
		A method to test the fields for a CourseWaitListEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudCourseWaitListEntry_testDTOFieldsForTestCreateUpdate(CourseWaitListEntryInfo expected, CourseWaitListEntryInfo actual)
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getCourseWaitListId(), actual.getCourseWaitListId());
		assertEquals (expected.getStudentId(), actual.getStudentId());
		assertEquals (expected.getRegistrationGroupId(), actual.getRegistrationGroupId());
		assertEquals (expected.getPosition(), actual.getPosition());
		assertEquals (expected.getLastCheckIn(), actual.getLastCheckIn());
	}
	
	/*
		A method to set the fields for a CourseWaitListEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudCourseWaitListEntry_setDTOFieldsForTestUpdate(CourseWaitListEntryInfo expected) throws ParseException
	{
        expected.setEffectiveDate(dateFormat.parse("20120219"));
        expected.setExpirationDate(dateFormat.parse("21000515"));
        expected.setLastCheckIn(dateFormat.parse("19000202"));
        expected.setRegistrationGroupId("registrationGroupId_Updated");
	}
	
	/*
		A method to test the fields for a CourseWaitListEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudCourseWaitListEntry_testDTOFieldsForTestReadAfterUpdate(CourseWaitListEntryInfo expected, CourseWaitListEntryInfo actual)
	{
		assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getCourseWaitListId(), actual.getCourseWaitListId());
        assertEquals (expected.getStudentId(), actual.getStudentId());
        assertEquals (expected.getRegistrationGroupId(), actual.getRegistrationGroupId());
        assertEquals (expected.getPosition(), actual.getPosition());
        assertEquals (expected.getLastCheckIn(), actual.getLastCheckIn());
	}
	
	/*
		A method to set the fields for a CourseWaitListEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudCourseWaitListEntry_setDTOFieldsForTestReadAfterUpdate(CourseWaitListEntryInfo expected) throws ParseException
	{
        expected.setEffectiveDate(dateFormat.parse("20130219"));
        expected.setExpirationDate(dateFormat.parse("21000414"));
        expected.setLastCheckIn(dateFormat.parse("19000303"));
        expected.setRegistrationGroupId("registrationGroupId_Updated");
        expected.setCourseWaitListId("WL_ID_Updated");
        expected.setStudentId("S_ID_Updated");
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getCourseWaitListsByActivityOffering */
	@Test
	public void test_getCourseWaitListsByActivityOffering() throws Exception {
        loadData();
        List<CourseWaitListInfo> waitLists = testService.getCourseWaitListsByActivityOffering("activityOfferingId0", contextInfo);
        assertEquals(10, waitLists.size());

        waitLists = testService.getCourseWaitListsByActivityOffering("activityOfferingId9", contextInfo);
        assertEquals(1, waitLists.size());
        Assert.assertEquals(Integer.valueOf(19), waitLists.get(0).getMaxSize());
    }

    /* Method Name: getCourseWaitListsByFormatOffering */
    @Test
    public void test_getCourseWaitListsByFormatOffering() throws Exception {
        loadData();
        List<CourseWaitListInfo> waitLists = testService.getCourseWaitListsByFormatOffering("formatOfferingId0", contextInfo);
        assertEquals(10, waitLists.size());

        waitLists = testService.getCourseWaitListsByFormatOffering("formatOfferingId9", contextInfo);
        assertEquals(1, waitLists.size());
        Assert.assertEquals(Integer.valueOf(19), waitLists.get(0).getMaxSize());
    }


    /* Method Name: searchForCourseWaitListIds */
	@Test
	public void test_searchForCourseWaitListIds()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForCourseWaitLists */
	@Test
	public void test_searchForCourseWaitLists()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateCourseWaitList */
	@Test
	public void test_validateCourseWaitList()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: changeCourseWaitListState */
	@Test
	public void test_changeCourseWaitListState() throws Exception {
        loadData();
        List<CourseWaitListInfo> waitLists = testService.getCourseWaitListsByActivityOffering("activityOfferingId0", contextInfo);
        assertEquals(10, waitLists.size());

        for(CourseWaitListInfo waitList : waitLists) {
            String newState = waitList.getStateKey() + "_UPDATED";
            testService.changeCourseWaitListState(waitList.getId(), newState, contextInfo);
            CourseWaitListInfo updatedWaitList = testService.getCourseWaitList(waitList.getId(), contextInfo);
            assertEquals(newState, updatedWaitList.getStateKey());
        }
    }

	/* Method Name: getCourseWaitListEntriesByStudent */
	@Test
	public void test_getCourseWaitListEntriesByStudent() throws Exception {
        loadData();

        List<CourseWaitListEntryInfo> entries = testService.getCourseWaitListEntriesByStudent("studentId9", contextInfo);
        assertEquals(1, entries.size());

        entries = testService.getCourseWaitListEntriesByStudent("studentId0", contextInfo);
        assertEquals(10, entries.size());
	}
	
	/* Method Name: getCourseWaitListEntriesByCourseWaitList */
	@Test
	public void test_getCourseWaitListEntriesByCourseWaitList() throws Exception {
        loadData();

        List<String> waitListIds = testService.getCourseWaitListIdsByType(CourseWaitListDataLoader.COURSE_WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<CourseWaitListEntryInfo> entriesByWaitList = testService.getCourseWaitListEntriesByCourseWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());

        for(int i = 0; i < 10; i++) {
            assertContainsInfo(entriesByWaitList.get(i), "studentId" + i, "registrationGroupId" + i, id, i + 1);
        }

	}
	/* Method Name: getCourseWaitListEntriesByCourseWaitListAndStudent */
	@Test
	public void test_getCourseWaitListEntriesByCourseWaitListAndStudent() throws Exception {
        loadData();

        List<String> waitListIds = testService.getCourseWaitListIdsByType(CourseWaitListDataLoader.COURSE_WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        for(int i = 0; i < 10; i++) {
            List<CourseWaitListEntryInfo> entriesByWaitList = testService.getCourseWaitListEntriesByCourseWaitListAndStudent(id, "studentId" + i, contextInfo);
            assertEquals(1, entriesByWaitList.size());
            CourseWaitListEntryInfo entry = entriesByWaitList.get(0);
            assertContainsInfo(entry, "studentId" + i, "registrationGroupId" + i, id, i +1);
        }

	}
	/* Method Name: searchForCourseWaitListEntryIds */
	@Test
	public void test_searchForCourseWaitListEntryIds()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForCourseWaitListEntries */
	@Test
	public void test_searchForCourseWaitListEntries()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateCourseWaitListEntry */
	@Test
	public void test_validateCourseWaitListEntry()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: changeCourseWaitListEntryState */
	@Test
	public void test_changeCourseWaitListEntryState() throws Exception {
        loadData();
        List<CourseWaitListEntryInfo> entries = testService.getCourseWaitListEntriesByStudent("studentId0", contextInfo);
        assertEquals(10, entries.size());

        for(CourseWaitListEntryInfo entry : entries) {
            String newState = entry.getStateKey() + "_UPDATED";
            testService.changeCourseWaitListEntryState(entry.getId(), newState, contextInfo);
            CourseWaitListEntryInfo updatedEntry = testService.getCourseWaitListEntry(entry.getId(), contextInfo);
            assertEquals(newState, updatedEntry.getStateKey());
        }
	}

	/* Method Name: reorderCourseWaitListEntries */
	@Test
	public void test_reorderCourseWaitListEntries() throws Exception {
        loadData();

        List<String> waitListIds = testService.getCourseWaitListIdsByType(CourseWaitListDataLoader.COURSE_WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<CourseWaitListEntryInfo> entriesByWaitList = testService.getCourseWaitListEntriesByCourseWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());
        List<String> reorderedIds = new ArrayList<String>();
        reorderedIds.add(entriesByWaitList.get(9).getId());
        reorderedIds.add(entriesByWaitList.get(2).getId());
        reorderedIds.add(entriesByWaitList.get(5).getId());
        reorderedIds.add(entriesByWaitList.get(6).getId());

        testService.reorderCourseWaitListEntries(id, reorderedIds, contextInfo);

        List<CourseWaitListEntryInfo> reorderedEntries = testService.getCourseWaitListEntriesByCourseWaitList(id, contextInfo);

        assertEquals(entriesByWaitList.get(9).getId(), reorderedEntries.get(0).getId());
        assertEquals(entriesByWaitList.get(2).getId(), reorderedEntries.get(1).getId());
        assertEquals(entriesByWaitList.get(5).getId(), reorderedEntries.get(2).getId());
        assertEquals(entriesByWaitList.get(6).getId(), reorderedEntries.get(3).getId());
        assertEquals(entriesByWaitList.get(0).getId(), reorderedEntries.get(4).getId());
        assertEquals(entriesByWaitList.get(1).getId(), reorderedEntries.get(5).getId());
        assertEquals(entriesByWaitList.get(3).getId(), reorderedEntries.get(6).getId());
        assertEquals(entriesByWaitList.get(4).getId(), reorderedEntries.get(7).getId());
        assertEquals(entriesByWaitList.get(7).getId(), reorderedEntries.get(8).getId());
        assertEquals(entriesByWaitList.get(8).getId(), reorderedEntries.get(9).getId());
    }
	
	/* Method Name: moveCourseWaitListEntryToPosition */
	@Test
	public void test_moveCourseWaitListEntryToPosition() throws Exception {
        loadData();

        List<String> waitListIds = testService.getCourseWaitListIdsByType(CourseWaitListDataLoader.COURSE_WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<CourseWaitListEntryInfo> entriesByWaitList = testService.getCourseWaitListEntriesByCourseWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());

        testService.moveCourseWaitListEntryToPosition(entriesByWaitList.get(0).getId(), 10, contextInfo);
        testService.moveCourseWaitListEntryToPosition(entriesByWaitList.get(3).getId(), 1, contextInfo);
        testService.moveCourseWaitListEntryToPosition(entriesByWaitList.get(7).getId(), 2, contextInfo);

        List<CourseWaitListEntryInfo> reorderedEntries = testService.getCourseWaitListEntriesByCourseWaitList(id, contextInfo);

        assertEquals(entriesByWaitList.get(3).getId(), reorderedEntries.get(0).getId());
        assertEquals(entriesByWaitList.get(7).getId(), reorderedEntries.get(1).getId());
        assertEquals(entriesByWaitList.get(1).getId(), reorderedEntries.get(2).getId());
        assertEquals(entriesByWaitList.get(2).getId(), reorderedEntries.get(3).getId());
        assertEquals(entriesByWaitList.get(4).getId(), reorderedEntries.get(4).getId());
        assertEquals(entriesByWaitList.get(5).getId(), reorderedEntries.get(5).getId());
        assertEquals(entriesByWaitList.get(6).getId(), reorderedEntries.get(6).getId());
        assertEquals(entriesByWaitList.get(8).getId(), reorderedEntries.get(7).getId());
        assertEquals(entriesByWaitList.get(9).getId(), reorderedEntries.get(8).getId());
        assertEquals(entriesByWaitList.get(0).getId(), reorderedEntries.get(9).getId());
	}

    private void assertContainsInfo(CourseWaitListEntryInfo entry, String studentId, String regGroupId,
                                    String waitListId, Integer position) {
        if(!entry.getStudentId().equals(studentId) || !entry.getRegistrationGroupId().equals(regGroupId) ||
                !entry.getCourseWaitListId().equals(waitListId) || !entry.getPosition().equals(position)) {
            fail("list does not contain " + studentId + ", " + regGroupId + ", " + waitListId + ", and " + position);
        }
    }

    private void loadData() throws Exception {
        dataLoader.beforeTest();
    }
	
}


