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
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssue;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssueItem;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.infc.ValidationResult;
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
     * This method is called when the user has entered a student id to get the studentInfo
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

    /**
     * This method is called when the user has entered a term code to get the termInfo
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getRegistrationInfo")
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

        //KSENROLL-13558 :work around for incorrect Data
        String studentID = StringUtils.EMPTY;
        List<Principal> principals = AdminRegResourceLoader.getIdentityService().getPrincipalsByEntityId(form.getPerson().getId().toUpperCase());
        for (Principal principalID : principals) {
            studentID = principalID.getPrincipalId();
        }
        //method needs to change to pass form.getStudentId and not studentID
        form.setRegisteredCourses(getViewHelper(form).getCourseRegForStudentAndTerm(studentID, form.getTerm().getId()));
        form.setWaitlistedCourses(getViewHelper(form).getCourseWaitListForStudentAndTerm(studentID, form.getTerm().getId()));

        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

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

        // Set the necessary attributes on the pending courses.
        for (RegistrationCourse course : form.getPendingCourses()) {
            course.setActivities(getViewHelper(form).getRegistrationActivitiesForRegistrationCourse(course, form.getTerm().getCode()));

        }

        return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        // Continue with registration submission
        List<RegistrationCourse> pendingCourses = form.getPendingCourses();
        form.setRegRequestId(getViewHelper(form).submitRegistrationRequest(form.getPerson().getId(), form.getTerm().getId(), pendingCourses));

        // Move courses to "In Process" list
        form.getCoursesInProcess().addAll(pendingCourses);
        form.resetPendingCourseValues();

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

        // Check if any courses is being processed by the registration engine.
        if (form.getRegRequestId() == null || form.getCoursesInProcess().isEmpty()) {
            result.put(AdminRegConstants.POLLING_STOP, true);
            return result;
        }

        // Retrieve registration request and check the state.
        RegistrationRequestInfo regRequest = this.getViewHelper(form).getRegistrationRequest(form.getRegRequestId());
        if (regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY)) {
            return result;
        }

        synchronized (form) {

            if (regRequest.getStateKey().equals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY)) {
                for (RegistrationRequestItemInfo item : regRequest.getRegistrationRequestItems()) {

                    //Check for LPRTRANS_ITEM_NEW_STATE_KEY and LPRTRANS_ITEM_PROCESSING_STATE_KEY if any
                    //and handle appropriately.

                    // Get the corresponding registration course from the courses in process list.
                    RegistrationCourse processedCourse = null;
                    for (RegistrationCourse regCourse : form.getCoursesInProcess()) {
                        if (regCourse.getRegGroup().getId().equals(item.getRegistrationGroupId())) {
                            processedCourse = regCourse;
                            break;
                        }
                    }

                    // Remove the item from the courses in process list.
                    form.getCoursesInProcess().remove(processedCourse);

                    // Move item to appropriate list based on the item state.
                    if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(item.getStateKey())) {
                        form.getRegisteredCourses().add(processedCourse);
                        updateIds.add(AdminRegConstants.REG_COLL_ID);
                    } else if (LprServiceConstants.LPRTRANS_ITEM_WAITLIST_STATE_KEY.equals(item.getStateKey())) {
                        form.getWaitlistedCourses().add(processedCourse);
                        updateIds.add(AdminRegConstants.WAITLIST_COLL_ID);
                    } else if (LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY.equals(item.getStateKey()) ||
                            LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(item.getStateKey())) {
                        // Create a new registration issue with the course in error.
                        RegistrationIssue regIssue = new RegistrationIssue();
                        regIssue.setCourse(processedCourse);
                        regIssue.setItems(getViewHelper(form).createIssueItemsFromResults(item.getValidationResults()));

                        form.getRegistrationIssues().add(regIssue);
                        updateIds.add(AdminRegConstants.ISSUES_COLL_ID);
                    }
                }
            }

            // Reset the form to ready state.
            form.setRegRequestId(null);
            form.getCoursesInProcess().clear();
            form.setClientState(AdminRegConstants.ClientStates.READY);
        }

        // Return updateIds to UI, to refresh selected collections.
        result.put(AdminRegConstants.POLLING_UPDATE_IDS, updateIds);
        return result;
    }

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

        return deleteLine(form, result, request, response);
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

        cancelEdits(form, selectedCollectionId);

        // May want to write your own copy/clone method or alternatively re-retrieve value from db on cancel
        RegistrationCourse tempCourse = (RegistrationCourse) (SerializationUtils.clone((RegistrationCourse) item));

        if (selectedCollectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            form.setEditRegisteredIndex(selectedLineIndex);
            form.setTempRegCourseEdit(tempCourse);
        } else if (selectedCollectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            form.setEditWaitlistedIndex(selectedLineIndex);
            form.setTempWaitlistCourseEdit(tempCourse);
        }

        return refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveEdit")
    public ModelAndView saveEdit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);
        if (selectedCollectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            form.setEditRegisteredIndex(-1);
            form.setTempRegCourseEdit(null);
        } else if (selectedCollectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            form.setEditWaitlistedIndex(-1);
            form.setTempWaitlistCourseEdit(null);
        }

        // perform actual save on item in the backend

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

        String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        int selectedLineIndex = -1;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        // would Force registration here
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object item = ((List) collection).get(selectedLineIndex);
        form.getRegisteredCourses().add(((RegistrationIssue) item).getCourse());
        ((List) collection).remove(selectedLineIndex);

        return getUIFModelAndView(form);
    }

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
        if (form.getEditRegisteredIndex() > -1 && collectionId.equals(AdminRegConstants.REG_COLL_ID)) {
            Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, AdminRegConstants.REGISTERED_COURSES);
            // using temp here but could retrieve original from db
            ((List) collection).set(form.getEditRegisteredIndex(), form.getTempRegCourseEdit());
            form.setEditRegisteredIndex(-1);
        } else if (form.getEditWaitlistedIndex() > -1 && collectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, AdminRegConstants.WAITLISTED_COURSES);
            // using temp here but could retrieve original from db
            ((List) collection).set(form.getEditWaitlistedIndex(), form.getTempWaitlistCourseEdit());
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
