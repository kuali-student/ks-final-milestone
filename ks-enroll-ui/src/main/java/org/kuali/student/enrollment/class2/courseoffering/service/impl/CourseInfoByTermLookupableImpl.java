package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * This class is a take-off of CourseInfoLookupableImpl, but adds support
 * for target term in the lookup but using the term dates to constrain
 * the courses retrieve by their respective effective and expire dates.
 * Besides courses that have an Active state, it also includes those are
 * Superseded and Retired.
 */
public class CourseInfoByTermLookupableImpl extends LookupableImpl {
    private static final long serialVersionUID = 1L;

    private transient CluService cluService;
    private AtpService atpService;

    public enum QueryParamEnum {
        TERM_START("lu.queryParam.luOptionalGreaterThanEqualExpirDate","termStartDate"),
        TERM_END("lu.queryParam.luOptionalLessThanEqualEffectDate","termEndDate"),
        TERM_ID("termId","termId"),
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
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter at least Course Code or Title search criteria.");
                valid = false;
            } else if ((StringUtils.isBlank(courseCodeParam) && StringUtils.length(courseTitleParam) < 2) ||
                    (StringUtils.length(courseCodeParam) < 2 && StringUtils.isBlank(courseTitleParam))) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter at least 2 characters for the search.");
                valid = false;
            }
        }

        return valid;
    }

    @Override
    public void buildReturnUrlForResult(Link returnLink, Object model) {

        /**
         * This is a custom hack of a KRAD lookup to return a criteria field value
         * along with the selected lookup value...when a result row is selected.
         *
         * To do this we will modify the url href to insert: 1. the
         * path to the field to set in the parent view, and 2. the value to set it to.
         *
         * note: This code is specifically for view: CourseOfferingCreateMaintenanceView.xml
         */
        super.buildReturnUrlForResult(returnLink, model);
        String href = returnLink.getHref();
        String targetTermCode="document.newMaintainableObject.dataObject.targetTermCode";

        LookupForm lookupForm = (LookupForm) model;
        Map<String, String> lookupCriteria = lookupForm.getLookupCriteria();
        String termId = lookupCriteria.get("termId");

        //We need to check whether p:lookupReturnByScript is set to "true" or "false" by checking
        //for either 'returnLookupResultByScript' (i.e. when it is true) or
        //for 'returnLookupResultReload' (when it is false), and construct the new href
        //accordingly
        if (StringUtils.isEmpty(termId) && href.indexOf("returnLookupResultByScript") >0) {
            String closeFunction = "closeLightbox();";
            int closeIndex = href.indexOf(closeFunction);
            returnLink.setHref(href.substring(0, closeIndex) + "returnLookupResultByScript(\"" + targetTermCode + "\", '" + termId + "');" + closeFunction);
        } else if (href.indexOf("returnLookupResultReload") >0) {
            String parent = "\", '_parent');";
            int parentIndex = href.indexOf(parent);
            returnLink.setHref(href.substring(0, parentIndex) + "&" + targetTermCode + "=" + termId + parent);
        }
    }

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.getValues().add("kuali.lu.type.CreditCourse");
        searchParams.add(qpv1);

        //Include course states of: Active, Superseded and Retired
        qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalState");
        qpv1.setValues(Arrays.asList(
                DtoConstants.STATE_ACTIVE,
                DtoConstants.STATE_SUPERSEDED,
                DtoConstants.STATE_RETIRED));
        searchParams.add(qpv1);

        for (QueryParamEnum qpEnum : QueryParamEnum.values()) {
            String fieldValue = searchCriteria.get(qpEnum.getFieldValue());
            if ( ! isEmpty(fieldValue) ) {
                SearchParamInfo qpv = new SearchParamInfo();
                switch (qpEnum) {

                    /**Note:  TERM_ID takes the special bus route:  it is used to retrieve
                     * term begin/end dates to in-turn constrain courses by their effective and
                     * retire dates, respectively.  AND NOTE that termId is not itself used
                     * directly in the course lookup query.
                     */
                    case TERM_ID:
                        //First get ATP information
                        List<AtpInfo> atps;
                        try {
                            atps = getAtpService().getAtpsByCode(fieldValue, context);
                        } catch (Exception e) {
                            return courseInfoList;
                        }
                        if (atps == null || atps.size() != 1) {
                            return courseInfoList;
                        }
                        int firstATPInfo = 0;
                        qpv.setKey(QueryParamEnum.TERM_START.getQueryKey());
                        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstATPInfo).getStartDate()));
                        searchParams.add(qpv);
                        qpv = new SearchParamInfo();
                        qpv.setKey(QueryParamEnum.TERM_END.getQueryKey());
                        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstATPInfo).getEndDate()));
                        searchParams.add(qpv);
                        break;
                    default:
                        qpv.setKey(qpEnum.getQueryKey());
                        qpv.getValues().add(fieldValue);
                        searchParams.add(qpv);
                }
            }
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);

        searchRequest.setSearchKey("lu.search.courseCodes"); //This defines the base query to use (see lu-search-config.xml)

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());

            if (searchResult.getRows().size() > 0) {
                for(SearchResultRowInfo srrow : searchResult.getRows()){
                    List<SearchResultCellInfo> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        CourseInfo course = new CourseInfo();
                        for(SearchResultCellInfo srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.cluOfficialIdentifier.cluCode")) {
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
        if(this.cluService == null) {
            this.cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.cluService;
    }
    private AtpService getAtpService() {
        if(atpService == null){
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

}