package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.*;

//Core slice class.
@Deprecated
public class AcademicCalendarWrapperMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;

    private static final String DEFAULT_DOCUMENT_DESC_FOR_CREATING_ACADEMIC_CALENDAR =
                                                            "New academic calendar";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_EDITING_ACADEMIC_CALENDAR =
                                                            "Edit academic calendar";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_COPYING_ACADEMIC_CALENDAR =
                                                            "Copy academic calendar";

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
		ContextInfo context = new ContextInfo();
		
    	AcademicCalendarWrapper academicCalendarWrapper = (AcademicCalendarWrapper)getDataObject();

         
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {

            	
            	//First prepare and persist AcademicCalendarInfo
            	AcademicCalendarInfo academicCalendarInfo = academicCalendarWrapper.getAcademicCalendarInfo();

                if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
                    List<AttributeInfo> attributes = academicCalendarInfo.getAttributes();
                    List<AttributeInfo> newAttributes = new ArrayList();
                    for (AttributeInfo attribute:attributes) {
                        if (attribute.getId().equals("CredentialProgramType")) {
                            attribute.setId(null);
                        }
                        newAttributes.add(attribute);
                    }
                    academicCalendarInfo.setAttributes(newAttributes);
                }

                String academicCalendarKey = getAcademicCalendarKey (academicCalendarInfo);
                academicCalendarInfo.setId(academicCalendarKey);
                academicCalendarInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        		academicCalendarService.createAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
        		
                //If we can successfully create a AcademicCalendarInfo, prepare a list of TermWrapper and persist them one by one
                List<TermWrapper> termWrapperList = academicCalendarWrapper.getTermWrapperList();
                for(TermWrapper termWrapper:termWrapperList){
                	//prepare termInfo
                	TermInfo termInfo = termWrapper.getTermInfo();
                    String termName = getTermInfoName(termInfo);
                    termInfo.setName(termName);
                    termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
                    
                    //prepare classesMeetDates
                    KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
                    classesMeetDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    classesMeetDates.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
                    String classesMeetDatesKey = getKeyDateInfoKey(classesMeetDates);
                    classesMeetDates.setId(classesMeetDatesKey);
                    
                    //prepare registrationPeriod
                    KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
                    registrationPeriod.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    registrationPeriod.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
                    String registrationPeriodKey = getKeyDateInfoKey(registrationPeriod);
                    registrationPeriod.setId(registrationPeriodKey);
                    
                    //prepare dropPeriodEndsDate
                    KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
                    dropPeriodEndsDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    dropPeriodEndsDate.setTypeKey(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
                    String dropPeriodEndsDateKey = getKeyDateInfoKey(dropPeriodEndsDate);
                    dropPeriodEndsDate.setId(dropPeriodEndsDateKey);

                    //prepare finalExaminationsDates
                    KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
                    finalExaminationsDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    finalExaminationsDates.setTypeKey(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
                    String finalExaminationsDatesKey = getKeyDateInfoKey(finalExaminationsDates);
                    finalExaminationsDates.setId(finalExaminationsDatesKey);

                    //prepare gradesDueDate
                    KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();
                    gradesDueDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    gradesDueDate.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
                    String gradesDueDateKey = getKeyDateInfoKey(gradesDueDate);
                    gradesDueDate.setId(gradesDueDateKey);

                    //create Term and five Key Dates
                    termInfo = academicCalendarService.createTerm(termInfo.getTypeKey(), termInfo, context);
            		academicCalendarService.createKeyDate(termInfo.getId(), classesMeetDatesKey, classesMeetDates, context);
            		academicCalendarService.createKeyDate(termInfo.getId(), registrationPeriodKey, registrationPeriod, context);
            		academicCalendarService.createKeyDate(termInfo.getId(), dropPeriodEndsDateKey, dropPeriodEndsDate, context);
            		academicCalendarService.createKeyDate(termInfo.getId(), finalExaminationsDatesKey, finalExaminationsDates, context);
            		academicCalendarService.createKeyDate(termInfo.getId(), gradesDueDateKey, gradesDueDate, context);
            		
            		//associate a Term with an Acal
            		academicCalendarService.addTermToAcademicCalendar(academicCalendarKey, termInfo.getId(), context);
                }
        	}
        	else { 
        		//for MAINTENANCE_EDIT_ACTION
        		AcademicCalendarInfo academicCalendarInfo = academicCalendarWrapper.getAcademicCalendarInfo();
        		academicCalendarService.updateAcademicCalendar(academicCalendarInfo.getId(), academicCalendarInfo, context);
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
                    academicCalendarService.updateTerm(termInfo.getId(), termInfo, context);
            		academicCalendarService.updateKeyDate(classesMeetDates.getId(), classesMeetDates, context);
            		academicCalendarService.updateKeyDate(registrationPeriod.getId(), registrationPeriod, context);
            		academicCalendarService.updateKeyDate(dropPeriodEndsDate.getId(), dropPeriodEndsDate, context);
            		academicCalendarService.updateKeyDate(finalExaminationsDates.getId(), finalExaminationsDates, context);
            		academicCalendarService.updateKeyDate(gradesDueDate.getId(), gradesDueDate, context);
            		
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

        }catch (ReadOnlyException roe){
            
        }catch (DoesNotExistException dee){
            
        }catch (VersionMismatchException vme){
            
        }       
        
    }
    
    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
    	ContextInfo context = new ContextInfo();
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
    			List<KeyDateInfo> keyDateInfoList = academicCalendarService.getKeyDatesForTerm(termInfo.getId(), context);
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
    	System.out.println (">>> in prepareForSave for AcademicCalendarWrapperMaintainableImpl:");
