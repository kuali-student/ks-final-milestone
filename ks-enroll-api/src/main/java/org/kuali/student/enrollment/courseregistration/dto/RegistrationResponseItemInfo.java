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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationResponseItem;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationResponseItemInfo", propOrder = {
                "registrationRequestItemId", "operationStatus",
                "courseRegistrationId", "courseWaitlistEntryId", "holdUntilListEntryId", 
                "_futureElements"})

public class RegistrationResponseItemInfo 
    implements RegistrationResponseItem, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String registrationRequestItemId;
   
    @XmlElement
    private OperationStatusInfo operationStatus;

    @XmlElement
    private String courseRegistrationId;

    @XmlElement
    private String courseWaitlistEntryId;

    @XmlElement
    private String holdUntilListEntryId;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     *  Constructs a new RegistrationResponseItemInfo.
     */
    public RegistrationResponseItemInfo() {
    }

    /**
     * Constructs a new RegistrationResponseItemInfo from another
     * RegistrationResponseItem.
     *
     * @param registrationResponseItem the RegistrationResponseItem to
     *        copy
     */
    public RegistrationResponseItemInfo(RegistrationResponseItem registrationResponseItem) {

        if (registrationResponseItem != null) {
            this.courseRegistrationId = registrationResponseItem.getCourseRegistrationId();
            if (registrationResponseItem.getOperationStatus() != null) {
                this.operationStatus = new OperationStatusInfo(registrationResponseItem.getOperationStatus());
            }

            this.courseRegistrationId = registrationResponseItem.getCourseRegistrationId();
            this.courseWaitlistEntryId = registrationResponseItem.getCourseWaitlistEntryId();
            this.holdUntilListEntryId = registrationResponseItem.getHoldUntilListEntryId();
        }
    }

    @Override
    public String getRegistrationRequestItemId() {
        return registrationRequestItemId;
    }

    public void setRegistrationRequestItemId(String registrationRequestItemId) {
        this.registrationRequestItemId = registrationRequestItemId;
    }

    @Override
    public OperationStatusInfo getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatusInfo operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public String getCourseRegistrationId() {
        return courseRegistrationId;
    }
 
    public void setCourseRegistrationId(String courseRegistrationId) {
        this.courseRegistrationId = courseRegistrationId;
    }

    @Override
    public String getCourseWaitlistEntryId() {
        return courseWaitlistEntryId;
    }

    public void setCourseWaitlistEntryId(String courseWaitlistEntryId) {
        this.courseWaitlistEntryId = courseWaitlistEntryId;
    }

    @Override
    public String getHoldUntilListEntryId() {
        return holdUntilListEntryId;
    }

    public void setHoldUntilListEntryId(String holdUntilListEntryId) {
        this.holdUntilListEntryId = holdUntilListEntryId;
    }
}
