package org.kuali.student.brms.repository.rule;

/**
 * This factory class creates new instances of <code>RuleSet</code>s. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleSetFactory {

    /** Rule factory instance */
    private static RuleSetFactory factory;
    
    /**
     * Private constructor
     */
    private RuleSetFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static RuleSetFactory getInstance() {
        if ( factory == null ) {
            factory = new RuleSetFactory();
        }
        return factory;
    }
    
    /**
     * Creates a new rule set.
     * 
     * @param name Rule set name
     * @return A new rule set
     */
    public RuleSet createRuleSet( final String name ) {
        return new RuleSetImpl( name );
    }
    
    /**
     * Creates a new rule set.
     * 
     * @param uuid Rule set UUID
     * @param name Rule set name
     * @return A new rule set
     */
    public RuleSet createRuleSet( final String uuid, final String name ) {
        return new RuleSetImpl( uuid, name );
    }
    
}
