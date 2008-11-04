/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.AgendaType;

/**
 * Maps a set of Business Rule Types to form an AgendaInfo and a specif instance of Business Rules form an Agenda for this
 * AgendaInfo
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "AgendaInfo_T")
@NamedQueries({@NamedQuery(name = "AgendaInfo.findBusinessRuleTypes", query = "SELECT a.businessRuleTypes FROM AgendaInfo a WHERE a.type = :agendaType "), 
              @NamedQuery(name = "AgendaInfo.findUniqueAgendaTypes", query = "SELECT DISTINCT a.type FROM AgendaInfo a ORDER BY a.type ASC")})
public class AgendaInfo {
    @Id
    private String id;

    private String type;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "agendaInfo")
    private List<AgendaInfoDeterminationStructure> agendaInfoDeterminationStructureList;

    //TODO: Change this to @OnetoMany relation after they fix the bug HHH-3410
    @ManyToMany(fetch = FetchType.EAGER)
    private List<BusinessRuleType> businessRuleTypes;

    private String orchestration;
    
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the agendaInfoDeterminationStructureList
     */
    public List<AgendaInfoDeterminationStructure> getAgendaInfoDeterminationStructureList() {
        return agendaInfoDeterminationStructureList;
    }

    /**
     * @param agendaInfoDeterminationStructureList
     *            the agendaInfoDeterminationStructureList to set
     */
    public void setAgendaInfoDeterminationStructureList(List<AgendaInfoDeterminationStructure> agendaInfoDeterminationStructureList) {
        this.agendaInfoDeterminationStructureList = agendaInfoDeterminationStructureList;
    }

    /**
     * @return the businessRuleTypes
     */
    public List<BusinessRuleType> getBusinessRuleTypes() {
        return businessRuleTypes;
    }

    /**
     * @param businessRuleTypes
     *            the businessRuleTypes to set
     */
    public void setBusinessRuleTypes(List<BusinessRuleType> businessRuleTypes) {
        this.businessRuleTypes = businessRuleTypes;
    }

    /**
     * @return the orchestration
     */
    public String getOrchestration() {
        return orchestration;
    }

    /**
     * @param orchestration
     *            the orchestration to set
     */
    public void setOrchestration(String orchestration) {
        this.orchestration = orchestration;
    }
}
