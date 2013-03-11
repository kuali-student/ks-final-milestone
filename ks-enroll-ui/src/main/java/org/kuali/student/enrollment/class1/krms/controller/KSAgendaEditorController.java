package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;
import org.kuali.rice.krms.impl.rule.AgendaEditorBusRule;
import org.kuali.rice.krms.impl.ui.AgendaEditor;
import org.kuali.rice.krms.impl.ui.AgendaEditorController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/18
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = org.kuali.rice.krms.impl.util.KrmsImplConstants.WebPaths.AGENDA_EDITOR_PATH)
public class KSAgendaEditorController extends AgendaEditorController {

    /**
     * This method updates the existing rule in the agenda.
     */
    @RequestMapping(params = "methodToCall=" + "editRule")
    public ModelAndView editRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        AgendaEditor agendaEditor = ((AgendaEditor)maintenanceForm.getDocument().getDocumentDataObject());

        // this is the root of the tree:
        AgendaItemBo firstItem = getFirstAgendaItem(agendaEditor.getAgenda());

        AgendaItemBo node = getAgendaItemById(firstItem, agendaEditor.getSelectedAgendaItemId());
        AgendaItemBo agendaItemLine = agendaEditor.getAgendaItemLine();

        agendaItemLine.getRule().setAttributes(agendaEditor.getCustomRuleAttributesMap());
        //updateRuleAction(agendaEditor);

        AgendaEditorBusRule rule = new AgendaEditorBusRule();
        MaintenanceDocument document = maintenanceForm.getDocument();
        if (rule.processAgendaItemBusinessRules(document)) {
            node.setRule(agendaItemLine.getRule());
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "AgendaEditorView-Agenda-Page");
        } else {
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "AgendaEditorView-EditRule-Page");
        }
        return super.navigate(form, result, request, response);
    }

    private AgendaItemBo getFirstAgendaItem(AgendaBo agenda) {
        AgendaItemBo firstItem = null;
        if (agenda != null && agenda.getItems() != null) for (AgendaItemBo agendaItem : agenda.getItems()) {
            if (agenda.getFirstItemId().equals(agendaItem.getId())) {
                firstItem = agendaItem;
                break;
            }
        }
        return firstItem;
    }

    /**
     * Search the tree for the agenda item with the given id.
     */
    private AgendaItemBo getAgendaItemById(AgendaItemBo node, String agendaItemId) {
        if (node == null) throw new IllegalArgumentException("node must be non-null");

        AgendaItemBo result = null;

        if (agendaItemId.equals(node.getId())) {
            result = node;
        }
        return result;
    }
}
