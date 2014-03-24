/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ap.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemSetInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public abstract class TestAcademicPlanServiceImplConformanceExtendedCrud extends TestAcademicPlanServiceImplConformanceBaseCrud
{
	
	// ========================================
	// DTO FIELD SPECIFIC METHODS
	// ========================================
	
	// ****************************************************
	//           LearningPlanInfo
	// ****************************************************
	
	/*
		A method to set the fields for a LearningPlan in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudLearningPlan_setDTOFieldsForTestCreate(LearningPlanInfo expected) 
	{
        expected.setStudentId("student1");
        expected.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN);
        expected.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ACTIVE_STATE_KEY);
        expected.setDescr(RichTextHelper.buildRichTextInfo("My Plan", "<span>My Plan</span>"));
        expected.setMeta(new MetaInfo());
        expected.setShared(false);
	}
	
	/*
		A method to test the fields for a LearningPlan. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudLearningPlan_testDTOFieldsForTestCreateUpdate(LearningPlanInfo expected, LearningPlanInfo actual) 
	{
		assertEquals (expected.getStudentId(), actual.getStudentId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.isShared(), actual.isShared());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a LearningPlan in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudLearningPlan_setDTOFieldsForTestUpdate(LearningPlanInfo expected) 
	{
		expected.setShared(false);
        expected.setDescr(RichTextHelper.buildRichTextInfo("My Plan updated", "<span>My Plan updated</span>"));
	}
	
	/*
		A method to test the fields for a LearningPlan after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudLearningPlan_testDTOFieldsForTestReadAfterUpdate(LearningPlanInfo expected, LearningPlanInfo actual) 
	{
		assertEquals (expected.getStudentId(), actual.getStudentId());
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		assertEquals (expected.isShared(), actual.isShared());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a LearningPlan in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudLearningPlan_setDTOFieldsForTestReadAfterUpdate(LearningPlanInfo expected) 
	{
        expected.setShared(true);
        expected.setDescr(RichTextHelper.buildRichTextInfo("My Plan updated again", "<span>My Plan updated again</span>"));
	}
	
	
	// ****************************************************
	//           PlanItemInfo
	// ****************************************************
	
	/*
		A method to set the fields for a PlanItem in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestCreate(PlanItemInfo expected) 
	{
		expected.setRefObjectId("refObjectId01");
		expected.setRefObjectType("refObjectType01");
        List<String> planPeriods= new ArrayList<String>();
        planPeriods.add("planPeriods01");
        planPeriods.add("planPeriods02");
		expected.setPlanPeriods(planPeriods);
        expected.setCredit(new BigDecimal("3.10"));
		expected.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
		expected.setTypeKey("typeKey01");
		expected.setStateKey("stateKey01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
	}
	
	/*
		A method to test the fields for a PlanItem. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudPlanItem_testDTOFieldsForTestCreateUpdate(PlanItemInfo expected, PlanItemInfo actual) 
	{
		assertEquals (expected.getRefObjectId(), actual.getRefObjectId());
		assertEquals (expected.getRefObjectType(), actual.getRefObjectType());
		assertEquals (expected.getLearningPlanId(), actual.getLearningPlanId());
        Collections.sort(expected.getPlanPeriods());
        Collections.sort(actual.getPlanPeriods());
		assertEquals (expected.getPlanPeriods(), actual.getPlanPeriods());
        assertEquals(expected.getCredit(), actual.getCredit());
		assertEquals(expected.getCategory(), actual.getCategory());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItem in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestUpdate(PlanItemInfo expected) 
	{
		expected.setRefObjectId("refObjectId_Updated");
		expected.setRefObjectType("refObjectType_Updated");
        expected.getPlanPeriods().remove("planPeriods01");
        expected.getPlanPeriods().add("planPeriods03");
		expected.setCredit(new BigDecimal("4.10"));
        expected.setCategory(AcademicPlanServiceConstants.ItemCategory.BACKUP);
		expected.setStateKey("stateKey_Updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
	}
	
	/*
		A method to test the fields for a PlanItem after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudPlanItem_testDTOFieldsForTestReadAfterUpdate(PlanItemInfo expected, PlanItemInfo actual) 
	{
		assertEquals (expected.getRefObjectId(), actual.getRefObjectId());
		assertEquals (expected.getRefObjectType(), actual.getRefObjectType());
		assertEquals (expected.getLearningPlanId(), actual.getLearningPlanId());
        Collections.sort(expected.getPlanPeriods());
        Collections.sort(actual.getPlanPeriods());
		assertEquals (expected.getPlanPeriods(), actual.getPlanPeriods());
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getCredit(), actual.getCredit());
		assertEquals (expected.getCategory(), actual.getCategory());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItem in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestReadAfterUpdate(PlanItemInfo expected) 
	{
		expected.setRefObjectId("refObjectId_Updated");
		expected.setRefObjectType("refObjectType_Updated");
		expected.setLearningPlanId("learningPlanId_Updated");
        expected.getPlanPeriods().add("planPeriods02");
        expected.getPlanPeriods().add("planPeriods03");
        expected.setCredit(new BigDecimal("4.10"));
        expected.setCategory(AcademicPlanServiceConstants.ItemCategory.BACKUP);
	}
	
	
	// ****************************************************
	//           PlanItemSetInfo
	// ****************************************************
	
	/*
		A method to set the fields for a PlanItemSet in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudPlanItemSet_setDTOFieldsForTestCreate(PlanItemSetInfo expected) 
	{
//NOTE: we are not using PlanItemSets yet...(perhaps will be in future work pulled in from myPlan)
//		>>>fix: *TYPE = StringList* expected.setPlanItemIds("planItemIds01");
//		>>>fix: *TYPE = Integer* expected.setInterestedInItemsCount("interestedInItemsCount01");
//		>>>fix: *TYPE = boolean* expected.setInterestedInAllItems("interestedInAllItems01");
        
//		expected.setTypeKey("typeKey01");
//		expected.setStateKey("stateKey01");
//		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
	}
	
	/*
		A method to test the fields for a PlanItemSet. This is called after:
		- creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
		- reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
		- updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
	*/
	public void testCrudPlanItemSet_testDTOFieldsForTestCreateUpdate(PlanItemSetInfo expected, PlanItemSetInfo actual) 
	{
//NOTE: we are not using PlanItemSets yet...(perhaps will be in future work pulled in from myPlan)
//		>>>fix: *TYPE = StringList* assertEquals (expected.getPlanItemIds(), actual.getPlanItemIds());
//		>>>fix: *TYPE = Integer* assertEquals (expected.getInterestedInItemsCount(), actual.getInterestedInItemsCount());
//		>>>fix: *TYPE = boolean* assertEquals (expected.isInterestedInAllItems(), actual.isInterestedInAllItems());
        
//		assertEquals (expected.getTypeKey(), actual.getTypeKey());
//		assertEquals (expected.getStateKey(), actual.getStateKey());
//		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItemSet in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudPlanItemSet_setDTOFieldsForTestUpdate(PlanItemSetInfo expected) 
	{
//NOTE: we are not using PlanItemSets yet...(perhaps will be in future work pulled in from myPlan)
//		>>>fix: *TYPE = StringList* expected.setPlanItemIds("planItemIds_Updated");
//		>>>fix: *TYPE = Integer* expected.setInterestedInItemsCount("interestedInItemsCount_Updated");
//		>>>fix: *TYPE = boolean* expected.setInterestedInAllItems("interestedInAllItems_Updated");
        
//		expected.setTypeKey("typeKey_Updated");
//		expected.setStateKey("stateKey_Updated");
//		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
	}
	
	/*
		A method to test the fields for a PlanItemSet after an update operation, followed by a read operation,
		where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
	*/
	public void testCrudPlanItemSet_testDTOFieldsForTestReadAfterUpdate(PlanItemSetInfo expected, PlanItemSetInfo actual) 
	{
//NOTE: we are not using PlanItemSets yet...(perhaps will be in future work pulled in from myPlan)
//		>>>fix: *TYPE = StringList* assertEquals (expected.getPlanItemIds(), actual.getPlanItemIds());
//		>>>fix: *TYPE = Integer* assertEquals (expected.getInterestedInItemsCount(), actual.getInterestedInItemsCount());
//		>>>fix: *TYPE = boolean* assertEquals (expected.isInterestedInAllItems(), actual.isInterestedInAllItems());
//		assertEquals (expected.getId(), actual.getId());
//		assertEquals (expected.getTypeKey(), actual.getTypeKey());
//		assertEquals (expected.getStateKey(), actual.getStateKey());
//		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItemSet in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudPlanItemSet_setDTOFieldsForTestReadAfterUpdate(PlanItemSetInfo expected) 
	{
//NOTE: we are not using PlanItemSets yet...(perhaps will be in future work pulled in from myPlan)
//		>>>fix: *TYPE = StringList* expected.setPlanItemIds("planItemIds_Updated");
//		>>>fix: *TYPE = Integer* expected.setInterestedInItemsCount("interestedInItemsCount_Updated");
//		>>>fix: *TYPE = boolean* expected.setInterestedInAllItems("interestedInAllItems_Updated");
	}
	
	
	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================
	
	/* Method Name: getPlanItemsInPlanByCategory */
	@Test
	public void test_getPlanItemsInPlanByCategory() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInPlan */
	@Test
	public void test_getPlanItemsInPlan() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInPlanByAtp */
	@Test
	public void test_getPlanItemsInPlanByAtp() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInPlanByRefObjectIdByRefObjectType */
	@Test
	public void test_getPlanItemsInPlanByRefObjectIdByRefObjectType() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInSet */
	@Test
	public void test_getPlanItemsInSet() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: validateLearningPlan */
	@Test
	public void test_validateLearningPlan() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: validatePlanItem */
	@Test
	public void test_validatePlanItem() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,AlreadyExistsException {
	}
	
	/* Method Name: validatePlanItemSet */
	@Test
	public void test_validatePlanItemSet() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
}


