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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.KsUifFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is the formClass for  HolidayCalendar views
 *
 * @author Kuali Student Team
 */

public class HolidayCalendarForm extends KsUifFormBase {
    private static final long serialVersionUID = 7526472595622776147L;

    private HolidayCalendarInfo holidayCalendarInfo;
    private List<HolidayWrapper> holidays;
    private String stateName;
    private String adminOrgName;
    private String newCalendarName;
    private Date newCalendarStartDate;
    private Date newCalendarEndDate;
    private String hcId;
    private String orgHcId;
    private boolean newCalendar;
    private boolean officialCalendar;

    //Just to display buttons (delete) based on this flag
    private boolean officialUI;

    public HolidayCalendarForm() {
        super();
        holidayCalendarInfo = new HolidayCalendarInfo();
        holidays = new ArrayList<HolidayWrapper>();
        newCalendar = true;
        officialCalendar = false;
    }

    /**
     * @return the HolidayCalendarInfo which is currently in use at the view
     */
    public HolidayCalendarInfo getHolidayCalendarInfo() {
        return holidayCalendarInfo;
    }

    /**
     * New Hcal or editing an existing Hcal object
     *
     * @param holidayCalendarInfo
     */
    public void setHolidayCalendarInfo(HolidayCalendarInfo holidayCalendarInfo) {
        this.holidayCalendarInfo = holidayCalendarInfo;
    }

    /**
     * @return the list of HolidayWrapper which are currently in use at the holiday view
     */
    public List<HolidayWrapper> getHolidays() {
        // Putting sort here causes list to be sorted when addLine "add" clicked, instead of having
        // the new row added to the top of the collection as desired.  Just so you know.
        //Collections.sort(holidays);
        return holidays;
    }

    /**
     * Update new list of HolidayWrapper or Save the edited an existing list of HolidayWrapper object
     *
     * @param holidays
     */
    public void setHolidays(List<HolidayWrapper> holidays) {
        this.holidays = holidays;
    }

    /**
     * State of the current holiday calendar.
     *
     * @return stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * State name involved with a hcal.
     *
     * @param stateName
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * Organization involved with a hcal.
     *
     * @return adminOrgName
     */
    public String getAdminOrgName() {
        return adminOrgName;
    }

    /**
     * Organization involved with a hcal.
     *
     * @param adminOrgName
     */
    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    /**
     * new hcal.
     *
     * @return newCalendarName
     */
    public String getNewCalendarName() {
        return newCalendarName;
    }

    /**
     * New Hcal object
     *
     * @param newCalendarName
     */
    public void setNewCalendarName(String newCalendarName) {
        this.newCalendarName = newCalendarName;
    }

    /**
     * Start date of the new hcal.
     *
     * @return newCalendarStartDate
     */
    public Date getNewCalendarStartDate() {
        return newCalendarStartDate;
    }

    /**
     * Start date of the new hcal.
     *
     * @param newCalendarStartDate
     */
    public void setNewCalendarStartDate(Date newCalendarStartDate) {
        this.newCalendarStartDate = newCalendarStartDate;
    }

    /**
     * The new calendar end date.
     *
     * @return newCalendarEndDate
     */
    public Date getNewCalendarEndDate() {
        return newCalendarEndDate;
    }

    /**
     * End date of the new hcal.
     *
     * @param newCalendarEndDate
     */
    public void setNewCalendarEndDate(Date newCalendarEndDate) {
        this.newCalendarEndDate = newCalendarEndDate;
    }

    /**
     * Holiday calendar id.
     *
     * @return hcId
     */
    public String getHcId() {
        return hcId;
    }

    /**
     * Holiday calendar id.
     *
     * @param hcId
     */
    public void setHcId(String hcId) {
        this.hcId = hcId;
    }

    /**
     * Original holiday calendar id.
     *
     * @return orgHcId
     */
    public String getOrgHcId() {
        return orgHcId;
    }

    /**
     * Original holiday calendar id.
     *
     * @param orgHcId
     */
    public void setOrgHcId(String orgHcId) {
        this.orgHcId = orgHcId;
    }

    /**
     * indicate new calendar exists or not.
     *
     * @return newCalendar
     */
    public boolean isNewCalendar() {
        return newCalendar;
    }

    /**
     * Set New calendar.
     *
     * @param newCalendar
     */
    public void setNewCalendar(boolean newCalendar) {
        this.newCalendar = newCalendar;
    }

    /**
     * It is an official Calendar or not with a hcal.
     *
     * @return
     */
    public boolean isOfficialCalendar() {
        return officialCalendar;
    }

    /**
     * It is an official Calendar or not with a hcal.
     *
     * @param officialCalendar
     */
    public void setOfficialCalendar(boolean officialCalendar) {
        this.officialCalendar = officialCalendar;
    }

    /**
     * The last updated time involved with a hcal.
     *
     * @return
     */
    public String getUpdateTimeString() {
        if (getHolidayCalendarInfo() != null &&
                getHolidayCalendarInfo().getMeta() != null &&
                getHolidayCalendarInfo().getMeta().getUpdateTime() != null) {
            Date updateTime = getHolidayCalendarInfo().getMeta().getUpdateTime();

            return "Last saved at " + DateFormatters.SIMPLE_TIMESTAMP_FORMATTER.format(updateTime);
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * UI data if an Calendar is official or not with a hcal.
     *
     * @return
     */
    public boolean isOfficialUI() {
        if (holidayCalendarInfo != null) {
            return StringUtils.equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY, holidayCalendarInfo.getStateKey());
        }
        return false;
    }
}
