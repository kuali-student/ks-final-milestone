package org.kuali.rice.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.tree.node.KSCompoundOpCodeNode;
import org.kuali.student.enrollment.class1.krms.tree.node.KSCompoundPropositionEditNode;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionEditNode;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionNode;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2/4/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleEditTreeBuilder extends AbstractTreeBuilder{

    private static final long serialVersionUID = 1L;

    public Tree buildTree(RuleEditor rule) {

        Tree myTree = new Tree<RuleEditorTreeNode, String>();

        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        rootNode.setNodeType(RuleEditorTreeNode.ROOT_TYPE);

        myTree.setRootElement(rootNode);

        PropositionEditor prop = (PropositionEditor) rule.getProposition();

        if (prop != null){

            addChildNode(rule, rootNode, prop);
        }

        //Underline the first node in the preview.
        if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)){
            for(Node<RuleEditorTreeNode, String> childNode : rootNode.getChildren()) {
                childNode.setNodeType(childNode.getNodeType() + " " + RuleEditorTreeNode.ROOT_TYPE);
            }
        }

        return myTree;
    }

    /**
     * This method builds a propositionTree recursively walking through the children of the proposition.
     *
     * @param sprout - parent tree node
     * @param prop   - PropositionBo for which to make the tree node
     */
    private void addChildNode(RuleEditor rule, Node sprout, PropositionEditor prop) {
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        if (prop != null) {
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Simple Proposition
                // add a node for the description display with a child proposition node
                Node<RuleEditorTreeNode, String> child = new Node<RuleEditorTreeNode, String>();

                // Simple Proposition add a node for the description display with a child proposition node
                if (prop.isEditMode()) {
                    child.setNodeType(KSSimplePropositionEditNode.NODE_TYPE);
                    KSSimplePropositionEditNode pNode = new KSSimplePropositionEditNode(prop);
                    child.setData(pNode);
                } else {
                    child.setNodeLabel(this.buildNodeLabel(rule, prop));
                    child.setNodeType(KSSimplePropositionNode.NODE_TYPE);
                    KSSimplePropositionNode pNode = new KSSimplePropositionNode(prop);
                    child.setData(pNode);
                }
                sprout.getChildren().add(child);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Compound Proposition
                Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();

                // editMode has description as an editable field
                if (prop.isEditMode()) {
                    aNode.setNodeLabel("");
                    aNode.setNodeType(KSCompoundPropositionEditNode.NODE_TYPE);
                    KSCompoundPropositionEditNode pNode = new KSCompoundPropositionEditNode(prop);
                    aNode.setData(pNode);
                } else {
                    aNode.setNodeLabel(this.buildNodeLabel(rule, prop));
                    aNode.setNodeType(RuleEditorTreeNode.COMPOUND_NODE_TYPE);
                    RuleEditorTreeNode pNode = new RuleEditorTreeNode(prop);
                    aNode.setData(pNode);
                }
                sprout.getChildren().add(aNode);

                int counter = 0;
                for (PropositionEditor child : prop.getCompoundEditors()) {
                    // add an opcode node in between each of the children.
                    if (counter > 0) {
                        addOpCodeNode(aNode, prop, counter);
                    }
                    counter++;
                    // call to build the childs node
                    addChildNode(rule, aNode, child);
                }
            }
        }
    }

    private String buildNodeLabel(RuleEditor rule, PropositionEditor prop) {
        //Build the node label.
        String prefix = this.getPropositionPrefix(rule, prop);
        return prefix + StringEscapeUtils.escapeHtml(this.getDescription(prop));
    }

    /**
     * This method adds an opCode Node to separate components in a compound proposition.
     *
     * @param currentNode
     * @param prop
     * @return
     */
    private void addOpCodeNode(Node currentNode, PropositionEditor prop, int counter) {
        //Create the node.
        Node<KSCompoundOpCodeNode, String> aNode = new Node<KSCompoundOpCodeNode, String>();
        aNode.setNodeLabel("");
        aNode.setNodeType("ruleTreeNode compoundOpCodeNode");

        //Add a dummy editor.
        PropositionEditor editor = new PropositionEditor();
        editor.setKey(prop.getKey() + counter);
        editor.setCompoundOpCode(prop.getCompoundOpCode());

        aNode.setData(new KSCompoundOpCodeNode(editor));
        currentNode.getChildren().add(aNode);
    }

    public String getNaturalLanguageUsageKey(){
        return  KsKrmsConstants.KRMS_NL_RULE_EDIT;
    }
}
