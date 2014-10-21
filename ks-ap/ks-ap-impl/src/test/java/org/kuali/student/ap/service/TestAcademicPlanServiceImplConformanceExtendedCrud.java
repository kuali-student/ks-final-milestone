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


import org.apache.commons.lang.mutable.MutableLong;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
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
        expected.setName("Test Plan Name");
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
		assertEquals (expected.getShared(), actual.getShared());
        assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a LearningPlan in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudLearningPlan_setDTOFieldsForTestUpdate(LearningPlanInfo expected) 
	{
		expected.setShared(false);
        expected.setDescr(RichTextHelper.buildRichTextInfo("My Plan updated", "<span>My Plan updated</span>"));
        expected.setName("Test Plan Name updated");
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
		assertEquals (expected.getShared(), actual.getShared());
        assertEquals (expected.getName(), actual.getName());
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
        expected.setName("Test Plan Name updated again");
	}
	
	
	// ****************************************************
	//           PlanItemInfo
	// ****************************************************
	
	/*
		A method to set the fields for a PlanItem in a 'test create' section prior to calling the 'create' operation.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestCreate(PlanItemInfo expected) 
	{
		expected.setRefObjectId("ENGL101ind");
		expected.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        List<String> planTermIds= new ArrayList<String>();
        planTermIds.add("planTermId01");
        planTermIds.add("planTermid02");
		expected.setPlanTermIds(planTermIds);
        expected.setCredit(new BigDecimal("3.10"));
		expected.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
		expected.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
		expected.setStateKey("stateKey01");
        expected.setName("item name 01");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "<span>descr01</span>"));
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
        Collections.sort(expected.getPlanTermIds());
        Collections.sort(actual.getPlanTermIds());
		assertEquals (expected.getPlanTermIds(), actual.getPlanTermIds());
        assertEquals(expected.getCredits(), actual.getCredits());
		assertEquals(expected.getCategory(), actual.getCategory());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItem in a 'test update' section prior to calling the 'update' operation.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestUpdate(PlanItemInfo expected) 
	{
		expected.setRefObjectId("CHEM131ind");
        expected.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        expected.getPlanTermIds().remove("planTermId01");
        expected.getPlanTermIds().add("planTermId03");
		expected.setCredit(new BigDecimal("4.00"));
        expected.setCategory(AcademicPlanServiceConstants.ItemCategory.BACKUP);
		expected.setStateKey("stateKeyUpdated");
        expected.setName("item name 01  updated");
		expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "<span>descr_Updated</span>"));
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
        Collections.sort(expected.getPlanTermIds());
        Collections.sort(actual.getPlanTermIds());
		assertEquals (expected.getPlanTermIds(), actual.getPlanTermIds());
		assertEquals (expected.getId(), actual.getId());
		assertEquals (expected.getCredits(), actual.getCredits());
		assertEquals (expected.getCategory(), actual.getCategory());
		assertEquals (expected.getTypeKey(), actual.getTypeKey());
		assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
		new RichTextTester().check(expected.getDescr(), actual.getDescr());
	}
	
	/*
		A method to set the fields for a PlanItem in the 'test read after update' section.
		This dto is another (second) dto object being created for other tests.
	*/
	public void testCrudPlanItem_setDTOFieldsForTestReadAfterUpdate(PlanItemInfo expected) 
	{
        expected.setRefObjectId("CHEM131ind");
        expected.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
		expected.setLearningPlanId("learningPlanId_Updated");
        expected.getPlanTermIds().add("planTermId02");
        expected.getPlanTermIds().add("planTermId03");
        expected.setCredit(new BigDecimal("4.00"));
        expected.setName("item name 01 updated again");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated2", "<span>descr_Updated2</span>"));
        expected.setCategory(AcademicPlanServiceConstants.ItemCategory.BACKUP);
	}


	// ========================================
	// SERVICE OPS NOT TESTED IN BASE TEST CLASS
	// ========================================

	/* Method Name: getPlanItemsInPlanByCategory */
	@Test
	public void test_getPlanItemsInPlanByCategory()
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}

    /* Method Name: getPlanItemsInPlanByCategories */
    @Test
    public void test_getPlanItemsByPlanTermAndCategories()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {

        String studentId = "student1";
        List<LearningPlanInfo> learningPlans;
        learningPlans = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
                studentId,
                AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN,
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        assertNotNull(learningPlans);
        assertTrue(String.format("no learning plans found for studenId=%s to test",studentId),
                learningPlans.size()>1); //"should be > 1
        int plansThatHaveItems=0;
        String useThisTerm=null;
        for (LearningPlan plan : learningPlans) {
            int categoryCount=0;
            boolean planHasMultipleCategories=false;
            HashMap<String,HashMap<AcademicPlanServiceConstants.ItemCategory,MutableLong>> termCategoryMap = new
                    HashMap<>();

            List<PlanItemInfo> items = KsapFrameworkServiceLocator.getAcademicPlanService()
                    .getPlanItemsInPlan(plan.getId(),KsapFrameworkServiceLocator.getContext().getContextInfo());
            //Look for non-empty learning plans
            if (items.size()>0)
                ++plansThatHaveItems;

            //Look for and count distinct items per categor per term
            if (items.size()>1) {
                for (PlanItemInfo item : items) {
                    for (String term : item.getPlanTermIds()) {
                        if (!termCategoryMap.containsKey(term)) {
                            termCategoryMap.put(term,new HashMap<AcademicPlanServiceConstants.ItemCategory,MutableLong>());
                        }
                        if (!termCategoryMap.get(term).containsKey(item.getCategory())) {
                            termCategoryMap.get(term).put(item.getCategory(),new MutableLong(0L));
                        }
                        termCategoryMap.get(term).get(item.getCategory()).add(1L);
                    }
                }
            }
            //No look for terms that use more that one category
            for (String term : termCategoryMap.keySet()) {
                if (useThisTerm==null && termCategoryMap.get(term).size()>1 ) {
                        planHasMultipleCategories=true;
                        useThisTerm=term;
                        continue;
                }
            }

            //No check that  getPlanItemsByPlanTermAndCategories returns the appropriate # of items
            //for categories
            if (planHasMultipleCategories) {
                int totalItemsPerTermSoFar=0;
                List<AcademicPlanServiceConstants.ItemCategory> categories = new ArrayList<>();
                //Get items for 1st category of term, then 1st+2nd categories then 1st+2nd+3rd categories...
                // .... & check count ea. time
                for (AcademicPlanServiceConstants.ItemCategory category : termCategoryMap.get(useThisTerm).keySet()) {
                    totalItemsPerTermSoFar+=termCategoryMap.get(useThisTerm).get(category).longValue(); //sum counts
                    categories.add(category);
                    List<PlanItemInfo> itemsSoFar = getAcademicPlanService().
                            getPlanItemsByPlanTermAndCategories(plan.getId(), useThisTerm, categories, contextInfo);

                    //compare sum of map counts to items returned b getPlanItemsByPlanTermAndCategories
                    assertEquals("wrong item count returned by getPlanItemsByPlanTermAndCategories",
                            totalItemsPerTermSoFar,itemsSoFar.size());
                }
            }
        }
        assertTrue(String.format("this test expects multiple plans to exist for student %s",studentId),
                plansThatHaveItems > 1);
        assertTrue("this test expects a learning plan having items in more than one category in a term",
            useThisTerm!=null);
    }

    /* Method Name: getPlanItemsInPlan */
	@Test
	public void test_getPlanItemsInPlan() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInPlanByTermIdByCategory */
	@Test
	public void test_getPlanItemsInPlanByAtp() 
	throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
	}
	
	/* Method Name: getPlanItemsInPlanByRefObjectIdByRefObjectType */
	@Test
	public void test_getPlanItemsInPlanByRefObjectIdByRefObjectType()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
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
	
}


