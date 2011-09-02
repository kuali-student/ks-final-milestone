package org.kuali.student.enrollment.class2.acal.service;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

public class TermWrapperLookupableImpl extends LookupableImpl {
    public final static String TERM_TYPE_KEY = "termInfo.typeKey";
    public final static String TERM_KEY = "termInfo.key";

 	private transient AcademicCalendarService academicCalendarService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
    	TermInfo termInfo = null;
    	List<TermWrapper> termWrapperList = new ArrayList<TermWrapper>();

    	String termKey = fieldValues.get(TERM_KEY);
    	ContextInfo context = ContextInfo.newInstance();
    	try{
    		termInfo = getAcademicCalendarService().getTerm(termKey, context);
            TermWrapper termWrapper = new TermWrapper();
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
    		termWrapperList.add(termWrapper);
    		return termWrapperList;
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
