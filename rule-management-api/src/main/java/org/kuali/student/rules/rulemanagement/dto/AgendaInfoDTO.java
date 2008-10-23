/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Maps a set of Business Rule Types to form an AgendaInfoDTO and a specif instance of Business Rules form an Agenda for this
 * AgendaInfoDTO
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AgendaInfoDTO implements Serializable {


    @XmlElement
    private String agendaTypeKey;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> agendaInfoDeterminationStructure;

    @XmlElement(name = "businessRuletype")
    @XmlElementWrapper(name = "businessRuleTypeList")
    private List<BusinessRuleTypeDTO> businessRuleTypeList;

    @XmlElement
    private String orchestration;

    /**
     * @return the agendaTypeKey
     */
    public String getAgendaTypeKey() {
        return agendaTypeKey;
    }

    /**
     * @param agendaTypeKey the agendaTypeKey to set
     */
    public void setAgendaTypeKey(String agendaType) {
        this.agendaTypeKey = agendaType;
    }

    /**
     * @return the agendaInfoDeterminationStructure
     */
    public Map<String, String> getAgendaInfoDeterminationStructure() {
        return agendaInfoDeterminationStructure;
    }

    /**
     * @param agendaInfoDeterminationStructure
     *            the agendaInfoDeterminationStructure to set
     */
    public void setAgendaInfoDeterminationStructure(Map<String, String> agendaInfoDeterminationStructure) {
        this.agendaInfoDeterminationStructure = agendaInfoDeterminationStructure;
    }

    /**
     * @return the businessRuleTypeList
     */
    public List<BusinessRuleTypeDTO> getBusinessRuleTypeList() {
        return businessRuleTypeList;
    }

    /**
     * @param businessRuleTypeList
     *            the businessRuleTypeList to set
     */
    public void setBusinessRuleTypeList(List<BusinessRuleTypeDTO> businessRuleTypeList) {
        this.businessRuleTypeList = businessRuleTypeList;
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
