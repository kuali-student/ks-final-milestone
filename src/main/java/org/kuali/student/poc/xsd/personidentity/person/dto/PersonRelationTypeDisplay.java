package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonRelationTypeDisplay implements Serializable{
	// TODO Does this class need to exist? We don't have a ReplationType Entity,
	// is this just a string, i.e. Parent, Member
	
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private String relationshipType;

	/**
	 * @return the relationshipType
	 */
	public String getRelationshipType() {
		return relationshipType;
	}

	/**
	 * @param relationshipType the relationshipType to set
	 */
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
	
}
