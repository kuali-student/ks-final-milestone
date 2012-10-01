package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.Map;

//Core slice class.
@Deprecated
public class TermInfoInquiryViewHelperServiceImpl extends InquirableImpl {
    public final static String TERM_TYPE_KEY = "typeKey";
    public final static String TERM_KEY = "key";
 	private transient AcademicCalendarService academicCalendarService;
	
	 @Override
	 public TermInfo retrieveDataObject(Map<String, String> parameters) {
	    	TermInfo termInfo = null;
	    		    	
	    	String termKey = parameters.get(TERM_KEY);
	    	ContextInfo context = new ContextInfo();
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
