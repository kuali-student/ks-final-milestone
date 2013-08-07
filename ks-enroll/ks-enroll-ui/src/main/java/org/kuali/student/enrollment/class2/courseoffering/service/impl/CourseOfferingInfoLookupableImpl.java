package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
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
    private transient CourseOfferingService courseOfferingService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        String termKey = fieldValues.get(CourseOfferingConstants.COURSEOFFERING_TERM_ID);
        String subjectArea = fieldValues.get(CourseOfferingConstants.COURSEOFFERING_SUBJECT_AREA);

        List<CourseOfferingInfo> courseOfferings;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termKey, subjectArea, contextInfo);
            courseOfferings = new ArrayList<CourseOfferingInfo>(courseOfferingIds.size());

            for(String coId : courseOfferingIds) {
                courseOfferings.add(getCourseOfferingService().getCourseOffering(coId, contextInfo));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseOfferings;
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

}
