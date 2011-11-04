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
package org.kuali.student.r2.lum.program.dto;

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

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.program.infc.CredentialProgram;

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
public class CredentialProgramInfo extends IdEntityInfo implements CredentialProgram {

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
       private String cip2000Code;

       @XmlElement
       private String diplomaTitle;

       @XmlElement
       private String hegisCode;

       @XmlElement
       private String selectiveEnrollmentCode;

       @XmlElement
       private String cip2010Code;



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
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getCredentialProgramType() {
        return credentialProgramType;
    }

    public void setCredentialProgramType(String credentialProgramType) {
        this.credentialProgramType = credentialProgramType;
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

    @Override
    public String getDiplomaTitle() {
        return diplomaTitle;
    }


    public void setDiplomaTitle(String diplomaTitle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * A code that indicates whether this is Graduate, Undergraduage etc    
     */
    @Override
    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    /**
     * The composite string that is used to officially reference or publish the Credential program. 
     */
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * University specific classification   
     */
    @Override
    public String getUniversityClassification() {
        return universityClassification;
    }

    public void setUniversityClassification(String universityClassification) {
        this.universityClassification = universityClassification;
    }

    /**
     * Institution owning the program.  
     */
    @Override
    public AdminOrgInfo getInstitution() {
        return institution;
    }

    public void setInstitution(AdminOrgInfo institution) {
        this.institution = institution;
    }

    /**
     * Result outcomes from taking the Credential program.  
     */
    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    /**
     * The first academic time period that this credential program would be effective. This may not reflect the first "real" academic time period for this program. 
     */
    @Override
    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }
    
    /**
     * The last academic time period that this credential program would be effective.   
     */
    @Override
    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }
    
    /**
     * The last academic time period that this credential program would be available for enrollment. This may not reflect the last "real" academic time period for this program.    
     */
    @Override
    public String getEndProgramEntryTerm() {
        return endProgramEntryTerm;
    }

    public void setEndProgramEntryTerm(String endProgramEntryTerm) {
        this.endProgramEntryTerm = endProgramEntryTerm;
    }

    /**
     * Divisions responsible to make changes to the credential program  
     */
    @Override
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwner) {
        this.divisionsContentOwner = divisionsContentOwner;
    }

    /**
     * Divisions responsible for student exceptions to the credential program.  
     */
    @Override
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) {
        this.divisionsStudentOversight = divisionsStudentOversight;
    }

    /*
     * Unit responsible to make changes to the credential program   
     */
    @Override
    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    /**
     * Unit responsible for student exceptions to the credential program.   
     */
    @Override
    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
    }

    public void setUnitsStudentOversight(List<String> unitsStudentOversight) {
        this.unitsStudentOversight = unitsStudentOversight;
    }

    /**
     * Learning Objectives associated with this credential program. 
     */
    @Override
    public List<LoDisplayInfo> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }


    @Override
    public String getCip2000Code() {
        return cip2010Code;
    }

    public void setCip2000Code(String cip2000Code) {
        this.cip2000Code = cip2000Code;
     }

    @Override
    public String getCip2010Code() {
        return cip2010Code;
    }

    public void setCip2010Code(String cip2010Code) {
        this.cip2010Code=cip2010Code;
    }

    @Override
    public String getHegisCode() {
        return hegisCode;  //To change body of implemented methods use File | Settings | File Templates.
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