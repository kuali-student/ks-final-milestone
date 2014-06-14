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
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

/**
 * This comparator sorts the {@link ActivityOfferingWrapper} by activity code.
 *
 * @author Kuali Student Team
 */

public class ActivityOfferingCodeComparator extends KSComparatorBase<ActivityOfferingWrapper> {

    /**
     * Sorts the activity code in alphabetical order like a,b,c.....x,y,z,aa,ab....az,ba etc.
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(ActivityOfferingWrapper o1, ActivityOfferingWrapper o2) {

        if (StringUtils.length(o1.getActivityCode()) > StringUtils.length(o2.getActivityCode())){
            return 1;
        } else if (StringUtils.length(o1.getActivityCode()) < StringUtils.length(o2.getActivityCode())){
            return -1;
        } else {
            return o1.getActivityCode().compareTo(o2.getActivityCode());
        }

    }

}
