package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-validation-decorator-test-context.xml"})
public class TestCourseOfferingServiceValidationDecorator {

    @Resource(name = "CourseOfferingService")
    protected CourseOfferingService courseOfferingService;

    public ContextInfo context = new ContextInfo();

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

//    @Test
//    public void verifySocStateIsValidForCrudOperations() throws Exception {
//        CourseOfferingInfo coInfo = makeCoInfo();
//        courseOfferingService.updateCourseOffering(coInfo.getId(), coInfo, context);
//
//    }

    private CourseOfferingInfo makeCoInfo() {
        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        coInfo.setId("co1");
        coInfo.setTypeKey("t");
        coInfo.setStateKey("s");
        coInfo.setCourseId("c1");
        coInfo.setTermId("t1");
        coInfo.setGradingOptionId("g1");
        coInfo.setCreditOptionId("c1");
        return coInfo;
    }
}
