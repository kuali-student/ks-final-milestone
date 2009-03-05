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
import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;


@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl",testSqlFile="classpath:ks-lu.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDSLServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181")
    public LuService client;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    
    @Test
    public void testGetLuStatementType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
        
        List<LuStatementTypeInfo> stmtTypeList = client.getLuStatementTypes();
        
        assertNotNull(stmtTypeList);
        assertEquals(2, stmtTypeList.size());
        
        
        LuStatementTypeInfo stmtType1 = null;
        LuStatementTypeInfo stmtType2 = null;
        
        
        if("kuali.luStatementType.prereqAcademicReadiness".equals(stmtTypeList.get(0).getId())) {

            stmtType1 = stmtTypeList.get(0);
            stmtType2 = stmtTypeList.get(1);
            
        } else if("kuali.luStatementType.coreqAcademicReadiness".equals(stmtTypeList.get(0).getId())) {

            stmtType1 = stmtTypeList.get(1);
            stmtType2 = stmtTypeList.get(0);

        } else {
            assertTrue(false);
        }       
        
        assertEquals(stmtType1.getDesc(),"Pre req rules used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType1.getName(), "Academic Readiness Pre Reqs");
        assertEquals(stmtType1.getEffectiveDate(),df.parse("20000101"));
        assertEquals(stmtType1.getExpirationDate(),df.parse("20001231"));
        
        assertEquals(stmtType2.getDesc(),"Co req used in the evaluation of a person's academic readiness for enrollment in an LU.");
        assertEquals(stmtType2.getName(), "Academic Readiness Co Reqs");
        assertEquals(stmtType2.getEffectiveDate(),df.parse("20000101"));
        assertEquals(stmtType2.getExpirationDate(),df.parse("20001231"));                    
    }
    
    @Test
    public void testGetReqComponentType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, ParseException {
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

        if("kuali.reqCompType.gradcheck".equals(reqCompTypeInfoList.get(0).getId())) {
            rqt = reqCompTypeInfoList.get(0);
        } else if("kuali.reqCompType.gradcheck".equals(reqCompTypeInfoList.get(1).getId())) {
            rqt = reqCompTypeInfoList.get(1);
        } else if("kuali.reqCompType.gradcheck".equals(reqCompTypeInfoList.get(2).getId())) {
            rqt = reqCompTypeInfoList.get(2);
        } else {
            assertTrue(false);
        }
        
        
        assertEquals(rqt.getDesc(),"Minimum overall GPA of <value>");
        assertEquals(rqt.getName(), "Minimum overall GPA");
        assertEquals(rqt.getEffectiveDate(),df.parse("20000101"));
        assertEquals(rqt.getExpirationDate(),df.parse("20011130"));                                
    }    
    
    
}
