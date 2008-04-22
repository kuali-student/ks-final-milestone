package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonCitizenshipInfo implements Serializable{

	private static final long serialVersionUID = -404428611781445073L;
	
	@XmlAttribute
	private String personId;
	
	@XmlElement
	private String countryOfCitizenshipCode;
	@XmlElement
	private String citizenStatusCode;
	
	@XmlElement
	private Date effectiveStartDate;
	@XmlElement
	private Date effectiveEndDate;
	
	@XmlElement
	private Date updateTime;
	@XmlElement
	private String updateUserId;
	@XmlElement
	private String updateUserComment;
	
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
	 * @return the countryOfCitizenshipCode
	 */
	public String getCountryOfCitizenshipCode() {
		return countryOfCitizenshipCode;
	}
	/**
	 * @param countryOfCitizenshipCode the countryOfCitizenshipCode to set
	 */
	public void setCountryOfCitizenshipCode(String countryOfCitizenshipCode) {
		this.countryOfCitizenshipCode = countryOfCitizenshipCode;
	}
	/**
	 * @return the citizenStatusCode
	 */
	public String getCitizenStatusCode() {
		return citizenStatusCode;
	}
	/**
	 * @param citizenStatusCode the citizenStatusCode to set
	 */
	public void setCitizenStatusCode(String citizenStatusCode) {
		this.citizenStatusCode = citizenStatusCode;
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
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
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
	 * @param updateUserId the updateUserId to set
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
	 * @param updateUserComment the updateUserComment to set
	 */
	public void setUpdateUserComment(String updateUserComment) {
		this.updateUserComment = updateUserComment;
	}
	
}
