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
 * Created by bobhurt on 6/5/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a place to put Meeting Time & Location form data for activity offerings.
 *
 * @author Kuali Student Team
 */
public class ScheduleComponentWrapper implements Serializable {

    // InputField properties exclusively for the addLine
    private String addDaysSpecifiedBoolean;
    private List<Integer> addWeekDayOptions;
    private String addStartTimeHour;
    private String addStartTimeAmPm;
    private String addEndTimeHour;
    private String addEndTimeAmPm;
    private List<String> addRoomResources;

    // DataField (read-only) properties exclusively for the collection
    private String weekDays;
    private String startTime;
    private String endTime;
    private String roomFeatures;

    // dual-purpose properties
    private String building;
    private String room;
    private String ownership;
    private Integer capacity;

    // constructor
    //public ScheduleComponentWrapper() {
    //}

    public String getAddDaysSpecifiedBoolean() {
        return addDaysSpecifiedBoolean;
    }

    public void setAddDaysSpecifiedBoolean(String addDaysSpecifiedBoolean) {
        this.addDaysSpecifiedBoolean = addDaysSpecifiedBoolean;
    }

    public List<Integer> getAddWeekDayOptions() {
        return addWeekDayOptions;
    }

    public void setAddWeekDayOptions(List<Integer> addWeekDayOptions) {
        this.addWeekDayOptions = addWeekDayOptions;
    }

    public String getAddStartTimeHour() {
        return addStartTimeHour;
    }

    public void setAddStartTimeHour(String addStartTimeHour) {
        this.addStartTimeHour = addStartTimeHour;
    }

    public String getAddStartTimeAmPm() {
        return addStartTimeAmPm;
    }

    public void setAddStartTimeAmPm(String addStartTimeAmPm) {
        this.addStartTimeAmPm = addStartTimeAmPm;
    }

    public String getAddEndTimeHour() {
        return addEndTimeHour;
    }

    public void setAddEndTimeHour(String addEndTimeHour) {
        this.addEndTimeHour = addEndTimeHour;
    }

    public String getAddEndTimeAmPm() {
        return addEndTimeAmPm;
    }

    public void setAddEndTimeAmPm(String addEndTimeAmPm) {
        this.addEndTimeAmPm = addEndTimeAmPm;
    }

    public List<String> getAddRoomResources() {
        return addRoomResources;
    }

    public void setAddRoomResources(List<String> addRoomResources) {
        this.addRoomResources = addRoomResources;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(String roomFeatures) {
        this.roomFeatures = roomFeatures;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
