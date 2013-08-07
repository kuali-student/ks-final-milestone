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
package org.kuali.student.enrollment.class1.roster.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
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
@ContextConfiguration(locations = {"classpath:lprRoster-test-with-map-context.xml"})
public class TestLprRosterServiceImplConformanceExtendedCrud extends TestLprRosterServiceImplConformanceBaseCrud 
{

    @Resource
    protected LprRosterDataLoader dataLoader;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           LprRosterInfo
	// ****************************************************
	
	/*
		A method to set the fields for a LprRoster in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudLprRoster_setDTOFieldsForTestCreate(LprRosterInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("plain descr " + expected.getName(), "formatted descr " + expected.getName()));
        List<String >associatedLuis = new ArrayList<String>();
        associatedLuis.add("50");
        associatedLuis.add("60");
        associatedLuis.add("70");
        associatedLuis.add("80");
        associatedLuis.add("90");
		expected.setAssociatedLuiIds(associatedLuis);
        expected.setMaximumCapacity(100);
		expected.setCheckInRequired(true);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(5);
        timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
		expected.setCheckInFrequency(timeAmountInfo);
	}
	
	/*
		A method to test the fields for a LprRoster. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudLprRoster_testDTOFieldsForTestCreateUpdate(LprRosterInfo expected, LprRosterInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        if(expected.getAssociatedLuiIds() != null) {
            assertEquals(expected.getAssociatedLuiIds().size(), actual.getAssociatedLuiIds().size());
            for(String luiId : expected.getAssociatedLuiIds()) {
                assertTrue(actual.getAssociatedLuiIds().contains(luiId));
            }
        } else {
            assertNull(actual.getAssociatedLuiIds());
        }

		assertEquals(expected.getMaximumCapacity(), actual.getMaximumCapacity());
		assertEquals(expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
	}
	
	/*
		A method to set the fields for a LprRoster in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudLprRoster_setDTOFieldsForTestUpdate(LprRosterInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo(expected.getDescr().getPlain() + "_Updated", expected.getDescr().getFormatted() + "_Updated"));
        List<String >associatedLuis = new ArrayList<String>();
        associatedLuis.add("100");
        expected.setAssociatedLuiIds(associatedLuis);
		expected.setMaximumCapacity(0);
		expected.setCheckInRequired(false);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(10);
        timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_MONTH_TYPE_KEY);
        expected.setCheckInFrequency(timeAmountInfo);
	}
	
	/*
		A method to test the fields for a LprRoster after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudLprRoster_testDTOFieldsForTestReadAfterUpdate(LprRosterInfo expected, LprRosterInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        if(expected.getAssociatedLuiIds() != null) {
            assertEquals(expected.getAssociatedLuiIds().size(), actual.getAssociatedLuiIds().size());
            for(String luiId : expected.getAssociatedLuiIds()) {
                assertTrue(actual.getAssociatedLuiIds().contains(luiId));
            }
        } else {
            assertNull(actual.getAssociatedLuiIds());
        }

        assertEquals(expected.getMaximumCapacity(), actual.getMaximumCapacity());
        assertEquals(expected.getCheckInRequired(), actual.getCheckInRequired());
        if(expected.getCheckInFrequency() != null) {
            assertEquals(expected.getCheckInFrequency().getTimeQuantity(), actual.getCheckInFrequency().getTimeQuantity());
            assertEquals(expected.getCheckInFrequency().getAtpDurationTypeKey(), actual.getCheckInFrequency().getAtpDurationTypeKey());
        } else {
            assertNull(actual.getCheckInFrequency());
        }
	}
	
	/*
		A method to set the fields for a LprRoster in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudLprRoster_setDTOFieldsForTestReadAfterUpdate(LprRosterInfo expected) 
	{
		expected.setName("name_Updated");
        List<String >associatedLuis = new ArrayList<String>();
        associatedLuis.add("101");
        associatedLuis.add("102");
        associatedLuis.add("103");
        expected.setAssociatedLuiIds(associatedLuis);
        expected.setMaximumCapacity(11);
        expected.setCheckInRequired(true);
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(5);
        timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_MINUTES_TYPE_KEY);
        expected.setCheckInFrequency(timeAmountInfo);
	}
	
	
	// ****************************************************
	//           LprRosterEntryInfo
	// ****************************************************
	
	/*
		A method to set the fields for a LprRosterEntry in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudLprRosterEntry_setDTOFieldsForTestCreate(LprRosterEntryInfo expected) {
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
        try {
            expected.setEffectiveDate(dateFormat.parse("20130611"));
            expected.setExpirationDate(dateFormat.parse("21000101"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
		expected.setLprRosterId("A");
		expected.setLprId("100");
		expected.setPosition(5);
	}
	
	/*
		A method to test the fields for a LprRosterEntry. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudLprRosterEntry_testDTOFieldsForTestCreateUpdate(LprRosterEntryInfo expected, LprRosterEntryInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getLprRosterId(), actual.getLprRosterId());
		assertEquals (expected.getLprId(), actual.getLprId());
		assertEquals (expected.getPosition(), actual.getPosition());
	}
	
	/*
		A method to set the fields for a LprRosterEntry in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudLprRosterEntry_setDTOFieldsForTestUpdate(LprRosterEntryInfo expected) {
		expected.setStateKey("stateKey_Updated");
        try {
            expected.setEffectiveDate(dateFormat.parse("20120611"));
            expected.setExpirationDate(dateFormat.parse("20130601"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
        expected.setLprRosterId("A");
        expected.setLprId("100");
        expected.setPosition(2);
	}
	
	/*
		A method to test the fields for a LprRosterEntry after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudLprRosterEntry_testDTOFieldsForTestReadAfterUpdate(LprRosterEntryInfo expected, LprRosterEntryInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals (expected.getExpirationDate(), actual.getExpirationDate());
		assertEquals (expected.getLprRosterId(), actual.getLprRosterId());
		assertEquals (expected.getLprId(), actual.getLprId());
		assertEquals (expected.getPosition(), actual.getPosition());
	}
	
	/*
		A method to set the fields for a LprRosterEntry in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudLprRosterEntry_setDTOFieldsForTestReadAfterUpdate(LprRosterEntryInfo expected) 
	{
        expected.setLprRosterId("C");
        expected.setLprId("222");
        try {
            expected.setEffectiveDate(dateFormat.parse("20120611"));
            expected.setExpirationDate(dateFormat.parse("30300601"));
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
        expected.setPosition(1);
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getLprRostersByLui */
	@Test
	public void test_getLprRostersByLui()
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<LprRosterInfo> rosters = testService.getLprRostersByLui("4", contextInfo);
        assertEquals(2, rosters.size());
        assertContainsId("D", rosters);
        assertContainsId("B", rosters);

