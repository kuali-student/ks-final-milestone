package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sambitpatnaik
 * Date: 10/27/11
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CoreProgram  extends IdEntity{
    
    /**
     * Core Program Requirements.
     */
    public List<String> getProgramRequirements() ;


    /**
     * Abbreviated name of the Core requirement 
     */
    public String getShortTitle() ;



    /**
     * Full name of the Core Requirement    
     */
    public String getLongTitle();

    /**
     * Information related to the official identification of the Core requirement, typically in human readable form. Used to officially reference or publish.
     */
    
    public String getTranscriptTitle() ;

    
    public String getDiplomaTitle() ;
    /**
     * The composite string that is used to officially reference or publish the Core Program.   
     */
    
    public String getCode() ;

    /**
     * University specific classification e.g General Education Program 
     */
    
    public String getUniversityClassification();
    /**
     * The first academic time period that this clu would be effective. This may not reflect the first "real" academic time period for this Core.   
     */
    
    public String getStartTerm() ;
    /**
     * The last academic time period that this Core would be effective. 
     */
    
    public String getEndTerm() ;
    /**
     * The last academic time period that this Core would be available for enrollment. This may not reflect the last "real" academic time period for this requirement.  
     */
    
    public String getEndProgramEntryTerm();
    /**
     * Divisions responsible to make changes to the CORE requirements   
     */
    
    public List<String> getDivisionsContentOwner();
    /**
     * Divisions responsible for student exceptions to the requirements.    
     */
    
    public List<String> getDivisionsStudentOversight() ;
    /**
     * Unit responsible to make changes to the CORE requirements    
     */
    
    public List<String> getUnitsContentOwner() ;

    /**
     * Unit responsible for student exceptions to the requirements. 
     */
    
    public List<String> getUnitsStudentOversight();
    /**
     * Narrative description of the Core that will show up in Catalog
     */
    
    public RichText getCatalogDescr() ;

    /**
     * List of catalog targets where information will be published.   
     */
    
    public List<String> getCatalogPublicationTargets() ;
    /**
     * An URL for additional information about the Core Requirement.    
     */
    
    public String getReferenceURL() ;

    /**
     * Learning Objectives associated with this Core requirement.   
     */
    
    public List<LoDisplayInfo> getLearningObjectives();

    public String getCip2000Code() ;


    public String getCip2010Code() ;


    public String getHegisCode() ;
    
    public String getSelectiveEnrollmentCode() ;

}
