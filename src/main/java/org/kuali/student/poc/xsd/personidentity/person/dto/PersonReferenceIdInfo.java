package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonReferenceIdInfo implements Serializable {
	private static final long serialVersionUID = -5482092601569681413L;

	@XmlAttribute
	private Long personId;

	@XmlAttribute
	private String referenceId;
	@XmlAttribute
	private String organizationReferenceId;
	@XmlAttribute
	private boolean restrictedAccess;

	@XmlElement
	private Date updateTime;
	@XmlElement
	private String updateUserId;
	@XmlElement
	private String updateUserComment;

	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId
	 *            the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the organizationReferenceId
	 */
	public String getOrganizationReferenceId() {
		return organizationReferenceId;
	}

	/**
	 * @param organizationReferenceId
	 *            the organizationReferenceId to set
	 */
	public void setOrganizationReferenceId(String organizationReferenceId) {
		this.organizationReferenceId = organizationReferenceId;
	}

	/**
	 * @return the restrictedAccess
	 */
	public boolean isRestrictedAccess() {
		return restrictedAccess;
	}

	/**
	 * @param restrictedAccess
	 *            the restrictedAccess to set
	 */
	public void setRestrictedAccess(boolean restrictedAccess) {
		this.restrictedAccess = restrictedAccess;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateUserId
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId
	 *            the updateUserId to set
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the updateUserComment
	 */
	public String getUpdateUserComment() {
		return updateUserComment;
	}

	/**
	 * @param updateUserComment
	 *            the updateUserComment to set
	 */
	public void setUpdateUserComment(String updateUserComment) {
		this.updateUserComment = updateUserComment;
	}

}
