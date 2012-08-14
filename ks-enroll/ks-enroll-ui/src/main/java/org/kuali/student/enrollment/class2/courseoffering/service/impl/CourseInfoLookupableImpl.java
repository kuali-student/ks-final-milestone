package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class CourseInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService luService;
    private transient CourseService courseService;

    public enum QueryParamEnum {
        ID("lu.queryParam.luOptionalId","id"),
        SUBJECT("lu.queryParam.luOptionalStudySubjectArea", "subjectArea"),
        CODE("lu.queryParam.luOptionalCode", "code"),
        TITLE("lu.queryParam.luOptionalLongName", "courseTitle");

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
        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
        String courseId = null;
        List<SearchParam> searchParams = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.setValue("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);
        for (QueryParamEnum qpEnum : QueryParamEnum.values()) {
            String fieldValue = fieldValues.get(qpEnum.getFieldValue());
            if ( ! isEmpty(fieldValue) ) {
                SearchParam qpv = new SearchParam();
                qpv.setKey(qpEnum.getQueryKey());
                qpv.setValue(fieldValue);
                searchParams.add(qpv);
            }
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.mostCurrent.union");

        try {
            SearchResult searchResult = getCluService().search(searchRequest);

            if (searchResult.getRows().size() > 0) {
                for(SearchResultRow srrow : searchResult.getRows()){
                    List<SearchResultCell> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCell srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                                courseId = srcell.getValue();
                                CourseInfo course = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(course);
                            }
                        }
                    }
                }
            }

            return courseInfoList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Note: here I am using r1 CluService implementation!!!
    protected CluService getCluService() {
        if(luService == null) {
            luService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,"CluService"));
        }
        return this.luService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService)GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE,"CourseService"));
        }
        return this.courseService;
    }
}
