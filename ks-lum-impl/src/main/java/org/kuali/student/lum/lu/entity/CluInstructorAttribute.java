package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_INSTR_ATTR")
public class CluInstructorAttribute extends Attribute<CluInstructor> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluInstructor owner;

	@Override
	public CluInstructor getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluInstructor owner) {
		this.owner = owner;
	}

}
