package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ar-test-context.xml"})
public class TestAcademicRecordServiceImpl {
    @Autowired
    @Qualifier("arService")
    private AcademicRecordService arService;

    @Autowired
    @Qualifier("courseRegService")
    private CourseRegistrationService courseRegService;
    
    public static String principalId = "123";
    public ContextInfo callContext = null;

	@Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testServiceSetup() {
    	assertNotNull(arService);
    	assertNotNull(courseRegService);
    }
	
	//The testing process involves creating courseOffering, registering course and grading. so it's better test academicrecord service methods from UI
}
