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

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public class RulePreviewTreeBuilder extends AbstractTreeBuilder{

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

        buildPreviewTree(rule, rootNode, rule.getPropositionEditor());

        //Underline the first node in the preview.
        if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)) {
            Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
            if ((firstNode.getChildren() != null) && (firstNode.getChildren().size() > 0)) {
                firstNode.setNodeType(NODE_TYPE_SUBRULEHEADER);
                addNodeType(firstNode, NODE_TYPE_SUBRULEELEMENT);
                TreeNode treeNode = firstNode.getData();
                treeNode.setData("<u>" + treeNode.getData() + ":</u>");
            }
        }

        return myTree;
    }

    private void buildPreviewTree(RuleEditor rule, Node<TreeNode, String> currentNode, PropositionEditor prop) {
        if (prop != null) {

            Node<TreeNode, String> newNode = new Node<TreeNode, String>();
            newNode.setNodeLabel(null);
            newNode.setNodeType(NODE_TYPE_SUBRULEELEMENT);

            TreeNode tNode = null;
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                tNode = new TreeNode(this.buildNodeLabel(prop));
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                tNode = new TreeNode(StringEscapeUtils.escapeHtml(this.getDescription(prop)));
                boolean first = true;
                for (PropositionEditor child : prop.getCompoundEditors()) {
                    // add an opcode node in between each of the children.
                    if (!first) {
                        //addOpCodeNode(newNode, propositionEditor);
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        opNode.setNodeLabel(PropositionTreeUtil.getLabelForOperator(prop.getCompoundOpCode()));
                        opNode.setData(new TreeNode(null));
                        newNode.getChildren().add(opNode);

                    }
                    first = false;
                    // call to build the childs node
                    buildPreviewTree(rule, newNode, child);
                }
            }

            tNode.setListItems(this.getListItems(prop));
            newNode.setData(tNode);
            tNode.setKey(prop.getKey());
            currentNode.getChildren().add(newNode);

        }
    }

    @Override
    protected String buildNodeLabel(PropositionEditor prop){
        //Build the node label.
        String prefix = this.getPropositionPrefix(prop);
        return prefix + StringEscapeUtils.escapeHtml(this.getDescription(prop));
    }

    @Override
    public String getNaturalLanguageUsageKey() {
        return  KsKrmsConstants.KRMS_NL_RULE_EDIT;
    }

    public List<Object> getListItems(PropositionEditor propositionEditor) {
        return null;
    }
}
