/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.ValidationResult;
import org.w3c.dom.Element;

/**
 * Information about the results of a data validation.
 *
 * @author nwright
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationResultInfo", propOrder = {
                "element", "level", "message",
                "_futureElements"})

public class ValidationResultInfo
    implements ValidationResult, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String element;
    
    @XmlElement
    private ErrorLevel level;
    
    @XmlElement
    private String message;

//  used to hold debugging information 
//  not intended to be sent over the wire          
    private transient Object invalidData;

    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new ValidationResultInfo.
     */
    public ValidationResultInfo() {
      this.level = ErrorLevel.OK;
    }
    
    /**
     * convenience constructor carried over from R1
     */
    public ValidationResultInfo(String element) {
        this();
        this.element = element;
    }
    
    /**
     * convenience constructor carried over from R1
     */
    public ValidationResultInfo(String element, Object invalidData) {
        this(element);
        this.invalidData = invalidData;
    }

    /**
     * Constructs a new ValidationResultInfo from another
     * ValidationResult.
     *
     * @param result the ValidationResult to copy
     */
    public ValidationResultInfo(ValidationResult result) {
        if (result != null) {
            this.level = result.getLevel();
            this.element = result.getElement();
            this.message = result.getMessage();
        }
    }

    /**
     * This is for compatibility with R1.
     * Use getLevel instead
     */
    @Deprecated
    public ErrorLevel getErrorLevel() {
        return level;
    }

    public void setErrorLevel(ErrorLevel errorLevel) {
        this.level = errorLevel;
    }

    /**
     * Convenience method from R1 to check if this is ok
     */
    public boolean isOk() {
        return this.level == ErrorLevel.OK;
    }

    /**
     * convenience method carried over from R1
     */
    public boolean isWarn() {
        return this.level == ErrorLevel.WARN;
    }

    /**
     * convenience method carried over from R1
     */
    public void setWarn(String message) {
        this.level = ErrorLevel.WARN;
        this.message = message;
    }

    /**
     * convenience method carried over from R1
     */
    public boolean isError() {
        return this.level == ErrorLevel.ERROR;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    /**
     * convenience method carried over from R1
     * Use getErrorLevel () instead
     */
    @Override
    public ErrorLevel getLevel() {
        return this.level;
    }

    public void setLevel(ErrorLevel level) {
        this.level = level;
    }
    
    /**
     * not part of the contract but carried over from r1
     */
    public Object getInvalidData() {
        return invalidData;
    }

    /**
     * not part of the contract but carried over from r1
     */
    public void setInvalidData(Object invalidData) {
        this.invalidData = invalidData;
    }

    @Override
    public String toString() {
        return "[" + level + "] Path: [" + element + "] - " + message + " data=[" + invalidData + "]";
    }

    /**
     * Convenience method. Adds a message with an error level of WARN
     *
     * @param message the warning message 
     */
    public void setWarning(String message) {
        this.level = ErrorLevel.WARN;
        this.message = message;
    }

    /**
     * Convenience method. Adds a message with an error level of ERROR
     *
     * @param message the error message to add
     */
    public void setError(String message) {
        this.level = ErrorLevel.ERROR;
        this.message = message;
    }
}
