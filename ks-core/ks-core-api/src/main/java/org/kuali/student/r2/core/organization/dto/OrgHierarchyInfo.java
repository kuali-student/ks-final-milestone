/*
 * Copyright 2010 The Kuali Foundation 
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
package org.kuali.student.r2.core.organization.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.organization.infc.OrgHierarchy;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Information for an organization hierarchy.
 *
 * @author tom
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgHierarchyInfo", propOrder = {
    "id", "typeKey", "stateKey", "name", "descr",
    "rootOrgId",
    "effectiveDate",
    "expirationDate",
    "meta", "attributes"/*, "_futureElements"*/})
public class OrgHierarchyInfo
        extends IdEntityInfo
        implements OrgHierarchy, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String rootOrgId;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    //    TODO KSCM-372: Non-GWT translatable code
    //@XmlAnyElement
    //private List<Element> _futureElements;

    /**
     * Constructs a new OrgHierarchyInfo.
     */
    public OrgHierarchyInfo() {
    }

    /**
     * Constructs a new OrgHierarchyInfo from an OrgHierarchy.
     *
     * @param orgHierarchy the OrgHierarchy to copy
     */
    public OrgHierarchyInfo(OrgHierarchy orgHierarchy) {
        super(orgHierarchy);

        if (orgHierarchy != null) {
            this.rootOrgId = orgHierarchy.getRootOrgId();
        }
    }

    @Override
    public String getRootOrgId() {
        return rootOrgId;
    }

    public void setRootOrgId(String rootOrgId) {
        this.rootOrgId = rootOrgId;
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
