/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.controller.RuleEditorController;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.cm.course.form.CourseRuleManagementWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Override of RuleEditorController for CM KRAD purposes.
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@Controller
@RequestMapping(value = "/courseOfferingRules")
public class CourseRuleEditorController extends RuleEditorController {
    
    private static final String KSKRMS_RULE_CO_TABS_ID = "KS-TabsWidget";
    
    private static final String KSKRMS_RULE_CO_EDITWITHOBJECT_ID = "KS-EditWithObject-EditSection";

    private static final String KSKRMS_MSG_INFO_CO_RULE_CHANGED = "info.krms.agenda.rule.co.changed";
    
    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @Override
    @RequestMapping(params = "methodToCall=addRule")
    public ModelAndView addRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KS-RuleMaintenance-Page-Parent");
        return super.addRule(form, result, request, response);
    }

    /**
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=goToRuleView")
    public ModelAndView goToRuleView(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KS-RuleMaintenance-Page-Parent");
        return super.goToRuleView(form, result, request, response);
    }

    /**
     * Reverts rule to previous state and navigates to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=cancelEditRule")
    public ModelAndView cancelEditRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KS-CourseView-CourseRequisitesPage");
        return super.cancelEditRule(form, result, request, response);
    }

    @Override
    public ModelAndView updateRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KS-CourseView-CourseRequisitesPage");
        
        RuleEditor ruleEditor = getRuleEditor(form);

        //Return with error message if user is currently editing a proposition.
        PropositionEditor proposition = PropositionTreeUtil.getProposition(ruleEditor);
        if ((proposition!=null) && (proposition.isEditMode())) {
            GlobalVariables.getMessageMap().putErrorForSectionId(KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID, KRMSConstants.KRMS_MSG_ERROR_RULE_PREVIEW);
            return getUIFModelAndView(form);
        }

        if (!(ruleEditor.getProposition() == null && ruleEditor.getPropId() == null)) {
            PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor.getPropositionEditor());
            ruleEditor.setDummy(false);
            PropositionTreeUtil.resetNewProp(ruleEditor.getPropositionEditor());
        }
        this.getViewHelper(form).refreshViewTree(ruleEditor);

        //Replace edited rule with existing rule.
        CourseInfoMaintainable courseInfoMaintainable = (CourseInfoMaintainable)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject();
        CourseRuleManagementWrapper courseRuleManagementWrapper = courseInfoMaintainable.getCourseRuleManagementWrapper();
        AgendaEditor agendaEditor = AgendaUtilities.getSelectedAgendaEditor(courseRuleManagementWrapper, ruleEditor.getKey());
        agendaEditor.getRuleEditors().put(ruleEditor.getKey(), ruleEditor);

        if (!form.getActionParameters().containsKey(UifParameters.NAVIGATE_TO_PAGE_ID)) {
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, KRMSConstants.KRMS_AGENDA_MAINTENANCE_PAGE_ID);
        }
        return super.navigate(form, result, request, response);
    }
    
    

    @Override
    public ModelAndView deleteRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request,
            HttpServletResponse response) {
        MaintenanceDocumentForm documentForm = (MaintenanceDocumentForm) form;
        CourseInfoMaintainable courseInfoMaintainable = (CourseInfoMaintainable)documentForm.getDocument().getNewMaintainableObject();
        RuleManagementWrapper ruleWrapper = courseInfoMaintainable.getCourseRuleManagementWrapper();
        String ruleKey = AgendaUtilities.getRuleKey(documentForm);

        AgendaEditor agenda = AgendaUtilities.getSelectedAgendaEditor(ruleWrapper, ruleKey);
        if (agenda != null) {
            RuleEditor ruleEditor = agenda.getRuleEditors().get(ruleKey);

            //Only add rules to delete list that are already persisted.
            if (ruleEditor.getId() != null) {
                agenda.getDeletedRules().add(ruleEditor);
            }

            RuleEditor dummyRule = new RuleEditor(ruleEditor.getKey(), true, ruleEditor.getRuleTypeInfo());
            dummyRule.setParent(ruleEditor.getParent());
            agenda.getRuleEditors().put(ruleEditor.getKey(), dummyRule);
        }

        return getUIFModelAndView(documentForm);
    }

    /**
     * Retrieves selected proposition key and initializes edit on propostion.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=getSelectedKey")
    public ModelAndView getSelectedKey(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) {

        //Clear the current states of the tabs to open the first tab again with the edit tree.
        Map<String, String> states = (Map<String, String>) form.getClientStateForSyncing().get(KSKRMS_RULE_CO_TABS_ID);
        states.put(KRMSConstants.KRMS_PARM_ACTIVE_TAB, KSKRMS_RULE_CO_EDITWITHOBJECT_ID);

        //Set the selected rule statement key.
        String selectedKey = request.getParameter(KRMSConstants.KRMS_PARM_SELECTED_KEY);
        getRuleEditor(form).setSelectedKey(selectedKey);

        return this.goToEditProposition(form, result, request, response);
    }

    protected void compareRulePropositions(MaintenanceDocumentForm form, RuleEditor ruleEditor) {

        RuleManagementWrapper ruleWrapper = getCourseMaintainableFrom(form).getCourseRuleManagementWrapper();

        //Compare CO to CLU and display info message
        if (ruleEditor.getProposition() != null) {
            if (!this.getViewHelper(form).compareRules(ruleWrapper.getRuleEditor())) {
                GlobalVariables.getMessageMap().putInfoForSectionId(KRMSConstants.KRMS_RULE_TREE_GROUP_ID, KSKRMS_MSG_INFO_CO_RULE_CHANGED);
            } else if (GlobalVariables.getMessageMap().containsMessageKey(KRMSConstants.KRMS_RULE_TREE_GROUP_ID)) {
                GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRMSConstants.KRMS_RULE_TREE_GROUP_ID);
            }
        }
    }
    
    /**
     * 
     * This is used on the 'Edit Rule' tab on the Course Requisite page (once a rule has already been selected).
     * 
     * @see org.kuali.rice.krms.controller.RuleEditorController#getRuleEditor(org.kuali.rice.krad.web.form.UifFormBase)
     */
    @Override
    protected RuleEditor getRuleEditor(UifFormBase form) {
        if (form instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) form;
            CourseInfoMaintainable courseInfoMaintainable = getCourseMaintainableFrom(maintenanceDocumentForm);
            RuleManagementWrapper ruleWrapper = courseInfoMaintainable.getCourseRuleManagementWrapper();
            return ruleWrapper.getRuleEditor();
        }
        return null;
    }
    
    /**
     * 
     * This is used on the Course Requisite screen to determine the matching {@link RuleEditor} for the selected rule.
     * 
     * @see org.kuali.rice.krms.controller.RuleEditorController#retrieveSelectedRuleEditor(org.kuali.rice.krad.web.form.MaintenanceDocumentForm)
     */
    @Override
    protected RuleEditor retrieveSelectedRuleEditor(MaintenanceDocumentForm document){

        CourseInfoMaintainable courseInfoMaintainable = getCourseMaintainableFrom(document);
        RuleManagementWrapper ruleWrapper = courseInfoMaintainable.getCourseRuleManagementWrapper();

        String ruleKey = document.getActionParamaterValue("ruleKey");
        RuleEditor ruleEditor = getSelectedRuleEditor(ruleWrapper, ruleKey);
        ruleWrapper.setRuleEditor((RuleEditor) ObjectUtils.deepCopy(ruleEditor));

        return ruleWrapper.getRuleEditor();
    }

    protected RuleEditor getSelectedRuleEditor(RuleManagementWrapper wrapper, String ruleKey) {

        AgendaEditor agendaEditor = getSelectedAgendaEditor(wrapper, ruleKey);
        if (agendaEditor != null) {
            return agendaEditor.getRuleEditors().get(ruleKey);
        }

        return null;
    }

    protected AgendaEditor getSelectedAgendaEditor(RuleManagementWrapper wrapper, String ruleKey) {

        for (AgendaEditor agendaEditor : wrapper.getAgendas()) {
            if (agendaEditor.getRuleEditors().containsKey(ruleKey)) {
                return agendaEditor;
            }
        }

        return null;
    }
    
    /**
     * Retrieves the {@link CourseInfoMaintainable} instance from the {@link MaintenanceDocumentForm} in session
     * 
     * @param form {@link MaintenanceDocumentForm}
     * @param {@link CourseInfoMaintainable}
     */
    protected CourseInfoMaintainable getCourseMaintainableFrom(final MaintenanceDocumentForm form) {
        return (CourseInfoMaintainable) form.getDocument().getNewMaintainableObject();
    }

}