        rosters = testService.getLprRostersByLui("DOES_NOT_EXIST", contextInfo);
        assertEquals(0, rosters.size());

        rosters = testService.getLprRostersByLui("2", contextInfo);
        assertEquals(1, rosters.size());
        assertContainsId("B", rosters);
	}
	
	/* Method Name: getLprRostersByTypeAndLui */
	@Test
	public void test_getLprRostersByTypeAndLui() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();

        List<LprRosterInfo> rosters = testService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY, "4", contextInfo);
        assertEquals(2, rosters.size());
        assertContainsId("D", rosters);
        assertContainsId("B", rosters);

        rosters = testService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY, "DOES_NOT_EXIST", contextInfo);
        assertEquals(0, rosters.size());

        rosters = testService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY, "1", contextInfo);
        assertEquals(1, rosters.size());
        assertContainsId("A", rosters);

        rosters = testService.getLprRostersByTypeAndLui("SOME_TYPE_KEY_THAT_DOES_NOT_EXIST", "1", contextInfo);
        assertEquals(0, rosters.size());
	}
	
	/* Method Name: searchForLprRosterIds */
	@Test
	public void test_searchForLprRosterIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForLprRosters */
	@Test
	public void test_searchForLprRosters() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateLprRoster */
	@Test
	public void test_validateLprRoster() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getLprRosterEntriesByLprRoster */
	@Test
	public void test_getLprRosterEntriesByLprRoster() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getLprRosterEntriesByLpr */
	@Test
	public void test_getLprRosterEntriesByLpr() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<LprRosterEntryInfo> entries = testService.getLprRosterEntriesByLprRoster("A", contextInfo);
        assertEquals(9, entries.size());
        for(int i = 0; i < entries.size(); i++) {
            LprRosterEntryInfo entry = entries.get(i);
            assertEquals(9 - i, Integer.parseInt(entry.getId()));
        }

        entries = testService.getLprRosterEntriesByLprRoster("D", contextInfo);
        assertEquals(0, entries.size());
	}
	
	/* Method Name: getLprRosterEntriesByLprRosterAndLpr */
	@Test
	public void test_getLprRosterEntriesByLprRosterAndLpr() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<LprRosterEntryInfo> entries = testService.getLprRosterEntriesByLprRosterAndLpr("C", "19", contextInfo);
        assertEquals(2, entries.size());
        assertContainsId("19", entries);
        assertContainsId("20", entries);

        entries = testService.getLprRosterEntriesByLprRosterAndLpr("C", "1", contextInfo);
        assertEquals(0, entries.size());
	}
	
	/* Method Name: searchForLprRosterEntryIds */
	@Test
	public void test_searchForLprRosterEntryIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForLprRosterEntries */
	@Test
	public void test_searchForLprRosterEntries() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateLprRosterEntry */
	@Test
	public void test_validateLprRosterEntry() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: moveLprRosterEntryToPosition */
	@Test
	public void test_moveLprRosterEntryToPosition() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        testService.moveLprRosterEntryToPosition("9", 9, contextInfo);
        List<LprRosterEntryInfo> entries = testService.getLprRosterEntriesByLprRoster("A", contextInfo);
        assertEquals(9, entries.size());
        for(int i = 0; i < entries.size() - 1; i++) {
            LprRosterEntryInfo entry = entries.get(i);
            assertEquals(8 - i, Integer.parseInt(entry.getId()));
        }
        assertEquals("9", entries.get(8).getId());

        testService.moveLprRosterEntryToPosition("1", 1, contextInfo);
        testService.moveLprRosterEntryToPosition("7", 7, contextInfo);
        testService.moveLprRosterEntryToPosition("5", 5, contextInfo);
        entries = testService.getLprRosterEntriesByLprRoster("A", contextInfo);
        assertEquals(9, entries.size());
        assertEquals("1", entries.get(0).getId());
        assertEquals("8", entries.get(1).getId());
        assertEquals("6", entries.get(2).getId());
        assertEquals("4", entries.get(3).getId());
        assertEquals("5", entries.get(4).getId());
        assertEquals("3", entries.get(5).getId());
        assertEquals("7", entries.get(6).getId());
        assertEquals("2", entries.get(7).getId());
        assertEquals("9", entries.get(8).getId());
	}
	
	/* Method Name: reorderLprRosterEntries */
	@Test
	public void test_reorderLprRosterEntries() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
        loadData();
        List<String> newOrder = new ArrayList<String>();
        newOrder.add("3");
        newOrder.add("2");
        newOrder.add("1");
        newOrder.add("7");
        testService.reorderLprRosterEntries("A", newOrder, contextInfo);
        List<LprRosterEntryInfo> entries = testService.getLprRosterEntriesByLprRoster("A", contextInfo);
        assertEquals(9, entries.size());
        assertEquals("3", entries.get(0).getId());
        assertEquals("2", entries.get(1).getId());
        assertEquals("1", entries.get(2).getId());
        assertEquals("7", entries.get(3).getId());
        assertEquals("9", entries.get(4).getId());
        assertEquals("8", entries.get(5).getId());
        assertEquals("6", entries.get(6).getId());
        assertEquals("5", entries.get(7).getId());
        assertEquals("4", entries.get(8).getId());

        newOrder = new ArrayList<String>();
        newOrder.add("4");
        testService.reorderLprRosterEntries("A", newOrder, contextInfo);
        entries = testService.getLprRosterEntriesByLprRoster("A", contextInfo);
        assertEquals(9, entries.size());

        assertEquals("4", entries.get(0).getId());
        assertEquals("3", entries.get(1).getId());
        assertEquals("2", entries.get(2).getId());
        assertEquals("1", entries.get(3).getId());
        assertEquals("7", entries.get(4).getId());
        assertEquals("9", entries.get(5).getId());
        assertEquals("8", entries.get(6).getId());
        assertEquals("6", entries.get(7).getId());
        assertEquals("5", entries.get(8).getId());
	}

    private void assertContainsId(String id, List<? extends HasId> list) {
        for(HasId info: list) {
            if(info.getId() != null && info.getId().equals(id)) {
                return;
            }
        }
        fail("list does not contain id " + id);
    }

    private void loadData() throws OperationFailedException{
        try {
            dataLoader.beforeTest();
        } catch (Exception e) {
            throw new OperationFailedException("failed to load data", e);
        }
    }
	
}


