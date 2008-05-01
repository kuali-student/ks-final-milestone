package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonRelationUpdateInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String personId;
	
	@XmlElement
	private String organizationId;
	@XmlElement
	private String posistionId;
	@XmlElement
	private Long relationshipType;
	@XmlElement
	private Date effectiveStartDate;
	@XmlElement
	private Date effectiveEndDate;

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
	/**
	 * @return the effectiveStartDate
	 */
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	/**
	 * @param effectiveStartDate the effectiveStartDate to set
	 */
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	/**
	 * @return the effectiveEndDate
	 */
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	/**
	 * @param effectiveEndDate the effectiveEndDate to set
	 */
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
}
