package org.kuali.student.rules.runtime.ast;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.runtime.ast.GenerateRuleFailureMessage;

import static org.junit.Assert.assertEquals;

public class GenerateRuleFailureMessageTest {
	
	private HashMap<String, Boolean> nodeValueMap;
	private HashMap<String, String> nodeFailureMessageMap;
	private String functionString;
	
	@Before
	public void setUp() throws Exception {
		nodeValueMap = new HashMap<String, Boolean>();
		nodeFailureMessageMap = new HashMap<String, String>();
	}
	
	@Test
	public void testExecuteRuleFailureMessage1()
	{
		functionString = "A*B*C*D";
    	
		nodeValueMap.put("A", false);
		nodeValueMap.put("B", true);
		nodeValueMap.put("C", true);
		nodeValueMap.put("D", true);
		
		nodeFailureMessageMap.put("A", "Need MATH 200");
		nodeFailureMessageMap.put("B", "Need MATH 110");
		nodeFailureMessageMap.put("C", "Need 15 credits or more of 1st year science");
		nodeFailureMessageMap.put("D", "Need English 6000");
		
		String expected = "Need MATH 200";
		String actual = GenerateRuleFailureMessage.executeRule(functionString, nodeValueMap, nodeFailureMessageMap);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testExecuteRuleFailureMessage2()
	{
		functionString = "A+(B*C)+D";
    	
		nodeValueMap.put("A", false);
		nodeValueMap.put("B", true);
		nodeValueMap.put("C", false);
		nodeValueMap.put("D", false);
		
		nodeFailureMessageMap.put("A", "Need MATH 200");
		nodeFailureMessageMap.put("B", "Need MATH 110");
		nodeFailureMessageMap.put("C", "Need 15 credits or more of 1st year science");
		nodeFailureMessageMap.put("D", "Need English 6000");
		
		String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
		String actual = GenerateRuleFailureMessage.executeRule(functionString, nodeValueMap, nodeFailureMessageMap);
		
		assertEquals(expected, actual);
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
