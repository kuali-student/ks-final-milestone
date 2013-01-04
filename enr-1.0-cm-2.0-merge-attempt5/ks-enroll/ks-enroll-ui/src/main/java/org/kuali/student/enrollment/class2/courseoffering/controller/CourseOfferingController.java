package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingHistorySearchImpl;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;


@Controller
@RequestMapping(value = "/courseOffering")
public class CourseOfferingController extends MaintenanceDocumentController {

    private CluService cluService;
    private CourseService courseService;
    private AcademicCalendarService academicCalendarService;
    private CourseOfferingService courseOfferingService;
    private transient LRCService lrcService;
    private transient SearchService searchService;

    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);

        }

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=loadCourseCatalog")
    public ModelAndView loadCourseCatalog(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();
        String termCode = coWrapper.getTargetTermCode();

        TermInfo term = getTerm(termCode);
        coWrapper.setTerm(term);

        List<CourseInfo> matchingCourses = retrieveMatchingCourses(courseCode);

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);

        }

        if (matchingCourses.size() == 1 && term != null) {
            CourseInfo course = matchingCourses.get(0);
            coWrapper.setCourse(course);
            coWrapper.setCreditCount(ViewHelperUtil.trimTrailing0(getLrcService().getResultValue(course.getCreditOptions().get(0).getResultValueKeys().get(0), contextInfo).getValue()));
            coWrapper.setShowAllSections(true);
            coWrapper.setShowCatalogLink(false);
            coWrapper.setShowTermOfferingLink(true);

            //Get all the course offerings in a term
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(), contextInfo);

            coWrapper.getExistingTermOfferings().clear();
            coWrapper.getExistingOfferingsInCurrentTerm().clear();

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                if (StringUtils.equals(courseOfferingInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)) {
                    ExistingCourseOffering co = new ExistingCourseOffering(courseOfferingInfo);
                    co.setCredits(courseOfferingInfo.getCreditCnt());
                    co.setGrading(getGradingOption(courseOfferingInfo.getGradingOptionId()));
                    coWrapper.getExistingOfferingsInCurrentTerm().add(co);
                }
            }

            //Get past 5 years CO
            Calendar termStart = Calendar.getInstance();
            termStart.setTime(term.getStartDate());
            String termYear = Integer.toString(termStart.get(Calendar.YEAR));


            org.kuali.student.r2.core.search.dto.SearchRequestInfo searchRequest = new org.kuali.student.r2.core.search.dto.SearchRequestInfo(CourseOfferingHistorySearchImpl.PAST_CO_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingHistorySearchImpl.COURSE_ID, coWrapper.getCourse().getId());

            searchRequest.addParam(CourseOfferingHistorySearchImpl.TARGET_YEAR_PARAM, termYear);
            org.kuali.student.r2.core.search.dto.SearchResultInfo searchResult = getSearchService().search(searchRequest, null);

            List<String> courseOfferingIds = new ArrayList<String>(searchResult.getTotalResults());
            for (org.kuali.student.r2.core.search.dto.SearchResultRowInfo row : searchResult.getRows()) {
                courseOfferingIds.add(row.getCells().get(0).getValue());
            }

            courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds, contextInfo);

            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                ExistingCourseOffering co = new ExistingCourseOffering(courseOfferingInfo);
                TermInfo termInfo = getAcademicCalendarService().getTerm(courseOfferingInfo.getTermId(), contextInfo);
                co.setTermCode(termInfo.getCode());
                co.setCredits(courseOfferingInfo.getCreditCnt());
                co.setGrading(getGradingOption(courseOfferingInfo.getGradingOptionId()));
                coWrapper.getExistingTermOfferings().add(co);
            }

            //Enable the create button
            coWrapper.setEnableCreateButton(true);
        } else {

            if (matchingCourses.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Multiple matches found for the course code");
                return null;
            } else if (matchingCourses.isEmpty() && term == null) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Both Catalog Course Code and Target Term are invalid");
            } else {
                if (term == null) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Invalid Target Term");
                } else if (matchingCourses.isEmpty()) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Invalid Catalog Course Code");
                }
            }
            coWrapper.clear();
            coWrapper.setEnableCreateButton(false);

            return getUIFModelAndView(form);
        }

        return getUIFModelAndView(form);
    }

    private String getGradingOption(String gradingOptionId) throws Exception {
        String gradingOption = "";
        if (StringUtils.isNotBlank(gradingOptionId)) {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if (rvg != null && StringUtils.isNotBlank(rvg.getName())) {
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    @RequestMapping(params = "methodToCall=createFromCatalog")
    public ModelAndView createFromCatalog(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        wrapper.setShowCatalogLink(false);
        wrapper.setShowTermOfferingLink(true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=copyExistingCourseOffering")
    public ModelAndView copyExistingCourseOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingInfo existingCO = ((ExistingCourseOffering) getSelectedObject(form)).getCourseOfferingInfo();
        CourseOfferingCreateWrapper createWrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        List<String> optionKeys = new ArrayList<String>();

        if (createWrapper.isExcludeInstructorInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        }

        if (createWrapper.isExcludeSchedulingInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        }

        if (createWrapper.isExcludeCancelledActivityOfferings()) {
            optionKeys.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY);
        }

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // if source term differs from target term determine if add suffix or not
        if (StringUtils.equals(existingCO.getTermId(), createWrapper.getTargetTermCode())) {
            optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);
        } else {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("courseOfferingCode", existingCO.getCourseOfferingCode() + "%"),
                    PredicateFactory.equalIgnoreCase("atpId", createWrapper.getTargetTermCode())));
            QueryByCriteria criteria = qbcBuilder.build();
            List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

            if (courseOfferingIds.size() > 0) {
                optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);
            }
        }

        SocRolloverResultItemInfo item = getCourseOfferingService().rolloverCourseOffering(existingCO.getId(),
                createWrapper.getTerm().getId(),
                optionKeys,
                contextInfo);

        getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), contextInfo);
        KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_INFO_COPIED_SUCCESSFULLY, createWrapper.getCatalogCourseCode(), createWrapper.getTargetTermCode());
        return getUIFModelAndView(form);

    }

    @RequestMapping(params = "methodToCall=createFromTermOffering")
    public ModelAndView createFromTermOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                               @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCatalogLink(true);
        wrapper.setShowTermOfferingLink(false);

        return getUIFModelAndView(form);
    }

    private TermInfo getTerm(String termCode) {
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termCode)) {
            p = equal("atpCode", termCode);
            pList.add(p);
        }

        qBuilder.setPredicates(p);

        try {
            List<TermInfo> terms = getAcademicCalendarService().searchForTerms(qBuilder.build(), ContextUtils.createDefaultContextInfo());
            if (terms.size() > 1) {
                //GlobalVariables.getMessageMap().putError("asf","asdf");//FIXME
                return null;
            } else if (terms.isEmpty()) {
                return null;
            }
            return terms.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Object getSelectedObject(MaintenanceDocumentForm theForm) {
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set");
        }

        int selectedLineIndex = -1;
        String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    private CourseService getCourseService() {
        if (courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private List<CourseInfo> retrieveMatchingCourses(String courseName) {

        CourseInfo returnCourseInfo;
        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.criteria.code");
        qpv1.getValues().add(courseName.toUpperCase());
        searchParams.add(qpv1);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.cluByCode");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            if (searchResult.getRows().size() > 0) {
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    List<SearchResultCellInfo> srCells = row.getCells();
                    if (srCells != null && srCells.size() > 0) {
                        for (SearchResultCellInfo cell : srCells) {
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(returnCourseInfo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseInfoList;

    }

    private CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    protected LRCService getLrcService() {
        if (lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return this.lrcService;
    }

    protected SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }
}
