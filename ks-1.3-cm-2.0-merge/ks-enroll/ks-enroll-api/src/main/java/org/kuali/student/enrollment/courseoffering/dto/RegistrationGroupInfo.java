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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationGroupInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "formatOfferingId",  "courseOfferingId", "termId", 
                "registrationCode", "activityOfferingIds", 
                "isHonorsOffering", "maximumEnrollment", "isGenerated",
                "meta", "attributes", "_futureElements"})

public class RegistrationGroupInfo 
    extends IdEntityInfo 
    implements RegistrationGroup {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String termId;
    
    @XmlElement
    private String registrationCode;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlElement
    private Boolean isGenerated;

    @XmlAnyElement
    private List<Element> _futureElements;

    
    /**
     * Constructs a new RegistrationGroupInfo.
     */
    public RegistrationGroupInfo() {
    }

    /**
     * Constructs a new RegistrationGroupInfo from another
     * RegistrationGroup.
     *
     * @param registrationGroup the registration group to copy
     */
    public RegistrationGroupInfo(RegistrationGroup registrationGroup) {
        super(registrationGroup); 
        
        if (registrationGroup == null) {
            return;      
        }

        this.formatOfferingId = registrationGroup.getFormatOfferingId();
        this.courseOfferingId = registrationGroup.getCourseOfferingId();
        this.termId = registrationGroup.getTermId();
        
        this.registrationCode = registrationGroup.getRegistrationCode();
        
        if (registrationGroup.getActivityOfferingIds() != null) {
            this.activityOfferingIds = new ArrayList<String>(registrationGroup.getActivityOfferingIds());
        }

        this.isHonorsOffering = registrationGroup.getIsHonorsOffering();
        this.maximumEnrollment = registrationGroup.getMaximumEnrollment();
        
        this.isGenerated = registrationGroup.getIsGenerated();
        
    }

    @Override
    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    @Override
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        if (activityOfferingIds == null) {
            activityOfferingIds = new ArrayList<String>();
        }

        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return isHonorsOffering;
    }

    public void setIsHonorsOffering(Boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public Boolean getIsGenerated() {
        return isGenerated;
    }

    public void setIsGenerated(Boolean isGenerated) {
        this.isGenerated = isGenerated;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegistrationGroupInfo [id=");
        builder.append(getId());
        builder.append("formatOfferingId=");
        builder.append(formatOfferingId);
        builder.append(", courseOfferingId=");
        builder.append(courseOfferingId);
        builder.append(", termId=");
        builder.append(termId);
        builder.append(", activityOfferingIds=");
        builder.append(StringUtils.join(activityOfferingIds, ", "));
        builder.append(", registrationCode=");
        builder.append(registrationCode);
        builder.append(", maximumEnrollment=");
        builder.append(maximumEnrollment);
        builder.append(", isHonorsOffering=");
        builder.append(isHonorsOffering);
        builder.append(", isGenerated=");
        builder.append(isGenerated);
        builder.append("]");
        return builder.toString();
    }
}
