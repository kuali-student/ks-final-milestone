/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.common.client.widgets;

import java.util.Date;

/**
 * <dl>
 * <dt><b>Title: </b><dd>GregorianCalendar</dd>
 * <p>
 * <dt><b>Description: </b><dd>java.util.GregorianCalendar replacment</dd>
 * </dl>
 * @author <a href="mailto:andre.freller@gmail.com">Andre Freller</a>
 * @version $Revision: 0.1 $
 */
///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
public class GregorianCalendar extends Calendar
{
    public static final int BC          = 0;
    public static final int AD          = 1;

    public GregorianCalendar() {
        super();
    }
    public GregorianCalendar(Date date) {
        setTime(date);
    }
    public GregorianCalendar(int year, int month, int date) {
        set(year, month, date);
    }
    public GregorianCalendar(int year, int month, int date, int hour) {
        set(year, month, date, hour);
    }
    public GregorianCalendar(int year, int month, int date, int hour, int minute) {
        set(year, month, date, hour, minute);
    }
    public GregorianCalendar(int year, int month, int date, int hour, int minute, int second) {
        set(year, month, date, hour, minute, second);
    }

    protected static int[] daysInMonth  = {31, 28, 31, 30, 31, 30, 31, 31 ,30, 31, 30, 31};
    public static int getMaxDaysInMonth(int year, int month) {
        if (month == Calendar.FEBRUARY  &&  isLeapYear(year))
            return daysInMonth[month] + 1;
        else
            return daysInMonth[month];
    }
    public int getMaxDaysInMonth() {
        return getMaxDaysInMonth(this.year, this.month);
    }

    public static int getNumOfWeeksInMonth(int year, int month, int firstDayOfWeek) {
        int day1        = getWeekDay(year, month, 1);
        int maxDays     = getMaxDaysInMonth(year, month);
        if (month == Calendar.FEBRUARY  &&  !isLeapYear(year)  &&  day1 == firstDayOfWeek)
            return 4;
        if (maxDays == 30  &&  (day1 > firstDayOfWeek + 5  ||  day1 < firstDayOfWeek))
            return 6;
        if (maxDays == 31  &&  (day1 > firstDayOfWeek + 4  ||  day1 < firstDayOfWeek))
            return 6;
        return 5;
    }
    public int getNumOfWeeksInMonth() {
        return getNumOfWeeksInMonth(this.year, this.month, this.firstWeekDayOfWeek);
    }

    public static int getWeekDay(int year, int month, int dayOfMonth) {
        int weekDay     = year - 1900;
        weekDay         += weekDay/4;
        weekDay         %= 7;
        if (month <= Calendar.FEBRUARY  &&  isLeapYear(weekDay))
            weekDay     -= 1;
        weekDay         += dayOfMonth;
        weekDay         %= 7;
        if (month == Calendar.MAY)
            weekDay     += 1;
        else if (month == Calendar.AUGUST)
            weekDay     += 2;
        else if (month == Calendar.FEBRUARY  ||  month == Calendar.MARCH  ||  month == Calendar.NOVEMBER)
            weekDay     += 3;
        else if (month == Calendar.JUNE)
            weekDay     += 4;
        else if (month == Calendar.SEPTEMBER  ||  month == Calendar.DECEMBER)
            weekDay     += 5;
        else if (month == Calendar.APRIL  ||  month == Calendar.JULY)
            weekDay     += 6;
        weekDay         %= 7;

        return Calendar.SUNDAY + weekDay;
    }
    public int getWeekDay() {
        return getWeekDay(this.year, this.month, this.dayOfMonth);
    }
    public int getFirstWeekDayOfMonth() {
        return getWeekDay(this.year, this.month, 1);
    }

    // Gets the minimum value for the given time field.
    public int getMinimum(int fieldCode) {
        switch (fieldCode) {
        case Calendar.MILLISECOND:
            return 0;
        case Calendar.SECOND:
            return 0;
        case Calendar.MINUTE:
            return 0;
        case Calendar.HOUR:
            return 0;
        case Calendar.HOUR_OF_DAY:
            return 0;
        case Calendar.DATE:
            return 1;
        case Calendar.MONTH:
            return Calendar.JANUARY;
        default:
            return -1;
        }
    }
    // Return the minimum value that this field could have, given the current date.
    public int getActualMinimum(int fieldCode) {
        return getMinimum(fieldCode);
    }
    // Gets the highest minimum value for the given field if varies.
    public   int getGreatestMinimum(int fieldCode) {
        return getMinimum(fieldCode);
    }

    // Gets the maximum value for the given time field.
    public   int getMaximum(int fieldCode) {
        switch (fieldCode) {
        case Calendar.MILLISECOND:
            return 999;
        case Calendar.SECOND:
            return 59;
        case Calendar.MINUTE:
            return 59;
        case Calendar.HOUR:
            return 11;
        case Calendar.HOUR_OF_DAY:
            return 23;
        case Calendar.DATE:
            return 31;
        case Calendar.MONTH:
            return Calendar.DECEMBER;
        default:
            return -1;
        }
    }
    // Return the maximum value that this field could have, given the current date.
    public int getActualMaximum(int fieldCode) {
        switch (fieldCode) {
        case Calendar.MILLISECOND:
            return 999;
        case Calendar.SECOND:
            return 59;
        case Calendar.MINUTE:
            return 59;
        case Calendar.HOUR:
            return 11;
        case Calendar.HOUR_OF_DAY:
            return 23;
        case Calendar.DATE:
            return getMaxDaysInMonth();
        case Calendar.MONTH:
            return Calendar.DECEMBER;
        default:
            return -1;
        }
    }
    // Gets the lowest maximum value for the given field if varies.
    public   int getLeastMaximum(int fieldCode) {
        switch (fieldCode) {
        case Calendar.MILLISECOND:
            return 999;
        case Calendar.SECOND:
            return 59;
        case Calendar.MINUTE:
            return 59;
        case Calendar.HOUR:
            return 11;
        case Calendar.HOUR_OF_DAY:
            return 23;
        case Calendar.DATE:
            return 28;
        case Calendar.MONTH:
            return Calendar.DECEMBER;
        default:
            return -1;
        }
    }


