package org.kuali.student.enrollment.uif.control;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.control.ControlBase;
import org.kuali.rice.krad.uif.element.Header;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
public class ScheduleControl extends ControlBase {
    private Header header;
    private List<String> days;
    private List<String> times;
    private boolean sundayFirst;
    //arbitrary defaults for start and endtime
    private Integer startTime = 5;
    private Integer endTime = 20;
    private boolean useLetterKeys;
    private boolean omitWeekend;
    private boolean omitAMPMSuffix;
    private boolean militaryTime;
    private String dayType;


    public static class dayTypes {
        public static final String ONE_LETTER = "ONE_LETTER";
        public static final String TWO_LETTER = "TWO_LETTER";
        public static final String SHORT = "SHORT";
        public static final String FULL = "FULL";
    }

    private static final String ZEROS = ":00";

    public String getScheduleOptions() {
        return "{" +
                "sundayFirst: " + sundayFirst + ", " +
                "startTime: " + startTime + ", " +
                "endTime: " + endTime + ", " +
                "useLetterKeys: " + useLetterKeys + ", " +
                "omitWeekend: " + omitWeekend +
                "}";
    }

    public List<String> getDays() {
        //International - based on locale
        Locale usersLocale = Locale.getDefault();

        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String weekdays[] = dfs.getWeekdays();
        days = new ArrayList<String>(8);
        days.add("&nbsp;");

        if (StringUtils.isEmpty(dayType) || dayType.equalsIgnoreCase(dayTypes.FULL) ||
                dayType.equalsIgnoreCase(dayTypes.SHORT)) {

            if (StringUtils.isEmpty(dayType)) {
                //default when not set is short
                dayType = dayTypes.SHORT;
            }

            if (dayType.equalsIgnoreCase(dayTypes.SHORT)) {
                weekdays = dfs.getShortWeekdays();
            }

            days.add(weekdays[Calendar.MONDAY]);
            days.add(weekdays[Calendar.TUESDAY]);
            days.add(weekdays[Calendar.WEDNESDAY]);
            days.add(weekdays[Calendar.THURSDAY]);
            days.add(weekdays[Calendar.FRIDAY]);

            if (sundayFirst && !omitWeekend) {
                days.add(0, weekdays[Calendar.SUNDAY]);
                days.add(weekdays[Calendar.SATURDAY]);
            } else if (!omitWeekend) {
                days.add(weekdays[Calendar.SATURDAY]);
                days.add(weekdays[Calendar.SUNDAY]);
            }
        } else if (dayType.equalsIgnoreCase(dayTypes.ONE_LETTER) ||
                dayType.equalsIgnoreCase(dayTypes.TWO_LETTER)) {

            int end = 1;
            if (dayType.equalsIgnoreCase(dayTypes.TWO_LETTER)) {
                end = 2;
            }

            days.add(weekdays[Calendar.MONDAY].substring(0, end));
            days.add(weekdays[Calendar.TUESDAY].substring(0, end));
            days.add(weekdays[Calendar.WEDNESDAY].substring(0, end));
            days.add(weekdays[Calendar.THURSDAY].substring(0, end));
            days.add(weekdays[Calendar.FRIDAY].substring(0, end));

            if (sundayFirst && !omitWeekend) {
                days.add(0, weekdays[Calendar.SUNDAY].substring(0, end));
                days.add(weekdays[Calendar.SATURDAY].substring(0, end));
            } else if (!omitWeekend) {
                days.add(weekdays[Calendar.SATURDAY].substring(0, end));
                days.add(weekdays[Calendar.SUNDAY].substring(0, end));
            }
        }
        return days;
    }

    public List<String> getTimes() {

        String am = "";
        String pm = "";
        if (!omitAMPMSuffix) {
            //international ampm symbols
            Locale usersLocale = Locale.getDefault();
            DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
            String ampm[] = dfs.getAmPmStrings();
            am = ampm[Calendar.AM];
            pm = ampm[Calendar.PM];
        }

        times = new ArrayList<String>();

        for (int i = startTime; i < endTime; i++) {
            if (!militaryTime) {
                if (i > 12) {
                    times.add("" + (i - 12) + ZEROS + pm);
                }
                else if (i == 12){
                    times.add("" + i + ZEROS + pm);
                }
                else if (i == 0) {
                    times.add("12" + ZEROS + am);
                } else {
                    times.add("" + i + ZEROS + am);
                }

            } else {
                times.add("" + i + ZEROS);
            }
        }

        return times;
    }

    public boolean isSundayFirst() {
        return sundayFirst;
    }

    public void setSundayFirst(boolean sundayFirst) {
        this.sundayFirst = sundayFirst;
    }

    public boolean isUseLetterKeys() {
        return useLetterKeys;
    }

    public void setUseLetterKeys(boolean useLetterKeys) {
        this.useLetterKeys = useLetterKeys;
    }

    public boolean isOmitWeekend() {
        return omitWeekend;
    }

    public void setOmitWeekend(boolean omitWeekend) {
        this.omitWeekend = omitWeekend;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public boolean isOmitAMPMSuffix() {
        return omitAMPMSuffix;
    }

    public void setOmitAMPMSuffix(boolean omitAMPMSuffix) {
        this.omitAMPMSuffix = omitAMPMSuffix;
    }

    public boolean isMilitaryTime() {
        return militaryTime;
    }

    public void setMilitaryTime(boolean militaryTime) {
        this.militaryTime = militaryTime;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
