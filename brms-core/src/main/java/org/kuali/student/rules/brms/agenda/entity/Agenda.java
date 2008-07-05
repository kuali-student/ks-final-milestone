package org.kuali.student.rules.brms.agenda.entity;

import java.util.ArrayList;
import java.util.Collection;

public class Agenda
{
	private String name;
	
	private AgendaType agendaType;
	
	private Collection<BusinessRuleSet> businessRules = new ArrayList<BusinessRuleSet>();

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

    public boolean addBusinessRule(BusinessRuleSet rule) {
        return businessRules.add(rule);
    }
    
    public Collection<BusinessRuleSet> getBusinessRules() {
        return businessRules;
    }
    
    public BusinessRuleSet getBusinessRule( String id ) {
        for( BusinessRuleSet rule : getBusinessRules() ) {
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
