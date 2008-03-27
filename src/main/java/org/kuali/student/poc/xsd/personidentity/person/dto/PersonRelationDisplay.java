package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonRelationDisplay implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private Long relationId;
	@XmlAttribute
	private Long personId;
	
	@XmlElement
	private Long organizationId;
	@XmlElement
	private Long posistionId;
	@XmlElement
	private Long relationshipType;

	/**
	 * @return the relationId
	 */
	public Long getRelationId() {
		return relationId;
	}
	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the posistionId
	 */
	public Long getPosistionId() {
		return posistionId;
	}
	/**
	 * @param posistionId the posistionId to set
	 */
	public void setPosistionId(Long posistionId) {
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
