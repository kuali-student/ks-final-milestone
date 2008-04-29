package org.kuali.student.brms.repository.rule;

import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;

public class RuleUtil {

    public static Rule createRule( String name ) {
        Rule rule = RuleFactory.getInstance().createDroolsRule( name );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }

    public static Rule createRule( String uuid, String name, long version ) {
        Rule rule = RuleFactory.getInstance().createDroolsRule( uuid, name, version );
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
