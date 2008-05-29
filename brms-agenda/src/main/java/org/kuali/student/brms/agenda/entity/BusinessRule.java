package org.kuali.student.brms.agenda.entity;

public class BusinessRule
{
	private String id;
	
    private String name;
	
	private Anchor anchor;
	
	private BusinessRuleType businessRuleType;

    public BusinessRule(String id, Anchor anchor, BusinessRuleType businessRuleType) {
        super();
        this.name = name;
        this.anchor = anchor;
        this.businessRuleType = businessRuleType;
    }

    public String getId() {
        return id;
    }
}
