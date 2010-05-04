/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.rulemanagement.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.kuali.student.common.util.UUIDHelper;

/**
 * Maps a set of Business Rule Types to form an Agenda and 
 * a specific instance of Business Rules to form an Agenda for this Agenda
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "KSBRMS_AGENDA")
@NamedQueries({@NamedQuery(name = "Agenda.findBusinessRuleTypes", query = "SELECT a.businessRuleTypeInfoList FROM Agenda a WHERE a.type = :agendaType "),
              @NamedQuery(name = "Agenda.findByAgendaType", query = "SELECT a FROM Agenda a WHERE a.type = :agendaType "),
              @NamedQuery(name = "Agenda.findUniqueAgendaTypes", query = "SELECT DISTINCT a.type FROM Agenda a ORDER BY a.type ASC")})
public class Agenda {
    @Id
    @Column(name="ID")
    private String id;

    @Column(name="TYPE")
    private String type;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "agenda")
    private List<AgendaDetermination> agendaDeterminationList;

    //TODO: Change this to @OnetoMany relation after they fix the bug HHH-3410
    @ManyToMany(fetch = FetchType.EAGER)
    // Added @JoinTable so table name is less then 31 characters for Oracle (Oracle table name limit is 30 characters)
    @JoinTable(name="KSBRMS_AGENDA_BRULETYPE", joinColumns={@JoinColumn(name="AGENDA_ID")}, inverseJoinColumns={@JoinColumn(name="BRT_ID")})    
    private List<BusinessRuleType> businessRuleTypeInfoList;

    @Column(name="AGENDA_ORCH")
    private String agendaOrchestration;
    
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
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
     * @return the agendaDeterminationInfoList
     */
    public List<AgendaDetermination> getAgendaDeterminationList() {
        if(null == agendaDeterminationList) {
            return new ArrayList<AgendaDetermination>();
        }
        
        return agendaDeterminationList;
    }

    /**
     * @param agendaDeterminationList the agendaDeterminationInfoList to set
     */
    public void setAgendaDeterminationList(List<AgendaDetermination> agendaDeterminationList) {
        this.agendaDeterminationList = agendaDeterminationList;
    }

    /**
     * @return the businessRuleTypeInfoList
     */
    public List<BusinessRuleType> getBusinessRuleTypeInfoList() {
        return businessRuleTypeInfoList;
    }

    /**
     * @param businessRuleTypeInfoList the businessRuleTypeInfoList to set
     */
    public void setBusinessRuleTypeInfoList(List<BusinessRuleType> businessRuleTypeInfoList) {
        this.businessRuleTypeInfoList = businessRuleTypeInfoList;
    }

    /**
     * @return the agendaOrchestration
     */
    public String getAgendaOrchestration() {
        return agendaOrchestration;
    }

    /**
     * @param agendaOrchestration the agendaOrchestration to set
     */
    public void setAgendaOrchestration(String agendaOrchestration) {
        this.agendaOrchestration = agendaOrchestration;
    }

    /**
     * 
     * This method converts the flattened out List of agendaDeterminationInfoList into a map of
     * Info Key and Info Value
     * 
     * @return
     */
    public Map<String, String> getAgendaDeterminationInfoMap() {
        Map<String, String> result = new HashMap<String, String>();
     
        for(AgendaDetermination agendaInfoElement : agendaDeterminationList) {
            result.put(agendaInfoElement.getStructureKey(), agendaInfoElement.getValue());
        }
           
        return result;
    }
}
