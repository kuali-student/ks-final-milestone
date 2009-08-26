package org.kuali.student.core.atp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSAP_ATP_SEASONAL_TYPE")
public class AtpSeasonalType extends Type<AtpSeasonalTypeAttribute>{
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpSeasonalTypeAttribute> attributes;

	public List<AtpSeasonalTypeAttribute> getAttributes() {
		if(attributes==null){
			attributes = new ArrayList<AtpSeasonalTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<AtpSeasonalTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
