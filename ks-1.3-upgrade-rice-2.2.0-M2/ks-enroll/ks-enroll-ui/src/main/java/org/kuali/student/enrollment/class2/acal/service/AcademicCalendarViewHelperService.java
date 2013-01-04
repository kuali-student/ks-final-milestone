package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.*;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 1/24/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AcademicCalendarViewHelperService extends ViewHelperService {

    //HC
    public void saveHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception;

    public HolidayCalendarInfo getNewestHolidayCalendar() throws Exception;

    public List<HolidayWrapper> getHolidayWrappersForHolidayCalendar(String holidayCalendarId) throws Exception;

    public void deleteHoliday(int selectedIndex,HolidayCalendarForm hcForm) throws Exception;

    public String getHolidayTypeName(String holidayTypeKey) throws Exception;

    public String getHolidayCalendarState(String holidayCalendarStateKey) throws Exception;

    public void deleteHolidayCalendar(String holidayCalendarId) throws Exception;

    public void populateHolidayTypes(InputField field, HolidayCalendarForm hcForm);

    //Acal
    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;

    public void deleteAcademicCalendar(String academicCalendarId) throws Exception;

    public AcademicCalendarInfo getLatestAcademicCalendar() throws Exception;

    public void copyToCreateAcademicCalendar(AcademicCalendarForm form) throws Exception;

    public List<AcalEventWrapper> populateEventWrappers(AcademicCalendarForm acalForm) throws Exception;

    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event) throws Exception;

    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception;

    public void deleteEvent(String eventId) throws Exception;

    public List<HolidayCalendarWrapper> loadHolidayCalendars (AcademicCalendarInfo acalInfo) throws Exception;

    //Terms
    public void saveTerm(AcademicTermWrapper termWrapper, String acalId,boolean isOfficial) throws Exception;

//    public void setTermOfficial(AcademicTermWrapper termWrapper, String acalId) throws Exception;

    public void deleteTerm(List<AcademicTermWrapper> termWrapper,int selectedIndex, String acalId) throws Exception;

    public void deleteKeyDate(KeyDatesGroupWrapper keyDatesGroup,int selectedIndex) throws Exception;

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm);

    public void deleteKeyDateGroup(AcademicTermWrapper termWrapper,int selectedIndex) throws Exception;

    public void populateKeyDateGroupTypes(InputField field, AcademicCalendarForm acalForm) throws Exception;

    public void populateInstructionalDays(List<AcademicTermWrapper> termWrapperList)throws Exception;

    public void populateInstructionalDays(AcademicTermWrapper termWrapper) throws Exception;

    public List<AcademicTermWrapper> populateTermWrappers(String acalId, boolean isCopy);

    public void validateAcademicCalendar(AcademicCalendarForm acalForm);

    public void validateHolidayCalendar(HolidayCalendarForm hcForm);

    public void validateTerm(List<AcademicTermWrapper> termWrapper,int termToValidateIndex,AcademicCalendarInfo acal);

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm);

    public void populateAcademicCalendarDefaults(AcademicCalendarForm acalForm);

    public AcademicTermWrapper populateTermWrapper(TermInfo termInfo, boolean isCopy);

    public List<TermInfo> getTermsByTypeAndCode(String type, String code) throws Exception;

    public AcademicCalendarService getAcalService();

    public ContextInfo getContextInfo();

}
