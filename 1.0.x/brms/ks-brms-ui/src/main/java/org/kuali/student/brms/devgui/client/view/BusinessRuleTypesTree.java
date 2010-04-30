/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 * 
 */
package org.kuali.student.brms.devgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.brms.devgui.client.model.RuleTypesHierarchyInfo;

/**
 * @author zzraly
 */
public class BusinessRuleTypesTree extends SimpleTree<RuleTypesHierarchyInfo> {

    boolean loaded = false;

    /**
     * @param loaded
     */
    public BusinessRuleTypesTree() {
        super();
    }

    /**
     * Called by the container to initialize the table. Do not call directly.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
        }
    }

    @Override
    public List<String> getPath(RuleTypesHierarchyInfo modelObject) {
        final List<String> items = new ArrayList<String>();

        //remove "KUALI_" from the Agenda Type since it is given and makes the string too long
        String agendaType = modelObject.getAgendaType();
        if (agendaType.startsWith("KUALI_")) {
        	agendaType = agendaType.substring(6);
        }                
        items.add(agendaType);

        String ruleType = modelObject.getBusinessRuleTypeKey();
        if (ruleType.startsWith("KUALI_")) {
        	ruleType = ruleType.substring(6);
        }   
        items.add(ruleType);
        return items;
    }

}
