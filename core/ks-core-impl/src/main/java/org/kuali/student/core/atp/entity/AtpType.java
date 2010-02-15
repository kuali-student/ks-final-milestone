/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