    public void set(int fieldCode, int value) {
        switch (fieldCode) {
        case Calendar.SECOND:
            this.second         = value;
            break;
        case Calendar.MINUTE:
            this.minute         = value;
            break;
        case Calendar.HOUR:
            this.hour           = value;
            break;
        case Calendar.HOUR_OF_DAY:
            if (value <= 12) {
                this.hour       = value;
                this.amPm       = Calendar.AM;
            } else {
                this.hour       = value - 12;
                this.amPm       = Calendar.PM;
            }
            break;
        case Calendar.DATE:
            this.dayOfMonth             = value;
            break;
        case Calendar.MONTH:
            this.month          = value;
            if (this.dayOfMonth > getMaxDaysInMonth())
                this.dayOfMonth = getMaxDaysInMonth();
            break;
        case Calendar.YEAR:
            this.year           = value;
            break;
        case Calendar.AM_PM:
            this.amPm           = value;
            break;
        }
        computeTime();
    }


    // Date Arithmetic function.
    public void add(int fieldCode, int amount) {
        switch (fieldCode) {
        case Calendar.DATE:
            amount              *= 24;
        case Calendar.HOUR:
            amount              *= 60;
        case Calendar.MINUTE:
            amount              *= 60;
        case Calendar.SECOND:
            amount              *= 1000;
        case Calendar.MILLISECOND:
            this.date.setTime(this.date.getTime() + amount);
            computeFields();
            break;
        case Calendar.MONTH:
            this.year           += (this.month + amount) / 12;
            this.month          = (this.month + amount) % 12;
            if (this.month < 0) {
                this.year       -= 1;
                this.month      += 12;
            }
            if (this.dayOfMonth > getMaxDaysInMonth())
                this.dayOfMonth = getMaxDaysInMonth();
            computeTime();
            break;
        case Calendar.YEAR:
            this.year           += amount;
            computeTime();
            break;
        }
    }

    // Time Field Rolling function.
    public void roll(int fieldCode, int amount) {
        switch (fieldCode) {
        case Calendar.SECOND:
            this.second         = (this.second + amount) % 60;
            if (this.second < 0)
                this.second     += 60;
            break;
        case Calendar.MINUTE:
            this.minute         = (this.minute + amount) % 60;
            if (this.minute < 0)
                this.minute     += 60;
            break;
        case Calendar.HOUR:
            this.hour           = (this.hour + amount) % 24;
            if (this.hour < 0)
                this.hour       += 24;
            break;
        case Calendar.DATE: {
            this.dayOfMonth     = (this.dayOfMonth + amount) % getActualMaximum(Calendar.DATE);
            if (this.dayOfMonth < 0)
                this.dayOfMonth += getActualMaximum(Calendar.DATE);
            break;
        }
        case Calendar.MONTH: {
            this.month          = (this.month + amount) % 12;
            if (this.month < 0)
                this.month      += 12;
            if (this.dayOfMonth > getActualMaximum(Calendar.DATE))
                this.dayOfMonth = getActualMaximum(Calendar.DATE);
            break;
        }
        case Calendar.YEAR:
            this.year           += amount;
            break;
        }
        computeTime();
    }


    // Compares the time field records.
    public boolean before(GregorianCalendar when) {
            return this.date.before(when.getTime());
    }
    public boolean after(GregorianCalendar when) {
            return this.date.after(when.getTime());
    }
    public boolean equals(GregorianCalendar obj) {
        return this.date.equals(obj.getTime());
    }

    public int hashCode() {
        if(this.date == null){
            return super.hashCode();
        }else{
            return this.date.hashCode();
        }

    }

    public boolean isLeapYear() {
        return isLeapYear(this.year);
    }
    public static boolean isLeapYear(int year) {
        if ((year % 4) == 0    &&    ((year % 100) != 0  ||  (year % 400) == 0))
            return true;
        return false;
    }


    // Converts the current millisecond time value time to field values in fields[].
    protected  void computeFields() {
        this.second             = this.date.getSeconds();
        this.minute             = this.date.getMinutes();
        this.hour               = this.date.getHours();
        if (this.hour < 12) {
            this.amPm           = Calendar.AM;
        } else {
            this.hour           -= 12;
            this.amPm           = Calendar.PM;
        }
        this.dayOfMonth         = this.date.getDate();
        this.month              = this.date.getMonth();
        this.year               = this.date.getYear() + 1900;
       
    }
    // Converts the current field values in fields[] to the millisecond time value time.
    protected   void computeTime() {
        this.date               = new Date(this.year - 1900, this.month, this.dayOfMonth, this.hour, this.minute, this.second);
    }

    // Overrides Cloneable
    public Object clone() {
        GregorianCalendar newCal                = new GregorianCalendar();
        newCal.setTime(this.date);
        return newCal;
    }

} // end of class GregorianCalendar
