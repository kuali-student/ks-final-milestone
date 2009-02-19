/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KS_LU_CODE_T")
public class LuCode extends MetaEntity implements AttributeOwner<LuCodeAttribute> {

	@Id
    @Column(name = "ID")
    private String id;
    
	@Column(name = "LU_CODE_DESC")
	private String desc;

	@Column(name = "LU_CODE_VALUE")
	private String value;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuCodeAttribute> attributes;

	@Column(name = "LU_CODE_TYPE")
	private String type;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<LuCodeAttribute> getAttributes() {
		if (attributes == null) {
		    attributes = new ArrayList<LuCodeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuCodeAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}