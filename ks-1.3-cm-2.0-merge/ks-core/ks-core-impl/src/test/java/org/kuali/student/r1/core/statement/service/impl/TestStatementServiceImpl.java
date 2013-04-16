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

package org.kuali.student.r1.core.statement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.kuali.student.r1.common.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r1.core.statement.config.context.lu.MockCluInfo;
import org.kuali.student.r1.core.statement.config.context.lu.MockCluSetInfo;
import org.kuali.student.r1.core.statement.config.context.lu.CourseListContextImpl;
import org.kuali.student.r1.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.r1.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r1.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.dto.StatementInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.r1.core.statement.naturallanguage.ReqComponentFieldTypes;
import org.kuali.student.r1.core.statement.service.StatementService;

@Daos({@Dao(value = "org.kuali.student.r1.core.statement.dao.impl.StatementDaoImpl")})
@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementServiceImpl extends AbstractServiceTest {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Client(value = "org.kuali.student.r1.core.statement.service.impl.StatementServiceImpl", additionalContextFile="classpath:statement-additional-context.xml")
    public StatementService statementService;

    private RefStatementRelationInfo newDto;

	@BeforeClass
	public static void beforeClass() {
		// Add test data
		MockCluInfo clu1 = new MockCluInfo("CLU-NL-1", "MATH152", "MATH 152", "MATH 152 Linear Systems");
		MockCluInfo clu2 = new MockCluInfo("CLU-NL-3", "MATH180", "MATH 180", "MATH 180 Differential Calculus with Physical Applications");
		MockCluInfo clu3 = new MockCluInfo("CLU-NL-2", "MATH221", "MATH 221", "MATH 221 Matrix Algebra");

		// Add CLUs
		// Clu list order is important for natural language translation
		// Adding clu2, clu1 doesn't work for method testGetNaturalLanguageForStatement(), testGetNaturalLanguageForReqComponent
		List<MockCluInfo> cluList1 = new ArrayList<MockCluInfo>();
		cluList1.add(clu1);
		cluList1.add(clu2);

		// Clu list order is important for natural language translation
		// Adding clu1, clu2, clu3 doesn't work for method testGetNaturalLanguageForStatement()
		List<MockCluInfo> cluList2 = new ArrayList<MockCluInfo>();
		cluList2.add(clu1);
		cluList2.add(clu3);
		cluList2.add(clu2);

		List<MockCluInfo> cluListAll = new ArrayList<MockCluInfo>();
		cluListAll.add(clu1);
		cluListAll.add(clu2);
		cluListAll.add(clu3);

		CourseListContextImpl.setCluInfo(cluListAll);

		// Add CLU Sets
		List<MockCluSetInfo> cluSetList = new ArrayList<MockCluSetInfo>();
		MockCluSetInfo cluSet1 = new MockCluSetInfo("CLUSET-NL-1", cluList1);
		MockCluSetInfo cluSet2 = new MockCluSetInfo("CLUSET-NL-2", cluList2);
		cluSetList.add(cluSet1);
		cluSetList.add(cluSet2);

		CourseListContextImpl.setCluSetInfo(cluSetList);
	}

	@After
	public void afterTest() throws Exception {
		if(newDto != null) {
			try {
				statementService.deleteRefStatementRelation(newDto.getId());
			} catch (DoesNotExistException e) {
				System.out.println("statementService.deleteRefStatementRelation: " + e.getMessage());
			}
			newDto = null;
		}
	}

    private RefStatementRelationInfo createRefStatementRelation() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        RefStatementRelationInfo dto = new RefStatementRelationInfo();
    	dto.setRefObjectId("CLU-NL-1"); //MATH152
    	dto.setRefObjectTypeKey("clu"); // CLU
    	dto.setState("ACTIVE");
    	dto.setStatementId("STMT-1");
    	dto.setType("clu.prerequisites");
    	dto.setEffectiveDate(effDate.getTime());
    	dto.setExpirationDate(expDate.getTime());

    	newDto = statementService.createRefStatementRelation(dto);

		return newDto;
    }

	@Test
	// FIXME - Investigate why adding clu1, clu3, clu2 works but adding clu1, clu2, clu3 doesn't work
	public void testGetNaturalLanguageForStatement() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	String nl = statementService.getNaturalLanguageForStatement("STMT-5", "KUALI.RULE", "en");
