package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


public class CourseOfferingInfoMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
	private transient CourseService courseService;
	private transient CourseOfferingService courseOfferingService;

    @Override
    public void saveDataObject() {
    	CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo)getDataObject();

    	
    	//get termKey from the user input through UI
    	String termKey = courseOfferingInfo.getTermKey();
    	//get courseId from the user input through UI
    	String courseId = courseOfferingInfo.getCourseId();
    	
    	CourseInfo course = null;
    	try {
    		course = getCourseService().getCourse(courseId);
    	}catch(org.kuali.student.common.exceptions.OperationFailedException ofe){
    		System.out.println("call getCourseService().getCourse(courseId), and get OperationFailedException:  "+ofe.toString()); 		
    	}catch (org.kuali.student.common.exceptions.DoesNotExistException dnee){
    		System.out.println("call getCourseService().getCourse(courseId), and get DoesNotExistException:  "+dnee.toString()); 		
    	}catch (org.kuali.student.common.exceptions.InvalidParameterException ipe){
    		System.out.println("call getCourseService().getCourse(courseId), and get InvalidParameterException:  "+ipe.toString()); 		
    	}catch (org.kuali.student.common.exceptions.PermissionDeniedException pde){
    		System.out.println("call getCourseService().getCourse(courseId), and get PermissionDeniedException:  "+pde.toString());
     	}catch (org.kuali.student.common.exceptions.MissingParameterException mpe){
    		System.out.println("call getCourseService().getCourse(courseId), and get MissingParameterException:  "+mpe.toString());
    	}
    	
		//form the formatIdList
     	List<String> formatIdList = new ArrayList<String>();
     	/*
           	       	List<FormatInfo> formatList = course.getFormats();
           	       	for (FormatInfo format : formatList){
           	       		formatIdList.add(format.getId());
           	       	}
     	 */       	
     	FormatInfo firstFormat = null;
     	// only pick the first format based on Larry's suggestion to simplify core slice development
     	if (course != null) {
     		firstFormat = course.getFormats().get(0);
     		formatIdList.add(firstFormat.getId());
     	}
     	
     	CourseOfferingInfo coi = null;        
     	try{
        	//create a CourseOfferingInfo coi
        	coi = getCourseOfferingService().createCourseOfferingFromCanonical(courseId, termKey, formatIdList, ContextInfo.newInstance());
    	}catch (OperationFailedException ofe){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get OperationFailedException:  "+ofe.toString()); 	
    	}catch (InvalidParameterException ipe){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get InvalidParameterException:  "+ipe.toString());
    	}catch (DoesNotExistException dnee){    		
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get DoesNotExistException:  "+dnee.toString());
    	}catch (PermissionDeniedException pde){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get PermissionDeniedException:  "+pde.toString());
    	}catch (MissingParameterException mpe){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get MissingParameterException:  "+mpe.toString());
    	}catch (AlreadyExistsException aee){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get AlreadyExistsException:  "+aee.toString());
    	}catch (DataValidationErrorException dvee){
    		System.out.println("call courseOfferingService.createCourseOfferingFromCanonical() method, and get DataValidationErrorException:  "+dvee.toString());
     	}
        	
    	//create a list of instructors
    	List<OfferingInstructorInfo> instructors = courseOfferingInfo.getInstructors();
    	//for each instructor, set personId to Id field.
    	for (OfferingInstructorInfo instructor : instructors){
     		instructor.setId(instructor.getPersonId());    		
    	}
    	//set the list of instructors to the CourseOfferingInfo coi
    	if (coi != null){
    		coi.setInstructors(instructors);
    		coi.setStateKey(LuiServiceConstants.LUI_OFFERED_STATE_KEY);
     	
    		//update the CourseOfferingInfo coi in DB with instructors info
    		try{
    			getCourseOfferingService().updateCourseOffering(coi.getId(), coi, ContextInfo.newInstance());
        	}catch (OperationFailedException ofe){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get OperationFailedException:  "+ofe.toString()); 	
        	}catch (InvalidParameterException ipe){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get InvalidParameterException:  "+ipe.toString());
        	}catch (DoesNotExistException dnee){    		
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get DoesNotExistException:  "+dnee.toString());
        	}catch (PermissionDeniedException pde){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get PermissionDeniedException:  "+pde.toString());
        	}catch (MissingParameterException mpe){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get MissingParameterException:  "+mpe.toString());
         	}catch (VersionMismatchException vme){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get VersionMismatchException:  "+vme.toString());
        	}catch (DataValidationErrorException dvee){
        		System.out.println("call courseOfferingService.updateCourseOffering() method, and get DataValidationErrorException:  "+dvee.toString());
         	}
    	}

    	//create a list of ActivityOfferingInfo based on activities defined in the format of the course
    	List<String> courseOfferingIdList = new ArrayList<String>();
    	courseOfferingIdList.add(coi.getId());

    	List<ActivityOfferingInfo> activityOfferingInfoList = new ArrayList <ActivityOfferingInfo>(); 
    	List<String> activityOfferingIdList = new ArrayList<String>();
    	if (firstFormat != null){
    		List<ActivityInfo> activities = firstFormat.getActivities();
    		for (ActivityInfo activity : activities){
    			ActivityOfferingInfo activityOfferingInfo = new ActivityOfferingInfo();
    			activityOfferingInfo.setInstructors(instructors);
    			//It looks like termKey and activityId are required fields to create an ActivityOfferingInfo data entry
    			activityOfferingInfo.setTermKey(termKey);
    			activityOfferingInfo.setActivityId(activity.getId());
    			try {
    				List<TypeInfo> activityOfferingTypes = getCourseOfferingService().getActivityOfferingTypesForActivityType(activity.getActivityType(), ContextInfo.newInstance());
    				if(activityOfferingTypes.size()>1){
    					System.out.println(">>for core slice, it should be 1-to-1 mapping. so only take the first one -- "+activityOfferingTypes.get(0).getKey());
    				}

    				//for Core Slice -- if the mapping between Canonical Activity to Activity Offering is not 1-to-1, 
    				//(see https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Types)
    				//only take the first one.
    				activityOfferingInfo.setTypeKey(activityOfferingTypes.get(0).getKey());
    				activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_OFFERED_STATE_KEY);
    				activityOfferingInfo = getCourseOfferingService().createActivityOffering(courseOfferingIdList, activityOfferingInfo, ContextInfo.newInstance());
    				activityOfferingInfoList.add(activityOfferingInfo);
    				activityOfferingIdList.add(activityOfferingInfo.getId());

    				//create a RegiistrationGroup after successfully create all activityOfferingInfos
    				RegistrationGroupInfo registrationGroupInfo = new RegistrationGroupInfo();
    				registrationGroupInfo.setCourseOfferingId(coi.getId());
    				registrationGroupInfo.setActivityOfferingIds(activityOfferingIdList);
    				registrationGroupInfo.setStateKey(LuiServiceConstants.LUI_OFFERED_STATE_KEY);
    				registrationGroupInfo.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
    				try {
    					getCourseOfferingService().createRegistrationGroup(coi.getId(), registrationGroupInfo, ContextInfo.newInstance());
    				}catch (OperationFailedException ofe){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get OperationFailedException:  "+ofe.toString()); 	
    				}catch (InvalidParameterException ipe){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get InvalidParameterException:  "+ipe.toString());
    				}catch (DoesNotExistException dnee){    		
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get DoesNotExistException:  "+dnee.toString());
    				}catch (PermissionDeniedException pde){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get PermissionDeniedException:  "+pde.toString());
    				}catch (MissingParameterException mpe){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get MissingParameterException:  "+mpe.toString());
    				}catch (AlreadyExistsException aee){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get AlreadyExistsException:  "+aee.toString());
    				}catch (DataValidationErrorException dvee){
    					System.out.println("call courseOfferingService.createRegistrationGroup() method, and get DataValidationErrorException:  "+dvee.toString());
    				}

    			}catch (OperationFailedException ofe){
    				System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get OperationFailedException:  "+ofe.toString()); 	
    			}catch (InvalidParameterException ipe){
    				System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get InvalidParameterException:  "+ipe.toString());
    			}catch (PermissionDeniedException pde){
    				System.out.println("call courseOfferingService.createActivityOffering() method, and get PermissionDeniedException:  "+pde.toString());
    			}catch (MissingParameterException mpe){
    				System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() or createActivityOffering() method, and get MissingParameterException:  "+mpe.toString());
    			}catch (AlreadyExistsException aee){
    				System.out.println("call courseOfferingService.createActivityOffering() method, and get AlreadyExistsException:  "+aee.toString());
    			}catch (DataValidationErrorException dvee){
    				System.out.println("call courseOfferingService.createActivityOffering() method, and get DataValidationErrorException:  "+dvee.toString());
    			}catch (DoesNotExistException dnee){    		
    				System.out.println("call courseOfferingService.getActivityOfferingTypesForActivityType() method, and get DoesNotExistException:  "+dnee.toString());
    			}

    		}
    	}
		
    }
    
    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
        	CourseOfferingInfo newCourseOffering = (CourseOfferingInfo)getDataObject();   	
        	newCourseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        	newCourseOffering.setStateKey(LuiServiceConstants.LUI_OFFERED_STATE_KEY);
        }
        super.prepareForSave();
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
