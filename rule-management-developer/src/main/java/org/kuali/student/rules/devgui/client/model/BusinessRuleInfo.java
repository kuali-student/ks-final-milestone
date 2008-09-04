package org.kuali.student.rules.devgui.client.model;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public class BusinessRuleInfo implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;

    private String businessRuleId;
    private String businessRuleTypeKey;
    private String anchorValue;
    private String anchorTypeKey;
    // TODO: facStructureInfoList
    private String successMessage;
    private String failureMessage;
    private String name;
    private String description;
    private String createTime;
    private String createUserId;
    private String updateTime;
    private String updateUserId;
    private String effectiveStartTime;
    private String effectiveEndTime;
    private String status;
    private List<BusinessRuleElement> ruleElementList;

    // TODO: outputStateSet and displayOutputStructure

    public BusinessRuleElement getBusinessRuleElement(Integer ordinalPosition) {
        for (BusinessRuleElement elem : ruleElementList) {
            if ((elem.getOperation().equals("PROPOSITION")) && (elem.getOrdinalPosition().equals(ordinalPosition)))
                return elem;
        }
        System.out.println("NULL");
        return null;
    }

    public String getUniqueId() {
        return businessRuleId;
    }

    /**
     * @return the businessRuleId
     */
    public final String getId() {
        return businessRuleId;
    }

    /**
     * @param businessRuleId
     *            the businessRuleId to set
     */
    public final void setId(String id) {
        this.businessRuleId = id;
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
     * @return the serialVersionUID
     */
    public static final long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return the anchorTypeKey
     */
    public final String getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey
     *            the anchorTypeKey to set
     */
    public final void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the businessRuleTypeKey
     */
    public final String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey
     *            the businessRuleTypeKey to set
     */
    public final void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

    /**
     * @return the businessRuleId
     */
    public final String getBusinessRuleId() {
        return businessRuleId;
    }

    /**
     * @param businessRuleId
     *            the businessRuleId to set
     */
    public final void setBusinessRuleId(String businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

    /**
     * @return the anchorValue
     */
    public final String getAnchorValue() {
        return anchorValue;
    }

    /**
     * @param anchorValue
     *            the anchorValue to set
     */
    public final void setAnchorValue(String anchorValue) {
        this.anchorValue = anchorValue;
    }

    /**
     * @return the createTime
     */
    public final String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public final void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the createUserId
     */
    public final String getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     *            the createUserId to set
     */
    public final void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return the updateTime
     */
    public final String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public final void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the updateUserId
     */
    public final String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * @param updateUserId
     *            the updateUserId to set
     */
    public final void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * @return the effectiveStartTime
     */
    public final String getEffectiveStartTime() {
        return effectiveStartTime;
    }

    /**
     * @param effectiveStartTime
     *            the effectiveStartTime to set
     */
    public final void setEffectiveStartTime(String effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    /**
     * @return the effectiveEndTime
     */
    public final String getEffectiveEndTime() {
        return effectiveEndTime;
    }

    /**
     * @param effectiveEndTime
     *            the effectiveEndTime to set
     */
    public final void setEffectiveEndTime(String effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    /**
     * @return the status
     */
    public final String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public final void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the ruleElementList
     */
    public final List<BusinessRuleElement> getRuleElementList() {
        return ruleElementList;
    }

    /**
     * @param ruleElementList
     *            the ruleElementList to set
     */
    public final void setRuleElementList(List<BusinessRuleElement> ruleElementList) {
        this.ruleElementList = ruleElementList;
    }
}