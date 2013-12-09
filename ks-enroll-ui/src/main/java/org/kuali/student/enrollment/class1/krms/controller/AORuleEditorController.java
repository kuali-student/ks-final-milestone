package org.kuali.student.enrollment.class1.krms.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.student.enrollment.class1.krms.dto.AORuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.service.impl.AORuleViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.krms.util.EnrolKRMSConstants;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleEditor;
import org.kuali.student.enrollment.class1.krms.service.impl.AORuleEditorMaintainableImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Override of RuleEditorController for Student
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/activityOfferingRules")
public class AORuleEditorController extends EnrolRuleEditorController {

    /**
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

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_AO_MAINTENANCE_PAGE_ID);
        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();

        AORuleEditor ruleEditor = (AORuleEditor) AgendaUtilities.retrieveSelectedRuleEditor((MaintenanceDocumentForm) form);

        this.getViewHelper(form).refreshInitTrees(ruleEditor);
        return super.navigate(form, result, request, response);
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

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_AO_MAINTENANCE_PAGE_ID);
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

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_AO_MAINTENANCE_PAGE_ID);
        return super.cancelEditRule(form, result, request, response);
    }

    /**
     * Updates rule and redirects to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=updateRule")
    public ModelAndView updateRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_AO_MAINTENANCE_PAGE_ID);
        return super.updateRule(form, result, request, response);
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
        Map<String, String> states = (Map<String, String>) form.getClientStateForSyncing().get(EnrolKRMSConstants.KSKRMS_RULE_AO_TABS_ID);
        states.put(KRMSConstants.KRMS_PARM_ACTIVE_TAB, EnrolKRMSConstants.KSKRMS_RULE_AO_EDITWITHOBJECT_ID);

        //Set the selected rule statement key.
        String selectedKey = request.getParameter(KRMSConstants.KRMS_PARM_SELECTED_KEY);
        getRuleEditor(form).setSelectedKey(selectedKey);

        return this.goToEditProposition(form, result, request, response);
    }

    /**
     * Test method for a controller that invokes a dialog lightbox.
     *
     * @param form     - test form
     * @param result   - Spring form binding result
     * @param request  - http request
     * @param response - http response
     * @return
     */
    @RequestMapping(params = "methodToCall=compareRules")
    public ModelAndView compareRules(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        AORuleManagementWrapper ruleWrapper = (AORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();

        RuleEditor aoRuleEditor = ruleWrapper.getRuleEditor();

        if(aoRuleEditor == null) {
            String ruleId = document.getActionParamaterValue(KRMSConstants.KRMS_PARM_RULE_KEY);
            if ((ruleId != null) && (StringUtils.isNotBlank(ruleId))) {
                //Get a specific ruleEditor based on the ruleId.
                aoRuleEditor = AgendaUtilities.getSelectedRuleEditor(ruleWrapper, ruleId);
            }
        }

        RuleEditor cluRuleEditor = ((AORuleEditor) aoRuleEditor).getCluEditor();
        //Build the compare rule tree
        ruleWrapper.setCompareTree(this.getViewHelper(form).buildCompareTree(cluRuleEditor, aoRuleEditor));
        ruleWrapper.setCompareLightBoxHeader(aoRuleEditor.getRuleTypeInfo().getDescription());

        // redirect back to client to display lightbox
        return showDialog(EnrolKRMSConstants.KSKRMS_DIALOG_COMPARE_CLU_CO_AO, form, request, response);
    }

    /**
     * Test method for a controller that invokes a dialog lightbox.
     *
     * @param form     - test form
     * @param result   - Spring form binding result
     * @param request  - http request
     * @param response - http response
     * @return
     */
    @RequestMapping(params = "methodToCall=viewCoAndCluRules")
    public ModelAndView viewCoAndCluRules(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        Object dataObject = document.getDocument().getNewMaintainableObject().getDataObject();
        if (dataObject instanceof AORuleManagementWrapper) {
            AORuleManagementWrapper ruleWrapper = (AORuleManagementWrapper) dataObject;
            String ruleId = document.getActionParamaterValue("ruleKey");
            RuleEditor aoRuleEditor = null;
            if ((ruleId != null) && (StringUtils.isNotBlank(ruleId))) {
                //Get a specific ruleEditor based on the ruleId.
                aoRuleEditor = AgendaUtilities.getSelectedRuleEditor(ruleWrapper, ruleId);
            } else {
                //Get the current editing ruleEditor.
                aoRuleEditor = ruleWrapper.getRuleEditor();
            }

            RuleEditor cluRuleEditor = ((AORuleEditor) aoRuleEditor).getCluEditor();
            //Build the compare rule tree
            ruleWrapper.setCompareTree(this.getViewHelper(form).buildMultiViewTree(cluRuleEditor, aoRuleEditor.getParent()));
            ruleWrapper.setCompareLightBoxHeader(aoRuleEditor.getRuleTypeInfo().getDescription());
        }

        // redirect back to client to display lightbox
        return showDialog(EnrolKRMSConstants.KSKRMS_DIALOG_VIEW_CLU_CO, form, request, response);
    }

    /**
     * Deletes selected rule from agenda on Manage Course Requistes page
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=deleteRuleStatements")
    public ModelAndView deleteRuleStatements(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper(document);
        String ruleKey = AgendaUtilities.getRuleKey(document);

        AgendaEditor agenda = AgendaUtilities.getSelectedAgendaEditor(ruleWrapper, ruleKey);
        if (agenda != null) {
            RuleEditor ruleEditor = agenda.getRuleEditors().get(ruleKey);
            if(ruleEditor.getProposition() != null){
            ruleEditor.setProposition(null);
            }
            else{
                form.getClientStateForSyncing().clear();
                ruleEditor.setDummy(false);
            }
            //Compare rule with parent rule.
            compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);
            this.getViewHelper(form).refreshViewTree(ruleEditor);

        }

        return getUIFModelAndView(document);
    }

    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=copyEditCoRule")
    public ModelAndView copyEditCoRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;

        AORuleEditor ruleEditor = (AORuleEditor) AgendaUtilities.getSelectedRuleEditor(document);
        AORuleEditor enrolRuleEditor = new AORuleEditor(ruleEditor.getKey(), false, ruleEditor.getRuleTypeInfo());
        enrolRuleEditor.setParent(ruleEditor.getParent());
        enrolRuleEditor.setCluEditor(ruleEditor.getCluEditor());

        PropositionEditor parentProp = ruleEditor.getParent().getPropositionEditor();
        AORuleEditorMaintainableImpl aoMaintainable = (AORuleEditorMaintainableImpl) document.getDocument().getNewMaintainableObject();
        aoMaintainable.initPropositionEditor(parentProp);

        enrolRuleEditor.setProposition(((AORuleViewHelperServiceImpl) this.getViewHelper(form)).copyCOProposition(parentProp));
        AgendaUtilities.getRuleWrapper(document).setRuleEditor(enrolRuleEditor);

        this.getViewHelper(form).refreshInitTrees(enrolRuleEditor);
        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_AO_MAINTENANCE_PAGE_ID);

        return super.navigate(form, result, request, response);

    }

    /**
     * Deletes selected rule from agenda on Manage Course Requistes page
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @Override
    @RequestMapping(params = "methodToCall=deleteRule")
    public ModelAndView deleteRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper(document);
        String ruleKey = AgendaUtilities.getRuleKey(document);

        AgendaEditor agenda = AgendaUtilities.getSelectedAgendaEditor(ruleWrapper, ruleKey);
        if (agenda != null) {
            AORuleEditor ruleEditor = (AORuleEditor) agenda.getRuleEditors().get(ruleKey);

            //Only add rules to delete list that are already persisted.
            if (ruleEditor.getId() != null) {
                agenda.getDeletedRules().add(ruleEditor);
            }

            AORuleEditor dummyRule = new AORuleEditor(ruleEditor.getKey(), true, ruleEditor.getRuleTypeInfo());
            dummyRule.setParent(ruleEditor.getParent());
            dummyRule.setCluEditor(ruleEditor.getCluEditor());
            agenda.getRuleEditors().put(ruleEditor.getKey(), dummyRule);
        }

        return getUIFModelAndView(document);
    }

    protected void compareRulePropositions(MaintenanceDocumentForm form, RuleEditor ruleEditor) {

        RuleManagementWrapper ruleWrapper = (RuleManagementWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        //Compare CO to CLU and display info message
        if (ruleEditor.getProposition() != null) {
            if (!this.getViewHelper(form).compareRules(ruleWrapper.getRuleEditor())) {
                GlobalVariables.getMessageMap().putInfoForSectionId(KRMSConstants.KRMS_RULE_TREE_GROUP_ID, EnrolKRMSConstants.KSKRMS_MSG_INFO_AO_RULE_CHANGED);
            } else if (GlobalVariables.getMessageMap().containsMessageKey(KRMSConstants.KRMS_RULE_TREE_GROUP_ID)) {
                GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRMSConstants.KRMS_RULE_TREE_GROUP_ID);
            }
        }
    }

}


