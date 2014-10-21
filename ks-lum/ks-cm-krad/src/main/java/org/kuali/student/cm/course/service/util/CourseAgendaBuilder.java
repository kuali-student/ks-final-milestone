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
package org.kuali.student.cm.course.service.util;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.AgendaSection;

/**
 * @author Kuali Student Team
 */
public class CourseAgendaBuilder extends AgendaBuilder {
    
    private static final String KSKRMS_MSG_WARNING_CO_RULE_HASPARENT = "warning.krms.agenda.rule.co.hasparent";

    private static final String KSKRMS_MSG_WARNING_CO_RULE_EMPTY = "warning.krms.agenda.rule.co.empty";
    
    private static final String KSKRMS_MSG_INFO_CO_RULE_CHANGED = "info.krms.agenda.rule.co.changed";
    
    /**
     * This method dynamically builds a disclosure section for each rule that already exists.
     *
     * @param rule
     * @return
     */
    protected Component buildRule(RuleEditor rule, String bindingPrefix, AgendaSection agendaSection) {
        Component group = super.buildRule(rule, bindingPrefix, agendaSection);

        //Add warning messages for empty or deleted rules.
        boolean hasMessage = false;
        if (rule.isDummy() && rule.getParent() != null)  {
            GlobalVariables.getMessageMap().putWarningForSectionId(group.getId(), KSKRMS_MSG_WARNING_CO_RULE_HASPARENT);
            hasMessage = true;
        } else if ((rule.getProposition()==null) && (rule.getParent()!=null) && (rule.getParent().getProposition()!=null)) {
            GlobalVariables.getMessageMap().putWarningForSectionId(group.getId(), KSKRMS_MSG_WARNING_CO_RULE_EMPTY);
            hasMessage = true;
        }
        //Open disclosure if rule has statements
        if(hasMessage || !rule.isDummy()){
            ((Group) group).getDisclosure().setDefaultOpen(true);
        }

        return group;
    }

}
