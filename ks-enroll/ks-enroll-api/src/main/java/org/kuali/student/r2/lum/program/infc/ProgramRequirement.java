package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sambitpatnaik
 * Date: 10/27/11
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ProgramRequirement extends IdEntity{

    public String getShortTitle() ;


    public String getLongTitle() ;


    public List<LoDisplayInfo> getLearningObjectives() ;

    public StatementTreeViewInfo getStatement() ;


    public Integer getMinCredits() ;


    public Integer getMaxCredits();

}
