package org.kuali.student.r2.lum.program.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.versionmanagement.infc.Version;
import org.kuali.student.r2.lum.course.infc.LoDisplay;

/*
 * fields that are common with the credential program
 */
public interface CommonWithCredentialProgram extends IdNamelessEntity {

    /**
     * Information about the Version of this program
     * 
     * @name Version
     */
    public Version getVersion();

    /**
     * Narrative Description of the program
     * 
     * @name Description
     */
    public RichText getDescr();

    /**
     * Abbreviated title used where presentation space is at a premium
     * 
     * @name Short Title
     */
    public String getShortTitle();

    /**
     * Fully expressed title
     * 
     * @name Long Title
     */
    public String getLongTitle();

    /**
     * Title used for transcript reporting purposes
     * 
     * @name Transcript Title
     */
    public String getTranscriptTitle();

    /**
     * Human readable code used to identify this program
     * 
     * The code is typically unique within the particular context (for example
     * the list of majors at the university) but it is not guaranteed to be 
     * completely unique.  
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
     * The first term in which this would be allowed to be offered. 
     * 
     * Note: This may not be the first "real" term during that this was offered.
     * 
     * @name Start Term Id
     */
    public String getStartTerm();

    /**
     * The last term that this would be allowed to be offered.
     * 
     * @name End Term Id
     */
    public String getEndTerm();

    /**
     * The last academic time period that this would be available for
     * new enrollments. T
     * 
     * @name End Program Entry Term Id
     */
    public String getEndProgramEntryTerm();

    /**
     * Identifiers for Divisions responsible to make changes to this program
     * 
     * @name Divisions Content Owner Ids
     */
    public List<String> getDivisionsContentOwner();

    /**
     * Divisions responsible for overseeing students in this program.
     * 
     * For example: Granting exceptions to requirements to students.
     * 
     * @name Divisions Student Oversight Ids
     */
    public List<String> getDivisionsStudentOversight();

    /**
     * Unit responsible for make changes to this program
     * 
     * The unit is typically an academic department but does not have to be.
     * 
     * @name Units Content Owner Ids
     */
    public List<String> getUnitsContentOwner();

    /**
     * Identifier of unit responsible for overseeing students in this program.
     * 
     * For example: granting exceptions to the requirements to students.
     * 
     * The unit is typically an academic department but does not have to be.
     * 
     * @name Units Student Oversight
     */
    public List<String> getUnitsStudentOversight();

    /**
     * Learning Objectives associated with this program.
     * 
     * @name Learning Objectives
     */
    public List<? extends LoDisplay> getLearningObjectives();

    /**
     * Identifiers for Requirements associated with this program
     * 
     * @Name Program Requirement Ids
     */
    public List<String> getProgramRequirements();
}
