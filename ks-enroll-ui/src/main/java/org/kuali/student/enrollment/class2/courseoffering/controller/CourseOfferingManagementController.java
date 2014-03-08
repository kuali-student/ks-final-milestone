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
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementToolbarUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;

/**
 * This is the controller class what handles all the requests (actions) from the <i>'Manage Course Offering'</i> ui.
 *
 * @see CourseOfferingManagementForm
 * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl
 */
@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(CourseOfferingManagementController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase uifForm, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(uifForm instanceof CourseOfferingManagementForm)) {
            throw new RuntimeException("Form object passed into start method was not of expected type CourseOfferingManagementForm. Got " + uifForm.getClass().getSimpleName());
        }

        CourseOfferingManagementForm form = (CourseOfferingManagementForm) uifForm;

        // set adminOrg to the form to temporarily overcome that we actually need page level authorization but not view
        // level authorization.
        String inputValue = request.getParameter("adminOrg");
        if ((inputValue != null) && !inputValue.isEmpty()) {
            form.setAdminOrg(inputValue);
        }

        //TODO: Workaround for KRMS return
        if(!( form.getMethodToCall().contains("edit")
                || form.getMethodToCall().contains("refresh")
                || form.getMethodToCall().contains("manageCORequisites") ))
        {
            form.setCurrentCourseOfferingWrapper(null);
        }

        //Refresh AO list on KRMS return
        CourseOfferingManagementUtil.getViewHelperService(form).setupRuleIndicator(form.getActivityWrapperList());
        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
            form.setEditAuthz(CourseOfferingManagementUtil.checkEditViewAuthz(form));
        }

        // check if the view is invoked within portal or not
        inputValue = request.getParameter("withinPortal");
        if ((inputValue != null) && !inputValue.isEmpty()) {
            boolean withinPortal = Boolean.valueOf(request.getParameter("withinPortal"));
            form.setWithinPortal(withinPortal);
        }

        // This is workaround to fix stack trace while refreshing manage CO pages.
        if ((!form.getCourseOfferingResultList().isEmpty()) && (form.getCourseOfferingResultList().size() == 1))  { // just one course offering is returned
            try {
                CourseOfferingListSectionWrapper coListWrapper = KSCollectionUtils.getRequiredZeroElement(form.getCourseOfferingResultList());
                CourseOfferingManagementUtil.prepareManageAOsModelAndView(form, coListWrapper);
                } catch (Exception e) {
                LOG.error("Exception occurred", e);
                throw new RuntimeException(e);
                }
                return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        }
        return getUIFModelAndView(form);
    }


    @Override
    public ModelAndView refresh(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Refresh AO list on KRMS return
        CourseOfferingManagementForm form = (CourseOfferingManagementForm) uifForm;
        CourseOfferingManagementUtil.getViewHelperService(form).setupRuleIndicator(form.getActivityWrapperList());
        return super.refresh(form, result, request, response);
    }

    /**
     * <p>
     * <p/>
     * Method used to
     * 1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     * don't find any termInfo, log and report an error message.
     * 2) If the input is subject code, load all course offerings based on termId and subjectCode
     * 3) If the input is course offering code,
     * a)find THE course offering based on termId and courseOfferingCode. If find more than one CO or don't find
     * any CO, log and report an error message.
     * b)load all activity offerings based on the courseOfferingId
     * <p/>
     * </p>
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        //If show is being called from another check that no validation errors were passed before resetting the form.
        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
            return getUIFModelAndView(form);
        }

        validateUserPopulatedTermAndCourseFields( form );
        if( GlobalVariables.getMessageMap().hasErrors() ) {
            return getUIFModelAndView( form );
        }

        //Reset the form (not including term- and course-codes though)
        CourseOfferingManagementUtil.clearForm(form);

        // convert term-code to UPPERCASE
        form.setInputCode( form.getInputCode().toUpperCase() );

        CourseOfferingManagementUtil.getViewHelperService(form).populateTerm(form);
        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
            return getUIFModelAndView(form, CourseOfferingConstants.SEARCH_PAGE);
        }

        CourseOfferingManagementUtil.getViewHelperService(form).loadCourseOfferingsByTermAndCourseCode(form.getTermInfo(), form.getInputCode(), form);

        //turn on authz
        form.setEditAuthz(CourseOfferingManagementUtil.checkEditViewAuthz(form));

        if (!form.getCourseOfferingResultList().isEmpty()) {
            if (form.getCourseOfferingResultList().size() > 1) {
                form.setSubjectCode(form.getCourseOfferingResultList().get(0).getSubjectArea());
                String longNameDescr = CourseOfferingManagementUtil.getOrgNameDescription(form.getSubjectCode());
                form.setSubjectCodeDescription(longNameDescr);

                CourseOfferingManagementToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
            } else { // just one course offering is returned
                CourseOfferingListSectionWrapper coListWrapper = form.getCourseOfferingResultList().get(0);
                CourseOfferingManagementUtil.prepareManageAOsModelAndView(form, coListWrapper);
                return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
            }
        }

        if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        } else {
            return getUIFModelAndView(form, CourseOfferingConstants.SEARCH_PAGE);
        }

    }

    private void validateUserPopulatedTermAndCourseFields( CourseOfferingManagementForm form ) {

        String termCode = form.getTermCode();
        if( StringUtils.isBlank(termCode) ) {
            GlobalVariables.getMessageMap().putError( "termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_TERMCODE_IS_REQUIRED, "Term", termCode );
        }

        String courseCode = form.getInputCode();
        if( StringUtils.isBlank(courseCode) ) {
            GlobalVariables.getMessageMap().putError( "inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_COURSECODE_IS_REQUIRED, "Course", courseCode );
        }

    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put("coInfo.id", theCourseOfferingInfo.getId());
        urlParameters.put(UifParameters.VIEW_ID, RegistrationGroupConstants.RG_VIEW);
        urlParameters.put(UifParameters.PAGE_ID, RegistrationGroupConstants.RG_PAGE);
        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put("withinPortal", BooleanUtils.toStringTrueFalse(theForm.isWithinPortal()));
        String controllerPath = RegistrationGroupConstants.RG_CONTROLLER_PATH;

        //set the redirection param to carry over view information to RG view for customized breadcrumb generation
        return super.performRedirect(theForm, controllerPath, urlParameters);

    }

    /**
     * Loads the previous course offering
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {
        CourseOfferingWrapper previousCOWrapper = form.getPreviousCourseOfferingWrapper();
        CourseOfferingManagementUtil.prepare_AOs_RGs_AOCs_Lists(form, previousCOWrapper);
        return getUIFModelAndView(form);
    }

    /**
     * Loads the next course offering.
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {
        CourseOfferingWrapper coWrapper = form.getNextCourseOfferingWrapper();
        CourseOfferingManagementUtil.prepare_AOs_RGs_AOCs_Lists(form, coWrapper);
        return getUIFModelAndView(form);
    }

    /**
     * This is called when the user click on the <i>Manage</i> link at the CO search result.
     * <p/>
     * <b>Usage at</b>: <i>CourseOfferingManagement-ManageCourseOfferingsPage.xml</i>
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadAOs_RGs_AOCs")
    public ModelAndView loadAOs_RGs_AOCs(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {
        if (ActivityOfferingClusterHandler.loadAOs_RGs_AOCs(form)) {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        } else {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingHandler.copyCourseOfferingCreateCopy(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if (selectedObject instanceof CourseOfferingListSectionWrapper) {

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());

            int selectedLineIndex = -1;
            String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            if (StringUtils.isNotBlank(selectedLine)) {
                selectedLineIndex = Integer.parseInt(selectedLine);
            }
            String courseOfferingCode = theForm.getCourseOfferingResultList().get(selectedLineIndex).getCourseOfferingCode();

            Properties urlParameters = new Properties();
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingCreateWrapper.class.getName());
            urlParameters.put("pageId", "courseOfferingCopyPage");
            urlParameters.put("targetTermCode",  theForm.getTermCode());
            urlParameters.put("catalogCourseCode", courseOfferingCode);
            urlParameters.put("courseOfferingId", courseOfferingInfo.getId());

            return super.performRedirect(theForm, "courseOfferingCreate", urlParameters);
        }
        else {
            //must be an error
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        }
    }


    @RequestMapping(params = "methodToCall=selectAllActivityOfferings")
    public ModelAndView selectAllActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        ActivityOfferingClusterHandler.selectAllActivityOfferings(theForm);
        return getUIFModelAndView(theForm);
    }


    /**
     * This is mapped to the <i>List All</i> link.
     * <p/>
     * <b>Usage at:</b> CourseOfferingManagement-ManageTheCourseOfferingPage.xml
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {
        CourseOfferingHandler.loadCOs(form);
        return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    /*
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) {
        ActivityOfferingClusterHandler.copyAO(form);
        return getUIFModelAndView(form);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
     * Method used to refresh Manage CO page (e.g. after edit AO)
     */
    @RequestMapping(params = "methodToCall=reloadManageCO")
    public ModelAndView reloadManageCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        theForm.setFormatOfferingIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setClusterIdForNewAO(null);
        CourseOfferingManagementUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        ActivityOfferingClusterHandler.deleteSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
    * Method used to cancel a list of selected Activity Offerings (that can be in Draft or Approved or Suspended state)
    */
    @RequestMapping(params = "methodToCall=cancelSelectedAoList")
    public ModelAndView cancelSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        ActivityOfferingClusterHandler.cancelSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendSelectedAoList")
    public ModelAndView suspendSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        theForm.setActionCSR(ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SUSPEND);
        ActivityOfferingClusterHandler.suspendSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateSelectedAoList")
    public ModelAndView reinstateSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        theForm.setActionCSR(ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_REINSTATE);
        ActivityOfferingClusterHandler.reinstateSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=deleteCoConfirmation")
    public ModelAndView deleteCoConfirmation(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        String viewName = CourseOfferingHandler.deleteCoConfirmation(theForm);
        return getUIFModelAndView(theForm, viewName);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteBulkCos")
    public ModelAndView deleteBulkCos(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingHandler.deleteBulkCos(theForm);
        CourseOfferingHandler.loadCOs(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteBulkCos")
    public ModelAndView cancelDeleteBulkCos(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingHandler.cancelDeleteBulkCos(theForm);
        if(theForm.getCurrentCourseOfferingWrapper() == null) {
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
    * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
    * Code (04a screen)
    */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        Properties urlParameters = CourseOfferingHandler.editTheCO(theForm);
        return super.performRedirect(theForm, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_EDIT_MAINTENANCE, urlParameters);
    }

    /*
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(theForm, "edit");

        if (selectedObject instanceof CourseOfferingListSectionWrapper) {
            Properties urlParameters = CourseOfferingHandler.edit(theForm, (CourseOfferingListSectionWrapper) selectedObject);
            if (((CourseOfferingListSectionWrapper) selectedObject).isCrossListed()){
                urlParameters.put("editCrossListedCoAlias", BooleanUtils.toStringTrueFalse(true));
             } else {
                urlParameters.put("editCrossListedCoAlias", BooleanUtils.toStringTrueFalse(false));
             }
            return super.performRedirect(theForm, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_EDIT_MAINTENANCE, urlParameters);
        } else if (selectedObject instanceof ActivityOfferingWrapper) {
            Properties urlParameters = ActivityOfferingClusterHandler.editAO(theForm, ((ActivityOfferingWrapper) selectedObject).getAoInfo().getId());
            return super.performRedirect(theForm, "activityOffering", urlParameters);
        } else if (selectedObject instanceof RegistrationGroupWrapper){
            //Edit link from activity offering
            Properties urlParameters = ActivityOfferingClusterHandler.editAO(theForm, theForm.getActionParameters().get("aoId"));
            return super.performRedirect(theForm, "activityOffering", urlParameters);
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }
    }

    /*
     *  Determine if any AOs were check-boxed.
     *  @return True if any AOs where selected. Otherwise, false.
     */
// _buildCOURLParameters

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=confirmDelete")
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        boolean canDelete = ActivityOfferingClusterHandler.confirmDelete(theForm);
        if(!canDelete) {
            return getUIFModelAndView(theForm);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    /*
     * Method used to view a CO or an AO
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters;
        String controllerPath = "inquiry";
        Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(theForm, "view");

        if (selectedObject instanceof CourseOfferingInfo) {
            urlParameters = CourseOfferingHandler.view(theForm, (CourseOfferingInfo) selectedObject);
        } else if (selectedObject instanceof ActivityOfferingWrapper) {
            urlParameters = ActivityOfferingClusterHandler.view(theForm, (ActivityOfferingWrapper) selectedObject);
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        String quantity = theForm.getNoOfActivityOfferings();
        // KSENROLL-6893 - Added code to default the value "1" for number of activity offering to add in the pop-over
        if (StringUtils.isEmpty(quantity)) {
            quantity = "1";
            theForm.setNoOfActivityOfferings(quantity);
        }
        if (StringUtils.isNumeric(quantity)) {
            ActivityOfferingClusterHandler.addActivityOfferings(theForm);
        } else {
            GlobalVariables.getMessageMap().putError("addActivityOfferingQuantity", CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_INVALID_ERROR);
        }

        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=createCourseOffering")
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        Properties props = CourseOfferingHandler.createCourseOffering(theForm);
        String controllerPath = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_CREATE_MAINTENANCE;
        return super.performRedirect(theForm, controllerPath, props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        CourseOfferingManagementUtil.getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList(), theForm.getViewId(), theForm.getSocStateKey(), false);
        CourseOfferingManagementUtil.getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(), theForm);
        return getUIFModelAndView(theForm);
    }

    /*
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = CourseOfferingManagementUtil._buildCOURLParameters(theCourseOfferingInfo, KRADConstants.START_METHOD);
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
        return super.performRedirect(theForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=approveCOs")
    public ModelAndView approveCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

       CourseOfferingManagementUtil.getViewHelperService(theForm).approveCourseOfferings(theForm);
       CourseOfferingManagementUtil.reloadCourseOfferings(theForm);

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=suspendCOs")
    public ModelAndView suspendCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  suspendCOs

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=cancelCOs")
    public ModelAndView cancelCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  cancelCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=reinstateCOs")
    public ModelAndView reinstateCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {


        //TODO: reinstateCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=deleteOneCoWithLink")
    public ModelAndView deleteOneCoWithLink(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingHandler.deleteOneCoWithLink(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteCOs")
    public ModelAndView deleteCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingManagementUtil.getViewHelperService(theForm).deleteCourseOfferings(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=approveAOs")
    public ModelAndView approveAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingManagementUtil.getViewHelperService(theForm).approveActivityOfferings(theForm);
        CourseOfferingManagementUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelAOs")
    public ModelAndView cancelAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        theForm.setActionCSR(ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_CANCEL);
        CourseOfferingHandler.prepareCSRConfirmationView(theForm, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_CANCEL, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_SUSPEND);
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_CSR_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendAOs")
    public ModelAndView suspendAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        theForm.setActionCSR(ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SUSPEND);
        CourseOfferingHandler.prepareCSRConfirmationView(theForm, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_SUSPEND, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_SUSPEND);
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_CSR_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateAOs")
    public ModelAndView reinstateAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        theForm.setActionCSR(ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_REINSTATE);
        CourseOfferingHandler.prepareCSRConfirmationView(theForm, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_REINSTATE, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_REINSTATE);
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_CSR_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAOs")
    public ModelAndView deleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        CourseOfferingWrapper currentCoWrapper = theForm.getCurrentCourseOfferingWrapper();
        currentCoWrapper.setColocatedAoToDelete(false);

        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;

        selectedIndexList.clear();
        for(ActivityOfferingWrapper ao : aoList) {

            if(ao.isEnableDeleteButton() && ao.getIsCheckedByCluster()) {
                ao.setActivityCode(ao.getAoInfo().getActivityCode());
                selectedIndexList.add(ao);
                if(ao.isColocatedAO())  {
                    currentCoWrapper.setColocatedAoToDelete(true);
                }
                enabled++;
            } else if (ao.getIsCheckedByCluster()){
                checked++;
                if (!bNoDeletion) {
                    bNoDeletion = true;
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bNoDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=draftAOs")
    public ModelAndView draftAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingManagementUtil.getViewHelperService(theForm).draftActivityOfferings(theForm);
        CourseOfferingManagementUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=addCluster")
    public ModelAndView addCluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        return show(ActivityOfferingClusterHandler.createNewCluster(theForm));
    }

    @RequestMapping(params = "methodToCall=moveToCluster")
    public ModelAndView moveToCluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        return show(ActivityOfferingClusterHandler.moveAOToACluster(theForm));
    }

    @RequestMapping(params = "methodToCall=copyAOs")
    public ModelAndView copyAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: copyAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAClusterThroughDialog")
    public ModelAndView deleteAClusterThroughDialog(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=RenameCluster")
    public ModelAndView openRenameAClusterPopup(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                    @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {


        return getUIFModelAndView(theForm, "KS-CourseOfferingManagement-RenameAOCPopupForm");
    }

    @RequestMapping(params = "methodToCall=showDeleteClusterConfirmPage")
    public ModelAndView showDeleteClusterConfirmPage(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingClusterHandler.showDeleteClusterConfirmPage(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_DELETE_CLUSTER_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteClusterCascaded")
    public ModelAndView deleteClusterCascaded(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingClusterHandler.deleteClusterCascaded(theForm);
        // Because everything in this method is inside a transaction we need to separate the delete and the render. If we don't the render
        // will not detect the delete and the user is shown the same screen. So for this case, delete the cluster then call
        // show, which will clean up the form and render outside the transaction.
        return show(theForm);
    }

    @RequestMapping(params = "methodToCall=cancelDeleteCluster")
    public ModelAndView cancelDeleteCluster(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=dummyReload")
    public ModelAndView dummyReload (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=renameAClusterThroughDialog")
    public ModelAndView renameAClusterThroughDialog(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        /* Indicate that the search used to redraw the page after this operation completes should use an exact
         * match of the course code rather than the usual "like" criteria. Otherwise, if the course code matches multiple
         * items (e.g. CHEM100, CHEM100A, CHEM100B) then the Manage multiple COs page will be displayed rather than Manage
         * individual CO page. */
        theForm.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());

        //  Test for required private name.
        String requestedPrivateName = theForm.getPrivateClusterNameForRenamePopover();
        if (StringUtils.isBlank(requestedPrivateName)) {
            GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
            return getUIFModelAndView(theForm);
        }

        //  Find the AOClusterWrapper that was changed and add it to the form.
        ActivityOfferingClusterWrapper selectedClusterWrapper = (ActivityOfferingClusterWrapper) CourseOfferingManagementUtil.getSelectedObject(theForm, "Rename Cluster");
        theForm.setSelectedCluster(selectedClusterWrapper);

        String coId = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingId();
        String currentPrivateName =  theForm.getSelectedCluster().getAoCluster().getPrivateName();
        String currentName = theForm.getSelectedCluster().getAoCluster().getName();
        String requestedName = theForm.getPublishedClusterNameForRenamePopover();

        boolean hasChange = false;
        //  If the private name has changed then check for uniqueness.
        if (! StringUtils.equals(currentPrivateName, requestedPrivateName)) {
            if ( ! CourseOfferingManagementUtil._isClusterUniqueWithinCO(theForm, coId, requestedPrivateName)) {
                GlobalVariables.getMessageMap().putError("KS-CourseOfferingManagement-AOClustersCollection", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME, requestedPrivateName);
                return show(theForm);
            }
            hasChange = true;
        }

        //  Check for name change
        if (! hasChange && ! StringUtils.equals(currentName, requestedName)) {
            hasChange = true;
        }

        //  If there is a change then save it.
        if (hasChange) {
            ActivityOfferingClusterInfo aoCluster = selectedClusterWrapper.getAoCluster();
            aoCluster.setPrivateName(requestedPrivateName);
            aoCluster.setName(requestedName);
            try {
                aoCluster = CourseOfferingManagementUtil.getCourseOfferingService().updateActivityOfferingCluster(aoCluster.getFormatOfferingId(),
                    aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.CLUSTER_RENAME_SUCCESS);
                selectedClusterWrapper.setAoCluster(aoCluster);
                selectedClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");
            } catch(Exception e) {
                LOG.error("AO Cluster save failed.", e);
                GlobalVariables.getMessageMap().putError("KS-CourseOfferingManagement-ViewAOClustersSection", RiceKeyConstants.ERROR_CUSTOM, "Save failed: " + e.getLocalizedMessage());
            }
        } //  else ... If no changes were made just silently continue.

        theForm.setSelectedCluster(null);
        theForm.setPrivateClusterNameForRenamePopover("");
        theForm.setPublishedClusterNameForRenamePopover("");

        return show(theForm);
    }

    /**
     * This is mapped to the <i>List All</i> link.
     * <p/>
     * <b>Usage at:</b> CourseOfferingManagement-ManageTheCourseOfferingPage.xml
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=manageAORules")
    public ModelAndView manageAORules(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(form, "manageAORules");

     if (selectedObject instanceof ActivityOfferingWrapper) {
            Properties urlParameters = ActivityOfferingClusterHandler.manageAO(form, ((ActivityOfferingWrapper) selectedObject).getAoInfo().getId());
            return super.performRedirect(form, "activityOfferingRules", urlParameters);
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }
    }

    /**
     * Show the exam offerings.
     *
     * @param theForm
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=viewExamOfferings")
    public ModelAndView viewExamOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingManagementUtil.getViewHelperService(theForm).loadExamOfferings(theForm);
        return getUIFModelAndView(theForm, "viewExamOfferingsPage");
    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        CourseOfferingManagementForm theForm = (CourseOfferingManagementForm) form;

        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "show");
        urlParameters.put("viewId", "courseOfferingManagementView");
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfo.class.getName());
        urlParameters.put(KRADConstants.HIDE_LOOKUP_RETURN_LINK, "true");
        urlParameters.put("inputCode", theForm.getCurrentCourseOfferingWrapper().getCourseOfferingCode());
        urlParameters.put("termCode", theForm.getCurrentCourseOfferingWrapper().getTerm().getCode());
        urlParameters.put("pageId", "manageTheCourseOfferingPage");

        return super.performRedirect(form, "courseOfferingManagement", urlParameters);
    }

}
