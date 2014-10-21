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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationRuleInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "searchCriteria",
    "ruleId",
    "groupIds",
    "personIds",
    "childPopulationIds",
    "referencePopulationId",
    "sortOrderTypeKeys",
    "variesByTime",
    "supportsGetMembers",
    "meta",
    "attributes", "_futureElements" }) 
public class PopulationRuleInfo
        extends IdEntityInfo
        implements PopulationRule, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private QueryByCriteria searchCriteria;
    @XmlElement
    private String ruleId;
    @XmlElement
    private List<String> groupIds;
    @XmlElement
    private List<String> personIds;
    @XmlElement
    private List<String> childPopulationIds;
    @XmlElement
    private String referencePopulationId;
    @XmlElement
    private List<String> sortOrderTypeKeys;
    @XmlElement
    private Boolean variesByTime;
    @XmlElement
    private Boolean supportsGetMembers;
    @XmlAnyElement
    private List<Object> _futureElements;  


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
            this.searchCriteria = populationRule.getSearchCriteria(); /* fix */
            this.ruleId = populationRule.getRuleId();

            if (populationRule.getGroupIds() != null) {
                this.groupIds = new ArrayList<String>(populationRule.getGroupIds());
            }

            if (populationRule.getPersonIds() != null) {
                this.personIds = new ArrayList<String>(populationRule.getPersonIds());
            }

            if (populationRule.getChildPopulationIds() != null) {
                this.childPopulationIds = new ArrayList<String>(populationRule.getChildPopulationIds());
            }

            this.referencePopulationId = populationRule.getReferencePopulationId();

            if (populationRule.getSortOrderTypeKeys() != null) {
                this.sortOrderTypeKeys = new ArrayList<String>(populationRule.getSortOrderTypeKeys());
            }

            this.variesByTime = populationRule.getVariesByTime();
            this.supportsGetMembers = populationRule.getSupportsGetMembers();
        }
    }

    @Override
    public QueryByCriteria getSearchCriteria() {
        return this.searchCriteria;
    }

    public void setSearchCriteria(QueryByCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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
    public List<String> getChildPopulationIds() {
        if (this.childPopulationIds == null) {
            this.childPopulationIds = new ArrayList<String>();
        }

        return this.childPopulationIds;
    }

    public void setChildPopulationIds(List<String> childPopulationIds) {
        this.childPopulationIds = childPopulationIds;
    }

    @Override
    public String getReferencePopulationId() {
        return this.referencePopulationId;
    }

    public void setReferencePopulationId(String referencePopulationId) {
        this.referencePopulationId = referencePopulationId;
    }

    @Override
    public List<String> getSortOrderTypeKeys() {
        if (this.sortOrderTypeKeys == null) {
            this.sortOrderTypeKeys = new ArrayList<String>();
        }

        return this.sortOrderTypeKeys;
    }

    public void setSortOrderTypeKeys(List<String> sortOrderTypeKeys) {
        this.sortOrderTypeKeys = sortOrderTypeKeys;
    }

    @Override
    public Boolean getVariesByTime() {
        return this.variesByTime;
    }

    public void setVariesByTime(Boolean variesByTime) {
        this.variesByTime = variesByTime;
    }

    @Override
    public Boolean getSupportsGetMembers() {
        return supportsGetMembers;
    }

    public void setSupportsGetMembers(Boolean supportsGetMembers) {
        this.supportsGetMembers = supportsGetMembers;
    }

    @Override
    public String toString() {
        return "PopulationRuleInfo{" +
                "id=" + getId() +
                ", typeKey=" + getTypeKey() +
                ", state=" + getStateKey() +
                ", searchCriteria=" + searchCriteria +
                ", ruleId=" + ruleId +
                ", groupIds=" + groupIds +
                ", personIds=" + personIds +
                ", childPopulationIds=" + childPopulationIds +
                ", referencePopulationId='" + referencePopulationId + '\'' +
                ", sortOrderTypeKeys=" + sortOrderTypeKeys +
                ", variesByTime=" + variesByTime +
                ", supportsGetMembers=" + supportsGetMembers +
                ", _futureElements=" + _futureElements +
                '}';
    }
}
