package org.kuali.rice.krms.tree.node;

import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeNode implements Serializable {

    private String data;

    public TreeNode(){
    }

    public TreeNode(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
