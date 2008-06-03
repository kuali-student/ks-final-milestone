package org.kuali.student.brms.agenda.entity;

import java.util.ArrayList;
import java.util.Collection;

public class Agenda
{
	private String name;
	
	private AgendaType agendaType;
	
	private Collection<BusinessRule> businessRules = new ArrayList<BusinessRule>();

    public Agenda(String name, AgendaType agendaType) {
        super();
        this.name = name;
        this.agendaType = agendaType;
    }

    public String getName() {
        return name;
    }

    public AgendaType getAgendaType() {
        return agendaType;
    }

    public boolean addBusinessRule(BusinessRule rule) {
        return businessRules.add(rule);
    }
    
    public Collection<BusinessRule> getBusinessRules() {
        return businessRules;
    }
    
    public BusinessRule getBusinessRule( String id ) {
        for( BusinessRule rule : getBusinessRules() ) {
            if ( id.equals( rule.getId() ) ) {
                return rule;
            }
        }
        return null;
    }
    
    public String toString() {
        return "Agenda[name=" + this.name + ", type=" + agendaType.getType() + "]";
    }
}
