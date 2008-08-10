package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;
import java.util.Date;

public class GwtPersonNameInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5518022284145943373L;


	private String personId;
	
	
	private boolean preferredName;

	
	private String personTitle;
	
	private String nonStandardName;
	
	private String surname;
	
	private String middleName;
	
	private String givenName;

	private String nameType;

	private String suffix;


	private Date effectiveStartDate;

	private Date effectiveEndDate;
	

	private Date updateTime;

	private String updateUserId;

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
	 * @return the personTitle
	 */
	public String getPersonTitle() {
		return personTitle;
	}
	/**
	 * @param personTitle the personTitle to set
	 */
	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}
	/**
	 * @return the nonStandardName
	 */
	public String getNonStandardName() {
		return nonStandardName;
	}
	/**
	 * @param nonStandardName the nonStandardName to set
	 */
	public void setNonStandardName(String nonStandardName) {
		this.nonStandardName = nonStandardName;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	/**
	 * @return the nameType
	 */
	public String getNameType() {
		return nameType;
	}
	/**
	 * @param nameType the nameType to set
	 */
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}
	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/**
	 * @return the preferredName
	 */
	public boolean getPreferredName() {
		return preferredName;
	}
	/**
	 * @param preferredName the preferredName to set
	 */
	public void setPreferredName(boolean preferredName) {
		this.preferredName = preferredName;
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
