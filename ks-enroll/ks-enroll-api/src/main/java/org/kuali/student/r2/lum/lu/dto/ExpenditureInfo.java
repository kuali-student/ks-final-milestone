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
import org.kuali.student.r2.lum.lu.infc.Expenditure;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpenditureInfo", propOrder = {"id", "affiliatedOrgs",
       "attributes", "meta", "_futureElements"})
public class ExpenditureInfo extends HasAttributesAndMetaInfo implements Expenditure, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute
    private String id;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ExpenditureInfo() {
        this.id = null;
        this.affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        this._futureElements = null;
    }
    
    public ExpenditureInfo(Expenditure exp) {
        super(exp);
        
        if(null == exp) return;
        
        this.affiliatedOrgs = (null != exp.getAffiliatedOrgs()) ? new ArrayList<AffiliatedOrgInfo>((List<AffiliatedOrgInfo>)exp.getAffiliatedOrgs()) : null;
        this.id = exp.getId();
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