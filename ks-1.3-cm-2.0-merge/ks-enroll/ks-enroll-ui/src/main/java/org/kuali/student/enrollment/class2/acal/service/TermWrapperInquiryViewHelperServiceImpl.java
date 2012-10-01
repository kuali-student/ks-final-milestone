package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 */
//Core slice class.
@Deprecated
public class TermWrapperInquiryViewHelperServiceImpl extends InquirableImpl {
     public final static String TERM_WRAPPER_KEY = "key";
	 private transient AcademicCalendarService academicCalendarService;

    @Override
    public TermWrapper retrieveDataObject(Map<String, String> parameters) {
    	TermInfo termInfo = null;
        TermWrapper termWrapper = new TermWrapper();

    	String termKey = parameters.get(TERM_WRAPPER_KEY);
    	ContextInfo context = new ContextInfo();
    	try{
    		termInfo = getAcademicCalendarService().getTerm(termKey, context);
            termWrapper.setTermInfo(termInfo);
            List<KeyDateInfo>  keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(termKey, context);
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
    		return termWrapper;
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
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
       }
       return academicCalendarService;
   }
}
