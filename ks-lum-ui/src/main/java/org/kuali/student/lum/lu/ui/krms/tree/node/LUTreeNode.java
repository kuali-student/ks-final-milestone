package org.kuali.student.lum.lu.ui.krms.tree.node;

import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.lum.lu.ui.krms.dto.CluGroup;

import java.util.List;

/**
 * Created by SW Genis on 2014/04/04.
 */
public class LUTreeNode extends TreeNode {

    private List<CluGroup> cluGroups;

    public LUTreeNode(String data){
        super(data);
    }

    public List<CluGroup> getCluGroups() {
        return cluGroups;
    }

    public void setCluGroups(List<CluGroup> cluGroups) {
        this.cluGroups = cluGroups;
    }
}
