/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesInfo;
import org.kuali.student.r2.lum.course.infc.CourseExpenditure;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;

/**
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "CourseExpenditureInfo", propOrder = {"affiliatedOrgs", "attributes" , "_futureElements" }) 
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseExpenditureInfo extends HasAttributesInfo implements CourseExpenditure, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CourseExpenditureInfo() {

    }

    public CourseExpenditureInfo(CourseExpenditure courseExpenditure) {
        super(courseExpenditure);
        if (courseExpenditure != null) {
            List<AffiliatedOrgInfo> affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
            for (AffiliatedOrg afflOrg : courseExpenditure.getAffiliatedOrgs()) {
                affiliatedOrgs.add(new AffiliatedOrgInfo(afflOrg));
            }
            this.affiliatedOrgs = affiliatedOrgs;
        }
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

}