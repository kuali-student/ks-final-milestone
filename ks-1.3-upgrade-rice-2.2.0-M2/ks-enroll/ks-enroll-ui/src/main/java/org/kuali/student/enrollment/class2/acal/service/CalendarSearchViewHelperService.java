package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;
import java.util.Properties;

public interface CalendarSearchViewHelperService  extends ViewHelperService {

    public List<TermInfo> searchForTerms(String name, String year,ContextInfo context)throws Exception;

    public List<HolidayCalendarInfo> searchForHolidayCalendars(String name, String year,ContextInfo context)throws Exception;

    public List<AcademicCalendarInfo> searchForAcademicCalendars(String name, String year,ContextInfo context)throws Exception;

    public Properties buildTermURLParameters(TermInfo term,String methodToCall,boolean readOnlyView, ContextInfo context);

    public Properties buildACalURLParameters(AcademicCalendarInfo acal,String methodToCall,boolean readOnlyView, ContextInfo context) ;

    public Properties buildHCalURLParameters(HolidayCalendarInfo hcInfo,String methodToCall,boolean readOnlyView, ContextInfo context) ;

}
