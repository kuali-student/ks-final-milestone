package org.kuali.student.ap.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.service.mock.AcademicPlanServiceMockImpl;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.course.service.impl.CourseServiceMapImpl;
import org.kuali.student.r2.lum.lu.service.impl.CluServiceMockImpl;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context2.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class AcademicPlanServiceImplTest extends TestAcademicPlanServiceImplConformanceExtendedCrud {

    /**
     *  This subclass is intended to Run AcademicPlanService Plan & Item Entity CRUD tests defined in generated
     *  class pair:
     *      TestAcademicPlanServiceImplConformanceBaseCrud (defines CRUD test methods)
     *      TestAcademicPlanServiceImplConformanceExtendedCrud (defines methods to set & test entity values
     */

    private final AcademicPlanServiceTstHelper testHelper = new AcademicPlanServiceTstHelper();

    @Before
    public void setUp() throws Exception {
        DefaultKsapContext.before("student1");
        contextInfo=KsapFrameworkServiceLocator.getContext().getContextInfo();
        testService= KsapFrameworkServiceLocator.getAcademicPlanService();
        principalId = "student1";
        contextInfo.setPrincipalId(principalId);

        testHelper.createKsapTypes();
        testHelper.createMockCourses();

//        testServiceNoValidator=testService;
//        while (testServiceNoValidator!=null &&
//                !(testServiceNoValidator instanceof AcademicPlanServiceImpl
//                        ||testServiceNoValidator instanceof AcademicPlanServiceMockImpl)) {
//            testServiceNoValidator = ((AcademicPlanServiceDecorator)testService).getNextDecorator();
//        }

        contextInfo.setPrincipalId(principalId); //reset context principalId...after loading plans

    }

    @After
	public void tearDown() {
        ((CourseServiceMapImpl)KsapFrameworkServiceLocator.getCourseService()).clear();
		DefaultKsapContext.after();
	}

	@Test(expected = DoesNotExistException.class)
	public void getUnknownLearningPlan()
            throws InvalidParameterException,
                   MissingParameterException, DoesNotExistException,
                   OperationFailedException, PermissionDeniedException {
		KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlan("unknown_plan",
				KsapFrameworkServiceLocator.getContext().getContextInfo());
	}

    /**
     * this tests that the plan type validation checks for both totally bogus types
     * (e.g. 'this.is.a.totally.bugs.type.qzsdfasdf'), and valid types that are not valid
     * plan types (e.g. AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE  ...which is a valid type
     * for a plan item, but not for the plan itself)
     *
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     * @throws VersionMismatchException
     * @throws DependentObjectsExistException
     * @throws AlreadyExistsException
     */
    @Test
    public void testCreateLearningPlanWithInvalidType()
            throws DataValidationErrorException,
                   DoesNotExistException,
                   MissingParameterException,
                   OperationFailedException,
                   PermissionDeniedException,
                   ReadOnlyException,
                   VersionMismatchException,
                   DependentObjectsExistException, AlreadyExistsException {
        // -------------------------------------
        // test create
        // -------------------------------------
        LearningPlanInfo original = new LearningPlanInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudLearningPlan_setDTOFieldsForTestCreate (original);
        new AttributeTester().add2ForCreate(original.getAttributes());

        LearningPlanInfo expected = new LearningPlanInfo (original);

        String exceptionMessageRegExpr = "Invalid (plan )*type.*";

        //Create a learning plan with a totally invalid type
        String invalidPlanType = "this.is.a.totally.bugs.type.qzsdfasdf";
        expected.setTypeKey(invalidPlanType);
        boolean exception = false;
        try {
            testService.createLearningPlan ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: " + e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception=true;
        }
        Assert.assertTrue("Invalid plan type ["+invalidPlanType+"] was not detected!",exception);

        //Create a learning plan with a valid, but wrong type
        invalidPlanType = AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE;
        expected.setTypeKey(invalidPlanType);
        exception = false;
        try {
            testService.createLearningPlan ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid plan type ["+invalidPlanType+"] was not detected!",exception);

        //Finally Create a learning plan with a valid/correct type...just to make sure it passes
        String validPlanType = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
        expected.setTypeKey(validPlanType);
        exception = false;
        try {
            testService.createLearningPlan ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            exception = true;
        }
        Assert.assertFalse("create plan w/ a valid plan type ["+validPlanType+"] failed!",
                exception);
    }

    /**
     * this test verifie that item validation checks for both totally bogus types
     * (e.g. 'this.is.a.totally.bugs.type.qzsdfasdf'), and valid types that are not valid
     * plan types (e.g. AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN  ...which is a valid type
     * for a plan, but not for a plan item)
     *
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     * @throws VersionMismatchException
     * @throws DependentObjectsExistException
     * @throws AlreadyExistsException
     */
    @Test
    public void testCreatePlanItemTypeValidation()
            throws DataValidationErrorException,
                   DoesNotExistException,
                   InvalidParameterException,
                   MissingParameterException,
                   OperationFailedException,
                   PermissionDeniedException,
                   ReadOnlyException,
                   VersionMismatchException,
                   DependentObjectsExistException, AlreadyExistsException {

        LearningPlanInfo learningPlan = new LearningPlanInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudLearningPlan_setDTOFieldsForTestCreate(learningPlan);
        new AttributeTester().add2ForCreate(learningPlan.getAttributes());

        // code to create actual
        learningPlan = testService.createLearningPlan(learningPlan, contextInfo);


        // -------------------------------------
        // test create plan item with invalid types
        // -------------------------------------
        PlanItemInfo planItem = new PlanItemInfo ();
        planItem.setLearningPlanId(learningPlan.getId());

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPlanItem_setDTOFieldsForTestCreate(planItem);
        new AttributeTester().add2ForCreate(planItem.getAttributes());
        PlanItemInfo expected = new PlanItemInfo (planItem);

        String exceptionMessageRegExpr = "Invalid (plan |item |item reference object )*type.*";

        //Create a plan item with a totally invalid type
        String invalidObjRefType = "this.is.a.totally.bugs.type.qzsdfasdf";
        expected.setRefObjectType(invalidObjRefType);
        boolean exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid item reference object type ["+invalidObjRefType+"] was not detected!",exception);

        //Create a plan item with a valid, but wrong type
        invalidObjRefType = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
        expected.setRefObjectType(invalidObjRefType);
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid item reference object type ["+invalidObjRefType+"] was not detected!",exception);

        //Create a plan item with a totally invalid type
        String invalidItemType = "this.is.a.totally.bugus.type.qzsdfasdf";
        expected.setTypeKey(invalidItemType);
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid item type ["+invalidItemType+"] was not detected!",exception);

        //Create a plan item with a valid, but wrong type
        invalidItemType = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;
        expected.setTypeKey(invalidItemType);
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid item type ["+invalidItemType+"] was not detected!",exception);

        //Create a plan item with a valid, but wrong ref-obj-type
        String validItemType = AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE;
        String inValidObjRefType = "bad.ref.object.type.zzz";
        expected.setTypeKey(validItemType);
        expected.setRefObjectType(inValidObjRefType);
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            Assert.assertTrue("unexpected exception: "+e.getMessage(),
                    e.getMessage().matches(exceptionMessageRegExpr));
            exception = true;
        }
        Assert.assertTrue("Invalid re-object type ["+inValidObjRefType+"] was not detected!",exception);

        //Finally....Create a plan item with a valid/correct ref-object type...just to make sure it passes ok
        validItemType = AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE;
        String validObjRefType = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;
        expected.setTypeKey(validItemType);
        expected.setRefObjectType(validObjRefType);
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            exception = true;
        }
        Assert.assertFalse("Valid item type ["+inValidObjRefType+"] and/or reference object type [" +
                validObjRefType +"] not properly recognized!",exception);

        //Test addding a Registration Group item type to the plan
        testCrudPlanItem_setDTOFieldsForTestCreate(planItem);
        new AttributeTester().add2ForCreate(planItem.getAttributes());
        expected = new PlanItemInfo (planItem);

        validItemType = AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE;
        validObjRefType = LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY;
        expected.setTypeKey(validItemType);
        expected.setRefObjectType(validObjRefType);
        expected.setRefObjectId("RG-1");  //created in AcademicPlanServiceTstHelper.java
        exception = false;
        try {
            testService.createPlanItem ( expected, contextInfo);
        } catch (InvalidParameterException e) {
            exception = true;
        }
        Assert.assertFalse("Valid item type [" + validItemType + "] was not properly recognized!", exception);
    }


	@Test
	public void test_getLearningPlansForStudentByType() throws Throwable {
		String studentId = "student1";
		List<LearningPlanInfo> learningPlans;
		learningPlans = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
				studentId,
				AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN,
				KsapFrameworkServiceLocator.getContext().getContextInfo());
		assertNotNull(learningPlans);
		assertEquals(2, learningPlans.size());

		LearningPlan lp = learningPlans.get(0);
		assertEquals("lp1", lp.getId());

		lp = learningPlans.get(1);
		assertEquals("lp2", lp.getId());
	}

    @Override
	@Test
	public void test_getPlanItemsInPlanByRefObjectIdByRefObjectType()
            throws InvalidParameterException, MissingParameterException,
                   DoesNotExistException, OperationFailedException, PermissionDeniedException {

		String planId = "lp1";
		String refObjectId = "006476b5-18d8-4830-bbb6-2bb9e79600fb";
		String refObjectType = "kuali.lu.type.CreditCourse";

		List<PlanItemInfo> planItems = KsapFrameworkServiceLocator.getAcademicPlanService()
				.getPlanItemsInPlanByRefObjectIdByRefObjectType(planId,
						refObjectId, refObjectType, KsapFrameworkServiceLocator
								.getContext().getContextInfo());
		assertEquals(1, planItems.size());
		assertEquals(planId, planItems.get(0).getLearningPlanId());
		assertEquals(refObjectId, planItems.get(0).getRefObjectId());
		assertEquals(refObjectType, planItems.get(0).getRefObjectType());
	}

	@Test
	public void updatePlanItemPlannedCoursePlanTermIds() throws Throwable {

		String planId = "lp1";

		// Create a new plan item.
		PlanItemInfo planItemInfo = new PlanItemInfo();

		RichTextInfo desc = new RichTextInfo();
		String formattedDesc = "<span>My Comment</span>";
		String planDesc = "My Comment";
		desc.setFormatted(formattedDesc);
		desc.setPlain(planDesc);
		planItemInfo.setDescr(desc);
		planItemInfo.setMeta(new MetaInfo());

		planItemInfo.setLearningPlanId(planId);

		// Set some ATP info since this is a planned course.
		List<String> planTermIds = new ArrayList<String>();
		planTermIds.add("20111");
		planTermIds.add("20114");
		planItemInfo.setPlanTermIds(planTermIds);

		String courseId = "ENGL101ind";
		String courseType = CLUConstants.CLU_TYPE_CREDIT_COURSE;

		planItemInfo.setRefObjectId(courseId);
		planItemInfo.setRefObjectType(courseType);

        planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        AcademicPlanServiceConstants.ItemCategory category = AcademicPlanServiceConstants.ItemCategory.PLANNED;
        planItemInfo.setCategory(category);
		planItemInfo
				.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

		// Save the plan item
		PlanItemInfo newPlanItem = testService.createPlanItem(
				planItemInfo, KsapFrameworkServiceLocator.getContext()
						.getContextInfo());
		String planItemId = newPlanItem.getId();

		// Verify the object returned by getPlanItem().
		PlanItemInfo fetchedPlanItem = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItem(
				planItemId, KsapFrameworkServiceLocator.getContext()
						.getContextInfo());

		assertNotNull(fetchedPlanItem);
		assertEquals(planItemId, fetchedPlanItem.getId());
		assertEquals(planId, fetchedPlanItem.getLearningPlanId());
		assertEquals(courseId, fetchedPlanItem.getRefObjectId());
		assertEquals(courseType, fetchedPlanItem.getRefObjectType());
        assertEquals(planItemInfo.getCategory(), fetchedPlanItem.getCategory());
		assertEquals(planItemInfo.getTypeKey(), fetchedPlanItem.getTypeKey());
		assertEquals(planItemInfo.getStateKey(), fetchedPlanItem.getStateKey());
		assertEquals(2, fetchedPlanItem.getPlanTermIds().size());
		assertEquals("student1", fetchedPlanItem.getMeta().getUpdateId());
		assertNotNull(fetchedPlanItem.getMeta().getUpdateTime());

		// Save meta data info.
		Date originalUpdateDate = newPlanItem.getMeta().getUpdateTime();

		// Update the plan item and save.
		fetchedPlanItem.getPlanTermIds().remove("20111");
		assertEquals(1, fetchedPlanItem.getPlanTermIds().size());

        // NOTE: the following stmt is required to change the date in contextInfo...otherwise it is always the same
        //        for ea transaction  (it is used to set the updateDateTime)
        Thread.sleep(5);
        KsapFrameworkServiceLocator.getContext().getContextInfo().setCurrentDate(new Date());

        PlanItemInfo updatedPlanItem = testService.updatePlanItem(
				planItemId, fetchedPlanItem, KsapFrameworkServiceLocator
						.getContext().getContextInfo());

		assertNotNull(updatedPlanItem);
		assertEquals(planItemId, updatedPlanItem.getId());
		assertEquals(planId, updatedPlanItem.getLearningPlanId());
		assertEquals(formattedDesc, updatedPlanItem.getDescr().getFormatted());
		assertEquals(planDesc, updatedPlanItem.getDescr().getPlain());
		assertEquals(courseId, updatedPlanItem.getRefObjectId());
		assertEquals(courseType, updatedPlanItem.getRefObjectType());
        assertEquals(category, updatedPlanItem.getCategory());
		assertEquals(1, updatedPlanItem.getPlanTermIds().size());
		assertTrue(updatedPlanItem.getPlanTermIds().contains("20114"));
		assertFalse(originalUpdateDate.equals(updatedPlanItem.getMeta()
				.getUpdateTime()));
	}

}
