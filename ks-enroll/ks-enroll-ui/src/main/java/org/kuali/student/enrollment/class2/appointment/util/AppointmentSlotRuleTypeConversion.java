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
 * Created by Adi Rajesh on 4/11/12
 */
package org.kuali.student.enrollment.class2.appointment.util;


import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AppointmentSlotRuleTypeConversion {

    public static int SECOND_IN_MILLIS = 1000;
    public static int MINUTE_IN_SECS =  60;
    public static int HOUR_IN_MINUTES = 60;
    public static String DELIMITER = ";";

    //Converting appt. rule type code to object
    // Ex: 1,3,4;8;4;kuali.type.duration.minutes;15;kuali.type.duration.minutes;0
    public static AppointmentSlotRuleInfo convToAppointmentSlotRuleInfo(String slotRuleTypeCode){
        AppointmentSlotRuleInfo slotRule = new AppointmentSlotRuleInfo();
        String[] codeWords = slotRuleTypeCode.split(";");
        String weekDaysArray[] = codeWords[0].split(",");
        if(weekDaysArray.length >0){
            List<Integer> weekdays = new ArrayList<Integer>();
            for (String s : weekDaysArray) {
                weekdays.add(Integer.parseInt(s));
            }
            slotRule.setWeekdays(weekdays);
        }
        
        slotRule.setStartTimeOfDay(convertToTimeOfDayInfo(convertToMilliSecs(codeWords[1])));
        codeWords[2] = String.valueOf(Integer.parseInt(codeWords[2]) + 12);
        slotRule.setEndTimeOfDay(convertToTimeOfDayInfo(convertToMilliSecs(codeWords[2])));
        slotRule.setSlotStartInterval(convertToTimeAmountInfo(codeWords[3],Integer.parseInt(codeWords[4])));
        slotRule.setSlotDuration(convertToTimeAmountInfo(codeWords[5],Integer.parseInt(codeWords[6])));
        return slotRule;
    }

    //Converting appt. rule type object to code
    public static String convTotAppointmentSlotRuleCode(AppointmentSlotRuleInfo slotRuleInfo){
        String slotRule = "";
        StringBuilder tempSlotRule = new StringBuilder();

        if(slotRuleInfo != null){
            if(slotRuleInfo.getWeekdays() != null){
                // Generate comma delimited days of week to save (max length is 13 characters)
                List<Integer> weekdays = slotRuleInfo.getWeekdays(); // not null
                for (Integer day : weekdays) {
                    if (tempSlotRule.length() > 0) {
                        tempSlotRule.append(",");
                    }
                    tempSlotRule.append(day);
                }
            }
            tempSlotRule.append(DELIMITER + convertToHrs(slotRuleInfo.getStartTimeOfDay().getMilliSeconds()));
            tempSlotRule.append(DELIMITER + convertToHrs(slotRuleInfo.getEndTimeOfDay().getMilliSeconds()));
            tempSlotRule.append(DELIMITER + slotRuleInfo.getSlotStartInterval().getAtpDurationTypeKey());
            tempSlotRule.append(DELIMITER + slotRuleInfo.getSlotStartInterval().getTimeQuantity());
            tempSlotRule.append(DELIMITER + slotRuleInfo.getSlotDuration().getAtpDurationTypeKey());
            tempSlotRule.append(DELIMITER + slotRuleInfo.getSlotDuration().getTimeQuantity());
            slotRule = tempSlotRule.toString();
        }
        return slotRule;
    }

    private static TimeOfDayInfo convertToTimeOfDayInfo(Long time) {
        if(time == null){
            return null;
        }
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(time);
        return info;
    }

    private static TimeAmountInfo convertToTimeAmountInfo(String typeKey, Integer quantity) {
        if ((typeKey == null) && (quantity == null)) {
            return null;
        }
        TimeAmountInfo info = new TimeAmountInfo();
        info.setAtpDurationTypeKey(typeKey);
        if (quantity != null) {
            info.setTimeQuantity(quantity);
        }
        return info;
    }
    
    private static Long convertToMilliSecs(String time_in_hrs){
        String[] timeSplit = time_in_hrs.split(":");  // hours and minutes
        Long time_in_millis = 0L;
        if(timeSplit.length>1) {
            timeSplit[0] = timeSplit[0].trim();
            timeSplit[1] = timeSplit[1].trim();
            time_in_millis = Long.parseLong(timeSplit[0]) * HOUR_IN_MINUTES * MINUTE_IN_SECS * SECOND_IN_MILLIS;
            time_in_millis = time_in_millis + Long.parseLong(timeSplit[1]) * MINUTE_IN_SECS * SECOND_IN_MILLIS;
        } else {
                time_in_millis = Long.parseLong(timeSplit[0]) * HOUR_IN_MINUTES * MINUTE_IN_SECS * SECOND_IN_MILLIS;
        }
        return time_in_millis;
    }

    private static String convertToHrs(Long milliSecs){
        String hrs = "";
        if(milliSecs%(SECOND_IN_MILLIS * MINUTE_IN_SECS * HOUR_IN_MINUTES)==0){
            int temp =  (int)(milliSecs/(SECOND_IN_MILLIS * MINUTE_IN_SECS * HOUR_IN_MINUTES));
            if(temp > 12) temp = temp -12;
            hrs = String.valueOf(temp);
        }else{
            int temp = (int)(milliSecs/(SECOND_IN_MILLIS * MINUTE_IN_SECS));
            int temp1 = temp/60; int temp2 = temp%60;
            if(temp1 > 12) temp1 = temp1-12;
            hrs = String.valueOf(temp1) + ":" + String.valueOf(temp2);
        }
        return hrs;
    }

}
