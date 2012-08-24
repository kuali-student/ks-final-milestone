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

package org.kuali.student.r2.lum.lo.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;

@Entity
@Table(name = "KSLO_LO_RELTN_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="ID")),
    @AttributeOverride(name="descr", column=@Column(name="DESCR"))})
public class LoLoRelationType extends Type<LoLoRelationTypeAttribute> {
		
	@Column(name = "REV_NAME")
	private String revName;
	
	@Column(name = "REV_DESCR")
	private String revDescription;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoLoRelationTypeAttribute> attributes;

	/**
	 * @return the reverse name
	 */
	public String getRevName() {
		return revName;
	}

	/**
	 * @param revName the new reverse name
	 */
	public void setRevName(String revName) {
		this.revName = revName;
	}

	/**
	 * @return the reverse description
	 */
	public String getRevDescription() {
		return revDescription;
	}

	/**
	 * @param revDescription the new reverse description
	 */
	public void setRevDescription(String revDescription) {
		this.revDescription = revDescription;
	}

	@Override
	public List<LoLoRelationTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<LoLoRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
