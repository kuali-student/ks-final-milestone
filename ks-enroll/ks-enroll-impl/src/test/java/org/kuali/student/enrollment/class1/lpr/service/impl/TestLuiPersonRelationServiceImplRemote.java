package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-test-context.xml"})
public class TestLuiPersonRelationServiceImplRemote {
	private LuiPersonRelationService lprService;
	
	public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
	
    @Autowired
	public void setLprService(LuiPersonRelationService lprService) {
		this.lprService = lprService;
	}

	@Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }
	
	@Test
    public void testLprServiceSetup() {
    	assertNotNull(lprService);
    }
	
}
