package org.kuali.student.brms.repository.rule;

public class RuleFactory {

    private RuleFactory() { }
    
    private static RuleFactory factory;
    
    public static RuleFactory getInstance() {
        if ( factory == null ) {
            factory = new RuleFactory();
        }
        return factory;
    }
    
    public Rule createRule( String name ) {
        return new RuleImpl( name );
    }
    
    public Rule createRule( String uuid, String name ) {
        return new RuleImpl( uuid, name );
    }
}
