package org.kuali.student.rules.internal.common.agenda.entity;

import java.util.Collection;

public class BusinessRuleSetType
{
	private String name;
	
	private String type;
	
    private Collection<AnchorType> anchorTypes;

    public BusinessRuleSetType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Collection<AnchorType> getAnchorTypes() {
        return anchorTypes;
    }
    
    public String toString() {
        return "BusinessRuleSetType[name=" + this.name + ", type=" + type + "]";
    }
	
}
