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
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueManagementForm;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueResult;
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
import java.util.Collection;
import java.util.List;
import java.util.Properties;

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
        List<HoldIssueResult> results = new ArrayList<HoldIssueResult>();
        try {

            results = this.getViewHelper(form).searchHolds(form);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(HoldIssueConstants.HOLD_ISSUE_SEARCH_ERROR_MSG,e); //To change body of catch statement use File | Settings | File Templates.
        }
        form.setHoldIssueResultList(results);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addHold")
    public ModelAndView addHold(@ModelAttribute("KualiForm") HoldIssueManagementForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = this.editHold(form, "");
        return super.performRedirect(form, "holdIssueMaintenance", urlParameters);
    }

    /*
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") HoldIssueManagementForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfoWrapper holdIssueInfoWrapper = getSelectedRegistrationCourse(form);

        Properties urlParameters = this.editHold(form, holdIssueInfoWrapper.getHoldIssueInfo().getId());
        return super.performRedirect(form, "holdIssueMaintenance", urlParameters);
    }

    private HoldIssueInfoWrapper getSelectedRegistrationCourse(HoldIssueManagementForm form) {
        return (HoldIssueInfoWrapper) this.getSelectedCollectionObject(form);
    }*/

    /*
      * Method used to refresh Manage hold page (e.g. after edit hold)
      */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=reloadManageHold")
    public ModelAndView reloadManageHold(@ModelAttribute("KualiForm") HoldIssueManagementForm theForm) throws Exception {

        //TODO Reload the page

        return getUIFModelAndView(theForm, "KS-Hold-SearchInput-Page");
    }
    private Object getSelectedCollectionObject(HoldIssueManagementForm form) {

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

    protected HoldIssueViewHelperService getViewHelper(UifFormBase form) {
        return (HoldIssueViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    public static Properties editHold(HoldIssueManagementForm theForm, String holdId) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, HoldIssueMaintenanceWrapper.class.getName());
        urlParameters.put(KRADConstants.OVERRIDE_KEYS,"id");
        urlParameters.put("id", holdId);
        urlParameters.put("viewName", "HoldIssueMaintenanceView");
        return urlParameters;
    }
}
