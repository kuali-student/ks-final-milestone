/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information about a single CLU Set.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class CluSetInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private MembershipQueryInfo membershipQuery;
    
    @XmlElement
    private String adminOrg;

    @XmlElement
    private List<String> cluSetIds;

    @XmlElement
    private List<String> cluIds;

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
     * Friendly name of the CLU Set.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Narrative description of the CLU Set.
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * Date and time that this CLU Set became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this CLU Set expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

//    /**
//     * Specifies a search for CLU identifiers. Present for dynamic CLU Sets
//     */
//    public CluCriteriaInfo getCluCriteria() {
//        return cluCriteria;
//    }
//
//    public void setCluCriteria(CluCriteriaInfo cluCriteria) {
//        this.cluCriteria = cluCriteria;
//    }

    /**
     * List of identifiers of directly contained CLU Sets. Present for enumerated CLU Sets.
     */
    public List<String> getCluSetIds() {
        if (cluSetIds == null) {
            cluSetIds = new ArrayList<String>();
        }
        return cluSetIds;
    }

    public void setCluSetIds(List<String> cluSetIds) {
        this.cluSetIds = cluSetIds;
    }

    /**
     * List of identifiers of directly contained CLUs. Present for enumerated CLU Sets.
     */
    public List<String> getCluIds() {
        if (cluIds == null) {
            cluIds = new ArrayList<String>();
        }
        return cluIds;
    }

    public void setCluIds(List<String> cluIds) {
        this.cluIds = cluIds;
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
     * Unique identifier for a CLU Set. This is optional, due to the identifier being set at the time of creation. Once the CLU Set has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public MembershipQueryInfo getMembershipQuery() {
		return membershipQuery;
	}

	public void setMembershipQuery(MembershipQueryInfo membershipQuery) {
		this.membershipQuery = membershipQuery;
	}

	/**
	 * Gets the clu set type. 
	 * Once set at create time, this field may not be updated.
	 * 
	 * @return Clu set type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the clu set type. 
	 * Once set at create time, this field may not be updated.
	 * 
	 * @param type Clu set type
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAdminOrg() {
		return adminOrg;
	}

	public void setAdminOrg(String adminOrg) {
		this.adminOrg = adminOrg;
	}    
}
