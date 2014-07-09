/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.infc.Accreditation;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.infc.MinorDiscipline;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;


@XmlType(name = "MinorDisciplineInfo", propOrder = {"id",
        "typeKey",
        "stateKey",
        "version",
        "descr",
        "code",
        "shortTitle",
        "longTitle",
        "transcriptTitle",
        "diplomaTitle",
        "cip2000Code",
        "cip2010Code",
        "hegisCode",
        "universityClassification",
        "selectiveEnrollmentCode",
        "intensity",
        "effectiveDate",
        "campusLocations",
        "resultOptions",
        "stdDuration",
        "divisionsDeployment",
        "divisionsFinancialResources",
        "divisionsFinancialControl",
        "unitsFinancialResources",
        "unitsFinancialControl",
        "unitsDeployment",
        "credentialProgramId",
        "programRequirements",
        "nextReviewPeriod",
        "publishedInstructors",
        "accreditingAgencies",
        "variations",
        "orgCoreProgram",
        "startTerm",
        "endTerm",
        "endProgramEntryTerm",
        "divisionsContentOwner",
        "divisionsStudentOversight",
        "unitsContentOwner",
        "unitsStudentOversight",
        "learningObjectives",
        "catalogDescr",
        "catalogPublicationTargets",
        "referenceURL",
        "attributes",
        "meta",
        "_futureElements"  })
    
@XmlAccessorType(XmlAccessType.FIELD)
public class MinorDisciplineInfo extends IdNamelessEntityInfo implements MinorDiscipline {
    
    private static final long serialVersionUID = 1L;

    // ProgramIdentifierAssembly
    @XmlElement
    private String code;
    @XmlElement
    private String shortTitle;
    @XmlElement
    private String longTitle;
    @XmlElement
    private String transcriptTitle;
    @XmlElement
    private String diplomaTitle;

    // ProgramCodeAssembly
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

    // CommonWithProgramVariationInfo
    @XmlElement
    private String intensity;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private List<String> campusLocations;
    @XmlElement
    private List<String> resultOptions;
    @XmlElement
    private TimeAmount stdDuration;

    // ProgramFullOrgAssembly
    @XmlElement
    private List<String> divisionsDeployment;
    @XmlElement
    private List<String> divisionsFinancialResources;
    @XmlElement
    private List<String> divisionsFinancialControl;
    @XmlElement
    private List<String> unitsFinancialResources;
    @XmlElement
    private List<String> unitsFinancialControl;
    @XmlElement
    private List<String> unitsDeployment;

    // ProgramCredentialAssembly
    // MajorDisciplineInfo
    @XmlElement
    private String credentialProgramId;

    // ProgramRequirementAssembly
    @XmlElement
    private List<String> programRequirements;

    // MajorDisciplineInfo
    @XmlElement
    private String nextReviewPeriod;
    @XmlElement
    private List<CluInstructorInfo> publishedInstructors;
    @XmlElement
    private List<AccreditationInfo> accreditingAgencies;
    @XmlElement
    private List<ProgramVariationInfo> variations;
    @XmlElement
    private CoreProgramInfo orgCoreProgram;

    // ProgramAtpAssembly
    @XmlElement
    private String startTerm;
    @XmlElement
    private String endTerm;
    @XmlElement
    private String endProgramEntryTerm;

    // CommonWithCredentialProgramInfo
    @XmlElement
    private List<String> divisionsContentOwner;
    @XmlElement
    private List<String> divisionsStudentOversight;
    @XmlElement
    private List<String> unitsContentOwner;
    @XmlElement
    private List<String> unitsStudentOversight;
    @XmlElement
    private List<LoDisplayInfo> learningObjectives;

    // ProgramPublicationAssembly
    @XmlElement
    private RichText catalogDescr;
    @XmlElement
    private List<String> catalogPublicationTargets;
    @XmlElement
    private String referenceURL;

    @XmlAnyElement
    private List<Object> _futureElements;
    
    public MinorDisciplineInfo() {
        this.programRequirements = new ArrayList<String>();
    }
    
