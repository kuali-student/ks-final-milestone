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
@Table(name = "KSSTMT_OBJECT_TYPE")
public class ObjectType extends Type<ObjectTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<ObjectTypeAttribute> attributes = new ArrayList<ObjectTypeAttribute>();
	
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "KSSTMT_OBJ_TYP_JN_OBJ_SUB_TYP", joinColumns = @JoinColumn(name = "OBJ_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "OBJ_SUB_TYPE_ID"))
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
