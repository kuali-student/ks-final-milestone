package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
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
    public HolidayCalendarInfo createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception;
    public HolidayCalendarInfo copyHolidayCalendar(HolidayCalendarForm form) throws Exception;
    public HolidayCalendarInfo getNewestHolidayCalendar() throws Exception;
    public HolidayCalendarInfo updateHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public List<HolidayWrapper> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception;
    public void createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayWrapper holiday) throws Exception;
    public void updateHoliday(String holidayId, HolidayWrapper holiday) throws Exception;
    public void deleteHoliday(String holidayId) throws Exception;
    public String getHolidayTypeName(String holidayTypeKey) throws Exception;

    //Acal
    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;
    public AcademicCalendarInfo getAcademicCalendar(String acalId) throws Exception;
    public AcademicCalendarInfo updateAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;
    public List<AcalEventWrapper> getEventsForAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;
    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event) throws Exception;
    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception;
    public void deleteEvent(String eventId) throws Exception;

    //Terms
    public void saveTerm(AcademicTermWrapper termWrapper,ContextInfo context) throws Exception;

    public void buildTerm(String termId,AcademicCalendarForm academicCalendarForm,ContextInfo context) throws Exception;

    public void setTermOfficial(AcademicTermWrapper termWrapper,ContextInfo context) throws Exception;

    public void deleteTerm(List<AcademicTermWrapper> termWrapper,int selectedIndex,ContextInfo context) throws Exception;

    public void deleteKeyDate(KeyDatesGroupWrapper keyDatesGroup,int selectedIndex,ContextInfo context) throws Exception;

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm);

    public void deleteKeyDateGroup(AcademicTermWrapper termWrapper,int selectedIndex,ContextInfo context) throws Exception;

    public void populateKeyDateGroupTypes(InputField field, AcademicCalendarForm acalForm) throws Exception;

    public AcademicCalendarService getAcalService();
}
