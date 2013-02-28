package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.enrollment.class1.krms.service.AgendaManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private SearchService searchService = null;

    private CourseService courseService;

    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        // TODO: How does one get rid of hard-coding "atpCode"?
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        AcademicCalendarService acalService = _getAcalService();
        return acalService.searchForTerms(criteria, createContextInfo());
    }

    public void loadAgendasByTermAndCourseCode(String termId, String courseCode, AgendaManagementForm form) throws Exception {

        ContextInfo contextInfo = createContextInfo();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);

        List<CourseOfferingListSectionWrapper>  coListWrapperList = new ArrayList<CourseOfferingListSectionWrapper>();
        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingListSectionWrapper coListWrapper = new CourseOfferingListSectionWrapper();

            for(SearchResultCellInfo cellInfo : row.getCells()){

                String value = StringUtils.EMPTY;
                if(cellInfo.getValue() != null)  {
                    value = new String(cellInfo.getValue());
                }

                if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCode(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingDesc(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.STATE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingStateKey(value);
                    coListWrapper.setCourseOfferingStateDisplay(getStateInfo(value).getName());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionKey(value);
                    CourseOfferingTransformer courseOfferingTransformer = new CourseOfferingTransformer();
                    coListWrapper.setCourseOfferingCreditOptionDisplay(courseOfferingTransformer.getCreditCount(value, "", null, null, contextInfo));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingId(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.SUBJECT_AREA.equals(cellInfo.getKey())){
                    coListWrapper.setSubjectArea(value);
                }

            }
            coListWrapperList.add(coListWrapper);
        }

        form.setCourseOfferingResultList(Collections.unmodifiableList(coListWrapperList));

    }

    private AcademicCalendarService _getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public CourseService getCourseService() {
        if (courseService == null){
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

}
