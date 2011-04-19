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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.naturallanguage.translators.StatementParser;
import org.kuali.student.core.statement.naturallanguage.util.ReqComponentReference;

public class StatementParserTest {
	@Test
	public void testBooleanExpressionAsStatement1() throws Exception {
		Statement stmt1 = new Statement();
		stmt1.setId("S1");
		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		Statement stmt12 = new Statement();
		stmt12.setId("S12");
		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		Statement stmt122 = new Statement();
		stmt122.setId("S122");

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
		Statement stmt1 = new Statement();
		stmt1.setId("S-1");
		Statement stmt11 = new Statement();
		stmt11.setId("S-11");
		Statement stmt12 = new Statement();
		stmt12.setId("S-12");
		Statement stmt121 = new Statement();
		stmt121.setId("S-121");
		Statement stmt122 = new Statement();
		stmt122.setId("S-122");

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
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		Statement stmt12 = new Statement();
		stmt12.setId("S12");
		Statement stmt13 = new Statement();
		stmt13.setId("S13");

		Statement stmt111 = new Statement();
		stmt111.setId("S111");
		Statement stmt112 = new Statement();
		stmt112.setId("S112");

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		Statement stmt122 = new Statement();
		stmt122.setId("S122");

		Statement stmt131 = new Statement();
		stmt131.setId("S131");
		Statement stmt132 = new Statement();
		stmt132.setId("S132");

		Statement stmt1211 = new Statement();
		stmt1211.setId("S1211");
		Statement stmt1212 = new Statement();
		stmt1212.setId("S1212");

		Statement stmt1321 = new Statement();
		stmt1321.setId("S1321");
		Statement stmt1322 = new Statement();
		stmt1322.setId("S1322");
		Statement stmt1323 = new Statement();
		stmt1323.setId("S1323");

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
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		ReqComponent reqComp1 = new ReqComponent();
		reqComp1.setId("R1");
		stmt1.setRequiredComponents(Arrays.asList(reqComp1));
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		StatementParser parser = new StatementParser();
		String expressionReduced = parser.getBooleanExpressionAsReqComponents(stmt1);

		Assert.assertEquals("R1", expressionReduced);
	}

	@Test
	public void testBooleanExpressionAsReqComponent1() throws Exception {
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		Statement stmt12 = new Statement();
		stmt12.setId("S12");

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		ReqComponent reqComp1221 = new ReqComponent();
		reqComp1221.setId("R3");
		ReqComponent reqComp1222 = new ReqComponent();
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
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		Statement stmt111 = new Statement();
		stmt111.setId("S111");
		stmt111.setParent(stmt11);
		ReqComponent reqComp0 = new ReqComponent();
		reqComp0.setId("R0");
		stmt111.setRequiredComponents(Arrays.asList(reqComp0));

		Statement stmt12 = new Statement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		ReqComponent reqComp1221 = new ReqComponent();
		reqComp1221.setId("R3");
		ReqComponent reqComp1222 = new ReqComponent();
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
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R-1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		Statement stmt12 = new Statement();
		stmt12.setId("S12");

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R-2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		ReqComponent reqComp1221 = new ReqComponent();
		reqComp1221.setId("R-3");
		ReqComponent reqComp1222 = new ReqComponent();
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
		Statement stmt1 = new Statement();
		stmt1.setId("S1");

		Statement stmt11 = new Statement();
		stmt11.setId("S11");
		Statement stmt12 = new Statement();
		stmt12.setId("S12");
		Statement stmt13 = new Statement();
		stmt13.setId("S13");

		Statement stmt111 = new Statement();
		stmt111.setId("S111");
		ReqComponent reqComp1 = new ReqComponent();
		reqComp1.setId("R1");
		ReqComponent reqComp2 = new ReqComponent();
		reqComp2.setId("R2");
		stmt111.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		Statement stmt112 = new Statement();
		stmt112.setId("S112");
		ReqComponent reqComp3 = new ReqComponent();
		reqComp3.setId("R3");
		ReqComponent reqComp4 = new ReqComponent();
		reqComp4.setId("R4");
		stmt112.setRequiredComponents(Arrays.asList(reqComp3, reqComp4));

		Statement stmt121 = new Statement();
		stmt121.setId("S121");
		Statement stmt122 = new Statement();
		stmt122.setId("S122");
		ReqComponent reqComp8 = new ReqComponent();
		reqComp8.setId("R8");
		stmt122.setRequiredComponents(Arrays.asList(reqComp8));

		Statement stmt131 = new Statement();
		stmt131.setId("S131");
		ReqComponent reqComp9 = new ReqComponent();
		reqComp9.setId("R9");
		stmt131.setRequiredComponents(Arrays.asList(reqComp9));

		Statement stmt132 = new Statement();
		stmt132.setId("S132");

		Statement stmt1211 = new Statement();
		stmt1211.setId("S1211");
		ReqComponent reqComp5 = new ReqComponent();
		reqComp5.setId("R5");
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));

		Statement stmt1212 = new Statement();
		stmt1212.setId("S1212");
		ReqComponent reqComp6 = new ReqComponent();
		reqComp6.setId("R6");
		ReqComponent reqComp7 = new ReqComponent();
		reqComp7.setId("R7");
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6, reqComp7));

		Statement stmt1321 = new Statement();
		stmt1321.setId("S1321");
		ReqComponent reqComp10 = new ReqComponent();
		reqComp10.setId("R10");
		stmt1321.setRequiredComponents(Arrays.asList(reqComp10));

		Statement stmt1322 = new Statement();
		stmt1322.setId("S1322");
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R11");
		ReqComponent reqComp12 = new ReqComponent();
		reqComp12.setId("R12");
		stmt1322.setRequiredComponents(Arrays.asList(reqComp11, reqComp12));

		Statement stmt1323 = new Statement();
		stmt1323.setId("S1323");
		ReqComponent reqComp13 = new ReqComponent();
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
