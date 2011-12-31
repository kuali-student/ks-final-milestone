package org.kuali.student.enrollment.class2.acal.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.document.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

public class AcademicCalendarInfoMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
    public final static String ACADEMIC_CALENDAR_KEY_PREFIX = "kuali.academic.calendar.";
    public final static String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
    
    private transient AcademicCalendarService academicCalendarService;
  
    @Override
    public void saveDataObject() {
        AcademicCalendarInfo academicCalendarInfo = (AcademicCalendarInfo)getDataObject();
        // switched from key to id so no need to calculate
//        String academicCalendarKey = getAcademicCalendarId (academicCalendarInfo);
//        academicCalendarInfo.setId(academicCalendarKey);
        academicCalendarInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
//        System.out.println(">>>>credentialProgramTypeKey = "+academicCalendarInfo.getCredentialProgramTypeKey());
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {   
        		getAcademicCalendarService().createAcademicCalendar(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, academicCalendarInfo, ContextInfo.newInstance());
        	}
        	else {
        		getAcademicCalendarService().updateAcademicCalendar(academicCalendarInfo.getId(), academicCalendarInfo, ContextInfo.newInstance());
        	}
        }catch (DataValidationErrorException dvee){
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }catch (VersionMismatchException vme){
            
        } catch (ReadOnlyException roe) {
            
        }
        
        
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
    	ContextInfo context = ContextInfo.newInstance();
    	try{
    		return getAcademicCalendarService().getAcademicCalendar(dataObjectKeys.get("key"), context);
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }
        return null;
  
    }
    
  
    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
    	System.out.println (">>> in prepareForSave ");
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
        	AcademicCalendarInfo newAcal = (AcademicCalendarInfo)getDataObject();   	
        	newAcal.setTypeKey("kuali.atp.type.AcademicCalendar");
            newAcal.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        }
        super.prepareForSave();
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
//     *  AcademicCalendarId should be 
//     *  kuali.academic.calendar.<last part of credentialProgramTypeKey>.<yearOfStartDate>-<yearOfEndDate>
//     */
//    private String getAcademicCalendarId(AcademicCalendarInfo academicCalendarInfo){
//        String academicCalendarKey = new String (ACADEMIC_CALENDAR_KEY_PREFIX);
//        String credentialProgram;
//        
//        String credentialProgramTypeKey = academicCalendarInfo.getCredentialProgramTypeKey();
//        if (credentialProgramTypeKey.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
//        	credentialProgram  = credentialProgramTypeKey.substring(25);
//        }
//        else {
//        	credentialProgram = credentialProgramTypeKey;
//        }        
//        String yearOfStartDate = getYearFromDate(academicCalendarInfo.getStartDate());
//        String yearOfEndDate = getYearFromDate(academicCalendarInfo.getEndDate());
//        academicCalendarKey = academicCalendarKey.concat(credentialProgram.toLowerCase()+"."+yearOfStartDate+"-"+yearOfEndDate);
//        return academicCalendarKey;       
//        
//    }
//    
//    private String getYearFromDate(Date date){
//    	Calendar cal = Calendar.getInstance();
//    	cal.setTime(date);
//    	int year = cal.get(Calendar.YEAR);
//    	return new Integer(year).toString();
//    }
}
