package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;

/**
 * Interface defining operations necessary to implement a Validator.
 */
public interface Validator {
    /**
     * Sets the internationalization messages to be used by the validator.
     * 
     * @param messages
     *            the internationalization messages to be used by the validator
     */
    public void setMessages(Messages messages);

    /**
     * Validates the object using the default attributes
     * 
     * @param value
     *            the value to validate
     * @return ValidationResult containing the validation state and messages
     */
    public ValidationResult validate(Object value);

    /**
     * Validates the object using the default attributes
     * 
     * @param value
     *            the value to validate
     * @param valueName
     *            the name of the value, used when interpolating error messages
     * @return ValidationResult containing the validation state and messages
     */
    public ValidationResult validate(Object value, String valueName);

    /**
     * Validates the object using an extended attribute set, in addition to the default attributes
     * 
     * @param value
     *            the value to validate
     * @param attributes
     *            the addition attributes to used for validation
     * @return ValidationResult containing the validation state and messages
     */
    public ValidationResult validate(Object value, Map<String, String> attributes);

    /**
     * Validates the object using an extended attribute set, in addition to the default attributes
     * 
     * @param value
     *            the value to validate
     * @param valueName
     *            the name of the value, used when interpolating error messages
     * @param attributes
     *            the addition attributes to used for validation
     * @return ValidationResult containing the validation state and messages
     */
    public ValidationResult validate(Object value, String valueName, Map<String, String> attributes);
}
