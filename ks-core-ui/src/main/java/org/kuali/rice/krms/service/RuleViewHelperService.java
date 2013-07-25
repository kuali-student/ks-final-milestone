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
package org.kuali.rice.krms.service;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public interface RuleViewHelperService {

    public  void validateProposition(PropositionEditor proposition);

    public void resetDescription(PropositionEditor proposition);

    public void configurePropositionForType(PropositionEditor proposition);

    public TemplateInfo getTemplateForType(String type);

    public void refreshInitTrees(RuleEditor rule);

    public void refreshViewTree(RuleEditor rule);

    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, RuleEditor compare) throws Exception;

    public Tree<CompareTreeNode, String> buildMultiViewTree(RuleEditor coRuleEditor, RuleEditor cluRuleEditor) throws Exception;

    public Boolean compareRules(RuleEditor original);

    public PropositionEditor copyProposition(PropositionEditor proposition);

    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild);

    public void setTypeForCompoundOpCode(PropositionEditor proposition, String compoundOpCode);

    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling);

    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare);

    public Boolean compareCompoundProposition(List<PropositionEditor> original, List<PropositionEditor> compare);

    public Boolean compareTerm(List<TermParameterEditor> original, List<TermParameterEditor> compare);

}
