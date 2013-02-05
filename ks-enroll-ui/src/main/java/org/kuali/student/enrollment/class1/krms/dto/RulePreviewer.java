package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.student.enrollment.class1.krms.form.TreeNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2/4/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class RulePreviewer {

    private static final long serialVersionUID = 1L;

    private  Tree<TreeNode, String> previewTree = new Tree<TreeNode, String>();

    public RulePreviewer() {
    }

    public Tree<TreeNode, String> getPreviewTree() {
        return previewTree;
    }

    public void initPreviewTree(RuleBo rule){
        Tree myTree = new Tree<TreeNode, String>();

        Node<TreeNode, String> rootNode = new Node<TreeNode, String>();
        rootNode.setNodeLabel("root");
        rootNode.setNodeType("rootNode");
        rootNode.setData(new TreeNode("Rule:"));
        myTree.setRootElement(rootNode);

        if (rule != null){
            PropositionBo prop = rule.getProposition();
            buildPreviewTree( rootNode, prop);
        }

        //Underline the first node in the preview.
        if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)){
            Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
            firstNode.setNodeType("subruleHeader");
            firstNode.setNodeLabel(firstNode.getNodeLabel() + ":");
        }

        this.previewTree = myTree;
    }

    private void buildPreviewTree(Node<TreeNode, String> currentNode, PropositionBo prop){
        if (prop != null) {
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                Node<TreeNode, String> newNode = new Node<TreeNode, String>();
                newNode.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                TreeNode tNode = new TreeNode(prop.getDescription());
                newNode.setData(tNode);
                currentNode.getChildren().add(newNode);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                Node<TreeNode, String> newNode = new Node<TreeNode, String>();
                newNode.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                TreeNode tNode = new TreeNode(prop.getDescription());
                newNode.setData(tNode);
                currentNode.getChildren().add(newNode);

                boolean first = true;
                List <PropositionBo> nodeChildren = prop.getCompoundComponents();
                int compoundSequenceNumber = 0;
                for (PropositionBo child : nodeChildren){
                    child.setCompoundSequenceNumber(++compoundSequenceNumber);  // start with 1
                    // add an opcode node in between each of the children.
                    if (!first){
                        //addOpCodeNode(newNode, propositionEditor);
                        String opCodeLabel = "";

                        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opCodeLabel = "AND";
                        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opCodeLabel = "OR";
                        }
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        opNode.setNodeLabel(opCodeLabel);
                        opNode.setData(new TreeNode(prop.getCompoundOpCode()));
                        newNode.getChildren().add(opNode);
                    }
                    first = false;
                    // call to build the childs node
                    buildPreviewTree(newNode, child);
                }
            }

        }
    }
}
