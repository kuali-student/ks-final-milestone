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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.lum.course.dto.LoDisplayInfo;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.program.infc.CredentialProgram;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialProgramInfo extends IdEntityInfo implements CredentialProgram, Serializable {

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
    private List<LoDisplayInfo> learningObjectives;

    @XmlElement
    private List<String> coreProgramIds;

    @XmlElement
    private List<String> programRequirements;

    @XmlAttribute
    private String credentialProgramType;

    @XmlElement
    private String diplomaTitle;

    @XmlElement
    private String hegisCode;

    @XmlElement
    private String selectiveEnrollmentCode;

    @XmlElement
    private String cip2000Code;

    @XmlElement
    private String cip2010Code;

    public CredentialProgramInfo() {

    }

    public CredentialProgramInfo(CredentialProgram credentialProgram) {
        super(credentialProgram);
        if (credentialProgram != null) {
            this.shortTitle = credentialProgram.getShortTitle();
            this.longTitle = credentialProgram.getLongTitle();
            this.transcriptTitle = credentialProgram.getTranscriptTitle();
            this.programLevel = credentialProgram.getProgramLevel();
            this.code = credentialProgram.getCode();
            this.universityClassification = credentialProgram.getUniversityClassification();
            this.institution = new AdminOrgInfo(credentialProgram.getInstitution());
            this.resultOptions = new ArrayList<String>(credentialProgram.getResultOptions());
            this.startTerm = credentialProgram.getStartTerm();
            this.endTerm = credentialProgram.getEndTerm();
            this.endProgramEntryTerm = credentialProgram.getEndProgramEntryTerm();
            this.divisionsContentOwner = new ArrayList<String>(credentialProgram.getDivisionsContentOwner());
            this.divisionsStudentOversight = new ArrayList<String>(credentialProgram.getDivisionsStudentOversight());
            this.unitsContentOwner = new ArrayList<String>(credentialProgram.getUnitsContentOwner());
            this.unitsStudentOversight = new ArrayList<String>(credentialProgram.getUnitsStudentOversight());
            this.learningObjectives = new ArrayList<LoDisplayInfo>(credentialProgram.getLearningObjectives());
            this.coreProgramIds = new ArrayList<String>(credentialProgram.getCoreProgramIds());
            this.programRequirements = new ArrayList<String>(credentialProgram.getProgramRequirements());
            this.credentialProgramType = credentialProgram.getCredentialProgramType();
            this.diplomaTitle = credentialProgram.getDiplomaTitle();
            this.hegisCode = credentialProgram.getHegisCode();
            this.selectiveEnrollmentCode = credentialProgram.getSelectiveEnrollmentCode();
            this.cip2000Code = credentialProgram.getCip2000Code();
            this.cip2010Code = credentialProgram.getCip2010Code();
        }
    }

    @Override
    public List<String> getCoreProgramIds() {
        return coreProgramIds;
    }

    public void setCoreProgramIds(List<String> coreProgramIds) {
        this.coreProgramIds = coreProgramIds;
    }

    @Override
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
     * Unique identifier for a learning unit type. Once set at create time, this
     * field may not be updated.
     */
    @Override
    public String getCredentialProgramType() {
        return credentialProgramType;
    }

    public void setCredentialProgramType(String credentialProgramType) {
        this.credentialProgramType = credentialProgramType;
    }

    /**
     * Abbreviated name of the Credential program
     */
    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * Full name of the Credential Program
     */
    @Override
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

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

    public void setDiplomaTitle(String diplomaTitle) {
        this.diplomaTitle = diplomaTitle;
    }

    @Override
    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    @Override
    public AdminOrgInfo getInstitution() {
        return institution;
    }

    public void setInstitution(AdminOrgInfo institution) {
        this.institution = institution;
    }

    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
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
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }
}