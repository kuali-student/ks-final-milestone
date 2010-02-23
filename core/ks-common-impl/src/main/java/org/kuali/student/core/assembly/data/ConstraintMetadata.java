/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ConstraintMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	/**
	 * Get the value of id
	 * 
	 * @return the value of id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the value of id
	 * 
	 * @param id
	 *            new value of id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@XmlTransient
	private List<ConstraintMetadata> childConstraints;

	/**
	 * Get the value of childConstraints
	 * 
	 * @return the value of childConstraints
	 */
	public List<ConstraintMetadata> getChildConstraints() {
		return childConstraints;
	}

	/**
	 * Set the value of childConstraints
	 * 
	 * @param childConstraints
	 *            new value of childConstraints
	 */
	public void setChildConstraints(List<ConstraintMetadata> childConstraints) {
		this.childConstraints = childConstraints;
	}

	private String messageId;

	/**
	 * Get the value of messageId
	 * 
	 * @return the value of messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Set the value of messageId
	 * 
	 * @param messageId
	 *            new value of messageId
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	private String desc;

	/**
	 * Get the value of desc
	 * 
	 * @return the value of desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Set the value of desc
	 * 
	 * @param desc
	 *            new value of desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	private boolean serverSide;

	/**
	 * Get the value of serverSide
	 * 
	 * @return the value of serverSide
	 */
	public boolean getServerSide() {
		return serverSide;
	}

	/**
	 * Set the value of serverSide
	 * 
	 * @param serverSide
	 *            new value of serverSide
	 */
	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}

	private Integer minLength;

	/**
	 * Get the value of minLength
	 * 
	 * @return the value of minLength
	 */
	public Integer getMinLength() {
		return minLength;
	}

	/**
	 * Set the value of minLength
	 * 
	 * @param minLength
	 *            new value of minLength
	 */
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	private Integer maxLength;

	/**
	 * Get the value of maxLength
	 * 
	 * @return the value of maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}

	/**
	 * Set the value of maxLength
	 * 
	 * @param maxLength
	 *            new value of maxLength
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	private String minValue;

	/**
	 * Get the value of minValue
	 * 
	 * @return the value of minValue
	 */
	public String getMinValue() {
		return minValue;
	}

	/**
	 * Set the value of minValue
	 * 
	 * @param minValue
	 *            new value of minValue
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	private String maxValue;

	/**
	 * Get the value of maxValue
	 * 
	 * @return the value of maxValue
	 */
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * Set the value of maxValue
	 * 
	 * @param maxValue
	 *            new value of maxValue
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	protected Integer minOccurs;

	/**
	 * Get the value of minOccurs
	 * 
	 * @return the value of minOccurs
	 */
	public Integer getMinOccurs() {
		return minOccurs;
	}

	/**
	 * Set the value of minOccurs
	 * 
	 * @param minOccurs
	 *            new value of minOccurs
	 */
	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}

	private Integer maxOccurs;

	/**
	 * Get the value of maxOccurs
	 * 
	 * @return the value of maxOccurs
	 */
	public Integer getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * Set the value of maxOccurs
	 * 
	 * @param maxOccurs
	 *            new value of maxOccurs
	 */
	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	private String validChars;

	/**
	 * Get the value of validChars
	 * 
	 * @return the value of validChars
	 */
	public String getValidChars() {
		return validChars;
	}

	/**
	 * Set the value of validChars
	 * 
	 * @param validChars
	 *            new value of validChars
	 */
	public void setValidChars(String validChars) {
		this.validChars = validChars;
	}

	private String comments;

	/**
	 * Get the value of comments
	 * 
	 * @return the value of comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Set the value of comments
	 * 
	 * @param comments
	 *            new value of comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	protected String specialValidator;

	/**
	 * Get the value of specialValidator
	 * 
	 * @return the value of specialValidator
	 */
	public String getSpecialValidator() {
		return specialValidator;
	}

	/**
	 * Set the value of specialValidator
	 * 
	 * @param specialValidator
	 *            new value of specialValidator
	 */
	public void setSpecialValidator(String specialValidator) {
		this.specialValidator = specialValidator;
	}

}
