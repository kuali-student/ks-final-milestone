package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RuleSetTest {

    @Test
    public void testNullName() {
        try {
            RuleUtil.createRuleSet( null );
            fail( "Should not be able to create a rule set with a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuid() {
        try {
            RuleUtil.createRuleSet( null, "ruleSetName", -1L );
            fail( "Should not be able to create a rule set with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleUtil.createRuleSet( null, null, -1L );
            fail( "Should not be able to create a rule set with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSetName" );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSetName" );

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testNameNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet2" );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testNameUuidVersionEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "123", "ruleSetName", 1L );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "123", "ruleSetName", 1L );

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testNameUuidVersionNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "123", "ruleSet1", 1L );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "987", "ruleSet2", 2L );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testRuleSetHeaderEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader("import java.util.Calendar");

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testRuleSetHeaderNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader("import java.util.ArrayList");

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testRuleSetHeaderNotEquals_MissingHeader() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testRuleSetsEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader("import java.util.Calendar");
        ruleSet2.addRule( RuleUtil.createRule( "rule1" ) );

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testRuleSetsNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet2" );
        ruleSet2.addHeader("import java.util.Calendar");
        ruleSet2.addRule( RuleUtil.createRule( "rule2" ) );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testRuleSetsEquals_MultipleRules() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        ruleSet1.addRule( RuleUtil.createRule( "rule2" ) );
        ruleSet1.addRule( RuleUtil.createRule( "rule3" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader("import java.util.Calendar");
        ruleSet2.addRule( RuleUtil.createRule( "rule1" ) );
        ruleSet2.addRule( RuleUtil.createRule( "rule2" ) );
        ruleSet2.addRule( RuleUtil.createRule( "rule3" ) );

        assertEquals( ruleSet1, ruleSet2 );
    }
    
    @Test
    public void testRuleSetsNotEquals_MultipleRules() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        ruleSet1.addRule( RuleUtil.createRule( "rule2" ) );
        ruleSet1.addRule( RuleUtil.createRule( "rule3" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader("import java.util.List");
        ruleSet2.addRule( RuleUtil.createRule( "rule4" ) );
        ruleSet2.addRule( RuleUtil.createRule( "rule5" ) );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }
    
}
