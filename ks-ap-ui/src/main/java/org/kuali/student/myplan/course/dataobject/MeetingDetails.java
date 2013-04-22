package org.kuali.student.myplan.course.dataobject;

import java.io.Serializable;

public class MeetingDetails implements Serializable {

	private static final long serialVersionUID = -9146325235552625275L;

	// eg MTWThF
	private String days;
	// eg 10:30 AM - 11:20 AM
	private String time;
	private String building;
	private String room;
	private String campus;

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}
}
