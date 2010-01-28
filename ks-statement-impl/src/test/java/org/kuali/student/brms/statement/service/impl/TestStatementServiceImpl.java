package org.kuali.student.brms.statement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.brms.statement.config.CluInfo;
import org.kuali.student.brms.statement.config.CluSetInfo;
import org.kuali.student.brms.statement.config.contexts.CourseListContextImpl;
import org.kuali.student.brms.statement.dto.ReqCompFieldInfo;
import org.kuali.student.brms.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.brms.statement.service.StatementService;

@Daos({@Dao(value = "org.kuali.student.brms.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")})
@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementServiceImpl extends AbstractServiceTest {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Client(value = "org.kuali.student.brms.statement.service.impl.StatementServiceImpl", additionalContextFile="classpath:statement-additional-context.xml")
    public StatementService statementService;
    
	@BeforeClass
	public static void beforeClass() {
		// Add test data

		// Add CLUs
		List<CluInfo> cluList = new ArrayList<CluInfo>();
		CluInfo clu1 = new CluInfo("1", "MATH152", "MATH 152", "MATH 152 Linear Systems");
		cluList.add(clu1);
		CluInfo clu2 = new CluInfo("2", "MATH180", "MATH 180", "MATH 180 Differential Calculus with Physical Applications");
		cluList.add(clu2);
		CourseListContextImpl.setCluInfo(cluList);
		
		// Add CLU Sets
		List<CluSetInfo> cluSetList = new ArrayList<CluSetInfo>();
		CluSetInfo cluSet1 = new CluSetInfo("CLUSET-NL-1", cluList);
		cluSetList.add(cluSet1);
		CourseListContextImpl.setCluSetInfo(cluSetList);
	}
	
	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
    	assertEquals("Student must have completed 1 of MATH 152, MATH 180", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponent_EnglishGerman() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", null);
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
		
        naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "de");
        assertEquals("Student muss abgeschlossen 1 von MATH 152, MATH 180", naturalLanguage);

		naturalLanguage = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG", "en");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

    @Test
    public void testGetLuStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        StatementInfo stmt = statementService.getStatement("STMT-2");

        assertNotNull(stmt);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
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
    
    private StatementInfo getStatement(List<StatementInfo> stmtList, String id) {
        for(StatementInfo stmt : stmtList) {
            if(id.equals(stmt.getId())) {
                return stmt;
            }
        }
        return null;
    }

    // getStatements (note: the pural from) is no longer supported in
    // StatementService service specification
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
    
    // getReqComponents (note: the pural from) is no longer supported in
    // StatementService service specification
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
    public void testGetLuStatements_DetailInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<String> luStatementIdList = Arrays.asList(new String[] {"STMT-2"});
        List<StatementInfo> stmtList = getStatements(luStatementIdList);

        assertNotNull(stmtList);
        assertEquals(1, stmtList.size());
        
        StatementInfo stmt = stmtList.get(0);
        
        assertEquals("STMT-2", stmt.getId());
        assertEquals("kuali.luStatementType.prereqAcademicReadiness", stmt.getType());
        assertEquals(StatementOperatorTypeKey.AND, stmt.getOperator());
        assertEquals("ACTIVE", stmt.getState());
        assertEquals("STMT 2", stmt.getName());
        assertEquals("Statement 2", stmt.getDesc().getPlain());

        // cluIds are no long stored in statementInfo
//        assertTrue(stmt.getCluIds().contains("CLU-2"));
        
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
    public void testGetLuStatementsByType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<StatementInfo> stmtList = statementService.getStatementsByType("kuali.luStatementType.prereqAcademicReadiness");

        assertNotNull(stmtList);
        assertEquals(10, stmtList.size());

        StatementInfo stmt = stmtList.get(0);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
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

    @Test
    public void testGetInvldStmt() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        try {
            statementService.getStatement("invalid.stmt");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testGetStmtByInvldType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<StatementInfo> stmtList = statementService.getStatementsByType("invalid.stmttype");

        assertNull(stmtList);
    }

    @Test
    public void testStmtStmtRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        StatementInfo stmt = statementService.getStatement("STMT-1");

        assertNotNull(stmt);

        assertEquals(stmt.getId(), "STMT-1");

        List<String> stmtIds = stmt.getLuStatementIds();

        assertEquals(1, stmtIds.size());
        assertTrue(stmtIds.contains("STMT-2"));
    }

    @Test
    public void testGetReqComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = statementService.getReqComponentTypes();

        assertNotNull(reqCompTypeInfoList);
        assertEquals(8, reqCompTypeInfoList.size());

        ReqComponentTypeInfo rqt = null;

        if ("kuali.reqCompType.courseList.all".equals(reqCompTypeInfoList.get(0).getId())) {
            rqt = reqCompTypeInfoList.get(0);
        } else if ("kuali.reqCompType.courseList.all".equals(reqCompTypeInfoList.get(1).getId())) {
            rqt = reqCompTypeInfoList.get(1);
        } else if ("kuali.reqCompType.courseList.all".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(2);
        }else if ("kuali.reqCompType.courseList.all".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(3);
        }else if ("kuali.reqCompType.courseList.all".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(4);
        } else {
            assertTrue(false);
        }

        assertEquals(rqt.getName(), "All of required courses");
    }

    @Test
    public void testGetReqComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
       ReqComponentTypeInfo rqt = statementService.getReqComponentType("kuali.reqCompType.courseList.all");
        
        assertNotNull(rqt);
        assertEquals(rqt.getId(), "kuali.reqCompType.courseList.all");
        assertEquals(rqt.getDescr(), "Student must have completed all of <reqCompFieldType.cluSet>");
        assertEquals(rqt.getName(), "All of required courses");
        assertEquals(rqt.getEffectiveDate(), df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(), df.parse("20001231"));                
    }

    @Test
    public void testGetReqComponentFieldType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {        
        ReqComponentTypeInfo rqt = statementService.getReqComponentType("kuali.reqCompType.courseList.all");
        
        List<ReqCompFieldTypeInfo> reqftList = rqt.getReqCompFieldTypeInfos();
        assertNotNull(reqftList);
        assertEquals(1, reqftList.size());
        
        ReqCompFieldTypeInfo ftInfo = reqftList.get(0);
        
        assertEquals(ftInfo.getId(), "reqCompFieldType.cluSet");
        
        FieldDescriptor fd = ftInfo.getFieldDescriptor();
        
        assertEquals(fd.getName(),"CLUSET");
        assertEquals(fd.getDesc(),"CLUSET");
        assertEquals(fd.getDataType(),"string");        
    }

    @Test
    public void testGetReqCompTypeForLuStmtType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = statementService.getReqComponentTypesForStatementType("kuali.luStatementType.prereqAcademicReadiness");

        assertNotNull(reqCompTypeInfoList);
        assertEquals(5, reqCompTypeInfoList.size());

        ReqComponentTypeInfo rqt = null;

        if ("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(0).getId())) {
            rqt = reqCompTypeInfoList.get(0);
        } else if ("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(1).getId())) {
            rqt = reqCompTypeInfoList.get(1);
        } else if ("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(2);
        } else if ("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(3).getId())) {
            rqt = reqCompTypeInfoList.get(3);
        } else if ("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(4).getId())) {
            rqt = reqCompTypeInfoList.get(4);
        } else {
            assertTrue(false);
        }

        assertEquals(rqt.getDescr(), "Student needs a minimum GPA of <reqCompFieldType.gpa>");
        assertEquals(rqt.getName(), "Minimum overall GPA");
        assertEquals(rqt.getEffectiveDate(), df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(), df.parse("20011130"));
    }

    @Test
    public void testGetReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentInfo reqComp = statementService.getReqComponent("REQCOMP-1");

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-1");
        assertEquals(reqComp.getType(), "kuali.reqCompType.courseList.all");
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

        assertEquals("kuali.reqCompType.courseList.all",reqComp.getType());
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
        List<ReqComponentInfo> reqCompList = statementService.getReqComponentsByType("kuali.reqCompType.grdCondCourseList");

        assertNotNull(reqCompList);
        assertEquals(1, reqCompList.size());

        ReqComponentInfo reqComp = reqCompList.get(0);

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-2");
        assertEquals(reqComp.getType(), "kuali.reqCompType.grdCondCourseList");
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
    public void testGetReqCompByInvldType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<ReqComponentInfo> reqCompList = statementService.getReqComponentsByType("invalid.reqcomptype");

        assertNull(reqCompList);
    }

    @Test
    public void testInvldGetReqComponentType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        try {
            statementService.getReqComponentType("invalid.reqcomp");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testCreateLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {

        StatementInfo stmt = new StatementInfo();
        RichTextInfo statement3 = new RichTextInfo();
        statement3.setPlain("Statement 3");
        statement3.setFormatted("<p>Statement 3</p>");
        stmt.setDesc(statement3);
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");
        stmt.setType("kuali.luStatementType.coreqAcademicReadiness");

        MetaInfo mf = new MetaInfo();

        mf.setCreateId("CREATEID");
        mf.setUpdateId("UPDATEID");

        stmt.setMetaInfo(mf);

        StatementInfo createdSmt = statementService.createStatement("kuali.luStatementType.coreqAcademicReadiness", stmt);

        assertNotNull(createdSmt.getId());
        assertEquals(createdSmt.getType(), "kuali.luStatementType.coreqAcademicReadiness");
        assertEquals(createdSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(createdSmt.getState(), "ACTIVE");
        assertEquals(createdSmt.getName(), "STMT 3");
        assertEquals(createdSmt.getDesc().getPlain(), "Statement 3");
    }

    @Test
    public void testUpdateLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        StatementInfo stmt = statementService.getStatement("STMT-1");
        RichTextInfo statement3 = new RichTextInfo();
        statement3.setPlain("Statement 3");
        statement3.setFormatted("<p>Statement 3</p>");
        stmt.setDesc(statement3);
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");

        MetaInfo mfOrg = stmt.getMetaInfo();

        MetaInfo mf = new MetaInfo();

        stmt.setMetaInfo(mf);

        StatementInfo updSmt = null;
        try {
            updSmt = statementService.updateStatement(stmt.getId(), stmt);
            fail("Should throw version mismatch exception");
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }

        stmt.setMetaInfo(mfOrg);

        try {
            updSmt = statementService.updateStatement(stmt.getId(), stmt);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }

        assertNotNull(updSmt.getId());
        assertEquals(updSmt.getType(), "kuali.luStatementType.createCourseAcademicReadiness");
        assertEquals(updSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(updSmt.getState(), "ACTIVE");
        assertEquals(updSmt.getName(), "STMT 3");
        assertEquals(updSmt.getDesc().getPlain(), "Statement 3");
    }

    @Test
    public void testDeleteLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatusInfo si;
        StatementInfo stmt = statementService.getStatement("STMT-2");
        try {
            si = statementService.deleteStatement(stmt.getId());
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("LuService.deleteLuStatement() failed deleting pre existing statement");
        }
    }

    @Test
    public void testCreateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {

        ReqComponentInfo req = new ReqComponentInfo();
        
        RichTextInfo reqComponent5 = new RichTextInfo();
        reqComponent5.setPlain("Required Component 5");
        reqComponent5.setFormatted("<p>Required Component 5</p>");
        req.setDesc(reqComponent5);
        req.setEffectiveDate(df.parse("20010101"));
        req.setExpirationDate(df.parse("20020101"));
        req.setState("ACTIVE");
        req.setType("kuali.reqCompType.courseList.all");

        MetaInfo mf = new MetaInfo();

        req.setMetaInfo(mf);

        ReqComponentInfo createdReq = statementService.createReqComponent("kuali.reqCompType.courseList.all", req);

        assertNotNull(createdReq.getId());
        assertEquals(createdReq.getType(), "kuali.reqCompType.courseList.all");
        assertEquals(createdReq.getEffectiveDate(), df.parse("20010101"));
        assertEquals(createdReq.getExpirationDate(), df.parse("20020101"));
        assertEquals(createdReq.getState(), "ACTIVE");
        assertEquals(createdReq.getDesc().getPlain(), "Required Component 5");
    }

    @Test
    public void testUpdateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        RichTextInfo reqComponent3 = new RichTextInfo();
        reqComponent3.setPlain("Req Comp 3");
        reqComponent3.setFormatted("<p>Req Comp 3</p>");
        req.setDesc(reqComponent3);
        req.setState("IN_PROGRESS");

        MetaInfo mfOrg = req.getMetaInfo();

        MetaInfo mf = new MetaInfo();

        req.setMetaInfo(mf);

        ReqComponentInfo updReq = null;
        try {
            updReq = statementService.updateReqComponent(req.getId(), req);
            fail("Should throw version mismatch exception");
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }

        req.setMetaInfo(mfOrg);

        try {
            updReq = statementService.updateReqComponent(req.getId(), req);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }

        assertNotNull(updReq.getId());
        assertEquals(updReq.getType(), "kuali.reqCompType.courseList.all");
        assertEquals(updReq.getState(), "IN_PROGRESS");
        assertEquals(updReq.getDesc().getPlain(), "Req Comp 3");
        assertEquals(updReq.getReqCompFields().size(), 0);
    }

    @Test
    public void testUpdateReqComponentField() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        
        assertEquals(req.getReqCompFields().size(), 0);
        
        ReqCompFieldInfo rcfInfo = new ReqCompFieldInfo();
        rcfInfo.setId("reqCompFieldType.clu");
        rcfInfo.setValue("MATH 101");
        
        List<ReqCompFieldInfo> reqCompField = new ArrayList<ReqCompFieldInfo>();
        reqCompField.add(rcfInfo);
        req.setReqCompFields(reqCompField);

        
        MetaInfo mfOrg = req.getMetaInfo();

        ReqComponentInfo updReq = null;

        req.setMetaInfo(mfOrg);

        try {
            updReq = statementService.updateReqComponent(req.getId(), req);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }
        
        assertEquals(updReq.getReqCompFields().size(), 1);
        ReqCompFieldInfo newrcfInfo = updReq.getReqCompFields().get(0);
                
        assertNotNull(updReq.getId());
        assertNotNull(newrcfInfo);
        assertEquals("MATH 101", newrcfInfo.getValue());
        assertEquals("reqCompFieldType.clu", newrcfInfo.getId());       
    }
    
    @Test
    public void testDeleteReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatusInfo si;
        ReqComponentInfo req = statementService.getReqComponent("REQCOMP-1");
        try {
            si = statementService.deleteReqComponent(req.getId());
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("LuService.deleteReqComponent() failed deleting pre existing req component");
        }
    }
}
