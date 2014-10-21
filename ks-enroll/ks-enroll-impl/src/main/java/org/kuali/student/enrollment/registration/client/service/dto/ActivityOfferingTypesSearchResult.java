/**
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
 *
 * Created by vgadiyak on 7/21/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingTypesSearchResult", propOrder = {
        "activityOfferingCode", "activityOfferingType", "activityOfferingTypeName", "activityOfferings"})
public class ActivityOfferingTypesSearchResult implements Serializable {
    private String activityOfferingCode;
    private String activityOfferingTypeName;
    private String activityOfferingType;
    private List<StudentScheduleActivityOfferingResult> activityOfferings;

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public String getActivityOfferingType() {
        return activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    public String getActivityOfferingTypeName() {
        return activityOfferingTypeName;
    }

    public void setActivityOfferingTypeName(String activityOfferingTypeName) {
        this.activityOfferingTypeName = activityOfferingTypeName;
    }

    public List<StudentScheduleActivityOfferingResult> getActivityOfferings() {
        return activityOfferings;
    }

    public void setActivityOfferings(List<StudentScheduleActivityOfferingResult> activityOfferings) {
        this.activityOfferings = activityOfferings;
    }
}

