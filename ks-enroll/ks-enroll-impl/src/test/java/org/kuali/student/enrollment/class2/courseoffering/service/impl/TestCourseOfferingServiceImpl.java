package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//Note: un-ignore and test within eclipse because the data for courseservice are not working via command-line: mvn clean install
@Ignore
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

    @Test
    public void testGetCourseOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

    	try{
    		try{
    			coServiceValidation.getCourseOffering("Lui-blah", callContext);
    		}catch (DoesNotExistException enee){}
    		
    		CourseOfferingInfo obj = coServiceValidation.getCourseOffering("Lui-1", callContext);
    		assertNotNull(obj);
    		assertEquals("ENGL 100 section 123", obj.getCourseOfferingCode());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey()); 
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey()); 
            assertEquals("Lui Desc 101", obj.getDescr().getPlain());  
            
//            List<OfferingInstructorInfo> instructors = obj.getInstructors();
//            assertTrue(instructors.size() == 1);
//            assertEquals("Pers-1", instructors.get(0).getPersonId());
//            assertEquals("Org-1", instructors.get(0).getOrgId());
//            assertEquals("Instr-1", instructors.get(0).getPersonInfoOverride());
//            assertEquals(Float.valueOf("30.5"), instructors.get(0).getPercentageEffort());
    	} catch (Exception ex) {
    		fail("exception from service call :" + ex.getMessage());
    	}    	
    }

    @Test
    public void testCreateCourseOfferingFromCanonical() throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
    	List<String> formatIdList = new ArrayList<String>();
    	CourseOfferingInfo created = coServiceValidation.createCourseOfferingFromCanonical("CLU-1", "testAtpId1", formatIdList, callContext);
    	assertNotNull(created);
    	assertEquals("CLU-1", created.getCourseId());
    	assertEquals("testAtpId1", created.getTermKey());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey()); 

        CourseOfferingInfo retrieved = coServiceValidation.getCourseOffering(created.getId(), callContext);
    	assertNotNull(retrieved);
    	assertEquals("CLU-1", retrieved.getCourseId());
    	assertEquals("testAtpId1", retrieved.getTermKey());
    	assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, retrieved.getTypeKey()); 
        assertEquals("CHEM123", retrieved.getCourseOfferingCode()); 
        assertEquals("Chemistry 123", retrieved.getCourseTitle()); 
    }
}
