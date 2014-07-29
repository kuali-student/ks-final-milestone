package org.kuali.student.ap.schedulebuilder.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.ClassMeetingTime;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PossibleScheduleOptionInfo", propOrder = { "id", "description", "activityOptions"})
public class PossibleScheduleOptionInfo extends ScheduleBuildOptionInfo
		implements PossibleScheduleOption {

	private static final long serialVersionUID = 4744705408785720809L;

	@XmlAttribute
	private String id;

	@XmlAttribute
	private String termId;

	@XmlElement
	private RichTextInfo description;

	@XmlElement
	private List<ActivityOptionInfo> activityOptions;

	public PossibleScheduleOptionInfo() {
	}

	public PossibleScheduleOptionInfo(PossibleScheduleOption copy) {
		super(copy);
		RichText copyDescr = copy.getDescription();
		if (copyDescr != null)
			description = new RichTextInfo(copyDescr.getPlain(),
					copyDescr.getFormatted());
		setActivityOptions(copy.getActivityOptions());
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	@Override
	public RichTextInfo getDescription() {
		return description;
	}

	public void setDescription(String description) {
		RichTextInfo rt = new RichTextInfo();
		rt.setPlain(description);
		Element wrap = DocumentHelper.createElement("div");
		wrap.addAttribute("class", "ksap-sb-schedule");

		Element span = wrap.addElement("span");
		span.addAttribute("class", "ksap-sb-schedule-title");
		span.setText(description);

		Element dl = wrap.addElement("dl");
		dl.addAttribute("class", "ksap-sb-schedule-activitydetail");
		for (ActivityOption ao : getActivityOptions())
			if (!ao.isEnrollmentGroup()) {
				StringBuilder hover = new StringBuilder();
				hover.append(ao.getCourseOfferingCode());
				hover.append(' ');
				hover.append(ao.getActivityName());
				hover.append(" (");
				hover.append(ao.getActivityTypeDescription());
				hover.append(") ");
				hover.append(ao.getAcademicSessionDescr());
				for (ClassMeetingTime meetingTime : ao.getClassMeetingTimes()) {
					hover.append(" ");
					hover.append(meetingTime.getDaysAndTimes());
				}
				String hoverText = hover.toString();

				Element dt = dl.addElement("dt");
				dt.addAttribute("class", "ksap-sb-schedule-activity-title");
				dt.addAttribute("title", hoverText);
				dt.setText(StringEscapeUtils.escapeHtml(ao
						.getCourseOfferingCode()));
				StringBuilder descr = new StringBuilder();
				descr.append(ao.getRegistrationCode());
				if (ao.isPrimary()) {
					BigDecimal minCredits = ao.getMinCredits();
					if (minCredits != null) {
						descr.append(" (");
						descr.append(ao.getMinCredits());
						if (ao.getMinCredits().compareTo(ao.getMaxCredits()) != 0) {
							descr.append("-");
							descr.append(ao.getMaxCredits());
						}
						descr.append(")");
					}
				}
				Element dd = dl.addElement("dd");
				dd.addAttribute("class", "ksap-sb-schedule-activity-subtitle");
				dd.addAttribute("title", hoverText);
				dd.setText(StringEscapeUtils.escapeHtml(descr.toString()));
			}
		rt.setFormatted(wrap.asXML());
		this.description = rt;
	}

	@Override
	public List<ActivityOption> getActivityOptions() {
		return activityOptions == null ? Collections
				.<ActivityOption> emptyList() : Collections
				.<ActivityOption> unmodifiableList(activityOptions);
	}

	public void setActivityOptions(List<ActivityOption> primaryActivities) {
		if (primaryActivities != null) {
			List<ActivityOptionInfo> activityOptionInfos = new java.util.ArrayList<ActivityOptionInfo>(
					primaryActivities.size());
			for (ActivityOption activityOption : primaryActivities) {
				ActivityOptionInfo activityOptionInfo = new ActivityOptionInfo(
						activityOption);
				activityOptionInfos.add(activityOptionInfo);
			}
			this.activityOptions = activityOptionInfos;
		} else {
			this.activityOptions = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activityOptions == null) ? 0 : activityOptions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PossibleScheduleOptionInfo other = (PossibleScheduleOptionInfo) obj;
		if (activityOptions == null) {
			if (other.activityOptions != null)
				return false;
		} else if (!activityOptions.equals(other.activityOptions))
			return false;
		return true;
	}

}
