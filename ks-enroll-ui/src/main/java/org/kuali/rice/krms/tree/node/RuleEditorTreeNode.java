package org.kuali.rice.krms.tree.node;

import org.kuali.rice.krms.dto.PropositionEditor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleEditorTreeNode implements Serializable {

    private static final long serialVersionUID = 8038174553531544943L;

    public static final String ROOT_TYPE = "treeRoot";

    public static final String NODE_TYPE = "ruleTreeNode";

    public static final String FIRST_IN_GROUP = "firstInGroup";

    public static final String LAST_IN_GROUP = "lastInGroup";

    public static final String COMPOUND_NODE_TYPE = RuleEditorTreeNode.NODE_TYPE + " compoundNode";

    public static final String COMPOUND_OP_NODE_TYPE = RuleEditorTreeNode.NODE_TYPE + " compoundOpCodeNode";

    protected PropositionEditor proposition;

    public RuleEditorTreeNode(){}

    public RuleEditorTreeNode(PropositionEditor proposition){
        this.proposition = proposition;
    }

    public PropositionEditor getProposition() {
        return this.proposition;
    }

    public void setProposition(PropositionEditor proposition) {
        this.proposition = proposition;
    }

}
