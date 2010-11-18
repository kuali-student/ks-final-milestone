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

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramCommonAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramFullOrgAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramRequirementAssembly;

/**
 * Detailed information about major program variations
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:55:59 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/programVariationInfo+Structure">ProgramVariationInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramVariationInfo implements Serializable, Idable, HasTypeState, HasAttributes , ProgramCommonAssembly,
        ProgramIdentifierAssembly, ProgramFullOrgAssembly, ProgramAtpAssembly,
        ProgramCodeAssembly, ProgramPublicationAssembly, ProgramRequirementAssembly {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String intensity;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private String code;

    @XmlElement
    private String cip2000Code;

    @XmlElement
    private String cip2010Code;
        
    @XmlElement
    private String hegisCode;

    @XmlElement
    private String universityClassification;

    @XmlElement
    private String selectiveEnrollmentCode;

    @XmlElement
    private List<String> resultOptions;

    @XmlElement
    private String startTerm;

    @XmlElement
    private String endTerm;

    @XmlElement
    private String endProgramEntryTerm;

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
    private List<String> catalogPublicationTargets;
    
    @XmlElement
    private List<LoDisplayInfo> learningObjectives;

    @XmlElement
    private List<String> campusLocations;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    private List<String> divisionsContentOwner;
    
    @XmlElement
    private List<String> divisionsStudentOversight;

    @XmlElement
    private List<String> divisionsDeployment;

    @XmlElement
    private List<String> divisionsFinancialResources;

    @XmlElement
    private List<String> divisionsFinancialControl;

    @XmlElement
    private List<String> unitsContentOwner;
    
    @XmlElement
    private List<String> unitsStudentOversight;

    @XmlElement
    private List<String> unitsDeployment;

    @XmlElement
    private List<String> unitsFinancialResources;

    @XmlElement
    private List<String> unitsFinancialControl;

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
     * Indicates if the program is full time, part time, both etc
     */
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    /**
     * An URL for additional information about the Variation.
     */
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    /**
     * The composite string that is used to officially reference or publish the Variation. Note it may have an internal structure that each Institution may want to enforce.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * CIP 2000 Code for the Program
     */
    public String getCip2000Code() {
        return cip2000Code;
    }

    public void setCip2000Code(String cip2000Code) {
        this.cip2000Code = cip2000Code;
    }

    /**
     * CIP 2010 Code for the Program
     */
    public String getCip2010Code() {
        return cip2010Code;
    }

    public void setCip2010Code(String cip2010Code) {
        this.cip2010Code = cip2010Code;
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
     * Specifies if the Variation is Limited Enrollment program or Selective Admissions
     */
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }

    
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    /**
     * The first academic time period that this Variation would be effective. This may not reflect the first "real" academic time period for this Variation.
     */
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * The last academic time period that this Variation would be effective.
     */
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * The last academic time period that this Variation would be available for enrollment. This may not reflect the last "real" academic time period for this Variation.   
     */
    public String getEndProgramEntryTerm() {
        return endProgramEntryTerm;
    }

    public void setEndProgramEntryTerm(String endProgramEntryTerm) {
        this.endProgramEntryTerm = endProgramEntryTerm;
    }

    /**
     * Date and time the Variation became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Abbreviated name of the Variation
     */
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Variation Discipline
     */
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    /**
     * Information related to the official identification of the Variation, typically in human readable form. Used to officially reference or publish.
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
     * Narrative description of the Variation.
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    /**
     * Narrative description of the Variation that will show up in Catalog
     */
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setCatalogDescr(RichTextInfo catalogDescr) {
        this.catalogDescr = catalogDescr;
    }
    
    /**
     * List of catalog targets where program variation information will be published.   
     */
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }
    
    /**
     * Learning Objectives associated with this Variation.
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
     * Places where this Variation might be offered
     */
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    /**
     * Program Variation Requirements.
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
    
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwner) {
        this.divisionsContentOwner = divisionsContentOwner;
    }

    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) {
        this.divisionsStudentOversight = divisionsStudentOversight;
    }

    public List<String> getDivisionsDeployment() {
        return divisionsDeployment;
    }

    public void setDivisionsDeployment(List<String> divisionsDeployment) {
        this.divisionsDeployment = divisionsDeployment;
    }

    public List<String> getDivisionsFinancialResources() {
        return divisionsFinancialResources;
    }

    public void setDivisionsFinancialResources(List<String> divisionsFinancialResources) {
        this.divisionsFinancialResources = divisionsFinancialResources;
    }

    public List<String> getDivisionsFinancialControl() {
        return divisionsFinancialControl;
    }

    public void setDivisionsFinancialControl(List<String> divisionsFinancialControl) {
        this.divisionsFinancialControl = divisionsFinancialControl;
    }

    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
    }

    public void setUnitsStudentOversight(List<String> unitsStudentOversight) {
        this.unitsStudentOversight = unitsStudentOversight;
    }

    public List<String> getUnitsDeployment() {
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    public List<String> getUnitsFinancialResources() {
        return unitsFinancialResources;
    }

    public void setUnitsFinancialResources(List<String> unitsFinancialResources) {
        this.unitsFinancialResources = unitsFinancialResources;
    }

    public List<String> getUnitsFinancialControl() {
        return unitsFinancialControl;
    }

    public void setUnitsFinancialControl(List<String> unitsFinancialControl) {
        this.unitsFinancialControl = unitsFinancialControl;
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
    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the major program. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    /**
     * The page majorProgramId Structure does not exist. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
