package org.kuali.student.r2.lum.program.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.infc.LoDisplay;

public interface ProgramAttributes extends IdEntity {

    /**
     * Core Program Requirements.
     * 
     * @name Program Requirements
     */
    public List<String> getProgramRequirements();

    /**
     * Abbreviated name of the Core requirement
     * 
     * @name Short Title
     */
    public String getShortTitle();

    /**
     * Full name of the Core Requirement
     * 
     * @name Long Title
     */
    public String getLongTitle();

    /**
     * Information related to the official identification of the Core
     * requirement, typically in human readable form. Used to officially
     * reference or publish.
     * 
     * @name Transcript Title
     */

    public String getTranscriptTitle();

    /**
     * @name Diploma Title
     */
    public String getDiplomaTitle();

    /**
     * The composite string that is used to officially reference or publish the
     * Core Program.
     * 
     * @name Code
     * @required
     */

    public String getCode();

    /**
     * University specific classification e.g General Education Program
     * 
     * @name University Classification
     */

    public String getUniversityClassification();

    /**
     * The first academic time period that this clu would be effective. This may
     * not reflect the first "real" academic time period for this Core.
     * 
     * @name Start Term id
     */

    public String getStartTermId();

    /**
     * The last academic time period that this Core would be effective.
     * 
     * @name End Term id
     */

    public String getEndTermId();

    /**
     * The last academic time period that this Core would be available for
     * enrollment. This may not reflect the last "real" academic time period for
     * this requirement.
     * 
     * @name End Program Entry Term id
     */

    public String getEndProgramEntryTermId();

    /**
     * Divisions responsible to make changes to the CORE requirements
     * 
     * @name Divisions Content Owner
     */

    public List<String> getDivisionsContentOwner();

    /**
     * Divisions responsible for student exceptions to the requirements.
     * 
     * @name Divisions Student Oversight
     */

    public List<String> getDivisionsStudentOversight();

    /**
     * Unit responsible to make changes to the CORE requirements
     * 
     * @name Units Content Owner
     */

    public List<String> getUnitsContentOwner();

    /**
     * Unit responsible for student exceptions to the requirements.
     * 
     * @name Units Student Oversight
     */

    public List<String> getUnitsStudentOversight();

    /**
     * Narrative description of the Core that will show up in Catalog
     * 
     * @name Catalog Descr
     */

    public RichText getCatalogDescr();

    /**
     * List of catalog targets where information will be published.
     * 
     * @name Catalog Publication Targets
     */

    public List<String> getCatalogPublicationTargets();

    
    /**
     * Learning Objectives associated with this Core requirement.
     * 
     * @name Learning Objectives
     */

    public List<? extends LoDisplay> getLearningObjectives();

    /**
     * CIP 2000 Code for the Program
     * 
     * @name Cip 2000 code
     */
    public String getCip2000Code();

    /**
     * CIP 2010 Code for the Program
     * 
     * @name Cip 2010 code
     */
    public String getCip2010Code();

    /**
     * HEGIS Code for the Program
     * 
     * @name Hegis Code
     */
    public String getHegisCode();

    /**
     * Specifies if the Program is Selective Major, Limited Enrollment program
     * or Selective Admissions
     * 
     * @name Selective Enrollment Code
     */
    public String getSelectiveEnrollmentCode();

}
