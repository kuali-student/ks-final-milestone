/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;

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

        String agendaType = modelObject.getAgendaType();
        if ((agendaType == null) || (agendaType.isEmpty())) {
            return items;
        }
        items.add(agendaType);

        String ruleType = modelObject.getBusinessRuleType();
        if ((ruleType == null) || (ruleType.isEmpty())) {
            return items;
        }
        items.add(ruleType);

        String anchor = modelObject.getAnchor();
        if ((anchor == null) || (anchor.isEmpty())) {
            return items;
        }
        items.add(anchor);

        String ruleName = modelObject.getBusinessRuleName();
        if ((ruleName == null) || (ruleName.isEmpty())) {
            return items;
        }
        items.add(ruleName);

        return items;
    }
}
