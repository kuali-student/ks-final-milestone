package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public class RuleEditTableModel extends RuleEditTableModelData {
    public RuleEditTableModel() {}
    public String getExpressionString(){
        StringBuilder sb = new StringBuilder();
        
        for(Node<Token>  n: getNodeList()){
            if(n.isLeaf()){
                sb.append(n.getUserObject().toString()+" ");
            }else{
                sb.append(ExpressionParser.getExpressionString(n.clone())+" ");
            }
        }
        return sb.toString();
    }
    // More than one ungrouped node selected
    // or More than one not all grouped node under same parent selected
    public boolean isAddOrOrable() {
        List<Node> selectedList = getSelectedNodeList();

        if (selectedList.size() < 2) {
            return false;
        }
        if (isAllInUngroupedNodes(selectedList)) {
            return true;
        }
        if (isMoreThanNotAllOnGroupedNodeUnderSameParentSelected()) {
            return true;
        }
        return false;
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
    // one grouped relation and one or more ungrouped node selected
    public boolean isAddable() {
        List<Node> selectedList = getSelectedNodeList();
        if (selectedList.size() < 2) {
            return false;
        }
        List<Node> selectedGroupedRelationList = getSelectedGroupedRelationList();
        if (selectedGroupedRelationList.size() != 1) {
            return false;
        }
        
        List<Node> childrenOfSelectedGroupedRelation = selectedGroupedRelationList.get(0).getAllChildren();
        for (Node selected : selectedList) {
            if (childrenOfSelectedGroupedRelation.indexOf(selected) != -1) {
                return false;
            }
        }
        for (Node selected : selectedList) {
            List<Node> children = selected.getAllChildren();

            if (children.indexOf(selectedGroupedRelationList.get(0)) != -1) {
                return false;
            }
        }
        
        selectedList.removeAll(selectedGroupedRelationList);
        if (isAllInUngroupedNodes(selectedList)) {
            return true;
        }
        return false;
    }

    // end addable
    public boolean isRemovable() {
        List<Node> selectedList = getSelectedNodeList();
        if (selectedList.size() == 0) {
            return false;
        }

        for (Node selected : selectedList) {
            if (selected.isLeaf() && selected.getParent() == null) {
                return false;
            }

        }
        return true;
    }
}
