package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.google.gwt.user.client.ui.Widget;

public class RuleEditTableModelData {
    private List<Node<Token>> orignalOrder = new ArrayList<Node<Token>>();
    private List<Widget> widgetList = new ArrayList<Widget>();
    private List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
    private Stack<List<Node<Token>>> undoStack = new Stack<List<Node<Token>>>();
    private Stack<List<Node<Token>>> redoStack = new Stack<List<Node<Token>>>();

    public void setNodeList(List<Node<Token>> list) {
        orignalOrder = list;
        nodeList = list;
        dump();
        reBuildWidgetList();
    }

    public boolean canRedo() {
        return redoStack.size() > 0;
    }

    public boolean canUndo() {
        return undoStack.size() >= 2;
    }

    private void dump() {
        List<Node<Token>> clonedWidgetList = new ArrayList<Node<Token>>();
        redoStack.clear();

        for (Node<Token> n : nodeList) {
            clonedWidgetList.add(n.clone());
        }
        undoStack.add(clonedWidgetList);
    }

    public List<NodeWidget> getAllNodeWidget() {
        List<NodeWidget> list = new ArrayList<NodeWidget>();
        for (Widget w : widgetList) {
            if (w instanceof NodeWidget) {
                list.add((NodeWidget) w);
            } else if (w instanceof TreeTable) {
                TreeTable t = (TreeTable) w;
                for (int x = 0; x < t.getRowCount(); x++) {
                    for (int y = 0; y < t.getCellCount(x); y++) {
                        list.add((NodeWidget) t.getWidget(x, y));
                    }
                }
            }
        }
        return list;
    }

    public List<Node> getUngroupedNodeList() {
        List<Node> list = new ArrayList<Node>();
        for (Widget w : widgetList) {
            if (w instanceof NodeWidget) {
                list.add(((NodeWidget) w).getNode());
            } else if (w instanceof TreeTable) {
                list.add(((TreeTable) w).getRootNodeWidget().getNode());
            }
        }
        return list;
    }

    public List<Node<Token>> getAllNodeList() {
        List<Node<Token>> list = new ArrayList<Node<Token>>();
        for (Node n : nodeList) {
            list.add(n);
            if (n.isLeaf() == false) {
                list.addAll(n.getAllChildren());
            }
        }
        return list;
    }

    public List<Node> getGroupedConditionList() {
        List<Node> list = new ArrayList<Node>();
        List<Node<Token>> all = getAllNodeList();
        for (Node n : all) {
            if (n.isLeaf() && n.parent != null) {
                list.add(n);
            }
        }
        return list;
    }

    public List<Node> getTreeTableRootNodeList() {
        List<Node> list = new ArrayList<Node>();
        for (Widget w : widgetList) {
            if (w instanceof TreeTable) {
                TreeTable t = (TreeTable) w;
                list.add(t.getRootNodeWidget().getNode());
            }
        }
        return list;
    }

    public List<TreeTable> getTreeTableList() {
        List<TreeTable> list = new ArrayList<TreeTable>();
        for (Widget w : widgetList) {
            if (w instanceof TreeTable) {
                TreeTable t = (TreeTable) w;
                list.add(t);
            }
        }
        return list;
    }

    public List<Node> getSelectedNodeList() {
        List<NodeWidget> list = getAllNodeWidget();
        List<Node> selectedList = new ArrayList<Node>();
        for (NodeWidget w : list) {
            if (w.isSelected() == true) {
                selectedList.add(w.getNode());
            }
        }
        return selectedList;
    }

    public List<Node> getGroupedRelationList() {
        List<Node> list = new ArrayList<Node>();
        List<Node<Token>> all = getAllNodeList();
        for (Node n : all) {
            if (n.isLeaf() == false && n.getParent() != null) {
                list.add(n);
            }
        }
        return list;
    }

    public List<Node> getSelectedGroupedRelationList() {
        List<Node> selectedList = getSelectedNodeList();
        List<Node> selectedGroupedRelationList = new ArrayList<Node>();
        List<Node> groupedRelationList = getGroupedRelationList();
        for (Node selectedNode : selectedList) {
            if (groupedRelationList.indexOf(selectedNode) != -1) {
                selectedGroupedRelationList.add(selectedNode);
            }
        }
        return selectedGroupedRelationList;
    }

    private void addNode(Node addedNode) {
        List<Node> selectedList = getSelectedNodeList();

        if (selectedList.get(0).getParent() == null) {
            for (Node w : selectedList) {
                addedNode.addNode(w);
            }
            for (Node w : selectedList) {
                nodeList.remove(w);
            }
            nodeList.add(addedNode);
        } else {
            Node parent = selectedList.get(0).getParent();
            for (Node w : selectedList) {
                w.removeFromParent();
            }
            for (Node w : selectedList) {
                addedNode.addNode(w);
            }
            parent.addNode(addedNode);
        }

        dump();
        reBuildWidgetList();

    }

