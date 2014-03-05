package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.common.util.security.ContextUtils;

import java.util.Map;

public class SocRolloverResultInfoInquirableImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;

    @Override
    public SocRolloverResultInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            SocRolloverResultInfo socRolloverResultInfo = CourseOfferingManagementUtil.getCourseOfferingSetService().getSocRolloverResult(
                                    parameters.get(CourseOfferingConstants.SOCROLLOVERRESULTINFO_ID), ContextUtils.createDefaultContextInfo());
            return socRolloverResultInfo;
        } catch (Exception e) {
            throw new RuntimeException("Errir looking up rollover results", e);
        }

    }
}
