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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.lum.lu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lu.infc.Accreditation;
import org.kuali.student.r2.lum.program.infc.MajorDiscipline;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;
import org.w3c.dom.Element;

/**
 * Detailed information about a single major discipline program
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "MajorDisciplineInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "intensity", "referenceURL", "publishedInstructors", "credentialProgramId", "variations", "code",
        "cip2000Code", "cip2010Code", "hegisCode", "universityClassification", "selectiveEnrollmentCode", "resultOptions", "stdDuration", "startTermId", "endTermId", "endProgramEntryTermId",
        "nextReviewPeriod", "effectiveDate", "shortTitle", "longTitle", "transcriptTitle", "diplomaTitle", "catalogDescr", "catalogPublicationTargets", "learningObjectives", "campusLocations",
        "coreProgramId", "programRequirements", "accreditingAgencies", "divisionsContentOwner", "divisionsStudentOversight", "divisionsDeployment", "divisionsFinancialResources",
        "divisionsFinancialControl", "unitsContentOwner", "unitsStudentOversight", "unitsDeployment", "unitsFinancialResources", "unitsFinancialControl", "meta", "attributes", "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class MajorDisciplineInfo extends IdEntityInfo implements MajorDiscipline, Serializable {

    private static final long serialVersionUID = 1L;

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
    private TimeAmountInfo stdDuration;

    @XmlElement
    private String startTermId;

    @XmlElement
    private String endTermId;

    @XmlElement
    private String endProgramEntryTermId;

    @XmlElement
    private String nextReviewPeriod;

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
    private RichTextInfo catalogDescr;

    @XmlElement
    private List<String> catalogPublicationTargets;

    @XmlElement
    private List<LoDisplayInfo> learningObjectives;

    @XmlElement
    private List<String> campusLocations;

    @XmlElement
    private String coreProgramId;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    private List<AccreditationInfo> accreditingAgencies;

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
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public MajorDisciplineInfo() {

    }

    public MajorDisciplineInfo(MajorDiscipline majorDiscipline) {

        this.intensity = majorDiscipline.getIntensity();

        this.referenceURL = majorDiscipline.getReferenceURL();
        //TODO - after course migration
        List<CluInstructorInfo> newPublishedInstructors = new ArrayList<CluInstructorInfo>();
        if (majorDiscipline.getPublishedInstructors() != null) {
            // for(CluInstructorInfo publishedInstructor:
            // majorDiscipline.getPublishedInstructors() ){
            // newPublishedInstructors.add(new
            // CluInstructorInfo(publishedInstructor));
            // }
        }
        this.publishedInstructors = newPublishedInstructors;
        this.credentialProgramId = majorDiscipline.getCredentialProgramId();
        if (majorDiscipline.getVariations() != null) {
         this.variations = new ArrayList<ProgramVariationInfo>(majorDiscipline.getVariations().size());
         for (ProgramVariation pv : majorDiscipline.getVariations()) {
             ProgramVariationInfo info = new ProgramVariationInfo (pv);
             this.variations.add(info);
         }
        }

        this.code = majorDiscipline.getCode();
        this.cip2000Code = majorDiscipline.getCip2000Code();
        this.cip2010Code = majorDiscipline.getCip2010Code();
        this.hegisCode = majorDiscipline.getHegisCode();
        this.universityClassification = majorDiscipline.getUniversityClassification();
        this.selectiveEnrollmentCode = majorDiscipline.getSelectiveEnrollmentCode();
        this.resultOptions = majorDiscipline.getResultOptions();
        this.stdDuration = new TimeAmountInfo(majorDiscipline.getStdDuration());
        this.startTermId = majorDiscipline.getStartTermId();
        this.endTermId = majorDiscipline.getEndTermId();
        this.endProgramEntryTermId = majorDiscipline.getEndProgramEntryTermId();
        this.nextReviewPeriod = majorDiscipline.getNextReviewPeriod();
        this.effectiveDate = majorDiscipline.getEffectiveDate();
        this.longTitle = majorDiscipline.getLongTitle();
        this.transcriptTitle = majorDiscipline.getTranscriptTitle();
        this.diplomaTitle = majorDiscipline.getDiplomaTitle();
        this.catalogDescr = new RichTextInfo(majorDiscipline.getCatalogDescr());
        this.catalogPublicationTargets = new ArrayList<String>(majorDiscipline.getCatalogPublicationTargets());
        List<LoDisplayInfo> learningObjectives = new ArrayList<LoDisplayInfo>();

        if (majorDiscipline.getLearningObjectives() != null) {

            for (LoDisplayInfo loDisplay : majorDiscipline.getLearningObjectives()) {

                learningObjectives.add(new LoDisplayInfo(loDisplay));
            }
        }
        this.learningObjectives = learningObjectives;
        this.campusLocations = new ArrayList<String>(majorDiscipline.getCampusLocations());
        this.coreProgramId = majorDiscipline.getCoreProgramId();
        this.programRequirements = new ArrayList<String>(majorDiscipline.getProgramRequirements());
        this.accreditingAgencies = new ArrayList<AccreditationInfo>();
        this.divisionsContentOwner = new ArrayList<String>(majorDiscipline.getDivisionsContentOwner());
        this.divisionsStudentOversight = new ArrayList<String>(majorDiscipline.getDivisionsContentOwner());
        this.divisionsDeployment = new ArrayList<String>(majorDiscipline.getDivisionsDeployment());
        this.divisionsFinancialResources = new ArrayList<String>(majorDiscipline.getDivisionsFinancialResources());
        this.divisionsFinancialControl = new ArrayList<String>(majorDiscipline.getDivisionsFinancialControl());
        this.unitsContentOwner = new ArrayList<String>(majorDiscipline.getUnitsContentOwner());
        this.unitsStudentOversight = new ArrayList<String>(majorDiscipline.getUnitsStudentOversight());
        this.unitsDeployment = new ArrayList<String>(majorDiscipline.getUnitsDeployment());
        this.unitsFinancialResources = new ArrayList<String>(majorDiscipline.getUnitsFinancialResources());
        this.unitsFinancialControl = new ArrayList<String>(majorDiscipline.getUnitsFinancialControl());

    }

    /**
     * Indicates if the program is full time, part time, both etc
     */
    @Override
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    /**
     * An URL for additional information about the Major.
     */
    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    /**
     * Instructors associated with this Major. This may not be an exhaustive
     * list, and instead may only be used to indicate potential instructors in
     * publication.
     */
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

    /**
     * Identifier of the credential program under which the major belongs
     */
    @Override
    public String getCredentialProgramId() {
        return credentialProgramId;
    }

    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }

    /**
     * Program variations for the Major
     */
    @Override
    public List<ProgramVariationInfo> getVariations() {
        if (variations == null) {
            variations = new ArrayList<ProgramVariationInfo>(0);
        }
        return variations;
    }

    public void seVariations(List<ProgramVariationInfo> variations) {
        this.variations = variations;
    }

    /**
     * The composite string that is used to officially reference or publish the
     * Major. Note it may have an internal structure that each Institution may
     * want to enforce. This structure may be composed from the other parts of
     * the structure such as Level amp; Division, but may include items such as
     * cluType.
     */
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * CIP 2000 Code for the Program
     */
    @Override
    public String getCip2000Code() {
        return cip2000Code;
    }

    public void setCip2000Code(String cip2000Code) {
        this.cip2000Code = cip2000Code;
    }

    /**
     * CIP 2010 Code for the Program
     */
    @Override
    public String getCip2010Code() {
        return cip2010Code;
    }

    public void setCip2010Code(String cip2010Code) {
        this.cip2010Code = cip2010Code;
    }

    /**
     * HEGIS Code for the Program
     */
    @Override
    public String getHegisCode() {
        return hegisCode;
    }

    public void setHegisCode(String hegisCode) {
        this.hegisCode = hegisCode;
    }

    /**
     * University specific classification e.g Major(Bacc), Specialization
     */
    @Override
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    /**
     * Specifies if the Major is Selective Major, Limited Enrollment program or
     * Selective Admissions
     */
    @Override
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }

    /**
     * The first academic time period that this clu would be effective. This may
     * not reflect the first "real" academic time period for this Major.
     */
    @Override
    public String getStartTermId() {
        return startTermId;
    }

    public void setStartTermId(String startTermId) {
        this.startTermId = startTermId;
    }

    /**
     * The last academic time period that this Major would be effective.
     */
    @Override
    public String getEndTermId() {
        return endTermId;
    }

    public void setEndTermId(String endTermId) {
        this.endTermId = endTermId;
    }

    @Override
    public String getNextReviewPeriod() {
        return nextReviewPeriod;
    }

    public void setNextReviewPeriod(String nextReviewPeriod) {
        this.nextReviewPeriod = nextReviewPeriod;
    }

    /**
     * Date and time the Course became effective. This is a similar concept to
     * the effective date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the expiration date.
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Abbreviated name of the Major Discipline
     */
    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Major Discipline
     */
    @Override
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    /**
     * Information related to the official identification of the Major
     * discipline, typically in human readable form. Used to officially
     * reference or publish.
     */
    @Override
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    public void setTranscriptTitle(String transcriptTitle) {
        this.transcriptTitle = transcriptTitle;
    }

    @Override
    public String getDiplomaTitle() {
        return diplomaTitle;
    }

    @Override
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setDiplomaTitle(String diplomaTitle) {
        this.diplomaTitle = diplomaTitle;
    }

    /**
     * List of catalog targets where major information will be published.
     */
    @Override
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }

    /**
     * Learning Objectives associated with this Major.
     */
    @Override
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
    @Override
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public String getCoreProgramId() {
        return coreProgramId;
    }

    public void setCoreProgramId(String coreProgramId) {
        this.coreProgramId = coreProgramId;
    }

    /**
     * Major Discipline Program Requirements.
     */
    @Override
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
    }

    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    @Override
    public TimeAmountInfo getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    @Override
    public String getEndProgramEntryTermId() {
        return endProgramEntryTermId;
    }

    public void setEndProgramEntryTermId(String endProgramEntryTermId) {
        this.endProgramEntryTermId = endProgramEntryTermId;
    }

    @Override
    public List<AccreditationInfo> getAccreditingAgencies() {
        return accreditingAgencies;
    }

    public void setAccreditingAgencies(List<AccreditationInfo> accreditingAgencies) {
        this.accreditingAgencies = accreditingAgencies;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
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
    public List<String> getUnitsDeployment() {
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
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

}
