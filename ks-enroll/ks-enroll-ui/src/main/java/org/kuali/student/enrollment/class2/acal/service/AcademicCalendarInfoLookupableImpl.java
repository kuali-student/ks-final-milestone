package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Core slice class.
@Deprecated
public class AcademicCalendarInfoLookupableImpl extends LookupableImpl {
	    public final static String CREDENTIAL_PROGRAM_TYPE_KEY = "credentialProgramTypeKey";
	    public final static String ACADEMIC_CALENDAR_KEY = "key";
	 	private transient AcademicCalendarService academicCalendarService;
	 	

	    @Override
	    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
	    	/*
	    	String credentialProgramTypeKey = fieldValues.get(CREDENTIAL_PROGRAM_TYPE_KEY);
	    	ContextInfo context = ContextInfo.newInstance();
	    	try{
	    		return getAcademicCalendarService().getAcademicCalendarsByCredentialProgramType(credentialProgramTypeKey, context);
	    	}catch (InvalidParameterException ipe){
	    		
	    	}catch (MissingParameterException mpe){
	    		
	    	}catch (OperationFailedException ofe){
	    		
	    	}catch (PermissionDeniedException pde){
	    		
	    	}
	    	return null;
	    	*/
	    	AcademicCalendarInfo academicCalendarInfo = null;
	    	List<AcademicCalendarInfo> academicCalendarInfoList = new ArrayList<AcademicCalendarInfo>();
	    	
	    	String academicCalendarKey = fieldValues.get(ACADEMIC_CALENDAR_KEY);
	    	ContextInfo context = new ContextInfo();
	    	try{
	    		academicCalendarInfo = getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context);
	    		academicCalendarInfoList.add(academicCalendarInfo);
	    		return academicCalendarInfoList;
	    	}catch (DoesNotExistException dnee){
	    		System.out.println("call getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context), and get DoesNotExistException:  "+dnee.toString());
	    	}catch (InvalidParameterException ipe){
	    	    System.out.println("call getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context), and get InvalidParameterException:  "+ipe.toString());
	    	}catch (MissingParameterException mpe){
                System.out.println("call getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context), and get MissingParameterException:  "+mpe.toString());
	    	}catch (OperationFailedException ofe){
                System.out.println("call getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context), and get OperationFailedException:  "+ofe.toString());
	    	}catch (PermissionDeniedException pde){
                System.out.println("call getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context), and get PermissionDeniedException:  "+pde.toString());
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
