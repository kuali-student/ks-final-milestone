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
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.LoDisplayInfo;

/**
 * Detailed information about a core program requirements associated with Credential Programs
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:55:48 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/coreProgramInfo+Structure">CoreProgramInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CoreProgramInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String shortTitle;

    @XmlElement
    private String longTitle;
    
    @XmlElement
    private String transcriptTitle;

    @XmlElement
    private String code;
    
    @XmlElement
    private String universityClassification;

    @XmlElement
    private String startTerm;

    @XmlElement
    private String endTerm;

    @XmlElement
    private String endProgramEntryTerm;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    private List<String> divisionsContentOwner;
    
    @XmlElement
    private List<String> divisionsStudentOversight;

    @XmlElement
    private List<String> unitsContentOwner;
    
    @XmlElement
    private List<String> unitsStudentOversight;

    @XmlElement
    private RichTextInfo descr;
    
    @XmlElement
    private String referenceURL;
    
    @XmlElement
    private RichTextInfo catalogDescr;

    @XmlElement
    private List<String> catalogPublicationTargets;
    
    @XmlElement
    private List<LoDisplayInfo> learningObjectives;
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlElement
    private MetaInfo versionInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Core Program Requirements.
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

        
    public MetaInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(MetaInfo versionInfo) {
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
     * The current status of the credential program. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for an Core Program Requirement. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Abbreviated name of the Core requirement 
     */
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Core Requirement    
     */
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    /**
     * Information related to the official identification of the Core requirement, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle() {
        return transcriptTitle;
    }
    
    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    /**
     * The composite string that is used to officially reference or publish the Core Program.   
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * University specific classification e.g General Education Program 
     */
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    /**
     * The first academic time period that this clu would be effective. This may not reflect the first "real" academic time period for this Core.   
     */
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * The last academic time period that this Core would be effective. 
     */
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * The last academic time period that this Core would be available for enrollment. This may not reflect the last "real" academic time period for this requirement.  
     */
    public String getEndProgramEntryTerm() {
        return endProgramEntryTerm;
    }

    public void setEndProgramEntryTerm(String endProgramEntryTerm) {
        this.endProgramEntryTerm = endProgramEntryTerm;
    }

    /**
     * Divisions responsible to make changes to the CORE requirements   
     */
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwner) {
        this.divisionsContentOwner = divisionsContentOwner;
    }

    /**
     * Divisions responsible for student exceptions to the requirements.    
     */
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) {
        this.divisionsStudentOversight = divisionsStudentOversight;
    }
    
    /**
     * Unit responsible to make changes to the CORE requirements    
     */
    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    /**
     * Unit responsible for student exceptions to the requirements. 
     */
    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
    }

    public void setUnitsStudentOversight(List<String> unitsStudentOversight) {
        this.unitsStudentOversight = unitsStudentOversight;
    }

    /**
     * Narrative description of the Core requirement.   
     */
    public RichTextInfo getDescr() {
        return descr;
    }
    
    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * Narrative description of the Core that will show up in Catalog
     */
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setCatalogDescr(RichTextInfo catalogDescr) {
        this.catalogDescr = catalogDescr;
    }

    /**
     * List of catalog targets where information will be published.   
     */
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }

    /**
     * An URL for additional information about the Core Requirement.    
     */
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    /**
     * Learning Objectives associated with this Core requirement.   
     */
    public List<LoDisplayInfo> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }
}