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
package org.kuali.student.rules.rulemanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * Contains information about a business rule type 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "BusinessRuleType_T")
@NamedQueries({@NamedQuery(name = "BusinessRuleType.findByKeyAndAnchorType", query = "SELECT c FROM BusinessRuleType c WHERE c.businessRuleTypeKey = :businessRuleTypeKey AND c.anchorTypeKey = :anchorTypeKey"),
               @NamedQuery(name = "BusinessRuleType.findBusinessRuleTypes", query = "SELECT c.businessRuleTypeKey FROM BusinessRuleType c"),
               @NamedQuery(name = "BusinessRuleType.findUniqueAnchorTypes", query = "SELECT DISTINCT c.anchorTypeKey FROM BusinessRuleType c order by c.anchorTypeKey ASC")})
public class BusinessRuleType {
    @Id
    private String id;
    
    private String businessRuleTypeKey;
    
    private String anchorTypeKey;

    private ArrayList<String> factTypeKeyList;

    private String description;
    
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
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the businessRuleTypeKey
     */
    public String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey the businessRuleTypeKey to set
     */
    public void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

    /**
     * @return the anchorTypeKey
     */
    public String getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey the anchorTypeKey to set
     */
    public void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the factTypeKeyList
     */
    public ArrayList<String> getFactTypeKeyList() {
        return factTypeKeyList;
    }

    /**
     * @param factTypeKeyList the factTypeKeyList to set
     */
    public void setFactTypeKeyList(ArrayList<String> factTypeKeyList) {
        this.factTypeKeyList = factTypeKeyList;
    }
}
