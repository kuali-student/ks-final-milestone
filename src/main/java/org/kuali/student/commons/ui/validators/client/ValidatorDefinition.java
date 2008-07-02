package org.kuali.student.commons.ui.validators.client;

import java.io.Serializable;

/**
 * Bean containing the definition information for a validator.
 */
public class ValidatorDefinition implements Serializable {
    private static final long serialVersionUID = 1L;
    String id;
    String type;
    String script;

    /**
     * Creates a new empty validator definition
     */
    public ValidatorDefinition() {

    }

    /**
     * Creates a new validator definition
     * 
     * @param id
     *            the validator id
     * @param type
     *            the validator type (e.g. "javascript")
     * @param script
     *            the script to be executed
     */
    public ValidatorDefinition(String id, String type, String script) {
        super();
        this.id = id;
        this.type = type;
        this.script = script;
    }

    /**
     * Returns the validator id
     * 
     * @return the validator id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the validator id
     * 
     * @param id
     *            the validator id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the validator type
     * 
     * @return the validator type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the validator type
     * 
     * @param type
     *            the validator type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the validator script
     * 
     * @return the validator script
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the validator script
     * 
     * @param script
     *            the validator script
     */
    public void setScript(String script) {
        this.script = script;
    }

}
