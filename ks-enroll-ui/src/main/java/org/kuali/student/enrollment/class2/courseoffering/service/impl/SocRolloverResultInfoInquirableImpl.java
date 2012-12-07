package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.Map;

public class SocRolloverResultInfoInquirableImpl extends InquirableImpl {
    private transient CourseOfferingSetService courseOfferingSetService = null;
    private static final long serialVersionUID = 1L;
    @Override
    public SocRolloverResultInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            SocRolloverResultInfo socRolloverResultInfo = getCourseOfferingSetService().getSocRolloverResult(
                                    parameters.get(CourseOfferingConstants.SOCROLLOVERRESULTINFO_ID), ContextUtils.createDefaultContextInfo());
            return socRolloverResultInfo;
        } catch (Exception e) {
            throw new RuntimeException("Errir looking up rollover results", e);
        }

    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null)
            courseOfferingSetService= CourseOfferingResourceLoader.loadCourseOfferingSetService();
        return courseOfferingSetService;
    }

}
