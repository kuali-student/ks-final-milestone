/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.rule;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.rules.repository.drools.rule.DroolsRuleImpl;
import org.kuali.student.rules.repository.rule.Rule;

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
            RuleUtil.createRule( null, "rule1", -1, "ruleSetUUID", "ruleSetName" );
            fail( "Should not be able to create a rule with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleUtil.createRule( null, null, -1, "ruleSetUUID", "ruleSetName"  );
            fail( "Should not be able to create a rule with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L, null, null );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 1L, null, null );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L, null, null );
        Rule rule2 = RuleUtil.createRule( "123", "rule2", 1L, null, null );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testVersionNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L, null, null );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 2L, null, null );

        assertFalse( rule1.equals( rule2 ) );
    }

    @Test
    public void testNameUuidVersionEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L, null, null );
        Rule rule2 = RuleUtil.createRule( "123", "rule1", 1L, null, null );

        assertEquals( rule1, rule2 );
    }

    @Test
    public void testNameUuidVersionNotEquals() {
        Rule rule1 = RuleUtil.createRule( "123", "rule1", 1L, null, null );
        Rule rule2 = RuleUtil.createRule( "987", "rule2", 2L, null, null );

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

    /*@Test
    public void testRuleCopy() {
        DroolsRuleImpl rule = (DroolsRuleImpl) RuleUtil.createRule( "rule1" );
        String content = "Some binary stuff";
        rule.setBinaryContent( content.getBytes() );
        rule.setCategory( "MyCategory" );
        Rule copy = rule.copy();
        // Make sure it is a true copy
        assertFalse( copy == rule );
        // Make sure that the copy is equal to the original rule
        assertEquals( copy, rule );
    }*/
}
