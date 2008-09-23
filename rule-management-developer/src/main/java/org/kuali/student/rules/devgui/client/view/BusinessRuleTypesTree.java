/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;

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

        items.add(modelObject.getAgendaType());
        items.add(modelObject.getBusinessRuleTypeKey());
        return items;
    }

}
