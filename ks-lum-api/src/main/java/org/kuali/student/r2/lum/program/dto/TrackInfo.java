/**
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.infc.Accreditation;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.infc.Track;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@XmlType(name = "TrackInfo", propOrder = {"id",
        "typeKey",
        "stateKey",
        "version",
        "descr",
        "code",
        "shortTitle",
        "longTitle",
        "transcriptTitle",
        "diplomaTitle",
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
        "lastAwardedTerm",
        "attributes",
        "meta",
        "_futureElements"  })
    
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackInfo extends IdNamelessEntityInfo implements Track {

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
    @XmlElement
    private String lastAwardedTerm;

    @XmlAnyElement
    private List<Object> _futureElements;

    public TrackInfo() {
    }

    public TrackInfo(Track track) {
        super(track);

        this.code = track.getCode();
        this.shortTitle = track.getShortTitle();
        this.longTitle = track.getLongTitle();
        this.transcriptTitle = track.getTranscriptTitle();
        this.diplomaTitle = track.getDiplomaTitle();

        this.universityClassification = track.getUniversityClassification();
        this.selectiveEnrollmentCode = track.getSelectiveEnrollmentCode();

        this.intensity = track.getIntensity();
        this.effectiveDate = track.getEffectiveDate();
        if(track.getCampusLocations() != null) {
            this.campusLocations = new ArrayList<String>(track.getCampusLocations().size());
            for(String campusLocation : track.getCampusLocations()) {
                this.campusLocations.add(campusLocation);
            }
        }
        if(track.getResultOptions() != null) {
            this.resultOptions = new ArrayList<String>(track.getResultOptions().size());
            for(String resultOption : track.getResultOptions()) {
                this.resultOptions.add(resultOption);
            }
        }
        this.stdDuration = track.getStdDuration();

        if(track.getDivisionsDeployment() != null) {
            this.divisionsDeployment = new ArrayList<String>(track.getDivisionsDeployment().size());
            for(String divisionDeployment : track.getDivisionsDeployment()) {
                this.divisionsDeployment.add(divisionDeployment);
            }
        }
        if(track.getDivisionsFinancialResources() != null) {
            this.divisionsFinancialResources = new ArrayList<String>(track.getDivisionsFinancialResources().size());
            for(String divisionFinancialResources : track.getDivisionsFinancialResources()) {
                this.divisionsFinancialResources.add(divisionFinancialResources);
            }
        }
        if(track.getDivisionsFinancialControl() != null) {
            this.divisionsFinancialControl = new ArrayList<String>(track.getDivisionsFinancialControl().size());
            for(String divisionFinancialControl : track.getDivisionsFinancialControl()) {
                this.divisionsFinancialControl.add(divisionFinancialControl);
            }
        }
        if(track.getUnitsFinancialResources() != null) {
            this.unitsFinancialResources = new ArrayList<String>(track.getUnitsFinancialResources().size());
            for(String unitsFinancialResource : track.getUnitsFinancialResources()) {
                this.unitsFinancialResources.add(unitsFinancialResource);
            }
        }
        if(track.getUnitsFinancialControl() != null) {
            this.unitsFinancialControl = new ArrayList<String>(track.getUnitsFinancialControl().size());
            for(String unitFinancialControl : track.getUnitsFinancialControl()) {
                this.unitsFinancialControl.add(unitFinancialControl);
            }
        }
        if(track.getUnitsDeployment() != null) {
            this.unitsDeployment = new ArrayList<String>(track.getUnitsDeployment().size());
            for(String unitDeployment : track.getUnitsDeployment()) {
                this.unitsDeployment.add(unitDeployment);
            }
        }
        this.credentialProgramId = track.getCredentialProgramId();
        if(track.getProgramRequirements() != null) {
            this.programRequirements = new ArrayList<String>(track.getProgramRequirements().size());
            for(String programRequirement : track.getProgramRequirements()) {
                this.programRequirements.add(programRequirement);
            }
        }
        this.nextReviewPeriod = track.getNextReviewPeriod();
        if (track.getPublishedInstructors() != null) {
            this.publishedInstructors = new ArrayList<CluInstructorInfo>(track.getPublishedInstructors().size());
            for (CluInstructor cluInst : track.getPublishedInstructors()) {
                this.publishedInstructors.add(new CluInstructorInfo(cluInst));
            }
        }
        if (track.getAccreditingAgencies() != null) {
            this.accreditingAgencies = new ArrayList<AccreditationInfo>();
            for (Accreditation aa : track.getAccreditingAgencies()) {
                this.accreditingAgencies.add(new AccreditationInfo(aa));
            }
        }
        this.startTerm = track.getStartTerm();
        this.endTerm = track.getEndTerm();
        this.endProgramEntryTerm = track.getEndProgramEntryTerm();
        if(track.getDivisionsContentOwner() != null) {
            this.divisionsContentOwner = new ArrayList<String>(track.getDivisionsContentOwner().size());
            for(String divisionContentOwner : track.getDivisionsContentOwner()) {
                this.divisionsContentOwner.add(divisionContentOwner);
            }
        }
        if(track.getDivisionsStudentOversight() != null) {
            this.divisionsStudentOversight = new ArrayList<String>(track.getDivisionsStudentOversight().size());
            for(String divisionStudentOversight : track.getDivisionsStudentOversight()) {
                this.divisionsStudentOversight.add(divisionStudentOversight);
            }
        }
        if(track.getUnitsContentOwner() != null) {
            this.unitsContentOwner = new ArrayList<String>(track.getUnitsContentOwner().size());
            for(String unitContentOwner : track.getUnitsContentOwner()) {
                this.unitsContentOwner.add(unitContentOwner);
            }
        }
        if(track.getUnitsStudentOversight() != null) {
            this.unitsStudentOversight = new ArrayList<String>(track.getUnitsStudentOversight().size());
            for(String unitStudentOversight : track.getUnitsStudentOversight()) {
                this.unitsStudentOversight.add(unitStudentOversight);
            }
        }
        if (track.getLearningObjectives() != null) {
            this.learningObjectives = new ArrayList<LoDisplayInfo>(track.getLearningObjectives().size());
            for (LoDisplayInfo lo : track.getLearningObjectives()) {
                LoDisplayInfo info = new LoDisplayInfo(lo);
                this.learningObjectives.add(info);
            }
        }
        this.catalogDescr = track.getCatalogDescr();
        if(track.getCatalogPublicationTargets() != null) {
            this.catalogPublicationTargets = new ArrayList<String>(track.getCatalogPublicationTargets().size());
            for(String catalogPublicationTarget : track.getCatalogPublicationTargets()) {
                this.catalogPublicationTargets.add(catalogPublicationTarget);
            }
        }
        this.referenceURL = track.getReferenceURL();
        this.lastAwardedTerm = track.getLastAwardedTerm();
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

    @Override
    public String getLastAwardedTerm() {
        return lastAwardedTerm;
    }

    public void setLastAwardedTerm(String lastAwardedTerm) {
        this.lastAwardedTerm = lastAwardedTerm;
    }

}
