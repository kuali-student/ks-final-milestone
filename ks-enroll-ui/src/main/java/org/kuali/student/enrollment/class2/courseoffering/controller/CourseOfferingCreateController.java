/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.dto.JointCourseWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
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
import org.kuali.student.r2.core.class1.type.service.TypeService;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;


/**
 * This is the controller class what handles all the requests (actions) from the <i>'create course offering'</i> ui.
 * <p>
 *      Wireframes at {@link http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Course_Offering/v17/start.html} and
 *      {@link http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Complex%20Course%20Offerings/Sandbox/start.html}
 * </p>
 *
 * @see CourseOfferingCreateWrapper
 * @see JointCourseWrapper
 * @see org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper
 * @see CourseOfferingCreateMaintainableImpl
 */
@Controller
@RequestMapping(value = "/courseOfferingCreate")
public class CourseOfferingCreateController extends CourseOfferingBaseController {

    private CluService cluService;
    private CourseService courseService;
    private AcademicCalendarService academicCalendarService;
    private CourseOfferingService courseOfferingService;
    private transient LRCService lrcService;
    private transient SearchService searchService;
    private transient TypeService typeService;
    private CourseOfferingSetService courseOfferingSetService;

    /**
     * This is called when the user clicks on the <i>'show'</i> button after entering the term and course code.
     */
    @RequestMapping(params = "methodToCall=loadCourseCatalog")
    public ModelAndView loadCourseCatalog(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, HttpServletRequest request) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();
        String termCode = coWrapper.getTargetTermCode();

        TermInfo term = getTerm(termCode);
        coWrapper.setTerm(term);

        List<CourseInfo> matchingCourses = retrieveMatchingCourses(courseCode);

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        coWrapper.clear();

        if (matchingCourses.size() == 1 && term != null) {
            CourseInfo course = matchingCourses.get(0);

            // set organization IDs and check if the user can edit the course
            List<String> orgIds = course.getUnitsContentOwner();
            if(orgIds != null && !orgIds.isEmpty()){
                String orgIDs = "";
                for (String orgId : orgIds) {
                    orgIDs = orgIDs + orgId + ",";
                }
                if (orgIDs.length() > 0) {
                    coWrapper.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
                }
            }
            coWrapper.setCourse(course);
            Person user = GlobalVariables.getUserSession().getPerson();
            boolean canOpenView = form.getView().getAuthorizer().canOpenView(form.getView(), form, user);

            if (!canOpenView) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "You are not authorized to create a New Course Offering from " + courseCode + " Catalog Course Code");
                coWrapper.setAdminOrg(null);
                coWrapper.setCourse(null);
                coWrapper.setEnableCreateButton(false);