    public MinorDisciplineInfo(MinorDiscipline minorDiscipline) {
        super(minorDiscipline);

        this.code = minorDiscipline.getCode();
        this.shortTitle = minorDiscipline.getShortTitle();
        this.longTitle = minorDiscipline.getLongTitle();
        this.transcriptTitle = minorDiscipline.getTranscriptTitle();
        this.diplomaTitle = minorDiscipline.getDiplomaTitle();

        this.cip2000Code = minorDiscipline.getCip2000Code();
        this.cip2010Code = minorDiscipline.getCip2010Code();
        this.hegisCode = minorDiscipline.getHegisCode();
        this.universityClassification = minorDiscipline.getUniversityClassification();
        this.selectiveEnrollmentCode = minorDiscipline.getSelectiveEnrollmentCode();

        this.intensity = minorDiscipline.getIntensity();
        this.effectiveDate = minorDiscipline.getEffectiveDate();
        if(minorDiscipline.getCampusLocations() != null) {
            this.campusLocations = new ArrayList<String>(minorDiscipline.getCampusLocations().size());
            for(String campusLocation : minorDiscipline.getCampusLocations()) {
                this.campusLocations.add(campusLocation);
            }
        }
        if(minorDiscipline.getResultOptions() != null) {
            this.resultOptions = new ArrayList<String>(minorDiscipline.getResultOptions().size());
            for(String resultOption : minorDiscipline.getResultOptions()) {
                this.resultOptions.add(resultOption);
            }
        }
        this.stdDuration = minorDiscipline.getStdDuration();

        if(minorDiscipline.getDivisionsDeployment() != null) {
            this.divisionsDeployment = new ArrayList<String>(minorDiscipline.getDivisionsDeployment().size());
            for(String divisionDeployment : minorDiscipline.getDivisionsDeployment()) {
                this.divisionsDeployment.add(divisionDeployment);
            }
        }
        if(minorDiscipline.getDivisionsFinancialResources() != null) {
            this.divisionsFinancialResources = new ArrayList<String>(minorDiscipline.getDivisionsFinancialResources().size());
            for(String divisionFinancialResources : minorDiscipline.getDivisionsFinancialResources()) {
                this.divisionsFinancialResources.add(divisionFinancialResources);
            }
        }
        if(minorDiscipline.getDivisionsFinancialControl() != null) {
            this.divisionsFinancialControl = new ArrayList<String>(minorDiscipline.getDivisionsFinancialControl().size());
            for(String divisionFinancialControl : minorDiscipline.getDivisionsFinancialControl()) {
                this.divisionsFinancialControl.add(divisionFinancialControl);
            }
        }
        if(minorDiscipline.getUnitsFinancialResources() != null) {
            this.unitsFinancialResources = new ArrayList<String>(minorDiscipline.getUnitsFinancialResources().size());
            for(String unitsFinancialResource : minorDiscipline.getUnitsFinancialResources()) {
                this.unitsFinancialResources.add(unitsFinancialResource);
            }
        }
        if(minorDiscipline.getUnitsFinancialControl() != null) {
            this.unitsFinancialControl = new ArrayList<String>(minorDiscipline.getUnitsFinancialControl().size());
            for(String unitFinancialControl : minorDiscipline.getUnitsFinancialControl()) {
                this.unitsFinancialControl.add(unitFinancialControl);
            }
        }
        if(minorDiscipline.getUnitsDeployment() != null) {
            this.unitsDeployment = new ArrayList<String>(minorDiscipline.getUnitsDeployment().size());
            for(String unitDeployment : minorDiscipline.getUnitsDeployment()) {
                this.unitsDeployment.add(unitDeployment);
            }
        }
        this.credentialProgramId = minorDiscipline.getCredentialProgramId();
        if(minorDiscipline.getProgramRequirements() != null) {
            this.programRequirements = new ArrayList<String>(minorDiscipline.getProgramRequirements().size());
            for(String programRequirement : minorDiscipline.getProgramRequirements()) {
                this.programRequirements.add(programRequirement);
            }
        }
        this.nextReviewPeriod = minorDiscipline.getNextReviewPeriod();
        if (minorDiscipline.getPublishedInstructors() != null) {
            this.publishedInstructors = new ArrayList<CluInstructorInfo>(minorDiscipline.getPublishedInstructors().size());
            for (CluInstructor cluInst : minorDiscipline.getPublishedInstructors()) {
                this.publishedInstructors.add(new CluInstructorInfo(cluInst));
            }
        }
        if (minorDiscipline.getAccreditingAgencies() != null) {
            this.accreditingAgencies = new ArrayList<AccreditationInfo>();
            for (Accreditation aa : minorDiscipline.getAccreditingAgencies()) {
                this.accreditingAgencies.add(new AccreditationInfo(aa));
            }
        }
        if (minorDiscipline.getVariations() != null) {
            this.variations = new ArrayList<ProgramVariationInfo>(minorDiscipline.getVariations().size());
            for (ProgramVariation pv : minorDiscipline.getVariations()) {
                ProgramVariationInfo info = new ProgramVariationInfo(pv);
                this.variations.add(info);
            }
        }
        if (minorDiscipline.getOrgCoreProgram() != null) {
            this.orgCoreProgram = new CoreProgramInfo(minorDiscipline.getOrgCoreProgram());
        }
        this.startTerm = minorDiscipline.getStartTerm();
        this.endTerm = minorDiscipline.getEndTerm();
        this.endProgramEntryTerm = minorDiscipline.getEndProgramEntryTerm();
        if(minorDiscipline.getDivisionsContentOwner() != null) {
            this.divisionsContentOwner = new ArrayList<String>(minorDiscipline.getDivisionsContentOwner().size());
            for(String divisionContentOwner : minorDiscipline.getDivisionsContentOwner()) {
                this.divisionsContentOwner.add(divisionContentOwner);
            }
        }
        if(minorDiscipline.getDivisionsStudentOversight() != null) {
            this.divisionsStudentOversight = new ArrayList<String>(minorDiscipline.getDivisionsStudentOversight().size());
            for(String divisionStudentOversight : minorDiscipline.getDivisionsStudentOversight()) {
                this.divisionsStudentOversight.add(divisionStudentOversight);
            }
        }
        if(minorDiscipline.getUnitsContentOwner() != null) {
            this.unitsContentOwner = new ArrayList<String>(minorDiscipline.getUnitsContentOwner().size());
            for(String unitContentOwner : minorDiscipline.getUnitsContentOwner()) {
                this.unitsContentOwner.add(unitContentOwner);
            }
        }
        if(minorDiscipline.getUnitsStudentOversight() != null) {
            this.unitsStudentOversight = new ArrayList<String>(minorDiscipline.getUnitsStudentOversight().size());
            for(String unitStudentOversight : minorDiscipline.getUnitsStudentOversight()) {
                this.unitsStudentOversight.add(unitStudentOversight);
            }
        }
        if (minorDiscipline.getLearningObjectives() != null) {
            this.learningObjectives = new ArrayList<LoDisplayInfo>(minorDiscipline.getLearningObjectives().size());
            for (LoDisplayInfo lo : minorDiscipline.getLearningObjectives()) {
                LoDisplayInfo info = new LoDisplayInfo(lo);
                this.learningObjectives.add(info);
            }
        }
        this.catalogDescr = minorDiscipline.getCatalogDescr();
        if(minorDiscipline.getCatalogPublicationTargets() != null) {
            this.catalogPublicationTargets = new ArrayList<String>(minorDiscipline.getCatalogPublicationTargets().size());
            for(String catalogPublicationTarget : minorDiscipline.getCatalogPublicationTargets()) {
                this.catalogPublicationTargets.add(catalogPublicationTarget);
            }
        }
        this.referenceURL = minorDiscipline.getReferenceURL();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    @Override
    public String getLongTitle() {
        return longTitle;
    }

    @Override
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    @Override
    public String getDiplomaTitle() {
        return diplomaTitle;
    }

    public void setDiplomaTitle(String diplomaTitle) {
        this.diplomaTitle = diplomaTitle;
    }

    @Override
    public String getCip2000Code() {
        return cip2000Code;
    }

    public void setCip2000Code(String cip2000Code) {
        this.cip2000Code = cip2000Code;
    }

    @Override
    public String getCip2010Code() {
        return cip2010Code;
    }

    public void setCip2010Code(String cip2010Code) {
        this.cip2010Code = cip2010Code;
    }

    @Override
    public String getHegisCode() {
        return hegisCode;
    }

    public void setHegisCode(String hegisCode) {
        this.hegisCode = hegisCode;
    }

    @Override
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    @Override
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }

