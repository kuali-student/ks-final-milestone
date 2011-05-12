package org.kuali.student.enrollment.classII.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Ignore
public class TestAcademicCalendarServiceImpl extends AbstractServiceTest{
    @Client(value = "org.kuali.student.enrollment.classII.acal.service.impl.AcademicCalendarServiceImpl")

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
    public void testGetAcademicCalendar()throws DoesNotExistException, InvalidParameterException,
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
  

}
