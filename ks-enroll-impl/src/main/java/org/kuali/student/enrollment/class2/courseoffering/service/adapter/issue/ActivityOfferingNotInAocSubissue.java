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
 * Created by Charles on 2/21/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue;

import java.util.HashSet;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingNotInAocSubissue implements FormatOfferingAutogenSubIssue {
    private Set<String> activityOfferingIds;
    private String courseOfferingId;
    private String formatOfferingId;

    public ActivityOfferingNotInAocSubissue(String courseOfferingId, String formatOfferingId) {
        this.activityOfferingIds = new HashSet<String>();
        this.courseOfferingId = courseOfferingId;
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public String getName() {
        return FormatOfferingAutogenSubIssue.ACTIVITY_OFFERINGS_NOT_IN_AOC;
    }

    public Set<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }
}
