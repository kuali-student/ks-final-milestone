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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class1.hold.dto.AppliedHoldMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldManagementForm;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldResult;
import org.kuali.student.enrollment.class1.hold.service.HoldsViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

/**
 * Controller class for applied hold management.
 *
 * @author Kuali Student Blue Team (SA)
 */
@Controller
@RequestMapping(value = "/appliedHoldManagement")
public class AppliedHoldManagementController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AppliedHoldManagementForm();
    }

    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        if (!(form instanceof AppliedHoldManagementForm)) {
            throw new RuntimeException("Unexpected type: " + form);
        }
        AppliedHoldManagementForm holdForm = (AppliedHoldManagementForm) form;

        holdForm.setHoldResultList(searchAppliedHolds(holdForm));
        return super.start(form, request, response);
    }

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.clearResults();
        form.setHasSearchBeenCalled(true);
        PersonInfo person = this.getViewHelper(form).getStudentById(form.getPerson().getId());
        if (GlobalVariables.getMessageMap().hasErrors()) {
            form.setValidPerson(false);
            return getUIFModelAndView(form);
        } else {
            form.setValidPerson(true);
            form.setPerson(person);
            form.setHoldResultList(searchAppliedHolds(form));
        }

        return getUIFModelAndView(form);
    }

    private List<AppliedHoldResult> searchAppliedHolds(AppliedHoldManagementForm form) {
        List<AppliedHoldResult> results = new ArrayList<AppliedHoldResult>();
        try {
            if (form.isHasSearchBeenCalled()) {
                results = this.getViewHelper(form).searchAppliedHolds(form);
            }
        } catch (Exception e) {
            throw new RuntimeException(HoldsConstants.HOLD_ISSUE_SEARCH_ERROR_MSG, e); //To change body of catch statement use File | Settings | File Templates.
        }

        return results;
    }

    @RequestMapping(params = "methodToCall=applyHold")
    public ModelAndView applyHold(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_APPLY);
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    @RequestMapping(params = "methodToCall=editHold")
    public ModelAndView editHold(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_EDIT);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID, getSelectedHoldIssue(form).getId());
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    @RequestMapping(params = "methodToCall=expireHoldToolbar")
    public ModelAndView expireHoldToolbar(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppliedHoldResult appliedHold = prepareEDConfirmationView(form);
        if (!this.getViewHelper(form).isAuthorized(appliedHold.getHoldIssue().getId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_EXPIRE_HOLD)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_EXPIRE,appliedHold.getHoldIssue().getHoldCode());
            return getUIFModelAndView(form);
        }
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID, appliedHold.getId());
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    @RequestMapping(params = "methodToCall=expireHold")
    public ModelAndView expireHold(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID, getSelectedHoldIssue(form).getId());
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    @RequestMapping(params = "methodToCall=deleteHoldToolbar")
    public ModelAndView deleteHoldToolbar(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        AppliedHoldResult appliedHold = prepareEDConfirmationView(form);
        if (!this.getViewHelper(form).isAuthorized(appliedHold.getHoldIssue().getId(), HoldsConstants.APPLIED_HOLD_ACTION_EVENT_EXPIRE_HOLD)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.HOLD_ISSUE_HOLD_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_DELETE,appliedHold.getHoldIssue().getHoldCode());
            return getUIFModelAndView(form);
        }
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_DELETE);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID, appliedHold.getId());
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    @RequestMapping(params = "methodToCall=deleteHold")
    public ModelAndView deleteHold(@ModelAttribute("KualiForm") AppliedHoldManagementForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = this.getMaintainHoldParameters(form, HoldsConstants.APPLIED_HOLDS_ACTION_DELETE);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID, getSelectedHoldIssue(form).getId());
        return super.performRedirect(form, HoldsConstants.APPLIED_HOLD_BASEURL, urlParameters);
    }

    private AppliedHoldResult getSelectedHoldIssue(AppliedHoldManagementForm form) {
        return (AppliedHoldResult) this.getSelectedCollectionObject(form);
    }

    /*
      * Method used to refresh Manage hold page (e.g. after edit hold)
      */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=reloadManageHold")
    public ModelAndView reloadManageHold(@ModelAttribute("KualiForm") AppliedHoldManagementForm theForm) throws Exception {
        //TODO KSENROLL-14464 Reload the page
        return getUIFModelAndView(theForm, "KS-Hold-SearchInput-Page");
    }

    private Object getSelectedCollectionObject(AppliedHoldManagementForm form) {

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection path was not set for collection action");
        }

        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        int selectedLineIndex = -1;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        // Retrieving the select registration result.
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object item = ((List) collection).get(selectedLineIndex);

        return item;

    }

    protected HoldsViewHelperService getViewHelper(UifFormBase form) {
        return (HoldsViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    public Properties getMaintainHoldParameters(AppliedHoldManagementForm form, String action) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, AppliedHoldMaintenanceWrapper.class.getName());
        urlParameters.put(KRADConstants.OVERRIDE_KEYS, HoldsConstants.HOLDS_URL_PARAMETERS_APPLIED_HOLD_ID + "," +
                HoldsConstants.HOLDS_URL_PARAMETERS_PERSON_ID + "," + HoldsConstants.HOLDS_URL_PARAMETERS_ACTION);
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_PERSON_ID, form.getPerson().getId());
        urlParameters.put(HoldsConstants.HOLDS_URL_PARAMETERS_ACTION, action);
        urlParameters.put("viewName", HoldsConstants.APPLIED_HOLD_MAINTENANCE_VIEWNAME);
        return urlParameters;
    }

    public AppliedHoldResult prepareEDConfirmationView(AppliedHoldManagementForm form) throws Exception{

        List<AppliedHoldResult> holdResultList = form.getHoldResultList();

        for(AppliedHoldResult holdResult : holdResultList) {
            if(holdResult.getIsCheckedByCluster()) {
                return holdResult;
            }
        }

        return null;
    }
}
