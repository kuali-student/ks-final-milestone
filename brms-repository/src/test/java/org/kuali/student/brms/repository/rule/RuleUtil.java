package org.kuali.student.brms.repository.rule;

public class RuleUtil {

    public static Rule createRule( String name ) {
        Rule rule = RuleFactory.getInstance().createRule( name );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }

    public static Rule createRule( String uuid, String name ) {
        Rule rule = RuleFactory.getInstance().createRule( uuid, name );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }
    
    public static RuleSet createRuleSet( String name ) {
        return RuleSetFactory.getInstance().createRuleSet( name );
    }    

    public static RuleSet createRuleSet( String uuid, String name ) {
        return RuleSetFactory.getInstance().createRuleSet( uuid, name );
    }    

}
