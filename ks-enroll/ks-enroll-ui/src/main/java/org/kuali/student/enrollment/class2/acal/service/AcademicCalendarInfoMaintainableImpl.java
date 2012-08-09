package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

//Core slice class.
@Deprecated
public class AcademicCalendarInfoMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
    public final static String ACADEMIC_CALENDAR_KEY_PREFIX = "kuali.academic.calendar.";
    public final static String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
    
    private transient AcademicCalendarService academicCalendarService;
  
    @Override
    public void saveDataObject() {
        AcademicCalendarInfo academicCalendarInfo = (AcademicCalendarInfo)getDataObject();
        String academicCalendarKey = getAcademicCalendarKey (academicCalendarInfo);
        academicCalendarInfo.setId(academicCalendarKey);
        academicCalendarInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        System.out.println(">>>>adminOrgId = "+academicCalendarInfo.getAdminOrgId());
        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {   
        		getAcademicCalendarService().createAcademicCalendar(academicCalendarKey, academicCalendarInfo, new ContextInfo());
        	}
        	else {
        		getAcademicCalendarService().updateAcademicCalendar(academicCalendarKey, academicCalendarInfo, new ContextInfo());
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
    	ContextInfo context = new ContextInfo();
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
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities
     *  AcademicCalendarKey should be 
     *  kuali.academic.calendar.<last part of credentialProgramTypeKey>.<yearOfStartDate>-<yearOfEndDate>
     */
    private String getAcademicCalendarKey(AcademicCalendarInfo academicCalendarInfo){
        String academicCalendarKey = new String (ACADEMIC_CALENDAR_KEY_PREFIX);
        String adminOrg;
        
        String adminOrgId = academicCalendarInfo.getAdminOrgId();
        if (adminOrgId.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
            adminOrg  = adminOrgId.substring(25);
        }
        else {
            adminOrg = adminOrgId;
        }        
        String yearOfStartDate = getYearFromDate(academicCalendarInfo.getStartDate());
        String yearOfEndDate = getYearFromDate(academicCalendarInfo.getEndDate());
        academicCalendarKey = academicCalendarKey.concat(adminOrg.toLowerCase()+"."+yearOfStartDate+"-"+yearOfEndDate);
        return academicCalendarKey;       
        
    }
    
    private String getYearFromDate(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	return new Integer(year).toString();
    }
}
