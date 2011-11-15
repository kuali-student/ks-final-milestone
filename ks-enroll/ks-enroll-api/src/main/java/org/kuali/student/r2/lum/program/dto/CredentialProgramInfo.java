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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.program.infc.CredentialProgram;
import org.w3c.dom.Element;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "CredentialProgramInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "shortTitle", "longTitle", "transcriptTitle", "programLevel", "code", "universityClassification",
        "institution","resultOptions" , "startTermKey", "endTermKey", "endProgramEntryTermKey",   "divisionsContentOwner", "divisionsStudentOversight", "unitsContentOwner", "unitsStudentOversight",
        "learningObjectives", "coreProgramIds","programRequirements", "credentialProgramType","diplomaTitle", "selectiveEnrollmentCode","hegisCode", "cip2000Code", "cip2010Code",  "meta", "attributes",
        "_futureElements"})
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
    private String startTermKey;

    @XmlElement
    private String endTermKey;

    @XmlElement
    private String endProgramEntryTermKey;

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
    
    @XmlAnyElement
    private List<Element> _futureElements;

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
            this.resultOptions = credentialProgram.getResultOptions() != null ? new ArrayList<String>(credentialProgram.getResultOptions()) : new ArrayList<String>();
            this.startTermKey = credentialProgram.getStartTermKey();
            this.endTermKey = credentialProgram.getEndTermKey();
            this.endProgramEntryTermKey = credentialProgram.getEndProgramEntryTermKey();
            this.divisionsContentOwner = credentialProgram.getDivisionsContentOwner() != null ? new ArrayList<String>(credentialProgram.getDivisionsContentOwner()) : new ArrayList<String>();
            this.divisionsStudentOversight = credentialProgram.getDivisionsStudentOversight() != null ? new ArrayList<String>(credentialProgram.getDivisionsStudentOversight())
                    : new ArrayList<String>();
            this.unitsContentOwner = credentialProgram.getUnitsContentOwner() != null ? new ArrayList<String>(credentialProgram.getUnitsContentOwner()) : new ArrayList<String>();
            this.unitsStudentOversight = credentialProgram.getUnitsStudentOversight() != null ? new ArrayList<String>(credentialProgram.getUnitsStudentOversight()) : new ArrayList<String>();
            List<LoDisplayInfo> learningObjectives = new ArrayList<LoDisplayInfo>();

            if (credentialProgram.getLearningObjectives() != null) {
                for (LoDisplay loDisplay : credentialProgram.getLearningObjectives()) {
                    LoDisplayInfo loDisplayInfo = new LoDisplayInfo(loDisplay);
                    learningObjectives.add(loDisplayInfo);
                }
            }

            this.coreProgramIds = credentialProgram.getCoreProgramIds() != null ? new ArrayList<String>(credentialProgram.getCoreProgramIds()) : new ArrayList<String>();
            this.programRequirements = credentialProgram.getProgramRequirements() != null ? new ArrayList<String>(credentialProgram.getProgramRequirements()) : new ArrayList<String>();
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
    public String getStartTermKey() {
        return startTermKey;
    }

    public void setStartTermKey(String startTermKey) {
        this.startTermKey = startTermKey;
    }

    @Override
    public String getEndTermKey() {
        return endTermKey;
    }

    public void setEndTermKey(String endTermKey) {
        this.endTermKey = endTermKey;
    }

    @Override
    public String getEndProgramEntryTermKey() {
        return endProgramEntryTermKey;
    }

    public void setEndProgramEntryTermKey(String endProgramEntryTermKey) {
        this.endProgramEntryTermKey = endProgramEntryTermKey;
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