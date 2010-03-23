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
