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

package org.kuali.student.r1.core.statement.naturallanguage.translators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.messagebuilder.MessageBuilder;
import org.kuali.student.common.messagebuilder.MessageTreeBuilder;
import org.kuali.student.common.messagebuilder.impl.BooleanOperators;
import org.kuali.student.common.messagebuilder.impl.MessageBuilderImpl;
import org.kuali.student.common.messagebuilder.impl.SuccessFailureMessageBuilder;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.entity.ReqComponent;
import org.kuali.student.r1.core.statement.entity.ReqComponentField;
import org.kuali.student.r1.core.statement.entity.Statement;
import org.kuali.student.r1.core.statement.naturallanguage.Context;
import org.kuali.student.r1.core.statement.naturallanguage.ContextRegistry;
import org.kuali.student.r1.core.statement.naturallanguage.NaturalLanguageUtil;

public class StatementTranslatorTest {

	private static StatementTranslator englishTranslator = new StatementTranslator();
	private static StatementTranslator germanTranslator = new StatementTranslator();

    @BeforeClass
    public static void setUp() throws Exception {
    	createTranslator();
    }

    private static void createTranslator() {
    	ContextRegistry<Context<ReqComponentInfo>> reqComponentContextRegistry = NaturalLanguageUtil.getReqComponentContextRegistry();

		BooleanOperators bo = new BooleanOperators("and", "or");
		MessageTreeBuilder tnmBuilder = new SuccessFailureMessageBuilder(bo);
    	MessageBuilder msgBuilder = new MessageBuilderImpl("en", tnmBuilder);
		Map<String, MessageBuilder> messageBuilderMap = new HashMap<String, MessageBuilder>();
		messageBuilderMap.put("en", msgBuilder);
		messageBuilderMap.put("de", msgBuilder);

    	NaturalLanguageMessageBuilder englishMessageBuilder = new NaturalLanguageMessageBuilder(messageBuilderMap);

    	ReqComponentTranslator englishReqComponentTranslator = new ReqComponentTranslator();
    	englishReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	englishReqComponentTranslator.setLanguage("en");

    	englishTranslator.setReqComponentTranslator(englishReqComponentTranslator);
		englishTranslator.setMessageBuilder(englishMessageBuilder);
		englishTranslator.setLanguage("en");

		NaturalLanguageMessageBuilder germanMessageBuilder = new NaturalLanguageMessageBuilder(messageBuilderMap);

    	ReqComponentTranslator germanReqComponentTranslator = new ReqComponentTranslator();
    	germanReqComponentTranslator.setContextRegistry(reqComponentContextRegistry);
    	germanReqComponentTranslator.setLanguage("de");

    	germanTranslator.setReqComponentTranslator(germanReqComponentTranslator);
		germanTranslator.setMessageBuilder(germanMessageBuilder);
		germanTranslator.setLanguage("de");
    }

