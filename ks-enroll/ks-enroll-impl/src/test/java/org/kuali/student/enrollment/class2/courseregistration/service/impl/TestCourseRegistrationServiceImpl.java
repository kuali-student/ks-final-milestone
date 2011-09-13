package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

public class TestCourseRegistrationServiceImpl {

    private CourseRegistrationServiceImpl crServiceValidation;

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

        return regRequest;
    }

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testCreateRegRequestFromExisting() {

    }

    public void testCreateRegRequest() {
        RegRequestInfo regRequest = createDummyRegRequest();

    }
}
