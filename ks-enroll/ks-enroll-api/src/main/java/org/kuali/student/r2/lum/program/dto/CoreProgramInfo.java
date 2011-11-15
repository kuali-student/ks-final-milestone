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

import javax.xml.bind.annotation.*;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.program.infc.CoreProgram;
import org.w3c.dom.Element;

@XmlType(name = "CoreProgramInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "shortTitle", "longTitle", "transcriptTitle", "code", "universityClassification", "startTermKey",
        "endTermKey", "endProgramEntryTermKey", "programRequirements", "divisionsContentOwner", "divisionsStudentOversight", "unitsContentOwner", "unitsStudentOversight", "referenceURL",
        "catalogDescr", "catalogPublicationTargets", "learningObjectives", "cip2000Code", "diplomaTitle", "hegisCode", "selectiveEnrollmentCode", "cip2010Code", "meta", "attributes",
        "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CoreProgramInfo extends IdEntityInfo implements CoreProgram, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String shortTitle;

    @XmlElement
    private String longTitle;

    @XmlElement
    private String transcriptTitle;

    @XmlElement
    private String code;

    @XmlElement
    private String universityClassification;

    @XmlElement
    private String startTermKey;

    @XmlElement
    private String endTermKey;

    @XmlElement
    private String endProgramEntryTermKey;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    private List<String> divisionsContentOwner;

    @XmlElement
    private List<String> divisionsStudentOversight;

    @XmlElement
    private List<String> unitsContentOwner;

    @XmlElement
    private List<String> unitsStudentOversight;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private RichTextInfo catalogDescr;

    @XmlElement
    private List<String> catalogPublicationTargets;

    @XmlElement
    private List<LoDisplayInfo> learningObjectives;

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

    @XmlAnyElement
    private List<Element> _futureElements;

    public CoreProgramInfo() {

    }

    public CoreProgramInfo(CoreProgram coreProgram) {
        super(coreProgram);
        if (coreProgram != null) {
            shortTitle = coreProgram.getShortTitle();
            longTitle = coreProgram.getLongTitle();
            transcriptTitle = coreProgram.getTranscriptTitle();
            this.code = coreProgram.getCode();
            this.universityClassification = coreProgram.getUniversityClassification();
            this.startTermKey = coreProgram.getStartTermKey();
            this.endTermKey = coreProgram.getEndTermKey();
            this.endProgramEntryTermKey = coreProgram.getEndProgramEntryTermKey();
            this.programRequirements = coreProgram.getProgramRequirements() != null ? new ArrayList<String>(coreProgram.getProgramRequirements()) : new ArrayList<String>();
            this.divisionsContentOwner = coreProgram.getDivisionsContentOwner() != null ? new ArrayList<String>(coreProgram.getDivisionsContentOwner()) : new ArrayList<String>();
            this.divisionsStudentOversight = coreProgram.getDivisionsStudentOversight() != null ? new ArrayList<String>(coreProgram.getDivisionsContentOwner()) : new ArrayList<String>();
            this.unitsContentOwner = coreProgram.getUnitsContentOwner() != null ? new ArrayList<String>(coreProgram.getUnitsContentOwner()) : new ArrayList<String>();
            this.unitsStudentOversight = coreProgram.getUnitsStudentOversight() != null ? new ArrayList<String>(coreProgram.getUnitsStudentOversight()) : new ArrayList<String>();
            this.referenceURL = coreProgram.getReferenceURL();
            this.catalogDescr = coreProgram.getCatalogDescr() != null ? new RichTextInfo(coreProgram.getCatalogDescr()) : null;
            this.catalogPublicationTargets = coreProgram.getCatalogPublicationTargets() != null ? new ArrayList<String>(coreProgram.getCatalogPublicationTargets()) : new ArrayList<String>();

            List<LoDisplayInfo> learningObjectives = new ArrayList<LoDisplayInfo>();

            if (coreProgram.getLearningObjectives() != null) {
                for (LoDisplay loDisplay : coreProgram.getLearningObjectives()) {
                    LoDisplayInfo loDisplayInfo = new LoDisplayInfo(loDisplay);
                    learningObjectives.add(loDisplayInfo);
                }
            }
            this.learningObjectives = learningObjectives;
            this.cip2000Code = coreProgram.getCip2000Code();
            this.diplomaTitle = coreProgram.getDiplomaTitle();
            this.hegisCode = coreProgram.getHegisCode();
            this.selectiveEnrollmentCode = coreProgram.getSelectiveEnrollmentCode();
            this.cip2010Code = coreProgram.getCip2010Code();

        }
    }

    @Override
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>();
        }
        return programRequirements;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
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
    public String getStartTermKey() {
        return startTermKey;
    }

    public void setStartTermKey(String startTerm) {
        this.startTermKey = startTerm;
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

    public void setEndProgramEntryTermKey(String endProgramEntryTerm) {
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
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    @Override
    public List<? extends LoDisplay> getLearningObjectives() {
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