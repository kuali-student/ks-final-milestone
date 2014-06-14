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
package org.kuali.student.enrollment.class1.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;

/**
 * This is a helper class to build the compare tree to be displayed on the lightboxes on the ui to compare one set of
 * rules with another. Rules statements that differ is highlighted in the ui with a css class.
 * <p/>
 * This class is overridden to add AO specific headers to the tree structure and add list items specific to multicourse
 * rule statement(proposition) types.
 *
 * @author Kuali Student Team
 */
public class AORuleCompareTreeBuilder extends KSRuleCompareTreeBuilder {

    public Tree<CompareTreeNode, String> buildTree(RuleEditor firstElement, RuleEditor secondElement, RuleEditor thirdElement) {
        Tree<CompareTreeNode, String> compareTree = this.buildAOTree(firstElement, secondElement, thirdElement);

        //Set data headers on root node.
        Node<CompareTreeNode, String> node = compareTree.getRootElement();
        if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
            Node<CompareTreeNode, String> childNode = node.getChildren().get(0);

            // Set the headers on the first root child
            if (childNode.getData() != null) {
                CompareTreeNode compareTreeNode = childNode.getData();
                compareTreeNode.setFirstElement("Catalog Rules");
                compareTreeNode.setSecondElement("Course Offering Rule");
                compareTreeNode.setThirdElement("Activity Offering Rules");

            }

        }

        return compareTree;
    }

    private Tree<CompareTreeNode, String> buildAOTree(RuleEditor firstElement, RuleEditor secondElement, RuleEditor thirdElement) {
        Tree<CompareTreeNode, String> myTree = initCompareTree();
        Node<CompareTreeNode, String> firstNode = myTree.getRootElement().getChildren().get(0);

        //Add the nodes recursively.
        addTreeNode(firstNode, getRootProposition(firstElement), getRootProposition(secondElement), getRootProposition(thirdElement));

        //Underline the first node in the preview.
        if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)) {
            Node<CompareTreeNode, String> childNode = firstNode.getChildren().get(0);
            if (childNode.getData() != null) {
                CompareTreeNode compareTreeNode = childNode.getData();

                if(colonRequired(firstElement)) {
                    if(!compareTreeNode.getFirstElement().trim().isEmpty()){
                        compareTreeNode.setFirstElement(compareTreeNode.getFirstElement() + ":");
                    }
                }

                if(colonRequired(secondElement)) {
                    if(!compareTreeNode.getSecondElement().trim().isEmpty()){
                        compareTreeNode.setSecondElement(compareTreeNode.getSecondElement() + ":");
                    }
                }

                if(colonRequired(thirdElement)) {
                    if(!compareTreeNode.getThirdElement().trim().isEmpty()){
                        compareTreeNode.setThirdElement(compareTreeNode.getThirdElement() + ":");
                    }
                }

                //Set suppressed rule description
                if (!thirdElement.isDummy() && thirdElement.getProposition() == null && thirdElement.getParent().getProposition() != null) {
                    compareTreeNode.setThirdElement("Rule Suppressed");
                }
            }
        }

        return myTree;
    }

    protected boolean colonRequired(RuleEditor element) {
        if(element != null && element.getProposition() != null) {
            if(element.getProposition().getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
                return true;
            }
        }
        return false;
    }


    private void addTreeNode(Node<CompareTreeNode, String> currentNode, PropositionEditor firstElement, PropositionEditor secondElement, PropositionEditor thirdElement) {
        if ((firstElement == null) && (secondElement == null) && (thirdElement == null)) {
            return;
        }

        Node<CompareTreeNode, String> newNode = new Node<CompareTreeNode, String>();
        CompareTreeNode tNode = new CompareTreeNode(this.getNodeWidth(currentNode), this.getDescription(firstElement),
                this.getDescription(secondElement), this.getDescription(thirdElement));
        tNode.setFirstElementItems(this.getListItems(firstElement));
        tNode.setSecondElementItems(this.getListItems(secondElement));
        tNode.setThirdElementItems(this.getListItems(thirdElement));
        newNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
        if (!tNode.getFirstElement().equals(tNode.getSecondElement())) {
            addNodeType(newNode, KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        } else if (!tNode.getSecondElement().equals(tNode.getThirdElement())) {
            addNodeType(newNode, KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        }
        newNode.setData(tNode);
        currentNode.getChildren().add(newNode);

        this.addCompoundTreeNode(newNode, firstElement, secondElement, thirdElement);
    }

    private void addCompoundTreeNode(Node<CompareTreeNode, String> newNode, PropositionEditor firstElement, PropositionEditor secondElement, PropositionEditor thirdElement) {

        // Retrieve the opreator code of the propositions
        String coOpCode = this.getLabelForOperator(firstElement);
        String cluOpCode = this.getLabelForOperator(secondElement);
        String aoOpCode = this.getLabelForOperator(thirdElement);

        // Get the size of the biggest children list
        int min = Math.max(getChildrenSize(firstElement), getChildrenSize(secondElement));
        int size = Math.max(min, getChildrenSize(thirdElement));
        for (int i = 0; i < size; i++) {

            PropositionEditor first = getChildForIndex(firstElement, i);
            PropositionEditor second = getChildForIndex(secondElement, i);
            PropositionEditor third = getChildForIndex(thirdElement, i);

            // add an opcode node in between each of the children.
            if (i > 0) {
                this.addOperatorTreeNode(newNode, getLabelForChild(first, coOpCode), getLabelForChild(second, cluOpCode), getLabelForChild(third, aoOpCode));
            }

            // call to build the childs node
            addTreeNode(newNode, first, second, third);
        }

    }

    private void addOperatorTreeNode(Node<CompareTreeNode, String> newNode, String coOpCode, String cluOpCode, String aoOpCode) {
        Node<CompareTreeNode, String> opNode = new Node<CompareTreeNode, String>();
        if (!coOpCode.equals(cluOpCode)) {
            opNode.setNodeType(KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        } else if (!cluOpCode.equals(aoOpCode)) {
            opNode.setNodeType(KRMSConstants.NODE_TYPE_COMPAREELEMENT);
        }
        opNode.setData(new CompareTreeNode(this.getNodeWidth(newNode), coOpCode, cluOpCode, aoOpCode));
        newNode.getChildren().add(opNode);
    }

}
