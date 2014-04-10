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
 * @author Kuali Student Team
 */

package org.kuali.student.cm.course.form;

/**
 * Wrapper for Activities for Review Proposal page.
 */
public class ActivityInfoWrapper {

    private String duration;
    private String durationCount;
    private String contactHours;
    private String activityType;
    private Integer anticipatedClassSize;

    public ActivityInfoWrapper(){

    }

    public ActivityInfoWrapper(String duration, Integer anticipatedClassSize, String activityType, String durationCount, String contactHours) {
        this.durationCount = durationCount;
        setDurationCount(durationCount);
        setDuration(duration);
        this.anticipatedClassSize = anticipatedClassSize;
        this.activityType = activityType;
        this.contactHours = contactHours;
    }

    public String getActivityType() {
        if (activityType == null)
            return "";
        // to get Discussion                  from  kuali.lu.type.activity.Discussion
        //        ExperientialLearningOROther from  kuali.lu.type.activity.ExperientialLearningOROther
        //        Lab                         from  kuali.lu.type.activity.Lab
        return activityType.substring(activityType.lastIndexOf(".") + 1);
    }

    public void setActivityType(String activityType) {
        activityType = activityType;
    }

    public String getContactHours() {
        if (contactHours == null)
            return "";
        return contactHours;
    }

    public void setContactHours(String contactHours) {
        this.contactHours = contactHours;
    }

    public Integer getAnticipatedClassSize() {
        return anticipatedClassSize;
    }

    public void setAnticipatedClassSize(Integer anticipatedClassSize) {
        this.anticipatedClassSize = anticipatedClassSize;
    }

    public String getDurationCount() {
        if (durationCount == null)
            return "";
        return durationCount;
    }

    public void setDurationCount(String durationCount) {
        this.durationCount = durationCount + " Term(s)";
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String durationTypeKey) {
        if (durationTypeKey.contains("Month"))
            this.duration = durationCount + " per month";
        else if (durationTypeKey.contains("Week"))
            this.duration = durationCount + " per week";
        else if (durationTypeKey.contains("Day"))
            this.duration = durationCount + " per day";
    }
}
