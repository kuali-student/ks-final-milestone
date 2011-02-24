/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private List<ConstraintMetadata> childConstraints;

	private String comments;

	private String desc;

	private String id;

	private Integer maxLength;

	private Integer maxOccurs;

	private String maxValue;

	private Integer minLength;

	protected Integer minOccurs;

	private String minValue;

	private boolean serverSide;

	protected String specialValidator;

	private String validChars;

	private String validCharsMessageId;
	
	private boolean requiredForNextState = false;
	
	private String nextState;

	public List<ConstraintMetadata> getChildConstraints() {
		return childConstraints;
	}

	public void setChildConstraints(List<ConstraintMetadata> childConstraints) {
		this.childConstraints = childConstraints;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public boolean isServerSide() {
		return serverSide;
	}

	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}

	public String getSpecialValidator() {
		return specialValidator;
	}

	public void setSpecialValidator(String specialValidator) {
		this.specialValidator = specialValidator;
	}

	public String getValidChars() {
		return validChars;
	}

	public void setValidChars(String validChars) {
		this.validChars = validChars;
	}

	public String getValidCharsMessageId() {
		return validCharsMessageId;
	}

	public void setValidCharsMessageId(String validCharsMessageId) {
		this.validCharsMessageId = validCharsMessageId;
	}

	public boolean isRequiredForNextState() {
		return requiredForNextState;
	}

	public void setRequiredForNextState(boolean requiredForNextState) {
		this.requiredForNextState = requiredForNextState;
	}

	public String getNextState() {
		return nextState;
	}

	public void setNextState(String nextState) {
		this.nextState = nextState;
	}	
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        _toString(sb);
        return sb.toString();
    }
    
    protected void _toString(StringBuilder sb) {
        sb.append("comments: ");
        sb.append(comments);
        sb.append(", desc: ");
        sb.append(desc);
        sb.append(", id: ");
        sb.append(id);
        sb.append(", maxLength: ");
        sb.append(null != maxLength ? maxLength.toString() : "null");
        sb.append(", maxOccurs: ");
        sb.append(null != maxOccurs ? maxOccurs.toString() : "null");
        sb.append(", maxValue: ");
        sb.append(maxValue);
        sb.append(", minLength: ");
        sb.append(null != minLength ? minLength.toString() : "null");
        sb.append(", minOccurs: ");
        sb.append(null != minOccurs ? minOccurs.toString() : "null");
        sb.append(", minValue: ");
        sb.append(minValue);
        sb.append(", serverSide: ");
        sb.append(Boolean.toString(serverSide));
        sb.append(", specialValidator: ");
        sb.append(specialValidator);
        sb.append(", validCharsMessageId: ");
        sb.append(validCharsMessageId);
        sb.append(", validChars: ");
        sb.append(validChars);
        sb.append(", requiredForNextState: ");
        sb.append(Boolean.toString(requiredForNextState));
        sb.append(", nextState: ");
        sb.append(nextState);
        sb.append(", ChildConstraints: {");
        if (null != childConstraints) {
            for (ConstraintMetadata constraint : childConstraints) {
                constraint._toString(sb);
            }
        }
        sb.append("}");
    }
}
