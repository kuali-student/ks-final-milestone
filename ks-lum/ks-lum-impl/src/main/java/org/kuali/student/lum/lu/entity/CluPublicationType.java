package org.kuali.student.lum.lu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_CLU_PUBL_TYPE")
public class CluPublicationType extends Type<CluPublicationTypeAttribute>{

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluPublicationTypeAttribute> attributes;

	public List<CluPublicationTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CluPublicationTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
