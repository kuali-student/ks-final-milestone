package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public class RuleEditTableModel {
    public final static String IllegalSelection = "IllegalSelection";
    public final static String NoSelection = "NoSelection";

    public final static String SingleGroupedConditionWithMoreThanOneSiblingsSelected = "SingleGroupedConditionWithMoreThanOneSiblingsSelected";
    public final static String SingleUnGroupedRelationSelected = "SingleUnGroupedRelationSelected";
    public final static String SingleGroupedRelationWithMoreThanOneSiblingsSelected = "SingleGroupedRelationWithMoreThanOneSiblingsSelected";

    public final static String MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected = "MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected";
    public final static String OneOrMoreGroupedConditionOrRelationSelected = "OneOrMoreGroupedConditionOrRelationSelected";

    public final static String OneGroupedRelationAndOneOrMoreUnGroupedConditionSelected = "OneGroupedRelationAndOneOrMoreUnGroupedConditionSelected";

    private List<Node<Token>> orignalOrder = new ArrayList<Node<Token>>();
    private List<Widget> widgetList = new ArrayList<Widget>();
    private List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
    private Stack<List<Node<Token>>> undoStack = new Stack<List<Node<Token>>>();
    private Stack<List<Node<Token>>> redoStack = new Stack<List<Node<Token>>>();

    private String state = "";

    public RuleEditTableModel() {}

    public void setNodeList(List<Node<Token>> list) {
        orignalOrder = list;
        nodeList = list;
        dump() ;
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

    public boolean isMoreThanOnUnGroupedConditionAndUnGroupedRelationSelected(List<Node> selectedList) {
        List<Node> list = getUngroupedNodeList();

        for (Node w : selectedList) {
            if (list.indexOf(w) < 0) {
                return false;
            }
        }
        return true;
    }
    private List<Node> getAllNodeList(){
        List<Node> list = new ArrayList<Node>();
        for (Node n : nodeList) {
            if (n.isLeaf() == false) {
                list.addAll(n.getAllChildren());
            }
        }
        return list;
    }
    public List<Node> getGroupedConditionList() {
        List<Node> list = new ArrayList<Node>();
        List<Node> all = getAllNodeList();
        for (Node n : all) {
            if (n.isLeaf() && n.parent!= null ) {
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

    // disable the inner node widget
    public void refreshNodeSelectionState() {
        if (NoSelection.equals(state)) {

        }
    }

    private boolean isOneOrMoreGroupedConditionOrRelationSelected() {
        List<Node> selectedList = getSelectedNodeList();
        List<Node> groupedConditionList = getGroupedConditionList();
        List<Node> groupRelationList = getGroupedRelationList();
        List<Node> ungroupNodeList = this.getUngroupedNodeList();
        
        for (Node n : selectedList) {
            if (ungroupNodeList.indexOf(n) != -1) {
                return false;
            }
        }
        if (selectedList.size() == 1) {
            if (groupedConditionList.indexOf(selectedList.get(0)) != -1) {
                return true;
            }
            if(groupRelationList.indexOf(selectedList.get(0)) != -1){
                return true;
            }
        } else if (selectedList.size() == 2) {
            if (selectedList.get(0).getParent() == selectedList.get(1).getParent()
                    && selectedList.get(0).isLeaf() == true
                    && selectedList.get(1).isLeaf() == true) {
                return true;
            }
        } else {
            // more than two, have to from the save parent
            for (int i = 0; i < selectedList.size() - 1; i++) {
                if (selectedList.get(i).getParent() != selectedList.get(i + 1).getParent()) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    private List<Node> getGroupedRelationList() {
        List<Node> list = new ArrayList<Node>();
        List<Node> all = getAllNodeList();
        for(Node n: all){
            if(n.isLeaf() == false && n.getParent() != null){
                list.add(n);
            }
        }
        return list;
    }
    private List<Node> getSelectedGroupedRelationList(){
        List<Node> selectedList = getSelectedNodeList();
        List<Node> selectedGroupedRelationList = new ArrayList<Node>();
        List<Node> groupedRelationList = getGroupedRelationList();
        for(Node selectedNode :selectedList ){
            if(groupedRelationList.indexOf(selectedNode) != -1){
                selectedGroupedRelationList.add(selectedNode);
            }
        }
        return selectedGroupedRelationList;
    }
    private boolean isOneGroupedRelationAndOneOrMoreUnGroupedConditionSelected() {
        List<Node> selectedList = getSelectedNodeList();
        if (selectedList.size() == 1) {
            return false;
        }
        List<Node> selectedGroupedRelationList = getSelectedGroupedRelationList();
        if(selectedGroupedRelationList.size() != 1){
            return false;
        }
        selectedList.removeAll(selectedGroupedRelationList);
        Node selectedGroupedRelation  = selectedGroupedRelationList.get(0);
   
        for(Node ungroupedNode: selectedList){
            if(ungroupedNode.isLeaf() == false || ungroupedNode.parent != null){
                return false;
            }
        }
        for(Node ungroupedNode: selectedList){
           if(ungroupedNode.getAllChildren().indexOf(selectedGroupedRelation) != -1){
               return false;
           } 
        }
        return true;
    }

    public void stateChanged() {
        List<Node> selectedList = getSelectedNodeList();
        if (selectedList.size() == 0) {
            state = NoSelection;
            return;
        }
        state = IllegalSelection;

        List<Node> treeNodeList = getTreeTableRootNodeList();
        if (selectedList.size() == 1) {
            Node w = selectedList.get(0);
            if (treeNodeList.indexOf(w) >= 0) {
                state = SingleUnGroupedRelationSelected;
            }
        } else if (selectedList.size() >= 2) {
            if (isMoreThanOnUnGroupedConditionAndUnGroupedRelationSelected(selectedList)) {
                state = MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected;
            }
        }
        if (isOneOrMoreGroupedConditionOrRelationSelected()) {
            state = OneOrMoreGroupedConditionOrRelationSelected;
        }
        if (isOneGroupedRelationAndOneOrMoreUnGroupedConditionSelected()) {
            state = OneGroupedRelationAndOneOrMoreUnGroupedConditionSelected;
        }

    }

    public String getState() {
        return state;
    }

    public void doAnd() {
        List<Node> selectedList = getSelectedNodeList();

        if (MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected.equals(state)) {
            Node<Token> andNode = new Node<Token>();
            andNode.setUserObject(Token.createAndToken());
            for (Node w : selectedList) {
                andNode.addNode(w);
                nodeList.remove(w);
            }
            nodeList.add(andNode);
        }
        dump() ;
        reBuildWidgetList();

    }

    public void doOr() {
        List<Node> selectedList = getSelectedNodeList();
        if (MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected.equals(state)) {
            Node<Token> orNode = new Node<Token>();
            orNode.setUserObject(Token.createOrToken());
            for (Node w : selectedList) {
                orNode.addNode(w);
                nodeList.remove(w);
            }
            nodeList.add(orNode);
        }
        dump() ;
        reBuildWidgetList();
    }

    public void doUngroup() {
        List<Node> selectedList = getSelectedNodeList();
        if (SingleUnGroupedRelationSelected.endsWith(state)) {
            Node w = selectedList.get(0);
            int count = w.getChildCount();
            for (int i = 0; i < count; i++) {
                Node childNode = w.getChildAt(0);
                childNode.removeFromParent();
                nodeList.add(childNode);
            }
            nodeList.remove(w);
        }
        dump() ;
        reBuildWidgetList();
    }

    public void doUnDo() {
        if (undoStack.size() == 1) {
            return;
        }
        List<Node<Token>> historyStep = this.undoStack.pop();
        redoStack.push(historyStep);
        historyStep = this.undoStack.peek();
        nodeList = historyStep;
        reBuildWidgetList();
    }

    public void doReDo() {
        if (redoStack.size() == 0) {
            return;
        }
        List<Node<Token>> historyStep = this.redoStack.pop();
//        List<Node<Token>> clonedList = new ArrayList<Node<Token>>();
  //      for (Node<Token> n : historyStep) {
    //        clonedList.add(n.clone());
      //  }
        undoStack.push(historyStep);
        nodeList = historyStep;
        reBuildWidgetList();
    }
    public void doRemoveFromGroup() {
        List<Node> selectedList = getSelectedNodeList();

        for (Node node : selectedList) {
            Node parent = node.getParent();
            if (parent == null) { // for removing all children
                break;
            }
            node.removeFromParent();
            this.nodeList.add(node);
            if (parent.children().size() == 1) {
                Node child = (Node) parent.children().get(0);
                child.removeFromParent();
                this.nodeList.add(child);
                this.nodeList.remove(parent);
            }
        }
        dump() ;
        reBuildWidgetList();
    }
    public void doAddToGroup(){
        // should only have one
        Node groupedRelation = getSelectedGroupedRelationList().get(0);
        List<Node> selectedList = getSelectedNodeList();
        selectedList.remove(groupedRelation);
        for(Node n: selectedList){
            groupedRelation.addNode(n);
            this.nodeList.remove(n);
        }
        reBuildWidgetList();
    }
    private void nodeClicked() {
        System.out.println("clicked:" + state);

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
        state = IllegalSelection;

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
    public List<Widget> getWidgetList() {
        return widgetList;
    }

}
