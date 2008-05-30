package org.kuali.student.brms.agenda.entity;

import java.util.ArrayList;
import java.util.Collection;

public class AgendaType
{
	private String name;
	private String type;
	
	private Collection<BusinessRuleType> businessRuleTypes = new ArrayList<BusinessRuleType>();

    public AgendaType(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public boolean addBusinessRuleType(BusinessRuleType rule) {
        return businessRuleTypes.add(rule);
    }
    
    public Collection<BusinessRuleType> getBusinessRuleTypes() {
        return businessRuleTypes;
    }

    public String toString() {
        return "AgendaType[name=" + this.name + ", type=" + type + "]";
    }
	
}
