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

package org.kuali.student.core.statement.ui.client.widgets.table;

import org.kuali.student.core.statement.ui.client.widgets.rules.Token;

import com.google.gwt.user.client.ui.FlexTable;

/**
 * Layout the token tree in GWT FlexTable.
 * 
 * */
public class TreeTable extends FlexTable {

    public TreeTable() {
        super();
        setBorderWidth(1);
    }

    public NodeWidget getRootNodeWidget(){
        return (NodeWidget)super.getWidget(0, 0);
    }

    private void initTable(Node root) {
        super.clear();
        int column = root.getMaxLevelDistance() + 1; // 1 is for root
        int row = root.getAllLeafCount();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Node emptyNode = new Node();
                emptyNode.setUserObject("Empty:" + i + ":" + j);
                setWidget(i, j, new NodeWidget(emptyNode));
            }
        }
    }

    public void buildTable(Node root) {
        root = ExpressionParser.mergeBinaryTree(root);
        //root = ExpressionParser.reStructure(root);
        initTable(root);
        buildTable(root, 0);
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = getCellCount(i) - 1; j >= 0; j--) {
                NodeWidget w = (NodeWidget) getWidget(i, j);
                if (w.getNode().isLeaf() == false) {
                    mergeCellAcrossRow(i, j, w.getNode().getAllLeafCount() - 1);
                }
            }
        }
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = getCellCount(i) - 1; j >= 0; j--) {
                NodeWidget w = (NodeWidget) getWidget(i, j);
                if (w.getNode().getUserObject().toString().startsWith("Empty")) {
                    // mergeCellAcrossRow(i, j, w.getNode().getAllLeafCount()-1);
                    NodeWidget n = ((NodeWidget) super.getWidget(i, j - 1));
                    mergeCellAcrossColumn(i, j - 1);
                    setWidget(i, j - 1, n);
                }
            }
        }
    }
    /** Get the row index for siblings*/
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
    
    public NodeWidget getNodeWidget(Node node) {
        NodeWidget result = null;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j =0; j < getCellCount(i); j++) {
                NodeWidget w = (NodeWidget) getWidget(i, j);
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
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCellCount(i); j++) {
                NodeWidget w = (NodeWidget) getWidget(i, j);
                if (w.getNode() == node.getParent()) {
                    return i;
                }
            }
        }
        return 0;
    }
    /** Build table for node passed in at columnIndex
     * 
     * @param node target node
     * @Param columnIndex column index
     * */
    private void buildTable(Node<Token> node, int columnIndex) {
        int rowIndex = getRowIndexAmongSibings(node);
        setWidget(rowIndex, columnIndex, new NodeWidget(node));

        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                int childRowIndex = getRowIndexAmongSibings(child);
                ((NodeWidget) super.getWidget(childRowIndex, columnIndex + 1)).setNode(child);
            } else {
                buildTable(child, columnIndex + 1);
            }
        }

    }
    /** Merge rows.
     * 
     * @param row row index
     * @param column column index
     * @param count row count
     * 
     * */
    public void mergeCellAcrossRow(int row, int column, int count) {
        for (int i = 1; i <= count; i++) {
            removeCell(row + i, column);
        }
        getFlexCellFormatter().setRowSpan(row, column, count + 1);
    }
    /**
     * Merge columns
     * 
     * @param row row index
     * @param column column index
     * */
    
    public void mergeCellAcrossColumn(int row, int column) {
        removeCell(row, column);
        getFlexCellFormatter().setColSpan(row, column, 10);
    }
}
