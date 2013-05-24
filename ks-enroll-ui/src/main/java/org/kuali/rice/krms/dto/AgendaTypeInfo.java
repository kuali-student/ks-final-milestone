package org.kuali.rice.krms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/12/13
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaTypeInfo implements Serializable {

    private String id;
    private String description;
    private List<RuleTypeInfo> ruleTypes;

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

    public List<RuleTypeInfo> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(List<RuleTypeInfo> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }
}
