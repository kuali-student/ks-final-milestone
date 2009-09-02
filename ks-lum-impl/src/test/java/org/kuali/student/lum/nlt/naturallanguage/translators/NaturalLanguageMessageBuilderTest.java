package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageMessageBuilder;

public class NaturalLanguageMessageBuilderTest {

	private SimpleExecutorDroolsImpl executor;
	private Map<String, BooleanOperators> booleanLanguageMap;
	
    @Before
    public void setUp() throws Exception {
    	this.executor = new SimpleExecutorDroolsImpl();
    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
		executor.setEnableStatisticsLogging(false);
		executor.setRuleBaseCache(ruleBase);

		booleanLanguageMap = new HashMap<String, BooleanOperators>();
		booleanLanguageMap.put("dk", new BooleanOperators("og", "eller"));
		booleanLanguageMap.put("fr", new BooleanOperators("et", "ou"));
		booleanLanguageMap.put("de", new BooleanOperators("und", "oder"));
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @Test
	public void testConstructor1_BuildMessage_Success() throws Exception {
		NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder(executor);
		
		MessageContainer messageContainer = new MessageContainer();
		String msg1 = "Student has completed MATH 100";
		String msg2 = "Student has completed MATH 200";
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, msg1);
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, msg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);

		String message = messageBuilder.buildMessage("A*B", messageContainer);
		Assert.assertEquals(msg1 + " and " + msg2, message);
	}

    @Test
	public void testConstructor2_BuildMessage_Success() throws Exception {
    	NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder(executor, "dk", booleanLanguageMap);
    	
		MessageContainer messageContainer = new MessageContainer();
		String msg1 = "Student has completed MATH 100";
		String msg2 = "Student has completed MATH 200";
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, msg1);
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, msg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);

		String message = messageBuilder.buildMessage("A*B", messageContainer);
		Assert.assertEquals(msg1 + " og " + msg2, message);
	}

    @Test
	public void testConstructor3_BuildMessage_Success() throws Exception {
    	NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder(executor, "fr", booleanLanguageMap);
    	
		MessageContainer messageContainer = new MessageContainer();
		String msg1 = "Etudiant a termine MATH 100";
		String msg2 = "Etudiant a termine MATH 200";
		BooleanMessage bm1 = new BooleanMessageImpl("A", true, msg1);
		BooleanMessage bm2 = new BooleanMessageImpl("B", true, msg2);
		messageContainer.addMessage(bm1);
		messageContainer.addMessage(bm2);

		String message = messageBuilder.buildMessage("A+B", messageContainer);
		Assert.assertEquals(msg1 + " ou " + msg2, message);
	}

    @Test
	public void testConstructor4_BuildMessage_Failure() throws Exception {
    	try {
    		NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder(executor, "xx", booleanLanguageMap);
    		Assert.fail("NaturalLanguageMessageBuilder should have thrown an exception language keys do not match");
    	} catch(Exception e) {
    		Assert.assertTrue(true);
    	}
    	
	}
}
