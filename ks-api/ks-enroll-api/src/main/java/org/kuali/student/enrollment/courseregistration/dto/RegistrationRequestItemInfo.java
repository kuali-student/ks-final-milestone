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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationRequestItemInfo", propOrder = {
        "id", "name", "descr", "typeKey", "stateKey",
        "registrationRequestId",
        "personId",
        "registrationGroupId",
        "existingCourseRegistrationId",
        "credits",
        "gradingOptionId",
        "requestedEffectiveDate",
        "okToWaitlist",
        "okToHoldUntilList",
        "okToRepeat",
        "validationResults",
        "crossListedCode", "courseRegistrationId", "courseWaitlistEntryId",
        "meta", "attributes", "_futureElements"})

public class RegistrationRequestItemInfo
        extends IdEntityInfo
        implements RegistrationRequestItem, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String registrationRequestId;

    @XmlElement
    private String personId;

    @XmlElement
    private String registrationGroupId;

    @XmlElement
    private String existingCourseRegistrationId;

    @XmlElement
    private KualiDecimal credits;

    @XmlElement
    private String gradingOptionId;

    @XmlElement
    private Date requestedEffectiveDate;

    @XmlElement
    private Boolean okToWaitlist;

    @XmlElement
    private Boolean okToHoldUntilList;

    @XmlElement
    private Boolean okToRepeat;

    @XmlElement
    private List<ValidationResultInfo> validationResults;

    @XmlElement
    private String crossListedCode;

    @XmlElement
    private String courseRegistrationId;

    @XmlElement
    private String courseWaitlistEntryId;

    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new RegistrationRequestItemInfo.
     */
    public RegistrationRequestItemInfo() {
    }

    /**
     * Constructs a new RegistrationRequestItemInfo from another
     * RegistrationRequestItem.
     *
     * @param registrationRequestItem the RegistrationRequestItem to
     *        copy
     */
    public RegistrationRequestItemInfo(RegistrationRequestItem registrationRequestItem) {
        super(registrationRequestItem);

        if (registrationRequestItem != null) {
            this.registrationRequestId = registrationRequestItem.getRegistrationRequestId();
            this.personId = registrationRequestItem.getPersonId();
            this.registrationGroupId = registrationRequestItem.getRegistrationGroupId();
            this.existingCourseRegistrationId = registrationRequestItem.getExistingCourseRegistrationId();
            if (registrationRequestItem.getCredits() != null) {
                this.credits = new KualiDecimal(registrationRequestItem.getCredits().bigDecimalValue());
            }
            this.gradingOptionId = registrationRequestItem.getGradingOptionId();
            this.requestedEffectiveDate = registrationRequestItem.getRequestedEffectiveDate();
            this.okToWaitlist = registrationRequestItem.getOkToWaitlist();
            this.okToHoldUntilList = registrationRequestItem.getOkToHoldUntilList();
            this.okToRepeat = registrationRequestItem.getOkToRepeat();
            this.validationResults = new ArrayList<>();
            this.crossListedCode = registrationRequestItem.getCrossListedCode();
            this.courseRegistrationId = registrationRequestItem.getCourseRegistrationId();
            this.courseWaitlistEntryId = registrationRequestItem.getCourseWaitlistEntryId();
            for(ValidationResult validationResult:registrationRequestItem.getValidationResults ()){
                this.getValidationResults().add(new ValidationResultInfo(validationResult));
            }
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
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getRegistrationGroupId() {
        return registrationGroupId;
    }

    public void setRegistrationGroupId(String registrationGroupId) {
        this.registrationGroupId = registrationGroupId;
    }

    @Override
    public String getExistingCourseRegistrationId() {
        return existingCourseRegistrationId;
    }

    public void setExistingCourseRegistrationId(String existingCourseRegistrationId) {
        this.existingCourseRegistrationId = existingCourseRegistrationId;
    }

    @Override
    public KualiDecimal getCredits() {
        return credits;
    }

    public void setCredits(KualiDecimal credits) {
        this.credits = credits;
    }

    @Override
    public String getGradingOptionId() {
        return gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }

    @Override
    public Date getRequestedEffectiveDate() {
        return requestedEffectiveDate;
    }

    public void setRequestedEffectiveDate(Date requestedEffectiveDate) {
        this.requestedEffectiveDate = requestedEffectiveDate;
    }

    @Override
    public Boolean getOkToWaitlist() {
        return okToWaitlist;
    }

    public void setOkToWaitlist(Boolean okToWaitlist) {
        this.okToWaitlist = okToWaitlist;
    }

    @Override
    public Boolean getOkToHoldUntilList() {
        return okToHoldUntilList;
    }

    public void setOkToHoldUntilList(Boolean okToHoldUntilList) {
        this.okToHoldUntilList = okToHoldUntilList;
    }

    @Override
    public Boolean getOkToRepeat() {
        return okToRepeat;
    }

    public void setOkToRepeat(Boolean okToRepeat) {
        this.okToRepeat = okToRepeat;
    }

    @Override
    public List<ValidationResultInfo> getValidationResults() {
        if (validationResults == null) {
            validationResults = new ArrayList<ValidationResultInfo>();
        }
        return validationResults;
    }

    public void setValidationResults(List<ValidationResultInfo> validationResults) {
        this.validationResults = validationResults;
    }

    @Override
    public String getCrossListedCode() { return crossListedCode; }

    public void setCrossListedCode(String crossListedCode) { this.crossListedCode = crossListedCode; }

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
}
