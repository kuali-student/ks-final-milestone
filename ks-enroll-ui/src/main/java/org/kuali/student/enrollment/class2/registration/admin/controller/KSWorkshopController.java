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
import org.kuali.student.enrollment.class2.registration.admin.form.KSRegistrationIssue;
import org.kuali.student.enrollment.class2.registration.admin.form.KSWorkshopActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.KSWorkshopCourse;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brian on 6/17/14.
 */
@Controller
@RequestMapping(value = "/adminreg")
public class KSWorkshopController extends UifControllerBase {

    @Override
    protected KSWorkshopForm createInitialForm(HttpServletRequest request) {
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

    @RequestMapping(params = "methodToCall=register")
    public ModelAndView register(@ModelAttribute("KualiForm") KSWorkshopForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        String dialogName = "registerConfirmDialog";
        if (!hasDialogBeenAnswered(dialogName, form)) {

            for (KSWorkshopCourse course: form.getPendingCourses()) {
                course.setCourseName("Some course name here");
                course.setCredits(3);
                course.setRegDate(new Date());
                course.setRegOptions("reg");
                List<KSWorkshopActivity> activities = new ArrayList<KSWorkshopActivity>();
                activities.add(new KSWorkshopActivity("Lec", "MWF 01:00pm - 02:30pm", "Steve Capriani", "PTX 2391"));
                activities.add(new KSWorkshopActivity("Lab", "MWF 02:30pm - 03:30pm", "Steve Capriani", "PTX 2391"));
                course.setActivities(activities);
            }

            return showDialog(dialogName, form, request, response);
        } else  {

            for(KSWorkshopCourse course : form.getPendingCourses()){
                if("PHYS232".equals(course.getCourse())){

                    // add registration issue
                    KSRegistrationIssue issue = new KSRegistrationIssue();
                    issue.setCourse(course);
                    issue.getItems().add(new RegistrationIssueItem("No seats available."));
                    issue.getItems().add(new RegistrationIssueItem("Time conflict with ENGL100 (10001)."));
                    form.getRegistrationIssues().add(issue);

                } else {

                    // continue with registration
                    form.getRegisteredCourses().addAll(form.getPendingCourses());
                    form.setPendingCourses(new ArrayList<KSWorkshopCourse>());
                    form.getPendingCourses().add(new KSWorkshopCourse());
                }
            }

            form.getDialogManager().resetDialogStatus(dialogName);
        }

        return getUIFModelAndView(form);
    }


}
