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

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.util.ExamOfferingConstants;
import org.kuali.student.enrollment.class2.registration.admin.form.KSRegistrationIssue;
import org.kuali.student.enrollment.class2.registration.admin.form.KSWorkshopForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssueItem;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Brian on 6/17/14.
 */
@Controller
@RequestMapping(value = "/adminreg")
public class KSWorkshopController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new KSWorkshopForm();
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=getStudentInfo")
    public ModelAndView getStudentInfo(@ModelAttribute("KualiForm") KSWorkshopForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        // Pretend service call to get student and populate
        form.setStudentName("Allison Glass");
        form.setStanding("Junior");
        form.setProgram("Undergrad");
        form.setDepartment("Arts and Humanites");
        form.setMajor("Psychology");
        form.setCredits("68");

        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=register")
    public ModelAndView register(@ModelAttribute("KualiForm") KSWorkshopForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        if (!hasDialogBeenAnswered("registerConfirmDialog", form)) {
            return showDialog("registerConfirmDialog", form, request, response);
        }

        KSRegistrationIssue issue = new KSRegistrationIssue();
        issue.getItems().add(new RegistrationIssueItem("No seats available."));
        issue.getItems().add(new RegistrationIssueItem("Time conflict with ENGL100 (10001)."));
        form.getRegistrationIssues().add(issue);

        return getUIFModelAndView(form);
    }
}
