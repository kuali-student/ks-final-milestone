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
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

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
    private List<String> academicSubjectOrgs;

    @XmlElement
    private String studySubjectArea;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement
    private RichTextInfo marketingDesc;

    // Deprecated in v  1.0-rc2
    @XmlElement
    private String accreditingOrg;

    @XmlElement
    private List<AccreditationInfo> accreditationList;

    // Deprecated in v  1.0-rc2
    @XmlElement
    private String adminOrg;

    // Deprecated in v  1.0-rc2
    @XmlElement
    private List<String> participatingOrgs;

    @XmlElement
    private List<String> campusLocationList;
    
    @XmlElement
    private AdminOrgInfo primaryAdminOrg;

    @XmlElement
    private List<AdminOrgInfo> alternateAdminOrgs;
    
    @XmlElement
    private CluInstructorInfo primaryInstructor;

    @XmlElement
    private List<CluInstructorInfo> instructors;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private TimeAmountInfo intensity;

    @XmlElement
    private TimeAmountInfo stdDuration;

    @XmlElement
    private boolean canCreateLui;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private List<LuCodeInfo> luCodes;

    // Deprecated in v  1.0-rc2
    @XmlElement
    private CluCreditInfo creditInfo;

    @XmlElement
    private CluPublishingInfo publishingInfo;

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

    /*
     * The organizations that represents the Subject area of the Clu, if different from the PrimaryAdminOrg
     */
    public List<String> getAcademicSubjectOrgs() {
        if (academicSubjectOrgs == null) {
            academicSubjectOrgs = new ArrayList<String>();
        }
        return academicSubjectOrgs;
    }

    public void setAcademicSubjectOrgs(List<String> academicSubjectOrgs) {
        this.academicSubjectOrgs = academicSubjectOrgs;
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
    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    /**
     * A publishable description of the LU to be used for advertising or marketing purposes
     */
    public RichTextInfo getMarketingDesc() {
        return marketingDesc;
    }

    public void setMarketingDesc(RichTextInfo marketingDesc) {
        this.marketingDesc = marketingDesc;
    }

    /**
     * The organization responsible for accrediting the program.
     * 
     * Replaced by accreditationList
     * 
     * @deprecated
     */
    public String getAccreditingOrg() {
        return accreditingOrg;
    }

    /**
     *
     * Replaced by accreditationList
     * 
     * @deprecated
     */
    public void setAccreditingOrg(String accreditingOrg) {
        this.accreditingOrg = accreditingOrg;
    }

    /*
     * Information around the accreditation of the clu.
     */
    public List<AccreditationInfo> getAccreditationList() {
        if (accreditationList == null) {
            accreditationList = new ArrayList<AccreditationInfo>();
        }
        return accreditationList;
    }

    public void setAccreditationList(List<AccreditationInfo> accreditation) {
        this.accreditationList = accreditation;
    }

    /*
     * Places where this clu might be offered
     */
    public List<String> getCampusLocationList() {
        if (campusLocationList == null) {
            campusLocationList = new ArrayList<String>();
        }
        return campusLocationList;
    }

    public void setCampusLocationList(List<String> campusLocationList) {
        this.campusLocationList = campusLocationList;
    }

    
    public AdminOrgInfo getPrimaryAdminOrg() {
        return primaryAdminOrg;
    }

    public void setPrimaryAdminOrg(AdminOrgInfo primaryAdminOrg) {
        this.primaryAdminOrg = primaryAdminOrg;
    }

    public List<AdminOrgInfo> getAlternateAdminOrgs() {
        if (alternateAdminOrgs == null) {
            alternateAdminOrgs = new ArrayList<AdminOrgInfo>();
        }
        return alternateAdminOrgs;
    }

    public void setAlternateAdminOrgs(List<AdminOrgInfo> alternateAdminOrgs) {
        this.alternateAdminOrgs = alternateAdminOrgs;
    }

    /**
     * The primary organization (typically, an academic department) with administrative oversight over the CLU.
     * 
     * @deprecated
     */
    public String getAdminOrg() {
        return adminOrg;
    }

    /**
     * @deprecated
     */
    public void setAdminOrg(String adminOrg) {
        this.adminOrg = adminOrg;
    }

    /**
     * For cross-listed CLUs, contains participating organizations (typically, academic departments).
     * @deprecated
     */
    public List<String> getParticipatingOrgs() {
        if (participatingOrgs == null) {
            participatingOrgs = new ArrayList<String>();
        }
        return participatingOrgs;
    }

    /**
     * For cross-listed CLUs, contains participating organizations (typically, academic departments).
     * @deprecated
     */
    public void setParticipatingOrgs(List<String> participatingOrgs) {
        this.participatingOrgs = participatingOrgs;
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
    public TimeAmountInfo getIntensity() {
        return intensity;
    }

    public void setIntensity(TimeAmountInfo intensity) {
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
     * Information about credit
     * 
     * @deprecated
     */
    public CluCreditInfo getCreditInfo() {
        return creditInfo;
    }

    /**
     * 
     * This method ...
     * 
     * @deprecated creditInfo
     * 
     */public void setCreditInfo(CluCreditInfo creditInfo) {
        this.creditInfo = creditInfo;
    }

    /**
     * Publishing information associated with this CLU.
     */
    public CluPublishingInfo getPublishingInfo() {
        return publishingInfo;
    }

    public void setPublishingInfo(CluPublishingInfo publishingInfo) {
        this.publishingInfo = publishingInfo;
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
}
