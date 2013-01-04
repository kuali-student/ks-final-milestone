/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by chongzhu on 10/24/12
 */
package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.uif.service.KSViewHelperService;

import java.util.List;

/**
 * Interface for Holiday Calendar View helper.
 */
public interface HolidayCalendarViewHelperService extends KSViewHelperService {

    //HC
    public void saveHolidayCalendar(HolidayCalendarForm hcForm,boolean isSetOfficial) throws Exception;

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception;

    public HolidayCalendarInfo getNewestHolidayCalendar() throws Exception;

    public List<HolidayWrapper> getHolidayWrappersForHolidayCalendar(String holidayCalendarId) throws Exception;

    public void deleteHoliday(int selectedIndex,HolidayCalendarForm hcForm) throws Exception;

    public String getHolidayTypeName(String holidayTypeKey) throws Exception;

    public String getHolidayCalendarState(String holidayCalendarStateKey) throws Exception;

    public void deleteHolidayCalendar(String holidayCalendarId) throws Exception;

    public void validateHolidayCalendar(HolidayCalendarForm hcForm);

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm);


}
