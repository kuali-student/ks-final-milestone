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
package org.kuali.student.lum.program.dto;

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
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;

/**
 * Detailed information about a single major discipline program
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:55:53 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/majorDisciplineInfo+Structure">MajorDisciplineInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MajorDisciplineInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String programLevel;

    @XmlElement
    private String intensity;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private List<CluInstructorInfo> publishedInstructors;

    @XmlElement
    private String credentialProgramId;

    @XmlElement
    private List<ProgramVariationInfo> variations;

    @XmlElement
    private String code;

    @XmlElement
    private String cipCode;

    @XmlElement
    private String hegisCode;

    @XmlElement
    private String universityClassification;

    @XmlElement
    private String selectiveEnrollmentCode;

    @XmlElement
    private List<ResultOptionInfo> resultOptions;

    @XmlElement
    private String startTerm;

    @XmlElement
    private String endTerm;

    @XmlElement
    private String lastAdmitTerm;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private String shortTitle;

    @XmlElement
    private String longTitle;

    @XmlElement
    private String transcriptTitle;

    @XmlElement
    private String diplomaTitle;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private RichTextInfo catalogDescr;

    @XmlElement
    private List<LoDisplayInfo> learningObjectives;

    @XmlElement
    private List<CampusLocationInfo> campusLocations;

    @XmlElement
    private List<String> programRequirements;

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
     * A code that indicates whether this is Graduate, Undergraduage etc level major
     */
    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    /**
     * Indicates if the program is full time, part time, both etc
     */
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    /**
     * An URL for additional information about the Major.
     */
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    /**
     * Instructors associated with this Major. This may not be an exhaustive list, and instead may only be used to indicate potential instructors in publication.
     */
    public List<CluInstructorInfo> getPublishedInstructors() {
        if (publishedInstructors == null) {
            publishedInstructors = new ArrayList<CluInstructorInfo>(0);
        }
        return publishedInstructors;
    }

    public void setPublishedInstructors(List<CluInstructorInfo> publishedInstructors) {
        this.publishedInstructors = publishedInstructors;
    }

    /**
     * Identifier of the credential program under which the major belongs
     */
    public String getCredentialProgramId() {
        return credentialProgramId;
    }

    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }

    /**
     * Program variations for the Major
     */
    public List<ProgramVariationInfo> getVariations() {
        if (variations == null) {
            variations = new ArrayList<ProgramVariationInfo>(0);
        }
        return variations;
    }

    public void setVariations(List<ProgramVariationInfo> variations) {
        this.variations = variations;
    }

    /**
     * The composite string that is used to officially reference or publish the Major. Note it may have an internal structure that each Institution may want to enforce. This structure may be composed from the other parts of the structure such as Level amp; Division, but may include items such as cluType.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * CIP Code for the Program
     */
    public String getCipCode() {
        return cipCode;
    }

    public void setCipCode(String cipCode) {
        this.cipCode = cipCode;
    }

    /**
     * HEGIS Code for the Program
     */
    public String getHegisCode() {
        return hegisCode;
    }

    public void setHegisCode(String hegisCode) {
        this.hegisCode = hegisCode;
    }

    /**
     * University specific classification e.g Major(Bacc), Specialization
     */
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    /**
     * Specifies if the Major is Selective Major, Limited Enrollment program or Selective Admissions
     */
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }

    /**
     * Result outcomes from taking the Major. This list is a subset of the outcomes in the associated credential program e.g B.A, B.S
     */
    public List<ResultOptionInfo> getResultOptions() {
        if (resultOptions == null) {
            resultOptions = new ArrayList<ResultOptionInfo>(0);
        }
        return resultOptions;
    }

    public void setResultOptions(List<ResultOptionInfo> resultOptions) {
        this.resultOptions = resultOptions;
    }

    /**
     * The first academic time period that this clu would be effective. This may not reflect the first "real" academic time period for this Major.
     */
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * The last academic time period that this Major would be effective.
     */
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * The last academic time period that this Major would be available for enrollment. This may not reflect the last "real" academic time period for this Major.
     */
    public String getLastAdmitTerm() {
        return lastAdmitTerm;
    }

    public void setLastAdmitTerm(String lastAdmitTerm) {
        this.lastAdmitTerm = lastAdmitTerm;
    }

    /**
     * Date and time the Course became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Abbreviated name of the Major Discipline
     */
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Major Discipline
     */
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    /**
     * Information related to the official identification of the Major discipline, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    /**
     * 
     */
    public String getDiplomaTitle() {
        return diplomaTitle;
    }

    public void setDiplomaTitle(String diplomaTitle) {
        this.diplomaTitle = diplomaTitle;
    }

    /**
     * Narrative description of the Major.
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * Narrative description of the Major that will show up in Catalog
     */
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setCatalogDescr(RichTextInfo catalogDescr) {
        this.catalogDescr = catalogDescr;
    }

    /**
     * Learning Objectives associated with this Major.
     */
    public List<LoDisplayInfo> getLearningObjectives() {
        if (learningObjectives == null) {
            learningObjectives = new ArrayList<LoDisplayInfo>(0);
        }
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    /**
     * Places where this Major might be offered
     */
    public List<CampusLocationInfo> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<CampusLocationInfo>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<CampusLocationInfo> campusLocations) {
        this.campusLocations = campusLocations;
    }

    /**
     * Major Discipline Program Requirements.
     */
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
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
     * The current status of the major program. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for an Major Discipline Program. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}