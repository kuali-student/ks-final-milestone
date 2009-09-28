package org.kuali.student.core.atp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSAP_ATP_TYPE")
public class AtpType extends Type<AtpTypeAttribute> implements AttributeOwner<AtpTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpTypeAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "SEASONAL_TYPE")
	private AtpSeasonalType seasonalType;

	@ManyToOne
	@JoinColumn(name = "DUR_TYPE")
	private AtpDurationType durationType;

	public AtpSeasonalType getSeasonalType() {
		return seasonalType;
	}

	public void setSeasonalType(AtpSeasonalType seasonalType) {
		this.seasonalType = seasonalType;
	}

	public AtpDurationType getDurationType() {
		return durationType;
	}

	public void setDurationType(AtpDurationType durationType) {
		this.durationType = durationType;
	}

	public List<AtpTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AtpTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
