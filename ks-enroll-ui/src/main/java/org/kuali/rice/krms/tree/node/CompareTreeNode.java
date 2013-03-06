package org.kuali.rice.krms.tree.node;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/20
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class CompareTreeNode {

    private String original;
    private String compared;

    public CompareTreeNode(){
    }

    public CompareTreeNode(String original, String compared){
        this.original = original;
        this.compared = compared;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getCompared() {
        return compared;
    }

    public void setCompared(String compared) {
        this.compared = compared;
    }
}
