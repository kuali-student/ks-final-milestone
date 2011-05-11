/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationResultInfo", propOrder = {"element", "level", "message", "_futureElements"})
public class ValidationResultInfo implements ValidationResult, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String element;
    @XmlElement
    private Integer level;
    @XmlElement
    private String message;
    private transient Object invalidData;
    @XmlAnyElement
    private final List<Element> _futureElements;    


    public static ValidationResultInfo newInstance() {
        return new ValidationResultInfo();
    }
    
    private ValidationResultInfo() {
        this.level = null;
        this.message = null;
        this.invalidData = null;
        this._futureElements = null;
    }

    private ValidationResultInfo(ValidationResult builder) {
        this.level = builder.getLevel();
        this.element = builder.getElement();
        this.message = builder.getMessage();
        this.invalidData = builder.getInvalidData();
        this._futureElements = null;
    }


    @Override
    public boolean isOk() {
        return getLevel() == ErrorLevel.OK.getLevel();
    }

    @Override
    public boolean isWarn() {
        return getLevel() == ErrorLevel.WARN.getLevel();
    }

    @Override
    public void setWarn(String message) {
        this.level = ErrorLevel.WARN.getLevel();
        this.message = message;
    }
    
    @Override
    public boolean isError() {
        return getLevel() == ErrorLevel.ERROR.getLevel();
    }

    @Override
    public void setError(String message) {
        this.level = ErrorLevel.ERROR.getLevel();
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + level + "] Path: [" + element + "] - " + message + " data=[" + invalidData + "]";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getElement() {
        return element;
    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Object getInvalidData() {
        return invalidData;
    }

    @Override
    public void setInvalidData(Object invalidData) {
        this.invalidData = invalidData;
    }
}
