package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private final static Logger LOG = Logger.getLogger(CourseComponentBuilder.class);
    private CourseService courseService;
    private CluService cluService;
    private AcademicCalendarService acalService = null;
    private SearchService searchService = null;

    private static final String CLU_KEY = "kuali.term.parameter.type.course.clu.id";
    private static final String TERM_KEY = "kuali.term.parameter.type.Term";
    private static final String TERM2_KEY = "kuali.term.parameter.type.Term2";

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String courseId = termParameters.get(CLU_KEY);
        if (courseId != null) {
            try {
                VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, courseId, null);
                CourseInfo courseInfo = this.getCourseService().getCourse(versionInfo.getId(), ContextUtils.getContextInfo());
                propositionEditor.setCourseInfo(courseInfo);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("Clu does not exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null){

            termParameters.put(CLU_KEY, propositionEditor.getCourseInfo().getVersion().getVersionIndId());
        }

       if ( propositionEditor.getTermCode() != null ){

            try {
                TermInfo termInfo = this.populateTerm(propositionEditor);
                propositionEditor.setTermInfo(termInfo);
                if (propositionEditor.getTermInfo() != null){
                termParameters.put(TERM_KEY, propositionEditor.getTermInfo().getId());
                    loadCourseOfferingsByTermAndCourseCode(propositionEditor.getTermInfo().getId(), propositionEditor.getCourseInfo().getCode(),propositionEditor) ;
                }
            } catch (DoesNotExistException e) {
                throw new RuntimeException("term does not exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if ( propositionEditor.getTermCode2() != null ){

            try {
                TermInfo termInfo = this.populateTerm(propositionEditor);
                propositionEditor.setTermInfo(termInfo);
                if (propositionEditor.getTermInfo() != null){
                    termParameters.put(TERM2_KEY, propositionEditor.getTermInfo().getId());
                    loadCourseOfferingsByTermAndCourseCode(propositionEditor.getTermInfo().getId(), propositionEditor.getCourseInfo().getCode(),propositionEditor) ;
                }
            } catch (DoesNotExistException e) {
                throw new RuntimeException("term does not exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Method used to
     * 1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     * don't find any termInfo, log and report an error message.
     * @param  propositionEditor
     * @return    termInfo
     */
    public TermInfo populateTerm(EnrolPropositionEditor propositionEditor) throws Exception {

        String termCode = propositionEditor.getTermCode();



        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<TermInfo> terms = getAcalService().searchForTerms(criteria, ContextUtils.getContextInfo());

        if (terms.isEmpty()) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
        } else if (terms.size() > 1) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
        } else {
            propositionEditor.setTermInfo(terms.get(0));

        }
      return  propositionEditor.getTermInfo();
    }


    /**  * Method used to
     * find THE course based on termId and courseCode. If find more than one CO or don't find
     * any CO, log and report an error message.
     * @param  termId
     * @param  courseCode
     * @param  propositionEditor
     */
    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, EnrolPropositionEditor propositionEditor) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        ContextInfo contextInfo = ContextUtils.getContextInfo();

        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);
        if (searchResult.getRows().isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Code", courseCode, termId);
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
    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

}
