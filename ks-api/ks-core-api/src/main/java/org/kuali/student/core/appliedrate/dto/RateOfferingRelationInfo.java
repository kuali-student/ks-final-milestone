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

import org.kuali.student.core.appliedrate.infc.RateOfferingRelation;

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
@XmlType(name = "RateOfferingRelationInfo", propOrder = {
        "id", "typeKey", "stateKey",
        "rateId", "formatOfferingId", "activityOfferingIds",
        "meta", "attributes", "_futureElements" })

public class RateOfferingRelationInfo
    extends RelationshipInfo 
    implements RateOfferingRelation {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String rateId;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private List<String> activityOfferingIds;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new RateOfferingRelationInfo.
     */
    public RateOfferingRelationInfo() {
    }

    /**
     * Constructs a new RateOfferingRelationInfo from another
     * RateOfferingRelation.
     *
     * @param relation the RateOfferingRelation to copy
     */
    public RateOfferingRelationInfo(RateOfferingRelation relation) {
        super(relation);

        if (relation != null) {
            this.rateId = relation.getRateId();
            this.formatOfferingId = relation.getFormatOfferingId();

            if (relation.getActivityOfferingIds() != null) {
                this.activityOfferingIds = new ArrayList<String>(relation.getActivityOfferingIds());
            }
        }
    }


    @Override
    public String getRateId() {
        return (this.rateId);
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    @Override
    public String getFormatOfferingId() {
        return (this.formatOfferingId);
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        if (this.activityOfferingIds == null) {
            this.activityOfferingIds = new ArrayList<String>(0);
        }

        return (this.activityOfferingIds);
    }

    public void setActivityOfferingIds(List <String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }
}
