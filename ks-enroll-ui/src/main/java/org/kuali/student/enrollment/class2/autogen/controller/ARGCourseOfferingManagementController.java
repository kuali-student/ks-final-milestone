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
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.autogen.util.ARGToolbarUtil;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.util.ContextUtils;
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
 * @see ARGCourseOfferingManagementForm
 * @see org.kuali.student.enrollment.class2.autogen.service.impl.ARGCourseOfferingManagementViewHelperServiceImpl
 */
@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class ARGCourseOfferingManagementController extends UifControllerBase {

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
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {

        //If show is being called from another check that no validation errors were passed before reseting the form.
        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
            return getUIFModelAndView(form);
        }

        //Reset the form
        ARGUtil.clearForm(form);

        form.setInputCode(form.getInputCode().toUpperCase());
        ARGUtil.getViewHelperService(form).populateTerm(form);

        form.setContextBarTermCode(form.getTermCode());
        form.setContextBarSocState(form.getSocState());

        if (GlobalVariables.getMessageMap().getErrorCount() > 0) {
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

                ARGToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
            } else { // just one course offering is returned
                CourseOfferingListSectionWrapper coListWrapper = form.getCourseOfferingResultList().get(0);
                ARGUtil.prepareManageAOsModelAndView(form, coListWrapper);
                return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
            }
        }

        //turn on authz
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
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        CourseOfferingWrapper previousCOWrapper = form.getPreviousCourseOfferingWrapper();
        ARGUtil.prepare_AOs_RGs_AOCs_Lists(form, previousCOWrapper);
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
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        CourseOfferingWrapper coWrapper = form.getNextCourseOfferingWrapper();
        ARGUtil.prepare_AOs_RGs_AOCs_Lists(form, coWrapper);
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
    public ModelAndView loadAOs_RGs_AOCs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        if (ARGActivityOfferingClusterHandler.loadAOs_RGs_AOCs(form)) {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        } else {
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.copyCourseOfferingCreateCopy(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
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
     * <b>Usage at:</b> CourseOfferingManagement-ManageTheCourseOfferingPage.xml
     *
     * @param form model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm form) throws Exception {
        ARGCourseOfferingHandler.loadCOs(form);
        return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
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
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGActivityOfferingClusterHandler.deleteSelectedAoList(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
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
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteBulkCos")
    public ModelAndView cancelDeleteBulkCos(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGCourseOfferingHandler.cancelDeleteBulkCos(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_CO_PAGE);
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
            if (((CourseOfferingListSectionWrapper) selectedObject).isCrossListed()){
                urlParameters.put("editCrossListedCoAlias", BooleanUtils.toStringTrueFalse(true));
             } else {
                urlParameters.put("editCrossListedCoAlias", BooleanUtils.toStringTrueFalse(false));
             }
            return super.performRedirect(theForm, CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_BASE_MAINTENANCE, urlParameters);
        } else if (selectedObject instanceof ActivityOfferingWrapper) {
            Properties urlParameters = ARGActivityOfferingClusterHandler.editAO(theForm, ((ActivityOfferingWrapper) selectedObject).getAoInfo().getId());
            return super.performRedirect(theForm, "activityOffering", urlParameters);
        } else if (selectedObject instanceof RegistrationGroupWrapper){
            //Edit link from activity offering
            Properties urlParameters = ARGActivityOfferingClusterHandler.editAO(theForm, theForm.getActionParameters().get("aoId"));
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

        Properties urlParameters;
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
        String quantity = theForm.getNoOfActivityOfferings();
        if(!StringUtils.isEmpty(quantity) && StringUtils.isNumeric(quantity)){
            if(Integer.parseInt(quantity) < 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.ERROR, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_INVALID_ERROR);
            } else {
                ARGActivityOfferingClusterHandler.addActivityOfferings(theForm);
            }
        }
        else{
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.ERROR, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_INVALID_ERROR);
        }

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
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        ARGUtil.getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList(), theForm.getViewId(), theForm.getSocStateKey(), false);
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
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=approveAOs")
    public ModelAndView approveAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ARGUtil.getViewHelperService(theForm).approveActivityOfferings(theForm);
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendAOs")
    public ModelAndView suspendAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO suspendAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelAOs")
    public ModelAndView cancelAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //TODO: cancelAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateAOs")
    public ModelAndView reinstateAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: reinstateAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAOs")
    public ModelAndView deleteAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
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
    public ModelAndView draftAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ARGUtil.getViewHelperService(theForm).draftActivityOfferings(theForm);
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=addCluster")
    public ModelAndView addCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        return show(ARGActivityOfferingClusterHandler.createNewCluster(theForm));
    }

    @RequestMapping(params = "methodToCall=moveToCluster")
    public ModelAndView moveToCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        return show(ARGActivityOfferingClusterHandler.moveAOToACluster(theForm));
    }

    @RequestMapping(params = "methodToCall=copyAOs")
    public ModelAndView copyAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: copyAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAClusterThroughDialog")
    public ModelAndView deleteAClusterThroughDialog(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=RenameCluster")
    public ModelAndView openRenameAClusterPopup(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                    @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {


        return getUIFModelAndView(theForm, "KS-CourseOfferingManagement-RenameAOCPopupForm");
    }

    @RequestMapping(params = "methodToCall=showDeleteClusterConfirmPage")
    public ModelAndView showDeleteClusterConfirmPage(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ARGActivityOfferingClusterHandler.showDeleteClusterConfirmPage(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_ARG_DELETE_CLUSTER_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteClusterCascaded")
    public ModelAndView deleteClusterCascaded(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ARGActivityOfferingClusterHandler.deleteClusterCascaded(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelDeleteCluster")
    public ModelAndView cancelDeleteCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=dummyReload")
    public ModelAndView dummyReload (@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_THE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=renameAClusterThroughDialog")
    public ModelAndView renameAClusterThroughDialog(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                    @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Test for required entry
        if (theForm.getPrivateClusterNameForRenamePopover() == null || theForm.getPrivateClusterNameForRenamePopover().isEmpty()) {
            GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);

            return getUIFModelAndView(theForm);
        }

        if(ARGUtil._isClusterUniqueWithinCO(theForm, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingId(), theForm.getPrivateClusterNameForRenamePopover())){
            ARGActivityOfferingClusterHandler.renameAClusterThroughDialog(theForm);
            ActivityOfferingClusterWrapper selectedClusterWrapper;

            selectedClusterWrapper = (ActivityOfferingClusterWrapper)ARGUtil.getSelectedObject(theForm, "Rename Cluster");
            theForm.setSelectedCluster(selectedClusterWrapper);
                selectedClusterWrapper = theForm.getSelectedCluster();
                if (theForm.getSelectedCluster().getAoCluster().getPrivateName().equalsIgnoreCase(theForm.getPrivateClusterNameForRenamePopover()) || ARGUtil._isClusterUniqueWithinCO(theForm, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingId(), theForm.getPrivateClusterNameForRenamePopover())){
                    ActivityOfferingClusterInfo aoCluster = selectedClusterWrapper.getAoCluster();

                    aoCluster.setPrivateName(theForm.getPrivateClusterNameForRenamePopover());
                    aoCluster.setName(theForm.getPublishedClusterNameForRenamePopover());
                    aoCluster = ARGUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                            aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
                    selectedClusterWrapper.setAoCluster(aoCluster);
                    selectedClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");
            }
            theForm.setSelectedCluster(null);

            theForm.setPrivateClusterNameForRenamePopover("");
            theForm.setPublishedClusterNameForRenamePopover("");


        }  else {
                GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
        }
        return show(theForm);
    }

}
