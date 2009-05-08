package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dto.MetaInfo;
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
import org.kuali.student.lum.lu.dto.LuNlStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

@Daos({@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql" /*
                                                                                                         * , testDataFile =
                                                                                                         * "classpath:test-beans.xml"
                                                                                                         */)})
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDSLServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181", additionalContextFile="classpath:lu-additional-context.xml")
    public LuService client;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void testGetLuStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {

        List<LuStatementTypeInfo> stmtTypeList = client.getLuStatementTypes();

        assertNotNull(stmtTypeList);
        assertEquals(3, stmtTypeList.size());

        LuStatementTypeInfo stmtType = null;

        if ("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(0).getId())) {
            stmtType = stmtTypeList.get(0);
        } else if ("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(1).getId())) {
            stmtType = stmtTypeList.get(1);
        } else if ("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(2).getId())) {
            stmtType = stmtTypeList.get(2);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void testGetLuStatementType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {

        LuStatementTypeInfo stmtType = client.getLuStatementType("kuali.luStatementType.coreqAcademicReadiness");

        assertNotNull(stmtType);

        assertEquals(stmtType.getId(), "kuali.luStatementType.coreqAcademicReadiness");
        assertEquals(stmtType.getDesc(), "Co req used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType.getName(), "Academic Readiness Co Reqs");
        assertEquals(stmtType.getEffectiveDate(), df.parse("20000101"));
        assertEquals(stmtType.getExpirationDate(), df.parse("20001231"));
    }

    @Test
    public void testGetLuStmtTypeForLuStmtType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<LuStatementTypeInfo> stmtTypeInfoList = client.getLuStatementTypesForLuStatementType("kuali.luStatementType.createCourseAcademicReadiness");

        assertNotNull(stmtTypeInfoList);
        assertEquals(2, stmtTypeInfoList.size());

        LuStatementTypeInfo stmtType = null;

        if ("kuali.luStatementType.prereqAcademicReadiness".equals(stmtTypeInfoList.get(0).getId())) {
            stmtType = stmtTypeInfoList.get(0);
        } else if ("kuali.luStatementType.prereqAcademicReadiness".equals(stmtTypeInfoList.get(1).getId())) {
            stmtType = stmtTypeInfoList.get(1);
        } else {
            assertTrue(false);
        }

        assertEquals(stmtType.getDesc(), "Pre req rules used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType.getName(), "Academic Readiness Pre Reqs");
        assertEquals(stmtType.getEffectiveDate(), df.parse("20000101"));
        assertEquals(stmtType.getExpirationDate(), df.parse("20001231"));
    }

    @Test
    public void testGetLuStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        LuStatementInfo stmt = client.getLuStatement("STMT-2");

        assertNotNull(stmt);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(), "ACTIVE");
        assertEquals(stmt.getName(), "STMT 2");
        assertEquals(stmt.getDesc(), "Statement 2");

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
        List<LuStatementInfo> stmtList = client.getLuStatementsByType("kuali.luStatementType.prereqAcademicReadiness");

        assertNotNull(stmtList);
        assertEquals(4, stmtList.size());

        LuStatementInfo stmt = stmtList.get(0);

        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(), "ACTIVE");
        assertEquals(stmt.getName(), "STMT 2");
        assertEquals(stmt.getDesc(), "Statement 2");

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
            client.getLuStatement("invalid.stmt");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testGetStmtByInvldType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<LuStatementInfo> stmtList = client.getLuStatementsByType("invalid.stmttype");

        assertNotNull(stmtList);
        assertEquals(0, stmtList.size());
    }

    @Test
    public void testStmtStmtRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        LuStatementInfo stmt = client.getLuStatement("STMT-1");

        assertNotNull(stmt);

        assertEquals(stmt.getId(), "STMT-1");

        List<String> stmtIds = stmt.getLuStatementIds();

        assertEquals(1, stmtIds.size());
        assertTrue(stmtIds.contains("STMT-2"));
    }

    @Test
    public void testGetLuStatementsByClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {

        List<LuStatementInfo> stmtList = client.getLuStatementsForClu("CLU-1");

        assertNotNull(stmtList);
        assertEquals(1, stmtList.size());

        LuStatementInfo stmt = stmtList.get(0);
        assertEquals(stmt.getId(), "STMT-1");
        assertEquals(stmt.getType(), "kuali.luStatementType.createCourseAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(), "ACTIVE");
        assertEquals(stmt.getName(), "STMT 1");
        assertEquals(stmt.getDesc(), "Statement 1");

        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(0, reqCompIds.size());

        List<String> stmtIds = stmt.getLuStatementIds();
        assertEquals(1, stmtIds.size());

        assertTrue(stmtIds.contains("STMT-2"));

        MetaInfo mf = stmt.getMetaInfo();

        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(), "UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));
    }

    @Test
    public void testGetInvldLuStatementType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {

        try {
            client.getLuStatementType("invalid.stmttype");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testGetReqComponentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = client.getReqComponentTypes();

        assertNotNull(reqCompTypeInfoList);
        assertEquals(6, reqCompTypeInfoList.size());

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
       ReqComponentTypeInfo rqt = client.getReqComponentType("kuali.reqCompType.courseList.all");
        
        assertNotNull(rqt);
        assertEquals(rqt.getId(), "kuali.reqCompType.courseList.all");
        assertEquals(rqt.getDesc(), "Student must have completed all of <reqCompFieldType.cluSet>");
        assertEquals(rqt.getName(), "All of required courses");
        assertEquals(rqt.getEffectiveDate(), df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(), df.parse("20001231"));                
    }
    
    @Test
    public void testGetReqComponentFieldType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {        
        ReqComponentTypeInfo rqt = client.getReqComponentType("kuali.reqCompType.courseList.all");
        
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
        List<ReqComponentTypeInfo> reqCompTypeInfoList = client.getReqComponentTypesForLuStatementType("kuali.luStatementType.prereqAcademicReadiness");

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

        assertEquals(rqt.getDesc(), "Student needs a minimum GPA of <reqCompFieldType.gpa>");
        assertEquals(rqt.getName(), "Minimum overall GPA");
        assertEquals(rqt.getEffectiveDate(), df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(), df.parse("20011130"));
    }

    @Test
    public void testGetReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentInfo reqComp = client.getReqComponent("REQCOMP-1");

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-1");
        assertEquals(reqComp.getType(), "kuali.reqCompType.courseList.all");
        assertEquals(reqComp.getDesc(), "Required Component 1");
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
    public void testGetReqComponentsByType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentInfo> reqCompList = client.getReqComponentsByType("kuali.reqCompType.grdCondCourseList");

        assertNotNull(reqCompList);
        assertEquals(1, reqCompList.size());

        ReqComponentInfo reqComp = reqCompList.get(0);

        assertNotNull(reqComp);

        assertEquals(reqComp.getId(), "REQCOMP-2");
        assertEquals(reqComp.getType(), "kuali.reqCompType.grdCondCourseList");
        assertEquals(reqComp.getDesc(), "Required Component 2");
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
        List<ReqComponentInfo> reqCompList = client.getReqComponentsByType("invalid.reqcomptype");

        assertNotNull(reqCompList);
        assertEquals(0, reqCompList.size());
    }

    @Test
    public void testInvldGetReqComponentType() throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        try {
            client.getReqComponentType("invalid.reqcomp");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testCreateLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {

        LuStatementInfo stmt = new LuStatementInfo();
        stmt.setDesc("Statement 3");
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");
        stmt.setType("kuali.luStatementType.coreqAcademicReadiness");

        MetaInfo mf = new MetaInfo();

        mf.setCreateId("CREATEID");
        mf.setUpdateId("UPDATEID");

        stmt.setMetaInfo(mf);

        LuStatementInfo createdSmt = client.createLuStatement("kuali.luStatementType.coreqAcademicReadiness", stmt);

        assertNotNull(createdSmt.getId());
        assertEquals(createdSmt.getType(), "kuali.luStatementType.coreqAcademicReadiness");
        assertEquals(createdSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(createdSmt.getState(), "ACTIVE");
        assertEquals(createdSmt.getName(), "STMT 3");
        assertEquals(createdSmt.getDesc(), "Statement 3");
    }

    @Test
    public void testUpdateLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        LuStatementInfo stmt = client.getLuStatement("STMT-1");
        stmt.setDesc("Statement 3");
        stmt.setName("STMT 3");
        stmt.setOperator(StatementOperatorTypeKey.OR);
        stmt.setState("ACTIVE");

        MetaInfo mfOrg = stmt.getMetaInfo();

        MetaInfo mf = new MetaInfo();

        stmt.setMetaInfo(mf);

        LuStatementInfo updSmt = null;
        try {
            updSmt = client.updateLuStatement(stmt.getId(), stmt);
            fail("Should throw version mismatch exception");
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }

        stmt.setMetaInfo(mfOrg);

        try {
            updSmt = client.updateLuStatement(stmt.getId(), stmt);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }

        assertNotNull(updSmt.getId());
        assertEquals(updSmt.getType(), "kuali.luStatementType.createCourseAcademicReadiness");
        assertEquals(updSmt.getOperator(), StatementOperatorTypeKey.OR);
        assertEquals(updSmt.getState(), "ACTIVE");
        assertEquals(updSmt.getName(), "STMT 3");
        assertEquals(updSmt.getDesc(), "Statement 3");
    }

    @Test
    public void testDeleteLuStatement() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatusInfo si;
        LuStatementInfo stmt = client.getLuStatement("STMT-2");
        try {
            si = client.deleteLuStatement(stmt.getId());
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("LuService.deleteLuStatement() failed deleting pre existing statement");
        }
    }

    @Test
    public void testRemoveLuStatementFromClu() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatusInfo si = null;
        LuStatementInfo stmt = client.getLuStatement("STMT-1");

        try {
            si = client.removeLuStatementFromClu("invalid.clu", stmt.getId());
            fail("Remove LuStatement from CLU did not throw Doesnotexistexception for invalid clu");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
        }

        try {
            si = client.removeLuStatementFromClu(null, stmt.getId());
            fail("Remove LuStatement from CLU did not throw MissingParameterException for null clu");
        } catch (MissingParameterException mpe) {
            assertTrue(true);
        }

        si = client.removeLuStatementFromClu("CLU-1", stmt.getId());
        assertTrue(si.getSuccess());
        List<LuStatementInfo> stmtList = client.getLuStatementsForClu("CLU-1");
        assertEquals(0, stmtList.size());
    }


    @Test
    public void testCreateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {

        ReqComponentInfo req = new ReqComponentInfo();
        
        req.setDesc("Required Component 5");
        req.setEffectiveDate(df.parse("20010101"));
        req.setExpirationDate(df.parse("20020101"));
        req.setState("ACTIVE");
        req.setType("kuali.reqCompType.courseList.all");

        MetaInfo mf = new MetaInfo();

        req.setMetaInfo(mf);

        ReqComponentInfo createdReq = client.createReqComponent("kuali.reqCompType.courseList.all", req);

        assertNotNull(createdReq.getId());
        assertEquals(createdReq.getType(), "kuali.reqCompType.courseList.all");
        assertEquals(createdReq.getEffectiveDate(), df.parse("20010101"));
        assertEquals(createdReq.getExpirationDate(), df.parse("20020101"));
        assertEquals(createdReq.getState(), "ACTIVE");
        assertEquals(createdReq.getDesc(), "Required Component 5");
    }

    @Test
    public void testUpdateReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        ReqComponentInfo req = client.getReqComponent("REQCOMP-1");
        req.setDesc("Req Comp 3");
        req.setState("IN_PROGRESS");

        MetaInfo mfOrg = req.getMetaInfo();

        MetaInfo mf = new MetaInfo();

        req.setMetaInfo(mf);

        ReqComponentInfo updReq = null;
        try {
            updReq = client.updateReqComponent(req.getId(), req);
            fail("Should throw version mismatch exception");
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }

        req.setMetaInfo(mfOrg);

        try {
            updReq = client.updateReqComponent(req.getId(), req);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }

        assertNotNull(updReq.getId());
        assertEquals(updReq.getType(), "kuali.reqCompType.courseList.all");
        assertEquals(updReq.getState(), "IN_PROGRESS");
        assertEquals(updReq.getDesc(), "Req Comp 3");
        assertEquals(updReq.getReqCompField().size(), 0);
    }
    @Test
    public void testUpdateReqComponentField() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {

        ReqComponentInfo req = client.getReqComponent("REQCOMP-1");
        
        assertEquals(req.getReqCompField().size(), 0);
        
        ReqCompFieldInfo rcfInfo = new ReqCompFieldInfo();
        rcfInfo.setId("reqCompFieldType.clu");
        rcfInfo.setValue("MATH 101");
        
        List<ReqCompFieldInfo> reqCompField = new ArrayList<ReqCompFieldInfo>();
        reqCompField.add(rcfInfo);
        req.setReqCompField(reqCompField);

        
        MetaInfo mfOrg = req.getMetaInfo();

        ReqComponentInfo updReq = null;

        req.setMetaInfo(mfOrg);

        try {
            updReq = client.updateReqComponent(req.getId(), req);
        } catch (VersionMismatchException e) {
            fail("Should not throw version mismatch exception");
        }
        
        assertEquals(updReq.getReqCompField().size(), 1);
        ReqCompFieldInfo newrcfInfo = updReq.getReqCompField().get(0);
                
        assertNotNull(updReq.getId());
        assertNotNull(newrcfInfo);
        assertEquals("MATH 101", newrcfInfo.getValue());
        assertEquals("reqCompFieldType.clu", newrcfInfo.getId());       
    }
    
    
    @Test
    public void testDeleteReqComponent() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, CircularReferenceException, VersionMismatchException {
        StatusInfo si;
        ReqComponentInfo req = client.getReqComponent("REQCOMP-1");
        try {
            si = client.deleteReqComponent(req.getId());
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("LuService.deleteReqComponent() failed deleting pre existing req component");
        }
    }
    
	@Test
	public void testTranslateReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	String nl = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG");
    	assertEquals("Student must have completed 1 of MATH 152, MATH 180", nl);
    }

    @Test
    public void testTranslateGPAReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = client.getNaturalLanguageForReqComponent("REQCOMP-NL-2", "KUALI.CATALOG");
        assertEquals("Student needs a minimum GPA of 3.5", nl);
    }
	
	@Test
	public void testGetNaturalLanguageForReqComponent() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForReqComponent("REQCOMP-NL-1", "KUALI.CATALOG");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForReqComponentInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        ReqComponentInfo reqCompInfo = client.getReqComponent("REQCOMP-NL-1");
		String naturalLanguage = client.getNaturalLanguageForReqComponentInfo(reqCompInfo, "KUALI.CATALOG");
        assertEquals("Student must have completed 1 of MATH 152, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String naturalLanguage = client.getNaturalLanguageForLuStatement("CLU-NL-1", "STMT-5", "KUALI.CATALOG");
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 OR Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	private List<ReqCompFieldInfo> createReqComponentFields(String expectedValue, String operator, String reqCompFieldType, String id) {
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
		reqComp.setReqCompField(fieldList);
		reqComp.setType(reqComponentType);
		
		return reqComp;
    }

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Simple_CluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo reqComp1 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG");

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 OR Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	public void testGetNaturalLanguageForLuStatementInfo_Simple_Clu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		LuNlStatementInfo statementInfo = new LuNlStatementInfo();
		statementInfo.setOperator(StatementOperatorTypeKey.OR);

		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-3");
		ReqComponentInfo reqComp1 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList1);
		reqComp1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo reqComp2 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		reqComp2.setId("req-2");
		
		statementInfo.setRequiredComponents(Arrays.asList(reqComp1, reqComp2));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", statementInfo, "KUALI.CATALOG");

		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 OR Student must have completed 2 of MATH 152, MATH 221, MATH 180", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Complex1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		//Rule: ((R1 OR R2) AND R3) OR R4
		LuNlStatementInfo s1 = new LuNlStatementInfo();
		s1.setName("s1");
		s1.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s2 = new LuNlStatementInfo();
		s2.setName("s2");
		s2.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s3 = new LuNlStatementInfo();
		s3.setName("s3");
		s3.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s4 = new LuNlStatementInfo();
		s4.setName("s4");
		s4.setOperator(StatementOperatorTypeKey.AND);

		s2.setChildren(Arrays.asList(s3, s4));

		LuNlStatementInfo s5 = new LuNlStatementInfo();
		s5.setName("s5");
		s5.setOperator(StatementOperatorTypeKey.AND);

		s1.setChildren(Arrays.asList(s2, s5));
		
		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r1 = createReqComponent("kuali.reqCompType.courseList.none", fieldList1);
		r1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("3", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-1,CLU-NL-2,CLU-NL-3");
		ReqComponentInfo r2 = createReqComponent("kuali.reqCompType.courseList.all", fieldList2);
		r2.setId("req-2");
		List<ReqCompFieldInfo> fieldList3 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r3 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList3);
		r3.setId("req-3");
		List<ReqCompFieldInfo> fieldList4 = createReqComponentFields("4", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-3");
		ReqComponentInfo r4 = createReqComponent("kuali.reqCompType.courseList.all", fieldList4);
		r4.setId("req-4");
		
		s3.setRequiredComponents(Arrays.asList(r1, r2));
		s4.setRequiredComponents(Arrays.asList(r3));
		s5.setRequiredComponents(Arrays.asList(r4));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", s1, "KUALI.CATALOG");

		assertEquals("Requirement for MATH 152 Linear Systems: " +
				"((Student must have completed none of MATH 152, MATH 180 " +
				"OR Student must have completed all of MATH 152, MATH 221, MATH 180) " +
				"AND Student must have completed 2 of MATH 152, MATH 221, MATH 180) " +
				"OR Student must have completed all of MATH 221, MATH 180, MATH 200, MATH 215", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForLuStatementInfo_Complex2() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		//Rule: ((R1 OR R2) AND (R3 OR R4)) OR R5 OR (R6 AND (R7 OR R8))
		LuNlStatementInfo s1 = new LuNlStatementInfo();
		s1.setName("s1");
		s1.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s2 = new LuNlStatementInfo();
		s2.setName("s2");
		s2.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s3 = new LuNlStatementInfo();
		s3.setName("s3");
		s3.setOperator(StatementOperatorTypeKey.OR);

		LuNlStatementInfo s4 = new LuNlStatementInfo();
		s4.setName("s4");
		s4.setOperator(StatementOperatorTypeKey.OR);

		s2.setChildren(Arrays.asList(s3, s4));

		LuNlStatementInfo s5 = new LuNlStatementInfo();
		s5.setName("s5");
		s5.setOperator(StatementOperatorTypeKey.AND);

		LuNlStatementInfo s6 = new LuNlStatementInfo();
		s6.setName("s6");
		s6.setOperator(StatementOperatorTypeKey.AND);
		
		s1.setChildren(Arrays.asList(s2, s5, s6));

		LuNlStatementInfo s7 = new LuNlStatementInfo();
		s7.setName("s7");
		s7.setOperator(StatementOperatorTypeKey.AND);
		
		LuNlStatementInfo s8 = new LuNlStatementInfo();
		s8.setName("s8");
		s8.setOperator(StatementOperatorTypeKey.OR);
		
		s6.setChildren(Arrays.asList(s7, s8));

		List<ReqCompFieldInfo> fieldList1 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r1 = createReqComponent("kuali.reqCompType.courseList.none", fieldList1);
		r1.setId("req-1");
		List<ReqCompFieldInfo> fieldList2 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-1");
		ReqComponentInfo r2 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList2);
		r2.setId("req-2");
		List<ReqCompFieldInfo> fieldList3 = createReqComponentFields("0", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r3 = createReqComponent("kuali.reqCompType.courseList.none", fieldList3);
		r3.setId("req-3");
		List<ReqCompFieldInfo> fieldList4 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r4 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList4);
		r4.setId("req-4");
		List<ReqCompFieldInfo> fieldList5 = createReqComponentFields("3", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r5 = createReqComponent("kuali.reqCompType.courseList.all", fieldList5);
		r5.setId("req-5");
		List<ReqCompFieldInfo> fieldList6 = createReqComponentFields("1", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-3");
		ReqComponentInfo r6 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList6);
		r6.setId("req-6");
		List<ReqCompFieldInfo> fieldList7 = createReqComponentFields("2", "greater_than_or_equal_to", "reqCompFieldType.cluSet", "CLUSET-NL-2");
		ReqComponentInfo r7 = createReqComponent("kuali.reqCompType.courseList.nof", fieldList7);
		r7.setId("req-7");
		List<ReqCompFieldInfo> fieldList8 = createReqComponentFields("4", "greater_than_or_equal_to", "reqCompFieldType.clu", "CLU-NL-2, CLU-NL-3, CLU-NL-4, CLU-NL-5");
		ReqComponentInfo r8 = createReqComponent("kuali.reqCompType.courseList.all", fieldList8);
		r8.setId("req-8");
		
		s3.setRequiredComponents(Arrays.asList(r1, r2));
		s4.setRequiredComponents(Arrays.asList(r3, r4));
		s5.setRequiredComponents(Arrays.asList(r5));
		s7.setRequiredComponents(Arrays.asList(r6));
		s8.setRequiredComponents(Arrays.asList(r7, r8));
		
		String naturalLanguage = client.getNaturalLanguageForLuStatementInfo("CLU-NL-1", s1, "KUALI.CATALOG");

		assertEquals("Requirement for MATH 152 Linear Systems: " +
				"((Student must have completed none of MATH 152, MATH 180 " +
				"OR Student must have completed 1 of MATH 152, MATH 180) " +
				"AND (Student must have completed none of MATH 152, MATH 221, MATH 180 " +
				"OR Student must have completed 1 of MATH 152, MATH 221, MATH 180)) " +
				"OR Student must have completed all of MATH 152, MATH 221, MATH 180 " +
				"OR (Student must have completed 1 of MATH 221, MATH 180, MATH 200, MATH 215 " +
				"AND (Student must have completed 2 of MATH 152, MATH 221, MATH 180 " +
				"OR Student must have completed all of MATH 221, MATH 180, MATH 200, MATH 215))", naturalLanguage);
	}

	@Test
	public void testGetNaturalLanguageForStatementAsTree() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementAsTree("CLU-NL-1", "STMT-5", "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 OR R2", rootNode.getBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 OR Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}

	@Test
	public void testGetNaturalLanguageForStatementInfoAsTree() throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		LuStatementInfo statementInfo = client.getLuStatement("STMT-5");
		NLTranslationNodeInfo rootNode = client.getNaturalLanguageForStatementInfoAsTree("CLU-NL-1", statementInfo, "KUALI.CATALOG");

		assertEquals("STMT-5", rootNode.getId());
		assertEquals("R1 OR R2", rootNode.getBooleanExpression());
		assertEquals(2, rootNode.getChildNodes().size());
		assertEquals("Requirement for MATH 152 Linear Systems: Student must have completed 1 of MATH 152, MATH 180 OR Student must have completed 2 of MATH 152, MATH 221, MATH 180", rootNode.getNLTranslation());
	}
}
