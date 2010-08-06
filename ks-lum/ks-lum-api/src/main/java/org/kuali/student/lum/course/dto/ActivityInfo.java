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
package org.kuali.student.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;

/**
 * Detailed information about a single course activity.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:30:56 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/activityInfo+Structure">ActivityInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private TimeAmountInfo duration;

    @XmlElement
    private List<AcademicSubjectOrgInfo> academicSubjectOrgs;

    @XmlElement
    private int defaultEnrollmentEstimate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String activityType;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private AmountInfo contactHours;

    /**
     * The standard duration of the Course.
     */
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    /**
     * The organizations that represents the Subject area of the course.
     */
    public List<AcademicSubjectOrgInfo> getAcademicSubjectOrgs() {
        if (academicSubjectOrgs == null) {
            academicSubjectOrgs = new ArrayList<AcademicSubjectOrgInfo>(0);
        }
        return academicSubjectOrgs;
    }

    public void setAcademicSubjectOrgs(List<AcademicSubjectOrgInfo> academicSubjectOrgs) {
        this.academicSubjectOrgs = academicSubjectOrgs;
    }

    /**
     * Default enrollment estimate for this CLU.
     */
    public int getDefaultEnrollmentEstimate() {
        return defaultEnrollmentEstimate;
    }

    public void setDefaultEnrollmentEstimate(int defaultEnrollmentEstimate) {
        this.defaultEnrollmentEstimate = defaultEnrollmentEstimate;
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
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /**
     * The current status of the course. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value. This field may not be updated through updating this structure and must instead be updated through a dedicated operation.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for an Activity. This is optional, due to the identifier being set at the time of creation. Once the Course has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public void setContactHours(AmountInfo contactHours) {
		this.contactHours = contactHours;
	}
    /**
     * ContactHours for an Activity.
     */
	public AmountInfo getContactHours() {
		return contactHours;
	}
}