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
package org.kuali.student.r2.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.lum.lu.infc.Revenue;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevenueInfo", propOrder = {"id", "feeType", "affiliatedOrgs",
       "attributes", "meta", "_futureElements"})
public class RevenueInfo extends HasAttributesAndMetaInfo implements Revenue, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute
    private String id;
    
    @XmlElement
    private String feeType;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RevenueInfo() {
        this.id = null;
        this.feeType = null;
        this.affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        this._futureElements = null;
    }
    
    public RevenueInfo(Revenue rev) {
        super(rev);
        
        if(null == rev) return;
        
        this.feeType = rev.getFeeType();
        this.affiliatedOrgs = (null != rev.getAffiliatedOrgs()) ? new ArrayList<AffiliatedOrgInfo>((List<AffiliatedOrgInfo>)rev.getAffiliatedOrgs()) : null;
        this.id = rev.getId();
    }
    
    @Override
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

 
    @Override
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        if (affiliatedOrgs == null) {
            affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>(0);
        }
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}