package org.kuali.student.enrollment.registration.client.service.exception;

import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;

/**
 * Created by swedev on 3/11/14.
 */
public class GenericUserException extends Exception {
    private UserMessageResult userMessage;

    public GenericUserException(UserMessageResult userMessage) {
        this.userMessage = userMessage;
    }

    public UserMessageResult getUserMessage() {
        return userMessage;
    }
}
