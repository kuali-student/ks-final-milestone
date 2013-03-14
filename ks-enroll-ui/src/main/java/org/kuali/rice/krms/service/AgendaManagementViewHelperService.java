package org.kuali.rice.krms.service;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;

import java.util.List;

public interface AgendaManagementViewHelperService {

    public List<AgendaEditor> getAgendaEditors();

    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original);

}
