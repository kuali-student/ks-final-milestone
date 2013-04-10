package org.kuali.rice.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.TreeNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2/4/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleViewTreeBuilder extends AbstractTreeBuilder {

    private static final long serialVersionUID = 1L;

    public Tree<TreeNode, String> buildTree(RuleEditor rule) {

        Tree myTree = new Tree<TreeNode, String>();

        Node<TreeNode, String> rootNode = new Node<TreeNode, String>();
        rootNode.setNodeLabel("root");
        rootNode.setNodeType("rootNode");
        rootNode.setData(new TreeNode("Rule:"));
        myTree.setRootElement(rootNode);

        if (rule == null) {
            return myTree;
        }

        PropositionEditor prop = (PropositionEditor) rule.getProposition();

        if (prop != null) {

            buildPreviewTree(rule, rootNode, prop);

            //Underline the first node in the preview.
            if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)) {
                Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
                if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)) {
                    firstNode.setNodeType("subruleHeader subruleElement");
                    firstNode.setNodeLabel("<u>" + firstNode.getNodeLabel() + ":</u>");
                }
            }
        }

        return myTree;
    }

    private void buildPreviewTree(RuleEditor rule, Node<TreeNode, String> currentNode, PropositionEditor prop) {
        if (prop != null) {

            Node<TreeNode, String> newNode = new Node<TreeNode, String>();
            newNode.setNodeLabel(null);
            newNode.setNodeType("subruleElement");

            TreeNode tNode = new TreeNode(this.buildNodeLabel(rule, prop));
            tNode.setListItems(this.getListItems(prop));
            tNode.setKey(prop.getKey());
            newNode.setData(tNode);
            currentNode.getChildren().add(newNode);

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {

                boolean first = true;
                for (PropositionEditor child : prop.getCompoundEditors()) {
                    // add an opcode node in between each of the children.
                    if (!first) {
                        //addOpCodeNode(newNode, propositionEditor);
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())) {
                            opNode.setNodeLabel("AND");
                        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())) {
                            opNode.setNodeLabel("OR");
                        }

                        opNode.setData(new TreeNode(prop.getCompoundOpCode()));
                        newNode.getChildren().add(opNode);
                    }
                    first = false;
                    // call to build the childs node
                    buildPreviewTree(rule, newNode, child);
                }
            }

        }
    }

    public List<String> getListItems(PropositionEditor propositionEditor) {
        return null;
    }
}
