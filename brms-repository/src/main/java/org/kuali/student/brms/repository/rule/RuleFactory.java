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
     * <p>For internal use only.</p>
     * 
     * <p>Creates a rule.
     * <code>uuid</code> and <code>version</code> are set by the 
     * repository implementation.</p>
     * 
     * @param uuid Rule UUID - Set the by the repository implementation
     * @param name Rule name
     * @param version Rule version number - Set the by the repository implementation
     * @return A new rule
     */
    public RuleImpl createRule( final String uuid, final String name, long version ) {
        return new RuleImpl( uuid, name, version );
    }
}
