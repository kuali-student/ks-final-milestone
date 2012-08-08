package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.lum.clu.infc.Accreditation;
import org.kuali.student.r2.lum.clu.infc.CluInstructor;

import java.util.List;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;

/**
 * Detailed information about a single major discipline program
 * 
 * This represents a traditional undergraduate major or a graduate area of study 
 * that leads to a degree in a particular discipline.
 */
public interface MajorDiscipline extends CommonWithProgramVariation {

    /**
     * Term when this major is next slated to be reviewed
     * 
     * @name Next Review Period
     */
    public String getNextReviewPeriod();

    /**
     * Instructors associated with this Major. 
     * 
     * This may not be an exhaustive list, and instead may only be used to 
     * indicate potential instructors in publication.
     * 
     * @name Published Instructors
     */
    public List<? extends CluInstructor> getPublishedInstructors();

    /**
     * Identifier of the credential program under which the major belongs
     * 
     * @name Credential Program Id
     */
    public String getCredentialProgramId();

    /**
     * External Agencies that accredit this major
     * 
     * @name Accrediting Agencies
     */
    public List<? extends Accreditation> getAccreditingAgencies();

    /**
     * Program variations for the Major
     * 
     * Also called specializations or tracks.
     * 
     * @name Variations
     */
    public List<? extends ProgramVariation> getVariations();

    /**
     * The core program requirements associated with this organizational unit
     * 
     * Also called School Core or School Wide Common Requirements.
     * 
     * @name Organization Core Program
     */
    public CoreProgramInfo getOrgCoreProgram();
}
