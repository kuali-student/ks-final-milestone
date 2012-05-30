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

package org.kuali.student.r1.lum.lu.dto;

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
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.HasTypeState;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

/**
 *Detailed information about a single CLU.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CluInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private CluIdentifierInfo officialIdentifier;

    @XmlElement
    private List<CluIdentifierInfo> alternateIdentifiers;

    @XmlElement
    private String studySubjectArea;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private List<String> campusLocations;

    @XmlElement
    private List<AccreditationInfo> accreditations;

    @XmlElement
    private List<AdminOrgInfo> adminOrgs;

    @XmlElement
    private CluInstructorInfo primaryInstructor;

    @XmlElement
    private List<CluInstructorInfo> instructors;

    @XmlElement
    private String expectedFirstAtp;

    @XmlElement
    private String lastAtp;

    @XmlElement
    private String lastAdmitAtp;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private AmountInfo intensity;

    @XmlElement
    private TimeAmountInfo stdDuration;

    @XmlElement
    private boolean canCreateLui;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private List<LuCodeInfo> luCodes;

    @XmlElement
    private String nextReviewPeriod;

    @XmlElement
    private boolean isEnrollable;

    @XmlElement
    private List<String> offeredAtpTypes;

    @XmlElement
    private boolean hasEarlyDropDeadline;

    @XmlElement
    private int defaultEnrollmentEstimate;

    @XmlElement
    private int defaultMaximumEnrollment;

    @XmlElement
    private boolean isHazardousForDisabledStudents;

    @XmlElement
    private CluFeeInfo feeInfo;

    @XmlElement
    private CluAccountingInfo accountingInfo;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlElement
    private VersionInfo versionInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;


    /**
     * Information related to the official identification of the clu, typically in human readable form. Used to officially reference or publish.
     */
    public CluIdentifierInfo getOfficialIdentifier() {
        return officialIdentifier;
    }

    public void setOfficialIdentifier(CluIdentifierInfo officialIdentifier) {
        this.officialIdentifier = officialIdentifier;
    }

    /**
     * Information related to alternate identifications of the clu, typically in human readable form. Used to reference or publish.
     */
    public List<CluIdentifierInfo> getAlternateIdentifiers() {
        if (alternateIdentifiers == null) {
            alternateIdentifiers = new ArrayList<CluIdentifierInfo>();
        }
        return alternateIdentifiers;
    }

    public void setAlternateIdentifiers(List<CluIdentifierInfo> alternateIdentifiers) {
        this.alternateIdentifiers = alternateIdentifiers;
    }


    /**
     * The Study Subject Area is used to identify the area of study associated with the clu. It may be a general study area (e.g. Chemistry) or very specific (e.g. Naval Architecture) depending on the level of specificity of the clu.
     */
    public String getStudySubjectArea() {
        return studySubjectArea;
    }

    public void setStudySubjectArea(String studySubjectArea) {
        this.studySubjectArea = studySubjectArea;
    }

    /**
     * Narrative description of the CLU, used for the catalog.
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /*
     * Information around the accreditation of the clu.
     */
    public List<AccreditationInfo> getAccreditations() {
        if (accreditations == null) {
            accreditations = new ArrayList<AccreditationInfo>();
        }
        return accreditations;
    }

    public void setAccreditations(List<AccreditationInfo> accreditations) {
        this.accreditations = accreditations;
    }

    /*
     * Places where this clu might be offered
     */
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>();
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    public List<AdminOrgInfo> getAdminOrgs() {
        if (adminOrgs == null) {
        	adminOrgs = new ArrayList<AdminOrgInfo>();
        }
        return adminOrgs;
    }

    public void setAdminOrgs(List<AdminOrgInfo> adminOrgs) {
        this.adminOrgs = adminOrgs;
    }

    /**
     * Primary potential instructor for the clu. This is primarily for use in advertising the clu and may not be the actual instructor.
     */
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(CluInstructorInfo primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    /**
     * Instructors associated with this clu. This may not be an exhaustive list, and instead may only be used to indicate potential instructors in publication.
     */
    public List<CluInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructorInfo>();
        }
        return instructors;
    }

    public void setInstructors(List<CluInstructorInfo> instructors) {
        this.instructors = instructors;
    }

    /**
     * Date and time the CLU became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this CLU expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /*
     * The expected level of time commitment between the student and the CLU meetings.
     */
    public AmountInfo getIntensity() {
        return intensity;
    }

    public void setIntensity(AmountInfo intensity) {
        this.intensity = intensity;
    }

    /**
     * The standard duration of the learning unit.
     */
    public TimeAmountInfo getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    /**
     * Indicates if the CLU can be used to instantiate LUIs (offerings).
     */
    public boolean isCanCreateLui() {
        return canCreateLui;
    }

    public void setCanCreateLui(boolean canCreateLui) {
        this.canCreateLui = canCreateLui;
    }

    /**
     * An URL for additional information about the CLU. This could be a reference to a document which might in turn have references to other documents (e.g. course syllabus provided by the faculty or department, standard schedule of classes, etc.).
     */
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    /**
     * List of LU code info structures. These are structures so that many different types of codes can be associated with the clu. This allows them to be put into categories.
     */
    public List<LuCodeInfo> getLuCodes() {
        if (luCodes == null) {
            luCodes = new ArrayList<LuCodeInfo>();
        }
        return luCodes;
    }

    public void setLuCodes(List<LuCodeInfo> luCodes) {
        this.luCodes = luCodes;
    }

    /**
     * When the next review should be
     */
    public String getNextReviewPeriod() {
        return nextReviewPeriod;
    }

    public void setNextReviewPeriod(String nextReviewPeriod) {
        this.nextReviewPeriod = nextReviewPeriod;
    }

    /**
     * Indicates if Luis generated from this Clu are intended to be enrolled in by Students directly
     */
    public boolean isEnrollable() {
        return isEnrollable;
    }

    public void setEnrollable(boolean isEnrollable) {
        this.isEnrollable = isEnrollable;
    }

    /**
     * The academic time period types in which this CLU is typically offered. Standard usage would equate to terms.
     */
    public List<String> getOfferedAtpTypes() {
        if (offeredAtpTypes == null) {
            offeredAtpTypes = new ArrayList<String>();
        }
        return offeredAtpTypes;
    }

    public void setOfferedAtpTypes(List<String> offeredAtpTypes) {
        this.offeredAtpTypes = offeredAtpTypes;
    }

    /**
     * Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses are designated as such to maximize access to courses that have historically experienced high demand and high attrition. Default is "false".
     */
    public boolean isHasEarlyDropDeadline() {
        return hasEarlyDropDeadline;
    }

    public void setHasEarlyDropDeadline(boolean hasEarlyDropDeadline) {
        this.hasEarlyDropDeadline = hasEarlyDropDeadline;
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
     * Default maximum enrollment for this CLU.
     */
    public int getDefaultMaximumEnrollment() {
        return defaultMaximumEnrollment;
    }

    public void setDefaultMaximumEnrollment(int defaultMaximumEnrollment) {
        this.defaultMaximumEnrollment = defaultMaximumEnrollment;
    }

    /**
     * Indicates if the CLU may be hazardous for students with disabilities. Would default to "false".
     */
    public boolean isHazardousForDisabledStudents() {
        return isHazardousForDisabledStudents;
    }

    public void setHazardousForDisabledStudents(boolean isHazardousForDisabledStudents) {
        this.isHazardousForDisabledStudents = isHazardousForDisabledStudents;
    }

    /**
     * Fee information associated with this CLU.
     */
    public CluFeeInfo getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(CluFeeInfo feeInfo) {
        this.feeInfo = feeInfo;
    }

    /**
     * Accounting information associated with this CLU.
     */
    public CluAccountingInfo getAccountingInfo() {
        return accountingInfo;
    }

    public void setAccountingInfo(CluAccountingInfo accountingInfo) {
        this.accountingInfo = accountingInfo;
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


    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }

    /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the clu. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value. This field may not be updated through updating this structure and must instead be updated through a dedicated operation.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a Canonical Learning Unit (CLU). This is optional, due to the identifier being set at the time of creation. Once the CLU has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getExpectedFirstAtp() {
		return expectedFirstAtp;
	}

	public void setExpectedFirstAtp(String expectedFirstAtp) {
		this.expectedFirstAtp = expectedFirstAtp;
	}

    public String getLastAtp() {
        return lastAtp;
    }

    public void setLastAtp(String lastAtp) {
        this.lastAtp = lastAtp;
    }

    public String getLastAdmitAtp() {
        return lastAdmitAtp;
    }

    public void setLastAdmitAtp(String lastAdmitAtp) {
        this.lastAdmitAtp = lastAdmitAtp;
    }

    @Override
    public String toString() {
    	return "CluInfo[id=" + id + ", type=" + type + "]";
    }

}
