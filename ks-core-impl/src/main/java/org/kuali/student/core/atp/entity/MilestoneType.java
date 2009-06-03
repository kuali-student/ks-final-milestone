package org.kuali.student.core.atp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSAP_MLSTN_TYPE")
@NamedQueries( { 
	@NamedQuery(name = "MilestoneType.findMilestoneTypesForAtpType", query = "SELECT milestone.type FROM Milestone milestone WHERE milestone.atp.type.id = :atpTypeId") 
})
public class MilestoneType extends Type<MilestoneTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<MilestoneTypeAttribute> attributes;

	public List<MilestoneTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<MilestoneTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<MilestoneTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
