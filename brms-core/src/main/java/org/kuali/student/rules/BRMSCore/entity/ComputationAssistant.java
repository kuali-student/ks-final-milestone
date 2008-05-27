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
    ComputationMethodType computationMethodName; // intersectionConstraint, subsetConstraint etc.

    /**
     * @param yieldValueFunction
     * @param computationMethodName
     */
    public ComputationAssistant(YieldValueFunctionType yieldValueFunction, ComputationMethodType computationMethodName) {
        this.yieldValueFunction = yieldValueFunction;
        this.computationMethodName = computationMethodName;
    }

    /**
     * 
     */
    public ComputationAssistant() {
        this.id = null;
        this.yieldValueFunction = null;
        this.computationMethodName = null;
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

    /**
     * @return the computationMethodName
     */
    public final ComputationMethodType getComputationMethodName() {
        return computationMethodName;
    }

    /**
     * @param computationMethodName
     *            the computationMethodName to set
     */
    public final void setComputationMethodName(ComputationMethodType computationMethodName) {
        this.computationMethodName = computationMethodName;
    }
}
