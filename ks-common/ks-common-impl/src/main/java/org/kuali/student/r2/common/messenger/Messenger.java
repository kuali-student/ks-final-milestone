package org.kuali.student.r2.common.messenger;

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
     * @param user
     * @param key
     * @param parameters
     */
    public void sendWarningMessage(final String user, final String key, final String[] parameters);

    /**
     * Send an info message to the queue for the specific user.
     *
     * @param user
     * @param key
     * @param parameters
     */
    public void sendInfoMessage(final String user, final String key, final String[] parameters);

    /**
     * Send an error message to the queue for the specific user.
     *
     * @param user
     * @param key
     * @param parameters
     */
    public void sendErrorMessage(final String user, final String key, final String[] parameters);

    /**
     * Send a success message to the queue for the specific user.
     *
     * @param user
     * @param key
     * @param parameters
     */
    public void sendSuccessMessage(final String user, final String key, final String[] parameters);

}

