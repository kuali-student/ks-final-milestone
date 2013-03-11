package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/7/13
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaEditor implements AgendaDefinitionContract {

    private String id;
    private String name;
    private String typeId;
    private String contextId;
    private boolean active = true;
    private String firstItemId;
    private Map<String, String> attributes;
    private Long versionNumber;

    private List<AgendaItemDefinition> agendaItems;
    private List<RuleEditor> ruleEditor;


    public AgendaEditor() {
        super();
    }

    public AgendaEditor(AgendaDefinition definition) {
        this.id = definition.getId();
        this.name = definition.getName();
        this.typeId = definition.getTypeId();
        this.contextId = definition.getContextId();
        this.active = definition.isActive();
        this.firstItemId = definition.getFirstItemId();
        this.attributes = definition.getAttributes();
        this.versionNumber = definition.getVersionNumber();
    }

    public List<AgendaItemDefinition> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItemDefinition> agendaItems) {
        this.agendaItems = agendaItems;
    }

    public List<RuleEditor> getRuleEditor() {
        return ruleEditor;
    }

    public void setRuleEditor(List<RuleEditor> ruleEditor) {
        this.ruleEditor = ruleEditor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstItemId() {
        return firstItemId;
    }

    public void setFirstItemId(String firstItemId) {
        this.firstItemId = firstItemId;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }
}
