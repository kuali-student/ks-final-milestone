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

package org.kuali.student.brms.statement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSST_OBJECT_TYPE")
public class ObjectType extends Type<ObjectTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<ObjectTypeAttribute> attributes = new ArrayList<ObjectTypeAttribute>();
	
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "KSST_OBJ_TYP_JN_OBJ_SUB_TYP", joinColumns = @JoinColumn(name = "OBJ_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "OBJ_SUB_TYPE_ID"))
	private List<ObjectSubType> objectSubTypes = new ArrayList<ObjectSubType>();

	@Override
	public List<ObjectTypeAttribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(List<ObjectTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<ObjectSubType> getObjectSubTypes() {
		return objectSubTypes;
	}

	public void setObjectSubTypes(List<ObjectSubType> objectSubTypes) {
		this.objectSubTypes = objectSubTypes;
	}

	@Override
	public String toString() {
		return "ObjectType[id=" + super.getId() + "]";
	}
}
