/* Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a wrapper for the holiday Calendar associated with academic calendar
 *
 * Note: This wrapper is being used only in Academic Calendar.
 *
 * @author Kuali Student Team
 */
public class HolidayCalendarWrapper {

    private String id;
    private String adminOrgName;
    private String stateName;
    private String acalStartYear;

    private HolidayCalendarInfo holidayCalendarInfo;
    private List<HolidayWrapper> holidays;

    public HolidayCalendarWrapper(){
        holidays = new ArrayList<HolidayWrapper>();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set. It equals to holidayCalendarInfo.getId()
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getHolidayCalendarHeading(){
        String sHeading = "No calendar available";
        HolidayCalendarInfo holidayCalendarInfo = getHolidayCalendarInfo();
        if (holidayCalendarInfo != null) {
            sHeading = holidayCalendarInfo.getName();
        }
        return sHeading;
    }

    public HolidayCalendarInfo getHolidayCalendarInfo(){
        return holidayCalendarInfo;
    }
    
    public void setHolidayCalendarInfo (HolidayCalendarInfo holidayCalendarInfo){
        this.holidayCalendarInfo = holidayCalendarInfo;
    }

    public String getAdminOrgName() {
        return adminOrgName;
    }
    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public List<HolidayWrapper> getHolidays() {
        Collections.sort(holidays);
        return holidays;
    }

    public void setHolidays(List<HolidayWrapper> holidays){
        this.holidays = holidays;
    }
    
    public String getAcalStartYear(){
        return acalStartYear;
    }
    
    public void setAcalStartYear(String acalStartYear) {
        this.acalStartYear = acalStartYear;
    }
    
}
