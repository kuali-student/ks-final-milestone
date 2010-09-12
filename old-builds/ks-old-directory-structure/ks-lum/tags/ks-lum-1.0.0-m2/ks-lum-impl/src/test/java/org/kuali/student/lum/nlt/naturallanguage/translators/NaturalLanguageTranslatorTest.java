/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.translators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.LuServiceMock;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageMessageBuilder;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageTranslatorImpl;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

public class NaturalLanguageTranslatorTest {

	private static LuServiceMock luService = new LuServiceMock();

	private static NaturalLanguageMessageBuilder englishMessageBuilder; 
	private static NaturalLanguageMessageBuilder germanMessageBuilder; 
	private static NaturalLanguageMessageBuilder japaneseMessageBuilder; 
	
	private NaturalLanguageTranslatorImpl englishTranslator = new NaturalLanguageTranslatorImpl();
	private NaturalLanguageTranslatorImpl germanTranslator = new NaturalLanguageTranslatorImpl();
	private NaturalLanguageTranslatorImpl japaneseTranslator = new NaturalLanguageTranslatorImpl();

	private String cluSetId1 = "CLUSET-NL-1";
	private static CustomReqComponentInfo reqComponent;
	private static CustomLuStatementInfo luStatement;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	createMessageBuilder();
    	createReqComponent();
    	createLuStatement();
    	luService.clear();
    	luService.addAll(NaturalLanguageUtil.createData());
    }

    @Before
    public void setUp() throws Exception {
    	createTranslator();
    }
    
    private static void createReqComponent() throws Exception {
    	reqComponent = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
    	reqComponent.setId("REQCOMP-NL-1");
    }

    private static void createLuStatement() throws Exception {
    	luStatement = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.OR);
    	luStatement.setId("STMT-5");

    	CustomReqComponentInfo req1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
    	req1.setId("REQCOMP-NL-1");
    	List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
    	req1.setReqCompFields(fieldList1);

    	CustomReqComponentInfo req2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
    	req2.setId("REQCOMP-NL-4");
    	List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
    	req2.setReqCompFields(fieldList2);

    	List<CustomReqComponentInfo> requiredComponents = new ArrayList<CustomReqComponentInfo>();
    	requiredComponents.add(req1);
    	requiredComponents.add(req2);

    	luStatement.setRequiredComponents(requiredComponents);
    }
    
    private static void createMessageBuilder() {
    	SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
		executor.setEnableStatisticsLogging(false);
		executor.setRuleBaseCache(ruleBase);

		Map<String, BooleanOperators> booleanLanguageMap = new HashMap<String, BooleanOperators>();
		booleanLanguageMap.put("dk", new BooleanOperators("og", "eller"));
		booleanLanguageMap.put("fr", new BooleanOperators("et", "ou"));
		booleanLanguageMap.put("de", new BooleanOperators("und", "oder"));
		booleanLanguageMap.put("en", new BooleanOperators("and", "or"));
		booleanLanguageMap.put("jp", new BooleanOperators("XandX", "XorX"));

		englishMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", booleanLanguageMap);
		germanMessageBuilder = new NaturalLanguageMessageBuilder(executor, "de", booleanLanguageMap);
		japaneseMessageBuilder = new NaturalLanguageMessageBuilder(executor, "jp", booleanLanguageMap);
    }
    
    private void createTranslator() {
    	englishTranslator.setLanguage("en");
    	germanTranslator.setLanguage("de");
    	japaneseTranslator.setLanguage("jp");
    	
    	ContextRegistry<Context<CustomReqComponentInfo>> reqComponentContextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(luService);
    	ContextRegistry<Context<LuStatementAnchor>> statementContextRegistry = NaturalLanguageUtil.getStatementContextRegistry(luService);

    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	englishReqComponentTranslator.setLanguage("en");

    	StatementTranslator englishStatementTranslator = new StatementTranslator();
    	englishStatementTranslator.setContextRegistry(statementContextRegistry);
    	englishStatementTranslator.setReqComponentTranslator(englishReqComponentTranslator);
    	englishStatementTranslator.setMessageBuilder(englishMessageBuilder);
    	englishStatementTranslator.setLanguage("en");

    	englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
    	englishTranslator.setStatementTranslator(englishStatementTranslator);

    	ReqComponentTranslator germanReqComponentTranslator = new ReqComponentTranslator();
    	germanReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	germanReqComponentTranslator.setLanguage("de");

    	StatementTranslator germanStatementTranslator = new StatementTranslator();
    	germanStatementTranslator.setContextRegistry(statementContextRegistry);
    	germanStatementTranslator.setReqComponentTranslator(germanReqComponentTranslator);
    	germanStatementTranslator.setMessageBuilder(germanMessageBuilder);
    	germanStatementTranslator.setLanguage("de");

    	germanTranslator.setReqComponentTranslator(germanReqComponentTranslator);
    	germanTranslator.setStatementTranslator(germanStatementTranslator);

    	ReqComponentTranslator japaneseReqComponentTranslator = new ReqComponentTranslator();
    	japaneseReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	japaneseReqComponentTranslator.setLanguage("jp");

    	StatementTranslator japaneseStatementTranslator = new StatementTranslator();
    	japaneseStatementTranslator.setContextRegistry(statementContextRegistry);
    	japaneseStatementTranslator.setReqComponentTranslator(japaneseReqComponentTranslator);
    	japaneseStatementTranslator.setMessageBuilder(japaneseMessageBuilder);
    	japaneseStatementTranslator.setLanguage("jp");

    	japaneseTranslator.setReqComponentTranslator(japaneseReqComponentTranslator);
    	japaneseTranslator.setStatementTranslator(japaneseStatementTranslator);
    }

	@Test
	public void testTranslateReqComponent_English() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", this.cluSetId1);
		reqComponent.setReqCompFields(fieldList);
		
		String text = englishTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_German() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", this.cluSetId1);
		reqComponent.setReqCompFields(fieldList);
		
		String text = germanTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_EnglishGerman() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", this.cluSetId1);		
		reqComponent.setReqCompFields(fieldList);
		String text = englishTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", text);
		englishTranslator.setLanguage("de");

		text = englishTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);
		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_NullReqComponent() throws DoesNotExistException, OperationFailedException {
		try {
			englishTranslator.translateReqComponent(null, "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since requirement component is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateReqComponent_InvalidNlUsageTypeKey() throws DoesNotExistException, OperationFailedException {
		try {
			englishTranslator.translateReqComponent(reqComponent, "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatement_NullStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		try {
			englishTranslator.translateStatement("CLU-NL-1", null, "KUALI.CATALOG");
			Assert.fail("Statement translation should have failed since statement is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatementTree_NullStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		try {
			englishTranslator.translateToTree("CLU-NL-1", null, "KUALI.CATALOG");
			Assert.fail("Tree node translation should have failed since statement is null");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatement_English() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatement_German() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = germanTranslator.translateStatement("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatement_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);

		englishTranslator.setLanguage("de");
		naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);

		englishTranslator.setLanguage("en");
		naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatementTree_English() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = englishTranslator.translateToTree("CLU-NL-1", luStatement, "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testTranslateStatementTree_German() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = germanTranslator.translateToTree("CLU-NL-1", luStatement, "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testTranslateStatementTree_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = englishTranslator.translateToTree("CLU-NL-1", luStatement, "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
		englishTranslator.setLanguage("de");

		rootNode = englishTranslator.translateToTree("CLU-NL-1", luStatement, "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}
}
