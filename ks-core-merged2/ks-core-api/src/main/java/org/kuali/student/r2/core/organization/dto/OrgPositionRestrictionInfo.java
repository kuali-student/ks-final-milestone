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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.organization.infc.OrgPositionRestriction;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.w3c.dom.Element;

/**
 * Information which constrains/describes organization to person
 * relationships of a particular type for an organization. These
 * constraints/descriptions typically involve active relationships.
 *
 * @author tom
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgPersonRestrictionInfo", propOrder = {
    "id",
    "orgId", "orgPersonRelationTypeKey",
    "title", "descr",
    "stdDuration",
    "minNumRelations", "maxNumRelations",
    "meta", "attributes", "_futureElements"})
public class OrgPositionRestrictionInfo extends HasAttributesAndMetaInfo
        implements OrgPositionRestriction, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private String id;
    @XmlElement
    private String orgId;
    @XmlElement
    private String title;
    @XmlElement
    private RichTextInfo descr;
    @XmlElement
    private String orgPersonRelationTypeKey;
    @XmlElement
    private TimeAmountInfo stdDuration;
    @XmlElement
    private Integer minNumRelations;
    @XmlElement
    private String maxNumRelations;
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new OrgPositionrestrictionInfo.
     */
    public OrgPositionRestrictionInfo() {
    }

    /**
     * Constructs a new OrgPositionRestrictionInfo from an
     * OrgPositionRestriction.
     *
     * @param orgPositionRestriction the OrgPositionRestriction to copy
     */
    public OrgPositionRestrictionInfo(OrgPositionRestriction restriction) {
        super(restriction);

        if (restriction != null) {
            this.id = restriction.getId();
            this.orgId = restriction.getOrgId();
            this.orgPersonRelationTypeKey = restriction.getOrgPersonRelationTypeKey();
            this.title = restriction.getTitle();
            if (restriction.getDescr() != null) {
                this.descr = new RichTextInfo(restriction.getDescr());
            }
            if (restriction.getStdDuration() != null) {
                this.stdDuration = new TimeAmountInfo(restriction.getStdDuration());
            }
            this.minNumRelations = restriction.getMinNumRelations();
            this.maxNumRelations = restriction.getMaxNumRelations();
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
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getOrgPersonRelationTypeKey() {
        return orgPersonRelationTypeKey;
    }

    public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey) {
        this.orgPersonRelationTypeKey = orgPersonRelationTypeKey;
    }

    @Override
    public TimeAmountInfo getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    @Override
    public Integer getMinNumRelations() {
        return minNumRelations;
    }

    public void setMinNumRelations(Integer minNumRelations) {
        this.minNumRelations = minNumRelations;
    }

    @Override
    public String getMaxNumRelations() {
        return maxNumRelations;
    }

    public void setMaxNumRelations(String maxNumRelations) {
        this.maxNumRelations = maxNumRelations;
    }
}
