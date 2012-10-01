package org.kuali.student.enrollment.class2.acal.service;

import org.apache.ojb.broker.metadata.ClassNotPersistenceCapableException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Core slice class.
@Deprecated
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
        String termKey = getTermInfoKey (termInfo);
        System.out.println(">>>termKey = "+termKey);
        termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        
        KeyDateInfo classesMeetDates = termWrapper.getClassesMeetDates();
        classesMeetDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        classesMeetDates.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
        String classesMeetDatesKey = getKeyDateInfoKey(classesMeetDates, termKey);
        classesMeetDates.setId(classesMeetDatesKey);
        
        KeyDateInfo registrationPeriod = termWrapper.getRegistrationPeriod();
        registrationPeriod.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        registrationPeriod.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        String registrationPeriodKey = getKeyDateInfoKey(registrationPeriod, termKey);
        registrationPeriod.setId(registrationPeriodKey);

        
  
        KeyDateInfo dropPeriodEndsDate = termWrapper.getDropPeriodEndsDate();
        dropPeriodEndsDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        dropPeriodEndsDate.setTypeKey(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
        String dropPeriodEndsDateKey = getKeyDateInfoKey(dropPeriodEndsDate, termKey);
        dropPeriodEndsDate.setId(dropPeriodEndsDateKey);


        KeyDateInfo finalExaminationsDates = termWrapper.getFinalExaminationsDates();
        finalExaminationsDates.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        finalExaminationsDates.setTypeKey(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
        String finalExaminationsDatesKey = getKeyDateInfoKey(finalExaminationsDates, termKey);
        finalExaminationsDates.setId(finalExaminationsDatesKey);

        
        KeyDateInfo gradesDueDate = termWrapper.getGradesDueDate();
        gradesDueDate.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        gradesDueDate.setTypeKey(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        String gradesDueDateKey = getKeyDateInfoKey(gradesDueDate, termKey);
        gradesDueDate.setId(gradesDueDateKey);
  
		academicCalendarService = getAcademicCalendarService();
		ContextInfo context = new ContextInfo();
	
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {          		
        		academicCalendarService.createTerm(termKey, termInfo, context);
        		academicCalendarService.createKeyDate(termKey, classesMeetDatesKey, classesMeetDates, context);
        		academicCalendarService.createKeyDate(termKey, registrationPeriodKey, registrationPeriod, context);
        		academicCalendarService.createKeyDate(termKey, dropPeriodEndsDateKey, dropPeriodEndsDate, context);
        		academicCalendarService.createKeyDate(termKey, finalExaminationsDatesKey, finalExaminationsDates, context);
        		academicCalendarService.createKeyDate(termKey, gradesDueDateKey, gradesDueDate, context);
       		
        	}
        	else {
        		getAcademicCalendarService().updateTerm(termKey, termInfo, new ContextInfo());
        		academicCalendarService.updateKeyDate(classesMeetDatesKey, classesMeetDates, context);
        		academicCalendarService.updateKeyDate(registrationPeriodKey, registrationPeriod, context);
        		academicCalendarService.updateKeyDate(dropPeriodEndsDateKey, dropPeriodEndsDate, context);
        		academicCalendarService.updateKeyDate(finalExaminationsDatesKey, finalExaminationsDates, context);
        		academicCalendarService.updateKeyDate(gradesDueDateKey, gradesDueDate, context);
        	}
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

       Object dataObject = null;
       try {
           // Since the dataObject is a wrapper class we need to build it and populate with the agenda bo.
           TermWrapper termWrapper = new TermWrapper();
           ContextInfo context = new ContextInfo();
           String termKey =  dataObjectKeys.get(TERM_KEY);

           try {
                TermInfo termInfo = getAcademicCalendarService().getTerm(termKey, context);
                // getLookupService().findObjectBySearch(((TermWrapper) getDataObject()).getTermInfo().getClass(), dataObjectKeys);

                if (KRADConstants.MAINTENANCE_COPY_ACTION.equals(getMaintenanceAction())) {
                    // If we don't clear the primary key and set the fieldsClearedOnCopy flag then the
                    // MaintenanceDocumentServiceImpl.processMaintenanceObjectForCopy() will try to locate the primary keys in
                    // an attempt to clear them which again would cause an exception due to the wrapper class.
                    termInfo.setId(null);
                    document.setFieldsClearedOnCopy(true);
                }
                termWrapper.setTermInfo(termInfo);

                List<KeyDateInfo> keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(termKey, new ContextInfo());

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
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get DoesNotExistException:  "+dnee.toString());
           }catch (InvalidParameterException ipe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get InvalidParameterException:  "+ipe.toString());
           }catch (MissingParameterException mpe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get MissingParameterException:  "+mpe.toString());
           }catch (OperationFailedException ofe){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get OperationFailedException:  "+ofe.toString());
           }catch (PermissionDeniedException pde){
                System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get PermissionDeniedException:  "+pde.toString());
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
