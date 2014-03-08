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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.MutableDateTime;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides common utility methods related to acal logic
 *
 * @author Kuali Student Team
 */
public class AcalCommonUtils {


    public static boolean isValidDateRange(Date startDate,Date endDate){
        if(startDate != null && endDate != null) {
            if (startDate.after(endDate) || endDate.before(startDate)){
                return false;
            }
        }
        return true;
    }

    public static String formatDate(Date date){
        return DateFormatUtils.format(date, CalendarConstants.DEFAULT_DATE_FORMAT);
    }

    public static boolean isDateWithinRange(Date startDate,Date endDate,Date checkDate){
        if(startDate != null && endDate != null && checkDate != null) {
            if ((!checkDate.equals(startDate) &&  checkDate.before(startDate)) || (!checkDate.equals(endDate) && checkDate.after(endDate))){
                return false;
            }
        }
        return true;
    }

    /**
     * Allows you to see if a date range overlaps another date range.
     *
     * This can be used to determine if a Holiday Calendar overlaps an Academic Calendar.
     *
     * @param periodStartDate
     * @param periodEndDate
     * @param subStart
     * @param subEnd
     * @return
     */
    public static boolean doDatesOverlap(Date periodStartDate, Date periodEndDate, Date subStart, Date subEnd){
        boolean bRet = false;

        int compStart = subStart.compareTo(periodEndDate);
        int compEnd = subEnd.compareTo(periodStartDate);
        if (compStart <= 0 && compEnd >= 0) {
            bRet = true;
        }

        return bRet;
    }

    public static RichTextInfo buildDesc(String descr){
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(descr);
        return rti;
    }

    public static Date getDateWithTime(Date date, String hourMinute, String amPm) {
        if (null == date) {
            return null;
        }

        MutableDateTime dateTime = new MutableDateTime(date);

        if (StringUtils.isNotBlank(hourMinute)) {
            int hour = Integer.parseInt(StringUtils.substringBefore(hourMinute,":"));
            if (StringUtils.equalsIgnoreCase(amPm,"PM")) {
                // 24-hour clock; e.g. 12 PM = hour 12; 8 PM = hour 20
                if (hour < 12) {
                    hour += 12;
                }
            }
            else // if amPm is blank/null, assume AM
            if (hour == 12) {
                hour = 0;  // 12 AM is stored in Calendar as hour 0
            }
            dateTime.setTime(hour, Integer.parseInt(StringUtils.substringAfter(hourMinute,":")), 0, 0);
        }

        return dateTime.toDate();
    }

    public static String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrgName = allHcOrgs.get(id);
        }

        return adminOrgName;
    }

    public static AttributeInfo createAttribute(String key, String value) {
        AttributeInfo newAttr = new AttributeInfo();
        newAttr.setKey(key);
        newAttr.setValue(value);
        return newAttr;
    }
}
