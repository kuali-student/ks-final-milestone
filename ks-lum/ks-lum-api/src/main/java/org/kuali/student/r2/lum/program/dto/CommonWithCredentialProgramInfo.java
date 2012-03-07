package org.kuali.student.r2.lum.program.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.program.infc.CommonWithCredentialProgram;

@SuppressWarnings("serial")
@XmlTransient
public abstract class CommonWithCredentialProgramInfo extends IdNamelessEntityInfo implements CommonWithCredentialProgram {

    @XmlElement
    private VersionInfo version;
    @XmlElement
    private RichTextInfo descr;
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
    private List<String> programRequirements;

    public CommonWithCredentialProgramInfo() {
    }

    public CommonWithCredentialProgramInfo(CommonWithCredentialProgram input) {
        super(input);
        if (input != null) {
            this.version = input.getVersion() != null
                    ? new VersionInfo(input.getVersion())
                    : null;
            this.descr = input.getDescr() != null
                    ? new RichTextInfo(input.getDescr())
                    : null;
            this.shortTitle = input.getShortTitle();
            this.longTitle = input.getLongTitle();
            this.transcriptTitle = input.getTranscriptTitle();
            this.code = input.getCode();
            this.universityClassification = input.getUniversityClassification();
            this.startTerm = input.getStartTerm();
            this.endTerm = input.getEndTerm();
            this.endProgramEntryTerm = input.getEndProgramEntryTerm();
            this.programRequirements = input.getProgramRequirements() != null
                    ? new ArrayList<String>(input.getProgramRequirements())
                    : new ArrayList<String>();
            this.divisionsContentOwner = input.getDivisionsContentOwner() != null
                    ? new ArrayList<String>(input.getDivisionsContentOwner())
                    : new ArrayList<String>();
            this.divisionsStudentOversight = input.getDivisionsStudentOversight() != null
                    ? new ArrayList<String>(input.getDivisionsContentOwner())
                    : new ArrayList<String>();
            this.unitsContentOwner = input.getUnitsContentOwner() != null
                    ? new ArrayList<String>(input.getUnitsContentOwner())
                    : new ArrayList<String>();
            this.unitsStudentOversight = input.getUnitsStudentOversight() != null
                    ? new ArrayList<String>(input.getUnitsStudentOversight())
                    : new ArrayList<String>();
            List<LoDisplayInfo> los = new ArrayList<LoDisplayInfo>();
            if (input.getLearningObjectives() != null) {
                for (LoDisplay loDisplay : input.getLearningObjectives()) {
                    LoDisplayInfo loDisplayInfo = new LoDisplayInfo(loDisplay);
                    los.add(loDisplayInfo);
                }
            }
            this.learningObjectives = los;
        }
    }

    @Override
    public VersionInfo getVersion() {
        return version;
    }

    public void setVersion(VersionInfo version) {
        this.version = version;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
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

    public void setEndProgramEntryTerm(String endProgramEntryTermId) {
        this.endProgramEntryTerm = endProgramEntryTermId;
    }

    @Override
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    public void setDivisionsContentOwner(List<String> divisionsContentOwnerIds) {
        this.divisionsContentOwner = divisionsContentOwnerIds;
    }

    @Override
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    public void setDivisionsStudentOversight(List<String> divisionsStudentOversightIds) {
        this.divisionsStudentOversight = divisionsStudentOversightIds;
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
    public List<String> getProgramRequirements() {
        return programRequirements;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
    }

  
}
