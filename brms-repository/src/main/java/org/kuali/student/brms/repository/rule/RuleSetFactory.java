package org.kuali.student.brms.repository.rule;

public class RuleSetFactory {

    private RuleSetFactory() { }
    
    private static RuleSetFactory factory;
    
    public static RuleSetFactory getInstance() {
        if ( factory == null ) {
            factory = new RuleSetFactory();
        }
        return factory;
    }
    
    public RuleSet createRuleSet( String name ) {
        return new RuleSetImpl( name );
    }
    
    public RuleSet createRuleSet( String uuid, String name ) {
        return new RuleSetImpl( uuid, name );
    }
    
}
