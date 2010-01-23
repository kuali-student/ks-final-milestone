package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Returned by Validator.validate() methods
 */
public class ValidationResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ErrorLevel {
        OK(0), WARN(1), ERROR(2);

        int level;

        private ErrorLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    ErrorLevel errorLevel = ErrorLevel.OK;
    List<String> messages = new ArrayList<String>();

    /**
     * Adds an message to the result. If the error level is greater than the current error level, then the specified error
     * level becomes the current error level.
     * 
     * @param level
     *            the error level associated with the message
     * @param message
     *            the message to add
     */
    public void addMessage(ErrorLevel level, String message) {
        if (level.getLevel() > errorLevel.getLevel()) {
            errorLevel = level;
        }
        messages.add(message);
    }

    /**
     * Returns a list of the messages associated with the ValidationResult
     * 
     * @return
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Returns the ValidationResult's error level
     * 
     * @return
     */
    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    /**
     * Convenience method. Adds a message with an error level of WARN
     * 
     * @param message
     *            the message to add
     */
    public void addWarning(String message) {
        addMessage(ErrorLevel.WARN, message);
    }

    /**
     * Convenience method. Adds a message with an error level of ERROR
     * 
     * @param message
     *            the message to add
     */
    public void addError(String message) {
        addMessage(ErrorLevel.ERROR, message);
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
     * 
     * @return true if getErrorLevel() == ErrorLevel.OK
     */
    public boolean isOk() {
        return getErrorLevel() == ErrorLevel.OK;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
     * 
     * @return true if getErrorLevel() == ErrorLevel.WARN
     */
    public boolean isWarn() {
        return getErrorLevel() == ErrorLevel.WARN;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
     * 
     * @return true if getErrorLevel() == ErrorLevel.ERROR
     */
    public boolean isError() {
        return getErrorLevel() == ErrorLevel.ERROR;
    }

}
