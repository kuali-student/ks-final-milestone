package org.kuali.student.rules.devgui.client.view;

import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;

import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;

public interface BusinessRuleTreeListener extends ModelTableSelectionListener<RulesHierarchyInfo> {

    void onTreeItemMouseOver(RulesHierarchyInfo item, int x, int y);
    void onMouseOut();
    
}
