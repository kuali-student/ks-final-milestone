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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.LuServiceMock;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.translators.NaturalLanguageMessageBuilder;
import org.kuali.student.lum.nlt.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

public class StatementTranslatorTest {

	private static LuServiceMock luService = new LuServiceMock();

	private static StatementTranslator englishTranslator = new StatementTranslator();
	private static StatementTranslator germanTranslator = new StatementTranslator();

    @BeforeClass
    public static void setUp() throws Exception {
    	createTranslator();
    	luService.addAll(NaturalLanguageUtil.createData());
    }
    
    private static void createTranslator() {
    	ContextRegistry<Context<CustomReqComponentInfo>> reqComponentContextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry(luService);
    	ContextRegistry<Context<LuStatementAnchor>> statementContextRegistry = NaturalLanguageUtil.getStatementContextRegistry(luService);

    	SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();
    	final DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
		executor.setEnableStatisticsLogging(false);
		executor.setRuleBaseCache(ruleBase);

		NaturalLanguageMessageBuilder englishMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", new BooleanOperators("and", "or"));

    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	englishReqComponentTranslator.setLanguage("en");

    	englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
		englishTranslator.setContextRegistry(statementContextRegistry);
		englishTranslator.setMessageBuilder(englishMessageBuilder);
		englishTranslator.setLanguage("en");

		NaturalLanguageMessageBuilder germanMessageBuilder = new NaturalLanguageMessageBuilder(executor, "en", new BooleanOperators("and", "or"));

    	ReqComponentTranslator germanReqComponentTranslator = new ReqComponentTranslator();
    	germanReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	germanReqComponentTranslator.setLanguage("de");

    	germanTranslator.setReqComponentTranslator(germanReqComponentTranslator);
		germanTranslator.setContextRegistry(statementContextRegistry);
		germanTranslator.setMessageBuilder(germanMessageBuilder);
		germanTranslator.setLanguage("de");
    }

