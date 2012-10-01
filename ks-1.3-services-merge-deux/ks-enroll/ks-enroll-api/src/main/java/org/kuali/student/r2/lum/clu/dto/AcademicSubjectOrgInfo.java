/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.lum.clu.infc.AcademicSubjectOrg;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Author KSContractMojo
 * @Author Kamal
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicSubjectOrgInfo", propOrder = {"orgId", "_futureElements"})
public class AcademicSubjectOrgInfo implements AcademicSubjectOrg, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public AcademicSubjectOrgInfo() {
    }

    public AcademicSubjectOrgInfo(AcademicSubjectOrg academicSubjectOrg) {
        if (null != academicSubjectOrg) {
            this.orgId = academicSubjectOrg.getOrgId();
        }
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}