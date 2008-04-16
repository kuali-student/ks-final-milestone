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
        lhs.add("n : BooleanNode()" );
        rt.setLHS(lhs);
        
        ArrayList<String> rhs = new ArrayList<String>();
        rhs.add("String logMessage = n.getLeftNode().getRuleFailureMessage() + \" OR \" + n.getRightNode().getRuleFailureMessage();"  );
        rhs.add("n.setRuleFailureMessage(logMessage);");
        rhs.add("System.out.println( n.getRuleFailureMessage() + \"   rule OR\");"  );
        rhs.add("BooleanNode parent = n.getParent();"  );
        rhs.add("if (parent != null){"  );
        rhs.add("    update(parent);"   );
        rhs.add("}"  );
        
        rt.setRHS(rhs);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcess() {
		String expected = "\r\nrule \"The Rule Name\"\r\n" +
							"\t#no-loop true\r\n" +
							"\t#salience 1\r\n"  +
							"when\r\n" +
							"\tn : BooleanNode()\r\n" +
							"then\r\n" +
							"\tString logMessage = n.getLeftNode().getRuleFailureMessage() + \" OR \" + n.getRightNode().getRuleFailureMessage();\r\n" +
							"\tn.setRuleFailureMessage(logMessage);\r\n" +
							"\tSystem.out.println( n.getRuleFailureMessage() + \"   rule OR\");\r\n" +
							"\tBooleanNode parent = n.getParent();\r\n" +
							"\tif (parent != null){\r\n" +
							"\t    update(parent);\r\n" +
							"\t}\r\n" +
							"end";
		
		System.out.println("Char count expected " + expected.length());
		String actual = rt.process("Test.vm");
		
		System.out.println("\nChar count actual " + actual.length());
		assertEquals(expected , actual);
	}

}
