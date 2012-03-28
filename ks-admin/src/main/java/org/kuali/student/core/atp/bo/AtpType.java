/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.atp.bo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.bo.KsTypeBusinessObjectBase;

@Entity
@Table(name = "KSAP_ATP_TYPE")
public class AtpType extends KsTypeBusinessObjectBase {
    
	private static final long serialVersionUID = 3559054373937794275L;

	private String seasonalTypeId;
	
	private String durationTypeId;
	
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

	public String getSeasonalTypeId() {
		return seasonalTypeId;
	}

	public void setSeasonalTypeId(String seasonalTypeId) {
		this.seasonalTypeId = seasonalTypeId;
	}

	public String getDurationTypeId() {
		return durationTypeId;
	}

	public void setDurationTypeId(String durationTypeId) {
		this.durationTypeId = durationTypeId;
	}
	
}
