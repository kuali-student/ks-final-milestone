package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GwtPersonCreateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 376250699879975417L;

	protected List<GwtPersonNameInfo> name;

	protected char gender;

	protected Date birthDate;

	protected List<GwtPersonReferenceIdInfo> referenceId;

	protected GwtPersonCitizenshipInfo citizenship;

	protected Map<String, String> attributes;

	protected Date createTime;

	protected String createUserId;

	protected String createUserComment;

	/**
	 * @return the name
	 */
	public List<GwtPersonNameInfo> getName() {
		if (name == null) {
			name = new ArrayList<GwtPersonNameInfo>();
		}
		return name;
	}

	public GwtPersonNameInfo getPreferredName(){
		GwtPersonNameInfo	pRet = null;
		
		if(this.name != null && this.name.size() > 0){
			for(GwtPersonNameInfo	pCurr : name){
				if(pCurr.getPreferredName() == true){
					pRet = pCurr;
				}
			}
			// if preferred is not set, set it to the first element.
			if(pRet == null)
				pRet = this.name.get(0);
		}
		
		return pRet;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(List<GwtPersonNameInfo> name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the referenceId
	 */
	public List<GwtPersonReferenceIdInfo> getReferenceId() {
		if (referenceId == null) {
			referenceId = new ArrayList<GwtPersonReferenceIdInfo>();
		}
		return referenceId;
	}

	/**
	 * @param referenceId
	 *            the referenceId to set
	 */
	public void setReferenceId(List<GwtPersonReferenceIdInfo> referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the citizenship
	 */
	public GwtPersonCitizenshipInfo getCitizenship() {
		return citizenship;
	}

	/**
	 * @param citizenship
	 *            the citizenship to set
	 */
	public void setCitizenship(GwtPersonCitizenshipInfo citizenship) {
		this.citizenship = citizenship;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId
	 *            the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createUserComment
	 */
	public String getCreateUserComment() {
		return createUserComment;
	}

	/**
	 * @param createUserComment
	 *            the createUserComment to set
	 */
	public void setCreateUserComment(String createUserComment) {
		this.createUserComment = createUserComment;
	}

	public String getAttribute(String attributeName) {
		return this.getAttributes().get(attributeName);
	}

	public void setAttribute(String attributeName, String value) {
		this.getAttributes().put(attributeName, value);
	}

}
