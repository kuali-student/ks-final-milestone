package org.kuali.student.enrollment.class2.autogen.dto;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 4/13/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleCalcContainer {


    String aoId;
    String scheduleId;
    String start;
    String end;
    String weekdays;
    String scheduleType; // requested or actual
    String roomCode;
    String bldgName;
    Boolean tbaInd;

    public ScheduleCalcContainer(String aoId, String scheduleId, String scheduleType, String start, String end,String weekdays, String roomCode, String bldgName, Boolean tbaInd) {
        this.aoId = aoId;
        this.end = end;
        this.scheduleId = scheduleId;
        this.scheduleType = scheduleType;
        this.start = start;
        this.weekdays = weekdays;
        this.roomCode = roomCode;
        this.bldgName = bldgName;
        this.tbaInd = tbaInd;
    }

    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getBldgName() {
        return bldgName;
    }

    public void setBldgName(String bldgName) {
        this.bldgName = bldgName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Boolean getTbaInd() {
        return tbaInd;
    }

    public void setTbaInd(Boolean tbaInd) {
        this.tbaInd = tbaInd;
    }
}
