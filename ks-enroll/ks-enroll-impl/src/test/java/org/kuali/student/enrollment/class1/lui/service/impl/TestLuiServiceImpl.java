package org.kuali.student.enrollment.class1.lui.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
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
}
