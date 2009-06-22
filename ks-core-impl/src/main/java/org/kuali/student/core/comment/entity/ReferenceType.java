package org.kuali.student.core.comment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSCO_REFERENCE_TYPE")
public class ReferenceType extends Type<ReferenceTypeAttribute>{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReferenceTypeAttribute> attributes;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="referenceType")
    private List<Reference> references;

    @Override
    public List<ReferenceTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<ReferenceTypeAttribute> attributes) {
        this.attributes=attributes;
    }

	/**
	 * @return the references
	 */
	public List<Reference> getReferences() {
		return references;
	}

	/**
	 * @param references the references to set
	 */
	public void setReferences(List<Reference> references) {
		this.references = references;
	}
}
