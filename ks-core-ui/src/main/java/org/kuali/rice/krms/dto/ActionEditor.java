package org.kuali.rice.krms.dto;

import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/09/04
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionEditor implements ActionDefinitionContract, Serializable {

    private String id;
    private String name;
    private String namespace;
    private String description;
    private String typeId;
    private String ruleId;
    private Integer sequenceNumber;

    private Map<String, String> attributes;
    private Long versionNumber;

    public ActionEditor() {
        super();
    }

    public ActionEditor(ActionDefinitionContract definition){
        this.setId(definition.getId());
        this.setName(definition.getName());
        this.setNamespace(definition.getNamespace());
        this.setDescription(definition.getDescription());
        this.setTypeId(definition.getTypeId());
        this.setRuleId(definition.getRuleId());
        this.setSequenceNumber(definition.getSequenceNumber());

        this.setAttributes(definition.getAttributes());
        this.setVersionNumber(definition.getVersionNumber());
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String getTypeId() {
        return this.typeId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public String getRuleId() {
        return this.ruleId;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public Integer getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

}
