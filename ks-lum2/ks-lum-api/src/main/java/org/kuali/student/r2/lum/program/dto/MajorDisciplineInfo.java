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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.infc.Accreditation;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCommonAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCredentialAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramFullOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.lum.program.infc.MajorDiscipline;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;
import org.w3c.dom.Element;

@XmlType(name = "MajorDisciplineInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "version",
    "descr",
    "code",
    "shortTitle",
    "longTitle",
    "transcriptTitle",
    "universityClassification",
    "startTerm",
    "endTerm",
    "endProgramEntryTerm",
    "divisionsContentOwner",
    "divisionsStudentOversight",
    "unitsContentOwner",
    "unitsStudentOversight",
    "learningObjectives",
    "programRequirements",
    "referenceURL",
    "catalogDescr",
    "catalogPublicationTargets",
    "intensity",
    "cip2000Code",
    "cip2010Code",
    "hegisCode",
    "selectiveEnrollmentCode",
    "effectiveDate",
    "diplomaTitle",
    "campusLocations",
    "resultOptions",
    "stdDuration",
    "divisionsDeployment",
    "divisionsFinancialResources",
    "divisionsFinancialControl",
    "unitsDeployment",
    "unitsFinancialResources",
    "unitsFinancialControl",
    "nextReviewPeriod",
    "publishedInstructors",
    "credentialProgramId",
    "accreditingAgencies",
    "variations",
    "orgCoreProgram",
    "attributes",
    "meta",
    "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class MajorDisciplineInfo extends CommonWithProgramVariationInfo implements MajorDiscipline,
        ProgramCommonAssembly,
        ProgramIdentifierAssembly,
        ProgramFullOrgAssembly,
        ProgramAtpAssembly,
        ProgramCodeAssembly,
        ProgramPublicationAssembly,
        ProgramCredentialAssembly,
        ProgramRequirementAssembly,
        Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String nextReviewPeriod;
    @XmlElement
    private List<CluInstructorInfo> publishedInstructors;
    @XmlElement
    private String credentialProgramId;
    @XmlElement
    private List<AccreditationInfo> accreditingAgencies;
    @XmlElement
    private List<ProgramVariationInfo> variations;
    @XmlElement
    private CoreProgramInfo orgCoreProgram;
    @XmlAnyElement
    private List<Element> _futureElements;

    public MajorDisciplineInfo() {
    }

    public MajorDisciplineInfo(MajorDiscipline input) {
        super(input);

        if (input.getPublishedInstructors() != null) {
            this.publishedInstructors = new ArrayList<CluInstructorInfo>(input.getPublishedInstructors().size());
            for (CluInstructor cluInst : input.getPublishedInstructors()) {
                this.publishedInstructors.add(new CluInstructorInfo(cluInst));
            }
        }
        this.credentialProgramId = input.getCredentialProgramId();

        this.nextReviewPeriod = input.getNextReviewPeriod();
        if (input.getAccreditingAgencies() != null) {
            this.accreditingAgencies = new ArrayList<AccreditationInfo>();
            for (Accreditation aa : input.getAccreditingAgencies()) {
                this.accreditingAgencies.add(new AccreditationInfo(aa));
            }
        }
        if (input.getVariations() != null) {
            this.variations = new ArrayList<ProgramVariationInfo>(input.getVariations().size());
            for (ProgramVariation pv : input.getVariations()) {
                ProgramVariationInfo info = new ProgramVariationInfo(pv);
                this.variations.add(info);
            }
        }
        if (input.getOrgCoreProgram() != null) {
            this.orgCoreProgram = new CoreProgramInfo(input.getOrgCoreProgram());
        }
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
    public String getCredentialProgramId() {
        return credentialProgramId;
    }

    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
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
}
