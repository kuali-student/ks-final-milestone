package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import java.util.List;
/**
 * 
 *  Detailed information about a program requirement
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 *
 */
public interface ProgramRequirement extends IdEntity {
    
    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getShortTitle();
    
    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getLongTitle();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public List<LoDisplayInfo> getLearningObjectives();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public StatementTreeViewInfo getStatement();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public Integer getMinCredits();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public Integer getMaxCredits();

}
