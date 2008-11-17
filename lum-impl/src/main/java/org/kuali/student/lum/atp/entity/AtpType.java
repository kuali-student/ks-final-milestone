package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Type;

@Entity
public class AtpType extends Type{
	@ManyToOne
	private AtpSeasonalType seasonalType;
	
	@ManyToOne
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
}
