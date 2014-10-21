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

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.service.HoldsViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsUtil;
import org.kuali.student.r2.core.hold.infc.HoldIssue;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides a controller for HoldIssue objects
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holdIssueMaintenance")
public class HoldIssueMaintenanceController extends MaintenanceDocumentController {

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        try {
            super.route(form, result, request, response);
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, e).getMessage());
        }

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }

        // Display succes growl messages.
        HoldIssue issue = this.getHoldIssueWrapper(form).getHoldIssue();
        if(issue.getId()==null){
            HoldsUtil.showMessage(HoldsConstants.HOLDS_ISSUE_MSG_SUCCESS_HOLD_ISSUE_CREATED, issue.getHoldCode());
        } else {
            HoldsUtil.showMessage(HoldsConstants.HOLDS_ISSUE_MSG_SUCCESS_HOLD_ISSUE_EDITED, issue.getHoldCode());
        }
        return back(form, result, request, response);

    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        if (!(form instanceof DocumentFormBase)){
            throw new RuntimeException("Unexpected type: " + form);
        }
        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

    /**
     * @param form
     * @return
     */
    protected HoldsViewHelperService getViewHelper(UifFormBase form) {
        return (HoldsViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    protected HoldIssueMaintenanceWrapper getHoldIssueWrapper(UifFormBase form) {
        MaintenanceDocumentForm document = this.getMaintenanceDocumentForm(form);
        return (HoldIssueMaintenanceWrapper) document.getDocument().getDocumentDataObject();
    }

    //Method for checking if the form is an instance of maintenanceDocumentForm, then returning the casted form
    protected MaintenanceDocumentForm getMaintenanceDocumentForm(UifFormBase form) {
        if (form instanceof MaintenanceDocumentForm) {
            return (MaintenanceDocumentForm) form;
        }
        throw new RuntimeException("Error retrieving Maintenance document form from UifFormBase");
    }

}
