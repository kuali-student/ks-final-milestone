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
package org.kuali.student.r2.lum.course.dto;

import com.google.gwt.dom.client.Element;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


import org.kuali.student.r2.lum.course.infc.AffiliatedOrg;

/**
 * Information about a fee related to a course.
 *
 * @Author KSContractMojo
 * @Author Daniel Epstein
 * @Since Mon Jul 26 14:12:33 EDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseFeeInfo+Structure">CourseFeeInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AffiliatedOrgInfo", propOrder = {"id", "orgId", "percentage",
    "effectiveDate", "expirationDate", "_futureElements"})
public class AffiliatedOrgInfo implements AffiliatedOrg, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private String id;
    @XmlElement
    private String orgId;
    @XmlElement
    private Long percentage;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Element> _futureElements;
    
    public AffiliatedOrgInfo() {
        super();
        id = null;
        orgId = null;
        percentage = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public AffiliatedOrgInfo(AffiliatedOrg affiliatedOrg) {
        this.id = affiliatedOrg.getId();
        this.percentage = affiliatedOrg.getPercentage();
        if (affiliatedOrg.getEffectiveDate() != null) {
            this.effectiveDate = new Date(affiliatedOrg.getEffectiveDate().getTime());
        }
        else {
            this.effectiveDate = null;
        }
            
        if (affiliatedOrg.getExpirationDate() != null) {
            this.expirationDate = new Date(affiliatedOrg.getExpirationDate().getTime());
        }
        else {
            this.expirationDate = null;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}