package org.kuali.rice.krms.util;

import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/05
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaUtilities {

    public static RuleEditor retrieveSelectedRuleEditor(UifFormBase form) {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = (RuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
        String ruleKey = document.getActionParamaterValue("ruleKey");

        RuleEditor ruleEditor = getSelectedRuleEditor(ruleWrapper, ruleKey);

        if(ruleEditor != null){
            ruleWrapper.setRuleEditor((RuleEditor) ObjectUtils.deepCopy(ruleEditor));
        }else {
            ruleWrapper.setRuleEditor(new RuleEditor());
        }
        ruleWrapper.setSelectedRuleId(ruleWrapper.getRuleEditor().getId());

        return ruleWrapper.getRuleEditor();
    }

    public static RuleEditor getSelectedRuleEditor(RuleManagementWrapper wrapper, String ruleKey) {

        for (AgendaEditor agendaEditor : wrapper.getAgendas()) {
            for (RuleEditor ruleEditor : agendaEditor.getRuleEditors()) {
                if ((ruleEditor.getKey() != null) && (ruleEditor.getKey().equals(ruleKey))) {
                    return ruleEditor;
                }
            }
        }

        return null;
    }

    public static RuleEditor getSelectedRuleEditorByType(List<RuleEditor> ruleEditors, String ruleTypeId) {

        for (RuleEditor ruleEditor : ruleEditors) {
            if ((ruleEditor.getTypeId().equals(ruleTypeId))) {
                return ruleEditor;
            }
        }

        return null;
    }
}
