package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Core slice class.
@Deprecated
public class AcademicCalendarWrapperInquiryViewHelperServiceImpl extends InquirableImpl {
//	 public final static String ACADEMIC_CALENDAR_KEY = "academicCalendarInfo.key";
     public final static String ACADEMIC_CALENDAR_WRAPPER_KEY = "id";
	 private transient AcademicCalendarService academicCalendarService;
	 
	@Override
    public AcademicCalendarWrapper retrieveDataObject(Map<String, String> parameters) {
    	ContextInfo context = new ContextInfo();
    	academicCalendarService = getAcademicCalendarService();
    	AcademicCalendarWrapper academicCalendarWrapper = new AcademicCalendarWrapper();
    	try{
    		//need to retrieve AcademicCalendarInfo, all TermInfo and all KeyDateInfo to form the AcademicCalendarWrapper.
    		String academicCalendarKey = parameters.get(ACADEMIC_CALENDAR_WRAPPER_KEY);
    		if(academicCalendarKey == null){
    			System.out.println(">>>academicCalendarKey is null");
    			return null;
    		}
    		else {
    			System.out.println(">>>academicCalendarKey ="+academicCalendarKey);
    		}	
    		AcademicCalendarInfo academicCalendarInfo = academicCalendarService.getAcademicCalendar(academicCalendarKey, context);
    		academicCalendarWrapper.setAcademicCalendarInfo(academicCalendarInfo);
    		
    		List<TermWrapper> termWrapperList = new ArrayList<TermWrapper>();
    		List<TermInfo> termInfoList = academicCalendarService.getTermsForAcademicCalendar(academicCalendarKey, context);
    		for (TermInfo termInfo:termInfoList){
    			TermWrapper termWrapper = new TermWrapper();
    			termWrapper.setTermInfo(termInfo);
    			List<KeyDateInfo> keyDateInfoList = academicCalendarService.getKeyDatesForTerm(termInfo.getId(), context);
    			for (KeyDateInfo keyDateInfo : keyDateInfoList){
    				if(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setClassesMeetDates(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setRegistrationPeriod(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setDropPeriodEndsDate(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setFinalExaminationsDates(keyDateInfo);
    				}
    				else if(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY.equals(keyDateInfo.getTypeKey())){
    					termWrapper.setGradesDueDate(keyDateInfo);
    				}
    					
    			}
    			termWrapperList.add(termWrapper);    			
    		}
    		academicCalendarWrapper.setTermWrapperList(termWrapperList);
    		return academicCalendarWrapper;
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

}
