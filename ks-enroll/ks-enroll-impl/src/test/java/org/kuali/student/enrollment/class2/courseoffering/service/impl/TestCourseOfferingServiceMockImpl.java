/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ocleirig
 * 
 * This is a set of unit test cases that runs directly against a mock implementation of the class 2 course offering service.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class TestCourseOfferingServiceMockImpl {

	@Resource
	private CourseOfferingService coService;
	
	@Resource
	private AcademicCalendarService acadService;
	
	/**
	 * 
	 */
	public TestCourseOfferingServiceMockImpl() {
	}
	
	public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        
        AcademicCalendarInfo calendar = new AcademicCalendarInfo();
        
        calendar.setName(name)
		acadService.createAcademicCalendar("kuali.atp.type.AcademicCalendar", calendar , callContext);
        
        TermInfo termInfo = new TermInfo();
        
        termInfo.setCode("FALL2012");
        termInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        termInfo.setStartDate(new Date());
        
		acadService.createTerm("kuali.atp.type.Fall", termInfo, callContext);
        
        coService.createCourseOffering("C1", termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
		
		coService.createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
		
		coService.createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, callContext);
        
    }
	
	
    @Test
    public void testCreateRegistrationGroup() {
    	

    
    }
	
    
    @Test
    public void testCreateRegistrationGroupTemplate () {
    	
    	RegistrationGroupTemplateInfo rgt = new RegistrationGroupTemplateInfo();
    	
    	rgt.setName("Test Template");
    	
		coService.createRegistrationGroupTemplate(rgt, callContext);
    }
    
    @Test
	public void testRegistrationGroupCRUD() {
		
		
		
		coService.createRegistrationGroup("FO-1", regGroupType, registrationGroupInfo, callContext);
		
		
		
		
		
	}

}
