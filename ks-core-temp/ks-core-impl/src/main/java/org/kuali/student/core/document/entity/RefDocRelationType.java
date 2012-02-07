package org.kuali.student.core.document.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.Type;;
@Entity
@Table(name="KSDO_REF_DOC_RELTN_TYPE")
public class RefDocRelationType extends Type<RefDocRelationTypeAttribute> {

    @ManyToMany
	@JoinTable(
			name="KSDO_REF_REL_TYP_JN_SUB_TYP",
	        joinColumns=
	            @JoinColumn(name="REF_DOC_RELTN_TYPE_KEY", referencedColumnName="TYPE_KEY"),
	        inverseJoinColumns=
	            @JoinColumn(name="REF_OBJ_SUB_TYPE_KEY", referencedColumnName="TYPE_KEY"))
    private List<RefObjectSubType> refObjectSubTypes;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<RefDocRelationTypeAttribute> attributes;
    
    @Override
    public List<RefDocRelationTypeAttribute> getAttributes() {
        return attributes;
    }

	@Override
	public void setAttributes(List<RefDocRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setRefObjectSubTypes(List<RefObjectSubType> refObjectSubTypes) {
		this.refObjectSubTypes = refObjectSubTypes;
	}

	public List<RefObjectSubType> getRefObjectSubTypes() {
		return refObjectSubTypes;
	}

}
