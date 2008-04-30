package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.brms.repository.drools.rule.DroolsRuleImpl;

/**
 * This is an <code>Rule</code> test class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
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
            RuleUtil.createRule( null, "rule1", -1 );
            fail( "Should not be able to create a rule with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleUtil.createRule( null, null, -1 );
            fail( "Should not be able to create a rule with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 1L );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L );
        Rule rule2 = RuleUtil.createRule( "123", "rule2", 1L );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testVersionNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 2L );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testNameUuidVersionEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 1L );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameUuidVersionNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L );
        Rule rule2 = RuleUtil.createRule( "987", "rule2", 2L );

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
    public void testBinaryContent() {
        DroolsRuleImpl rule = (DroolsRuleImpl) RuleUtil.createRule( "rule1" );
        byte[] b = rule.getContent().getBytes();
        rule.setBinaryContent( b );
        assertArrayEquals( b, rule.getBinaryContent() );
        b = null;
        assertNotNull( "getCompiledRuleSet should not be null", rule.getBinaryContent() );
    }
    
    @Test
    public void testNullBinaryContent() {
        DroolsRuleImpl rule = (DroolsRuleImpl) RuleUtil.createRule( "rule1" );
        rule.setBinaryContent( null );
        assertArrayEquals( null, rule.getBinaryContent() );
    }
}
