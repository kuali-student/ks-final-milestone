package org.kuali.rice.krms.service;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public interface RuleViewHelperService {

    public String resetDescription(PropositionEditor proposition);

    public void configurePropositionForType(PropositionEditor proposition);

    public TemplateInfo getTemplateForType(String type);

    public void refreshInitTrees(RuleEditor rule);

    public void refreshViewTree(RuleEditor rule);

    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, String refObjectId) throws Exception;

    public Boolean compareRules(RuleEditor original, String refObjectId) throws Exception;

    public PropositionEditor copyProposition(PropositionEditor proposition);

    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild);

    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling);

    public String getParentRefOjbectId(String refObjectId) throws Exception;

    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare);

    public Boolean compareCompoundProposition(List<PropositionEditor> original, List<PropositionEditor> compare);

    public Boolean compareTerm(List<TermParameterEditor> original, List<TermParameterEditor> compare);

}
