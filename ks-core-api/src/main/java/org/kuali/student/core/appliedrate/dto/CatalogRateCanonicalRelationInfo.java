/*
 * Copyright 2013 The Kuali Foundation 
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

package org.kuali.student.core.appliedrate.dto;

import org.kuali.student.core.appliedrate.infc.CatalogRateCanonicalRelation;

import org.kuali.student.r2.common.dto.RelationshipInfo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CatalogRateCanonicalRelationInfo", propOrder = {
        "id", "typeKey", "stateKey",
        "catalogRateId", "formatId", "activityIds",
        "meta", "attributes", "_futureElements" })

public class CatalogRateCanonicalRelationInfo
    extends RelationshipInfo 
    implements CatalogRateCanonicalRelation {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String catalogRateId;

    @XmlElement
    private String formatId;

    @XmlElement
    private List<String> activityIds;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CatalogRateCanonicalRelationInfo.
     */
    public CatalogRateCanonicalRelationInfo() {
    }

    /**
     * Constructs a new CatalogRateCanonicalRelationInfo from another
     * CatalogRateCanonicalRelation.
     *
     * @param relation the CatalogRateCanonicalRelation to copy
     */
    public CatalogRateCanonicalRelationInfo(CatalogRateCanonicalRelation relation) {
        super(relation);

        if (relation != null) {
            this.catalogRateId = relation.getCatalogRateId();
            this.formatId = relation.getFormatId();

            if (relation.getActivityIds() != null) {
                this.activityIds = new ArrayList<String>(relation.getActivityIds());
            }
        }
    }


    @Override
    public String getCatalogRateId() {
        return (this.catalogRateId);
    }

    public void setCatalogRateId(String catalogRateId) {
        this.catalogRateId = catalogRateId;
    }

    @Override
    public String getFormatId() {
        return (this.formatId);
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    @Override
    public List<String> getActivityIds() {
        if (this.activityIds == null) {
            this.activityIds = new ArrayList<String>(0);
        }

        return (this.activityIds);
    }

    public void setActivityIds(List <String> activityIds) {
        this.activityIds = activityIds;
    }
}
