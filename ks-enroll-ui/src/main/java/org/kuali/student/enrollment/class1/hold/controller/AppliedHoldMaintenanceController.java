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
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.service.HoldsViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
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
 * @author Kuali Student Blue Team (SA)
 */
@Controller
@RequestMapping(value = "/appliedHoldMaintenance")
public class AppliedHoldMaintenanceController extends MaintenanceDocumentController {

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        if (!(form instanceof MaintenanceDocumentForm)){
            throw new RuntimeException("Unexpected type: " + form);
        }
        MaintenanceDocumentForm document = this.getMaintenanceDocumentForm( form);
        AppliedHoldMaintenanceWrapper holdWrapper = (AppliedHoldMaintenanceWrapper) document.getDocument().getDocumentDataObject();

        if(!this.getViewHelper(form).canApply(holdWrapper.getHoldIssue().getId())){
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_APPLY);
        }else{
            try {
                super.route(form, result, request, response);
            } catch (Exception e) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, e).getMessage());
            }
        }
        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }
        return back(form, result, request, response);

    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=searchHoldIssueByCode")
    public ModelAndView searchHoldIssueByCode(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        AppliedHoldMaintenanceWrapper holdWrapper = (AppliedHoldMaintenanceWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        holdWrapper.setHoldIssue(this.getViewHelper(form).searchHoldIssueByCode(holdWrapper.getHoldCode()));

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }
        return getUIFModelAndView(form);
    }

    /**
     * @param form
     * @return
     */
    protected HoldsViewHelperService getViewHelper(UifFormBase form) {
        return (HoldsViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    //Method for checking if the form is an instance of maintenanceDocumentForm, then returning the casted form
    protected MaintenanceDocumentForm getMaintenanceDocumentForm(UifFormBase form) {
        if (form instanceof MaintenanceDocumentForm) {
            return (MaintenanceDocumentForm) form;
        }
        throw new RuntimeException("Error retrieving Maintenance document form from UifFormBase");
    }
}
