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

import org.kuali.rice.core.api.util.jaxb.KualiDecimalAdapter;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationRequestItemInfo", propOrder = {
                "id", "name", "descr", "typeKey", "stateKey",
                "registrationRequestId", 
                "personId", 
                "registrationGroupId",
                "existingCourseRegistrationId", 
                "credits", 
                "gradingOptionId", 
                "okToWaitlist", 
                "okToHoldUntilList", 
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
    @XmlJavaTypeAdapter(KualiDecimalAdapter.class)
    private KualiDecimal credits;

    @XmlElement
    private String gradingOptionId;

    @XmlElement
    private Boolean okToWaitlist;

    @XmlElement
    private Boolean okToHoldUntilList;

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
     * @param reqistrationRequestItem the RegistrationRequestItem to
     *        copy
     */
    public RegistrationRequestItemInfo(RegistrationRequestItem registrationRequestItem) {
        super(registrationRequestItem);

        if (registrationRequestItem != null) {
            this.registrationRequestId = registrationRequestItem.getRegistrationRequestId();
            this.personId = registrationRequestItem.getPersonId();
            this.registrationGroupId = registrationRequestItem.getRegistrationGroupId();
            this.existingCourseRegistrationId = registrationRequestItem.getExistingCourseRegistrationId();
            this.credits = new KualiDecimal(registrationRequestItem.getCredits().bigDecimalValue());
            this.gradingOptionId = registrationRequestItem.getGradingOptionId();
            this.okToWaitlist = registrationRequestItem.getOkToWaitlist();
            this.okToHoldUntilList = registrationRequestItem.getOkToHoldUntilList();
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
}
