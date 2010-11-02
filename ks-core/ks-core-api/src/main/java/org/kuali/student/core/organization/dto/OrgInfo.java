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

package org.kuali.student.core.organization.dto;

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
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information about a single organization.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class OrgInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String longName;

    @XmlElement
    private String shortName;

    @XmlElement
    private String sortName;

    @XmlElement
    private String shortDesc;

    @XmlElement
    private String longDesc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private List<OrgCodeInfo> orgCodes;

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
     * Full name of the organization.
     */
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * Shortened format or abbreviation of the organization's name.
     */
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * The name used for sorting, for cases when alphabetical sorting by shortName or longName is undesirable.
     */
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * A brief description of the organization.
     */
    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /**
     * Narrative description of the organization.
     */
    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    /**
     * Date and time that this organization became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this organization expires. This is a similar concept to the expiration date on enumerated values. If specified, this must be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of organization code info structures. These are structures so that many different types of codes can be associated with the org. This allows them to be put into categories.
     */
    public List<OrgCodeInfo> getOrgCodes() {
        if (orgCodes == null) {
            orgCodes = new ArrayList<OrgCodeInfo>();
        }
        return orgCodes;
    }

    public void setOrgCodes(List<OrgCodeInfo> orgCodes) {
        this.orgCodes = orgCodes;
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
     * Unique identifier for an organization type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the organization. The values for this field are constrained to those in the orgState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for an organization. This is optional, due to the identifier being set at the time of creation. Once the organization has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
