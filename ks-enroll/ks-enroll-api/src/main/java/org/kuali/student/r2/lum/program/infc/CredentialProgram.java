package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.lu.infc.AdminOrg;

import java.util.List;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CredentialProgram extends IdEntity {

    /**
     * List of core programs related to this credential program
     * @name Core Program Ids
     */

    public List<String> getCoreProgramIds();

    /**
     * Credential Program Requirements.
     * 
     * @name Program Requirements
     */
    public List<String> getProgramRequirements();

    /**
     * Unique identifier for a learning unit type. Once set at create time, this
     * field may not be updated.
     * 
     * @name Credential Program Type
     */
    public String getCredentialProgramType();

    /**
     * Abbreviated name of the Credential program
     * 
     * @name Short Title
     */
    public String getShortTitle();

    /**
     * Full name of the Credential Program
     * 
     * @name Long Title
     */
    public String getLongTitle();

    /**
     * Information related to the official identification of the credential
     * program, typically in human readable form. Used to officially reference
     * or publish.
     * 
     * @name Transcript Title
     */
    public String getTranscriptTitle();

    /**
     * Diploma Title of the Credential Program
     * 
     * @name Diploma Title
     * 
     */
    public String getDiplomaTitle();

    /**
     * A code that indicates whether this is Graduate, Undergraduage etc
     * 
     * @name Program Level
     */
    public String getProgramLevel();

    /**
     * The composite string that is used to officially reference or publish the
     * Credential program.
     * 
     * @name COde
     */
    public String getCode();

    /**
     * University specific classification
     * 
     * @name University specific classification
     */
    public String getUniversityClassification();

    /**
     * Institution owning the program.
     * 
     * @name Institution
     */
    public AdminOrg getInstitution();
    

    /**
     * Result outcomes from taking the Credential program.
     * 
     * @name Result Options
     */
    public List<String> getResultOptions();

    /**
     * The first academic time period that this credential program would be
     * effective. This may not reflect the first "real" academic time period for
     * this program.
     * 
     * @name Start Term Key
     */
    public String getStartTermKey();

    /**
     * The last academic time period that this credential program would be
     * effective.
     * 
     * @name End Term Key
     */
    public String getEndTermKey();

    /**
     * The last academic time period that this credential program would be
     * available for enrollment. This may not reflect the last "real" academic
     * time period for this program.
     * 
     * @name End Program Entry Term Key
     */
    public String getEndProgramEntryTermKey();

    /**
     * Divisions responsible to make changes to the credential program
     * 
     * @name Divisions Content Owner
     */
    public List<String> getDivisionsContentOwner();

    /**
     * Divisions responsible for student exceptions to the credential program.
     * 
     * @name Divisions Student Oversight
     */
    public List<String> getDivisionsStudentOversight();

    /**
     * Unit responsible to make changes to the credential program
     * 
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /**
     * Unit responsible for student exceptions to the credential program.
     * 
     * @name Units Student Oversight
     */
    public List<String> getUnitsStudentOversight();

    /**
     * Learning Objectives associated with this credential program.
     * 
     * @name Learning Objectives
     */
    public List<? extends LoDisplay> getLearningObjectives();

    /**
     * CIP 2000 code for the credential program
     * 
     * @name CIP 2000 code
     */
    public String getCip2000Code();

    /**
     * CIP 2010 code for the credential program
     * 
     * @name CIP 2010 code
     */
    public String getCip2010Code();
    
    /**
     * 
     * Hegis code for the credential prgram
     * 
     * @name Hegis Code
     */
    public String getHegisCode();

    /**
     * 
     * Selective Enrollment for the credential program
     * 
     * @name Selective Enrollment Code
     */
    public String getSelectiveEnrollmentCode();

}
