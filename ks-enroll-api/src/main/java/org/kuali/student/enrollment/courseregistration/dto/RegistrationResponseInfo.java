/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationResponse;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationResponseItem;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationResponseInfo", propOrder = {
                "registrationRequestId", "hasFailed", "messages",
                "registrationResponseItems", "_futureElements"})

public class RegistrationResponseInfo 
    implements RegistrationResponse, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String registrationRequestId;

    @XmlElement
    private Boolean hasFailed;

    @XmlElement
    private List<String> messages;

    @XmlElement
    private List<RegistrationResponseItemInfo> registrationResponseItems;

    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     *  Constructs a new RegistrationResponseInfo.
     */
    public RegistrationResponseInfo() {
    }

    /**
     * Constructs a new RegistrationResponseInfo from another
     * RegistrationResponse.
     *
     * @param registrationResponse the RegistrationResponse to copy
     */
    public RegistrationResponseInfo(RegistrationResponse registrationResponse) {
        
        if (registrationResponse == null) {
            return;
        }

        this.registrationRequestId = registrationResponse.getRegistrationRequestId();
        this.hasFailed = registrationResponse.getHasFailed();
        
        if (registrationResponse.getMessages() != null) {
            this.messages = new ArrayList(registrationResponse.getMessages());
        }

        this.registrationResponseItems = new ArrayList<RegistrationResponseItemInfo>();
        
        for (RegistrationResponseItem registrationResponseItem : registrationResponse.getRegistrationResponseItems()) {
            this.registrationResponseItems.add(new RegistrationResponseItemInfo(registrationResponseItem));
        }
    }

    @Override
    public String getRegistrationRequestId() {
        return registrationRequestId;
    }

    public void setRegistrationRequestId(String registrationRequestId) {
        this.registrationRequestId = registrationRequestId;
    }

    @Override
    public Boolean getHasFailed() {
        return hasFailed;
    }

    public void setHasFailed(Boolean hasFailed) {
        this.hasFailed = hasFailed;
    }

    @Override
    public List<String> getMessages() {
        if (messages == null) {
            messages = new ArrayList<String>();
        }

        return messages;
    }

    public void setMessagess(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public List<RegistrationResponseItemInfo> getRegistrationResponseItems() {
        if (registrationResponseItems == null) {
            registrationResponseItems = new ArrayList<RegistrationResponseItemInfo>();
        }

        return registrationResponseItems;
    }

    public void setRegistrationResponseItems(List<RegistrationResponseItemInfo> registrationResponseItems) {
        this.registrationResponseItems = registrationResponseItems;
    }
}
