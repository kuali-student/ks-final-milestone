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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.DiagnoseRolloverForm;
import org.kuali.student.enrollment.class2.courseoffering.form.TestServiceCallForm;
import org.kuali.student.enrollment.class2.courseoffering.service.DiagnoseRolloverViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.TestServiceCallViewHelperService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/testServiceCall")
public class TestServiceController extends UifControllerBase {
    private TestServiceCallViewHelperService viewHelperService;
    private CourseOfferingSetService socService;
    private CourseOfferingService coService;
    private TypeService typeService;
    private StateService stateService;

    private static final Logger LOGGER = Logger.getLogger(DiagnoseRolloverController.class);

    public static final String PAGE_ID = "pageId";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new TestServiceCallForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        TestServiceCallForm theForm = (TestServiceCallForm) form;
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

    private ModelAndView _startFirstServiceCall(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                HttpServletRequest request, HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        TestServiceCallForm theForm = (TestServiceCallForm) form;
        LOGGER.info("firstServiceCall");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=testSocService")
    public ModelAndView testSocService(@ModelAttribute("KualiForm") TestServiceCallForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        TestServiceCallViewHelperService helper = getViewHelperService(form);
        List<String> socIds = helper.getSocIdsByTerm("20123");
        return getUIFModelAndView(form);
    }

    public TestServiceCallViewHelperService getViewHelperService(TestServiceCallForm serviceCallForm) {
        if (viewHelperService == null) {
            if (serviceCallForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (TestServiceCallViewHelperService) serviceCallForm.getView().getViewHelperService();
            } else {
                viewHelperService = (TestServiceCallViewHelperService) serviceCallForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
