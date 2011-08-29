package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//Note: un-ignore and test within eclipse because the data for courseservice are not working via command-line: mvn clean install
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
    @Ignore
    public void testGetCourseOffering() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

    	try{
    		try{
    			coServiceValidation.getCourseOffering("Lui-blah", callContext);
    		}catch (DoesNotExistException enee){}
    		
    		CourseOfferingInfo obj = coServiceValidation.getCourseOffering("Lui-1", callContext);
    		assertNotNull(obj);
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey()); 
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey()); 
            assertEquals("Lui Desc 101", obj.getDescr().getPlain());  
            
    	} catch (Exception ex) {
    		fail("exception from service call :" + ex.getMessage());
    	}    	
    }

    @Test
    @Ignore
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
      
        //test update
        retrieved.setStateKey(LuiServiceConstants.LUI_APROVED_STATE_KEY);
        ResultValuesGroupInfo rv = new ResultValuesGroupInfo();
        rv.setStateKey("test");
        rv.setTypeKey("test");
        rv.setEffectiveDate(Calendar.getInstance().getTime());
        rv.setName("test");
        ResultValueRangeInfo rvr = new ResultValueRangeInfo();
        rvr.setEffectiveDate(Calendar.getInstance().getTime());
        rv.setResultValueRange(rvr);
        retrieved.setCreditOptions(rv);
        try {
			coServiceValidation.updateCourseOffering(retrieved.getId(), retrieved, callContext);
			
			CourseOfferingInfo retrieved2 = coServiceValidation.getCourseOffering(retrieved.getId(), callContext);
			assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, retrieved2.getStateKey()); 
		} catch (VersionMismatchException ex) {
			fail("exception from service call :" + ex.getMessage());
		}
    }
    
    @Test
    @Ignore
    public void testCreateAndGetActivityOffering() throws AlreadyExistsException, DataValidationErrorException,InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException{
    	List<String> courseOfferingIdList = new ArrayList<String>();
    	courseOfferingIdList.add("Lui-1");
		ActivityOfferingInfo ao = new ActivityOfferingInfo();
		ao.setActivityId("CLU-1");
		ao.setTermKey("testAtpId1");
		ao.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		ao.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
		
		 try {
			 ActivityOfferingInfo created = coServiceValidation.createActivityOffering(courseOfferingIdList, ao, callContext);
			 assertNotNull(created);
			 
			 ActivityOfferingInfo retrieved = coServiceValidation.getActivityOffering(created.getId(), callContext);
			 assertNotNull(retrieved);
			 assertEquals("CLU-1", retrieved.getActivityId());
		     assertEquals("testAtpId1", retrieved.getTermKey());
		     assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
		     assertEquals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, retrieved.getTypeKey()); 
		     
		     //test getActivitiesForCourseOffering
		     List<ActivityOfferingInfo> activities = coServiceValidation.getActivitiesForCourseOffering("Lui-1", callContext);
		     assertNotNull(activities);
		     assertTrue(activities.size() == 1);
		     assertEquals(activities.get(0).getActivityId(), "CLU-1");
		     assertEquals(activities.get(0).getId(), created.getId());
		 } catch (Exception ex) {
	    		fail("exception from service call :" + ex.getMessage());
    	 }  
    }
    
    @Test
    @Ignore
	public void testCreateAndGetRegistrationGroup() throws AlreadyExistsException, DoesNotExistException,DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,PermissionDeniedException {
    	String courseOfferingId = "Lui-1";
		RegistrationGroupInfo rg = new RegistrationGroupInfo();
		rg.setFormatId("CLU-1");
		rg.setName("RegGroup-1");
		rg.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
		rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
		
		try {
			RegistrationGroupInfo created =  coServiceValidation.createRegistrationGroup(courseOfferingId, rg, callContext);
			assertNotNull(created);
			
			RegistrationGroupInfo retrieved = coServiceValidation.getRegistrationGroup(created.getId(), callContext);
			assertNotNull(retrieved);
			assertEquals("CLU-1", retrieved.getFormatId());
			assertEquals("RegGroup-1", retrieved.getName());
		    assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, retrieved.getStateKey()); 
		    assertEquals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, retrieved.getTypeKey());
		    
		    //test getRegGroupsForCourseOffering
		    List<RegistrationGroupInfo> rgs = coServiceValidation.getRegGroupsForCourseOffering(courseOfferingId, callContext);
		    assertNotNull(rgs);
		    assertTrue(rgs.size() == 1);
		    assertEquals(rgs.get(0).getFormatId(), "CLU-1");
		    assertEquals(rgs.get(0).getId(), created.getId());
		    assertEquals(rgs.get(0).getCourseOfferingId(), courseOfferingId);
		} catch (Exception ex) {
    		fail("exception from service call :" + ex.getMessage());
		}
	}
    
	@Test
    public void testUpdateCourseOffering() throws DataValidationErrorException, 
    		DoesNotExistException, InvalidParameterException, MissingParameterException, 
    		OperationFailedException, PermissionDeniedException,VersionMismatchException {
	    	try{
		    	CourseOfferingInfo obj = coServiceValidation.getCourseOffering("Lui-1", callContext);
		    	assertNotNull(obj);
		    	
		    	obj.setTermKey("testAtpId1");
		    	List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
		    	OfferingInstructorInfo instructor = new OfferingInstructorInfo();
		    	instructor.setPersonId("Pers-1");
		    	instructor.setPercentageEffort(Float.valueOf("60"));
		    	instructor.setTypeKey(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
		    	instructor.setStateKey(LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY);
		    	instructors.add(instructor);
		    	obj.setInstructors(instructors);
		    	CourseOfferingInfo updated = coServiceValidation.updateCourseOffering("Lui-1", obj, callContext);
		    	assertNotNull(updated);
		    	
		    	CourseOfferingInfo retrieved = coServiceValidation.getCourseOffering("Lui-1", callContext);
		    	assertNotNull(retrieved);
		    	assertTrue(retrieved.getInstructors().size() == 1);
	    	} catch (Exception ex) {
	    		fail("exception from service call :" + ex.getMessage());
			}
    }   
}
