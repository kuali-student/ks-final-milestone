package org.kuali.student.enrollment.class2.acal.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: localadmin
 * Date: 6/19/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */



public class AcademicTermInfoInquirableImpl  extends InquirableImpl {

    private AcademicCalendarService academicCalendarService;

    @Override
    public TermInfo retrieveDataObject(Map<String, String> parameters) {
        TermInfo termInfo = null;

        String termKey = parameters.get("id");
        ContextInfo context = new ContextInfo();
        try{
            termInfo = getAcademicCalendarService().getTerm(termKey, context);
            //List<KeyDateInfo> keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(termKey, context);
            return termInfo;
        }catch (Exception e){
            e.printStackTrace();
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
