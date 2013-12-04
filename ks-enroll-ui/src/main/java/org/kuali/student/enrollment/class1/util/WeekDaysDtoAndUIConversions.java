/**
   * Copyright 2013 The Kuali Foundation Licensed under the
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
   * Created by chongzhu on 10/1/13
   */
package org.kuali.student.enrollment.class1.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
   * This class contains the methods to convert  days of the week
   *
   * @author Kuali Student Team
   */
  public class WeekDaysDtoAndUIConversions {

     public static String buildDaysForUI(List<Integer> days){

         StringBuilder returnValue = new StringBuilder();

         for (Integer day : days) {
             switch (day){
                 case Calendar.MONDAY:
                     returnValue.append("M ");
                     break;
                 case Calendar.TUESDAY:
                     returnValue.append("T ");
                     break;
                 case Calendar.WEDNESDAY:
                     returnValue.append("W ");
                     break;
                 case Calendar.THURSDAY:
                     returnValue.append("H ");
                     break;
                 case Calendar.FRIDAY:
                     returnValue.append("F ");
                     break;
                 case Calendar.SATURDAY:
                     returnValue.append("S ");
                     break;
                 case Calendar.SUNDAY:
                     returnValue.append("U ");
                     break;
             }
         }

         return StringUtils.removeEnd(returnValue.toString(), " ");
     }

    public static List<Integer> buildDaysForDTO(String days){

         List<Integer> weekdays  = new ArrayList<Integer>();

         if(days != null) {

             if (StringUtils.containsIgnoreCase(days,"M")){
                 weekdays.add(Calendar.MONDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"T")){
                 weekdays.add(Calendar.TUESDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"W")){
                 weekdays.add(Calendar.WEDNESDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"H")){
                 weekdays.add(Calendar.THURSDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"F")){
                 weekdays.add(Calendar.FRIDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"S")){
                 weekdays.add(Calendar.SATURDAY);
             }

             if (StringUtils.containsIgnoreCase(days,"U")){
                 weekdays.add(Calendar.SUNDAY);
             }

         }

         return weekdays;
     }

    public static boolean isValidDays(String days){
        if (StringUtils.isNotBlank(days)){
            days = StringUtils.upperCase(days);
            String validDays = "MTWHFSU";
            return StringUtils.containsOnly(days,validDays);
        }
        return false;
    }
 }
