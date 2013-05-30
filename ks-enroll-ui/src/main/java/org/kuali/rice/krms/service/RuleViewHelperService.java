package org.kuali.rice.krms.service;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleViewHelperService {

    public String resetDescription(PropositionEditor proposition);

    public void configurePropositionForType(PropositionEditor proposition);

    public TemplateInfo getTemplateForType(String type);

    public boolean validateProposition(PropositionEditor proposition, String namespace);

    public void refreshInitTrees(RuleEditor rule);

    public void refreshViewTree(RuleEditor rule);

    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original, String compareToRefObjectId) throws Exception;

    public Boolean compareRules(RuleEditor original, String compareToRefObjectId) throws Exception;

    public PropositionEditor copyProposition(PropositionEditor proposition);

    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild);

    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling);

}
