package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sambitpatnaik
 * Date: 10/27/11
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MinorDiscipline extends IdEntity{
    public String getCredentialProgramId() ;
    public List<String> getProgramRequirements() ;


}
