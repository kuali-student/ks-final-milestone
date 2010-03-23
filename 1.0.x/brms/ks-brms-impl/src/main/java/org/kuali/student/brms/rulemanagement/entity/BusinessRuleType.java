/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.brms.rulemanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.brms.internal.common.entity.AnchorTypeKey;
import org.kuali.student.brms.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.common.util.UUIDHelper;

/**
 * Contains information about a business rule type
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "KSBRMS_BUS_RULE_TYPE", uniqueConstraints = {@UniqueConstraint(columnNames = {"BRT_TYPE_KEY", "ANCHOR_TYPE_KEY"})})
@NamedQueries({@NamedQuery(name = "BusinessRuleType.findByKeyAndAnchorType", query = "SELECT c FROM BusinessRuleType c WHERE c.businessRuleTypeKey = :businessRuleTypeKey AND c.anchorTypeKey = :anchorTypeKey"), 
	@NamedQuery(name = "BusinessRuleType.findBusinessRuleTypes", query = "SELECT c.businessRuleTypeKey FROM BusinessRuleType c"), 
	@NamedQuery(name = "BusinessRuleType.findUniqueAnchorTypes", query = "SELECT DISTINCT c.anchorTypeKey FROM BusinessRuleType c order by c.anchorTypeKey ASC")})
public class BusinessRuleType {
    @Id
    @Column(name="ID")
    private String id;

    @Column(name="BRT_TYPE_KEY")
    private BusinessRuleTypeKey businessRuleTypeKey;

    @Column(name="ANCHOR_TYPE_KEY")
    private AnchorTypeKey anchorTypeKey;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="businessRuleType")
    private List<BusinessRuleTypeFactTypeKey> factTypeKeyList;

    @Column(name="DESCR")
    private String desc;

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
     * @return the businessRuleTypeKey
     */
    public BusinessRuleTypeKey getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey
     *            the businessRuleTypeKey to set
     */
    public void setBusinessRuleTypeKey(BusinessRuleTypeKey businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

    /**
     * @return the anchorTypeKey
     */
    public AnchorTypeKey getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey
     *            the anchorTypeKey to set
     */
    public void setAnchorTypeKey(AnchorTypeKey anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDesc(String description) {
        this.desc = description;
    }

    /**
     * @return the factTypeKeyList
     */
    public List<BusinessRuleTypeFactTypeKey> getFactTypeKeyList() {
        if(null == factTypeKeyList) {
            return new ArrayList<BusinessRuleTypeFactTypeKey>();
        }
        
        return factTypeKeyList;
    }

    /**
     * @param factTypeKeyList
     *            the factTypeKeyList to set
     */
    public void setFactTypeKeyList(List<BusinessRuleTypeFactTypeKey> factTypeKeyList) {
        this.factTypeKeyList = factTypeKeyList;
    }
}
