package org.kuali.rice.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Build a tree to display to different rules next to each other.
 *
 * Used on the compare lightbox in KRMS.
 *
 * @author Kuali Student Team
 */
public class RuleCompareTreeBuilder extends AbstractTreeBuilder{

    private static final long serialVersionUID = 1L;

    public Tree<CompareTreeNode, String> buildTree(RuleDefinitionContract original, RuleDefinitionContract compare) {
        Tree<CompareTreeNode, String> myTree = new Tree<CompareTreeNode, String>();

        Node<CompareTreeNode, String> rootNode = new Node<CompareTreeNode, String>();
        rootNode.setNodeType("subruleElement");
        rootNode.setData(new CompareTreeNode());
        myTree.setRootElement(rootNode);

        Node<CompareTreeNode, String> firstNode = new Node<CompareTreeNode, String>();
        firstNode.setNodeType("subruleElement");
        firstNode.setData(new CompareTreeNode());
        rootNode.getChildren().add(firstNode);

        if (original != null) {
            addTreeNode(firstNode, original.getProposition(), compare.getProposition());
        }

        //Underline the first node in the preview.
        if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)){
            Node<CompareTreeNode, String> childNode = firstNode.getChildren().get(0);
            if(childNode.getData() != null){
                CompareTreeNode compareTreeNode = childNode.getData();

                if(!compareTreeNode.getOriginal().trim().isEmpty()){
                    compareTreeNode.setOriginal(compareTreeNode.getOriginal() + ":");
                }

                if(!compareTreeNode.getCompared().trim().isEmpty()){
                    compareTreeNode.setCompared(compareTreeNode.getCompared() + ":");
                }
            }
        }

        return myTree;
    }

    public static Tree<CompareTreeNode, String> initCompareTree() {
        Tree<CompareTreeNode, String> myTree = new Tree<CompareTreeNode, String>();

        Node<CompareTreeNode, String> rootNode = new Node<CompareTreeNode, String>();
        rootNode.setNodeType("subruleElement");
        rootNode.setData(new CompareTreeNode());
        myTree.setRootElement(rootNode);

        Node<CompareTreeNode, String> firstNode = new Node<CompareTreeNode, String>();
        firstNode.setNodeType("subruleElement");
        firstNode.setData(new CompareTreeNode());
        rootNode.getChildren().add(firstNode);

        return myTree;
    }

    private void addTreeNode(Node<CompareTreeNode, String> currentNode, PropositionDefinitionContract original, PropositionDefinitionContract compared) {
        if ((original == null) && (compared == null)) {
            return;
        }

        Node<CompareTreeNode, String> newNode = new Node<CompareTreeNode, String>();
        CompareTreeNode tNode = new CompareTreeNode(this.getDescription(original), this.getDescription(compared));
        tNode.setOriginalItems(this.getListItems(original));
        tNode.setComparedItems(this.getListItems(compared));
        if (tNode.getOriginal().equals(tNode.getCompared())){
            newNode.setNodeType("subruleElement");
        } else {
            newNode.setNodeType("subruleElement compareElement");
        }

        newNode.setData(tNode);
        currentNode.getChildren().add(newNode);

        this.addCompoundTreeNode(newNode, original, compared);
    }

    private void addCompoundTreeNode(Node<CompareTreeNode, String> newNode, PropositionDefinitionContract original, PropositionDefinitionContract compared) {

        // Retrieve the opreator code of the original proposition
        String originalOpCode = this.getOpCode(original);

        // Retrieve the opreator code of the compare to proposition
        String compareOpCode = this.getOpCode(compared);

        // Get the children form both nodes.
        List<? extends PropositionDefinitionContract> originalChildren = getChildPropositions(original);
        List<? extends PropositionDefinitionContract> comparedChildren = getChildPropositions(compared);

        // Get the size of the biggest children list
        int size = Math.max(originalChildren.size(), comparedChildren.size());

        for (int i = 0; i < size; i++) {

            // Get the original child proposition at current position
            PropositionDefinitionContract originalChild = null;
            if (originalChildren.size() > i){
                originalChild = originalChildren.get(i);
            } else {
                originalOpCode = " ";
            }

            // Get the compare child proposition at current position
            PropositionDefinitionContract compareChild = null;
            if (comparedChildren.size() > i){
                compareChild = comparedChildren.get(i);
            } else {
                compareOpCode = " ";
            }
            // add an opcode node in between each of the children.
            if (i>0) {
                this.addOperatorTreeNode(newNode, originalOpCode, compareOpCode);
            }

            // call to build the childs node
            addTreeNode(newNode, originalChild, compareChild);
        }

    }

    private List<? extends PropositionDefinitionContract> getChildPropositions(PropositionDefinitionContract parent) {
        List<? extends PropositionDefinitionContract> children;
        if ((parent != null) && (parent.getCompoundComponents()!=null)){
            children = parent.getCompoundComponents();
        } else {
            children = new ArrayList<PropositionDefinitionContract>();
        }
        return children;
    }

    private String getOpCode(PropositionDefinitionContract proposition){

        String operatorCode = " ";
        if (proposition != null){
            if (LogicalOperator.AND.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                operatorCode = "AND";
            } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                operatorCode = "OR";
            }
        }
        return operatorCode;
    }

    private void addOperatorTreeNode(Node<CompareTreeNode, String> newNode, String originial, String compared) {
        Node<CompareTreeNode, String> opNode = new Node<CompareTreeNode, String>();
        if (!originial.equals(compared)){
            opNode.setNodeType("compareElement");
        }
        opNode.setData(new CompareTreeNode(originial, compared));
        newNode.getChildren().add(opNode);
    }

    public List<String> getListItems(PropositionDefinitionContract propositionEditor) {
        return null;
    }

    public String getNaturalLanguageUsageKey(){
        return  KsKrmsConstants.KRMS_NL_RULE_EDIT;
    }

}
