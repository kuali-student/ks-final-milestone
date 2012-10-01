package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

//Core slice class.
@Deprecated
public class TermInfoMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
    public final static String TERM_KEY_PREFIX = "kuali.term.";
    public final static String TYPE_KEY_PREFIX = "kuali.atp.type.";
    public final static String DEFAULT_VALUE_OF_ATP_STATE ="kuali.atp.state.Official";
    
    private transient AcademicCalendarService academicCalendarService;

    @Override
    public void saveDataObject() {
    	System.out.println(">>In TermInfoMaintainableImpl.saveDataObject()");
        TermInfo termInfo = (TermInfo)getDataObject();
        String termKey = getTermInfoKey (termInfo);
        System.out.println(">>>termKey = "+termKey);
        termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);

        try{
        	if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {   
        		getAcademicCalendarService().createTerm(termKey, termInfo, new ContextInfo());
        	}
        	else {
        		getAcademicCalendarService().updateTerm(termKey, termInfo, new ContextInfo());
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
    		return getAcademicCalendarService().getTerm(dataObjectKeys.get("key"), context);
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }
        return null;
  
    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }

       return academicCalendarService;
   }
    
    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
    	System.out.println (">>> in prepareForSave ");
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
        	TermInfo newTerm = (TermInfo)getDataObject();   	
        	newTerm.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        }
        super.prepareForSave();
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
       if (theTypeKey.startsWith(TYPE_KEY_PREFIX)){
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

}
