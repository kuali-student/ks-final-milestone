package org.kuali.student.enrollment.class2.acal.service;

import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class AcademicCalendarInfoInquiryViewHelperServiceImpl extends KualiInquirableImpl {
	 public final static String ACADEMIC_CALENDAR_KEY = "key";
	 private transient AcademicCalendarService academicCalendarService;
	 
    @Override
    public AcademicCalendarInfo retrieveDataObject(Map fieldValues) {
    	AcademicCalendarInfo academicCalendarInfo = null;
    	
    	String academicCalendarKey = (String)fieldValues.get(ACADEMIC_CALENDAR_KEY);
    	ContextInfo context = ContextInfo.newInstance();
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

