package org.kuali.rice.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.tree.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/03
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleManagementWrapper extends UifFormBase {

    private String refObjectId;
    private String selectedRuleId;
    private String namePrefix;
    private String namespace;

    private List<AgendaEditor> agendas;
    private RuleEditor ruleEditor;

    private List<RuleEditor> deletedRules;

    private Tree<CompareTreeNode, String> compareTree;

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getSelectedRuleId() {
        return selectedRuleId;
    }

    public void setSelectedRuleId(String selectedRuleId) {
        this.selectedRuleId = selectedRuleId;
    }

    public List<AgendaEditor> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaEditor> agendas) {
        this.agendas = agendas;
    }

    public Tree<CompareTreeNode, String> getCompareTree() {
        return compareTree;
    }

    public void setCompareTree(Tree<CompareTreeNode, String> compareTree) {
        this.compareTree = compareTree;
    }

    public RuleEditor getRuleEditor() {
        return ruleEditor;
    }

    public void setRuleEditor(RuleEditor ruleEditor) {
        this.ruleEditor = ruleEditor;
    }

    public Tree<RuleEditorTreeNode, String> getEditTree() {
        return this.getRuleEditor().getEditTree();
    }

    public Tree<TreeNode, String> getPreviewTree() {
        return this.getRuleEditor().getPreviewTree();
    }

    public Tree<TreeNode, String> getViewTree() {
       return this.getRuleEditor().getViewTree();
    }

    public void setActiveSelections(List<String> activeSelections) {
        this.getRuleEditor().setActiveSelections(activeSelections);
    }

    public List<String> getActiveSelections(){
        return this.getRuleEditor().getActiveSelections();
    }

    public String getSelectedKey() {
        return this.getRuleEditor().getSelectedKey();
    }

    public void setSelectedKey(String selectedKey) {
        this.getRuleEditor().setSelectedKey(selectedKey);
    }

    public String getCutKey() {
        return this.getRuleEditor().getCutKey();
    }

    public void setCutKey(String cutKey) {
        this.getRuleEditor().setCutKey(cutKey);
    }

    public String getCopyKey() {
        return this.getRuleEditor().getCopyKey();
    }

    public void setCopyKey(String copyKey) {
        this.getRuleEditor().setCopyKey(copyKey);
    }

    public String getLogicArea() {
        return this.getRuleEditor().getLogicArea();
    }

    public void setLogicArea(String logicArea) {
        this.getRuleEditor().setLogicArea(logicArea);
    }

    public String getSelectedTab() {
        return this.getRuleEditor().getSelectedTab();
    }

    public void setSelectedTab(String selectedTab) {
        this.getRuleEditor().setSelectedTab(selectedTab);
    }

    public List<RuleEditor> getDeletedRules() {
        if(this.deletedRules == null) {
            return deletedRules = new ArrayList<RuleEditor>();
        }
        return deletedRules;
    }

    public void setDeletedRules(List<RuleEditor> deletedRules) {
        this.deletedRules = deletedRules;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
