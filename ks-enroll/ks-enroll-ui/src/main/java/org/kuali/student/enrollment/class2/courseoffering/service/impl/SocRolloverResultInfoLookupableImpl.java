package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingSetConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 5/10/12
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocRolloverResultInfoLookupableImpl extends LookupableImpl {
    private transient CourseOfferingSetService courseOfferingSetService = null;
    private ContextInfo contextInfo;

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<SocRolloverResultInfo> socRolloverResultInfos = new ArrayList<SocRolloverResultInfo>();
        String sourceTermId = fieldValues.get(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID);
        String targetTermId = fieldValues.get(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID);

        return socRolloverResultInfos;

    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null)
            courseOfferingSetService= CourseOfferingResourceLoader.loadCourseOfferingSetService(courseOfferingSetService);
        return courseOfferingSetService;
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo(contextInfo);
    }
}
