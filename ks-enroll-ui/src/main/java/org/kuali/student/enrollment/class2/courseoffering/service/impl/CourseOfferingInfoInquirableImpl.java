package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.common.util.security.ContextUtils;

import java.util.Map;


public class CourseOfferingInfoInquirableImpl extends InquirableImpl {

    @Override
    public CourseOfferingInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(parameters.get(CourseOfferingConstants.COURSEOFFERING_ID), ContextUtils.createDefaultContextInfo());
            return courseOfferingInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
