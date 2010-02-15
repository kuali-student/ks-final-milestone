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
import org.junit.Test;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementParser;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentReference;

public class StatementParserTest {
	@Test
	public void testBooleanExpressionAsStatement1() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");
		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);
		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsStatements(stmt1);

		// S11 AND (S121 OR S122)
		Assert.assertEquals("S1 and (S2 or S3)", expressionReduced);
	}

	@Test
	public void testBooleanExpressionAsStatement2() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S-1");
		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S-11");
		stmt11.setParent(stmt1);
		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S-12");
		stmt12.setParent(stmt1);
		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S-121");
		stmt121.setParent(stmt12);
		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S-122");
		stmt122.setParent(stmt12);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsStatements(stmt1);

		// S11 AND (S121 OR S122)
		Assert.assertEquals("S1 and (S2 or S3)", expressionReduced);
	}

	@Test
	public void testBooleanExpressionAsStatement3() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
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
		CustomLuStatementInfo stmt112 = new CustomLuStatementInfo();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);

		CustomLuStatementInfo stmt131 = new CustomLuStatementInfo();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		CustomLuStatementInfo stmt132 = new CustomLuStatementInfo();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		
		CustomLuStatementInfo stmt1211 = new CustomLuStatementInfo();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		CustomLuStatementInfo stmt1212 = new CustomLuStatementInfo();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);

		CustomLuStatementInfo stmt1321 = new CustomLuStatementInfo();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		CustomLuStatementInfo stmt1322 = new CustomLuStatementInfo();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		CustomLuStatementInfo stmt1323 = new CustomLuStatementInfo();
		stmt1323.setId("S1323");
		stmt1323.setParent(stmt132);
		
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
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsStatements(stmt1);
		
		// S111 AND S112 AND ((S1211 AND S1212) OR S122) AND S131 AND (S1321 OR S1322 OR S1323)
		Assert.assertEquals("S1 and S2 and ((S3 and S4) or S5) and S6 and (S7 or S8 or S9)", expressionReduced);
	}

	@Test
	public void testBooleanExpressionAsReqComponent0() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");

		CustomReqComponentInfo reqComp1 = new CustomReqComponentInfo();
		reqComp1.setId("R1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp1));
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);

		Assert.assertEquals("R1", expressionReduced);
	}

	@Test
	public void testBooleanExpressionAsReqComponent1() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		CustomReqComponentInfo reqComp11 = new CustomReqComponentInfo();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomReqComponentInfo reqComp121 = new CustomReqComponentInfo();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		CustomReqComponentInfo reqComp1221 = new CustomReqComponentInfo();
		reqComp1221.setId("R3");
		CustomReqComponentInfo reqComp1222 = new CustomReqComponentInfo();
		reqComp1222.setId("R4");
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);

		Assert.assertEquals("R1 and (R2 or (R3 and R4))", expressionReduced);
	}

	/*@Test
	public void testBooleanExpressionAsReqComponent2() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		CustomReqComponentInfo reqComp11 = new CustomReqComponentInfo();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		CustomLuStatementInfo stmt111 = new CustomLuStatementInfo();
		stmt111.setId("S111");
		stmt111.setParent(stmt11);
		CustomReqComponentInfo reqComp0 = new CustomReqComponentInfo();
		reqComp0.setId("R0");
		stmt111.setRequiredComponents(Arrays.asList(reqComp0));
		
		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomReqComponentInfo reqComp121 = new CustomReqComponentInfo();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		CustomReqComponentInfo reqComp1221 = new CustomReqComponentInfo();
		reqComp1221.setId("R3");
		CustomReqComponentInfo reqComp1222 = new CustomReqComponentInfo();
		reqComp1222.setId("R4");
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);
		stmt11.setChildren(Arrays.asList(stmt111));

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);
System.out.println("expr="+expressionReduced);
		Assert.assertEquals("R0 AND R1 AND (R2 OR (R3 AND R4))", expressionReduced);
	}*/

	@Test
	public void testBooleanExpressionAsReqComponent3() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
		stmt1.setId("S1");

		CustomLuStatementInfo stmt11 = new CustomLuStatementInfo();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		CustomReqComponentInfo reqComp11 = new CustomReqComponentInfo();
		reqComp11.setId("R-1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		CustomLuStatementInfo stmt12 = new CustomLuStatementInfo();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomReqComponentInfo reqComp121 = new CustomReqComponentInfo();
		reqComp121.setId("R-2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		CustomReqComponentInfo reqComp1221 = new CustomReqComponentInfo();
		reqComp1221.setId("R-3");
		CustomReqComponentInfo reqComp1222 = new CustomReqComponentInfo();
		reqComp1222.setId("R-4");
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);
		List<ReqComponentReference> list = parser.getLeafReqComponents(stmt1);

		Assert.assertEquals("R1 and (R2 or (R3 and R4))", expressionReduced);
		Assert.assertEquals(4, list.size());
	}

	@Test
	public void testBooleanExpressionAsReqComponent4() throws Exception {
		CustomLuStatementInfo stmt1 = new CustomLuStatementInfo();
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
		CustomReqComponentInfo reqComp1 = new CustomReqComponentInfo();
		reqComp1.setId("R1");
		CustomReqComponentInfo reqComp2 = new CustomReqComponentInfo();
		reqComp2.setId("R2");
		stmt111.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		CustomLuStatementInfo stmt112 = new CustomLuStatementInfo();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);
		CustomReqComponentInfo reqComp3 = new CustomReqComponentInfo();
		reqComp3.setId("R3");
		CustomReqComponentInfo reqComp4 = new CustomReqComponentInfo();
		reqComp4.setId("R4");
		stmt112.setRequiredComponents(Arrays.asList(reqComp3, reqComp4));
		
		CustomLuStatementInfo stmt121 = new CustomLuStatementInfo();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		CustomLuStatementInfo stmt122 = new CustomLuStatementInfo();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		CustomReqComponentInfo reqComp8 = new CustomReqComponentInfo();
		reqComp8.setId("R8");
		stmt122.setRequiredComponents(Arrays.asList(reqComp8));
		
		CustomLuStatementInfo stmt131 = new CustomLuStatementInfo();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		CustomReqComponentInfo reqComp9 = new CustomReqComponentInfo();
		reqComp9.setId("R9");
		stmt131.setRequiredComponents(Arrays.asList(reqComp9));
		
		CustomLuStatementInfo stmt132 = new CustomLuStatementInfo();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		
		CustomLuStatementInfo stmt1211 = new CustomLuStatementInfo();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		CustomReqComponentInfo reqComp5 = new CustomReqComponentInfo();
		reqComp5.setId("R5");
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		
		CustomLuStatementInfo stmt1212 = new CustomLuStatementInfo();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);
		CustomReqComponentInfo reqComp6 = new CustomReqComponentInfo();
		reqComp6.setId("R6");
		CustomReqComponentInfo reqComp7 = new CustomReqComponentInfo();
		reqComp7.setId("R7");
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6, reqComp7));
		
		CustomLuStatementInfo stmt1321 = new CustomLuStatementInfo();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		CustomReqComponentInfo reqComp10 = new CustomReqComponentInfo();
		reqComp10.setId("R10");
		stmt1321.setRequiredComponents(Arrays.asList(reqComp10));

		CustomLuStatementInfo stmt1322 = new CustomLuStatementInfo();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		CustomReqComponentInfo reqComp11 = new CustomReqComponentInfo();
		reqComp11.setId("R11");
		CustomReqComponentInfo reqComp12 = new CustomReqComponentInfo();
		reqComp12.setId("R12");
		stmt1322.setRequiredComponents(Arrays.asList(reqComp11, reqComp12));

		CustomLuStatementInfo stmt1323 = new CustomLuStatementInfo();
		stmt1323.setId("S1323");
		stmt1323.setParent(stmt132);
		CustomReqComponentInfo reqComp13 = new CustomReqComponentInfo();
		reqComp13.setId("R13");
		stmt1323.setRequiredComponents(Arrays.asList(reqComp13));
		
		stmt111.setOperator(StatementOperatorTypeKey.OR);
		stmt112.setOperator(StatementOperatorTypeKey.AND);

		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt121.setChildren(Arrays.asList(stmt1211, stmt1212));
		stmt122.setOperator(StatementOperatorTypeKey.AND);

		stmt1211.setOperator(StatementOperatorTypeKey.AND);
		stmt1212.setOperator(StatementOperatorTypeKey.OR);
		
		stmt131.setOperator(StatementOperatorTypeKey.AND);
		stmt132.setOperator(StatementOperatorTypeKey.OR);
		stmt132.setChildren(Arrays.asList(stmt1321, stmt1322, stmt1323));

		stmt1321.setOperator(StatementOperatorTypeKey.AND);
		stmt1322.setOperator(StatementOperatorTypeKey.OR);
		stmt1323.setOperator(StatementOperatorTypeKey.AND);
		
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		stmt11.setChildren(Arrays.asList(stmt111, stmt112));

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt13.setOperator(StatementOperatorTypeKey.AND);
		stmt13.setChildren(Arrays.asList(stmt131, stmt132));
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12, stmt13));
		
		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);
		List<ReqComponentReference> list = parser.getLeafReqComponents(stmt1);
		
		Assert.assertEquals("(R1 or R2) and (R3 and R4) and ((R5 and (R6 or R7)) or R8) and R9 and (R10 or (R11 or R12) or R13)", expressionReduced);
		Assert.assertEquals(13, list.size());
	}
}
