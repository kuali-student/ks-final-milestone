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
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;

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

    public Tree<CompareTreeNode, String> buildTree(RuleEditor firstElement, RuleEditor secondElement) {
        Tree<CompareTreeNode, String> myTree = initCompareTree();
        Node<CompareTreeNode, String> firstNode = myTree.getRootElement().getChildren().get(0);

        //Add the nodes recursively.
        addTreeNode(firstNode, getRootProposition(firstElement), getRootProposition(secondElement));

        //Underline the first node in the preview.
        if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)){
            Node<CompareTreeNode, String> childNode = firstNode.getChildren().get(0);
            if(childNode.getData() != null){
                CompareTreeNode compareTreeNode = childNode.getData();

                if(firstElement != null) {
                    if(firstElement.getProposition() != null) {
                        if(firstElement.getProposition().getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
                            if(!compareTreeNode.getFirstElement().trim().isEmpty()){
                                compareTreeNode.setFirstElement(compareTreeNode.getFirstElement() + ":");
                            }
                        }
                    }
                }

                if(secondElement != null) {
                    if(secondElement.getProposition() != null) {
                        if(secondElement.getProposition().getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
                            if(!compareTreeNode.getSecondElement().trim().isEmpty()){
                                compareTreeNode.setSecondElement(compareTreeNode.getSecondElement() + ":");
                            }
                        }
                    }
                }
            }
        }

        return myTree;
    }

    protected PropositionEditor getRootProposition(RuleEditor rule){
        if(rule!=null){
            return rule.getPropositionEditor();
        }
        return null;
    }

    public static Tree<CompareTreeNode, String> initCompareTree() {
        Tree<CompareTreeNode, String> myTree = new Tree<CompareTreeNode, String>();

        Node<CompareTreeNode, String> rootNode = createCompareNode();
        myTree.setRootElement(rootNode);

        rootNode.getChildren().add(createCompareNode());

        return myTree;
    }

    private static Node<CompareTreeNode, String> createCompareNode() {
        Node<CompareTreeNode, String> rootNode = new Node<CompareTreeNode, String>();
        rootNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
        rootNode.setData(new CompareTreeNode());
        return rootNode;
    }

    protected void addTreeNode(Node<CompareTreeNode, String> currentNode, PropositionEditor firstElement, PropositionEditor secondElement) {
        if ((firstElement == null) && (secondElement == null)) {
            return;
        }

        Node<CompareTreeNode, String> newNode = new Node<CompareTreeNode, String>();
        CompareTreeNode tNode = new CompareTreeNode(this.getDescription(firstElement), this.getDescription(secondElement));
        tNode.setFirstElementItems(this.getListItems(firstElement));
        tNode.setSecondElementItems(this.getListItems(secondElement));
        newNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
        if (!tNode.getFirstElement().equals(tNode.getSecondElement())){
            addNodeType(newNode, KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        }

        newNode.setData(tNode);
        currentNode.getChildren().add(newNode);

        this.addCompoundTreeNode(newNode, firstElement, secondElement);
    }

    protected void addCompoundTreeNode(Node<CompareTreeNode, String> newNode, PropositionEditor firstElement, PropositionEditor secondElement) {

        // Retrieve the opreator code of the propositions
        String firstOpCode = this.getLabelForOperator(firstElement);
        String secondOpCode = this.getLabelForOperator(secondElement);

        // Get the size of the biggest children list
        int size = Math.max(getChildrenSize(firstElement), getChildrenSize(secondElement));

        for (int i = 0; i < size; i++) {

            PropositionEditor first = getChildForIndex(firstElement, i);
            PropositionEditor second = getChildForIndex(secondElement, i);

            // add an opcode node in between each of the children.
            if (i>0) {
                this.addOperatorTreeNode(newNode, getLabelForChild(first, firstOpCode), getLabelForChild(second, secondOpCode));
            }

            // call to build the childs node
            addTreeNode(newNode, first, second);
        }

    }

    protected String getLabelForChild(PropositionEditor proposition, String label){
        if (proposition!=null){
            return label;
        }
        return StringUtils.EMPTY;
    }

    protected String getLabelForOperator(PropositionEditor proposition){
        if(proposition!=null){
            return PropositionTreeUtil.getLabelForOperator(proposition.getCompoundOpCode());
        }
        return " ";
    }

    protected int getChildrenSize(PropositionEditor parent) {
        if ((parent != null) && (parent.getCompoundComponents()!=null)){
            return parent.getCompoundEditors().size();
        }
        return 0;
    }

    protected PropositionEditor getChildForIndex(PropositionEditor parent, int index) {
        if ((parent != null) && (parent.getCompoundComponents()!=null)){
            if(parent.getCompoundComponents().size() > index){
                return parent.getCompoundEditors().get(index);
            }
        }
        return null;
    }

    protected void addOperatorTreeNode(Node<CompareTreeNode, String> newNode, String firstElement, String secondElement) {
        Node<CompareTreeNode, String> opNode = new Node<CompareTreeNode, String>();
        if (!firstElement.equals(secondElement)){
            opNode.setNodeType(KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        }
        opNode.setData(new CompareTreeNode(firstElement, secondElement));
        newNode.getChildren().add(opNode);
    }

    public List<String> getListItems(PropositionEditor propositionEditor) {
        return null;
    }

    public String getNaturalLanguageUsageKey(){
        return null;
    }

}
