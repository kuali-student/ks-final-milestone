package org.kuali.student.enrollment.class2.acal.service;

import java.util.Calendar;
import java.util.Date;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.util.KNSConstants;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

public class TermWrapperMaintainableImpl extends KualiMaintainableImpl{
	private static final long serialVersionUID = 1L;	
	
    public final static String TERM_KEY_PREFIX = "kuali.term.";
    //Type keys for term are "kuali.atp.type.Fall", "kuali.atp.type.Winter", "kuali.atp.type.Spring", or "kuali.atp.type.Summer".
    public final static String TERM_TYPE_KEY_PREFIX = "kuali.atp.type.";    
    public final static String MILESTONE_TYPE_KEY_PREFIX = "kuali.atp.milestone.";    
    public final static String KEY_DATE_INFO_KEY_PREFIX = "kuali.milestone.";

    
    private transient AcademicCalendarService academicCalendarService;
	
    @Override
    public void saveBusinessObject() {
    	System.out.println(">>In TermWrapperMaintainableImpl.saveBusinessObject()");
        TermWrapper termWrapper = (TermWrapper)getDataObject();
        TermInfo termInfo = termWrapper.getTermInfo();
        String termKey = getTermInfoKey (termInfo);
        System.out.println(">>>termKey = "+termKey);
        termInfo.setKey(termKey);
        termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        
        KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
        classesMeetDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        classesMeetDates.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
        String classesMeetDatesKey = getKeyDateInfoKey(classesMeetDates, termKey);
        classesMeetDates.setKey(classesMeetDatesKey);
        
        KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
        registrationPeriod.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        registrationPeriod.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        String registrationPeriodKey = getKeyDateInfoKey(registrationPeriod, termKey);
        registrationPeriod.setKey(registrationPeriodKey);

        
  
        KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
        dropPeriodEndsDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        dropPeriodEndsDate.setTypeKey(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
        String dropPeriodEndsDateKey = getKeyDateInfoKey(dropPeriodEndsDate, termKey);
        dropPeriodEndsDate.setKey(dropPeriodEndsDateKey);


        KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
        finalExaminationsDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        finalExaminationsDates.setTypeKey(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
        String finalExaminationsDatesKey = getKeyDateInfoKey(finalExaminationsDates, termKey);
        finalExaminationsDates.setKey(finalExaminationsDatesKey);

        
        KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();
        gradesDueDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        gradesDueDate.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        String gradesDueDateKey = getKeyDateInfoKey(gradesDueDate, termKey);
        gradesDueDate.setKey(gradesDueDateKey);
  
		academicCalendarService = getAcademicCalendarService();
		ContextInfo context = ContextInfo.newInstance();
	
        try{
        	if(getMaintenanceAction().equals(KNSConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KNSConstants.MAINTENANCE_COPY_ACTION)) {          		
        		academicCalendarService.createTerm(termKey, termInfo, context);
        		academicCalendarService.createKeyDateForTerm(termKey, classesMeetDatesKey, classesMeetDates, context);
        		academicCalendarService.createKeyDateForTerm(termKey, registrationPeriodKey, registrationPeriod, context);
        		academicCalendarService.createKeyDateForTerm(termKey, dropPeriodEndsDateKey, dropPeriodEndsDate, context);
        		academicCalendarService.createKeyDateForTerm(termKey, finalExaminationsDatesKey, finalExaminationsDates, context);
        		academicCalendarService.createKeyDateForTerm(termKey, gradesDueDateKey, gradesDueDate, context);
       		
        	}
        	else {
        		getAcademicCalendarService().updateTerm(termKey, termInfo, ContextInfo.newInstance());
        		academicCalendarService.updateKeyDate(classesMeetDatesKey, classesMeetDates, context);
        		academicCalendarService.updateKeyDate(registrationPeriodKey, registrationPeriod, context);
        		academicCalendarService.updateKeyDate(dropPeriodEndsDateKey, dropPeriodEndsDate, context);
        		academicCalendarService.updateKeyDate(finalExaminationsDatesKey, finalExaminationsDates, context);
        		academicCalendarService.updateKeyDate(gradesDueDateKey, gradesDueDate, context);        		
        	}
        }catch (AlreadyExistsException aee){
            
        }catch (DataValidationErrorException dvee){
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }catch (VersionMismatchException vme){
            
        }       
        
    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }

       return academicCalendarService;
   }
    
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities
     *  Term Keys should be 
     *  kuali.term.<yearOfStartDate>-<yearOfEndDate>.
     *  <The last part of the type key of the term selected (when split using ".") converted to lower case>
     */
    private String getTermInfoKey(TermInfo termInfo){
        String termKey = new String (TERM_KEY_PREFIX);
        String theType;
        
        String theTypeKey = termInfo.getTypeKey();      
        if (theTypeKey.startsWith(TERM_TYPE_KEY_PREFIX)){
     	   theType = theTypeKey.substring(15);
        }
        else {
     	   theType = theTypeKey;
        }        
        String yearOfStartDate = getYearFromDate(termInfo.getStartDate());
        String yearOfEndDate = getYearFromDate(termInfo.getEndDate());
        termKey = termKey.concat("."+yearOfStartDate+"-"+yearOfEndDate+"."+theType.toLowerCase());
        return termKey;       
        
    }
    
    private String getYearFromDate(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	return new Integer(year).toString();
    }
	
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities#HowtoCalculateKeysforAcademicCalendarEntities-MilestoneKeys
     *  KeyDateInfo Key should be 
     *  kuali.milestone.<The last part of the type key of the milestone selected (when split using ".") converted to lower case>.
     *  <The term key to which this milestone is expected to be connected with the "kuali." prefix removed>
     */
    private String getKeyDateInfoKey(KeyDateInfo keyDateInfo, String termKey){
        String keyDateInfoKey = new String (KEY_DATE_INFO_KEY_PREFIX);
        
        String theKeyDateInfoType;
        
        String theKeyDateInfoTypeKey = keyDateInfo.getTypeKey();      
        if (theKeyDateInfoTypeKey.startsWith(MILESTONE_TYPE_KEY_PREFIX)){
        	theKeyDateInfoType = theKeyDateInfoTypeKey.substring(MILESTONE_TYPE_KEY_PREFIX.length());
        }
        else {
        	theKeyDateInfoType = theKeyDateInfoTypeKey;
        }        

        keyDateInfoKey = keyDateInfoKey.concat("."+theKeyDateInfoType.toLowerCase()+"."+termKey.substring(6));
        return keyDateInfoKey;       
        
    }

}
