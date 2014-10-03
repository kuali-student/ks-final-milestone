/*
 * Copyright 2006-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class2.registration.admin.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.io.SerializationUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResult;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResultItem;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegClientCache;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * <p/>
 * Controller for Admin Registration.
 */
@Controller
@RequestMapping(value = "/adminreg")
public class AdminRegistrationController extends UifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(AdminRegistrationController.class);

    ///////////////////////////////////////////////
    //Initialization methods
    //////////////////////////////////////////////

    @Override
    protected AdminRegistrationForm createInitialForm(HttpServletRequest request) {
        return new AdminRegistrationForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=refresh")
    public ModelAndView refresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        if (!(form instanceof AdminRegistrationForm)){
            throw new RuntimeException("Unexpected type: " + form);
        }
        AdminRegistrationForm adminForm = (AdminRegistrationForm) form;
        if(AdminRegConstants.REG_COLL_ID.equals(form.getUpdateComponentId())) {
            adminForm.setRegisteredCourses(getViewHelper(form).getCourseRegForStudentAndTerm(adminForm.getPerson().getId(), adminForm.getTerm().getId()));
        } else if(AdminRegConstants.WAITLIST_COLL_ID.equals(form.getUpdateComponentId())) {
            adminForm.setWaitlistedCourses(getViewHelper(form).getCourseWaitListForStudentAndTerm(adminForm.getPerson().getId(), adminForm.getTerm().getId()));
        }
        return super.refresh(form, result, request, response);
    }

    /**
     * This method is called when the user has added or changed a course code in the input section when adding a new
     * pending course to update the course title. It is called via AJAX on a conditional property refresh.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see org.kuali.student.enrollment.class2.registration.admin.service.impl.AdminRegistrationViewHelperServiceImpl#retrieveCourseTitle
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshCourseTitle")
    public ModelAndView refreshCourseTitle(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        return super.refresh(form, result, request, response);
    }

    /**
     * This method is called when the user has added or changed a course code in the input section when adding a new
     * pending course to update the course title. It is called via AJAX on a conditional property refresh.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see org.kuali.student.enrollment.class2.registration.admin.service.impl.AdminRegistrationViewHelperServiceImpl#retrieveCourseTitle
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshRegistrationResults")
    public ModelAndView refreshRegistrationResults(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        // Reset the form to ready state.
        form.setRegRequestId(null);
        if (form.getCoursesInProcess() != null) {
            form.getCoursesInProcess().clear();
        }
        form.setPendingDropCourse(null);
        if (form.getCoursesInEdit() != null) {
            form.getCoursesInEdit().clear();
        }
        form.getEditingIssues().clear();
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return super.refresh(form, result, request, response);
    }

    ///////////////////////////////////////////////
    //Student and Term methods
    //////////////////////////////////////////////

    /**
     * This method is called when the user has entered a student id and a term code to get the studentInfo for the selected term
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getStudentAndTermInfo")
    public ModelAndView getStudentAndTermInfo(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        form.clearCourseRegistrationValues();

        form.setPerson(this.getViewHelper(form).getStudentById(form.getPersonId()));
        form.setTerm(this.getViewHelper(form).getTermByCode(form.getTermCode()));
        form.setContextBar(getViewHelper(form).getContextBarInfo(form.getTerm()));
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setClientState(AdminRegConstants.ClientStates.OPEN);
            form.setDisplayRegistrationTab(false);
            return getUIFModelAndView(form);
        }

        form.setRegisteredCourses(getViewHelper(form).getCourseRegForStudentAndTerm(form.getPerson().getId(), form.getTerm().getId()));
        form.setWaitlistedCourses(getViewHelper(form).getCourseWaitListForStudentAndTerm(form.getPerson().getId(), form.getTerm().getId()));

        form.setDisplayRegistrationTab(true);
        form.getTermIssues().addAll(getViewHelper(form).checkStudentEligibilityForTermLocal(form.getPerson().getId().toUpperCase(), form.getTerm()));

        printTime(form.getMethodToCall(), start);

        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelFailedEligibilityTerm")
    public ModelAndView cancelFailedEligibilityTerm(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                                    HttpServletRequest request, HttpServletResponse response) {

        form.clearCourseRegistrationValues();
        form.setDisplayRegistrationTab(false);
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=continueFailedEligibilityTerm")
    public ModelAndView continueFailedEligibilityTerm(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                                      HttpServletRequest request, HttpServletResponse response) {

        form.clearCourseRegistrationValues();
        form.setDisplayRegistrationTab(true);
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    ///////////////////////////////////////////////
    //Registration methods
    //////////////////////////////////////////////

    //Method used for removing a course from registration
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=removeRegisterCourse")
    public ModelAndView removeRegisterCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        return deleteLine(form, result, request, response);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=register")
    public ModelAndView register(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        // Validate for existing courses.
        getViewHelper(form).validateForRegistration(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setClientState(AdminRegConstants.ClientStates.READY);

            printTime(form.getMethodToCall(), start);

            return getUIFModelAndView(form);
        }

        // Move courses to "In Process" list
        form.getCoursesInProcess().clear();
        form.getCoursesInProcess().addAll(form.getPendingCourses());

        // Set the necessary attributes on the pending courses.
        for (RegistrationCourse course : form.getCoursesInProcess()) {
            course.setActivities(getViewHelper(form).getRegistrationActivitiesForRegistrationCourse(course, form.getTerm().getCode()));
        }
        form.setConfirmationIssues(new ArrayList<String>());

        printTime(form.getMethodToCall(), start);

        return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        // Validate the input values.
        form.getConfirmationIssues().clear();
        getViewHelper(form).validateForSubmission(form);
        if (!form.getConfirmationIssues().isEmpty()) {
            return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);
        }

        // Continue with registration submission
        form.resetPendingCourseValues();
        form.setRegRequestId(getViewHelper(form).submitCourses(form.getPerson().getId(), form.getTerm().getId(),
                form.getCoursesInProcess(), LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY));

        // Set the client state to "Registering" so that we can prevent certain actions on UI.
        form.setClientState(AdminRegConstants.ClientStates.REGISTERING);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=queryForRegistrationStatus")
    @ResponseBody
    public Map queryForRegistrationStatus(@ModelAttribute("KualiForm") AdminRegistrationForm form) {

        Map<String, Object> result = new HashMap<String, Object>();
        Set<String> updateIds = new HashSet<String>();
        List<String> growlMessages = new ArrayList<String>();

        // Only do polling while registration is in progress.
        if ((!AdminRegConstants.ClientStates.REGISTERING.equals(form.getClientState()) || (form.getRegRequestId() == null))) {
            result.put(AdminRegConstants.POLLING_STOP, true);
            return result;
        }

        // Retrieve registration request and check the state.
        RegistrationRequestInfo regRequest = this.getViewHelper(form).getRegistrationRequest(form.getRegRequestId());
        if ((regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY)) ||
                (regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY))) {
            return result;
        }

        synchronized (form) {

            for (RegistrationRequestItemInfo item : regRequest.getRegistrationRequestItems()) {

                // Move item to appropriate list based on the item state.
                RegistrationResult regResult = null;
                if (LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY.equals(item.getTypeKey())) {
                    regResult = handleAddRequestItem(form, item);
                } else if (LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY.equals(item.getTypeKey())) {
                    regResult = handleEditRequestItem(form, item);
                } else if (LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY.equals(item.getTypeKey())) {
                    regResult = handleDropRequestItem(form, item);
                }

                if (regResult != null) {
                    if (regResult.getCollectionId() != null) {
                        updateIds.add(regResult.getCollectionId());
                    }
                    if (AdminRegConstants.ResultLevels.RESULT_LEVEL_SUCCESS.equals(regResult.getLevel())) {
                        for (RegistrationResultItem resultItem : regResult.getItems()) {
                            growlMessages.add(resultItem.getDescription());
                        }

                    } else {
                        form.getRegistrationResults().add(regResult);
                    }
                }
            }
        }

        // Set the ajax return values.
        result.put(AdminRegConstants.POLLING_GROWL_MESSAGES, growlMessages);
        result.put(AdminRegConstants.POLLING_REFRESH, true);
        result.put(AdminRegConstants.POLLING_CLIENT_STATE, AdminRegConstants.ClientStates.READY);
        result.put(AdminRegConstants.POLLING_REGISTERED_CREDITS, form.getRegisteredCredits());
        result.put(AdminRegConstants.POLLING_WAITLISTED_CREDITS, form.getWaitlistedCredits());

        // Return updateIds to UI, to refresh selected collections and stop the polling.
        result.put(AdminRegConstants.POLLING_STOP, true);
        result.put(AdminRegConstants.POLLING_UPDATE_IDS, updateIds);
        return result;
    }

    /**
     * Handles a item that added a new course registration.
     *
     * @param form
     * @param item
     * @return
     */
    private RegistrationResult handleAddRequestItem(AdminRegistrationForm form, RegistrationRequestItemInfo item) {

        RegistrationResult result = null;
        RegistrationCourse addCourse = AdminRegistrationUtil.retrieveFromCourseList(form.getCoursesInProcess(), item);
        if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(item.getStateKey())) {
            if(item.getCourseRegistrationId()!=null) {
                result = this.getViewHelper(form).buildRegistrationResult(addCourse, form.getTerm(),
                        AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_REGISTERED, item.getValidationResults());
                result.setCollectionId(AdminRegConstants.REG_COLL_ID);
            } else {
                result = this.getViewHelper(form).buildRegistrationResult(addCourse, form.getTerm(),
                        AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_WAITLISTED, item.getValidationResults());
                result.setCollectionId(AdminRegConstants.WAITLIST_COLL_ID);
            }
        } else if (LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(item.getStateKey())) {
            result = this.getViewHelper(form).buildRegistrationResult(addCourse, form.getTerm(), null, item.getValidationResults());
            result.setOriginRequestTypeKey(item.getTypeKey());
        }

        return result;
    }

    /**
     * Handles an edited registration request item.
     * <p/>
     * If the request originated from an override, we need to remove the previous warning result from the registration
     * result section and adds it to the registered courses, as well as remove the old pre-updated course from the
     * registered courses section.
     *
     * @param form
     * @param item
     * @return
     */
    private RegistrationResult handleEditRequestItem(AdminRegistrationForm form, RegistrationRequestItemInfo item) {

        // Retrieve the course that was updated.
        RegistrationResult result = null;
        RegistrationCourse updatedCourse = AdminRegistrationUtil.retrieveFromCourseList(form.getCoursesInEdit(), item);
        // Update the registered courses list with updated detail.
        if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(item.getStateKey())) {
            result = this.getViewHelper(form).buildRegistrationResult(updatedCourse, form.getTerm(),
                    AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_UPDATED, item.getValidationResults());
            result.setCollectionId(AdminRegConstants.REG_COLL_ID);
        } else if (LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(item.getStateKey())) {
            result = this.getViewHelper(form).buildRegistrationResult(updatedCourse, form.getTerm(), null, item.getValidationResults());
            result.setOriginRequestTypeKey(item.getTypeKey());
        }

        return result;
    }

    /**
     * Handles a drop registration request item.
     *
     * @param form
     * @param item
     * @return
     */
    private RegistrationResult handleDropRequestItem(AdminRegistrationForm form, RegistrationRequestItemInfo item) {

        // Retrieve the course that was updated.
        RegistrationResult result = null;
        RegistrationCourse dropCourse = form.getPendingDropCourse();
        if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(item.getStateKey())) {
            result = this.getViewHelper(form).buildRegistrationResult(dropCourse, form.getTerm(),
                    AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_DROPPED, item.getValidationResults());
            result.setCollectionId(AdminRegConstants.REG_COLL_ID);
        } else if (LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(item.getStateKey())) {
            result = this.getViewHelper(form).buildRegistrationResult(dropCourse, form.getTerm(), null, item.getValidationResults());
            result.setOriginRequestTypeKey(item.getTypeKey());
        }

        return result;
    }

    ///////////////////////////////////////////////
    //Manage Course methods
    //////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=dropCourse")
    public ModelAndView dropCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        // Set the pending drop course with the selected course.
        RegistrationCourse regCourse = getSelectedRegistrationCourse(form);

        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);
        if (selectedCollectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            regCourse.setRegisteredDropDate(new Date());
        } else if (selectedCollectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            regCourse.setWaitlistedDropDate(new Date());
        }

        form.setPendingDropCourse(regCourse);
        return showDialog(AdminRegConstants.DROP_COURSE_DIALOG, form, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=dropRegisteredCourse")
    public ModelAndView confirmDropCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Submit the drop request.
        form.setRegRequestId(getViewHelper(form).submitCourse(form.getPerson().getId(), form.getTerm().getId(),
                form.getPendingDropCourse(), LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY));

        // perform actual drop on item in the backend
        form.setClientState(AdminRegConstants.ClientStates.REGISTERING);
        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=removeWaitlistCourse")
    public ModelAndView removeWaitlistCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        return deleteLine(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editCourse")
    public ModelAndView editCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        form.getEditingIssues().clear();
        RegistrationCourse regCourse = getSelectedRegistrationCourse(form);

        CourseOfferingInfo courseOffering = AdminRegClientCache.getCourseOfferingByCodeAndTerm(form.getTerm().getId(), regCourse.getCode());
        if (regCourse.getCreditOptions() == null) {
            regCourse.setCreditOptions(getViewHelper(form).getCourseOfferingCreditOptionValues(courseOffering.getCreditOptionId()));
            if (regCourse.getCreditOptions().size() == 1) {
                regCourse.setCreditType(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
            } else {
                regCourse.setCreditType(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
            }
        }

        if (regCourse.getGradingOptions() == null) {
            regCourse.setGradingOptions(getViewHelper(form).getGradingOptionsForCourseOffering(courseOffering));
        }

        // May want to write your own copy/clone method or alternatively re-retrieve value from db on cancel
        RegistrationCourse editingCourse = (RegistrationCourse) (SerializationUtils.clone(regCourse));
        editingCourse.setOriginCourse(regCourse);
        form.getCoursesInEdit().clear();
        form.getCoursesInEdit().add(editingCourse);

        form.setClientState(AdminRegConstants.ClientStates.READY);
        return showDialog(AdminRegConstants.COURSE_EDIT_DIALOG, form, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveEdit")
    public ModelAndView saveEdit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Validate the input values.
        form.getEditingIssues().clear();
        getViewHelper(form).validateCourseEdit(form);
        if (!form.getEditingIssues().isEmpty()) {
            return showDialog(AdminRegConstants.COURSE_EDIT_DIALOG, form, request, response);
        }

        // Continue with saving
        // perform actual save on item in the backend
        form.setRegRequestId(getViewHelper(form).submitCourses(form.getPerson().getId(), form.getTerm().getId(),
                form.getCoursesInEdit(), LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY));

        form.setClientState(AdminRegConstants.ClientStates.REGISTERING);
        //reset the dialog
        form.getDialogManager().resetDialogStatus(AdminRegConstants.COURSE_EDIT_DIALOG);
        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelEdit")
    public ModelAndView cancelEdit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        form.getCoursesInEdit().clear();
        form.getEditingIssues().clear();
        form.setClientState(AdminRegConstants.ClientStates.READY);
        //reset the dialog
        form.getDialogManager().resetDialogStatus(AdminRegConstants.COURSE_EDIT_DIALOG);
        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=allowCourse")
    public ModelAndView allowCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {

        RegistrationResult regResult = (RegistrationResult) getSelectedCollectionObject(form);

        // Resubmit registration
        form.setRegRequestId(getViewHelper(form).resubmitCourse(form.getPerson().getId(), form.getTerm().getId(),
                regResult.getCourse(), regResult.getOriginRequestTypeKey()));

        // Set the client state to "Registering" so that we can prevent certain actions on UI.
        form.setClientState(AdminRegConstants.ClientStates.REGISTERING);
        return getUIFModelAndView(form);
    }

    //Method used for the deny button of the registration request
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=denyCourse")
    public ModelAndView denyCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {
        // would deny registration request here
        return deleteLine(form, result, request, response);
    }

    /**
     * @param form
     * @return
     */
    protected AdminRegistrationViewHelperService getViewHelper(UifFormBase form) {
        return (AdminRegistrationViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    private RegistrationCourse getSelectedRegistrationCourse(AdminRegistrationForm form) {
        return (RegistrationCourse) this.getSelectedCollectionObject(form);
    }

    private Object getSelectedCollectionObject(AdminRegistrationForm form) {

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection path was not set for collection action");
        }

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        int selectedLineIndex = -1;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        // Retrieving the select registration result.
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object item = ((List) collection).get(selectedLineIndex);

        return item;

    }

    private void printTime(String methodName, long startTime){
        LOG.info("***   " + methodName + ": " + (System.currentTimeMillis() - startTime));
    }

}
