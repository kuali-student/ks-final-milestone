package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessage;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessageContainer;
import org.kuali.student.rules.ruleexecution.runtime.drools.DroolsRuleBase;
import org.kuali.student.rules.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;

public class BooleanMessageBuilderTest {
	private static SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    private final static DroolsRuleBase ruleBase = new DroolsRuleBase();
    private BooleanMessageBuilder builder;

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
		builder = new BooleanMessageBuilder(executor);
	}
	
	@Test
	public void testBuildMessage1_True() throws Exception {
		BooleanMessageContainer messageContainer = new BooleanMessageContainer();
		BooleanMessage msg1 = new BooleanMessage("M1", true, "Message 1 is true", "Message 1 is false");
		BooleanMessage msg2 = new BooleanMessage("M2", true, "Message 2 is true", "Message 2 is false");
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		
		String message = builder.buildMessage("M1*M2", messageContainer);
		Assert.assertEquals("Message 1 is true AND Message 2 is true", message);
	}

	@Test
	public void testBuildMessage2_True() throws Exception {
		BooleanMessageContainer messageContainer = new BooleanMessageContainer();
		BooleanMessage msg1 = new BooleanMessage("M1", true, "Message 1 is true", "Message 1 is false");
		BooleanMessage msg2 = new BooleanMessage("M2", false, "Message 2 is true", "Message 2 is false");
		BooleanMessage msg3 = new BooleanMessage("M3", true, "Message 3 is true", "Message 3 is false");
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		messageContainer.addMessage(msg3);
		
		String message = builder.buildMessage("(M1*M2)+M3", messageContainer);
		Assert.assertEquals("Message 3 is true", message);
	}

	@Test
	public void testBuildMessage3_False() throws Exception {
		BooleanMessageContainer messageContainer = new BooleanMessageContainer();
		BooleanMessage msg1 = new BooleanMessage("M1", true, "Message 1 is true", "Must have MATH 100");
		BooleanMessage msg2 = new BooleanMessage("M2", false, "Message 2 is true", "Must have CHEM 101");
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		
		String message = builder.buildMessage("M1*M2", messageContainer);
		Assert.assertEquals("Must have CHEM 101", message);
	}

	@Test
	public void testBuildMessage4_False() throws Exception {
		BooleanMessageContainer messageContainer = new BooleanMessageContainer();
		BooleanMessage msg1 = new BooleanMessage("M1", true, "Requirement Fulfilled 1", "Must have MATH 100");
		BooleanMessage msg2 = new BooleanMessage("M2", false, "Requirement Fulfilled 2", "Must have CHEM 101");
		BooleanMessage msg3 = new BooleanMessage("M3", false, "Requirement Fulfilled 3", "Must have PHYS 101");
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		messageContainer.addMessage(msg3);
		
		String message = builder.buildMessage("(M1*M2)+M3", messageContainer);
		Assert.assertEquals("Must have CHEM 101 OR Must have PHYS 101", message);
	}
}
