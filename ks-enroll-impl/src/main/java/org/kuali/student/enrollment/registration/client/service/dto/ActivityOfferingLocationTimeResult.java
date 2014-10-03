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
 * Created by vgadiyak on 2/10/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingLocationTimeResult", propOrder = {
        "location", "time"})
public class ActivityOfferingLocationTimeResult {
    private ScheduleLocationResult location;
    private ScheduleTimeResult time;
    private Boolean isTBA;

    public ScheduleLocationResult getLocation() { return location; }

    public void setLocation(ScheduleLocationResult location) { this.location = location; }

    public ScheduleTimeResult getTime() { return time; }

    public void setTime(ScheduleTimeResult time) { this.time = time; }

    public Boolean getIsTBA() {
        return isTBA;
    }

    public void setIsTBA(Boolean isTBA) {
        this.isTBA = isTBA;
    }
}