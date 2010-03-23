package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSBRMS_BRT_FACT_TYPE_KEY")
public class BusinessRuleTypeFactTypeKey {
    @Id
    @Column(name = "ID")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "BRT_ID")
    private BusinessRuleType businessRuleType;
    
    @Column(name = "FACT_TYPE_KEY")
    private String factTypeKey;
    
    @PrePersist
    public  void prePersist() {
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
     * @return the businessRuleType
     */
    public BusinessRuleType getBusinessRuleType() {
        return businessRuleType;
    }

    /**
     * @param businessRuleType the businessRuleType to set
     */
    public void setBusinessRuleType(BusinessRuleType businessRuleType) {
        this.businessRuleType = businessRuleType;
    }

    /**
     * @return the factTypeKey
     */
    public String getFactTypeKey() {
        return factTypeKey;
    }

    /**
     * @param factTypeKey the factTypeKey to set
     */
    public void setFactTypeKey(String factTypeKey) {
        this.factTypeKey = factTypeKey;
    }    
}
