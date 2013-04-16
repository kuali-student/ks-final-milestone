package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.Map;

//Core slice class.
@Deprecated
public class AcademicCalendarInfoInquiryViewHelperServiceImpl extends InquirableImpl {
	 public final static String ACADEMIC_CALENDAR_KEY = "key";
	 private transient AcademicCalendarService academicCalendarService;
	 
    @Override
    public AcademicCalendarInfo retrieveDataObject(Map<String, String> parameters) {
    	AcademicCalendarInfo academicCalendarInfo = null;
    	
    	String academicCalendarKey = parameters.get(ACADEMIC_CALENDAR_KEY);
    	ContextInfo context = new ContextInfo();
    	try{
    		academicCalendarInfo = getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context);
    		return academicCalendarInfo;
    	}catch (DoesNotExistException dnee){
    		
    	}catch (InvalidParameterException ipe){
    		    		
    	}catch (MissingParameterException mpe){
    		
    	}catch (OperationFailedException ofe){
    		
    	}catch (PermissionDeniedException pde){
    		
    	}
    	return null;

    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }

       return academicCalendarService;
   }


}

