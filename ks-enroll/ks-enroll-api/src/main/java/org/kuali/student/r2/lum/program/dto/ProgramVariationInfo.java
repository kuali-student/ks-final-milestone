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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.TimeAmountInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;

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
public class ProgramVariationInfo extends IdEntityInfo implements ProgramVariation {

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
    private TimeAmountInfo stdDuration;
    
    @XmlElement
    private String startTermKey;

    @XmlElement
    private String endTermKey;

    @XmlElement
    private String endProgramEntryTermKey;

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

    @Override
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

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
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setCatalogDescr(RichTextInfo catalogDescr) {
        this.catalogDescr = catalogDescr;
    }
    
    @Override
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }
    

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
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
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

    @Override
    public MetaInfo getMetaInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setUnitsFinancialControl(List<String> unitsFinancialControl) {
        this.unitsFinancialControl = unitsFinancialControl;
    }

}
