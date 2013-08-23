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

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.lum.clu.infc.Accreditation;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Accreditation information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccreditationInfo", propOrder = {"id", "orgId", "effectiveDate", "expirationDate", "attributes", "meta" , "_futureElements" }) 
public class AccreditationInfo extends HasAttributesAndMetaInfo implements Accreditation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String id;
    @XmlElement
    private String orgId;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public AccreditationInfo() {
    }

    public AccreditationInfo(Accreditation accreditation) {
        super(accreditation);
        if (null != accreditation) {
            this.id = accreditation.getId();
            this.orgId = accreditation.getOrgId();
            this.effectiveDate = (null != accreditation.getEffectiveDate()) ? new Date(accreditation.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != accreditation.getExpirationDate()) ? new Date(accreditation.getExpirationDate().getTime()) : null;
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
