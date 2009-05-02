package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.ExpressionParser;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class RuleTable extends Composite {

    private TreeTable treeTable;
    
    public RuleTable() {
        treeTable = new TreeTable();
        super.initWidget(treeTable);
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
        root = ExpressionParser.mergeBinaryTree(root);
        //root = ExpressionParser.reStructure(root);
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
    
    public void addCheckBoxHandler(ClickHandler checkboxHandler) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = 0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                w.addCheckBoxClickHandler(checkboxHandler);
            }
        }
    }
    
    public void removeCheckBoxHandler(ClickHandler checkboxHandler) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = 0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                if (w != null) {
                    w.clearCheckBoxClickHandler();
                }
            }
        }
    }

    public void addToggleHandler(ClickHandler toggleHandler) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            for (int j = 0; j < treeTable.getCellCount(i); j++) {
                RuleNodeWidget w = (RuleNodeWidget) treeTable.getWidget(i, j);
                w.addToggleHandler(toggleHandler);
            }
        }
    }
    
    static int test = 0;
    
    public void addEditClauseHandler(ClickHandler editClauseHandler) {
        test++;
        System.out.println("addEditClauseHandler called " + test);
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
        RuleNodeWidget nodeWidget = new RuleNodeWidget(node);
        treeTable.setWidget(rowIndex, columnIndex, nodeWidget);

        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                int childRowIndex = getRowIndexAmongSibings(child);
                RuleNodeWidget childNodeWidget = 
                    (RuleNodeWidget) ((FlexTable)treeTable).getWidget(childRowIndex, columnIndex + 1);
                childNodeWidget.setNode(child);
            } else {
                buildTable(child, columnIndex + 1);
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
        treeTable.clear();
    }

    
}
