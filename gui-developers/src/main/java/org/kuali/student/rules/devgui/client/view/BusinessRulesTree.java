/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.rules.devgui.client.model.BusinessRule;

/**
 * @author zzraly
 */
public class BusinessRulesTree extends SimpleTree<BusinessRule> {

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
    public List<String> getPath(BusinessRule modelObject) {
        final List<String> items = new ArrayList<String>();

        // items.add(modelObject.getAgendaType());
        // items.add(modelObject.getBusinessRuleTypeKey());
        // items.add(modelObject.getAnchorTypeKey());
        // items.add(modelObject.getAnchor());

        items.add(modelObject.getAgendaType());
        items.add(modelObject.getAnchor());
        items.add(modelObject.getBusinessRuleTypeKey());
        items.add(modelObject.getName());

        return items;
    }

}
