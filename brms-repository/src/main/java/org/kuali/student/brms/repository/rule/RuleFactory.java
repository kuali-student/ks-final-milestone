package org.kuali.student.brms.repository.rule;

/**
 * This factory class creates new instances of <code>Rule</code>s. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleFactory {

    /** Rule factory instance */
    private static RuleFactory factory;

    /**
     * Private constructor
     */
    private RuleFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static RuleFactory getInstance() {
        if ( factory == null ) {
            factory = new RuleFactory();
        }
        return factory;
    }

    /**
     * Creates a rule.
     * 
     * @param name Rule name
     * @return A new rule
     */
    public Rule createRule( final String name ) {
        return new RuleImpl( name );
    }

    /**
     * Creates a rule.
     * 
     * @param uuid Rule UUID
     * @param name Rule name
     * @return A new rule
     */
    public Rule createRule( final String uuid, final String name ) {
        return new RuleImpl( uuid, name );
    }
}
