/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Peggy Mufamadi on 5/9/13
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.krms.dto.FECustomComponentsForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the controller class what handles all the requests (actions) from the <i>'Manage Final Exam Matrix'</i> ui.
 *
 */
@Controller
@RequestMapping(value = "/feCustomComponents")
public class FECustomComponentsController extends UifControllerBase {

    private static final Logger LOG = Logger.getLogger(FECustomComponentsController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new FECustomComponentsForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase uifForm, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(uifForm instanceof FECustomComponentsForm)) {
            throw new RuntimeException("Form object passed into start method was not of expected type FERuleManagementWrapper form. Got " + uifForm.getClass().getSimpleName());
        }

        FECustomComponentsForm form = (FECustomComponentsForm) uifForm;

        return getUIFModelAndView(form);
    }
}
