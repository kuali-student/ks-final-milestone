package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonRelationDisplay implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private String relationId;
	@XmlAttribute
	private String personId;
	
	@XmlElement
	private String organizationId;
	@XmlElement
	private String posistionId;
	@XmlElement
	private Long relationshipType;

	/**
	 * @return the relationId
	 */
	public String getRelationId() {
		return relationId;
	}
	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the posistionId
	 */
	public String getPosistionId() {
		return posistionId;
	}
	/**
	 * @param posistionId the posistionId to set
	 */
	public void setPosistionId(String posistionId) {
		this.posistionId = posistionId;
	}
	/**
	 * @return the relationshipType
	 */
	public Long getRelationshipType() {
		return relationshipType;
	}
	/**
	 * @param relationshipType the relationshipType to set
	 */
	public void setRelationshipType(Long relationshipType) {
		this.relationshipType = relationshipType;
	}

}
