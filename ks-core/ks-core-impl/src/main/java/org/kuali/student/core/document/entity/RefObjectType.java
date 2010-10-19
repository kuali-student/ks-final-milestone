package org.kuali.student.core.document.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;
@Entity
@Table(name="KSDO_REF_OBJ_TYPE")
public class RefObjectType extends Type<RefObjectTypeAttribute> {

	@OneToMany(mappedBy="refObjectType")
	private List<RefObjectSubType> refObjectSubTypes;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<RefObjectTypeAttribute> attributes;
    
    @Override
    public List<RefObjectTypeAttribute> getAttributes() {
        return attributes;
    }

	@Override
	public void setAttributes(List<RefObjectTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setRefObjectSubTypes(List<RefObjectSubType> refObjectSubTypes) {
		this.refObjectSubTypes = refObjectSubTypes;
	}

	public List<RefObjectSubType> getRefObjectSubTypes() {
		return refObjectSubTypes;
	}
}
