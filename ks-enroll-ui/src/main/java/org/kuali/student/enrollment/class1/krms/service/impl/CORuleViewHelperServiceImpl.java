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
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.enrollment.class1.krms.tree.CORuleCompareTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.service.impl.LURuleViewHelperServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class CORuleViewHelperServiceImpl extends LURuleViewHelperServiceImpl {

    private KSRuleCompareTreeBuilder compareTreeBuilder;

    /**
     *
     * @return
     */
    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return LUPropositionEditor.class;
    }

    /**
     * Builds compare tree for the compare CO and CLU lightbox links.
     *
     * @param original
     * @param compare
     * @return
     * @throws Exception
     */
    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, RuleEditor compare) {

        //Set the original nl if not already exists.
        checkNaturalLanguageForTree(original);
        checkNaturalLanguageForTree(compare);

        Tree<CompareTreeNode, String> compareTree = this.getCompareTreeBuilder().buildTree(original, compare);

        return compareTree;
    }

    protected RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new CORuleCompareTreeBuilder();
            compareTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return compareTreeBuilder;
    }

}
