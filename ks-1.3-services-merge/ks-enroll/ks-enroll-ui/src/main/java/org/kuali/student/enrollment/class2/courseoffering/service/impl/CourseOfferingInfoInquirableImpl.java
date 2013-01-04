package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.*;


public class CourseOfferingInfoInquirableImpl extends InquirableImpl {
    private transient CourseOfferingService courseOfferingService;
    private CourseService courseService;
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

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
