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

package org.kuali.student.lum.lu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.Type;

@Entity
@Table(name = "KSLU_CLU_LO_RELTN_TYPE")
public class CluLoRelationType extends Type<CluLoRelationTypeAttribute> {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluLoRelationTypeAttribute> attributes;

	public List<CluLoRelationTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CluLoRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}