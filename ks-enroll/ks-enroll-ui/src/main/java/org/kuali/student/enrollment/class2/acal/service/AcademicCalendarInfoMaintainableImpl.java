package org.kuali.student.enrollment.class2.acal.service;

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

    
    private transient AcademicCalendarService academicCalendarService;
  
    @Override
    public void saveBusinessObject() {
        AcademicCalendarInfo academicCalendarInfo = (AcademicCalendarInfo)getDataObject();
        String academicCalendarKey = getAcademicCalendarKey (academicCalendarInfo);
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
            academicCalendarService = GlobalResourceLoader.getService("AcademicCalendarService");
        }
        return this.academicCalendarService;
    }
    /*
     *  From Larry:
     *  AcademicCalendarKey should be 
     *  kuali.academic.calendar.<credentialProgramTypeKey>.<yearOfStartDate>
     */
    private String getAcademicCalendarKey(AcademicCalendarInfo academicCalendarInfo){
        String academicCalendarKey = new String (ACADEMIC_CALENDAR_KEY_PREFIX);
        
        String credentialProgramTypeKey = academicCalendarInfo.getCredentialProgramTypeKey();
        String yearOfStartDate = new Integer(academicCalendarInfo.getStartDate().getYear()).toString();
        academicCalendarKey.concat(credentialProgramTypeKey+"."+yearOfStartDate);
        System.out.println(">>>academicCalendarKey = "+ academicCalendarKey);
        return academicCalendarKey;       
        
    }
}
