package org.kuali.student.enrollment.class2.acal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class TermInfoInquiryViewHelperServiceImpl extends KualiInquirableImpl {
    public final static String TERM_TYPE_KEY = "typeKey";
    public final static String TERM_KEY = "key";
 	private transient AcademicCalendarService academicCalendarService;
	
	 @Override
	 public TermInfo getDataObject(Map fieldValues) {
	    	TermInfo termInfo = null;
	    		    	
	    	String termKey = (String)fieldValues.get(TERM_KEY);
	    	ContextInfo context = ContextInfo.newInstance();
	    	try{
	    		termInfo = getAcademicCalendarService().getTerm(termKey, context);	    		
	    		return termInfo;
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
