package org.kuali.student.brms.agenda.entity;

import java.util.Collection;

public class BusinessRuleType
{
	private String name;
	
	private String type;
	
    private Collection<AnchorType> anchorTypes;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Collection<AnchorType> getAnchorTypes() {
        return anchorTypes;
    }
    
	
}
