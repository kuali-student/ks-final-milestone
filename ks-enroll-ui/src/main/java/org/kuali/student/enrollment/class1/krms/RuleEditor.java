package org.kuali.student.enrollment.class1.krms;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krms.impl.repository.ActionBo;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;
import org.kuali.rice.krms.impl.repository.ContextBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.ui.AgendaTreeLogicNode;
import org.kuali.rice.krms.impl.ui.AgendaTreeNode;
import org.kuali.rice.krms.impl.ui.AgendaTreeRuleNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleEditor extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private RuleBo rule;
    private ContextBo context;
    private String namespace;
    private String contextName;
    private String selectedAgendaItemId;
    private String cutAgendaItemId;
    private String selectedPropositionId;
    private String cutPropositionId;
    private String copyRuleName;
    private String oldContextId;
    private String ruleEditorMessage;
    private boolean addRuleInProgress = false;
    private Map<String, String> customAttributesMap = new HashMap<String, String>();
    private Map<String, String> customRuleAttributesMap = new HashMap<String, String>();
    private Map<String, String> customRuleActionAttributesMap = new HashMap<String, String>();

    public RuleEditor() {
        rule = new RuleBo();
    }

    public RuleEditor(RuleBo rule) {
        super();
        this.rule = rule;
    }

    public RuleBo getRule() {
        return rule;
    }

    public void setRule(RuleBo rule) {
        this.rule = rule;
    }

    /**
     * @return the selectedAgendaItemId
     */
    public String getSelectedAgendaItemId() {
        return this.selectedAgendaItemId;
    }

    /**
     * @param selectedAgendaItemId the selectedAgendaItemId to set
     */
    public void setSelectedAgendaItemId(String selectedAgendaItemId) {
        this.selectedAgendaItemId = selectedAgendaItemId;
    }

    /**
     * @return the cutAgendaItemId
     */
    public String getCutAgendaItemId() {
        return this.cutAgendaItemId;
    }

    /**
     * @param cutAgendaItemId the cutAgendaItemId to set
     */
    public void setCutAgendaItemId(String cutAgendaItemId) {
        this.cutAgendaItemId = cutAgendaItemId;
    }

    /**
     * @return the context
     */
    public ContextBo getContext() {
        return this.context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ContextBo context) {
        this.context = context;
    }

    public Map<String, String> getCustomAttributesMap() {
        return customAttributesMap;
    }

    public void setCustomAttributesMap(Map<String, String> customAttributesMap) {
        this.customAttributesMap = customAttributesMap;
    }

    public Map<String, String> getCustomRuleAttributesMap() {
        return customRuleAttributesMap;
    }

    public void setCustomRuleAttributesMap(Map<String, String> customRuleAttributesMap) {
        this.customRuleAttributesMap = customRuleAttributesMap;
    }

    public Map<String, String> getCustomRuleActionAttributesMap() {
        return customRuleActionAttributesMap;
    }

    public void setCustomRuleActionAttributesMap(Map<String, String> customRuleActionAttributesMap) {
        this.customRuleActionAttributesMap = customRuleActionAttributesMap;
    }

    /**
     * @param copyRuleName the rule name from which to copy
     */
    public void setCopyRuleName(String copyRuleName) {
        this.copyRuleName = copyRuleName;
    }

    /**
     * @return the rule name from which to copy
     */
    public String getCopyRuleName() {
        return copyRuleName;
    }

    /**
     * @param oldContextId the contextId that the agenda currently has (i.e. before the next context change)
     */
    public void setOldContextId(String oldContextId) {
        this.oldContextId = oldContextId;
    }

    /**
     * @return the contextId that the agenda had before the context change
     */
    public String getOldContextId() {
        return oldContextId;
    }

    /**
     * @return the selectedPropositionId
     */
    public String getSelectedPropositionId() {
        return this.selectedPropositionId;
    }

    /**
     * @param selectedPropositionId the selectedPropositionId to set
     */
    public void setSelectedPropositionId(String selectedPropositionId) {
        this.selectedPropositionId = selectedPropositionId;
    }


    /**
     * @return the cutPropositionId
     */
    public String getCutPropositionId() {
        return cutPropositionId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRuleEditorMessage() {
        return this.ruleEditorMessage;
    }

    public void setRuleEditorMessage(String message) {
        this.ruleEditorMessage = message;
    }

    public boolean isAddRuleInProgress() {
        return addRuleInProgress;
    }

    public void setAddRuleInProgress(boolean addRuleInProgress) {
        this.addRuleInProgress = addRuleInProgress;
    }

    /**
     * @param cutPropositionId the cutPropositionId to set
     */
    public void setCutPropositionId(String cutPropositionId) {
        this.cutPropositionId = cutPropositionId;
    }

    // Need to override this method since the actual persistable BO is wrapped inside dataObject.
    @Override
    public void refreshNonUpdateableReferences() {
        getPersistenceService().refreshAllNonUpdatingReferences(this.getRule());
    }
}
