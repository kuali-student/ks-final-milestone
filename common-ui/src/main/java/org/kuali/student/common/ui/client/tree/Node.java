package org.kuali.student.common.ui.client.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> childrenList = new ArrayList<Node>();
    Node parent;
    Object userObject;

    public Node(Object obj) {
        userObject = obj;
    }

    public void setParent(Node p) {
        parent = p;
    }

    public Node getParent() {
        return parent;
    }

    public void addNode(Node node) {
        childrenList.add(node);
    }

    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    public int getChildCount() {
        return childrenList.size();
    }

    public Node getChildAt(int index) {
        return childrenList.get(index);
    }

    public void remove(int childIndex) {
        Node child = getChildAt(childIndex);
        childrenList.remove(childIndex);
        child.setParent(null);
    }

    public boolean isNodeChild(Node aNode) {
        boolean retval;
        if (aNode == null) {
            retval = false;
        } else {
            if (getChildCount() == 0) {
                retval = false;
            } else {
                retval = (aNode.getParent() == this);
            }
        }
        return retval;
    }

    public int getIndex(Node aChild) {
        if (!isNodeChild(aChild)) {
            return -1;
        }
        return childrenList.indexOf(aChild); // linear search
    }

    public boolean isNodeSibling(Node anotherNode) {
        boolean retval;

        if (anotherNode == null) {
            retval = false;
        } else if (anotherNode == this) {
            retval = true;
        } else {
            Node myParent = getParent();
            retval = (myParent != null && myParent == anotherNode.getParent());

            if (retval && !((Node) getParent()).isNodeChild(anotherNode)) {
                throw new Error("sibling has different parent");
            }
        }
        return retval;
    }
}