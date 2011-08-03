package org.kuali.student.enrollment.class2.courseoffering.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;


public class CourseOfferingInfoMaintainableImpl extends KualiMaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
	private transient CourseService courseService;
	private transient CourseOfferingService courseOfferingService;
	
    @Override
    public void saveBusinessObject() {
    	CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo)getDataObject();

    	
    	//get termKey from the user input through UI
    	String termKey = courseOfferingInfo.getTermKey();
    	//get courseId from the user input through UI
    	String courseId = courseOfferingInfo.getCourseId();
    	
    	CourseInfo course = null;
    	
		//get formatIdList
    	try {
    		course = getCourseService().getCourse(courseId);
    	}catch(org.kuali.student.common.exceptions.OperationFailedException ofe){
    	
    	}catch (org.kuali.student.common.exceptions.DoesNotExistException dnee){
    		
    	}catch (org.kuali.student.common.exceptions.InvalidParameterException ipe){

    	}catch (org.kuali.student.common.exceptions.PermissionDeniedException pde){
    		
    	}catch (org.kuali.student.common.exceptions.MissingParameterException mpe){
    		
    	}
    	
    	
    	try{
           	List<String> formatIdList = new ArrayList<String>();
           	/*
           	       	List<FormatInfo> formatList = course.getFormats();
           	       	for (FormatInfo format : formatList){
           	       		formatIdList.add(format.getId());
           	       	}
           	*/       	
           	// only pick the first format based on Larry's suggestion to simplify core slice development
           	FormatInfo format = course.getFormats().get(0);
        	formatIdList.add(format.getId());
        	//create a CourseOfferingInfo coi
        	CourseOfferingInfo coi = getCourseOfferingService().createCourseOfferingFromCanonical(courseId, termKey, formatIdList, ContextInfo.newInstance());
        	//create a list of instructors
        	List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();
        	//for each instructor, set personId to Id field.
        	for (OfferingInstructorInfo instructor : instructors){
        		OfferingInstructorInfo oii = new OfferingInstructorInfo();
        		instructor.setId(instructor.getPersonId());    		
        	}
        	//set the list of instructors to the CourseOfferingInfo coi
        	coi.setInstructors(instructors);
        	//update the CourseOfferingInfo coi in DB with instructors info
        	getCourseOfferingService().updateCourseOffering(coi.getId(), coi, ContextInfo.newInstance());

        	//create a list of ActivityOfferingInfo based on activities defined in the format of the course
        	List<String> courseOfferingIdList = new ArrayList<String>();
        	courseOfferingIdList.add(coi.getId());
        	
        	List<ActivityOfferingInfo> activityOfferingInfoList = new ArrayList <ActivityOfferingInfo>(); 
        	List<String> activityOfferingIdList = new ArrayList<String>();
        	List<ActivityInfo> activities = format.getActivities();
        	for (ActivityInfo activity : activities){
        		//strange, no info that needs to be copied over from ActivityInfo to ActivityOfferingInfo?
        		ActivityOfferingInfo activityOfferingInfo = new ActivityOfferingInfo();
        		activityOfferingInfo = getCourseOfferingService().createActivityOffering(courseOfferingIdList, activityOfferingInfo, ContextInfo.newInstance());
        		activityOfferingInfoList.add(activityOfferingInfo);
        		activityOfferingIdList.add(activityOfferingInfo.getId());
        	}

        	//create a RegiistrationGroup
        	RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
        	registrationGroupInfo.setCourseOfferingId(coi.getId());
        	registrationGroupInfo.setActivityOfferingIds(activityOfferingIdList);
        	getCourseOfferingService().createRegistrationGroup(coi.getId(), registrationGroupInfo, ContextInfo.newInstance());

    	}catch (OperationFailedException ofe){
    		
    	}catch (InvalidParameterException ipe){
    		
    	}catch (DoesNotExistException dnee){    		
   		
    	}catch (PermissionDeniedException pde){
    		
    	}catch (MissingParameterException mpe){
    		
    	}catch (AlreadyExistsException aee){
    		
    	}catch (DataValidationErrorException dvee){
    		
    	}catch (VersionMismatchException vme){
    		
    	}
		
    }
    
    protected CourseService getCourseService() {
         if(courseService == null) {
        	 courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE,"CourseService"));
        }
        return courseService;
    }
    
    protected CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null) {
        	courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,"CourseOfferingService"));
       }
       return courseOfferingService;
   }
}
