package org.kuali.student.rules.brms.agenda.entity;

public class BusinessRule
{
	private String id;
	
	private String businessRuleName;
	
	private Anchor anchor;
	
	private BusinessRuleType businessRuleType;

	private String functionalRuleString;
	
    public BusinessRule(String id, String businessRuleName, BusinessRuleType businessRuleType, String functionalRuleString ) {
        this.id = id;
        this.businessRuleName = businessRuleName;
        this.businessRuleType = businessRuleType;
        this.functionalRuleString = functionalRuleString;
    }

    public String getId() {
        return id;
    }

    public String getBusinessRuleName() {
        return businessRuleName;
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

    
    /**
     * @return the functionalRule
     */
    public String getFunctionalRuleString() {
        return functionalRuleString;
    }

    /**
     * @param functionalRule the functionalRule to set
     */
    public void setFunctionalRule(String functionalRuleString) {
        this.functionalRuleString = functionalRuleString;
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
