/**
 * 
 */
package org.kuali.student.rules.internal.common.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    private String identifier;
    private YieldValueFunctionType type; // intersection, subset etc.

    // List<String> factStructures;

    /**
     * @param id
     * @param yieldValueFunction
     */
    public YieldValueFunction(String identifier, YieldValueFunctionType type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * 
     */
    public YieldValueFunction() {
        this.id = null;
        this.identifier = null;
        this.type = null;
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
     * @param id
     *            the id to set
     */
    public final void setId(String id) {
        this.id = id;
    }

    /**
     * @return the identifier
     */
    public final String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public final void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the type
     */
    public final YieldValueFunctionType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public final void setType(YieldValueFunctionType type) {
        this.type = type;
    }
}
