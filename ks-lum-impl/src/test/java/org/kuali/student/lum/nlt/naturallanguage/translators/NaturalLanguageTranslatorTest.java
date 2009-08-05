package org.kuali.student.lum.nlt.naturallanguage.translators;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageMessageBuilder;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageTranslatorImpl;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class NaturalLanguageTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;
	private NaturalLanguageTranslatorImpl englishTranslator;
	private NaturalLanguageTranslatorImpl germanTranslator;
	private NaturalLanguageTranslatorImpl japaneseTranslator;

	private String cluSetId1 = "CLUSET-NL-1";
	private ReqComponent reqComponent;

    @Before
    public void setUp() throws Exception {
    	createTranslator();
    	this.reqComponent = luDao.fetch(ReqComponent.class, "REQCOMP-NL-1");
    }

    @After
    public void tearDown() throws Exception {
    }
    
    private void createTranslator() {
    	this.englishTranslator = new NaturalLanguageTranslatorImpl();
    	this.englishTranslator.setLanguage("en");
    	this.germanTranslator = new NaturalLanguageTranslatorImpl();
    	this.germanTranslator.setLanguage("de");
    	this.japaneseTranslator = new NaturalLanguageTranslatorImpl();
    	this.japaneseTranslator.setLanguage("jp");
    	
    	ContextRegistry<Context<ReqComponent>> reqComponentContextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(this.luDao);
    	ContextRegistry<Context<LuStatementAnchor>> statementContextRegistry = NaturalLanguageUtil.getStatementContextRegistry(this.luDao);

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

		NaturalLanguageMessageBuilder englishMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", booleanLanguageMap);

    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
    	englishReqComponentTranslator.setLuDao(this.luDao);
    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	englishReqComponentTranslator.setLanguage("en");

    	StatementTranslator englishStatementTranslator = new StatementTranslator();
    	englishStatementTranslator.setLuDao(this.luDao);
    	englishStatementTranslator.setContextRegistry(statementContextRegistry);
    	englishStatementTranslator.setReqComponentTranslator(englishReqComponentTranslator);
    	englishStatementTranslator.setMessageBuilder(englishMessageBuilder);
    	englishStatementTranslator.setLanguage("en");

    	this.englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
    	this.englishTranslator.setStatementTranslator(englishStatementTranslator);

		NaturalLanguageMessageBuilder germanMessageBuilder = new NaturalLanguageMessageBuilder(executor, "de", booleanLanguageMap);

    	ReqComponentTranslator germanReqComponentTranslator = new ReqComponentTranslator();
    	germanReqComponentTranslator.setLuDao(this.luDao);
    	germanReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	germanReqComponentTranslator.setLanguage("de");

    	StatementTranslator germanStatementTranslator = new StatementTranslator();
    	germanStatementTranslator.setLuDao(this.luDao);
    	germanStatementTranslator.setContextRegistry(statementContextRegistry);
    	germanStatementTranslator.setReqComponentTranslator(germanReqComponentTranslator);
    	germanStatementTranslator.setMessageBuilder(germanMessageBuilder);
    	germanStatementTranslator.setLanguage("de");

    	this.germanTranslator.setReqComponentTranslator(germanReqComponentTranslator);
    	this.germanTranslator.setStatementTranslator(germanStatementTranslator);

		NaturalLanguageMessageBuilder japaneseMessageBuilder = new NaturalLanguageMessageBuilder(executor, "jp", booleanLanguageMap);

    	ReqComponentTranslator japaneseReqComponentTranslator = new ReqComponentTranslator();
    	japaneseReqComponentTranslator.setLuDao(this.luDao);
    	japaneseReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	japaneseReqComponentTranslator.setLanguage("jp");

    	StatementTranslator japaneseStatementTranslator = new StatementTranslator();
    	japaneseStatementTranslator.setLuDao(this.luDao);
    	japaneseStatementTranslator.setContextRegistry(statementContextRegistry);
    	japaneseStatementTranslator.setReqComponentTranslator(japaneseReqComponentTranslator);
    	japaneseStatementTranslator.setMessageBuilder(japaneseMessageBuilder);
    	japaneseStatementTranslator.setLanguage("jp");

    	this.japaneseTranslator.setReqComponentTranslator(japaneseReqComponentTranslator);
    	this.japaneseTranslator.setStatementTranslator(japaneseStatementTranslator);
    }

	@Test
	public void testTranslateReqComponent_English() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		NaturalLanguageUtil.createReqComponentFields(this.luDao, this.reqComponent, "1", "greater_than_or_equal_to", this.cluSetId1);
		
		String text = englishTranslator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_German() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		NaturalLanguageUtil.createReqComponentFields(this.luDao, this.reqComponent, "1", "greater_than_or_equal_to", this.cluSetId1);
		
		String text = germanTranslator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_EnglishGerman() throws DoesNotExistException, OperationFailedException {
		String nlUsageTypeKey = "KUALI.CATALOG";
		NaturalLanguageUtil.createReqComponentFields(this.luDao, this.reqComponent, "1", "greater_than_or_equal_to", this.cluSetId1);
		
		String text = englishTranslator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", text);
		englishTranslator.setLanguage("de");

		text = englishTranslator.translateReqComponent(reqComponent.getId(), nlUsageTypeKey);
		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", text);
	}

	@Test
	public void testTranslateReqComponent_InvalidReqComponentId() throws DoesNotExistException, OperationFailedException {
		try {
			englishTranslator.translateReqComponent("InvalidId", "KUALI.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'InvalidId' is not a valid requirement component id");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateReqComponent_InvalidNlUsageTypeKey() throws DoesNotExistException, OperationFailedException {
		try {
			englishTranslator.translateReqComponent(reqComponent.getId(), "KUALI.xxx.CATALOG");
			Assert.fail("Requirement component translation should have failed since 'KUALI.xxx.CATALOG' is not a valid nlUsageTypeKey");
		} catch (DoesNotExistException e) {
			Assert.assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testTranslateStatement_English() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatement_German() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = germanTranslator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatement_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);

		englishTranslator.setLanguage("de");
		naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);

		englishTranslator.setLanguage("en");
		naturalLanguage = englishTranslator.translateStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testTranslateStatementTree_English() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = englishTranslator.translateToTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testTranslateStatementTree_German() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = germanTranslator.translateToTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testTranslateStatementTree_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NLTranslationNodeInfo rootNode = englishTranslator.translateToTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
		englishTranslator.setLanguage("de");

		rootNode = englishTranslator.translateToTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}
}
