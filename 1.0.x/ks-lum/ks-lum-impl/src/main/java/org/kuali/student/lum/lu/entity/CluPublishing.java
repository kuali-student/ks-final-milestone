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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KSLU_CLU_PUBL")
public class CluPublishing implements AttributeOwner<CluPublishingAttribute> {

	@Column(name = "START_CYCLE")
	private String startCycle;

	@Column(name = "END_CYCLE")
	private String endCycle;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRI_INSTR_ID")
	private CluInstructor primaryInstructor;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "KSLU_CLU_PUBL_JN_CLU_INSTR", joinColumns = @JoinColumn(name = "CLU_PUBL_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_INSTR_ID"))
	private List<CluInstructor> instructors;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluPublishingAttribute> attributes;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ST")
	private String state;

	@Id
	@Column(name = "ID")
	private String id;

	@PrePersist
	public  void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	public String getStartCycle() {
		return startCycle;
	}

	public void setStartCycle(String startCycle) {
		this.startCycle = startCycle;
	}

	public String getEndCycle() {
		return endCycle;
	}

	public void setEndCycle(String endCycle) {
		this.endCycle = endCycle;
	}

	public List<CluInstructor> getInstructors() {
		if (instructors == null) {
			instructors = new ArrayList<CluInstructor>();
		}
		return instructors;
	}

	public void setInstructors(List<CluInstructor> instructors) {
		this.instructors = instructors;
	}

	public List<CluPublishingAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<CluPublishingAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<CluPublishingAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CluInstructor getPrimaryInstructor() {
		return primaryInstructor;
	}

	public void setPrimaryInstructor(CluInstructor primaryInstructor) {
		this.primaryInstructor = primaryInstructor;
	}
}
