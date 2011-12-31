package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.document.MaintenanceDocument;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.Context;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ojb.broker.metadata.ClassNotPersistenceCapableException;

public class TermWrapperMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;

     public final static String TERM_KEY = "key";
	
    public final static String TERM_KEY_PREFIX = "kuali.term.";
    //Type keys for term are "kuali.atp.type.Fall", "kuali.atp.type.Winter", "kuali.atp.type.Spring", or "kuali.atp.type.Summer".
    public final static String TERM_TYPE_KEY_PREFIX = "kuali.atp.type.";    
    public final static String MILESTONE_TYPE_KEY_PREFIX = "kuali.atp.milestone.";    
    public final static String KEY_DATE_INFO_KEY_PREFIX = "kuali.milestone.";

    
    private transient AcademicCalendarService academicCalendarService;
	
    @Override
    public void saveDataObject() {
    	System.out.println(">>In TermWrapperMaintainableImpl.saveDataObject()");
        TermWrapper termWrapper = (TermWrapper)getDataObject();
        TermInfo termInfo = termWrapper.getTermInfo();
//        String termId = getTermInfoKey (termInfo);
//        System.out.println(">>>termId = "+termId);
//        termInfo.setKey(termId);
        termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        
        KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
        classesMeetDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        classesMeetDates.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
//        String classesMeetDatesKey = getKeyDateInfoKey(classesMeetDates, termId);
//        classesMeetDates.setKey(classesMeetDatesKey);
        
        KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
        registrationPeriod.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        registrationPeriod.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
//        String registrationPeriodKey = getKeyDateInfoKey(registrationPeriod, termId);
//        registrationPeriod.setKey(registrationPeriodKey);

        
  
        KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
        dropPeriodEndsDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        dropPeriodEndsDate.setTypeKey(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
//        String dropPeriodEndsDateKey = getKeyDateInfoKey(dropPeriodEndsDate, termId);
//        dropPeriodEndsDate.setKey(dropPeriodEndsDateKey);


        KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
        finalExaminationsDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        finalExaminationsDates.setTypeKey(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
//        String finalExaminationsDatesKey = getKeyDateInfoKey(finalExaminationsDates, termId);
//        finalExaminationsDates.setKey(finalExaminationsDatesKey);

        
        KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();
        gradesDueDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        gradesDueDate.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
//        String gradesDueDateKey = getKeyDateInfoKey(gradesDueDate, termId);
//        gradesDueDate.setKey(gradesDueDateKey);
  
		academicCalendarService = getAcademicCalendarService();
		ContextInfo context = ContextInfo.newInstance();
	
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {          		
        		termInfo = academicCalendarService.createTerm(termInfo.getTypeKey(), termInfo, context);
        		academicCalendarService.createKeyDate(termInfo.getId(), classesMeetDates.getTypeKey(), classesMeetDates, context);
        		academicCalendarService.createKeyDate(termInfo.getId(), registrationPeriod.getTypeKey(), registrationPeriod, context);
        		academicCalendarService.createKeyDate(termInfo.getId(), dropPeriodEndsDate.getTypeKey(), dropPeriodEndsDate, context);
        		academicCalendarService.createKeyDate(termInfo.getId(), finalExaminationsDates.getTypeKey(), finalExaminationsDates, context);
        		academicCalendarService.createKeyDate(termInfo.getId(), gradesDueDate.getTypeKey(), gradesDueDate, context);
       		
        	}
        	else {
        		termInfo = getAcademicCalendarService().updateTerm(termInfo.getId(), termInfo, ContextInfo.newInstance());
        		academicCalendarService.updateKeyDate(classesMeetDates.getId(), classesMeetDates, context);
        		academicCalendarService.updateKeyDate(registrationPeriod.getId(), registrationPeriod, context);
        		academicCalendarService.updateKeyDate(dropPeriodEndsDate.getId(), dropPeriodEndsDate, context);
        		academicCalendarService.updateKeyDate(finalExaminationsDates.getId(), finalExaminationsDates, context);
        		academicCalendarService.updateKeyDate(gradesDueDate.getId(), gradesDueDate, context);        		
        	}
        }catch (ReadOnlyException roe){

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

       Object dataObject = null;
       try {
           // Since the dataObject is a wrapper class we need to build it and populate with the agenda bo.
           TermWrapper termWrapper = new TermWrapper();
           ContextInfo context = ContextInfo.newInstance();
           String termId =  dataObjectKeys.get(TERM_KEY);

           try {
                TermInfo termInfo = getAcademicCalendarService().getTerm(termId, context);
                // getLookupService().findObjectBySearch(((TermWrapper) getDataObject()).getTermInfo().getClass(), dataObjectKeys);

                if (KRADConstants.MAINTENANCE_COPY_ACTION.equals(getMaintenanceAction())) {
                    // If we don't clear the primary key and set the fieldsClearedOnCopy flag then the
                    // MaintenanceDocumentServiceImpl.processMaintenanceObjectForCopy() will try to locate the primary keys in
                    // an attempt to clear them which again would cause an exception due to the wrapper class.
                    termInfo.setId(null);
                    document.setFieldsClearedOnCopy(true);
                }
                termWrapper.setTermInfo(termInfo);

                List<KeyDateInfo> keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(termId, ContextInfo.newInstance());

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
    	   }catch (DoesNotExistException dnee){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get DoesNotExistException:  "+dnee.toString());
           }catch (InvalidParameterException ipe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get InvalidParameterException:  "+ipe.toString());
           }catch (MissingParameterException mpe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get MissingParameterException:  "+mpe.toString());
           }catch (OperationFailedException ofe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get OperationFailedException:  "+ofe.toString());
           }catch (PermissionDeniedException pde){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get PermissionDeniedException:  "+pde.toString());
           }
           dataObject = termWrapper;

       } catch (ClassNotPersistenceCapableException ex) {
           if (!document.getOldMaintainableObject().isExternalBusinessObject()) {
               throw new RuntimeException("Data Object Class: " + getDataObjectClass() +
                       " is not persistable and is not externalizable - configuration error");
           }
           // otherwise, let fall through
       }

       return dataObject;

    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
       if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }
       return academicCalendarService;
   }
    
//    /*
//     *  Based on Norm's suggestion at 
//     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities
//     *  Term ids should be 
//     *  kuali.term.<yearOfStartDate>-<yearOfEndDate>.
//     *  <The last part of the type key of the term selected (when split using ".") converted to lower case>
//     */
//    private String getTermInfoKey(TermInfo termInfo){
//        String termId = new String (TERM_KEY_PREFIX);
//        String theType;
//        
//        String theTypeKey = termInfo.getTypeKey();      
//        if (theTypeKey.startsWith(TERM_TYPE_KEY_PREFIX)){
//     	   theType = theTypeKey.substring(15);
//        }
//        else {
//     	   theType = theTypeKey;
//        }        
//        String yearOfStartDate = getYearFromDate(termInfo.getStartDate());
//        String yearOfEndDate = getYearFromDate(termInfo.getEndDate());
//        termId = termId.concat("."+yearOfStartDate+"-"+yearOfEndDate+"."+theType.toLowerCase());
//        return termId;       
//        
//    }
//    
//    private String getYearFromDate(Date date){
//    	Calendar cal = Calendar.getInstance();
//    	cal.setTime(date);
//    	int year = cal.get(Calendar.YEAR);
//    	return new Integer(year).toString();
//    }
//	
//    /*
//     *  Based on Norm's suggestion at 
//     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities#HowtoCalculateKeysforAcademicCalendarEntities-MilestoneIds
//     *  KeyDateInfo Key should be 
//     *  kuali.milestone.<The last part of the type key of the milestone selected (when split using ".") converted to lower case>.
//     *  <The term id to which this milestone is expected to be connected with the "kuali." prefix removed>
//     */
//    private String getKeyDateInfoKey(KeyDateInfo keyDateInfo, String termId){
//        String keyDateInfoKey = new String (KEY_DATE_INFO_KEY_PREFIX);
//        
//        String theKeyDateInfoType;
//        
//        String theKeyDateInfoTypeKey = keyDateInfo.getTypeKey();      
//        if (theKeyDateInfoTypeKey.startsWith(MILESTONE_TYPE_KEY_PREFIX)){
//        	theKeyDateInfoType = theKeyDateInfoTypeKey.substring(MILESTONE_TYPE_KEY_PREFIX.length());
//        }
//        else {
//        	theKeyDateInfoType = theKeyDateInfoTypeKey;
//        }        
//
//        keyDateInfoKey = keyDateInfoKey.concat("."+theKeyDateInfoType.toLowerCase()+"."+termId.substring(6));
//        return keyDateInfoKey;       
//        
//    }

}
