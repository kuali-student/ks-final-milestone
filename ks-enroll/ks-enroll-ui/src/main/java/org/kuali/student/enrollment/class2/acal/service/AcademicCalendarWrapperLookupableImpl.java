package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AcademicCalendarWrapperLookupableImpl extends LookupableImpl {
    public final static String CREDENTIAL_PROGRAM_TYPE_KEY = "academicCalendarInfo.credentialProgramTypeKey";
    public final static String ACADEMIC_CALENDAR_KEY = "academicCalendarInfo.key";
 	private transient AcademicCalendarService academicCalendarService;
 	

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
    	List<AcademicCalendarWrapper> academicCalendarWrapperList = new ArrayList<AcademicCalendarWrapper>();
    	AcademicCalendarWrapper academicCalendarWrapper = new AcademicCalendarWrapper();
    	List<TermWrapper> termWrapperList = academicCalendarWrapper.getTermWrapperList();    
    	academicCalendarService = getAcademicCalendarService();
    	AcademicCalendarInfo academicCalendarInfo = null;
    	List<AcademicCalendarInfo> academicCalendarInfoList = new ArrayList<AcademicCalendarInfo>();
    	
    	String academicCalendarKey = fieldValues.get(ACADEMIC_CALENDAR_KEY);
        System.out.println(">>>academicCalendarKey = "+academicCalendarKey);
    	ContextInfo context = ContextInfo.newInstance();
    	try{
    		academicCalendarInfo = academicCalendarService.getAcademicCalendar(academicCalendarKey, context);
    		academicCalendarWrapper.setAcademicCalendarInfo(academicCalendarInfo);
    		academicCalendarWrapperList.add(academicCalendarWrapper);
    		return academicCalendarWrapperList;
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
