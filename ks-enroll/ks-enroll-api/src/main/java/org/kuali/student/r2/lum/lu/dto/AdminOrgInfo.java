/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lu.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lu.infc.AdminOrg;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdminOrgInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "isPrimary", "orgId", "meta", "attributes", "_futureElements"})
public class AdminOrgInfo extends IdEntityInfo implements AdminOrg, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;
    @XmlElement
    private boolean isPrimary;
    @XmlAnyElement
    private List<Element> _futureElements;

    public AdminOrgInfo() {

    }

    public AdminOrgInfo(AdminOrg adminOrg) {
        super(adminOrg);
        if (adminOrg != null) {
            this.orgId = adminOrg.getOrgId();
            this.isPrimary = adminOrg.isPrimary();
        }
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

}
