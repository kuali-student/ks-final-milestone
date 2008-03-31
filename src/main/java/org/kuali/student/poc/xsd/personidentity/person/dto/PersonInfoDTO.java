package org.kuali.student.poc.xsd.personidentity.person.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.util.jaxb.JaxbAttributeMapListAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonInfoDTO {
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private Date dob;

	@XmlElement
	private String firstName;
	
	@XmlElement
	private String lastName;
	
	@XmlElement
	private boolean confidential;
	
	@XmlElement
	private char gender;
	
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String, String> attributes;
	
	public PersonInfoDTO() {
		id = null;
		attributes = null;
	}

	public void setAttribute(String type, String value){
		this.getAttributes().put(type, value);
	}
	
	public String getAttribute(String type){
		return this.getAttributes().get(type);
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the confidential
	 */
	public boolean isConfidential() {
		return confidential;
	}

	/**
	 * @param confidential the confidential to set
	 */
	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
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


}
