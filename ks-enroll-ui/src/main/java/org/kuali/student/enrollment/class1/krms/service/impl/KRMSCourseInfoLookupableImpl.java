package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.util.CluInformationHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class KRMSCourseInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;
    private transient CourseService courseService;

    public enum QueryParamEnum {
        ID("lu.queryParam.luOptionalId","id"),
        TITLE("lu.queryParam.luOptionalLongName", "title"),
        CODE("lu.queryParam.luOptionalCode", "code"),
        DESCRIPTION("lu.queryParam.luOptionalDescr", "description");

        private final String fieldValue;
        private final String queryKey;

        QueryParamEnum(String qKey, String fValue) {
            this.queryKey = qKey;
            this.fieldValue = fValue;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public String getQueryKey() {
            return queryKey;
        }
    }

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.getValues().add("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);
        searchParams.add(CluInformationHelper.getApprovedStateSearchParam());
        for (QueryParamEnum qpEnum : QueryParamEnum.values()) {
            String fieldValue = fieldValues.get(qpEnum.getFieldValue());
            if ( ! isEmpty(fieldValue) ) {
                SearchParamInfo qpv = new SearchParamInfo();
                qpv.setKey(qpEnum.getQueryKey());
                qpv.getValues().add(fieldValue);
                searchParams.add(qpv);
            }
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.mostCurrent.union");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            return CluInformationHelper.resolveCluSearchResultSet(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }
}
