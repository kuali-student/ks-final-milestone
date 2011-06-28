package org.kuali.student.enrollment.class1.lui.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
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

}
