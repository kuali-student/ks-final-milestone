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
 * Created by chongzhu on 9/9/13
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl;

import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.List;

/**
 * This class sorts the {@link RegistrationGroupWrapper} by AO DL Component #1 End Time
 *
 * @author Kuali Student Team
 */
public class RegGroupEndTimeComparator extends KSComparatorBase<RegistrationGroupWrapper> {

    @Override
    public int compare(RegistrationGroupWrapper o1, RegistrationGroupWrapper o2) {
        List<String> o1EndTime = o1.getEndTime();
        List<String>  o2EndTime = o2.getEndTime();
        Long o1End = 0L;
        Long o2End = 0L;

        // always compare the first set of End time
        if(o1EndTime.size() > 0) {
            String  o1Time = o1.getEndTime().get(0);
            if(!o1Time.isEmpty()) {
                o1End = Long.parseLong(o1Time);
            }
        }
        if(o2EndTime.size() > 0) {
            String  o2Time = o2.getEndTime().get(0);
            if(!o2Time.isEmpty()) {
                o2End = Long.parseLong(o2Time);
            }
        }
        return o1End.compareTo(o2End);
    }
}
