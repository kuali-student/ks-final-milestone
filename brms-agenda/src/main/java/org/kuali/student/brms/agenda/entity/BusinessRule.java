package org.kuali.student.brms.agenda.entity;

public class BusinessRule
{
	private String id;
	
	private Anchor anchor;
	
	private BusinessRuleType businessRuleType;

    public BusinessRule(String id, BusinessRuleType businessRuleType) {
        this.id = id;
        this.businessRuleType = businessRuleType;
    }

    public String getId() {
        return id;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public BusinessRuleType getBusinessRuleType() {
        return businessRuleType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "BusinessRule[id=" ); 
        sb.append( id );
        sb.append( ", type=" );
        sb.append( businessRuleType.getType() );
        sb.append( ", anchor=" );
        sb.append( anchor == null ? "null" : anchor.getName() );
        sb.append( "]" );
        return sb.toString();
    }
}
