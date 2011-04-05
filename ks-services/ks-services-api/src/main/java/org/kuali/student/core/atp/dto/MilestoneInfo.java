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

package org.kuali.student.core.atp.dto;

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
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Information about a milestone.
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
public class MilestoneInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement(name="atpKey")
    private String atpId;

    @XmlElement
    private boolean isDateRange;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute(name="key")
    private String id;

    /**
     * Name of the milestone.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description of the milestone.
     */
    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    /**
     * Unique identifier for an Academic Time Period (ATP).
     */
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    /**
     * Tests if this milestone has a date range. If true, the end date
     * value follows the start date.
     */

    public boolean getIsDateRange() {
        return isDateRange;
    }

    public void setStartDate(boolean isDateRange) {
        this.isDateRange = isDateRange;
    }

    /**
     * Start Date and time of the milestone.
     */
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * End Date and time of the milestone.
     */
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String,String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String,String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String,String> attributes) {
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
     * Unique identifier for a milestone type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the milestone. The values for this field are constrained to those in the milestoneState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a milestone.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
