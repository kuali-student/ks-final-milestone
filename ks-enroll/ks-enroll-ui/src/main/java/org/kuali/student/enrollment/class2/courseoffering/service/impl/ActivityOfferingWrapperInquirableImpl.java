package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.Map;

public class ActivityOfferingWrapperInquirableImpl extends InquirableImpl {

    @Override
    public ActivityOfferingWrapper retrieveDataObject(Map<String, String> parameters) {
        try {
            ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(parameters.get(ActivityOfferingConstants.ACTIVITYOFFERING_ID), getContextInfo());
            ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(activityOfferingInfo);
            return aoWrapper;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }

}
