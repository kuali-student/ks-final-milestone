package org.kuali.student.enrollment.class2.acal.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.*;

public class AcademicTermLookupableImpl  extends LookupableImpl {

    public final static String TERM_TYPE_KEY = "typeKey";
    public final static String TERM_ID = "id";
 	private transient AcademicCalendarService academicCalendarService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

    	TermInfo termInfo = null;
    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();
        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

    	String termId = fieldValues.get(TERM_ID);
    	ContextInfo context = new ContextInfo();

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

    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
    }

    @Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall,
            List<String> pkNames) {

        if (dataObject == null){
            return "";
        }
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");

        TermInfo term = (TermInfo)dataObject;
        String acalId = null;
        try {
            acalId = getAcademicCalendarService().getAcademicCalendarsForTerm(term.getId(), TestHelper.getContext1()).get(0).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        props.put("id",acalId);
        props.put(UifParameters.VIEW_ID, "academicCalendarEditView");

        if (StringUtils.isNotBlank(lookupForm.getReturnLocation())) {
            props.put(KRADConstants.RETURN_LOCATION_PARAMETER, lookupForm.getReturnLocation());
        }


        return UrlFactory.parameterizeUrl("academicCalendar", props);
    }

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

}
