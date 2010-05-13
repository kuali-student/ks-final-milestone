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

package org.kuali.student.brms.devgui.client;

import java.util.Date;

/**
 * <dl>
 * <dt><b>Title: </b><dd>Calendar</dd>
 * <p>
 * <dt><b>Description: </b><dd>java.util.Calendar replacment</dd>
 * </dl>
 * @author <a href="mailto:andre.freller@gmail.com">Andre Freller</a>
 * @version $Revision: 0.1 $
 */
///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
public abstract class Calendar
{
    public static final int AM                  = 0;
    public static final int AM_PM               = 9;
    public static final int PM                  = 1;

    public static final int JANUARY             = 0;
    public static final int FEBRUARY            = 1;
    public static final int MARCH               = 2;
    public static final int APRIL               = 3;
    public static final int MAY                 = 4;
    public static final int JUNE                = 5;
    public static final int JULY                = 6;
    public static final int AUGUST              = 7;
    public static final int SEPTEMBER           = 8;
    public static final int OCTOBER             = 9;
    public static final int NOVEMBER            = 10;
    public static final int DECEMBER            = 11;
    public static final int UNDECIMBER          = 12;

    public static final int SUNDAY              = 1;
    public static final int MONDAY              = 2;
    public static final int TUESDAY             = 3;
    public static final int WEDNESDAY           = 4;
    public static final int THURSDAY            = 5;
    public static final int FRIDAY              = 6;
    public static final int SATURDAY            = 7;


    public static final int ERA                 = 0;
    public static final int YEAR                = 1;
    public static final int MONTH               = 2;
    public static final int WEEK_OF_YEAR        = 3;
    public static final int WEEK_OF_MONTH       = 4;
    public static final int DATE                = 5;
    public static final int DAY_OF_MONTH        = DATE;
    public static final int DAY_OF_YEAR         = 6;
    public static final int DAY_OF_WEEK         = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int HOUR                = 10;
    public static final int HOUR_OF_DAY         = 11;
    public static final int MINUTE              = 12;
    public static final int SECOND              = 13;
    public static final int MILLISECOND         = 14;
    public static final int ZONE_OFFSET         = 15;
    public static final int DST_OFFSET          = 16;
    public static final int FIELD_COUNT         = 17;


    // fields
    protected Date date                 = null;
    protected int second                = -1;
    protected int minute                = -1;
    protected int hour                  = -1;
    protected int dayOfMonth            = -1;
    protected int month                 = -1;
    protected int year                  = -1;
    protected int amPm                  = -1;

    protected int firstWeekDayOfWeek    = Calendar.SUNDAY;

    protected Calendar() {
        setTime(new Date());
    }

    // Sets this Calendar's current time with the given Date.
    public void setTime(Date date) {
        this.date               = date;
        computeFields();
    }
    public void setTime(Calendar cal) {
        this.date               = cal.getTime();
        computeFields();
    }
    // Sets this Calendar's current time from the given long value.
    public void setTimeInMillis(long milliSeconds) {
        this.date.setTime(milliSeconds);
        computeFields();
    }
    // Sets the values for the fields year, month, and day.
    public void set(int year, int month, int dayOfMonth) {
        this.year               = year;
        this.month              = month;
        this.dayOfMonth         = dayOfMonth;
        computeTime();
    }
    // Sets the values for the fields year, month, day, hour, and minute.
    public void set(int year, int month, int dayOfMonth, int hour) {
        this.year               = year;
        this.month              = month;
        this.dayOfMonth         = dayOfMonth;
        this.hour               = hour;
        computeTime();
    }
    // Sets the values for the fields year, month, day, hour, and minute.
    public void set(int year, int month, int dayOfMonth, int hour, int minute) {
        this.year               = year;
        this.month              = month;
        this.dayOfMonth         = dayOfMonth;
        this.hour               = hour;
        this.minute             = minute;
        computeTime();
    }
    // Sets the values for the fields year, month, day, hour, minute, and second.
    public void set(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        this.year               = year;
        this.month              = month;
        this.dayOfMonth         = dayOfMonth;
        this.hour               = hour;
        this.minute             = minute;
        this.second             = second;
        computeTime();
    }
    // Sets the time field with the given value.
    public void set(int fieldCode, String value) {
        set(fieldCode, Integer.parseInt(value));
    }
    public abstract void set(int fieldCode, int value);

