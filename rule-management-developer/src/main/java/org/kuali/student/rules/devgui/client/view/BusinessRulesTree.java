/**
 * 
 */
package org.kuali.student.rules.devgui.client.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.commons.ui.widgets.trees.SimpleTree;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author zzraly
 */
public class BusinessRulesTree extends SimpleTree<RulesHierarchyInfo> implements ModelTableSelectionListener<RulesHierarchyInfo> {
    
    boolean loaded = false;
    private Set <BusinessRuleTreeListener> listeners = new HashSet<BusinessRuleTreeListener>(); 

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
        super.addSelectionListener(this);
    }
    
    public void addSelectionListener(ModelTableSelectionListener<RulesHierarchyInfo> listener) {
        throw new java.lang.IllegalArgumentException(
                "addBusinessRuleTreeListener(BusinessRuleTreeListener listener) method should be used instead");
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

    public void onSelect(RulesHierarchyInfo modelObject) {
        fireSelection(getSelection());
    }
    
    protected void fireSelection(RulesHierarchyInfo modelObject) {
        for (BusinessRuleTreeListener listener : listeners) {
            listener.onSelect(modelObject);
        }
    }

    protected void fireMouseOver(RulesHierarchyInfo modelObject,
            int x, int y) {
        for (BusinessRuleTreeListener listener : listeners) {
            listener.onTreeItemMouseOver(modelObject, x, y);
        }
    }
    
    protected void fireMouseOut() {
        for (BusinessRuleTreeListener listener : listeners) {
            listener.onMouseOut();
        }
    }
        
    
    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        int eventType = DOM.eventGetType(event);
        switch(eventType) {
            case Event.ONMOUSEOVER: {
                com.google.gwt.user.client.Element element = 
                    DOM.eventGetTarget(event);
                List<RulesHierarchyInfo> items = getItems();
                RulesHierarchyInfo mouseOverredItem = null;
                int xPosition = 0;
                int yPosition = 0;
                for (RulesHierarchyInfo item : items) {
                    TreeItem treeItem = getTreeItem(item);
                    if (treeItem.getElement().isOrHasChild(element)) {
                        mouseOverredItem = item;
                        xPosition = element.getAbsoluteLeft();
                        yPosition = element.getAbsoluteTop();
                    }
                }
                if (mouseOverredItem != null) {
                    fireMouseOver(mouseOverredItem, 
                            xPosition, yPosition);
                }
                break;
            }
            case Event.ONMOUSEOUT: {
                fireMouseOut();
            }
        }

    }

    public void addBusinessRuleTreeListener(BusinessRuleTreeListener listener) {
        listeners.add(listener);
    }
    
}
