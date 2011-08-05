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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.web.spring.controller.UifControllerBase;
import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/grading")
public class GradingController extends UifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new GradingForm();
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=loadGradeRoster")
    public ModelAndView loadGradeRoster(@ModelAttribute("KualiForm") GradingForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String selectedCourse = form.getSelectedCourse();

        ContextInfo context = TestHelper.getContext1();

       GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/grading", "GradingService"));
        List<GradeRosterInfo> rosterInfos = gradingService.getFinalGradeRostersForCourseOffering(selectedCourse, context);
        if (rosterInfos != null){
            List<GradeStudent> students = new ArrayList();
            for (GradeRosterInfo rosterInfo : rosterInfos){
                List<GradeRosterEntryInfo> entryInfos = gradingService.getGradeRosterEntriesByIdList(rosterInfo.getGradeRosterEntryIds(), context);
                for (GradeRosterEntryInfo entryInfo : entryInfos){
                    GradeStudent student = new GradeStudent();
                    student.setStudentId(entryInfo.getStudentId());
                    students.add(student);
                }
            }
            form.setStudents(students);
        }

        return getUIFModelAndView(form,form.getViewId(),"page2");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
	public ModelAndView save(@ModelAttribute("KualiForm") GradingForm uiTestForm, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
         return getUIFModelAndView(uiTestForm, uiTestForm.getViewId(), "page1");
    }

}