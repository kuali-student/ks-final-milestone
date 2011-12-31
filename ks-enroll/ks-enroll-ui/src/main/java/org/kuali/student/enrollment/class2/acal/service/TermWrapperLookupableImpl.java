package org.kuali.student.enrollment.class2.acal.service;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

public class TermWrapperLookupableImpl extends LookupableImpl {
    public final static String TERM_WRAPPER_KEY = "key";

 	private transient AcademicCalendarService academicCalendarService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
    	TermInfo termInfo = null;
    	List<TermWrapper> termWrapperList = new ArrayList<TermWrapper>();

    	String termId = fieldValues.get(TERM_WRAPPER_KEY);
    	ContextInfo context = ContextInfo.newInstance();
    	try{
    		termInfo = getAcademicCalendarService().getTerm(termId, context);
            TermWrapper termWrapper = new TermWrapper();
            termWrapper.setTermInfo(termInfo);
            List<KeyDateInfo>  keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(termId, context);
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
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get DoesNotExistException:  "+dnee.toString());
    	}catch (InvalidParameterException ipe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get InvalidParameterException:  "+ipe.toString());
    	}catch (MissingParameterException mpe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get MissingParameterException:  "+mpe.toString());
    	}catch (OperationFailedException ofe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get OperationFailedException:  "+ofe.toString());
    	}catch (PermissionDeniedException pde){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termId, context), and get PermissionDeniedException:  "+pde.toString());
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
