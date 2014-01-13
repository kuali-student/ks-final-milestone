package org.kuali.student.ap.coursesearch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class CourseInfoLookupableImpl extends LookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;

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
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria) {
        boolean valid = super.validateSearchParameters(form,searchCriteria);

        if (valid){
            String courseCodeParam = searchCriteria.get(QueryParamEnum.CODE.getFieldValue());
            String courseTitleParam = searchCriteria.get(QueryParamEnum.TITLE.getFieldValue());

            if (StringUtils.isBlank(courseCodeParam) && StringUtils.isBlank(courseTitleParam)){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter atleast one field.");
                valid = false;
            } else if ((StringUtils.isBlank(courseCodeParam) && StringUtils.length(courseTitleParam) < 2) ||
                       (StringUtils.length(courseCodeParam) < 2 && StringUtils.isBlank(courseTitleParam))) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter atleast 2 characters for the search.");
                valid = false;
            }
        }

        return valid;
    }

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.getValues().add("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);

        qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalState");
        qpv1.getValues().add("Active");
        searchParams.add(qpv1);

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

            if (searchResult.getRows().size() > 0) {
                for(SearchResultRowInfo srrow : searchResult.getRows()){
                    List<SearchResultCellInfo> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        CourseInfo course = new CourseInfo();
                        for(SearchResultCellInfo srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                                course.setCode(srcell.getValue());
                            } else if (srcell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                                course.setCourseTitle(srcell.getValue());
                            }else if (srcell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                                RichTextInfo desc = new RichTextInfo();
                                desc.setFormatted(srcell.getValue());
                                course.setDescr(desc);
                            }
                        }
                        courseInfoList.add(course);
                    }
                }
            }

            return courseInfoList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected CluService getCluService() {
        if(cluService == null) {
            cluService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.cluService;
    }

}
