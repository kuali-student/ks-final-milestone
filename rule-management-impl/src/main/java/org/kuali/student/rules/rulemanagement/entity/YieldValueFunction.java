/**
 * 
 */
package org.kuali.student.rules.rulemanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;

/**
 * @author zzraly
 */
@Entity
@Table(name = "YieldValueFunction_T")
public class YieldValueFunction {

    @Id
    private String id;
    
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
        return facts;
    }
    
    /**
     * @param facts the facts to set
     */
    public void setFacts(List<FactStructure> facts) {
        this.facts = facts;
    }    
}
