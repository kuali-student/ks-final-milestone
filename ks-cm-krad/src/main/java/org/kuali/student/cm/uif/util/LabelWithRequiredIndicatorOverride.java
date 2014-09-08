package org.kuali.student.cm.uif.util;

import org.kuali.rice.krad.uif.element.Label;

/**
 * Overrides Label#requiredIndicator to get the message text from RequiredIndicatorService.
 */
public class LabelWithRequiredIndicatorOverride extends Label {

    private RequiredIndicatorService requiredIndicatorService;

    private String parentPropertyName;

    private String workflowDocumentType;

    private String workflowState;

    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }

    public void setWorkflowDocumentType(String workflowDocumentType) {
        this.workflowDocumentType = workflowDocumentType;
    }

    public void setRequiredIndicatorService(RequiredIndicatorService requiredIndicatorService) {
        this.requiredIndicatorService = requiredIndicatorService;
    }

    public void setParentPropertyName(String parentPropertyName) {
        this.parentPropertyName = parentPropertyName;
    }

    @Override
    public String getRequiredIndicator() {
        return requiredIndicatorService.getRequiredIndicatorMessage(workflowDocumentType, workflowState, parentPropertyName);
    }
}