    @Override
    public List<String> getUnitsDeployment() {
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    @Override
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    @Override
    public TimeAmount getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    @Override
    public List<String> getDivisionsDeployment() {
        return divisionsDeployment;
    }

    public void setDivisionsDeployment(List<String> divisionsDeployment) {
        this.divisionsDeployment = divisionsDeployment;
    }

    @Override
    public List<String> getDivisionsFinancialResources() {
        return divisionsFinancialResources;
    }

    public void setDivisionsFinancialResources(List<String> divisionsFinancialResources) {
        this.divisionsFinancialResources = divisionsFinancialResources;
    }

    @Override
    public List<String> getDivisionsFinancialControl() {
        return divisionsFinancialControl;
    }

    public void setDivisionsFinancialControl(List<String> divisionsFinancialControl) {
        this.divisionsFinancialControl = divisionsFinancialControl;
    }

    @Override
    public List<String> getUnitsFinancialResources() {
        return unitsFinancialResources;
    }

    public void setUnitsFinancialResources(List<String> unitsFinancialResources) {
        this.unitsFinancialResources = unitsFinancialResources;
    }

    @Override
    public List<String> getUnitsFinancialControl() {
        return unitsFinancialControl;
    }

    public void setUnitsFinancialControl(List<String> unitsFinancialControl) {
        this.unitsFinancialControl = unitsFinancialControl;
    }

    /**
     * Identifier of the credential program under which the honors belongs
     */
    @Override
    public String getCredentialProgramId() {
        return credentialProgramId;
    }
    
    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }
    @Override
    public List<CluInstructorInfo> getPublishedInstructors() {
        if (publishedInstructors == null) {
            publishedInstructors = new ArrayList<CluInstructorInfo>(0);
        }
        return publishedInstructors;
    }

