package org.kuali.student.enrollment.class1.krms.dto;

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
    public static final String COMPOUND_NODE_TYPE = "ruleTreeNode compoundNode";
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
