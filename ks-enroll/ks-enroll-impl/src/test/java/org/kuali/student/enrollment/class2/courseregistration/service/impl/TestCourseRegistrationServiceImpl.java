package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// Note: un-ignore and test within eclipse because the data for courseservice
// are not working via command-line: mvn clean install
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:coursereg-test-context.xml"})
public class TestCourseRegistrationServiceImpl {

    private CourseRegistrationService courseRegServiceValidation;

    public CourseRegistrationService getCourseRegServiceValidation() {
        return courseRegServiceValidation;
    }

    public void setCourseRegServiceValidation(CourseRegistrationService courseRegServiceValidation) {
        this.courseRegServiceValidation = courseRegServiceValidation;
    }

    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    private RegRequestInfo createDummyRegRequest() {
        RegRequestInfo regRequest = new RegRequestInfo();
        regRequest.setId(String.valueOf(Math.random()));
        regRequest.setRequestorId("Student1");
        regRequest.setTermKey(AtpServiceConstants.ATP_SENIOR_YEAR_TERM_1_TYPE_KEY);
        regRequest.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);

        RegRequestItemInfo regRequestItem = new RegRequestItemInfo();
        regRequestItem.setCreditOptionKey("kuali.credit.option.RVG1");
        regRequestItem.setGradingOptionKey("kuali.grading.option.RVG1");
        regRequestItem.setName("TRANS NAME");
        regRequestItem.setOkToHoldList(false);
        regRequestItem.setOkToWaitlist(false);
        regRequestItem.setStudentId("Student1");
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

    @Ignore
    @Test
    public void testServiceSetup() {
        assertNotNull(courseRegServiceValidation);
    }

    @Ignore
    @Test
    public void testCreateRegRequestFromExisting() {

    }

    @Ignore
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
}
