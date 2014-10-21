/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.ap.development.controller;

import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.development.form.DevelopmentForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.decorators.CourseHelperCacheDecorator;
import org.kuali.student.ap.framework.context.decorators.TermHelperCacheDecorator;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.controller.PlanItemControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/ksapdevelopment/**")
public class DevelopmentController extends KsapControllerBase {



    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new DevelopmentForm();
    }


    /**
     * Sets up basic empty Course search page
     */
    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        super.start(form, request, response);
        form.setViewId("KSAP-DevelopementUtils-FormView");
        form.setView(super.getViewService().getViewById(form.getViewId()));
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=InvalidateCache")
    public ModelAndView invalidateCache(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        ((TermHelperCacheDecorator) KsapFrameworkServiceLocator.getTermHelper()).getCacheManager().clearAll();

        return getUIFModelAndView(form);
    }
}
