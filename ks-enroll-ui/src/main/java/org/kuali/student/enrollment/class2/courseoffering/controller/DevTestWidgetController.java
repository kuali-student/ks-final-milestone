/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/26/12
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.DevTestWidgetForm;
import org.kuali.student.enrollment.class2.courseoffering.service.DevTestWidgetViewHelperService;
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
 * This class provides controller methods for testing service calls through the ui
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/devTestWidget")
public class DevTestWidgetController extends UifControllerBase {

    private static final Logger LOGGER = Logger.getLogger(DevTestWidgetController.class);

    public static final String PAGE_ID = "pageId";
    private DevTestWidgetViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new DevTestWidgetForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof DevTestWidgetForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type DevTestWidgetForm. Got " + form.getClass().getSimpleName());
        }
        DevTestWidgetForm theForm = (DevTestWidgetForm) form;
        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey(PAGE_ID)) {
            String pageId = ((String []) paramMap.get(PAGE_ID))[0];
            if (pageId.equals("firstServiceCall")) {
                return _startFirstServiceCall(form, result, request, response);
            }
        }
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startFirstServiceCall(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        DevTestWidgetForm theForm = (DevTestWidgetForm) form;
        LOGGER.info("firstServiceCall");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=alphaTest")
    public ModelAndView alphaTest(@ModelAttribute("KualiForm") DevTestWidgetForm form, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("alphaTest in DevTestWidgetController");
        DevTestWidgetViewHelperService helper = getViewHelperService(form);
        helper.runAlphaTest(form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=betaTest")
    public ModelAndView betaTest(@ModelAttribute("KualiForm") DevTestWidgetForm form, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("betaTest in DevTestWidgetController");
        DevTestWidgetViewHelperService helper = getViewHelperService(form);
        helper.runBetaTest(form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=gammaTest")
    public ModelAndView gammaTest(@ModelAttribute("KualiForm") DevTestWidgetForm form, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("gammaTest in DevTestWidgetController");
        DevTestWidgetViewHelperService helper = getViewHelperService(form);
        helper.runGammaTest(form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deltaTest")
    public ModelAndView deltaTest(@ModelAttribute("KualiForm") DevTestWidgetForm form, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("deltaTest in DevTestWidgetController");
        DevTestWidgetViewHelperService helper = getViewHelperService(form);
        helper.runDeltaTest(form);
        return getUIFModelAndView(form);
    }

    public DevTestWidgetViewHelperService getViewHelperService(DevTestWidgetForm serviceCallForm) {
        if (viewHelperService == null) {
            if (serviceCallForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (DevTestWidgetViewHelperService) serviceCallForm.getView().getViewHelperService();
            } else {
                viewHelperService = (DevTestWidgetViewHelperService) serviceCallForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
