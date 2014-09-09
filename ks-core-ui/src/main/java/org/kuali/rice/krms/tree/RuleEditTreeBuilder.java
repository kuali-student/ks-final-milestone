/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompoundOpCodeNode;
import org.kuali.rice.krms.tree.node.SimplePropositionEditNode;
import org.kuali.rice.krms.tree.node.SimplePropositionNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.util.KRMSConstants;

/**
 *
 * @author Kuali Student Team
 */
public class RuleEditTreeBuilder extends AbstractTreeBuilder{

    private static final long serialVersionUID = 1L;

    public Tree buildTree(RuleEditor rule) {

        Tree myTree = new Tree<RuleEditorTreeNode, String>();

        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        rootNode.setNodeType(KRMSConstants.ROOT_TYPE);

        myTree.setRootElement(rootNode);

        if (rule.getPropositionEditor() != null){
            Node firstNode = addChildNode(rule, rootNode, rule.getPropositionEditor());
            firstNode.setNodeType(firstNode.getNodeType() + " " + KRMSConstants.ROOT_TYPE);
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
                handleSimpleNode(rule, sprout, prop, leaf);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                handleCompoundNode(rule, sprout, prop, leaf);
            }

            //Set move left disabled flag if simple proposition in the root compound
            if(sprout.getData() != null) {
                if(((RuleEditorTreeNode) sprout.getData()).getProposition().equals(rule.getProposition())) {
                    addNodeType(leaf, KRMSConstants.DISABLE_MOVE_OUT);
                }
            }
            return leaf;
        }
        return null;
    }

    protected void handleCompoundNode(RuleEditor rule, Node sprout, PropositionEditor prop, Node<RuleEditorTreeNode, String> leaf) {
        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getCompoundKeys().next());
        }
        setupCompoundNode(rule, prop, leaf);
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
                addNodeType(childNode, KRMSConstants.FIRST_IN_GROUP);
            }
            if (counter==prop.getCompoundEditors().size()-1){
                addNodeType(childNode, KRMSConstants.LAST_IN_GROUP);
            }
            //Add flag to identify if child can move right, if child has sibling after it
            if((leaf.getData().getProposition().getCompoundEditors().size() - 1) != counter) {
                if(!leaf.getData().getProposition().getCompoundEditors().get(leaf.getData().getProposition().getCompoundEditors().indexOf(child) + 1).getPropositionTypeCode().equals("C")) {
                    addNodeType(childNode, KRMSConstants.DISABLE_MOVE_IN);
                }
            } //Set flag for last child in leaf
            else {
                addNodeType(childNode, KRMSConstants.DISABLE_MOVE_IN);
            }
            counter++;
        }
    }

    protected void setupCompoundNode(RuleEditor rule, PropositionEditor prop, Node<RuleEditorTreeNode, String> leaf) {
        // Compound Proposition: editMode has description as an editable field
        leaf.setNodeLabel(this.getDescription(prop));
        leaf.setNodeType(KRMSConstants.COMPOUND_NODE_TYPE);
        leaf.setData(new SimplePropositionNode(prop));
    }

    protected void handleSimpleNode(RuleEditor rule, Node sprout, PropositionEditor prop, Node<RuleEditorTreeNode, String> leaf) {
        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getSimpleKeys().next());
        }
        // Simple Proposition: add a node for the description display with a child proposition node
        if (prop.isEditMode()) {
            setupEditNode(rule, prop, leaf);
        } else {
            setupSimpleNode(rule, prop, leaf);
        }

        sprout.getChildren().add(leaf);
    }

    protected void setupEditNode(RuleEditor rule, PropositionEditor prop, Node<RuleEditorTreeNode, String> leaf) {
        leaf.setNodeType(KRMSConstants.EDIT_NODE_TYPE);
        PropositionEditor copy = (PropositionEditor) ObjectUtils.deepCopy(prop);
        leaf.setData(new SimplePropositionEditNode(copy));
    }

    protected void setupSimpleNode(RuleEditor rule, PropositionEditor prop, Node<RuleEditorTreeNode, String> leaf) {
        leaf.setNodeLabel(this.buildNodeLabel(rule, prop));
        leaf.setNodeType(KRMSConstants.SIMPLE_NODE_TYPE);
        addNodeType(leaf, KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
        leaf.setData(new SimplePropositionNode(prop));
    }

    protected String buildNodeLabel(RuleEditor rule, PropositionEditor prop) {
        //Build the node label.
        String prefix = this.getPropositionPrefix(prop);
        return prefix + this.getDescription(prop);
    }

    /**
     * This method adds an opCode Node to separate components in a compound proposition.
     *
     * @param currentNode
     * @param prop
     * @return
     */
    protected void addOpCodeNode(Node currentNode, PropositionEditor prop, int counter) {
        //Create the node.
        Node<CompoundOpCodeNode, String> aNode = new Node<CompoundOpCodeNode, String>();
        aNode.setNodeType(KRMSConstants.COMPOUND_OP_NODE_TYPE);

        //Add a dummy editor.
        PropositionEditor editor = new PropositionEditor();
        editor.setKey(prop.getKey() + counter);
        editor.setCompoundOpCode(prop.getCompoundOpCode());

        aNode.setData(new CompoundOpCodeNode(editor));
        currentNode.getChildren().add(aNode);
    }

    public String getNaturalLanguageUsageKey(){
        return null;
    }

}
