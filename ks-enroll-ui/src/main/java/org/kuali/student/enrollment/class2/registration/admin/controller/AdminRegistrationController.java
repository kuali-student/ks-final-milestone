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
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResult;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResultItem;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegResourceLoader;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
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
        //cancelEdits((AdminRegistrationForm) form, form.getUpdateComponentId());
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
        form.getCoursesInProcess().clear();
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return super.refresh(form, result, request, response);
    }

    ///////////////////////////////////////////////
    //Student methods
    //////////////////////////////////////////////

    /**
     * This method is called when the user has entered a student id to get the studentInfo
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getStudentInfo")
    public ModelAndView getStudentInfo(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        form.clearTermValues();
        PersonInfo person = this.getViewHelper(form).getStudentById(form.getPerson().getId());
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.getPerson().setName(null);
            form.setClientState(AdminRegConstants.ClientStates.OPEN);
            return getUIFModelAndView(form);
        } else {
            form.setPerson(person);
        }

        form.setClientState(AdminRegConstants.ClientStates.INITIALIZED);
        return getUIFModelAndView(form);
    }

    ///////////////////////////////////////////////
    //Term methods
    //////////////////////////////////////////////

    /**
     * This method is called when the user has entered a term code to get the termInfo
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getTermInfo")
    public ModelAndView getTermInfo(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {

        form.clearCourseRegistrationValues();
        TermInfo term = getViewHelper(form).getTermByCode(form.getTerm().getCode());
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setClientState(AdminRegConstants.ClientStates.INITIALIZED);
            return getUIFModelAndView(form);
        } else {
            form.setTerm(term);
        }

        List<RegistrationCourse> registrationCourses = getViewHelper(form).getCourseRegForStudentAndTerm(form.getPerson().getId(), form.getTerm().getId());
        List<RegistrationCourse> waitlistedCourses = getViewHelper(form).getCourseWaitListForStudentAndTerm(form.getPerson().getId(), form.getTerm().getId());
        //TODO: KSENROLL-13558 :work around for incorrect Data
        List<Principal> principals = AdminRegResourceLoader.getIdentityService().getPrincipalsByEntityId(form.getPerson().getId().toUpperCase());
        for (Principal principalID : principals) {
            registrationCourses.addAll(getViewHelper(form).getCourseRegForStudentAndTerm(principalID.getPrincipalId(), form.getTerm().getId()));
            waitlistedCourses.addAll(getViewHelper(form).getCourseWaitListForStudentAndTerm(principalID.getPrincipalId(), form.getTerm().getId()));
        }
        //end workaround

        form.setRegisteredCourses(registrationCourses);
        form.setWaitlistedCourses(waitlistedCourses);

        form.getTermIssues().addAll(getViewHelper(form).checkStudentEligibilityForTermLocal(form.getPerson().getId().toUpperCase(), term.getId()));

        if (!form.getTermIssues().isEmpty()) {
            form.setTermEligible(true);
            if (form.getRegisteredCourses().isEmpty() && form.getWaitlistedCourses().isEmpty()) {
                showDialog(AdminRegConstants.TERM_ELIGIBILITY_DIALOG, form, request, response);
            } else {
                form.setDisplayRegistrationTabs(true);
            }
        } else {
            form.setTermEligible(false);
            form.setDisplayRegistrationTabs(true);
        }
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelFailedEligibilityTerm")
    public ModelAndView cancelFailedEligibilityTerm(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        form.clearCourseRegistrationValues();
        form.setDisplayRegistrationTabs(false);
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=continueFailedEligibilityTerm")
    public ModelAndView continueFailedEligibilityTerm(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        form.clearCourseRegistrationValues();
        form.setDisplayRegistrationTabs(true);
        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    ///////////////////////////////////////////////
    //Registration methods
    //////////////////////////////////////////////

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=register")
    public ModelAndView register(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {

        // Validate for existing courses.
        getViewHelper(form).validateForRegistration(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setClientState(AdminRegConstants.ClientStates.READY);
            return getUIFModelAndView(form);
        }

        // Move courses to "In Process" list
        form.setCoursesInProcess(form.getPendingCourses());

        // Set the necessary attributes on the pending courses.
        for (RegistrationCourse course : form.getCoursesInProcess()) {
            course.setActivities(getViewHelper(form).getRegistrationActivitiesForRegistrationCourse(course, form.getTerm().getCode()));
        }
        form.setConfirmationIssues(new ArrayList<String>());
        return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        // Validate the input values.
        form.getConfirmationIssues().clear();
        getViewHelper(form).validateForSubmission(form);
        if(!form.getConfirmationIssues().isEmpty()){
            return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);
        }

        // Continue with registration submission
        form.resetPendingCourseValues();
        form.setRegRequestId(getViewHelper(form).submitCoursesForRegistration(form.getPerson().getId(), form.getTerm().getId(),
                form.getCoursesInProcess()));

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

        // Only do polling while registration is in progress.
        if (!AdminRegConstants.ClientStates.REGISTERING.equals(form.getClientState())) {
            result.put(AdminRegConstants.POLLING_STOP, true);
            return result;
        }

        // Check if any courses is being processed by the registration engine.
        if (form.getRegRequestId() == null || form.getCoursesInProcess().isEmpty()) {
            result.put(AdminRegConstants.POLLING_STOP, true);
            return result;
        }

        // Retrieve registration request and check the state.
        RegistrationRequestInfo regRequest = this.getViewHelper(form).getRegistrationRequest(form.getRegRequestId());
        if ((regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY)) ||
                (regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY))){
            return result;
        }

        synchronized (form) {

            for (RegistrationRequestItemInfo item : regRequest.getRegistrationRequestItems()) {

                RegistrationCourse courseToProcess = this.getRegistrationCourseToProcess(form, regRequest, item);

                // Create a new registration issue with the course in error.
                RegistrationResult regResult = new RegistrationResult();
                regResult.setCourse(courseToProcess);

                // Move item to appropriate list based on the item state.
                if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(item.getStateKey())) {
                    form.getRegisteredCourses().add(courseToProcess);
                    regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_SUCCESS);
                    String msg = AdminRegistrationUtil.getMessageForKey(AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_REGISTERED);
                    regResult.getItems().add(new RegistrationResultItem(msg));
                    updateIds.add(AdminRegConstants.REG_COLL_ID);
                } else if (LprServiceConstants.LPRTRANS_ITEM_WAITLIST_STATE_KEY.equals(item.getStateKey())) {
                    form.getWaitlistedCourses().add(courseToProcess);
                    regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_SUCCESS);
                    String msg = AdminRegistrationUtil.getMessageForKey(AdminRegConstants.ADMIN_REG_MSG_INFO_SUCCESSFULLY_WAITLISTED);
                    regResult.getItems().add(new RegistrationResultItem(msg));
                    updateIds.add(AdminRegConstants.WAITLIST_COLL_ID);
                } else if (LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY.equals(item.getStateKey()) ||
                        LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(item.getStateKey())) {
                    regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_WARNING);
                    regResult.getItems().addAll(getViewHelper(form).createRegResultsFromValidationResults(item.getValidationResults()));
                }

                form.getRegistrationResults().add(regResult);
            }
        }

        // Set the ajax return values.
        result.put(AdminRegConstants.POLLING_REFRESH, true);
        result.put(AdminRegConstants.POLLING_CLIENT_STATE, AdminRegConstants.ClientStates.READY);
        result.put(AdminRegConstants.POLLING_REGISTERED_CREDITS, form.getRegisteredCredits());
        result.put(AdminRegConstants.POLLING_WAITLISTED_CREDITS, form.getWaitlistedCredits());

        // Return updateIds to UI, to refresh selected collections and stop the polling.
        result.put(AdminRegConstants.POLLING_STOP, true);
        result.put(AdminRegConstants.POLLING_UPDATE_IDS, updateIds);
        return result;
    }

    private RegistrationCourse getRegistrationCourseToProcess(AdminRegistrationForm form, RegistrationRequestInfo regRequest,
                                                              RegistrationRequestItemInfo item){

        // Check if eligibity was overridden for this request.
        boolean isEligibilityOverride = false;
        for (Attribute attr : regRequest.getAttributes()) {
            if (attr.getKey().equals(CourseRegistrationServiceConstants.ELIGIBILITY_OVERRIDE_TYPE_KEY_ATTR)) {
                if (Boolean.valueOf(attr.getValue())) {
                    isEligibilityOverride = true;
                    break;
                }
            }
        }

        if(isEligibilityOverride){
            // Get the registration course from the current registration issues.
            for (RegistrationResult regResult : form.getRegistrationResults()) {
                if (regResult.getCourse().getRegGroup().getId().equals(item.getRegistrationGroupId())) {
                    form.getRegistrationResults().remove(regResult);
                    return regResult.getCourse();
                }
            }
        } else {
            // Get the corresponding registration course from the courses in process list.
            for (RegistrationCourse regCourse : form.getCoursesInProcess()) {
                if (regCourse.getRegGroup().getId().equals(item.getRegistrationGroupId())) {
                    form.getCoursesInProcess().remove(regCourse);
                    return regCourse;
                }
            }
        }

        return null;
    }

    ///////////////////////////////////////////////
    //Manage Course methods
    //////////////////////////////////////////////


    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=dropCourse")
    public ModelAndView dropCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection path was not set for collection action");
        }

        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        int selectedLineIndex = -1;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object item = ((List) collection).get(selectedLineIndex);

        if (!hasDialogBeenAnswered(AdminRegConstants.DROP_COURSE_DIALOG, form)) {

            // Create temp object with the info we need about the course
            RegistrationCourse pendingDropCourse = new RegistrationCourse();
            pendingDropCourse.setCode(((RegistrationCourse) item).getCode());
            pendingDropCourse.setSection(((RegistrationCourse) item).getSection());
            pendingDropCourse.setDropDate(new Date());
            form.setPendingDropCourse(pendingDropCourse);

            return showDialog(AdminRegConstants.DROP_COURSE_DIALOG, form, request, response);
        } else {
            // you would do the actual drop call here
            ((RegistrationCourse) item).setDropDate(form.getPendingDropCourse().getDropDate());

            cancelEdits(form, selectedCollectionId);
        }

        return showDialog(AdminRegConstants.DROP_COURSE_DIALOG, form, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=dropRegisteredCourse")
    public ModelAndView confirmDropCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        // perform actual drop on item in the backend

        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=removeWaitlistCourse")
    public ModelAndView removeWaitlistCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);
        cancelEdits(form, selectedCollectionId);

        return deleteLine(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editCourse")
    public ModelAndView editCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection path was not set for collection action");
        }

        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        int selectedLineIndex = -1;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object item = ((List) collection).get(selectedLineIndex);

        // May want to write your own copy/clone method or alternatively re-retrieve value from db on cancel
         RegistrationCourse tempCourse = (RegistrationCourse) (SerializationUtils.clone((RegistrationCourse) item));
         form.getCoursesEdit().add(tempCourse);
        if (selectedCollectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            form.setEditRegisteredIndex(selectedLineIndex);
        } else if (selectedCollectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            form.setEditWaitlistedIndex(selectedLineIndex);
        }
        showDialog(AdminRegConstants.COURSE_EDIT_DIALOG, form, request, response);

        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveEdit")
    public ModelAndView saveEdit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

        // perform actual save on item in the backend
         form.getCoursesEdit().clear();
        if (selectedCollectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            form.setEditRegisteredIndex(-1);
        } else if (selectedCollectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            form.setEditWaitlistedIndex(-1);
        }

        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=cancelEdit")
    public ModelAndView cancelEdit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);
        cancelEdits(form, selectedCollectionId);

        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=allowCourse")
    public ModelAndView allowCourse(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {

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
        RegistrationCourse regCourse = ((RegistrationResult) item).getCourse();

        // Resubmit registration
        form.setRegRequestId(getViewHelper(form).resubmitCourseForRegistration(form.getPerson().getId(), form.getTerm().getId(),
                regCourse));

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

    private void cancelEdits(AdminRegistrationForm form, String collectionId) {
        if (collectionId == null) {
            return;
        }

        // Cancel other edit if one is open
        form.getCoursesEdit().clear();
        if (form.getEditRegisteredIndex() > -1 && collectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            form.setEditRegisteredIndex(-1);
        } else if (form.getEditWaitlistedIndex() > -1 && collectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            form.setEditRegisteredIndex(-1);
        }
    }

    /**
     * @param form
     * @return
     */
    protected AdminRegistrationViewHelperService getViewHelper(UifFormBase form) {
        return (AdminRegistrationViewHelperService) KSControllerHelper.getViewHelperService(form);
    }
}
