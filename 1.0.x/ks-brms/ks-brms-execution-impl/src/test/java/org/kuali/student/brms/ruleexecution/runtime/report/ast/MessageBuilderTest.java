package org.kuali.student.brms.ruleexecution.runtime.report.ast;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.tools.generic.DateTool;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

public class MessageBuilderTest {
	private static SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    private final static DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
    private MessageBuilder builder;

    @BeforeClass
    public static void setUpOnce() throws Exception {
		executor.setEnableStatisticsLogging(true);
    	executor.setRuleBaseCache(ruleBase);
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    	executor = null;
    }

	@Before
	public void setUp() throws Exception {
		builder = new MessageBuilderImpl(executor);
	}

	@Test
	public void testBuildMessage1_InvalidBooleanExpression() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, "Message 2 is true");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", true, "Message 3 is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);

		try {
			builder.buildMessage("M1**M2**M3", messageContainer);
			Assert.fail("Building message should have failed due to boolean expression syntax error");
		} catch(MessageBuilderException e) {
			Assert.assertTrue(e.getMessage().startsWith("Building message failed: Boolean Function Parser Error. Invalid Boolean Expression: 'M1**M2**M3'"));
		}
	}

	@Test
	public void testBuildMessage1_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, "Message 2 is true");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", true, "Message 3 is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		String message = builder.buildMessage("M1*M2*M3", messageContainer);
		Assert.assertEquals("Message 1 is true AND Message 2 is true AND Message 3 is true", message);
	}

	@Test
	public void testBuildMessage2_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, "Message 2 is false");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", true, "Message 3 is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		String message = builder.buildMessage("(M1*M2)+M3", messageContainer);
		Assert.assertEquals("Message 3 is true", message);
	}

	@Test
	public void testBuildMessage3_False() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, "Must have CHEM 101");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		
		String message = builder.buildMessage("M1*M2", messageContainer);
		Assert.assertEquals("Must have CHEM 101", message);
	}

	@Test
	public void testBuildMessage4_False() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Requirement Fulfilled 1");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, "Must have CHEM 101");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", false, "Must have PHYS 101");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		BooleanFunctionResult result = builder.build("(M1*M2)+M3", messageContainer);
		Assert.assertFalse(result.getResult());
		Assert.assertEquals("Must have CHEM 101 OR Must have PHYS 101", result.getMessage());
	}

	@Test
	public void testBuildMessage5_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, "Message 2 is true");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", false, "Message 3 is false");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		// result = True : M1*M2+M3 = T and T or F -> (M1*M2)+M3 (T and T) or F
		String bf = "M1*M2+M3";
		BooleanFunctionResult result = builder.build(bf, messageContainer);
		// Order of boolean operation: AND before OR, operations inside parentheses before anything else
		Assert.assertEquals(bf, result.getBooleanFunction());
		Assert.assertTrue(result.getResult());
		Assert.assertEquals("Message 1 is true AND Message 2 is true", result.getMessage());
	}

	@Test
	public void testBuildMessage6() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, "Message 2 is true");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", false, "Message 3 is false");
		BooleanMessage bm4 = new BooleanMessageImpl("M4", false, "Message 4 is false");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		BooleanFunctionResult result1 = builder.build("M1*M2+M3*M4", messageContainer);
		BooleanFunctionResult result2 = builder.build("(M1*M2)+(M3*M4)", messageContainer);
		BooleanFunctionResult result3 = builder.build("((M1*M2)+M3)*M4", messageContainer);
		
		// Order of boolean operation: ANDs before ORs and 
		// operations inside parentheses before anything else.
		// result1 = True  : T and T or F and F
		// result2 = True  : (T and T) or (F and F)
		// result1 = False : ((T and T) or F) and F
		Assert.assertEquals("M1*M2+M3*M4", result1.getBooleanFunction());
		Assert.assertEquals("(M1*M2)+(M3*M4)", result2.getBooleanFunction());
		Assert.assertEquals("((M1*M2)+M3)*M4", result3.getBooleanFunction());
		Assert.assertTrue(result1.getResult());
		Assert.assertTrue(result2.getResult());
		Assert.assertFalse(result3.getResult());
		Assert.assertTrue(result1.getResult() == result2.getResult());
		Assert.assertFalse(result1.getResult() == result3.getResult());
		Assert.assertFalse(result2.getResult() == result3.getResult());
		Assert.assertTrue(result1.getMessage().equals(result2.getMessage()));
		Assert.assertFalse(result1.getMessage().equals(result3.getMessage()));
		Assert.assertFalse(result2.getMessage().equals(result3.getMessage()));
	}

	@Test
	public void testBuildMessage7() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A is true");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B is true");
		BooleanMessage bm3 = new BooleanMessageImpl("C", true, "C is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		String message = builder.buildMessage("(A*B)+(A*C)+(B*C)", messageContainer);
		Assert.assertEquals("(A is true AND B is true) OR (A is true AND C is true) OR (B is true AND C is true)", message);
	}

	@Test
	public void testBuildMessage8() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", true, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", true, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("A*(B+C+D)", messageContainer);
		Assert.assertEquals("A AND (B OR C OR D)", message);
	}
	
	@Test
	public void testBuildMessage9() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", true, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", true, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("A*(B+(C+D))", messageContainer);
		Assert.assertEquals("A AND (B OR C OR D)", message);
	}
	
	@Test
	public void testBuildMessage10() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", true, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", true, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("(A*(B+C))+D", messageContainer);
		Assert.assertEquals("(A AND (B OR C)) OR D", message);
	}
	
	@Test
	public void testBuildMessage11() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", false, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", false, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("(A*(B+C))+D", messageContainer);
		Assert.assertEquals("A AND B", message);
	}
	
	@Test
	public void testBuildMessage12() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", true, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", true, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("(A*B)+(C+D)", messageContainer);
		Assert.assertEquals("(A AND B) OR C OR D", message);
	}
	
	@Test
	public void testBuildMessage13() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, "A");
		BooleanMessage bm2 = new BooleanMessageImpl("B", false, "B");
		BooleanMessage bm3 = new BooleanMessageImpl("C", false, "C");
		BooleanMessage bm4 = new BooleanMessageImpl("D", false, "D");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		messageContainer.addMessage(bm4);
		
		String message = builder.buildMessage("(A*B)+(C+D)", messageContainer);
		Assert.assertEquals("B OR C OR D", message);
	}
	
	@Test
	public void testBuildMessageTemplate1_NullContextMap_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, "Message 2 is true");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", true, "Message 3 is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		BooleanFunctionResult result = builder.build("M1*M2*M3", messageContainer, null);
		Assert.assertEquals("Message 1 is true AND Message 2 is true AND Message 3 is true", result.getMessage());
	}

	@Test
	public void testBuildMessageTemplate2_NullContextMap_False() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, "Message 1 is true");
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, "Message 2 is false");
		BooleanMessage bm3 = new BooleanMessageImpl("M3", true, "Message 3 is true");
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		messageContainer.addMessage(bm3);
		
		BooleanFunctionResult result = builder.build("M1*M2*M3", messageContainer, null);
		Assert.assertEquals("Message 2 is false", result.getMessage());
	}

	@Test
	public void testBuildMessageTemplate3_True() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100");
		map.put("resultValue", "60");

		MessageContainer messageContainer = new MessageContainer();
		String successMsg1 = "Date created $dateTool.get('yyyy-MM-dd')";
		String successMsg2 = "#set( $difference = ( $mathTool.toNumber($expectedValue) - $mathTool.toNumber($resultValue)) )" +
				"expectedValue=$expectedValue, resultValue=$resultValue, difference=$difference";
		BooleanMessage bm1 = new BooleanMessageImpl("M1", true, successMsg1);
		BooleanMessage bm2 = new BooleanMessageImpl("M2", true, successMsg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		
		BooleanFunctionResult result = builder.build("M1*M2", messageContainer, map);
		Assert.assertEquals("Date created " + new DateTool().get("yyyy-MM-dd") + 
				" AND expectedValue=100, resultValue=60, difference=40", result.getMessage());
	}

	@Test
	public void testBuildMessageTemplate4_False() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100");
		map.put("resultValue", "60");

		MessageContainer messageContainer = new MessageContainer();
		String failureMsg1 = "Date created $dateTool.get('yyyy-MM-dd')";
		String failureMsg2 = "#set( $difference = ( $mathTool.toNumber($expectedValue) - $mathTool.toNumber($resultValue)) )" +
				"expectedValue=$expectedValue, resultValue=$resultValue, difference=$difference";
		BooleanMessage bm1 = new BooleanMessageImpl("M1", false, failureMsg1);
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, failureMsg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);
		
		BooleanFunctionResult result = builder.build("M1*M2", messageContainer, map);
		Assert.assertEquals("Date created " + new DateTool().get("yyyy-MM-dd") + 
				" AND expectedValue=100, resultValue=60, difference=40", result.getMessage());
	}

	@Test
	public void testBuildMessageTemplate5_False_Exception() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expectedValue", "100");
		map.put("resultValue", "60");

		MessageContainer messageContainer = new MessageContainer();
		String failureMsg1 = "Date created $dateTool.get('yyyy-MM-dd HH:mm:ss z')";
		// VTL syntax error
		String failureMsg2 = "#set( $difference = expectedValue=$expectedValue, resultValue=$resultValue, difference=$difference";
		BooleanMessage bm1 = new BooleanMessageImpl("M1", false, failureMsg1);
		BooleanMessage bm2 = new BooleanMessageImpl("M2", false, failureMsg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);

		try {
			builder.build("M1*M2", messageContainer, map);
			Assert.fail("Building message should have failed due to VTL syntax error");
		} catch(MessageBuilderException e) {
			Assert.assertTrue(true);
		}
	}
}
