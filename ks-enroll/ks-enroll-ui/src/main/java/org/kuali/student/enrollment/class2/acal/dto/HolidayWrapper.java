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
 */
package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.HolidayInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HolidayWrapper extends TimeSetWrapper {
    private String typeName;
    private String stateName;
//    private Date startDate;
//    private Date endDate;
//    private String startTime;
//    private String startTimeAmPm;
//    private String endTime;
//    private String endTimeAmPm;
    private HolidayInfo holidayInfo;

    public HolidayWrapper(){}

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /*    public String getEndTimeAmPm() {
        return endTimeAmPm;
    }

    public void setEndTimeAmPm(String endTimeAmPm) {
        this.endTimeAmPm = endTimeAmPm;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTimeAmPm() {
        return startTimeAmPm;
    }

    public void setStartTimeAmPm(String startTimeAmPm) {
        this.startTimeAmPm = startTimeAmPm;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }    */

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public HolidayInfo getHolidayInfo() {
        return holidayInfo;
    }

    public void setHolidayInfo(HolidayInfo holidayInfo) {
        this.holidayInfo = holidayInfo;
    }
}
