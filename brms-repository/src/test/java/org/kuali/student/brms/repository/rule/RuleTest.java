package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RuleTest {

    @Test
    public void testNullName() {
        try {
            RuleUtil.createRule( null );
            fail( "Should not be able to create a rule with a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuid() {
        try {
            RuleUtil.createRule( null, "rule1" );
            fail( "Should not be able to create a rule with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleUtil.createRule( null, null );
            fail( "Should not be able to create a rule with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "123", "rule1" );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "123", "rule2" );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testNameUuidEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "123", "rule1" );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameUuidNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "987", "rule2" );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testRulesEquals() {
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule1" );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testRulesNotEquals() {
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule2" );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testNameUuidRulesEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "123", "rule1" );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameUuidRulesNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1" );
        Rule rule2 = RuleUtil.createRule( "987", "rule1" );

        assertFalse( rule1.equals( rule2 ) );
    }
    
}
