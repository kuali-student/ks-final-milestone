package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.Arrays;
import java.util.List;

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
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageMessageBuilder;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class StatementTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;
	private StatementTranslator englishTranslator;
	private StatementTranslator germanTranslator;
	private static StatementTestUtil statementTestUtil;
	
    @Before
    public void setUp() throws Exception {
    	statementTestUtil = new StatementTestUtil();
    	statementTestUtil.setLuDao(luDao);
    	createTranslator();

    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    private void createTranslator() {
    	ContextRegistry<Context<ReqComponent>> reqComponentContextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(this.luDao);
    	ContextRegistry<Context<LuStatementAnchor>> statementContextRegistry = NaturalLanguageUtil.getStatementContextRegistry(this.luDao);

    	SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
		executor.setEnableStatisticsLogging(false);
		executor.setRuleBaseCache(ruleBase);

		NaturalLanguageMessageBuilder englishMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", new BooleanOperators("and", "or"));

    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	englishReqComponentTranslator.setLanguage("en");

    	this.englishTranslator = new StatementTranslator();
    	this.englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
		this.englishTranslator.setContextRegistry(statementContextRegistry);
		this.englishTranslator.setMessageBuilder(englishMessageBuilder);
		this.englishTranslator.setLanguage("en");

		NaturalLanguageMessageBuilder germanMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", new BooleanOperators("and", "or"));

    	ReqComponentTranslator germanReqComponentTranslator = new ReqComponentTranslator();
    	germanReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	germanReqComponentTranslator.setLanguage("de");

    	this.germanTranslator = new StatementTranslator();
    	this.germanTranslator.setReqComponentTranslator(germanReqComponentTranslator);
		this.germanTranslator.setContextRegistry(statementContextRegistry);
		this.germanTranslator.setMessageBuilder(germanMessageBuilder);
		this.germanTranslator.setLanguage("de");
    }

	@Test
	public void testTranslateStatement1_English() throws Exception {
		// Rule = R1
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		reqComp.setId("req-1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		this.luDao.create(stmt1);
		
		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_German() throws Exception {
		// Rule = R1
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		reqComp.setId("req-1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		this.luDao.create(stmt1);
		
		String translation = germanTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_EnglishGerman() throws Exception {
		// Rule = R1
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		reqComp.setId("req-1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		this.luDao.create(stmt1);
		
		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180", translation);

		englishTranslator.setLanguage("de");
		translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");
		Assert.assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180", translation);

		englishTranslator.setLanguage("en");
		translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1b() throws Exception {
		// Rule = R1
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.1of2", fieldList1, false);
		reqComp1.setId("req-1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp1));

		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed MATH 152 or MATH 180", translation);
	}

	@Test
	public void testTranslateStatementId1() throws Exception {
		// Rule = R1
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		reqComp.setId("req-1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement2() throws Exception {
		// Rule = R1
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		LuStatement stmt11 = new LuStatement();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		reqComp.setId("req-1");
		
		stmt11.setRequiredComponents(Arrays.asList(reqComp));
		this.luDao.create(stmt11);

		stmt1.setChildren(Arrays.asList(stmt11));
		this.luDao.create(stmt1);
		
		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement3() throws Exception {
		// Rule = R1 AND R2
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.OR);

		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqComponentField> fieldList2 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		stmt1.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement4() throws Exception {
		// Rule = R1 AND R2
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		LuStatement stmt11 = new LuStatement();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1, true);
		
		stmt11.setRequiredComponents(Arrays.asList(reqComp1));
		this.luDao.create(stmt11);

		LuStatement stmt12 = new LuStatement();
		stmt12.setParent(stmt1);
		stmt12.setOperator(StatementOperatorTypeKey.OR);
		List<ReqComponentField> fieldList2 = statementTestUtil.createReqComponentFields("2", "equal_to", "CLUSET-NL-1");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2, true);

		stmt12.setRequiredComponents(Arrays.asList(reqComp2));
		this.luDao.create(stmt12);

		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		this.luDao.create(stmt1);
		
		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 and Student must have completed 2 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement5() throws Exception {
		// Rule = R1 AND (R2 OR (R3 AND R4))
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		// R1;
		List<ReqComponentField> fieldList11 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp11 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList11);
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		// R2;
		List<ReqComponentField> fieldList121 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp121 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList121);
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqComponentField> fieldList1221 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1221 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1221);
		reqComp1221.setId("R3");
		// R4;
		List<ReqComponentField> fieldList1222 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1222 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1222);
		reqComp1222.setId("R4");
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		String translation = this.englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 1 of MATH 152, MATH 221, MATH 180 or " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180))", translation);
	}

	private LuStatement getComplexStatement() throws DoesNotExistException {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);
		LuStatement stmt13 = new LuStatement();
		stmt13.setId("S13");
		stmt13.setParent(stmt1);

		LuStatement stmt111 = new LuStatement();
		stmt111.setId("S111");
		stmt111.setParent(stmt11);
		// R1;
		List<ReqComponentField> fieldList111 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList111);
		reqComp1.setId("Rq1");
		stmt111.setRequiredComponents(Arrays.asList(reqComp1));
		LuStatement stmt112 = new LuStatement();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);
		// R2;
		List<ReqComponentField> fieldList112 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList112);
		reqComp2.setId("Rq2");
		stmt112.setRequiredComponents(Arrays.asList(reqComp2));

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		// No ReqComponent for stmt121
		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqComponentField> fieldList122 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp3 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList122);
		reqComp3.setId("Rq3");
		stmt122.setRequiredComponents(Arrays.asList(reqComp3));

		LuStatement stmt131 = new LuStatement();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		// R4;
		List<ReqComponentField> fieldList131 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp4 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList131);
		reqComp4.setId("Rq4");
		stmt131.setRequiredComponents(Arrays.asList(reqComp4));
		LuStatement stmt132 = new LuStatement();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		// No ReqComponent for stmt132
		
		LuStatement stmt1211 = new LuStatement();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		// R5;
		List<ReqComponentField> fieldList1211 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp5 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1211);
		reqComp5.setId("Rq5");
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		LuStatement stmt1212 = new LuStatement();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);
		// R6;
		List<ReqComponentField> fieldList1212 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp6 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1212);
		reqComp6.setId("Rq6");
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6));

		LuStatement stmt1321 = new LuStatement();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		// R7;
		List<ReqComponentField> fieldList1321 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp7 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1321);
		reqComp7.setId("Rq7");
		stmt1321.setRequiredComponents(Arrays.asList(reqComp7));
		LuStatement stmt1322 = new LuStatement();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		// R8;
		List<ReqComponentField> fieldList1322 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp8 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1322);
		reqComp8.setId("Rq8");
		stmt1322.setRequiredComponents(Arrays.asList(reqComp8));
		LuStatement stmt1323 = new LuStatement();
		stmt1323.setId("S1323");
		stmt1323.setParent(stmt132);
		// R9;
		List<ReqComponentField> fieldList1323 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp9 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1323);
		reqComp9.setId("Rq9");
		stmt1323.setRequiredComponents(Arrays.asList(reqComp9));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt121.setChildren(Arrays.asList(stmt1211, stmt1212));

		stmt132.setOperator(StatementOperatorTypeKey.OR);
		stmt132.setChildren(Arrays.asList(stmt1321, stmt1322, stmt1323));

		stmt11.setOperator(StatementOperatorTypeKey.AND);
		stmt11.setChildren(Arrays.asList(stmt111, stmt112));

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt13.setOperator(StatementOperatorTypeKey.AND);
		stmt13.setChildren(Arrays.asList(stmt131, stmt132));
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12, stmt13));
		
		return stmt1;
	}

	@Test
	public void testTranslateStatement6() throws Exception {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		LuStatement stmt1 = getComplexStatement();
		
		String translation = this.englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		Assert.assertEquals(
				"Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 1 of MATH 152, MATH 221, MATH 180 and " +
				"((Student must have completed 0 of MATH 152, MATH 221, MATH 180 and Student must have completed 1 of MATH 152, MATH 221, MATH 180) or Student must have completed 2 of MATH 152, MATH 221, MATH 180) and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 or Student must have completed 3 of MATH 152, MATH 221, MATH 180 or Student must have completed 0 of MATH 152, MATH 221, MATH 180)", 
				translation);
	}

	private LuStatement createSimpleStatement(boolean persist) throws DoesNotExistException {
		//Rule: (R1 OR R2) AND R3
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		LuStatement stmt11 = new LuStatement();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.OR);
		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1, persist);
		reqComp1.setId("req-1");
		List<ReqComponentField> fieldList2 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2, persist);
		reqComp2.setId("req-2");
		stmt11.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		if(persist) {
			this.luDao.create(stmt11);
		} else {
			stmt11.setId("S2");
		}

		LuStatement stmt12 = new LuStatement();
		stmt12.setParent(stmt1);
		stmt12.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList3 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp3 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList3, persist);
		reqComp3.setId("req-3");
		stmt12.setRequiredComponents(Arrays.asList(reqComp3));
		if(persist) {
			this.luDao.create(stmt12);
		} else {
			stmt12.setId("S3");
		}
		
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		if(persist) {
			this.luDao.create(stmt1);
		} else {
			stmt1.setId("S1");
		}
		
		return stmt1;
	}
	
	@Test
	public void testTranslateStatementTree1() throws Exception {
		// Rule: R1 AND R2
		LuStatement stmt1 = statementTestUtil.createStatement("kuali.luStatementType.prereqAcademicReadiness");
		stmt1.setId("stmt-1");
		stmt1.setOperator(StatementOperatorTypeKey.OR);

		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqComponentField> fieldList2 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		stmt1.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		NLTranslationNodeInfo root = englishTranslator.translateToTree("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("stmt-1", root.getId());
		Assert.assertEquals(2, root.getChildNodes().size());
		Assert.assertEquals("req-1", root.getChildNodes().get(0).getId());
		Assert.assertEquals("req-2", root.getChildNodes().get(1).getId());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", root.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 221, MATH 180", root.getChildNodes().get(1).getNLTranslation());
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", root.getNLTranslation());
	}
	
	@Test
	public void testTranslateStatementTree2() throws Exception {
		//Rule: (R1 OR R2) AND R3
		LuStatement stmt1 = createSimpleStatement(false);
		
		NLTranslationNodeInfo root = englishTranslator.translateToTree("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("S1", root.getId());
		Assert.assertEquals(2, root.getChildNodes().size());
		Assert.assertEquals("S2", root.getChildNodes().get(0).getId());
		Assert.assertEquals("S3", root.getChildNodes().get(1).getId());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", root.getChildNodes().get(0).getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 180", root.getChildNodes().get(1).getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", root.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 180", root.getChildNodes().get(1).getNLTranslation());
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: (Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180) and Student must have completed 2 of MATH 152, MATH 180", root.getNLTranslation());
	}

	@Test
	public void testTranslateStatementTreeByStatementId2() throws Exception {
		// Rule = R1 and R2
		LuStatement stmt1 = createSimpleStatement(true);
		
		NLTranslationNodeInfo root = englishTranslator.translateToTree("CLU-NL-1", stmt1, "KUALI.CATALOG");
		Assert.assertNotNull(root.getId());
		Assert.assertEquals(2, root.getChildNodes().size());
		Assert.assertNotNull(root.getChildNodes().get(0).getId());
		Assert.assertNotNull(root.getChildNodes().get(1).getId());
		Assert.assertNotNull(root.getChildNodes().get(0).getChildNodes().get(0).getId());
		Assert.assertNotNull(root.getChildNodes().get(0).getChildNodes().get(1).getId());
		Assert.assertNotNull(root.getChildNodes().get(1).getChildNodes().get(0).getId());
//		Assert.assertEquals("R1", root.getChildNodes().get(0).getChildNodes().get(0).getBooleanId());
//		Assert.assertEquals("R2", root.getChildNodes().get(0).getChildNodes().get(1).getBooleanId());
//		Assert.assertEquals("R3", root.getChildNodes().get(1).getChildNodes().get(0).getBooleanId());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", root.getChildNodes().get(0).getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 180", root.getChildNodes().get(1).getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", root.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 180", root.getChildNodes().get(1).getNLTranslation());
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: (Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180) and Student must have completed 2 of MATH 152, MATH 180", root.getNLTranslation());
	}
	
	@Test
	public void testTranslateStatementTree3() throws Exception {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		LuStatement stmt1 = getComplexStatement();

		NLTranslationNodeInfo root = englishTranslator.translateToTree("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("S1", root.getId());
		Assert.assertEquals(3, root.getChildNodes().size());
		Assert.assertEquals("S11", root.getChildNodes().get(0).getId());
		Assert.assertEquals("S12", root.getChildNodes().get(1).getId());
		Assert.assertEquals("S13", root.getChildNodes().get(2).getId());

		NLTranslationNodeInfo node = root.getChildNodes().get(0);
		Assert.assertEquals(2, node.getChildNodes().size());
		Assert.assertEquals("S111", node.getChildNodes().get(0).getId());
		Assert.assertEquals("S112", node.getChildNodes().get(1).getId());
		Assert.assertEquals("R1", node.getChildNodes().get(0).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("R2", node.getChildNodes().get(1).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(1).getNLTranslation());

		node = root.getChildNodes().get(1);
		Assert.assertEquals(2, node.getChildNodes().size());
		Assert.assertEquals("S121", node.getChildNodes().get(0).getId());
		Assert.assertEquals("S122", node.getChildNodes().get(1).getId());
		Assert.assertEquals("R5", node.getChildNodes().get(1).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals(null, node.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(1).getNLTranslation());

		node = root.getChildNodes().get(2);
		Assert.assertEquals(2, node.getChildNodes().size());
		Assert.assertEquals("S131", node.getChildNodes().get(0).getId());
		Assert.assertEquals("S132", node.getChildNodes().get(1).getId());
		Assert.assertEquals("R6", node.getChildNodes().get(0).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("Student must have completed 3 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals(null, node.getChildNodes().get(1).getNLTranslation());

		node = root.getChildNodes().get(1).getChildNodes().get(0);
		Assert.assertEquals(2, node.getChildNodes().size());
		Assert.assertEquals("S1211", node.getChildNodes().get(0).getId());
		Assert.assertEquals("S1212", node.getChildNodes().get(1).getId());
		Assert.assertEquals("R3", node.getChildNodes().get(0).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("R4", node.getChildNodes().get(1).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(1).getNLTranslation());

		node = root.getChildNodes().get(2).getChildNodes().get(1);
		Assert.assertEquals(3, node.getChildNodes().size());
		Assert.assertEquals("S1321", node.getChildNodes().get(0).getId());
		Assert.assertEquals("S1322", node.getChildNodes().get(1).getId());
		Assert.assertEquals("S1323", node.getChildNodes().get(2).getId());
		Assert.assertEquals("R7", node.getChildNodes().get(0).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("R8", node.getChildNodes().get(1).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("R9", node.getChildNodes().get(2).getChildNodes().get(0).getReferenceId());
		Assert.assertEquals("Student must have completed 2 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(0).getNLTranslation());
		Assert.assertEquals("Student must have completed 3 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(1).getNLTranslation());
		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180", node.getChildNodes().get(2).getNLTranslation());
		Assert.assertEquals("Requirement for MATH 152 Linear Systems: " +
				"Student must have completed 0 of MATH 152, MATH 221, MATH 180 " +
				"and Student must have completed 1 of MATH 152, MATH 221, MATH 180 " +
				"and ((Student must have completed 0 of MATH 152, MATH 221, MATH 180 " +
				"and Student must have completed 1 of MATH 152, MATH 221, MATH 180) " +
				"or Student must have completed 2 of MATH 152, MATH 221, MATH 180) " +
				"and Student must have completed 3 of MATH 152, MATH 221, MATH 180 " +
				"and (Student must have completed 2 of MATH 152, MATH 221, MATH 180 " +
				"or Student must have completed 3 of MATH 152, MATH 221, MATH 180 " +
				"or Student must have completed 0 of MATH 152, MATH 221, MATH 180)", root.getNLTranslation());
	}
}
