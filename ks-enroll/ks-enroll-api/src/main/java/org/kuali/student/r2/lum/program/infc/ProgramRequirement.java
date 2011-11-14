package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import java.util.List;
/**
 * 
 * This is a description of what this class does - sambit don't forget to fill this in. 
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 *
 */
public interface ProgramRequirement extends IdEntity {

    public String getShortTitle();

    public String getLongTitle();

    public List<LoDisplayInfo> getLearningObjectives();

    public StatementTreeViewInfo getStatement();

    public Integer getMinCredits();

    public Integer getMaxCredits();

}
