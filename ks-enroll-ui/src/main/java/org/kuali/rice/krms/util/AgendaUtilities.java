package org.kuali.rice.krms.util;

import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/05
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaUtilities {

    public static RuleEditor retrieveSelectedRuleEditor(MaintenanceDocumentForm document) {

        RuleManagementWrapper ruleWrapper = getRuleWrapper(document);
        RuleEditor ruleEditor = getSelectedRuleEditor(ruleWrapper, getRuleKey(document));
        ruleWrapper.setRuleEditor((RuleEditor) ObjectUtils.deepCopy(ruleEditor));

        return ruleWrapper.getRuleEditor();
    }

    public static RuleEditor getSelectedRuleEditor(MaintenanceDocumentForm document) {
        return AgendaUtilities.getSelectedRuleEditor(getRuleWrapper(document), getRuleKey(document));
    }

    public static RuleEditor getSelectedRuleEditor(RuleManagementWrapper wrapper, String ruleKey) {

        AgendaEditor agendaEditor = getSelectedAgendaEditor(wrapper, ruleKey);
        if (agendaEditor != null) {
            return agendaEditor.getRuleEditors().get(ruleKey);
        }

        return null;
    }

    public static AgendaEditor getSelectedAgendaEditor(MaintenanceDocumentForm document) {
        return AgendaUtilities.getSelectedAgendaEditor(getRuleWrapper(document), getRuleKey(document));
    }

    public static AgendaEditor getSelectedAgendaEditor(RuleManagementWrapper wrapper, String ruleKey) {

        for (AgendaEditor agendaEditor : wrapper.getAgendas()) {
            if (agendaEditor.getRuleEditors().containsKey(ruleKey)) {
                return agendaEditor;
            }
        }

        return null;
    }

    public static String getRuleKey(MaintenanceDocumentForm document) {
        return document.getActionParamaterValue("ruleKey");
    }

    public static RuleManagementWrapper getRuleWrapper(MaintenanceDocumentForm document) {
        return (RuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
    }

}
