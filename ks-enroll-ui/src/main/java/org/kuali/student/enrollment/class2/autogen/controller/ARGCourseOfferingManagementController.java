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
package org.kuali.student.enrollment.class2.autogen.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ToolbarUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/argCourseOfferingManagement")
public class ARGCourseOfferingManagementController extends UifControllerBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGCourseOfferingManagementController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ARGCourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase uifForm, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(uifForm instanceof ARGCourseOfferingManagementForm)) {
            throw new RuntimeException("Form object passed into start method was not of expected type ARGCourseOfferingManagementForm. Got " + uifForm.getClass().getSimpleName());
        }

        ARGCourseOfferingManagementForm form = (ARGCourseOfferingManagementForm) uifForm;

        // set adminOrg to the form to temporarily overcome that we actually need page level authorization but not view
        // level authorization.
        String inputValue = request.getParameter("adminOrg");
        if ((inputValue != null) && !inputValue.isEmpty()) {
            form.setAdminOrg(inputValue);
        }

        //clean up termCode, inputCode, and theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        form.setTermCode(null);
        form.setInputCode(null);
        form.setCurrentCourseOfferingWrapper(null);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
            form.setEditAuthz(ARGUtil.checkEditViewAuthz(form));
        }

        // check if the view is invoked within portal or not
        inputValue = request.getParameter("withinPortal");
        if ((inputValue != null) && !inputValue.isEmpty()) {
            boolean withinPortal = Boolean.valueOf(request.getParameter("withinPortal"));
            form.setWithinPortal(withinPortal);
        }

        return getUIFModelAndView(form);
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
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {

        form.setInputCode(form.getInputCode().toUpperCase());
        ARGUtil.getViewHelperService(form).populateTerm(form);

        if (GlobalVariables.getMessageMap().getErrorCount() > 1) {
            return getUIFModelAndView(form);
        }

        String inputCode = form.getInputCode();

        if (StringUtils.isBlank(inputCode)) {
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, form.getTermCode());
            form.getCourseOfferingResultList().clear();
            form.setActivityWrapperList(null);
            return getUIFModelAndView(form);
        }

        ARGUtil.getViewHelperService(form).loadCourseOfferingsByTermAndCourseCode(form.getTermInfo().getId(), inputCode, form);

        if (!form.getCourseOfferingResultList().isEmpty()) {
            if (form.getCourseOfferingResultList().size() > 1) {
                form.setSubjectCode(form.getCourseOfferingResultList().get(0).getSubjectArea());
                String longNameDescr = ARGUtil.getOrgNameDescription(form.getSubjectCode());
                form.setSubjectCodeDescription(longNameDescr);
                // Pull out the first CO from the result list and then pull out the org ids from this CO
                // and pass in the first one as the adminOrg
//                CourseOfferingInfo firstCO = getCourseOfferingService().getCourseOffering(form.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                List<String> orgIds = firstCO.getUnitsDeploymentOrgIds();
//                if(orgIds !=null && !orgIds.isEmpty()){
//                    form.setAdminOrg(orgIds.get(0));
//                }

                ToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
                //ToolbarUtil.processCoToolbarForCentralAdmin(form.getCourseOfferingResultList(), form);
            } else { // just one course offering is returned
                CourseOfferingListSectionWrapper coListWrapper = form.getCourseOfferingResultList().get(0);
                ARGUtil.prepareManageAOsModelAndView(form, coListWrapper);
                return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_AO_PAGE);
            }
        }

        form.setEditAuthz(ARGUtil.checkEditViewAuthz(form));

        if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        } else {
            return getUIFModelAndView(form, CourseOfferingConstants.SEARCH_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put("coInfo.id", theCourseOfferingInfo.getId());
        urlParameters.put(UifParameters.VIEW_ID, RegistrationGroupConstants.RG_VIEW);
        urlParameters.put(UifParameters.PAGE_ID, RegistrationGroupConstants.RG_PAGE);
        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
//        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put("withinPortal", BooleanUtils.toStringTrueFalse(theForm.isWithinPortal()));
        String controllerPath = RegistrationGroupConstants.RG_CONTROLLER_PATH;

        //set the redirection param to carry over view information to RG view for customized breadcrumb generation
        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH);
        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY, CourseOfferingConstants.MANAGE_CO_VIEW_ID);
        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY, theForm.getFormHistory().getHomewardPath().get(0).getUrl());
        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, theForm.getFormHistory().getHistoryParameterString());
        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY, theForm.getFormKey());

        return super.performRedirect(theForm, controllerPath, urlParameters);

    }

    /**
     * Loads the previous course offering
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        ARGCourseOfferingHandler.loadPreviousCO(form);
        return getUIFModelAndView(form);
    }

    /**
     * Loads the next course offering.
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        return getUIFModelAndView(form);
    }

    /**
     * This is called when the user click on the <i>Manage</i> link at the CO search result.
     * <p/>
     * <b>Usage at</b>: <i>CourseOfferingManagement-ManageCourseOfferingsPage.xml</i>
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        if (ARGActivityOfferingClusterHandler.loadAOs(form)) {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        } else {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_CO_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.copyCourseOfferingCreateCopy(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.copyCourseOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.COPY_CO_PAGE);
    }


    @RequestMapping(params = "methodToCall=selectAllActivityOfferings")
    public ModelAndView selectAllActivityOfferings(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGActivityOfferingClusterHandler.selectAllActivityOfferings(theForm);
        return getUIFModelAndView(theForm);
    }


    /**
     * This is mapped to the <i>List All</i> link.
     * <p/>
     * <b>Usage at:</b> CourseOfferingManagement-ManageActivityOfferingsPage.xml
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        ARGCourseOfferingHandler.loadCOs(form);
        return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) {
        ARGActivityOfferingClusterHandler.copyAO(form);
        return getUIFModelAndView(form);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGUtil.reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGActivityOfferingClusterHandler.deleteSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=deleteCoConfirmation")
    public ModelAndView deleteCoConfirmation(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        String viewName = ARGCourseOfferingHandler.deleteCoConfirmation(theForm);
        return getUIFModelAndView(theForm, viewName);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteBulkCos")
    public ModelAndView deleteBulkCos(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.deleteBulkCos(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteBulkCos")
    public ModelAndView cancelDeleteBulkCos(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.cancelDeleteBulkCos(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
    * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
    * Code (04a screen)
    */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        Properties urlParameters = ARGCourseOfferingHandler.editTheCO(theForm);
        return super.performRedirect(theForm, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_BASE_MAINTENANCE, urlParameters);
    }

    /*
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        Object selectedObject = ARGUtil.getSelectedObject(theForm, "edit");

        if (selectedObject instanceof CourseOfferingListSectionWrapper) {
            Properties urlParameters = ARGCourseOfferingHandler.edit(theForm, (CourseOfferingListSectionWrapper) selectedObject);
            return super.performRedirect(theForm, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_BASE_MAINTENANCE, urlParameters);
        } else if (selectedObject instanceof ActivityOfferingWrapper) {
            Properties urlParameters = ARGActivityOfferingClusterHandler.edit(theForm, (ActivityOfferingWrapper) selectedObject);
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
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        boolean canDelete = ARGActivityOfferingClusterHandler.confirmDelete(theForm);
        if(!canDelete) {
            return getUIFModelAndView(theForm);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    /*
     * Method used to view a CO or an AO
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "inquiry";
        Object selectedObject = ARGUtil.getSelectedObject(theForm, "view");

        if (selectedObject instanceof CourseOfferingInfo) {
            urlParameters = ARGCourseOfferingHandler.view(theForm, (CourseOfferingInfo)selectedObject);
        } else if (selectedObject instanceof ActivityOfferingWrapper) {
            urlParameters = ARGActivityOfferingClusterHandler.view(theForm, (ActivityOfferingWrapper) selectedObject);
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        ARGActivityOfferingClusterHandler.addActivityOfferings(theForm);
        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=createCourseOffering")
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        Properties props = ARGCourseOfferingHandler.createCourseOffering(theForm);
        String controllerPath = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_CREATE_MAINTENANCE;
        return super.performRedirect(theForm, controllerPath, props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl) theForm.getView().getViewHelperService();
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        helperService.markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList(), false);
        ARGUtil.getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(), theForm);
        return getUIFModelAndView(theForm);
    }

    /*
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = ARGUtil._buildCOURLParameters(theCourseOfferingInfo, KRADConstants.START_METHOD);
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
        return super.performRedirect(theForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=approveCOs")
    public ModelAndView approveCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String dialogName = CourseOfferingConstants.ConfirmDialogs.APRROVE_CO;

        if (!hasDialogBeenAnswered(dialogName, theForm)) {
            return showDialog(dialogName, theForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, theForm, request, response);
        theForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {
            ARGUtil.getViewHelperService(theForm).approveCourseOfferings(theForm);
            ARGUtil.reloadCourseOfferings(theForm);
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=suspendCOs")
    public ModelAndView suspendCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  suspendCOs

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=cancelCOs")
    public ModelAndView cancelCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  cancelCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=reinstateCOs")
    public ModelAndView reinstateCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {


        //TODO: reinstateCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=deleteOneCoWithLink")
    public ModelAndView deleteOneCoWithLink(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ARGCourseOfferingHandler.deleteOneCoWithLink(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteCOs")
    public ModelAndView deleteCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ARGUtil.getViewHelperService(theForm).deleteCourseOfferings(theForm);
        //reloadCourseOfferings(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=approveAOs")
    public ModelAndView approveAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ARGUtil.getViewHelperService(theForm).approveActivityOfferings(theForm);
        ARGUtil.reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendAOs")
    public ModelAndView suspendAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO suspendAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelAOs")
    public ModelAndView cancelAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //TODO: cancelAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateAOs")
    public ModelAndView reinstateAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: reinstateAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAOs")
    public ModelAndView deleteAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ARGActivityOfferingClusterHandler.deleteAOs(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=draftAOs")
    public ModelAndView draftAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ARGUtil.getViewHelperService(theForm).draftActivityOfferings(theForm);
        ARGUtil.reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=addCluster")
    public ModelAndView addCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: addCluster
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=moveToCluster")
    public ModelAndView moveToCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: moveToCluster
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyAOs")
    public ModelAndView copyAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: copyAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

}
