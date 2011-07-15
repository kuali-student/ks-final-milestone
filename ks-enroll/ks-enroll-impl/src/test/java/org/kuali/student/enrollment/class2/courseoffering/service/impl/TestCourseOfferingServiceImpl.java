package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
public class TestCourseOfferingServiceImpl {
	private CourseOfferingService coServiceValidation;
	
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Autowired
	public void setCoServiceValidation(CourseOfferingService coServiceValidation) {
		this.coServiceValidation = coServiceValidation;
	}
  
	@Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testServiceSetup() {
    	assertNotNull(coServiceValidation);
    }

    
}