                return getUIFModelAndView(form);
            } else {
                // check if SOC state is "published"
                List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(term.getId(), contextInfo);
                if (socIds != null && !socIds.isEmpty()){
                    SocInfo soc = getCourseOfferingSetService().getSoc(socIds.get(0), contextInfo);
                    coWrapper.setSocInfo(soc);

                    //Get all the course offerings in a term
                    List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(), contextInfo);

                    coWrapper.setCourse(course);
                    coWrapper.setCreditCount(CourseOfferingViewHelperUtil.trimTrailing0(getLrcService().getResultValue(course.getCreditOptions().get(0).getResultValueKeys().get(0), contextInfo).getValue()));
                    coWrapper.setShowAllSections(true);
                    coWrapper.setShowCatalogLink(false);
                    coWrapper.setShowTermOfferingLink(true);

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

                    CourseOfferingCreateMaintainableImpl maintainable = (CourseOfferingCreateMaintainableImpl)KSControllerHelper.getViewHelperService(form);
                    maintainable.loadCourseJointInfos(coWrapper, form.getViewId());
                    //Enable the create button
                    coWrapper.setEnableCreateButton(true);
                } else {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
                }
            }
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

    /**
     * This is mapped to the <i>'Create from Catalog'</i> link
     *
     */
    @RequestMapping(params = "methodToCall=createFromCatalog")
    public ModelAndView createFromCatalog(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        wrapper.setShowCatalogLink(false);
        wrapper.setShowTermOfferingLink(true);

        return getUIFModelAndView(form);
    }

    /**
     * This is mapped to the <i>'Copy from existing'</i> link
     *
     */
    @RequestMapping(params = "methodToCall=copyExistingCourseOffering")
    public ModelAndView copyExistingCourseOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingInfo existingCO = ((ExistingCourseOffering) KSControllerHelper.getSelectedCollectionItem(form)).getCourseOfferingInfo();
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
        if (StringUtils.equals(existingCO.getTermId(), createWrapper.getTerm().getId())) {
            optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);
        } else {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("courseOfferingCode", existingCO.getCourseOfferingCode() + "%"),
                    PredicateFactory.equalIgnoreCase("atpId", createWrapper.getTerm().getId())));
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

        CourseOfferingInfo newCO = getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), contextInfo);
        KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_INFO_COPIED_SUCCESSFULLY, newCO.getCourseOfferingCode());
        return getUIFModelAndView(form);

    }

    @RequestMapping(params = "methodToCall=createFromTermOffering")
    public ModelAndView createFromTermOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCatalogLink(true);
        wrapper.setShowTermOfferingLink(false);

        return getUIFModelAndView(form);
    }

    /**
     * This is mapped to the link to to toggle between creating a new format offering or
     * copy from existing joint format offerings.
     *
     */
    @RequestMapping(params = "methodToCall=showCreateFormatSection")
    public ModelAndView showCreateFormatSection(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCreateFormatSection(true);
        wrapper.setShowCopyFormatSection(false);

        return getUIFModelAndView(form);
    }

    /**
     * This is mapped to the link to to toggle between creating a new format offering or
     * copy from existing joint format offerings.
     *
     */
    @RequestMapping(params = "methodToCall=showCopyFromJointOffering")
    public ModelAndView showCopyFromJointOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setShowCreateFormatSection(false);
        wrapper.setShowCopyFormatSection(true);

        return getUIFModelAndView(form);
    }

    /**
     * This will be called whenever the user selects/deselects a joint course. If user deselects a joint course, make sure
     * it doesnt have associated formats. If exists, display a confirmation dialog with all the available formats.
     *
     * XML reference at CourseOfferingCreateMaintenanceView.xml
     *
     */
    @RequestMapping(params = "methodToCall=markCourseForJointOffering")
    public ModelAndView markCourseForJointOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        int index = wrapper.getSelectedJointCourseIndex();
        if(form.getActionParameters().size() > 1)   {
            String lineIndex = form.getActionParameters().get("selectedLineIndex");
            index = Integer.parseInt(lineIndex);
            wrapper.setSelectedJointCourseIndex(index);
        }
        JointCourseWrapper joint = wrapper.getJointCourses().get(index);

        if (joint.isSelectedToJointlyOfferred()){
            String dialogName = CourseOfferingConstants.JOINT_COURSE_FORMATS_DELETE_DIALOG;
            
            if (!hasDialogBeenAnswered(dialogName, form)) {
                wrapper.setSelectedJointCourseCode(joint.getCourseCode());
                wrapper.setDialogFormatOfferingWrapperList(joint.getFormatOfferingWrappers());
                return showDialog(dialogName, form, request, response);
            }

            boolean dialogAnswer = getBooleanDialogResponse(dialogName, form, request, response);
            form.getDialogManager().resetDialogStatus(dialogName);

            if (dialogAnswer) {
                joint.setSelectedToJointlyOfferred(false);
                String jointCodes = StringUtils.remove(wrapper.getJointCourseCodes(), ", " + joint.getCourseCode());
                wrapper.setJointCourseCodes(jointCodes);
                wrapper.getFormatOfferingWrappers().removeAll(joint.getFormatOfferingWrappers());
            }

        } else {
            wrapper.setJointCourseCodes(wrapper.getJointCourseCodes() + ", " + joint.getCourseCode());
            joint.setSelectedToJointlyOfferred(true);
        }

        return getUIFModelAndView(form);
    }

    /**
     * Mapped to the <i>'Add'</i> button at the format section. This either copies from the user selected joint
     * offerings or create a new format.
     *
     */
    @RequestMapping(params = "methodToCall=addFormat")
        public ModelAndView addFormat(@ModelAttribute("KualiForm") MaintenanceDocumentForm form) throws Exception {

        CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        CourseOfferingCreateMaintainableImpl maintainable = (CourseOfferingCreateMaintainableImpl)KSControllerHelper.getViewHelperService(form);

        //If the user creates a new format
        if (wrapper.isShowCreateFormatSection()){
            maintainable.addFormatOffering(wrapper);
        } else { //If the user copies from existing joint formats
            maintainable.copyJointFormatOfferings(wrapper);
        }

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
        qpv1.setKey("lu.queryParam.startsWith.cluCode");
        qpv1.getValues().add(courseName.toUpperCase());
        searchParams.add(qpv1);

        SearchParamInfo qpv2 = new SearchParamInfo();
        qpv2.setKey("lu.queryParam.cluState");
        qpv2.setValues(Arrays.asList("Active"));
        searchParams.add(qpv2);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.cluByCodeAndState");

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

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
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

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }
}
