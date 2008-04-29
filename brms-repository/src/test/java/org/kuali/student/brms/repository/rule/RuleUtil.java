package org.kuali.student.brms.repository.rule;

public class RuleUtil {

    public static Rule createRule( String name ) {
        Rule rule = RuleFactory.getInstance().createRule( name );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }

    public static Rule createRule( String uuid, String name, long version ) {
        Rule rule = RuleFactory.getInstance().createRule( uuid, name, version );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }
    
    public static RuleSet createRuleSet( String name ) {
        return RuleSetFactory.getInstance().createRuleSet( name );
    }    

    public static RuleSet createRuleSet( String uuid, String name, long version ) {
        return RuleSetFactory.getInstance().createRuleSet( uuid, name, version );
    }    

}
