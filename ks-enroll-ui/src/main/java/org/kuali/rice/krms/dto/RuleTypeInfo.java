package org.kuali.rice.krms.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/12/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleTypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private String instruction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