//		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", nl);
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", nl);
	}

	@Test
	public void testGetNaturalLanguageForRefStatementRelation() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		String nl = statementService.getNaturalLanguageForRefStatementRelation("ref-stmt-rel-5", "KUALI.RULE", "en");
		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", nl);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.RULE", "en");
    	assertEquals("Student must have completed 1 of MATH 152, MATH 180", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_1of1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-5", "KUALI.RULE", "en");
    	assertEquals("Student must have completed MATH 180", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_GradeCheck() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-2", "KUALI.RULE", "en");
    	assertEquals("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180", nl);
    }

	@Test(expected=DoesNotExistException.class)
	public void testGetNaturalLanguageForReqComponent_InvalidNlUsageTypeKey() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "xxx", "en");
		fail("Invalid usage type key should have thrown DoesNotExistException");
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.RULE", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.RULE", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);

        naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.RULE", "de");
        assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.RULE", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	private ReqComponentInfo createReqComponent1() {
		ReqComponentInfo reqCompInfo = new ReqComponentInfo();
		reqCompInfo.setId("REQCOMP-NL-1");
		reqCompInfo.setType("kuali.reqComponent.type.courseList.nof");
		reqCompInfo.setState("active");
		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("formatted");
		desc.setPlain("plain");
		reqCompInfo.setDesc(desc);
		reqCompInfo.setEffectiveDate(new Date());

		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();

		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("1");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue("1");
		fieldList.add(field1);

		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("2");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue(">=");
		fieldList.add(field2);

		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("3");
		field3.setType(ReqComponentFieldTypes.CLUSET_KEY.getType());
		field3.setValue("CLUSET-NL-1");
		fieldList.add(field3);

		reqCompInfo.setReqCompFields(fieldList);
		return reqCompInfo;
	}

	@Test
	public void testTranslateReqComponentToNL() throws InvalidParameterException, MissingParameterException, OperationFailedException {
		ReqComponentInfo reqCompInfo = createReqComponent1();
		String naturalLanguage = statementService.translateReqComponentToNL(reqCompInfo, "KUALI.RULE", "en");
		assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

    private List<ReqCompFieldInfo> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();
		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("1");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue(expectedValue);
		fieldList.add(field1);

		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("2");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue(operator);
		fieldList.add(field2);

		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("3");
		field3.setType(reqCompFieldType);
		field3.setValue(id);
		fieldList.add(field3);

		return fieldList;
    }

    private ReqComponentInfo createReqComponent(String reqComponentType, List<ReqCompFieldInfo> fieldList) {
    	ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
    	reqCompType.setId(reqComponentType);

		ReqComponentInfo reqComp = new ReqComponentInfo();
		reqComp.setReqCompFields(fieldList);
		reqComp.setType(reqComponentType);
		//reqComp.setRequiredComponentType(reqCompType);

		return reqComp;
    }

	@Test
    @Ignore
    /**
     * @deprecated Being moved to ProgramRequirementService
     */
	public void testTranslateStatementTreeViewToNL_SimpleStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		StatementTreeViewInfo statementInfo = new StatementTreeViewInfo();
		statementInfo.setType("kuali.statement.type.course.academicReadiness.prereq");
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("1", "greater_than_or_equal_to", ReqComponentFieldTypes.CLU_KEY.getType(), "CLU-NL-1,CLU-NL-3");
		ReqComponentInfo reqComp1 = createReqComponent("kuali.reqComponent.type.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("2", "greater_than_or_equal_to", ReqComponentFieldTypes.CLUSET_KEY.getType(), "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = createReqComponent("kuali.reqComponent.type.courseList.nof", fieldList2);
		reqComp2.setId("req-2");

		statementInfo.setReqComponents(Arrays.asList(reqComp1, reqComp2));

		String naturalLanguage = statementService.translateStatementTreeViewToNL(statementInfo, "KUALI.RULE", "en");

		assertEquals("Student must have completed 1 of MATH 152, MATH 180 or Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
    public void testGetNlUsageType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		NlUsageTypeInfo info = statementService.getNlUsageType("KUALI.REQCOMP.EXAMPLE");

        GregorianCalendar grepEff = new GregorianCalendar(2010, 00, 01, 1, 1, 1);
        GregorianCalendar grepExp = new GregorianCalendar(2010, 11, 31, 1, 1, 1);

        assertNotNull(info);
        assertEquals("NlUsageTypeInfo[id=KUALI.REQCOMP.EXAMPLE]", info.toString());
        assertEquals("KUALI.REQCOMP.EXAMPLE", info.getId());
        assertEquals("Requirement Component Example", info.getName());
        assertEquals("Kuali Requirement Component Rule Example", info.getDescr());
        assertEquals(grepEff.getTime(), info.getEffectiveDate());
        assertEquals(grepExp.getTime(), info.getExpirationDate());
	}

	@Test(expected=DoesNotExistException.class)
    public void testGetNlUsageType_InvalidType() throws Exception {
		statementService.getNlUsageType("xxx");
		fail("Invalid usage type key should have thrown DoesNotExistException");
	}

	@Test
    public void testGetNlUsageTypes() throws OperationFailedException {
		List<NlUsageTypeInfo> infoList = statementService.getNlUsageTypes();
		assertTrue(infoList.size() > 0);
	}

	private boolean containsTypeId(List<? extends TypeInfo> types, String id) {
		for(TypeInfo type : types) {
			if(type.getId().equals(id)) return true;
		}
		return false;
	}

    @Test
	public void testGetStatementTypes() throws OperationFailedException {
    	List<StatementTypeInfo> types = statementService.getStatementTypes();
		assertEquals(3, types.size());
		assertTrue(containsTypeId(types, "kuali.statement.type.course"));
		assertTrue(containsTypeId(types, "kuali.statement.type.course.academicReadiness.prereq"));
		assertTrue(containsTypeId(types, "kuali.statement.type.course.academicReadiness.coreq"));
	}

    @Test
	public void testGetStatementType() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
    	StatementTypeInfo type = statementService.getStatementType("kuali.statement.type.course");

        GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2000, 11, 31, 0, 0, 0);

    	assertNotNull(type);
		assertEquals("kuali.statement.type.course", type.getId());
		assertNotNull(type.getAllowedStatementTypes());
		assertEquals(2, type.getAllowedStatementTypes().size());
		assertEquals(0, type.getAllowedReqComponentTypes().size());
		assertEquals(0, type.getAttributes().size());
		assertEquals("Rules used in the evaluation of a person's academic readiness for enrollment in a LU.", type.getDescr());
		assertEquals(effDate.getTime(), type.getEffectiveDate());
		assertEquals(expDate.getTime(), type.getExpirationDate());
		assertEquals("Overall Academic Readiness Rules", type.getName());
	}

    @Test
    public void testGetStatementTypesForStatementType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<String> allowedTypes = statementService.getStatementTypesForStatementType("kuali.statement.type.course");

		assertEquals(2, allowedTypes.size());
		assertEquals("kuali.statement.type.course.academicReadiness.prereq", allowedTypes.get(0));
		assertEquals("kuali.statement.type.course.academicReadiness.coreq", allowedTypes.get(1));
    }

    @Test
    public void testGetStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        StatementInfo stmt = statementService.getStatement("STMT-2");

        assertNotNull(stmt);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.statement.type.course.academicReadiness.prereq");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(), "ACTIVE");
        assertEquals(stmt.getName(), "STMT 2");
        assertEquals(stmt.getDesc().getPlain(), "Statement 2");

        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(3, reqCompIds.size());

        assertTrue(reqCompIds.contains("REQCOMP-1"));
        assertTrue(reqCompIds.contains("REQCOMP-2"));
        assertTrue(reqCompIds.contains("REQCOMP-3"));

        MetaInfo mf = stmt.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    private boolean containsId(List<? extends Idable> idList, String id) {
    	for(Idable iden : idList) {
    		if(iden.getId().equals(id)) return true;
    	}
    	return false;
    }

    @Test
    public void testGetStatementsUsingStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<StatementInfo> stmtList = statementService.getStatementsUsingStatement("STMT-101");

    	assertNotNull(stmtList);
    	assertEquals(2, stmtList.size());
    	assertTrue(containsId(stmtList, "STMT-102"));
    	assertTrue(containsId(stmtList, "STMT-103"));
    }

    @Test
    public void testGetStatementsUsingReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<StatementInfo> stmtList = statementService.getStatementsUsingReqComponent("REQCOMP-NL-1");

        assertNotNull(stmtList);
        assertEquals(4, stmtList.size());
    	assertTrue(containsId(stmtList, "STMT-3"));
    	assertTrue(containsId(stmtList, "STMT-5"));
    	assertTrue(containsId(stmtList, "STMT-104"));
    	assertTrue(containsId(stmtList, "STMT-106"));
    }

    private List<StatementInfo> getStatements(List<String> statementIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<StatementInfo> stmtList = null;

        for (String statementId : statementIdList) {
            StatementInfo stmtInfo = null;
            stmtList = (stmtList == null)? new ArrayList<StatementInfo>() : stmtList;
            stmtInfo = statementService.getStatement(statementId);
            stmtList.add(stmtInfo);
        }
        return stmtList;
    }

    private List<ReqComponentInfo> getReqComponents(List<String> reqComponentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentInfo> reqCompList = null;

        for (String reqComponentId : reqComponentIdList) {
            ReqComponentInfo reqComponentInfo = null;
            reqCompList = (reqCompList == null)? new ArrayList<ReqComponentInfo>() : reqCompList;
            reqComponentInfo = statementService.getReqComponent(reqComponentId);
            reqCompList.add(reqComponentInfo);
        }
        return reqCompList;
    }

    @Test
    public void testGetStatements_DetailInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<String> statementIdList = Arrays.asList(new String[] {"STMT-2"});
        List<StatementInfo> stmtList = getStatements(statementIdList);

        assertNotNull(stmtList);
        assertEquals(1, stmtList.size());

        StatementInfo stmt = stmtList.get(0);

        assertEquals("STMT-2", stmt.getId());
        assertEquals("kuali.statement.type.course.academicReadiness.prereq", stmt.getType());
        assertEquals(StatementOperatorTypeKey.AND, stmt.getOperator());
        assertEquals("ACTIVE", stmt.getState());
        assertEquals("STMT 2", stmt.getName());
        assertEquals("Statement 2", stmt.getDesc().getPlain());

        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(3, reqCompIds.size());

        assertTrue(reqCompIds.contains("REQCOMP-1"));
        assertTrue(reqCompIds.contains("REQCOMP-2"));
        assertTrue(reqCompIds.contains("REQCOMP-3"));

        MetaInfo mf = stmt.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    @Test
    public void testGetStatementsByType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<StatementInfo> stmtList = statementService.getStatementsByType("kuali.statement.type.course.academicReadiness.prereq");

        assertNotNull(stmtList);
        assertEquals(13, stmtList.size());

        StatementInfo stmt = stmtList.get(0);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.statement.type.course.academicReadiness.prereq");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(), "ACTIVE");
        assertEquals(stmt.getName(), "STMT 2");
        assertEquals(stmt.getDesc().getPlain(), "Statement 2");

        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(3, reqCompIds.size());

        assertTrue(reqCompIds.contains("REQCOMP-1"));
        assertTrue(reqCompIds.contains("REQCOMP-2"));
        assertTrue(reqCompIds.contains("REQCOMP-3"));

        MetaInfo mf = stmt.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    @Test(expected=DoesNotExistException.class)
    public void testGetStatement_InvalidStatementId() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        statementService.getStatement("invalid.stmt.id");
        fail("statementService.getStatement should have failed getting a statement by an invalid id");
    }

    @Test
    public void testGetStatementByType_InvalidType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<StatementInfo> stmtList = statementService.getStatementsByType("invalid.stmttype");
        assertNull(stmtList);
    }

    @Test
    public void testStatementStatementRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        StatementInfo stmt = statementService.getStatement("STMT-1");
        assertNotNull(stmt);
        assertEquals(stmt.getId(), "STMT-1");

        List<String> stmtIds = stmt.getStatementIds();
        assertEquals(1, stmtIds.size());
        assertTrue(stmtIds.contains("STMT-2"));
    }

    private ReqComponentTypeInfo getReqComponentTypeInfo(List<ReqComponentTypeInfo> reqCompTypeInfoList, String id) {
    	for(ReqComponentTypeInfo reqCompType : reqCompTypeInfoList) {
    		if(reqCompType.getId().equals(id)) {
    			return reqCompType;
    		}
    	}
    	return null;
    }

    @Test
    public void testGetReqComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = statementService.getReqComponentTypes();
                
        assertNotNull(reqCompTypeInfoList);
        assertEquals(9, reqCompTypeInfoList.size());
        // Test StatementType.allowedReqComponentTypes sort order
        assertEquals(reqCompTypeInfoList.get(0).getId(), "kuali.reqComponent.type.courseList.none");
        assertEquals(reqCompTypeInfoList.get(1).getId(), "kuali.reqComponent.type.courseList.all");
        assertEquals(reqCompTypeInfoList.get(2).getId(), "kuali.reqComponent.type.courseList.1of1");
        assertEquals(reqCompTypeInfoList.get(3).getId(), "kuali.reqComponent.type.courseList.1of2");
        assertEquals(reqCompTypeInfoList.get(4).getId(), "kuali.reqComponent.type.courseList.nof");
        assertEquals(reqCompTypeInfoList.get(5).getId(), "kuali.reqComponent.type.gradecheck");
        assertEquals(reqCompTypeInfoList.get(6).getId(), "kuali.reqComponent.type.grdCondCourseList");
        assertEquals(reqCompTypeInfoList.get(7).getId(), "kuali.reqComponent.type.courseList.coreq.all");
        assertEquals(reqCompTypeInfoList.get(8).getId(), "kuali.reqComponent.type.courseList.coreq.oneof");
    }

    @Test
    public void testGetReqComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentTypeInfo rqt = statementService.getReqComponentType("kuali.reqComponent.type.courseList.all");

        assertNotNull(rqt);
        assertEquals(rqt.getId(), "kuali.reqComponent.type.courseList.all");
        assertEquals(rqt.getDescr(), "Student must have completed all of <courses>");
        assertEquals(rqt.getName(), "All of required courses");
        assertEquals(rqt.getEffectiveDate(), df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(), df.parse("20001231"));
    }

    @Test
    public void testGetReqComponentFieldType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentTypeInfo rqt = statementService.getReqComponentType("kuali.reqComponent.type.courseList.all");

        List<ReqCompFieldTypeInfo> reqftList = rqt.getReqCompFieldTypeInfos();
        assertNotNull(reqftList);
        assertEquals(1, reqftList.size());

        ReqCompFieldTypeInfo ftInfo = reqftList.get(0);
        assertEquals(ftInfo.getId(), ReqComponentFieldTypes.CLUSET_KEY.getType());

        FieldDescriptor fd = ftInfo.getFieldDescriptor();
        assertEquals(fd.getName(),"CLUSET");
        assertEquals(fd.getDesc(),"CLUSET");
        assertEquals(fd.getDataType(),"string");
    }

    @Test
    public void testGetReqComponentTypesForStatementType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = statementService.getReqComponentTypesForStatementType("kuali.statement.type.course.academicReadiness.prereq");

        assertEquals(6, reqCompTypeInfoList.size());
        // Test StatementType.allowedReqComponentTypes sort order
        assertEquals(reqCompTypeInfoList.get(0).getId(), "kuali.reqComponent.type.courseList.1of1");
        assertEquals(reqCompTypeInfoList.get(1).getId(), "kuali.reqComponent.type.courseList.nof");
        assertEquals(reqCompTypeInfoList.get(2).getId(), "kuali.reqComponent.type.courseList.all");
        assertEquals(reqCompTypeInfoList.get(3).getId(), "kuali.reqComponent.type.courseList.1of2");
        assertEquals(reqCompTypeInfoList.get(4).getId(), "kuali.reqComponent.type.grdCondCourseList");
        assertEquals(reqCompTypeInfoList.get(5).getId(), "kuali.reqComponent.type.gradecheck");
    }

    @Test
    public void testGetReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentInfo reqComp = statementService.getReqComponent("REQCOMP-1");

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-1");
        assertEquals(reqComp.getType(), "kuali.reqComponent.type.courseList.all");
        assertEquals(reqComp.getDesc().getPlain(), "Req Comp 1");
        assertEquals(reqComp.getEffectiveDate(), df.parse("20010101"));
        assertEquals(reqComp.getExpirationDate(), df.parse("20020101"));
        assertEquals(reqComp.getState(), "ACTIVE");

        MetaInfo mf = reqComp.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    private ReqComponentInfo getReqComponent(List<ReqComponentInfo> reqCompList, String id) {
        for(ReqComponentInfo req : reqCompList) {
            if(id.equals(req.getId())) {
                return req;
            }
        }
        return null;
    }

    @Test
    public void testGetReqComponents() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<String> reqComponentIdList = Arrays.asList(new String[] {"REQCOMP-NL-1","REQCOMP-NL-2", "REQCOMP-NL-3", "REQCOMP-NL-4"});
        List<ReqComponentInfo> reqCompList = getReqComponents(reqComponentIdList);

        assertNotNull(reqCompList);
        assertEquals(4, reqCompList.size());
        assertNotNull(getReqComponent(reqCompList, "REQCOMP-NL-1"));
        assertNotNull(getReqComponent(reqCompList, "REQCOMP-NL-2"));
        assertNotNull(getReqComponent(reqCompList, "REQCOMP-NL-3"));
        assertNotNull(getReqComponent(reqCompList, "REQCOMP-NL-4"));
    }

    @Test
    public void testGetReqComponents_DetailInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<String> reqComponentIdList = Arrays.asList(new String[] {"REQCOMP-1"});
        List<ReqComponentInfo> reqCompList = getReqComponents(reqComponentIdList);


        assertNotNull(reqCompList);
        assertEquals(1, reqCompList.size());

        ReqComponentInfo reqComp = reqCompList.get(0);

        assertEquals("REQCOMP-1", reqComp.getId());

        assertEquals("kuali.reqComponent.type.courseList.all",reqComp.getType());
        assertEquals("Req Comp 1", reqComp.getDesc().getPlain());
        assertEquals(df.parse("20010101"), reqComp.getEffectiveDate());
        assertEquals(df.parse("20020101"),reqComp.getExpirationDate());
        assertEquals("ACTIVE", reqComp.getState());

        MetaInfo mf = reqComp.getMetaInfo();

        assertEquals("CREATEID",mf.getCreateId());
        assertEquals("UPDATEID", mf.getUpdateId());
        assertEquals(df.parse("20000101"), mf.getCreateTime());
        assertEquals(df.parse("20010101"), mf.getUpdateTime());
    }

    @Test
    public void testGetReqComponentsByType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentInfo> reqCompList = statementService.getReqComponentsByType("kuali.reqComponent.type.grdCondCourseList");

        assertNotNull(reqCompList);
        assertEquals(1, reqCompList.size());

        ReqComponentInfo reqComp = reqCompList.get(0);

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-2");
        assertEquals(reqComp.getType(), "kuali.reqComponent.type.grdCondCourseList");
        assertEquals(reqComp.getDesc().getPlain(), "Req Comp 2");
        assertEquals(reqComp.getEffectiveDate(), df.parse("20010101"));
        assertEquals(reqComp.getExpirationDate(), df.parse("20020101"));
        assertEquals(reqComp.getState(), "ACTIVE");

        MetaInfo mf = reqComp.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    @Test
    public void testGetReqComponentsByType_InvalidType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<ReqComponentInfo> reqCompList = statementService.getReqComponentsByType("invalid.reqcomptype");
        assertNull(reqCompList);
    }

    @Test(expected=DoesNotExistException.class)
    public void testGetReqComponentType_InvalidType_DoesNotExistException() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        statementService.getReqComponentType("invalid.reqcomp");
        fail("Invalid ReqComponentType should have thrown DoesNotExistException");
    }

    @Test
    public void testCreateStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {
        StatementInfo stmt = new StatementInfo();
        RichTextInfo statement3 = new RichTextInfo();
        statement3.setPlain("Statement 3");
        statement3.setFormatted("<p>Statement 3</p>");
        stmt.setDesc(statement3);
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");
        stmt.setType("kuali.statement.type.course.academicReadiness.coreq");

        MetaInfo mf = new MetaInfo();

        mf.setCreateId("CREATEID");
        mf.setUpdateId("UPDATEID");

        stmt.setMetaInfo(mf);

        StatementInfo createdSmt = statementService.createStatement("kuali.statement.type.course.academicReadiness.coreq", stmt);

        assertNotNull(createdSmt.getId());
        assertEquals(createdSmt.getType(), "kuali.statement.type.course.academicReadiness.coreq");
        assertEquals(createdSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(createdSmt.getState(), "ACTIVE");
        assertEquals(createdSmt.getName(), "STMT 3");
        assertEquals(createdSmt.getDesc().getPlain(), "Statement 3");
    }

    @Test
    public void testUpdateStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatementInfo stmt = statementService.getStatement("STMT-1");
        RichTextInfo richText = new RichTextInfo();
        richText.setPlain("Statement 3");
        richText.setFormatted("<p>Statement 3</p>");
        stmt.setDesc(richText);
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");
        MetaInfo mfOrg = stmt.getMetaInfo();
        stmt.setMetaInfo(mfOrg);

        StatementInfo updSmt = statementService.updateStatement(stmt.getId(), stmt);

        assertNotNull(updSmt.getId());
        assertEquals(updSmt.getType(), "kuali.statement.type.course");
        assertEquals(updSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(updSmt.getState(), "ACTIVE");
        assertEquals(updSmt.getName(), "STMT 3");
        assertEquals(updSmt.getDesc().getPlain(), "Statement 3");
    }

    @Test(expected=VersionMismatchException.class)
    public void testUpdateStatement_VersionMismatchException() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatementInfo stmt = statementService.getStatement("STMT-1");
        RichTextInfo richText = new RichTextInfo();
        richText.setPlain("Statement 3");
        stmt.setDesc(richText);
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");

        MetaInfo mf = new MetaInfo();
        stmt.setMetaInfo(mf);

        statementService.updateStatement(stmt.getId(), stmt);
        fail("Should throw version mismatch exception");
    }

    @Test
    public void testDeleteStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatementInfo stmt = statementService.getStatement("STMT-2");
        StatusInfo si = statementService.deleteStatement(stmt.getId());
		assertNotNull(si);
        assertTrue(si.getSuccess());
		assertNotNull(si.getMessage());
		try{
			statementService.getStatement(stmt.getId());
			assertTrue(false);
		}catch(DoesNotExistException e){
			assertTrue(true);
		}
    }

    @Test
    public void testCreateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {
        ReqComponentInfo req = new ReqComponentInfo();

        RichTextInfo richText = new RichTextInfo();
        richText.setPlain("Required Component 5");
        richText.setFormatted("<p>Required Component 5</p>");
        req.setDesc(richText);
        req.setEffectiveDate(df.parse("20010101"));
        req.setExpirationDate(df.parse("20020101"));
        req.setState("ACTIVE");
        req.setType("kuali.reqComponent.type.courseList.all");

        MetaInfo mf = new MetaInfo();
        req.setMetaInfo(mf);

        ReqComponentInfo createdReq = statementService.createReqComponent("kuali.reqComponent.type.courseList.all", req);

        assertNotNull(createdReq.getId());
        assertEquals(createdReq.getType(), "kuali.reqComponent.type.courseList.all");
        assertEquals(createdReq.getEffectiveDate(), df.parse("20010101"));
        assertEquals(createdReq.getExpirationDate(), df.parse("20020101"));
        assertEquals(createdReq.getState(), "ACTIVE");
        assertEquals(createdReq.getDesc().getPlain(), "Required Component 5");
    }

    @Test
    public void testUpdateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        RichTextInfo richText = new RichTextInfo();
        richText.setPlain("Req Comp 3");
        richText.setFormatted("<p>Req Comp 3</p>");
        req.setDesc(richText);
        req.setState("IN_PROGRESS");

        MetaInfo mfOrg = req.getMetaInfo();
        req.setMetaInfo(mfOrg);

        ReqComponentInfo updReq = statementService.updateReqComponent(req.getId(), req);

        assertNotNull(updReq.getId());
        assertEquals(updReq.getType(), "kuali.reqComponent.type.courseList.all");
        assertEquals(updReq.getState(), "IN_PROGRESS");
        assertEquals(updReq.getDesc().getPlain(), "Req Comp 3");
        assertEquals(updReq.getReqCompFields().size(), 0);
    }

    @Test(expected=VersionMismatchException.class)
    public void testUpdateReqComponent_VersionMismatchException() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        RichTextInfo richText = new RichTextInfo();
        richText.setPlain("Req Comp 3");
        richText.setFormatted("<p>Req Comp 3</p>");
        req.setDesc(richText);
        req.setState("IN_PROGRESS");

        MetaInfo mf = new MetaInfo();
        req.setMetaInfo(mf);

        statementService.updateReqComponent(req.getId(), req);
        fail("Should throw version mismatch exception");
    }

    @Test
    public void testUpdateReqComponentForField() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");

        assertEquals(req.getReqCompFields().size(), 0);

        ReqCompFieldInfo rcfInfo = new ReqCompFieldInfo();
        rcfInfo.setType(ReqComponentFieldTypes.CLU_KEY.getType());
        rcfInfo.setValue("MATH 101");

        List<ReqCompFieldInfo> reqCompField = new ArrayList<ReqCompFieldInfo>();
        reqCompField.add(rcfInfo);
        req.setReqCompFields(reqCompField);

        MetaInfo mfOrg = req.getMetaInfo();
        req.setMetaInfo(mfOrg);

        ReqComponentInfo updReq = statementService.updateReqComponent(req.getId(), req);

        assertEquals(updReq.getReqCompFields().size(), 1);
        ReqCompFieldInfo newrcfInfo = updReq.getReqCompFields().get(0);

        assertNotNull(updReq.getId());
        assertNotNull(newrcfInfo);
        assertEquals("MATH 101", newrcfInfo.getValue());
        assertEquals(ReqComponentFieldTypes.CLU_KEY.getType(), newrcfInfo.getType());
    }

    @Test
    public void testDeleteReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        StatusInfo si = statementService.deleteReqComponent(req.getId());
		assertNotNull(si);
        assertTrue(si.getSuccess());
		assertNotNull(si.getMessage());
    }

    @Test
    public void testGetRefObjectTypes() throws OperationFailedException {
    	List<String> objectTypeIds = statementService.getRefObjectTypes();
        assertNotNull(objectTypeIds);
        assertEquals(1, objectTypeIds.size());
        assertEquals("clu", objectTypeIds.get(0));
    }

    @Test
    public void testGetRefObjectSubTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<String> objectSubTypeIds = statementService.getRefObjectSubTypes("clu");
        assertNotNull(objectSubTypeIds);
        assertEquals(2, objectSubTypeIds.size());
        assertEquals("course", objectSubTypeIds.get(0));
        assertEquals("program", objectSubTypeIds.get(1));
    }

    @Test
    public void testCreateRefStatementRelation() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        RefStatementRelationInfo newDto = createRefStatementRelation();

    	assertNotNull(newDto);
    	assertNotNull(newDto.getId());
    	assertNotNull(newDto.getMetaInfo());
        assertEquals("CLU-NL-1", newDto.getRefObjectId());
        assertEquals("clu", newDto.getRefObjectTypeKey());
        assertEquals("ACTIVE", newDto.getState());
        assertEquals("STMT-1", newDto.getStatementId());
        assertEquals("clu.prerequisites", newDto.getType());
        assertEquals(effDate.getTime(), newDto.getEffectiveDate());
        assertEquals(expDate.getTime(), newDto.getExpirationDate());
    }
    @Test
    public void testGetRefStatementRelation() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	RefStatementRelationInfo newDto = createRefStatementRelation();

		GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

    	RefStatementRelationInfo dto = statementService.getRefStatementRelation(newDto.getId());

    	assertNotNull(dto);
    	assertNotNull(dto.getId());
    	assertNotNull(dto.getMetaInfo());
        assertEquals("CLU-NL-1", dto.getRefObjectId());
        assertEquals("clu", dto.getRefObjectTypeKey());
        assertEquals("ACTIVE", dto.getState());
        assertEquals("STMT-1", dto.getStatementId());
        assertEquals("clu.prerequisites", dto.getType());
        assertEquals(effDate.getTime(), dto.getEffectiveDate());
        assertEquals(expDate.getTime(), dto.getExpirationDate());
    }

    @Test
    public void testDeleteRefStatementRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
    	RefStatementRelationInfo newDto = createRefStatementRelation();

		StatusInfo status = this.statementService.deleteRefStatementRelation(newDto.getId());

		assertNotNull(status);
		assertTrue(status.getSuccess());
		assertNotNull(status.getMessage());
    }

    @Test(expected=DoesNotExistException.class)
    public void testDeleteRefStatementRelation_InvalidId() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		this.statementService.deleteRefStatementRelation("xxx");
		fail("StatementService.deleteRefStatementRelation should have thrown a DoesNotExistException");
    }

    @Test(expected=InvalidParameterException.class)
    public void testDeleteRefStatementRelation_InvalidParameter() throws DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException, InvalidParameterException {
		this.statementService.deleteRefStatementRelation("");
		fail("StatementService.deleteRefStatementRelation should have thrown a InvalidParameterException");
    }

    @Test(expected=MissingParameterException.class)
    public void testDeleteRefStatementRelation_MissingParameter() throws DoesNotExistException, InvalidParameterException, OperationFailedException, PermissionDeniedException, MissingParameterException {
		this.statementService.deleteRefStatementRelation(null);
		fail("StatementService.deleteRefStatementRelation should have thrown a MissingParameterException");
    }

    @Test
    @Ignore
    /**
     * @deprecated Being moved to ProgramRequirementService
     */
    public void testGetStatementTreeView() throws Exception {
        // Tree structure should be
        //                          STMT-TV-1:OR
        //          STMT-TV-2:AND                   STMT-TV-3:AND
        //     REQCOMP-TV-1  REQCOMP-TV-2      REQCOMP-TV-3  REQCOMP-TV-4
        StatementTreeViewInfo rootTree = statementService.getStatementTreeView("STMT-TV-1");
        List<StatementTreeViewInfo> subTreeView = rootTree.getStatements();
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);

        // Check root tree
        assertNotNull(rootTree);
        assertEquals(subTreeView.size(), 2);
        assertNotNull(subTree1);
        assertNotNull(subTree2);

        // Check reqComps of sub-tree 1
        assertEquals(subTree1.getId(), "STMT-TV-2");
        assertEquals(subTree1.getReqComponents().size(), 2);
        assertEquals(subTree1.getReqComponents().get(0).getId(), "REQCOMP-TV-1");
        assertEquals(subTree1.getReqComponents().get(1).getId(), "REQCOMP-TV-2");

        // Check reqComps of sub-tree 2
        assertEquals(subTree2.getId(), "STMT-TV-3");
        assertEquals(subTree2.getReqComponents().size(), 2);
        assertEquals(subTree2.getReqComponents().get(0).getId(), "REQCOMP-TV-3");
        assertEquals(subTree2.getReqComponents().get(1).getId(), "REQCOMP-TV-4");
    }

    @Test
    @Ignore
    /**
     * @deprecated Being moved to ProgramRequirementService
     */
    public void testGetStatementTreeViewForNlUsageType() throws Exception {
        // Tree structure should be:
        //                          STMT-TV-1:OR
        //          STMT-TV-2:AND                   STMT-TV-3:AND
        //     REQCOMP-TV-1  REQCOMP-TV-2      REQCOMP-TV-3  REQCOMP-TV-4
    	//
    	// Statement translation:
    	// (Student must have completed all of MATH 152, MATH 180 AND Student needs a minimum GPA of 3.5 in MATH 152, MATH 180)
    	// OR
    	// (Student must have completed 1 of MATH 152, MATH 180 AND Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)

        StatementTreeViewInfo rootTree = statementService.getStatementTreeViewForNlUsageType("STMT-TV-1", "KUALI.RULE", "en");

        List<StatementTreeViewInfo> subTreeView = rootTree.getStatements();
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);

        // root statement
        assertNotNull(rootTree);
        assertEquals(subTreeView.size(), 2);
        assertNotNull(subTree1);
        assertNotNull(subTree2);

        // check reqComps of sub-tree 1
        assertEquals(subTree1.getId(), "STMT-TV-2");
        assertEquals(subTree1.getReqComponents().size(), 2);
        assertEquals(subTree1.getReqComponents().get(0).getId(), "REQCOMP-TV-1");
        assertEquals(subTree1.getReqComponents().get(1).getId(), "REQCOMP-TV-2");
        assertEquals("Student must have completed all of MATH 152, MATH 180", subTree1.getReqComponents().get(0).getNaturalLanguageTranslation());
        assertEquals("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180", subTree1.getReqComponents().get(1).getNaturalLanguageTranslation());

        // check reqComps of sub-tree 2
        assertEquals(subTree2.getId(), "STMT-TV-3");
        assertEquals(subTree2.getReqComponents().size(), 2);
        assertEquals(subTree2.getReqComponents().get(0).getId(), "REQCOMP-TV-3");
        assertEquals(subTree2.getReqComponents().get(1).getId(), "REQCOMP-TV-4");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", subTree2.getReqComponents().get(0).getNaturalLanguageTranslation());
        assertEquals("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180", subTree2.getReqComponents().get(1).getNaturalLanguageTranslation());
    }

    @Test
    public void testUpdateStatementTreeViewFromEmpty() throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, AlreadyExistsException {
        //     After tree is updated
        //                          STMT-TV-1:OR
        //          STMT TV 2:AND                   STMT TV 3:AND
        //     REQCOMP TV 1  REQCOMP TV 2      REQCOMP TV 3  REQCOMP TV 4

        List<StatementTreeViewInfo> subStatements = new ArrayList<StatementTreeViewInfo>(3);
        List<ReqComponentInfo> stv1ReqComps = new ArrayList<ReqComponentInfo>(3);
        List<ReqComponentInfo> stv2ReqComps = new ArrayList<ReqComponentInfo>(3);

        // req components
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDesc(toRichText("REQCOMP-TV-TEST-1"));
        rc1.setType("kuali.reqComponent.type.gradecheck");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDesc(toRichText("REQCOMP-TV-TEST-2"));
        rc2.setType("kuali.reqComponent.type.gradecheck");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDesc(toRichText("REQCOMP-TV-TEST-3"));
        rc3.setType("kuali.reqComponent.type.gradecheck");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDesc(toRichText("REQCOMP-TV-TEST-4"));
        rc4.setType("kuali.reqComponent.type.gradecheck");

        // statement tree views
        StatementTreeViewInfo treeView = new StatementTreeViewInfo();
        treeView.setDesc(toRichText("STMT-TV-TEST-1"));
        treeView.setOperator(StatementOperatorTypeKey.OR);
        treeView.setType("kuali.statement.type.course.academicReadiness.prereq");

        StatementTreeViewInfo subTreeView1 = new StatementTreeViewInfo();
        subTreeView1.setDesc(toRichText("STMT-TV-TEST-2"));
        subTreeView1.setOperator(StatementOperatorTypeKey.AND);
        subTreeView1.setType("kuali.statement.type.course.academicReadiness.prereq");

        StatementTreeViewInfo subTreeView2 = new StatementTreeViewInfo();
        subTreeView2.setDesc(toRichText("STMT-TV-TEST-3"));
        subTreeView2.setOperator(StatementOperatorTypeKey.AND);
        subTreeView2.setType("kuali.statement.type.course.academicReadiness.prereq");

        // construct tree with statements and req components
        stv1ReqComps.add(rc1);
        stv1ReqComps.add(rc2);
        subTreeView1.setReqComponents(stv1ReqComps);
        stv2ReqComps.add(rc3);
        stv2ReqComps.add(rc4);
        subTreeView2.setReqComponents(stv2ReqComps);
        subStatements.add(subTreeView1);
        subStatements.add(subTreeView2);
        treeView.setStatements(subStatements);

        StatementTreeViewInfo returnedTreeView = statementService.createStatementTreeView(treeView);
        testStatementTreeView(returnedTreeView);

        StatementTreeViewInfo retrievedUpdatedTreeView = statementService.getStatementTreeView(returnedTreeView.getId());
        testStatementTreeView(retrievedUpdatedTreeView);
        
        StatusInfo status = statementService.deleteStatementTreeView(retrievedUpdatedTreeView.getId());
        try{
        	statementService.getStatementTreeView(returnedTreeView.getId());
        	assertTrue(false);
        }catch(DoesNotExistException e){
        	assertTrue(true);
        }
        try{
        	statementService.getStatementTreeView(returnedTreeView.getStatements().get(0).getId());
        	assertTrue(false);
        }catch(DoesNotExistException e){
        	assertTrue(true);
        }
        try{
        	statementService.getStatementTreeView(returnedTreeView.getStatements().get(1).getId());
        	assertTrue(false);
        }catch(DoesNotExistException e){
        	assertTrue(true);
        }
    }

    private void testStatementTreeView(StatementTreeViewInfo treeView) {
        List<StatementTreeViewInfo> subTrees = treeView.getStatements();
        StatementTreeViewInfo subTree1 = (subTrees == null)? null : subTrees.get(0);
        StatementTreeViewInfo subTree2 = (subTrees == null)? null : subTrees.get(1);
        assertNotNull(treeView.getId());
        assertEquals("STMT-TV-TEST-1", treeView.getDesc().getPlain());
        int numReturnedSubTrees = (subTrees == null)? 0 : subTrees.size();
        assertEquals(2, numReturnedSubTrees);
        assertEquals("STMT-TV-TEST-2", subTree1.getDesc().getPlain());
        assertEquals("STMT-TV-TEST-3", subTree2.getDesc().getPlain());
        int numReturnedSubTree2Statements = (subTree2.getStatements() == null)? 0 :
            subTree2.getStatements().size();
        assertEquals(0, numReturnedSubTree2Statements);
        assertEquals(2, subTree1.getReqComponents().size());
        assertEquals(2, subTree2.getReqComponents().size());
        assertEquals("REQCOMP-TV-TEST-1", subTree1.getReqComponents().get(0).getDesc().getPlain());
        assertEquals("REQCOMP-TV-TEST-2", subTree1.getReqComponents().get(1).getDesc().getPlain());
        assertEquals("REQCOMP-TV-TEST-3", subTree2.getReqComponents().get(0).getDesc().getPlain());
        assertEquals("REQCOMP-TV-TEST-4", subTree2.getReqComponents().get(1).getDesc().getPlain());
    }

    private void updateStatementTreeViewInfo(StatementTreeViewInfo treeView, String editText) {
        List<StatementTreeViewInfo> subTreeView = treeView.getStatements();
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);
        StatementTreeViewInfo newSubTree2 = new StatementTreeViewInfo();
        List<ReqComponentInfo> reqComponents = new ArrayList<ReqComponentInfo>(7);
        ReqComponentInfo reqComp_tv_test1 = new ReqComponentInfo();
        ReqComponentInfo reqComp_tv_test2 = new ReqComponentInfo();
    	reqComponents.addAll(subTree2.getReqComponents());
        reqComp_tv_test1.setDesc(toRichText("REQCOMP TV TEST1"));
        reqComp_tv_test1.setType("kuali.reqComponent.type.gradecheck");
        reqComp_tv_test2.setDesc(toRichText("REQCOMP TV TEST2"));
        reqComp_tv_test2.setType("kuali.reqComponent.type.gradecheck");
        reqComponents.add(reqComp_tv_test1);
        reqComponents.add(reqComp_tv_test2);
        newSubTree2.setDesc(toRichText("STMT TV TEST"));
        newSubTree2.setReqComponents(reqComponents);
        newSubTree2.setOperator(StatementOperatorTypeKey.AND);
        newSubTree2.setType("kuali.statement.type.course.academicReadiness.prereq");
    	subTreeView.remove(subTree2);
        subTreeView.add(newSubTree2);
    	subTree1.setDesc(toRichText(subTree1.getDesc().getPlain() + editText)); //" is edited"));
    }

    @Test
    @Ignore
    /**
     * @deprecated Being moved to ProgramRequirementService
     */
    public void testUpdateStatementTreeView() throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // Before tree is updated
        //                          STMT-TV-1:OR
        //          STMT TV 2:AND                   STMT TV 3:AND
        //     REQCOMP TV 1  REQCOMP TV 2      REQCOMP TV 3  REQCOMP TV 4
    	//
        // After tree is updated
        //                       *------------------------ STMT TV 1:OR ---------------------*
        //                       |                                                           |
        //         *-- STMT TV 2 is edited:AND --*                     *-----------*--- STMT TV TEST:AND ---*--------------*
        //         |                             |                     |           |                        |              |
        //     REQCOMP TV 1               REQCOMP TV 2            REQCOMP TV 3  REQCOMP TV 4      REQCOMP TV TEST1     REQCOMP TV TEST2

    	StatementTreeViewInfo treeView = statementService.getStatementTreeView("STMT-TV-1");
    	updateStatementTreeViewInfo(treeView, " is edited");

        StatementTreeViewInfo returnedTreeView = statementService.updateStatementTreeView(treeView.getId(), treeView);
        List<StatementTreeViewInfo> returnedSubTrees = returnedTreeView.getStatements();
        StatementTreeViewInfo returnedSubTree1 = returnedSubTrees.get(0);
        StatementTreeViewInfo returnedSubTree2 = returnedSubTrees.get(1);

        // Assert updateStatementTreeView
        assertNotNull(returnedSubTree1);
        assertNotNull(returnedSubTree2);
        assertEquals("STMT-TV-1", returnedTreeView.getId());
        assertNotNull(returnedSubTrees);
        assertEquals(2, returnedSubTrees.size());
        assertEquals("Statement Tree View 2 is edited", returnedSubTree1.getDesc().getPlain());
        assertEquals("STMT TV TEST", returnedSubTree2.getDesc().getPlain());
        assertNull(returnedSubTree2.getStatements());
        assertEquals(4, returnedSubTree2.getReqComponents().size());
        assertEquals("RC Tree View 3", returnedSubTree2.getReqComponents().get(0).getDesc().getPlain());
        assertEquals("RC Tree View 4", returnedSubTree2.getReqComponents().get(1).getDesc().getPlain());
        assertEquals("REQCOMP TV TEST1", returnedSubTree2.getReqComponents().get(2).getDesc().getPlain());
        assertEquals("REQCOMP TV TEST2", returnedSubTree2.getReqComponents().get(3).getDesc().getPlain());
    }

    @Test
    @Ignore
    /**
     * @deprecated Being moved to ProgramRequirementService
     */
    public void testUpdateAndGetStatementTreeView() throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // Before tree is updated
        //                          STMT-TV-1:OR
        //          STMT TV 2:AND                   STMT TV 3:AND
        //     REQCOMP TV 1  REQCOMP TV 2      REQCOMP TV 3  REQCOMP TV 4
    	//
        // After tree is updated
        //                       *------------------------ STMT TV 1:OR ---------------------*
        //                       |                                                           |
        //         *-- STMT TV 2 is edited:AND --*                     *-----------*--- STMT TV TEST:AND ---*--------------*
        //         |                             |                     |           |                        |              |
        //     REQCOMP TV 1               REQCOMP TV 2            REQCOMP TV 3  REQCOMP TV 4      REQCOMP TV TEST1     REQCOMP TV TEST2

    	StatementTreeViewInfo treeView = statementService.getStatementTreeView("STMT-TV-1");
    	updateStatementTreeViewInfo(treeView, " again");

        StatementTreeViewInfo updatedTreeView = statementService.updateStatementTreeView(treeView.getId(), treeView);
        List<StatementTreeViewInfo> updatedSubTrees = updatedTreeView.getStatements();
        StatementTreeViewInfo updatedSubTree1 = updatedSubTrees.get(0);
        StatementTreeViewInfo updatedSubTree2 = updatedSubTrees.get(1);

        StatementTreeViewInfo getUpdatedTreeView = statementService.getStatementTreeView("STMT-TV-1");
        List<StatementTreeViewInfo> getUpdatedSubTrees = getUpdatedTreeView.getStatements();
        StatementTreeViewInfo getUpdatedSubTree1 = getUpdatedSubTrees.get(0);
        StatementTreeViewInfo getUpdatedSubTree2 = getUpdatedSubTrees.get(1);

        // Assert that updatedTreeView is equal to getUpdatedTreeView
        assertEquals(updatedTreeView.getId(), getUpdatedTreeView.getId());
        // Assert sub-trees
        assertEquals(updatedSubTrees.size(), getUpdatedSubTrees.size());
        assertEquals(updatedSubTree1.getDesc().getPlain(), getUpdatedSubTree1.getDesc().getPlain());
        assertEquals(updatedSubTree2.getDesc().getPlain(), getUpdatedSubTree2.getDesc().getPlain());

        // updatedSubTree1 should not have any statements
        assertNull(getUpdatedSubTree1.getStatements());
        assertEquals(updatedSubTree1.getStatements(), getUpdatedSubTree1.getStatements());

        // updatedSubTree2 should only have req components and not any statements
        assertNull(updatedSubTree2.getStatements());
        assertEquals(updatedSubTree2.getStatements(), getUpdatedSubTree2.getStatements());
        assertEquals(updatedSubTree2.getReqComponents().size(), getUpdatedSubTree2.getReqComponents().size());
        assertEquals(updatedSubTree2.getReqComponents().get(0).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(0).getDesc().getPlain());
        assertEquals(updatedSubTree2.getReqComponents().get(1).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(1).getDesc().getPlain());
        assertEquals(updatedSubTree2.getReqComponents().get(2).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(2).getDesc().getPlain());
        assertEquals(updatedSubTree2.getReqComponents().get(3).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(3).getDesc().getPlain());
        assertEquals(updatedSubTree2.getReqComponents().get(4).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(4).getDesc().getPlain());
        assertEquals(updatedSubTree2.getReqComponents().get(5).getDesc().getPlain(), getUpdatedSubTree2.getReqComponents().get(5).getDesc().getPlain());
    }

    private RichTextInfo toRichText(String text) {
        RichTextInfo richTextInfo = new RichTextInfo();
        if (text == null) return null;
        richTextInfo.setPlain(text);
        richTextInfo.setFormatted("<p>" + text + "</p>");
        return richTextInfo;
    }

    @Test
    public void testGetRefStatementRelationType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	RefStatementRelationTypeInfo type = this.statementService.getRefStatementRelationType("clu.prerequisites");

        GregorianCalendar effDate = new GregorianCalendar(2000, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2100, 11, 31, 0, 0, 0);

        assertNotNull(type);
    	assertEquals("clu.prerequisites", type.getId());
    	assertEquals(0, type.getAttributes().size());
    	assertEquals("CLU Prerequisites", type.getDesc());
    	assertEquals(effDate.getTime(), type.getEffectiveDate());
    	assertEquals(expDate.getTime(), type.getExpirationDate());
    	assertEquals("CLU Prereq", type.getName());
    }

    @Test
    public void testGetRefStatementRelationTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<RefStatementRelationTypeInfo> types = this.statementService.getRefStatementRelationTypes();

        assertNotNull(types);
    	assertEquals(2, types.size());
    	assertTrue(containsId(types, "clu.prerequisites"));
    	assertTrue(containsId(types, "clu.corequisites"));
    }

    @Test
    public void testGetRefStatementRelationsByStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<RefStatementRelationInfo> list = this.statementService.getRefStatementRelationsByStatement("STMT-1");

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("ref-stmt-rel-1", list.get(0).getId());
    }

    @Test(expected=MissingParameterException.class)
    public void testGetRefStatementRelationsByStatement_NullStatementId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		this.statementService.getRefStatementRelationsByStatement(null);
		fail("statementService.getRefStatementRelationsByStatement should have thrown a MissingParameterException");
    }

    @Test(expected=InvalidParameterException.class)
    public void testGetRefStatementRelationsByStatement_EmptyStatementId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		this.statementService.getRefStatementRelationsByStatement("");
		fail("statementService.getRefStatementRelationsByStatement should have thrown an InvalidParameterException");
    }

    @Test
    public void testUpdateRefStatementRelation() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        GregorianCalendar effDate = new GregorianCalendar(2100, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2200, 11, 31, 0, 0, 0);

    	RefStatementRelationInfo refInfo = createRefStatementRelation();

    	refInfo.setEffectiveDate(effDate.getTime());
    	refInfo.setExpirationDate(expDate.getTime());
    	refInfo.setRefObjectId("123");
    	refInfo.setRefObjectTypeKey("clu");
    	refInfo.setState("SUSPENDED");
    	refInfo.setStatementId("STMT-101");
    	refInfo.setType("clu.corequisites");

    	RefStatementRelationInfo updatedRefInfo = this.statementService.updateRefStatementRelation(refInfo.getId(), refInfo);

    	assertNotNull(updatedRefInfo);
    	assertEquals(refInfo.getEffectiveDate(), updatedRefInfo.getEffectiveDate());
    	assertEquals(refInfo.getExpirationDate(), updatedRefInfo.getExpirationDate());
    	assertEquals(refInfo.getRefObjectId(), updatedRefInfo.getRefObjectId());
    	assertEquals(refInfo.getRefObjectTypeKey(), updatedRefInfo.getRefObjectTypeKey());
    	assertEquals(refInfo.getState(), updatedRefInfo.getState());
    	assertEquals(refInfo.getStatementId(), updatedRefInfo.getStatementId());
    	assertEquals(refInfo.getType(), updatedRefInfo.getType());
    }

    @Test(expected=DoesNotExistException.class)
    public void testUpdateRefStatementRelation_InvalidRefObjectTypeKey() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        GregorianCalendar effDate = new GregorianCalendar(2100, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2200, 11, 31, 0, 0, 0);

    	RefStatementRelationInfo refInfo = createRefStatementRelation();

    	refInfo.setEffectiveDate(effDate.getTime());
    	refInfo.setExpirationDate(expDate.getTime());
    	refInfo.setRefObjectId("123");
    	refInfo.setRefObjectTypeKey("x.invalid.clu.key.x");
    	refInfo.setState("SUSPENDED");
    	refInfo.setStatementId("STMT-101");
    	refInfo.setType("clu.corequisites");

		this.statementService.updateRefStatementRelation(refInfo.getId(), refInfo);
		fail("statementService.updateRefStatementRelation should have thrown a DoesNotExistException");
    }

    @Test(expected=DoesNotExistException.class)
    public void testUpdateRefStatementRelation_InvalidRefStatementRelationType() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        GregorianCalendar effDate = new GregorianCalendar(2100, 00, 01, 0, 0, 0);
        GregorianCalendar expDate = new GregorianCalendar(2200, 11, 31, 0, 0, 0);

    	RefStatementRelationInfo refInfo = createRefStatementRelation();

    	refInfo.setEffectiveDate(effDate.getTime());
    	refInfo.setExpirationDate(expDate.getTime());
    	refInfo.setRefObjectId("123");
    	refInfo.setRefObjectTypeKey("clu");
    	refInfo.setState("SUSPENDED");
    	refInfo.setStatementId("STMT-101");
    	refInfo.setType("x.invalid.type.x");

		this.statementService.updateRefStatementRelation(refInfo.getId(), refInfo);
		fail("statementService.updateRefStatementRelation should have thrown a DoesNotExistException");
    }

	private ReqComponentInfo createBadReqComponent1() {
		ReqComponentInfo reqCompInfo = new ReqComponentInfo();
//		reqCompInfo.setId("REQCOMP-NL-X");
		reqCompInfo.setId("1234567890123456789012345678901234567890");
		reqCompInfo.setType("kuali.reqComponent.type.courseList.nof");
		reqCompInfo.setState("active");

		List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();

		ReqCompFieldInfo field1 = new ReqCompFieldInfo();
		field1.setId("1234567890123456789012345678901234567890");
		field1.setType(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getType());
		field1.setValue("-1");
		fieldList.add(field1);

		ReqCompFieldInfo field2 = new ReqCompFieldInfo();
		field2.setId("2");
		field2.setType(ReqComponentFieldTypes.OPERATOR_KEY.getType());
		field2.setValue("greater_than_or_equal_to42");
		fieldList.add(field2);

		ReqCompFieldInfo field3 = new ReqCompFieldInfo();
		field3.setId("3");
		field3.setType(ReqComponentFieldTypes.CLUSET_KEY.getType());
		field3.setValue("CLUSET-NL-Y");
		fieldList.add(field3);

		reqCompInfo.setReqCompFields(fieldList);
		return reqCompInfo;
	}

    @Test
    // TODO: This should test valid ReqCompFieldInfo types and values, too
    public void testValidateReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	ReqComponentInfo reqInfo = createBadReqComponent1();
    	List<ValidationResultInfo> resultInfo = statementService.validateReqComponent("SYSTEM", reqInfo);

	   	assertNotNull(resultInfo);
    	assertEquals(4, resultInfo.size());

    	reqInfo = new ReqComponentInfo();
    	resultInfo = statementService.validateReqComponent("SYSTEM", reqInfo);
    	assertNull(resultInfo);

    	reqInfo = createReqComponent1();
    	resultInfo = statementService.validateReqComponent("SYSTEM", reqInfo);
    	if (resultInfo != null){
    	assertNull(resultInfo);
    	System.out.println(resultInfo.size() + " errors");
    	
    	for (ValidationResultInfo vri : resultInfo) {
			System.out.println(vri.getErrorLevel() + " " + vri.getElement()
					+ " " + vri.getMessage());
		}
		assertEquals(0, resultInfo.size());
    	}
    	
		
		
	}

    @Test
    public void testValidateRefStatementRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		RefStatementRelationInfo refRelation = new RefStatementRelationInfo();
		refRelation.setState("ACTIVE");
		refRelation.setType("cluStatementRelationType.academicreadiness");
		List<ValidationResultInfo> resultInfo = statementService.validateRefStatementRelation("SYSTEM", refRelation);
		if (resultInfo != null){
		assertNull(resultInfo);
		assertEquals(0, resultInfo.size());
		}
    }

	@Test
	public void testSearchForCluInReqComponentTypes() throws Exception {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("stmt.search.cluInReqComponent");

		List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam searchParam1 = new SearchParam();
        searchParam1.setKey("stmt.queryParam.reqComponentFieldType");
        searchParam1.setValue("kuali.reqComponent.field.type.clu.id");
        queryParamValues.add(searchParam1);

        SearchParam searchParam2 = new SearchParam();
        searchParam2.setKey("stmt.queryParam.reqComponentFieldValue");
        searchParam2.setValue("CLU-NL-3");
        queryParamValues.add(searchParam2);
        
		searchRequest.setParams(queryParamValues);
		SearchResult result = statementService.search(searchRequest);
		List<SearchResultCell> resultRow1Columns = result.getRows().get(0).getCells();
		List<SearchResultCell> resultRow2Columns = result.getRows().get(1).getCells();

		assertEquals(2, result.getRows().size());
		assertEquals("REQCOMP-NL-3", resultRow1Columns.get(0).getValue());
		assertEquals("REQCOMP-NL-5", resultRow2Columns.get(0).getValue());
		assertEquals("kuali.reqComponent.type.courseList.1of1", resultRow2Columns.get(1).getValue());
		assertEquals("One required course", resultRow2Columns.get(2).getValue());
		assertEquals("Student must have completed <course>", resultRow2Columns.get(3).getValue());
		assertEquals("kuali.statement.type.course.academicReadiness.prereq", resultRow2Columns.get(4).getValue());
		assertEquals("Academic Readiness Pre Reqs", resultRow2Columns.get(5).getValue());
		assertEquals("Pre req rules used in the evaluation of a person's academic readiness for enrollment in a LU.", resultRow2Columns.get(6).getValue());
	}
}
