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
 *
 * @author Kuali Student Team
 */
public class RuleEditTreeBuilder extends AbstractTreeBuilder{

    private static final long serialVersionUID = 1L;

    public Tree buildTree(RuleEditor rule) {

        Tree myTree = new Tree<RuleEditorTreeNode, String>();

        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        rootNode.setNodeType(RuleEditorTreeNode.ROOT_TYPE);

        myTree.setRootElement(rootNode);

        if (rule.getPropositionEditor() != null){
            Node firstNode = addChildNode(rule, rootNode, rule.getPropositionEditor());
            firstNode.setNodeType(firstNode.getNodeType() + " " + RuleEditorTreeNode.ROOT_TYPE);
        }

        return myTree;
    }

    /**
     * This method builds a propositionTree recursively walking through the children of the proposition.
     *
     * @param sprout - parent tree node
     * @param prop   - PropositionBo for which to make the tree node
     */
    private Node addChildNode(RuleEditor rule, Node sprout, PropositionEditor prop) {
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        if (prop != null) {
            // add a node for the description display with a child proposition node
            Node<RuleEditorTreeNode, String> leaf = new Node<RuleEditorTreeNode, String>();
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Simple Proposition: add a node for the description display with a child proposition node
                if (prop.isEditMode()) {
                    leaf.setNodeType(KSSimplePropositionEditNode.NODE_TYPE);
                    KSSimplePropositionEditNode pNode = new KSSimplePropositionEditNode(prop);
                    leaf.setData(pNode);
                } else {
                    leaf.setNodeLabel(this.buildNodeLabel(rule, prop));
                    leaf.setNodeType(KSSimplePropositionNode.NODE_TYPE);
                    KSSimplePropositionNode pNode = new KSSimplePropositionNode(prop);
                    leaf.setData(pNode);
                }
                sprout.getChildren().add(leaf);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Compound Proposition: editMode has description as an editable field
                leaf.setNodeLabel(this.buildNodeLabel(rule, prop));
                leaf.setNodeType(RuleEditorTreeNode.COMPOUND_NODE_TYPE);
                RuleEditorTreeNode pNode = new RuleEditorTreeNode(prop);
                leaf.setData(pNode);
                sprout.getChildren().add(leaf);

                int counter = 0;
                for (PropositionEditor child : prop.getCompoundEditors()) {
                    // add an opcode node in between each of the children.
                    if (counter > 0) {
                        addOpCodeNode(leaf, prop, counter);
                    }
                    // call to build the childs node
                    Node childNode = addChildNode(rule, leaf, child);
                    if (counter==0){
                        childNode.setNodeType(childNode.getNodeType() + " " + RuleEditorTreeNode.FIRST_IN_GROUP);
                    }
                    if (counter==prop.getCompoundEditors().size()-1){
                        childNode.setNodeType(childNode.getNodeType() + " " + RuleEditorTreeNode.LAST_IN_GROUP);
                    }
                    counter++;
                }
            }
            return leaf;
        }
        return null;
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
