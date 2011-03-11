/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lui.dto;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Detailed information about a LUI to LUI relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiLuiRelationInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String luiId;

    @XmlElement
    private String relatedLuiId;

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
     * Unique identifier for a Learning Unit Instance (LUI).
     *
     * @return a LUI identifier
     */

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }


    /**
     * Unique identifier for a Learning Unit Instance (LUI).
     *
     * @return a LUI identifier
     */

    public String getRelatedLuiId() {
        return relatedLuiId;
    }

    public void setRelatedLuiId(String relatedLuiId) {
        this.relatedLuiId = relatedLuiId;
    }


    /**
     * Date and time that this LUI to LUI relationship type became
     * effective. This is a similar concept to the effective date on
     * enumerated values. When an expiration date has been specified,
     * this field must be less than or equal to the expiration date.
     *
     * @return the effective date
     */

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Date and time that this LUI to LUI relationship type
     * expires. This is a similar concept to the expiration date on
     * enumerated values. If specified, this should be greater than or
     * equal to the effective date. If this field is not specified,
     * then no expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     *
     * @return the expiration date
     */

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * List of key/value pairs, typically used for dynamic attributes.
     *
     * @return an attribute map
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
     * Create and last update info for the structure. This is optional
     * and treated as read only since the data is set by the internals
     * of the service during maintenance operations.
     *
     * @return metainfo
     */

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }


    /**
     * Unique identifier for the LU to LU relation type.
     *
     * @return the relation type key
     */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the LUI to LUI relationship. The values
     * for this field are constrained to those in the
     * luLuRelationState enumeration. A separate setup operation does
     * not exist for retrieval of the meta data around this value.
     *
     * @return the state
     */

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    /**
     * Unique identifier for a LUI to LUI relation. This is optional,
     * due to the identifier being set at the time of creation. Once
     * the relation has been created, this should be seen as required.
     *
     * @return the identifier for this relationship
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
