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

package org.kuali.student.core.statement.ui.client.widgets.rules;

import org.kuali.student.core.statement.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.ui.client.widgets.table.TreeTable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

public class RuleTable extends Composite {

    private TreeTable treeTable;
    private SimplePanel simplePanel;   //TODO do we need simple panel?
    private boolean showControls;
    
    public RuleTable(Boolean showCheckbox) {
        showControls = showCheckbox;
        treeTable = new TreeTable();
        treeTable.setStyleName("KS-Rules-Table");
        simplePanel = new SimplePanel();
        simplePanel.add(treeTable);
        super.initWidget(simplePanel);
    }

    private void initTable(Node root) {
        treeTable.clear();
        int column = root.getMaxLevelDistance() + 1; // 1 is for root
        int row = root.getAllLeafCount();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Node emptyNode = new Node();
                emptyNode.setUserObject("Empty:" + i + ":" + j);
                ((FlexTable)treeTable).setWidget(i, j, new RuleNodeWidget(emptyNode));
            }
        }
    }
    
    public void buildTable(Node root) {
        initTable(root);
        buildTable(root, 0);
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = treeTable.getCellCount(i) - 1; j >= 0; j--) {
                RuleNodeWidget w = (RuleNodeWidget) ((FlexTable)treeTable).getWidget(i, j);
                if (w.getNode().isLeaf() == false) {
                    treeTable.mergeCellAcrossRow(i, j, w.getNode().getAllLeafCount() - 1);
                }
            }
        }
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = treeTable.getCellCount(i) - 1; j >= 0; j--) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                if (w.getNode().getUserObject().toString().startsWith("Empty")) {
                    // mergeCellAcrossRow(i, j, w.getNode().getAllLeafCount()-1);
                    RuleNodeWidget n = ((RuleNodeWidget) ((FlexTable)treeTable).getWidget(i, j - 1));
                    treeTable.mergeCellAcrossColumn(i, j - 1);
                    treeTable.setWidget(i, j - 1, n);
                }
            }
        }
    }
    
    public CellFormatter getCellFormatter() {
        return treeTable.getCellFormatter();
    }    

    public HandlerRegistration addTextClickHandler(ClickHandler textClickHandler) {
        return addDomHandler(textClickHandler, ClickEvent.getType());
    }    
       
    public void addEditClauseHandler(ClickHandler editClauseHandler) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = 0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                w.addEditClauseHandler(editClauseHandler);
            }
        }
    }

    /** Build table for node passed in at columnIndex
     * 
     * @param node target node
     * @Param columnIndex column index
     * */
    private void buildTable(Node node, int columnIndex) {
        int rowIndex = getRowIndexAmongSibings(node);
        RuleNodeWidget nodeWidget = new RuleNodeWidget(node, showControls);
        nodeWidget.drawNode(node, this, rowIndex, columnIndex);
        treeTable.setWidget(rowIndex, columnIndex, nodeWidget);
        nodeWidget.setWidth("100%");

        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                int childRowIndex = getRowIndexAmongSibings(child);
                RuleNodeWidget childNodeWidget = (RuleNodeWidget) ((FlexTable)treeTable).getWidget(childRowIndex, columnIndex + 1);
                childNodeWidget.setShowCheckbox(this.showControls);
                childNodeWidget.drawNode(child, this, childRowIndex, columnIndex + 1);
            } else {
                buildTable(child, columnIndex + 1);
            }
        }

    }

    public void setEnabled(boolean enabled) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = treeTable.getCellCount(i) - 1; j >= 0; j--) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                w.setEnabled(enabled);
            }
        }
    }
    
    public RuleNodeWidget getRuleNodeWidget(Node node) {
        RuleNodeWidget result = null;
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j =0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) getWidget(i, j);
                if (w.getNode() == node) {
                    return w;
                }
            }
        }
        return result;
    }
    
    /**
     * Get the starting row for node passed in
     * 
     * @param node target node
     * */
    public int getParentRowIndex(Node node) {
        Node parent = node.getParent();
        if (parent == null) {
            return 0;
        }
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = 0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) getWidget(i, j);
                if (w.getNode() == node.getParent()) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    private int getRowIndexAmongSibings(Node node) {
        Node parent = node.getParent();
        if (parent == null) {
            return 0;
        }

        int count = getParentRowIndex(node);
        for (int i = 0; i < parent.getChildCount(); i++) {
            Node child = parent.getChildAt(i);
            if (child == node) {
                return count;
            }
            if (child.isLeaf()) {
                count++;
            } else {
                count += child.getAllLeafCount();
            }
        }
        return count;
    }
    
    public HandlerRegistration addClickHandler(ClickHandler clickHandler) {
        return treeTable.addClickHandler(clickHandler);
    }
    
    public Cell getCellForEvent(ClickEvent clickEvent) {
        return treeTable.getCellForEvent(clickEvent);
    }
    
    public Widget getWidget(int row, int column) {
        return treeTable.getWidget(row, column);
    }
    
    public void clear() {
        treeTable = null;
        simplePanel.clear();
        treeTable = new TreeTable();
        treeTable.setStyleName("KS-Rules-Table");
        simplePanel.add(treeTable);
    }

    public boolean isShowControls() {
        return showControls;
    }

    public void setShowControls(boolean showControls) {
        this.showControls = showControls;
    }
}
