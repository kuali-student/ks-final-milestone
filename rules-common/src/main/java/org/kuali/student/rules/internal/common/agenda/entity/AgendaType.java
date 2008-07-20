package org.kuali.student.rules.internal.common.agenda.entity;

import java.util.ArrayList;
import java.util.List;

public class AgendaType
{
	private String name;
	private String type;
	
	private List<BusinessRuleSetType> businessRuleTypes = new ArrayList<BusinessRuleSetType>();

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
    
    public boolean addBusinessRuleType(BusinessRuleSetType rule) {
        return businessRuleTypes.add(rule);
    }
    
    public List<BusinessRuleSetType> getBusinessRuleTypes() {
        return businessRuleTypes;
    }
    
    public BusinessRuleSetType getBusinessRuleType( String key ) {
        for( BusinessRuleSetType ruleType : businessRuleTypes ) {
            if ( ruleType.getType().equals( key ) ) {
                return ruleType;
            }
        }
        return null;
    }

    public String toString() {
        return "AgendaType[name=" + this.name + ", type=" + type + "]";
    }
	
}
