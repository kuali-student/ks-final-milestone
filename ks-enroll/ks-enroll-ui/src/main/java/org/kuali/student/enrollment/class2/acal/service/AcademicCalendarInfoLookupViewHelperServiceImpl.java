package org.kuali.student.enrollment.class2.acal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.uif.service.impl.LookupViewHelperServiceImpl;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class AcademicCalendarInfoLookupViewHelperServiceImpl  extends LookupViewHelperServiceImpl {
	    public final static String CREDENTIAL_PROGRAM_TYPE_KEY = "credentialProgramTypeKey";
	    public final static String ACADEMIC_CALENDAR_KEY = "key";
	 	private transient AcademicCalendarService academicCalendarService;
	 	

	    @Override
	    protected List<?> getSearchResultsWithBounding(Map<String, String> fieldValues, boolean unbounded) {
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
	    	ContextInfo context = ContextInfo.newInstance();
	    	try{
	    		academicCalendarInfo = getAcademicCalendarService().getAcademicCalendar(academicCalendarKey, context);
	    		academicCalendarInfoList.add(academicCalendarInfo);
	    		return academicCalendarInfoList;
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
