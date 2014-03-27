package org.kuali.student.enrollment.batch.dto;

/**
 * Created by SW Genis on 2014/03/27.
 */
public class BatchParameterDefinition {

    private String key;

    private String type;

    private boolean required;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
