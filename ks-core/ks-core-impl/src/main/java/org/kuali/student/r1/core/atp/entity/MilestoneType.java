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

package org.kuali.student.r1.core.atp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;

@Deprecated
@Entity
@Table(name = "KSAP_MLSTN_TYPE")
@NamedQueries( { 
	@NamedQuery(name = "MilestoneType.findMilestoneTypesForAtpType", query = "SELECT milestone.type FROM Milestone milestone WHERE milestone.atp.type.id = :atpTypeId") 
})
public class MilestoneType extends Type<MilestoneTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<MilestoneTypeAttribute> attributes;

	public List<MilestoneTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MilestoneTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