    public void setPublishedInstructors(List<CluInstructorInfo> publishedInstructors) {
        this.publishedInstructors = publishedInstructors;
    }

    @Override
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
    }

    @Override
    public RichText getCatalogDescr() {
        return catalogDescr;
    }

    @Override
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
    }

    @Override
    public List<ProgramVariationInfo> getVariations() {
        if (variations == null) {
            variations = new ArrayList<ProgramVariationInfo>(0);
        }
        return variations;
    }

    public void setVariations(List<ProgramVariationInfo> variations) {
        this.variations = variations;
    }

    @Override
    public String getNextReviewPeriod() {
        return nextReviewPeriod;
    }

    public void setNextReviewPeriod(String nextReviewPeriod) {
        this.nextReviewPeriod = nextReviewPeriod;
    }

    @Override
    public List<AccreditationInfo> getAccreditingAgencies() {
        return accreditingAgencies;
    }

    public void setAccreditingAgencies(List<AccreditationInfo> accreditingAgencies) {
        this.accreditingAgencies = accreditingAgencies;
    }

    @Override
    public CoreProgramInfo getOrgCoreProgram() {
        return orgCoreProgram;
    }

    public void setOrgCoreProgram(CoreProgramInfo orgCoreProgram) {
        this.orgCoreProgram = orgCoreProgram;
    }

    @Override
    public List<String> getCampusLocations() {
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    @Override
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    @Override
    public String getEndProgramEntryTerm() {
        return endProgramEntryTerm;
    }

    public void setEndProgramEntryTerm(String endProgramEntryTerm) {
        this.endProgramEntryTerm = endProgramEntryTerm;
    }

    @Override
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwner) {
        this.divisionsContentOwner = divisionsContentOwner;
    }

    @Override
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) {
        this.divisionsStudentOversight = divisionsStudentOversight;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    @Override
    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
    }

    public void setUnitsStudentOversight(List<String> unitsStudentOversight) {
        this.unitsStudentOversight = unitsStudentOversight;
    }

    @Override
    public List<LoDisplayInfo> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    public void setCatalogDescr(RichText catalogDescr) {
        this.catalogDescr = catalogDescr;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

}
