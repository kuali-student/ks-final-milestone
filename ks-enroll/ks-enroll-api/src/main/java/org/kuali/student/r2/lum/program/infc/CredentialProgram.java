package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */public interface CredentialProgram extends IdEntity {


      public List<String> getCoreProgramIds() ;
    /**
     * Credential Program Requirements.
     */
    public List<String> getProgramRequirements();

   /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getCredentialProgramType();

     /**
     * Abbreviated name of the Credential program
     */
    public String getShortTitle();

    /**
     * Full name of the Credential Program
     */
    public String getLongTitle();

    /*
     * Information related to the official identification of the credential program, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle() ;

    public String getDiplomaTitle() ;


    /**
     * A code that indicates whether this is Graduate, Undergraduage etc
     */
    public String getProgramLevel() ;

    /**
     * The composite string that is used to officially reference or publish the Credential program.
     */
    public String getCode() ;



    /**
     * University specific classification
     */
    public String getUniversityClassification() ;

    /**
     * Institution owning the program.
     */
    public AdminOrgInfo getInstitution() ;


    /**
     * Result outcomes from taking the Credential program.
     */
    public List<String> getResultOptions() ;


    /**
     * The first academic time period that this credential program would be effective. This may not reflect the first "real" academic time period for this program.
     */
    public String getStartTerm() ;

    /**
     * The last academic time period that this credential program would be effective.
     */
    public String getEndTerm() ;
    /**
     * The last academic time period that this credential program would be available for enrollment. This may not reflect the last "real" academic time period for this program.
     */
    public String getEndProgramEntryTerm() ;


    /**
     * Divisions responsible to make changes to the credential program
     */
    public List<String> getDivisionsContentOwner() ;


    /**
     * Divisions responsible for student exceptions to the credential program.
     */
    public List<String> getDivisionsStudentOversight() ;


    /*
     * Unit responsible to make changes to the credential program
     */
    public List<String> getUnitsContentOwner() ;


    /**
     * Unit responsible for student exceptions to the credential program.
     */
    public List<String> getUnitsStudentOversight() ;


    /**
     * Learning Objectives associated with this credential program.
     */
    public List<LoDisplayInfo> getLearningObjectives();



    public String getCip2000Code() ;

    public String getCip2010Code();

    public String getHegisCode() ;


   public String getSelectiveEnrollmentCode() ;


}
