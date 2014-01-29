package org.kuali.student.r1.core.document.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;
@Deprecated
@Entity
@Table(name="KSDO_REF_OBJ_SUB_TYPE")
public class RefObjectSubType extends Type<RefObjectSubTypeAttribute> {

	@ManyToMany(mappedBy="refObjectSubTypes")
	private List<RefDocRelationType> refDocRelationTypes;
	 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<RefObjectSubTypeAttribute> attributes;
    
    @ManyToOne
    @JoinColumn(name="REF_OBJ_TYPE_KEY")
    private RefObjectType refObjectType;
    
    @Override
    public List<RefObjectSubTypeAttribute> getAttributes() {
        return attributes;
    }

	@Override
	public void setAttributes(List<RefObjectSubTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setRefDocRelationTypes(List<RefDocRelationType> refDocRelationTypes) {
		this.refDocRelationTypes = refDocRelationTypes;
	}

	public List<RefDocRelationType> getRefDocRelationTypes() {
		return refDocRelationTypes;
	}

	public void setRefObjectType(RefObjectType refObjectType) {
		this.refObjectType = refObjectType;
	}

	public RefObjectType getRefObjectType() {
		return refObjectType;
	}
}
