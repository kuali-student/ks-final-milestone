package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-validation-decorator-test-context.xml"})
public class TestCourseOfferingServiceValidationDecorator {

    private static final String EXPECTED_ERROR_MESSAGE = "Access to course offerings is not permitted while this term's Set of Course (SOC) is being scheduled (SocState).";
    @Resource(name = "CourseOfferingService")
    protected CourseOfferingService courseOfferingService;
    @Resource(name = "socService")
    protected CourseOfferingSetService socService;

    public static String principalId = "123";
    public static ContextInfo context;

    @BeforeClass
    public static void setUp() {
        context = new ContextInfo();
        context.setPrincipalId(principalId);
    }

    /**
     * The mock SearceServiceMockImpl injected into the test is set to return ao1->A, ao2->B for a search for coId->co1.
     * The updateAoCode* tests are
     */
    @Test
    public void updateAoCodeValidationDuplicateCode() throws Exception {
        ActivityOfferingInfo aoInfo = makeAoInfo();
        //  Change the code from A to B where B is already in use.
        aoInfo.setActivityCode("B");

        try {
            courseOfferingService.updateActivityOffering(aoInfo.getId(), aoInfo, context);
            fail("Validation should have failed with duplicate AO code.");
        } catch(DataValidationErrorException e) {
            boolean foundAoCodeError = false;
            for (ValidationResultInfo v : e.getValidationResults()) {
                if (StringUtils.equals(v.getElement(), "activityCode")) {
                    foundAoCodeError = true;
                }
            }
            if (! foundAoCodeError) {
               fail("The validation failed, but didn't find a duplicate AO code error in the validation results.");
            }
        }
    }

    @Test(expected = DoesNotExistException.class)
    public void updateAoCodeValidationNoAoCodeChange() throws Exception {
        ActivityOfferingInfo aoInfo = makeAoInfo();
        courseOfferingService.updateActivityOffering(aoInfo.getId(), aoInfo, context);
        fail("Validation should have failed with a DoesNotExistException.");
    }

    @Test(expected = DoesNotExistException.class)
    public void updateAoCodeValidationAoCodeChangeToVacantCode() throws Exception {
        ActivityOfferingInfo aoInfo = makeAoInfo();
        aoInfo.setActivityCode("Z");
        courseOfferingService.updateActivityOffering(aoInfo.getId(), aoInfo, context);
        fail("Validation should have failed with a DoesNotExistException.");
    }

    private ActivityOfferingInfo makeAoInfo() {
        ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
        aoInfo.setId("ao1");
        aoInfo.setActivityCode("A");
        aoInfo.setFormatOfferingId("fo1");
        aoInfo.setActivityId("activity1");
        aoInfo.setCourseOfferingId("co1");
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        aoInfo.setMinimumEnrollment(100);
        aoInfo.setMaximumEnrollment(150);
        aoInfo.setIsEvaluated(true);
        aoInfo.setIsMaxEnrollmentEstimate(false);
        aoInfo.setIsHonorsOffering(true);
        return aoInfo;
    }

    @Test
    public void verifySocStateIsValidForCrudOperations() throws Exception {

        String termId = "termH2G242";
        String cId = "c1";
        String offeringId = "offering1";
        String coType = "coType1";
        List<String> optionKeys = new ArrayList<String>();

        SocInfo orig = makeSocInfo(termId, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS);
        this.socService.createSoc(orig.getTermId(), orig.getTypeKey(), orig, context);

        CourseOfferingInfo coInfo = makeCoInfo(termId, offeringId, coType);
        // CourseOffering
        try {
            this.courseOfferingService.createCourseOffering(cId, termId, coType, coInfo, optionKeys, context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

        try {
            courseOfferingService.updateCourseOffering(coInfo.getId(), coInfo, context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

        try {
            courseOfferingService.deleteCourseOffering(coInfo.getId(), context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

        try {
            courseOfferingService.deleteCourseOfferingCascaded(coInfo.getId(), context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

       // ActivityOffering

        String foId = "foId1";
        String aId = "aId1";
        String aTypeKey = "aTypeKey1";
        ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
        aoInfo.setTermId("termH2G242");
        try {
            this.courseOfferingService.createActivityOffering(foId, aId, aTypeKey, aoInfo, context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

        try {
            courseOfferingService.updateActivityOffering(offeringId, aoInfo, context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }

        try {
            courseOfferingService.deleteActivityOffering(offeringId, context);
            fail("OperationFailedException should have been thrown");
        } catch(OperationFailedException e) {
            assertNotNull(e.getMessage());
            assertEquals(EXPECTED_ERROR_MESSAGE, e.getMessage());
        }
    }

    private CourseOfferingInfo makeCoInfo(String termId, String coId, String coType) {
        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        coInfo.setId(coId);
        coInfo.setTypeKey(coType);
        coInfo.setStateKey("s");
        coInfo.setCourseId("c1");
        coInfo.setTermId(termId);
        coInfo.setGradingOptionId("g1");
        coInfo.setCreditOptionId("c1");
        return coInfo;
    }

    private SocInfo makeSocInfo(String termId, String socState) {

        SocInfo socInfo = new SocInfo();
        socInfo.setName("test name");
        socInfo.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        socInfo.setStateKey(socState);
        socInfo.setTermId(termId);
        socInfo.setSubjectArea("ENG");
        socInfo.setUnitsContentOwnerId("myUnitId");
        socInfo.getAttributes().add(new AttributeTester().toAttribute("key1", "value1"));
        socInfo.getAttributes().add(new AttributeTester().toAttribute("key2", "value2"));

        return socInfo;
    }

}
