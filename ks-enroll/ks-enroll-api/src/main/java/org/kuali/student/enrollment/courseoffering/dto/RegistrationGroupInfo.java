/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationGroupInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "activityOfferingIds", 
                "courseOfferingId", "registrationCode", "termId", "formatOfferingId",
                "isHonorsOffering", "maximumEnrollment", 
                "meta", "attributes", "_futureElements"})

public class RegistrationGroupInfo extends IdEntityInfo implements RegistrationGroup {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String registrationCode;

    @XmlElement
    private String termId;
    
    @XmlElement
    private Boolean isHonorsOffering;

    @XmlAnyElement
    private List<Element> _futureElements;

    
    public RegistrationGroupInfo() {
    }

    public RegistrationGroupInfo(RegistrationGroup registrationGroup) {
        super(registrationGroup); 
        
        if (registrationGroup == null) {
            return;      
        }
        
        this.activityOfferingIds = (null != registrationGroup.getActivityOfferingIds()) ? new ArrayList<String>(registrationGroup.getActivityOfferingIds()) : null;
        this.courseOfferingId = registrationGroup.getCourseOfferingId();
        this.formatOfferingId = registrationGroup.getFormatOfferingId();
        this.isHonorsOffering = (null != registrationGroup.getIsHonorsOffering()) ? new Boolean(registrationGroup.getIsHonorsOffering()) : null;
        this.maximumEnrollment = registrationGroup.getMaximumEnrollment();
        this.registrationCode = registrationGroup.getRegistrationCode();
        this.termId = registrationGroup.getTermId();
    }

    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    @Override
    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    @Override
    public String getRegistrationCode() {
        return registrationCode;
    }

   @Override
    public String getTermId() {
        return termId;
    }
    
    @Override
    public Boolean getIsHonorsOffering() {
        return isHonorsOffering;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }
}
