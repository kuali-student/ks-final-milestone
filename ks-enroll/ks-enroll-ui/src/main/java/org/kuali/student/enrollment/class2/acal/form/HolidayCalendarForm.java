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
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;

import java.text.SimpleDateFormat;
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
    private List<HolidayWrapper> holidays;
    private String stateName;
    private String adminOrgName;
    private String newCalendarName;
    private Date newCalendarStartDate;
    private Date newCalendarEndDate;
    private String hcId;
    private boolean officialButtonVisible;
    private boolean deleteButtonVisible;
    private String updateTimeString;

    public HolidayCalendarForm() {
        super();
        holidayCalendarInfo = new HolidayCalendarInfo();
        holidays = new ArrayList<HolidayWrapper>();
        officialButtonVisible = false;
        deleteButtonVisible = false;
    }

    public HolidayCalendarInfo getHolidayCalendarInfo() {
        return holidayCalendarInfo;
    }
    public void setHolidayCalendarInfo(HolidayCalendarInfo holidayCalendarInfo) {
        this.holidayCalendarInfo = holidayCalendarInfo;
    }

    public List<HolidayWrapper> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<HolidayWrapper> holidays) {
        this.holidays = holidays;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public String getHcId() {
        return hcId;
    }

    public void setHcId(String hcId) {
        this.hcId = hcId;
    }

    public boolean isDeleteButtonVisible() {
        return deleteButtonVisible;
    }

    public void setDeleteButtonVisible(boolean deleteButtonVisible) {
        this.deleteButtonVisible = deleteButtonVisible;
    }

    public boolean isOfficialButtonVisible() {
        return officialButtonVisible;
    }

    public void setOfficialButtonVisible(boolean officialButtonVisible) {
        this.officialButtonVisible = officialButtonVisible;
    }

    public String getUpdateTimeString(){
        updateTimeString = new String("");
        if (getHolidayCalendarInfo() == null ||
            getHolidayCalendarInfo().getId()== null ||
            getHolidayCalendarInfo().getId().isEmpty()){
            return updateTimeString;
        }
        else {
            Date updateTime = getHolidayCalendarInfo().getMeta().getUpdateTime();
            if (updateTime != null){
                updateTimeString = "Last saved at "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(updateTime);
            }
            return updateTimeString;
        }
    }
}
