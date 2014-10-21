package org.kuali.student.ap.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceDecorator;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.service.mock.AcademicPlanServiceMockImpl;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.course.service.impl.CourseServiceMapImpl;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Services tests against Map based impl
 *  (...we don't need to test the CRUD (jpa-mapping level) for these tests)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-map-mock-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class AcademicPlanServiceMapMockImplTest extends TestAcademicPlanServiceImplConformanceExtendedCrud {


    private final AcademicPlanServiceTstHelper testHelper = new AcademicPlanServiceTstHelper();
    private AcademicPlanService nonDecoratedMockService;

    @Before
    public void setUp() throws Exception {
        DefaultKsapContext.before("student1");
        contextInfo=KsapFrameworkServiceLocator.getContext().getContextInfo();
        testService= KsapFrameworkServiceLocator.getAcademicPlanService();
        principalId = "student1";
        contextInfo.setPrincipalId(principalId);

        testHelper.createKsapTypes();
        testHelper.createMockCourses();



        nonDecoratedMockService =testService;
        while (nonDecoratedMockService !=null &&
                !(nonDecoratedMockService instanceof AcademicPlanServiceMockImpl)) {
            nonDecoratedMockService = ((AcademicPlanServiceDecorator)testService).getNextDecorator();
        }

        //Load Test Plans/Items
        AcademicPlanDataLoader loader = new AcademicPlanDataLoader();
        loader.setContextInfo(contextInfo);
        loader.setPlanService(testService);
        loader.load();

        contextInfo.setPrincipalId(principalId); //reset context principalId...after loading plans
    }

    @After
    public void tearDown() {
        ((AcademicPlanServiceMockImpl) nonDecoratedMockService).clear();
        ((CourseServiceMapImpl)KsapFrameworkServiceLocator.getCourseService()).clear();
        DefaultKsapContext.after();
    }

//    DECISION NOTE:  I initially overrode these CRUD tests in this class with empty logic ...thinking that
//           execution of the tests in this class was unneccessary they are adequately tested via.
//           the jpa/mapped impl AcademicPlanServiceImplTest,
//           BUT: then I realized they are a sort of a test of the MapImpl itself, so I commented
//           them back out so the base methods will be executed against the mapImpl as well.
//    @Override
//    @Test
//    public void testCrudLearningPlan() {}; //Tested by JPA backed impl
//
//    @Override
//    @Test
//    public void testCrudPlanItem() {};  //Tested by JPA backed impl

    @Test(expected = DoesNotExistException.class)
    public void getUnknownLearningPlan()
            throws InvalidParameterException,
                   MissingParameterException, DoesNotExistException,
                   OperationFailedException, PermissionDeniedException {
        KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlan("unknown_plan",
                KsapFrameworkServiceLocator.getContext().getContextInfo());
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
        } catch (InvalidParameterException exc) {
            exc.printStackTrace();
            assertTrue(exc.getMessage().startsWith("Invalid item reference object type"));
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
        String courseType = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;

        planItem.setRefObjectId(courseId);
        planItem.setRefObjectType(courseType);
        planItem.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);

        try {
            KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItem,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            fail("DataValidationErrorException should have been thrown as learning plan id was null");
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
        String courseType = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;

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
    public void validatePlanItemForCourse()
            throws InvalidParameterException,
                   MissingParameterException, AlreadyExistsException,
                   DoesNotExistException, OperationFailedException, PermissionDeniedException {

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
        planItem.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
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
        planItemInfo.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
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
                   OperationFailedException, PermissionDeniedException {
        PlanItemInfo planItemInfo = new PlanItemInfo();
        planItemInfo.setLearningPlanId("lp1");
        planItemInfo.setRefObjectId("XX");
        planItemInfo.setRefObjectType(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
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
