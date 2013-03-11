package org.kuali.rice.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionTreeUtil {

    public static Node<RuleEditorTreeNode, String> findParentPropositionNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropKey){
        Node<RuleEditorTreeNode,String> bingo = null;
        if (selectedPropKey != null) {
            // if it's in children, we have the parent
            List<Node<RuleEditorTreeNode,String>> children = currentNode.getChildren();
            for( Node<RuleEditorTreeNode,String> child : children){
                RuleEditorTreeNode dataNode = child.getData();
                if (selectedPropKey.equalsIgnoreCase(dataNode.getProposition().getKey())) {
                    return currentNode;
                }
            }

            // if not found check grandchildren
            for( Node<RuleEditorTreeNode,String> kid : children){
                bingo = findParentPropositionNode(kid, selectedPropKey);
                if (bingo != null) {
                    break;
                }
            }
        }
        return bingo;
    }

    /**
     * @return the {@link org.kuali.rice.krms.impl.repository.PropositionBo} from the form
     */
    public static PropositionEditor getProposition(RuleEditor ruleEditor) {

        if (ruleEditor != null) {
            String selectedPropKey = ruleEditor.getSelectedKey();
            return findProposition(ruleEditor.getEditTree().getRootElement(), selectedPropKey);
        }

        return null;
    }

    private static PropositionEditor findProposition(Node<RuleEditorTreeNode, String> currentNode, String selectedPropKey) {

        if (selectedPropKey == null) {
            return null;
        } else if(selectedPropKey.isEmpty()) {
            return currentNode.getChildren().get(0).getData().getProposition();
        }

        // if it's in children, we have the parent
        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            PropositionEditor proposition = child.getData().getProposition();
            if ("S".equals(proposition.getPropositionTypeCode()) && proposition.isEditMode()) {
                return proposition;
            } else if(!proposition.isEditMode()) {
                // if not found check grandchildren
                proposition = findProposition(child, selectedPropKey);
                if (proposition != null) {
                    return proposition;
                }
            } else if(selectedPropKey.equalsIgnoreCase(proposition.getKey())){
                return proposition;
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
        if (node.getData() != null && node.getData().getProposition() != null && node.getData().getProposition()
                .isEditMode()) {
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
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
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
            dataNode.getProposition().setEditMode(false);
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
            if (selectedPropId.equalsIgnoreCase(dataNode.getProposition().getId())) {
                return currentNode;
            }
        }

        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            bingo = findPropositionTreeNode(child, selectedPropId);
            if (bingo != null) break;
        }
        return bingo;
    }

    public static String configureLogicExpression(PropositionEditor proposition) {
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        String logicExpression = proposition.getKey();
        if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
            logicExpression += "(";
            boolean first = true;
            for (PropositionEditor child : proposition.getCompoundEditors()) {
                // add an opcode node in between each of the children.
                if (!first) {
                    if (LogicalOperator.AND.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                        logicExpression += " AND ";
                    } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                        logicExpression += " OR ";
                    }
                }
                first = false;
                // call to build the childs node
                logicExpression += configureLogicExpression(child);
            }
            logicExpression += ")";
        }
        return logicExpression;
    }

}
