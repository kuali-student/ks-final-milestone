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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;

import java.util.Date;

/**
 * This is a wrapper for the Holiday info object.
 *
 * @author Kuali Student Team
 */
public class HolidayWrapper extends TimeSetWrapper implements Comparable<HolidayWrapper> {

    private String typeName;
    private HolidayInfo holidayInfo;
    private boolean instructional;
    private String typeKey;

    public HolidayWrapper(){
        holidayInfo = new HolidayInfo();
        setAllDay(true);
        setInstructional(false);
        setDateRange(false);
    }

    public HolidayWrapper(HolidayInfo holidayInfo){
        this.setHolidayInfo(holidayInfo);
        this.setStartDate(holidayInfo.getStartDate());
        this.setEndDate(holidayInfo.getEndDate());
        this.setAllDay(holidayInfo.getIsAllDay());
        this.setDateRange(holidayInfo.getIsDateRange());
        this.setTypeKey(holidayInfo.getTypeKey());
        this.setInstructional(holidayInfo.getIsInstructionalDay());
        buildDateAndTime();
    }

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

    //This is for UI display purpose
    public String getIsNonInstructional(){
        if (holidayInfo != null){
            return StringUtils.capitalize(BooleanUtils.toStringYesNo(!holidayInfo.getIsInstructionalDay()));
        }
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(true));
    }

    public boolean isInstructional() {
        return instructional;
    }

    public void setInstructional(boolean instructional) {
        this.instructional = instructional;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }


    /**
     * Allow Collections.sort() to sort by startDate & endDate
     */
    public int compareTo(HolidayWrapper holidayToCompare) {
        //FindBugs - it is fine as is
        int compareValue = compareDates(this.getStartDate(), holidayToCompare.getStartDate());
        if (compareValue == 0) {
            // startDates are equal so compare endDates
            compareValue = compareDates(this.getEndDate(), holidayToCompare.getEndDate());
        }
        return compareValue;
    }

    private int compareDates(Date thisDate, Date compareToDate) {
        // unfortunately, Date.before() & .after() are not null friendly

        if (null == thisDate) {
            if (null == compareToDate) {
                return 0; // both dates are null and therefore equal
            }
            return -1; // compare-date is later than this-date (which is null)
        }

        if (null == compareToDate) {
            return 1; // this-date (which is valid) is after the compare-date (which is null)
        }

        // safe to use .before() and .after() methods to do the comparison
        if (thisDate.before(compareToDate)) {
            return -1;
        }
        if (thisDate.after(compareToDate)) {
            return 1;
        }
        return 0; // dates are equal
    }

    //This is for UI display purpose
    public String getStartDateUI(){
        return formatStartDateUI(holidayInfo.getStartDate());
    }

    //This is for UI display purpose
    public String getEndDateUI(){
        return formatEndDateUI(holidayInfo.getEndDate());
    }

}
