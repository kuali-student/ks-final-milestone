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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_LUTYPE")
public class LuType extends Type<LuTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuTypeAttribute> attributes;

	@Column(name = "INSTR_FRMT")
	private String instructionalFormat;

	@Column(name = "DLVR_MTHD")
    private String deliveryMethod;
	
	
	public List<LuTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getInstructionalFormat() {
		return instructionalFormat;
	}

	public void setInstructionalFormat(String instructionalFormat) {
		this.instructionalFormat = instructionalFormat;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
}
