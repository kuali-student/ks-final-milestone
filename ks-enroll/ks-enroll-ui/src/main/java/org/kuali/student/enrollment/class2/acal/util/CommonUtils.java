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
package org.kuali.student.enrollment.class2.acal.util;

import org.kuali.student.enrollment.class2.acal.dto.TimeSetWrapper;
import org.kuali.student.r2.common.dto.RichTextInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CommonUtils {
    public static void assembleTimeSet(TimeSetWrapper timeSetWrapper, Date startDate, Date endDate) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        if (startDate !=null) {
            String startDateFullString = formatter.format(startDate);
            String[] timeStr = startDateFullString.split(" ");
            timeSetWrapper.setStartDate(new SimpleDateFormat("MM/dd/yyyy").parse(timeStr[0]));
            if (!"12:00".equals(timeStr[1])){
                timeSetWrapper.setStartTime(timeStr[1]);
            }
            timeSetWrapper.setStartTimeAmPm(timeStr[2].toLowerCase());
        }

        if (endDate !=null) {
            String endDateFullString = formatter.format(endDate);
            String[] timeStr = endDateFullString.split(" ");
            timeSetWrapper.setEndDate(new SimpleDateFormat("MM/dd/yyyy").parse(timeStr[0]));
            if (!"12:00".equals(timeStr[1])){
                timeSetWrapper.setEndTime(timeStr[1]);
            }
            timeSetWrapper.setEndTimeAmPm(timeStr[2].toLowerCase());

        }
    }

    public static RichTextInfo buildDesc(String descr){
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(descr);
        return rti;
    }

    public static Date getStartDate(TimeSetWrapper timeSetWrapper) throws Exception {
        Date startDate = timeSetWrapper.getStartDate();
        String startTime =  timeSetWrapper.getStartTime();
        String amPm = timeSetWrapper.getStartTimeAmPm();
        Date fullStartDate = getDate(startDate, startTime, amPm);

        return fullStartDate;
    }

    public static Date getEndDate(TimeSetWrapper timeSetWrapper) throws Exception {
        Date endate = timeSetWrapper.getEndDate();
        String endTime =  timeSetWrapper.getEndTime();
        String amPm = timeSetWrapper.getEndTimeAmPm();
        Date fullEndDate = getDate(endate, endTime, amPm);

        return fullEndDate;
    }


    public static Date getDate(Date adate, String atime,  String ampm) throws Exception {
        Date fullDate = null;
        if (ampm == null)
            ampm = "am";

        if(adate != null){
            if(atime != null && !atime.isEmpty()){
                String fullDateString = new SimpleDateFormat("MM/dd/yyyy").format(adate);
                fullDateString = fullDateString.concat(" " + atime + " " + ampm);
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                fullDate = formatter.parse(fullDateString);
            }
            else {
                fullDate = adate;
            }
        }

        return fullDate;

    }

}
