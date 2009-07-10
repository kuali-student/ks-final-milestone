package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.naturallanguage.translators.StatementParser;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponent;

public class StatementParserTest {
	@Test
	public void testBooleanExpressionAsStatement1() throws Exception {
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");
		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);
		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		LuStatement stmt122 = new LuStatement();
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
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S-1");
		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S-11");
		stmt11.setParent(stmt1);
		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S-12");
		stmt12.setParent(stmt1);
		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S-121");
		stmt121.setParent(stmt12);
		LuStatement stmt122 = new LuStatement();
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
		LuStatement stmt1 = new LuStatement();
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
		LuStatement stmt112 = new LuStatement();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);

		LuStatement stmt131 = new LuStatement();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		LuStatement stmt132 = new LuStatement();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		
		LuStatement stmt1211 = new LuStatement();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		LuStatement stmt1212 = new LuStatement();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);

		LuStatement stmt1321 = new LuStatement();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		LuStatement stmt1322 = new LuStatement();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		LuStatement stmt1323 = new LuStatement();
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
		LuStatement stmt1 = new LuStatement();
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
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
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
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		LuStatement stmt111 = new LuStatement();
		stmt111.setId("S111");
		stmt111.setParent(stmt11);
		ReqComponent reqComp0 = new ReqComponent();
		reqComp0.setId("R0");
		stmt111.setRequiredComponents(Arrays.asList(reqComp0));
		
		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		LuStatement stmt122 = new LuStatement();
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
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R-1");
		stmt11.setRequiredComponents(Arrays.asList(reqComp11));

		LuStatement stmt12 = new LuStatement();
		stmt12.setId("S12");
		stmt12.setParent(stmt1);

		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		ReqComponent reqComp121 = new ReqComponent();
		reqComp121.setId("R-2");
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
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
		List<CustomReqComponent> list = parser.getLeafReqComponents(stmt1);

		Assert.assertEquals("R1 and (R2 or (R3 and R4))", expressionReduced);
		Assert.assertEquals(4, list.size());
	}

	@Test
	public void testBooleanExpressionAsReqComponent4() throws Exception {
		LuStatement stmt1 = new LuStatement();
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
		ReqComponent reqComp1 = new ReqComponent();
		reqComp1.setId("R1");
		ReqComponent reqComp2 = new ReqComponent();
		reqComp2.setId("R2");
		stmt111.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));

		LuStatement stmt112 = new LuStatement();
		stmt112.setId("S112");
		stmt112.setParent(stmt11);
		ReqComponent reqComp3 = new ReqComponent();
		reqComp3.setId("R3");
		ReqComponent reqComp4 = new ReqComponent();
		reqComp4.setId("R4");
		stmt112.setRequiredComponents(Arrays.asList(reqComp3, reqComp4));
		
		LuStatement stmt121 = new LuStatement();
		stmt121.setId("S121");
		stmt121.setParent(stmt12);
		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		ReqComponent reqComp8 = new ReqComponent();
		reqComp8.setId("R8");
		stmt122.setRequiredComponents(Arrays.asList(reqComp8));
		
		LuStatement stmt131 = new LuStatement();
		stmt131.setId("S131");
		stmt131.setParent(stmt13);
		ReqComponent reqComp9 = new ReqComponent();
		reqComp9.setId("R9");
		stmt131.setRequiredComponents(Arrays.asList(reqComp9));
		
		LuStatement stmt132 = new LuStatement();
		stmt132.setId("S132");
		stmt132.setParent(stmt13);
		
		LuStatement stmt1211 = new LuStatement();
		stmt1211.setId("S1211");
		stmt1211.setParent(stmt121);
		ReqComponent reqComp5 = new ReqComponent();
		reqComp5.setId("R5");
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		
		LuStatement stmt1212 = new LuStatement();
		stmt1212.setId("S1212");
		stmt1212.setParent(stmt121);
		ReqComponent reqComp6 = new ReqComponent();
		reqComp6.setId("R6");
		ReqComponent reqComp7 = new ReqComponent();
		reqComp7.setId("R7");
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6, reqComp7));
		
		LuStatement stmt1321 = new LuStatement();
		stmt1321.setId("S1321");
		stmt1321.setParent(stmt132);
		ReqComponent reqComp10 = new ReqComponent();
		reqComp10.setId("R10");
		stmt1321.setRequiredComponents(Arrays.asList(reqComp10));

		LuStatement stmt1322 = new LuStatement();
		stmt1322.setId("S1322");
		stmt1322.setParent(stmt132);
		ReqComponent reqComp11 = new ReqComponent();
		reqComp11.setId("R11");
		ReqComponent reqComp12 = new ReqComponent();
		reqComp12.setId("R12");
		stmt1322.setRequiredComponents(Arrays.asList(reqComp11, reqComp12));

		LuStatement stmt1323 = new LuStatement();
		stmt1323.setId("S1323");
		stmt1323.setParent(stmt132);
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
		List<CustomReqComponent> list = parser.getLeafReqComponents(stmt1);
		
		Assert.assertEquals("(R1 or R2) and (R3 and R4) and ((R5 and (R6 or R7)) or R8) and R9 and (R10 or (R11 or R12) or R13)", expressionReduced);
		Assert.assertEquals(13, list.size());
	}
}
