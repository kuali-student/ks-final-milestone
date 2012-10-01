package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import java.util.List;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Detailed information about a program requirement
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 *
 */
public interface ProgramRequirement extends IdNamelessEntity {

    /**
     * Description of the requirement.
     * 
     * @name Description
     */
    public RichText getDescr();

    /**
     * 
     * Brief title for the requirement.
     * 
     * @name Short Title
     */
    public String getShortTitle();

    /**
     * Long name for the requirement
     * 
     * @name Long Title
     */
    public String getLongTitle();

    /**
     * Learning Objectives for the Program Requirement
     * 
     * @name Learning Objectives
     */
    public List<LoDisplayInfo> getLearningObjectives();

    /**
     * Rule Statement for the requirement 
     * 
     * @name Statement
     */
    public StatementTreeViewInfo getStatement();

    /**
     * Minimum credits awarded
     * 
     * @name Max Credits
     */
    public Integer getMinCredits();

    /**
     *  Maximum credits awarded
     * 
     * @name Min Credits
     */
    public Integer getMaxCredits();
}
