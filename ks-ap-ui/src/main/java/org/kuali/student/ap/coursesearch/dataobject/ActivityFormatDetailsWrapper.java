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

import java.util.List;

public class ActivityFormatDetailsWrapper {
    private String formatName;
    private String formatType;
    private String termId;
    private String courseOfferingCode;
    private String formatOfferingId;
    private List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers;

    public ActivityFormatDetailsWrapper(String termId, String courseOfferingCode, String formatOfferingId, String activityFormatName, String activityTypeKey) {
        formatName = activityFormatName;
        formatType = activityTypeKey;
        this.termId = termId;
        this.courseOfferingCode = courseOfferingCode;
        this.formatOfferingId = formatOfferingId;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatType() {
        return formatType;
    }

    /**
     * Get an XML safe representation of the formatType by replacing "." with "-"
     * @return A formatType with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeFormatType() {
        return formatType.replace(".", "-");
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getTermId() {
        return termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public List<ActivityOfferingDetailsWrapper> getActivityOfferingDetailsWrappers() {
        return activityOfferingDetailsWrappers;
    }

    public void setActivityOfferingDetailsWrappers(List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers) {
        this.activityOfferingDetailsWrappers = activityOfferingDetailsWrappers;
    }
}
