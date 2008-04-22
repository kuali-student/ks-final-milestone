package org.kuali.student.rules.BRMSCore;

/**
 * Defines possible types of Rule Element
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
public enum RuleElementType {

    AND_TYPE("AND", "1"), OR_TYPE("OR", "2"), XOR_TYPE("XOR", "3"), NOT_TYPE("NOT", "4"), LPAREN_TYPE("(", "5"), RPAREN_TYPE(")", "6"), PROPOSITION_TYPE("PROPOSITION", "7");

    private String name;
    private String id;

    /**
     * Sets up a RuleElementType instance.
     */
    private RuleElementType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the validateTypeId
     */
    public String getRuleElementTypeId() {
        return id;
    }
}
