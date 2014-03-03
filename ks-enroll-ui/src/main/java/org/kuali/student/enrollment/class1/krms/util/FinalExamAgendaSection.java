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

import com.google.common.collect.Maps;
import org.kuali.rice.krad.uif.component.BindingInfo;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.NodePrototype;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.AgendaSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class FinalExamAgendaSection extends AgendaSection {

    private AgendaBuilder agendaBuilder;
    private Map<String, Group> agendaPrototypeMap;

    public AgendaBuilder getAgendaBuilder() {
        if (this.agendaBuilder == null) {
            this.agendaBuilder = new FinalExamAgendaBuilder();
        }
        return this.agendaBuilder;
    }

    public Map<String, Group> getAgendaPrototypeMap() {
        return agendaPrototypeMap;
    }

    public void setAgendaPrototypeMap(Map<String, Group> agendaPrototypeMap) {
        this.agendaPrototypeMap = agendaPrototypeMap;
    }

}
