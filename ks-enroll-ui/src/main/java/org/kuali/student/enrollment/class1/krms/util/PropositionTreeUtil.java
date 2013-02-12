package org.kuali.student.enrollment.class1.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
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

    /**
     * @return the {@link org.kuali.rice.krms.impl.repository.PropositionBo} from the form
     */
    public static PropositionEditor getProposition(RuleEditor ruleEditor) {

        if (ruleEditor != null) {
            String selectedPropId = ruleEditor.getSelectedPropositionId();
            return findProposition(ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
        }

        return null;
    }

    private static PropositionEditor findProposition(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId) {

        if (selectedPropId == null) {
            return null;
        }

        // if it's in children, we have the parent
        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            PropositionEditor proposition = child.getData().getProposition();
            if (selectedPropId.equalsIgnoreCase(proposition.getProposition().getId())) {
                return proposition;
            } else {
                // if not found check grandchildren
                proposition = findProposition(child, selectedPropId);
                if (proposition != null) {
                    return proposition;
                }
            }
        }

        return null;
    }

    /**
     * Find and return the node containing the proposition that is in currently in edit mode
     *
     * @param node the node to start searching from (typically the root)
     * @return the node that is currently being edited, if any.  Otherwise, null.
     */
    public static Node<RuleEditorTreeNode, String> findEditedProposition(Node<RuleEditorTreeNode, String> node) {
        Node<RuleEditorTreeNode, String> result = null;
        if (node.getData() != null && node.getData().getProposition() != null && node.getData().getProposition().getProposition()
                .getEditMode()) {
            result = node;
        } else {
            for (Node<RuleEditorTreeNode, String> child : node.getChildren()) {
                result = findEditedProposition(child);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    public static void resetEditModeOnPropositionTree(RuleEditor ruleEditor) {
        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();
        resetEditModeOnPropositionTree(root);
    }

    /**
     * disable edit mode for all Nodes beneath and including the passed in Node
     *
     * @param currentNode
     */
    public static void resetEditModeOnPropositionTree(Node<RuleEditorTreeNode, String> currentNode) {
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            dataNode.getProposition().getProposition().setEditMode(false);
        }
        List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
        for (Node<RuleEditorTreeNode, String> child : children) {
            resetEditModeOnPropositionTree(child);
        }
    }

    public static Node<RuleEditorTreeNode, String> findPropositionTreeNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId) {
        Node<RuleEditorTreeNode, String> bingo = null;
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            if (selectedPropId.equalsIgnoreCase(dataNode.getProposition().getProposition().getId())) {
                return currentNode;
            }
        }
        List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
        for (Node<RuleEditorTreeNode, String> child : children) {
            bingo = findPropositionTreeNode(child, selectedPropId);
            if (bingo != null) break;
        }
        return bingo;
    }

}
