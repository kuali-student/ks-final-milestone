/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.controller;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.CourseForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller handles all the request from Academic calendar UI.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

@Controller
@RequestMapping(value = "/courses")
public class CourseController extends UifControllerBase {
    protected String thing;


    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseForm();
    }

    /**
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        return super.start(form, result, request, response);
    }

    /*
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=reload")
    public ModelAndView reload(@ModelAttribute("KualiForm") CourseForm courseForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        return super.reload(courseForm, result, request, response);
    }
    */

    /**
     * This method is called before the Create New Academic Calendar page is displayed
     *
     * It fills in the original Acal for the form with the latest calendar found, by default
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView startNew( @ModelAttribute("KualiForm") CourseForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) {

        return getUIFModelAndView(form);
    }


    /**
     * This will save the academic calendar.
     *
     * @param academicCalendarForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    /*
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") CourseForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {
        return super.save(form, result, request, response);
    }
    */
}
