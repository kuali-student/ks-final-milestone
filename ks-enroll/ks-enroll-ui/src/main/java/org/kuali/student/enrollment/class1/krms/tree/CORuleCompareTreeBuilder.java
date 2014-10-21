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
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class to build the compare tree to be displayed on the lightboxes on the ui to compare one set of
 * rules with another. Rules statements that differ is highlighted in the ui with a css class.
 *
 * This class is overridden to add CO specific headers to the tree structure and add list items specific to multicourse
 * rule statement(proposition) types.
 *
 * @author Kuali Student Team
 */
public class CORuleCompareTreeBuilder extends KSRuleCompareTreeBuilder {

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
                compareTreeNode.setFirstElement("Catalog Rules");
                compareTreeNode.setSecondElement("Course Offering Rules");
            }

        }

        return compareTree;
    }

    @Override
    public List<String> getListItems(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof LUPropositionEditor) {
            LUPropositionEditor enrolProp = (LUPropositionEditor) propositionEditor;
            List<String> listItems = new ArrayList<String>();
            if (enrolProp.getCourseSet() != null) {
                if (enrolProp.getCourseSet().getClus() != null) {
                    for (CluInformation clu : enrolProp.getCourseSet().getClus()) {
                        String description = clu.getCode() + " " + clu.getTitle() + " " + clu.getCredits();
                        listItems.add(description);
                    }
                }
            }
            return listItems;
        }
        return null;
    }

    public String getNaturalLanguageUsageKey(){
        return KSKRMSServiceConstants.KRMS_NL_RULE_EDIT;
    }

}
