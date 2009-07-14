package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class TypeStateCaseConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "typeStateWhen")
    protected List<TypeStateWhenConstraint> typeStateWhen;

	/**
	 * @return the typeStateWhen
	 */
	public List<TypeStateWhenConstraint> getTypeStateWhen() {
		return typeStateWhen;
	}

	/**
	 * @param typeStateWhen the typeStateWhen to set
	 */
	public void setTypeStateWhen(List<TypeStateWhenConstraint> typeStateWhen) {
		this.typeStateWhen = typeStateWhen;
	}


}
