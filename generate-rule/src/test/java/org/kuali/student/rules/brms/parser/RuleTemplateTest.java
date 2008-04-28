package org.kuali.student.rules.brms.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RuleTemplateTest {
    RuleTemplate rt;
    
    @Before
    public void setUp() throws Exception {
        rt = new RuleTemplate();
        
        rt.setRuleName("The Rule Name");
        
        ArrayList<String> ruleAttributes = new ArrayList<String>();
        ruleAttributes.add("#no-loop true" );
        ruleAttributes.add("#salience 1" );
        rt.setRuleAttributes(ruleAttributes);
        
        ArrayList<String> lhs = new ArrayList<String>();
        lhs.add("constraint : Constraint(constraintID == \"A constraintID from DB\")" );
        lhs.add("Boolean(booleanValue == Boolean.FALSE) from constraint.apply()");
        rt.setLHS(lhs);
        
        ArrayList<String> rhs = new ArrayList<String>();
        rhs.add("Propositions.setProposition(\"A\", true);");
        
        rt.setRHS(rhs);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProcess() throws Exception {
        String expected = "\r\nrule \"The Rule Name\"\r\n" +
                            "\t#no-loop true\r\n" +
                            "\t#salience 1\r\n"  +
                            "when\r\n" +
                            "\tconstraint : Constraint(constraintID == \"A constraintID from DB\")\r\n" +
                            "\tBoolean(booleanValue == Boolean.FALSE) from constraint.apply()\r\n" +
                            "then\r\n" +
                            "\tPropositions.setProposition(\"A\", true);\r\n" +
                            "end";
        
        System.out.println("Char count expected " + expected.length());
        String actual = rt.process("RuleTemplate.vm");
        
        System.out.println("\nChar count actual " + actual.length());
        assertEquals(expected , actual);
    }

}
