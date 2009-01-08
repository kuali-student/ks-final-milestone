/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;

/**
 * @author zzraly
 */
public class BusinessRulesTree extends SimpleTree<RulesHierarchyInfo> {

    boolean loaded = false;

    /**
     * @param loaded
     */
    public BusinessRulesTree() {
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
    public List<String> getPath(RulesHierarchyInfo modelObject) {
        final List<String> items = new ArrayList<String>();
       
        //remove "KUALI_" from the Agenda Type since it is given and makes the string too long
        String agendaType = modelObject.getAgendaType();
        if (agendaType.startsWith("KUALI_")) {
        	agendaType = agendaType.substring(6);
        }
        
        if ((agendaType == null) || (agendaType.isEmpty())) {
            return items;
        }
        items.add(agendaType);

        String ruleType = modelObject.getBusinessRuleType();
        if (ruleType.startsWith("KUALI_")) {
        	ruleType = ruleType.substring(6);
        }        
        
        if ((ruleType == null) || (ruleType.isEmpty())) {
            return items;
        }
        items.add(ruleType);

        String anchor = modelObject.getAnchor();
        if ((anchor == null) || (anchor.isEmpty())) {
            return items;
        }
        items.add(anchor);

        //determine if status will change the way rule is displayed
        String prefix = "";
        if (modelObject.getStatus() != null) {
	        if (modelObject.getStatus().equals(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString())) {
	        	prefix = "[D] ";
	        } else if (modelObject.getStatus().equals(BusinessRuleStatus.RETIRED.toString())) {
	        	prefix = "[R] ";
	        }
        }
        
        String ruleName = modelObject.getBusinessRuleDisplayName();
        if ((ruleName == null) || (ruleName.isEmpty())) {
            return items;
        }
        items.add(prefix + ruleName);

        return items;
    }
}
