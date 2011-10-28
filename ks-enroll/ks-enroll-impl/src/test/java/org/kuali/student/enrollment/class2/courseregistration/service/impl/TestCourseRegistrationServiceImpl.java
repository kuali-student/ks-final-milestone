package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:coursereg-test-context.xml"})
public class TestCourseRegistrationServiceImpl {

    private CourseRegistrationService courseRegServiceValidation;

    @Autowired
    public void setCourseRegServiceValidation(CourseRegistrationService courseRegServiceValidation) {
        this.courseRegServiceValidation = courseRegServiceValidation;
    }

    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    private RegRequestInfo createDummyRegRequest() {
        RegRequestInfo regRequest = new RegRequestInfo();
        regRequest.setRequestorId("Student1");
        regRequest.setTermKey(AtpServiceConstants.ATP_SENIOR_YEAR_TERM_1_TYPE_KEY);
        regRequest.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_NEW_STATE_KEY);
        regRequest.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);

        RegRequestItemInfo regRequestItem = new RegRequestItemInfo();
        regRequestItem.setCreditOptionKey("kuali.credit.option.RVG1");
        regRequestItem.setGradingOptionKey("kuali.grading.option.RVG1");
        regRequestItem.setName("TRANS NAME");
        regRequestItem.setOkToHoldList(false);
        regRequestItem.setOkToWaitlist(false);
        regRequestItem.setStudentId("Student1");
        regRequestItem.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);

        regRequestItem.setNewRegGroupId("Lui-1");
        regRequestItem.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        regRequest.getRegRequestItems().add(regRequestItem);

        return regRequest;
    }

    @Before
    public void setUp() throws Exception {

        principalId = "123";
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);

    }

    @Test
    public void testServiceSetup() {
        assertNotNull(courseRegServiceValidation);
    }

    
    @Test
    public void testCreateRegRequestFromExisting() {

    }

    @Test
    public void testCreateRegRequest() {

        try {
            RegRequestInfo regRequest = createDummyRegRequest();
            RegRequestInfo regRequestNew = courseRegServiceValidation.createRegRequest(regRequest, callContext);
            assertNotNull(regRequestNew);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("exception from service call :" + ex.getMessage());
        }

    }

    @Ignore
    @Test
    public void testSubmitRegRequest() {

        try {
            RegResponseInfo regResponse = courseRegServiceValidation.submitRegRequest("testLprTransId1",
                    callContext);
            assertNotNull(regResponse);
            assertTrue(regResponse.getOperationStatus().equals("SUCCESS"));

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }

    }

    @Test
    public void testCheckStudentEligibiltyForCourseOffering() {
        String studentId = "testStudentId1";
        String courseOfferingId = "courseOffering3";

        try {
            List<ValidationResultInfo> passedResults = courseRegServiceValidation.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);

            assertEquals(1, passedResults.size());
            ValidationResultInfo result = passedResults.get(0);
            assertEquals(result.getLevel().intValue(), ValidationResult.ErrorLevel.OK.getLevel());
        } catch (Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }

        // test with a course offering the student is not eligible for
        studentId = "testStudentId2";
        courseOfferingId = "courseOffering3";

        try {
            List<ValidationResultInfo> failedResults = courseRegServiceValidation.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);

            assertEquals(1, failedResults.size());
            ValidationResultInfo result = failedResults.get(0);
            assertEquals(result.getLevel().intValue(), ValidationResult.ErrorLevel.ERROR.getLevel());
        } catch (Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }

        // test with an invalid course offering id
        courseOfferingId = "invalidCourse Offering Id";
        List<ValidationResultInfo> shouldBeNull = null;

        try {
            shouldBeNull = courseRegServiceValidation.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);
            fail("should have thrown an exception in checkStudentEligibiltyForCourseOffering due to invalid course offering id");
        }
        catch(InvalidParameterException ie) {
            assertNull(shouldBeNull);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }
    }
}
