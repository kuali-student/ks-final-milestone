package org.kuali.student.rules.devgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public class BusinessRule implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;

    private String id;
    private String identifier;
    private String name;
    private String description;
    private String successMessage;
    private String failureMessage;
    private String compiledID;
    private String anchor;
    private String businessRuleType;

    public String getUniqueId() {
        return id;
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
     * @return the successMessage
     */
    public final String getSuccessMessage() {
        return successMessage;
    }

    /**
     * @param successMessage
     *            the successMessage to set
     */
    public final void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * @return the failureMessage
     */
    public final String getFailureMessage() {
        return failureMessage;
    }

    /**
     * @param failureMessage
     *            the failureMessage to set
     */
    public final void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * @return the compiledID
     */
    public final String getCompiledID() {
        return compiledID;
    }

    /**
     * @param compiledID
     *            the compiledID to set
     */
    public final void setCompiledID(String compiledID) {
        this.compiledID = compiledID;
    }

    /**
     * @return the anchor
     */
    public final String getAnchor() {
        return anchor;
    }

    /**
     * @param anchor
     *            the anchor to set
     */
    public final void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * @return the businessRuleType
     */
    public final String getBusinessRuleType() {
        return businessRuleType;
    }

    /**
     * @param businessRuleType
     *            the businessRuleType to set
     */
    public final void setBusinessRuleType(String businessRuleType) {
        this.businessRuleType = businessRuleType;
    }

    /**
     * @return the serialVersionUID
     */
    public static final long getSerialVersionUID() {
        return serialVersionUID;
    }
}