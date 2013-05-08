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
 * Created by Charles on 5/6/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.TestStatePropagationForm;
import org.kuali.student.enrollment.class2.courseoffering.service.TestServiceCallViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.TestStatePropagationViewHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping(value = "/testStatePropagation")
public class TestStatePropagationController extends UifControllerBase {
    private TestStatePropagationViewHelperService viewHelperService;

    private static final Logger LOGGER = Logger.getLogger(TestStatePropagationController.class);

    public static final String PAGE_ID = "pageId";

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new TestStatePropagationForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof TestStatePropagationForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type TestServiceCallForm. Got " + form.getClass().getSimpleName());
        }
        TestStatePropagationForm theForm = (TestStatePropagationForm) form;
        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey(PAGE_ID)) {
            String pageId = ((String []) paramMap.get(PAGE_ID))[0];
            if (pageId.equals("testStatePropagationPageId")) {
                return _startStatePropagationTest(form, result, request, response);
            }
        }
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startStatePropagationTest(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        TestStatePropagationForm theForm = (TestStatePropagationForm) form;
        LOGGER.info("firstServiceCall");
        return getUIFModelAndView(theForm);
    }

    @Transactional
    @RequestMapping(params = "methodToCall=testStatePropagation")
    public ModelAndView testStatePropagation(@ModelAttribute("KualiForm") TestStatePropagationForm form, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        TestStatePropagationViewHelperService helper = getViewHelperService(form);
        helper.runTests();
        return getUIFModelAndView(form);
    }

    public TestStatePropagationViewHelperService getViewHelperService(TestStatePropagationForm serviceCallForm) {
        if (viewHelperService == null) {
            if (serviceCallForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (TestStatePropagationViewHelperService) serviceCallForm.getView().getViewHelperService();
            } else {
                viewHelperService = (TestStatePropagationViewHelperService) serviceCallForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
