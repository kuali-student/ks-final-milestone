package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Defines the synchronous interface for the Validator service
 */
public interface ValidatorService extends RemoteService {

    /**
     * URI for the servlet implementing the Validator service
     */
    public static final String SERVICE_URI = "/ValidatorService";

    /**
     * Utility class providing getInstance method for creating a ValidatorServiceAsync instance
     */
    public static class Util {

        /**
         * Creates an instance of ValidatorServiceAsync using the module base URL and the SERVICE_URI constant
         * 
         * @return an instance of ValidatorServiceAsync
         */
        public static ValidatorServiceAsync getInstance() {

            ValidatorServiceAsync instance = (ValidatorServiceAsync) GWT.create(ValidatorService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }

    /**
     * Returns the definition of the specified validator
     * 
     * @param id
     *            the id for the validator to return
     * @return ValidatorDefinition for the specified validator
     */
    public ValidatorDefinition getValidatorDefinition(String id);

    /**
     * Returns the definitions for validators of the specified type
     * 
     * @param type
     *            the type of validator to return
     * @return ValidatorDefinitions for the validators of the specified type
     */
    public Map<String, ValidatorDefinition> getValidatorDefinitions(String type);

    /**
     * Creates a new validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @return true if operation was successful
     */
    public Boolean createValidator(String id, String type, String script);

    /**
     * Updates a validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @return true if operation was successful
     */
    public Boolean updateValidator(String id, String type, String script);

    /**
     * Deletes a validator
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type
     * @param script
     *            the validator script
     * @return true if operation was successful
     */
    public Boolean deleteValidator(String id);

}
