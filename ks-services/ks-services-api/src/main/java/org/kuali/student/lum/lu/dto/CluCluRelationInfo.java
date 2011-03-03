/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Detailed information about a CLU to CLU relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCluRelationInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private String relatedCluId;

    @XmlElement
    private Boolean isCluRelationRequired;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;


    /**
     * Unique identifier for a Canonical Learning Unit (CLU).
     */

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }


    /**
     * Unique identifier for a Canonical Learning Unit (CLU).
     */

    public String getRelatedCluId() {
        return relatedCluId;
    }

    public void setRelatedCluId(String relatedCluId) {
        this.relatedCluId = relatedCluId;
    }


    /**
     * Indicates if the relation is required upon instantiation of a
     * LUI.  Default is "true".
     */

    public Boolean getIsCluRelationRequired() {
        return isCluRelationRequired;
    }

    public void setIsCluRelationRequired(Boolean isCluRelationRequired) {
        this.isCluRelationRequired = isCluRelationRequired;
    }


    /**
     * Date and time that this CLU to CLU relationship became
     * effective. This is a similar concept to the effective date on
     * enumerated values. When an expiration date has been specified,
     * this field must be less than or equal to the expiration date.
     */

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Date and time that this CLU to CLU relationship expires. This
     * is a similar concept to the expiration date on enumerated
     * values. If specified, this should be greater than or equal to
     * the effective date.  If this field is not specified, then no
     * expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */

    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }

        return attributes;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }


    /**
     * Create and last update info for the structure. This is optional
     * and treated as read only since the data is set by the internals
     * of the service during maintenance operations.
     */

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }


    /**
     * Unique identifier for the LU to LU relation type.
     */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * Identifier for the current status of a CLU to CLU
     * relationship. The values for this field are constrained to
     * those in the luLuRelationState enumeration. A separate setup
     * operation does not exist for retrieval of the meta data around
     * this value.
     */

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    /**
     * Unique identifier for a CLU to CLU relationship. This is
     *  optional, due to the identifier being set at the time of
     *  creation. Once the relation has been created, this should be
     *  seen as required.
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
    	return "CluCluRelationInfo[id=" + id + ", cluId=" + cluId + ", relatedCluId=" + relatedCluId + ", type=" + type + ", cluRelationRequired=" + isCluRelationRequired + "]";
    }
}
