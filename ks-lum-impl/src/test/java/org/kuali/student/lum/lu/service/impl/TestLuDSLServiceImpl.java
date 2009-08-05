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
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;

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
}
