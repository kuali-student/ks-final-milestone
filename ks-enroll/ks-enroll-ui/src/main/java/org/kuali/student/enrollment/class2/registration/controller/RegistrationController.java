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
package org.kuali.student.enrollment.class2.registration.controller;

import org.kuali.rice.kns.web.spring.controller.UifControllerBase;
import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.class2.registration.form.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController extends UifControllerBase {

    public UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new RegistrationForm();
    }

    /**
     * Initial method called when requesting a new view instance which forwards
     * the view for rendering
     */
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        return getUIFModelAndView(form);
    }

    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=registerClass")
    public ModelAndView register(@ModelAttribute("KualiForm") UifFormBase formBase, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RegistrationForm registrationForm = (RegistrationForm) formBase;
        return getUIFModelAndView(registrationForm);
    }
}
