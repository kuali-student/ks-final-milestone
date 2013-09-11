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
 * Created by chongzhu on 9/6/13
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl;

import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.List;

import static org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil.weekdaysString2WeekdaysList;

/**
 * This class sorts the {@link RegistrationGroupWrapper} by AO DL Component Days of the week
 *
 * @author Kuali Student Team
 */
public class RegGroupDaysOfWeekComparator extends KSComparatorBase<RegistrationGroupWrapper> {
    @Override
    public int compare(RegistrationGroupWrapper o1, RegistrationGroupWrapper o2) {
        List<String> o1Days = o1.getWeekDays();
        List<String>  o2Days = o2.getWeekDays();
        List<Integer> o1WeekOfDays;
        List<Integer> o2WeekOfDays;

        int firstSetDays = 0;
        Integer o1Day = 0;
        Integer o2Day = 0;

        int setDays = o1Days.size() < o2Days.size()? o1Days.size(): o2Days.size();
        if(setDays > 0) {

            /* KSENROLL-9445
            * A DL can have multiple components (hence the list); but we only need to analyze the first one for sorting
            * purposes (hence the call to get-0 call).  In this case, it should not be regarded as a Sonar-violation
            * and can be Sonar-ignored.
            */
            String o1FirstSetDays = o1Days.get(firstSetDays);
            String o2FirstSetDays = o2Days.get(firstSetDays);
            o1WeekOfDays =  weekdaysString2WeekdaysList(o1FirstSetDays);
            o2WeekOfDays =  weekdaysString2WeekdaysList(o2FirstSetDays);
            if(!o1WeekOfDays.isEmpty())  {
                o1Day = o1WeekOfDays.size();
            }
            if(!o2WeekOfDays.isEmpty())  {
                o2Day = o2WeekOfDays.size();
            }
            int minLength = o1Day < o2Day? o1Day : o2Day;
            for ( int i = 0; i < minLength; i++ ) {
                if(o1WeekOfDays.get(i).compareTo(o2WeekOfDays.get(i)) == 0) {
                    o1Day = o1WeekOfDays.get(i);
                    o2Day = o2WeekOfDays.get(i);
                } else {
                    return o1WeekOfDays.get(i).compareTo(o2WeekOfDays.get(i));
                }
            }
        }

        return o1Day.compareTo(o2Day);
    }
}
