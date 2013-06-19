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

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinitionContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class AgendaEditor extends UifFormBase implements AgendaDefinitionContract, Serializable {

    private String id;
    private String name;
    private String typeId;
    private String contextId;
    private boolean active = true;
    private String firstItemId;
    private Map<String, String> attributes;
    private Long versionNumber;
    private String courseName;

    private AgendaTypeInfo agendaTypeInfo;
    private Map<String, RuleEditor> ruleEditors;
    private List<RuleEditor> deletedRules;

    private AgendaEditor parent;

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

    public Map<String, RuleEditor> getRuleEditors() {
        return ruleEditors;
    }

    public void setRuleEditors(Map<String, RuleEditor> ruleEditors) {
        this.ruleEditors = ruleEditors;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public AgendaTypeInfo getAgendaTypeInfo() {
        return agendaTypeInfo;
    }

    public void setAgendaTypeInfo(AgendaTypeInfo agendaTypeInfo) {
        this.agendaTypeInfo = agendaTypeInfo;
    }

    public AgendaEditor getParent() {
        return parent;
    }

    public void setParent(AgendaEditor parent) {
        this.parent = parent;
    }

    public boolean isDummyAgenda(){
        if(this.getId()==null){
            for(RuleEditor rule : this.getRuleEditors().values()){
                if(rule.isDummy()==false){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
