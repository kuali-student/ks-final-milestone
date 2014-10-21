/*
 * Copyright 2013 The Kuali Foundation 
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

package org.kuali.student.enrollment.bundledoffering.dto;

import org.kuali.student.enrollment.bundledoffering.infc.BundledOffering;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BundledOfferingInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "courseBundleId", "termId",
        "bundledOfferingCode", "subjectAreaOrgId", "bundledOfferingCodeSuffix",
        "adminOrgIds", "formatOfferingIds", "registrationGroupIds",
        "meta", "attributes", "_futureElements" })

public class BundledOfferingInfo
    extends IdEntityInfo 
    implements BundledOffering {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseBundleId;

    @XmlElement
    private String termId;

    @XmlElement
    private String bundledOfferingCode;

    @XmlElement
    private String subjectAreaOrgId;

    @XmlElement
    private String bundledOfferingCodeSuffix;

    @XmlElement
    private List<String> adminOrgIds;

    @XmlElement
    private List<String> formatOfferingIds;

    @XmlElement
    private List<String> registrationGroupIds;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new BundledOfferingInfo.
     */
    public BundledOfferingInfo() {
    }

    /**
     * Constructs a new BundledOfferingInfo from another
     * BundledOffering.
     *
     * @param offering the BundledOffering to copy
     */
    public BundledOfferingInfo(BundledOffering offering) {
        super(offering);

        if (offering != null) {
            this.courseBundleId = offering.getCourseBundleId();
            this.termId = offering.getTermId();
            this.bundledOfferingCode = offering.getBundledOfferingCode();
            this.subjectAreaOrgId = offering.getSubjectAreaOrgId();
            this.bundledOfferingCodeSuffix = offering.getBundledOfferingCodeSuffix();

            if (offering.getAdminOrgIds() != null) {
                this.adminOrgIds = new ArrayList<String>(offering.getAdminOrgIds());
            }

            if (offering.getFormatOfferingIds() != null) {
                this.formatOfferingIds = new ArrayList<String>(offering.getFormatOfferingIds());
            }

            if (offering.getRegistrationGroupIds() != null) {
                this.registrationGroupIds = new ArrayList<String>(offering.getRegistrationGroupIds());
            }
        }
    }

    
    @Override
    public String getCourseBundleId() {
        return (this.courseBundleId);
    }
    
    public void setCourseBundleId(String courseBundleId) {
        this.courseBundleId = courseBundleId;
    }

    @Override
    public String getTermId() {
        return (this.termId);
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getBundledOfferingCode() {
        return (this.bundledOfferingCode);
    }

    public void setBundledOfferingCode(String bundledOfferingCode) {
        this.bundledOfferingCode = bundledOfferingCode;
    }

    @Override
    public String getSubjectAreaOrgId() {
        return (this.subjectAreaOrgId);
    }

    public void setSubjectAreaOrgId(String subjectAreaOrgId) {
        this.subjectAreaOrgId = subjectAreaOrgId;
    }

    @Override
    public String getBundledOfferingCodeSuffix() {
        return (this.bundledOfferingCodeSuffix);
    }

    public void setBundledOfferingCodeSuffix(String bundledOfferingCodeSuffix) {
        this.bundledOfferingCodeSuffix = bundledOfferingCodeSuffix;
    }

    @Override
    public List<String> getAdminOrgIds() {
        if (this.adminOrgIds == null) {
            this.adminOrgIds = new ArrayList<String>(0);
        }

        return (this.adminOrgIds);
    }

    public void setAdminOrgIds(List<String> adminOrgIds) {
        this.adminOrgIds = adminOrgIds;
    }

    @Override
    public List<String> getFormatOfferingIds() {
        if (this.formatOfferingIds == null) {
            this.formatOfferingIds = new ArrayList<String>(0);
        }

        return (this.formatOfferingIds);
    }

    public void setFormatOfferingIds(List<String> formatOfferingIds) {
        this.formatOfferingIds = formatOfferingIds;
    }

    @Override
    public List<String> getRegistrationGroupIds() {
        if (this.registrationGroupIds == null) {
            this.registrationGroupIds = new ArrayList<String>(0);
        }

        return (this.registrationGroupIds);
    }

    public void setRegistrationGroupIds(List<String> registrationGroupIds) {
        this.registrationGroupIds = registrationGroupIds;
    }
}