    // Sets what the first day of the week is; e.g., Sunday in US, Monday in France.
    public void setFirstWeekDayOfWeek(int firstWeekDayOfWeek) {
        this.firstWeekDayOfWeek = firstWeekDayOfWeek;
    }


    // Gets this Calendar's current time.
    public Date getTime() {
        return this.date;
    }
    // Gets this Calendar's current time as a long.
    public long getTimeInMillis() {
        return this.date.getTime();
    }


    // Gets the value for a given time field.
    public int get(int fieldCode) {
        switch (fieldCode) {
        case Calendar.SECOND:
            return this.second;
        case Calendar.MINUTE:
            return this.minute;
        case Calendar.HOUR:
            return this.hour;
        case Calendar.HOUR_OF_DAY:
            if (this.amPm == Calendar.PM)
                return this.hour + 12;
            return this.hour;
        case Calendar.DATE:
            return this.dayOfMonth;
        case Calendar.MONTH:
            return this.month;
        case Calendar.YEAR:
            return this.year;
        case Calendar.AM_PM:
            return this.amPm;
        default:
            return -1;
        }
    }
    // Gets what the first day of the week is; e.g., Sunday in US, Monday in France.
    public int getFirstWeekDayOfWeek() {
        return this.firstWeekDayOfWeek;
    }



    // Compares the time field records.
    public boolean before(Date when) {
        return this.date.before(when);
    }
    public boolean before(Calendar when) {
        return this.date.before(when.getTime());
    }
    public boolean after(Date when) {
        return this.date.after(when);
    }
    public boolean after(Calendar when) {
        return this.date.after(when.getTime());
    }
    public boolean equals(Date obj) {
        return this.date.equals(obj);
    }
    public boolean equals(Calendar obj) {
        return this.date.equals(obj.getTime());
    }
    // Determines if the given time field has a value set.
    public boolean isSet(int fieldCode) {
        return true;
    }


    // Time Field Rolling function.
    public void roll(int fieldCode, boolean up) {
        if (up)
            roll(fieldCode, 1);
        else
            roll(fieldCode, -1);
    }

    // Fills in any unset fields in the time field list.
    protected  void complete() {
        computeFields();
    }

    // Returns a hash code for this calendar.
    public int hashCode() {
        return this.date.hashCode();
    }
    // Return a string representation of this calendar.
    public String toString() {
        return this.date.toString();
    }



    // Converts the current millisecond time value time to field values in fields[].
    protected abstract void computeFields();

    // Converts the current field values in fields[] to the millisecond time value time.
    protected abstract void computeTime();


    // Gets the minimum value for the given time field.
    public abstract int getMinimum(int fieldCode);
    // Return the minimum value that this field could have, given the current date.
    public abstract int getActualMinimum(int fieldCode);
    // Gets the highest minimum value for the given field if varies.
    public abstract int getGreatestMinimum(int fieldCode);

    // Gets the maximum value for the given time field.
    public abstract int getMaximum(int fieldCode);
    // Return the maximum value that this field could have, given the current date.
    public abstract int getActualMaximum(int fieldCode);
    // Gets the lowest maximum value for the given field if varies.
    public abstract int getLeastMaximum(int fieldCode);


    // Date Arithmetic function.
    public abstract void add(int fieldCode, int amount);

    // Time Field Rolling function.
    public abstract void roll(int fieldCode, int amount);


} // end of class Calendar
