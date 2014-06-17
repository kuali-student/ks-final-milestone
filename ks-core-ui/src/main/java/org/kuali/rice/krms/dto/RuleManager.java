package org.kuali.rice.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.tree.node.TreeNode;

import java.util.List;

/**
 * Created by SW Genis on 2014/06/17.
 */
public interface RuleManager {

    public String getRefObjectId();

    public void setRefObjectId(String refObjectId);

    public String getViewId();

    public void setViewId(String viewId);

    public List<AgendaEditor> getAgendas();

    public void setAgendas(List<AgendaEditor> agendas);

    public Tree<CompareTreeNode, String> getCompareTree();

    public void setCompareTree(Tree<CompareTreeNode, String> compareTree);

    public RuleEditor getRuleEditor();

    public void setRuleEditor(RuleEditor ruleEditor);

    public AgendaEditor getAgendaEditor();

    public void setAgendaEditor(AgendaEditor agendaEditor);

    public Tree<RuleEditorTreeNode, String> getEditTree();

    public Tree<TreeNode, String> getPreviewTree();

    public Tree<TreeNode, String> getViewTree();

    public void setActiveSelections(List<String> activeSelections);

    public List<String> getActiveSelections();

    public String getSelectedKey();

    public void setSelectedKey(String selectedKey);

    public String getCutKey();

    public void setCutKey(String cutKey);

    public String getCopyKey();

    public void setCopyKey(String copyKey);

    public String getLogicArea();

    public void setLogicArea(String logicArea);

    public String getNamespace();

    public void setNamespace(String namespace);

    public String getRefDiscriminatorType();

    public void setRefDiscriminatorType(String refDiscriminatorType);

    public String getCompareLightBoxHeader();

    public void setCompareLightBoxHeader( String compareLightBoxHeader);

    public boolean hasOptimisticLockingError();

    public void setHasOptimisticLockingError(boolean hasOptimisticLockingError);

}
