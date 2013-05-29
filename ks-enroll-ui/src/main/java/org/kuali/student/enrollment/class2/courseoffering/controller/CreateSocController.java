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
 * Created by Charles on 4/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.CreateSocForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CreateSocViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/createSoc")
public class CreateSocController extends UifControllerBase {
    private CreateSocViewHelperService viewHelperService;

    private static final Logger LOGGER = Logger.getLogger(CreateSocController.class);

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new CreateSocForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof CreateSocForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type CreateSocForm. Got " + form.getClass().getSimpleName());
        }
        CreateSocForm theForm = (CreateSocForm) form;
        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey("pageId")) {
            String pageId = ((String []) paramMap.get("pageId"))[0];
            if (pageId.equals("selectTermForSocCreation")) {
                return _startSelectTermForSocCreation(form, result, request, response);
            }
        }
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startSelectTermForSocCreation(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        CreateSocForm theForm = (CreateSocForm) form;
        LOGGER.info("selectTermForSocCreation");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=createSocTerm")
    public ModelAndView createSocTerm(@ModelAttribute("KualiForm") CreateSocForm form, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CreateSocViewHelperService helper = getViewHelperService(form);
        // First, check if term exists
        try {
            helper.getTermByTermCode(form.getSocTermCode());
        } catch (InvalidParameterException e) {
            form.setSocMessage(form.getSocTermCode() + " is an invalid term");
            return getUIFModelAndView(form);
        } catch (Exception e) {
            form.setSocMessage(form.getSocTermCode() + " is producing an unknown error");
            return getUIFModelAndView(form);
        }
        SocInfo socInfo = helper.createSocForTerm(form.getSocTermCode());

        if (socInfo == null) {
            form.setTermAlreadyHasSoc(true);
            form.setSocInfo(socInfo);
            socInfo = helper.getMainSocForTerm(form.getSocTermCode());
            form.setSocMessage("Term " + form.getSocTermCode() + " already has SOC with state: " + socInfo.getStateKey());
        } else {
            form.setSocInfo(socInfo);
            form.setTermAlreadyHasSoc(false);
            form.setSocMessage("Term " + form.getSocTermCode() + " is created with state: " + socInfo.getStateKey());
        }

        return getUIFModelAndView(form);
    }

    public CreateSocViewHelperService getViewHelperService(CreateSocForm createSocForm) {
        if (viewHelperService == null) {
            if (createSocForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (CreateSocViewHelperService) createSocForm.getView().getViewHelperService();
            } else {
                viewHelperService = (CreateSocViewHelperService) createSocForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
