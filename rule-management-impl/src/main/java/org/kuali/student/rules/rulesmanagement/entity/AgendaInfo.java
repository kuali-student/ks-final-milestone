/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.rulesmanagement.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDeterminationStructureDTO;

/**
 * Maps a set of Business Rule Types to form an AgendaInfo and a specif instance of Business Rules form 
 * an Agenda for this AgendaInfo
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "AgendaInfo_T")
public class AgendaInfo {
    @Id
    private String id;

    @Embedded
    private AgendaType type;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy="agendaInfo")
    private List<AgendaInfoDeterminationStructure> agendaInfoDeterminationStructureList;
    
    @OneToMany(cascade = {CascadeType.ALL})
    private List<BusinessRuleType> businessRuleTypes;
    
    private String orchestration;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public AgendaType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(AgendaType type) {
        this.type = type;
    }

    /**
     * @return the agendaInfoDeterminationStructureList
     */
    public List<AgendaInfoDeterminationStructure> getAgendaInfoDeterminationStructureList() {
        return agendaInfoDeterminationStructureList;
    }

    /**
     * @param agendaInfoDeterminationStructureList the agendaInfoDeterminationStructureList to set
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
     * @param businessRuleTypes the businessRuleTypes to set
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
     * @param orchestration the orchestration to set
     */
    public void setOrchestration(String orchestration) {
        this.orchestration = orchestration;
    }    
}
