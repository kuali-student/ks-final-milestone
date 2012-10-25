package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 10/24/12
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HolidayCalendarViewHelperService extends ViewHelperService {

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

    public void validateHolidayCalendar(HolidayCalendarForm hcForm);

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm);


}
