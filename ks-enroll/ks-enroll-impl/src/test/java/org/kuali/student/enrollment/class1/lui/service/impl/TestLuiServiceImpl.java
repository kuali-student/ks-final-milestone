package org.kuali.student.enrollment.class1.lui.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-test-context.xml"})
public class TestLuiServiceImpl {
private LuiService luiServiceValidation;

public static String principalId = "123";
public ContextInfo callContext = ContextInfo.newInstance();

@Autowired
public void setLuiServiceValidation(LuiService luiServiceValidation) {
	this.luiServiceValidation = luiServiceValidation;
}

@Before
public void setUp() {
    principalId = "123";    
    callContext = ContextInfo.getInstance(callContext);
    callContext.setPrincipalId(principalId);
}

@Test
public void testLuiServiceSetup() {
	assertNotNull(luiServiceValidation);
}

@Test
public void testGetLui()throws DoesNotExistException, InvalidParameterException,MissingParameterException, OperationFailedException {
	try{
		LuiInfo obj = luiServiceValidation.getLui("Lui-1", callContext);
		assertNotNull(obj);
		assertEquals("Lui one", obj.getName()); 
		assertEquals("ENGL 100 section 123", obj.getLuiCode());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey()); 
        assertEquals("Lui Desc 101", obj.getDescr().getPlain());  
	} catch (Exception ex) {
		fail("exception from service call :" + ex.getMessage());
	}
}

@Test
public void testCreateLui() throws AlreadyExistsException,DataValidationErrorException, DoesNotExistException,
		InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	LuiInfo info = new LuiInfo();
	info.setName("Test lui one");
	info.setLuiCode("LUI-Testing-Section");
	info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
	info.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
	info.setEffectiveDate(Calendar.getInstance().getTime());
	info.setMaximumEnrollment(25);
	info.setMinimumEnrollment(10);
	info.setCluId("testCluId");
	info.setAtpKey("testAtpId1");
	
	LuiInfo created = null;
	try{
		created = luiServiceValidation.createLui("testCluId", "testAtpId1", info, callContext);
		assertNotNull(created);
        assertEquals("Test lui one", created.getName());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());
        assertEquals(Integer.valueOf(25), created.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), created.getMinimumEnrollment());
		assertEquals("testCluId", created.getCluId());
		assertEquals("testAtpId1", created.getAtpKey());
        				
	} catch (Exception e) {
        fail(e.getMessage());
    }
	
	try {
		LuiInfo retrieved = luiServiceValidation.getLui(created.getId(), callContext);
		assertNotNull(retrieved);
		assertEquals("Test lui one", retrieved.getName());
	} catch (DoesNotExistException e) {
		fail(e.getMessage());
	}	
}
}
