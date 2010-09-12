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
package org.kuali.student.lum.nlt.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageUtil;
import org.kuali.student.lum.nlt.service.TranslationService;

@Daos({@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-nl.sql")})
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestTranslationServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.nlt.service.impl.TranslationServiceImpl", additionalContextFile="classpath:nl-additional-context.xml")
    public TranslationService client;

	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
    	assertEquals("Student must have completed 1 of MATH 152, MATH 180", nl);
    }

    @Test
    public void testTranslateGPAReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = client.getNaturalLanguageForReqComponent("REQCOMP-NL-2", "KUALI.CATALOG", "en");
        assertEquals("Student needs a minimum GPA of 3.5", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
		
        naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "de");
        assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

    private ReqComponentInfo createReqComponent1() {
        ReqComponentInfo reqCompInfo = new ReqComponentInfo();
        reqCompInfo.setId("REQCOMP-NL-1");
        reqCompInfo.setType("kuali.reqCompType.courseList.nof");
        
        List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
        
        ReqCompFieldInfo field1 = new ReqCompFieldInfo();
        field1.setId("reqCompFieldType.requiredCount");
        field1.setValue("1");
        fieldList.add(field1);
        
        ReqCompFieldInfo field2 = new ReqCompFieldInfo();
        field2.setId("reqCompFieldType.operator");
        field2.setValue("greater_than_or_equal_to");
        fieldList.add(field2);
        
        ReqCompFieldInfo field3 = new ReqCompFieldInfo();
        field3.setId("reqCompFieldType.cluSet");
        field3.setValue("CLUSET-NL-1");
        fieldList.add(field3);
        
        reqCompInfo.setReqCompFields(fieldList);
        return reqCompInfo;
    }
    
	@Test
	public void testGetNaturalLanguageForReqComponentInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        ReqComponentInfo reqCompInfo = createReqComponent1();
		String naturalLanguage = client.getNaturalLanguageForReqComponentInfo(reqCompInfo, "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponentInfo_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        ReqComponentInfo reqCompInfo = createReqComponent1();
		String naturalLanguage = client.getNaturalLanguageForReqComponentInfo(reqCompInfo, "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForReqComponentInfo(reqCompInfo, "KUALI.CATALOG", "de");
        assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForReqComponentInfo(reqCompInfo, "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement_NullCluId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement(null, "STMT-5", "KUALI.CATALOG", "en");
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement_EmptyCluId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("", "STMT-5", "KUALI.CATALOG", "en");
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG", null);
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG", null);
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "de");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement_Complex() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-101", "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: " +
				"((Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 3.5) or " +
				"Student must have completed MATH 180 or MATH 200) and " +
				"(Student must have completed 1 of MATH 152, MATH 180 or " +
				"Student must have completed 2 of MATH 152, MATH 221, MATH 180)", naturalLanguage);
	}

	/*private List<ReqCompFieldInfo> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		fieldList.add(field1);
		
		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("reqCompFieldType.operator");
		field2.setValue(operator);
		fieldList.add(field2);
		
		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId(reqCompFieldType);
		field3.setValue(id);
		fieldList.add(field3);
		
		return fieldList;
    }

	private ReqComponentInfo createReqComponent(String reqComponentType, List<ReqCompFieldInfo> fieldList) throws DoesNotExistException {
		ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
		reqCompType.setId(reqComponentType);

		ReqComponentInfo reqComp = new ReqComponentInfo();
		reqComp.setReqCompFields(fieldList);
		reqComp.setType(reqComponentType);
		
		return reqComp;
    }*/

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Simple_CluSet_EmptyCluId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Simple_CluSet_NullCluId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo(null, statementInfo, "KUALI.CATALOG", "en");

		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Simple_CluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo1_Simple_Clu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo1_Simple_Clu_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "de");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", naturalLanguage);

		naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo2_Simple_Clu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setStatementTypeId("kuali.luStatementType.prereqAcademicReadiness");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = NaturalLanguageUtil.createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponentInfo reqComp1 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.1of2", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = NaturalLanguageUtil.createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = NaturalLanguageUtil.createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed MATH 152 or MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Complex1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		//Rule: ((R1 OR R2) AND R3) OR R4
		LuNlStatementInfo s1 = NaturalLanguageUtil.getComplexLuNlStatementInfo1();
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", s1, "KUALI.CATALOG", "en");

		assertEquals("Requirement for MATH 152 Linear Systems: " +
				"((Student must have completed none of MATH 152, MATH 180 " +
				"or Student must have completed all of MATH 152, MATH 221, MATH 180) " +
				"and Student must have completed 2 of MATH 152, MATH 221, MATH 180) " +
				"or Student must have completed all of MATH 221, MATH 180, MATH 200, MATH 215", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Complex2() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		//Rule: ((R1 OR R2) AND (R3 OR R4)) OR R5 OR (R6 AND (R7 OR R8))
		LuNlStatementInfo s1 = NaturalLanguageUtil.getComplexLuNlStatementInfo2();
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", s1, "KUALI.CATALOG", "en");

		assertEquals("Requirement for MATH 152 Linear Systems: " +
				"((Student must have completed none of MATH 152, MATH 180 " +
				"or Student must have completed 1 of MATH 152, MATH 180) " +
				"and (Student must have completed none of MATH 152, MATH 221, MATH 180 " +
				"or Student must have completed 1 of MATH 152, MATH 221, MATH 180)) " +
				"or Student must have completed all of MATH 152, MATH 221, MATH 180 " +
				"or (Student must have completed 1 of MATH 221, MATH 180, MATH 200, MATH 215 " +
				"and (Student must have completed 2 of MATH 152, MATH 221, MATH 180 " +
				"or Student must have completed all of MATH 221, MATH 180, MATH 200, MATH 215))", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForStatementAsTree_EmptyCluId() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementAsTree("", "STMT-5", "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementAsTree_NullCluId() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementAsTree(null, "STMT-5", "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementAsTree() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementAsTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementAsTree_EnglishGerman() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementAsTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());

		rootNode = client.getNaturalLanguageForStatementAsTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "de");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());

		rootNode = client.getNaturalLanguageForStatementAsTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	private LuStatementInfo createLuStatement1() {
		LuStatementInfo statementInfo = new LuStatementInfo();
		statementInfo.setId("STMT-5");
		statementInfo.setType("kuali.luStatementType.prereqAcademicReadiness");
		List<String> reqCompIdList = Arrays.asList(new String[] {"REQCOMP-NL-1","REQCOMP-NL-4"});
		statementInfo.setReqComponentIds(reqCompIdList);
		statementInfo.setOperator(StatementOperatorTypeKey.OR);
		return statementInfo;
	}
	
	@Test
	public void testGetNaturalLanguageForStatementInfoAsTree_EmptyCluId() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		LuStatementInfo statementInfo = createLuStatement1();
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementInfoAsTree("", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementInfoAsTree_NullCluId() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		LuStatementInfo statementInfo = createLuStatement1();
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementInfoAsTree(null, statementInfo, "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementInfoAsTree() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		LuStatementInfo statementInfo = createLuStatement1();
		statementInfo.setCluIds(Arrays.asList(new String[] {"CLU-NL-1"}));
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementInfoAsTree("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementInfoAsTree_EnglishGerman() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		LuStatementInfo statementInfo = createLuStatement1();
		statementInfo.setCluIds(Arrays.asList(new String[] {"CLU-NL-1"}));
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementInfoAsTree("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 + R2", rootNode.getBooleanExpression());
		assertEquals("R1 or R2", rootNode.getProperBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());

		rootNode = client.getNaturalLanguageForStatementInfoAsTree("CLU-NL-1", statementInfo, "KUALI.CATALOG", "de");
		assertEquals("Voraussetzung fur die MATH 152 Linear Systems: Student muss abgeschlossen 1 von MATH 152, MATH 180 oder Student muss abgeschlossen 2 von MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());

		rootNode = client.getNaturalLanguageForStatementInfoAsTree("CLU-NL-1", statementInfo, "KUALI.CATALOG", "en");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}
}
