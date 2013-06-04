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
package org.kuali.student.r2.core.scheduling.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-map-impl-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestSchedulingServiceImplConformanceExtendedCrud extends TestSchedulingServiceImplConformanceBaseCrud 
{
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           ScheduleInfo
	// ****************************************************
	
	/*
		A method to set the fields for a Schedule in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudSchedule_setDTOFieldsForTestCreate(ScheduleInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr01");
		expected.setScheduleTransactionId("scheduleTransactionId01");
		expected.setAtpId("atpId01");
		//TODO *TYPE = ScheduleComponentInfoList* expected.setScheduleComponents("scheduleComponents01");
		//TODO *TYPE = DateRangeInfoList* expected.setBlackoutDates("blackoutDates01");
		//TODO *TYPE = StringList* expected.setBlackoutMilestoneIds("blackoutMilestoneIds01");
		//TODO *TYPE = MeetingTimeInfoList* expected.setAdditionalMeetingTimes("additionalMeetingTimes01");
	}
	
	/*
		A method to test the fields for a Schedule. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudSchedule_testDTOFieldsForTestCreateUpdate(ScheduleInfo expected, ScheduleInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		//TODO when entity updated assertEquals (expected.getScheduleTransactionId(), actual.getScheduleTransactionId());
		assertEquals (expected.getAtpId(), actual.getAtpId());
		//TODO *TYPE = ScheduleComponentInfoList* assertEquals (expected.getScheduleComponents(), actual.getScheduleComponents());
		//TODO *TYPE = DateRangeInfoList* assertEquals (expected.getBlackoutDates(), actual.getBlackoutDates());
		//TODO *TYPE = StringList* assertEquals (expected.getBlackoutMilestoneIds(), actual.getBlackoutMilestoneIds());
		//TODO *TYPE = MeetingTimeInfoList* assertEquals (expected.getAdditionalMeetingTimes(), actual.getAdditionalMeetingTimes());
	}
	
	/*
		A method to set the fields for a Schedule in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudSchedule_setDTOFieldsForTestUpdate(ScheduleInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr_Updated");
		expected.setScheduleTransactionId("scheduleTransactionId_Updated");
		expected.setAtpId("atpId_Updated");
		//TODO *TYPE = ScheduleComponentInfoList* expected.setScheduleComponents("scheduleComponents_Updated");
		//TODO *TYPE = DateRangeInfoList* expected.setBlackoutDates("blackoutDates_Updated");
		//TODO *TYPE = StringList* expected.setBlackoutMilestoneIds("blackoutMilestoneIds_Updated");
		//TODO *TYPE = MeetingTimeInfoList* expected.setAdditionalMeetingTimes("additionalMeetingTimes_Updated");
	}
	
	/*
		A method to test the fields for a Schedule after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudSchedule_testDTOFieldsForTestReadAfterUpdate(ScheduleInfo expected, ScheduleInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getScheduleTransactionId(), actual.getScheduleTransactionId());
		assertEquals (expected.getAtpId(), actual.getAtpId());
		//TODO *TYPE = ScheduleComponentInfoList* assertEquals (expected.getScheduleComponents(), actual.getScheduleComponents());
		//TODO *TYPE = DateRangeInfoList* assertEquals (expected.getBlackoutDates(), actual.getBlackoutDates());
		//TODO *TYPE = StringList* assertEquals (expected.getBlackoutMilestoneIds(), actual.getBlackoutMilestoneIds());
		//TODO *TYPE = MeetingTimeInfoList* assertEquals (expected.getAdditionalMeetingTimes(), actual.getAdditionalMeetingTimes());
	}
	
	/*
		A method to set the fields for a Schedule in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudSchedule_setDTOFieldsForTestReadAfterUpdate(ScheduleInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setScheduleTransactionId("scheduleTransactionId_Updated");
		expected.setAtpId("atpId_Updated");
		//TODO *TYPE = ScheduleComponentInfoList* expected.setScheduleComponents("scheduleComponents_Updated");
		//TODO *TYPE = DateRangeInfoList* expected.setBlackoutDates("blackoutDates_Updated");
		//TODO *TYPE = StringList* expected.setBlackoutMilestoneIds("blackoutMilestoneIds_Updated");
		//TODO *TYPE = MeetingTimeInfoList* expected.setAdditionalMeetingTimes("additionalMeetingTimes_Updated");
	}
	
	
	// ****************************************************
	//           ScheduleBatchInfo
	// ****************************************************
	
	/*
		A method to set the fields for a ScheduleBatch in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudScheduleBatch_setDTOFieldsForTestCreate(ScheduleBatchInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr01");
		expected.setRequestingPersonId("requestingPersonId01");
		expected.setOrgId("orgId01");
		expected.setStatusMessage("statusMessage01");
	}
	
	/*
		A method to test the fields for a ScheduleBatch. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudScheduleBatch_testDTOFieldsForTestCreateUpdate(ScheduleBatchInfo expected, ScheduleBatchInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getRequestingPersonId(), actual.getRequestingPersonId());
		assertEquals (expected.getOrgId(), actual.getOrgId());
		assertEquals (expected.getStatusMessage(), actual.getStatusMessage());
	}
	
	/*
		A method to set the fields for a ScheduleBatch in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudScheduleBatch_setDTOFieldsForTestUpdate(ScheduleBatchInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr_Updated");
		expected.setRequestingPersonId("requestingPersonId_Updated");
		expected.setOrgId("orgId_Updated");
		expected.setStatusMessage("statusMessage_Updated");
	}
	
	/*
		A method to test the fields for a ScheduleBatch after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudScheduleBatch_testDTOFieldsForTestReadAfterUpdate(ScheduleBatchInfo expected, ScheduleBatchInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getRequestingPersonId(), actual.getRequestingPersonId());
		assertEquals (expected.getOrgId(), actual.getOrgId());
		assertEquals (expected.getStatusMessage(), actual.getStatusMessage());
	}
	
	/*
		A method to set the fields for a ScheduleBatch in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudScheduleBatch_setDTOFieldsForTestReadAfterUpdate(ScheduleBatchInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setRequestingPersonId("requestingPersonId_Updated");
		expected.setOrgId("orgId_Updated");
		expected.setStatusMessage("statusMessage_Updated");
	}
	
	
	// ****************************************************
	//           ScheduleRequestInfo
	// ****************************************************
	
	/*
		A method to set the fields for a ScheduleRequest in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudScheduleRequest_setDTOFieldsForTestCreate(ScheduleRequestInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr01");
		expected.setScheduleRequestSetId("scheduleRequestSetId01");
		expected.setScheduleId("scheduleId01");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents01");
	}
	
	/*
		A method to test the fields for a ScheduleRequest. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudScheduleRequest_testDTOFieldsForTestCreateUpdate(ScheduleRequestInfo expected, ScheduleRequestInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getScheduleRequestSetId(), actual.getScheduleRequestSetId());
		assertEquals (expected.getScheduleId(), actual.getScheduleId());
		//TODO *TYPE = ScheduleRequestComponentInfoList* assertEquals (expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
	}
	
	/*
		A method to set the fields for a ScheduleRequest in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudScheduleRequest_setDTOFieldsForTestUpdate(ScheduleRequestInfo expected)
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr_Updated");
		expected.setScheduleRequestSetId("scheduleRequestSetId_Updated");
		expected.setScheduleId("scheduleId_Updated");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents_Updated");
	}
	
	/*
		A method to test the fields for a ScheduleRequest after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudScheduleRequest_testDTOFieldsForTestReadAfterUpdate(ScheduleRequestInfo expected, ScheduleRequestInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getScheduleRequestSetId(), actual.getScheduleRequestSetId());
		assertEquals (expected.getScheduleId(), actual.getScheduleId());
		//TODO *TYPE = ScheduleRequestComponentInfoList* assertEquals (expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
	}
	
	/*
		A method to set the fields for a ScheduleRequest in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudScheduleRequest_setDTOFieldsForTestReadAfterUpdate(ScheduleRequestInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setScheduleRequestSetId("scheduleRequestSetId_Updated");
		expected.setScheduleId("scheduleId_Updated");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents_Updated");
	}
	
	
	// ****************************************************
	//           TimeSlotInfo
	// ****************************************************
	
	/*
		A method to set the fields for a TimeSlot in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudTimeSlot_setDTOFieldsForTestCreate(TimeSlotInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr01");
		//TODO *TYPE = IntegerList* expected.setWeekdays("weekdays01");
		//TODO *TYPE = TimeOfDayInfo* expected.setStartTime("startTime01");
		//TODO *TYPE = TimeOfDayInfo* expected.setEndTime("endTime01");
	}
	
	/*
		A method to test the fields for a TimeSlot. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudTimeSlot_testDTOFieldsForTestCreateUpdate(TimeSlotInfo expected, TimeSlotInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		//TODO *TYPE = IntegerList* assertEquals (expected.getWeekdays(), actual.getWeekdays());
		//TODO *TYPE = TimeOfDayInfo* assertEquals (expected.getStartTime(), actual.getStartTime());
		//TODO *TYPE = TimeOfDayInfo* assertEquals (expected.getEndTime(), actual.getEndTime());
	}
	
	/*
		A method to set the fields for a TimeSlot in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudTimeSlot_setDTOFieldsForTestUpdate(TimeSlotInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr_Updated");
		//TODO *TYPE = IntegerList* expected.setWeekdays("weekdays_Updated");
		//TODO *TYPE = TimeOfDayInfo* expected.setStartTime("startTime_Updated");
		//TODO *TYPE = TimeOfDayInfo* expected.setEndTime("endTime_Updated");
	}
	
	/*
		A method to test the fields for a TimeSlot after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudTimeSlot_testDTOFieldsForTestReadAfterUpdate(TimeSlotInfo expected, TimeSlotInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		//TODO *TYPE = IntegerList* assertEquals (expected.getWeekdays(), actual.getWeekdays());
		//TODO *TYPE = TimeOfDayInfo* assertEquals (expected.getStartTime(), actual.getStartTime());
		//TODO *TYPE = TimeOfDayInfo* assertEquals (expected.getEndTime(), actual.getEndTime());
	}
	
	/*
		A method to set the fields for a TimeSlot in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudTimeSlot_setDTOFieldsForTestReadAfterUpdate(TimeSlotInfo expected) 
	{
		expected.setName("name_Updated");
		//TODO *TYPE = IntegerList* expected.setWeekdays("weekdays_Updated");
		//TODO *TYPE = TimeOfDayInfo* expected.setStartTime("startTime_Updated");
		//TODO *TYPE = TimeOfDayInfo* expected.setEndTime("endTime_Updated");
	}
	
	
	// ****************************************************
	//           ScheduleTransactionInfo
	// ****************************************************
	
	/*
		A method to set the fields for a ScheduleTransaction in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudScheduleTransaction_setDTOFieldsForTestCreate(ScheduleTransactionInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr01");
		expected.setScheduleRequestId("scheduleRequestId01");
		expected.setExistingScheduleId("existingScheduleId01");
		expected.setScheduleBatchId("scheduleBatchId01");
		expected.setScheduleId("scheduleId01");
		expected.setStatusMessage("statusMessage01");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents01");
	}
	
	/*
		A method to test the fields for a ScheduleTransaction. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudScheduleTransaction_testDTOFieldsForTestCreateUpdate(ScheduleTransactionInfo expected, ScheduleTransactionInfo actual) 
	{
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getScheduleRequestId(), actual.getScheduleRequestId());
		assertEquals (expected.getExistingScheduleId(), actual.getExistingScheduleId());
		assertEquals (expected.getScheduleBatchId(), actual.getScheduleBatchId());
		assertEquals (expected.getScheduleId(), actual.getScheduleId());
		assertEquals (expected.getStatusMessage(), actual.getStatusMessage());
		//TODO *TYPE = ScheduleRequestComponentInfoList* assertEquals (expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
	}
	
	/*
		A method to set the fields for a ScheduleTransaction in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudScheduleTransaction_setDTOFieldsForTestUpdate(ScheduleTransactionInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
		//TODO *TYPE = RichTextInfo* expected.setDescr("descr_Updated");
		expected.setScheduleRequestId("scheduleRequestId_Updated");
		expected.setExistingScheduleId("existingScheduleId_Updated");
		expected.setScheduleBatchId("scheduleBatchId_Updated");
		expected.setScheduleId("scheduleId_Updated");
		expected.setStatusMessage("statusMessage_Updated");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents_Updated");
	}
	
	/*
		A method to test the fields for a ScheduleTransaction after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudScheduleTransaction_testDTOFieldsForTestReadAfterUpdate(ScheduleTransactionInfo expected, ScheduleTransactionInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
		//TODO *TYPE = RichTextInfo* assertEquals (expected.getDescr(), actual.getDescr());
		assertEquals (expected.getScheduleRequestId(), actual.getScheduleRequestId());
		assertEquals (expected.getExistingScheduleId(), actual.getExistingScheduleId());
		assertEquals (expected.getScheduleBatchId(), actual.getScheduleBatchId());
		assertEquals (expected.getScheduleId(), actual.getScheduleId());
		assertEquals (expected.getStatusMessage(), actual.getStatusMessage());
		//TODO *TYPE = ScheduleRequestComponentInfoList* assertEquals (expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
	}
	
	/*
		A method to set the fields for a ScheduleTransaction in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudScheduleTransaction_setDTOFieldsForTestReadAfterUpdate(ScheduleTransactionInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setScheduleRequestId("scheduleRequestId_Updated");
		expected.setExistingScheduleId("existingScheduleId_Updated");
		expected.setScheduleBatchId("scheduleBatchId_Updated");
		expected.setScheduleId("scheduleId_Updated");
		expected.setStatusMessage("statusMessage_Updated");
		//TODO *TYPE = ScheduleRequestComponentInfoList* expected.setScheduleRequestComponents("scheduleRequestComponents_Updated");
	}
	
	
	// ****************************************************
	//           ScheduleRequestSetInfo
	// ****************************************************
	
	/*
		A method to set the fields for a ScheduleRequestSet in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudScheduleRequestSet_setDTOFieldsForTestCreate(ScheduleRequestSetInfo expected) 
	{
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setName("name01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("plain", "formatted"));
		List<String> ids = new ArrayList<String>();
        ids.add("refObjectIds01");
        ids.add("refObjectIds02");
        expected.setRefObjectIds(ids);
		expected.setRefObjectTypeKey("refObjectTypeKey01");
		expected.setMaxEnrollmentShared(true);
		expected.setMaximumEnrollment(10000);
	}
	
	/*
		A method to test the fields for a ScheduleRequestSet. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudScheduleRequestSet_testDTOFieldsForTestCreateUpdate(ScheduleRequestSetInfo expected, ScheduleRequestSetInfo actual) {
		assertEquals(expected.getTypeKey(), actual.getTypeKey());
		assertEquals(expected.getStateKey(), actual.getStateKey());
		assertEquals(expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
		assertEquals (expected.getRefObjectIds().size(), actual.getRefObjectIds().size());
        for(String id : expected.getRefObjectIds()) {
            assertTrue(actual.getRefObjectIds().contains(id));
        }

		assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(expected.getIsMaxEnrollmentShared(), actual.getIsMaxEnrollmentShared());
		assertEquals(expected.getMaximumEnrollment(), actual.getMaximumEnrollment());
	}
	
	/*
		A method to set the fields for a ScheduleRequestSet in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudScheduleRequestSet_setDTOFieldsForTestUpdate(ScheduleRequestSetInfo expected) 
	{
		expected.setStateKey("stateKey_Updated");
		expected.setName("name_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("plain_Updated", "formatted_Updated"));
        List<String> ids = new ArrayList<String>();
        ids.add("refObjectIds01_Updated");
        ids.add("refObjectIds02_Updated");
        expected.setRefObjectIds(ids);
		expected.setMaxEnrollmentShared(false);
		expected.setMaximumEnrollment(10001);
	}
	
	/*
		A method to test the fields for a ScheduleRequestSet after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudScheduleRequestSet_testDTOFieldsForTestReadAfterUpdate(ScheduleRequestSetInfo expected, ScheduleRequestSetInfo actual) 
	{
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getRefObjectIds().size(), actual.getRefObjectIds().size());
        for(String id : expected.getRefObjectIds()) {
            assertTrue(actual.getRefObjectIds().contains(id));
        }
        assertEquals (expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(expected.getIsMaxEnrollmentShared(), actual.getIsMaxEnrollmentShared());
        assertEquals(expected.getMaximumEnrollment(), actual.getMaximumEnrollment());
	}
	
	/*
		A method to set the fields for a ScheduleRequestSet in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudScheduleRequestSet_setDTOFieldsForTestReadAfterUpdate(ScheduleRequestSetInfo expected) 
	{
		expected.setName("name_Updated");
		expected.setRefObjectTypeKey("refObjectTypeKey_Updated");
        List<String> ids = new ArrayList<String>();
        ids.add("refObjectIds03_Updated");
        expected.setRefObjectIds(ids);
		expected.setMaxEnrollmentShared(true);
		expected.setMaximumEnrollment(5);
	}

    public void createScheduleRequestSetsForTest() throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        ScheduleRequestSetInfo reqSet = new ScheduleRequestSetInfo();
        testCrudScheduleRequestSet_setDTOFieldsForTestCreate(reqSet);
        reqSet.setId("scheduleRequestSetId01");
        getSchedulingService().createScheduleRequestSet(reqSet.getTypeKey(), reqSet.getRefObjectTypeKey(), reqSet,  getContextInfo());

        reqSet = new ScheduleRequestSetInfo();
        testCrudScheduleRequestSet_setDTOFieldsForTestCreate(reqSet);
        reqSet.setId("scheduleRequestSetId_Updated");
        getSchedulingService().createScheduleRequestSet(reqSet.getTypeKey(), reqSet.getRefObjectTypeKey(), reqSet,  getContextInfo());

        reqSet = new ScheduleRequestSetInfo();
        testCrudScheduleRequestSet_setDTOFieldsForTestCreate(reqSet);
        reqSet.setId("scheduleRequestSetId_02");
        List<String> ids = new ArrayList<String>();
        ids.add("refObjectIds03");
        ids.add("refObjectIds04");
        reqSet.setRefObjectIds(ids);
        reqSet.setRefObjectTypeKey("refObjectTypeKey02");
        getSchedulingService().createScheduleRequestSet(reqSet.getTypeKey(), reqSet.getRefObjectTypeKey(), reqSet,  getContextInfo());
    }



    // ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: searchForScheduleIds */
	@Test
	public void test_searchForScheduleIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForSchedules */
	@Test
	public void test_searchForSchedules() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateSchedule */
	@Test
	public void test_validateSchedule() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleBatchIds */
	@Test
	public void test_searchForScheduleBatchIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleBatches */
	@Test
	public void test_searchForScheduleBatches() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateScheduleBatch */
	@Test
	public void test_validateScheduleBatch() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleRequestIdsByRefObject */
	@Test
	public void test_getScheduleRequestIdsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleRequestsByRefObject */
	@Test
	public void test_getScheduleRequestsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleRequestsByRefObjects */
	@Test
	public void test_getScheduleRequestsByRefObjects() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleRequestIds */
	@Test
	public void test_searchForScheduleRequestIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleRequests */
	@Test
	public void test_searchForScheduleRequests() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateScheduleRequest */
	@Test
	public void test_validateScheduleRequest() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getTimeSlotsByDaysAndStartTime */
	@Test
	public void test_getTimeSlotsByDaysAndStartTime() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getTimeSlotsByDaysAndStartTimeAndEndTime */
	@Test
	public void test_getTimeSlotsByDaysAndStartTimeAndEndTime() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForTimeSlotIds */
	@Test
	public void test_searchForTimeSlotIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForTimeSlots */
	@Test
	public void test_searchForTimeSlots() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateTimeSlot */
	@Test
	public void test_validateTimeSlot() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: submitScheduleBatch */
	@Test
	public void test_submitScheduleBatch() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: commitSchedules */
	@Test
	public void test_commitSchedules() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getValidDaysOfWeekByTimeSlotType */
	@Test
	public void test_getValidDaysOfWeekByTimeSlotType() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleBatchesForScheduleTransaction */
	@Test
	public void test_getScheduleBatchesForScheduleTransaction() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleTransactionIdsByRefObject */
	@Test
	public void test_getScheduleTransactionIdsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleTransactionsByRefObject */
	@Test
	public void test_getScheduleTransactionsByRefObject() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleTransactionsForScheduleBatch */
	@Test
	public void test_getScheduleTransactionsForScheduleBatch() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleTransactionIds */
	@Test
	public void test_searchForScheduleTransactionIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleTransactions */
	@Test
	public void test_searchForScheduleTransactions() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateScheduleTransaction */
	@Test
	public void test_validateScheduleTransaction() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: areTimeSlotsInConflict */
	@Test
	public void test_areTimeSlotsInConflict() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleDisplays */
	@Test
	public void test_searchForScheduleDisplays() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleRequestDisplays */
	@Test
	public void test_searchForScheduleRequestDisplays() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleRequestSetIdsByRefObjType */
	@Test
	public void test_getScheduleRequestSetIdsByRefObjType()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        createScheduleRequestSetsForTest();

        List<String> list = getSchedulingService().getScheduleRequestSetIdsByRefObjType("refObjectTypeKey01", getContextInfo());
        assertEquals(2, list.size());
        assertTrue(list.contains("scheduleRequestSetId01"));
        assertTrue(list.contains("scheduleRequestSetId_Updated"));

        list = getSchedulingService().getScheduleRequestSetIdsByRefObjType("refObjectTypeKey02", getContextInfo());
        assertEquals(1, list.size());
        assertTrue(list.contains("scheduleRequestSetId_02"));
	}
	
	/* Method Name: searchForScheduleRequestSetIds */
	@Test
	public void test_searchForScheduleRequestSetIds() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: searchForScheduleRequestSets */
	@Test
	public void test_searchForScheduleRequestSets() 
	throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: validateScheduleRequestSet */
	@Test
	public void test_validateScheduleRequestSet() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
	}
	
	/* Method Name: getScheduleRequestSetsByRefObject */
	@Test
	public void test_getScheduleRequestSetsByRefObject()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        createScheduleRequestSetsForTest();

        List<ScheduleRequestSetInfo> list = getSchedulingService().getScheduleRequestSetsByRefObject("refObjectTypeKey02", "refObjectIds04", getContextInfo());
        assertEquals(1, list.size());
        assertEquals("scheduleRequestSetId_02", list.get(0).getId());
	}
	
}


