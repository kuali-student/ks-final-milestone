/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.statement.naturallanguage.translators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.booleanmessage.BooleanMessage;
import org.kuali.student.common.messagebuilder.booleanmessage.MessageContainer;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanMessageImpl;
import org.kuali.student.common.messagebuilder.impl.BooleanOperators;
import org.kuali.student.core.statement.naturallanguage.translators.NaturalLanguageMessageBuilder;

public class NaturalLanguageMessageBuilderTest {

//	private static SimpleExecutorDroolsImpl executor;
	private static Map<String, BooleanOperators> booleanLanguageMap;
	
    @BeforeClass
    public static void setUp() throws Exception {
//    	executor = new SimpleExecutorDroolsImpl();
//    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
//		executor.setRuleBaseCache(ruleBase);

		booleanLanguageMap = new HashMap<String, BooleanOperators>();
		booleanLanguageMap.put("dk", new BooleanOperators("og", "eller"));
		booleanLanguageMap.put("fr", new BooleanOperators("et", "ou"));
		booleanLanguageMap.put("de", new BooleanOperators("und", "oder"));
    }
    
    @Test
	public void testConstructor1_BuildMessage_Success() throws Exception {
		NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder();
		
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
    	NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder("dk", booleanLanguageMap);
    	
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
    	NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder("fr", booleanLanguageMap);
    	
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
    		NaturalLanguageMessageBuilder messageBuilder = new NaturalLanguageMessageBuilder("xx", booleanLanguageMap);
    		Assert.fail("NaturalLanguageMessageBuilder should have thrown an exception language keys do not match");
    	} catch(Exception e) {
    		Assert.assertTrue(true);
    	}
    	
	}
}
