package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitlistOptionInfo", propOrder = { "id", "typeKey",
		"stateKey", "name", "descr", "regGroupId", "clearingStrategy",
		"checkInRequired", "checkInFrequency", "_futureElements" })
public class CourseWaitlistOptionInfo extends IdEntityInfo implements
		CourseWaitlistOption, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String regGroupId;

	@XmlElement
	private String clearingStrategy;

	@XmlElement
	private Boolean checkInRequired;

	@XmlElement
	private TimeAmountInfo checkInFrequency;

	@XmlAnyElement
	private List<Element> _futureElements;

	public CourseWaitlistOptionInfo() {
		super();
		this.regGroupId = null;
		this.clearingStrategy = null;
		this.checkInRequired = null;
		this.checkInFrequency = null;
		this._futureElements = null;
	}

	public CourseWaitlistOptionInfo(CourseWaitlistOption courseWaitlistOption) {
		super(courseWaitlistOption);
		if (null != courseWaitlistOption) {

			this.regGroupId = courseWaitlistOption.getRegGroupId();
			this.clearingStrategy = courseWaitlistOption.getClearingStrategy();
			this.checkInRequired = courseWaitlistOption.getCheckInRequired();
			this.checkInFrequency = (TimeAmountInfo)courseWaitlistOption.getCheckInFrequency();
			this._futureElements = null;
		}
	}

	@Override
	public String getRegGroupId() {
		return regGroupId;
	}

	@Override
	public String getClearingStrategy() {
		return clearingStrategy;
	}

	@Override
	public Boolean getCheckInRequired() {
		return checkInRequired;
	}

	@Override
	public TimeAmountInfo getCheckInFrequency() {
		return checkInFrequency;
	}

}
