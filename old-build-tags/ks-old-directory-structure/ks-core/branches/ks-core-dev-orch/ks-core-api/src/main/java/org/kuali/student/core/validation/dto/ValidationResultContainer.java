/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.validation.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResultContainer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
    
    @XmlElement
    protected ErrorLevel errorLevel = ErrorLevel.OK;

    @XmlElement
    protected String element = null;

    @XmlElement
    protected Integer derivedMinOccurs = 0;
    
    @XmlElement 
    protected String derivedMaxOccurs = null;
    
    @XmlElement
    protected Integer derivedMinLength = 0;
    
    @XmlElement
    protected String derivedMaxLength = null;
            
    @XmlElement
    protected String dataType = null;
    
    public ValidationResultContainer(){
    	super();
    }
    
    public ValidationResultContainer(String element) {
        super();        
        this.element = element;
    }
    

    /**
     * Adds an message to the result. If the error level is greater than the current error level, then the specified error
     * level becomes the current error level.
     *
     * @param level
     *            the error level associated with the message
     * @param message
     *            the message to add
     */
    public void addMessage(ErrorLevel level, String message) {
    	ValidationResultInfo val = new ValidationResultInfo();
    	val.setLevel(level);
    	val.setMessage(message);    	
    	
        if (level.getLevel() > errorLevel.getLevel()) {
            errorLevel = level;
        }
        
        validationResults.add(val);
    }

    /**
     * Returns the ValidationResult's error level
     *
     * @return
     */
    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    /**
     * Convenience method. Adds a message with an error level of WARN
     *
     * @param message
     *            the message to add
     */
    public void addWarning(String message) {        	
        addMessage(ErrorLevel.WARN, message);
    }

    /**
     * Convenience method. Adds a message with an error level of ERROR
     *
     * @param message
     *            the message to add
     */
    public void addError(String message) {
        addMessage(ErrorLevel.ERROR, message);
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
     *
     * @return true if getErrorLevel() == ErrorLevel.OK
     */
    public boolean isOk() {
        return getErrorLevel() == ErrorLevel.OK;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
     *
     * @return true if getErrorLevel() == ErrorLevel.WARN
     */
    public boolean isWarn() {
        return getErrorLevel() == ErrorLevel.WARN;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
     *
     * @return true if getErrorLevel() == ErrorLevel.ERROR
     */
    public boolean isError() {
        return getErrorLevel() == ErrorLevel.ERROR;
    }


	/**
	 * @return the validationResults
	 */
	public List<ValidationResultInfo> getValidationResults() {
		return validationResults;
	}


	/**
	 * @param validationResults the validationResults to set
	 */
	public void setValidationResults(
			List<ValidationResultInfo> validationResults) {
		this.validationResults = validationResults;
	}


	/**
	 * @return the element
	 */
	public String getElement() {
		return element;
	}

	/**
	 * @return the derivedMinOccurs
	 */
	public Integer getDerivedMinOccurs() {
		return derivedMinOccurs;
	}


	/**
	 * @param derivedMinOccurs the derivedMinOccurs to set
	 */
	public void setDerivedMinOccurs(Integer derivedMinOccurs) {
		this.derivedMinOccurs = derivedMinOccurs;
	}


	/**
	 * @return the derivedMaxOccurs
	 */
	public String getDerivedMaxOccurs() {
		return derivedMaxOccurs;
	}


	/**
	 * @param derivedMaxOccurs the derivedMaxOccurs to set
	 */
	public void setDerivedMaxOccurs(String derivedMaxOccurs) {
		this.derivedMaxOccurs = derivedMaxOccurs;
	}


	/**
	 * @return the derivedMinLength
	 */
	public Integer getDerivedMinLength() {
		return derivedMinLength;
	}


	/**
	 * @param derivedMinLength the derivedMinLength to set
	 */
	public void setDerivedMinLength(Integer derivedMinLength) {
		this.derivedMinLength = derivedMinLength;
	}


	/**
	 * @return the derivedMaxLength
	 */
	public String getDerivedMaxLength() {
		return derivedMaxLength;
	}


	/**
	 * @param derivedMaxLength the derivedMaxLength to set
	 */
	public void setDerivedMaxLength(String derivedMaxLength) {
		this.derivedMaxLength = derivedMaxLength;
	}


	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}


	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	/**
	 * @param errorLevel the errorLevel to set
	 */
	public void setErrorLevel(ErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}

	public void setElement(String element) {
		this.element = element;
	}
}
