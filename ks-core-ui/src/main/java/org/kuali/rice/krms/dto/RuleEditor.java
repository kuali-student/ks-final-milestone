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
package org.kuali.rice.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.rice.krms.util.AlphaIterator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class RuleEditor extends UifFormBase implements RuleDefinitionContract, Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String id;
    private String namespace;
    private String description;
    private String name;
    private String typeId;
    private String propId;
    private boolean active = true;
    private Long versionNumber;

    private List<ActionEditor> actions;
    private Map<String, String> attributes;

    private PropositionEditor proposition;

    private String copyKey;
    private String selectedKey;
    private String cutKey;
    private boolean dummy;
    private List<String> activeSelections;

    //Edit with Logic
    private String logicArea;

    // for Rule editor display
    private Tree<RuleEditorTreeNode, String> editTree;

    // for Rule Preview display
    private Tree<TreeNode, String> previewTree;
    private Tree<TreeNode, String> viewTree;
    private AlphaIterator simpleKeys;
    private AlphaIterator compoundKeys;

    // for Compare
    private Tree<CompareTreeNode, String> compareTree;

    //Rule Instruction
    private RuleTypeInfo ruleTypeInfo;
    private RuleEditor parent;

    public RuleEditor() {
        super();
    }

    public RuleEditor(String key, boolean dummy, RuleTypeInfo ruleTypeInfo) {
        this.setKey(key);
        this.setDummy(dummy);
        this.setTypeId(ruleTypeInfo.getId());
        this.setRuleTypeInfo(ruleTypeInfo);
    }

    public RuleEditor(RuleDefinitionContract definition) {
        this.id = definition.getId();
        this.namespace = definition.getNamespace();
        this.name = definition.getName();
        this.description = definition.getDescription();
        this.typeId = definition.getTypeId();
        this.propId = definition.getPropId();
        this.active = definition.isActive();
        if(definition.getProposition()!=null){
            this.proposition = createPropositionEditor(definition.getProposition());
        }
        this.versionNumber = definition.getVersionNumber();

        if(definition.getActions()!=null){
            this.actions = new ArrayList<ActionEditor>();
            for(ActionDefinitionContract action : definition.getActions()){
                this.actions.add(new ActionEditor(action));
            }
        } else {
            this.actions = null;
        }

        this.attributes = definition.getAttributes();

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropositionEditor getPropositionEditor(){
        return proposition;
    }

    public void setProposition(PropositionEditor proposition) {
        this.proposition = proposition;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<String> getActiveSelections() {
        return activeSelections;
    }

    public void setActiveSelections(List<String> activeSelections) {
        this.activeSelections = activeSelections;
    }

    public AlphaIterator getSimpleKeys() {
        if (simpleKeys == null){
            simpleKeys = new AlphaIterator(StringUtils.EMPTY);
        }
        return simpleKeys;
    }

    public void setSimpleKeys(AlphaIterator simpleKeys) {
        this.simpleKeys = simpleKeys;
    }

    public AlphaIterator getCompoundKeys() {
        if (compoundKeys == null){
            compoundKeys = new AlphaIterator(PropositionType.COMPOUND.getCode() + "-");
        }
        return compoundKeys;
    }

    public void setCompoundKeys(AlphaIterator compoundKeys) {
        this.compoundKeys = compoundKeys;
    }

    /**
     * @return the selectedKey
     */
    public String getSelectedKey() {
        return this.selectedKey;
    }

    /**
     * @param selectedKey the selectedKey to set
     */
    public void setSelectedKey(String selectedKey) {
        this.selectedKey = selectedKey;
    }

    /**
     * @return the cutKey
     */
    public String getCutKey() {
        return cutKey;
    }

    public void setCutKey(String cutKey) {
        this.cutKey = cutKey;
    }

    public boolean isDummy() {
        return dummy;
    }

    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    /**
     * @return the copyKey
     */
    public String getCopyKey() {
        return copyKey;
    }

    /**
     * @param copyKey the copyKey to set
     */
    public void setCopyKey(String copyKey) {
        this.copyKey = copyKey;
    }

    public String getLogicArea() {
        return logicArea;
    }

    public void setLogicArea(String logicArea) {
        this.logicArea = logicArea;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public String getTypeId() {
        return this.typeId;
    }

    @Override
    public String getPropId() {
        return this.propId;
    }

    @Override
    public PropositionDefinitionContract getProposition() {
        return proposition;
    }

    @Override
    public List<? extends ActionDefinitionContract> getActions() {
        if(this.actions==null){
            this.actions = new ArrayList<ActionEditor>();
        }
        return actions;
    }

    @Override
    public Map<String, String> getAttributes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    public Tree<RuleEditorTreeNode, String> getEditTree() {
        return editTree;
    }

    public void setEditTree(Tree<RuleEditorTreeNode, String> editTree) {
        this.editTree = editTree;
    }

    public Tree<TreeNode, String> getPreviewTree() {
        return previewTree;
    }

    public void setPreviewTree(Tree<TreeNode, String> previewTree) {
        this.previewTree = previewTree;
    }


    public Tree<TreeNode, String> getViewTree() {
        return viewTree;
    }

    public void setViewTree(Tree<TreeNode, String> viewTree) {
        this.viewTree = viewTree;
    }


    public Tree<CompareTreeNode, String> getCompareTree() {
        return compareTree;
    }

    public void setCompareTree(Tree<CompareTreeNode, String> compareTree) {
        this.compareTree = compareTree;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public Long getVersionNumber() {
        return versionNumber;
    }

    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new PropositionEditor(definition);
    }

    public RuleTypeInfo getRuleTypeInfo() {
        return ruleTypeInfo;
    }

    public void setRuleTypeInfo(RuleTypeInfo ruleTypeInfo) {
        this.ruleTypeInfo = ruleTypeInfo;
    }

    public RuleEditor getParent() {
        return parent;
    }

    public void setParent(RuleEditor parent) {
        this.parent = parent;
    }

    public void reset(){
        this.getEditTree().setRootElement(null);
        this.setPropId(null);
        this.setProposition(null);
        this.setSimpleKeys(null);
        this.setCompoundKeys(null);
        this.setSelectedKey(StringUtils.EMPTY);
    }
}
