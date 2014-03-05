package org.kuali.student.r2.common.messenger;

import org.kuali.student.r2.common.dto.ContextInfo;

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
     * @param contextInfo
     */
    public void sendWarningMessage(String key, String[] parameters, ContextInfo contextInfo);

    /**
     * Send an info message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     * @param contextInfo
     */
    public void sendInfoMessage(String key, String[] parameters, ContextInfo contextInfo);

    /**
     * Send an error message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     * @param contextInfo
     */
    public void sendErrorMessage(String key, String[] parameters, ContextInfo contextInfo);

    /**
     * Send a success message to the queue for the specific user.
     *
     * @param key
     * @param parameters
     * @param contextInfo
     */
    public void sendSuccessMessage(String key, String[] parameters, ContextInfo contextInfo);

}

