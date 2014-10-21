package org.kuali.student.cm.uif.util;

import org.kuali.rice.krad.uif.element.Message;

/**
 * Overrides Label#getMessageText to get the message text from RequiredIndicatorService.
 */
public class RequiredIndicatorMessage extends Message {

    private RequiredIndicatorService requiredIndicatorService;

    private String messageKey;

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

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageText() {
        return requiredIndicatorService.getRequiredIndicatorMessage(workflowDocumentType, workflowState, messageKey);
    }
}
