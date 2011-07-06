package org.kuali.student.enrollment.class2.acal.service;

import java.util.Calendar;
import java.util.Date;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.util.KNSConstants;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class AcademicCalendarInfoMaintainableImpl extends KualiMaintainableImpl {
    public final static String ACADEMIC_CALENDAR_KEY_PREFIX = "kuali.academic.calendar.";
    public final static String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
    public final static String DEFAULT_VALUE_OF_ATP_STATE =" kuali.atp.state.Officia";

    
    private transient AcademicCalendarService academicCalendarService;
  
    @Override
    public void saveBusinessObject() {
        AcademicCalendarInfo academicCalendarInfo = (AcademicCalendarInfo)getDataObject();
        String academicCalendarKey = getAcademicCalendarKey (academicCalendarInfo);
        academicCalendarInfo.setKey(academicCalendarKey);
        academicCalendarInfo.setStateKey(DEFAULT_VALUE_OF_ATP_STATE);
        ContextInfo context = ContextInfo.newInstance();
        try{
        	if(getMaintenanceAction().equals(KNSConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KNSConstants.MAINTENANCE_COPY_ACTION)) {   
        		getAcademicCalendarService().createAcademicCalendar(academicCalendarKey, academicCalendarInfo, ContextInfo.newInstance());
        	}
        	else {
        		getAcademicCalendarService().updateAcademicCalendar(academicCalendarKey, academicCalendarInfo, ContextInfo.newInstance());
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
        return academicCalendarKey;       
        
    }
    
    private String getYearFromDate(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	return new Integer(year).toString();
    }
}
