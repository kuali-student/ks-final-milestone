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

import org.kuali.rice.kns.web.spring.controller.UifControllerBase;
import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/grading")
public class GradingController extends UifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new GradingForm();
    }

    @RequestMapping(params = "methodToCall=" + GradingConstants.LOAD_GRADES_ROSTER_METHOD)
    public ModelAndView loadGradeRoster(@ModelAttribute("GradingForm") GradingForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

//        StudentToGrade student = new StudentToGrade();
//        student.setFirstName("Mark");
//        student.setLastName("Steve");
//        student.setId("1000");
//
//        form.getStudentToGradeList().add(student);
//
//        student = new StudentToGrade();
//        student.setFirstName("John");
//        student.setLastName("Brito");
//        student.setId("1002");
//        form.getStudentToGradeList().add(student);
//
//        student = new StudentToGrade();
//        student.setFirstName("Chris");
//        student.setLastName("Aswr");
//        student.setId("1004");
//        form.getStudentToGradeList().add(student);

        return getUIFModelAndView(form,form.getViewId(),"page2");
    }

}
