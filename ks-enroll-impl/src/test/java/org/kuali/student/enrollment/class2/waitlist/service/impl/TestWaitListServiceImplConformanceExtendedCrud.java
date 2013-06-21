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
package org.kuali.student.enrollment.class2.waitlist.service.impl;


import java.lang.Exception;import java.lang.Integer;import java.lang.RuntimeException;import java.lang.String;import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:waitList-test-with-map-context.xml"})
public class TestWaitListServiceImplConformanceExtendedCrud extends TestWaitListServiceImplConformanceBaseCrud 
{

    @Resource
    protected WaitListDataLoader dataLoader;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           WaitListInfo
	// ****************************************************
	
	/*
		A method to set the fields for a WaitList in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudWaitList_setDTOFieldsForTestCreate(WaitListInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
        List<String> offerings = new ArrayList<String>();
        offerings.add("1");
        offerings.add("2");
        offerings.add("3");
		expected.setAssociatedOfferingIds(offerings);
		expected.setOfferingTypeKey(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY);
		expected.setWaitListProcessingTypeKey("waitListProcessingTypeKey01");
		expected.setMaxSize(200);
		expected.setCheckInRequired(true);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(5);
        timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
		expected.setCheckInFrequency(timeAmountInfo);
		expected.setAllowHoldListEntries(true);
        try {
            expected.setEffectiveDate(dateFormat.parse("20130611"));
            expected.setExpirationDate(dateFormat.parse("21000101"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
	}
	
	/*
		A method to test the fields for a WaitList. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudWaitList_testDTOFieldsForTestCreateUpdate(WaitListInfo expected, WaitListInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        if(expected.getAssociatedOfferingIds() != null) {
            assertEquals(expected.getAssociatedOfferingIds().size(), actual.getAssociatedOfferingIds().size());
            for(String offeringId : expected.getAssociatedOfferingIds()) {
                assertTrue(actual.getAssociatedOfferingIds().contains(offeringId));
            }
        } else {
            assertNull(actual.getAssociatedOfferingIds());
        }
		assertEquals (expected.getOfferingTypeKey(), actual.getOfferingTypeKey());
		assertEquals (expected.getWaitListProcessingTypeKey(), actual.getWaitListProcessingTypeKey());
		assertEquals (expected.getMaxSize(), actual.getMaxSize());
		assertEquals (expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
		assertEquals (expected.getAllowHoldListEntries(), actual.getAllowHoldListEntries());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
	}
	
	/*
		A method to set the fields for a WaitList in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudWaitList_setDTOFieldsForTestUpdate(WaitListInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
        List<String> offerings = new ArrayList<String>();
        offerings.add("2");
        offerings.add("3");
        offerings.add("4");
        offerings.add("5");
        expected.setAssociatedOfferingIds(offerings);
		expected.setOfferingTypeKey("offeringTypeKey_Updated");
		expected.setWaitListProcessingTypeKey("waitListProcessingTypeKey_Updated");
		expected.setMaxSize(1234);
		expected.setCheckInRequired(false);
		expected.setCheckInFrequency(null);
		expected.setAllowHoldListEntries(false);
        try {
            expected.setEffectiveDate(dateFormat.parse("20130519"));
            expected.setExpirationDate(dateFormat.parse("21000102"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
	}
	
	/*
		A method to test the fields for a WaitList after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudWaitList_testDTOFieldsForTestReadAfterUpdate(WaitListInfo expected, WaitListInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        if(expected.getAssociatedOfferingIds() != null) {
            assertEquals(expected.getAssociatedOfferingIds().size(), actual.getAssociatedOfferingIds().size());
            for(String offeringId : expected.getAssociatedOfferingIds()) {
                assertTrue(actual.getAssociatedOfferingIds().contains(offeringId));
            }
        } else {
            assertNull(actual.getAssociatedOfferingIds());
        }
        assertEquals (expected.getOfferingTypeKey(), actual.getOfferingTypeKey());
        assertEquals (expected.getWaitListProcessingTypeKey(), actual.getWaitListProcessingTypeKey());
        assertEquals (expected.getMaxSize(), actual.getMaxSize());
        assertEquals (expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
        assertEquals (expected.getAllowHoldListEntries(), actual.getAllowHoldListEntries());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
	}
	
	/*
		A method to set the fields for a WaitList in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudWaitList_setDTOFieldsForTestReadAfterUpdate(WaitListInfo expected) 
	{
        expected.setStateKey("stateKey2_Updated");
        List<String> offerings = new ArrayList<String>();
        offerings.add("4");
        offerings.add("AOEU");
        expected.setAssociatedOfferingIds(offerings);
        expected.setOfferingTypeKey("offeringTypeKey2_Updated");
        expected.setWaitListProcessingTypeKey("waitListProcessingTypeKey2_Updated");
        expected.setMaxSize(4321);
        expected.setCheckInRequired(false);
        expected.setCheckInFrequency(null);
        expected.setAllowHoldListEntries(true);
        try {
            expected.setEffectiveDate(dateFormat.parse("20110519"));
            expected.setExpirationDate(dateFormat.parse("21000212"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
	}
	
	
	// ****************************************************
	//           WaitListEntryInfo
	// ****************************************************
	
	/*
		A method to set the fields for a WaitListEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudWaitListEntry_setDTOFieldsForTestCreate(WaitListEntryInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
        try {
            expected.setEffectiveDate(dateFormat.parse("20110519"));
            expected.setExpirationDate(dateFormat.parse("21000212"));
            expected.setLastCheckIn(dateFormat.parse("19000101"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
		expected.setWaitListId("waitListId01");
		expected.setStudentId("studentId01");
		expected.setOfferingId("offeringId01");
		expected.setPosition(1);
        List<String> ruleIds = new ArrayList<String>();
        ruleIds.add("4");
        ruleIds.add("5");
        ruleIds.add("6");
        ruleIds.add("7");
		expected.setHoldListRuleIds(ruleIds);
	}
	
	/*
		A method to test the fields for a WaitListEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudWaitListEntry_testDTOFieldsForTestCreateUpdate(WaitListEntryInfo expected, WaitListEntryInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getWaitListId(), actual.getWaitListId());
		assertEquals (expected.getStudentId(), actual.getStudentId());
		assertEquals (expected.getOfferingId(), actual.getOfferingId());
		assertEquals (expected.getPosition(), actual.getPosition());
		assertEquals (expected.getLastCheckIn(), actual.getLastCheckIn());
        if(expected.getHoldListRuleIds() != null) {
            assertEquals(expected.getHoldListRuleIds().size(), actual.getHoldListRuleIds().size());
            for(String offeringId : expected.getHoldListRuleIds()) {
                assertTrue(actual.getHoldListRuleIds().contains(offeringId));
            }
        } else {
            assertNull(actual.getHoldListRuleIds());
        }
	}
	
	/*
		A method to set the fields for a WaitListEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudWaitListEntry_setDTOFieldsForTestUpdate(WaitListEntryInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
        try {
            expected.setEffectiveDate(dateFormat.parse("20120219"));
            expected.setExpirationDate(dateFormat.parse("21000515"));
            expected.setLastCheckIn(dateFormat.parse("19000202"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
		expected.setOfferingId("offeringId_Updated");
        List<String> ruleIds = new ArrayList<String>();
        ruleIds.add("A");
        ruleIds.add("B");
        expected.setHoldListRuleIds(ruleIds);
	}
	
	/*
		A method to test the fields for a WaitListEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudWaitListEntry_testDTOFieldsForTestReadAfterUpdate(WaitListEntryInfo expected, WaitListEntryInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals (expected.getWaitListId(), actual.getWaitListId());
        assertEquals (expected.getStudentId(), actual.getStudentId());
        assertEquals (expected.getOfferingId(), actual.getOfferingId());
        assertEquals (expected.getPosition(), actual.getPosition());
        assertEquals (expected.getLastCheckIn(), actual.getLastCheckIn());
        if(expected.getHoldListRuleIds() != null) {
            assertEquals(expected.getHoldListRuleIds().size(), actual.getHoldListRuleIds().size());
            for(String offeringId : expected.getHoldListRuleIds()) {
                assertTrue(actual.getHoldListRuleIds().contains(offeringId));
            }
        } else {
            assertNull(actual.getHoldListRuleIds());
        }
	}
	
	/*
		A method to set the fields for a WaitListEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudWaitListEntry_setDTOFieldsForTestReadAfterUpdate(WaitListEntryInfo expected) 
	{
        expected.setStateKey("stateKey2_Updated");
        try {
            expected.setEffectiveDate(dateFormat.parse("20130219"));
            expected.setExpirationDate(dateFormat.parse("21000414"));
            expected.setLastCheckIn(dateFormat.parse("19000303"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
        expected.setOfferingId("offeringId_Updated");
        expected.setWaitListId("WL_ID_Updated");
        expected.setStudentId("S_ID_Updated");
        List<String> ruleIds = new ArrayList<String>();
        ruleIds.add("C");
        expected.setHoldListRuleIds(ruleIds);
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getWaitListsByOffering */
	@Test
	public void test_getWaitListsByOffering() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<WaitListInfo> waitLists = testService.getWaitListsByOffering("offeringId0", contextInfo);
        assertEquals(10, waitLists.size());

