package org.kuali.student.enrollment.class2.acal.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.infc.AcademicCalendar;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.*;

public class TermInfoLookupableImpl extends LookupableImpl {
    public final static String TERM_TYPE_KEY = "typeKey";
    public final static String TERM_KEY = "key";
 	private transient AcademicCalendarService academicCalendarService;
 	

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
    	TermInfo termInfo = null;
    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();
        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

    	String termId = fieldValues.get(TERM_KEY);
    	ContextInfo context = ContextInfo.newInstance();
        if (StringUtils.isBlank(termId)) {
            Calendar now = Calendar.getInstance();
            int thisYear = now.get(Calendar.YEAR);
            try {
                // next year
                acalInfoList.addAll(getAcademicCalendarService().getAcademicCalendarsByStartYear(thisYear + 1, context));
                // this year
                acalInfoList.addAll(getAcademicCalendarService().getAcademicCalendarsByStartYear(thisYear, context));
                // and last
                acalInfoList.addAll(getAcademicCalendarService().getAcademicCalendarsByStartYear(thisYear - 1, context));
                for (AcademicCalendarInfo acalInfo : acalInfoList) {
                    termInfoList.addAll(getAcademicCalendarService().getTermsForAcademicCalendar(acalInfo.getId(), context));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            try{
                termInfo = getAcademicCalendarService().getTerm(termId, context);
                termInfoList.add(termInfo);
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return termInfoList;
    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
        	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
        }

        return academicCalendarService;
    }

}
