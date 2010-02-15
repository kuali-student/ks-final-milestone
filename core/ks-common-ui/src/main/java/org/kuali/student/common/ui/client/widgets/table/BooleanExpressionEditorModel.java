/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class BooleanExpressionEditorModel implements NodeEditor {
    private List<Node<Token>> orignalOrder = new ArrayList<Node<Token>>();
    private List<Widget> widgetList = new ArrayList<Widget>();
    private List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
    protected Stack<List<Node<Token>>> undoStack = new Stack<List<Node<Token>>>();
    protected Stack<List<Node<Token>>> redoStack = new Stack<List<Node<Token>>>();

    //private HandlerManager handlers = new HandlerManager(this);
    private List<ModelChangedListener> eventsListenerList = new ArrayList<ModelChangedListener>();
    public void addModelChangedEvent(ModelChangedListener e){
        eventsListenerList.add(e);
    }
    private void fireEvent(){
        for(ModelChangedListener lis: eventsListenerList){
            lis.modelChanged(this);
        }
    }
    public boolean canRedo() {
        return redoStack.size() > 0;
    }

    public boolean canUndo() {
        return undoStack.size() >= 2;
    }

    public void setNodeFromExpressionEditor(Node<Token> treeRoot) {
        nodeList.clear();
        for (Node node : orignalOrder) {
            node.setParent(null);
            nodeList.add(node);
        }
        if(treeRoot != null){
            List<Node<Token>> all = treeRoot.getAllChildren();
            for (Node<Token> node : all) {
                if(node.isLeaf() && getIndexInOrignalList(node) == 0){
                    orignalOrder.add(node);
                }
            }
            List<Node<Token>> nodeToBeRemoved = treeRoot.getAllChildren();

            for (Node<Token> node : all) {
                for (Node<Token> nodeInList : nodeList) {
                    if (node.getUserObject().equals(nodeInList.getUserObject())) {
                        nodeToBeRemoved.add(nodeInList);
                    }
                }
            }
            nodeList.removeAll(nodeToBeRemoved);
            nodeList.add(treeRoot);
        }
        dump();
        reBuildWidgetList();
    }
/*
    public void setNodeList(List<Node<Token>> list) {
        for (Node n : list) {
            orignalOrder.add(n);
        }
        for (Node n : list) {
            nodeList.add(n);
        }
        dump();
        reBuildWidgetList();
    }
*/
    private void dump() {
        //undoStack.peek().
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

    private List<Node> getUngroupedNodeList() {
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

    private List<Node<Token>> getAllNodeList() {
        List<Node<Token>> list = new ArrayList<Node<Token>>();
        for (Node n : nodeList) {
            list.add(n);
            if (n.isLeaf() == false) {
                list.addAll(n.getAllChildren());
            }
        }
        return list;
    }

    private List<Node> getGroupedConditionList() {
        List<Node> list = new ArrayList<Node>();
        List<Node<Token>> all = getAllNodeList();
        for (Node n : all) {
            if (n.isLeaf() && n.parent != null) {
                list.add(n);
            }
        }
        return list;
    }

    private List<Node> getTreeTableRootNodeList() {
        List<Node> list = new ArrayList<Node>();
        for (Widget w : widgetList) {
            if (w instanceof TreeTable) {
                TreeTable t = (TreeTable) w;
                list.add(t.getRootNodeWidget().getNode());
            }
        }
        return list;
    }

    private List<Node> getSelectedNodeList() {
        List<NodeWidget> list = getAllNodeWidget();
        List<Node> selectedList = new ArrayList<Node>();
        for (NodeWidget w : list) {
            if (w.isSelected() == true) {
                selectedList.add(w.getNode());
            }
        }
        return selectedList;
    }

    private List<Node> getGroupedRelationList() {
        List<Node> list = new ArrayList<Node>();
        List<Node<Token>> all = getAllNodeList();
        for (Node n : all) {
            if (n.isLeaf() == false && n.getParent() != null) {
                list.add(n);
            }
        }
        return list;
    }

    private List<Node> getSelectedGroupedRelationList() {
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
    @Override
    public void doAnd() {
        Node<Token> andNode = new Node<Token>();
        andNode.setUserObject(Token.createAndToken());
        addNode(andNode);
    }
    @Override
    public void doOr() {

        Node<Token> orNode = new Node<Token>();
        orNode.setUserObject(Token.createOrToken());
        addNode(orNode);
    }
    @Override
    public void doRemoveFromGroup() {
        List<Node> selectedList = getSelectedNodeList();
        for (Node node : selectedList) {
            if (node.isLeaf() && node.getParent() != null) {
                node.getParent().remove(node);
                node.setParent(null);
                this.nodeList.add(node);
            }
        }
        for (Node node : selectedList) {
            if (node.isLeaf()) {
                continue;
            }
            Node parent = node.getParent();
            if (parent == null) {
                nodeList.remove(node);
                List<Node> childList = node.children();
                for (Node child : childList) {
                    nodeList.add(child);
                    child.setParent(null);
                }
            } else {
                // node.removeFromParent();
                List<Node> childList = node.children();
                for (Node child : childList) {
                    parent.addNode(child);
                }
                parent.remove(node);

            }
        }

        while (isTreeValid() == false) {
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
                    if (n.getParent() == null) {
                        nodeList.remove(n);
                    } else {
                        n.getParent().remove(n);
                    }
                    n.setParent(null);
                }
            }
        }
        dump();
        reBuildWidgetList();
    }

    private boolean isTreeValid() {
        List<Node<Token>> all = this.getAllNodeList();
        for (Node<Token> n : all) {
            if (n.isLeaf() && (n.getUserObject().type == Token.And || n.getUserObject().type == Token.Or)) {
                return false;
            }
            if (n.getChildCount() == 1) {
                return false;

            }
        }
        return true;
    }
    @Override
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
// sort value not index
    private int getIndexInOrignalList(Node<Token> n){
        int i=0;
        for(Node<Token> node: orignalOrder){
            i++;
            if(node.getUserObject().equals(n.getUserObject())){
                return i;
            }
        }
        return 0;
    }
    private void sortNodeList() {
        if (nodeList.size() == 2) {
            if (getIndexInOrignalList(nodeList.get(0)) > getIndexInOrignalList(nodeList.get(1))) {
            //if (orignalOrder.indexOf(nodeList.get(0)) > orignalOrder.indexOf(nodeList.get(1))) {
                Node<Token> buffer = nodeList.get(0);
                nodeList.remove(0);
                nodeList.add(buffer);
            }
        }
        for (int out = nodeList.size() - 1; out > 1; out--) {
            for (int in = 0; in < out; in++) {
                //if (orignalOrder.indexOf(nodeList.get(in)) > orignalOrder.indexOf(nodeList.get(in + 1))) {
                if (getIndexInOrignalList(nodeList.get(in)) > getIndexInOrignalList(nodeList.get(in + 1))) {
                    Node<Token> buffer = nodeList.get(in);
                    nodeList.remove(in);
                    nodeList.add(in + 1, buffer);
                }
            }
        }
    }

    private void reBuildWidgetList() {
        widgetList.clear();
        sortNodeList();
        // put the orphan first
        for (Node<Token> n : nodeList) {
            if (n.isLeaf()) {
                NodeWidget w = new NodeWidget(n);
                widgetList.add(w);
            } 
        }
        for (Node<Token> n : nodeList) {
            if (n.isLeaf() == false) {
                TreeTable table = new TreeTable();
                table.buildTable(n);
                widgetList.add(table);
            }
        }
        List<NodeWidget> list = getAllNodeWidget();
        for (NodeWidget w : list) {
            w.installCheckBox();
            
            w.addSelectionHandler(new ValueChangeHandler<Boolean>(){
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    fireEvent();
                }
            });
        }
        fireEvent();
    }

    public List<Widget> getWidgetList() {
        return widgetList;
    }

    public List<Node<Token>> getNodeList() {
        return nodeList;
    }

    public String getExpressionString() {
        StringBuilder sb = new StringBuilder();

        for (Node<Token> n : getNodeList()) {
            if (n.isLeaf()) {
              //  sb.append(n.getUserObject().toString() + " ");
            } else {
                sb.append(ExpressionParser.getExpressionString(n.clone()) + "  ");
            }
        }
        return sb.toString();
    }

    // More than one ungrouped node selected
    // or More than one not all grouped node under same parent selected

    public boolean isAndable() {
        return isAndable(null, getSelectedNodeList());
    }
    @Override
    public boolean isOrable() {
        return isOrable(null, getSelectedNodeList());
    }

    private boolean isAllInUngroupedNodes(List<Node> list) {
        List<Node> ungroupNodeList = this.getUngroupedNodeList();
        for (Node selected : list) {
            if (ungroupNodeList.indexOf(selected) == -1) {
                return false;
            }
        }
        return true;
    }

    private boolean isMoreThanNotAllOnGroupedNodeUnderSameParentSelected() {
        List<Node> selectedList = getSelectedNodeList();
        Node parent = selectedList.get(0).getParent();
        if (parent == null) {
            return false;
        }
        if (parent.children().size() == selectedList.size()) {
            return false;
        }
        for (Node n : selectedList) {
            if (parent != n.getParent()) {
                return false;
            }
        }
        return true;
    }

    // end is addable
    @Override
    public boolean isTogglable() {
        List<Node> selectedList = this.getSelectedNodeList();

        if (selectedList.size() == 0) {
            return false;
        }
        for (Node n : selectedList) {
            if (((Token) n.getUserObject()).type == Token.Condition) {
                return false;
            }
        }

        return true;
    }
    @Override
    public void doToggle() {
        List<Node> selectedList = this.getSelectedNodeList();
        for (Node n : selectedList) {
            n.setUserObject(((Token) n.getUserObject()).toggleAndOr());

        }
        dump();
        reBuildWidgetList();
    }

    // one grouped relation and one or more ungrouped node selected
    @Override
    public boolean isAddable() {
        List<Node> selectedGroupedRelationList = getSelectedGroupedRelationList();
        if (selectedGroupedRelationList.size() != 1) {
            return false;
        }
        return isAddable(selectedGroupedRelationList.get(0), this.getSelectedNodeList());
    }

    public boolean isRemovable() {
        return isRemovable(getSelectedNodeList());
    }

    // end addable

    @Override
    public boolean isAndable(Node target, List<Node> nList) {
        if (nList.size() < 2) {
            return false;
        }
        if (isAllInUngroupedNodes(nList)) {
            return true;
        }
        if (isMoreThanNotAllOnGroupedNodeUnderSameParentSelected()) {
            List<Node> selectedList = getSelectedNodeList();
            Node parent = selectedList.get(0).getParent();
            Token token = (Token)parent.getUserObject();
            if(token.type == Token.Or){
                return true;
            }else{
               return false;
            }
        }
        return false;
    }
    @Override
    public boolean isOrable(Node target, List<Node> nList) {
        if (nList.size() < 2) {
            return false;
        }
        if (isAllInUngroupedNodes(nList)) {
            return true;
        }
        if (isMoreThanNotAllOnGroupedNodeUnderSameParentSelected()) {
            List<Node> selectedList = getSelectedNodeList();
            Node parent = selectedList.get(0).getParent();
            Token token = (Token)parent.getUserObject();
            if(token.type == Token.And){
                return true;
            }else{
               return false;
            }
        }
        return false;
    }

    @Override
    public boolean isAddable(Node to, List<Node> nList) {
        if (nList.size() < 2) {
            return false;
        }
        List<Node> childrenOfSelectedGroupedRelation = to.getAllChildren();
        for (Node selected : nList) {
            if (childrenOfSelectedGroupedRelation.indexOf(selected) != -1) {
                return false;
            }
        }
        for (Node selected : nList) {
            List<Node> children = selected.getAllChildren();
            if (children.indexOf(to) != -1) {
                return false;
            }
        }

        nList.remove(to);
        if (isAllInUngroupedNodes(nList)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRemovable(List<Node> nList) {
        if (nList.size() == 0) {
            return false;
        }

        for (Node selected : nList) {
            if (selected.isLeaf() && selected.getParent() == null) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void doRedo() {
        if (redoStack.size() == 0) {
            return;
        }
        List<Node<Token>> historyStep = this.redoStack.pop();
        undoStack.push(historyStep);
        
        List<Node<Token>> clonedList = new ArrayList<Node<Token>>();
        for (Node<Token> n : historyStep) {
            clonedList.add(n.clone());
        }
        nodeList = clonedList;
        reBuildWidgetList();

    }

    @Override
    public void doUndo() {
        if (undoStack.size() == 1) {
            return;
        }
        List<Node<Token>> historyStep = this.undoStack.pop();
        redoStack.push(historyStep);
        historyStep = this.undoStack.peek();

        List<Node<Token>> clonedList = new ArrayList<Node<Token>>();
        for (Node<Token> n : historyStep) {
            clonedList.add(n.clone());
        }
        nodeList = clonedList;
        System.out.println("undo:"+undoStack+"\n");
        reBuildWidgetList();
    }
}
