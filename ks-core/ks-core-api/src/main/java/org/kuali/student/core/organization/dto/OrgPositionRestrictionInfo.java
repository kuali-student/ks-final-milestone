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
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.TimeAmountInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Information which constrains/describes organization to person relationships of a particular type for an organization. These constraints/descriptions typically involve active relationships.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class OrgPositionRestrictionInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;

    @XmlElement
    private String orgPersonRelationTypeKey;

    @XmlElement
    private String desc;

    @XmlElement
    private String title;

    @XmlElement
    private TimeAmountInfo stdDuration;

    @XmlElement
    private Integer minNumRelations;

    @XmlElement
    private String maxNumRelations;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String id;

    /**
     * Organization the restriction applies to.
     */
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Organization to person relationship type the restriction applies to.
     */
    public String getOrgPersonRelationTypeKey() {
        return orgPersonRelationTypeKey;
    }

    public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey) {
        this.orgPersonRelationTypeKey = orgPersonRelationTypeKey;
    }

    /**
     * Description of the restrictions and use of the relationship type within this particular organization. This should primarily focus on deviations from the standard use of the relationship type.
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Title of organization person relationships of this type. This allows for distinction from the name of the relationship type itself, specific for the given organization.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Describes the standard duration of relationships of this type.
     */
    public TimeAmountInfo getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    /**
     * Acts as a lower bound on the number of relationships of this type expected for the organization. If specified, this should be less than or equal to the value of maxNumRelations
     */
    public Integer getMinNumRelations() {
        return minNumRelations;
    }

    public void setMinNumRelations(Integer minNumRelations) {
        this.minNumRelations = minNumRelations;
    }

    /**
     * Acts as an upper bound on the number of relationships of this type expected for the organization. The values of this field are restricted to integer values and the string "unbounded". If specified, this should be greater than or equal to the value of minNumRelations, with the value "unbounded" being automatically assumed to be greater.
     */
    public String getMaxNumRelations() {
        return maxNumRelations;
    }

    public void setMaxNumRelations(String maxNumRelations) {
        this.maxNumRelations = maxNumRelations;
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
     * Unique identifier for the organization position restriction record. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. Once set by the service, this should be seen as read-only and immutable. This structure is not retrievable by this identifier to limit the number of active organization position restriction records visible through the service. It is strongly recommended that this identifier not be referenced by outside consumers.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
