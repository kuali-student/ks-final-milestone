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
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssue;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssueItem;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Brian on 6/17/14.
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
    public ModelAndView refresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //cancelEdits((AdminRegistrationForm) form, form.getUpdateComponentId());
        return super.refresh(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=refreshCourseName")
    public ModelAndView refreshCourseName(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.refresh(form, result, request, response);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getStudentInfo")
    public ModelAndView getStudentInfo(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        form.clear();
        this.validateUserPopulatedStudentIdField(form);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setClientState(AdminRegConstants.ClientStates.OPEN);
            return getUIFModelAndView(form);
        }

        try {
            this.getViewHelper(form).populateStudentInfo(form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        form.setClientState(AdminRegConstants.ClientStates.INITIALIZED);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getRegistrationInfo")
    public ModelAndView getTermInfo(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) {

        if (form.getTermCode() != null) {
            TermInfo term = getViewHelper(form).getTermByCode(form.getTermCode());
            if (term != null) {
                form.setTermInfo(term);
                form.setTermName(term.getName());

                form.setRegisteredCourses(getViewHelper(form).getCourseRegForStudentAndTerm(form.getStudentId(), term.getId()));
                form.setWaitlistedCourses(getViewHelper(form).getCourseWaitListForStudentAndTerm(form.getStudentId(), term.getId()));
            } else {
                form.clearTermValues();
            }
        } else {
            if (StringUtils.isBlank(form.getTermCode())) {
                GlobalVariables.getMessageMap().putError("termCode", AdminRegConstants.ADMIN_REG_MSG_ERROR_TERM_CODE_REQUIRED);
                form.clearTermValues();
            } else {
                GlobalVariables.getMessageMap().putError("termCode", AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_TERM);
                form.clearTermValues();
            }
        }

        form.setClientState(AdminRegConstants.ClientStates.READY);
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=register")
    public ModelAndView register(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {

        for (RegistrationCourse course : form.getPendingCourses()) {
            course.setCredits(3);
            course.setTransactionalDate(new Date());
            course.setRegOptions("reg");
            course.setActivities(getViewHelper(form).getRegistrationActivitiesForRegistrationCourse(course, form.getTermCode()));
        }

        return showDialog(AdminRegConstants.REG_CONFIRM_DIALOG, form, request, response);

    }

    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=submit")
    public ModelAndView submit(@ModelAttribute("KualiForm") AdminRegistrationForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        // continue with registration
        form.getCoursesInProcess().addAll(form.getPendingCourses());
        form.resetPendingCourseValues();

        return getUIFModelAndView(form);
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

    @MethodAccessible
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=queryForRegistrationStatus")
    @ResponseBody
    public Map queryForRegistrationStatus(@ModelAttribute("KualiForm") AdminRegistrationForm form) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<String> updateIds = new ArrayList<String>();

        if (form.getCoursesInProcess().isEmpty()) {
            result.put("stop", true);
            return result;
        }

        Random generator = new Random();
        if (generator.nextInt(3) != 0) {
            return result;
        }

        synchronized (form) {

            int i = generator.nextInt(3);

            RegistrationCourse regCourse = form.getCoursesInProcess().remove(0);
            if (i == 0) {
                // faking a registration complete
                form.getRegisteredCourses().add(regCourse);
                updateIds.add(AdminRegConstants.REG_COLL_ID);
            }

            if (i == 1) {
                // faking a waitlist complete
                form.getWaitlistedCourses().add(regCourse);
                updateIds.add(AdminRegConstants.WAITLIST_COLL_ID);
            }

            if (i == 2) {
                // faking an issue found add registration issue
                RegistrationIssue regIssue = new RegistrationIssue();
                regIssue.setCourse(regCourse);
                regIssue.getItems().add(new RegistrationIssueItem("No seats available."));
                regIssue.getItems().add(new RegistrationIssueItem("Time conflict with ENGL100 (10001)."));
                form.getRegistrationIssues().add(regIssue);
                updateIds.add(AdminRegConstants.ISSUES_COLL_ID);
            }
        }

        result.put("updateIds", updateIds);
        return result;
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
            Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, "registeredCourses");
            // using temp here but could retrieve original from db
            ((List) collection).set(form.getEditRegisteredIndex(), form.getTempRegCourseEdit());
            form.setEditRegisteredIndex(-1);
        } else if (form.getEditWaitlistedIndex() > -1 && collectionId.equals(AdminRegConstants.WAITLIST_COLL_ID)) {
            Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, "waitlistedCourses");
            // using temp here but could retrieve original from db
            ((List) collection).set(form.getEditWaitlistedIndex(), form.getTempWaitlistCourseEdit());
            form.setEditRegisteredIndex(-1);
        }
    }

    private void validateUserPopulatedStudentIdField(AdminRegistrationForm form) {

        String StudentId = form.getStudentId();
        if (StringUtils.isBlank(StudentId)) {
            GlobalVariables.getMessageMap().putError(AdminRegConstants.STUDENT_INFO_SECTION_STUDENT_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_STUDENT_REQUIRED);
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
