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

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationRequestInfo", propOrder = {
                "id", "name", "descr", "typeKey", "stateKey", 
                "requestorId", "termId", "registrationRequestItems", 
                "meta", "attributes", "_futureElements"})

public class RegistrationRequestInfo 
    extends IdEntityInfo 
    implements RegistrationRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String requestorId;

    @XmlElement
    private String termId;

    @XmlElement
    private List<RegistrationRequestItemInfo> registrationRequestItems;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new RegistrationRequestInfo.
     */
    public RegistrationRequestInfo() { 
    }

    /**
     * Constructs a new RegistrationRequestInfo from another
     * RegistrationRequest.
     *
     * @param registrationRequest the RegistrationRequest to copy
     */
    public RegistrationRequestInfo(RegistrationRequest registrationRequest) {
        super(registrationRequest);

        if (registrationRequest != null) {
            this.requestorId = registrationRequest.getRequestorId();
            this.termId = registrationRequest.getTermId();
            this.registrationRequestItems = new ArrayList<RegistrationRequestItemInfo>();
      
            for (RegistrationRequestItem registrationRequestItem : registrationRequest.getRegistrationRequestItems()) {
                this.registrationRequestItems.add(new RegistrationRequestItemInfo(registrationRequestItem));
            }
        }
    }

    @Override
    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    @Override
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItems() {
        if (this.registrationRequestItems == null) {
            this.registrationRequestItems = new ArrayList<RegistrationRequestItemInfo>();
        }

        return registrationRequestItems;
    }

    public void setRegistrationRequestItems(List<RegistrationRequestItemInfo> registrationRequestItems) {
        this.registrationRequestItems = registrationRequestItems;
    }
}
