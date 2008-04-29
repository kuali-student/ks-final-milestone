package org.kuali.student.brms.repository.drools.rule;

import org.kuali.student.brms.repository.rule.RuleSet;

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
        return new DroolsRuleSetImpl( name );
    }
    
    /**
     * <p>For internal use only.</p>
     * 
     * <p>Creates a new rule set. 
     * <code>uuid</code> and <code>version</code> are set by the 
     * repository implementation.</p>
     * 
     * @param uuid Rule set UUID - Set the by the repository implementation
     * @param name Rule set name
     * @param version Rule version number - Set the by the repository implementation
     * @return A new rule set
     */
    public DroolsRuleSetImpl createRuleSet( final String uuid, final String name, long version ) {
        return new DroolsRuleSetImpl( uuid, name, version );
    }
    
}
