/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.statement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;

/**
 *Information about a requirement component.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqComponentInfo implements Serializable, Idable, HasTypeState {

	private static final long serialVersionUID = 1L;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement
    private List<ReqCompFieldInfo> reqCompFields;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    @XmlElement
    private ReqComponentTypeInfo requiredComponentType;

    /**
     * <code>naturalLanguageTranslation</code> attribute is a read-only 
     * attribute which generated on-the-fly and should not be persisted.
     */
    @XmlAttribute
    private String naturalLanguageTranslation;
    
	/**
     * Narrative description of the requirement component.
     */
    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    /**
     * Detailed information about a requirement component field value.
     */
    public List<ReqCompFieldInfo> getReqCompFields() {
        if(null == reqCompFields) {
            reqCompFields = new ArrayList<ReqCompFieldInfo>();
        }
        return reqCompFields;
    }

    public void setReqCompFields(List<ReqCompFieldInfo> reqCompFields) {
        this.reqCompFields = reqCompFields;
    }

    /**
     * Date and time that this requirement component became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this requirement component expires. This is a similar concept to the expiration date on enumerated values. If specified, this must be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
     * Unique identifier for a requirement component type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReqComponentTypeInfo getRequiredComponentType() {
        return requiredComponentType;
    }

    public void setRequiredComponentType(ReqComponentTypeInfo requiredComponentType) {
        this.requiredComponentType = requiredComponentType;
        if(requiredComponentType != null) {
            setType(requiredComponentType.getId());
        }
    }
    
	/**
     * The current status of the requirement component. The values for this field are constrained to those in the reqComponentState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a requirement component. This is optional, due to the identifier being set at the time of creation. Once the requirement component has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getNaturalLanguageTranslation() {
		return naturalLanguageTranslation;
	}

	public void setNaturalLanguageTranslation(String naturalLanguageTranslation) {
		this.naturalLanguageTranslation = naturalLanguageTranslation;
	}

    @Override
	public String toString() {
		return "ReqComponentInfo[id=" + id + ", type=" + type + ", state="
				+ state + "]";
	}
}
