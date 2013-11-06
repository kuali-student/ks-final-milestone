package org.kuali.student.enrollment.class1.krms.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.krms.dto.FEAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.service.impl.FERuleEditorMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.service.impl.FERuleViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.krms.util.EnrolKRMSConstants;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Override of RuleEditorController for Student
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/finalExamRules")
public class FERuleEditorController extends EnrolRuleEditorController {

    /**
     * Setups a new <code>MaintenanceDocumentView</code> with the edit maintenance
     * action
     */
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_EDIT)
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);
        this.displayLinkedTermTypeMessages(form);
        return getUIFModelAndView(form);
    }

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


        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper(document);
        ruleWrapper.setAgendaEditor(this.getSelectedAgenda(document, "Edit"));

        //Set a copy on the wrapper so that we can allow the user to cancel his/her action.
        RuleEditor rule = new FERuleEditor();
        AgendaTypeInfo agendaType = ruleWrapper.getAgendaEditor().getAgendaTypeInfo();
        try {
            RuleTypeInfo ruleType = KSCollectionUtils.getOptionalZeroElement(agendaType.getRuleTypes());
            rule.setTypeId(ruleType.getId());
            rule.setRuleTypeInfo(ruleType);
        } catch (OperationFailedException e) {
            throw new RuntimeException("Could not retrieve default rule type.");
        }
        rule.setDummy(Boolean.TRUE);
        ruleWrapper.setRuleEditor(rule);
        if (document.getDocument().getNewMaintainableObject() instanceof FERuleEditorMaintainableImpl) {
            FERuleEditorMaintainableImpl maintainable = (FERuleEditorMaintainableImpl) document.getDocument().getNewMaintainableObject();
            rule.setKey(maintainable.getNextRuleKey());
        }

        this.getViewHelper(form).refreshInitTrees(ruleWrapper.getRuleEditor());

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_FE_MAINTENANCE_PAGE_ID);
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
    @RequestMapping(params = "methodToCall=deleteRule")
    public ModelAndView deleteRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;

        FEAgendaEditor agenda = this.getSelectedAgenda(document, "Delete");
        if (agenda != null) {
            RuleEditor ruleEditor = getSelectedRule(document, "Delete");

            //Only add rules to delete list that are already persisted.
            if (ruleEditor.getId() != null) {
                agenda.getDeletedRules().add(ruleEditor);
            }
            agenda.getRules().remove(ruleEditor);

        }

        return getUIFModelAndView(document);
    }

    /**
     * Method used to invoke the FE inquiry view from Manage Course Offering screen while search input is Course Offering
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

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper(document);
        RuleEditor ruleEditor = getSelectedRule(document, "Edit");

        //Set a copy on the wrapper so that we can allow the user to cancel his/her action.
        ruleWrapper.setRuleEditor((RuleEditor) ObjectUtils.deepCopy(ruleEditor));
        ruleWrapper.setAgendaEditor(this.getSelectedAgenda(document, "Edit"));
        this.getViewHelper(form).refreshInitTrees(ruleWrapper.getRuleEditor());

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_FE_MAINTENANCE_PAGE_ID);
        return super.navigate(form, result, request, response);
    }

    /**
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=loadTermType")
    public ModelAndView loadTermType(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) {

        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) form;
        FERuleEditorMaintainableImpl maintainable = (FERuleEditorMaintainableImpl) maintenanceDocumentForm.getDocument().getNewMaintainableObject();
        FERuleManagementWrapper wrapper = (FERuleManagementWrapper) maintainable.getDataObject();


        Map<String, String> dataObjectKeys = new HashMap<String, String>();
        if (!wrapper.getTermToUse().equals("na")) {
            dataObjectKeys.put("refObjectId", wrapper.getTermToUse());
        } else {
            dataObjectKeys.put("refObjectId", wrapper.getRefObjectId());

        }
        Object dataObject = maintainable.retrieveObjectForEditOrCopy(maintenanceDocumentForm.getDocument(), dataObjectKeys);
        wrapper.setAgendas(((FERuleManagementWrapper) dataObject).getAgendas());


        return getUIFModelAndView(form);
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

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_FE_MAINTENANCE_PAGE_ID);
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

        FERuleEditor ruleEditor = (FERuleEditor) getRuleEditor(form);
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper((MaintenanceDocumentForm) form);

        if (this.getViewHelper(form).validateRule(ruleEditor)) {
            return getUIFModelAndView(form);
        } else {
            PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor.getPropositionEditor());
            PropositionTreeUtil.resetNewProp(ruleEditor.getPropositionEditor());
            this.getViewHelper(form).buildActions(ruleEditor);
        }

        FEAgendaEditor agenda = (FEAgendaEditor) ruleWrapper.getAgendaEditor();
        if (ruleEditor.isDummy()) {
            ruleEditor.setName(ruleWrapper.getRefObjectId() + ":" + ruleEditor.getTypeId() + ":" + agenda.getRules().size() + 1);
            ruleEditor.setDummy(Boolean.FALSE);
            agenda.getRules().add(ruleEditor);
        } else {
            //Replace edited rule with existing rule.
            List<RuleEditor> rules = agenda.getRules();
            for (RuleEditor existingRule : rules) {
                if (existingRule.getKey().equals(ruleEditor.getKey())) {
                    rules.set(rules.indexOf(existingRule), ruleEditor);
                    break;
                }
            }
        }

        this.getViewHelper(form).refreshViewTree(ruleWrapper.getRuleEditor());

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_FE_MAINTENANCE_PAGE_ID);
        return super.navigate(form, result, request, response);
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
        Map<String, String> states = (Map<String, String>) form.getClientStateForSyncing().get(EnrolKRMSConstants.KSKRMS_RULE_FE_TABS_ID);
        states.put(KRMSConstants.KRMS_PARM_ACTIVE_TAB, EnrolKRMSConstants.KSKRMS_RULE_FE_EDITWITHOBJECT_ID);

        //Set the selected rule statement key.
        String selectedKey = request.getParameter(KRMSConstants.KRMS_PARM_SELECTED_KEY);
        getRuleEditor(form).setSelectedKey(selectedKey);

        return this.goToEditProposition(form, result, request, response);
    }

    private RuleEditor getSelectedRule(MaintenanceDocumentForm form, String actionLink) {
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<RuleEditor> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        RuleEditor rule = ((List<RuleEditor>) collection).get(selectedLineIndex);

        return rule;
    }

    private FEAgendaEditor getSelectedAgenda(MaintenanceDocumentForm form, String actionLink) {
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }
        selectedCollectionPath = selectedCollectionPath.substring(0, selectedCollectionPath.lastIndexOf("."));

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        return ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
    }

    /**
     * Parses Date into Long
     *
     * @param time
     * @return date in long value
     */
    protected long parseTimeToMillis(final String time) throws ParseException {
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(time).getTime();
    }

    protected void compareRulePropositions(MaintenanceDocumentForm form, RuleEditor ruleEditor) {
        //We don't do comparisons on Final Exam.
        return;
    }

    protected void displayLinkedTermTypeMessages(MaintenanceDocumentForm form) {

        FERuleManagementWrapper ruleWrapper = (FERuleManagementWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        for(String termType : ruleWrapper.getLinkedTermTypes()){
            GlobalVariables.getMessageMap().putInfoForSectionId("KSFE-AgendaMaintenance-Page-parent", EnrolKRMSConstants.KSKRMS_MSG_INFO_FE_LINKED_TERMTYPE, termType);
        }
    }
}
