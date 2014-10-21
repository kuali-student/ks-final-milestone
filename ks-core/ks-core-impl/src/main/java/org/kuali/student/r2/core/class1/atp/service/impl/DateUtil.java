/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.class1.atp.service.impl;

import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;

import java.util.Date;

/**
 * Utility to manage datesis a date range
 * 
 * For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
 * @author nwright
 */
public class DateUtil {

    private static KSDateTimeFormatter JUST_DATE = DateFormatters.DEFAULT_DATE_FORMATTER;
    private static KSDateTimeFormatter END_OF_DAY = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER;

    public static Date startOfDay(Date date) {
        if (date == null) {
            return null;
        }
        String dateStr = JUST_DATE.format(date);
        try {
            Date newDate = JUST_DATE.parse(dateStr);
            return newDate;
        } catch (Exception ex) {
            throw new IllegalArgumentException(dateStr);
        }
    }

    public static Date endOfDay(Date date) {
        if (date == null) {
            return null;
        }
        String dateStr = JUST_DATE.format(date);
        try {
            Date newDate = END_OF_DAY.parse(dateStr + " 23:59:59.9");
            return newDate;
        } catch (Exception ex) {
            throw new IllegalArgumentException(dateStr);
        }
    }

    /**
     * nulls the end date if is a date range
     * 
     * For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
     */
    public static Date nullIfNotDateRange(boolean isDateRange, Date endDate) {
        if (endDate == null) {
            return null;
        }
        if (!isDateRange) {
            return null;
        }
        return endDate;
    }

    /**
     * truncates date if is all day
     * 
     * For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
     */
    public static Date startOfDayfIsAllDay(boolean isAllDay, Date date) {
        if (date == null) {
            return null;
        }
        if (!isAllDay) {
            return date;
        }
        return DateUtil.startOfDay(date);
    }

    /**
     * truncates date if is all day
     * 
     * For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
     */
    public static Date endOfDayIfIsAllDay(boolean isAllDay, Date date) {
        if (date == null) {
            return null;
        }
        if (!isAllDay) {
            return date;
        }
        return DateUtil.endOfDay(date);
    }
}
