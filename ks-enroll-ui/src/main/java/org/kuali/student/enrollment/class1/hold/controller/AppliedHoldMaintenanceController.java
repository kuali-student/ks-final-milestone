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
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleRefreshBuild;
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
import org.kuali.student.r2.core.constants.HoldServiceConstants;
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

    /**
     * Setups a new <code>MaintenanceDocumentView</code> with the edit maintenance
     * action
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_EDIT)
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);
        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        if (HoldsConstants.APPLIED_HOLDS_ACTION_APPLY.equals(holdWrapper.getAction())) {
            return getUIFModelAndView(form, HoldsConstants.APPLIED_HOLD_APPLY_PAGE_ID);
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_EDIT.equals(holdWrapper.getAction())) {
            return getUIFModelAndView(form, HoldsConstants.APPLIED_HOLD_EDIT_PAGE_ID);
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE.equals(holdWrapper.getAction())) {
            return getUIFModelAndView(form, HoldsConstants.APPLIED_HOLD_EXPIRE_PAGE_ID);
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_DELETE.equals(holdWrapper.getAction())) {
            return getUIFModelAndView(form, HoldsConstants.APPLIED_HOLD_DELETE_PAGE_ID);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=apply")
    public ModelAndView apply(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        if (holdWrapper.getHoldIssue() != null) {
            if (!this.getViewHelper(form).isAuthorized(holdWrapper.getHoldIssue().getId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_APPLY_HOLD)) {
                GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_APPLY);
            } else {
                try {
                    holdWrapper.getAppliedHold().setStateKey(HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY);
                    super.route(form, result, request, response);
                } catch (Exception e) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, KSObjectUtils.unwrapException(20, e).getMessage());
                }
            }
        } else
            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_INVALID);

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return getUIFModelAndView(form);
        }
        return back(form, result, request, response);

    }

    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        if (!this.getViewHelper(form).isAuthorized(holdWrapper.getHoldIssue().getId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_APPLY_HOLD)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_APPLY);
        } else {
            try {
                holdWrapper.getAppliedHold().setStateKey(HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY);
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

    @RequestMapping(params = "methodToCall=expire")
    public ModelAndView expire(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        if (!this.getViewHelper(form).isAuthorized(holdWrapper.getMaintenanceHold().getAppliedHold().getHoldIssueId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_EXPIRE_HOLD)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_EXPIRE,holdWrapper.getMaintenanceHold().getHoldCode());
        } else {
            try {
                holdWrapper.getAppliedHold().setStateKey(HoldServiceConstants.APPLIED_HOLD_EXPIRED_STATE_KEY);
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

    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        if (!this.getViewHelper(form).isAuthorized(holdWrapper.getMaintenanceHold().getAppliedHold().getHoldIssueId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_EXPIRE_HOLD)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_DELETE, holdWrapper.getMaintenanceHold().getHoldCode());
        } else {
            try {
                holdWrapper.getAppliedHold().setStateKey(HoldServiceConstants.APPLIED_HOLD_DELETED_STATE_KEY);
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

        return back(form, result, request, response);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=searchHoldIssueByCode")
    public ModelAndView searchHoldIssueByCode(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        holdWrapper.setHoldIssue(this.getViewHelper(form).searchHoldIssueByCode(holdWrapper.getHoldCode()));

        if (GlobalVariables.getMessageMap().hasErrors()) {
            holdWrapper.setHoldIssue(null);
        }
        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=refresh")
    public ModelAndView refresh(@ModelAttribute("KualiForm") final UifFormBase form, BindingResult result,
                                final HttpServletRequest request, HttpServletResponse response) throws Exception {

        ViewLifecycle.encapsulateLifecycle(form.getView(), form, form.getViewPostMetadata(), null, request, response,
                new ViewLifecycleRefreshBuild());

        AppliedHoldMaintenanceWrapper holdWrapper = this.getAppliedHoldWrapper(form);
        holdWrapper.setHoldIssue(this.getViewHelper(form).searchHoldIssueByCode(holdWrapper.getHoldCode()));

        return getUIFModelAndView(form);
    }

    /**
     * @param form
     * @return
     */
    protected HoldsViewHelperService getViewHelper(UifFormBase form) {
        return (HoldsViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    protected AppliedHoldMaintenanceWrapper getAppliedHoldWrapper(UifFormBase form) {
        MaintenanceDocumentForm document = this.getMaintenanceDocumentForm(form);
        return (AppliedHoldMaintenanceWrapper) document.getDocument().getDocumentDataObject();
    }

    //Method for checking if the form is an instance of maintenanceDocumentForm, then returning the casted form
    protected MaintenanceDocumentForm getMaintenanceDocumentForm(UifFormBase form) {
        if (form instanceof MaintenanceDocumentForm) {
            return (MaintenanceDocumentForm) form;
        }
        throw new RuntimeException("Error retrieving Maintenance document form from UifFormBase");
    }
}