	@Test
	public void testTranslateStatement1_English() throws Exception {
		// Rule = R1
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_German() throws Exception {
		// Rule = R1
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = germanTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_EnglishGerman() throws Exception {
		// Rule = R1
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

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
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1,CLU-NL-3");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.1of2");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed MATH 152 or MATH 180", translation);
	}

	@Test
	public void testTranslateStatementId1() throws Exception {
		// Rule = R1
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement2() throws Exception {
		// Rule = R1
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqCompFieldInfo> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqCompFields(fieldList);
		stmt11.setRequiredComponents(Arrays.asList(reqComp));

		stmt1.setChildren(Arrays.asList(stmt11));
		
		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement3() throws Exception {
		// Rule = R1 AND R2
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqCompFields(fieldList1);

		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqCompFields(fieldList2);
		
		stmt1.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement4() throws Exception {
		// Rule = R1 AND R2
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);
		stmt1.setId("stmt-1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqCompFields(fieldList1);
		
		stmt11.setRequiredComponents(Arrays.asList(reqComp1));

		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setParent(stmt1);
		stmt12.setOperator(StatementOperatorTypeKey.OR);
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqCompFields(fieldList2);

		stmt12.setRequiredComponents(Arrays.asList(reqComp2));

		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		String translation = englishTranslator.translate("CLU-NL-1", stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 and Student must have completed 2 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement5() throws Exception {
		// Rule = R1 AND (R2 OR (R3 AND R4))
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		// R1;
		List<ReqCompFieldInfo> fieldList11 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp11 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp11.setId("R1");
		reqComp11.setReqCompFields(fieldList11);
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		// R2;
		List<ReqCompFieldInfo> fieldList121 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp121 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp121.setId("R2");
		reqComp121.setReqCompFields(fieldList121);
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqCompFieldInfo> fieldList1221 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp1221 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1221.setId("R3");
		reqComp1221.setReqCompFields(fieldList1221);
		// R4;
		List<ReqCompFieldInfo> fieldList1222 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp1222 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1222.setId("R4");
		reqComp1222.setReqCompFields(fieldList1222);
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 1 of MATH 152, MATH 221, MATH 180 or " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180))", translation);
	}

	private CustomLuStatementInfo getComplexStatement() throws Exception {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(null);
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);
		CustomLuStatementInfo stmt13 = new CustomLuStatementInfo();
		stmt13.setId("S13");
		stmt13.setParent(stmt1);

		CustomLuStatementInfo stmt111 = new CustomLuStatementInfo();
		stmt111.setId("S111");
		stmt111.setParent(stmt11);
		// R1;
		List<ReqCompFieldInfo> fieldList111 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1.setId("Rq1");
		reqComp1.setReqCompFields(fieldList111);
		stmt111.setRequiredComponents(Arrays.asList(reqComp1));
		CustomLuStatementInfo stmt112 = new CustomLuStatementInfo();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);
		// R2;
		List<ReqCompFieldInfo> fieldList112 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp2.setId("Rq2");
		reqComp2.setReqCompFields(fieldList112);
		stmt112.setRequiredComponents(Arrays.asList(reqComp2));

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		// No CustomReqComponentInfo for stmt121
		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqCompFieldInfo> fieldList122 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp3 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp3.setId("Rq3");
		reqComp3.setReqCompFields(fieldList122);
		stmt122.setRequiredComponents(Arrays.asList(reqComp3));

		CustomLuStatementInfo stmt131 = new CustomLuStatementInfo();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		// R4;
		List<ReqCompFieldInfo> fieldList131 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp4 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp4.setId("Rq4");
		reqComp4.setReqCompFields(fieldList131);
		stmt131.setRequiredComponents(Arrays.asList(reqComp4));
		CustomLuStatementInfo stmt132 = new CustomLuStatementInfo();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		// No CustomReqComponentInfo for stmt132
		
		CustomLuStatementInfo stmt1211 = new CustomLuStatementInfo();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		// R5;
		List<ReqCompFieldInfo> fieldList1211 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp5 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp5.setId("Rq5");
		reqComp5.setReqCompFields(fieldList1211);
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		CustomLuStatementInfo stmt1212 = new CustomLuStatementInfo();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);
		// R6;
		List<ReqCompFieldInfo> fieldList1212 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp6 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp6.setId("Rq6");
		reqComp6.setReqCompFields(fieldList1212);
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6));

		CustomLuStatementInfo stmt1321 = new CustomLuStatementInfo();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		// R7;
		List<ReqCompFieldInfo> fieldList1321 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp7 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp7.setId("Rq7");
		reqComp7.setReqCompFields(fieldList1321);
		stmt1321.setRequiredComponents(Arrays.asList(reqComp7));
		CustomLuStatementInfo stmt1322 = new CustomLuStatementInfo();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		// R8;
		List<ReqCompFieldInfo> fieldList1322 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp8 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp8.setId("Rq8");
		reqComp8.setReqCompFields(fieldList1322);
		stmt1322.setRequiredComponents(Arrays.asList(reqComp8));
		CustomLuStatementInfo stmt1323 = new CustomLuStatementInfo();
		stmt1323.setId("S1323");
		stmt1323.setParent(stmt132);
		// R9;
		List<ReqCompFieldInfo> fieldList1323 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp9 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp9.setId("Rq9");
		reqComp9.setReqCompFields(fieldList1323);
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
		CustomLuStatementInfo stmt1 = getComplexStatement();
		
		String translation = englishTranslator.translate(null, stmt1, "KUALI.CATALOG");

		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		Assert.assertEquals(
				"Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 1 of MATH 152, MATH 221, MATH 180 and " +
				"((Student must have completed 0 of MATH 152, MATH 221, MATH 180 and Student must have completed 1 of MATH 152, MATH 221, MATH 180) or Student must have completed 2 of MATH 152, MATH 221, MATH 180) and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 or Student must have completed 3 of MATH 152, MATH 221, MATH 180 or Student must have completed 0 of MATH 152, MATH 221, MATH 180)", 
				translation);
	}

	private CustomLuStatementInfo createSimpleStatement() throws Exception {
		//Rule: (R1 OR R2) AND R3
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S2");
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.OR);
		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1,CLU-NL-3");
		CustomReqComponentInfo reqComp1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqCompFields(fieldList1);
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqCompFields(fieldList2);
		stmt11.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S3");
		stmt12.setParent(stmt1);
		stmt12.setOperator(StatementOperatorTypeKey.AND);
		List<ReqCompFieldInfo> fieldList3 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp3 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp3.setId("req-3");
		reqComp3.setReqCompFields(fieldList3);
		stmt12.setRequiredComponents(Arrays.asList(reqComp3));
		
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		return stmt1;
	}

	@Test
	public void testTranslateStatementTree1() throws Exception {
		// Rule: R1 AND R2
		CustomLuStatementInfo stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.OR);
		stmt1.setId("stmt-1");

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		CustomReqComponentInfo reqComp1 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqCompFields(fieldList1);
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		CustomReqComponentInfo reqComp2 = NaturalLanguageUtil.createCustomReqComponent("KUALI.CATALOG", "kuali.reqCompType.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqCompFields(fieldList2);
		
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
		CustomLuStatementInfo stmt1 = createSimpleStatement();
		
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
		CustomLuStatementInfo stmt1 = createSimpleStatement();
		
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
		CustomLuStatementInfo stmt1 = getComplexStatement();

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
