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

package org.kuali.student.r2.lum.lu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LU_CODE")
public class LuCode extends MetaEntity implements AttributeOwner<LuCodeAttribute> {
    
	@Column(name = "DESCR")
	private String descr;

	@Column(name = "VALUE")
	private String value;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuCodeAttribute> attributes;

	@Column(name = "TYPE")
	private String type;

	@ManyToOne
	@JoinColumn(name="CLU_ID")
	private Clu clu;
	
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<LuCodeAttribute> getAttributes() {
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

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

}
