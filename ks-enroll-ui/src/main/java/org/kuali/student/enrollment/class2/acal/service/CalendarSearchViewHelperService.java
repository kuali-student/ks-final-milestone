package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.class2.acal.dto.AcalSearchResult;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;
import java.util.Properties;

public interface CalendarSearchViewHelperService  extends ViewHelperService {

    public Properties buildTermURLParameters(AcalSearchResult term,String methodToCall,boolean readOnlyView, ContextInfo context);

    public Properties buildACalURLParameters(AcalSearchResult acal,String methodToCall,boolean readOnlyView, ContextInfo context) ;

    public Properties buildHCalURLParameters(AcalSearchResult hcInfo,String methodToCall,boolean readOnlyView, ContextInfo context) ;

    public List<AcalSearchResult> searchForCalendars(String name, String year, String calendarType, ContextInfo contextInfo) throws Exception;
}
