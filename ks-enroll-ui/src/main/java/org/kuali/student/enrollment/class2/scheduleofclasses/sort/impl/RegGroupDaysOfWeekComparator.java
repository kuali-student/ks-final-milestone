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

import com.google.common.collect.ImmutableMap;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.List;

/**
 * This class sorts the {@link RegistrationGroupWrapper} by AO DL Component Days of the week
 *
 * @author Kuali Student Team
 */
public class RegGroupDaysOfWeekComparator extends KSComparatorBase<RegistrationGroupWrapper> {
    static final ImmutableMap<String, Integer> DAYS_MAP = ImmutableMap.<String, Integer>builder()
            .put("M", 1)
            .put("T", 2)
            .put("W", 3)
            .put("H", 4)
            .put("F", 5)
            .put("S", 6)
            .put("U", 7)
            .build();

    @Override
    public int compare(RegistrationGroupWrapper o1, RegistrationGroupWrapper o2) {
        List<String> o1Days = o1.getWeekDays();
        List<String>  o2Days = o2.getWeekDays();
        Integer o1Day = 0;
        Integer o2Day = 0;
        int setDays = o1Days.size() < o2Days.size()? o1Days.size(): o2Days.size();
        if(setDays > 0) {
            String o1FirstSetDays = o1Days.get(0);
            String o2FirstSetDays = o2Days.get(0);
            int minLength = o1FirstSetDays.length() < o2FirstSetDays.length()? o1FirstSetDays.length() : o2FirstSetDays.length();
            for ( int i = 0; i < minLength; i++ ) {
                char c = o1FirstSetDays.charAt( i );
                String chString = Character.toString(c);
                o1Day += DAYS_MAP.get(chString);

                c = o2FirstSetDays.charAt( i );
                chString = Character.toString(c);
                o2Day += DAYS_MAP.get(chString);
            }
        }

        return o1Day.compareTo(o2Day);
    }

}
