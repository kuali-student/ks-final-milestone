package org.kuali.student.rules.brms.agenda.entity;

import java.util.Collection;

public class BusinessRuleType
{
	private String name;
	
	private String type;
	
    private Collection<AnchorType> anchorTypes;

    public BusinessRuleType(String name, String type) {
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
        return "BusinessRuleType[name=" + this.name + ", type=" + type + "]";
    }
	
}
