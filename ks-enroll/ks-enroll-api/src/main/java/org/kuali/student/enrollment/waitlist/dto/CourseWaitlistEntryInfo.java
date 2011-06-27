package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistEntry;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitlistEntryInfo", propOrder = { "id", "typeKey",
		"stateKey", "effectiveDate", "expirationDate", "studentId",
		"courseWaitlistOptionId", "position", "hasCheckedIn", "meta",
		"attributes", "_futureElements" })
public class CourseWaitlistEntryInfo extends RelationshipInfo implements
		CourseWaitlistEntry, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String studentId;
	@XmlElement
	private String courseWaitlistOptionId;
	@XmlElement
	private Integer position;
	@XmlElement
	private Boolean hasCheckedIn;
	@XmlAnyElement
	private List<Element> _futureElements;

	public CourseWaitlistEntryInfo() {
		super();
		this.studentId = null;
		this.courseWaitlistOptionId = null;
		this.position = null;
		this.hasCheckedIn = null;
		this._futureElements = null;
	}

	public CourseWaitlistEntryInfo(CourseWaitlistEntry courseWaitlistEntry) {
		super(courseWaitlistEntry);
		if (null != courseWaitlistEntry) {
			this.studentId = courseWaitlistEntry.getStudentId();
			this.courseWaitlistOptionId = courseWaitlistEntry.getCourseWaitlistOptionId();
			this.position = courseWaitlistEntry.getPosition();
			this.hasCheckedIn = courseWaitlistEntry.getHasCheckedIn();
			this._futureElements = null;
		}
	}

	public void setHasCheckedIn(Boolean hasCheckedIn) {
		this.hasCheckedIn = hasCheckedIn;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setCourseWaitlistOptionId(String courseWaitlistOptionId) {
		this.courseWaitlistOptionId = courseWaitlistOptionId;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public String getStudentId() {
		return studentId;
	}

	@Override
	public String getCourseWaitlistOptionId() {
		return courseWaitlistOptionId;
	}

	@Override
	public Integer getPosition() {
		return position;
	}

	@Override
	public Boolean getHasCheckedIn() {
		return hasCheckedIn;
	}

}
