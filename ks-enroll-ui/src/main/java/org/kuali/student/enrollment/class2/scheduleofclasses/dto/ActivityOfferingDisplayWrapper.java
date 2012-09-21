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
 * Created by vgadiyak on 9/18/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingDisplayWrapper {

    private ActivityOfferingDisplayInfo aoDisplayInfo;
    private String information;
    private String startTimeDisplay;
    private String endTimeDisplay;
    private String daysDisplayName;
    private String buildingName;
    private String roomName;
    private String tbaDisplayName;

    public ActivityOfferingDisplayWrapper(){
        aoDisplayInfo = new ActivityOfferingDisplayInfo();
    }

    public ActivityOfferingDisplayInfo getAoDisplayInfo() {
        return aoDisplayInfo;
    }

    public void setAoDisplayInfo(ActivityOfferingDisplayInfo aoDisplayInfo) {
        this.aoDisplayInfo = aoDisplayInfo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        this.startTimeDisplay = startTimeDisplay;
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        this.endTimeDisplay = endTimeDisplay;
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        this.daysDisplayName = daysDisplayName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTbaDisplayName() {
        return tbaDisplayName;
    }

    public void setTbaDisplayName(boolean tba) {
        tbaDisplayName = StringUtils.EMPTY;
        if (tba){
            tbaDisplayName =  "TBA";
        }
    }
}
