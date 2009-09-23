package org.kuali.student.core.entity;

import javax.persistence.Embeddable;

@Embeddable
public class TimeAmount {
	//TODO Col names
	private String atpDurationTypeKey;
	private Integer timeQuantity;
	public String getAtpDurationTypeKey() {
		return atpDurationTypeKey;
	}
	public void setAtpDurationTypeKey(String atpDurationTypeKey) {
		this.atpDurationTypeKey = atpDurationTypeKey;
	}
	public Integer getTimeQuantity() {
		return timeQuantity;
	}
	public void setTimeQuantity(Integer timeQuantity) {
		this.timeQuantity = timeQuantity;
	}
}
