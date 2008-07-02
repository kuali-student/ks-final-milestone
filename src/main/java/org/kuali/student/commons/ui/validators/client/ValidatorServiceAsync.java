package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous interface for Validator service, to be used on the client
 */
public interface ValidatorServiceAsync {
    /**
     * Retrieves the definition of the specified validator
     * 
     * @param id
     *            the id for the validator to retrieve
     * @param callback
     *            the callback to invoke when response is received
     */
    public void getValidatorDefinition(String id, AsyncCallback<ValidatorDefinition> callback);

    /**
     * Retrieves the definitions for validators of the specified type
     * 
     * @param type
     *            the type of validator to retrieve
     * @param callback
     *            the callback to invoke when response is received
     */
    public void getValidatorDefinitions(String type, AsyncCallback<Map<String, ValidatorDefinition>> callback);

    /**
     * Creates a new validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @param callback
     *            the callback to invoke when response is received
     */
    public void createValidator(String id, String type, String script, AsyncCallback<Boolean> callback);

    /**
     * Updates a validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @param callback
     *            the callback to invoke when response is received
     */
    public void updateValidator(String id, String type, String script, AsyncCallback<Boolean> callback);

    /**
     * Deletes a validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @param callback
     *            the callback to invoke when response is received
     */
    public void deleteValidator(String id, String type, String script, AsyncCallback<Boolean> callback);
}
