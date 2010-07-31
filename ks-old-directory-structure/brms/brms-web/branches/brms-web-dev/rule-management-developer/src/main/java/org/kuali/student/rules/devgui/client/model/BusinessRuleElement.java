/**
 * 
 */
package org.kuali.student.rules.devgui.client.model;

import java.io.Serializable;

/**
 * @author zzraly
 */
public class BusinessRuleElement implements Serializable {
    private static final long serialVersionUID = 123123142351353L;

    private String name;
    private String description;
    private String operation;
    private Integer ordinalPosition;
    private BusinessRuleProposition ruleProposition;

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the operation
     */
    public final String getOperation() {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public final void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the ordinalPosition
     */
    public final Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    /**
     * @param ordinalPosition
     *            the ordinalPosition to set
     */
    public final void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    /**
     * @return the ruleProposition
     */
    public final BusinessRuleProposition getRuleProposition() {
        return ruleProposition;
    }

    /**
     * @param ruleProposition
     *            the ruleProposition to set
     */
    public final void setRuleProposition(BusinessRuleProposition ruleProposition) {
        this.ruleProposition = ruleProposition;
    }

}
