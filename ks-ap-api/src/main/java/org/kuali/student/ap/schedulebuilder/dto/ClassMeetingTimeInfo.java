package org.kuali.student.ap.schedulebuilder.dto;

import org.kuali.student.ap.schedulebuilder.infc.ClassMeetingTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassMeetingTimeInfo", propOrder = { "id", "uniqueId",
		"instructorName", "location", "arranged" })
public class ClassMeetingTimeInfo extends ScheduleBuildEventInfo implements
		ClassMeetingTime {

	private static final long serialVersionUID = 137433871719708085L;

	@XmlAttribute
	private String id;

	@XmlAttribute
	private String uniqueId;

	@XmlAttribute
	private String instructorName;

	@XmlAttribute
	private String location;

	@XmlAttribute
	private boolean arranged;

	public ClassMeetingTimeInfo() {
	}

	public ClassMeetingTimeInfo(ClassMeetingTime copy) {
		super(copy);
		id = copy.getId();
		uniqueId = copy.getUniqueId();
		instructorName = copy.getInstructorName();
		location = copy.getLocation();
		arranged = copy.isArranged();
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	@Override
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isArranged() {
		return arranged;
	}

	public void setArranged(boolean arranged) {
		this.arranged = arranged;
	}

	@Override
	public String toString() {
		return "ClassMeetingTimeInfo [id=" + id + ", uniqueId=" + uniqueId
				+ ", instructorName=" + instructorName + ", location="
				+ location + ", getDescription()=" + getDescription()
				+ ", isAllDay()=" + isAllDay() + ", getDaysAndTimes()="
				+ getDaysAndTimes() + ", getStartDate()=" + getStartDate()
				+ ", isSunday()=" + isSunday() + ", isMonday()=" + isMonday()
				+ ", isTuesday()=" + isTuesday() + ", isWednesday()="
				+ isWednesday() + ", isThursday()=" + isThursday()
				+ ", isFriday()=" + isFriday() + ", isSaturday()="
				+ isSaturday() + ", getUntilDate()=" + getUntilDate() + "]";
	}

}
