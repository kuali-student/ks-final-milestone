package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import static org.junit.Assert.assertNotNull;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        regRequest.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
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
}
