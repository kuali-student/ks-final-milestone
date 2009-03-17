package org.kuali.student.common.ui.client.widgets.table;


import com.google.gwt.user.client.ui.FlexTable;

public class TreeTable extends FlexTable {
    public TreeTable() {
        super();
        setBorderWidth(1);
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

    private void buildTable(Node node, int columnIndex) {
        int rowIndex = getRowIndexAmongSibings(node);

        // mergeCellAcrossRow(rowIndex, columnIndex, node.getAllLeafCount()-1);
        setWidget(rowIndex, columnIndex, new NodeWidget(node));

        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                int childRowIndex = getRowIndexAmongSibings(child);
                // System.out.println(child+":"+childRowIndex+":"+(columnIndex+1));
                ((NodeWidget) super.getWidget(childRowIndex, columnIndex + 1)).setNode(child);
                // setWidget(childRowIndex, columnIndex+1,new NodeWidget(child));
            } else {
                buildTable(child, columnIndex + 1);
            }
        }

    }

    public void mergeCellAcrossRow(int row, int column, int count) {
        for (int i = 1; i <= count; i++) {
            removeCell(row + i, column);
        }
        getFlexCellFormatter().setRowSpan(row, column, count + 1);
    }

    public void mergeCellAcrossColumn(int row, int column) {
        removeCell(row, column);
        getFlexCellFormatter().setColSpan(row, column, 10);
    }
}
