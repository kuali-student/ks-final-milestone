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
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;

import java.util.List;

/**
 * This is a helper class to build the view tree to be displayed on the manage requisites page on the ui to display
 * a readonly tree of the rule.
 *
 * @author Kuali Student Team
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

        if (rule.getPropositionEditor() != null) {

            buildPreviewTree(rule, rootNode, rule.getPropositionEditor());

            //Underline the first node in the preview.
            if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)) {
                Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
                if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)) {
                    firstNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEHEADER);
                    addNodeType(firstNode, KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
                    firstNode.setNodeLabel("<u>" + firstNode.getNodeLabel() + ":</u>");
                }
            }
        }

        return myTree;
    }

    private void buildPreviewTree(RuleEditor rule, Node<TreeNode, String> currentNode, PropositionEditor prop) {
        if (prop != null) {

            Node<TreeNode, String> newNode = new Node<TreeNode, String>();
            newNode.setNodeType(KRMSConstants.NODE_TYPE_SUBRULEELEMENT);
            addNodeType(newNode, KRMSConstants.NODE_TYPE_VIEWELEMENT);

            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                newNode.setNodeLabel(this.buildNodeLabel(prop));
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                newNode.setNodeLabel(this.buildNodeLabel(prop));
                boolean first = true;
                for (PropositionEditor child : prop.getCompoundEditors()) {
                    // add an opcode node in between each of the children.
                    if (!first) {
                        //addOpCodeNode(newNode, propositionEditor);
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        opNode.setNodeLabel(PropositionTreeUtil.getLabelForOperator(prop.getCompoundOpCode()));
                        opNode.setData(new TreeNode(prop.getCompoundOpCode()));
                        newNode.getChildren().add(opNode);
                    }
                    first = false;
                    // call to build the child nodes
                    buildPreviewTree(rule, newNode, child);
                }
            }

            TreeNode tNode = new TreeNode(newNode.getNodeLabel());
            tNode.setListItems(this.getListItems(prop));
            tNode.setKey(prop.getKey());
            newNode.setData(tNode);
            currentNode.getChildren().add(newNode);

        }
    }

    public List<Object> getListItems(PropositionEditor propositionEditor) {
        return null;
    }

    @Override
    public String getNaturalLanguageUsageKey() {
        return null;
    }
}
