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
 * Created by Charles on 10/9/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingTypePrioritizer {
    public static final List<String> ACTIVITY_OFFERING_TYPE_PRIORITY_LIST;
    static {
        ACTIVITY_OFFERING_TYPE_PRIORITY_LIST = new ArrayList<String>();
        // AOs are added in priority order.  Those with smaller indices have higher priority.
        // Those that are not in the list are considered equivalent with the highest priority
        ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.add(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.add(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.add(LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY);
    }

    /**
     * Gives relative comparison based on the usual ways of comparing two numbers.
     * @param aoTypeFirst
     * @param aoTypeSecond
     * @return negative if aoTypeFirst has higher priority than aoTypeSecond
     *         zero if the two have same priority
     *         positive if aoTypeSecond has higher priority than aoTypeFirst
     */
    public static int compare(String aoTypeFirst, String aoTypeSecond) {
        int firstPriority = ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.indexOf(aoTypeFirst);
        int secondPriority = ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.indexOf(aoTypeSecond);
        if (firstPriority == -1) {
            // Force this to be "lowest" priority
            firstPriority = ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.size();
        }
        if (secondPriority == -1) {
            // Force this to be "lowest" priority
            secondPriority = ACTIVITY_OFFERING_TYPE_PRIORITY_LIST.size();
        }
        return firstPriority - secondPriority;
    }
}
