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
    private Integer level;

    @XmlElement
    private String message;

    private transient Object invalidData;

    @XmlAnyElement
    private List<Element> _futureElements;    


    /**
     * Constructs a new ValidationResultInfo.
     */
    public ValidationResultInfo() {
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
            this.invalidData = result.getInvalidData();
        }
    }

    @Override
    public Boolean getIsOk() {
        return getLevel() == ErrorLevel.OK.getLevel();
    }

    @Override
    public Boolean getIsWarn() {
        return getLevel() == ErrorLevel.WARN.getLevel();
    }
    
    public void setWarn(String message) {
        this.level = ErrorLevel.WARN.getLevel();
        this.message = message;
    }
    
    @Override
    public Boolean getIsError() {
        return getLevel() == ErrorLevel.ERROR.getLevel();
    }
    
    public void setError(String message) {
        this.level = ErrorLevel.ERROR.getLevel();
        this.message = message;
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

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Object getInvalidData() {
        return invalidData;
    }
    
    public void setInvalidData(Object invalidData) {
        this.invalidData = invalidData;
    }

    @Override
    public String toString() {
        return "[" + level + "] Path: [" + element + "] - " + message + " data=[" + invalidData + "]";
    }


    // Compatibility methods

    @Deprecated
    public static ValidationResultInfo newInstance() {
        return new ValidationResultInfo();
    }
}
