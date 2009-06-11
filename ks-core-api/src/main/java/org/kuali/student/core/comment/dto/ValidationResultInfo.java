/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.comment.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Contains information returned as a result of a validation operation.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:38 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/validationResultInfo+Structure+v1.0-rc2">ValidationResultInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResultInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String element;

    @XmlElement
    private String errorLevel;

    @XmlElement
    private String message;

    @XmlElement
    private String task;

    /**
     * Indication of which element the validation result pertains to. In the case of validation results which are stem from a cross type constraint, the element should be the containing object. Ex. if two citizenships (which are embedded in the person) fail a uniqueness constraint, the element indicated should be the person.
     */
    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    /**
     * Indication of the error level of the validation result. Allowed values are OK, WARN, and ERROR.
     */
    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    /**
     * Message generated for the validation. Describes failure conditions and success aspects. May have additional meta about the specifics of the constraint violated.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Indication of a potential task stemming from the current validation result. In the case of a warning or error, this may be a corrective action
     */
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}