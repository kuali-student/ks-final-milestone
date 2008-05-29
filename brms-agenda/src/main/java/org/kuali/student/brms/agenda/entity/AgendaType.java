package org.kuali.student.brms.agenda.entity;

import java.util.Collection;

public class AgendaType
{
	private String name;
	private String type;
	
	private Collection<BusinessRuleType> businessRules;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public Collection<BusinessRuleType> getBusinessRuleTypes() {
        return businessRules;
    }

	
}
