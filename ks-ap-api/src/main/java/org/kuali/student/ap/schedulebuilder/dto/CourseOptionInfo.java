package org.kuali.student.ap.schedulebuilder.dto;

import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOptionInfo", propOrder = { "uniqueId", "selected", "courseId", "courseCode", "courseTitle",
		"credits", "activityOptions", "_futureElements" })
public class CourseOptionInfo extends ScheduleBuildOptionInfo implements CourseOption {

	private static final long serialVersionUID = 8095707701469604619L;

	@XmlAttribute
	private String courseId;

	@XmlAttribute
	private String courseCode;

	@XmlAttribute
	private String courseTitle;
	
	@XmlAttribute
	private BigDecimal credits;

	@XmlElement
	private List<ActivityOptionInfo> activityOptions;

	@XmlAnyElement
	private List<?> _futureElements;

	public CourseOptionInfo() {
	}

	public CourseOptionInfo(CourseOption courseOption) {
		super(courseOption);
		this.courseId = courseOption.getCourseId();
		this.courseCode = courseOption.getCourseCode();
		this.courseTitle = courseOption.getCourseTitle();
		setActivityOptions(courseOption.getActivityOptions());
	}

	@Override
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Override
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@Override
	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	@Override
	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	@Override
	public List<ActivityOption> getActivityOptions() {
		return activityOptions == null ? Collections.<ActivityOption> emptyList() : Collections
				.<ActivityOption> unmodifiableList(activityOptions);
	}

	public void setActivityOptions(List<ActivityOption> primaryActivities) {
		if (primaryActivities != null) {
			List<ActivityOptionInfo> activityOptionInfos = new java.util.ArrayList<ActivityOptionInfo>(
					primaryActivities.size());
			for (ActivityOption activityOption : primaryActivities) {
				ActivityOptionInfo activityOptionInfo = new ActivityOptionInfo(activityOption);
				activityOptionInfos.add(activityOptionInfo);
			}
			this.activityOptions = activityOptionInfos;
		} else {
			this.activityOptions = null;
		}
	}

	@Override
	public int getActivityCount(boolean includeClosed) {
		if (includeClosed)
			return activityOptions == null ? 0 : activityOptions.size();
		int c = 0;
		if (activityOptions != null)
			for (ActivityOption ao : activityOptions)
				if (ao.isLockedIn() || !ao.isClosed())
					c++;
		return c;
	}

	@Override
	public int getSelectedActivityCount(boolean includeClosed) {
		int c = 0;
		if (activityOptions != null)
			for (ActivityOption ao : activityOptions)
				if ((includeClosed || !ao.isClosed() || ao.isLockedIn()) && ao.isSelected())
					c++;
		return c;
	}

	public BigDecimal getMaxSelectedMinCredits() {
		BigDecimal mc = BigDecimal.ZERO;
		if (activityOptions != null)
			for (ActivityOption ao : activityOptions)
				if (ao.isSelected()) {
					BigDecimal amc = ao.getMinCredits();
					if (amc != null && amc.compareTo(mc) > 0)
						mc = amc;
				}
		return mc;
	}

	@Override
	public String toString() {
		return "CourseOptionInfo [courseId=" + courseId + ", courseCode=" + courseCode + ", courseTitle=" + courseTitle
				+ ", activityOptions=" + activityOptions + ", getUniqueId()=" + getUniqueId() + ", isSelected()="
				+ isSelected() + "]";
	}

	@Override
	public int compareTo(CourseOption o) {
		if (o == null)
			return -1;
		boolean ol = o.isLockedIn();
		if (ol != isLockedIn())
			return isLockedIn() ? -1 : 1;
		boolean os = o.isSelected();
		if (os != isSelected())
			return isSelected() ? -1 : 1;

		boolean open = getActivityCount(false) > 0;
		boolean oopen = o.getActivityCount(false) > 0;
		if (open != oopen)
			return open ? -1 : 1;

		BigDecimal mc1 = getMaxSelectedMinCredits();
		BigDecimal mc2 = o.getMaxSelectedMinCredits();
		int mcc = mc1.compareTo(mc2);
		if (mcc != 0)
			return -mcc;

		String occ = o.getCourseCode();
		if (courseCode == null && occ == null)
			return 0;
		if (courseCode == null)
			return 1;
		if (occ == null)
			return -1;
		return courseCode.compareTo(occ);
	}

}
