package org.kuali.student.enrollment.class2.courseoffering.dto;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 4/13/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleCalcContainer {


    private String aoId;
    private String scheduleId;
    private String start;
    private String end;
    private String weekdays;
    private String scheduleType; // requested or actual
    private String roomCode;
    private String bldgName;
    private String bldgCode;
    private Boolean tbaInd;

    public ScheduleCalcContainer(String aoId, String scheduleId, String scheduleType, String start, String end,String weekdays, String roomCode, String bldgName, String bldgCode, Boolean tbaInd) {
        this.aoId = aoId;
        this.end = end;
        this.scheduleId = scheduleId;
        this.scheduleType = scheduleType;
        this.start = start;
        this.weekdays = weekdays;
        this.roomCode = roomCode;
        this.bldgName = bldgName;
        this.bldgCode = bldgCode;
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

    public String getBldgCode() {
        return bldgCode;
    }

    public void setBldgCode(String bldgCode) {
        this.bldgCode = bldgCode;
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
