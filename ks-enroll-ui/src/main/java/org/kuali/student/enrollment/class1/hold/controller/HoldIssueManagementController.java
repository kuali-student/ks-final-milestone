/**
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
 *
 * Created by dietrich on 2014/08/05
 */
package org.kuali.student.enrollment.class1.hold.controller;

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueInfoWrapper;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueManagementForm;
import org.kuali.student.enrollment.class1.hold.service.HoldIssueViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/holdIssueManagement")
public class HoldIssueManagementController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new HoldIssueManagementForm();
    }

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") HoldIssueManagementForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HoldIssueInfoWrapper> results = new ArrayList<HoldIssueInfoWrapper>();
        try {

            results = this.getViewHelper(form).searchHolds(form);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(HoldIssueConstants.HOLD_ISSUE_SEARCH_ERROR_MSG,e); //To change body of catch statement use File | Settings | File Templates.
        }



        form.setHoldIssueInfoList(results);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addHold")
    public ModelAndView addHold(@ModelAttribute("KualiForm") HoldIssueManagementForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, HoldIssueConstants.HOLD_ISSUE_CREATE_PAGE);
        return super.navigate(form, result, request, response);
    }

    protected HoldIssueViewHelperService getViewHelper(UifFormBase form) {
        return (HoldIssueViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

}
