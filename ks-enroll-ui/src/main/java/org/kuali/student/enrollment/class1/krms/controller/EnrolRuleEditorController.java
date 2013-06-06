/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.controller.RuleEditorController;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.service.impl.EnrolRuleViewHelperServiceImpl;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.krms.KRMSConstants;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Override of RuleEditorController for Student
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = KRMSConstants.WebPaths.RULE_STUDENT_EDITOR_PATH)
public class EnrolRuleEditorController extends RuleEditorController {

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
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    @RequestMapping(params = "methodToCall=addRule")
    public ModelAndView addRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;

        RuleEditor ruleEditor = AgendaUtilities.getSelectedRuleEditor(document);
        EnrolRuleEditor enrolRuleEditor = new EnrolRuleEditor(ruleEditor.getKey(), false, ruleEditor.getRuleTypeInfo());
        AgendaUtilities.getRuleWrapper(document).setRuleEditor(enrolRuleEditor);

        this.getViewHelper(form).refreshInitTrees(enrolRuleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-RuleMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="methodToCall=viewCourseRange")
    public ModelAndView viewCourseRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialog = "courseRangeLookup";
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        CORuleManagementWrapper ruleWrapper = (CORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
        EnrolPropositionEditor prop = (EnrolPropositionEditor) ruleWrapper.getEnrolRuleEditor().getProposition();

        if (prop.getCluSet() != null) {
            ruleWrapper.setClusInRange(prop.getCluSet().getClusInRange());
        }

        return showDialog(dialog, form, request, response);
     }

    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=addRange")
    public ModelAndView addRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        EnrolRuleEditor rule = (EnrolRuleEditor) getRuleEditor(form);
        EnrolPropositionEditor prop = (EnrolPropositionEditor) PropositionTreeUtil.getProposition(rule);

        if(prop.getCluSet()==null){
            prop.setCluSet(new CluSetInformation());
        }

        MembershipQueryInfo membershipQueryInfo = prop.getCluSetRange().buildMembershipQuery(prop.getCluSet().getMembershipQueryInfo());
        prop.getCluSet().setMembershipQueryInfo(membershipQueryInfo);
        prop.getCluSet().setClusInRange(this.getViewHelper(form).getCoursesInRange(membershipQueryInfo));

        return getUIFModelAndView(form);
    }

    /**
     *
     * @param form
     * @return
     */
    protected EnrolRuleViewHelperServiceImpl getViewHelper(UifFormBase form) {
        return (EnrolRuleViewHelperServiceImpl) KSControllerHelper.getViewHelperService(form);
    }
}
