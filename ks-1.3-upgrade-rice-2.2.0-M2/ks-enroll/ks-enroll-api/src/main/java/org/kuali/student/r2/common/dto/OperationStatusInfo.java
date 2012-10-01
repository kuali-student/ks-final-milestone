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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.OperationStatus;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationStatusInfo", propOrder = {
                "status", "messages", "warnings", "errors", 
                "_futureElements"})

public class OperationStatusInfo 
    implements OperationStatus, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String status;

    @XmlElement
    private List<String> messages;

    @XmlElement
    private List<String> warnings;

    @XmlElement
    private List<String> errors;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new OperationStatusInfo.
     */
    public OperationStatusInfo() {
    }
    
    /**
     * Constructs a new OperationStatusInfo from another
     * OperationStatus.
     *
     * @param status the OperationStatus to copy
     */
    public OperationStatusInfo (OperationStatus status) {
        if (status != null) {
            this.status = status.getStatus();
            if (status.getMessages() != null) {
                this.messages = new ArrayList(status.getMessages());
            }

            if (status.getWarnings() != null) {
                this.warnings = new ArrayList(status.getWarnings());
            }

            if (status.getErrors() != null) {
                this.errors = new ArrayList(status.getErrors());
            }
        }
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public List<String> getMessages() {
        if (messages == null) {
            messages = new ArrayList<String>(0);
        }

        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
    @Override
    public List<String> getWarnings() {
        if (warnings == null) {
            warnings = new ArrayList<String>(0);
        }

        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    @Override
    public List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<String>(0);
        }

        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
