/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.MetaInfo;

/**
 * Detailed information about publishing a clu.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:21:19 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/cluPublicationInfo+Structure+v1.0-rc3">CluPublicationInfo v1.0-rc3</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CluPublicationInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String cluId;

    @XmlElement
    private List<FieldInfo> variants;

    @XmlElement
    private String startCycle;

    @XmlElement
    private String endCycle;

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
     * The identifier for the canonical learning unit which is described by this publication information.
     */
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    /**
     * Fields in cluInfo whose values are overridden as part of this publication.
     */
    public List<FieldInfo> getVariants() {
        if (variants == null) {
            variants = new ArrayList<FieldInfo>(0);
        }
        return variants;
    }

    public void setVariants(List<FieldInfo> variants) {
        this.variants = variants;
    }

    /**
     * The start academic time period for when the CLU should be published in this type of usage. Should be less than or equal to endCycle.
     */
    public String getStartCycle() {
        return startCycle;
    }

    public void setStartCycle(String startCycle) {
        this.startCycle = startCycle;
    }

    /**
     * The end academic time period for when the CLU should be published in this type of usage. If specified, should be greater than or equal to startCycle.
     */
    public String getEndCycle() {
        return endCycle;
    }

    public void setEndCycle(String endCycle) {
        this.endCycle = endCycle;
    }

    /**
     * Date and time that this LU publication type became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this LU publication type expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
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

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Type of publication for which this information should be used.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Current state of the information for this publication type. This value should be constrained to those within the cluPublishingState enumeration. In general, an "active" record for a type indicates that the clu should be published within that media, though that may be further constrained by the cycle information included.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Identifier for the publishing information. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. This structure is not currently accessible through unique operations, and it is strongly recommended that no external references to this particular identifier be maintained.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}