    public void doAnd() {
        Node<Token> andNode = new Node<Token>();
        andNode.setUserObject(Token.createAndToken());
        addNode(andNode);

    }

    public void doOr() {

        Node<Token> orNode = new Node<Token>();
        orNode.setUserObject(Token.createOrToken());
        addNode(orNode);
    }

    public void doUnDo() {
        if (undoStack.size() == 1) {
            return;
        }
        List<Node<Token>> historyStep = this.undoStack.pop();
        redoStack.push(historyStep);
        historyStep = this.undoStack.peek();

        List<Node<Token>> clonedList = new ArrayList<Node<Token>>();
        for (Node<Token> n : historyStep) {
            clonedList.add(n);
        }
        nodeList = clonedList;
        reBuildWidgetList();
    }

    public void doReDo() {
        if (redoStack.size() == 0) {
            return;
        }
        List<Node<Token>> historyStep = this.redoStack.pop();
        undoStack.push(historyStep);
        nodeList = historyStep;
        reBuildWidgetList();
    }

    public void doRemoveFromGroup() {
        List<Node> selectedList = getSelectedNodeList();
        for (Node node : selectedList) {
            if (node.isLeaf() && node.getParent() != null ) {
                node.getParent().remove(node);
                node.setParent(null);
                this.nodeList.add(node);
            }
        }
        for (Node node : selectedList) {
            if(node.isLeaf()){
                continue;
            }
            Node parent = node.getParent();
            if (parent == null) {
                nodeList.remove(node);
                List<Node> childList = node.children();
                for (Node child: childList) {
                    nodeList.add(child);
                    child.setParent(null);
                }
            }else{
                //node.removeFromParent();
                List<Node> childList = node.children();
                for (Node child: childList) {
                    parent.addNode(child);
                }
                parent.remove(node);
                
            }
        }

        while(isTreeValid() == false){
            List<Node<Token>> all = this.getAllNodeList();
            for (Node<Token> n : all) {
                if (n.isLeaf() == false && n.getChildCount() == 1) {
                    Node parent = n.getParent();
                    if (parent == null) {
                        nodeList.add(n.getChildAt(0));
                        n.getChildAt(0).setParent(null);
                        nodeList.remove(n);
                        
                    } else {
                        parent.addNode(n.getChildAt(0));
                        n.getParent().remove(n);
                        n.setParent(null);
                    }
                }
            }
          all = this.getAllNodeList();
            for (Node<Token> n : all) {
              if (n.isLeaf() && (n.getUserObject().type == Token.And || n.getUserObject().type == Token.Or)) {
                  if(n.getParent() == null){
                      nodeList.remove(n);
                  }else{
                      n.getParent().remove(n);    
                  }                  
                  n.setParent(null);
               }
            }
        }
        dump();
        reBuildWidgetList();
    }
    private boolean isTreeValid(){
        List<Node<Token>> all = this.getAllNodeList();
        for(Node<Token> n: all){
            if (n.isLeaf() && (n.getUserObject().type == Token.And || n.getUserObject().type == Token.Or)) {
                return false;
            }
            if(n.getChildCount() == 1){
                return false;
                
            }
        }
        return true;
    }
    public void doAddToGroup() {
        // should only have one
        Node groupedRelation = getSelectedGroupedRelationList().get(0);
        List<Node> selectedList = getSelectedNodeList();
        selectedList.remove(groupedRelation);
        for (Node n : selectedList) {
            groupedRelation.addNode(n);
            this.nodeList.remove(n);
        }
        reBuildWidgetList();
    }

    private void sortNodeList() {
        if (nodeList.size() == 2) {
            if (orignalOrder.indexOf(nodeList.get(0)) > orignalOrder.indexOf(nodeList.get(1))) {
                Node<Token> buffer = nodeList.get(0);
                nodeList.remove(0);
                nodeList.add(buffer);
            }
        }
        for (int out = nodeList.size() - 1; out > 1; out--) {
            for (int in = 0; in < out; in++) {
                if (orignalOrder.indexOf(nodeList.get(in)) > orignalOrder.indexOf(nodeList.get(in + 1))) {
                    Node<Token> buffer = nodeList.get(in);
                    nodeList.remove(in);
                    nodeList.add(in + 1, buffer);
                }
            }
        }
    }

    private void reBuildWidgetList() {
        widgetList.clear();
        for (Node<Token> n : nodeList) {
            if (n.isLeaf()) {
                NodeWidget w = new NodeWidget(n);
                widgetList.add(w);
            } else {
                TreeTable table = new TreeTable();
                table.buildTable(n);
                widgetList.add(table);
            }
        }
        sortNodeList();

        List<NodeWidget> list = getAllNodeWidget();
        for (NodeWidget w : list) {
            w.installCheckBox();
        }
        // state = IllegalSelection;

    }

    public List<Widget> getWidgetList() {
        return widgetList;
    }
    public List<Node<Token>> getNodeList(){
        return nodeList;
    }
}
