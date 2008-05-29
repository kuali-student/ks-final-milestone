/**
 * 
 */
package org.kuali.student.rules.BRMSCore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * @author zzraly
 */
@Entity
@Table(name = "ComputationAssistant_T")
public class ComputationAssistant {

    @Id
    private String id;
    YieldValueFunctionType yieldValueFunction; // intersection, subset etc.

    /**
     * @param id
     * @param yieldValueFunction
     */
    public ComputationAssistant(YieldValueFunctionType yieldValueFunction) {
        this.yieldValueFunction = yieldValueFunction;
    }

    /**
     * 
     */
    public ComputationAssistant() {
        this.id = null;
        this.yieldValueFunction = null;
    }

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
    public final String getId() {
        return id;
    }

    /**
     * @return the yieldValueFunction
     */
    public final YieldValueFunctionType getYieldValueFunction() {
        return yieldValueFunction;
    }

    /**
     * @param yieldValueFunction
     *            the yieldValueFunction to set
     */
    public final void setYieldValueFunction(YieldValueFunctionType yieldValueFunction) {
        this.yieldValueFunction = yieldValueFunction;
    }
}