	@Test
	public void testTranslateStatement1_English_EmptyCluId() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_English_NullCluId() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_English() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_German() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = germanTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement1_EnglishGerman() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);

		englishTranslator.setLanguage("de");
		translation = englishTranslator.translate(stmt1, "KUALI.RULE");
		Assert.assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", translation);

		englishTranslator.setLanguage("en");
		translation = englishTranslator.translate(stmt1, "KUALI.RULE");
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	@Ignore
	public void testTranslateStatement1b() throws Exception {
		// Rule = R1
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);

		//Comma separated lists of clu ids no longer supported
		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1,CLU-NL-3");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.1of2");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed MATH 152 or MATH 180", translation);
	}

	@Test
	public void testTranslateStatementId1() throws Exception {
		// Rule = R1
		Statement stmt1 = new Statement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement2() throws Exception {
		// Rule = R1
		Statement stmt1 = new Statement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		Statement stmt11 = new Statement();
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp.setId("req-1");
		reqComp.setReqComponentFields(fieldList);
		stmt11.setRequiredComponents(Arrays.asList(reqComp));

		stmt1.setChildren(Arrays.asList(stmt11));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement3() throws Exception {
		// Rule = R1 AND R2
		Statement stmt1 = new Statement();
		stmt1.setOperator(StatementOperatorTypeKey.OR);

		List<ReqComponentField> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqComponentFields(fieldList1);

		List<ReqComponentField> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqComponentFields(fieldList2);

		stmt1.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");
		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement4() throws Exception {
		// Rule = R1 AND R2
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);
		stmt1.setId("stmt-1");

		Statement stmt11 = new Statement();
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqComponentFields(fieldList1);

		stmt11.setRequiredComponents(Arrays.asList(reqComp1));

		Statement stmt12 = new Statement();
		stmt12.setOperator(StatementOperatorTypeKey.OR);
		List<ReqComponentField> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "equal_to", "CLUSET-NL-1");
		ReqComponent reqComp2 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqComponentFields(fieldList2);

		stmt12.setRequiredComponents(Arrays.asList(reqComp2));

		stmt1.setChildren(Arrays.asList(stmt11, stmt12));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 180 and Student must have completed 2 of MATH 152, MATH 180", translation);
	}

	@Test
	public void testTranslateStatement5() throws Exception {
		// Rule = R1 AND (R2 OR (R3 AND R4))
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		// R1;
		List<ReqComponentField> fieldList11 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp11 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp11.setId("R1");
		reqComp11.setReqComponentFields(fieldList11);
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		Statement stmt12 = new Statement();
		stmt12.setId("S12");

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		// R2;
		List<ReqComponentField> fieldList121 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp121 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp121.setId("R2");
		reqComp121.setReqComponentFields(fieldList121);
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		// R3;
		List<ReqComponentField> fieldList1221 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1221 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1221.setId("R3");
		reqComp1221.setReqComponentFields(fieldList1221);
		// R4;
		List<ReqComponentField> fieldList1222 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1222 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1222.setId("R4");
		reqComp1222.setReqComponentFields(fieldList1222);
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));

		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);

		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 1 of MATH 152, MATH 221, MATH 180 or " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180))", translation);
	}

	private Statement getComplexStatement() throws Exception {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		Statement stmt1 = NaturalLanguageUtil.createStatement(null);
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		Statement stmt12 = new Statement();
		stmt12.setId("S12");
		Statement stmt13 = new Statement();
		stmt13.setId("S13");

		Statement stmt111 = new Statement();
		stmt111.setId("S111");
		// R1;
		List<ReqComponentField> fieldList111 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1.setId("Rq1");
		reqComp1.setReqComponentFields(fieldList111);
		stmt111.setRequiredComponents(Arrays.asList(reqComp1));
		Statement stmt112 = new Statement();
		stmt112.setId("S112");
		// R2;
		List<ReqComponentField> fieldList112 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp2.setId("Rq2");
		reqComp2.setReqComponentFields(fieldList112);
		stmt112.setRequiredComponents(Arrays.asList(reqComp2));

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		// No ReqComponent for stmt121
		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		// R3;
		List<ReqComponentField> fieldList122 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp3 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp3.setId("Rq3");
		reqComp3.setReqComponentFields(fieldList122);
		stmt122.setRequiredComponents(Arrays.asList(reqComp3));

		Statement stmt131 = new Statement();
		stmt131.setId("S131");
		// R4;
		List<ReqComponentField> fieldList131 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp4 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp4.setId("Rq4");
		reqComp4.setReqComponentFields(fieldList131);
		stmt131.setRequiredComponents(Arrays.asList(reqComp4));
		Statement stmt132 = new Statement();
		stmt132.setId("S132");
		// No ReqComponent for stmt132

		Statement stmt1211 = new Statement();
		stmt1211.setId("S1211");
		// R5;
		List<ReqComponentField> fieldList1211 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp5 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp5.setId("Rq5");
		reqComp5.setReqComponentFields(fieldList1211);
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		Statement stmt1212 = new Statement();
		stmt1212.setId("S1212");
		// R6;
		List<ReqComponentField> fieldList1212 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp6 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp6.setId("Rq6");
		reqComp6.setReqComponentFields(fieldList1212);
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6));

		Statement stmt1321 = new Statement();
		stmt1321.setId("S1321");
		// R7;
		List<ReqComponentField> fieldList1321 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp7 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp7.setId("Rq7");
		reqComp7.setReqComponentFields(fieldList1321);
		stmt1321.setRequiredComponents(Arrays.asList(reqComp7));
		Statement stmt1322 = new Statement();
		stmt1322.setId("S1322");
		// R8;
		List<ReqComponentField> fieldList1322 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp8 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp8.setId("Rq8");
		reqComp8.setReqComponentFields(fieldList1322);
		stmt1322.setRequiredComponents(Arrays.asList(reqComp8));
		Statement stmt1323 = new Statement();
		stmt1323.setId("S1323");
		// R9;
		List<ReqComponentField> fieldList1323 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp9 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp9.setId("Rq9");
		reqComp9.setReqComponentFields(fieldList1323);
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
		Statement stmt1 = getComplexStatement();

		String translation = englishTranslator.translate(stmt1, "KUALI.RULE");

		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		Assert.assertEquals(
				"Student must have completed 0 of MATH 152, MATH 221, MATH 180 and " +
				"Student must have completed 1 of MATH 152, MATH 221, MATH 180 and " +
				"((Student must have completed 0 of MATH 152, MATH 221, MATH 180 and Student must have completed 1 of MATH 152, MATH 221, MATH 180) or Student must have completed 2 of MATH 152, MATH 221, MATH 180) and " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180 and " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 or Student must have completed 3 of MATH 152, MATH 221, MATH 180 or Student must have completed 0 of MATH 152, MATH 221, MATH 180)",
				translation);
	}

	private Statement createSimpleStatement() throws Exception {
		//Rule: (R1 OR R2) AND R3
		Statement stmt1 = NaturalLanguageUtil.createStatement(StatementOperatorTypeKey.AND);
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S2");
		stmt11.setOperator(StatementOperatorTypeKey.OR);
		List<ReqComponentField> fieldList1 = NaturalLanguageUtil.createReqComponentFieldsForClu("1", "greater_than_or_equal_to", "CLU-NL-1,CLU-NL-3");
		ReqComponent reqComp1 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp1.setId("req-1");
		reqComp1.setReqComponentFields(fieldList1);
		List<ReqComponentField> fieldList2 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp2.setId("req-2");
		reqComp2.setReqComponentFields(fieldList2);
		stmt11.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		Statement stmt12 = new Statement();
		stmt12.setId("S3");
		stmt12.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList3 = NaturalLanguageUtil.createReqComponentFieldsForCluSet("2", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp3 = NaturalLanguageUtil.createReqComponent("KUALI.RULE", "kuali.reqComponent.type.courseList.nof");
		reqComp3.setId("req-3");
		reqComp3.setReqComponentFields(fieldList3);
		stmt12.setRequiredComponents(Arrays.asList(reqComp3));

		stmt1.setChildren(Arrays.asList(stmt11, stmt12));

		return stmt1;
	}
}
