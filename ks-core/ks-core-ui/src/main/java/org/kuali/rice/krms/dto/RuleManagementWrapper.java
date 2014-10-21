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
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.common.util.DTOWrapperBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class RuleManagementWrapper extends DTOWrapperBase implements Serializable, RuleManager {

    private String refObjectId;
    private String viewId;
    private String namespace;
    private String refDiscriminatorType;

    private List<AgendaEditor> agendas;
    private RuleEditor ruleEditor;
    private AgendaEditor agendaEditor;
    private boolean hasOptimisticLockingError;

    private Tree<CompareTreeNode, String> compareTree;

    private String compareLightBoxHeader;

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public List<AgendaEditor> getAgendas() {
        if(agendas==null){
            agendas = new ArrayList<AgendaEditor>();
        }
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

    public AgendaEditor getAgendaEditor() {
        return agendaEditor;
    }

    public void setAgendaEditor(AgendaEditor agendaEditor) {
        this.agendaEditor = agendaEditor;
    }

    public Tree<RuleEditorTreeNode, String> getEditTree() {
        if(this.getRuleEditor() == null){
            return null;
        }
        return this.getRuleEditor().getEditTree();
    }

    public Tree<TreeNode, String> getPreviewTree() {
        if(this.getRuleEditor() == null){
            return null;
        }
        return this.getRuleEditor().getPreviewTree();
    }

    public Tree<TreeNode, String> getViewTree() {
        if(this.getRuleEditor() == null){
            return null;
        }
        return this.getRuleEditor().getViewTree();
    }

    public void setActiveSelections(List<String> activeSelections) {
        if (this.getRuleEditor() != null){
            this.getRuleEditor().setActiveSelections(activeSelections);
        }
    }

    public List<String> getActiveSelections(){
        if(this.getRuleEditor() == null){
            return null;
        }
        return this.getRuleEditor().getActiveSelections();
    }

    public String getSelectedKey() {
        if(this.getRuleEditor() == null) {
            return StringUtils.EMPTY;
        }
        return this.getRuleEditor().getSelectedKey();
    }

    public void setSelectedKey(String selectedKey) {
        if (this.getRuleEditor() != null){
            this.getRuleEditor().setSelectedKey(selectedKey);
        }
    }

    public String getCutKey() {
        if(this.getRuleEditor() == null) {
            return StringUtils.EMPTY;
        }
        return this.getRuleEditor().getCutKey();
    }

    public void setCutKey(String cutKey) {
        if (this.getRuleEditor() != null){
            this.getRuleEditor().setCutKey(cutKey);
        }
    }

    public String getCopyKey() {
        if(this.getRuleEditor() == null) {
            return StringUtils.EMPTY;
        }
        return this.getRuleEditor().getCopyKey();
    }

    public void setCopyKey(String copyKey) {
        if (this.getRuleEditor() != null){
            this.getRuleEditor().setCopyKey(copyKey);
        }
    }

    public String getLogicArea() {
        if(this.getRuleEditor() == null) {
            return StringUtils.EMPTY;
        }
        return this.getRuleEditor().getLogicArea();
    }

    public void setLogicArea(String logicArea) {
        if (this.getRuleEditor() != null){
             this.getRuleEditor().setLogicArea(logicArea);
        }
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRefDiscriminatorType() {
        return refDiscriminatorType;
    }

    public void setRefDiscriminatorType(String refDiscriminatorType) {
        this.refDiscriminatorType = refDiscriminatorType;
    }

    public String getCompareLightBoxHeader(){
        return compareLightBoxHeader;
    }

    public void setCompareLightBoxHeader( String compareLightBoxHeader){
        this.compareLightBoxHeader = compareLightBoxHeader;
    }

    public boolean hasOptimisticLockingError() {
        return hasOptimisticLockingError;
    }

    public void setHasOptimisticLockingError(boolean hasOptimisticLockingError) {
        this.hasOptimisticLockingError = hasOptimisticLockingError;
    }
}
