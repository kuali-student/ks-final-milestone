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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;

/**
 * Contains information about a business rule type 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "BusinessRuleType_T")
public class BusinessRuleType {
    @Id
    private String id;

    @Embedded
    private BusinessRuleTypeKey key;
    
    @Embedded
    private AnchorTypeKey anchorTypeKey;
    
    @OneToMany(cascade = {CascadeType.ALL})
    private List<FactStructure> facts;

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
     * @return the key
     */
    public BusinessRuleTypeKey getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(BusinessRuleTypeKey key) {
        this.key = key;
    }

    /**
     * @return the anchorTypeKey
     */
    public AnchorTypeKey getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey the anchorTypeKey to set
     */
    public void setAnchorTypeKey(AnchorTypeKey anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the facts
     */
    public List<FactStructure> getFacts() {
        return facts;
    }

    /**
     * @param facts the facts to set
     */
    public void setFacts(List<FactStructure> facts) {
        this.facts = facts;
    }
        
}
