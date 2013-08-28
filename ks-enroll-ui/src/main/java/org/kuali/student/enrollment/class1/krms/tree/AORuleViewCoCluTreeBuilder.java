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
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;

/**
 * This is a helper class to build the compare tree to be displayed on the lightboxes on the ui to compare one set of
 * rules with another. Rules statements that differ is highlighted in the ui with a css class.
 *
 * This class is overridden to add AO specific headers to the tree structure and add list items specific to multicourse
 * rule statement(proposition) types.
 *
 * @author Kuali Student Team
 */
public class AORuleViewCoCluTreeBuilder extends KSRuleCompareTreeBuilder {

    @Override
    public Tree<CompareTreeNode, String> buildTree(RuleEditor firstElement, RuleEditor secondElement) {
        Tree<CompareTreeNode, String> compareTree = super.buildTree(firstElement, secondElement);

        //Set data headers on root node.
        Node<CompareTreeNode, String> node = compareTree.getRootElement();
        if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
            Node<CompareTreeNode, String> childNode = node.getChildren().get(0);

            // Set the headers on the first root child
            if (childNode.getData() != null) {
                CompareTreeNode compareTreeNode = childNode.getData();
                compareTreeNode.setFirstElement("Course Offering Rules");
                compareTreeNode.setSecondElement("Catalog Rules");
            }

        }

        return compareTree;
    }

    @Override
    protected void addTreeNode(Node<CompareTreeNode, String> currentNode, PropositionEditor firstElement, PropositionEditor secondElement) {
        if ((firstElement == null) && (secondElement == null)) {
            return;
        }

        Node<CompareTreeNode, String> newNode = new Node<CompareTreeNode, String>();
        CompareTreeNode tNode = new CompareTreeNode(this.getNodeWidth(currentNode), this.getDescription(firstElement),
                this.getDescription(secondElement), null, null, null);
        tNode.setFirstElementItems(this.getListItems(firstElement));
        tNode.setSecondElementItems(this.getListItems(secondElement));
        newNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEELEMENT);

        newNode.setData(tNode);
        currentNode.getChildren().add(newNode);

        this.addCompoundTreeNode(newNode, firstElement, secondElement);
    }

    @Override
    protected void addOperatorTreeNode(Node<CompareTreeNode, String> newNode, String firstElement, String secondElement) {
        Node<CompareTreeNode, String> opNode = new Node<CompareTreeNode, String>();
        opNode.setData(new CompareTreeNode(this.getNodeWidth(newNode), firstElement, secondElement, null, null, null));
        newNode.getChildren().add(opNode);
    }

}
