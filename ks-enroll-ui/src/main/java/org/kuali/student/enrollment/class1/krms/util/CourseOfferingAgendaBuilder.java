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
package org.kuali.student.enrollment.class1.krms.util;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.AgendaSection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class CourseOfferingAgendaBuilder extends AgendaBuilder {

    /**
     * This method dynamically builds a disclosure section for each rule that already exists.
     *
     * @param rule
     * @return
     */
    protected Component buildRule(RuleEditor rule, String bindingPrefix, AgendaSection agendaSection) {
        Component group = super.buildRule(rule, bindingPrefix, agendaSection);

        //Add warning messages for empty or deleted rules.
        if (rule.isDummy() && rule.getParent() != null)  {
            GlobalVariables.getMessageMap().putWarningForSectionId(group.getId(), "warning.krms.agenda.rule.co.hasparent");
        } else if ((rule.getProposition()==null) && (rule.getParent()!=null) && (rule.getParent().getProposition()!=null)) {
            GlobalVariables.getMessageMap().putWarningForSectionId(group.getId(), "warning.krms.agenda.rule.co.empty");
        }

        //Add Info message if co rule differs from clu rule.
        if (!this.getViewHelperService().compareRules(rule)) {
            if(!rule.isDummy()) {
                GlobalVariables.getMessageMap().putInfoForSectionId(group.getId(), "info.krms.agenda.rule.co.changed");
            } else {
                GlobalVariables.getMessageMap().putInfoForSectionId(group.getId(), "info.krms.agenda.rule.differ");
            }
        }

        return group;
    }

}
