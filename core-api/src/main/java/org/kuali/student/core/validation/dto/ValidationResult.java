package org.kuali.student.core.validation.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;

public class ValidationResult implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final ValidationResult OK = new ImmutableOkResult();
    
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
    String key = null;

    public ValidationResult() {
        super();
    }
    public ValidationResult(String key, ErrorLevel errorLevel, String message) {
        super();
        this.key = key;
        this.errorLevel = errorLevel;
        this.messages.add(message);
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
    
    private static class ImmutableOkResult extends ValidationResult {
        private final List<String> messages = Collections.unmodifiableList(new ArrayList<String>());
        @Override
        public void addError(String message) {
            throw new UnsupportedOperationException("ValidationResult.OK is immutable");
        }

        @Override
        public void addMessage(ErrorLevel level, String message) {
            throw new UnsupportedOperationException("ValidationResult.OK is immutable");
        }

        @Override
        public void addWarning(String message) {
            throw new UnsupportedOperationException("ValidationResult.OK is immutable");
        }

        @Override
        public List<String> getMessages() {
            return messages;
        }

        @Override
        public void setKey(String key) {
            throw new UnsupportedOperationException("ValidationResult.OK is immutable");
        }
        
    }

}