package org.kuali.student.cm.uif.util;

/**
 * Provides required indicator messages.
 */
public interface RequiredIndicatorService {
    String getRequiredIndicatorMessage(String workflowDocumentType, String workflowState, String propertyName);
}
