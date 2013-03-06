package org.kuali.rice.krms.service;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleViewHelperService extends TemplateRegistry {

    public String getTermSpecIdForType(String type);

    public String getDescriptionForTypeId(String typeId);

    public boolean validateProposition(PropositionEditor proposition, String namespace);

    public void refreshInitTrees(RuleEditor rule);

    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original);

    public void setLogicSection(RuleEditor ruleEditor);
}
