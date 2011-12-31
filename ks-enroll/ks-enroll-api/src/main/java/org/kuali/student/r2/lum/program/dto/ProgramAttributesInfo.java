package org.kuali.student.r2.lum.program.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.program.infc.ProgramAttributes;

@SuppressWarnings("serial")
@XmlTransient
public abstract class ProgramAttributesInfo extends IdEntityInfo implements ProgramAttributes {

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
    private String startTermId;

    @XmlElement
    private String endTermId;

    @XmlElement
    private String endProgramEntryTermId;

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
    private List<String> programRequirements;

    @XmlElement
    private List<String> divisionsContentOwner;

    @XmlElement
    private List<String> divisionsStudentOversight;

    @XmlElement
    private List<String> unitsContentOwner;

    @XmlElement
    private List<String> unitsStudentOversight;

    public ProgramAttributesInfo() {

    }

    public ProgramAttributesInfo(ProgramAttributes programAttributes) {
        super(programAttributes);
        if (programAttributes != null) {
            this.shortTitle = programAttributes.getShortTitle();
            this.longTitle = programAttributes.getLongTitle();
            this.transcriptTitle = programAttributes.getTranscriptTitle();
            this.diplomaTitle = programAttributes.getDiplomaTitle();
            this.code = programAttributes.getCode();
            this.universityClassification = programAttributes.getUniversityClassification();
            this.startTermId = programAttributes.getStartTermId();
            this.endTermId = programAttributes.getEndTermId();
            this.endProgramEntryTermId = programAttributes.getEndProgramEntryTermId();
            this.programRequirements = programAttributes.getProgramRequirements() != null ? new ArrayList<String>(programAttributes.getProgramRequirements()) : new ArrayList<String>();
            this.divisionsContentOwner = programAttributes.getDivisionsContentOwner() != null ? new ArrayList<String>(programAttributes.getDivisionsContentOwner()) : new ArrayList<String>();
            this.divisionsStudentOversight = programAttributes.getDivisionsStudentOversight() != null ? new ArrayList<String>(programAttributes.getDivisionsContentOwner()) : new ArrayList<String>();
            this.unitsContentOwner = programAttributes.getUnitsContentOwner() != null ? new ArrayList<String>(programAttributes.getUnitsContentOwner()) : new ArrayList<String>();
            this.unitsStudentOversight = programAttributes.getUnitsStudentOversight() != null ? new ArrayList<String>(programAttributes.getUnitsStudentOversight()) : new ArrayList<String>();
            this.catalogDescr = programAttributes.getCatalogDescr() != null ? new RichTextInfo(programAttributes.getCatalogDescr()) : null;
            this.catalogPublicationTargets = programAttributes.getCatalogPublicationTargets() != null ? new ArrayList<String>(programAttributes.getCatalogPublicationTargets())
                    : new ArrayList<String>();

            List<LoDisplayInfo> learningObjectives = new ArrayList<LoDisplayInfo>();

            if (programAttributes.getLearningObjectives() != null) {
                for (LoDisplay loDisplay : programAttributes.getLearningObjectives()) {
                    LoDisplayInfo loDisplayInfo = new LoDisplayInfo(loDisplay);
                    learningObjectives.add(loDisplayInfo);
                }
            }
            this.learningObjectives = learningObjectives;
            this.cip2000Code = programAttributes.getCip2000Code();
            this.diplomaTitle = programAttributes.getDiplomaTitle();
            this.hegisCode = programAttributes.getHegisCode();
            this.selectiveEnrollmentCode = programAttributes.getSelectiveEnrollmentCode();
            this.cip2010Code = programAttributes.getCip2010Code();

        }
    }

    @Override
    public List<String> getProgramRequirements() {
        return programRequirements;
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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getUniversityClassification() {
        return universityClassification;
    }

    @Override
    public String getStartTermId() {
        return startTermId;
    }

    @Override
    public String getEndTermId() {
        return endTermId;
    }

    @Override
    public String getEndProgramEntryTermId() {
        return endProgramEntryTermId;
    }

    @Override
    public List<String> getDivisionsContentOwner() {
        return divisionsContentOwner;
    }

    @Override
    public List<String> getDivisionsStudentOversight() {
        return divisionsStudentOversight;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    @Override
    public List<String> getUnitsStudentOversight() {
        return unitsStudentOversight;
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
    public List<? extends LoDisplay> getLearningObjectives() {
        return learningObjectives;
    }

    @Override
    public String getCip2000Code() {
        return cip2000Code;
    }

    @Override
    public String getCip2010Code() {
        return cip2010Code;
    }

    @Override
    public String getHegisCode() {
        return hegisCode;
    }

    @Override
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

}
