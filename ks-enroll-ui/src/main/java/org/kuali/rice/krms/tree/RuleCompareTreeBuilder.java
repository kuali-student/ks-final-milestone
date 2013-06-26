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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
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

    public Tree<CompareTreeNode, String> buildTree(RuleEditor original, RuleEditor compare) {
        Tree<CompareTreeNode, String> myTree = new Tree<CompareTreeNode, String>();

        Node<CompareTreeNode, String> rootNode = createCompareNode();
        myTree.setRootElement(rootNode);

        Node<CompareTreeNode, String> firstNode = createCompareNode();
        rootNode.getChildren().add(firstNode);

        //Get the root original proposition.
        PropositionEditor originalProp = null;
        if(original!=null){
            originalProp = original.getPropositionEditor();
        }

        //Get the root compare proposition.
        PropositionEditor compareProp = null;
        if(compare!=null){
            compareProp = compare.getPropositionEditor();
        }

        //Add the nodes recursively.
        addTreeNode(firstNode, originalProp, compareProp);

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

        Node<CompareTreeNode, String> rootNode = createCompareNode();
        myTree.setRootElement(rootNode);

        Node<CompareTreeNode, String> firstNode = createCompareNode();
        rootNode.getChildren().add(firstNode);

        return myTree;
    }

    private static Node<CompareTreeNode, String> createCompareNode() {
        Node<CompareTreeNode, String> rootNode = new Node<CompareTreeNode, String>();
        rootNode.setNodeType(NODE_TYPE_SUBRULEELEMENT);
        rootNode.setData(new CompareTreeNode());
        return rootNode;
    }

    private void addTreeNode(Node<CompareTreeNode, String> currentNode, PropositionEditor original, PropositionEditor compared) {
        if ((original == null) && (compared == null)) {
            return;
        }

        Node<CompareTreeNode, String> newNode = new Node<CompareTreeNode, String>();
        CompareTreeNode tNode = new CompareTreeNode(this.getDescription(original), this.getDescription(compared));
        tNode.setOriginalItems(this.getListItems(original));
        tNode.setComparedItems(this.getListItems(compared));
        newNode.setNodeType(NODE_TYPE_SUBRULEELEMENT);
        if (!tNode.getOriginal().equals(tNode.getCompared())){
            addNodeType(newNode, NODE_TYPE_COMPAREELEMENT);
        }

        newNode.setData(tNode);
        currentNode.getChildren().add(newNode);

        this.addCompoundTreeNode(newNode, original, compared);
    }

    private void addCompoundTreeNode(Node<CompareTreeNode, String> newNode, PropositionEditor original, PropositionEditor compared) {

        // Retrieve the opreator code of the original proposition
        String originalOpCode = " ";
        if(original!=null){
            originalOpCode = PropositionTreeUtil.getLabelForOperator(original.getCompoundOpCode());
        }

        // Retrieve the opreator code of the compare to proposition
        String compareOpCode = " ";
        if(compared!=null){
            compareOpCode = PropositionTreeUtil.getLabelForOperator(compared.getCompoundOpCode());
        }

        // Get the children form both nodes.
        List<PropositionEditor> originalChildren = getChildPropositions(original);
        List<PropositionEditor> comparedChildren = getChildPropositions(compared);

        // Get the size of the biggest children list
        int size = Math.max(originalChildren.size(), comparedChildren.size());

        for (int i = 0; i < size; i++) {

            // Get the original child proposition at current position
            PropositionEditor originalChild = null;
            if (originalChildren.size() > i){
                originalChild = originalChildren.get(i);
            }

            // Get the compare child proposition at current position
            PropositionEditor compareChild = null;
            if (comparedChildren.size() > i){
                compareChild = comparedChildren.get(i);
            }

            // add an opcode node in between each of the children.
            if (i>0) {
                this.addOperatorTreeNode(newNode, originalOpCode, compareOpCode);
            }

            // call to build the childs node
            addTreeNode(newNode, originalChild, compareChild);
        }

    }

    private List<PropositionEditor> getChildPropositions(PropositionEditor parent) {
        List<PropositionEditor> children;
        if ((parent != null) && (parent.getCompoundComponents()!=null)){
            children = parent.getCompoundEditors();
        } else {
            children = new ArrayList<PropositionEditor>();
        }
        return children;
    }

    private void addOperatorTreeNode(Node<CompareTreeNode, String> newNode, String originial, String compared) {
        Node<CompareTreeNode, String> opNode = new Node<CompareTreeNode, String>();
        if (!originial.equals(compared)){
            opNode.setNodeType(NODE_TYPE_COMPAREELEMENT);
        }
        opNode.setData(new CompareTreeNode(originial, compared));
        newNode.getChildren().add(opNode);
    }

    public List<String> getListItems(PropositionEditor propositionEditor) {
        return null;
    }

    public String getNaturalLanguageUsageKey(){
        return  KsKrmsConstants.KRMS_NL_RULE_EDIT;
    }

}
