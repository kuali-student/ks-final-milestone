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
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * This is the controller class what handles all the requests (actions) from the <i>'Manage Final Exam Matrix'</i> ui.
 *
 */
@Controller
@RequestMapping(value = "/finalExamMatrixManagement")
public class FinalExamMatrixManagementController extends UifControllerBase {

    private static final Logger LOG = Logger.getLogger(FinalExamMatrixManagementController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new FERuleManagementWrapper();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase uifForm, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(uifForm instanceof FERuleManagementWrapper)) {
            throw new RuntimeException("Form object passed into start method was not of expected type FERuleManagementWrapper form. Got " + uifForm.getClass().getSimpleName());
        }

        checkViewAuthorization(uifForm, request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER));

        FERuleManagementWrapper form = (FERuleManagementWrapper) uifForm;

        return getUIFModelAndView(form);
    }



    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        FERuleManagementWrapper theForm = (FERuleManagementWrapper) form;
        Properties urlParameters = this.showFE(theForm, theForm.getType().getKey());
        return super.performRedirect(theForm, "finalExamRules", urlParameters);
    }

    public static Properties showFE(FERuleManagementWrapper theForm, String refObjectId)  {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        urlParameters.put("viewName", "FEAgendaManagementView");
        urlParameters.put("refObjectId", refObjectId);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, FERuleManagementWrapper.class.getName());

        return urlParameters;
    }

}
