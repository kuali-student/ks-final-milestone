package org.kuali.student.core.organization.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.kuali.student.core.entity.Type;
@Entity
public class OrgType extends Type {
	@ManyToMany
	@JoinTable(
	        name="KS_ORG_TYPE_ORG_PERSON_RELATION_TYPE_T",
	        joinColumns=
	            @JoinColumn(name="ORG_TYPE_KEY", referencedColumnName="TYPE_KEY"),
	        inverseJoinColumns=
	            @JoinColumn(name="ORG_PERSON_RELATION_TYPE_KEY", referencedColumnName="OPRT_KEY")
	    )
	    
    private List<OrgPersonRelationType> orgPersonRelationTypes;

	public void setOrgPersonRelationTypes(List<OrgPersonRelationType> orgPersonRelationTypes) {
		this.orgPersonRelationTypes = orgPersonRelationTypes;
	}

	public List<OrgPersonRelationType> getOrgPersonRelationTypes() {
		return orgPersonRelationTypes;
	}
}
