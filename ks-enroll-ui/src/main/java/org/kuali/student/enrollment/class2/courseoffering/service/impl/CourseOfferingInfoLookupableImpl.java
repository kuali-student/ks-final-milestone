package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is leftover from Core Slice. Delete when no longer needed or un deprecate if needed.
 */

public class CourseOfferingInfoLookupableImpl extends LookupableImpl {
    private static final long serialVersionUID = 1L;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {

        String termKey = searchCriteria.get(CourseOfferingConstants.COURSEOFFERING_TERM_ID);
        String subjectArea = searchCriteria.get(CourseOfferingConstants.COURSEOFFERING_SUBJECT_AREA);

        List<CourseOfferingInfo> courseOfferings;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            List<String> courseOfferingIds = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termKey, subjectArea, contextInfo);
            courseOfferings = new ArrayList<CourseOfferingInfo>(courseOfferingIds.size());

            for(String coId : courseOfferingIds) {
                courseOfferings.add(CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coId, contextInfo));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseOfferings;
    }
}
