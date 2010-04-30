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

/**
 * 
 */
package org.kuali.student.brms.rulemanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.common.util.UUIDHelper;

/**
 * @author zzraly
 */
@Entity
@Table(name = "KSBRMS_YVF")
public class YieldValueFunction {

    @Id
    @Column(name="ID")
    private String id;
    
    @Column(name="YVF_TYPE")
    @Enumerated(EnumType.STRING)
    YieldValueFunctionType yieldValueFunctionType; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yieldValueFunction")
    List<FactStructure> facts = new ArrayList<FactStructure>();
       
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
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return the yieldValueFunctionType
     */
    public YieldValueFunctionType getYieldValueFunctionType() {
        return yieldValueFunctionType;
    }
    
    /**
     * @param yieldValueFunctionType the yieldValueFunctionType to set
     */
    public void setYieldValueFunctionType(YieldValueFunctionType yieldValueFunctionType) {
        this.yieldValueFunctionType = yieldValueFunctionType;
    }
    
    /**
     * @return the facts
     */
    public List<FactStructure> getFacts() {
        if(null == facts) {
            return new ArrayList<FactStructure>();
        }
        return facts;
    }
    
    /**
     * @param facts the facts to set
     */
    public void setFacts(List<FactStructure> facts) {
        this.facts = facts;
    }    
}
