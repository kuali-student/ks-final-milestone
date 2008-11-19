package org.kuali.student.lum.atp.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class AtpTypeInfo extends TypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String durationType;
	@XmlElement
	private String seasonalType;

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getSeasonalType() {
		return seasonalType;
	}

	public void setSeasonalType(String seasonalType) {
		this.seasonalType = seasonalType;
	}
}