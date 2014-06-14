package org.kuali.student.enrollment.batch.dto;

import java.util.List;

/**
 * Created by SW Genis on 2014/03/26.
 */
public class BatchJobDefinition {

    private String name;
    private String key;

    private List<BatchParameterDefinition> parameterDefinitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<BatchParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }

    public void setParameterDefinitions(List<BatchParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }
}