        waitLists = testService.getWaitListsByOffering("offeringId9", contextInfo);
        assertEquals(1, waitLists.size());
        Assert.assertEquals(Integer.valueOf(19), waitLists.get(0).getMaxSize());
    }
	
	/* Method Name: getWaitListsByTypeAndOffering */
	@Test
	public void test_getWaitListsByTypeAndOffering() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<WaitListInfo> waitLists = testService.getWaitListsByTypeAndOffering(WaitListDataLoader.WAIT_LIST_TYPE_KEY + ".1", "offeringId0", contextInfo);
        assertEquals(1, waitLists.size());
        assertEquals(Integer.valueOf(10), waitLists.get(0).getMaxSize());

        waitLists = testService.getWaitListsByTypeAndOffering(WaitListDataLoader.WAIT_LIST_TYPE_KEY, "offeringId9", contextInfo);
        assertEquals(0, waitLists.size());
	}
	
	/* Method Name: searchForWaitListIds */
	@Test
	public void test_searchForWaitListIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForWaitLists */
	@Test
	public void test_searchForWaitLists() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateWaitList */
	@Test
	public void test_validateWaitList() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getWaitListEntriesByStudent */
	@Test
	public void test_getWaitListEntriesByStudent() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<WaitListEntryInfo> entries = testService.getWaitListEntriesByStudent("studentId9", contextInfo);
        assertEquals(1, entries.size());

        entries = testService.getWaitListEntriesByStudent("studentId0", contextInfo);
        assertEquals(10, entries.size());
	}
	
	/* Method Name: getWaitListEntriesByWaitList */
	@Test
	public void test_getWaitListEntriesByWaitList() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<String> waitListIds = testService.getWaitListIdsByType(WaitListDataLoader.WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<WaitListEntryInfo> entriesByWaitList = testService.getWaitListEntriesByWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());

        for(int i = 0; i < 10; i++) {
            assertContainsInfo(entriesByWaitList.get(i), "studentId" + i, "offeringId" + i, id, i + 1);
        }

	}
	
	/* Method Name: getWaitListEntriesByWaitListAndStudent */
	@Test
	public void test_getWaitListEntriesByWaitListAndStudent() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<String> waitListIds = testService.getWaitListIdsByType(WaitListDataLoader.WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        for(int i = 0; i < 10; i++) {
            List<WaitListEntryInfo> entriesByWaitList = testService.getWaitListEntriesByWaitListAndStudent(id, "studentId" + i, contextInfo);
            assertEquals(1, entriesByWaitList.size());
            WaitListEntryInfo entry = entriesByWaitList.get(0);
            assertContainsInfo(entry, "studentId" + i, "offeringId" + i, id, i +1);
        }

	}
	
	/* Method Name: searchForWaitListEntryIds */
	@Test
	public void test_searchForWaitListEntryIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForWaitListEntries */
	@Test
	public void test_searchForWaitListEntries() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateWaitListEntry */
	@Test
	public void test_validateWaitListEntry() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: reorderWaitListEntries */
	@Test
	public void test_reorderWaitListEntries() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<String> waitListIds = testService.getWaitListIdsByType(WaitListDataLoader.WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<WaitListEntryInfo> entriesByWaitList = testService.getWaitListEntriesByWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());
        List<String> reorderedIds = new ArrayList<String>();
        reorderedIds.add(entriesByWaitList.get(9).getId());
        reorderedIds.add(entriesByWaitList.get(2).getId());
        reorderedIds.add(entriesByWaitList.get(5).getId());
        reorderedIds.add(entriesByWaitList.get(6).getId());

        testService.reorderWaitListEntries(id, reorderedIds, contextInfo);

        List<WaitListEntryInfo> reorderedEntries = testService.getWaitListEntriesByWaitList(id, contextInfo);

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
	
	/* Method Name: moveWaitListEntryToPosition */
	@Test
	public void test_moveWaitListEntryToPosition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<String> waitListIds = testService.getWaitListIdsByType(WaitListDataLoader.WAIT_LIST_TYPE_KEY + ".10", contextInfo);
        assertEquals(1, waitListIds.size());

        String id = waitListIds.get(0);

        List<WaitListEntryInfo> entriesByWaitList = testService.getWaitListEntriesByWaitList(id, contextInfo);
        assertEquals(10, entriesByWaitList.size());

        testService.moveWaitListEntryToPosition(entriesByWaitList.get(0).getId(), 10, contextInfo);
        testService.moveWaitListEntryToPosition(entriesByWaitList.get(3).getId(), 1, contextInfo);
        testService.moveWaitListEntryToPosition(entriesByWaitList.get(7).getId(), 2, contextInfo);

        List<WaitListEntryInfo> reorderedEntries = testService.getWaitListEntriesByWaitList(id, contextInfo);

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

    private void assertContainsInfo(WaitListEntryInfo entry, String studentId, String offeringId, String waitListId, Integer position) {
        if(!entry.getStudentId().equals(studentId) || !entry.getOfferingId().equals(offeringId) || !entry.getWaitListId().equals(waitListId) || !entry.getPosition().equals(position)) {
            fail("list does not contain " + studentId + ", " + offeringId + ", " + waitListId + ", and " + position);
        }
    }

    private void loadData() throws OperationFailedException {
        try {
            dataLoader.beforeTest();
        } catch (Exception e) {
            throw new OperationFailedException("failed to load data", e);
        }
    }
	
}


