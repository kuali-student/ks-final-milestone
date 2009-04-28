package org.kuali.student.lum.lu.naturallanguage;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class StatementTranslatorTest extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") 
	public LuDao luDao;
	private StatementTranslator translator;
	private static StatementTestUtil statementTestUtil;
	
    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    	statementTestUtil = new StatementTestUtil();
    	statementTestUtil.setLuDao(luDao);
		this.translator = new StatementTranslator();
		this.translator.setLuDao(this.luDao);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
	@Test
	public void testTranslateStatement1() throws Exception {
		// Rule = R1
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		stmt1.setRequiredComponents(Arrays.asList(reqComp));

		this.luDao.create(stmt1);
		
		String translation = translator.translate(stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", translation);
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
		
		stmt11.setRequiredComponents(Arrays.asList(reqComp));
		this.luDao.create(stmt11);

		stmt1.setChildren(Arrays.asList(stmt11));
		this.luDao.create(stmt1);
		
		String translation = translator.translate(stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", translation);
	}

	@Test
	public void testTranslateStatement3() throws Exception {
		// Rule = R1 AND R2
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		LuStatement stmt11 = new LuStatement();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList1 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);

		stmt11.setRequiredComponents(Arrays.asList(reqComp1));
		this.luDao.create(stmt11);

		LuStatement stmt12 = new LuStatement();
		stmt12.setParent(stmt1);
		stmt12.setOperator(StatementOperatorTypeKey.OR);
		List<ReqComponentField> fieldList2 = statementTestUtil.createReqComponentFields("2", "equal_to", "CLUSET-NL-1");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);

		stmt12.setRequiredComponents(Arrays.asList(reqComp2));
		this.luDao.create(stmt12);

		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		this.luDao.create(stmt1);
		
		String translation = translator.translate(stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221 AND Student must have completed 2 of MATH 152, MATH 221", translation);
	}

	@Test
	public void testTranslateStatement4() throws Exception {
		// Rule = R1 AND (R2 OR (R3 AND R4))
		LuStatement stmt1 = new LuStatement();
		stmt1.setId("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setId("S11");
		stmt11.setParent(stmt1);
		// R1;
		List<ReqComponentField> fieldList11 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp11 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList11);
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
		stmt121.setRequiredComponents(Arrays.asList(reqComp121));

		LuStatement stmt122 = new LuStatement();
		stmt122.setId("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqComponentField> fieldList1221 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1221 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1221);
		// R4;
		List<ReqComponentField> fieldList1222 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1222 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1222);
		stmt122.setRequiredComponents(Arrays.asList(reqComp1221, reqComp1222));

		stmt11.setOperator(StatementOperatorTypeKey.AND);

		stmt12.setOperator(StatementOperatorTypeKey.OR);
		stmt12.setChildren(Arrays.asList(stmt121, stmt122));
		
		stmt121.setOperator(StatementOperatorTypeKey.AND);
		stmt122.setOperator(StatementOperatorTypeKey.AND);
		
		stmt1.setOperator(StatementOperatorTypeKey.AND);
		stmt1.setChildren(Arrays.asList(stmt11, stmt12));
		
		String translation = this.translator.translate(stmt1, "KUALI.CATALOG");

		Assert.assertEquals("Student must have completed 0 of MATH 152, MATH 221, MATH 180 AND " +
				"(Student must have completed 1 of MATH 152, MATH 221, MATH 180 OR " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 AND " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180))", translation);
	}

	@Test
	public void testTranslateStatement5() throws Exception {
		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		LuStatement stmt1 = new LuStatement();
		stmt1.setName("S1");

		LuStatement stmt11 = new LuStatement();
		stmt11.setName("S11");
		stmt11.setParent(stmt1);
		LuStatement stmt12 = new LuStatement();
		stmt12.setName("S12");
		stmt12.setParent(stmt1);
		LuStatement stmt13 = new LuStatement();
		stmt13.setName("S13");
		stmt13.setParent(stmt1);

		LuStatement stmt111 = new LuStatement();
		stmt111.setName("S111");
		stmt111.setParent(stmt11);
		// R1;
		List<ReqComponentField> fieldList111 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp1 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList111);
		stmt111.setRequiredComponents(Arrays.asList(reqComp1));
		LuStatement stmt112 = new LuStatement();
		stmt112.setName("S112");
		stmt112.setParent(stmt11);
		// R2;
		List<ReqComponentField> fieldList112 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp2 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList112);
		stmt112.setRequiredComponents(Arrays.asList(reqComp2));

		LuStatement stmt121 = new LuStatement();
		stmt121.setName("S121");
		stmt121.setParent(stmt12);
		LuStatement stmt122 = new LuStatement();
		stmt122.setName("S122");
		stmt122.setParent(stmt12);
		// R3;
		List<ReqComponentField> fieldList122 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp3 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList122);
		stmt122.setRequiredComponents(Arrays.asList(reqComp3));

		LuStatement stmt131 = new LuStatement();
		stmt131.setName("S131");
		stmt131.setParent(stmt13);
		// R4;
		List<ReqComponentField> fieldList131 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp4 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList131);
		stmt131.setRequiredComponents(Arrays.asList(reqComp4));
		LuStatement stmt132 = new LuStatement();
		stmt132.setName("S132");
		stmt132.setParent(stmt13);
		
		LuStatement stmt1211 = new LuStatement();
		stmt1211.setName("S1211");
		stmt1211.setParent(stmt121);
		// R5;
		List<ReqComponentField> fieldList1211 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp5 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1211);
		stmt1211.setRequiredComponents(Arrays.asList(reqComp5));
		LuStatement stmt1212 = new LuStatement();
		stmt1212.setName("S1212");
		stmt1212.setParent(stmt121);
		// R6;
		List<ReqComponentField> fieldList1212 = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp6 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1212);
		stmt1212.setRequiredComponents(Arrays.asList(reqComp6));

		LuStatement stmt1321 = new LuStatement();
		stmt1321.setName("S1321");
		stmt1321.setParent(stmt132);
		// R7;
		List<ReqComponentField> fieldList1321 = statementTestUtil.createReqComponentFields("2", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp7 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1321);
		stmt1321.setRequiredComponents(Arrays.asList(reqComp7));
		LuStatement stmt1322 = new LuStatement();
		stmt1322.setName("S1322");
		stmt1322.setParent(stmt132);
		// R8;
		List<ReqComponentField> fieldList1322 = statementTestUtil.createReqComponentFields("3", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp8 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1322);
		stmt1322.setRequiredComponents(Arrays.asList(reqComp8));
		LuStatement stmt1323 = new LuStatement();
		stmt1323.setName("S1323");
		stmt1323.setParent(stmt132);
		// R9;
		List<ReqComponentField> fieldList1323 = statementTestUtil.createReqComponentFields("0", "greater_than_or_equal_to", "CLUSET-NL-2");
		ReqComponent reqComp9 = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1323);
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
		
		String translation = this.translator.translate(stmt1, "KUALI.CATALOG");

		// Rule: R1 AND R2 AND ((R3 AND R4) OR R5) AND R6 AND (R7 OR R8 OR R9)
		Assert.assertEquals(
				"Student must have completed 0 of MATH 152, MATH 221, MATH 180 AND " +
				"Student must have completed 1 of MATH 152, MATH 221, MATH 180 AND " +
				"((Student must have completed 0 of MATH 152, MATH 221, MATH 180 AND Student must have completed 1 of MATH 152, MATH 221, MATH 180) OR Student must have completed 2 of MATH 152, MATH 221, MATH 180) AND " +
				"Student must have completed 3 of MATH 152, MATH 221, MATH 180 AND " +
				"(Student must have completed 2 of MATH 152, MATH 221, MATH 180 OR Student must have completed 3 of MATH 152, MATH 221, MATH 180 OR Student must have completed 0 of MATH 152, MATH 221, MATH 180)", 
				translation);
	}

	//@Test
	public void testTranslateStatementTree1() throws Exception {
		// Rule = R1
		LuStatement stmt1 = new LuStatement();
		stmt1.setOperator(StatementOperatorTypeKey.AND);

		LuStatement stmt11 = new LuStatement();
		stmt11.setParent(stmt1);
		stmt11.setOperator(StatementOperatorTypeKey.AND);
		List<ReqComponentField> fieldList = statementTestUtil.createReqComponentFields("1", "greater_than_or_equal_to", "CLUSET-NL-1");
		ReqComponent reqComp = statementTestUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList);
		
		stmt11.setRequiredComponents(Arrays.asList(reqComp));
		this.luDao.create(stmt11);

		stmt1.setChildren(Arrays.asList(stmt11));
		this.luDao.create(stmt1);
		
		NLTranslationNode root = translator.translateAsTree(stmt1, "KUALI.CATALOG");

System.out.println("\n\n\nroot.extr="+root.getBooleanExpression());		
System.out.println("root.translation="+root.getNLTranslation());		
System.out.println("root.childNodes.id="+root.getChildNodes().get(0).getId());		
System.out.println("root.childNodes.expr="+root.getChildNodes().get(0).getBooleanExpression());		
System.out.println("root.childNodes.trans="+root.getChildNodes().get(0).getNLTranslation());		
System.out.println("\n\n\n");
	//Assert.assertEquals("Student must have completed 1 of MATH 152, MATH 221", translation);
	}

}
