package org.kuali.student.common.ui.client.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> childrenList = new ArrayList<Node>();
    Node parent;
    Object userObject;

    public Node() {}

    public Node(Object obj) {
        userObject = obj;
    }

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object obj) {
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
        node.setParent(this);
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

    /**
     * Returns the total number of leaves that are descendants of this node.
     * 
     * @see #isNodeAncestor
     * @return the number of leaves beneath this node
     */
    public int getAllLeafCount() {
        int count = 0;
        List<Node> nodeList = getAllChildren();
        for (Node node : nodeList) {
            if (node.isLeaf()) {
                count++;
            }
        }

        return count;
    }

    public List<Node> getAllChildren() {
        List<Node> nodeList = new ArrayList<Node>();
        for (Node child : childrenList) {
            nodeList.add(child);
            if (!child.isLeaf()) {
                nodeList.addAll(child.getAllChildren());
            }
        }
        return nodeList;
    }

    public List<Node> children() {
        if (childrenList == null) {
            return new ArrayList<Node>();
        } else {
            return childrenList;
        }
    }

    // root is the level one
    // results contain current node
    public List<List<Node>> toLevel() {
        List<List<Node>> levelList = new ArrayList<List<Node>>();

        List<Node> level = new ArrayList<Node>();
        level.add(this);
        levelList.add(level);


        int maxDistance = getMaxLevelDistance();

        List<Node> nodeList = getAllChildren();
        for (int levelIndex = 1; levelIndex <= maxDistance; levelIndex++) {
            level = new ArrayList<Node>();
            for (Node node : nodeList) {
                int d = getDistance(node);
                if (levelIndex == d) {
                    level.add(node);
                }
            }
            levelList.add(level);
        }

        return levelList;
    }

    public int getMaxLevelDistance() {
        List<Node> nodeList = getAllChildren();
        int maxDistance = 0;
        for (Node node : nodeList) {
            int d = getDistance(node);
            if (maxDistance < d) {
                maxDistance = d;
            }
        }
        return maxDistance;
    }

    // return the level distance to the current node
    public int getDistance(Node node) {
        Node myParent = node.getParent();
        int level = 1;
        while (myParent != this) {
            level++;
            myParent = myParent.getParent();
        }
        return level;
    }

    public static void main(String[] argv) {
        Node root = new Node();
        root.setUserObject("root");
        Node r = new Node();
        r.setUserObject("r");
        Node q = new Node();
        q.setUserObject("q");
        root.addNode(q);
        root.addNode(r);

        Node p = new Node();
        p.setUserObject("p");
        root.addNode(p);

        Node o = new Node();
        o.setUserObject("o");
        p.addNode(o);
        Node m = new Node();
        m.setUserObject("m");
        p.addNode(m);
        Node n = new Node();
        n.setUserObject("n");
        p.addNode(n);

        Node k = new Node();
        k.setUserObject("k");
        Node l = new Node();
        l.setUserObject("l");
        n.addNode(k);
        n.addNode(l);
        Node j = new Node();
        j.setUserObject("j");
        n.addNode(j);

        Node i = new Node();
        i.setUserObject("i");
        Node h = new Node();
        h.setUserObject("h");
        j.addNode(i);
        j.addNode(h);
        Node g = new Node();
        g.setUserObject("g");
        j.addNode(g);

        Node e = new Node();
        e.setUserObject("e");
        g.addNode(e);
        Node d = new Node();
        d.setUserObject("d");
        g.addNode(d);
        Node f = new Node();
        f.setUserObject("f");
        g.addNode(f);

        Node a = new Node();
        a.setUserObject("a");
        Node b = new Node();
        b.setUserObject("b");
        Node c = new Node();
        c.setUserObject("c");
        d.addNode(a);
        d.addNode(b);
        d.addNode(c);

        Node n1 = new Node();
        n1.setUserObject("n1");
        Node n2 = new Node();
        n2.setUserObject("n2");
        Node n3 = new Node();
        n3.setUserObject("n3");

        f.addNode(n1);
        f.addNode(n2);
        f.addNode(n3);

        System.out.println(root.getDistance(n1));
        System.out.println(root.getChildCount());
        System.out.println(root.getAllChildren().size());
        System.out.println(root.getAllLeafCount());

        List<List<Node>> levelList = root.toLevel();
        for (List<Node> level : levelList) {
            for (Node node : level) {
                System.out.print(node.getUserObject() + ",");
            }
            System.out.println();
        }
        System.out.println("---");
        for (int x=0;x<levelList.size();x++) {
            List<Node> aLevel = levelList.get(x);
            int nonLeafIndex = 0;
            for (int y = 0;y<aLevel.size() ;y++) {
                Node nn = aLevel.get(y);
                if(nn.isLeaf() == false){
                    System.out.println(x+":"+nonLeafIndex+":"+nn.getAllLeafCount()+":"+nn.getUserObject());    

                    nonLeafIndex += nn.getAllLeafCount();
                }
                
                //mergeCellAcrossRow(i, j,n.getAllLeafCount());
            
            }
            
        }
        System.out.println("---");
        for (int x=0;x<levelList.size();x++) {
            List<Node> aLevel = levelList.get(x);
            int nonLeafIndex = 0;
            for (int y = 0;y<aLevel.size() ;y++) {
                Node nn = aLevel.get(y);
                if(nn.isLeaf() == false){
                    System.out.println(x+":"+nonLeafIndex+":"+nn.getAllLeafCount()+":"+nn.getUserObject());    

                    nonLeafIndex += nn.getAllLeafCount();
                }
                
                //mergeCellAcrossRow(i, j,n.getAllLeafCount());
            
            }
            
        }
        
    }
}