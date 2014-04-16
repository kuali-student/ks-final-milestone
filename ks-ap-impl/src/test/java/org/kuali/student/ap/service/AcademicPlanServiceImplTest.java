package org.kuali.student.ap.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceImpl;
import org.kuali.student.ap.academicplan.service.mock.AcademicPlanServiceMockImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
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

        testServiceNoValidator=testService;
        while (testServiceNoValidator!=null &&
                !(testServiceNoValidator instanceof AcademicPlanServiceImpl
                        ||testServiceNoValidator instanceof AcademicPlanServiceMockImpl)) {
            testServiceNoValidator = ((AcademicPlanServiceDecorator)testService).getNextDecorator();
        }

        contextInfo.setPrincipalId(principalId); //reset context principalId...after loading plans

//TODO:  KSAP-1016 - Setup Course MapImpl to sppt KSAP service tests
//        this is a clu loader helper that I found, but it may not be what we need
//        we also need tp inject a Course / CLU mapimpl..
//
//        CluDataLoader cluDataLoader = new CluDataLoader();
//        cluDataLoader.setCluService(KsapFrameworkServiceLocator.getCluService());
//        cluDataLoader.setContextInfo(new DefaultKsapContext().getContextInfo());
//        cluDataLoader.load();
//
//        TermAndCalDataLoader termAndCalDataLoader = new TermAndCalDataLoader();
//        termAndCalDataLoader.loadData();
    }

    @After
	public void tearDown() {
		DefaultKsapContext.after();
	}

	@Test(expected = DoesNotExistException.class)
	public void getUnknownLearningPlan() throws InvalidParameterException,
			MissingParameterException, DoesNotExistException,
			OperationFailedException {
		KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlan("unknown_plan",
				KsapFrameworkServiceLocator.getContext().getContextInfo());
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
			DoesNotExistException, OperationFailedException {

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

		String courseId = "c796aecc-7234-4482-993c-bf00b8088e84";
		String courseType = CLUConstants.CLU_TYPE_CREDIT_COURSE;

		planItemInfo.setRefObjectId(courseId);
		planItemInfo.setRefObjectType(courseType);

        planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        AcademicPlanServiceConstants.ItemCategory category = AcademicPlanServiceConstants.ItemCategory.PLANNED;
        planItemInfo.setCategory(category);
		planItemInfo
				.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

		// Save the plan item
		PlanItemInfo newPlanItem = testServiceNoValidator.createPlanItem(
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

        PlanItemInfo updatedPlanItem = testServiceNoValidator.updatePlanItem(
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

	@Test
	public void addPlanItemNullCourseType() throws Throwable {
		String planId = "lp1";

		PlanItemInfo planItem = new PlanItemInfo();

		RichTextInfo desc = new RichTextInfo();
		String formattedDesc = "<span>My Comment</span>";
		String planDesc = "My Comment";
		desc.setFormatted(formattedDesc);
		desc.setPlain(planDesc);
		planItem.setDescr(desc);

		planItem.setLearningPlanId(planId);
        planItem.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItem.setCategory(AcademicPlanServiceConstants.ItemCategory.WISHLIST);
		String courseId = "c796aecc-7234-4482-993c-bf00b8088e84";
		String courseType = null;

		planItem.setRefObjectId(courseId);
		planItem.setRefObjectType(courseType);
		planItem.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

        try {
            KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItem,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            fail("DataValidationErrorException should have been thrown");
        } catch (DataValidationErrorException dvee) {
            dvee.printStackTrace();
            assertTrue("validation messages should not be empty", !dvee.getValidationResults().isEmpty());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("refObjectType", resultInfo.getElement());
            assertEquals("error.required", resultInfo.getMessage());
        }
    }

	@Test
	public void addPlanItemNullLearningPlan() throws Throwable {
		String planId = null;

		PlanItemInfo planItem = new PlanItemInfo();

		RichTextInfo desc = new RichTextInfo();
		String formattedDesc = "<span>My Comment</span>";
		String planDesc = "My Comment";
		desc.setFormatted(formattedDesc);
		desc.setPlain(planDesc);
		planItem.setDescr(desc);

		planItem.setLearningPlanId(planId);
        planItem.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItem.setCategory(AcademicPlanServiceConstants.ItemCategory.WISHLIST);

		String courseId = "c796aecc-7234-4482-993c-bf00b8088e84";
		String courseType = CLUConstants.CLU_TYPE_CREDIT_COURSE;

		planItem.setRefObjectId(courseId);
		planItem.setRefObjectType(courseType);
		planItem.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

		try {
			KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItem,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
            fail("InvalidParameterException should have been thrown as learning plan id was null");
        } catch (DataValidationErrorException dvee) {
            dvee.printStackTrace();
            assertTrue("validation messages should not be empty", !dvee.getValidationResults().isEmpty());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertEquals("learningPlanId", resultInfo.getElement());
            assertEquals("error.required", resultInfo.getMessage());
        }
	}

	@Test
	public void addPlanItemNullCourseId() throws Exception {
		String planId = "lp1";

		PlanItemInfo planItem = new PlanItemInfo();

		RichTextInfo desc = new RichTextInfo();
		String formattedDesc = "<span>My Comment</span>";
		String planDesc = "My Comment";
		desc.setFormatted(formattedDesc);
		desc.setPlain(planDesc);
		planItem.setDescr(desc);

		planItem.setLearningPlanId(planId);
        planItem.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
		planItem.setCategory(AcademicPlanServiceConstants.ItemCategory.WISHLIST);
		String courseId = null;
		String courseType = CLUConstants.CLU_TYPE_CREDIT_COURSE;

		planItem.setRefObjectId(courseId);
		planItem.setRefObjectType(courseType);
		planItem.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

        try {
            KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItem,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            fail("DataValidationErrorException should have been thrown");
        } catch (DataValidationErrorException dvee) {
            dvee.printStackTrace();
            assertEquals("Error(s) validating plan item.",dvee.getMessage());
            ValidationResultInfo resultInfo = dvee.getValidationResults().get(0);
            assertTrue("validation results should not be empty", !dvee.getValidationResults().isEmpty());
            assertEquals("refObjectId", resultInfo.getElement());
            assertEquals("error.required", resultInfo.getMessage());
        }
    }

	@Test
	public void validatePlanItemForCourse() throws InvalidParameterException,
			MissingParameterException, AlreadyExistsException,
			DoesNotExistException, OperationFailedException {

        String planId = "lp1";

        PlanItemInfo planItem = new PlanItemInfo();

        RichTextInfo desc = new RichTextInfo();
        String formattedDesc = "<span>My Comment</span>";
        String planDesc = "My Comment";
        desc.setFormatted(formattedDesc);
        desc.setPlain(planDesc);
        planItem.setDescr(desc);

        planItem.setLearningPlanId(planId);

//        String courseId = "c796aecc-7234-4482-993c-bf00b8088e84";
//        String courseType = CLUConstants.CLU_TYPE_CREDIT_COURSE;

        planItem.setRefObjectId("XX");
        planItem.setRefObjectType(CLUConstants.CLU_TYPE_CREDIT_COURSE);
        planItem.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItem.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        planItem.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
		List<ValidationResultInfo> validationResultInfos;
        validationResultInfos = KsapFrameworkServiceLocator.getAcademicPlanService().validatePlanItem(
                "FULL_VALIDATION", planItem,
                KsapFrameworkServiceLocator.getContext().getContextInfo());
        assertTrue("validationResultsInfos should not be empty", !validationResultInfos.isEmpty());
        assertEquals("Could not find course with ID [XX].",validationResultInfos.get(0).getMessage());
	}

	@Test
	public void validatePlanItemForPlannedItem() throws Throwable {
		PlanItemInfo planItemInfo = new PlanItemInfo();
        planItemInfo.setLearningPlanId("lp1");
		planItemInfo.setRefObjectId("XX");
        planItemInfo.setRefObjectType(CLUConstants.CLU_TYPE_CREDIT_COURSE);
        planItemInfo.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItemInfo.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
		List<ValidationResultInfo> validationResultInfos = KsapFrameworkServiceLocator.getAcademicPlanService()
				.validatePlanItem("FULL_VALIDATION", planItemInfo,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
		assertEquals("Could not find course with ID [XX].",
				validationResultInfos.get(0).getMessage());
		assertEquals("refObjectId", validationResultInfos.get(0).getElement());
		assertEquals(
				"Plan Item category was ["+AcademicPlanServiceConstants.ItemCategory.PLANNED+"], " +
                        "but no plan terms were defined.",
				validationResultInfos.get(1).getMessage());
		assertEquals("category", validationResultInfos.get(1).getElement());
	}

	@Test
	public void validatePlanItemForBackupPlanItem()
			throws InvalidParameterException, MissingParameterException,
			AlreadyExistsException, DoesNotExistException,
			OperationFailedException {
		PlanItemInfo planItemInfo = new PlanItemInfo();
        planItemInfo.setLearningPlanId("lp1");
        planItemInfo.setRefObjectId("XX");
        planItemInfo.setRefObjectType(CLUConstants.CLU_TYPE_CREDIT_COURSE);
        planItemInfo.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItemInfo.setCategory(AcademicPlanServiceConstants.ItemCategory.BACKUP);
		List<ValidationResultInfo> validationResultInfos = KsapFrameworkServiceLocator
				.getAcademicPlanService()
				.validatePlanItem("FULL_VALIDATION", planItemInfo,
						KsapFrameworkServiceLocator.getContext()
								.getContextInfo());
		assertEquals("Could not find course with ID [XX].",
				validationResultInfos.get(0).getMessage());
		assertEquals("refObjectId", validationResultInfos.get(0).getElement());
		assertEquals(
				"Plan Item category was ["+ AcademicPlanServiceConstants.ItemCategory.BACKUP +"], " +
                        "but no plan terms were defined.",
				validationResultInfos.get(1).getMessage());
		assertEquals("category", validationResultInfos.get(1).getElement());
	}
}
