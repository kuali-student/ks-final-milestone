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
 */
package org.kuali.student.enrollment.class1.hold.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.hold.dto.AuthorizingOrgWrapper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
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

/**
 * This class provides a controller for HoldIssue objects
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holdIssueMaintenance")
public class HoldIssueMaintenanceController extends MaintenanceDocumentController {

    @RequestMapping(params = "methodToCall=create")
    public ModelAndView create(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueMaintenanceWrapper holdIssueWrapper = (HoldIssueMaintenanceWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        this.getViewHelper(form).validateHold(holdIssueWrapper);
        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }
        form.getView().setApplyDirtyCheck(false);
        holdIssueWrapper.setIsSaveSuccess(true);
        GlobalVariables.getMessageMap().putInfo(HoldIssueConstants.HOLD_ISSUE_PROCESS, HoldIssueConstants.HOLD_ISSUE_SAVE_SUCCESS_MSG);
        holdIssueWrapper.setIsSaveSuccess(true);
        clearSearchValues(holdIssueWrapper);
        return this.route(form, result, request, response);
    }

    private void clearSearchValues( HoldIssueMaintenanceWrapper holdIssueWrapper) {
        holdIssueWrapper.setName("");
        holdIssueWrapper.setOrganizationId("");
        holdIssueWrapper.setOrgName("");
        holdIssueWrapper.setStateKey("");
        holdIssueWrapper.setTypeKey("");
        holdIssueWrapper.setDescr("");
        holdIssueWrapper.setOrgName("");
        holdIssueWrapper.setCode("");
        holdIssueWrapper.setOrgAddress("");
        holdIssueWrapper.setBaseType("");
        holdIssueWrapper.setFirstDate("");
        holdIssueWrapper.setFirstTerm("");
        holdIssueWrapper.setOrganizationNames(new ArrayList<AuthorizingOrgWrapper>());
    }


    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        super.route(form, result, request, response);
        return back(form, result, request, response);
    }

    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

    /**
     * @param form
     * @return
     */
    protected HoldIssueViewHelperService getViewHelper(UifFormBase form) {
        return (HoldIssueViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

}
