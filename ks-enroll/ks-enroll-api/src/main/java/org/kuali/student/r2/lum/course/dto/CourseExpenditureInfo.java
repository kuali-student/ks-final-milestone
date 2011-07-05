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
import org.kuali.student.r2.lum.course.infc.CourseExpenditure;
import org.w3c.dom.Element;

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
@XmlType(name = "CourseExpenditureInfo", propOrder = {"id", "affiliatedOrgs",
       "attributes", "meta", "_futureElements"})
public class CourseExpenditureInfo extends HasAttributesAndMetaInfo implements CourseExpenditure, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute
    private String id;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseExpenditureInfo() {
        this.id = null;
        this.affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        this._futureElements = null;
    }
    
    public CourseExpenditureInfo(CourseExpenditure exp) {
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