package org.kuali.student.enrollment.class2.grading.controller;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.uif.UifParameters;
import org.kuali.rice.kns.web.spring.controller.UifControllerBase;
import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.*;

@Controller
@RequestMapping(value = "/grading")
public class GradingController extends UifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new GradingForm();
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.LOAD_GRADES_ROSTER_METHOD)
    public ModelAndView loadGradeRoster(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String selectedCourse = gradingForm.getSelectedCourse();
        List<GradeStudent> students = ((GradingViewHelperService)gradingForm.getView().getViewHelperService()).loadStudents(selectedCourse);
        gradingForm.setStudents(students);

        return getUIFModelAndView(gradingForm, gradingForm.getViewId(),"page2");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=unassignGrade")
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
            throw new RuntimeException("Selected line index was not set for delete unassign action, cannot unassign grade");
        }

        ((GradingViewHelperService)gradingForm.getView().getViewHelperService()).unAssignGrade(gradingForm.getView(),gradingForm,selectedCollectionPath,selectedLineIndex);

         return getUIFModelAndView(gradingForm, gradingForm.getViewId(),"page2");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
	public ModelAndView save(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

        String selectedCourse = gradingForm.getSelectedCourse();
        ContextInfo context = TestHelper.getContext1(); // TODO replace
        GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/grading", "GradingService"));

        // Get the GradeRosterEntryInfo objects mapped by studentId
        List<GradeRosterInfo> gradeRosterInfoList = gradingService.getFinalGradeRostersForCourseOffering(selectedCourse, context);
        Map<String, GradeRosterEntryInfo> gradeRosterEntryInfoMap = new HashMap<String, GradeRosterEntryInfo>();
        if (gradeRosterInfoList != null){
            for (GradeRosterInfo rosterInfo : gradeRosterInfoList){
                List<GradeRosterEntryInfo> gradeRosterEntryInfoList = gradingService.getGradeRosterEntriesByIdList(rosterInfo.getGradeRosterEntryIds(), context);
                for (GradeRosterEntryInfo gradeRosterEntryInfo : gradeRosterEntryInfoList){
                    String gradeRosterEntryStudentId = gradeRosterEntryInfo.getStudentId();
                    gradeRosterEntryInfoMap.put(gradeRosterEntryStudentId, gradeRosterEntryInfo);
                }
            }
        }

        // Update the assigned grade for each student in form.
        List<GradeStudent> gradeStudentList = gradingForm.getStudents();
        for (GradeStudent gradeStudent: gradeStudentList) {
            GradeRosterEntryInfo gradeRosterEntryInfo = gradeRosterEntryInfoMap.get(gradeStudent.getStudentId());
            if (gradeRosterEntryInfo == null) {
                // TODO Do we need to throw an error?
            }

            AssignedGradeInfo assignedGradeInfo = gradeRosterEntryInfo.getAssignedGrade();
            if (assignedGradeInfo == null) {
                // TODO not sure if this is how to get a new one.
                assignedGradeInfo = new AssignedGradeInfo();
            }
            String grade = gradeStudent.getSelectedGrade();
            String gradeRosterEntryId = gradeRosterEntryInfo.getId();

            assignedGradeInfo.setGrade(grade);
            gradingService.updateAssignedGrade(gradeRosterEntryId, assignedGradeInfo, context);

        }

        return close(gradingForm, result, request, response);
    }
}