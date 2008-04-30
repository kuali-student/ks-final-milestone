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
package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.brms.repository.drools.rule.DroolsRuleSetImpl;
import org.kuali.student.brms.repository.util.CompiledObject;

/**
 * This is a <code>RuleSet</code> test class.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
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
    
    @Test
    public void testCompiledRuleSet() {
        DroolsRuleSetImpl ruleSet = (DroolsRuleSetImpl) RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet.addHeader("import java.util.Calendar");
        ruleSet.addRule( RuleUtil.createRule( "rule1" ) );
        byte[] b = ruleSet.getContent().getBytes();
        ruleSet.setCompiledRuleSet( b );
        
        assertArrayEquals( b, ruleSet.getCompiledRuleSet() );
        b = null;
        assertNotNull( "getCompiledRuleSet should not be null", ruleSet.getCompiledRuleSet() );
    }

    @Test
    public void testNullCompiledRuleSet() {
        DroolsRuleSetImpl ruleSet = (DroolsRuleSetImpl) RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet.addHeader("import java.util.Calendar");
        ruleSet.addRule( RuleUtil.createRule( "rule1" ) );
        ruleSet.setCompiledRuleSet( null );
        
        assertArrayEquals( null, ruleSet.getCompiledRuleSet() );
    }

    @Test
    public void testCompiledRuleSetObject_ObjectReference() {
        DroolsRuleSetImpl ruleSet = (DroolsRuleSetImpl) RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet.addHeader("import java.util.Calendar");
        ruleSet.addRule( RuleUtil.createRule( "rule1" ) );
        
        String expected = "ACompiledObject";
        CompiledObject obj1 = new CompiledObject( expected );
        ruleSet.setCompiledRuleSetObject( obj1 );
        
        assertFalse( obj1.equals( ruleSet.getCompiledRuleSetObject() ) );
        obj1 = new CompiledObject( "Some stuff" );

        assertNotNull( "getCompiledRuleSet should not be null", ruleSet.getCompiledRuleSetObject() );

        CompiledObject obj2 = (CompiledObject) ruleSet.getCompiledRuleSetObject();
        assertEquals( expected, obj2.getObject() );
    }
    
}
