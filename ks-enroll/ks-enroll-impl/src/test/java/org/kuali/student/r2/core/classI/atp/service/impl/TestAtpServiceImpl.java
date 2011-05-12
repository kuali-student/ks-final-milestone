package org.kuali.student.r2.core.classI.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Daos( { @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpDao", testSqlFile = "classpath:ks-atp.sql"),
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpTypeDao"),
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpStateDao"),
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpRichTextDao"),
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpAtpRelationDao"),
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpAtpRelationTypeDao") } )
@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestAtpServiceImpl extends AbstractServiceTest{
    @Client(value = "org.kuali.student.r2.core.classI.atp.service.impl.AtpServiceImpl")
    
    public AtpService atpService;
    public ApplicationContext appContext;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Before
    public void setUp() {
        principalId = "123";
        appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }
    
    @Test 
    public void testGetAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        try{
           AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
           assertNotNull(atpInfo);
           assertEquals("testAtpId1", atpInfo.getKey());
           assertEquals("testAtp1", atpInfo.getName());
           assertEquals("Desc", atpInfo.getDescr().getPlain());
           assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
           assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
       } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }
    
    @Ignore
    @Test
    //TODO: fix locking issue
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getKey());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = AtpInfo.getInstance(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = atpService.updateAtp(atpInfo.getKey(), modified, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }
    
    @Test
    public void testCreateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = AtpInfo.newInstance();
        atpInfo.setKey("newId");
        atpInfo.setName("newId");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try {
            AtpInfo created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        }
    }
   
    @Ignore
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpService.getAtp("testAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId2", atpInfo.getKey());
        
        try{
        atpService.deleteAtp("testAtpId2", callContext);
        AtpInfo deleted = atpService.getAtp("testAtpId1", callContext);
        assertEquals(deleted, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
