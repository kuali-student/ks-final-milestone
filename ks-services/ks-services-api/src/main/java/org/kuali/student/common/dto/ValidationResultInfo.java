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
package org.kuali.student.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.common.infc.ValidationResultInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResultInfo implements ValidationResultInfc, Serializable {

    private static final long serialVersionUID = 1L;

    public ValidationResultInfo() {
        super();
        this.level = ErrorLevel.OK;
    }
    private transient Object invalidData = null;

    public ValidationResultInfo(String element) {
        super();
        this.level = ErrorLevel.OK;
        this.element = element;
    }

    public ValidationResultInfo(String element, Object invalidData) {
        this(element);
        this.invalidData = invalidData;
    }
    @XmlElement
    private String element;
    @XmlElement
    private ValidationResultInfc.ErrorLevel level = ErrorLevel.OK;
    @XmlElement
    private String message;


    /**
     * @param level the level to set
     */
    public void setLevel(ErrorLevel level) {
        this.level = level;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    /**
     * Returns the ValidationResult's error level
     *
     * @return
     */
    @Override
    public ErrorLevel getErrorLevel() {
        return level;
    }

    /**
     * Convenience method. Adds a message with an error level of WARN
     *
     * @param message
     *            the message to add
     */
    public void setWarning(String message) {
        this.level = ErrorLevel.WARN;
        this.message = message;
    }

    /**
     * Convenience method. Adds a message with an error level of ERROR
     *
     * @param message
     *            the message to add
     */
    public void setError(String message) {
        this.level = ErrorLevel.ERROR;
        this.message = message;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
     *
     * @return true if getErrorLevel() == ErrorLevel.OK
     */
    @Override
    public boolean isOk() {
        return getErrorLevel() == ErrorLevel.OK;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
     *
     * @return true if getErrorLevel() == ErrorLevel.WARN
     */
    @Override
    public boolean isWarn() {
        return getErrorLevel() == ErrorLevel.WARN;
    }

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
     *
     * @return true if getErrorLevel() == ErrorLevel.ERROR
     */
    @Override
    public boolean isError() {
        return getErrorLevel() == ErrorLevel.ERROR;
    }

    @Override
    public String toString() {
        return "[" + level + "] Path: [" + element + "] - " + message + " data=[" + invalidData + "]";
    }
}
