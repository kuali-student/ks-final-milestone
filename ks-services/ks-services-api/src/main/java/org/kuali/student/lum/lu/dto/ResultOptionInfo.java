/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;


/**
 * Information about a result option.
 *
 * @Author Kamal
 * @Since Mon Jan 11 15:21:31 PST 2010
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ResultOptionInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement
    private String resultUsageTypeKey;

    @XmlElement
    private String resultComponentId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Narrative description of the result option.
     */

    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }


    /**
     * Unique identifier for a result usage type.
     */

    public String getResultUsageTypeKey() {
        return resultUsageTypeKey;
    }

    public void setResultUsageTypeKey(String resultUsageTypeKey) {
        this.resultUsageTypeKey = resultUsageTypeKey;
    }


    /**
     * Unique identifier for a result component.
     */

    public String getResultComponentId() {
        return resultComponentId;
    }

    public void setResultComponentId(String resultComponentId) {
        this.resultComponentId = resultComponentId;
    }


    /**
     * Date and time that this result option became effective. This is
     * a similar concept to the effective date on enumerated
     * values. When an expiration date has been specified, this field
     * must be less than or equal to the expiration date.
     */

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Date and time that this result option expires. This is a
     * similar concept to the expiration date on enumerated values. If
     * specified, this must be greater than or equal to the effective
     * date. If this field is not specified, then no expiration date
     * has been currently defined and should automatically be
     * considered greater than the effective date.
     */

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
     * The current status of the result option. The values for this
     * field are constrained to those in the resultOptionState
     * enumeration. A separate setup operation does not exist for
     * retrieval of the meta data around this value.
     */

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    /**
     * Unique identifier for a result option. This is optional, due to
     * the identifier being set at the time of creation. Once the
     * result option has been created, this should be seen as
     * required.
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}