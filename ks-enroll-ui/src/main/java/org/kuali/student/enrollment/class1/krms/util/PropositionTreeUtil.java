package org.kuali.student.enrollment.class1.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditorTreeNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionTreeUtil {

    public static Node<RuleEditorTreeNode, String> findParentPropositionNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId){
        Node<RuleEditorTreeNode,String> bingo = null;
        if (selectedPropId != null) {
            // if it's in children, we have the parent
            List<Node<RuleEditorTreeNode,String>> children = currentNode.getChildren();
            for( Node<RuleEditorTreeNode,String> child : children){
                RuleEditorTreeNode dataNode = child.getData();
                if (selectedPropId.equalsIgnoreCase(dataNode.getProposition().getProposition().getId()))
                    return currentNode;
            }

            // if not found check grandchildren
            for( Node<RuleEditorTreeNode,String> kid : children){
                bingo = findParentPropositionNode(kid, selectedPropId);
                if (bingo != null) break;
            }
        }
        return bingo;
    }

    public static PropositionEditor findPropositionEditor(String propositionId, Tree<RuleEditorTreeNode, String> propositionTree) {
        if (propositionId != null && propositionTree != null) {
            List<Node<RuleEditorTreeNode, String>> nodeList = propositionTree.toList();
            if (nodeList != null) {
                for (Node<RuleEditorTreeNode, String> node : nodeList) {
                    RuleEditorTreeNode ruleEditorTreeNode = node.getData();
                    if (ruleEditorTreeNode != null) {
                        PropositionEditor propositionEditor = ruleEditorTreeNode.getProposition();
                        if (propositionId.equals(propositionEditor.getProposition().getId())) {
                            return propositionEditor;
                        }
                    }
                }
            }
        }
        return null;
    }

}
