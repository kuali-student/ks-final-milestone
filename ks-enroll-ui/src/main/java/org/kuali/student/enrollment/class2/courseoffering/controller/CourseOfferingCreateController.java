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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.JointCourseWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CreateSocViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.CourseOfferingHistorySearchImpl;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;


/**
 * This is the controller class what handles all the requests (actions) from the <i>'create course offering'</i> ui.
 * <p>
 *      Wireframes at <a href="http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Course_Offering/v17/start.html">
 *          http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Course_Offering/v17/start.html</a> and
 *
 *       <a href="http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Complex%20Course%20Offerings/Sandbox/start.html">
 *           http://ux.ks.kuali.org.s3.amazonaws.com/wireframes/ENR/Complex%20Course%20Offerings/Sandbox/start.html</a>
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
    private CourseOfferingService courseOfferingService;
    private transient SearchService searchService;
    private transient TypeService typeService;
    private transient CourseOfferingSetService socService;

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

    private DefaultOptionKeysService defaultOptionKeysService;
    private DefaultOptionKeysService getDefaultOptionKeysService() {
        if (defaultOptionKeysService == null) {
            defaultOptionKeysService = new DefaultOptionKeysServiceImpl();
        }
        return this.defaultOptionKeysService;
    }

    /**
     * Initial method called when requesting a new view instance.
     *
     */
    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        setupMaintenance(maintenanceForm, request, KRADConstants.MAINTENANCE_NEW_ACTION);
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);

            // check if creating CO and populate the form
            String createCO = request.getParameter(CourseOfferingConstants.CREATE_COURSEOFFERING);
            if (createCO != null && createCO.equals("true")) {
                populateCreateCourseOfferingForm(maintenanceForm, request);
            }
            // done with creating CO
            String pageId = request.getParameter("pageId");
            if(pageId !=null && pageId.equals("courseOfferingCopyPage")){
                Object selectedObject =  maintenanceForm.getDocument().getNewMaintainableObject().getDataObject();
                if (selectedObject instanceof CourseOfferingCreateWrapper)  {
                    CourseOfferingCreateWrapper coCreateWrapper = (CourseOfferingCreateWrapper)selectedObject;
                    String targetTermCode = request.getParameter("targetTermCode");
                    if(targetTermCode !=null){
                        coCreateWrapper.setTargetTermCode(targetTermCode);
                        TermInfo term = getTerm(targetTermCode);
                        coCreateWrapper.setTerm(term);
                    }
                    String catalogCourseCode = request.getParameter("catalogCourseCode");
                    if(catalogCourseCode !=null){
                        coCreateWrapper.setCatalogCourseCode(catalogCourseCode);
                    }
                    String coId = request.getParameter("courseOfferingId");
                    if(coId != null){
                        try {
                            // configure context bar
                            SocInfo soc = getMainSocForTerm(coCreateWrapper.getTerm(), contextInfo);
                            coCreateWrapper.setSocInfo(soc);
                            coCreateWrapper.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(coCreateWrapper.getTerm(), coCreateWrapper.getSocInfo(),
                                    getStateService(), getAcalService(), contextInfo));

                            CourseOfferingInfo theCO = getCourseOfferingService().getCourseOffering(coId, contextInfo);
                            CourseOfferingEditWrapper coEditWrapper = new CourseOfferingEditWrapper(theCO);
                            TermInfo termInfo = getAcalService().getTerm(theCO.getTermId(), contextInfo);
                            coEditWrapper.setTerm(termInfo);
                            coEditWrapper.setGradingOption(getGradingOption(theCO.getGradingOptionId()));
                            // To prevent showing the same row twice in the table. It can be caused by pressing F5 key.
                            if (coCreateWrapper.getExistingTermOfferings().size() == 0) {
                                coCreateWrapper.getExistingTermOfferings().add(coEditWrapper);
                            }
                        }catch(Exception e){
                            LOG.warn("An Exception occurred but was not handled. ", e);
                        }
                    }
                }


            }
            checkViewAuthorization(form, methodToCall);
        }

        return getUIFModelAndView(maintenanceForm);
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
    public ModelAndView markCourseForJointOffering(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
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

    @RequestMapping(params = "methodToCall=continueFromCreate")
    public ModelAndView continueFromCreate(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject());
        String courseCode = coWrapper.getCatalogCourseCode();
        String termCode = coWrapper.getTargetTermCode();

        // check if term or course is empty
        if( StringUtils.isBlank(termCode) ) {
//            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.targetTermCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Term");
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Term");
        }
        if( StringUtils.isBlank(courseCode) ) {
//            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Course Code");
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Course Code");
        }
        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
            return getUIFModelAndView(form);
        }

        TermInfo term = getTerm(termCode);
        if (term == null) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_INVALID, termCode);
            return getUIFModelAndView(form);
        }

        coWrapper.setTerm(term);

        List<CourseInfo> matchingCourses = retrieveMatchingCourses(courseCode, term);
        coWrapper.clear();

        if (matchingCourses.size() == 1) {
            CourseInfo course = matchingCourses.get(0);

            // set organization IDs and check if the user is authorized to create a course
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
            Person user = GlobalVariables.getUserSession().getPerson();
            boolean canOpenView = form.getView().getAuthorizer().canOpenView(form.getView(), form, user);

            if (!canOpenView) {    // checking authz for course
//              GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_COURSE_RESTRICTED, courseCode);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_COURSE_RESTRICTED, courseCode);
                coWrapper.setAdminOrg(null);
                coWrapper.setCourse(null);

                return getUIFModelAndView(form);
            } else {
                // check if SOC state is "published"
                ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
                List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(term.getId(), contextInfo);
                if (socIds != null && !socIds.isEmpty()){
                    // check if user authz for the soc
                    SocInfo soc = getCourseOfferingSetService().getSoc(socIds.get(0), contextInfo);
                    coWrapper.setSocInfo(soc);
                    boolean canOpenViewSoc = form.getView().getAuthorizer().canOpenView(form.getView(), form, user);

                    if(!canOpenViewSoc) {   // check if user authz for the soc
//                      GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.targetTermCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_RESTRICTED);
                        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_TERM_RESTRICTED);
                        coWrapper.setSocInfo(null);

                        return getUIFModelAndView(form);
                    } else {
                        if (coWrapper.isCreateFromCatalog()) {
                            Properties urlParameters = _buildCOURLParameters(course.getId(), term.getId(), soc.getId(), KRADConstants.Maintenance.METHOD_TO_CALL_NEW);
                            return super.performRedirect(form, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_CREATE_MAINTENANCE, urlParameters);
                        } else {  // Copy part
                            //Get all the course offerings in a term
                            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(course.getId(), term.getId(), contextInfo);

                            coWrapper.setCourse(course);
                            coWrapper.setCreditCount(CourseOfferingViewHelperUtil.trimTrailing0(getLrcService().getResultValue(course.getCreditOptions().get(0).getResultValueKeys().get(0), contextInfo).getValue()));
                            coWrapper.setShowAllSections(true);
                            coWrapper.setShowCopyCourseOffering(false);
                            coWrapper.setShowTermOfferingLink(true);

                            coWrapper.setContextBar( CourseOfferingContextBar.NEW_INSTANCE(coWrapper.getTerm(), coWrapper.getSocInfo(),
                                    getStateService(), getAcalService(), contextInfo) );

                            coWrapper.getExistingTermOfferings().clear();
                            coWrapper.getExistingOfferingsInCurrentTerm().clear();

                            for (CourseOfferingInfo courseOfferingInfo : courseOfferingInfos) {
                                if (StringUtils.equals(courseOfferingInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)) {
                                    CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                                    co.setGradingOption(getGradingOption(courseOfferingInfo.getGradingOptionId()));
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
                                CourseOfferingEditWrapper co = new CourseOfferingEditWrapper(courseOfferingInfo);
                                TermInfo termInfo = getAcalService().getTerm(courseOfferingInfo.getTermId(), contextInfo);
                                co.setTerm(termInfo);
                                co.setGradingOption(getGradingOption(courseOfferingInfo.getGradingOptionId()));
                                coWrapper.getExistingTermOfferings().add(co);
                            }

                            //sort existing COs by term code
                            Collections.sort(coWrapper.getExistingTermOfferings(), new Comparator<CourseOfferingEditWrapper>() {
                                @Override
                                public int compare(CourseOfferingEditWrapper co1, CourseOfferingEditWrapper co2) {
                                    return co2.getTerm().getCode().compareTo(co1.getTerm().getCode());
                                }
                            });


                            CourseOfferingCreateMaintainableImpl maintainable = (CourseOfferingCreateMaintainableImpl)KSControllerHelper.getViewHelperService(form);
                            maintainable.loadCourseJointInfos(coWrapper, form.getViewId());
                            //Enable the create button
                            coWrapper.setEnableCreateButton(true);

                            return getUIFModelAndView(form, CourseOfferingConstants.COPY_COURSEOFFERING_PAGE);
                        }
                    }
                } else {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
                    return getUIFModelAndView(form);
                }
            }
        } else {
            if (matchingCourses.size() > 1) {
//              GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.catalogCourseCode", CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_MULTIPLE_COURSE_MATCHES, courseCode);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_MULTIPLE_COURSE_MATCHES, courseCode);
            } else if (matchingCourses.isEmpty()) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.ERROR_INVALID_CLU_VERSION, courseCode, termCode);
            }

            return getUIFModelAndView(form);
        }
    }

    @RequestMapping(params = "methodToCall=createFromCopy")
    public ModelAndView createFromCopy(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
         @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCreateWrapper createWrapper = (CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        CourseOfferingInfo existingCO = null;

        //the first CO that is selected or if there is only one, grab that one
        for(CourseOfferingEditWrapper eco : createWrapper.getExistingTermOfferings()){
            if(eco.getIsChecked() || createWrapper.getExistingTermOfferings().size() == 1){
                existingCO = eco.getCourseOfferingInfo();
                break;
            }
        }

        if(existingCO==null){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Selected Course");
            return getUIFModelAndView(form);
        }

        List<String> optionKeys = this.getDefaultOptionKeysService().getDefaultOptionKeysForCopySingleCourseOffering();

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

        //  According to Jira 4353 copy the title from CO instead of CLU. So set the property not to copy from CLU
        optionKeys.add(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY);
        SocRolloverResultItemInfo item = getCourseOfferingService().rolloverCourseOffering(existingCO.getId(),
                createWrapper.getTerm().getId(),
                optionKeys,
                contextInfo);

        CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), contextInfo);

        Properties urlParameters;
        urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "show");
        urlParameters.put("termCode",createWrapper.getTargetTermCode());
        urlParameters.put("inputCode",courseOfferingInfo.getCourseOfferingCode());
        urlParameters.put("viewId",CourseOfferingConstants.MANAGE_CO_VIEW_ID);
        urlParameters.put("pageId",CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        urlParameters.put("withinPortal","false");
        urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS);
        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, courseOfferingInfo.getCourseOfferingCode());

        return super.performRedirect(form, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH, urlParameters);
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
            List<TermInfo> terms = getAcalService().searchForTerms(qBuilder.build(), ContextUtils.createDefaultContextInfo());
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

    private CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private List<CourseInfo> retrieveMatchingCourses(String courseCode, TermInfo term) {

        CourseInfo returnCourseInfo;
        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        searchParams.add(new SearchParamInfo("lu_queryParam_code", courseCode.toUpperCase()));
        searchParams.add(new SearchParamInfo("lu_queryParam_someDate", DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(term.getStartDate())));

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.validCluForDate");

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

    private static Properties _buildCOURLParameters(String courseId, String termId, String socId, String methodToCall) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CourseOfferingConstants.COURSE_ID, courseId);
        props.put(CourseOfferingConstants.TARGET_TERM_ID, termId);
        props.put(CourseOfferingConstants.SOC_ID, socId);
        props.put(CourseOfferingConstants.CREATE_COURSEOFFERING, "true");
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }

    private SocInfo getMainSocForTerm(TermInfo term, ContextInfo contextInfo) throws Exception {
        List<String> socIds = getSocService().getSocIdsByTerm(term.getId(), contextInfo);
        List<SocInfo> socInfos = getSocService().getSocsByIds(socIds, contextInfo);
        for (SocInfo socInfo: socInfos) {
            if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                return socInfo;
            }
        }
        return null;
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

    protected SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    private CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = CourseOfferingResourceLoader.loadSocService();
        }
        return socService;
    }

}
