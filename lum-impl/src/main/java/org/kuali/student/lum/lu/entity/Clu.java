package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KS_CLU_T")
public class Clu extends MetaEntity implements AttributeOwner<CluAttribute> {
	@Id
	@Column(name = "ID")
	private String id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluAttribute> attributes;

	@ManyToOne
	@JoinColumn(name="LU_TYPE_ID")
	private LuType luType;
	
	@OneToMany(mappedBy="clu")
	private List<LearningObjective> learningObjectives;

	@OneToMany(mappedBy="clu")
	private List<LearningObjective> resourceTypes;
	
	@ManyToMany(mappedBy="clus")
	List<CluSet> cluSets;
	
	@Override
	public List<CluAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<CluAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<CluAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LuType getLuType() {
		return luType;
	}

	public void setLuType(LuType luType) {
		this.luType = luType;
	}

	public List<LearningObjective> getLearningObjectives() {
		return learningObjectives;
	}

	public void setLearningObjectives(List<LearningObjective> learningObjectives) {
		this.learningObjectives = learningObjectives;
	}

	public List<LearningObjective> getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(List<LearningObjective> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}
}
