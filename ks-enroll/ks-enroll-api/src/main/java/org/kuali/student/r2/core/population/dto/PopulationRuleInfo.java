/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.population.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationRuleInfo", propOrder = { "id", "typeKey", 
                "stateKey", "name", "descr", "criteria", "statementIds", 
                "groupIds", "personIds", "populationIds", "meta", "attributes",
		        "_futureElements" })

public class PopulationRuleInfo extends IdEntityInfo 
    implements PopulationRule, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private QueryByCriteria criteria;

    @XmlElement
    private List<String> statementIds;

    @XmlElement
    private List<String> groupIds;

    @XmlElement
    private List<String> personIds;

    @XmlElement
    private List<String> populationIds;

    @XmlAnyElement
    private List<Element> _futureElements;
    

    /**
     * Constructs a new PopulationRuleInfo.
     */
    public PopulationRuleInfo() {
    }

    /**
     * Constructs a new PopulationRuleInfo from another
     * PopulationRule.
     * 
     * @param populationRule the PopulationRule to copy
     */
    public PopulationRuleInfo(PopulationRule populationRule) {
        super(populationRule);

        if (populationRule != null) {
            this.criteria = populationRule.getSearchCriteria(); /* fix */
            if (populationRule.getStatementIds() != null) {
                this.statementIds = new ArrayList<String>(populationRule.getStatementIds());
            }
            
            if (populationRule.getGroupIds() != null) {
                this.groupIds = new ArrayList<String>(populationRule.getGroupIds());
            }
            
            if (populationRule.getPersonIds() != null) {
                this.personIds = new ArrayList<String>(populationRule.getPersonIds());
            }
            
            if (populationRule.getPopulationIds() != null) {
                this.populationIds = new ArrayList<String>(populationRule.getPopulationIds());
            }
        }
    }

    @Override
    public QueryByCriteria getSearchCriteria() {        
        return this.criteria;
    }

    public void setSearchCriteria(QueryByCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public List<String> getStatementIds() {
        if (this.statementIds == null) {
            this.statementIds = new ArrayList<String>();
        }

        return this.statementIds;
    }

    public void setStatementIds(List<String> statementIds) {
        this.statementIds = statementIds;
    }

    @Override
    public List<String> getGroupIds() {
        if (this.groupIds == null) {
            this.groupIds = new ArrayList<String>();
        }

        return this.groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public List<String> getPersonIds() {
        if (this.personIds == null) {
            this.personIds = new ArrayList<String>();
        }

        return this.personIds;
    }

    public void setPersonIds(List<String> personIds) {
        this.personIds = personIds;
    }

    @Override
    public List<String> getPopulationIds() {
        if (this.populationIds == null) {
            this.populationIds = new ArrayList<String>();
        }

        return this.populationIds;
    }

    public void setPopulationIds(List<String> populationIds) {
        this.populationIds = populationIds;
    }
}
