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

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.organization.infc.OrgPersonRelation;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

/**
 * Information about an organization to person relationship.
 *
 * @author tom
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgPersonRelationInfo", propOrder = {
                "id", "typeKey", "stateKey", 
                "orgId", "personId",
                "effectiveDate", "expirationDate",
                "meta", "attributes", "_futureElements" })

public class OrgPersonRelationInfo 
    extends RelationshipInfo 
    implements OrgPersonRelation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;

    @XmlElement
    private String personId;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new OrgPersonRelationInfo.
     */
    public OrgPersonRelationInfo() {
    }

    /**
     * Constructs a new OrgPersonRelationInfo from an
     * OrgPersonRelation.
     *
     * @param orgPersonRelation the OrgPersonRelation to copy
     */
    public OrgPersonRelationInfo(OrgPersonRelation orgPersonRelation) {
        super(orgPersonRelation);

        if (orgPersonRelation != null) {
            this.orgId = orgPersonRelation.getOrgId();
            this.personId = orgPersonRelation.getPersonId();
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
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
