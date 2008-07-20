package org.kuali.student.rules.internal.common.agenda.entity;

public class BusinessRuleSet
{
	private String id;
	
	private String businessRuleName;
	
	private Anchor anchor;
	
	private BusinessRuleSetType businessRuleType;

	private String functionalRuleString;
	
    public BusinessRuleSet(String id, String businessRuleName, BusinessRuleSetType businessRuleType, String functionalRuleString ) {
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

    public BusinessRuleSetType getBusinessRuleType() {
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
        sb.append( "BusinessRuleSet[id=" ); 
        sb.append( id );
        sb.append( ", type=" );
        sb.append( businessRuleType.getType() );
        sb.append( ", anchor=" );
        sb.append( anchor == null ? "null" : anchor.getName() );
        sb.append( "]" );
        return sb.toString();
    }

}
