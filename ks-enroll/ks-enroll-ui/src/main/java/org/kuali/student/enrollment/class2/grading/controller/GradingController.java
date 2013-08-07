package org.kuali.student.enrollment.class2.grading.controller;

/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.form.StudentGradeForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
@Controller
@RequestMapping(value = "/grading")
public class GradingController extends UifControllerBase {
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        if (StringUtils.equals(httpServletRequest.getParameter("viewId"), "StudentGradeView")) {
            return new StudentGradeForm();
        } else {
            return new GradingForm();
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.LOAD_GRADES_ROSTER_METHOD)
    public ModelAndView loadGradeRoster(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String selectedCourse = gradingForm.getSelectedCourse();
        List<GradeStudent> students = ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).loadStudents(selectedCourse,gradingForm);
        gradingForm.setStudents(students);

        return getUIFModelAndView(gradingForm, GradingConstants.GRADE_ROSTER_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=loadStudents")
    public ModelAndView loadStudents(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        int selectedLineIndex = -1;
        String selectedLine = gradingForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        CourseOfferingInfo selectedCourse = gradingForm.getCourseOfferingInfoList().get(selectedLineIndex);
        String courseId = selectedCourse.getId();
        gradingForm.setSelectedCourseOffering(selectedCourse);

        //Reset all the students and other info from the form, which might be set for some other course selected before
        resetValuesForPageChange(gradingForm);

        //FIXME: Just a workaround as the propertyreplacer not working
        gradingForm.setTitle(selectedCourse.getCourseOfferingCode() + " - " + selectedCourse.getCourseOfferingTitle());

        List<GradeStudent> students = ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).loadStudents(courseId,gradingForm);

        if (students == null || students.isEmpty()){
            //FIXME: Not sure how to set a global error instead of for a field. If no fields mentioned, KRAD throwing error
            GlobalVariables.getMessageMap().putInfo("firstName",GradingConstants.INFO_GRADE_STUDENTS_NOT_FOUND,selectedCourse.getCourseOfferingCode());
            gradingForm.setReadOnly(true);
        }else{
            gradingForm.setStudents(students);
        }

        return getUIFModelAndView(gradingForm, GradingConstants.GRADE_ROSTER_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.UNASSIGN_GRADE_METHOD)
    public ModelAndView unassignGrade(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = gradingForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for unassign action, cannot unassign grade");
        }

        int selectedLineIndex = -1;
        String selectedLine = gradingForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException(
                    "Selected line index was not set for delete unassign action, cannot unassign grade");
        }

        // TODO: Needs to be a atpService side method instead of handling at server side
        ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).unAssignGrade(gradingForm.getView(),
                gradingForm, selectedCollectionPath, selectedLineIndex);

        return getUIFModelAndView(gradingForm, GradingConstants.GRADE_ROSTER_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.BACK_TO_GRADING_METHOD)
    public ModelAndView backToGrading(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getUIFModelAndView(gradingForm, GradingConstants.SELECT_COURSE_OFFERING_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.BACK_TO_TERM)
    public ModelAndView backToTerm(@ModelAttribute("KualiForm") StudentGradeForm studentGradeForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getUIFModelAndView(studentGradeForm, GradingConstants.STUDENT_TERM_RECORD_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.SAVE_METHOD)
    public ModelAndView save(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean success = ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).saveGrades(gradingForm);

        if (success){
            //FIXME: Not sure how to set a global error instead of for a field. If no fields mentioned, KRAD throwing error
            GlobalVariables.getMessageMap().putInfo("firstName", GradingConstants.INFO_GRADE_ROSTER_SAVED);
        }
        // only refreshing page
        //RICE22M4 gradingForm.setRenderFullView(false);

        return getUIFModelAndView(gradingForm, GradingConstants.GRADE_ROSTER_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall="  + GradingConstants.LOAD_COURSES_METHOD)
    public ModelAndView loadCourses(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).loadCourses(gradingForm);
        return getUIFModelAndView(gradingForm, GradingConstants.SELECT_COURSE_OFFERING_PAGE);

    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall="  + GradingConstants.SUBMIT_METHOD)
    public ModelAndView submit(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean success = ((GradingViewHelperService) gradingForm.getView().getViewHelperService()).submitGradeRoster(gradingForm);

        if (success){
            //FIXME: Not sure how to set a global error instead of for a field. If no fields mentioned, KRAD throwing error
            GlobalVariables.getMessageMap().putInfo("firstName",GradingConstants.INFO_GRADE_ROSTER_SUBMITTED);
        }

        return getUIFModelAndView(gradingForm, GradingConstants.SELECT_COURSE_OFFERING_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.VIEW_GRADES)
    public ModelAndView viewGrades(@ModelAttribute("KualiForm") StudentGradeForm studentGradeForm,
            BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ((GradingViewHelperService) studentGradeForm.getView().getViewHelperService()).loadStudentGrades(studentGradeForm);

        return getUIFModelAndView(studentGradeForm, GradingConstants.STUDENT_CREDIT_DETAILS_PAGE);
    }

    private void resetValuesForPageChange(GradingForm form){
        form.setStudents(new ArrayList());
        form.setReadOnly(false);
        form.setRosterInfos(new ArrayList());
    }

}