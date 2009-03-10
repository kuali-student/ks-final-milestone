package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;


@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl",testSqlFile="classpath:ks-lu.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDSLServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181")
    public LuService client;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void testGetLuStatementTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {

        List<LuStatementTypeInfo> stmtTypeList = client.getLuStatementTypes();
        
        assertNotNull(stmtTypeList);
        assertEquals(3, stmtTypeList.size());
 
        LuStatementTypeInfo stmtType = null;        
        
        if("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(0).getId())) {
            stmtType = stmtTypeList.get(0);           
        } else if("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(1).getId())) {
            stmtType = stmtTypeList.get(1);
        } else if("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(2).getId())) {
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
        assertEquals(stmtType.getDesc(),"Co req used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType.getName(), "Academic Readiness Co Reqs");
        assertEquals(stmtType.getEffectiveDate(),df.parse("20000101"));
        assertEquals(stmtType.getExpirationDate(),df.parse("20001231"));                    
    }

    @Test
    public void testGetLuStmtTypeForLuStmtType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<LuStatementTypeInfo> stmtTypeInfoList = client.getLuStatementTypesForLuStatementType("kuali.luStatementType.createCourseAcademicReadiness");
        
        assertNotNull(stmtTypeInfoList);
        assertEquals(2, stmtTypeInfoList.size());
        
        LuStatementTypeInfo stmtType = null;

        if("kuali.luStatementType.prereqAcademicReadiness".equals(stmtTypeInfoList.get(0).getId())) {
            stmtType = stmtTypeInfoList.get(0);
        } else if("kuali.luStatementType.prereqAcademicReadiness".equals(stmtTypeInfoList.get(1).getId())) {
            stmtType = stmtTypeInfoList.get(1);
        }  else {
            assertTrue(false);
        }
        
        
        assertEquals(stmtType.getDesc(),"Pre req rules used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType.getName(), "Academic Readiness Pre Reqs");
        assertEquals(stmtType.getEffectiveDate(),df.parse("20000101"));
        assertEquals(stmtType.getExpirationDate(),df.parse("20001231"));
    }   
    
    @Test
    public void testGetLuStatement()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException { 
        LuStatementInfo stmt = client.getLuStatement("STMT-2");
        
        assertNotNull(stmt);
        
        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(),"ACTIVE");
        assertEquals(stmt.getName(),"STMT 2");
        assertEquals(stmt.getDesc(), "Statement 2");
        
        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(3, reqCompIds.size());
        
        assertTrue( reqCompIds.contains("REQCOMP-1"));
        assertTrue( reqCompIds.contains("REQCOMP-2"));
        assertTrue( reqCompIds.contains("REQCOMP-3"));
    
        MetaInfo mf = stmt.getMetaInfo();
        
        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(),"UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));                
    }
    
    @Test
    public void testGetLuStatementsByType()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException { 
        List<LuStatementInfo> stmtList = client.getLuStatementsByType("kuali.luStatementType.prereqAcademicReadiness");
        
        assertNotNull(stmtList);
        assertEquals(1, stmtList.size());
        
        LuStatementInfo stmt = stmtList.get(0);
        
        assertEquals(stmt.getId(), "STMT-2");
        assertEquals(stmt.getType(), "kuali.luStatementType.prereqAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(),"ACTIVE");
        assertEquals(stmt.getName(),"STMT 2");
        assertEquals(stmt.getDesc(), "Statement 2");
        
        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(3, reqCompIds.size());
        
        assertTrue( reqCompIds.contains("REQCOMP-1"));
        assertTrue( reqCompIds.contains("REQCOMP-2"));
        assertTrue( reqCompIds.contains("REQCOMP-3"));
    
        MetaInfo mf = stmt.getMetaInfo();
        
        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(),"UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));                
    }
    
    @Test
    public void testGetInvldStmt()  throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        try {
            client.getLuStatement("invalid.stmt");
         } catch (DoesNotExistException dne) {
             assertTrue(true);
             return;
         }
         
         assertTrue(false);    
    }
    
    @Test
    public void testGetStmtByInvldType()  throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<LuStatementInfo> stmtList = client.getLuStatementsByType("invalid.stmttype");
        
        assertNotNull(stmtList);
        assertEquals(0,stmtList.size());
    }

    
    @Test
    public void testStmtStmtRelation()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        LuStatementInfo stmt = client.getLuStatement("STMT-1");
        
        assertNotNull(stmt);
        
        assertEquals(stmt.getId(), "STMT-1");
        
        List<String> stmtIds = stmt.getLuStatementIds();
        
        assertEquals(1, stmtIds.size());
        assertTrue( stmtIds.contains("STMT-2"));       
    }
    
    @Test
    public void testGetLuStatementsByClu()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException { 
        
        List<LuStatementInfo> stmtList = client.getLuStatementsForClu("CLU-1");
        
        assertNotNull(stmtList);
        assertEquals(1, stmtList.size());
        
        LuStatementInfo stmt = stmtList.get(0);        
        assertEquals(stmt.getId(), "STMT-1");
        assertEquals(stmt.getType(), "kuali.luStatementType.createCourseAcademicReadiness");
        assertEquals(stmt.getOperator(), StatementOperatorTypeKey.AND);
        assertEquals(stmt.getState(),"ACTIVE");
        assertEquals(stmt.getName(),"STMT 1");
        assertEquals(stmt.getDesc(), "Statement 1");
        
        List<String> reqCompIds = stmt.getReqComponentIds();
        assertEquals(0, reqCompIds.size());

        List<String> stmtIds = stmt.getLuStatementIds();
        assertEquals(1,stmtIds.size());
        
        assertTrue( stmtIds.contains("STMT-2"));
    
        MetaInfo mf = stmt.getMetaInfo();
        
        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(),"UPDATEID");
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
        assertEquals(3, reqCompTypeInfoList.size());
        
        ReqComponentTypeInfo rqt = null;

        if("kuali.reqCompType.courseList".equals(reqCompTypeInfoList.get(0).getId())) {
            rqt = reqCompTypeInfoList.get(0);
        } else if("kuali.reqCompType.courseList".equals(reqCompTypeInfoList.get(1).getId())) {
            rqt = reqCompTypeInfoList.get(1);
        } else if("kuali.reqCompType.courseList".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(2);
        } else {
            assertTrue(false);
        }
        
        assertEquals(rqt.getName(), "Course completed");
    }
    
    @Test
    public void testGetReqComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        ReqComponentTypeInfo rqt = client.getReqComponentType("kuali.reqCompType.courseList");
        
        assertNotNull(rqt);
        assertEquals(rqt.getId(),"kuali.reqCompType.courseList");
        assertEquals(rqt.getDesc(),"Student must have completed all of <courses>");
        assertEquals(rqt.getName(), "Course completed");
        assertEquals(rqt.getEffectiveDate(),df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(),df.parse("20001231"));                            
    }

    @Test
    public void testGetReqCompTypeForLuStmtType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        List<ReqComponentTypeInfo> reqCompTypeInfoList = client.getReqComponentTypesForLuStatementType("kuali.luStatementType.prereqAcademicReadiness");
        
        assertNotNull(reqCompTypeInfoList);
        assertEquals(3, reqCompTypeInfoList.size());
        
        ReqComponentTypeInfo rqt = null;

        if("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(0).getId())) {
            rqt = reqCompTypeInfoList.get(0);
        } else if("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(1).getId())) {
            rqt = reqCompTypeInfoList.get(1);
        } else if("kuali.reqCompType.gradecheck".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(2);
        } else {
            assertTrue(false);
        }
        
        
        assertEquals(rqt.getDesc(),"Minimum overall GPA of <value>");
        assertEquals(rqt.getName(), "Minimum overall GPA");
        assertEquals(rqt.getEffectiveDate(),df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(),df.parse("20011130"));                                
    }        

    @Test
    public void testGetReqComponent()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException { 
        ReqComponentInfo reqComp = client.getReqComponent("REQCOMP-1");
        
        assertNotNull(reqComp);
        
        assertEquals(reqComp.getId(), "REQCOMP-1");
        assertEquals(reqComp.getType(), "kuali.reqCompType.courseList");
        assertEquals(reqComp.getDesc(), "Required Component 1");
        assertEquals(reqComp.getEffectiveDate(), df.parse("20010101"));
        assertEquals(reqComp.getExpirationDate(),df.parse("20020101") );
        assertEquals(reqComp.getState(), "ACTIVE");
        
        MetaInfo mf = reqComp.getMetaInfo();
        
        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(),"UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));                
    }    

    @Test
    public void testGetReqComponentsByType()  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException { 
        List<ReqComponentInfo> reqCompList = client.getReqComponentsByType("kuali.reqCompType.grdCondCourseList");
        
        assertNotNull(reqCompList);
        assertEquals(1, reqCompList.size());
        
        ReqComponentInfo reqComp = reqCompList.get(0);
        
        assertNotNull(reqComp);
        
        assertEquals(reqComp.getId(), "REQCOMP-2");
        assertEquals(reqComp.getType(), "kuali.reqCompType.grdCondCourseList");
        assertEquals(reqComp.getDesc(), "Required Component 2");
        assertEquals(reqComp.getEffectiveDate(), df.parse("20010101"));
        assertEquals(reqComp.getExpirationDate(),df.parse("20020101") );
        assertEquals(reqComp.getState(), "ACTIVE");
        
        MetaInfo mf = reqComp.getMetaInfo();
        
        assertEquals(mf.getCreateId(), "CREATEID");
        assertEquals(mf.getUpdateId(),"UPDATEID");
        assertEquals(mf.getCreateTime(), df.parse("20000101"));
        assertEquals(mf.getUpdateTime(), df.parse("20010101"));                    
    }    
    
    @Test
    public void testGetReqCompByInvldType()  throws InvalidParameterException, MissingParameterException, OperationFailedException, ParseException, DoesNotExistException {
        List<ReqComponentInfo> reqCompList = client.getReqComponentsByType("invalid.reqcomptype");
        
        assertNotNull(reqCompList);
        assertEquals(0,reqCompList.size());
    }
    
    @Test
    public void testInvldGetReqComponentType() throws  InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        try {
            client.getReqComponentType("invalid.reqcomp");
        } catch (DoesNotExistException dne) {
            assertTrue(true);
            return;
        }
        
        assertTrue(false);
    }
    
}
