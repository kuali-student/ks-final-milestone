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
package org.kuali.rice.krms.util;

import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;

/**
 * @author Kuali Student Team
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
