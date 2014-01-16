package org.kuali.student.ap.schedulebuilder.dto;

import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.SecondaryActivityOptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecondaryActivityOptionsInfo", propOrder = { "index",
		"uniqueId", "activityTypeDescription", "enrollmentGroup",
		"activityOptions", "_futureElements" })
public class SecondaryActivityOptionsInfo implements SecondaryActivityOptions,
		Serializable {

	private static final long serialVersionUID = -6831820824316403614L;

	@XmlAttribute
	private int index;

	@XmlAttribute
	private String uniqueId;

	@XmlAttribute
	private String activityTypeDescription;

	@XmlAttribute
	private boolean enrollmentGroup;

	@XmlElement
	private List<ActivityOptionInfo> activityOptions;

	@XmlAnyElement
	private List<?> _futureElements;

	public SecondaryActivityOptionsInfo() {
	}

	public SecondaryActivityOptionsInfo(SecondaryActivityOptions copy) {
		uniqueId = copy.getUniqueId();
		activityTypeDescription = copy.getActivityTypeDescription();
		enrollmentGroup = copy.isEnrollmentGroup();
		setActivityOptions(copy.getActivityOptions());
	}

	@Override
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String getActivityTypeDescription() {
		return activityTypeDescription;
	}

	public void setActivityTypeDescription(String activityTypeDescription) {
		this.activityTypeDescription = activityTypeDescription;
	}

	@Override
	public int getActivityCount(boolean includeClosed) {
		if (includeClosed)
			return activityOptions == null ? 0 : activityOptions.size();
		int c = 0;
		if (activityOptions != null)
			for (ActivityOption ao : activityOptions)
				if (ao.isLockedIn() || includeClosed || !ao.isClosed())
					c++;
		return c;
	}

	@Override
	public int getSelectedActivityCount(boolean includeClosed) {
		int c = 0;
		if (activityOptions != null)
			for (ActivityOption ao : activityOptions)
				if (ao.isLockedIn() || ((includeClosed || !ao.isClosed()) && ao.isSelected()))
					c++;
		return c;
	}

	public boolean isEnrollmentGroup() {
		return enrollmentGroup;
	}

	public void setEnrollmentGroup(boolean enrollmentGroup) {
		this.enrollmentGroup = enrollmentGroup;
	}

	@Override
	public List<ActivityOption> getActivityOptions() {
		return activityOptions == null ? Collections
				.<ActivityOption> emptyList() : Collections
				.<ActivityOption> unmodifiableList(activityOptions);
	}

	public void setActivityOptions(
			List<? extends ActivityOption> secondaryOptions) {
		if (secondaryOptions != null) {
			List<ActivityOptionInfo> meetingTimes = new java.util.ArrayList<ActivityOptionInfo>(
					secondaryOptions.size());
			for (ActivityOption secondaryOption : secondaryOptions) {
				meetingTimes.add(new ActivityOptionInfo(secondaryOption));
			}
			this.activityOptions = meetingTimes;
		} else {
			this.activityOptions = null;
		}
	}

	@Override
	public String toString() {
		return "SecondaryActivityOptionsInfo [activityTypeDescription="
				+ activityTypeDescription + ", enrollmentGroup="
				+ enrollmentGroup + ", activityOptions=" + activityOptions
				+ "]";
	}

}
