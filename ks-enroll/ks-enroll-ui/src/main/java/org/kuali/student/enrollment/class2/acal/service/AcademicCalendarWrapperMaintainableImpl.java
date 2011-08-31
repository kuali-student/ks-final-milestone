package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.document.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.*;

public class AcademicCalendarWrapperMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
    public final static String ACADEMIC_CALENDAR_KEY_PREFIX = "kuali.academic.calendar.";
    public final static String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
    
    public final static String TERM_KEY_PREFIX = "kuali.term.";
    //Type keys for term are "kuali.atp.type.Fall", "kuali.atp.type.Winter", "kuali.atp.type.Spring", or "kuali.atp.type.Summer".
    public final static String TERM_TYPE_KEY_PREFIX = "kuali.atp.type.";    
    public final static String MILESTONE_TYPE_KEY_PREFIX = "kuali.atp.milestone.";    
    public final static String KEY_DATE_INFO_KEY_PREFIX = "kuali.milestone.";
    
    private transient AcademicCalendarService academicCalendarService;
    
    
    @Override
    public void saveDataObject() {
		academicCalendarService = getAcademicCalendarService();
		ContextInfo context = ContextInfo.newInstance();
		
    	AcademicCalendarWrapper academicCalendarWrapper = (AcademicCalendarWrapper)getDataObject();

         
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) { 
            	
            	//First prepare and persist AcademicCalendarInfo
            	AcademicCalendarInfo academicCalendarInfo = academicCalendarWrapper.getAcademicCalendarInfo();
                String academicCalendarKey = getAcademicCalendarKey (academicCalendarInfo);
                academicCalendarInfo.setKey(academicCalendarKey);
                academicCalendarInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
                System.out.println(">>>>credentialProgramTypeKey = "+academicCalendarInfo.getCredentialProgramTypeKey());
        		academicCalendarService.createAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
        		
                //If we can successfully create a AcademicCalendarInfo, prepare a list of TermWrapper and persist them one by one
                List<TermWrapper> termWrapperList = academicCalendarWrapper.getTermWrapperList();
                for(TermWrapper termWrapper:termWrapperList){
                	//prepare termInfo
                	TermInfo termInfo = termWrapper.getTermInfo();
                    String termKey = getTermInfoKey (termInfo);
                    System.out.println(">>>termKey = "+termKey);
                    termInfo.setKey(termKey);
                    termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
                    
                    //prepare classesMeetDates
                    KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
                    classesMeetDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    classesMeetDates.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
                    String classesMeetDatesKey = getKeyDateInfoKey(classesMeetDates, termKey);
                    classesMeetDates.setKey(classesMeetDatesKey);
                    
                    //prepare registrationPeriod
                    KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
                    registrationPeriod.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    registrationPeriod.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
                    String registrationPeriodKey = getKeyDateInfoKey(registrationPeriod, termKey);
                    registrationPeriod.setKey(registrationPeriodKey);
                    
                    //prepare dropPeriodEndsDate
                    KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
                    dropPeriodEndsDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    dropPeriodEndsDate.setTypeKey(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
                    String dropPeriodEndsDateKey = getKeyDateInfoKey(dropPeriodEndsDate, termKey);
                    dropPeriodEndsDate.setKey(dropPeriodEndsDateKey);

                    //prepare finalExaminationsDates
                    KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
                    finalExaminationsDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    finalExaminationsDates.setTypeKey(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
                    String finalExaminationsDatesKey = getKeyDateInfoKey(finalExaminationsDates, termKey);
                    finalExaminationsDates.setKey(finalExaminationsDatesKey);

                    //prepare gradesDueDate
                    KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();
                    gradesDueDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    gradesDueDate.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
                    String gradesDueDateKey = getKeyDateInfoKey(gradesDueDate, termKey);
                    gradesDueDate.setKey(gradesDueDateKey);

                    //create Term and five Key Dates
                    academicCalendarService.createTerm(termKey, termInfo, context);
            		academicCalendarService.createKeyDateForTerm(termKey, classesMeetDatesKey, classesMeetDates, context);
            		academicCalendarService.createKeyDateForTerm(termKey, registrationPeriodKey, registrationPeriod, context);
            		academicCalendarService.createKeyDateForTerm(termKey, dropPeriodEndsDateKey, dropPeriodEndsDate, context);
            		academicCalendarService.createKeyDateForTerm(termKey, finalExaminationsDatesKey, finalExaminationsDates, context);
            		academicCalendarService.createKeyDateForTerm(termKey, gradesDueDateKey, gradesDueDate, context);
            		
            		//associate a Term with an Acal
            		academicCalendarService.addTermToAcademicCalendar(academicCalendarKey, termKey, context);            		
                }
        	}
        	else { 
        		//for MAINTENANCE_EDIT_ACTION
        		AcademicCalendarInfo academicCalendarInfo = academicCalendarWrapper.getAcademicCalendarInfo();
        		academicCalendarService.updateAcademicCalendar(academicCalendarInfo.getKey(), academicCalendarInfo, context);
                //If we can successfully update a AcademicCalendarInfo, update a list of TermWrapper.
                List<TermWrapper> termWrapperList = academicCalendarWrapper.getTermWrapperList();
                for(TermWrapper termWrapper:termWrapperList){
              
                	TermInfo termInfo = termWrapper.getTermInfo();              
                    KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
                    KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
                    KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
                    KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
                    KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();

                    //update Term and five Key Dates
                    academicCalendarService.updateTerm(termInfo.getKey(), termInfo, context);             
            		academicCalendarService.updateKeyDate(classesMeetDates.getKey(), classesMeetDates, context);            		
            		academicCalendarService.updateKeyDate(registrationPeriod.getKey(), registrationPeriod, context);
            		academicCalendarService.updateKeyDate(dropPeriodEndsDate.getKey(), dropPeriodEndsDate, context);
            		academicCalendarService.updateKeyDate(finalExaminationsDates.getKey(), finalExaminationsDates, context);
            		academicCalendarService.updateKeyDate(gradesDueDate.getKey(), gradesDueDate, context);
            		
            		//TODO Need to handle new added Terms plus keyDates
                	
                }
        	}
        }catch (AlreadyExistsException aee){
            //re-throw it as Runtime exception
        	//check how KEW handle exception --
        }catch (DataValidationErrorException dvee){
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }catch (VersionMismatchException vme){
            
        }       
        
    }
    
    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
    	ContextInfo context = ContextInfo.newInstance();
    	academicCalendarService = getAcademicCalendarService();
    	AcademicCalendarWrapper academicCalendarWrapper = new AcademicCalendarWrapper();
    	try{
    		//need to retrieve AcademicCalendarInfo, all TermInfo and all KeyDateInfo to form the AcademicCalendarWrapper.
    		AcademicCalendarInfo academicCalendarInfo = academicCalendarService.getAcademicCalendar(dataObjectKeys.get("key"), context);
    		academicCalendarWrapper.setAcademicCalendarInfo(academicCalendarInfo);
    		
    		List<TermWrapper> termWrapperList = new ArrayList<TermWrapper>();
    		List<TermInfo> termInfoList = academicCalendarService.getTermsForAcademicCalendar(dataObjectKeys.get("key"), context);
    		for (TermInfo termInfo:termInfoList){
    			TermWrapper termWrapper = new TermWrapper();
    			termWrapper.setTermInfo(termInfo);
    			List<KeyDateInfo> keyDateInfoList = academicCalendarService.getKeyDatesForTerm(termInfo.getKey(), context);
    			for (KeyDateInfo keyDateInfo : keyDateInfoList){
    				if(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setClassesMeetDates(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setRegistrationPeriod(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setDropPeriodEndsDate(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setFinalExaminationsDates(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setGradesDueDate(keyDateInfo);
    				}
    					
    			}
    			termWrapperList.add(termWrapper);    			
    		}
    		academicCalendarWrapper.setTermWrapperList(termWrapperList);
    		return academicCalendarWrapper;
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }
        return null;
  
    }
    
  
    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     
    @Override
    public void prepareForSave() {
    	System.out.println (">>> in prepareForSave ");
        if (getMaintenanceAction().equalsIgnoreCase(KNSConstants.MAINTENANCE_NEW_ACTION) ||
            getMaintenanceAction().equals(KNSConstants.MAINTENANCE_COPY_ACTION)) {
        	AcademicCalendarInfo newAcal = (AcademicCalendarInfo)getDataObject();   	
        	newAcal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        	newAcal.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        	
        	//Interesting, why typeKey and stateKey for TermInfo and KeyDateInfo are not required???
        	
        }
        super.prepareForSave();
    }
    */
    
    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
        	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
        }

        return academicCalendarService;
    }
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities
     *  AcademicCalendarKey should be 
     *  kuali.academic.calendar.<last part of credentialProgramTypeKey>.<yearOfStartDate>-<yearOfEndDate>
     */
    private String getAcademicCalendarKey(AcademicCalendarInfo academicCalendarInfo){
        String academicCalendarKey = new String (ACADEMIC_CALENDAR_KEY_PREFIX);
        String credentialProgram;
        
        String credentialProgramTypeKey = academicCalendarInfo.getCredentialProgramTypeKey();
        if (credentialProgramTypeKey.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
        	credentialProgram  = credentialProgramTypeKey.substring(25);
        }
        else {
        	credentialProgram = credentialProgramTypeKey;
        }        
        String yearOfStartDate = getYearFromDate(academicCalendarInfo.getStartDate());
        String yearOfEndDate = getYearFromDate(academicCalendarInfo.getEndDate());
        academicCalendarKey = academicCalendarKey.concat(credentialProgram+"."+yearOfStartDate+"-"+yearOfEndDate);
        return academicCalendarKey.toLowerCase();       
        
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
        termKey = termKey.concat(yearOfStartDate+"-"+yearOfEndDate+"."+theType);
        return termKey.toLowerCase();       
        
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

        keyDateInfoKey = keyDateInfoKey.concat(theKeyDateInfoType.toLowerCase()+"."+termKey.substring(6));
        return keyDateInfoKey.toLowerCase();       
        
    }

    //A helper class
    private String getYearFromDate(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	return new Integer(year).toString();
    } 

}
