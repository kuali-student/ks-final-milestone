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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TimeSetWrapper {
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String startTimeAmPm;
    private String endTime;
    private String endTimeAmPm;
    private boolean allDay;
    private boolean dateRange;

    //This is needed for view purpose as we're setting the end date as null sothat it wont display in the ui
    protected Date endDateUI;

    protected void buildDateAndTime(){

        //This is needed to display enddate for readonly view (not needed for edit mode).
        endDateUI = getEndDate();

        // If not all day, set start/end time in the wrapper
        if (!isAllDay()){

            DateFormat dfm = new SimpleDateFormat("hh:mm");

            if (getEndDate() != null){
                setStartTime(dfm.format(getStartDate()));
                setEndTime(dfm.format(getEndDate()));

                dfm = new SimpleDateFormat("a");
                setStartTimeAmPm(dfm.format(getStartDate()));
                setEndTimeAmPm(dfm.format(getEndDate()));

                String startDate = CommonUtils.formatDate(getStartDate());
                String endDate = CommonUtils.formatDate(getEndDate());

                if (StringUtils.equals(startDate,endDate)){
                    setDateRange(false);
                    setEndDate(null);
                }else{
                    setDateRange(true);
                }
            }
        }else if (!isDateRange()){
            setEndDate(null);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeAmPm() {
        return startTimeAmPm;
    }

    public void setStartTimeAmPm(String startTimeAmPm) {
        this.startTimeAmPm = startTimeAmPm;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeAmPm() {
        return endTimeAmPm;
    }

    public void setEndTimeAmPm(String endTimeAmPm) {
        this.endTimeAmPm = endTimeAmPm;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public boolean isDateRange() {
        return dateRange;
    }

    public void setDateRange(boolean dateRange) {
        this.dateRange = dateRange;
    }

    //This is for UI display purpose
    protected String formatStartDateUI(Date startDate){
        if (startDate != null) {
            if (!isAllDay()){
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                String formattedDate = formatter.format(startDate);
                return StringUtils.removeEndIgnoreCase(formattedDate,"12:00 am");
            }else{
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                return formatter.format(startDate);
            }
        }else{
            return StringUtils.EMPTY;
        }

    }

    //This is for UI display purpose
    protected String formatEndDateUI(Date endDate){
        if (endDate != null) {
            if (!isAllDay()){
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                String formattedEndDate = formatter.format(endDate);
                String formattedStartDate = formatter.format(startDate);
                String strippedDate = StringUtils.removeStart(formattedEndDate,StringUtils.substringBefore(formattedStartDate," "));
                return StringUtils.removeEndIgnoreCase(strippedDate,"11:59 pm");
            }else{
                if (isDateRange()){
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    return formatter.format(endDate);
                }else{
                    return StringUtils.EMPTY;
                }
            }
        }else{
            return StringUtils.EMPTY;
        }

    }

}
