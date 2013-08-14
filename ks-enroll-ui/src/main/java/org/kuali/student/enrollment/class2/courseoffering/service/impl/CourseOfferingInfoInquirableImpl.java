package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.Map;


public class CourseOfferingInfoInquirableImpl extends InquirableImpl {
    private CourseOfferingService courseOfferingService;
    private CourseService courseService;

    @Override
    public CourseOfferingInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(parameters.get(CourseOfferingConstants.COURSEOFFERING_ID), ContextUtils.createDefaultContextInfo());
            return courseOfferingInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }

}
