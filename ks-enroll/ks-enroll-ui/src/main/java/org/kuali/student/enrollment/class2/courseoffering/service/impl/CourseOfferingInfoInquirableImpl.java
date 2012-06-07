package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.Map;


public class CourseOfferingInfoInquirableImpl extends InquirableImpl {
    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo = null;

    @Override
    public CourseOfferingInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(parameters.get(CourseOfferingConstants.COURSEOFFERING_ID), getContextInfo());
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

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
