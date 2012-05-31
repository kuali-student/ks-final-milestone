package org.kuali.student.enrollment.class2.acal.service;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.util.CalendarSearchViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;


public class AcademicTermLookupableImpl  extends LookupableImpl {

    private transient AcademicCalendarService academicCalendarService;
    private ContextInfo contextInfo;
    final Logger logger = Logger.getLogger(AcademicTermLookupableImpl.class);

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<TermInfo> rList = null;
        String name = fieldValues.get("code");
        String year = fieldValues.get("startDate");

        try{
            rList = CalendarSearchViewHelperUtil.searchForTerms(name, year, getContextInfo(), getAcademicCalendarService());
        }   catch (Exception ex){
            throw new RuntimeException("Error in AcademicTermLookupableImpl searching for term. name[" + name +"] year["+year +"]", ex);
        }

        return rList;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }

}
