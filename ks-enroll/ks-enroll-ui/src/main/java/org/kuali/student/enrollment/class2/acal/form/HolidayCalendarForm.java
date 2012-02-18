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
 */
package org.kuali.student.enrollment.class2.acal.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is the formClass for  HolidayCalendar views
 *
 * @author Kuali Student Team
 */

public class HolidayCalendarForm  extends UifFormBase {
    private static final long serialVersionUID = 7526472595622776147L;

    private HolidayCalendarInfo holidayCalendarInfo;
    private List<HolidayInfo> holidays;
    private String adminOrgName;
    private String newCalendarName;
    private Date newCalendarStartDate;
    private Date newCalendarEndDate;

    public HolidayCalendarForm() {
        super();
        holidayCalendarInfo = new HolidayCalendarInfo();
        holidays = new ArrayList<HolidayInfo>();
    }

    public HolidayCalendarInfo getHolidayCalendarInfo() {
        return holidayCalendarInfo;
    }
    public void setHolidayCalendarInfo(HolidayCalendarInfo holidayCalendarInfo) {
        this.holidayCalendarInfo = holidayCalendarInfo;
    }

    public List<HolidayInfo> getHolidays() {
        return holidays;
    }
    public void setHolidays(List<HolidayInfo> holidays) {
        if (null != holidays) {
            this.holidays = holidays;
        }
    }

    public String getAdminOrgName() {
        return adminOrgName;
    }
    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    public String getNewCalendarName() {
        return newCalendarName;
    }
    public void setNewCalendarName(String newCalendarName) {
        this.newCalendarName = newCalendarName;
    }

    public Date getNewCalendarStartDate() {
        return newCalendarStartDate;
    }
    public void setNewCalendarStartDate(Date newCalendarStartDate) {
        this.newCalendarStartDate = newCalendarStartDate;
    }

    public Date getNewCalendarEndDate() {
        return newCalendarEndDate;
    }
    public void setNewCalendarEndDate(Date newCalendarEndDate) {
        this.newCalendarEndDate = newCalendarEndDate;
    }
}
