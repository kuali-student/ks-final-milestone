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
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate, Master, Doctoral, Graduate Certificate, Undergraduate Certificate
.
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:55:47 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/credentialProgramInfo+Structure">CredentialProgramInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialProgramInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String shortTitle;

    @XmlElement
    private String longTitle;
    
    @XmlElement
    private String transcriptTitle;

    @XmlElement 
    private String programLevel;
    
    @XmlElement
    private String code;
    
    @XmlElement
    private String universityClassification;

    @XmlElement
    private AdminOrgInfo institution;
    
    @XmlElement
    private List<String> resultOptions;
    
    @XmlElement
    private String startTerm;

    @XmlElement
    private String endTerm;

    @XmlElement
    private String endProgramEntryTerm;

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
    private List<LoDisplayInfo> learningObjectives;
    
    @XmlElement
    private List<String> coreProgramIds;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlElement
    private VersionInfo versionInfo;

    @XmlAttribute
    private String credentialProgramType;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;
    
    public List<String> getCoreProgramIds() {
        return coreProgramIds;
    }

    public void setCoreProgramIds(List<String> coreProgramIds) {
        this.coreProgramIds = coreProgramIds;
    }

    /**
     * Credential Program Requirements.
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
    @Override
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    @Override
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
    public String getCredentialProgramType() {
        return credentialProgramType;
    }

    public void setCredentialProgramType(String credentialProgramType) {
        this.credentialProgramType = credentialProgramType;
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
     * Unique identifier for an Credential Program. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Abbreviated name of the Credential program   
     */
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Credential Program  
     */
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    /*
     * Information related to the official identification of the credential program, typically in human readable form. Used to officially reference or publish.  
     */
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }
    
    /**
     * A code that indicates whether this is Graduate, Undergraduage etc    
     */
    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    /**
     * The composite string that is used to officially reference or publish the Credential program. 
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * University specific classification   
     */
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    /**
     * Institution owning the program.  
     */
    public AdminOrgInfo getInstitution() {
        return institution;
    }

    public void setInstitution(AdminOrgInfo institution) {
        this.institution = institution;
    }

    /**
     * Result outcomes from taking the Credential program.  
     */
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    /**
     * The first academic time period that this credential program would be effective. This may not reflect the first "real" academic time period for this program. 
     */
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }
    
    /**
     * The last academic time period that this credential program would be effective.   
     */
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }
    
    /**
     * The last academic time period that this credential program would be available for enrollment. This may not reflect the last "real" academic time period for this program.    
     */
    public String getEndProgramEntryTerm() {
        return endProgramEntryTerm;
    }

    public void setEndProgramEntryTerm(String endProgramEntryTerm) {
        this.endProgramEntryTerm = endProgramEntryTerm;
    }

    /**
     * Divisions responsible to make changes to the credential program  
     */
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwner) {
        this.divisionsContentOwner = divisionsContentOwner;
    }

    /**
     * Divisions responsible for student exceptions to the credential program.  
     */
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) {
        this.divisionsStudentOversight = divisionsStudentOversight;
    }

    /*
     * Unit responsible to make changes to the credential program   
     */
    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    /**
     * Unit responsible for student exceptions to the credential program.   
     */
    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
    }

    public void setUnitsStudentOversight(List<String> unitsStudentOversight) {
        this.unitsStudentOversight = unitsStudentOversight;
    }

    /**
     * Narrative description of the Credential program. 
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * Learning Objectives associated with this credential program. 
     */
    public List<LoDisplayInfo> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }    
}