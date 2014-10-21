/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.ap.coursesearch.dataobject;

import java.util.ArrayList;
import java.util.List;

public class PlannedRegistrationGroupDetailsWrapper {

    private String regGroupCode;
    private List<ActivityOfferingDetailsWrapper> activities;

    public String getRegGroupCode() {
        return regGroupCode;
    }

    public void setRegGroupCode(String regGroupCode) {
        this.regGroupCode = regGroupCode;
    }

    public List<ActivityOfferingDetailsWrapper> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityOfferingDetailsWrapper> activities) {
        this.activities = activities;
    }
    public void addActivities(ActivityOfferingDetailsWrapper activity) {
        if(activities == null) activities = new ArrayList<ActivityOfferingDetailsWrapper>();
        activities.add(activity);
    }
}
