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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.rules.repository.drools.rule.DroolsRuleSetImpl;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.repository.util.CompiledObject;

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
            RuleUtil.createRuleSet(null);
            fail("Should not be able to create a rule set with a null name");
        }
        catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testNullUuid() {
        try {
            RuleUtil.createRuleSet(null, "ruleSetName", "ruleSetDescription", -1L);
            fail("Should not be able to create a rule set with a null UUID");
        }
        catch( IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            RuleUtil.createRuleSet(null, null, "ruleSetDescription", -1L);
            fail("Should not be able to create a rule set with a null UUID and name");
        }
        catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testNullUuidAndNameAndDescription() {
        try {
            RuleUtil.createRuleSet(null, null, null, -1L);
            fail("Should not be able to create a rule set with a null UUID, name and description");
        }
        catch(IllegalArgumentException e) {
            assertTrue(true);
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
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "123", "ruleSetName", "ruleSetDescription", 1L );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "123", "ruleSetName", "ruleSetDescription", 1L );

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testNameUuidVersionNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "123", "ruleSet1", "ruleSetDescription", 1L );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "987", "ruleSet2", "ruleSetDescription", 2L );

        assertFalse( ruleSet1.equals( ruleSet2 ) );
    }

    @Test
    public void testUuidNotEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "123", "ruleSet1", "ruleSetDescription", 1L );
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "987", "ruleSet1", "ruleSetDescription", 1L );

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
    public void testRuleSetAddOneHeader() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header = "import java.util.Calendar;";
        ruleSet.addHeader(header);

        assertEquals( 1, ruleSet.getHeaderList().size() );
        assertEquals( header, ruleSet.getHeaderList().get(0) );
    }

    @Test
    public void testRuleSetWithDuplicateHeader() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header = "import java.util.Calendar;";
        String duplicateHeader = "import java.util.Calendar;";
        ruleSet.addHeader(header);
        ruleSet.addHeader(duplicateHeader);

        assertEquals( 1, ruleSet.getHeaderList().size() );
        assertEquals( header, ruleSet.getHeaderList().get(0) );
    }

    public void testRuleSetAddHeader_WithMissingSimicolon() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header = "import java.util.Calendar";
        ruleSet.addHeader(header);

        String expectedHeader = header +";";
        assertEquals( 1, ruleSet.getHeaderList().size() );
        assertEquals( expectedHeader, ruleSet.getHeaderList().get(0) );
    }

    @Test
    public void testRuleSetAddThreeHeaders() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header1 = "import java.util.Calendar;";
        String header2 = "import java.util.Map;";
        String header3 = "import java.util.List;";
        ruleSet.addHeader(header1);
        ruleSet.addHeader(header2);
        ruleSet.addHeader(header3);

        assertEquals( 3, ruleSet.getHeaderList().size() );
        assertEquals( header1, ruleSet.getHeaderList().get(0) );
        assertEquals( header2, ruleSet.getHeaderList().get(1) );
        assertEquals( header3, ruleSet.getHeaderList().get(2) );
    }

    @Test
    public void testRuleSetContainsThreeHeaders() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header1 = "import java.util.Calendar;";
        String header2 = "import java.util.Map;";
        String header3 = "import java.util.List;";
        ruleSet.addHeader(header1);
        ruleSet.addHeader(header2);
        ruleSet.addHeader(header3);

        assertEquals( 3, ruleSet.getHeaderList().size() );
        assertTrue( ruleSet.containsHeader(header1) );
        assertTrue( ruleSet.containsHeader(header2) );
        assertTrue( ruleSet.containsHeader(header3) );
    }

    @Test
    public void testRuleSetClearHeaders() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet.addHeader("import java.util.Calendar");
        ruleSet.clearHeaders();

        assertEquals( 0, ruleSet.getHeaderList().size() );
    }

    @Test
    public void testRuleSetRemoveHeader_WithSemicolon() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header = "import java.util.Calendar;";
        ruleSet.addHeader(header);
        ruleSet.removeHeader(header);

        assertEquals( 0, ruleSet.getHeaderList().size() );
    }

    @Test
    public void testRuleSetRemoveHeader_WithoutSemicolon() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        String header = "import java.util.Calendar";
        ruleSet.addHeader(header);
        ruleSet.removeHeader(header);

        assertEquals( 0, ruleSet.getHeaderList().size() );
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
    public void testRuleSetGetRules_NonModifiableRuleContent() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet.addRule( RuleUtil.createRule( "rule1" ) );
        Rule rule = ruleSet.getRules().get(0);
        
        Rule ruleCopy = ruleSet.getRules().get(0);
        ruleCopy.setContent( "xxx" );
        // Test that the rule is not modifiable
        assertFalse( rule.equals( ruleCopy ) );
    }

    @Test
    public void testRuleSetGetRules_NonModifiableRuleList() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule = RuleUtil.createRule( "rule1" );
        ruleSet.addRule( rule );
        ruleSet.getRules().clear();
        
        // Test that the rule list is not modifiable
        assertEquals( 1, ruleSet.getRules().size() );
        assertEquals( rule, ruleSet.getRules().get(0) );
    }

    @Test
    public void testRuleSetAddOneRule() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule = RuleUtil.createRule( "rule1" );
        ruleSet.addRule( rule );
        assertEquals( 1, ruleSet.getRules().size() );
        assertEquals( rule, ruleSet.getRules().get(0) );
    }

    @Test
    public void testRuleSetAddThreeRules() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule2" );
        Rule rule3 = RuleUtil.createRule( "rule3" );
        ruleSet.addRule( rule1 );
        ruleSet.addRule( rule2 );
        ruleSet.addRule( rule3 );

        assertEquals( 3, ruleSet.getRules().size() );
        assertEquals( rule1, ruleSet.getRules().get(0) );
        assertEquals( rule2, ruleSet.getRules().get(1) );
        assertEquals( rule3, ruleSet.getRules().get(2) );
    }

    @Test
    public void testRuleSetAddDuplicateRule() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule = RuleUtil.createRule( "rule1" );
        Rule duplicateRule = RuleUtil.createRule( "rule1" );
        ruleSet.addRule( rule );
        ruleSet.addRule( duplicateRule );

        // Duplicates are not added
        assertEquals( 1, ruleSet.getRules().size() );
        assertEquals( rule, ruleSet.getRules().get(0) );
    }

    @Test
    public void testRuleSetContainsThreeRules() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule2" );
        Rule rule3 = RuleUtil.createRule( "rule3" );
        ruleSet.addRule( rule1 );
        ruleSet.addRule( rule2 );
        ruleSet.addRule( rule3 );

        assertEquals( 3, ruleSet.getRules().size() );
        assertTrue( ruleSet.containsRule(rule1.getName()) );
        assertTrue( ruleSet.containsRule(rule2.getName()) );
        assertTrue( ruleSet.containsRule(rule3.getName()) );
    }

    @Test
    public void testRuleSetClearRules() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule = RuleUtil.createRule( "rule1" );
        ruleSet.addRule( rule );
        ruleSet.clearRules();
        assertEquals( 0, ruleSet.getRules().size() );
    }

    @Test
    public void testRuleSetRemoveOneRule() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule2" );
        Rule rule3 = RuleUtil.createRule( "rule3" );
        ruleSet.addRule( rule1 );
        ruleSet.addRule( rule2 );
        ruleSet.addRule( rule3 );

        ruleSet.removeRule( rule2.getName() );
        assertEquals( 2, ruleSet.getRules().size() );
        assertFalse( ruleSet.getRules().contains( rule2 ) );
    }

    @Test
    public void testRuleSetRemoveThreeRules() {
        RuleSet ruleSet = RuleUtil.createRuleSet( "ruleSet1" );
        Rule rule1 = RuleUtil.createRule( "rule1" );
        Rule rule2 = RuleUtil.createRule( "rule2" );
        Rule rule3 = RuleUtil.createRule( "rule3" );
        ruleSet.addRule( rule1 );
        ruleSet.addRule( rule2 );
        ruleSet.addRule( rule3 );

        ruleSet.removeRule( rule2.getName() );
        ruleSet.removeRule( rule1.getName() );
        ruleSet.removeRule( rule3.getName() );
        assertEquals( 0, ruleSet.getRules().size() );
    }

    @Test
    public void testRuleSetsEquals() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader("import java.util.Calendar");
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet2.addHeader( "import java.util.Calendar" );
        ruleSet2.addRule( RuleUtil.createRule( "rule1" ) );

        assertEquals( ruleSet1, ruleSet2 );
    }

    @Test
    public void testRuleSetsNotEquals_DifferentRuleName() {
        RuleSet ruleSet1 = RuleUtil.createRuleSet( "ruleSet1" );
        ruleSet1.addHeader( "import java.util.Calendar" );
        ruleSet1.addRule( RuleUtil.createRule( "rule1" ) );
        
        RuleSet ruleSet2 = RuleUtil.createRuleSet( "ruleSet2" );
        ruleSet2.addHeader( "import java.util.Calendar" );
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
