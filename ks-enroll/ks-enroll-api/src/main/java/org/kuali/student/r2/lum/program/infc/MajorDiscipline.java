package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lu.infc.Accreditation;
import org.kuali.student.r2.lum.lu.infc.CluInstructor;

import java.util.Date;
import java.util.List;


/**
 *
 */

public interface MajorDiscipline extends IdEntity {

    /**
     * Indicates if the program is full time, part time, both etc
     */
    public String getIntensity();

    /**
     * An URL for additional information about the Major.
     */
    public String getReferenceURL();

    /**
     * Instructors associated with this Major. This may not be an exhaustive
     * list, and instead may only be used to indicate potential instructors in
     * publication.
     */
    public List<? extends CluInstructor> getPublishedInstructors();

    /**
     * Identifier of the credential program under which the major belongs
     */
    public String getCredentialProgramId();

    /**
     * Program variations for the Major
     */
    public List<? extends ProgramVariation> getVariations();

    /**
     * The composite string that is used to officially reference or publish the
     * Major. Note it may have an internal structure that each Institution may
     * want to enforce. This structure may be composed from the other parts of
     * the structure such as Level amp; Division, but may include items such as
     * cluType.
     */
    public String getCode();

    /**
     * CIP 2000 Code for the Program
     */
    public String getCip2000Code();

    /**
     * CIP 2010 Code for the Program
     */
    public String getCip2010Code();

    /**
     * HEGIS Code for the Program
     */
    public String getHegisCode();

    /**
     * University specific classification e.g Major(Bacc), Specialization
     */
    public String getUniversityClassification();

    /**
     * Specifies if the Major is Selective Major, Limited Enrollment program or
     * Selective Admissions
     */
    public String getSelectiveEnrollmentCode();

    /**
     * The first academic time period that this clu would be effective. This may
     * not reflect the first "real" academic time period for this Major.
     */
    public String getStartTermId();

    /**
     * The last academic time period that this Major would be effective.
     */
    public String getEndTermId();
    
    public String getNextReviewPeriod();

    /**
     * Date and time the Course became effective. This is a similar concept to
     * the effective date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate();

    /**
     * Abbreviated name of the Major Discipline
     */
    public String getShortTitle();

    /**
     * Full name of the Major Discipline
     */
    public String getLongTitle();

    /**
     * Information related to the official identification of the Major
     * discipline, typically in human readable form. Used to officially
     * reference or publish.
     */
    public String getTranscriptTitle();

    public String getDiplomaTitle();

    /**
     * Narrative description of the Major that will show up in Catalog
     */
    public RichText getCatalogDescr();

    /**
     * List of catalog targets where major information will be published.
     */
    public List<String> getCatalogPublicationTargets();

    /**
     * Learning Objectives associated with this Major.
     */
    public List<LoDisplayInfo> getLearningObjectives();

    /**
     * Places where this Major might be offered
     */
    public List<String> getCampusLocations();

    public String getCoreProgramId();

    /**
     * Major Discipline Program Requirements.
     */
    public List<String> getProgramRequirements();

    public List<String> getResultOptions();

    public TimeAmount getStdDuration();

    public String getEndProgramEntryTermId();

    public List<? extends Accreditation> getAccreditingAgencies();

    public List<String> getDivisionsContentOwner();

    public List<String> getDivisionsStudentOversight();

    public List<String> getDivisionsDeployment();

    public List<String> getDivisionsFinancialResources();

    public List<String> getDivisionsFinancialControl();

    public List<String> getUnitsContentOwner();

    public List<String> getUnitsStudentOversight();

    public List<String> getUnitsDeployment();

    public List<String> getUnitsFinancialResources();

    public List<String> getUnitsFinancialControl();
}
