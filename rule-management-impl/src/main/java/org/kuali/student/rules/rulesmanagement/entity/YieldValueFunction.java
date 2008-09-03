/**
 * 
 */
package org.kuali.student.rules.rulesmanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * @author zzraly
 */
@Entity
@Table(name = "YieldValueFunction_T")
public class YieldValueFunction {

    @Id
    private String id;
    
    @Embedded
    YieldValueFunctionType yieldValueFunctionType; 
    
    @OneToMany(cascade = {CascadeType.ALL})
    List<FactStructure> facts;
       
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
        return facts;
    }
    
    /**
     * @param facts the facts to set
     */
    public void setFacts(List<FactStructure> facts) {
        this.facts = facts;
    }

    
}
