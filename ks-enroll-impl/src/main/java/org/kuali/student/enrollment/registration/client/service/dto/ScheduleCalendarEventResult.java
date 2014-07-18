package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 1/28/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleCourseResult", propOrder = {
        "text", "colour", "startTimeMin", "durationMin", "sumPrecedingDurations", "position", "numColumns"})
public class ScheduleCalendarEventResult {
    private String text;
    private String colour;
    private int startTimeMin;
    private int durationMin;
    private int sumPrecedingDurations;
    private int position = 0;
    private int numColumns = 0;

    public ScheduleCalendarEventResult() {
        super();
    }

    public ScheduleCalendarEventResult(int durationMin, String text, int startTimeMin, String colour) {
        this.durationMin = durationMin;
        this.text = text;
        this.startTimeMin = startTimeMin;
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getSumPrecedingDurations() {
        return sumPrecedingDurations;
    }

    public void setSumPrecedingDurations(int sumPrecedingDurations) {
        this.sumPrecedingDurations = sumPrecedingDurations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(int startTimeMin) {
        this.startTimeMin = startTimeMin;
    }
}