//        if (getMaintenanceAction().equalsIgnoreCase(KNSConstants.MAINTENANCE_NEW_ACTION) ||
//            getMaintenanceAction().equals(KNSConstants.MAINTENANCE_COPY_ACTION)) {
//        	AcademicCalendarInfo newAcal = (AcademicCalendarInfo)getDataObject();
//        	newAcal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
//        	newAcal.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
//
//        	//Interesting, why typeKey and stateKey for TermInfo and KeyDateInfo are not required???
//
//        }
        AcademicCalendarWrapper newAcalWrapper = (AcademicCalendarWrapper)getDataObject();
        super.prepareForSave();
    }
    */

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterCopy
     */
    @Override
    public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_COPYING_ACADEMIC_CALENDAR);
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterEdit
     */
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_EDITING_ACADEMIC_CALENDAR);

    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterNew
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_CREATING_ACADEMIC_CALENDAR);

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
     *  AcademicCalendarKey should be 
     *  kuali.academic.calendar.<last part of credentialProgramTypeKey>.<yearOfStartDate>-<yearOfEndDate>
     */
    private String getAcademicCalendarKey(AcademicCalendarInfo academicCalendarInfo){
        String academicCalendarKey = new String (ACADEMIC_CALENDAR_KEY_PREFIX);
        String credentialProgram;
        
        String adminOrgId = academicCalendarInfo.getAdminOrgId();
        //TODO redo this string creation.  the switch from credential program type to adminOrgId caused some issues
        if (adminOrgId.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
        	credentialProgram  = adminOrgId.substring(25);
        }
        else {
        	credentialProgram = adminOrgId;
        }        
        String yearOfStartDate = getYearFromDate(academicCalendarInfo.getStartDate());
        String yearOfEndDate = getYearFromDate(academicCalendarInfo.getEndDate());
        academicCalendarKey = academicCalendarKey.concat(credentialProgram+"."+yearOfStartDate+"-"+yearOfEndDate);
        return academicCalendarKey.toLowerCase();       
        
    }
    
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities#HowtoCalculateKeysforAcademicCalendarEntities-MilestoneKeys
     *  KeyDateInfo Key should be 
     *  kuali.milestone.<The last part of the type key of the milestone selected (when split using ".") converted to lower case>.
     *  <The term key to which this milestone is expected to be connected with the "kuali." prefix removed>
     */
    private String getKeyDateInfoKey(KeyDateInfo keyDateInfo){
        String keyDateInfoKey = new String (KEY_DATE_INFO_KEY_PREFIX);
        
        String theKeyDateInfoType;
        
        String theKeyDateInfoTypeKey = keyDateInfo.getTypeKey();      
        if (theKeyDateInfoTypeKey.startsWith(MILESTONE_TYPE_KEY_PREFIX)){
        	theKeyDateInfoType = theKeyDateInfoTypeKey.substring(MILESTONE_TYPE_KEY_PREFIX.length());
        }
        else {
        	theKeyDateInfoType = theKeyDateInfoTypeKey;
        }        

        return keyDateInfoKey.toLowerCase();
        
    }

     /*
      * The value of the NAME of an TermInfo is constructed by the last part of the typeKey of a TermInfo
      * (such as Fall from kuali.atp.type.Fall) plus the year info from the startDate of a TermInfo.
      */
    private String getTermInfoName(TermInfo termInfo){
        String theType;
        String theTypeKey = termInfo.getTypeKey();
        if (theTypeKey.startsWith(TERM_TYPE_KEY_PREFIX)){
     	   theType = theTypeKey.substring(15);
        }
        else {
     	   theType = theTypeKey;
        }
        String yearOfStartDate = getYearFromDate(termInfo.getStartDate());
        String termName = new String (theType+" "+yearOfStartDate);
        return termName;
    }

    //A helper class
    private String getYearFromDate(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	return new Integer(year).toString();
    } 

}
