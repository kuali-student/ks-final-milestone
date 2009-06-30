/**
 * 
 */
package org.kuali.student.brms.devgui.client.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.brms.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TreeItem;

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
        String agendaType = modelObject.getGroupAgendaType();
//        if (agendaType.startsWith("KUALI_")) {
//        	agendaType = agendaType.substring(6);
//        }
        
        if ((agendaType == null) || (agendaType.isEmpty())) {
            return items;
        }
        items.add(agendaType);

        String ruleType = modelObject.getGroupBusinessRuleType();
//        if (ruleType.startsWith("KUALI_")) {
//        	ruleType = ruleType.substring(6);
//        }        
        
        if ((ruleType == null) || (ruleType.isEmpty())) {
            return items;
        }
        items.add(ruleType);

        String anchor = modelObject.getGroupAnchor();
        if ((anchor == null) || (anchor.isEmpty())) {
            return items;
        }
        items.add(anchor);

        String ruleName = modelObject.getGroupBusinessRuleDisplayName();
        if ((ruleName == null) || (ruleName.isEmpty())) {
            return items;
        }
        items.add(ruleName);

        return items;
    }

    /**
     * Reason to override the SimpleTree's update method is that
     * the update method of the superclass does not select the
     * model item once it is updated.  Using the superclass's update
     * will result in the selected node to appear unselected.
     */
    @Override
    public void update(RulesHierarchyInfo modelObject) {
        super.update(modelObject);
        super.select(modelObject);
    }

}
