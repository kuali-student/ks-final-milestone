package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessage;
import org.kuali.student.rules.internal.common.runtime.ast.MessageContainer;
import org.kuali.student.rules.internal.common.runtime.ast.Message;
import org.kuali.student.rules.ruleexecution.runtime.drools.DroolsRuleBase;
import org.kuali.student.rules.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;

public class MessageBuilderTest {
	private static SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    private final static DroolsRuleBase ruleBase = new DroolsRuleBase();
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
		builder = new MessageBuilder(executor);
	}
	
	private static class MessageMock implements Message {
		private String id;
		private BooleanMessage message;
		
		public MessageMock(String id, BooleanMessage message) {
			this.id = id;
			this.message = message;
		}
		
		public String getMessageId() {
			return this.id;
		}
	    public BooleanMessage getBooleanMessage() {
	    	return this.message;
	    }
	}
	
	@Test
	public void testBuildMessage1_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessage(true, "Message 1 is true", "Message 1 is false");
		BooleanMessage bm2 = new BooleanMessage(true, "Message 2 is true", "Message 2 is false");
		MessageMock msg1 = new MessageMock("M1", bm1);
		MessageMock msg2 = new MessageMock("M2", bm2);
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		
		String message = builder.buildMessage("M1*M2", messageContainer);
		Assert.assertEquals("Message 1 is true AND Message 2 is true", message);
	}

	@Test
	public void testBuildMessage2_True() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessage(true, "Message 1 is true", "Message 1 is false");
		BooleanMessage bm2 = new BooleanMessage(false, "Message 2 is true", "Message 2 is false");
		BooleanMessage bm3 = new BooleanMessage(true, "Message 3 is true", "Message 3 is false");
		MessageMock msg1 = new MessageMock("M1", bm1);
		MessageMock msg2 = new MessageMock("M2", bm2);
		MessageMock msg3 = new MessageMock("M3", bm3);
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		messageContainer.addMessage(msg3);
		
		String message = builder.buildMessage("(M1*M2)+M3", messageContainer);
		Assert.assertEquals("Message 3 is true", message);
	}

	@Test
	public void testBuildMessage3_False() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessage(true, "Message 1 is true", "Must have MATH 100");
		BooleanMessage bm2 = new BooleanMessage(false, "Message 2 is true", "Must have CHEM 101");
		MessageMock msg1 = new MessageMock("M1", bm1);
		MessageMock msg2 = new MessageMock("M2", bm2);
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		
		String message = builder.buildMessage("M1*M2", messageContainer);
		Assert.assertEquals("Must have CHEM 101", message);
	}

	@Test
	public void testBuildMessage4_False() throws Exception {
		MessageContainer messageContainer = new MessageContainer();
		BooleanMessage bm1 = new BooleanMessage(true, "Requirement Fulfilled 1", "Must have MATH 100");
		BooleanMessage bm2 = new BooleanMessage(false, "Requirement Fulfilled 2", "Must have CHEM 101");
		BooleanMessage bm3 = new BooleanMessage(false, "Requirement Fulfilled 3", "Must have PHYS 101");
		MessageMock msg1 = new MessageMock("M1", bm1);
		MessageMock msg2 = new MessageMock("M2", bm2);
		MessageMock msg3 = new MessageMock("M3", bm3);
		messageContainer.addMessage(msg1);
		messageContainer.addMessage(msg2);
		messageContainer.addMessage(msg3);
		
		String message = builder.buildMessage("(M1*M2)+M3", messageContainer);
		Assert.assertEquals("Must have CHEM 101 OR Must have PHYS 101", message);
	}
}
