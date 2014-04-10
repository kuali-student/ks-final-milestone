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

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.AgendaSection;

import java.util.List;

/**
 * @author Kuali Student Team
 */
public class FinalExamAgendaBuilder extends AgendaBuilder {

    /**
     * This method dynamically build the components on the screen to render an agenda.
     *
     * @param agenda
     * @return
     */
    public Component buildAgenda(AgendaEditor agenda, int index, AgendaSection agendaSection) {

        FinalExamAgendaSection feAgendaSection = (FinalExamAgendaSection) agendaSection;

        String agendaSuffix = "_agenda" + Integer.toString(index);
        Group group = ComponentUtils.copy(feAgendaSection.getAgendaPrototypeMap().get(agenda.getAgendaTypeInfo().getType()), agendaSuffix);
        group.setHeaderText(agenda.getAgendaTypeInfo().getDescription());

        String bindingPrefix = "agendas[" + index + "]";
        List<CollectionGroup> components = ViewLifecycleUtils.getElementsOfTypeDeep(group, CollectionGroup.class);
        for (CollectionGroup fieldCollectionGroup : components) {
            ComponentUtils.prefixBindingPath(fieldCollectionGroup, bindingPrefix);
            fieldCollectionGroup.setSubCollectionSuffix(agendaSuffix);
        }

        List<Action> actionLinks = ViewLifecycleUtils.getElementsOfTypeDeep(group, Action.class);
        for (Action actionLink : actionLinks) {
            actionLink.getActionParameters().put(UifParameters.SELECTED_LINE_INDEX, "" + index);
        }

        return group;
    }

}
