package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.util.jaxb.JaxbAttributeMapListAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonUpdateInfo implements Serializable{

	private static final long serialVersionUID = 2398792732220533518L;
	
	@XmlElement
	@XmlElementWrapper(name="names")
	private List<PersonNameInfo> name;
	@XmlElement
	private char gender;
	@XmlElement
	private Date birthDate;
	@XmlElement
	@XmlElementWrapper(name="referenceIds")
	private List<PersonReferenceIdInfo> referenceId;
	@XmlElement
	private PersonCitizenshipInfo citizenship;
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String,String> attributes;
	

	@XmlElement
	private Date updateTime;
	@XmlElement
	private String updateUserId;
	@XmlElement
	private String updateUserComment;



	public PersonUpdateInfo(){
	    super();
	}
	
	public PersonUpdateInfo(PersonInfo personInfo){
        this.name = personInfo.getName();
	    this.birthDate = personInfo.getBirthDate();
        this.gender = personInfo.getGender();	    
	    this.referenceId = personInfo.getReferenceId();
	    this.citizenship = personInfo.getCitizenship();
	    this.attributes = personInfo.getAttributes();
	    
	    this.updateTime = personInfo.getUpdateTime();
	    this.updateUserComment = personInfo.getUpdateUserComment();
	    this.updateUserId = personInfo.getUpdateUserId();
	}

	
	/**
	 * @return the name
	 */
	public List<PersonNameInfo> getName() {
		if(name == null){
			name  = new ArrayList<PersonNameInfo>();
		}
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(List<PersonNameInfo> name) {
		this.name = name;
	}
	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
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
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the referenceId
	 */
	public List<PersonReferenceIdInfo> getReferenceId() {
		if(referenceId == null){
			referenceId  = new ArrayList<PersonReferenceIdInfo>();
		}
		return referenceId;
	}
	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(List<PersonReferenceIdInfo> referenceId) {
		this.referenceId = referenceId;
	}
	/**
	 * @return the citizenship
	 */
	public PersonCitizenshipInfo getCitizenship() {
		return citizenship;
	}
	/**
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(PersonCitizenshipInfo citizenship) {
		this.citizenship = citizenship;
	}
	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		if(attributes == null){
			attributes  = new HashMap<String, String> ();
		}
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
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
	
   public String getAttribute(String attributeName) {
        return this.getAttributes().get(attributeName);
    }

    public void setAttribute(String attributeName, String value) {
        this.getAttributes().put(attributeName, value);
    }
	
}
