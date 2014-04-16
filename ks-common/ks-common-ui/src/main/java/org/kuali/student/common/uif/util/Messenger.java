package org.kuali.student.common.uif.util;

import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * This is a messenger interface that can be implemented to send messages from a service to the user.
 * Messages will be displayed as growl messages in the browser.
 *
 * @author Kuali Student Team
 */
public interface Messenger {

    /**
     * Send a warning message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     */
    public void addWarningMessage(String key, String[] parameters);

    /**
     * Send an info message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     */
    public void addInfoMessage(String key, String[] parameters);

    /**
     * Send an error message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     */
    public void addErrorMessage(String key, String[] parameters);

    /**
     * Send a success message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     */
    public void addSuccessMessage(String key, String[] parameters);

    /**
     * Retrieve messages for this context.
     */
    public void publishMessages();

}

